package java.lang.management;

public interface RuntimeMXBean {
    java.util.List<String> getInputArguments();
    long getUptime();
    long getStartTime();
}
