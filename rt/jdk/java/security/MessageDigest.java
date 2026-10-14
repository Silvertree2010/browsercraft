package java.security;

import java.nio.ByteBuffer;

public class MessageDigest implements Cloneable {
    final String alg;
    final int bs, dl;
    int[] h;
    byte[] buf;
    int bl;
    long len;

    MessageDigest(String alg) {
        this.alg = alg;
        switch (alg) {
            case "MD5" -> { bs = 64; dl = 16; }
            case "SHA-1", "SHA1", "SHA" -> { bs = 64; dl = 20; }
            case "SHA-256", "SHA256" -> { bs = 64; dl = 32; }
            default -> throw new IllegalArgumentException();
        }
        buf = new byte[bs];
        reset();
    }

    public static MessageDigest getInstance(String a) throws NoSuchAlgorithmException {
        try { return new MessageDigest(a.toUpperCase()); } catch (IllegalArgumentException e) { throw new NoSuchAlgorithmException(a + " MessageDigest not available"); }
    }
    public static MessageDigest getInstance(String a, String p) throws NoSuchAlgorithmException { return getInstance(a); }

    public final String getAlgorithm() { return alg; }
    public final int getDigestLength() { return dl; }
    public final Provider getProvider() { return null; }

    public void reset() {
        bl = 0; len = 0;
        h = switch (dl) {
            case 16 -> new int[]{0x67452301, 0xefcdab89, 0x98badcfe, 0x10325476};
            case 20 -> new int[]{0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476, 0xC3D2E1F0};
            default -> new int[]{0x6a09e667, 0xbb67ae85, 0x3c6ef372, 0xa54ff53a, 0x510e527f, 0x9b05688c, 0x1f83d9ab, 0x5be0cd19};
        };
    }

    public void update(byte b) { buf[bl++] = b; len++; if (bl == bs) { blk(buf, 0); bl = 0; } }
    public void update(byte[] b) { update(b, 0, b.length); }
    public void update(byte[] b, int o, int n) {
        for (int i = 0; i < n; i++) {
            buf[bl++] = b[o + i];
            if (bl == bs) { blk(buf, 0); bl = 0; }
        }
        len += n;
    }
    public final void update(ByteBuffer b) { while (b.hasRemaining()) update(b.get()); }

    public byte[] digest() {
        long bits = len * 8;
        update((byte) 0x80);
        while (bl != bs - 8) update((byte) 0);
        for (int i = 0; i < 8; i++) {
            int sh = dl == 16 ? 8 * i : 8 * (7 - i);
            buf[bl++] = (byte) (bits >>> sh);
        }
        blk(buf, 0);
        byte[] r = new byte[dl];
        for (int i = 0; i < dl / 4; i++) for (int j = 0; j < 4; j++) {
            int sh = dl == 16 ? 8 * j : 8 * (3 - j);
            r[i * 4 + j] = (byte) (h[i] >>> sh);
        }
        reset();
        return r;
    }
    public byte[] digest(byte[] b) { update(b); return digest(); }
    public int digest(byte[] b, int o, int n) { var d = digest(); System.arraycopy(d, 0, b, o, d.length); return d.length; }

    public static boolean isEqual(byte[] a, byte[] b) { return java.util.Arrays.equals(a, b); }

    public Object clone() throws CloneNotSupportedException {
        var m = new MessageDigest(alg);
        m.h = h.clone(); m.buf = buf.clone(); m.bl = bl; m.len = len;
        return m;
    }
    public String toString() { return alg + " Message Digest"; }

    static final int[] S5 = {7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21};
    static final int[] K5 = new int[64];
    static final int[] K2 = {0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5, 0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174, 0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da, 0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967, 0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85, 0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070, 0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3, 0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2};
    static { for (int i = 0; i < 64; i++) K5[i] = (int) (long) (Math.abs(Math.sin(i + 1)) * 4294967296.0); }

