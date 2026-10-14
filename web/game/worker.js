self.lat = {
  st: {}, t: 0,
  now() { return performance.timeOrigin + performance.now(); },
  add(k, v) { const s = this.st[k] || (this.st[k] = []); s.push(v); },
  rx(e, d) { const u = new Uint8Array(d[0]); u.rt = this.now(); this.add('tx', u.rt - d[1]); e.q.push(u); },
  dump() {
    const o = [];
    for (const k in this.st) { const a = this.st[k].sort((x, y) => x - y); if (!a.length) continue; o.push(k + ' n' + a.length + ' p50 ' + a[a.length >> 1].toFixed(1) + ' p95 ' + a[Math.floor(a.length * .95)].toFixed(1) + ' max ' + a[a.length - 1].toFixed(1)); this.st[k] = []; }
    if (o.length && self.latOn) console.log('LAT ' + o.join(' | '));
  },
};
const lat = self.lat;
{ let last = lat.now(); setInterval(() => { const n = lat.now(); lat.add('loop', n - last - 50); last = n; }, 50); setInterval(() => lat.dump(), 5000); }
self.addEventListener('error', e => console.log('CRASH ' + (e.error && e.error.stack || e.message)));
self.addEventListener('unhandledrejection', e => console.log('CRASH ' + (e.reason && e.reason.stack || e.reason)));
Error.stackTraceLimit = 60;
const sab = new SharedArrayBuffer(16 + (2 << 20));
const ctl = new Int32Array(sab, 0, 4);
const f64 = new Float64Array(sab, 0, 2);
const data = new Uint8Array(sab, 16);
const dec = new TextDecoder();
let io, mem, teavm;

function u8() { return new Uint8Array(mem.buffer); }

const bc = self.bc = {
  io(o, p, a, b, m, n) {
    if (o === 3) data.set(u8().subarray(m, m + n));
    if (o === 7) data.set(u8().subarray(m, m + n));
    Atomics.store(ctl, 0, 0);
    io.postMessage({ o, p, a, b, n, sab });
    Atomics.wait(ctl, 0, 0);
    const r = f64[1];
    if (o === 2 && r > 0) u8().set(data.subarray(0, r), m);
    return r;
  },
  ioList(p) {
    const r = bc.io(12, p, 0, 0, 0, 0);
    if (r < 0) return null;
    return dec.decode(data.slice(0, r));
  },
  win: { w: 854, h: 480, dpr: 1, hz: 60, sw: 1920, sh: 1080 },
  q: [],
  text: '',
  clip: '',
  ev(a) {
    const e = bc.q.shift();
    if (!e) return 0;
    if (e.t === 0x303) bc.text = e.s;
    const f = new Float64Array(mem.buffer, a, 16);
    for (let i = 0; i < e.a.length; i++) f[i] = e.a[i];
    return e.t;
  },
  post(m) { postMessage(m); },
  pipe: {
    q: [], cb: null,
    add(pt) { this.q.push(pt); this.flush(); },
    listen(cb) { this.cb = cb; this.flush(); },
    flush() { while (this.cb && this.q.length) this.cb(bc.ws.adopt(this.q.shift())); },
  },
  ws: {
    n: 1, m: new Map(),
    port(pt) {
      return { readyState: 0, send(b) { pt.postMessage([b.buffer, lat.now()], [b.buffer]); }, close() { this.readyState = 3; try { pt.postMessage(null); } catch {} pt.close(); } };
    },
    adopt(pt) {
      const id = this.n++;
      const s = this.port(pt);
      s.readyState = 1;
      const e = { s, q: [], off: 0, cb: null, dead: false };
      this.m.set(id, e);
      pt.onmessage = (ev) => {
        const d = ev.data;
        if (d === null) { s.readyState = 3; if (e.cb) e.cb(3); else e.dead = true; }
        else { lat.rx(e, d); if (e.cb) e.cb(2); }
      };
      pt.postMessage('open');
      return id;
    },
    bind(id, cb) {
      const e = this.m.get(id);
      if (!e) return;
      e.cb = cb;
      if (e.q.length) cb(2);
      if (e.dead) cb(3);
    },
    open(h, p, cb) {
      const id = this.n++;
      if (h === 'bc-local') {
        const ch = new MessageChannel(), pt = ch.port1;
        const s = this.port(pt);
        const e = { s, q: [], off: 0 };
        this.m.set(id, e);
        pt.onmessage = (ev) => {
          const d = ev.data;
          if (d === 'open') { s.readyState = 1; cb(1); }
          else if (d === null) { s.readyState = 3; cb(3); }
          else { lat.rx(e, d); cb(2); }
        };
        postMessage({ k: 'pipe', port: ch.port2 }, [ch.port2]);
        return id;
      }
      const u = new URL('/ws?h=' + encodeURIComponent(h) + '&p=' + p, self.location.origin);
      u.protocol = u.protocol === 'https:' ? 'wss:' : 'ws:';
      const s = new WebSocket(u);
      s.binaryType = 'arraybuffer';
      const e = { s, q: [], off: 0 };
      this.m.set(id, e);
      s.onopen = () => cb(1);
      s.onmessage = (ev) => { e.q.push(new Uint8Array(ev.data)); cb(2); };
      s.onclose = () => cb(3);
      s.onerror = () => {};
      return id;
    },
    send(id, a, n) { const e = this.m.get(id); if (e && e.s.readyState === 1) e.s.send(new Uint8Array(mem.buffer, a, n).slice()); },
    next(id, a, n) {
      const e = this.m.get(id);
      if (!e || !e.q.length) return 0;
      const b = e.q[0];
      if (e.off === 0 && b.rt) { lat.add('pick', lat.now() - b.rt); b.rt = 0; }
      const k = Math.min(n, b.length - e.off);
      new Uint8Array(mem.buffer, a, k).set(b.subarray(e.off, e.off + k));
      e.off += k;
      if (e.off >= b.length) { e.q.shift(); e.off = 0; }
      return k;
    },
    close(id) { const e = this.m.get(id); if (e) { try { e.s.close(); } catch {} this.m.delete(id); } },
  },
  abuf(id, p, n, ch, bps, hz) {
    const frames = Math.floor(n / (ch * bps));
    const out = [];
    for (let c = 0; c < ch; c++) out.push(new Float32Array(frames));
    if (bps === 2) {
      const v = new Int16Array(mem.buffer, p, frames * ch);
      for (let i = 0; i < frames; i++) for (let c = 0; c < ch; c++) out[c][i] = v[i * ch + c] / 32768;
    } else {
      const v = new Uint8Array(mem.buffer, p, frames * ch);
      for (let i = 0; i < frames; i++) for (let c = 0; c < ch; c++) out[c][i] = (v[i * ch + c] - 128) / 128;
    }
    postMessage({ au: 'buf', id, hz, data: out }, out.map(x => x.buffer));
  },
  log(s) { console.log(s); },
  err(s) { console.error(s); },
  now() { return performance.now(); },
};

