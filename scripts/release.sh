set -e
cd "$(dirname "$0")/.."
V=${1:-0.1.0}
R=out/release/Browsercraft
rm -rf out/release && mkdir -p $R/web/game $R/data/assets/indexes
(cd local
 GOOS=darwin GOARCH=arm64 go build -trimpath -ldflags "-s -w" -o ../out/release/mac-arm64 .
 GOOS=darwin GOARCH=amd64 go build -trimpath -ldflags "-s -w" -o ../out/release/mac-amd64 .
 GOOS=windows GOARCH=amd64 go build -trimpath -ldflags "-s -w" -o ../$R/Browsercraft.exe .
 GOOS=linux GOARCH=amd64 go build -trimpath -ldflags "-s -w" -o ../$R/browsercraft-linux .)
lipo -create -output "$R/Browsercraft (Mac).command" out/release/mac-arm64 out/release/mac-amd64
codesign -s - --force "$R/Browsercraft (Mac).command" 2>/dev/null || true
for f in index.html boot.js worker.js io.js gpu.js audio.js mc.wasm mc.wasm-runtime.js naga.wasm; do cp web/game/$f $R/web/game/; done
cp data/manifest.json data/jar.zip $R/data/
cp -r data/ui $R/data/
cp data/assets/indexes/*.json $R/data/assets/indexes/
cat > $R/README.txt <<EOT
Browsercraft $V - Minecraft: Java Edition 26.3 in your browser (singleplayer)

1. Start the launcher for your system:
     Mac:     Browsercraft (Mac).command
     Windows: Browsercraft.exe
     Linux:   ./browsercraft-linux
2. Your browser opens http://localhost:8090/game/ - pick a player name and play.
   Use Chrome or Edge (needs WebGPU). Keep the launcher window open while playing.

Worlds are saved in the browser (site data for localhost:8090). Clearing site
data deletes them. Game sounds and textures are downloaded from Mojang the first
time they are needed, so the first start needs internet.

Mac: the launcher is not notarised. If macOS blocks it, right-click > Open, or run
  xattr -dr com.apple.quarantine "Browsercraft (Mac).command"
Windows: SmartScreen may warn: "More info" > "Run anyway".

Not affiliated with Mojang or Microsoft. Minecraft is (c) Mojang AB. Please own the game.
EOT
(cd out/release && rm -f mac-arm64 mac-amd64 && zip -qr -9 Browsercraft-$V.zip Browsercraft)
ls -la out/release
