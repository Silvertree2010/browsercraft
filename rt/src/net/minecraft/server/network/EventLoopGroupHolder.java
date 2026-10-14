package net.minecraft.server.network;

import io.netty.channel.Channel;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.local.LocalIoHandler;
import io.netty.channel.local.LocalServerChannel;

public abstract class EventLoopGroupHolder {
    static final EventLoopGroupHolder WS = new EventLoopGroupHolder("WS", bc.net.WsChannel.class, bc.net.PipeServer.class) {
        EventLoopGroup make() { return new DefaultEventLoopGroup(1); }
    };
    static final EventLoopGroupHolder LOCAL = new EventLoopGroupHolder("Local", LocalChannel.class, LocalServerChannel.class) {
        EventLoopGroup make() { return new MultiThreadIoEventLoopGroup(1, LocalIoHandler.newFactory()); }
    };

    final String type;
    final Class<? extends Channel> ch;
    final Class<? extends ServerChannel> sch;
    volatile EventLoopGroup group;

    EventLoopGroupHolder(String type, Class<? extends Channel> ch, Class<? extends ServerChannel> sch) { this.type = type; this.ch = ch; this.sch = sch; }

    public static EventLoopGroupHolder remote(boolean n) { return WS; }
    public static EventLoopGroupHolder local() { return LOCAL; }

    abstract EventLoopGroup make();

    public EventLoopGroup eventLoopGroup() {
        var r = group;
        if (r == null) synchronized (this) { if ((r = group) == null) group = r = make(); }
        return r;
    }

    public Class<? extends Channel> channelCls() { return ch; }
    public Class<? extends ServerChannel> serverChannelCls() { return sch; }
}
