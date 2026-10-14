package jdk.jfr;

public final class FlightRecorder {
    public static boolean isAvailable() { return false; }
    public static boolean isInitialized() { return false; }
    public static FlightRecorder getFlightRecorder() { throw new IllegalStateException("no jfr"); }
}
