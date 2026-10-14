package bc.gpu;

import com.mojang.renderpearl.api.GpuFormat;
import com.mojang.renderpearl.api.pipeline.*;
import com.mojang.renderpearl.api.pipeline.ShaderType;
import com.mojang.renderpearl.backend.api.BackendRenderPipeline;
import java.util.HashMap;

public class WgPipeline implements BackendRenderPipeline {
    final BackendRenderPipeline.CreateInfo ci;
    final int vs, fs;
    final String[] kinds;
    final HashMap<String, Integer> variants = new HashMap<>();
    boolean closed;

    WgPipeline(BackendRenderPipeline.CreateInfo ci) {
        this.ci = ci;
        int v = 0, f = 0;
        var st = new HashMap<String, String>();
        for (var s : ci.shaders()) {
            var m = (WgslModule) s.module();
            int id = G.module(m.wgsl);
            if (m.type() == ShaderType.VERTEX) v = id; else f = id;
            if (m.samplerTypes != null) st.putAll(m.samplerTypes);
        }
        vs = v;
        fs = f;
        kinds = new String[ci.uniforms().size()];
        for (int i = 0; i < kinds.length; i++) {
            var u = ci.uniforms().get(i);
            kinds[i] = switch (u.type()) {
                case UNIFORM_BUFFER -> "u";
                case TEXEL_BUFFER -> "s";
                default -> {
                    var t = st.get(u.name());
                    if (t != null && t.startsWith("i")) yield "ti";
                    if (t != null && t.startsWith("u")) yield "tu";
                    if (t != null && t.contains("Cube")) yield "tc";
                    if (u.name().toLowerCase().contains("depth")) yield "td";
                    yield "t";
                }
            };
        }
    }

    static String blendF(BlendFactor f) {
        return switch (f) {
            case CONSTANT_ALPHA, CONSTANT_COLOR -> "constant";
            case DST_ALPHA -> "dst-alpha";
            case DST_COLOR -> "dst";
            case ONE -> "one";
            case ONE_MINUS_CONSTANT_ALPHA, ONE_MINUS_CONSTANT_COLOR -> "one-minus-constant";
            case ONE_MINUS_DST_ALPHA -> "one-minus-dst-alpha";
            case ONE_MINUS_DST_COLOR -> "one-minus-dst";
            case ONE_MINUS_SRC_ALPHA -> "one-minus-src-alpha";
            case ONE_MINUS_SRC_COLOR -> "one-minus-src";
            case SRC_ALPHA -> "src-alpha";
            case SRC_ALPHA_SATURATE -> "src-alpha-saturated";
            case SRC_COLOR -> "src";
            case ZERO -> "zero";
        };
    }

    static String eq(BlendEquation e) {
        var op = switch (e.op()) { case ADD -> "add"; case SUBTRACT -> "subtract"; case REVERSE_SUBTRACT -> "reverse-subtract"; case MIN -> "min"; case MAX -> "max"; };
        boolean mm = e.op() == BlendOp.MIN || e.op() == BlendOp.MAX;
        return "{\"srcFactor\":\"" + (mm ? "one" : blendF(e.sourceFactor())) + "\",\"dstFactor\":\"" + (mm ? "one" : blendF(e.destFactor())) + "\",\"operation\":\"" + op + "\"}";
    }

    static String cmp(CompareOp c) {
        return switch (c) {
            case ALWAYS_PASS -> "always";
            case LESS_THAN -> "less";
            case LESS_THAN_OR_EQUAL -> "less-equal";
            case EQUAL -> "equal";
            case NOT_EQUAL -> "not-equal";
            case GREATER_THAN_OR_EQUAL -> "greater-equal";
            case GREATER_THAN -> "greater";
            case NEVER_PASS -> "never";
        };
    }

    static final String[] FMT = {"r8unorm", "r8snorm", "rg8unorm", "rg8snorm", "rgba8unorm", "rgba8snorm", "rgba8unorm", "rgba8snorm", "r16unorm", "r16snorm", "rg16unorm", "rg16snorm",
            "rgba16unorm", "rgba16snorm", "rgba16unorm", "rgba16snorm", "r8uint", "r8sint", "rg8uint", "rg8sint", "rgba8uint", "rgba8sint", "rgba8uint", "rgba8sint",
            "r16uint", "r16sint", "rg16uint", "rg16sint", "rgba16uint", "rgba16sint", "rgba16uint", "rgba16sint", "r32uint", "r32sint", "rg32uint", "rg32sint",
            "rgba32uint", "rgba32sint", "rgba32uint", "rgba32sint", "r16float", "rg16float", "rgba16float", "rgba16float", "r32float", "rg32float", "rgba32float", "rgba32float",
            "rgb10a2unorm", "rgb10a2uint", "rg11b10ufloat", "depth32float", "depth32float-stencil8", "depth24plus-stencil8", "depth16unorm", "stencil8"};

