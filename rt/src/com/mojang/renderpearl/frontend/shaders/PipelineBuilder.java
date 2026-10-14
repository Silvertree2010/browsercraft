package com.mojang.renderpearl.frontend.shaders;

import bc.gpu.Glsl;
import bc.gpu.WgslModule;
import com.mojang.logging.LogUtils;
import com.mojang.renderpearl.api.GpuFormat;
import com.mojang.renderpearl.api.pipeline.BindGroupLayout;
import com.mojang.renderpearl.api.pipeline.CompiledRenderPipeline;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.mojang.renderpearl.api.pipeline.ShaderSource;
import com.mojang.renderpearl.api.pipeline.ShaderType;
import com.mojang.renderpearl.api.vertex.VertexFormat;
import com.mojang.renderpearl.api.vertex.VertexFormatElement;
import com.mojang.renderpearl.backend.api.BackendRenderPipeline;
import com.mojang.renderpearl.backend.api.GpuDeviceBackend;
import com.mojang.renderpearl.frontend.FrontendRenderPipeline;
import com.mojang.renderpearl.util.UncheckedAutoCloseable;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import it.unimi.dsi.fastutil.objects.ReferenceLists;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;

public class PipelineBuilder implements UncheckedAutoCloseable {
    static final Logger L = LogUtils.getLogger();
    final GpuDeviceBackend dev;

    public PipelineBuilder(GpuDeviceBackend dev) { this.dev = dev; }

    public void close() {}

    static String inc(ShaderSource src, String id) {
        var r = src.getInclude(Identifier.parse(id));
        if (r == null || r.error()) return null;
        return r.content();
    }

    BackendRenderPipeline.CreateInfo info(RenderPipeline p, ShaderSource src, Object2IntOpenHashMap<String> ub) {
        var d = p.getShaderDefines();
        var pre = new EnumMap<ShaderType, String>(ShaderType.class);
        for (var e : p.getShaders().entrySet()) {
            var s = src.getShader(e.getValue(), e.getKey());
            if (s == null) { L.error("Couldn't find source for {} shader ({})", e.getKey(), e.getValue()); return null; }
            pre.put(e.getKey(), Glsl.constArr(Glsl.preprocess(s, d.values(), d.flags(), id -> inc(src, id))));
        }
        var names = new LinkedHashSet<String>();
        for (var e : pre.entrySet()) {
            var r = Glsl.rewrite(e.getValue(), Map.of(), e.getKey() == ShaderType.VERTEX);
            names.addAll(r.ubos);
            names.addAll(r.samplers);
            names.addAll(r.buffers);
        }
        var byName = new HashMap<String, BindGroupLayout.UniformDescription>();
        for (var u : BindGroupLayout.flattenUniforms(p.getBindGroupLayouts())) byName.put(u.name(), u);
        var uniforms = new ArrayList<BindGroupLayout.UniformDescription>();
        var bind = new HashMap<String, Integer>();
        for (var n : names) {
            var u = byName.get(n);
            if (u == null) { L.debug("Pipeline {}: unbound uniform {}", p.getLocation(), n); continue; }
            int i = ub.computeIntIfAbsent(n, k -> ub.size());
            bind.put(n, 2 * i);
            if (i >= uniforms.size()) uniforms.add(u);
        }
        var shaders = new ArrayList<BackendRenderPipeline.CreateInfo.Shader>();
        Glsl.Res vr = null;
        for (var e : pre.entrySet()) {
            var r = Glsl.rewrite(e.getValue(), bind, e.getKey() == ShaderType.VERTEX);
            if (e.getKey() == ShaderType.VERTEX) vr = r;
            var name = p.getShaders().get(e.getKey()).toString();
            var m = WgslModule.compile(name, r.src, e.getKey(), r.samplerTypes);
            if (m == null) return null;
            shaders.add(new BackendRenderPipeline.CreateInfo.Shader(name, "main", m));
        }
        var vbs = new ArrayList<BackendRenderPipeline.CreateInfo.VertexBuffer>();
        var abs = new ArrayList<BackendRenderPipeline.CreateInfo.AttribBinding>();
        if (vr != null) {
            var vf = p.getVertexFormatBindings();
            String pn = null;
            int pl = 0;
            for (int i = 0; i < vf.size(); i++) {
                var b = vf.get(i);
                if (b == null) continue;
                vbs.add(new BackendRenderPipeline.CreateInfo.VertexBuffer(i, b.getVertexSize(), b.getStepRate()));
                for (VertexFormatElement el : b.getElements()) {
                    var in = vr.inputs.get(el.name());
                    if (in == null) continue;
                    int loc = el.name().equals(pn) ? pl + 1 : in[0];
                    abs.add(new BackendRenderPipeline.CreateInfo.AttribBinding(i, loc, el.offset(), el.format()));
                    pn = el.name();
                    pl = loc;
                }
            }
        }
        return new BackendRenderPipeline.CreateInfo(p.getLocation().toString(), List.copyOf(shaders), List.copyOf(vbs), List.copyOf(abs), List.copyOf(uniforms),
                p.pushConstantSize(), p.getDepthStencilState(), p.getPolygonMode(), p.isCull(), p.getColorTargetStates(), p.getPrimitiveTopology());
    }

    public CompletableFuture<CompiledRenderPipeline.Pending> compilePipeline(RenderPipeline p, ShaderSource src, Executor ex) {
        return CompletableFuture.supplyAsync(() -> {
            var ub = new Object2IntOpenHashMap<String>();
            BackendRenderPipeline.CreateInfo ci;
            try { ci = info(p, src, ub); } catch (RuntimeException e) { L.error("Couldn't compile pipeline ({})", p.getLocation(), e); ci = null; }
            if (ci == null) return CompiledRenderPipeline.Pending.NULL;
            var pend = dev.compilePipeline(ci);
            return () -> {
                var bp = pend.finishCompile();
                return bp == null ? null : new FrontendRenderPipeline(p.getLocation().toString(), bp,
                        ReferenceLists.unmodifiable(new ReferenceArrayList<>(p.getVertexFormatBindings())), Object2IntMaps.unmodifiable(ub),
                        BindGroupLayout.flattenUniforms(p.getBindGroupLayouts()), ReferenceLists.unmodifiable(new ReferenceArrayList<>(p.getColorTargetStates())),
                        p.wantsDepthTexture(), p.pushConstantSize());
            };
        }, ex);
    }
}
