const SC = {};
'ABCDEFGHIJKLMNOPQRSTUVWXYZ'.split('').forEach((c, i) => SC['Key' + c] = 4 + i);
'123456789'.split('').forEach((c, i) => SC['Digit' + c] = 30 + i);
Object.assign(SC, { Digit0: 39, Enter: 40, Escape: 41, Backspace: 42, Tab: 43, Space: 44, Minus: 45, Equal: 46, BracketLeft: 47, BracketRight: 48,
  Backslash: 49, Semicolon: 51, Quote: 52, Backquote: 53, Comma: 54, Period: 55, Slash: 56, CapsLock: 57, PrintScreen: 70, ScrollLock: 71, Pause: 72,
  Insert: 73, Home: 74, PageUp: 75, Delete: 76, End: 77, PageDown: 78, ArrowRight: 79, ArrowLeft: 80, ArrowDown: 81, ArrowUp: 82, NumLock: 83,
  NumpadDivide: 84, NumpadMultiply: 85, NumpadSubtract: 86, NumpadAdd: 87, NumpadEnter: 88, Numpad0: 98, NumpadDecimal: 99, IntlBackslash: 100,
  ContextMenu: 101, ControlLeft: 224, ShiftLeft: 225, AltLeft: 226, MetaLeft: 227, ControlRight: 228, ShiftRight: 229, AltRight: 230, MetaRight: 231 });
for (let i = 1; i <= 12; i++) SC['F' + i] = 57 + i;
for (let i = 13; i <= 24; i++) SC['F' + i] = 91 + i;
for (let i = 1; i <= 9; i++) SC['Numpad' + i] = 88 + i;

function mods(e) {
  return (e.shiftKey ? 0x1 : 0) | (e.ctrlKey ? 0x40 : 0) | (e.altKey ? 0x100 : 0) | (e.metaKey ? 0x400 : 0) |
    (e.getModifierState && e.getModifierState('CapsLock') ? 0x2000 : 0) | (e.getModifierState && e.getModifierState('NumLock') ? 0x1000 : 0);
}

import { audio } from './audio.js';

