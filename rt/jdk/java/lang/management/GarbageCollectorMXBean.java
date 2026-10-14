package java.lang.management;

public interface GarbageCollectorMXBean {
    long getCollectionCount();
    long getCollectionTime();
    String getName();
}
