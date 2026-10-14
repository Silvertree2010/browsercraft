D=$(cd "$(dirname "$0")" && pwd)
[ -f "$D/.bake.env" ] && . "$D/.bake.env"
: "${BC_HOST:?set BC_HOST=user@host (or create .bake.env)}"
BC_DIR=${BC_DIR:-browsercraft}
bash "$D/sync.sh"
ssh "$BC_HOST" "docker run --rm -e BC_INC=1 ${BC_XMX:+-e BC_XMX=$BC_XMX} --name bc-bake --cpus ${BC_CPUS:-6} --memory ${BC_MEM:-22g} -v ~/$BC_DIR/w:/w -v ~/$BC_DIR/mc:/w/mc -v ~/$BC_DIR/.m2:/root/.m2 maven:3-eclipse-temurin-25 bash /w/run.sh $* 2>&1 | grep -vE '^(WARNING|INFO: Download)' | tail -90"
