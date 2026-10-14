package com.ibm.icu.util;

public class Calendar {
    final TimeZone z;
    Calendar(TimeZone z) { this.z = z; }
    public static Calendar getInstance(TimeZone z, ULocale l) { return new Calendar(z); }
    public static Calendar getInstance(ULocale l) { return new Calendar(null); }
    public int off() { return z == null ? bc.rt.R.tzOff() : z.off; }
}
