import puppeteer from 'puppeteer-core';
const url = process.argv[2] || 'http://localhost:8090/game/index.html';
const secs = +(process.argv[3] || 60);
const shot = process.argv[4] || '/tmp/a.png';
const acts = (process.env.ACT || '').split(';').filter(Boolean).map(s => { const [t, k, a] = s.split(':'); return { t: +t, k, a }; }).sort((x, y) => x.t - y.t);
const b = await puppeteer.launch({
  executablePath: process.env.CHROME || decodeURIComponent(new URL('b/chrome-mac-arm64/Google Chrome for Testing.app/Contents/MacOS/Google Chrome for Testing', import.meta.url).pathname),
  headless: 'new',
  args: ['--enable-unsafe-webgpu', '--enable-features=Vulkan,WebGPU', '--use-angle=metal', '--window-size=1280,720', '--no-first-run', '--disable-background-timer-throttling', '--disable-renderer-backgrounding'],
  defaultViewport: { width: 1280, height: 720 },
  protocolTimeout: 0,
});
const p = await b.newPage();
const t0 = Date.now();
const log = (s) => console.log(((Date.now() - t0) / 1000).toFixed(1).padStart(6), s);
p.on('console', m => log('[page] ' + m.text()));
p.on('pageerror', e => log('[pageerror] ' + e.message));
p.on('workercreated', w => { log('[worker] ' + w.url()); w.on('console', m => log('[w] ' + m.text())); });
await p.goto(url);
const sh = (n) => p.screenshot({ path: shot.replace('.png', '_' + n + '.png') });
let n = 0, last = 0;
while (Date.now() - t0 < secs * 1000) {
  const s = (Date.now() - t0) / 1000;
  while (acts.length && acts[0].t <= s) {
    const a = acts.shift();
    log('[act] ' + a.k + ' ' + (a.a || ''));
    if (a.k === 'c') { const [x, y] = a.a.split(',').map(Number); await p.mouse.move(x, y); await p.mouse.down(); await new Promise(r => setTimeout(r, 80)); await p.mouse.up(); }
    else if (a.k === 'm') { const [x, y] = a.a.split(',').map(Number); await p.mouse.move(x, y); }
    else if (a.k === 'k') await p.keyboard.press(a.a);
    else if (a.k === 'd') await p.keyboard.down(a.a);
    else if (a.k === 'u') await p.keyboard.up(a.a);
    else if (a.k === 't') await p.keyboard.type(a.a);
    else if (a.k === 's') await sh('a' + String(++n).padStart(2, '0'));
  }
  if (s - last >= 10) { last = s; await sh(String(Math.round(s)).padStart(3, '0')); }
  await new Promise(r => setTimeout(r, 250));
}
await sh('end');
await b.close();
