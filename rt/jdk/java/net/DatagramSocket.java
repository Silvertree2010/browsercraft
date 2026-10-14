package java.net;

import java.io.*;

public class DatagramSocket implements Closeable {
    public DatagramSocket() throws SocketException { throw new SocketException("no udp in browser"); }
    public DatagramSocket(int p) throws SocketException { throw new SocketException("no udp in browser"); }
    public DatagramSocket(SocketAddress a) throws SocketException { throw new SocketException("no udp in browser"); }
    DatagramSocket(boolean x) {}
    public void send(DatagramPacket p) throws IOException { throw new SocketException("closed"); }
    public void receive(DatagramPacket p) throws IOException { throw new SocketException("closed"); }
    public void setSoTimeout(int t) {}
    public void close() {}
    public boolean isClosed() { return true; }
}
