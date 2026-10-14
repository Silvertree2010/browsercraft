package bc.gpu;

import com.mojang.logging.LogUtils;
import com.mojang.renderpearl.api.pipeline.ShaderType;
import com.mojang.renderpearl.backend.api.SpvModule;
import java.nio.ByteBuffer;
import java.util.List;
import org.teavm.jso.JSBody;

public final class WgslModule implements SpvModule {
    public final String name;
    public final String wgsl;
    final ShaderType type;
    public java.util.Map<String, String> samplerTypes;

    WgslModule(String name, String wgsl, ShaderType type) { this.name = name; this.wgsl = wgsl; this.type = type; }

    @JSBody(params = {"s", "t"}, script = "return bc.naga(s, t);")
    static native String naga(String s, int t);

    public static WgslModule compile(String name, String glsl, ShaderType t, java.util.Map<String, String> st) {
        var m = compile(name, glsl, t);
        if (m != null) m.samplerTypes = st;
        return m;
    }

    public static WgslModule compile(String name, String glsl, ShaderType t) {
        var r = naga(glsl, t == ShaderType.VERTEX ? 0 : 1);
        if (r == null || r.isEmpty() || r.charAt(0) != 'O') {
            LogUtils.getLogger().error("Shader {} failed: {}\n{}", name, r == null ? "null" : r.substring(1), glsl);
            return null;
        }
        return new WgslModule(name, r.substring(1), t);
    }

    static final Reflection EMPTY = new Reflection() {
        public List<Reflection.InterfaceVariable> inputs() { return List.of(); }
        public List<Reflection.InterfaceVariable> outputs() { return List.of(); }
        public List<Reflection.Descriptor> descriptors(int s) { return List.of(); }
        public List<Reflection.Descriptor> descriptors() { return List.of(); }
        public List<Reflection.PushConstant> pushConstants() { return List.of(); }
    };

    public void close() {}
    public ByteBuffer spv() { return null; }
    public ShaderType type() { return type; }
    public Reflection reflect() { return EMPTY; }
    public Reflection getReflectionInfoIfAvailable() { return EMPTY; }
}
