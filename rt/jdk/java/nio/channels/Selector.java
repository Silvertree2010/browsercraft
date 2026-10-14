package java.nio.channels;

import java.io.IOException;

public abstract class Selector implements java.io.Closeable {
    protected Selector() {}
    public static Selector open() throws IOException { throw new IOException("no selector in browser"); }
    public abstract boolean isOpen();
    public abstract java.util.Set<SelectionKey> keys();
    public abstract java.util.Set<SelectionKey> selectedKeys();
    public abstract int selectNow() throws IOException;
    public abstract int select(long t) throws IOException;
    public abstract int select() throws IOException;
    public abstract Selector wakeup();
    public abstract void close() throws IOException;
}
