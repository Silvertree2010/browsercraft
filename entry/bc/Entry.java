package bc;

public class Entry {
    static {
        bc.rt.Js.log("clinit");
    }

    public static void main(String[] a) throws Exception {
        System.setProperty("os.name", "Linux");
        System.setProperty("os.arch", "amd64");
        System.setProperty("java.version", "25");
        System.setProperty("user.home", "/home");
        System.setProperty("java.io.tmpdir", "/tmp");
        System.setProperty("io.netty.allocator.type", "unpooled");
        System.setProperty("io.netty.leakDetection.level", "disabled");
        System.setProperty("io.netty.noUnsafe", "true");
        var args = a.length > 0 ? a : new String[]{"--accessToken", "0", "--version", "26.3", "--username", "Dev", "--gameDir", "/mc", "--assetsDir", "/mc/assets", "--assetIndex", "34"};
        try {
            bc.rt.Vfs.install();
            if (a.length > 0 && a[0].equals("--bc-server")) {
                new java.io.File("/mc/saves").mkdirs();
                new java.io.File("/tmp").mkdirs();
                bc.net.Sp.srv(a[1], a[2], a[3].equals("1"), a.length > 4 ? a[4] : "");
                return;
            }
            bc.rt.Js.log("reg " + (net.minecraft.core.registries.Registries.DIMENSION == null ? "NULL" : "ok"));
            bc.rt.Js.log("uas " + (com.mojang.authlib.minecraft.UserApiService.OFFLINE_PROPERTIES == null ? "NULL" : "ok"));
            bc.rt.Sdl.install();
            bc.rt.Al.install();
            new java.io.File("/mc").mkdirs();
            new java.io.File("/tmp").mkdirs();
            net.minecraft.client.main.Main.main(args);
        } catch (Throwable t) {
            var sb = new StringBuilder("JAVA ");
            for (Throwable c = t; c != null; c = c.getCause()) sb.append(c.getClass().getName()).append(": ").append(c.getMessage()).append(" | ");
            bc.rt.Js.log(sb.toString());
            throw t;
        }
    }
}
