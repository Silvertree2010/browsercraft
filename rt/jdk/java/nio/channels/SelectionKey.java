package java.nio.channels;

public abstract class SelectionKey {
    public static final int OP_READ = 1, OP_WRITE = 4, OP_CONNECT = 8, OP_ACCEPT = 16;
    Object att;
    protected SelectionKey() {}
    public abstract SelectableChannel channel();
    public abstract Selector selector();
    public abstract boolean isValid();
    public abstract void cancel();
    public abstract int interestOps();
    public abstract SelectionKey interestOps(int o);
    public abstract int readyOps();
    public final Object attach(Object o) { var a = att; att = o; return a; }
    public final Object attachment() { return att; }
    public final boolean isReadable() { return (readyOps() & OP_READ) != 0; }
    public final boolean isWritable() { return (readyOps() & OP_WRITE) != 0; }
    public final boolean isConnectable() { return (readyOps() & OP_CONNECT) != 0; }
    public final boolean isAcceptable() { return (readyOps() & OP_ACCEPT) != 0; }
}
