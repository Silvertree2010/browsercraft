import http from 'node:http';
import fs from 'node:fs';
import fsp from 'node:fs/promises';
import path from 'node:path';
import net from 'node:net';
import dns from 'node:dns/promises';
import crypto from 'node:crypto';
import { WebSocketServer } from 'ws';
import { spawn } from 'node:child_process';

const ROOT = process.env.BC_ROOT || path.resolve('web');
const DATA = process.env.BC_DATA || path.resolve('data');
const STATE = process.env.BC_STATE || path.resolve('state');
const PORT = +(process.env.PORT || 8090);
const CLIENT_ID = process.env.MS_CLIENT_ID || '';
const ADMINS = (process.env.ADMIN_UUIDS || '').split(',').map(s => s.trim().replace(/-/g, '')).filter(Boolean);
const PORTS = (process.env.MC_PORTS || '25565-25580').split('-').map(Number);
const DEV = process.env.BC_DEV === '1';
const PROXY_HOSTS = new Set(['discovery.minecraftservices.com', 'sessionserver.mojang.com', 'api.minecraftservices.com', 'textures.minecraft.net', 'api.mojang.com', 'authserver.mojang.com', 'pc.realms.minecraft.net', 'resources.download.minecraft.net']);

fs.mkdirSync(STATE, { recursive: true });
const dbFile = path.join(STATE, 'db.json');
const db = fs.existsSync(dbFile) ? JSON.parse(fs.readFileSync(dbFile, 'utf8')) : { sessions: {}, accounts: {}, instances: [], catalog: [] };
let saveT;
function save() { clearTimeout(saveT); saveT = setTimeout(() => fsp.writeFile(dbFile + '.tmp', JSON.stringify(db, null, 1)).then(() => fsp.rename(dbFile + '.tmp', dbFile)), 200); }

const MIME = { '.html': 'text/html; charset=utf-8', '.js': 'text/javascript', '.mjs': 'text/javascript', '.css': 'text/css', '.wasm': 'application/wasm', '.json': 'application/json', '.png': 'image/png', '.woff2': 'font/woff2', '.svg': 'image/svg+xml', '.ogg': 'audio/ogg' };

function headers(res, extra = {}) {
  res.setHeader('Cross-Origin-Opener-Policy', 'same-origin');
  res.setHeader('Cross-Origin-Embedder-Policy', 'require-corp');
  res.setHeader('Cross-Origin-Resource-Policy', 'same-origin');
  res.setHeader('X-Content-Type-Options', 'nosniff');
  for (const [k, v] of Object.entries(extra)) res.setHeader(k, v);
}

function cookies(req) {
  const o = {};
  for (const p of (req.headers.cookie || '').split(';')) { const i = p.indexOf('='); if (i > 0) o[p.slice(0, i).trim()] = decodeURIComponent(p.slice(i + 1).trim()); }
  return o;
}

function session(req) {
  if (DEV) return { uuid: 'dev', name: 'dev', admin: true };
  const s = db.sessions[cookies(req).bc];
  return s && s.exp > Date.now() ? s : null;
}

function json(res, code, o) { headers(res, { 'Content-Type': 'application/json', 'Cache-Control': 'no-store' }); res.writeHead(code); res.end(JSON.stringify(o)); }

async function body(req) {
  const chunks = [];
  let n = 0;
  for await (const c of req) { n += c.length; if (n > 1 << 20) throw new Error('too large'); chunks.push(c); }
  const s = Buffer.concat(chunks).toString('utf8');
  return s ? JSON.parse(s) : {};
}

async function post(url, data, form) {
  const r = await fetch(url, {
    method: 'POST',
    headers: { 'Content-Type': form ? 'application/x-www-form-urlencoded' : 'application/json', Accept: 'application/json' },
    body: form ? new URLSearchParams(data) : JSON.stringify(data),
  });
  const t = await r.text();
  let j;
  try { j = JSON.parse(t); } catch { j = { raw: t }; }
  if (!r.ok) { const e = new Error(j.error_description || j.error || j.XErr || r.status); e.data = j; e.status = r.status; throw e; }
  return j;
}

