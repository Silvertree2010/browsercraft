package org.slf4j;

import org.slf4j.helpers.BasicMarkerFactory;

public class MarkerFactory {
    static final IMarkerFactory F = new BasicMarkerFactory();

    private MarkerFactory() {}

    public static Marker getMarker(String n) { return F.getMarker(n); }
    public static Marker getDetachedMarker(String n) { return F.getDetachedMarker(n); }
    public static IMarkerFactory getIMarkerFactory() { return F; }
}
