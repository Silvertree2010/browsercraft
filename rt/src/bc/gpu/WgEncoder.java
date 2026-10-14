package bc.gpu;

import static org.lwjgl.system.MemoryUtil.*;

import com.mojang.renderpearl.api.buffers.GpuBuffer;
import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import com.mojang.renderpearl.api.buffers.TransientMemory;
import com.mojang.renderpearl.api.commands.GpuFence;
import com.mojang.renderpearl.api.commands.GpuQueryPool;
import com.mojang.renderpearl.api.commands.RenderPassDescriptor;
import com.mojang.renderpearl.api.textures.GpuTexture;
import com.mojang.renderpearl.backend.api.CommandEncoderBackend;
import com.mojang.renderpearl.backend.api.RenderPassBackend;
import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.ByteBuffer;
import org.joml.Vector4fc;

public class WgEncoder implements CommandEncoderBackend {
    static WgEncoder I;
    final WgTransient tm = new WgTransient();
    WgPass cur;

    WgEncoder() { I = this; }

    void afterSubmit() { tm.rotate(); }

    public void submit() {
        G.submit();
        tm.rotate();
    }

    public TransientMemory transientMemory() { return tm; }

    static String col(Vector4fc c) { return "[" + c.x() + "," + c.y() + "," + c.z() + "," + c.w() + "]"; }

    public RenderPassBackend createRenderPass(RenderPassDescriptor d) {
        var sb = new StringBuilder("{\"c\":[");
        var cl = new StringBuilder("[");
        int w = 0, h = 0, n = 0;
        var fmts = new int[d.colorAttachments().size()];
        for (int i = 0; i < d.colorAttachments().size(); i++) {
            var a = d.colorAttachments().get(i);
            if (i > 0) { sb.append(','); cl.append(','); }
            if (a == null) { sb.append(0); cl.append("null"); fmts[i] = -1; continue; }
            var v = (WgView) a.textureView();
            sb.append(v.attach());
            cl.append(a.clearValue().isPresent() ? col(a.clearValue().get()) : "null");
            w = v.getWidth(0);
            h = v.getHeight(0);
            fmts[i] = v.texture().getFormat().ordinal();
            n++;
        }
        sb.append("],\"cl\":").append(cl).append("]");
        var da = d.depthAttachment();
        boolean depth = da != null;
        if (depth) {
            var v = (WgView) da.textureView();
            sb.append(",\"d\":").append(v.attach()).append(",\"dc\":").append(da.clearValue().isPresent() ? String.valueOf(da.clearValue().getAsDouble()) : "-1");
            if (n == 0) { w = v.getWidth(0); h = v.getHeight(0); }
        } else sb.append(",\"d\":0");
        var ra = d.renderArea();
        sb.append(",\"s\":[").append(ra.x()).append(',').append(ra.y()).append(',').append(ra.width()).append(',').append(ra.height()).append("]}");
        G.pass(sb.toString());
        cur = new WgPass(this, fmts, depth ? ((WgView) da.textureView()).texture().getFormat().ordinal() : -1, w, h, ra.x(), ra.y(), ra.width(), ra.height());
        return cur;
    }

    public void submitRenderPass() {
        G.endPass();
        cur = null;
    }

    void clear(int view, Vector4fc c, int dview, double depth) {
        G.pass("{\"c\":[" + (view > 0 ? view : "") + "],\"cl\":[" + (view > 0 ? col(c) : "") + "],\"d\":" + dview + ",\"dc\":" + depth + "}");
        G.endPass();
    }

    public void clearColorTexture(GpuTexture t, Vector4fc c) {
        var x = (WgTexture) t;
        for (int m = 0; m < t.getMipLevels(); m++) clear(x.mipView(m), c, 0, -1);
    }

    public void clearColorAndDepthTextures(GpuTexture t, Vector4fc c, GpuTexture d, double depth) {
        var x = (WgTexture) t;
        var y = (WgTexture) d;
        for (int m = 0; m < t.getMipLevels(); m++) clear(x.mipView(m), c, y.mipView(m), depth);
    }

    public void clearColorAndDepthTextures(GpuTexture t, Vector4fc c, GpuTexture d, double depth, int x, int y, int w, int h, int mip) {
        clear(((WgTexture) t).mipView(mip), c, ((WgTexture) d).mipView(mip), depth);
    }

    public void clearDepthTexture(GpuTexture d, double depth) {
        var y = (WgTexture) d;
        for (int m = 0; m < d.getMipLevels(); m++) clear(0, null, y.mipView(m), depth);
    }

    public void writeToBuffer(GpuBufferSlice s, ByteBuffer data) {
        G.write(((WgBuffer) s.buffer()).id, s.offset(), (int) memAddress(data), data.remaining());
    }

    public void copyToBuffer(GpuBufferSlice s, GpuBufferSlice t) {
        G.copyBB(((WgBuffer) s.buffer()).id, s.offset(), ((WgBuffer) t.buffer()).id, t.offset(), s.length());
    }

    public void writeToTexture(GpuTexture t, ByteBuffer src, int mip, int layer, int x, int y, int w, int h) {
        int bs = t.getFormat().blockSize();
        G.writeTex(((WgTexture) t).id, mip, layer, x, y, w, h, (int) memAddress(src), src.remaining(), w * bs);
    }

    public void copyBufferToTexture(GpuBufferSlice s, int sx, int sy, int sw, int sh, GpuTexture t, int dx, int dy, int cw, int ch, int mip, int layer) {
        var b = (WgBuffer) s.buffer();
        int bs = t.getFormat().blockSize();
        long base = b.host() + s.offset() + ((long) sx + (long) sy * sw) * bs;
        G.writeTex(((WgTexture) t).id, mip, layer, dx, dy, cw, ch, (int) base, sw * bs * (ch - 1) + cw * bs, sw * bs);
    }

    public void copyTextureToBuffer(GpuTexture t, GpuBuffer b, long off, Runnable cb, int mip) {
        copyTextureToBuffer(t, b, off, cb, mip, 0, 0, t.getWidth(mip), t.getHeight(mip));
    }

    public void copyTextureToBuffer(GpuTexture t, GpuBuffer b, long off, Runnable cb, int mip, int x, int y, int w, int h) {
        var wb = (WgBuffer) b;
        G.readTex(((WgTexture) t).id, mip, x, y, w, h, t.getFormat().blockSize(), (int) (wb.host() + off));
        RenderSystem.queueFencedTask(cb);
    }

    public void copyTextureToTexture(GpuTexture s, GpuTexture d, int mip, int dx, int dy, int sx, int sy, int w, int h) {
        G.copyTT(((WgTexture) d).id, mip, dx, dy, ((WgTexture) s).id, sx, sy, w, h);
    }

    public GpuFence createFence() { return new WgFence(G.fence()); }

    public void writeTimestamp(GpuQueryPool p, int i) {}
}
