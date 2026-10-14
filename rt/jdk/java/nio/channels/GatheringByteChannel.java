package java.nio.channels;

import java.nio.ByteBuffer;

public interface GatheringByteChannel extends WritableByteChannel {
    long write(ByteBuffer[] s, int o, int l) throws java.io.IOException;
    long write(ByteBuffer[] s) throws java.io.IOException;
}
