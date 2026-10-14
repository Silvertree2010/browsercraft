package java.lang.management;

public class ThreadInfo {
    public long getThreadId() { return 0; }
    public String getThreadName() { return ""; }
    public Thread.State getThreadState() { return Thread.State.RUNNABLE; }
    public StackTraceElement[] getStackTrace() { return new StackTraceElement[0]; }
    public boolean isDaemon() { return false; }
    public String toString() { return ""; }
}
