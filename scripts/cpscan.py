import zipfile,re,struct,collections,sys
def utf8s(d):
    n=struct.unpack('>H',d[8:10])[0];i=10;k=1;out=[]
    while k<n:
        t=d[i]
        if t==1:
            l=struct.unpack('>H',d[i+1:i+3])[0];out.append(d[i+3:i+3+l]);i+=3+l
        elif t in(3,4):i+=5
        elif t in(5,6):i+=9;k+=1
        elif t in(7,8,16,19,20):i+=3
        elif t in(9,10,11,12,17,18):i+=5
        elif t==15:i+=4
        else:break
        k+=1
    return out
pat=re.compile(r'%(\d+\$)?([-#+ 0,(<]*)(\d+)?(\.\d+)?([a-zA-Z%])')
ok=set('bBhHsScCdDoOxXf%n')
bad=collections.defaultdict(set)
if __name__=="__main__":
 for jar in sys.argv[1:]:
   z=zipfile.ZipFile(jar)
   for n in z.namelist():
     if not n.endswith('.class'):continue
     for s in utf8s(z.read(n)):
         s=s.decode('utf8','replace')
         if '%' not in s or s.startswith('%') and ' ' not in s:continue
         for m in pat.finditer(s):
             if m.group(5) not in ok or m.group(4) and m.group(5)!='f' and m.group(5) not in 'sS':
                 bad[m.group(0)].add((n.split('/')[-1],s[:70]))
 for c,v in sorted(bad.items()):
     print(repr(c),len(v));[print('   ',x) for x in list(v)[:3]]
 