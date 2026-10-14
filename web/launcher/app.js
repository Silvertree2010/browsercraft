import { boot } from '../game/boot.js';

const $ = (s, r = document) => r.querySelector(s);
const h = (tag, attrs = {}, ...kids) => {
  const e = document.createElement(tag);
  for (const [k, v] of Object.entries(attrs)) {
    if (k.startsWith('on')) e.addEventListener(k.slice(2), v);
    else if (k === 'class') e.className = v;
    else if (v !== false && v != null) e.setAttribute(k, v === true ? '' : v);
  }
  for (const k of kids.flat()) if (k != null && k !== false) e.append(k.nodeType ? k : String(k));
  return e;
};

const ICON = {
  play: '<path d="M7 5v14l11-7z" fill="currentColor"/>',
  box: '<path d="M4 7.5 12 3l8 4.5v9L12 21l-8-4.5z M4 7.5l8 4.5 8-4.5 M12 12v9" fill="none" stroke="currentColor" stroke-width="1.6" stroke-linejoin="round"/>',
  user: '<circle cx="12" cy="8" r="3.6" fill="none" stroke="currentColor" stroke-width="1.6"/><path d="M5 20c1.2-3.6 4-5.4 7-5.4s5.8 1.8 7 5.4" fill="none" stroke="currentColor" stroke-width="1.6" stroke-linecap="round"/>',
  gear: '<circle cx="12" cy="12" r="3" fill="none" stroke="currentColor" stroke-width="1.6"/><path d="M12 3v2.5M12 18.5V21M3 12h2.5M18.5 12H21M5.6 5.6l1.8 1.8M16.6 16.6l1.8 1.8M5.6 18.4l1.8-1.8M16.6 7.4l1.8-1.8" stroke="currentColor" stroke-width="1.6" stroke-linecap="round"/>',
  shield: '<path d="M12 3 5 6v5c0 4.4 3 8.2 7 10 4-1.8 7-5.6 7-10V6z" fill="none" stroke="currentColor" stroke-width="1.6" stroke-linejoin="round"/>',
};
const icon = (n) => { const s = document.createElementNS('http://www.w3.org/2000/svg', 'svg'); s.setAttribute('viewBox', '0 0 24 24'); s.innerHTML = ICON[n]; return s; };

const store = {
  get(k, d) { try { const v = localStorage.getItem('bc.' + k); return v ? JSON.parse(v) : d; } catch { return d; } },
  set(k, v) { try { localStorage.setItem('bc.' + k, JSON.stringify(v)); } catch {} },
};

async function api(path, opts = {}) {
  const r = await fetch('/api' + path, { headers: { 'content-type': 'application/json' }, ...opts, body: opts.body ? JSON.stringify(opts.body) : undefined });
  if (!r.ok) throw new Error((await r.text()) || r.statusText);
  return r.headers.get('content-type')?.includes('json') ? r.json() : r.text();
}

const S = {
  view: 'home',
  me: null,
  instances: [],
  catalog: [],
  accounts: store.get('accounts', []),
  active: store.get('active', null),
  selected: store.get('selected', null),
  settings: store.get('settings', { renderDistance: 8, fpsLimit: 0, gui: 0, sensitivity: 100 }),
};

const demo = {
  instances: [{ id: 'vanilla', name: 'Vanilla', version: '26.3', mods: [], state: 'ready', build: 'dev' }],
  catalog: [],
};

async function load() {
  try { S.me = await api('/me'); } catch { S.me = { admin: true, name: 'dev', offline: true }; }
  try { S.instances = await api('/instances'); } catch { S.instances = demo.instances; }
  try { S.catalog = await api('/catalog'); } catch { S.catalog = demo.catalog; }
  if (!S.instances.find(i => i.id === S.selected)) S.selected = S.instances[0]?.id || null;
  render();
}

function account() { return S.accounts.find(a => a.id === S.active) || null; }

function headImg(a) {
  const c = h('canvas', { class: 'head', width: 8, height: 8 });
  const g = c.getContext('2d');
  g.fillStyle = a ? '#3b4256' : '#2a3040';
  g.fillRect(0, 0, 8, 8);
  if (a && a.skin) {
    const im = new Image();
    im.onload = () => { g.drawImage(im, 8, 8, 8, 8, 0, 0, 8, 8); g.drawImage(im, 40, 8, 8, 8, 0, 0, 8, 8); };
    im.src = a.skin;
  } else if (a) {
    g.fillStyle = '#c69c7b'; g.fillRect(1, 2, 6, 6);
    g.fillStyle = '#4a2f1f'; g.fillRect(0, 0, 8, 2);
    g.fillStyle = '#ffffff'; g.fillRect(1, 4, 2, 1); g.fillRect(5, 4, 2, 1);
    g.fillStyle = '#3d58a8'; g.fillRect(2, 4, 1, 1); g.fillRect(5, 4, 1, 1);
  }
  return c;
}

