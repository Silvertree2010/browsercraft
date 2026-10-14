package java.nio.channels;

import java.nio.ByteBuffer;

public interface ScatteringByteChannel extends ReadableByteChannel {
    long read(ByteBuffer[] d, int o, int l) throws java.io.IOException;
    long read(ByteBuffer[] d) throws java.io.IOException;
}
