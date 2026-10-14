package io.netty.util.internal.logging;

public class Log4J2LoggerFactory extends InternalLoggerFactory {
    public static final InternalLoggerFactory INSTANCE = new Log4J2LoggerFactory();
    public InternalLogger newInstance(String n) { throw new NoClassDefFoundError("log4j"); }
}
