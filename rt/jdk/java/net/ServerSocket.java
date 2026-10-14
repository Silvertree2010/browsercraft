package java.net;

import java.io.*;

public class ServerSocket implements Closeable {
    public ServerSocket() throws IOException {}
    public ServerSocket(int p) throws IOException { throw new SocketException("no server sockets in browser"); }
    public ServerSocket(int p, int b, InetAddress a) throws IOException { throw new SocketException("no server sockets in browser"); }
    public void bind(SocketAddress a) throws IOException { throw new SocketException("no server sockets in browser"); }
    public Socket accept() throws IOException { throw new SocketException("closed"); }
    public int getLocalPort() { return -1; }
    public boolean isClosed() { return true; }
    public void close() {}
}
