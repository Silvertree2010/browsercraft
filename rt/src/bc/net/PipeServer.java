package bc.net;

import io.netty.channel.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import org.teavm.jso.JSBody;

public class PipeServer extends AbstractServerChannel {
    static PipeServer cur;

    final DefaultChannelConfig cfg = new DefaultChannelConfig(this);
    boolean open = true, bound;
    SocketAddress local;

    @JSBody(params = {"cb"}, script = "bc.pipe.listen(cb);")
    static native void listen(WsChannel.Cb cb);

    public static void ready() {
        var s = cur;
        if (s != null) listen(id -> s.eventLoop().execute(() -> s.accept(id)));
    }

    void accept(int id) {
        pipeline().fireChannelRead(new WsChannel(this, id));
        pipeline().fireChannelReadComplete();
    }

    public ChannelConfig config() { return cfg; }
    public boolean isOpen() { return open; }
    public boolean isActive() { return open && bound; }
    protected boolean isCompatible(EventLoop l) { return true; }
    protected SocketAddress localAddress0() { return local; }
    protected void doBind(SocketAddress a) { local = a; bound = true; cur = this; }
    protected void doClose() { open = false; if (cur == this) cur = null; }
    protected void doBeginRead() {}
}
