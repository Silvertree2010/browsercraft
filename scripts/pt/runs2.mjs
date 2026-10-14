import fs from 'fs';
const pr = JSON.parse(fs.readFileSync(process.argv[2]));
const min = +(process.argv[3] || 100);
const by = new Map(pr.nodes.map(n => [n.id, n]));
const par = new Map();
for (const n of pr.nodes) for (const c of n.children || []) par.set(c, n.id);
const nm = n => n.callFrame.functionName || '(' + n.callFrame.url.split('/').pop() + ')';
const stk = id => { const a = []; for (let k = id; k !== undefined; k = par.get(k)) a.push(nm(by.get(k))); return a.reverse(); };
let t = 0, run = [], t0 = 0;
const out = [];
const flush = () => {
  if (!run.length) return;
  const d = (run[run.length - 1][0] - t0) / 1000;
  if (d >= min) {
    const cnt = new Map();
    for (const [, s] of run) { const seen = new Set(s); for (const f of seen) cnt.set(f, (cnt.get(f) || 0) + 1); }
    const st = run[Math.floor(run.length / 2)][1];
    const common = st.filter(f => cnt.get(f) >= run.length * 0.9);
    out.push(d.toFixed(0) + 'ms ' + common.slice(-6).join(' > '));
  }
  run = [];
};
for (let i = 0; i < pr.samples.length; i++) {
  t += pr.timeDeltas[i];
  const s = stk(pr.samples[i]);
  const leaf = s[s.length - 1];
  if (leaf === '(idle)' || leaf === '(program)' || leaf === '(garbage collector)' && false) { flush(); continue; }
  if (!run.length) t0 = t;
  run.push([t, s]);
}
flush();
console.log(out.length + ' runs >= ' + min + 'ms');
const agg = new Map();
for (const o of out) { const k = o.replace(/^\d+ms /, ''); agg.set(k, (agg.get(k) || []).concat(+o.split('ms')[0])); }
for (const [k, v] of [...agg].sort((a, b) => b[1].length - a[1].length)) console.log(v.length, 'x max', Math.max(...v), k);
