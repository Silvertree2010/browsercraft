package net.fabricmc.loader.impl;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.*;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import bc.fabric.Mods;

public final class FabricLoaderImpl implements FabricLoader {
    public static final FabricLoaderImpl INSTANCE = new FabricLoaderImpl();
    Object game;
    Path gameDir = Path.of("/mc");
    final ObjectShare share = new ObjectShare() {
        final Map<String, Object> m = new HashMap<>();
        public Object get(String k) { return m.get(k); }
        public void whenAvailable(String k, java.util.function.BiConsumer<String, Object> c) { var v = m.get(k); if (v != null) c.accept(k, v); }
        public Object put(String k, Object v) { return m.put(k, v); }
        public Object putIfAbsent(String k, Object v) { return m.putIfAbsent(k, v); }
        public Object remove(String k) { return m.remove(k); }
    };
    final MappingResolver maps = new MappingResolver() {
        public Collection<String> getNamespaces() { return List.of("official", "named", "intermediary"); }
        public String getCurrentRuntimeNamespace() { return "named"; }
        public String mapClassName(String ns, String c) { return c; }
        public String unmapClassName(String ns, String c) { return c; }
        public String mapFieldName(String ns, String o, String n, String d) { return n; }
        public String mapMethodName(String ns, String o, String n, String d) { return n; }
    };

    public void prepareModInit(Path dir, Object game) { this.gameDir = dir; this.game = game; }

    @SuppressWarnings("unchecked")
    public <T> List<T> getEntrypoints(String key, Class<T> type) {
        var r = new ArrayList<T>();
        for (var c : Mods.entrypoints(key)) r.add((T) c.make());
        return r;
    }

    @SuppressWarnings("unchecked")
    public <T> List<EntrypointContainer<T>> getEntrypointContainers(String key, Class<T> type) {
        var r = new ArrayList<EntrypointContainer<T>>();
        for (var c : Mods.entrypoints(key)) {
            T v = (T) c.make();
            var mod = getModContainer(c.mod()).orElseThrow();
            r.add(new EntrypointContainer<T>() {
                public T getEntrypoint() { return v; }
                public ModContainer getProvider() { return mod; }
                public String getDefinition() { return c.def(); }
            });
        }
        return r;
    }

    public <T> void invokeEntrypoints(String key, Class<T> type, Consumer<? super T> inv) {
        for (var e : getEntrypoints(key, type)) {
            try { inv.accept(e); } catch (Throwable t) { throw new RuntimeException("entrypoint " + key + " failed", t); }
        }
    }

    public ObjectShare getObjectShare() { return share; }
    public MappingResolver getMappingResolver() { return maps; }
    public Optional<ModContainer> getModContainer(String id) { return Optional.ofNullable(Mods.byId().get(id)); }
    public Collection<ModContainer> getAllMods() { return Collections.unmodifiableCollection(Mods.byId().values()); }
    public boolean isModLoaded(String id) { return Mods.byId().containsKey(id); }
    public boolean isDevelopmentEnvironment() { return false; }
    public EnvType getEnvironmentType() { return EnvType.CLIENT; }
    public String getRawGameVersion() { return Mods.gameVersion(); }
    public Object getGameInstance() { return game; }
    public Path getGameDir() { return gameDir; }
    public File getGameDirectory() { return gameDir.toFile(); }
    public Path getConfigDir() { var p = gameDir.resolve("config"); p.toFile().mkdirs(); return p; }
    public File getConfigDirectory() { return getConfigDir().toFile(); }
    public String[] getLaunchArguments(boolean s) { return new String[0]; }
}
