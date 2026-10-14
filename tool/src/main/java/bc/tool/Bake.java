package bc.tool;

import java.io.*;
import java.net.*;
import java.util.*;
import org.teavm.tooling.ConsoleTeaVMToolLog;
import org.teavm.tooling.TeaVMTargetType;
import org.teavm.tooling.TeaVMTool;
import org.teavm.vm.TeaVMOptimizationLevel;

public class Bake {
    static java.util.Set<String> ALLOW = new java.util.HashSet<>();

    static class Cf extends URLClassLoader {
        Cf(URL[] u, ClassLoader p) { super(u, p); }
        @Override public URL getResource(String n) {
            var r = findResource(n);
            if (r != null) return r;
            if (blocked(n)) return null;
            return super.getResource(n);
        }
        static boolean blocked(String n) {
            return n.endsWith(".class") && (n.startsWith("java/") || n.startsWith("javax/") || n.startsWith("jdk/") || n.startsWith("sun/") || n.startsWith("com/sun/")) && !ALLOW.contains(n.substring(0, n.length() - 6));
        }
        @Override public InputStream getResourceAsStream(String n) {
            var r = getResource(n);
            try { return r == null ? null : r.openStream(); } catch (IOException e) { return null; }
        }
        @Override public Enumeration<URL> getResources(String n) throws IOException {
            var l = Collections.list(findResources(n));
            if (!blocked(n)) l.addAll(Collections.list(getParent().getResources(n)));
            return Collections.enumeration(l);
        }
    }

    public static void main(String[] a) throws Exception {
        var al = new File("rules/jdkallow.txt");
        if (al.exists()) for (var l : java.nio.file.Files.readAllLines(al.toPath())) if (!l.isBlank() && !l.startsWith("#")) ALLOW.add(l.strip());
        var u = new ArrayList<URL>();
        for (var p : a[0].split(":")) if (!p.isBlank()) u.add(new File(p).toURI().toURL());
        var cl = new Cf(u.toArray(new URL[0]), Bake.class.getClassLoader());
        var t = new TeaVMTool();
        t.setLog(new ConsoleTeaVMToolLog(false));
        t.setClassLoader(cl);
        t.setTargetType(TeaVMTargetType.WEBASSEMBLY_GC);
        t.setMainClass(a[2]);
        t.setTargetDirectory(new File(a[1]));
        t.setTargetFileName("mc.wasm");
        t.setObfuscated(false);
        t.setOptimizationLevel(TeaVMOptimizationLevel.valueOf(a.length > 3 ? a[3] : "SIMPLE"));
        t.setStrict(false);
        if (System.getenv("BC_INC") != null) { t.setIncremental(true); t.setCacheDirectory(new File(a[1], "../cache")); }
        long s = System.currentTimeMillis();
        t.setProgressListener(new org.teavm.vm.TeaVMProgressListener() {
            int n, last = -1;
            public org.teavm.vm.TeaVMProgressFeedback phaseStarted(org.teavm.vm.TeaVMPhase ph, int c) {
                n = c;
                last = -1;
                System.out.println("phase " + ph + " " + c + " @" + (System.currentTimeMillis() - s) / 1000 + "s");
                return org.teavm.vm.TeaVMProgressFeedback.CONTINUE;
            }
            public org.teavm.vm.TeaVMProgressFeedback progressReached(int p) {
                int q = n > 0 ? p * 10 / n : 0;
                if (q != last) { last = q; System.out.println("  " + q * 10 + "% @" + (System.currentTimeMillis() - s) / 1000 + "s"); }
                return org.teavm.vm.TeaVMProgressFeedback.CONTINUE;
            }
        });
        t.generate();
        System.out.println("ms " + (System.currentTimeMillis() - s));
        var p = t.getProblemProvider();
        System.out.println("errors " + p.getSevereProblems().size() + " problems " + p.getProblems().size());
        try (var w = new PrintWriter(new File(a[1], "problems.txt"))) {
            for (var q : p.getProblems()) {
                var loc = q.getLocation();
                var m = loc == null ? null : loc.getMethod();
                w.println(q.getSeverity() + " " + q.getText() + " " + Arrays.toString(q.getParams()) + " @ " + (m == null ? "?" : m.getClassName() + "." + m.getName()));
            }
        }
    }
}
