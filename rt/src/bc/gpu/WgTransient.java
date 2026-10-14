package bc.gpu;

import static org.lwjgl.system.MemoryUtil.*;

import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import com.mojang.renderpearl.api.buffers.TransientMemory;
import com.mojang.renderpearl.backend.util.TransientBlockAllocator;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class WgTransient implements TransientMemory {
    record Blk(WgBuffer buf, long host) implements TransientBlockAllocator.Allocator.Block {
        public boolean suboptimal() { return false; }
    }

    final TransientBlockAllocator<TransientBlockAllocator.Allocator.CpuBlock> cpu = new TransientBlockAllocator<>(524288L, 16L, TransientBlockAllocator.Allocator.CpuBlock.memalloc());
    final TransientBlockAllocator<Blk> gpu = new TransientBlockAllocator<>(1L << 21, 1L << 16, TransientBlockAllocator.Allocator.create(s -> {
        long h = nmemCalloc(1, s);
        return new Blk(new WgBuffer(32 | 64 | 128 | 256 | 512 | 8 | 16, s, h), h);
    }, b -> { b.buf.close(); nmemFree(b.host); }));

    void rotate() {
        cpu.rotate().run();
        gpu.rotate().run();
    }

    public ByteBuffer allocateCpu(long size, long align, long min, long el) {
        var a = cpu.allocate(size, align, min, el);
        return memByteBuffer(a.block().address() + a.offset(), (int) a.size());
    }

    public GpuBufferSlice.MappedView allocateStaging(long size, long align, int usage, long min, long el) {
        var a = gpu.allocate(size, Math.max(4, align), min, el);
        var b = a.block();
        var slice = new GpuBufferSlice(b.buf, a.offset(), a.size());
        long h = b.host + a.offset();
        int n = (int) a.size();
        return new GpuBufferSlice.MappedView(slice, memByteBuffer(h, n), () -> G.write(b.buf.id, a.offset(), (int) h, n));
    }

    public GpuBufferSlice allocateGpu(long size, long align, int usage, long min, long el) {
        var a = gpu.allocate(size, Math.max(4, align), min, el);
        return new GpuBufferSlice(a.block().buf, a.offset(), a.size());
    }

    public GpuBufferSlice.MappedView allocateGpuMapped(long size, long align, int usage, long min, long el) {
        return allocateStaging(size, align, usage, min, el);
    }

    public GpuBufferSlice uploadStaging(List<ByteBuffer> d, long align, int usage, long min, long el) { return uploadGpu(d, align, usage, min, el); }

    static long round(long v, long a) { return a <= 1 ? v : (v + a - 1) / a * a; }

    public GpuBufferSlice uploadGpu(List<ByteBuffer> d, long align, int usage, long min, long el) {
        long total = 0;
        for (var b : d) total = round(total + b.remaining(), align);
        try (var v = allocateStaging(Math.max(total, 4), align, usage, min, el)) {
            long base = memAddress(v.data());
            long o = 0;
            for (var b : d) {
                memCopy(memAddress(b), base + o, b.remaining());
                o = round(o + b.remaining(), align);
            }
            return v.slice();
        }
    }

    public List<GpuBufferSlice> multiUploadStaging(List<ByteBuffer> d, long align, int usage) { return multiUploadGpu(d, align, usage); }

    public List<GpuBufferSlice> multiUploadGpu(List<ByteBuffer> d, long align, int usage) {
        long total = 0;
        for (var b : d) total = round(total + b.remaining(), align);
        var r = new ArrayList<GpuBufferSlice>(d.size());
        try (var v = allocateStaging(Math.max(total, 4), align, usage, 0, 1)) {
            long base = memAddress(v.data());
            long o = 0;
            for (var b : d) {
                int n = b.remaining();
                memCopy(memAddress(b), base + o, n);
                r.add(v.slice().slice(o, n));
                o = round(o + n, align);
            }
        }
        return r;
    }
}
