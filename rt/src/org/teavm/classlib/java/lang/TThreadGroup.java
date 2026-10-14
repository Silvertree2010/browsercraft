package org.teavm.classlib.java.lang;

public class TThreadGroup implements TThread.UncaughtExceptionHandler {
    static final TThreadGroup MAIN = new TThreadGroup(null, "main");
    final TThreadGroup parent;
    final String name;
    int maxPri = 10;
    boolean daemon;

    TThreadGroup(TThreadGroup p, String n) { parent = p; name = n; }
    public TThreadGroup(String n) { this(MAIN, n); }
    public TThreadGroup(TThreadGroup p, String n, boolean x) { this(p, n); }

    public final String getName() { return name; }
    public final TThreadGroup getParent() { return parent; }
    public final int getMaxPriority() { return maxPri; }
    public final void setMaxPriority(int p) { maxPri = p; }
    public final boolean isDaemon() { return daemon; }
    public final void setDaemon(boolean d) { daemon = d; }
    public int activeCount() { return TThread.activeCount(); }
    public void uncaughtException(TThread t, Throwable e) {
        if (parent != null) parent.uncaughtException(t, e);
        else { System.err.print("Exception in thread \"" + t.getName() + "\" "); e.printStackTrace(); }
    }
}
