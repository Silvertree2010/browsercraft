package org.lwjgl.stb;

import static org.lwjgl.system.MemoryUtil.*;

public class STBImageResize {
    public static long nstbir_resize_uint8_linear(long in, int iw, int ih, int is, long out, int ow, int oh, int os, int layout) {
        int c = layout >= 1 && layout <= 3 ? layout : 4;
        if (is == 0) is = iw * c;
        if (os == 0) os = ow * c;
        if (out == 0) out = nmemAlloc((long) os * oh);
        for (int y = 0; y < oh; y++) {
            float fy = (y + 0.5f) * ih / oh - 0.5f;
            int y0 = Math.max(0, (int) Math.floor(fy)), y1 = Math.min(ih - 1, y0 + 1);
            float ty = Math.max(0, fy - y0);
            for (int x = 0; x < ow; x++) {
                float fx = (x + 0.5f) * iw / ow - 0.5f;
                int x0 = Math.max(0, (int) Math.floor(fx)), x1 = Math.min(iw - 1, x0 + 1);
                float tx = Math.max(0, fx - x0);
                for (int k = 0; k < c; k++) {
                    float a = memGetByte(in + (long) y0 * is + x0 * c + k) & 255, b = memGetByte(in + (long) y0 * is + x1 * c + k) & 255;
                    float d = memGetByte(in + (long) y1 * is + x0 * c + k) & 255, e = memGetByte(in + (long) y1 * is + x1 * c + k) & 255;
                    float v = (a * (1 - tx) + b * tx) * (1 - ty) + (d * (1 - tx) + e * tx) * ty;
                    memPutByte(out + (long) y * os + x * c + k, (byte) Math.round(v));
                }
            }
        }
        return out;
    }
}