async function start(m) {
  io = new Worker('io.js');
  await new Promise(r => { io.onmessage = e => { if (e.data.ready) r(); else if (e.data.log) console.log(e.data.log); else if (e.data.progress || e.data.shot) postMessage(e.data, e.data.shot ? [e.data.data.buffer] : []); }; io.postMessage({ init: true, sab, manifest: m.manifest || '/data/manifest.json', base: m.base || '/data/' }); });
  bc.args = m.args || [];
  let G = null;
  if (!m.server) {
    bc.canvas = m.canvas;
    G = await import('./gpu.js');
    bc.gi = await G.init(m.canvas);
    bc.g = G.gpu;
    const nw = await WebAssembly.instantiateStreaming(fetch('naga.wasm'), {});
    const nx = nw.instance.exports;
    const te = new TextEncoder();
    bc.naga = (src, st) => {
      const b = te.encode(src);
      const p = nx.alloc(b.length);
      new Uint8Array(nx.memory.buffer, p, b.length).set(b);
      const r = nx.conv(p, b.length, st);
      const rp = Number(r >> 32n), rl = Number(r & 0xffffffffn);
      const out = dec.decode(new Uint8Array(nx.memory.buffer, rp, rl));
      nx.free(rp, rl);
      nx.free(p, b.length);
      return out;
    };
  }
  importScripts(m.runtime || 'mc.wasm-runtime.js');
  mem = new WebAssembly.Memory({ initial: 512, maximum: 32768 });
  if (G) G.setMemory(mem);
  teavm = await TeaVM.wasmGC.load(m.wasm || 'mc.wasm', {
    stackDeobfuscator: { enabled: false },
    memory: { external: mem, maxSize: 2147483647 },
  });
  postMessage({ status: 'running' });
  try { teavm.exports.main(bc.args); } catch (e) { console.log('CRASH ' + (e && e.stack)); postMessage({ status: 'crash', error: String(e && e.stack || e) }); }
}

onmessage = (e) => {
  const m = e.data;
  if (m.start) {
    if (m.server) { const L = console.log.bind(console); console.log = (...a) => L('[S]', ...a); }
    if (m.win) Object.assign(bc.win, m.win);
    start(m);
    return;
  }
  if (m.pipe) { bc.pipe.add(m.port); return; }
  if (m.pause !== undefined) { bc.paused = m.pause; return; }
  if (m.ev === 'in') bc.q.push({ t: m.t, a: m.a });
  else if (m.ev === 'text') bc.q.push({ t: 0x303, a: [], s: m.s });
  else if (m.ev === 'win') Object.assign(bc.win, m.win);
  else if (m.ev === 'hz') bc.win.hz = m.hz;
  else if (m.ev === 'clip') bc.clip = m.s;
  else if (bc.onmsg) bc.onmsg(m);
};
