package bc.tool;

import java.util.*;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

public class Gsg {
    static final String G = "bc/gen/Gs", SN = "Lcom/google/gson/annotations/SerializedName;";
    static final String[] PK = {"com/mojang/authlib/", "com/mojang/realmsclient/"};

    static boolean anon(String n) {
        int i = n.lastIndexOf('$');
        return i >= 0 && i + 1 < n.length() && Character.isDigit(n.charAt(i + 1));
    }

    static boolean sn(List<AnnotationNode> a) {
        if (a != null) for (var x : a) if (x.desc.equals(SN)) return true;
        return false;
    }

    static String jname(FieldNode f) {
        if (f.visibleAnnotations != null) for (var x : f.visibleAnnotations) {
            if (!x.desc.equals(SN) || x.values == null) continue;
            String v = f.name;
            var al = new ArrayList<String>();
            for (int i = 0; i < x.values.size(); i += 2) {
                if (x.values.get(i).equals("value")) v = (String) x.values.get(i + 1);
                else if (x.values.get(i).equals("alternate")) for (var o : (List<?>) x.values.get(i + 1)) al.add((String) o);
            }
            var b = new StringBuilder(v);
            for (var s : al) b.append('\0').append(s);
            return b.toString();
        }
        return f.name;
    }

    static boolean want(ClassNode cn) {
        if ((cn.access & (Opcodes.ACC_INTERFACE | Opcodes.ACC_ANNOTATION)) != 0 || anon(cn.name)) return false;
        var o = cn.name.indexOf('$') < 0 ? cn.name : cn.name.substring(0, cn.name.indexOf('$'));
        if (java.nio.file.Files.exists(java.nio.file.Path.of("rt/src/" + o + ".java"))) return false;
        for (var p : PK) if (cn.name.startsWith(p)) return true;
        for (var f : cn.fields) if (sn(f.visibleAnnotations)) return true;
        return false;
    }

    static List<FieldNode> flds(ClassNode cn, boolean en) {
        var r = new ArrayList<FieldNode>();
        for (var f : cn.fields) {
            if (en) { if ((f.access & Opcodes.ACC_ENUM) != 0) r.add(f); continue; }
            if ((f.access & (Opcodes.ACC_STATIC | Opcodes.ACC_TRANSIENT)) == 0) r.add(f);
        }
        return r;
    }

    static final String[][] PR = {{"Z", "java/lang/Boolean", "z"}, {"B", "java/lang/Byte", "b"}, {"C", "java/lang/Character", "c"}, {"S", "java/lang/Short", "s"},
        {"I", "java/lang/Integer", "i"}, {"J", "java/lang/Long", "j"}, {"F", "java/lang/Float", "f"}, {"D", "java/lang/Double", "d"}};

    static String[] prim(String d) {
        for (var p : PR) if (p[0].equals(d)) return p;
        return null;
    }

    static void box(InsnList il, String d) {
        var p = prim(d);
        if (p != null) il.add(new MethodInsnNode(Opcodes.INVOKESTATIC, p[1], "valueOf", "(" + d + ")L" + p[1] + ";", false));
    }

