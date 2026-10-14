package bc.gpu;

import static org.lwjgl.system.MemoryUtil.*;

import com.mojang.renderpearl.api.GpuFormat;
import com.mojang.renderpearl.api.buffers.GpuBuffer;
import com.mojang.renderpearl.api.commands.GpuQueryPool;
import com.mojang.renderpearl.api.device.*;
import com.mojang.renderpearl.api.textures.*;
import com.mojang.renderpearl.backend.api.*;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class WgDevice implements GpuDeviceBackend {
    final WgEncoder enc = new WgEncoder();
    final DeviceInfo info;

    WgDevice() {
        info = new DeviceInfo(G.info("name"), "WebGPU", "browser", true, "WebGPU", 1f,
                new DeviceLimits(16, G.infoI("uboAlign"), G.infoI("maxTex"), 1L << 31, 0, 8, 1 << 20),
                new DeviceFeatures(false, false, false, true, false, true, true, false),
                Set.of(), new HintsAndWorkarounds(false, false, false, true), DeviceType.OTHER);
    }

    public GpuSurfaceBackend createSurface(long w, BooleanSupplier ic) { return new WgSurface(); }
    public CommandEncoderBackend createCommandEncoder() { return enc; }
    public GpuSampler createSampler(AddressMode u, AddressMode v, FilterMode mi, FilterMode ma, int an, OptionalDouble ml) { return new WgSampler(u, v, mi, ma, an, ml); }
    public GpuTexture createTexture(String l, int u, GpuFormat f, int w, int h, int d, int m) { return new WgTexture(u, l, f, w, h, d, m); }
    public GpuTextureView createTextureView(GpuTexture t, int b, int m) { return new WgView((WgTexture) t, b, m); }
    public GpuBuffer createBuffer(Supplier<String> l, int u, long s) { return new WgBuffer(u, s); }

    public GpuBuffer createBuffer(Supplier<String> l, int u, ByteBuffer d) {
        var b = new WgBuffer(u, d.remaining());
        G.write(b.id, 0, (int) memAddress(d), d.remaining());
        if ((u & 3) != 0) memCopy(memAddress(d), b.host(), d.remaining());
        return b;
    }

    public List<String> getLastDebugMessages() { return List.of(); }
    public boolean isDebuggingEnabled() { return false; }

    public BackendRenderPipeline.Pending compilePipeline(BackendRenderPipeline.CreateInfo ci) {
        return () -> new WgPipeline(ci);
    }

    public void close() {}

    public GpuQueryPool createTimestampQueryPool(int n) {
        return new GpuQueryPool() {
            public int size() { return n; }
            public OptionalLong getValue(int i) { return OptionalLong.empty(); }
            public OptionalLong[] getValues(int a, int c) { var r = new OptionalLong[c]; java.util.Arrays.fill(r, OptionalLong.empty()); return r; }
            public void close() {}
        };
    }

    public long getTimestampCalibrationOffset() { return 0; }
    public DeviceInfo getDeviceInfo() { return info; }
}
