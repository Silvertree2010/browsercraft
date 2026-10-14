package bc.gpu;

import com.mojang.renderpearl.api.device.BackendCreationException;
import com.mojang.renderpearl.api.device.GpuDebugOptions;
import com.mojang.renderpearl.api.device.GpuDevice;
import com.mojang.renderpearl.frontend.FrontendGpuDevice;

public final class Wg {
    public static long window(String t, int w, int h) { return 0x5000; }

    public static GpuDevice device(GpuDebugOptions o) throws BackendCreationException {
        return new FrontendGpuDevice(new WgDevice());
    }
}