function nav(id, label, ic) {
  return h('button', { class: 'nav' + (S.view === id ? ' on' : ''), onclick: () => { S.view = id; render(); } }, icon(ic), label);
}

function render() {
  const a = account();
  const side = h('aside', { class: 'side' },
    h('div', { class: 'brand' }, h('span', {}, 'browser', h('b', {}, 'craft')), h('small', {}, 'beta')),
    nav('home', 'Spielen', 'play'),
    nav('instances', 'Instanzen', 'box'),
    nav('accounts', 'Accounts', 'user'),
    nav('settings', 'Einstellungen', 'gear'),
    S.me?.admin ? nav('admin', 'Admin', 'shield') : null,
    h('div', { class: 'sep' }),
    h('div', { class: 'acct', onclick: () => { S.view = 'accounts'; render(); } }, headImg(a),
      h('div', { class: 'who' }, h('div', { class: 'name' }, a ? a.name : 'Kein Account'), h('div', { class: 'kind' }, a ? (a.type === 'ms' ? 'Microsoft' : 'Offline') : 'Account wählen'))));
  const main = h('main', { class: 'main' }, VIEWS[S.view]());
  const app = $('#app');
  app.replaceChildren(side, main);
}

function inst() { return S.instances.find(i => i.id === S.selected) || null; }

const VIEWS = {
  home() {
    const i = inst();
    const a = account();
    if (!i) return h('div', { class: 'empty' }, 'Noch keine Instanz vorhanden.');
    const ready = i.state === 'ready';
    return h('div', {},
      h('div', { class: 'title' }, h('div', {}, h('h1', {}, 'Spielen'), h('p', {}, a ? `Angemeldet als ${a.name}` : 'Wähle zuerst einen Account.'))),
      h('section', { class: 'hero' },
        h('div', {},
          h('span', { class: 'tag' }, i.mods.length ? 'Fabric' : 'Vanilla'),
          h('h2', {}, i.name),
          h('div', { class: 'meta' }, `Minecraft ${i.version}` + (i.mods.length ? ` · ${i.mods.length} Mods` : '')),
          i.mods.length ? h('div', { class: 'mods' }, i.mods.slice(0, 10).map(m => h('span', { class: 'chip' }, m.name || m))) : null),
        h('button', { class: 'btn primary play', disabled: !ready || !a, onclick: () => play(i, a) }, icon('play'), ready ? 'Spielen' : 'Wird gebaut')),
      S.instances.length > 1 ? h('div', { class: 'section' }, h('h3', {}, 'Instanzen'), instGrid()) : null);
  },

  instances() {
    return h('div', {},
      h('div', { class: 'title' }, h('div', {}, h('h1', {}, 'Instanzen'), h('p', {}, S.me?.admin ? 'Neue Instanzen legst du im Admin-Bereich an.' : 'Instanzen werden vom Admin angelegt.'))),
      instGrid());
  },

  accounts() {
    return h('div', {},
      h('div', { class: 'title' }, h('div', {}, h('h1', {}, 'Accounts'), h('p', {}, 'Microsoft-Accounts für Online-Server, Offline-Profile für Singleplayer und LAN.')),
        h('div', { style: 'display:flex;gap:8px' }, h('button', { class: 'btn', onclick: addOffline }, 'Offline-Profil'), h('button', { class: 'btn primary', onclick: addMicrosoft }, 'Microsoft-Login'))),
      S.accounts.length ? h('div', { class: 'list' }, S.accounts.map(a => h('div', { class: 'row' }, headImg(a),
        h('div', { class: 'grow' }, h('div', { style: 'font-weight:650' }, a.name), h('div', { class: 'sub' }, a.type === 'ms' ? 'Microsoft' : 'Offline')),
        S.active === a.id ? h('span', { class: 'chip' }, 'Aktiv') : h('button', { class: 'btn', onclick: () => { S.active = a.id; store.set('active', a.id); render(); } }, 'Wählen'),
        h('button', { class: 'btn danger', onclick: () => removeAccount(a) }, 'Entfernen'))))
        : h('div', { class: 'empty' }, 'Noch kein Account. Melde dich mit Microsoft an oder lege ein Offline-Profil an.'));
  },

  settings() {
    const s = S.settings;
    const save = () => store.set('settings', s);
    const range = (k, label, min, max, fmt) => {
      const out = h('span', {}, fmt(s[k]));
      return h('div', { class: 'field' }, h('label', {}, label, ' · ', out),
        h('input', { type: 'range', min, max, value: s[k], oninput: (e) => { s[k] = +e.target.value; out.textContent = fmt(s[k]); save(); } }));
    };
    return h('div', {},
      h('div', { class: 'title' }, h('div', {}, h('h1', {}, 'Einstellungen'), h('p', {}, 'Gilt für alle Instanzen auf diesem Gerät.'))),
      h('div', { class: 'form' },
        range('renderDistance', 'Sichtweite', 2, 16, v => v + ' Chunks'),
        range('fpsLimit', 'FPS-Limit', 0, 240, v => v === 0 ? 'Bildschirm' : v),
        range('sensitivity', 'Mausempfindlichkeit', 10, 200, v => v + ' %'),
        range('gui', 'GUI-Grösse', 0, 4, v => v === 0 ? 'Auto' : v)),
      h('div', { class: 'section' }, h('h3', {}, 'Speicher'),
        h('div', { class: 'row' }, h('div', { class: 'grow' }, h('div', {}, 'Lokaler Cache'), h('div', { class: 'sub', id: 'quota' }, 'wird berechnet …')),
          h('button', { class: 'btn danger', onclick: clearCache }, 'Cache leeren'))));
  },

  admin() {
    const box = h('div', {});
    const name = h('input', { type: 'text', placeholder: 'z. B. Survival mit Freunden' });
    const ver = h('select', {}, (S.me?.versions || ['26.3']).map(v => h('option', { value: v }, v)));
    const mods = h('div', { class: 'form' }, S.catalog.length ? S.catalog.map(m => h('label', { class: 'check' }, h('input', { type: 'checkbox', value: m.id }), m.name)) : h('div', { class: 'sub' }, 'Mod-Katalog ist noch leer.'));
    const log = h('div', { class: 'log' }, 'Noch kein Bake gestartet.');
    box.append(
      h('div', { class: 'title' }, h('div', {}, h('h1', {}, 'Admin'), h('p', {}, 'Instanzen anlegen und Bakes starten.'))),
      h('div', { class: 'form' }, h('div', { class: 'field' }, h('label', {}, 'Name'), name), h('div', { class: 'field' }, h('label', {}, 'Version'), ver)),
      h('div', { class: 'section' }, h('h3', {}, 'Mods'), mods),
      h('div', { class: 'section', style: 'display:flex;gap:8px' }, h('button', { class: 'btn primary', onclick: async () => {
        const sel = [...mods.querySelectorAll('input:checked')].map(c => c.value);
        try { await api('/instances', { method: 'POST', body: { name: name.value || 'Neue Instanz', version: ver.value, mods: sel } }); toast('Instanz angelegt, Bake läuft.'); load(); }
        catch (e) { toast('Fehler: ' + e.message); }
      } }, 'Instanz anlegen und baken')),
      h('div', { class: 'section' }, h('h3', {}, 'Bake-Log'), log));
    api('/bake/log').then(t => { log.textContent = t || 'Leer.'; }).catch(() => {});
    return box;
  },
};

