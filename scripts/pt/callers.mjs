import fs from 'fs';
const p = JSON.parse(fs.readFileSync(process.argv[2]));
const re = new RegExp(process.argv[3]);
const d = +(process.argv[4] || 7);
const by = new Map(p.nodes.map(n => [n.id, n]));
const par = new Map();
for (const n of p.nodes) for (const c of n.children || []) par.set(c, n.id);
const cnt = new Map();
for (const s of p.samples) cnt.set(s, (cnt.get(s) || 0) + 1);
const agg = new Map(), T = p.samples.length;
for (const [id, c] of cnt) {
  let k = id;
  while (k !== undefined && !re.test(by.get(k).callFrame.functionName)) k = par.get(k);
  if (k === undefined) continue;
  const ch = [];
  for (let i = 0; i < d && k !== undefined; i++) { ch.push(by.get(k).callFrame.functionName.replace('net.minecraft.', '')); k = par.get(k); }
  const key = ch.join(' < ');
  agg.set(key, (agg.get(key) || 0) + c);
}
for (const [k, c] of [...agg].sort((a, b) => b[1] - a[1]).slice(0, +(process.env.N || 8))) console.log((100 * c / T).toFixed(1) + '%', k);
