package com.mojang.logging;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LogQueues {
    static final HashMap<String, BlockingQueue<String>> M = new HashMap<>();

    public static BlockingQueue<String> getOrCreateQueue(String n) { return M.computeIfAbsent(n, k -> new LinkedBlockingQueue<>()); }
    public static String getNextLogEvent(String n) { var q = M.get(n); return q == null ? null : q.poll(); }
}
