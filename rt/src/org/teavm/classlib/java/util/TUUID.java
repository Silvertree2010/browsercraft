package org.teavm.classlib.java.util;

import java.io.Serializable;
import bc.rt.Md5;

public final class TUUID implements Serializable, Comparable<TUUID> {
    private final long m;
    private final long l;
    private static java.util.Random rnd;

    public TUUID(long m, long l) { this.m = m; this.l = l; }

    public static TUUID randomUUID() {
        if (rnd == null) rnd = new java.util.Random();
        long a = rnd.nextLong(), b = rnd.nextLong();
        a = (a & ~0xf000L) | 0x4000L;
        b = (b & 0x3fffffffffffffffL) | 0x8000000000000000L;
        return new TUUID(a, b);
    }

    public static TUUID nameUUIDFromBytes(byte[] n) {
        byte[] d = Md5.hash(n);
        d[6] &= 0x0f; d[6] |= 0x30;
        d[8] &= 0x3f; d[8] |= (byte) 0x80;
        long a = 0, b = 0;
        for (int i = 0; i < 8; i++) a = (a << 8) | (d[i] & 0xff);
        for (int i = 8; i < 16; i++) b = (b << 8) | (d[i] & 0xff);
        return new TUUID(a, b);
    }

    public static TUUID fromString(String s) {
        int d1 = s.indexOf('-'), d2 = s.indexOf('-', d1 + 1), d3 = s.indexOf('-', d2 + 1), d4 = s.indexOf('-', d3 + 1);
        if (d1 < 0 || d2 < 0 || d3 < 0 || d4 < 0) throw new IllegalArgumentException("Invalid UUID string: " + s);
        long a = Long.parseLong(s.substring(0, d1), 16) & 0xffffffffL;
        long b = Long.parseLong(s.substring(d1 + 1, d2), 16) & 0xffffL;
        long c = Long.parseLong(s.substring(d2 + 1, d3), 16) & 0xffffL;
        long e = Long.parseLong(s.substring(d3 + 1, d4), 16) & 0xffffL;
        long f = bc.rt.R.pul(s.substring(d4 + 1), 16) & 0xffffffffffffL;
        return new TUUID((a << 32) | (b << 16) | c, (e << 48) | f);
    }

    public long getLeastSignificantBits() { return l; }
    public long getMostSignificantBits() { return m; }
    public int version() { return (int) ((m >> 12) & 0x0f); }
    public int variant() { return (int) ((l >>> (64 - (l >>> 62))) & (l >> 63)); }
    public long timestamp() { return (m & 0x0FFFL) << 48 | ((m >> 16) & 0x0FFFFL) << 32 | m >>> 32; }
    public int clockSequence() { return (int) ((l & 0x3FFF000000000000L) >>> 48); }
    public long node() { return l & 0x0000FFFFFFFFFFFFL; }

    static String hx(long v, int n) {
        var s = Long.toHexString(v);
        var sb = new StringBuilder();
        for (int i = s.length(); i < n; i++) sb.append('0');
        return sb.append(s).toString();
    }

    public String toString() {
        return hx(m >>> 32, 8) + "-" + hx((m >>> 16) & 0xffff, 4) + "-" + hx(m & 0xffff, 4) + "-" + hx(l >>> 48, 4) + "-" + hx(l & 0xffffffffffffL, 12);
    }

    public int hashCode() { long h = m ^ l; return ((int) (h >> 32)) ^ (int) h; }
    public boolean equals(Object o) { return o instanceof TUUID u && u.m == m && u.l == l; }
    public int compareTo(TUUID v) {
        int c = Long.compare(m, v.m);
        return c != 0 ? c : Long.compare(l, v.l);
    }
}
