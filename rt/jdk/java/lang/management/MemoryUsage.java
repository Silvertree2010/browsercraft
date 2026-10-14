package java.lang.management;

public class MemoryUsage {
    final long i, u, c, m;
    public MemoryUsage(long i, long u, long c, long m) { this.i = i; this.u = u; this.c = c; this.m = m; }
    public long getInit() { return i; }
    public long getUsed() { return u; }
    public long getCommitted() { return c; }
    public long getMax() { return m; }
}
