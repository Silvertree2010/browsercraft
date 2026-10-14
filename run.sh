set -e
cd /w
T0=$(date +%s)
LIBS=$(ls mc/libs/*.jar | grep -vEf rules/exclude.txt | tr "\n" ":")
CP="mc/client.jar:$LIBS"
if [ ! -f tool/cp.txt ] || [ tool/pom.xml -nt tool/cp.txt ] || [ -n "$(find tool/src -newer tool/cp.txt)" ]; then
  (cd tool && mvn -q -B -o compile dependency:build-classpath -Dmdep.outputFile=cp.txt 2>/dev/null || mvn -q -B compile dependency:build-classpath -Dmdep.outputFile=cp.txt)
  touch tool/cp.txt
fi
TCP="tool/target/classes:$(cat tool/cp.txt)"
rm -rf rt/cls rt/jcls entry/cls && mkdir -p rt/cls rt/jcls entry/cls out
javac -nowarn -XDignore.symbol.file --patch-module java.base=rt/jdk -d rt/jcls $(find rt/jdk -name "*.java")
cp -r rt/jcls/. rt/cls/
javac -nowarn --patch-module java.desktop=rt/jdkd -d rt/cls $(find rt/jdkd -name "*.java")
javac -nowarn --add-modules jdk.jfr --patch-module jdk.jfr=rt/jdkj -d rt/cls $(find rt/jdkj -name "*.java")
javac -nowarn --patch-module java.base=rt/jcls -d rt/cls -cp "rt/cls:$CP:$(cat tool/cp.txt)" $(find rt/src -name "*.java")
javac -d entry/cls -cp "rt/cls:$CP:$(cat tool/cp.txt)" entry/bc/Entry.java
rm -f rt/cls/bc/gen/Sig.class rt/cls/bc/gen/Gs.class
java -cp "$TCP" bc.tool.Prep rules out/prep.jar mc/client.jar ${LIBS//:/ }
echo prep $(( $(date +%s) - T0 ))s
[ "$1" = prep ] && exit 0
rm -rf out/wasm && mkdir -p out/wasm
java -Xmx${BC_XMX:-20g} -Xlog:gc:file=out/gc.log:uptime -cp "$TCP" bc.tool.Bake "entry/cls:rt/cls:out/prep.jar:${CP%:}" out/wasm bc.Entry ${2:-SIMPLE}
echo total $(( $(date +%s) - T0 ))s
