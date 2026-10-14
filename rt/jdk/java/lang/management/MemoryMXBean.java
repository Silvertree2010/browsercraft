package java.lang.management;

public interface MemoryMXBean {
    MemoryUsage getHeapMemoryUsage();
    MemoryUsage getNonHeapMemoryUsage();
}
