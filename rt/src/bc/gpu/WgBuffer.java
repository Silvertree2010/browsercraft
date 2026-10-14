package bc.gpu;

import static org.lwjgl.system.MemoryUtil.*;

import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import com.mojang.renderpearl.backend.common.BaseGpuBuffer;

public class WgBuffer extends BaseGpuBuffer {
    final int id;
    long host;
    boolean ownHost = true;
    boolean closed;

    WgBuffer(int usage, long size) {
        super(usage, size);
        id = G.buf(size, wgUsage(usage));
    }

    WgBuffer(int usage, long size, long host) {
        this(usage, size);
        this.host = host;
        this.ownHost = false;
    }

    static int wgUsage(int u) {
        int r = 4 | 8;
        if ((u & 32) != 0) r |= 32;
        if ((u & 64) != 0) r |= 16;
        if ((u & 128) != 0) r |= 64;
        if ((u & 256) != 0) r |= 128;
        if ((u & 512) != 0) r |= 256;
        if (r == 12) r |= 32 | 16 | 64;
        return r;
    }

    long host() {
        if (host == 0) host = nmemCalloc(1, size());
        return host;
    }

    public boolean isClosed() { return closed; }

    public void close() {
        if (closed) return;
        closed = true;
        G.bufDel(id);
        if (host != 0 && ownHost) nmemFree(host);
        host = 0;
    }

    public GpuBufferSlice.MappedView map(long off, long len, boolean read, boolean write) {
        long h = host() + off;
        var slice = new GpuBufferSlice(this, off, len);
        return new GpuBufferSlice.MappedView(slice, memByteBuffer(h, (int) len), () -> { if (write && !closed) G.write(id, off, (int) h, (int) len); });
    }
}
