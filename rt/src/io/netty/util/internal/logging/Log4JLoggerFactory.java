package io.netty.util.internal.logging;

public class Log4JLoggerFactory extends InternalLoggerFactory {
    public static final InternalLoggerFactory INSTANCE = new Log4JLoggerFactory();
    public InternalLogger newInstance(String n) { throw new NoClassDefFoundError("log4j"); }
}
