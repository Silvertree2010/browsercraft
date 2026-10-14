import puppeteer from 'puppeteer-core';
const url = process.argv[2] || 'http://localhost:8090/index.html';
const secs = +(process.argv[3] || 60);
const shot = process.argv[4] || '/tmp/bc.png';
const b = await puppeteer.launch({
  executablePath: process.env.CHROME || decodeURIComponent(new URL('b/chrome-mac-arm64/Google Chrome for Testing.app/Contents/MacOS/Google Chrome for Testing', import.meta.url).pathname),
  headless: 'new',
  args: ['--enable-unsafe-webgpu', '--enable-features=Vulkan,WebGPU', '--use-angle=metal', '--window-size=1280,720', '--no-first-run', '--disable-background-timer-throttling', '--disable-renderer-backgrounding'],
  defaultViewport: { width: 1280, height: 720 },
  protocolTimeout: 0,
});
const p = await b.newPage();
const log = (s) => console.log(new Date().toISOString().slice(11, 19), s);
p.on('console', m => log('[page] ' + m.text()));
p.on('pageerror', e => log('[pageerror] ' + e.message));
p.on('workercreated', w => { log('[worker] ' + w.url()); w.on('console', m => log('[w] ' + m.text())); });
const cdp = await p.target().createCDPSession();
await cdp.send('Target.setAutoAttach', { autoAttach: true, waitForDebuggerOnStart: false, flatten: true });
await p.goto(url);
const end = Date.now() + secs * 1000;
let i = 0;
while (Date.now() < end) {
  await new Promise(r => setTimeout(r, 5000));
  if (++i % 3 === 0) await p.screenshot({ path: shot });
}
await p.screenshot({ path: shot });
await b.close();