async function minecraft(msToken) {
  const xbl = await post('https://user.auth.xboxlive.com/user/authenticate', { Properties: { AuthMethod: 'RPS', SiteName: 'user.auth.xboxlive.com', RpsTicket: 'd=' + msToken }, RelyingParty: 'http://auth.xboxlive.com', TokenType: 'JWT' });
  const uhs = xbl.DisplayClaims.xui[0].uhs;
  const xsts = await post('https://xsts.auth.xboxlive.com/xsts/authorize', { Properties: { SandboxId: 'RETAIL', UserTokens: [xbl.Token] }, RelyingParty: 'rp://api.minecraftservices.com/', TokenType: 'JWT' });
  const mc = await post('https://api.minecraftservices.com/authentication/login_with_xbox', { identityToken: `XBL3.0 x=${uhs};${xsts.Token}` });
  const pr = await fetch('https://api.minecraftservices.com/minecraft/profile', { headers: { Authorization: 'Bearer ' + mc.access_token } });
  if (pr.status === 404) throw Object.assign(new Error('Dieser Account besitzt Minecraft nicht.'), { status: 403 });
  if (!pr.ok) throw new Error('profile ' + pr.status);
  const p = await pr.json();
  const skin = p.skins?.find(s => s.state === 'ACTIVE')?.url || p.skins?.[0]?.url || null;
  return { token: mc.access_token, exp: Date.now() + (mc.expires_in || 86400) * 1000, uuid: p.id, name: p.name, skin };
}

async function refresh(acc) {
  const t = await post('https://login.microsoftonline.com/consumers/oauth2/v2.0/token', { client_id: CLIENT_ID, grant_type: 'refresh_token', refresh_token: acc.refresh, scope: 'XboxLive.signin offline_access' }, true);
  acc.refresh = t.refresh_token || acc.refresh;
  Object.assign(acc, await minecraft(t.access_token));
  save();
  return acc;
}

function newSession(res, p) {
  const id = crypto.randomBytes(24).toString('base64url');
  db.sessions[id] = { uuid: p.uuid, name: p.name, admin: ADMINS.includes(p.uuid), exp: Date.now() + 1000 * 3600 * 24 * 180 };
  save();
  res.setHeader('Set-Cookie', `bc=${id}; Path=/; HttpOnly; Secure; SameSite=Lax; Max-Age=${3600 * 24 * 180}`);
}

const bakeLog = [];
function log(s) { bakeLog.push(new Date().toISOString().slice(0, 19) + ' ' + s); if (bakeLog.length > 2000) bakeLog.shift(); }

const api = {
  'GET /me': (req, res, s) => json(res, 200, { name: s.name, uuid: s.uuid, admin: !!s.admin, versions: db.versions || ['26.3'] }),
  'GET /instances': (req, res) => json(res, 200, db.instances),
  'GET /catalog': (req, res) => json(res, 200, db.catalog),
  'GET /bake/log': (req, res, s) => { if (!s.admin) return json(res, 403, { error: 'admin' }); headers(res, { 'Content-Type': 'text/plain' }); res.end(bakeLog.slice(-400).join('\n')); },
  'POST /instances': async (req, res, s) => {
    if (!s.admin) return json(res, 403, { error: 'admin' });
    const b = await body(req);
    const i = { id: crypto.randomBytes(6).toString('hex'), name: String(b.name || 'Instanz').slice(0, 60), version: String(b.version || '26.3'), mods: (b.mods || []).map(String), state: 'baking', build: null, created: Date.now() };
    db.instances.push(i);
    save();
    startBake(i);
    json(res, 200, i);
  },
  'POST /instances/delete': async (req, res, s) => {
    if (!s.admin) return json(res, 403, { error: 'admin' });
    const b = await body(req);
    db.instances = db.instances.filter(i => i.id !== b.id);
    save();
    json(res, 200, {});
  },
  'POST /instances/rebake': async (req, res, s) => {
    if (!s.admin) return json(res, 403, { error: 'admin' });
    const b = await body(req);
    const i = db.instances.find(x => x.id === b.id);
    if (!i) return json(res, 404, {});
    i.state = 'baking';
    save();
    startBake(i);
    json(res, 200, i);
  },
  'POST /auth/token': async (req, res, s) => {
    const b = await body(req);
    const acc = db.accounts[s.uuid + ':' + b.uuid];
    if (!acc) return json(res, 404, { error: 'unknown account' });
    if (acc.exp - Date.now() < 3600 * 1000) await refresh(acc);
    json(res, 200, { token: acc.token, uuid: acc.uuid, name: acc.name });
  },
  'POST /auth/forget': async (req, res, s) => {
    const b = await body(req);
    delete db.accounts[s.uuid + ':' + b.uuid];
    save();
    json(res, 200, {});
  },
};

