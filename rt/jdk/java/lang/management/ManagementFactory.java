package java.lang.management;

import java.util.List;

public class ManagementFactory {
    static final long T0 = System.currentTimeMillis();
    public static RuntimeMXBean getRuntimeMXBean() {
        return new RuntimeMXBean() {
            public List<String> getInputArguments() { return List.of(); }
            public long getUptime() { return System.currentTimeMillis() - T0; }
            public long getStartTime() { return T0; }
        };
    }
    public static ThreadMXBean getThreadMXBean() { return new ThreadMXBean() {}; }
    public static List<GarbageCollectorMXBean> getGarbageCollectorMXBeans() { return List.of(); }
    public static MemoryMXBean getMemoryMXBean() {
        return new MemoryMXBean() {
            public MemoryUsage getHeapMemoryUsage() { long t = Runtime.getRuntime().totalMemory(); return new MemoryUsage(t, t - Runtime.getRuntime().freeMemory(), t, t); }
            public MemoryUsage getNonHeapMemoryUsage() { return new MemoryUsage(0, 0, 0, -1); }
        };
    }
}
