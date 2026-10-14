import zipfile,sys,glob,os
sys.path.insert(0,'.')
from cpscan import utf8s
pre=sys.argv[1].encode(); excl=sys.argv[2].encode() if len(sys.argv)>2 else pre
W=os.path.join(os.path.dirname(os.path.abspath(__file__)),'..'); jars=[W+'/mc/client.jar']+glob.glob(W+'/mc/libs/*.jar')
for j in jars:
    z=zipfile.ZipFile(j)
    for n in z.namelist():
        if not n.endswith('.class') or n.startswith(excl.decode()): continue
        try: u=utf8s(z.read(n))
        except Exception: continue
        if any(pre in s for s in u): print(j.split('/')[-1], n)