function instGrid() {
  return h('div', { class: 'grid' }, S.instances.map(i => h('div', { class: 'card' + (i.id === S.selected ? ' on' : ''), onclick: () => { S.selected = i.id; store.set('selected', i.id); S.view = 'home'; render(); } },
    h('h4', {}, i.name),
    h('div', { class: 'meta' }, `${i.version} · ${i.mods.length ? i.mods.length + ' Mods' : 'Vanilla'}`),
    h('div', { class: 'state' }, h('i', { class: 'dot ' + (i.state === 'ready' ? 'ok' : i.state === 'failed' ? 'bad' : 'warn') }), i.state === 'ready' ? 'Bereit' : i.state === 'failed' ? 'Fehler' : 'Bake läuft'))));
}

function modal(title, text, body, actions) {
  const bg = h('div', { class: 'modal-bg', onclick: (e) => { if (e.target === bg) bg.remove(); } },
    h('div', { class: 'modal' }, h('h3', {}, title), text ? h('p', {}, text) : null, body, h('div', { class: 'actions' }, actions(() => bg.remove()))));
  document.body.append(bg);
  return bg;
}

function toast(t) {
  const e = h('div', { class: 'toast' }, t);
  document.body.append(e);
  setTimeout(() => e.remove(), 3500);
}

function saveAccounts() { store.set('accounts', S.accounts); }

