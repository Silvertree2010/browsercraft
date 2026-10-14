package java.net;

import java.io.*;

public class Socket implements Closeable {
    public Socket() {}
    public Socket(String h, int p) throws IOException { throw new ConnectException("no raw sockets in browser"); }
    public Socket(InetAddress a, int p) throws IOException { throw new ConnectException("no raw sockets in browser"); }
    public void connect(SocketAddress a) throws IOException { throw new ConnectException("no raw sockets in browser"); }
    public void connect(SocketAddress a, int t) throws IOException { throw new ConnectException("no raw sockets in browser"); }
    public InputStream getInputStream() throws IOException { throw new SocketException("not connected"); }
    public OutputStream getOutputStream() throws IOException { throw new SocketException("not connected"); }
    public void setSoTimeout(int t) {}
    public void setTcpNoDelay(boolean b) {}
    public boolean isConnected() { return false; }
    public boolean isClosed() { return true; }
    public int getPort() { return 0; }
    public int getLocalPort() { return -1; }
    public InetAddress getInetAddress() { return null; }
    public SocketAddress getRemoteSocketAddress() { return null; }
    public void close() {}
}
