import fs from 'fs';
const b = fs.readFileSync(process.argv[2]);
let p = 8;
const u = () => { let r = 0, s = 0, x; do { x = b[p++]; r += (x & 127) * 2 ** s; s += 7; } while (x & 128); return r; };
const out = [];
while (p < b.length) {
  const id = b[p++], n = u(), e = p + n;
  if (id === 0) {
    const l = u(), nm = b.toString('utf8', p, p + l); p += l;
    if (nm === 'name') {
      while (p < e) {
        const sid = b[p++], sn = u(), se = p + sn;
        if (sid === 1) { const c = u(); for (let i = 0; i < c; i++) { const ix = u(), ln = u(); out.push(ix + '\t' + b.toString('utf8', p, p + ln)); p += ln; } }
        p = se;
      }
    }
  }
  p = e;
}
fs.writeFileSync(process.argv[3], out.join('\n'));
