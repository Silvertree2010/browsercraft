const H = new Map();
let root;
const dec = new TextDecoder(), enc = new TextEncoder();

async function dir(parts, create) {
  let d = root;
  for (const p of parts) d = await d.getDirectoryHandle(p, { create });
  return d;
}
function split(p) { return p.split('/').filter(Boolean); }
async function entry(p) {
  const s = split(p);
  if (!s.length) return { kind: 'directory', h: root };
  let parent;
  try { parent = await dir(s.slice(0, -1), false); } catch (e) { return null; }
  const n = s[s.length - 1];
  try { return { kind: 'file', h: await parent.getFileHandle(n) }; } catch (e) {}
  try { return { kind: 'directory', h: await parent.getDirectoryHandle(n) }; } catch (e) {}
  return null;
}
async function handle(p, create) {
  let e = H.get(p);
  if (e) { e.n++; return e.s; }
  const s = split(p);
  const parent = await dir(s.slice(0, -1), create);
  const fh = await parent.getFileHandle(s[s.length - 1], { create });
  let sh;
  for (let i = 0; ; i++) {
    try { sh = await fh.createSyncAccessHandle(); break; }
    catch (err) { if (i > 50) throw err; await new Promise(r => setTimeout(r, 20)); }
  }
  H.set(p, { s: sh, n: 1 });
  return sh;
}
async function tmp(p, f) {
  const e = H.get(p);
  if (e) return f(e.s);
  const sh = await handle(p, false);
  try { return f(sh); } finally { close(p); }
}
function close(p) {
  const e = H.get(p);
  if (!e) return;
  if (--e.n <= 0) {
    e.s.flush();
    if (/\/screenshots\/[^/]+\.png$/.test(p)) { const b = new Uint8Array(e.s.getSize()); e.s.read(b, { at: 0 }); postMessage({ shot: p.split('/').pop(), data: b }, [b.buffer]); }
    e.s.close(); H.delete(p);
  }
}

let ctl, data, man = new Map(), dirs = new Map(), base = 'data/';
const fetched = new Set();
function addDir(p) {
  const s = split(p);
  for (let i = 0; i < s.length; i++) {
    const d = '/' + s.slice(0, i).join('/');
    const k = i === 0 ? '/' : d;
    let c = dirs.get(k);
    if (!c) dirs.set(k, c = new Set());
    c.add(s[i]);
  }
}
async function ensure(p) {
  if (!man.has(p) || fetched.has(p)) return;
  const e = await entry(p);
  if (e && e.kind === 'file' && (await e.h.getFile()).size === man.get(p)) { fetched.add(p); return; }
  const r = await fetch(base + p.slice(4));
  if (!r.ok) throw new Error('fetch ' + p + ' ' + r.status);
  const buf = new Uint8Array(await r.arrayBuffer());
  const s = split(p);
  const par = await dir(s.slice(0, -1), true);
  const fh = await par.getFileHandle(s[s.length - 1], { create: true });
  const sh = await fh.createSyncAccessHandle();
  sh.truncate(0);
  sh.write(buf, { at: 0 });
  sh.flush();
  sh.close();
  fetched.add(p);
}

const JAR = new Map(), jarCache = new Map();
let jarH, jarCacheSize = 0;
function u16(b, o) { return b[o] | (b[o + 1] << 8); }
function u32(b, o) { return (b[o] | (b[o + 1] << 8) | (b[o + 2] << 16) | (b[o + 3] << 24)) >>> 0; }
async function openJar(j) {
  const cache = await dir(['cache'], true);
  const name = 'jar-' + j.hash + '.zip';
  let fh;
  try { fh = await cache.getFileHandle(name); if ((await fh.getFile()).size !== j.size) fh = null; } catch { fh = null; }
  if (!fh) {
    for await (const k of cache.keys()) if (k.startsWith('jar-')) await cache.removeEntry(k).catch(() => {});
    const r = await fetch(base + j.url);
    const total = +r.headers.get('content-length') || j.size;
    const rd = r.body.getReader();
    const parts = [];
    let got = 0;
    for (;;) {
      const { done, value } = await rd.read();
      if (done) break;
      parts.push(value);
      got += value.length;
      postMessage({ progress: { text: 'Loading game files …', p: got / total } });
    }
    fh = await cache.getFileHandle(name, { create: true });
    const sh = await fh.createSyncAccessHandle();
    sh.truncate(0);
    let o = 0;
    for (const v of parts) { sh.write(v, { at: o }); o += v.length; }
    sh.flush();
    sh.close();
  }
  jarH = await fh.createSyncAccessHandle({ mode: 'read-only' });
  const size = jarH.getSize();
  const tail = new Uint8Array(Math.min(size, 65557));
  jarH.read(tail, { at: size - tail.length });
  let e = tail.length - 22;
  while (e >= 0 && u32(tail, e) !== 0x06054b50) e--;
  const cdSize = u32(tail, e + 12), cdOff = u32(tail, e + 16);
  const cd = new Uint8Array(cdSize);
  jarH.read(cd, { at: cdOff });
  const td = new TextDecoder();
  for (let i = 0; i < cdSize;) {
    const method = u16(cd, i + 10), csize = u32(cd, i + 20), usize = u32(cd, i + 24);
    const nl = u16(cd, i + 28), xl = u16(cd, i + 30), cl = u16(cd, i + 32), lo = u32(cd, i + 42);
    const nm = td.decode(cd.subarray(i + 46, i + 46 + nl));
    if (!nm.endsWith('/')) { const k = '/mc/jar/' + nm; JAR.set(k, { method, csize, usize, lo }); addDir(k); }
    i += 46 + nl + xl + cl;
  }
}
async function jarData(p) {
  let d = jarCache.get(p);
  if (d) return d;
  const e = JAR.get(p);
  const lh = new Uint8Array(30);
  jarH.read(lh, { at: e.lo });
  const start = e.lo + 30 + u16(lh, 26) + u16(lh, 28);
  const c = new Uint8Array(e.csize);
  jarH.read(c, { at: start });
  if (e.method === 0) d = c;
  else d = new Uint8Array(await new Response(new Blob([c]).stream().pipeThrough(new DecompressionStream('deflate-raw'))).arrayBuffer());
  if (jarCacheSize > (96 << 20)) { jarCache.clear(); jarCacheSize = 0; }
  jarCache.set(p, d);
  jarCacheSize += d.length;
  return d;
}

