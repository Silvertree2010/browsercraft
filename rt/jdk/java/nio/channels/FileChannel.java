package java.nio.channels;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.util.Set;

public abstract class FileChannel extends AbstractInterruptibleChannel implements SeekableByteChannel, GatheringByteChannel, ScatteringByteChannel {
    protected FileChannel() {}

    public static FileChannel open(Path p, Set<? extends OpenOption> o, FileAttribute<?>... a) throws IOException { return BcFileChannel.open(p, o); }
    public static FileChannel open(Path p, OpenOption... o) throws IOException { return BcFileChannel.open(p, Set.of(o)); }

    public abstract int read(ByteBuffer d) throws IOException;
    public abstract long read(ByteBuffer[] d, int o, int l) throws IOException;
    public final long read(ByteBuffer[] d) throws IOException { return read(d, 0, d.length); }
    public abstract int write(ByteBuffer s) throws IOException;
    public abstract long write(ByteBuffer[] s, int o, int l) throws IOException;
    public final long write(ByteBuffer[] s) throws IOException { return write(s, 0, s.length); }
    public abstract long position() throws IOException;
    public abstract FileChannel position(long p) throws IOException;
    public abstract long size() throws IOException;
    public abstract FileChannel truncate(long s) throws IOException;
    public abstract void force(boolean m) throws IOException;
    public abstract long transferTo(long p, long c, WritableByteChannel t) throws IOException;
    public abstract long transferFrom(ReadableByteChannel s, long p, long c) throws IOException;
    public abstract int read(ByteBuffer d, long p) throws IOException;
    public abstract int write(ByteBuffer s, long p) throws IOException;
    public abstract FileLock lock(long p, long s, boolean sh) throws IOException;
    public final FileLock lock() throws IOException { return lock(0L, Long.MAX_VALUE, false); }
    public abstract FileLock tryLock(long p, long s, boolean sh) throws IOException;
    public final FileLock tryLock() throws IOException { return tryLock(0L, Long.MAX_VALUE, false); }
}
