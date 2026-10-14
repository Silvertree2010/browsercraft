package bc.gpu;

import static org.lwjgl.system.MemoryUtil.*;

import com.mojang.renderpearl.api.buffers.GpuBuffer;
import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import com.mojang.renderpearl.api.commands.GpuQueryPool;
import com.mojang.renderpearl.api.pipeline.IndexType;
import com.mojang.renderpearl.backend.api.BackendRenderPipeline;
import com.mojang.renderpearl.backend.api.RenderPassBackend;
import com.mojang.renderpearl.util.TextureViewAndSampler;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.function.Supplier;
import org.lwjgl.PointerBuffer;

public class WgPass implements RenderPassBackend {
    static final ByteBuffer BIND = memAlloc(64 * 12);
    static final long BA = memAddress(BIND);

    final WgEncoder enc;
    final int[] colors;
    final int depth, w, h, ax, ay, aw, ah;
    WgPipeline pipe;
    int pid;
    Object[] uni = new Object[0];
    boolean dirty = true;
    final GpuBufferSlice[] vbs = new GpuBufferSlice[16];
    final boolean[] vbDirty = new boolean[16];
    WgBuffer ib;
    IndexType it = IndexType.INT;
    boolean ibDirty;

    WgPass(WgEncoder enc, int[] colors, int depth, int w, int h, int ax, int ay, int aw, int ah) {
        this.enc = enc; this.colors = colors; this.depth = depth; this.w = w; this.h = h; this.ax = ax; this.ay = ay; this.aw = aw; this.ah = ah;
    }

    public void pushDebugGroup(Supplier<String> l) {}
    public void popDebugGroup() {}

    public void setPipeline(BackendRenderPipeline p) {
        var np = (WgPipeline) p;
        if (np != pipe) {
            pipe = np;
            uni = new Object[np.kinds.length];
            dirty = true;
        }
        int id = np.variant(colors, depth);
        if (id != pid) { pid = id; if (id > 0) G.setPipe(id); dirty = true; }
        for (int i = 0; i < 16; i++) vbDirty[i] = vbs[i] != null;
        ibDirty = ib != null;
    }

    public void setUniform(int i, Object v) {
        if (i < uni.length && uni[i] != v) { uni[i] = v; dirty = true; }
    }

    public void pushConstants(ByteBuffer v) {}

    public void enableScissor(int x, int y, int sw, int sh) {
        int x0 = Math.max(0, x), y0 = Math.max(0, y);
        int x1 = Math.min(w, x + sw), y1 = Math.min(h, y + sh);
        G.scissor(x0, y0, Math.max(0, x1 - x0), Math.max(0, y1 - y0));
    }

    public void disableScissor() { G.scissor(ax, ay, aw, ah); }

    public void setVertexBuffer(int s, GpuBufferSlice v) { vbs[s] = v; vbDirty[s] = v != null; }

    public void setIndexBuffer(GpuBuffer b, IndexType t) { ib = (WgBuffer) b; it = t; ibDirty = b != null; }

    boolean setup() {
        if (pid <= 0) return false;
        for (int i = 0; i < 16; i++) {
            if (!vbDirty[i]) continue;
            vbDirty[i] = false;
            var s = vbs[i];
            G.setVB(i, ((WgBuffer) s.buffer()).id, s.offset());
        }
        if (ibDirty) { ibDirty = false; G.setIB(ib.id, it == IndexType.INT ? 1 : 0, 0); }
        if (dirty) {
            int n = 0;
            for (int i = 0; i < pipe.kinds.length; i++) {
                var k = pipe.kinds[i];
                var v = uni[i];
                if (k.equals("u") || k.equals("s")) {
                    if (!(v instanceof GpuBufferSlice s)) return false;
                    put(n++, ((WgBuffer) s.buffer()).id, (int) s.offset(), (int) s.length());
                } else {
                    if (!(v instanceof TextureViewAndSampler t)) return false;
                    put(n++, ((WgView) t.view()).id, 0, 0);
                    put(n++, ((WgSampler) t.sampler()).id, 0, 0);
                }
            }
            G.bind(pid, (int) BA, n);
            dirty = false;
        }
        return true;
    }

    static void put(int i, int id, int off, int size) {
        memPutInt(BA + i * 12L, id);
        memPutInt(BA + i * 12L + 4, off);
        memPutInt(BA + i * 12L + 8, size);
    }

    public void drawIndexed(int n, int inst, int first, int base, int fi) {
        if (setup()) G.drawIdx(n, inst, first, base, fi);
    }

    public void multiDrawIndexed(IntBuffer p, int inst, int fi, int count) {
        if (!setup()) return;
        for (int i = 0; i < count; i++) G.drawIdx(p.get(p.position() + i * 3), inst, p.get(p.position() + i * 3 + 1), p.get(p.position() + i * 3 + 2), fi);
    }

    public void multiDrawIndexed(PointerBuffer offs, IntBuffer counts, IntBuffer verts, int count) {
        if (!setup()) return;
        int bytes = it.bytes;
        for (int i = 0; i < count; i++) G.drawIdx(counts.get(counts.position() + i), 1, (int) (offs.get(offs.position() + i) / bytes), verts.get(verts.position() + i), 0);
    }

    public void drawIndexedIndirect(GpuBufferSlice c, int count) {
        if (!setup()) return;
        int b = ((WgBuffer) c.buffer()).id;
        for (int i = 0; i < count; i++) G.drawIdxInd(b, c.offset() + i * 20L);
    }

    public void draw(int vc, int ic, int fv, int fi) {
        if (setup()) G.draw(vc, ic, fv, fi);
    }

    public void multiDraw(IntBuffer p, int inst, int fi, int count) {
        if (!setup()) return;
        for (int i = 0; i < count; i++) G.draw(p.get(p.position() + i * 2 + 1), inst, p.get(p.position() + i * 2), fi);
    }

    public void multiDraw(IntBuffer first, IntBuffer counts, int count) {
        if (!setup()) return;
        for (int i = 0; i < count; i++) G.draw(counts.get(counts.position() + i), 1, first.get(first.position() + i), 0);
    }

    public void drawIndirect(GpuBufferSlice c, int count) {
        if (!setup()) return;
        int b = ((WgBuffer) c.buffer()).id;
        for (int i = 0; i < count; i++) G.drawInd(b, c.offset() + i * 16L);
    }

    public void writeTimestamp(GpuQueryPool p, int i) {}
}
