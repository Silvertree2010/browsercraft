set -e
cd "$(dirname "$0")/.."
[ -f .bake.env ] && . ./.bake.env
scp -q ${BC_HOST:?set BC_HOST}:${BC_DIR:-browsercraft}/w/out/wasm/mc.wasm web/game/mc.wasm
~/.cargo/bin/wasm-tools validate --features all web/game/mc.wasm 2>&1 | head -4 || true
pgrep -f "srv/dev.py" >/dev/null || (python3 srv/dev.py web data 8090 > /tmp/dev.log 2>&1 &)
sleep 1
cd scripts/pt && node run.mjs http://localhost:8090/game/index.html ${1:-60} /tmp/g1.png 2>&1 | grep -v "^\s*$" > /tmp/game.log || true
tail -${2:-60} /tmp/game.log
