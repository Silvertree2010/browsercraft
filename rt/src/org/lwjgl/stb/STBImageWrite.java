package org.lwjgl.stb;

import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.system.Callback;

public class STBImageWrite {
    public static int nstbi_write_png_to_func(long func, long ctx, int w, int h, int comp, long data, int stride) {
        if (stride == 0) stride = w * comp;
        byte[] px = new byte[stride * h];
        for (int i = 0; i < px.length; i++) px[i] = memGetByte(data + i);
        byte[] png = bc.rt.Png.encode(px, w, h, comp, stride);
        long p = nmemAlloc(png.length);
        for (int i = 0; i < png.length; i++) memPutByte(p + i, png[i]);
        STBIWriteCallbackI cb = Callback.get(func);
        if (cb != null) cb.invoke(ctx, p, png.length);
        nmemFree(p);
        return 1;
    }
}
