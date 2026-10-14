package com.mojang.logging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.event.Level;

public class LogListeners {
    static final HashMap<String, List<Listener>> M = new HashMap<>();

    public interface Listener { void accept(String s, Level l); }

    public static Object getOrCreateTarget(String n) { return M.computeIfAbsent(n, k -> new ArrayList<>()); }
    public static void addListener(String n, Listener l) { M.computeIfAbsent(n, k -> new ArrayList<>()).add(l); }
    public static void emit(String s, Level l) {
        for (var ls : M.values()) for (var x : ls) x.accept(s, l);
    }
}
