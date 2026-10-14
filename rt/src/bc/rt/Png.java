package bc.rt;

import java.io.ByteArrayOutputStream;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public final class Png {
    public int w, h, ch;
    public byte[] px;

    static int i32(byte[] b, int o) { return (b[o] & 255) << 24 | (b[o + 1] & 255) << 16 | (b[o + 2] & 255) << 8 | (b[o + 3] & 255); }

    public static Png decode(byte[] d) {
        if (d.length < 8 || (d[0] & 255) != 0x89 || d[1] != 'P' || d[2] != 'N' || d[3] != 'G') throw new IllegalArgumentException("not a PNG");
        int p = 8, w = 0, h = 0, depth = 0, ct = 0, il = 0;
        byte[] pal = null, trns = null;
        var idat = new ByteArrayOutputStream();
        while (p + 8 <= d.length) {
            int len = i32(d, p);
            int t = i32(d, p + 4);
            int o = p + 8;
            switch (t) {
                case 0x49484452 -> { w = i32(d, o); h = i32(d, o + 4); depth = d[o + 8] & 255; ct = d[o + 9] & 255; il = d[o + 12] & 255; }
                case 0x504c5445 -> { pal = new byte[len]; System.arraycopy(d, o, pal, 0, len); }
                case 0x74524e53 -> { trns = new byte[len]; System.arraycopy(d, o, trns, 0, len); }
                case 0x49444154 -> idat.write(d, o, len);
                default -> {}
            }
            if (t == 0x49454e44) break;
            p = o + len + 4;
        }
        int sc = switch (ct) { case 0 -> 1; case 2 -> 3; case 3 -> 1; case 4 -> 2; case 6 -> 4; default -> throw new IllegalArgumentException("bad color type " + ct); };
        var z = new Inflater();
        z.setInput(idat.toByteArray());
        var raw = new ByteArrayOutputStream(w * h * 4 + h);
        byte[] buf = new byte[65536];
        try {
            while (!z.finished()) {
                int n = z.inflate(buf);
                if (n == 0 && (z.needsInput() || z.needsDictionary())) break;
                raw.write(buf, 0, n);
            }
        } catch (java.util.zip.DataFormatException e) {
            throw new IllegalArgumentException("corrupt PNG", e);
        }
        z.end();
        byte[] r = raw.toByteArray();
        var png = new Png();
        png.w = w;
        png.h = h;
        boolean alpha = ct == 4 || ct == 6 || trns != null;
        png.ch = ct == 3 ? (alpha ? 4 : 3) : ct == 0 ? (alpha ? 2 : 1) : ct == 2 ? (alpha ? 4 : 3) : sc;
        png.px = new byte[w * h * 4];
        int bpp = Math.max(1, sc * depth / 8);
        if (il == 0) unfilter(r, 0, w, h, sc, depth, bpp, png, 0, 0, 1, 1, pal, trns, ct);
        else {
            int[] xs = {0, 4, 0, 2, 0, 1, 0}, ys = {0, 0, 4, 0, 2, 0, 1}, dx = {8, 8, 4, 4, 2, 2, 1}, dy = {8, 8, 8, 4, 4, 2, 2};
            int off = 0;
            for (int pass = 0; pass < 7; pass++) {
                int pw = (w - xs[pass] + dx[pass] - 1) / dx[pass];
                int ph = (h - ys[pass] + dy[pass] - 1) / dy[pass];
                if (pw <= 0 || ph <= 0) continue;
                off = unfilter(r, off, pw, ph, sc, depth, bpp, png, xs[pass], ys[pass], dx[pass], dy[pass], pal, trns, ct);
            }
        }
        return png;
    }

    static int unfilter(byte[] r, int off, int w, int h, int sc, int depth, int bpp, Png png, int x0, int y0, int dx, int dy, byte[] pal, byte[] trns, int ct) {
        int stride = (w * sc * depth + 7) / 8;
        byte[] prev = new byte[stride], cur = new byte[stride];
        for (int y = 0; y < h; y++) {
            int f = r[off++] & 255;
            System.arraycopy(r, off, cur, 0, stride);
            off += stride;
            for (int i = 0; i < stride; i++) {
                int a = i >= bpp ? cur[i - bpp] & 255 : 0;
                int b = prev[i] & 255;
                int c = i >= bpp ? prev[i - bpp] & 255 : 0;
                int v = cur[i] & 255;
                switch (f) {
                    case 1 -> v += a;
                    case 2 -> v += b;
                    case 3 -> v += (a + b) >> 1;
                    case 4 -> {
                        int pp = a + b - c, pa = Math.abs(pp - a), pb = Math.abs(pp - b), pc = Math.abs(pp - c);
                        v += pa <= pb && pa <= pc ? a : pb <= pc ? b : c;
                    }
                    default -> {}
                }
                cur[i] = (byte) v;
            }
            for (int x = 0; x < w; x++) {
                int o = ((y0 + y * dy) * png.w + x0 + x * dx) * 4;
                int R, G, B, A = 255;
                if (depth == 8) {
                    int i = x * sc;
                    switch (ct) {
                        case 0 -> { R = G = B = cur[i] & 255; if (trns != null && trns.length >= 2 && R == (trns[1] & 255)) A = 0; }
                        case 2 -> { R = cur[i] & 255; G = cur[i + 1] & 255; B = cur[i + 2] & 255; if (trns != null && trns.length >= 6 && R == (trns[1] & 255) && G == (trns[3] & 255) && B == (trns[5] & 255)) A = 0; }
                        case 3 -> { int k = cur[i] & 255; R = pal[k * 3] & 255; G = pal[k * 3 + 1] & 255; B = pal[k * 3 + 2] & 255; if (trns != null && k < trns.length) A = trns[k] & 255; }
                        case 4 -> { R = G = B = cur[i] & 255; A = cur[i + 1] & 255; }
                        default -> { R = cur[i] & 255; G = cur[i + 1] & 255; B = cur[i + 2] & 255; A = cur[i + 3] & 255; }
                    }
                } else if (depth == 16) {
                    int i = x * sc * 2;
                    switch (ct) {
                        case 0 -> { R = G = B = cur[i] & 255; }
                        case 2 -> { R = cur[i] & 255; G = cur[i + 2] & 255; B = cur[i + 4] & 255; }
                        case 4 -> { R = G = B = cur[i] & 255; A = cur[i + 2] & 255; }
                        default -> { R = cur[i] & 255; G = cur[i + 2] & 255; B = cur[i + 4] & 255; A = cur[i + 6] & 255; }
                    }
                } else {
                    int bit = x * depth;
                    int v = ((cur[bit >> 3] & 255) >> (8 - depth - (bit & 7))) & ((1 << depth) - 1);
                    if (ct == 3) { R = pal[v * 3] & 255; G = pal[v * 3 + 1] & 255; B = pal[v * 3 + 2] & 255; if (trns != null && v < trns.length) A = trns[v] & 255; }
                    else { R = G = B = v * 255 / ((1 << depth) - 1); if (trns != null && trns.length >= 2 && v == (trns[1] & 255)) A = 0; }
                }
                png.px[o] = (byte) R;
                png.px[o + 1] = (byte) G;
                png.px[o + 2] = (byte) B;
                png.px[o + 3] = (byte) A;
            }
            var t = prev;
            prev = cur;
            cur = t;
        }
        return off;
    }

    public static byte[] encode(byte[] px, int w, int h, int comp, int stride) {
        int ct = switch (comp) { case 1 -> 0; case 2 -> 4; case 3 -> 2; default -> 6; };
        var raw = new ByteArrayOutputStream(h * (w * comp + 1));
        for (int y = 0; y < h; y++) {
            raw.write(0);
            raw.write(px, y * stride, w * comp);
        }
        var df = new Deflater(6);
        df.setInput(raw.toByteArray());
        df.finish();
        var z = new ByteArrayOutputStream();
        byte[] buf = new byte[65536];
        while (!df.finished()) { int n = df.deflate(buf); z.write(buf, 0, n); }
        df.end();
        var o = new ByteArrayOutputStream();
        o.write(new byte[]{(byte) 0x89, 'P', 'N', 'G', 13, 10, 26, 10}, 0, 8);
        byte[] ihdr = {(byte) (w >>> 24), (byte) (w >>> 16), (byte) (w >>> 8), (byte) w, (byte) (h >>> 24), (byte) (h >>> 16), (byte) (h >>> 8), (byte) h, 8, (byte) ct, 0, 0, 0};
        chunk(o, "IHDR", ihdr);
        chunk(o, "IDAT", z.toByteArray());
        chunk(o, "IEND", new byte[0]);
        return o.toByteArray();
    }

    static void chunk(ByteArrayOutputStream o, String t, byte[] d) {
        int n = d.length;
        o.write(n >>> 24); o.write(n >>> 16); o.write(n >>> 8); o.write(n);
        byte[] tb = t.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
        o.write(tb, 0, 4);
        o.write(d, 0, n);
        var c = new CRC32();
        c.update(tb);
        c.update(d);
        long v = c.getValue();
        o.write((int) (v >>> 24)); o.write((int) (v >>> 16)); o.write((int) (v >>> 8)); o.write((int) v);
    }
}
