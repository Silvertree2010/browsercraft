package java.nio.channels;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.file.NoSuchFileException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Set;

public final class BcFileChannel extends FileChannel {
    final RandomAccessFile f;
    final boolean app;

    BcFileChannel(RandomAccessFile f, boolean app) { this.f = f; this.app = app; }

    public static BcFileChannel open(Path p, Set<? extends OpenOption> o) throws IOException {
        var file = new java.io.File(p.toString());
        boolean w = o.contains(StandardOpenOption.WRITE) || o.contains(StandardOpenOption.APPEND);
        if (o.contains(StandardOpenOption.CREATE_NEW) && file.exists()) throw new FileAlreadyExistsException(p.toString());
        if (!file.exists() && !(o.contains(StandardOpenOption.CREATE) || o.contains(StandardOpenOption.CREATE_NEW))) throw new NoSuchFileException(p.toString());
        if (!file.exists() && w) file.createNewFile();
        var f = new RandomAccessFile(file, w ? "rw" : "r");
        if (o.contains(StandardOpenOption.TRUNCATE_EXISTING) && w) f.setLength(0);
        var c = new BcFileChannel(f, o.contains(StandardOpenOption.APPEND));
        if (c.app) f.seek(f.length());
        return c;
    }

    public int read(ByteBuffer d) throws IOException {
        int l = d.remaining();
        if (l == 0) return 0;
        byte[] b = new byte[l];
        int n = f.read(b, 0, l);
        if (n > 0) d.put(b, 0, n);
        return n;
    }
    public long read(ByteBuffer[] d, int o, int l) throws IOException { long t = 0; for (int i = o; i < o + l; i++) { int n = read(d[i]); if (n < 0) return t == 0 ? -1 : t; t += n; } return t; }
    public int write(ByteBuffer s) throws IOException {
        if (app) f.seek(f.length());
        int n = s.remaining();
        byte[] b = new byte[n];
        s.get(b);
        f.write(b);
        return n;
    }
    public long write(ByteBuffer[] s, int o, int l) throws IOException { long t = 0; for (int i = o; i < o + l; i++) t += write(s[i]); return t; }
    public long position() throws IOException { return f.getFilePointer(); }
    public FileChannel position(long p) throws IOException { f.seek(p); return this; }
    public long size() throws IOException { return f.length(); }
    public FileChannel truncate(long s) throws IOException { if (s < f.length()) f.setLength(s); if (f.getFilePointer() > s) f.seek(s); return this; }
    public void force(boolean m) {}
    public long transferTo(long p, long c, WritableByteChannel t) throws IOException {
        long old = position();
        position(p);
        long done = 0;
        var b = ByteBuffer.allocate(8192);
        while (done < c) {
            b.clear();
            b.limit((int) Math.min(8192, c - done));
            int n = read(b);
            if (n <= 0) break;
            b.flip();
            while (b.hasRemaining()) t.write(b);
            done += n;
        }
        position(old);
        return done;
    }
    public long transferFrom(ReadableByteChannel s, long p, long c) throws IOException {
        long old = position();
        position(p);
        long done = 0;
        var b = ByteBuffer.allocate(8192);
        while (done < c) {
            b.clear();
            b.limit((int) Math.min(8192, c - done));
            int n = s.read(b);
            if (n <= 0) break;
            b.flip();
            write(b);
            done += n;
        }
        position(old);
        return done;
    }
    public int read(ByteBuffer d, long p) throws IOException { long old = position(); position(p); try { return read(d); } finally { position(old); } }
    public int write(ByteBuffer s, long p) throws IOException { long old = position(); position(p); try { return write(s); } finally { position(old); } }
    public FileLock lock(long p, long s, boolean sh) { return lk(p, s, sh); }
    public FileLock tryLock(long p, long s, boolean sh) { return lk(p, s, sh); }
    FileLock lk(long p, long s, boolean sh) {
        return new FileLock(this, p, s, sh) {
            boolean v = true;
            public boolean isValid() { return v && isOpen(); }
            public void release() { v = false; }
        };
    }
    protected void implCloseChannel() throws IOException { f.close(); }
}
