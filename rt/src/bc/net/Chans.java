package bc.net;

import io.netty.bootstrap.AbstractBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.local.LocalServerChannel;

public final class Chans {
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static AbstractBootstrap channel(AbstractBootstrap b, Class c) {
        if (c == LocalChannel.class) return b.channelFactory(LocalChannel::new);
        if (c == LocalServerChannel.class) return b.channelFactory(LocalServerChannel::new);
        if (c == WsChannel.class) return b.channelFactory(WsChannel::new);
        if (c == PipeServer.class) return b.channelFactory(PipeServer::new);
        throw new IllegalArgumentException("unsupported channel " + c);
    }
}
