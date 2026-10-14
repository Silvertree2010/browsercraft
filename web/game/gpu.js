const H = [null];
const free = [];
const doomed = [];
function put(o) { const i = free.length ? free.pop() : H.length; H[i] = o; return i; }
function del(i) { const o = H[i]; H[i] = null; free.push(i); return o; }

let dev, q, ctx, cfmt, enc, pass, mem, stage, stageOff = 0, blit, blitBgl, blitSampler;
const STAGE = 32 << 20;
const bgCache = new Map();
let frames = [];
const FMT = ['r8unorm', 'r8snorm', 'rg8unorm', 'rg8snorm', 'rgba8unorm', 'rgba8snorm', 'rgba8unorm', 'rgba8snorm', 'r16unorm', 'r16snorm', 'rg16unorm', 'rg16snorm',
  'rgba16unorm', 'rgba16snorm', 'rgba16unorm', 'rgba16snorm', 'r8uint', 'r8sint', 'rg8uint', 'rg8sint', 'rgba8uint', 'rgba8sint', 'rgba8uint', 'rgba8sint',
  'r16uint', 'r16sint', 'rg16uint', 'rg16sint', 'rgba16uint', 'rgba16sint', 'rgba16uint', 'rgba16sint', 'r32uint', 'r32sint', 'rg32uint', 'rg32sint',
  'rgba32uint', 'rgba32sint', 'rgba32uint', 'rgba32sint', 'r16float', 'rg16float', 'rgba16float', 'rgba16float', 'r32float', 'rg32float', 'rgba32float', 'rgba32float',
  'rgb10a2unorm', 'rgb10a2uint', 'rg11b10ufloat', 'depth32float', 'depth32float-stencil8', 'depth24plus-stencil8', 'depth16unorm', 'stencil8'];

function u8() { return new Uint8Array(mem.buffer); }

export async function init(canvas, memory) {
  const ad = await navigator.gpu.requestAdapter({ powerPreference: 'high-performance' });
  if (!ad) throw new Error('WebGPU not available');
  const feats = [];
  for (const f of ['depth32float-stencil8', 'indirect-first-instance', 'texture-compression-bc', 'float32-filterable', 'rg11b10ufloat-renderable']) if (ad.features.has(f)) feats.push(f);
  dev = await ad.requestDevice({
    requiredFeatures: feats,
    requiredLimits: {
      maxTextureDimension2D: ad.limits.maxTextureDimension2D,
      maxBufferSize: ad.limits.maxBufferSize,
      maxStorageBufferBindingSize: ad.limits.maxStorageBufferBindingSize,
      maxUniformBufferBindingSize: ad.limits.maxUniformBufferBindingSize,
      maxBindingsPerBindGroup: ad.limits.maxBindingsPerBindGroup,
      maxSampledTexturesPerShaderStage: ad.limits.maxSampledTexturesPerShaderStage,
      maxSamplersPerShaderStage: ad.limits.maxSamplersPerShaderStage,
      maxVertexBuffers: ad.limits.maxVertexBuffers,
      maxVertexAttributes: ad.limits.maxVertexAttributes,
    },
  });
  dev.lost.then(i => console.error('[gpu] device lost', i.message));
  dev.onuncapturederror = e => { if ((self.gpuErr = (self.gpuErr || 0) + 1) < 50) console.error('[gpu]', e.error.message); };
  q = dev.queue;
  ctx = canvas.getContext('webgpu');
  cfmt = navigator.gpu.getPreferredCanvasFormat();
  ctx.configure({ device: dev, format: cfmt, alphaMode: 'opaque' });
  stage = dev.createBuffer({ size: STAGE, usage: GPUBufferUsage.COPY_SRC | GPUBufferUsage.COPY_DST });
  enc = dev.createCommandEncoder();
  const sm = dev.createShaderModule({ code: `
    @group(0) @binding(0) var t: texture_2d<f32>;
    @group(0) @binding(1) var s: sampler;
    struct O { @builtin(position) p: vec4f, @location(0) uv: vec2f };
    @vertex fn vs(@builtin(vertex_index) i: u32) -> O {
      var o: O;
      let x = f32((i << 1u) & 2u);
      let y = f32(i & 2u);
      o.p = vec4f(x * 2.0 - 1.0, y * 2.0 - 1.0, 0.0, 1.0);
      o.uv = vec2f(x, y);
      return o;
    }
    @fragment fn fs(i: O) -> @location(0) vec4f { return vec4f(textureSample(t, s, i.uv).rgb, 1.0); }` });
  blitBgl = dev.createBindGroupLayout({ entries: [{ binding: 0, visibility: GPUShaderStage.FRAGMENT, texture: {} }, { binding: 1, visibility: GPUShaderStage.FRAGMENT, sampler: {} }] });
  blit = dev.createRenderPipeline({ layout: dev.createPipelineLayout({ bindGroupLayouts: [blitBgl] }), vertex: { module: sm, entryPoint: 'vs' }, fragment: { module: sm, entryPoint: 'fs', targets: [{ format: cfmt }] }, primitive: { topology: 'triangle-list' } });
  blitSampler = dev.createSampler({ magFilter: 'nearest', minFilter: 'nearest' });
  return { maxTex: dev.limits.maxTextureDimension2D, uboAlign: dev.limits.minUniformBufferOffsetAlignment, name: (ad.info && (ad.info.description || ad.info.vendor)) || 'WebGPU' };
}

