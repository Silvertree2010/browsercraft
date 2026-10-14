package java.net;

public final class IDN {
    public static final int ALLOW_UNASSIGNED = 1;
    public static final int USE_STD3_ASCII_RULES = 2;
    public static String toASCII(String s) { return s; }
    public static String toASCII(String s, int f) { return s; }
    public static String toUnicode(String s) { return s; }
    public static String toUnicode(String s, int f) { return s; }
}
