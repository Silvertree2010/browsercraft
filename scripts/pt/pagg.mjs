import fs from 'fs';
const pr = JSON.parse(fs.readFileSync(process.argv[2]));
const top = +(process.argv[3] || 40);
const keys = (process.env.KEYS || '').split(',').filter(Boolean);
const by = new Map(pr.nodes.map(n => [n.id, n]));
const par = new Map();
for (const n of pr.nodes) for (const c of n.children || []) par.set(c, n.id);
const cnt = new Map();
for (const s of pr.samples) cnt.set(s, (cnt.get(s) || 0) + 1);
const tot = pr.samples.length;
const nm = n => n.callFrame.functionName || '(' + n.callFrame.url.split('/').pop() + ')';
const self = new Map(), inc = new Map();
for (const [id, c] of cnt) {
  const n = by.get(id);
  self.set(nm(n), (self.get(nm(n)) || 0) + c);
  const seen = new Set();
  for (let k = id; k !== undefined; k = par.get(k)) {
    const f = nm(by.get(k));
    if (seen.has(f)) continue;
    seen.add(f);
    inc.set(f, (inc.get(f) || 0) + c);
  }
}
const pc = c => (100 * c / tot).toFixed(1).padStart(5) + '%';
console.log('samples ' + tot);
console.log('--- self');
for (const [f, c] of [...self].sort((a, b) => b[1] - a[1]).slice(0, top)) console.log(pc(c), f);
console.log('--- inclusive');
for (const [f, c] of [...inc].sort((a, b) => b[1] - a[1]).filter(([f]) => !keys.length || keys.some(k => f.includes(k))).slice(0, top)) console.log(pc(c), f);
