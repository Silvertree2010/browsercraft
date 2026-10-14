package bc.gpu;

import com.mojang.renderpearl.api.device.GpuSurface;
import com.mojang.renderpearl.api.device.SurfaceException;
import com.mojang.renderpearl.api.textures.GpuTextureView;
import com.mojang.renderpearl.backend.api.CommandEncoderBackend;
import com.mojang.renderpearl.backend.api.GpuSurfaceBackend;
import java.util.Collection;
import java.util.EnumSet;
import java.util.function.BooleanSupplier;

public class WgSurface implements GpuSurfaceBackend {
    int w, h;
    WgView view;

    public void configure(GpuSurface.Configuration c) throws SurfaceException {
        w = c.width();
        h = c.height();
        G.resize(w, h);
    }

    public boolean isSuboptimal() { return false; }
    public void acquireNextTexture() {}
    public void blitFromTexture(CommandEncoderBackend e, GpuTextureView v) { view = (WgView) v; }

    boolean first = true;

    @org.teavm.jso.JSBody(params = {}, script = "bc.post({status: 'frame'});")
    static native void firstFrame();

    public void present() {
        if (view != null) G.present(view.id, w, h);
        if (first && view != null) { first = false; firstFrame(); }
        WgEncoder.I.afterSubmit();
        G.frame();
    }

    public void close() {}
    public Collection<GpuSurface.PresentMode> supportedPresentModes() { return EnumSet.of(GpuSurface.PresentMode.FIFO, GpuSurface.PresentMode.IMMEDIATE); }
}
