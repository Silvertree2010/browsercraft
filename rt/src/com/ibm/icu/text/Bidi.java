package com.ibm.icu.text;

public class Bidi {
    public static final int DO_MIRRORING = 2;
    public static final int REORDER_DEFAULT = 0;
    final String s;
    public Bidi(String s, int f) { this.s = s; }
    public void setReorderingMode(int m) {}
    public int countRuns() { return 1; }
    public BidiRun getVisualRun(int i) { return new BidiRun(0, s.length(), false); }
    public String writeReordered(int o) { return s; }
}