async function op(o, p, a, b, n) {
  if (JAR.has(p)) {
    if (o === 1) return a ? JAR.get(p).usize : 1;
    if (o === 8 || o === 9) return 1;
    if (o === 11) return 0;
    if (o === 2) {
      const d = await jarData(p);
      const k = Math.max(0, Math.min(n, d.length - a));
      data.set(d.subarray(a, a + k));
      return k;
    }
    return -1;
  }
  if (o === 1 && man.has(p)) return a ? man.get(p) : 1;
  if (o === 1 && dirs.has(p)) { if (a) return 0; return 2; }
  if ((o === 2 || o === 8 || o === 11) && man.has(p)) await ensure(p);
  switch (o) {
    case 1: {
      const e = await entry(p);
      if (!e) return a ? -1 : 0;
      if (!a) return e.kind === 'file' ? 1 : 2;
      if (e.kind !== 'file') return 0;
      const h = H.get(p);
      if (h) return h.s.getSize();
      return (await e.h.getFile()).size;
    }
    case 2: return tmp(p, s => s.read(new Uint8Array(data.buffer, data.byteOffset, n), { at: a }));
    case 3: return tmp(p, s => s.write(new Uint8Array(data.buffer, data.byteOffset, n), { at: a }));
    case 4: return tmp(p, s => { s.truncate(a); return 1; });
    case 5: { const s = split(p); try { const par = await dir(s.slice(0, -1), true); try { await par.getFileHandle(s[s.length - 1]); return 0; } catch (e) {} await par.getDirectoryHandle(s[s.length - 1], { create: true }); return 1; } catch (e) { return 0; } }
    case 6: { const s = split(p); try { const par = await dir(s.slice(0, -1), false); await par.removeEntry(s[s.length - 1]); return 1; } catch (e) { return 0; } }
    case 7: {
      const to = dec.decode(data.slice(0, n));
      const e = await entry(p);
      if (!e) return 0;
      const ts = split(to);
      const par = await dir(ts.slice(0, -1), true);
      if (e.h.move) { await e.h.move(par, ts[ts.length - 1]); return 1; }
      return 0;
    }
    case 8: try { await handle(p, a > 0); return 1; } catch (e) { postMessage({ log: 'IOFAIL open ' + p + ' ' + (e && e.name) + ' ' + (e && e.message) }); return -1; }
    case 9: if (p.endsWith('session.lock')) postMessage({ log: 'IO close ' + p + ' n=' + (H.get(p) ? H.get(p).n : 'none') }); close(p); return 1;
    case 10: { const s = split(p); try { const par = await dir(s.slice(0, -1), true); try { await par.getFileHandle(s[s.length - 1]); return 0; } catch (e) {} await par.getFileHandle(s[s.length - 1], { create: true }); return 1; } catch (e) { return 0; } }
    case 11: { const e = await entry(p); if (!e || e.kind !== 'file') return 0; return (await e.h.getFile()).lastModified; }
    case 12: {
      const e = await entry(p);
      const vd = dirs.get(p);
      if ((!e || e.kind !== 'directory') && !vd) return -1;
      const names = new Set(vd || []);
      if (e && e.kind === 'directory') for await (const k of e.h.keys()) names.add(k);
      const b = enc.encode([...names].join('/'));
      data.set(b.subarray(0, data.length));
      return b.length;
    }
  }
  return -1;
}

let q = Promise.resolve();
onmessage = async (ev) => {
  const m = ev.data;
  if (m.init) {
    root = await navigator.storage.getDirectory();
    if (m.manifest) {
      base = m.base || base;
      const j = await (await fetch(m.manifest)).json();
      for (const [k, v] of Object.entries(j.files)) { man.set(k, v); addDir(k); }
      if (j.jar) await openJar(j.jar);
    }
    ctl = new Int32Array(m.sab, 0, 4);
    data = new Uint8Array(m.sab, 16);
    postMessage({ ready: true });
    return;
  }
  q = q.then(async () => {
    let r;
    try { r = await op(m.o, m.p, m.a, m.b, m.n); } catch (e) { console.warn('io', m, e); r = -1; }
    const f = new Float64Array(m.sab, 0, 2);
    f[1] = r;
    Atomics.store(ctl, 0, 1);
    Atomics.notify(ctl, 0);
  });
};
