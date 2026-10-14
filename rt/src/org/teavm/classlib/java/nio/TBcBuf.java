package org.teavm.classlib.java.nio;

import org.teavm.interop.Address;

public final class TBcBuf {
    private TBcBuf() {}

    public static long addr(java.nio.Buffer b) {
        if (b == null) return 0;
        Object o = b;
        Address a;
        if (o instanceof TByteBufferNative x) a = x.address;
        else if (o instanceof TFloatBufferNative x) a = x.address;
        else if (o instanceof TIntBufferNative x) a = x.address;
        else if (o instanceof TShortBufferNative x) a = x.address;
        else if (o instanceof TLongBufferNative x) a = x.address;
        else if (o instanceof TCharBufferNative x) a = x.address;
        else if (o instanceof TDoubleBufferNative x) a = x.address;
        else throw new IllegalArgumentException("not a direct buffer: " + o.getClass().getName());
        return a == null ? 0 : a.toInt() & 0xffffffffL;
    }

    public static java.nio.ByteBuffer wrap(long p, int c) {
        var r = new TByteBufferWasmGC(null, null, 0, null, Address.fromInt((int) p), c, false);
        r.limit = c;
        r.order(TByteOrder.LITTLE_ENDIAN);
        return (java.nio.ByteBuffer) (Object) r;
    }
}
