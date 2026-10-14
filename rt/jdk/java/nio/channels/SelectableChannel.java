package java.nio.channels;

public abstract class SelectableChannel extends java.nio.channels.spi.AbstractInterruptibleChannel implements Channel {
    protected SelectableChannel() {}
    public abstract int validOps();
    public abstract boolean isRegistered();
    public abstract SelectionKey keyFor(Selector s);
    public abstract SelectionKey register(Selector s, int o, Object a) throws ClosedChannelException;
    public final SelectionKey register(Selector s, int o) throws ClosedChannelException { return register(s, o, null); }
    public abstract SelectableChannel configureBlocking(boolean b) throws java.io.IOException;
    public abstract boolean isBlocking();
}
