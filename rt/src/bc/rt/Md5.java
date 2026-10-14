package bc.rt;

public final class Md5 {
    static final int[] S = {7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21};
    static final int[] K = new int[64];
    static { for (int i = 0; i < 64; i++) K[i] = (int) (long) (Math.abs(Math.sin(i + 1)) * 4294967296.0); }

    public static byte[] hash(byte[] in) {
        int n = in.length;
        int tl = ((n + 8) / 64 + 1) * 64;
        byte[] p = new byte[tl];
        System.arraycopy(in, 0, p, 0, n);
        p[n] = (byte) 0x80;
        long bits = (long) n * 8;
        for (int i = 0; i < 8; i++) p[tl - 8 + i] = (byte) (bits >>> (8 * i));
        int a0 = 0x67452301, b0 = 0xefcdab89, c0 = 0x98badcfe, d0 = 0x10325476;
        int[] w = new int[16];
        for (int o = 0; o < tl; o += 64) {
            for (int i = 0; i < 16; i++) w[i] = (p[o + i * 4] & 0xff) | (p[o + i * 4 + 1] & 0xff) << 8 | (p[o + i * 4 + 2] & 0xff) << 16 | (p[o + i * 4 + 3] & 0xff) << 24;
            int a = a0, b = b0, c = c0, d = d0;
            for (int i = 0; i < 64; i++) {
                int f, g;
                if (i < 16) { f = (b & c) | (~b & d); g = i; }
                else if (i < 32) { f = (d & b) | (~d & c); g = (5 * i + 1) % 16; }
                else if (i < 48) { f = b ^ c ^ d; g = (3 * i + 5) % 16; }
                else { f = c ^ (b | ~d); g = (7 * i) % 16; }
                int t = d;
                d = c;
                c = b;
                b = b + Integer.rotateLeft(a + f + K[i] + w[g], S[i]);
                a = t;
            }
            a0 += a; b0 += b; c0 += c; d0 += d;
        }
        byte[] r = new byte[16];
        int[] v = {a0, b0, c0, d0};
        for (int i = 0; i < 4; i++) for (int j = 0; j < 4; j++) r[i * 4 + j] = (byte) (v[i] >>> (8 * j));
        return r;
    }
}
