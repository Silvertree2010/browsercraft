package org.lwjgl.stb;

import static org.lwjgl.system.MemoryUtil.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import bc.rt.Png;

public class STBImage {
    static String err;

    public static ByteBuffer stbi_load_from_memory(ByteBuffer buf, IntBuffer x, IntBuffer y, IntBuffer comp, int req) {
        byte[] d = new byte[buf.remaining()];
        buf.duplicate().get(d);
        Png p;
        try { p = Png.decode(d); } catch (RuntimeException e) { err = e.getMessage(); return null; }
        int ch = req == 0 ? p.ch : req;
        long out = nmemAlloc((long) p.w * p.h * ch);
        int n = p.w * p.h;
        for (int i = 0; i < n; i++) {
            int s = i * 4;
            long o = out + (long) i * ch;
            switch (ch) {
                case 1 -> memPutByte(o, p.px[s]);
                case 2 -> { memPutByte(o, p.px[s]); memPutByte(o + 1, p.px[s + 3]); }
                case 3 -> { memPutByte(o, p.px[s]); memPutByte(o + 1, p.px[s + 1]); memPutByte(o + 2, p.px[s + 2]); }
                default -> memPutInt(o, (p.px[s] & 255) | (p.px[s + 1] & 255) << 8 | (p.px[s + 2] & 255) << 16 | (p.px[s + 3] & 255) << 24);
            }
        }
        x.put(x.position(), p.w);
        y.put(y.position(), p.h);
        comp.put(comp.position(), p.ch);
        return memByteBuffer(out, p.w * p.h * ch);
    }

    public static boolean stbi_info_from_memory(ByteBuffer buf, IntBuffer x, IntBuffer y, IntBuffer comp) {
        byte[] d = new byte[Math.min(buf.remaining(), 64)];
        buf.duplicate().get(d);
        if (d.length < 26) return false;
        x.put(x.position(), (d[16] & 255) << 24 | (d[17] & 255) << 16 | (d[18] & 255) << 8 | (d[19] & 255));
        y.put(y.position(), (d[20] & 255) << 24 | (d[21] & 255) << 16 | (d[22] & 255) << 8 | (d[23] & 255));
        int ct = d[25];
        comp.put(comp.position(), ct == 0 ? 1 : ct == 4 ? 2 : ct == 2 ? 3 : 4);
        return true;
    }

    public static void nstbi_image_free(long p) { if (p != 0) nmemFree(p); }
    public static void stbi_image_free(ByteBuffer b) { nmemFree(memAddress(b)); }
    public static String stbi_failure_reason() { return err; }
}
