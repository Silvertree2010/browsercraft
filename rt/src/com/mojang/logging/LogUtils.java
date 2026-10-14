package com.mojang.logging;

import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.event.Level;
import bc.rt.Log;

public class LogUtils {
    public static final String FATAL_MARKER_ID = "FATAL";
    public static final Marker FATAL_MARKER = MarkerFactory.getMarker(FATAL_MARKER_ID);
    static final Logger L = new Log("mc");

    public static boolean isLoggerActive() { return true; }
    public static void configureRootLoggingLevel(Level l) { Log.min = l.toInt(); }
    public static Object defer(Supplier<Object> s) {
        return new Object() { public String toString() { return String.valueOf(s.get()); } };
    }
    public static Logger getLogger() { return L; }
}
