package bc.rt;

import java.util.ArrayList;
import java.util.HashMap;

public final class Nat {
    public interface F { long c(long[] a); }

    static final HashMap<String, Integer> ids = new HashMap<>();
    static final ArrayList<String> names = new ArrayList<>();
    static final ArrayList<F> fns = new ArrayList<>();
    static final HashMap<String, F> reg = new HashMap<>();
    static final java.util.HashSet<String> warned = new java.util.HashSet<>();

    private Nat() {}

    public static void reg(String n, F f) {
        reg.put(n, f);
        var i = ids.get(n);
        if (i != null) fns.set(i, f);
    }

    public static long id(String n) {
        int z = n.indexOf('\0');
        if (z >= 0) n = n.substring(0, z);
        var i = ids.get(n);
        if (i == null) {
            i = names.size();
            names.add(n);
            fns.add(reg.get(n));
            ids.put(n, i);
        }
        return 0x10000L + i;
    }

    public static long c(long fn, long[] a) {
        int i = (int) (fn - 0x10000L);
        if (i < 0 || i >= fns.size()) throw new IllegalStateException("bad fn " + fn);
        var f = fns.get(i);
        if (f == null) {
            var n = names.get(i);
            if (warned.add(n)) System.err.println("[nat] missing " + n);
            return 0;
        }
        return f.c(a);
    }
}
