package jdk.internal.vm;

public class SharedThreadContainer implements AutoCloseable {
    public static SharedThreadContainer create(String n) { return new SharedThreadContainer(); }
    public static SharedThreadContainer create(Object p, String n) { return new SharedThreadContainer(); }
    public void start(Thread t) { t.start(); }
    public void onStart(Thread t) {}
    public void onExit(Thread t) {}
    public void close() {}
    public String name() { return "c"; }
}
