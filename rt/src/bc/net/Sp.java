package bc.net;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.WorldStem;
import net.minecraft.server.notifications.NotificationManager;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.teavm.jso.JSBody;

public final class Sp {
    @JSBody(params = {"w", "u", "c", "x"}, script = "bc.post({ k: 'srv', w: w, u: u, c: c, x: x });")
    static native void spawn(String w, String u, boolean c, String x);

    public static void load(Minecraft mc, LevelStorageSource.LevelStorageAccess acc, PackRepository pr, WorldStem ws, Optional<GameRules> gr, boolean nw) {
        mc.disconnectWithProgressScreen();
        var d = ws.worldDataAndGenSettings().data();
        String id = acc.getLevelId(), nm = d.getLevelName();
        boolean c = d.isAllowCommands();
        String x = "difficulty=" + d.getDifficulty().getSerializedName() + "\ngamemode=" + d.getGameType().getName() + "\nhardcore=" + d.isHardcore() + "\n";
        acc.saveDataTag(d);
        try {
            var gs = ws.worldDataAndGenSettings().genSettings();
            var st = new net.minecraft.world.level.storage.SavedDataStorage(acc.getLevelPath(net.minecraft.world.level.storage.LevelResource.DATA), net.minecraft.util.datafix.DataFixers.getDataFixer(), ws.registries().compositeAccess());
            st.set(net.minecraft.world.level.levelgen.WorldGenSettings.TYPE, gs);
            gs.setDirty();
            if (gr.isPresent()) new net.minecraft.world.level.gamerules.GameRules(d.enabledFeatures(), st.computeIfAbsent(net.minecraft.world.level.gamerules.GameRuleMap.TYPE)).setAll(gr.get(), null);
            st.saveAndJoin();
            st.close();
        } catch (Exception e) {
            bc.rt.Js.log("sp gen " + e);
        }
        ws.close();
        try { acc.close(); } catch (Exception e) { bc.rt.Js.log("sp close " + e); }
        spawn(id, mc.getUser().getName(), c, x);
        ConnectScreen.startConnecting(new TitleScreen(), mc, new ServerAddress("bc-local", 25565), new ServerData(nm, "bc-local", ServerData.Type.OTHER), false, null);
    }

    static boolean loc(Minecraft mc) {
        var s = mc.getCurrentServer();
        return s != null && "bc-local".equals(s.ip);
    }

    public static net.minecraft.network.chat.Component lbl(boolean b) {
        return net.minecraft.network.chat.CommonComponents.disconnectButtonLabel(b || loc(Minecraft.getInstance()));
    }

    public static void toast(net.minecraft.client.gui.components.toasts.ToastManager m, net.minecraft.client.gui.components.toasts.Toast t) {
        if (t.getToken() == net.minecraft.client.gui.components.toasts.SystemToast.SystemToastId.UNSECURE_SERVER_WARNING && loc(Minecraft.getInstance())) return;
        m.addToast(t);
    }

    public static void dis(Minecraft mc, net.minecraft.network.chat.Component m) {
        if (!loc(mc)) { mc.disconnectFromWorld(m); return; }
        if (mc.level != null) mc.level.disconnect(m);
        mc.disconnectWithSavingScreen();
        mc.gui.setScreen(new TitleScreen());
    }

    @JSBody(params = {}, script = "bc.post({ k: 'srvexit' });")
    static native void exited0();

    public static void exited() { bc.rt.Js.log("SRV EXIT"); exited0(); }

    public static void left(net.minecraft.server.players.PlayerList pl, net.minecraft.server.level.ServerPlayer p) {
        pl.remove(p);
        if (pl.getPlayerCount() == 0) pl.getServer().halt(false);
    }

    @JSBody(params = {"n"}, script = "bc.post({ k: 'pause', n: n });")
    static native void pause0(boolean n);
    @JSBody(params = {}, script = "return !!bc.paused;")
    static native boolean paused();
    static boolean cp, sp;

    public static void ctick(Minecraft mc) {
        mc.tick();
        var sc = mc.gui.screen();
        boolean p = mc.level != null && sc != null && sc.isPauseScreen();
        if (p != cp) { cp = p; pause0(p); }
    }

    public static void cons(net.minecraft.server.dedicated.DedicatedServer s) {
        s.handleConsoleInputs();
        boolean p = paused();
        if (p != sp) { sp = p; s.tickRateManager().setFrozen(p); bc.rt.Js.log("SRV FROZEN " + p); }
    }

    public static void started(NotificationManager n) {
        n.serverStarted();
        PipeServer.ready();
        bc.rt.Js.log("SRV READY");
    }

    public static java.security.KeyPair kp() { return null; }
    public static net.minecraft.server.network.ServerTextFilter tf(net.minecraft.server.dedicated.DedicatedServerProperties p) { return null; }
    public static net.minecraft.server.jsonrpc.ManagementServer rpc(net.minecraft.server.dedicated.DedicatedServerSettings s, NotificationManager n) { return null; }
    public static net.minecraft.server.rcon.thread.RconThread rcon(net.minecraft.server.ServerInterface s) { return null; }
    public static net.minecraft.server.rcon.thread.QueryThreadGs4 query(net.minecraft.server.ServerInterface s) { return null; }
    public static void jmx(net.minecraft.server.MinecraftServer s) {}
    public static void gui(Object o) {}
    public static void pid(Path p) {}
    public static void help(Object p, Object o) {}
    public static boolean yes() { return true; }

    public static void pstore(java.util.Properties p, java.io.Writer w, String c) throws java.io.IOException {
        if (c != null) w.write("#" + c + "\n");
        for (var k : new java.util.TreeSet<>(p.stringPropertyNames())) w.write(esc(k, true) + "=" + esc(p.getProperty(k), false) + "\n");
        w.flush();
    }

    static String esc(String s, boolean k) {
        var b = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            switch (ch) {
                case '\\' -> b.append("\\\\");
                case '\n' -> b.append("\\n");
                case '\r' -> b.append("\\r");
                case '\t' -> b.append("\\t");
                case '=', ':', '#', '!' -> b.append('\\').append(ch);
                case ' ' -> b.append(k || i == 0 ? "\\ " : " ");
                default -> b.append(ch);
            }
        }
        return b.toString();
    }

    public static void srv(String w, String u, boolean c, String x) throws Exception {
        Files.writeString(Path.of("/mc/server.properties"), String.join("\n",
            "online-mode=false", "server-port=25565", "server-ip=", "level-name=" + w, "max-tick-time=-1",
            "network-compression-threshold=-1", "region-file-compression=none", "sync-chunk-writes=false",
            "spawn-protection=0", "white-list=false", "enforce-whitelist=false", "enable-query=false", "enable-rcon=false", "management-server-enabled=false",
            "enable-status=true", "view-distance=8", "simulation-distance=6", "max-players=8", "") + x);
        Files.writeString(Path.of("/mc/eula.txt"), "eula=true\n");
        String ops = c ? "[{\"uuid\":\"" + UUIDUtil.createOfflinePlayerUUID(u) + "\",\"name\":\"" + u + "\",\"level\":4,\"bypassesPlayerLimit\":true}]" : "[]";
        Files.writeString(Path.of("/mc/ops.json"), ops);
        net.minecraft.server.Main.main(new String[]{"--nogui", "--universe", "/mc/saves", "--world", w});
    }
}
