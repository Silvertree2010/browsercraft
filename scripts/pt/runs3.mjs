import fs from 'fs';
const pr = JSON.parse(fs.readFileSync(process.argv[2]));
const min = +(process.argv[3] || 100);
const by = new Map(pr.nodes.map(n => [n.id, n]));
const par = new Map();
for (const n of pr.nodes) for (const c of n.children || []) par.set(c, n.id);
const nm = n => n.callFrame.functionName || '(' + n.callFrame.url.split('/').pop() + ')';
const memo = new Map();
const stk = id => { if (memo.has(id)) return memo.get(id); const a = []; for (let k = id; k !== undefined; k = par.get(k)) a.push(nm(by.get(k))); a.reverse(); memo.set(id, a); return a; };
const key = s => { const i = s.indexOf('java.lang.Thread::runThread'); return i < 0 ? s[s.length - 1] : s.slice(i + 1, i + 3).join('>'); };
let t = 0, run = [], t0 = 0, k0 = null;
const out = [];
const flush = () => {
  if (run.length) {
    const d = (run[run.length - 1][0] - t0) / 1000;
    if (d >= min) {
      const cnt = new Map();
      for (const [, s] of run) for (const f of new Set(s)) cnt.set(f, (cnt.get(f) || 0) + 1);
      const st = run[Math.floor(run.length / 2)][1];
      const common = st.filter(f => cnt.get(f) >= run.length * 0.8);
      out.push([d, common.slice(-4).join(' > ')]);
    }
  }
  run = [];
};
for (let i = 0; i < pr.samples.length; i++) {
  t += pr.timeDeltas[i];
  const s = stk(pr.samples[i]);
  const k = key(s);
  if (k === '(idle)' || k === '(program)' || k !== k0) { flush(); k0 = k; }
  if (k === '(idle)' || k === '(program)') continue;
  if (!run.length) t0 = t;
  run.push([t, s]);
}
flush();
console.log(out.length + ' runs >= ' + min + 'ms, total ' + (out.reduce((a, b) => a + b[0], 0) / 1000).toFixed(1) + 's');
for (const [d, c] of out.sort((a, b) => b[0] - a[0]).slice(0, 25)) console.log(d.toFixed(0).padStart(5), c.slice(-330));
