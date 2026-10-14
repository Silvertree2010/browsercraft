package java.net;

public final class DatagramPacket {
    byte[] b; int o, l; SocketAddress a;
    public DatagramPacket(byte[] b, int l) { this(b, 0, l); }
    public DatagramPacket(byte[] b, int o, int l) { this.b = b; this.o = o; this.l = l; }
    public DatagramPacket(byte[] b, int l, InetAddress a, int p) { this(b, 0, l); this.a = new InetSocketAddress(a, p); }
    public DatagramPacket(byte[] b, int l, SocketAddress a) { this(b, 0, l); this.a = a; }
    public byte[] getData() { return b; }
    public int getOffset() { return o; }
    public int getLength() { return l; }
    public void setLength(int l) { this.l = l; }
    public InetAddress getAddress() { return a instanceof InetSocketAddress i ? i.getAddress() : null; }
    public int getPort() { return a instanceof InetSocketAddress i ? i.getPort() : -1; }
    public SocketAddress getSocketAddress() { return a; }
}
