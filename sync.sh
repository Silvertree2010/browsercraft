D=$(cd "$(dirname "$0")" && pwd)
[ -f "$D/.bake.env" ] && . "$D/.bake.env"
: "${BC_HOST:?set BC_HOST=user@host (or create .bake.env)}"
rsync -a --delete --exclude mc --exclude out --exclude .m2 --exclude teavm-jars --exclude target --exclude cls --exclude jcls --exclude .git --exclude vendor --exclude naga-bc --exclude web --exclude srv --exclude problems.txt --exclude .bake.env "$D/" "$BC_HOST:${BC_DIR:-browsercraft}/w/"
