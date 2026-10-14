package bc.gpu;

import com.mojang.renderpearl.api.textures.AddressMode;
import com.mojang.renderpearl.api.textures.FilterMode;
import com.mojang.renderpearl.api.textures.GpuSampler;
import java.util.OptionalDouble;

public class WgSampler implements GpuSampler {
    final int id;
    final AddressMode u, v;
    final FilterMode min, mag;
    final int aniso;
    final OptionalDouble maxLod;
    boolean closed;

    WgSampler(AddressMode u, AddressMode v, FilterMode min, FilterMode mag, int aniso, OptionalDouble maxLod) {
        this.u = u; this.v = v; this.min = min; this.mag = mag; this.aniso = aniso; this.maxLod = maxLod;
        id = G.sampler(u == AddressMode.CLAMP_TO_EDGE ? 1 : 0, v == AddressMode.CLAMP_TO_EDGE ? 1 : 0, min == FilterMode.LINEAR ? 1 : 0, mag == FilterMode.LINEAR ? 1 : 0, aniso, maxLod.isPresent() ? maxLod.getAsDouble() : -1);
    }

    public AddressMode getAddressModeU() { return u; }
    public AddressMode getAddressModeV() { return v; }
    public FilterMode getMinFilter() { return min; }
    public FilterMode getMagFilter() { return mag; }
    public int getMaxAnisotropy() { return aniso; }
    public OptionalDouble getMaxLod() { return maxLod; }
    public boolean isClosed() { return closed; }
    public void close() { closed = true; }
}
