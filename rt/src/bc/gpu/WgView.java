package bc.gpu;

import com.mojang.renderpearl.backend.common.BaseGpuTextureView;

public class WgView extends BaseGpuTextureView {
    final int id;
    final WgTexture tex;
    boolean closed;

    WgView(WgTexture t, int base, int mips) {
        super(t, base, mips);
        tex = t;
        id = G.view(t.id, base, mips, t.cube);
    }

    int attach() { return mipLevels() == 1 && !tex.cube ? id : tex.mipView(baseMipLevel()); }

    public boolean isClosed() { return closed || tex.isClosed(); }

    public void close() {
        if (closed) return;
        closed = true;
        G.viewDel(id);
    }
}
