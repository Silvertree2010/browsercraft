set -e
W=~/projects/private/browsercraft/work/web/game/mc.wasm
D=~/projects/private/browsercraft/work/out/syms
mkdir -p $D
node ~/projects/private/browsercraft/work/scripts/names.mjs $W $D/fnames.tsv
~/.cargo/bin/wasm-tools print --print-offsets $W > $D/mco.wat
grep -E "^\(;@[0-9a-f]+;\)  \(func \(;[0-9]+;\)" $D/mco.wat | sed -E 's/^\(;@([0-9a-f]+);\)  \(func \(;([0-9]+);\).*/\1 \2/' > $D/fstart.txt