function addOffline() {
  const inp = h('input', { type: 'text', placeholder: 'Spielername', maxlength: 16 });
  modal('Offline-Profil', 'Gilt für Singleplayer, LAN und Server ohne Online-Modus.', inp, (close) => [
    h('button', { class: 'btn', onclick: close }, 'Abbrechen'),
    h('button', { class: 'btn primary', onclick: () => {
      const n = inp.value.trim();
      if (!/^[A-Za-z0-9_]{3,16}$/.test(n)) { toast('3–16 Zeichen: Buchstaben, Zahlen, _'); return; }
      const a = { id: 'off:' + n.toLowerCase(), type: 'offline', name: n };
      S.accounts = S.accounts.filter(x => x.id !== a.id).concat(a);
      S.active = a.id; saveAccounts(); store.set('active', a.id); close(); render();
    } }, 'Anlegen')]);
  setTimeout(() => inp.focus(), 0);
}

async function addMicrosoft() {
  let d;
  try { d = await api('/auth/device', { method: 'POST' }); } catch (e) { toast('Login nicht verfügbar: ' + e.message); return; }
  const code = h('div', { class: 'code' }, d.user_code);
  let alive = true;
  const m = modal('Microsoft-Login', `Öffne ${d.verification_uri} und gib diesen Code ein:`, code, (close) => [h('button', { class: 'btn', onclick: () => { alive = false; close(); } }, 'Abbrechen')]);
  while (alive) {
    await new Promise(r => setTimeout(r, (d.interval || 5) * 1000));
    try {
      const r = await api('/auth/poll', { method: 'POST', body: { device_code: d.device_code } });
      if (r.pending) continue;
      const a = { id: 'ms:' + r.uuid, type: 'ms', name: r.name, uuid: r.uuid, skin: r.skin };
      S.accounts = S.accounts.filter(x => x.id !== a.id).concat(a);
      S.active = a.id; saveAccounts(); store.set('active', a.id);
      m.remove(); render(); return;
    } catch (e) { toast('Login fehlgeschlagen: ' + e.message); m.remove(); return; }
  }
}

function removeAccount(a) {
  modal('Account entfernen', `${a.name} wird von diesem Gerät entfernt.`, null, (close) => [
    h('button', { class: 'btn', onclick: close }, 'Abbrechen'),
    h('button', { class: 'btn primary', onclick: async () => {
      S.accounts = S.accounts.filter(x => x.id !== a.id);
      if (S.active === a.id) S.active = S.accounts[0]?.id || null;
      saveAccounts(); store.set('active', S.active);
      if (a.type === 'ms') api('/auth/forget', { method: 'POST', body: { uuid: a.uuid } }).catch(() => {});
      close(); render();
    } }, 'Entfernen')]);
}

async function clearCache() {
  const r = await navigator.storage.getDirectory();
  for await (const k of r.keys()) { try { await r.removeEntry(k, { recursive: true }); } catch {} }
  toast('Cache geleert.');
}

async function play(i, a) {
  let auth = { name: a.name, uuid: a.uuid || '', token: '0', type: a.type };
  if (a.type === 'ms') {
    try { auth = { ...auth, ...(await api('/auth/token', { method: 'POST', body: { uuid: a.uuid } })) }; }
    catch (e) { toast('Token-Erneuerung fehlgeschlagen. Bitte neu anmelden.'); return; }
  }
  const g = h('div', { class: 'game' });
  const canvas = h('canvas', { tabindex: 0 });
  const step = h('div', { class: 'step' }, 'Starte …');
  const bar = h('i');
  const sub = h('div', { class: 'sub' }, '');
  const load = h('div', { class: 'load' }, h('div', { class: 'box' }, step, h('div', { class: 'bar' }, bar), sub));
  g.append(canvas, load);
  document.body.append(g);
  const s = S.settings;
  const args = ['--username', auth.name, '--accessToken', auth.token || '0', '--version', i.version, '--gameDir', '/mc', '--assetsDir', '/mc/assets', '--assetIndex', i.assetIndex || '34'];
  if (auth.uuid) args.push('--uuid', auth.uuid);
  if (auth.type === 'ms') args.push('--userType', 'msa');
  const base = i.build === 'dev' ? '../game/' : `/builds/${i.build}/`;
  boot(canvas, {
    worker: '../game/worker.js',
    wasm: base + 'mc.wasm',
    runtime: base + 'mc.wasm-runtime.js',
    args,
    onmessage(m) {
      if (m.status === 'running') { step.textContent = 'Minecraft startet …'; bar.style.transform = 'scaleX(0.6)'; }
      if (m.status === 'frame') load.remove();
      if (m.status === 'crash') { step.textContent = 'Absturz'; sub.textContent = m.error; }
      if (m.progress) { step.textContent = m.progress.text; bar.style.transform = 'scaleX(' + m.progress.p + ')'; }
    },
  });
}

const bg = $('.bg');
bg.style.backgroundImage = "url('../data/ui/panorama_0.png')";
load();