    void blk(byte[] p, int o) {
        if (dl == 16) {
            int[] w = new int[16];
            for (int i = 0; i < 16; i++) w[i] = (p[o + i * 4] & 0xff) | (p[o + i * 4 + 1] & 0xff) << 8 | (p[o + i * 4 + 2] & 0xff) << 16 | (p[o + i * 4 + 3] & 0xff) << 24;
            int a = h[0], b = h[1], c = h[2], d = h[3];
            for (int i = 0; i < 64; i++) {
                int f, g;
                if (i < 16) { f = (b & c) | (~b & d); g = i; }
                else if (i < 32) { f = (d & b) | (~d & c); g = (5 * i + 1) % 16; }
                else if (i < 48) { f = b ^ c ^ d; g = (3 * i + 5) % 16; }
                else { f = c ^ (b | ~d); g = (7 * i) % 16; }
                int t = d; d = c; c = b;
                b = b + Integer.rotateLeft(a + f + K5[i] + w[g], S5[i]);
                a = t;
            }
            h[0] += a; h[1] += b; h[2] += c; h[3] += d;
            return;
        }
        int[] w = new int[dl == 20 ? 80 : 64];
        for (int i = 0; i < 16; i++) w[i] = (p[o + i * 4] & 0xff) << 24 | (p[o + i * 4 + 1] & 0xff) << 16 | (p[o + i * 4 + 2] & 0xff) << 8 | (p[o + i * 4 + 3] & 0xff);
        if (dl == 20) {
            for (int i = 16; i < 80; i++) w[i] = Integer.rotateLeft(w[i - 3] ^ w[i - 8] ^ w[i - 14] ^ w[i - 16], 1);
            int a = h[0], b = h[1], c = h[2], d = h[3], e = h[4];
            for (int i = 0; i < 80; i++) {
                int f, k;
                if (i < 20) { f = (b & c) | (~b & d); k = 0x5A827999; }
                else if (i < 40) { f = b ^ c ^ d; k = 0x6ED9EBA1; }
                else if (i < 60) { f = (b & c) | (b & d) | (c & d); k = 0x8F1BBCDC; }
                else { f = b ^ c ^ d; k = 0xCA62C1D6; }
                int t = Integer.rotateLeft(a, 5) + f + e + k + w[i];
                e = d; d = c; c = Integer.rotateLeft(b, 30); b = a; a = t;
            }
            h[0] += a; h[1] += b; h[2] += c; h[3] += d; h[4] += e;
            return;
        }
        for (int i = 16; i < 64; i++) {
            int s0 = Integer.rotateRight(w[i - 15], 7) ^ Integer.rotateRight(w[i - 15], 18) ^ (w[i - 15] >>> 3);
            int s1 = Integer.rotateRight(w[i - 2], 17) ^ Integer.rotateRight(w[i - 2], 19) ^ (w[i - 2] >>> 10);
            w[i] = w[i - 16] + s0 + w[i - 7] + s1;
        }
        int a = h[0], b = h[1], c = h[2], d = h[3], e = h[4], f = h[5], g = h[6], hh = h[7];
        for (int i = 0; i < 64; i++) {
            int S1 = Integer.rotateRight(e, 6) ^ Integer.rotateRight(e, 11) ^ Integer.rotateRight(e, 25);
            int ch = (e & f) ^ (~e & g);
            int t1 = hh + S1 + ch + K2[i] + w[i];
            int S0 = Integer.rotateRight(a, 2) ^ Integer.rotateRight(a, 13) ^ Integer.rotateRight(a, 22);
            int mj = (a & b) ^ (a & c) ^ (b & c);
            int t2 = S0 + mj;
            hh = g; g = f; f = e; e = d + t1; d = c; c = b; b = a; a = t1 + t2;
        }
        h[0] += a; h[1] += b; h[2] += c; h[3] += d; h[4] += e; h[5] += f; h[6] += g; h[7] += hh;
    }
}
