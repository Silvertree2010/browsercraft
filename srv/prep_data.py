import json, os, sys, zipfile, urllib.request

mc = sys.argv[1]
out = sys.argv[2]
os.makedirs(out, exist_ok=True)
v = json.load(open(os.path.join(mc, 'version.json')))
files = {}
z = zipfile.ZipFile(os.path.join(mc, 'client.jar'))
for e in z.infolist():
    n = e.filename
    if n.endswith('/') or n.endswith('.class') or n.startswith('META-INF/'):
        continue
    p = os.path.join(out, 'jar', n)
    os.makedirs(os.path.dirname(p), exist_ok=True)
    with open(p, 'wb') as f:
        f.write(z.read(n))
    files['/mc/jar/' + n] = e.file_size
ai = v['assetIndex']
ip = os.path.join(out, 'assets', 'indexes', ai['id'] + '.json')
os.makedirs(os.path.dirname(ip), exist_ok=True)
if not os.path.exists(ip):
    urllib.request.urlretrieve(ai['url'], ip)
files['/mc/assets/indexes/' + ai['id'] + '.json'] = os.path.getsize(ip)
idx = json.load(open(ip))
for k, o in idx['objects'].items():
    h = o['hash']
    files['/mc/assets/objects/' + h[:2] + '/' + h] = o['size']
json.dump({'files': files, 'version': v['id'], 'assetIndex': ai['id']}, open(os.path.join(out, 'manifest.json'), 'w'))
print(len(files), 'files')
ui = os.path.join(out, 'ui')
os.makedirs(ui, exist_ok=True)
for i in range(6):
    o = idx['objects'].get('minecraft/textures/gui/title/background/panorama_%d.png' % i)
    if o:
        p = os.path.join(ui, 'panorama_%d.png' % i)
        if not os.path.exists(p):
            urllib.request.urlretrieve('https://resources.download.minecraft.net/' + o['hash'][:2] + '/' + o['hash'], p)
zp = os.path.join(out, 'jar.zip')
with zipfile.ZipFile(zp, 'w', zipfile.ZIP_DEFLATED, compresslevel=6) as zo:
    for e in z.infolist():
        n = e.filename
        if n.endswith('/') or n.endswith('.class') or n.startswith('META-INF/'):
            continue
        zo.writestr(n, z.read(n))
import hashlib
h = hashlib.sha1(open(zp, 'rb').read()).hexdigest()[:16]
m = json.load(open(os.path.join(out, 'manifest.json')))
m['jar'] = {'url': 'jar.zip', 'hash': h, 'size': os.path.getsize(zp)}
m['files'] = {k: v for k, v in m['files'].items() if not k.startswith('/mc/jar/')}
json.dump(m, open(os.path.join(out, 'manifest.json'), 'w'))
print('jar.zip', os.path.getsize(zp), h)
