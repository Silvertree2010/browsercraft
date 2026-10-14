import json, os, sys, urllib.request, urllib.parse

ver = sys.argv[1]
loader = sys.argv[2]
mods = [m for m in sys.argv[3].split(',') if m]
out = sys.argv[4]
os.makedirs(os.path.join(out, 'libs'), exist_ok=True)
os.makedirs(os.path.join(out, 'mods'), exist_ok=True)
UA = {'User-Agent': 'browsercraft/0.1 (private)'}

def get(u):
    return urllib.request.urlopen(urllib.request.Request(u, headers=UA)).read()

prof = json.loads(get(f'https://meta.fabricmc.net/v2/versions/loader/{ver}/{loader}/profile/json'))
for l in prof['libraries']:
    g, a, v = l['name'].split(':')[:3]
    p = f"{g.replace('.', '/')}/{a}/{v}/{a}-{v}.jar"
    f = os.path.join(out, 'libs', f'{a}-{v}.jar')
    if not os.path.exists(f):
        open(f, 'wb').write(get(l['url'].rstrip('/') + '/' + p))

seen = {}
def add(pid):
    if pid in seen:
        return
    q = urllib.parse.urlencode({'game_versions': json.dumps([ver]), 'loaders': json.dumps(['fabric'])})
    vs = json.loads(get(f'https://api.modrinth.com/v2/project/{pid}/version?{q}'))
    if not vs:
        print('no version for', pid, file=sys.stderr)
        sys.exit(2)
    v = vs[0]
    seen[pid] = v
    fl = [f for f in v['files'] if f.get('primary')] or v['files']
    f = fl[0]
    p = os.path.join(out, 'mods', f['filename'])
    if not os.path.exists(p):
        open(p, 'wb').write(get(f['url']))
    for d in v.get('dependencies', []):
        if d.get('dependency_type') == 'required' and d.get('project_id'):
            add(d['project_id'])

for m in mods:
    add(m)
json.dump({k: {'id': v['project_id'], 'version': v['version_number'], 'name': v['name']} for k, v in seen.items()}, open(os.path.join(out, 'mods.json'), 'w'), indent=1)
print(len(seen), 'mods')
