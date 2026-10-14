import java.io.*;
import java.lang.reflect.*;
import java.nio.file.*;
import java.util.*;
import java.util.zip.*;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

public class Defaults {
    static Map<String, ClassNode> tv = new HashMap<>();

    static void load(Map<String, ClassNode> m, File f) throws IOException {
        try (var z = new ZipFile(f)) {
            for (var e : Collections.list(z.entries())) {
                if (!e.getName().endsWith(".class")) continue;
                var cn = new ClassNode();
                new ClassReader(z.getInputStream(e)).accept(cn, ClassReader.SKIP_CODE);
                m.put(cn.name, cn);
            }
        }
    }

    static void loadDir(Map<String, ClassNode> m, File d) throws IOException {
        if (!d.exists()) return;
        try (var w = Files.walk(d.toPath())) {
            for (var p : (Iterable<Path>) w::iterator) {
                if (!p.toString().endsWith(".class")) continue;
                var cn = new ClassNode();
                new ClassReader(Files.readAllBytes(p)).accept(cn, ClassReader.SKIP_CODE);
                m.put(cn.name, cn);
            }
        }
    }

    static String tname(String j) {
        int i = j.lastIndexOf('/');
        return "org/teavm/classlib/" + j.substring(0, i + 1) + "T" + j.substring(i + 1);
    }

    static boolean has(String jIface, Method m, Set<String> seen) {
        var tn = tname(jIface);
        if (!seen.add(tn)) return false;
        var cn = tv.get(tn);
        if (cn == null) return false;
        for (var mn : cn.methods) {
            if (!mn.name.equals(m.getName())) continue;
            if (org.objectweb.asm.Type.getArgumentTypes(mn.desc).length != m.getParameterCount()) continue;
            if ((mn.access & Opcodes.ACC_ABSTRACT) == 0) return true;
        }
        if (cn.interfaces != null) for (var s : cn.interfaces) {
            var j = s.replace("org/teavm/classlib/", "");
            int i = j.lastIndexOf('/');
            j = j.substring(0, i + 1) + j.substring(i + 2);
            if (has(j, m, seen)) return true;
        }
        return false;
    }

    public static void main(String[] a) throws Exception {
        var app = new HashMap<String, ClassNode>();
        for (var p : a[0].split(":")) if (!p.isBlank()) load(app, new File(p));
        load(tv, new File(a[1]));
        loadDir(tv, new File(a[2]));
        var ifaces = new TreeSet<String>();
        for (var cn : app.values()) {
            if (cn.interfaces != null) for (var i : cn.interfaces) if (i.startsWith("java/")) ifaces.add(i);
        }
        var out = new TreeSet<String>();
        for (var i : ifaces) {
            Class<?> c;
            try { c = Class.forName(i.replace('/', '.'), false, null); } catch (Throwable t) { continue; }
            var todo = new ArrayDeque<Class<?>>(List.of(c));
            var vis = new HashSet<Class<?>>();
            while (!todo.isEmpty()) {
                var k = todo.poll();
                if (!vis.add(k)) continue;
                todo.addAll(List.of(k.getInterfaces()));
                for (var m : k.getDeclaredMethods()) {
                    if (!m.isDefault()) continue;
                    var jn = k.getName().replace('.', '/');
                    if (!tv.containsKey(tname(jn))) { out.add("NOCLASS " + jn); continue; }
                    if (!has(jn, m, new HashSet<>())) out.add(jn + "." + m.getName() + "/" + m.getParameterCount() + "  (via " + i + ")");
                }
            }
        }
        out.forEach(System.out::println);
    }
}
