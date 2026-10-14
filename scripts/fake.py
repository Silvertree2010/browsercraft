import sys, bisect
st = []
for l in open(sys.argv[1]):
    o, i = l.split()
    st.append((int(o, 16), int(i)))
st.sort()
offs = [x[0] for x in st]
nm = {}
for l in open(sys.argv[2]):
    p = l.rstrip('\n').split('\t')
    if len(p) == 2: nm[int(p[0])] = p[1]
for l in sys.stdin:
    if 'fakeMethod(Throwable.java:' in l:
        o = int(l.split('Throwable.java:')[1].split(')')[0])
        k = bisect.bisect_right(offs, o) - 1
        print('    at ' + nm.get(st[k][1], str(st[k][1])) + ' +' + hex(o - offs[k]))
    else:
        print(l.rstrip('\n'))