export function setMemory(m) { mem = m; }

function endPass() { if (pass) { pass.end(); pass = null; } }

function staged(ptr, len) {
  const n = (len + 3) & ~3;
  if (stageOff + n > STAGE) return -1;
  const o = stageOff;
  q.writeBuffer(stage, o, mem.buffer, ptr, len);
  stageOff += (n + 255) & ~255;
  return o;
}

export const gpu = {
  buf(size, usage) { const u = (usage & GPUBufferUsage.UNIFORM) !== 0; return put(dev.createBuffer({ size: u ? Math.max(16, (size + 15) & ~15) : Math.max(4, (size + 3) & ~3), usage })); },
  bufDel(i) { const b = del(i); if (b) doomed.push(b); bgCache.clear(); },
  write(i, off, ptr, len) {
    const b = H[i];
    if (!pass && (off & 3) === 0 && (len & 3) === 0) {
      const o = staged(ptr, len);
      if (o >= 0) { enc.copyBufferToBuffer(stage, o, b, off, len); return; }
    }
    q.writeBuffer(b, off, mem.buffer, ptr, (len + 3) & ~3);
  },
  copyBB(s, so, d, dof, len) { endPass(); enc.copyBufferToBuffer(H[s], so, H[d], dof, (len + 3) & ~3); },
  tex(w, h, layers, mips, fmt, usage, cube) {
    return put(dev.createTexture({ size: [w, h, layers], mipLevelCount: mips, format: FMT[fmt], usage, dimension: '2d' }));
  },
  texDel(i) { const t = del(i); if (t) doomed.push(t); bgCache.clear(); },
  view(t, base, mips, cube) { return put(H[t].createView({ baseMipLevel: base, mipLevelCount: mips, dimension: cube ? 'cube' : '2d' })); },
  viewDel(i) { del(i); bgCache.clear(); },
  sampler(au, av, min, mag, aniso, maxLod) {
    const d = { addressModeU: au ? 'clamp-to-edge' : 'repeat', addressModeV: av ? 'clamp-to-edge' : 'repeat', minFilter: min ? 'linear' : 'nearest', magFilter: mag ? 'linear' : 'nearest', mipmapFilter: min ? 'linear' : 'nearest' };
    if (maxLod >= 0) d.lodMaxClamp = maxLod;
    if (aniso > 1 && min && mag) d.maxAnisotropy = aniso;
    return put(dev.createSampler(d));
  },
  writeTex(t, mip, layer, x, y, w, h, ptr, len, rowBytes) {
    const tex = H[t];
    if (!pass && (rowBytes & 255) === 0) {
      const o = staged(ptr, len);
      if (o >= 0) { enc.copyBufferToTexture({ buffer: stage, offset: o, bytesPerRow: rowBytes, rowsPerImage: h }, { texture: tex, mipLevel: mip, origin: [x, y, layer] }, [w, h, 1]); return; }
    }
    if (!pass) {
      const al = (rowBytes + 255) & ~255;
      const need = al * h;
      if (stageOff + need <= STAGE) {
        const tmp = new Uint8Array(need);
        const src = u8();
        for (let r = 0; r < h; r++) tmp.set(src.subarray(ptr + r * rowBytes, ptr + r * rowBytes + rowBytes), r * al);
        const o = stageOff;
        q.writeBuffer(stage, o, tmp);
        stageOff += (need + 255) & ~255;
        enc.copyBufferToTexture({ buffer: stage, offset: o, bytesPerRow: al, rowsPerImage: h }, { texture: tex, mipLevel: mip, origin: [x, y, layer] }, [w, h, 1]);
        return;
      }
    }
    q.writeTexture({ texture: tex, mipLevel: mip, origin: [x, y, layer] }, mem.buffer, { offset: ptr, bytesPerRow: rowBytes, rowsPerImage: h }, [w, h, 1]);
  },
  copyBT(b, off, rowBytes, rows, t, mip, layer, x, y, w, h) {
    endPass();
    enc.copyBufferToTexture({ buffer: H[b], offset: off, bytesPerRow: rowBytes, rowsPerImage: rows }, { texture: H[t], mipLevel: mip, origin: [x, y, layer] }, [w, h, 1]);
  },
  copyTT(s, d, mip, dx, dy, sx, sy, w, h) {
    endPass();
    enc.copyTextureToTexture({ texture: H[s], mipLevel: mip, origin: [sx, sy, 0] }, { texture: H[d], mipLevel: mip, origin: [dx, dy, 0] }, [w, h, 1]);
  },
  copyTB(t, mip, x, y, w, h, b, off, rowBytes) {
    endPass();
    enc.copyTextureToBuffer({ texture: H[t], mipLevel: mip, origin: [x, y, 0] }, { buffer: H[b], offset: off, bytesPerRow: rowBytes, rowsPerImage: h }, [w, h, 1]);
  },
  clear(views, colors, dview, depth, x, y, w, h) {
    endPass();
    gpu.pass(views, colors, dview, depth);
    endPass();
  },
  pass(views, colors, dview, depth) {
    endPass();
    const ca = [];
    for (let i = 0; i < views.length; i++) {
      if (views[i] <= 0) { ca.push(null); continue; }
      const c = colors[i];
      ca.push(c ? { view: H[views[i]], loadOp: 'clear', storeOp: 'store', clearValue: c } : { view: H[views[i]], loadOp: 'load', storeOp: 'store' });
    }
    const d = { colorAttachments: ca };
    if (dview > 0) {
      d.depthStencilAttachment = depth === null || depth === undefined || depth < 0 ? { view: H[dview], depthLoadOp: 'load', depthStoreOp: 'store' } : { view: H[dview], depthLoadOp: 'clear', depthStoreOp: 'store', depthClearValue: depth };
    }
    pass = enc.beginRenderPass(d);
  },
  endPass,
  passJ(j) {
    const d = JSON.parse(j);
    gpu.pass(d.c, d.cl, d.d, d.dc);
    if (d.s) pass.setScissorRect(d.s[0], d.s[1], d.s[2], d.s[3]);
  },
  bindM(pi, a, n) {
    const v = new Int32Array(mem.buffer, a, n * 3);
    const P = H[pi];
    let key = pi + ':';
    const offs = [];
    for (let i = 0; i < n; i++) {
      key += v[i * 3] + ',' + v[i * 3 + 2] + ';';
      const k = P.d.bgl[i].k;
      if (k === 'u' || k === 's') offs.push(v[i * 3 + 1]);
    }
    let bg = bgCache.get(key);
    if (!bg) {
      const entries = [];
      for (let i = 0; i < n; i++) {
        const e = P.d.bgl[i];
        if (e.k === 'u' || e.k === 's') entries.push({ binding: e.b, resource: { buffer: H[v[i * 3]], offset: 0, size: e.k === 'u' ? (v[i * 3 + 2] + 15) & ~15 : (v[i * 3 + 2] + 3) & ~3 } });
        else entries.push({ binding: e.b, resource: H[v[i * 3]] });
      }
      try { bg = dev.createBindGroup({ layout: P.bgl, entries }); } catch (e) { console.error('[gpu] bindgroup', P.d.name, e); return; }
      if (bgCache.size > 8192) bgCache.clear();
      bgCache.set(key, bg);
    }
    pass.setBindGroup(0, bg, offs);
  },
  doneMax: 0,
  chain: Promise.resolve(),
  ticket: 0,
  readTex(t, mip, x, y, w, h, bs, dst) {
    const row = w * bs, al = (row + 255) & ~255;
    const st = dev.createBuffer({ size: al * h, usage: GPUBufferUsage.MAP_READ | GPUBufferUsage.COPY_DST });
    endPass();
    enc.copyTextureToBuffer({ texture: H[t], mipLevel: mip, origin: [x, y, 0] }, { buffer: st, offset: 0, bytesPerRow: al, rowsPerImage: h }, [w, h, 1]);
    gpu.submit();
    const k = ++gpu.ticket;
    const p = st.mapAsync(GPUMapMode.READ).then(() => {
      const src = new Uint8Array(st.getMappedRange());
      const m = u8();
      for (let r = 0; r < h; r++) m.set(src.subarray(r * al, r * al + row), dst + r * row);
      st.unmap();
      st.destroy();
    }).catch(e => console.error('[gpu] readTex', e));
    gpu.chain = Promise.all([gpu.chain, p]).then(() => { gpu.doneMax = Math.max(gpu.doneMax, k); });
    return k;
  },
  module(code) {
    const m = dev.createShaderModule({ code });
    return put(m);
  },
  pipe(json, vs, fs) {
    const d = JSON.parse(json);
    const bgl = dev.createBindGroupLayout({ entries: d.bgl.map(e => {
      const v = GPUShaderStage.VERTEX | GPUShaderStage.FRAGMENT;
      if (e.k === 'u') return { binding: e.b, visibility: v, buffer: { type: 'uniform', hasDynamicOffset: true } };
      if (e.k === 's') return { binding: e.b, visibility: v, buffer: { type: 'read-only-storage', hasDynamicOffset: true } };
      if (e.k === 't') return { binding: e.b, visibility: v, texture: { sampleType: e.st || 'float', viewDimension: e.cube ? 'cube' : '2d' } };
      return { binding: e.b, visibility: v, sampler: { type: e.st === 'depth' ? 'non-filtering' : 'filtering' } };
    }) });
    const desc = {
      layout: dev.createPipelineLayout({ bindGroupLayouts: [bgl] }),
      vertex: { module: H[vs], entryPoint: 'main', buffers: d.vb },
      primitive: d.prim,
    };
    if (fs > 0) desc.fragment = { module: H[fs], entryPoint: 'main', targets: d.targets };
    if (d.depth) desc.depthStencil = d.depth;
    try {
      const p = dev.createRenderPipeline(desc);
      return put({ p, bgl, d });
    } catch (e) {
      console.error('[gpu] pipeline', d.name, e);
      return 0;
    }
  },
  pipeDel(i) { del(i); },
  setPipe(i) { pass.setPipeline(H[i].p); },
  setVB(slot, b, off) { pass.setVertexBuffer(slot, H[b], off); },
  setIB(b, fmt, off) { pass.setIndexBuffer(H[b], fmt ? 'uint32' : 'uint16', off); },
  bind(pi, key, ids, offs, sizes) {
    const P = H[pi];
    let bg = bgCache.get(key);
    if (!bg) {
      const entries = [];
      for (let i = 0; i < P.d.bgl.length; i++) {
        const e = P.d.bgl[i];
        if (e.k === 'u' || e.k === 's') entries.push({ binding: e.b, resource: { buffer: H[ids[i]], offset: 0, size: e.k === 'u' ? (sizes[i] + 15) & ~15 : (sizes[i] + 3) & ~3 } });
        else entries.push({ binding: e.b, resource: H[ids[i]] });
      }
      bg = dev.createBindGroup({ layout: P.bgl, entries });
      if (bgCache.size > 4096) bgCache.clear();
      bgCache.set(key, bg);
    }
    pass.setBindGroup(0, bg, offs);
  },
  scissor(x, y, w, h) { pass.setScissorRect(x, y, w, h); },
  draw(vc, ic, fv, fi) { pass.draw(vc, ic, fv, fi); },
  drawIdx(n, ic, fi, bv, finst) { pass.drawIndexed(n, ic, fi, bv, finst); },
  drawInd(b, off) { pass.drawIndirect(H[b], off); },
  drawIdxInd(b, off) { pass.drawIndexedIndirect(H[b], off); },
  submit() {
    endPass();
    q.submit([enc.finish()]);
    for (const o of doomed) o.destroy();
    doomed.length = 0;
    enc = dev.createCommandEncoder();
    stageOff = 0;
  },
  present(view, w, h) {
    const t = performance.now();
    gpu.fn = (gpu.fn || 0) + 1;
    if (!gpu.ft) gpu.ft = t;
    if (t - gpu.ft >= 2000) {
      const f = gpu.fn * 1000 / (t - gpu.ft);
      console.log('FPS ' + f.toFixed(2));
      postMessage({ fps: f });
      gpu.fn = 0;
      gpu.ft = t;
    }
    endPass();
    const tex = ctx.getCurrentTexture();
    const bg = dev.createBindGroup({ layout: blitBgl, entries: [{ binding: 0, resource: H[view] }, { binding: 1, resource: blitSampler }] });
    const p = enc.beginRenderPass({ colorAttachments: [{ view: tex.createView(), loadOp: 'clear', storeOp: 'store', clearValue: [0, 0, 0, 1] }] });
    p.setPipeline(blit);
    p.setBindGroup(0, bg);
    p.draw(3);
    p.end();
    q.submit([enc.finish()]);
    for (const o of doomed) o.destroy();
    doomed.length = 0;
    enc = dev.createCommandEncoder();
    stageOff = 0;
  },
  resize(w, h) {
    const c = ctx.canvas;
    if (c.width !== w || c.height !== h) { c.width = w; c.height = h; }
  },
  done: 0,
  pending: 0,
  fence() {
    const k = ++gpu.ticket;
    const p = q.onSubmittedWorkDone();
    gpu.chain = Promise.all([gpu.chain, p]).then(() => { gpu.doneMax = Math.max(gpu.doneMax, k); });
    return k;
  },
  readBack(b, off, len, dst) {
    const st = dev.createBuffer({ size: (len + 3) & ~3, usage: GPUBufferUsage.MAP_READ | GPUBufferUsage.COPY_DST });
    endPass();
    enc.copyBufferToBuffer(H[b], off, st, 0, (len + 3) & ~3);
    gpu.submit();
    const k = ++gpu.ticket;
    const p = st.mapAsync(GPUMapMode.READ).then(() => {
      u8().set(new Uint8Array(st.getMappedRange(0, (len + 3) & ~3)).subarray(0, len), dst);
      st.unmap();
      st.destroy();
    });
    gpu.chain = Promise.all([gpu.chain, p]).then(() => { gpu.doneMax = Math.max(gpu.doneMax, k); });
    return k;
  },
};
