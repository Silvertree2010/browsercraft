import puppeteer from 'puppeteer-core';
const url = process.argv[2];
const secs = +process.argv[3];
const bps = process.argv.slice(4).map(x => parseInt(x, 16));
const b = await puppeteer.launch({
  executablePath: process.env.CHROME || decodeURIComponent(new URL('b/chrome-mac-arm64/Google Chrome for Testing.app/Contents/MacOS/Google Chrome for Testing', import.meta.url).pathname),
  headless: 'new',
  args: ['--enable-unsafe-webgpu', '--enable-features=Vulkan,WebGPU', '--use-angle=metal', '--window-size=1280,720', '--no-first-run'],
  defaultViewport: { width: 1280, height: 720 },
  protocolTimeout: 0,
});
const p = await b.newPage();
const log = (s) => console.log(new Date().toISOString().slice(11, 19), s);
p.on('console', m => log('[page] ' + m.text()));
const cdp = await p.target().createCDPSession();
const conn = cdp.connection();
cdp.on('Target.attachedToTarget', async ev => {
  if (ev.targetInfo.type !== 'worker') return;
  const s = conn.session(ev.sessionId);
  s.on('Runtime.exceptionThrown', e => log('[exc] ' + (e.exceptionDetails.exception?.description || e.exceptionDetails.text).split('\n').slice(0, +(process.env.EXN || 40)).join('\n')));
  s.on('Runtime.consoleAPICalled', e => log('[w] ' + e.args.map(a => a.value ?? a.description).join(' ')));
  s.on('Debugger.scriptParsed', async e => {
    if (!e.url.endsWith('.wasm')) return;
    log('wasm parsed ' + e.scriptId);
    for (const o of bps) {
      try { const r = await s.send('Debugger.setBreakpoint', { location: { scriptId: e.scriptId, lineNumber: 0, columnNumber: o } }); log('bp 0x' + o.toString(16) + ' -> ' + r.actualLocation.columnNumber.toString(16)); }
      catch (x) { log('bp fail 0x' + o.toString(16) + ' ' + x.message); }
    }
  });
  s.on('Debugger.paused', async e => {
    const f = e.callFrames;
    if (e.reason === 'exception') log('EXC ' + (e.data?.description || '').split('\n')[0]);
    if (process.env.CALLER) log('CALLER ' + f.slice(0, 4).map(c => c.functionName + '@0x' + c.location.columnNumber.toString(16)).join(' < '));
    log('HIT 0x' + f[0].location.columnNumber.toString(16) + ' | ' + f.slice(0, +(process.env.FR || 8)).map(c => c.functionName).join(' < '));
    const dump = async (id, depth) => {
      const q = await s.send('Runtime.getProperties', { objectId: id, ownProperties: true });
      const out = [];
      for (const y of q.result) {
        let d = y.value?.description ?? y.value?.value;
        if (y.value?.objectId && /Array<&[CB]>/.test(d)) {
          const q1 = await s.send('Runtime.getProperties', { objectId: y.value.objectId, ownProperties: true });
          const vv = q1.result.find(z => z.name === 'value');
          let arr = vv?.value?.objectId;
          if (arr) {
            const q2 = await s.send('Runtime.getProperties', { objectId: arr, ownProperties: true });
            const dt = q2.result.find(z => z.name.includes('data'));
            if (dt?.value?.objectId) {
              const q3 = await s.send('Runtime.getProperties', { objectId: dt.value.objectId, ownProperties: true });
              const q4v = q3.result.find(z => z.name === 'value');
              const src = q4v?.value?.objectId ? (await s.send('Runtime.getProperties', { objectId: q4v.value.objectId, ownProperties: true })).result : q3.result;
              const cs = [];
              for (const z of src.filter(z => /^\d+$/.test(z.name)).slice(0, /&B/.test(d) ? 16 : 100000)) {
                let v = z.value?.value;
                if (typeof v !== 'number' && z.value?.objectId) {
                  const q5 = await s.send('Runtime.getProperties', { objectId: z.value.objectId, ownProperties: true });
                  v = q5.result.find(w => w.name === 'value')?.value?.value;
                }
                cs.push(typeof v === 'number' ? v : null);
              }
              d += /&B/.test(d) ? ' [' + cs.join(',') + ']' : ' "' + cs.map(c => c == null || isNaN(c) ? '?' : String.fromCharCode(c)).join('') + '"';
            }
          }
        }
        if (y.value?.objectId && depth > 0) d += ' {' + (await dump(y.value.objectId, depth - 1)) + '}';
        out.push(y.name + '=' + d);
      }
      return out.join(', ');
    };
    const depth = +(process.env.DEPTH || 1);
    if (!process.env.NOLOC) for (const sc of f[0].scopeChain) {
      if (sc.type !== 'wasm-expression-stack' && sc.type !== 'local') continue;
      log('  ' + sc.type + ': ' + (await dump(sc.object.objectId, depth)).slice(0, +(process.env.MAXC || 2000)));
    }
    if (process.env.STEP && (globalThis.__st ?? 0) < +process.env.STEP) {
      globalThis.__st = (globalThis.__st ?? 0) + 1;
      log('STEP ' + globalThis.__st + ' ' + f.slice(0, 3).map(c => c.functionName + '@0x' + c.location.columnNumber.toString(16)).join(' < '));
      await s.send('Debugger.stepInto');
      return;
    }
    await s.send('Debugger.resume');
  });
  await s.send('Runtime.enable');
  await s.send('Debugger.enable');
  if (process.env.PEX) await s.send('Debugger.setPauseOnExceptions', { state: 'all' });
  await s.send('Runtime.runIfWaitingForDebugger');
  if (process.env.EVAL) setTimeout(async () => { log('eval...'); const t = setTimeout(() => log('eval TIMEOUT (worker blockiert)'), 5000); try { const r = await s.send('Runtime.evaluate', { expression: process.env.EVAL }); clearTimeout(t); log('eval = ' + JSON.stringify(r.result.value ?? r.result.description)); } catch (x) { log('eval fail ' + x.message); } }, 20000);
  if (process.env.PAUSE) for (const t of process.env.PAUSE.split(',')) setTimeout(() => { log('pause @' + t); s.send('Debugger.pause').catch(x => log('pause fail ' + x.message)); }, +t * 1000);
});
await cdp.send('Target.setAutoAttach', { autoAttach: true, waitForDebuggerOnStart: true, flatten: true });
await p.goto(url);
for (const a of (process.env.ACT || '').split(';').filter(Boolean).map(x => { const [t, k, v] = x.split(':'); return { t: +t, k, v }; })) setTimeout(async () => {
  log('[act] ' + a.k + ' ' + (a.v || ''));
  if (a.k === 'c') { const [x, y] = a.v.split(',').map(Number); await p.mouse.move(x, y); await p.mouse.down(); await new Promise(r => setTimeout(r, 80)); await p.mouse.up(); }
  else if (a.k === 'k') await p.keyboard.press(a.v);
  else if (a.k === 's') await p.screenshot({ path: '/tmp/dbg_' + a.t + '.png' });
}, a.t * 1000);
await new Promise(r => setTimeout(r, secs * 1000));
await b.close();
