package java.net;

import java.io.*;

public class MulticastSocket extends DatagramSocket {
    public MulticastSocket() throws IOException { super(false); throw new SocketException("no multicast in browser"); }
    public MulticastSocket(int p) throws IOException { super(false); throw new SocketException("no multicast in browser"); }
    public void joinGroup(InetAddress a) throws IOException {}
    public void joinGroup(SocketAddress a, NetworkInterface n) throws IOException {}
    public void leaveGroup(InetAddress a) throws IOException {}
    public void leaveGroup(SocketAddress a, NetworkInterface n) throws IOException {}
}