export function boot(canvas, opts = {}) {
  const au = audio();
  addEventListener('pointerdown', () => au.resume());
  addEventListener('keydown', () => au.resume());
  const off = canvas.transferControlToOffscreen();
  const w = new Worker(opts.worker || 'worker.js');
  let grab = false, wantGrab = false, textOn = false;
  const dpr = () => window.devicePixelRatio || 1;
  const size = () => ({ w: Math.max(1, Math.round(canvas.clientWidth * dpr())), h: Math.max(1, Math.round(canvas.clientHeight * dpr())), dpr: dpr(), sw: Math.round(screen.width * dpr()), sh: Math.round(screen.height * dpr()) });
  const send = (m) => w.postMessage(m);
  let hz = 60;
  { let n = 0, t0 = 0; const f = (t) => { if (!t0) t0 = t; if (++n < 60) requestAnimationFrame(f); else { hz = Math.round(60000 / (t - t0) / 10) * 10 || 60; send({ ev: 'hz', hz }); } }; requestAnimationFrame(f); }
  w.postMessage({ start: true, canvas: off, win: size(), args: opts.args || [], wasm: opts.wasm, runtime: opts.runtime }, [off]);
  let sw = null;
  const pq = [];
  w.onmessage = (e) => {
    const m = e.data;
    if (m.au) { au.handle(m); return; }
    if (m.k === 'srv') {
      const go = () => {
        const s = sw = new Worker(opts.worker || 'worker.js');
        s.done = new Promise(r => { s.fin = r; });
        s.onmessage = (x) => {
          const d = x.data;
          if (d.k === 'srvexit') { s.fin(); s.terminate(); if (sw === s) sw = null; }
          else if (d.status === 'crash') { s.fin(); if (opts.onmessage) opts.onmessage({ status: 'server crash', error: d.error }); }
        };
        s.postMessage({ start: true, server: true, args: ['--bc-server', m.w, m.u, m.c ? '1' : '0', m.x || ''], wasm: opts.wasm, runtime: opts.runtime });
        for (const p of pq.splice(0)) s.postMessage({ pipe: true, port: p }, [p]);
      };
      const old = sw;
      sw = null;
      if (old) Promise.race([old.done, new Promise(r => setTimeout(r, 20000))]).then(() => { old.terminate(); go(); });
      else go();
      return;
    }
    if (m.shot) { console.log('SHOT ' + m.shot + ' ' + m.data.length); const a = document.createElement('a'); a.href = URL.createObjectURL(new Blob([m.data], { type: 'image/png' })); a.download = m.shot; a.click(); setTimeout(() => URL.revokeObjectURL(a.href), 10000); return; }
    if (m.k === 'pause') { if (sw) sw.postMessage({ pause: !!m.n }); return; }
    if (m.k === 'pipe') { if (sw) sw.postMessage({ pipe: true, port: m.port }, [m.port]); else pq.push(m.port); return; }
    if (m.k === 'title') document.title = m.a;
    else if (m.k === 'grab') { wantGrab = !!m.n; if (wantGrab) canvas.requestPointerLock?.()?.catch?.(() => {}); else if (document.pointerLockElement) document.exitPointerLock(); }
    else if (m.k === 'cursor') canvas.style.cursor = ['default', 'text', 'wait', 'crosshair', 'progress', 'nwse-resize', 'nesw-resize', 'ew-resize', 'ns-resize', 'move', 'not-allowed', 'pointer'][m.n] || 'default';
    else if (m.k === 'clip') navigator.clipboard?.writeText(m.a).catch(() => {});
    else if (m.k === 'url') window.open(m.a, '_blank', 'noopener');
    else if (m.k === 'text') textOn = !!m.n;
    else if (m.k === 'fullscreen') { if (m.n) canvas.requestFullscreen?.().catch(() => {}); else if (document.fullscreenElement) document.exitFullscreen(); }
    if (opts.onmessage) opts.onmessage(m);
  };
  const ev = (t, a = []) => send({ ev: 'in', t, a });
  addEventListener('keydown', (e) => {
    const sc = SC[e.code];
    if (sc === undefined) return;
    if (!(e.ctrlKey && (e.code === 'KeyV' || e.code === 'KeyC'))) e.preventDefault();
    const key = e.key.length === 1 ? e.key.toLowerCase().codePointAt(0) : 0;
    ev(0x300, [sc, key, mods(e), e.repeat ? 1 : 0]);
    if (textOn && e.key.length === 1 && !e.ctrlKey && !e.metaKey) send({ ev: 'text', s: e.key });
  });
  addEventListener('keyup', (e) => {
    const sc = SC[e.code];
    if (sc === undefined) return;
    e.preventDefault();
    const key = e.key.length === 1 ? e.key.toLowerCase().codePointAt(0) : 0;
    ev(0x301, [sc, key, mods(e), 0]);
  });
  addEventListener('paste', (e) => { const s = e.clipboardData.getData('text'); send({ ev: 'clip', s }); });
  canvas.addEventListener('mousemove', (e) => { const r = canvas.getBoundingClientRect(); ev(0x400, [(e.clientX - r.left) * dpr(), (e.clientY - r.top) * dpr(), e.movementX * dpr(), e.movementY * dpr()]); });
  canvas.addEventListener('mousedown', (e) => { e.preventDefault(); if (wantGrab && !document.pointerLockElement) canvas.requestPointerLock?.()?.catch?.(() => {}); ev(0x401, [[1, 2, 3, 4, 5][e.button] || 1]); });
  addEventListener('mouseup', (e) => ev(0x402, [[1, 2, 3, 4, 5][e.button] || 1]));
  canvas.addEventListener('contextmenu', (e) => e.preventDefault());
  canvas.addEventListener('wheel', (e) => { e.preventDefault(); const k = e.deltaMode === 1 ? 1 : 1 / 100; ev(0x403, [-e.deltaX * k, -e.deltaY * k]); }, { passive: false });
  addEventListener('focus', () => ev(0x20E));
  addEventListener('blur', () => ev(0x20F));
  document.addEventListener('pointerlockchange', () => { grab = document.pointerLockElement === canvas; });
  new ResizeObserver(() => { const s = size(); send({ ev: 'win', win: s }); ev(0x207, [s.w, s.h]); ev(0x206, [s.w, s.h]); }).observe(canvas);
  return w;
}