    static void unbox(InsnList il, String d) {
        var p = prim(d);
        if (p != null) { il.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "bc/rt/Gu", p[2], "(Ljava/lang/Object;)" + d, false)); return; }
        var t = Type.getType(d);
        var n = t.getSort() == Type.ARRAY ? d : t.getInternalName();
        if (!n.equals("java/lang/Object")) il.add(new TypeInsnNode(Opcodes.CHECKCAST, n));
    }

    static void type(InsnList il, FieldNode f) {
        var p = prim(f.desc);
        if (p != null) { il.add(new FieldInsnNode(Opcodes.GETSTATIC, p[1], "TYPE", "Ljava/lang/Class;")); return; }
        Prep.sg = f.signature != null ? f.signature : f.desc;
        Prep.sp = 0;
        Prep.emitSig(il);
    }

    static MethodNode mn(String n, String d) { return new MethodNode(Opcodes.ACC_STATIC | Opcodes.ACC_PRIVATE, n, d, null, null); }

    static void strs(InsnList il, List<String> xs) {
        il.add(new LdcInsnNode(xs.size()));
        il.add(new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/String"));
        for (int i = 0; i < xs.size(); i++) {
            il.add(new InsnNode(Opcodes.DUP));
            il.add(new LdcInsnNode(i));
            il.add(new LdcInsnNode(xs.get(i)));
            il.add(new InsnNode(Opcodes.AASTORE));
        }
    }

    static void fswitch(InsnList il, int var, int n, java.util.function.IntConsumer body, Runnable dflt) {
        var ls = new LabelNode[n];
        for (int i = 0; i < n; i++) ls[i] = new LabelNode();
        var d = new LabelNode();
        il.add(new VarInsnNode(Opcodes.ILOAD, var));
        il.add(new TableSwitchInsnNode(0, n - 1, d, ls));
        for (int i = 0; i < n; i++) { il.add(ls[i]); body.accept(i); }
        il.add(d);
        dflt.run();
    }

    static void gen(Map<String, ClassNode> nodes, Set<String> dirty) {
        var cs = new ArrayList<ClassNode>();
        for (var cn : nodes.values()) if (want(cn)) cs.add(cn);
        cs.sort(Comparator.comparing(c -> c.name));
        var g = new ClassNode();
        g.visit(Opcodes.V1_5, Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL, G, null, "java/lang/Object", null);
        var kinds = new int[cs.size()];
        for (int c = 0; c < cs.size(); c++) {
            var cn = cs.get(c);
            boolean en = "java/lang/Enum".equals(cn.superName) && (cn.access & Opcodes.ACC_ENUM) != 0;
            var fs = flds(cn, en);
            var own = cn.name;
            var canon = new StringBuilder("(");
            for (var f : fs) canon.append(f.desc);
            canon.append(")V");
            boolean rec = !en && "java/lang/Record".equals(cn.superName) && cn.methods.stream().anyMatch(m -> m.name.equals("<init>") && m.desc.equals(canon.toString()));
            boolean noarg = cn.methods.stream().anyMatch(m -> m.name.equals("<init>") && m.desc.equals("()V"));
            boolean abs = (cn.access & Opcodes.ACC_ABSTRACT) != 0;
            kinds[c] = en ? 2 : rec ? 1 : abs ? 3 : 0;

            var n = mn("n" + c, "()[Ljava/lang/String;");
            var nl = new ArrayList<String>();
            for (var f : fs) nl.add(jname(f));
            strs(n.instructions, nl);
            n.instructions.add(new InsnNode(Opcodes.ARETURN));
            g.methods.add(n);

            var t = mn("t" + c, "()[Ljava/lang/reflect/Type;");
            var ti = t.instructions;
            ti.add(new LdcInsnNode(fs.size()));
            ti.add(new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/reflect/Type"));
            if (!en) for (int i = 0; i < fs.size(); i++) {
                ti.add(new InsnNode(Opcodes.DUP));
                ti.add(new LdcInsnNode(i));
                type(ti, fs.get(i));
                ti.add(new InsnNode(Opcodes.AASTORE));
            }
            ti.add(new InsnNode(Opcodes.ARETURN));
            g.methods.add(t);

            var gt = mn("g" + c, "(ILjava/lang/Object;)Ljava/lang/Object;");
            var st = mn("s" + c, "(ILjava/lang/Object;Ljava/lang/Object;)V");
            if (fs.isEmpty()) {
                gt.instructions.add(new InsnNode(Opcodes.ACONST_NULL));
                gt.instructions.add(new InsnNode(Opcodes.ARETURN));
                st.instructions.add(new InsnNode(Opcodes.RETURN));
            } else {
                fswitch(gt.instructions, 0, fs.size(), i -> {
                    var f = fs.get(i);
                    if (en) gt.instructions.add(new FieldInsnNode(Opcodes.GETSTATIC, own, f.name, f.desc));
                    else {
                        gt.instructions.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        gt.instructions.add(new TypeInsnNode(Opcodes.CHECKCAST, own));
                        gt.instructions.add(new FieldInsnNode(Opcodes.GETFIELD, own, f.name, f.desc));
                        box(gt.instructions, f.desc);
                    }
                    gt.instructions.add(new InsnNode(Opcodes.ARETURN));
                }, () -> { gt.instructions.add(new InsnNode(Opcodes.ACONST_NULL)); gt.instructions.add(new InsnNode(Opcodes.ARETURN)); });
                fswitch(st.instructions, 0, fs.size(), i -> {
                    var f = fs.get(i);
                    if (!en) {
                        st.instructions.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        st.instructions.add(new TypeInsnNode(Opcodes.CHECKCAST, own));
                        st.instructions.add(new VarInsnNode(Opcodes.ALOAD, 2));
                        unbox(st.instructions, f.desc);
                        st.instructions.add(new FieldInsnNode(Opcodes.PUTFIELD, own, f.name, f.desc));
                    }
                    st.instructions.add(new InsnNode(Opcodes.RETURN));
                }, () -> st.instructions.add(new InsnNode(Opcodes.RETURN)));
            }
            g.methods.add(gt);
            g.methods.add(st);

            var m = mn("m" + c, "([Ljava/lang/Object;)Ljava/lang/Object;");
            var mi = m.instructions;
            if (en || abs) mi.add(new InsnNode(Opcodes.ACONST_NULL));
            else {
                mi.add(new TypeInsnNode(Opcodes.NEW, own));
                if (rec) {
                    mi.add(new InsnNode(Opcodes.DUP));
                    for (int i = 0; i < fs.size(); i++) {
                        mi.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        mi.add(new LdcInsnNode(i));
                        mi.add(new InsnNode(Opcodes.AALOAD));
                        unbox(mi, fs.get(i).desc);
                    }
                    mi.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, own, "<init>", canon.toString(), false));
                } else if (noarg) {
                    mi.add(new InsnNode(Opcodes.DUP));
                    mi.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, own, "<init>", "()V", false));
                }
            }
            mi.add(new InsnNode(Opcodes.ARETURN));
            g.methods.add(m);
        }

        int ch = 0;
        for (int b = 0; b < cs.size(); b += 60, ch++) {
            var m = mn("i" + ch, "(Ljava/lang/Class;)I");
            var il = m.instructions;
            for (int i = b; i < Math.min(cs.size(), b + 60); i++) {
                var lb = new LabelNode();
                il.add(new VarInsnNode(Opcodes.ALOAD, 0));
                il.add(new LdcInsnNode(Type.getObjectType(cs.get(i).name)));
                il.add(new JumpInsnNode(Opcodes.IF_ACMPNE, lb));
                il.add(new LdcInsnNode(i));
                il.add(new InsnNode(Opcodes.IRETURN));
                il.add(lb);
            }
            il.add(new InsnNode(Opcodes.ICONST_M1));
            il.add(new InsnNode(Opcodes.IRETURN));
            g.methods.add(m);
        }
        var id = new MethodNode(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "id", "(Ljava/lang/Class;)I", null, null);
        for (int i = 0; i < ch; i++) {
            var lb = new LabelNode();
            id.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
            id.instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, G, "i" + i, "(Ljava/lang/Class;)I", false));
            id.instructions.add(new InsnNode(Opcodes.DUP));
            id.instructions.add(new JumpInsnNode(Opcodes.IFLT, lb));
            id.instructions.add(new InsnNode(Opcodes.IRETURN));
            id.instructions.add(lb);
            id.instructions.add(new InsnNode(Opcodes.POP));
        }
        id.instructions.add(new InsnNode(Opcodes.ICONST_M1));
        id.instructions.add(new InsnNode(Opcodes.IRETURN));
        g.methods.add(id);

        int nc = cs.size();
        var kd = new MethodNode(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "kind", "(I)I", null, null);
        if (nc > 0) fswitch(kd.instructions, 0, nc, i -> { kd.instructions.add(new LdcInsnNode(kinds[i])); kd.instructions.add(new InsnNode(Opcodes.IRETURN)); }, () -> {});
        kd.instructions.add(new InsnNode(Opcodes.ICONST_M1));
        kd.instructions.add(new InsnNode(Opcodes.IRETURN));
        g.methods.add(kd);

        disp(g, nc, "names", "(I)[Ljava/lang/String;", "n", "()[Ljava/lang/String;", 0, Opcodes.ARETURN);
        disp(g, nc, "types", "(I)[Ljava/lang/reflect/Type;", "t", "()[Ljava/lang/reflect/Type;", 0, Opcodes.ARETURN);
        disp(g, nc, "get", "(IILjava/lang/Object;)Ljava/lang/Object;", "g", "(ILjava/lang/Object;)Ljava/lang/Object;", 2, Opcodes.ARETURN);
        disp(g, nc, "set", "(IILjava/lang/Object;Ljava/lang/Object;)V", "s", "(ILjava/lang/Object;Ljava/lang/Object;)V", 3, Opcodes.RETURN);
        disp(g, nc, "make", "(I[Ljava/lang/Object;)Ljava/lang/Object;", "m", "([Ljava/lang/Object;)Ljava/lang/Object;", -1, Opcodes.ARETURN);
        nodes.put(G, g);
        dirty.add(G);
        System.out.println("gson classes " + nc);
        try { var b = new StringBuilder(); for (int i = 0; i < nc; i++) b.append(i).append(' ').append(kinds[i]).append(' ').append(cs.get(i).name).append('\n'); java.nio.file.Files.writeString(java.nio.file.Path.of("out/gs.txt"), b); } catch (Exception e) {}
    }

    static void disp(ClassNode g, int nc, String name, String desc, String pre, String sub, int nargs, int ret) {
        var m = new MethodNode(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, name, desc, null, null);
        var il = m.instructions;
        if (nc > 0) fswitch(il, 0, nc, i -> {
            if (nargs < 0) il.add(new VarInsnNode(Opcodes.ALOAD, 1));
            else if (nargs > 0) {
                il.add(new VarInsnNode(Opcodes.ILOAD, 1));
                for (int a = 1; a < nargs; a++) il.add(new VarInsnNode(Opcodes.ALOAD, 1 + a));
            }
            il.add(new MethodInsnNode(Opcodes.INVOKESTATIC, G, pre + i, sub, false));
            il.add(new InsnNode(ret));
        }, () -> {});
        if (ret == Opcodes.RETURN) il.add(new InsnNode(Opcodes.RETURN));
        else { il.add(new InsnNode(Opcodes.ACONST_NULL)); il.add(new InsnNode(Opcodes.ARETURN)); }
        g.methods.add(m);
    }
}
