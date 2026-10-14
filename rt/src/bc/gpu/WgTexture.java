package bc.gpu;

import com.mojang.renderpearl.api.GpuFormat;
import com.mojang.renderpearl.backend.common.BaseGpuTexture;

public class WgTexture extends BaseGpuTexture {
    final int id;
    final boolean cube;
    int[] mipViews;
    boolean closed;

    WgTexture(int usage, String label, GpuFormat f, int w, int h, int d, int m) {
        super(usage, label, f, w, h, d, m);
        cube = (usage & 16) != 0;
        int u = 1 | 2 | 4;
        if ((usage & 8) != 0) u |= 16;
        id = G.tex(w, h, d, m, f.ordinal(), u, cube);
    }

    int mipView(int m) {
        if (mipViews == null) mipViews = new int[getMipLevels()];
        if (mipViews[m] == 0) mipViews[m] = G.view(id, m, 1, false);
        return mipViews[m];
    }

    public boolean isClosed() { return closed; }

    public void close() {
        if (closed) return;
        closed = true;
        if (mipViews != null) for (int v : mipViews) if (v != 0) G.viewDel(v);
        G.texDel(id);
    }
}
