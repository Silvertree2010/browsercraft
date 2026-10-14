package java.net;

public class Proxy {
    public enum Type { DIRECT, HTTP, SOCKS }
    public static final Proxy NO_PROXY = new Proxy();
    final Type t;
    final SocketAddress a;
    private Proxy() { t = Type.DIRECT; a = null; }
    public Proxy(Type t, SocketAddress a) { this.t = t; this.a = a; }
    public Type type() { return t; }
    public SocketAddress address() { return a; }
    public String toString() { return t == Type.DIRECT ? "DIRECT" : t + " @ " + a; }
}
