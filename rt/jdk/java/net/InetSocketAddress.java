package java.net;

public class InetSocketAddress extends SocketAddress {
    final String host;
    final InetAddress addr;
    final int port;
    final boolean unres;

    InetSocketAddress(String h, InetAddress a, int p, boolean u) { host = h; addr = a; port = p; unres = u; }
    public InetSocketAddress(int p) { this((String) null, InetAddress.getLocalHost(), p, false); }
    public InetSocketAddress(InetAddress a, int p) { this(null, a == null ? InetAddress.getLocalHost() : a, p, false); }
    public InetSocketAddress(String h, int p) { this(h, res(h), p, false); }

    static InetAddress res(String h) { try { return InetAddress.getByName(h); } catch (UnknownHostException e) { return null; } }

    public static InetSocketAddress createUnresolved(String h, int p) { return new InetSocketAddress(h, null, p, true); }

    public final int getPort() { return port; }
    public final InetAddress getAddress() { return addr; }
    public final String getHostName() { return host != null ? host : addr != null ? addr.getHostName() : null; }
    public final String getHostString() { return host != null ? host : addr != null ? (addr.host != null ? addr.host : addr.getHostAddress()) : null; }
    public final boolean isUnresolved() { return unres; }
    public String toString() { return (unres ? getHostString() + "/<unresolved>" : String.valueOf(addr)) + ":" + port; }
    public final boolean equals(Object o) { return o instanceof InetSocketAddress i && i.port == port && java.util.Objects.equals(getHostString(), i.getHostString()); }
    public final int hashCode() { return java.util.Objects.hashCode(getHostString()) * 31 + port; }
}
