package java.util.concurrent.locks;

import java.util.HashMap;

public final class LockSupport {
    static final class P { boolean permit; Object blocker; }
    static final HashMap<Thread, P> M = new HashMap<>();

    private LockSupport() {}

    static P p(Thread t) { return M.computeIfAbsent(t, k -> new P()); }

    public static void unpark(Thread t) {
        if (t == null) return;
        p(t).permit = true;
    }

    public static void park() { park0(0); }
    public static void park(Object b) { var p = p(Thread.currentThread()); p.blocker = b; try { park0(0); } finally { p.blocker = null; } }
    public static void parkNanos(long n) { if (n > 0) park0(Math.max(1, n / 1000000)); }
    public static void parkNanos(Object b, long n) { var p = p(Thread.currentThread()); p.blocker = b; try { parkNanos(n); } finally { p.blocker = null; } }
    public static void parkUntil(long d) { long w = d - System.currentTimeMillis(); if (w > 0) park0(w); }
    public static void parkUntil(Object b, long d) { parkUntil(d); }
    public static Object getBlocker(Thread t) { var p = M.get(t); return p == null ? null : p.blocker; }
    static long getThreadId(Thread t) { return t.getId(); }
    public static void setCurrentBlocker(Object b) { p(Thread.currentThread()).blocker = b; }

    static void park0(long ms) {
        var t = Thread.currentThread();
        var p = p(t);
        long end = ms > 0 ? System.currentTimeMillis() + ms : Long.MAX_VALUE;
        while (!p.permit && !t.isInterrupted() && System.currentTimeMillis() < end) {
            try { Thread.sleep(1); } catch (InterruptedException e) { t.interrupt(); break; }
        }
        p.permit = false;
    }
}
