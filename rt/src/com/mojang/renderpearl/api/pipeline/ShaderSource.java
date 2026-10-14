package com.mojang.renderpearl.api.pipeline;

import net.minecraft.resources.Identifier;

public interface ShaderSource extends AutoCloseable {
    String getShader(Identifier id, ShaderType type);

    ShaderSource.CachedIncludeSource getInclude(Identifier id);

    @Override
    void close();

    record CachedIncludeSource(String name, String content, boolean error) implements AutoCloseable {
        public static CachedIncludeSource create(Identifier id, String source) { return new CachedIncludeSource(id.toString(), source, false); }
        public static CachedIncludeSource createError(String message) { return new CachedIncludeSource("", message, true); }
        public long includeResultPtr() { return 0; }
        @Override public void close() {}
    }
}
