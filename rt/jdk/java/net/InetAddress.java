package java.net;

public class InetAddress implements java.io.Serializable {
    final String host;
    final byte[] addr;

    InetAddress(String host, byte[] addr) { this.host = host; this.addr = addr; }

    public static InetAddress getByName(String h) throws UnknownHostException {
        if (h == null || h.isEmpty() || h.equals("localhost")) return new Inet4Address("localhost", new byte[]{127, 0, 0, 1});
        var b = parse4(h);
        if (b != null) return new Inet4Address(null, b);
        return new Inet4Address(h, new byte[]{0, 0, 0, 0});
    }
    public static InetAddress[] getAllByName(String h) throws UnknownHostException { return new InetAddress[]{getByName(h)}; }
    public static InetAddress getByAddress(byte[] a) throws UnknownHostException { return getByAddress(null, a); }
    public static InetAddress getByAddress(String h, byte[] a) throws UnknownHostException {
        if (a.length == 4) return new Inet4Address(h, a.clone());
        if (a.length == 16) return new Inet6Address(h, a.clone());
        throw new UnknownHostException("addr is of illegal length");
    }
    public static InetAddress getLocalHost() { return new Inet4Address("localhost", new byte[]{127, 0, 0, 1}); }
    public static InetAddress getLoopbackAddress() { return getLocalHost(); }

    static byte[] parse4(String s) {
        var p = s.split("\\.");
        if (p.length != 4) return null;
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            try { int v = Integer.parseInt(p[i]); if (v < 0 || v > 255) return null; b[i] = (byte) v; } catch (NumberFormatException e) { return null; }
        }
        return b;
    }

    public String getHostName() { return host != null ? host : getHostAddress(); }
    public String getCanonicalHostName() { return getHostName(); }
    public byte[] getAddress() { return addr.clone(); }
    public String getHostAddress() {
        if (addr.length == 4) return (addr[0] & 255) + "." + (addr[1] & 255) + "." + (addr[2] & 255) + "." + (addr[3] & 255);
        var sb = new StringBuilder();
        for (int i = 0; i < 16; i += 2) { if (i > 0) sb.append(':'); sb.append(Integer.toHexString(((addr[i] & 255) << 8) | (addr[i + 1] & 255))); }
        return sb.toString();
    }
    public boolean isLoopbackAddress() { return addr.length == 4 && addr[0] == 127; }
    public boolean isAnyLocalAddress() { for (byte b : addr) if (b != 0) return false; return true; }
    public boolean isSiteLocalAddress() { return addr.length == 4 && (addr[0] == 10 || (addr[0] & 255) == 192 && (addr[1] & 255) == 168 || (addr[0] & 255) == 172 && (addr[1] & 0xf0) == 16); }
    public boolean isLinkLocalAddress() { return false; }
    public boolean isMulticastAddress() { return false; }
    public boolean isReachable(int t) { return false; }
    public int hashCode() { return java.util.Arrays.hashCode(addr); }
    public boolean equals(Object o) { return o instanceof InetAddress i && java.util.Arrays.equals(i.addr, addr); }
    public String toString() { return (host != null ? host : "") + "/" + getHostAddress(); }
}
