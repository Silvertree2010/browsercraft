package com.mojang.text2speech;

import org.slf4j.Logger;

public interface Narrator {
    Logger LOGGER = com.mojang.logging.LogUtils.getLogger();
    Narrator EMPTY = new Narrator() {
        public void say(String m, boolean i, float v) {}
        public void clear() {}
        public boolean active() { return false; }
        public void destroy() {}
    };
    void say(String m, boolean i, float v);
    void clear();
    default boolean active() { return true; }
    void destroy();
    static Narrator getNarrator() { return EMPTY; }
}
