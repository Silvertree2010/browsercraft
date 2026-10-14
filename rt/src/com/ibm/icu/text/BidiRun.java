package com.ibm.icu.text;

public class BidiRun {
    final int st, ln;
    final boolean odd;
    BidiRun(int st, int ln, boolean odd) { this.st = st; this.ln = ln; this.odd = odd; }
    public int getStart() { return st; }
    public int getLength() { return ln; }
    public boolean isOddRun() { return odd; }
}
