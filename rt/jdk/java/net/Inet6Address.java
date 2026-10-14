package java.net;

public final class Inet6Address extends InetAddress {
    Inet6Address(String h, byte[] a) { super(h, a); }
    public int getScopeId() { return 0; }
    public NetworkInterface getScopedInterface() { return null; }
    public static Inet6Address getByAddress(String h, byte[] a, int s) throws UnknownHostException { if (a.length != 16) throw new UnknownHostException("bad length"); return new Inet6Address(h, a.clone()); }
    public static Inet6Address getByAddress(String h, byte[] a, NetworkInterface n) throws UnknownHostException { return getByAddress(h, a, 0); }
}
