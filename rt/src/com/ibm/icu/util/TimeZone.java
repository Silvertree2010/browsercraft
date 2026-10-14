package com.ibm.icu.util;

public class TimeZone {
    final String id;
    final int off;
    TimeZone(String id, int off) { this.id = id; this.off = off; }
    public static TimeZone getTimeZone(String id) {
        try { return new TimeZone(id, java.time.ZoneId.of(id).getRules().getOffset(java.time.Instant.now()).getTotalSeconds()); }
        catch (RuntimeException e) { return new TimeZone(id, 0); }
    }
    public static final TimeZone UNKNOWN_ZONE = new TimeZone("Etc/Unknown", 0);
    public String getID() { return id; }
    public static TimeZone getDefault() { return new TimeZone("UTC", bc.rt.R.tzOff()); }
    public boolean equals(Object o) { return o instanceof TimeZone t && t.id.equals(id); }
    public int hashCode() { return id.hashCode(); }
}
