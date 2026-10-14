package java.util;

public final class HexFormat {
    static final HexFormat L = new HexFormat(false, ""), U = new HexFormat(true, "");
    final boolean up;
    final String pre;
    HexFormat(boolean up, String pre) { this.up = up; this.pre = pre; }
    public HexFormat withPrefix(String p) { return new HexFormat(up, p); }
    public String prefix() { return pre; }
    public static HexFormat of() { return L; }
    public HexFormat withUpperCase() { return new HexFormat(true, pre); }
    public HexFormat withLowerCase() { return new HexFormat(false, pre); }
    public boolean isUpperCase() { return up; }
    char d(int v) { v &= 15; return (char) (v < 10 ? '0' + v : (up ? 'A' : 'a') + v - 10); }
    public String toHexDigits(byte b) { return "" + d(b >> 4) + d(b); }
    public String toHexDigits(char c) { return toHexDigits((long) c, 4); }
    public String toHexDigits(short s) { return toHexDigits((long) s, 4); }
    public String toHexDigits(int i) { return toHexDigits((long) i, 8); }
    public String toHexDigits(long l) { return toHexDigits(l, 16); }
    public String toHexDigits(long l, int n) { char[] c = new char[n]; for (int i = n - 1; i >= 0; i--) { c[i] = d((int) l); l >>>= 4; } return new String(c); }
    public String formatHex(byte[] b) { return formatHex(b, 0, b.length); }
    public String formatHex(byte[] b, int f, int t) { var sb = new StringBuilder(); for (int i = f; i < t; i++) sb.append(pre).append(d(b[i] >> 4)).append(d(b[i])); return sb.toString(); }
    public byte[] parseHex(CharSequence s) { byte[] r = new byte[s.length() / 2]; for (int i = 0; i < r.length; i++) r[i] = (byte) ((fromHexDigit(s.charAt(2 * i)) << 4) | fromHexDigit(s.charAt(2 * i + 1))); return r; }
    public static boolean isHexDigit(int c) { return Character.digit(c, 16) >= 0; }
    public static int fromHexDigit(int c) { int v = Character.digit(c, 16); if (v < 0) throw new NumberFormatException("not a hex digit"); return v; }
    public static int fromHexDigits(CharSequence s) { return fromHexDigits(s, 0, s.length()); }
    public static int fromHexDigits(CharSequence s, int f, int t) { if (t - f > 8) throw new IllegalArgumentException("string length greater than 8"); return (int) fromHexDigitsToLong(s, f, t); }
    public static long fromHexDigitsToLong(CharSequence s) { return fromHexDigitsToLong(s, 0, s.length()); }
    public static long fromHexDigitsToLong(CharSequence s, int f, int t) { if (t - f > 16) throw new IllegalArgumentException("string length greater than 16"); long v = 0; for (int i = f; i < t; i++) v = (v << 4) | fromHexDigit(s.charAt(i)); return v; }
}
