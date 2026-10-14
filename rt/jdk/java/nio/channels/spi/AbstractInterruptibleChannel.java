package java.nio.channels.spi;

import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.channels.InterruptibleChannel;

public abstract class AbstractInterruptibleChannel implements Channel, InterruptibleChannel {
    boolean closed;
    protected AbstractInterruptibleChannel() {}
    public final void close() throws IOException { if (!closed) { closed = true; implCloseChannel(); } }
    protected abstract void implCloseChannel() throws IOException;
    public final boolean isOpen() { return !closed; }
    protected final void begin() {}
    protected final void end(boolean c) {}
}