const pub = {
  'POST /auth/device': async (req, res) => {
    if (!CLIENT_ID) return json(res, 503, { error: 'MS_CLIENT_ID fehlt' });
    const d = await post('https://login.microsoftonline.com/consumers/oauth2/v2.0/devicecode', { client_id: CLIENT_ID, scope: 'XboxLive.signin offline_access' }, true);
    json(res, 200, { user_code: d.user_code, device_code: d.device_code, verification_uri: d.verification_uri, interval: d.interval });
  },
  'POST /auth/poll': async (req, res) => {
    const b = await body(req);
    let t;
    try {
      t = await post('https://login.microsoftonline.com/consumers/oauth2/v2.0/token', { client_id: CLIENT_ID, grant_type: 'urn:ietf:params:oauth:grant-type:device_code', device_code: b.device_code }, true);
    } catch (e) {
      if (e.data?.error === 'authorization_pending' || e.data?.error === 'slow_down') return json(res, 200, { pending: true });
      throw e;
    }
    const p = await minecraft(t.access_token);
    let s = session(req);
    if (!s) { newSession(res, p); s = { uuid: p.uuid }; }
    db.accounts[s.uuid + ':' + p.uuid] = { ...p, refresh: t.refresh_token };
    save();
    json(res, 200, { uuid: p.uuid, name: p.name, skin: p.skin ? '/proxy/textures.minecraft.net' + new URL(p.skin).pathname : null });
  },
};

const bakes = new Map();
function startBake(i) {
  const cmd = process.env.BAKE_CMD;
  if (!cmd) { log(`[${i.id}] BAKE_CMD nicht gesetzt`); i.state = 'failed'; save(); return; }
  if (bakes.has(i.id)) return;
  log(`[${i.id}] Bake gestartet: ${i.name} (${i.version}, ${i.mods.length} Mods)`);
  const p = spawn('sh', ['-c', cmd], { env: { ...process.env, BC_INSTANCE: i.id, BC_VERSION: i.version, BC_MODS: i.mods.join(',') } });
  bakes.set(i.id, p);
  const out = (d) => String(d).split('\n').filter(Boolean).forEach(l => log(`[${i.id}] ${l}`));
  p.stdout.on('data', out);
  p.stderr.on('data', out);
  p.on('close', (code) => {
    bakes.delete(i.id);
    i.state = code === 0 ? 'ready' : 'failed';
    if (code === 0) i.build = i.id;
    log(`[${i.id}] Bake beendet: ${code === 0 ? 'ok' : 'Fehler ' + code}`);
    save();
  });
}

async function proxy(req, res, s) {
  const u = new URL(req.url, 'http://x');
  const parts = u.pathname.split('/');
  const host = parts[2];
  if (!PROXY_HOSTS.has(host)) return json(res, 403, { error: 'host' });
  if (DEV && process.env.BC_FAKEJOIN === '1' && u.pathname.endsWith('/session/minecraft/join')) { headers(res); res.writeHead(204); return res.end(); }
  const target = 'https://' + host + '/' + parts.slice(3).join('/') + u.search;
  const h = {};
  for (const k of ['authorization', 'content-type', 'accept']) if (req.headers[k]) h[k] = req.headers[k];
  const r = await fetch(target, { method: req.method, headers: h, body: req.method === 'GET' || req.method === 'HEAD' ? undefined : Buffer.concat(await (async () => { const c = []; for await (const x of req) c.push(x); return c; })()) });
  headers(res, { 'Content-Type': r.headers.get('content-type') || 'application/octet-stream', 'Cache-Control': host === 'textures.minecraft.net' ? 'public, max-age=86400' : 'no-store' });
  res.writeHead(r.status);
  res.end(Buffer.from(await r.arrayBuffer()));
}

