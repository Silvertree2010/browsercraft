package java.lang.management;

public interface ThreadMXBean {
    default ThreadInfo[] dumpAllThreads(boolean a, boolean b) { return new ThreadInfo[0]; }
    default ThreadInfo[] dumpAllThreads(boolean a, boolean b, int d) { return new ThreadInfo[0]; }
    default ThreadInfo getThreadInfo(long id) { return null; }
    default ThreadInfo[] getThreadInfo(long[] ids, boolean a, boolean b) { return new ThreadInfo[ids.length]; }
    default long getThreadCpuTime(long id) { return -1; }
    default boolean isThreadCpuTimeSupported() { return false; }
    default long[] findDeadlockedThreads() { return null; }
    default boolean isObjectMonitorUsageSupported() { return false; }
    default boolean isSynchronizerUsageSupported() { return false; }
}
