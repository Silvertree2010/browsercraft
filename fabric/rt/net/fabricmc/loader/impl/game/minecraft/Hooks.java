package net.fabricmc.loader.impl.game.minecraft;

import java.io.File;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.impl.FabricLoaderImpl;

public final class Hooks {
    public static final String INTERNAL_NAME = "net/fabricmc/loader/impl/game/minecraft/Hooks";
    public static final String FABRIC = "fabric";
    public static final String VANILLA = "vanilla";
    public static String appletMainClass;

    public static String insertBranding(String b) {
        if (b == null || b.isEmpty()) return FABRIC;
        return b.equals(VANILLA) ? FABRIC : b + "/" + FABRIC;
    }

    public static void startClient(File dir, Object game) {
        var l = FabricLoaderImpl.INSTANCE;
        l.prepareModInit(dir == null ? new File("/mc").toPath() : dir.toPath(), game);
        l.invokeEntrypoints("main", ModInitializer.class, ModInitializer::onInitialize);
        l.invokeEntrypoints("client", ClientModInitializer.class, ClientModInitializer::onInitializeClient);
    }

    public static void startServer(File dir, Object game) {
        var l = FabricLoaderImpl.INSTANCE;
        l.prepareModInit(dir == null ? new File("/mc").toPath() : dir.toPath(), game);
        l.invokeEntrypoints("main", ModInitializer.class, ModInitializer::onInitialize);
    }
}
