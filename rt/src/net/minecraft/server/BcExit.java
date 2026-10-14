package net.minecraft.server;

public final class BcExit {
    public static void exit(MinecraftServer s) {
        try { s.onServerExit(); } finally { bc.net.Sp.exited(); }
    }
}
