D=~/projects/private/browsercraft/work/out/syms
cd ~/projects/private/browsercraft/work/scripts/pt
f=$1
for lvl in $(seq 1 ${2:-6}); do
  n=$(awk -F'\t' -v i=$f '$1==i{print $2}' $D/fnames.tsv)
  s=$(grep -nE "\(func \(;$f;\)" $D/mco.wat | head -1 | cut -d: -f1)
  [ -z "$s" ] && s=$(grep -nF "(func \$$n " $D/mco.wat | head -1 | cut -d: -f1)
  sed -n "$s,$((s+20000))p" $D/mco.wat | awk 'NR>1 && /^\(;@[0-9a-f]+;\)  \(func /{exit} {print}' > /tmp/dsc.wat
  grep -E "call [0-9\$]|call_ref" /tmp/dsc.wat | grep -v "Fiber::" | head -60 > /tmp/dsc.calls
  B=$(grep -oE "^\(;@[0-9a-f]+" /tmp/dsc.calls | cut -c4- | tr '\n' ' ')
  [ -z "$B" ] && { echo "L$lvl $n: keine Aufrufe"; break; }
  node dbg.mjs http://localhost:8090/game/index.html ${T:-20} $B > /tmp/dsc.out 2>&1
  last=$(grep -a " HIT 0x" /tmp/dsc.out | grep -a "| \$$(printf '%s' "$n" | sed 's/[]\/$*.^[]/\\&/g') " | tail -1 | grep -oE "HIT 0x[0-9a-f]+" | cut -c7-)
  [ -z "$last" ] && { echo "L$lvl $n: kein Treffer"; break; }
  l=$(grep "(;@$last;)" /tmp/dsc.calls)
  t=$(echo "$l" | grep -oE "call(_ref)? [^ ]+")
  id=$(echo "$t" | awk '{print $2}')
  case "$t" in call\ \$*) cn=${id#\$}; id=$(awk -F'\t' -v x="$cn" '$2==x{print $1}' $D/fnames.tsv | head -1);; esac
  cn=$(awk -F'\t' -v i="$id" '$1==i{print $2}' $D/fnames.tsv)
  echo "L$lvl $n -> letzter Aufruf @$last: $t $cn"
  case "$t" in call_ref*) echo "  (virtueller Aufruf, Abstieg stoppt)"; break;; esac
  f=$id
done
