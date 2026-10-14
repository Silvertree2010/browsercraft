package java.util.concurrent.atomic;

import java.util.function.LongBinaryOperator;

public class LongAccumulator extends Number implements java.io.Serializable {
    final LongBinaryOperator f;
    final long id;
    long v;
    public LongAccumulator(LongBinaryOperator f, long id) { this.f = f; this.id = id; v = id; }
    public void accumulate(long x) { v = f.applyAsLong(v, x); }
    public long get() { return v; }
    public void reset() { v = id; }
    public long getThenReset() { long r = v; v = id; return r; }
    public String toString() { return Long.toString(v); }
    public long longValue() { return v; }
    public int intValue() { return (int) v; }
    public float floatValue() { return v; }
    public double doubleValue() { return v; }
}
