export function audio() {
  let ctx = null;
  const bufs = new Map(), srcs = new Map();
  const pending = [];
  let lis = { p: [0, 0, 0], f: [0, 0, -1], u: [0, 1, 0], g: 1 };
  function c() {
    if (!ctx) {
      ctx = new AudioContext({ latencyHint: 'interactive' });
      ctx.listener.positionX && 0;
      master = ctx.createGain();
      master.connect(ctx.destination);
    }
    return ctx;
  }
  let master;
  function src(id) {
    let s = srcs.get(id);
    if (!s) {
      s = { gain: 1, pitch: 1, loop: false, rel: false, pos: [0, 0, 0], ref: 1, roll: 1, max: 1e9, buffer: 0, q: [], nodes: [], playing: false, next: 0 };
      srcs.set(id, s);
    }
    return s;
  }
  function chain(s) {
    const x = c();
    const g = x.createGain();
    g.gain.value = s.gain;
    let out = g;
    if (!s.rel) {
      const p = x.createPanner();
      p.panningModel = 'HRTF';
      p.distanceModel = 'linear';
      p.refDistance = s.ref;
      p.maxDistance = s.max;
      p.rolloffFactor = s.roll;
      p.positionX.value = s.pos[0]; p.positionY.value = s.pos[1]; p.positionZ.value = s.pos[2];
      g.connect(p);
      out = p;
      s.panner = p;
    } else s.panner = null;
    out.connect(master);
    s.gn = g;
    return g;
  }
  function startBuf(s, id, at) {
    const b = bufs.get(id);
    if (!b) return at;
    const x = c();
    const n = x.createBufferSource();
    n.buffer = b;
    n.playbackRate.value = s.pitch;
    n.loop = s.loop && !s.q.length;
    n.connect(s.gn);
    const t = Math.max(at, x.currentTime);
    n.start(t);
    s.nodes.push(n);
    n.onended = () => { const i = s.nodes.indexOf(n); if (i >= 0) s.nodes.splice(i, 1); };
    return t + b.duration / s.pitch;
  }
  function stop(s) {
    for (const n of s.nodes) { try { n.stop(); } catch (e) {} }
    s.nodes = [];
    s.playing = false;
    s.q = [];
  }
  function lisApply() {
    if (!ctx) return;
    const L = ctx.listener;
    if (L.positionX) {
      L.positionX.value = lis.p[0]; L.positionY.value = lis.p[1]; L.positionZ.value = lis.p[2];
      L.forwardX.value = lis.f[0]; L.forwardY.value = lis.f[1]; L.forwardZ.value = lis.f[2];
      L.upX.value = lis.u[0]; L.upY.value = lis.u[1]; L.upZ.value = lis.u[2];
    } else {
      L.setPosition(...lis.p);
      L.setOrientation(...lis.f, ...lis.u);
    }
    master.gain.value = lis.g;
  }
  function handle(m) {
    if (m.au === 'buf') {
      const x = c();
      const b = x.createBuffer(m.data.length, m.data[0].length || 1, m.hz);
      m.data.forEach((d, i) => b.copyToChannel(d, i));
      bufs.set(m.id, b);
      return;
    }
    const s = m.s ? src(m.s) : null;
    switch (m.au) {
      case 'si':
        if (m.p === 0x1007) s.loop = !!m.v[0];
        else if (m.p === 0x1009) { s.buffer = m.v[0]; s.q = []; }
        else if (m.p === 0x202) s.rel = !!m.v[0];
        break;
      case 'sf':
        if (m.p === 0x100A) { s.gain = m.v[0]; if (s.gn) s.gn.gain.value = s.gain; }
        else if (m.p === 0x1003) s.pitch = m.v[0];
        else if (m.p === 0x1020) s.ref = m.v[0];
        else if (m.p === 0x1021) s.roll = m.v[0];
        else if (m.p === 0x1023) s.max = m.v[0];
        break;
      case 's3':
        if (m.p === 0x1004) { s.pos = m.v.slice(0, 3); if (s.panner) { s.panner.positionX.value = s.pos[0]; s.panner.positionY.value = s.pos[1]; s.panner.positionZ.value = s.pos[2]; } }
        break;
      case 'l':
        if (m.p === 0x1004) lis.p = m.v.slice(0, 3);
        else if (m.p === 0x100F) { lis.f = m.v.slice(0, 3); lis.u = m.v.slice(3, 6); }
        lisApply();
        break;
      case 'lf':
        if (m.p === 0x100A) { lis.g = m.v[0]; lisApply(); }
        break;
      case 'play': {
        stop(s);
        chain(s);
        lisApply();
        s.playing = true;
        let t = c().currentTime;
        if (s.buffer) t = startBuf(s, s.buffer, t);
        for (const b of s.pq || []) t = startBuf(s, b, t);
        s.pq = [];
        s.next = t;
        break;
      }
      case 'queue':
        if (s.playing) s.next = startBuf(s, m.p, s.next);
        else (s.pq = s.pq || []).push(m.p);
        break;
      case 'pause':
      case 'stop':
        stop(s);
        break;
    }
  }
  return {
    handle(m) { if (ctx || m.au === 'buf') handle(m); else { pending.push(m); } },
    resume() { c(); if (ctx.state !== 'running') ctx.resume(); while (pending.length) handle(pending.shift()); },
  };
}