async function file(req, res, p) {
  try {
    const st = await fsp.stat(p);
    if (st.isDirectory()) return file(req, res, path.join(p, 'index.html'));
    headers(res, { 'Content-Type': MIME[path.extname(p)] || 'application/octet-stream', 'Content-Length': st.size, 'Cache-Control': p.includes('/builds/') || p.includes('/objects/') ? 'public, max-age=31536000, immutable' : 'no-cache' });
    res.writeHead(200);
    fs.createReadStream(p).pipe(res);
  } catch {
    headers(res);
    res.writeHead(404);
    res.end('not found');
  }
}

function safe(base, rel) {
  const p = path.resolve(base, '.' + path.posix.normalize('/' + rel));
  return p.startsWith(base) ? p : null;
}

async function objects(req, res, rel) {
  const p = safe(DATA, rel);
  if (!p) return json(res, 400, {});
  if (!fs.existsSync(p)) {
    const h = path.basename(p);
    if (!/^[0-9a-f]{40}$/.test(h)) return json(res, 404, {});
    const r = await fetch(`https://resources.download.minecraft.net/${h.slice(0, 2)}/${h}`);
    if (!r.ok) return json(res, 404, {});
    await fsp.mkdir(path.dirname(p), { recursive: true });
    await fsp.writeFile(p + '.tmp', Buffer.from(await r.arrayBuffer()));
    await fsp.rename(p + '.tmp', p);
  }
  return file(req, res, p);
}

const server = http.createServer(async (req, res) => {
  try {
    const u = new URL(req.url, 'http://x');
    const s = session(req);
    if (u.pathname.startsWith('/api/')) {
      const k = req.method + ' ' + u.pathname.slice(4);
      if (pub[k]) return await pub[k](req, res);
      if (!s) return json(res, 401, { error: 'login' });
      if (api[k]) return await api[k](req, res, s);
      return json(res, 404, { error: 'no route' });
    }
    if (u.pathname.startsWith('/proxy/')) { if (!s) return json(res, 401, {}); return await proxy(req, res, s); }
    if (u.pathname.startsWith('/data/') || u.pathname.startsWith('/builds/')) {
      if (!s) return json(res, 401, {});
      if (u.pathname.startsWith('/data/assets/objects/')) return await objects(req, res, u.pathname.slice(6));
      const p = u.pathname.startsWith('/data/') ? safe(DATA, u.pathname.slice(6)) : safe(path.join(STATE, 'builds'), u.pathname.slice(8));
      return p ? await file(req, res, p) : json(res, 400, {});
    }
    if (u.pathname === '/') { res.writeHead(302, { Location: '/launcher/' }); return res.end(); }
    const p = safe(ROOT, u.pathname);
    return p ? await file(req, res, p) : json(res, 400, {});
  } catch (e) {
    console.error(e);
    if (!res.headersSent) json(res, e.status || 500, { error: e.message });
    else res.end();
  }
});

const wss = new WebSocketServer({ noServer: true, maxPayload: 8 << 20 });
server.on('upgrade', async (req, sock, head) => {
  const u = new URL(req.url, 'http://x');
  const s = session(req);
  if (u.pathname !== '/ws' || !s) { sock.destroy(); return; }
  let host = String(u.searchParams.get('h') || '').toLowerCase();
  let port = +u.searchParams.get('p') || 25565;
  if (!/^[a-z0-9.-]{1,253}$/.test(host)) { sock.destroy(); return; }
  if (port === 25565) {
    try { const r = await dns.resolveSrv('_minecraft._tcp.' + host); if (r.length) { host = r[0].name; port = r[0].port; } } catch {}
  }
  if (port < PORTS[0] || port > (PORTS[1] || PORTS[0])) { sock.destroy(); return; }
  wss.handleUpgrade(req, sock, head, (ws) => {
    const t = net.connect({ host, port });
    t.setNoDelay(true);
    let open = false;
    const q = [];
    t.on('connect', () => { open = true; for (const b of q) t.write(b); q.length = 0; });
    ws.on('message', (d) => { if (open) t.write(d); else q.push(d); });
    t.on('data', (d) => { if (ws.readyState === 1) ws.send(d); });
    const end = () => { try { ws.close(); } catch {} t.destroy(); };
    t.on('error', end);
    t.on('close', end);
    ws.on('close', end);
    ws.on('error', end);
  });
});

server.listen(PORT, () => console.log('browsercraft on :' + PORT));
