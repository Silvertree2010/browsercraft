import json,urllib.request,os,sys
m=json.load(urllib.request.urlopen("https://piston-meta.mojang.com/mc/game/version_manifest_v2.json"))
vid=sys.argv[1] if len(sys.argv)>1 else m["latest"]["release"]
v=[x for x in m["versions"] if x["id"]==vid][0]
d=json.load(urllib.request.urlopen(v["url"]))
os.makedirs("mc/libs",exist_ok=True)
def get(u,p):
    if not os.path.exists(p): urllib.request.urlretrieve(u,p)
get(d["downloads"]["client"]["url"],"mc/client.jar")
for l in d["libraries"]:
    a=l.get("downloads",{}).get("artifact")
    if not a or "natives" in l["name"]: continue
    if "rules" in l and not any(r.get("os",{}).get("name") in (None,"linux") and r["action"]=="allow" for r in l["rules"]): continue
    get(a["url"],"mc/libs/"+a["path"].split("/")[-1])
json.dump(d,open("mc/version.json","w"))
print(vid,len(os.listdir("mc/libs")))
