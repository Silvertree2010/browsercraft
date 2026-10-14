D=~/projects/private/browsercraft/work/out/syms
nm(){ awk -F'\t' -v i=$1 '$1==i{print $2}' $D/fnames.tsv; }
id(){ awk -F'\t' -v n="$1" '$2==n{print $1}' $D/fnames.tsv | head -1; }
body(){ local n=$(nm $1); local s=$(grep -nF -e "(func (;$1;)" -e "(func \$$n " $D/mco.wat | head -1 | cut -d: -f1); [ -z "$s" ] && return; sed -n "${s},$((s+4000))p" $D/mco.wat | awk 'NR>1 && /\(func /{exit} {print}'; }
asy(){ body $1 | grep -B2 "isPresentAndSuspending" | grep -oE "call(_ref)? [^ ]+" | grep -v Fiber | sort -u; }
f=$(id "$1"); [ -z "$f" ] && f=$1
for d in $(seq 1 ${2:-15}); do
  echo "$f $(nm $f)"; a=$(asy $f); echo "$a" | sed 's/^/     /' | head -8
  n=$(echo "$a" | grep -oE "^call [0-9]+" | head -1 | awk '{print $2}')
  if [ -z "$n" ]; then n=$(echo "$a" | grep -oE '^call \$[^ ]+' | head -1 | sed 's/call \$//'); [ -n "$n" ] && n=$(id "$n"); fi
  [ -z "$n" ] && break; f=$n
done
