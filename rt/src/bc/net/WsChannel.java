package bc.net;

import static org.lwjgl.system.MemoryUtil.*;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import org.teavm.jso.JSBody;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;

public class WsChannel extends AbstractChannel {
    static final ChannelMetadata META = new ChannelMetadata(false, 16);
    static final ByteBuffer IN = memAlloc(1 << 20);
    static final long INA = memAddress(IN);
    static final ByteBuffer OUT = memAlloc(1 << 20);
    static final long OUTA = memAddress(OUT);

    final DefaultChannelConfig cfg = new DefaultChannelConfig(this);
    int id;
    boolean open = true, active;
    InetSocketAddress remote;
    ChannelPromise pending;

    @JSFunctor
    public interface Cb extends JSObject { void run(int ev); }

    @JSBody(params = {"h", "p", "cb"}, script = "return bc.ws.open(h, p, cb);")
    static native int wsOpen(String h, int p, Cb cb);
    @JSBody(params = {"i", "a", "n"}, script = "bc.ws.send(i, a, n);")
    static native void wsSend(int i, int a, int n);
    @JSBody(params = {"i", "a", "n"}, script = "return bc.ws.next(i, a, n);")
    static native int wsNext(int i, int a, int n);
    @JSBody(params = {"i"}, script = "bc.ws.close(i);")
    static native void wsClose(int i);

    public WsChannel() { super(null); }

    WsChannel(Channel par, int id) {
        super(par);
        this.id = id;
        active = true;
        remote = new InetSocketAddress(java.net.InetAddress.getLoopbackAddress(), 40000 + id);
    }

    @JSBody(params = {"i", "cb"}, script = "bc.ws.bind(i, cb);")
    static native void wsBind(int i, Cb cb);

    protected void doRegister() {
        if (parent() != null) wsBind(id, ev -> eventLoop().execute(() -> event(ev)));
    }

    public ChannelConfig config() { return cfg; }
    public boolean isOpen() { return open; }
    public boolean isActive() { return active; }
    public ChannelMetadata metadata() { return META; }

    protected AbstractUnsafe newUnsafe() {
        return new AbstractUnsafe() {
            public void connect(SocketAddress r, SocketAddress l, ChannelPromise p) {
                if (!p.setUncancellable() || !ensureOpen(p)) return;
                remote = (InetSocketAddress) r;
                pending = p;
                String host = remote.getHostString();
                id = wsOpen(host, remote.getPort(), ev -> eventLoop().execute(() -> event(ev)));
            }
        };
    }

    void event(int ev) {
        if (ev == 1) {
            active = true;
            if (pending != null) { pending.trySuccess(); pending = null; }
            pipeline().fireChannelActive();
        } else if (ev == 2) {
            for (;;) {
                int n = wsNext(id, (int) INA, IN.capacity());
                if (n <= 0) break;
                ByteBuf b = alloc().buffer(n);
                IN.clear().limit(n);
                b.writeBytes(IN);
                pipeline().fireChannelRead(b);
            }
            pipeline().fireChannelReadComplete();
        } else if (ev == 3) {
            if (pending != null) { pending.tryFailure(new java.net.ConnectException("Verbindung fehlgeschlagen")); pending = null; }
            unsafe().close(voidPromise());
        }
    }

    protected boolean isCompatible(EventLoop l) { return true; }
    protected SocketAddress localAddress0() { return new InetSocketAddress(0); }
    protected SocketAddress remoteAddress0() { return remote; }
    protected void doBind(SocketAddress a) { throw new UnsupportedOperationException(); }
    protected void doDisconnect() { doClose(); }

    protected void doClose() {
        boolean was = active;
        open = false;
        active = false;
        if (id != 0) { wsClose(id); id = 0; }
    }

    protected void doBeginRead() {}

    protected void doWrite(ChannelOutboundBuffer in) {
        for (;;) {
            Object m = in.current();
            if (m == null) break;
            if (m instanceof ByteBuf b) {
                while (b.isReadable()) {
                    int n = Math.min(b.readableBytes(), OUT.capacity());
                    OUT.clear().limit(n);
                    b.readBytes(OUT);
                    if (id != 0) wsSend(id, (int) OUTA, n);
                }
            }
            in.remove();
        }
    }
}
