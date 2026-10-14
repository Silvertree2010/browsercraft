package java.util.concurrent.atomic;

public class DoubleAdder extends Number implements java.io.Serializable {
    double v;
    public DoubleAdder() {}
    public void add(double x) { v += x; }
    public double sum() { return v; }
    public void reset() { v = 0; }
    public double sumThenReset() { double r = v; v = 0; return r; }
    public String toString() { return Double.toString(v); }
    public long longValue() { return (long) v; }
    public int intValue() { return (int) v; }
    public float floatValue() { return (float) v; }
    public double doubleValue() { return v; }
}
