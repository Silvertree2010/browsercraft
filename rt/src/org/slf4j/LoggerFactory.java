package org.slf4j;

import bc.rt.Log;

public final class LoggerFactory {
    public static final String PROVIDER_PROPERTY_KEY = "slf4j.provider";
    static final ILoggerFactory F = Log::new;

    private LoggerFactory() {}

    public static Logger getLogger(String n) { return new Log(n); }
    public static Logger getLogger(Class<?> c) { return new Log(c.getSimpleName()); }
    public static ILoggerFactory getILoggerFactory() { return F; }
}
