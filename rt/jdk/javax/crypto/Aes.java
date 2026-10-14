package javax.crypto;

final class Aes {
    static final int[] S = new int[256];
    static final int[] T0 = new int[256], T1 = new int[256], T2 = new int[256], T3 = new int[256];
    static {
        int p = 1, q = 1;
        S[0] = 0x63;
        do {
            p = p ^ ((p << 1) & 0xff) ^ (((p & 0x80) != 0) ? 0x1b : 0);
            q ^= q << 1; q ^= q << 2; q ^= q << 4; q &= 0xff;
            if ((q & 0x80) != 0) q ^= 0x09;
            int x = q ^ rl(q, 1) ^ rl(q, 2) ^ rl(q, 3) ^ rl(q, 4);
            S[p] = (x ^ 0x63) & 0xff;
        } while (p != 1);
        for (int i = 0; i < 256; i++) {
            int s = S[i];
            int s2 = xt(s), s3 = s2 ^ s;
            int t = (s2 << 24) | (s << 16) | (s << 8) | s3;
            T0[i] = t;
            T1[i] = Integer.rotateRight(t, 8);
            T2[i] = Integer.rotateRight(t, 16);
            T3[i] = Integer.rotateRight(t, 24);
        }
    }
    static int rl(int x, int n) { return ((x << n) | (x >> (8 - n))) & 0xff; }
    static int xt(int x) { x <<= 1; if ((x & 0x100) != 0) x ^= 0x11b; return x & 0xff; }

    final int[] rk;
    final int nr;

    Aes(byte[] key) {
        int nk = key.length / 4;
        nr = nk + 6;
        rk = new int[4 * (nr + 1)];
        for (int i = 0; i < nk; i++) rk[i] = (key[4 * i] & 255) << 24 | (key[4 * i + 1] & 255) << 16 | (key[4 * i + 2] & 255) << 8 | (key[4 * i + 3] & 255);
        int rc = 1;
        for (int i = nk; i < rk.length; i++) {
            int t = rk[i - 1];
            if (i % nk == 0) {
                t = Integer.rotateLeft(t, 8);
                t = sub(t) ^ (rc << 24);
                rc = xt(rc);
            } else if (nk > 6 && i % nk == 4) t = sub(t);
            rk[i] = rk[i - nk] ^ t;
        }
    }

    static int sub(int t) { return S[t >>> 24] << 24 | S[(t >>> 16) & 255] << 16 | S[(t >>> 8) & 255] << 8 | S[t & 255]; }

    void enc(byte[] in, byte[] out) {
        int s0 = (in[0] & 255) << 24 | (in[1] & 255) << 16 | (in[2] & 255) << 8 | (in[3] & 255);
        int s1 = (in[4] & 255) << 24 | (in[5] & 255) << 16 | (in[6] & 255) << 8 | (in[7] & 255);
        int s2 = (in[8] & 255) << 24 | (in[9] & 255) << 16 | (in[10] & 255) << 8 | (in[11] & 255);
        int s3 = (in[12] & 255) << 24 | (in[13] & 255) << 16 | (in[14] & 255) << 8 | (in[15] & 255);
        s0 ^= rk[0]; s1 ^= rk[1]; s2 ^= rk[2]; s3 ^= rk[3];
        int k = 4;
        for (int r = 1; r < nr; r++) {
            int t0 = T0[s0 >>> 24] ^ T1[(s1 >>> 16) & 255] ^ T2[(s2 >>> 8) & 255] ^ T3[s3 & 255] ^ rk[k++];
            int t1 = T0[s1 >>> 24] ^ T1[(s2 >>> 16) & 255] ^ T2[(s3 >>> 8) & 255] ^ T3[s0 & 255] ^ rk[k++];
            int t2 = T0[s2 >>> 24] ^ T1[(s3 >>> 16) & 255] ^ T2[(s0 >>> 8) & 255] ^ T3[s1 & 255] ^ rk[k++];
            int t3 = T0[s3 >>> 24] ^ T1[(s0 >>> 16) & 255] ^ T2[(s1 >>> 8) & 255] ^ T3[s2 & 255] ^ rk[k++];
            s0 = t0; s1 = t1; s2 = t2; s3 = t3;
        }
        int o0 = (S[s0 >>> 24] << 24 | S[(s1 >>> 16) & 255] << 16 | S[(s2 >>> 8) & 255] << 8 | S[s3 & 255]) ^ rk[k++];
        int o1 = (S[s1 >>> 24] << 24 | S[(s2 >>> 16) & 255] << 16 | S[(s3 >>> 8) & 255] << 8 | S[s0 & 255]) ^ rk[k++];
        int o2 = (S[s2 >>> 24] << 24 | S[(s3 >>> 16) & 255] << 16 | S[(s0 >>> 8) & 255] << 8 | S[s1 & 255]) ^ rk[k++];
        int o3 = (S[s3 >>> 24] << 24 | S[(s0 >>> 16) & 255] << 16 | S[(s1 >>> 8) & 255] << 8 | S[s2 & 255]) ^ rk[k];
        put(out, 0, o0); put(out, 4, o1); put(out, 8, o2); put(out, 12, o3);
    }
    static void put(byte[] b, int o, int v) { b[o] = (byte) (v >>> 24); b[o + 1] = (byte) (v >>> 16); b[o + 2] = (byte) (v >>> 8); b[o + 3] = (byte) v; }
}
