package bc.rt;

public final class Gu {
    private Gu() {}

    public static boolean z(Object o) { return o instanceof Boolean b && b; }
    public static byte b(Object o) { return o instanceof Number n ? n.byteValue() : 0; }
    public static char c(Object o) { return o instanceof Character c ? c : 0; }
    public static short s(Object o) { return o instanceof Number n ? n.shortValue() : 0; }
    public static int i(Object o) { return o instanceof Number n ? n.intValue() : 0; }
    public static long j(Object o) { return o instanceof Number n ? n.longValue() : 0; }
    public static float f(Object o) { return o instanceof Number n ? n.floatValue() : 0; }
    public static double d(Object o) { return o instanceof Number n ? n.doubleValue() : 0; }
}
