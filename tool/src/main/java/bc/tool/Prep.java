package bc.tool;

import java.io.*;
import java.net.URI;
import java.nio.file.*;
import java.util.*;
import java.util.jar.*;
import java.util.zip.*;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.analysis.*;

public class Prep {
    record Rd(String o, String n, String d, boolean st) {}
    record Vh(int k, String o, String n, String fd) {}

    static Map<String, byte[]> src = new LinkedHashMap<>();
    static Map<String, ClassNode> nodes = new HashMap<>();
    static Map<String, Rd> rd = new HashMap<>();
    static Map<String, Vh> vhs = new HashMap<>();
    static Map<String, Vh> offs = new HashMap<>();
    static int nus, nusBad;
    static Set<String> dirty = new HashSet<>();
    static int nvh, nrd;

    public static void main(String[] a) throws Exception {
        var rules = Path.of(a[0]);
        var out = Path.of(a[1]);
        for (var l : Files.readAllLines(rules.resolve("redirect.txt"))) {
            l = l.strip();
            if (l.isEmpty() || l.startsWith("#")) continue;
            var p = l.split("\\s+");
            var t = p[1];
            int i = t.indexOf('('), j = t.lastIndexOf('.', i);
            rd.put(p[0], new Rd(t.substring(0, j), t.substring(j + 1, i), t.substring(i), p.length > 2 && p[2].equals("s")));
        }
        var jdk = new ArrayList<String>();
        for (var l : Files.readAllLines(rules.resolve("jdk.txt"))) if (!l.isBlank() && !l.startsWith("#")) jdk.add(l.strip());
        for (int i = 2; i < a.length; i++) readJar(Path.of(a[i]));
        var fs = FileSystems.getFileSystem(URI.create("jrt:/"));
        for (var pk : jdk) {
            var root = fs.getPath("/modules/java.base", pk);
            if (!Files.exists(root)) continue;
            try (var w = Files.walk(root)) {
                for (var f : (Iterable<Path>) w::iterator) {
                    var s = f.toString();
                    if (!s.endsWith(".class")) continue;
                    var n = s.substring("/modules/java.base/".length(), s.length() - 6);
                    if (n.endsWith("module-info")) continue;
                    if (!src.containsKey(n)) { src.put(n, Files.readAllBytes(f)); dirty.add(n); }
                }
            }
        }
        for (var e : src.entrySet()) {
            var cn = new ClassNode();
            new ClassReader(e.getValue()).accept(cn, 0);
            nodes.put(e.getKey(), cn);
        }
        for (var cn : nodes.values()) scanVh(cn);
        for (var cn : nodes.values()) scanOff(cn);
        System.out.println("unsafe offsets " + offs.size());
        System.out.println("varhandles " + vhs.size());
        for (var cn : nodes.values()) fix(cn);
        for (var cn : nodes.values()) patch(cn);
        int nn = 0;
        for (var cn : nodes.values()) {
            if (cn.name.startsWith("java/") || cn.name.startsWith("jdk/") || cn.name.startsWith("sun/")) continue;
            for (var m : cn.methods) {
                if ((m.access & Opcodes.ACC_NATIVE) == 0) continue;
                m.access &= ~Opcodes.ACC_NATIVE;
                m.instructions.clear();
                m.instructions.add(new TypeInsnNode(Opcodes.NEW, "java/lang/UnsatisfiedLinkError"));
                m.instructions.add(new InsnNode(Opcodes.DUP));
                m.instructions.add(new LdcInsnNode(cn.name + "." + m.name));
                m.instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "java/lang/UnsatisfiedLinkError", "<init>", "(Ljava/lang/String;)V", false));
                m.instructions.add(new InsnNode(Opcodes.ATHROW));
                m.maxStack = 3;
                dirty.add(cn.name);
                nn++;
            }
        }
        System.out.println("denatived " + nn);
        System.out.println("unsafe calls " + nus + " unresolved " + nusBad);
        stubs();
        sigs();
        Gsg.gen(nodes, dirty);
        clinits();
        System.out.println("vh calls " + nvh + " unresolved " + nbad + " redirects " + nrd + " dirty " + dirty.size());
        try (var jo = new JarOutputStream(Files.newOutputStream(out))) {
            for (var n : dirty) {
                var cn = nodes.get(n);
                var cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                cn.accept(cw);
                jo.putNextEntry(new JarEntry(n + ".class"));
                jo.write(cw.toByteArray());
                jo.closeEntry();
            }
        }
    }

    static final Set<String> TT = Set.of("com/google/gson/reflect/TypeToken", "com/google/common/reflect/TypeToken", "com/google/common/reflect/TypeCapture", "com/google/common/reflect/TypeParameter",
        "io/netty/channel/SimpleChannelInboundHandler", "io/netty/channel/SimpleUserEventChannelHandler", "io/netty/handler/codec/MessageToByteEncoder", "io/netty/handler/codec/MessageToMessageEncoder", "io/netty/handler/codec/MessageToMessageDecoder", "io/netty/handler/codec/MessageToMessageCodec", "io/netty/handler/codec/ByteToMessageCodec");
    static String sg;
    static int sp;

    static boolean known(String n) { return nodes.containsKey(n) || n.startsWith("java/lang/") || n.startsWith("java/util/"); }

    static boolean sys(String n) { return n.startsWith("java/") || n.startsWith("jdk/") || n.startsWith("sun/"); }

    static MethodNode clinit(ClassNode cn) {
        for (var m : cn.methods) if (m.name.equals("<clinit>")) return m;
        return null;
    }

    static void clinits() {
        int n = 0;
        for (var cn : new ArrayList<>(nodes.values())) {
            if ((cn.access & Opcodes.ACC_INTERFACE) != 0 || sys(cn.name)) continue;
            var ci = clinit(cn);
            if (ci == null) continue;
            ClassNode sn = null;
            for (var s = cn.superName; s != null && !sys(s); ) {
                var x = nodes.get(s);
                if (x == null) break;
                if (clinit(x) != null) { sn = x; break; }
                s = x.superName;
            }
            if (sn == null) continue;
            var fs = sn;
            if (sn.methods.stream().noneMatch(m -> m.name.equals("$bci"))) {
                var m = new MethodNode(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC | Opcodes.ACC_SYNTHETIC, "$bci", "()V", null, null);
                m.instructions.add(new InsnNode(Opcodes.RETURN));
                sn.methods.add(m);
                dirty.add(fs.name);
            }
            ci.instructions.insert(new MethodInsnNode(Opcodes.INVOKESTATIC, sn.name, "$bci", "()V", false));
            dirty.add(cn.name);
            n++;
        }
        System.out.println("clinit chains " + n);
    }

    static void sigs() {
        var es = new ArrayList<String[]>();
        for (var cn : nodes.values()) {
            if (cn.signature == null || !TT.contains(cn.superName)) continue;
            es.add(new String[]{cn.name, cn.signature});
        }
        var g = new ClassNode();
        g.visit(Opcodes.V1_5, Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, "bc/gen/Sig", null, "java/lang/Object", null);
        int ch = 0;
        for (int b = 0; b < es.size(); b += 60, ch++) {
            var m = new MethodNode(Opcodes.ACC_STATIC | Opcodes.ACC_PRIVATE, "s" + ch, "(Ljava/lang/Class;)Ljava/lang/reflect/Type;", null, null);
            var il = m.instructions;
            for (int i = b; i < Math.min(es.size(), b + 60); i++) {
                var e = es.get(i);
                var lb = new LabelNode();
                il.add(new VarInsnNode(Opcodes.ALOAD, 0));
                il.add(new LdcInsnNode(Type.getObjectType(e[0])));
                il.add(new JumpInsnNode(Opcodes.IF_ACMPNE, lb));
                sg = e[1];
                sp = 0;
                if (sg.charAt(0) == '<') skipBr();
                emitCls(il);
                il.add(new InsnNode(Opcodes.ARETURN));
                il.add(lb);
            }
            il.add(new InsnNode(Opcodes.ACONST_NULL));
            il.add(new InsnNode(Opcodes.ARETURN));
            g.methods.add(m);
        }
        var m = new MethodNode(Opcodes.ACC_STATIC | Opcodes.ACC_PUBLIC, "sup", "(Ljava/lang/Class;)Ljava/lang/reflect/Type;", null, null);
        for (int i = 0; i < ch; i++) {
            var lb = new LabelNode();
            m.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
            m.instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "bc/gen/Sig", "s" + i, "(Ljava/lang/Class;)Ljava/lang/reflect/Type;", false));
            m.instructions.add(new InsnNode(Opcodes.DUP));
            m.instructions.add(new JumpInsnNode(Opcodes.IFNULL, lb));
            m.instructions.add(new InsnNode(Opcodes.ARETURN));
            m.instructions.add(lb);
            m.instructions.add(new InsnNode(Opcodes.POP));
        }
        m.instructions.add(new InsnNode(Opcodes.ACONST_NULL));
        m.instructions.add(new InsnNode(Opcodes.ARETURN));
        g.methods.add(m);
        nodes.put(g.name, g);
        dirty.add(g.name);
        System.out.println("sigs " + es.size());
    }

    static void skipBr() {
        int d = 0;
        do {
            char c = sg.charAt(sp++);
            if (c == '<') d++;
            else if (c == '>') d--;
        } while (d > 0);
    }

    static void ldcCls(InsnList il, String n) {
        il.add(new LdcInsnNode(Type.getObjectType(known(n) ? n : "java/lang/Object")));
    }

    static void emitCls(InsnList il) {
        sp++;
        var nb = new StringBuilder();
        var al = new ArrayList<InsnList>();
        while (true) {
            char c = sg.charAt(sp);
            if (c == '<') {
                sp++;
                al.clear();
                while (sg.charAt(sp) != '>') {
                    var sub = new InsnList();
                    emitArg(sub);
                    al.add(sub);
                }
                sp++;
            } else if (c == '.') {
                nb.append('$');
                sp++;
            } else if (c == ';') {
                sp++;
                break;
            } else {
                nb.append(c);
                sp++;
            }
        }
        var n = nb.toString();
        if (al.isEmpty() || !known(n)) {
            ldcCls(il, n);
            return;
        }
        ldcCls(il, n);
        il.add(new InsnNode(Opcodes.ACONST_NULL));
        pushArr(il, al);
        il.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "bc/rt/R", "pt", "(Ljava/lang/Class;Ljava/lang/reflect/Type;[Ljava/lang/reflect/Type;)Ljava/lang/reflect/Type;", false));
    }

    static void pushArr(InsnList il, List<InsnList> xs) {
        il.add(new LdcInsnNode(xs.size()));
        il.add(new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/reflect/Type"));
        for (int i = 0; i < xs.size(); i++) {
            il.add(new InsnNode(Opcodes.DUP));
            il.add(new LdcInsnNode(i));
            il.add(xs.get(i));
            il.add(new InsnNode(Opcodes.AASTORE));
        }
    }

    static void emitArg(InsnList il) {
        char c = sg.charAt(sp);
        if (c == '*') {
            sp++;
            var o = new InsnList();
            o.add(new LdcInsnNode(Type.getObjectType("java/lang/Object")));
            pushArr(il, List.of(o));
            pushArr(il, List.of());
            il.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "bc/rt/R", "wc", "([Ljava/lang/reflect/Type;[Ljava/lang/reflect/Type;)Ljava/lang/reflect/Type;", false));
            return;
        }
        if (c == '+' || c == '-') {
            sp++;
            var b = new InsnList();
            emitSig(b);
            var o = new InsnList();
            o.add(new LdcInsnNode(Type.getObjectType("java/lang/Object")));
            if (c == '+') { pushArr(il, List.of(b)); pushArr(il, List.of()); }
            else { pushArr(il, List.of(o)); pushArr(il, List.of(b)); }
            il.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "bc/rt/R", "wc", "([Ljava/lang/reflect/Type;[Ljava/lang/reflect/Type;)Ljava/lang/reflect/Type;", false));
            return;
        }
        emitSig(il);
    }

    static void emitSig(InsnList il) {
        char c = sg.charAt(sp);
        if (c == 'L') { emitCls(il); return; }
        if (c == 'T') {
            int e = sg.indexOf(';', sp);
            il.add(new LdcInsnNode(sg.substring(sp + 1, e)));
            sp = e + 1;
            il.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "bc/rt/R", "tv", "(Ljava/lang/String;)Ljava/lang/reflect/Type;", false));
            return;
        }
        if (c == '[') {
            sp++;
            char d = sg.charAt(sp);
            if ("BCDFIJSZ".indexOf(d) >= 0) {
                sp++;
                il.add(new LdcInsnNode(Type.getType("[" + d)));
                return;
            }
            emitSig(il);
            il.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "bc/rt/R", "ga", "(Ljava/lang/reflect/Type;)Ljava/lang/reflect/Type;", false));
            return;
        }
        sp++;
        il.add(new LdcInsnNode(Type.getType("Ljava/lang/Object;")));
    }

    static void readJar(Path p) throws IOException {
        try (var z = new ZipFile(p.toFile())) {
            for (var e : Collections.list(z.entries())) {
                var n = e.getName();
                if (!n.endsWith(".class") || n.startsWith("META-INF/") || n.endsWith("module-info.class")) continue;
                n = n.substring(0, n.length() - 6);
                if (src.containsKey(n)) continue;
                src.put(n, z.getInputStream(e).readAllBytes());
            }
        }
    }

    static Frame<SourceValue>[] an(String o, MethodNode m) {
        try {
            return new Analyzer<>(new SourceInterpreter()).analyze(o, m);
        } catch (AnalyzerException x) {
            return null;
        }
    }

    static AbstractInsnNode one(SourceValue v) {
        return v.insns.size() == 1 ? v.insns.iterator().next() : null;
    }

    static Frame<SourceValue>[] cfr;
    static MethodNode cm;

    static Object cst(SourceValue v) {
        var i = one(v);
        for (int d = 0; d < 4 && i instanceof VarInsnNode vi && cfr != null; d++) {
            var f = cfr[cm.instructions.indexOf(vi)];
            if (f == null) return null;
            var st = one(f.getLocal(vi.var));
            if (!(st instanceof VarInsnNode sv) || sv.getOpcode() != Opcodes.ASTORE) return null;
            var f2 = cfr[cm.instructions.indexOf(sv)];
            i = one(f2.getStack(f2.getStackSize() - 1));
        }
        if (i instanceof LdcInsnNode l) return l.cst;
        if (i instanceof FieldInsnNode f && f.getOpcode() == Opcodes.GETSTATIC && f.name.equals("TYPE")) {
            return switch (f.owner) {
                case "java/lang/Integer" -> Type.INT_TYPE;
                case "java/lang/Long" -> Type.LONG_TYPE;
                case "java/lang/Boolean" -> Type.BOOLEAN_TYPE;
                case "java/lang/Byte" -> Type.BYTE_TYPE;
                case "java/lang/Short" -> Type.SHORT_TYPE;
                case "java/lang/Character" -> Type.CHAR_TYPE;
                case "java/lang/Float" -> Type.FLOAT_TYPE;
                case "java/lang/Double" -> Type.DOUBLE_TYPE;
                default -> null;
            };
        }
        return null;
    }

    static String fdesc(String o, String n, Type t) {
        var cn = nodes.get(o);
        if (cn != null) for (var f : cn.fields) if (f.name.equals(n)) return f.desc;
        return t == null ? null : t.getDescriptor();
    }

    static void scanVh(ClassNode cn) {
        for (var m : cn.methods) {
            if (!m.name.equals("<clinit>")) continue;
            boolean has = false;
            for (var i : m.instructions) if (i instanceof MethodInsnNode mi && mi.desc.endsWith("Ljava/lang/invoke/VarHandle;")) has = true;
            if (!has) continue;
            var fr = an(cn.name, m);
            if (fr == null) { System.out.println("vh: analyze failed " + cn.name); continue; }
            cfr = fr;
            cm = m;
            var ins = m.instructions.toArray();
            for (int k = 0; k < ins.length; k++) {
                if (!(ins[k] instanceof FieldInsnNode ps) || ps.getOpcode() != Opcodes.PUTSTATIC || !ps.desc.equals("Ljava/lang/invoke/VarHandle;")) continue;
                var f = fr[k];
                if (f == null) continue;
                var mi = one(f.getStack(f.getStackSize() - 1));
                while (mi instanceof TypeInsnNode) {
                    var ff = fr[m.instructions.indexOf(mi)];
                    mi = one(ff.getStack(ff.getStackSize() - 1));
                }
                if (!(mi instanceof MethodInsnNode c)) continue;
                var cf = fr[m.instructions.indexOf(c)];
                int sz = cf.getStackSize();
                Vh v = null;
                if (c.owner.equals("jdk/internal/invoke/MhUtil") && c.name.equals("findVarHandle")) {
                    int na = Type.getArgumentTypes(c.desc).length;
                    var o = na == 4 ? cst(cf.getStack(sz - 3)) : Type.getObjectType(cn.name);
                    var n = cst(cf.getStack(sz - 2));
                    var t = cst(cf.getStack(sz - 1));
                    if (o instanceof Type ot && n instanceof String ns) {
                        var fd = fdesc(ot.getInternalName(), ns, t instanceof Type tt ? tt : null);
                        if (fd != null) v = new Vh(0, ot.getInternalName(), ns, fd);
                    }
                } else if (c.name.equals("findVarHandle") || c.name.equals("findStaticVarHandle")) {
                    var o = cst(cf.getStack(sz - 3));
                    var n = cst(cf.getStack(sz - 2));
                    var t = cst(cf.getStack(sz - 1));
                    if (o instanceof Type ot && n instanceof String ns) {
                        var fd = fdesc(ot.getInternalName(), ns, t instanceof Type tt ? tt : null);
                        if (fd != null) v = new Vh(c.name.equals("findVarHandle") ? 0 : 1, ot.getInternalName(), ns, fd);
                    }
                } else if (c.name.equals("arrayElementVarHandle")) {
                    var t = cst(cf.getStack(sz - 1));
                    if (t instanceof Type at) v = new Vh(2, at.getDescriptor(), null, at.getElementType().getDescriptor().length() == 1 && at.getDimensions() == 1 ? at.getElementType().getDescriptor() : at.getDescriptor().substring(1));
                }
                if (v == null) { System.out.println("vh: unresolved " + cn.name + "." + ps.name + " via " + c.owner + "." + c.name); continue; }
                vhs.put(ps.owner + "." + ps.name, v);
            }
        }
    }

    static void patch(ClassNode cn) {
        for (var m : cn.methods) if (m.name.equals("fillInStackTrace") && m.desc.equals("()Ljava/lang/Throwable;") && (m.access & Opcodes.ACC_SYNCHRONIZED) != 0) {
            m.access &= ~Opcodes.ACC_SYNCHRONIZED;
            dirty.add(cn.name);
        }
        switch (cn.name) {
            case "net/minecraft/util/profiling/jfr/JvmProfiler" -> {
                for (var m : cn.methods) if (m.name.equals("<clinit>")) {
                    m.instructions.clear();
                    m.tryCatchBlocks.clear();
                    var n = "net/minecraft/util/profiling/jfr/JvmProfiler$NoOpProfiler";
                    m.instructions.add(new TypeInsnNode(Opcodes.NEW, n));
                    m.instructions.add(new InsnNode(Opcodes.DUP));
                    m.instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, n, "<init>", "()V", false));
                    m.instructions.add(new FieldInsnNode(Opcodes.PUTSTATIC, cn.name, "INSTANCE", "Lnet/minecraft/util/profiling/jfr/JvmProfiler;"));
                    m.instructions.add(new InsnNode(Opcodes.RETURN));
                    m.localVariables = null;
                    dirty.add(cn.name);
                }
            }
            default -> {}
        }
    }

    static boolean isU(String o) { return o.equals("jdk/internal/misc/Unsafe") || o.equals("sun/misc/Unsafe"); }

    static void scanOff(ClassNode cn) {
        for (var m : cn.methods) {
            if (!m.name.equals("<clinit>")) continue;
            boolean has = false;
            for (var i : m.instructions) if (i instanceof MethodInsnNode mi && isU(mi.owner) && mi.name.equals("objectFieldOffset")) has = true;
            if (!has) continue;
            var fr = an(cn.name, m);
            if (fr == null) continue;
            cfr = fr;
            cm = m;
            var ins = m.instructions.toArray();
            for (int k = 0; k < ins.length; k++) {
                if (!(ins[k] instanceof FieldInsnNode ps) || ps.getOpcode() != Opcodes.PUTSTATIC || !ps.desc.equals("J")) continue;
                var f = fr[k];
                if (f == null) continue;
                var c = one(f.getStack(f.getStackSize() - 1));
                if (!(c instanceof MethodInsnNode mi) || !isU(mi.owner) || !mi.name.equals("objectFieldOffset") || !mi.desc.equals("(Ljava/lang/Class;Ljava/lang/String;)J")) continue;
                var cf = fr[m.instructions.indexOf(mi)];
                int sz = cf.getStackSize();
                var o = cst(cf.getStack(sz - 2));
                var n = cst(cf.getStack(sz - 1));
                if (o instanceof Type ot && n instanceof String ns) {
                    var fd = fdesc(ot.getInternalName(), ns, null);
                    if (fd != null) offs.put(ps.owner + "." + ps.name, new Vh(3, ot.getInternalName(), ns, fd));
                }
            }
        }
    }

    static String uop(String n) {
        n = n.replace("compareAndSwap", "compareAndSet").replace("putOrdered", "put");
        n = n.replaceAll("(Int|Long|Reference|Object|Boolean|Byte|Short|Char|Float|Double)", "");
        n = n.replaceAll("(Volatile|Acquire|Release|Opaque|Plain)$", "");
        if (n.equals("put")) n = "set";
        return n;
    }

    static final Set<String> UOPS = Set.of("get", "set", "compareAndSet", "weakCompareAndSet", "compareAndExchange", "getAndSet", "getAndAdd", "getAndBitwiseOr", "getAndBitwiseAnd", "getAndBitwiseXor");

    static MethodNode konst(String hn, String hd, long v) {
        var m = new MethodNode(Opcodes.ACC_STATIC | Opcodes.ACC_SYNTHETIC | Opcodes.ACC_PRIVATE, hn, hd, null, null);
        var rt = Type.getReturnType(hd);
        if (rt.getSort() == Type.LONG) { m.instructions.add(new LdcInsnNode(v)); m.instructions.add(new InsnNode(Opcodes.LRETURN)); }
        else if (rt.getSort() == Type.VOID) m.instructions.add(new InsnNode(Opcodes.RETURN));
        else if (rt.getSort() >= Type.ARRAY) { m.instructions.add(new InsnNode(Opcodes.ACONST_NULL)); m.instructions.add(new InsnNode(Opcodes.ARETURN)); }
        else { m.instructions.add(new LdcInsnNode((int) v)); m.instructions.add(new InsnNode(Opcodes.IRETURN)); }
        return m;
    }

    static void stubs() {
        for (var u : List.of("jdk/internal/misc/Unsafe", "sun/misc/Unsafe")) {
            var cn = new ClassNode();
            cn.visit(Opcodes.V21, Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, u, null, "java/lang/Object", null);
            var g = new MethodNode(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "getUnsafe", "()L" + u + ";", null, null);
            g.instructions.add(new InsnNode(Opcodes.ACONST_NULL));
            g.instructions.add(new InsnNode(Opcodes.ARETURN));
            cn.methods.add(g);
            cn.fields.add(new FieldNode(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC | Opcodes.ACC_FINAL, "theUnsafe", "L" + u + ";", null, null));
            nodes.put(u, cn);
            dirty.add(u);
        }
    }

    static void fix(ClassNode cn) {
        var add = new ArrayList<MethodNode>();
        var hk = new HashMap<String, String>();
        for (var m : new ArrayList<>(cn.methods)) {
            Frame<SourceValue>[] fr = null;
            boolean anaDone = false;
            for (var i : m.instructions.toArray()) {
                if (i instanceof InvokeDynamicInsnNode id) {
                    for (int a = 0; a < id.bsmArgs.length; a++) {
                        if (!(id.bsmArgs[a] instanceof Handle h)) continue;
                        var r = rd.get(h.getOwner() + "." + h.getName() + h.getDesc());
                        if (r == null) continue;
                        id.bsmArgs[a] = new Handle(Opcodes.H_INVOKESTATIC, r.o, r.n, r.d, false);
                        dirty.add(cn.name);
                        nrd++;
                    }
                    continue;
                }
                if (!(i instanceof MethodInsnNode mi)) continue;
                var key = mi.owner + "." + mi.name + mi.desc;
                var r = rd.get(key);
                if (r == null && mi.getOpcode() != Opcodes.INVOKESTATIC) r = rd.get("*." + mi.name + mi.desc);
                if (r != null && mi.name.equals("<init>")) {
                    if (!anaDone) { fr = an(cn.name, m); anaDone = true; }
                    if (fr == null) continue;
                    var f = fr[m.instructions.indexOf(mi)];
                    int na = Type.getArgumentTypes(mi.desc).length;
                    var rcv = one(f.getStack(f.getStackSize() - na - 1));
                    if (!(rcv instanceof InsnNode dup) || dup.getOpcode() != Opcodes.DUP) continue;
                    var nw = dup.getPrevious();
                    while (nw != null && nw.getOpcode() < 0) nw = nw.getPrevious();
                    if (!(nw instanceof TypeInsnNode t) || t.getOpcode() != Opcodes.NEW) continue;
                    m.instructions.remove(nw);
                    m.instructions.remove(dup);
                    m.instructions.set(mi, new MethodInsnNode(Opcodes.INVOKESTATIC, r.o, r.n, r.d, false));
                    dirty.add(cn.name);
                    nrd++;
                    anaDone = false;
                    fr = null;
                    continue;
                }
                if (r != null) {
                    m.instructions.set(mi, new MethodInsnNode(Opcodes.INVOKESTATIC, r.o, r.n, r.d, false));
                    dirty.add(cn.name);
                    nrd++;
                    continue;
                }
                if (isU(mi.owner) && mi.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                    var hd = "(L" + mi.owner + ";" + mi.desc.substring(1);
                    var op = uop(mi.name);
                    var at = Type.getArgumentTypes(mi.desc);
                    Vh v = null;
                    if (UOPS.contains(op) && at.length >= 2 && at[1].getSort() == Type.LONG && at[0].getSort() == Type.OBJECT) {
                        if (!anaDone) { fr = an(cn.name, m); anaDone = true; }
                        if (fr != null) {
                            var f = fr[m.instructions.indexOf(mi)];
                            if (f != null) {
                                var src = one(f.getStack(f.getStackSize() - at.length + 1));
                                if (src instanceof FieldInsnNode g && g.getOpcode() == Opcodes.GETSTATIC) v = offs.get(g.owner + "." + g.name);
                            }
                        }
                    }
                    var k = "u|" + v + "|" + mi.name + "|" + mi.desc;
                    var hn = hk.get(k);
                    if (hn == null) {
                        hn = "us$" + hk.size();
                        hk.put(k, hn);
                        if (mi.name.endsWith("FieldOffset") || mi.name.startsWith("array") || mi.name.endsWith("Fence") || mi.name.equals("addressSize") || mi.name.equals("pageSize") || mi.name.equals("ensureClassInitialized") || mi.name.equals("shouldBeInitialized") || mi.name.equals("loadFence") || mi.name.equals("storeFence") || mi.name.equals("fullFence")) {
                            add.add(konst(hn, hd, mi.name.equals("arrayIndexScale") ? 4 : mi.name.equals("addressSize") ? 8 : 0));
                        } else if (v != null) {
                            add.add(gen(hn, hd, v, op));
                        } else {
                            nusBad++;
                            add.add(bad(hn, hd));
                        }
                    }
                    m.instructions.set(mi, new MethodInsnNode(Opcodes.INVOKESTATIC, cn.name, hn, hd, (cn.access & Opcodes.ACC_INTERFACE) != 0));
                    dirty.add(cn.name);
                    nus++;
                    continue;
                }
                if (mi.owner.equals("java/lang/invoke/MethodHandle") && mi.getOpcode() == Opcodes.INVOKEVIRTUAL && (mi.name.equals("invokeExact") || mi.name.equals("invoke") || mi.name.equals("invokeWithArguments"))) {
                    var hd = "(Ljava/lang/invoke/MethodHandle;" + mi.desc.substring(1);
                    var k = "mh|" + mi.name + "|" + mi.desc;
                    var hn = hk.get(k);
                    if (hn == null) { hn = "mh$" + hk.size(); hk.put(k, hn); add.add(bad(hn, hd)); }
                    m.instructions.set(mi, new MethodInsnNode(Opcodes.INVOKESTATIC, cn.name, hn, hd, (cn.access & Opcodes.ACC_INTERFACE) != 0));
                    dirty.add(cn.name);
                    continue;
                }
                if (!mi.owner.equals("java/lang/invoke/VarHandle") || mi.getOpcode() != Opcodes.INVOKEVIRTUAL) continue;
                if (!anaDone) { fr = an(cn.name, m); anaDone = true; }
                if (fr == null) { System.out.println("vh: analyze failed " + cn.name + "." + m.name); continue; }
                var f = fr[m.instructions.indexOf(mi)];
                if (f == null) continue;
                int na = Type.getArgumentTypes(mi.desc).length;
                var rcv = one(f.getStack(f.getStackSize() - na - 1));
                Vh v = null;
                if (rcv instanceof FieldInsnNode g && g.getOpcode() == Opcodes.GETSTATIC) v = vhs.get(g.owner + "." + g.name);
                var hd = "(Ljava/lang/invoke/VarHandle;" + mi.desc.substring(1);
                var k = v + "|" + mi.name + "|" + mi.desc;
                var hn = hk.get(k);
                if (hn == null) {
                    hn = "vh$" + hk.size();
                    hk.put(k, hn);
                    if (v == null) { nbad++; add.add(bad(hn, hd)); } else add.add(gen(hn, hd, v, mi.name));
                }
                m.instructions.set(mi, new MethodInsnNode(Opcodes.INVOKESTATIC, cn.name, hn, hd, (cn.access & Opcodes.ACC_INTERFACE) != 0));
                dirty.add(cn.name);
                nvh++;
            }
        }
        cn.methods.addAll(add);
    }

    static int nbad;

    static MethodNode bad(String hn, String hd) {
        var m = new MethodNode(Opcodes.ACC_STATIC | Opcodes.ACC_SYNTHETIC | Opcodes.ACC_PRIVATE, hn, hd, null, null);
        m.instructions.add(new TypeInsnNode(Opcodes.NEW, "java/lang/UnsupportedOperationException"));
        m.instructions.add(new InsnNode(Opcodes.DUP));
        m.instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "java/lang/UnsupportedOperationException", "<init>", "()V", false));
        m.instructions.add(new InsnNode(Opcodes.ATHROW));
        return m;
    }

    static int ld(Type t) { return t.getOpcode(Opcodes.ILOAD); }

    static MethodNode gen(String hn, String hd, Vh v, String op) {
        var m = new MethodNode(Opcodes.ACC_STATIC | Opcodes.ACC_SYNTHETIC | Opcodes.ACC_PRIVATE, hn, hd, null, null);
        var ps = Type.getArgumentTypes(hd);
        var rt = Type.getReturnType(hd);
        var ft = Type.getType(v.fd);
        int[] sl = new int[ps.length];
        int s = 0;
        for (int i = 0; i < ps.length; i++) { sl[i] = s; s += ps[i].getSize(); }
        int tmp = s;
        int base = v.k == 0 ? 2 : v.k == 1 ? 1 : 3;
        var il = m.instructions;
        Runnable tgt = () -> {
            if (v.k == 0 || v.k == 3) { il.add(new VarInsnNode(Opcodes.ALOAD, sl[1])); il.add(new TypeInsnNode(Opcodes.CHECKCAST, v.o)); }
            else if (v.k == 2) { il.add(new VarInsnNode(Opcodes.ALOAD, sl[1])); il.add(new TypeInsnNode(Opcodes.CHECKCAST, v.o)); il.add(new VarInsnNode(Opcodes.ILOAD, sl[2])); }
        };
        Runnable lod = () -> {
            if (v.k == 0 || v.k == 3) il.add(new FieldInsnNode(Opcodes.GETFIELD, v.o, v.n, v.fd));
            else if (v.k == 1) il.add(new FieldInsnNode(Opcodes.GETSTATIC, v.o, v.n, v.fd));
            else il.add(new InsnNode(ft.getOpcode(Opcodes.IALOAD)));
        };
        Runnable sto = () -> {
            if (v.k == 0 || v.k == 3) il.add(new FieldInsnNode(Opcodes.PUTFIELD, v.o, v.n, v.fd));
            else if (v.k == 1) il.add(new FieldInsnNode(Opcodes.PUTSTATIC, v.o, v.n, v.fd));
            else il.add(new InsnNode(ft.getOpcode(Opcodes.IASTORE)));
        };
        java.util.function.IntConsumer arg = i -> {
            if (ps[i].getSort() == Type.OBJECT && ps[i].getInternalName().equals("java/lang/Void")) { il.add(new InsnNode(Opcodes.ACONST_NULL)); return; }
            il.add(new VarInsnNode(ld(ps[i]), sl[i]));
            if (ft.getSort() >= Type.ARRAY && !ft.getInternalName().equals("java/lang/Object") && !ps[i].equals(ft)) il.add(new TypeInsnNode(Opcodes.CHECKCAST, ft.getInternalName()));
        };
        Runnable ret = () -> {
            if (rt.getSort() >= Type.ARRAY && !rt.equals(ft) && !rt.getInternalName().equals("java/lang/Object")) il.add(new TypeInsnNode(Opcodes.CHECKCAST, rt.getInternalName()));
            il.add(new InsnNode(rt.getOpcode(Opcodes.IRETURN)));
        };
        java.util.function.Consumer<LabelNode> neq = l -> {
            switch (ft.getSort()) {
                case Type.LONG -> { il.add(new InsnNode(Opcodes.LCMP)); il.add(new JumpInsnNode(Opcodes.IFNE, l)); }
                case Type.FLOAT -> { il.add(new InsnNode(Opcodes.FCMPL)); il.add(new JumpInsnNode(Opcodes.IFNE, l)); }
                case Type.DOUBLE -> { il.add(new InsnNode(Opcodes.DCMPL)); il.add(new JumpInsnNode(Opcodes.IFNE, l)); }
                case Type.ARRAY, Type.OBJECT -> il.add(new JumpInsnNode(Opcodes.IF_ACMPNE, l));
                default -> il.add(new JumpInsnNode(Opcodes.IF_ICMPNE, l));
            }
        };
        var b = op.replaceAll("(Acquire|Release|Plain|Volatile|Opaque)$", "");
        switch (b) {
            case "get" -> { tgt.run(); lod.run(); ret.run(); }
            case "set" -> { tgt.run(); arg.accept(base); sto.run(); il.add(new InsnNode(Opcodes.RETURN)); }
            case "compareAndSet", "weakCompareAndSet" -> {
                var no = new LabelNode();
                tgt.run(); lod.run(); arg.accept(base); neq.accept(no);
                tgt.run(); arg.accept(base + 1); sto.run();
                il.add(new InsnNode(Opcodes.ICONST_1)); il.add(new InsnNode(Opcodes.IRETURN));
                il.add(no); il.add(new InsnNode(Opcodes.ICONST_0)); il.add(new InsnNode(Opcodes.IRETURN));
            }
            case "compareAndExchange" -> {
                var no = new LabelNode();
                tgt.run(); lod.run(); il.add(new VarInsnNode(ft.getOpcode(Opcodes.ISTORE), tmp));
                il.add(new VarInsnNode(ft.getOpcode(Opcodes.ILOAD), tmp)); arg.accept(base); neq.accept(no);
                tgt.run(); arg.accept(base + 1); sto.run();
                il.add(no); il.add(new VarInsnNode(ft.getOpcode(Opcodes.ILOAD), tmp)); ret.run();
            }
            case "getAndSet", "getAndAdd", "getAndBitwiseOr", "getAndBitwiseAnd", "getAndBitwiseXor" -> {
                tgt.run(); lod.run(); il.add(new VarInsnNode(ft.getOpcode(Opcodes.ISTORE), tmp));
                tgt.run();
                if (b.equals("getAndSet")) arg.accept(base);
                else {
                    il.add(new VarInsnNode(ft.getOpcode(Opcodes.ILOAD), tmp));
                    arg.accept(base);
                    int o = switch (b) { case "getAndAdd" -> Opcodes.IADD; case "getAndBitwiseOr" -> Opcodes.IOR; case "getAndBitwiseAnd" -> Opcodes.IAND; default -> Opcodes.IXOR; };
                    var t2 = ft.getSort() == Type.BOOLEAN || ft.getSort() == Type.BYTE || ft.getSort() == Type.SHORT || ft.getSort() == Type.CHAR ? Type.INT_TYPE : ft;
                    il.add(new InsnNode(t2.getOpcode(o)));
                    if (ft.getSort() == Type.BYTE) il.add(new InsnNode(Opcodes.I2B));
                    else if (ft.getSort() == Type.SHORT) il.add(new InsnNode(Opcodes.I2S));
                    else if (ft.getSort() == Type.CHAR) il.add(new InsnNode(Opcodes.I2C));
                }
                sto.run();
                il.add(new VarInsnNode(ft.getOpcode(Opcodes.ILOAD), tmp)); ret.run();
            }
            default -> throw new IllegalStateException("vh op " + op);
        }
        return m;
    }
}
