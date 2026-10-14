package java.nio.channels;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public final class Channels {
    private Channels() {}

    public static InputStream newInputStream(ReadableByteChannel ch) {
        return new InputStream() {
            final ByteBuffer one = ByteBuffer.allocate(1);
            public int read() throws IOException { one.clear(); int n; do { n = ch.read(one); } while (n == 0); return n < 0 ? -1 : one.get(0) & 255; }
            public int read(byte[] b, int o, int l) throws IOException { if (l == 0) return 0; int n; do { n = ch.read(ByteBuffer.wrap(b, o, l)); } while (n == 0); return n; }
            public void close() throws IOException { ch.close(); }
        };
    }

    public static OutputStream newOutputStream(WritableByteChannel ch) {
        return new OutputStream() {
            public void write(int b) throws IOException { ch.write(ByteBuffer.wrap(new byte[]{(byte) b})); }
            public void write(byte[] b, int o, int l) throws IOException { var bb = ByteBuffer.wrap(b, o, l); while (bb.hasRemaining()) ch.write(bb); }
            public void close() throws IOException { ch.close(); }
        };
    }

    public static ReadableByteChannel newChannel(InputStream in) {
        return new ReadableByteChannel() {
            boolean open = true;
            public int read(ByteBuffer d) throws IOException {
                int l = d.remaining();
                if (l == 0) return 0;
                byte[] b = new byte[Math.min(l, 8192)];
                int n = in.read(b);
                if (n > 0) d.put(b, 0, n);
                return n;
            }
            public boolean isOpen() { return open; }
            public void close() throws IOException { open = false; in.close(); }
        };
    }

    public static WritableByteChannel newChannel(OutputStream out) {
        return new WritableByteChannel() {
            boolean open = true;
            public int write(ByteBuffer s) throws IOException { int n = s.remaining(); byte[] b = new byte[n]; s.get(b); out.write(b); return n; }
            public boolean isOpen() { return open; }
            public void close() throws IOException { open = false; out.close(); }
        };
    }

    public static Reader newReader(ReadableByteChannel ch, String cs) { return new InputStreamReader(newInputStream(ch), Charset.forName(cs)); }
    public static Reader newReader(ReadableByteChannel ch, Charset cs) { return new InputStreamReader(newInputStream(ch), cs); }
    public static Writer newWriter(WritableByteChannel ch, String cs) { return new OutputStreamWriter(newOutputStream(ch), Charset.forName(cs)); }
    public static Writer newWriter(WritableByteChannel ch, Charset cs) { return new OutputStreamWriter(newOutputStream(ch), cs); }
}
