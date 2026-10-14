import puppeteer from 'puppeteer-core';
import fs from 'fs';
const url = process.argv[2];
const secs = +process.argv[3];
const out = process.argv[4] || '/tmp/prof.cpuprofile';
const [ps, pe] = (process.env.PROF || '100,130').split(',').map(Number);
const b = await puppeteer.launch({
  executablePath: process.env.CHROME || decodeURIComponent(new URL('b/chrome-mac-arm64/Google Chrome for Testing.app/Contents/MacOS/Google Chrome for Testing', import.meta.url).pathname),
  headless: 'new',
  args: ['--enable-unsafe-webgpu', '--enable-features=Vulkan,WebGPU', '--use-angle=metal', '--window-size=1280,720', '--no-first-run', '--disable-background-timer-throttling', '--disable-renderer-backgrounding', '--disable-gpu-vsync', '--disable-frame-rate-limit'],
  defaultViewport: { width: 1280, height: 720 },
  protocolTimeout: 0,
});
const p = await b.newPage();
const t0 = Date.now();
const log = (s) => console.log(((Date.now() - t0) / 1000).toFixed(1).padStart(6), s);
p.on('console', m => log('[page] ' + m.text()));
const cdp = await p.target().createCDPSession();
const conn = cdp.connection();
let wn = 0;
cdp.on('Target.attachedToTarget', async ev => {
  if (ev.targetInfo.type !== 'worker') return;
  const s = conn.session(ev.sessionId);
  const k = wn++;
  log('[attach] ' + k + ' ' + ev.targetInfo.url);
  s.on('Runtime.consoleAPICalled', e => log('[w] ' + e.args.map(a => a.value ?? a.description).join(' ')));
  await s.send('Runtime.enable');
  await s.send('Profiler.enable');
  await s.send('Profiler.setSamplingInterval', { interval: +(process.env.IV || 500) });
  await s.send('Runtime.runIfWaitingForDebugger');
  setTimeout(async () => { log('prof start'); await s.send('Profiler.start'); }, Math.max(0, ps * 1000 - (Date.now() - t0)));
  setTimeout(async () => { const r = await s.send('Profiler.stop'); const o = out.replace('.cpuprofile', '_' + k + '.cpuprofile'); fs.writeFileSync(o, JSON.stringify(r.profile)); log('prof saved ' + o); }, Math.max(0, pe * 1000 - (Date.now() - t0)));
});
await cdp.send('Target.setAutoAttach', { autoAttach: true, waitForDebuggerOnStart: true, flatten: true });
await p.goto(url);
for (const a of (process.env.ACT || '').split(';').filter(Boolean).map(x => { const [t, k, v] = x.split(':'); return { t: +t, k, v }; })) setTimeout(async () => {
  log('[act] ' + a.k + ' ' + (a.v || ''));
  if (a.k === 'c') { const [x, y] = a.v.split(',').map(Number); await p.mouse.move(x, y); await p.mouse.down(); await new Promise(r => setTimeout(r, 80)); await p.mouse.up(); }
  else if (a.k === 'k') await p.keyboard.press(a.v);
  else if (a.k === 's') await p.screenshot({ path: '/tmp/prof_' + a.t + '.png' });
}, a.t * 1000);
await new Promise(r => setTimeout(r, secs * 1000));
await b.close();
