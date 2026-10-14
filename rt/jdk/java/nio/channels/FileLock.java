package java.nio.channels;

import java.io.IOException;

public abstract class FileLock implements AutoCloseable {
    final Channel ch;
    final long pos, size;
    final boolean shared;
    protected FileLock(FileChannel c, long p, long s, boolean sh) { ch = c; pos = p; size = s; shared = sh; }
    protected FileLock(AsynchronousFileChannel c, long p, long s, boolean sh) { ch = null; pos = p; size = s; shared = sh; }
    public final FileChannel channel() { return (FileChannel) ch; }
    public Channel acquiredBy() { return ch; }
    public final long position() { return pos; }
    public final long size() { return size; }
    public final boolean isShared() { return shared; }
    public final boolean overlaps(long p, long s) { return p + s > pos && pos + size > p; }
    public abstract boolean isValid();
    public abstract void release() throws IOException;
    public final void close() throws IOException { release(); }
    public final String toString() { return "FileLock[" + pos + ":" + size + "]"; }
}