    static String vfmt(GpuFormat f) {
        int n = f.componentCount();
        String b = switch (f.componentType()) {
            case UNORM_8 -> "unorm8";
            case SNORM_8 -> "snorm8";
            case UINT_8 -> "uint8";
            case SINT_8 -> "sint8";
            case UNORM_16 -> "unorm16";
            case SNORM_16 -> "snorm16";
            case UINT_16 -> "uint16";
            case SINT_16 -> "sint16";
            case FLOAT_16 -> "float16";
            case UINT_32 -> "uint32";
            case SINT_32 -> "sint32";
            default -> "float32";
        };
        boolean small = f.componentType().byteSize() < 4;
        if (small && n == 3) n = 4;
        if (n == 1) return b;
        return b + "x" + n;
    }

    String topo() {
        return switch (ci.primitiveTopology()) {
            case DEBUG_LINES -> "line-list";
            case DEBUG_LINE_STRIP -> "line-strip";
            case POINTS -> "point-list";
            case TRIANGLE_STRIP, TRIANGLE_FAN -> "triangle-strip";
            default -> "triangle-list";
        };
    }

    int variant(int[] colors, int depth) {
        var key = java.util.Arrays.toString(colors) + depth;
        var v = variants.get(key);
        if (v != null) return v;
        var sb = new StringBuilder("{\"name\":\"").append(ci.name()).append("\",\"bgl\":[");
        for (int i = 0; i < kinds.length; i++) {
            if (i > 0) sb.append(',');
            var k = kinds[i];
            if (k.equals("u") || k.equals("s")) sb.append("{\"b\":").append(2 * i).append(",\"k\":\"").append(k).append("\"}");
            else {
                var stype = k.equals("ti") ? "sint" : k.equals("tu") ? "uint" : k.equals("td") ? "unfilterable-float" : "float";
                sb.append("{\"b\":").append(2 * i).append(",\"k\":\"t\",\"st\":\"").append(stype).append("\"").append(k.equals("tc") ? ",\"cube\":true" : "").append("},");
                sb.append("{\"b\":").append(2 * i + 1).append(",\"k\":\"p\",\"st\":\"").append(k.equals("td") || k.equals("ti") || k.equals("tu") ? "depth" : "f").append("\"}");
            }
        }
        sb.append("],\"vb\":[");
        int maxSlot = -1;
        for (var b : ci.vertexBuffers()) maxSlot = Math.max(maxSlot, b.bufferSlot());
        for (int s = 0; s <= maxSlot; s++) {
            if (s > 0) sb.append(',');
            CreateInfo.VertexBuffer vb = null;
            for (var b : ci.vertexBuffers()) if (b.bufferSlot() == s) vb = b;
            if (vb == null) { sb.append("null"); continue; }
            sb.append("{\"arrayStride\":").append((vb.stride() + 3) & ~3).append(",\"stepMode\":\"").append(vb.stepRate() > 0 ? "instance" : "vertex").append("\",\"attributes\":[");
            boolean first = true;
            for (var a : ci.attribBindings()) {
                if (a.bufferSlot() != s) continue;
                if (!first) sb.append(',');
                first = false;
                sb.append("{\"shaderLocation\":").append(a.location()).append(",\"offset\":").append(a.offset()).append(",\"format\":\"").append(vfmt(a.format())).append("\"}");
            }
            sb.append("]}");
        }
        sb.append("],\"prim\":{\"topology\":\"").append(topo()).append("\",\"cullMode\":\"").append(ci.cull() ? "back" : "none").append("\",\"frontFace\":\"cw\"}");
        if (depth >= 0) {
            var ds = ci.depthStencilState();
            sb.append(",\"depth\":{\"format\":\"").append(FMT[depth]).append("\",\"depthWriteEnabled\":").append(ds != null && ds.writeDepth())
                    .append(",\"depthCompare\":\"").append(ds == null ? "always" : cmp(ds.depthTest())).append("\"");
            if (ds != null && (ds.depthBiasConstant() != 0 || ds.depthBiasScaleFactor() != 0))
                sb.append(",\"depthBias\":").append((int) ds.depthBiasConstant()).append(",\"depthBiasSlopeScale\":").append(ds.depthBiasScaleFactor());
            sb.append('}');
        }
        sb.append(",\"targets\":[");
        var cts = ci.colorTargetStates();
        for (int i = 0; i < colors.length; i++) {
            if (i > 0) sb.append(',');
            ColorTargetState t = i < cts.size() ? cts.get(i) : null;
            if (colors[i] < 0) { sb.append("null"); continue; }
            sb.append("{\"format\":\"").append(FMT[colors[i]]).append("\",\"writeMask\":").append(t == null ? 0 : t.writeMask());
            if (t != null && t.blendFunction().isPresent()) {
                var bf = t.blendFunction().get();
                sb.append(",\"blend\":{\"color\":").append(eq(bf.color())).append(",\"alpha\":").append(eq(bf.alpha())).append('}');
            }
            sb.append('}');
        }
        sb.append("]}");
        int id = G.pipe(sb.toString(), vs, fs);
        variants.put(key, id);
        return id;
    }

    public boolean isClosed() { return closed; }

    public void close() { closed = true; }
}
