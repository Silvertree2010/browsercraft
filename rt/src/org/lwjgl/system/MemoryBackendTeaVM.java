package org.lwjgl.system;

import java.nio.*;
import java.nio.BcBuf;
import org.teavm.interop.Address;
import org.teavm.runtime.heap.Heap;

final class MemoryBackendTeaVM implements MemoryBackend {
    static Address A(long p) { return Address.fromInt((int) p); }

    static final class Al implements MemoryUtil.MemoryAllocator {
        public long getMalloc() { return 0; }
        public long getCalloc() { return 0; }
        public long getRealloc() { return 0; }
        public long getFree() { return 0; }
        public long getAlignedAlloc() { return 0; }
        public long getAlignedFree() { return 0; }
        public long malloc(long s) { var a = Heap.alloc((int) Math.max(1, s)); return a == null ? 0 : a.toInt() & 0xffffffffL; }
        public long calloc(long n, long s) { long t = n * s; long p = malloc(t); if (p != 0) Address.fillZero(A(p), (int) t); return p; }
        public long realloc(long p, long s) { if (p == 0) return malloc(s); var a = Heap.realloc(A(p), (int) Math.max(1, s)); return a == null ? 0 : a.toInt() & 0xffffffffL; }
        public void free(long p) { if (p != 0) Heap.release(A(p)); }
        public long aligned_alloc(long al, long s) { return malloc(s); }
        public void aligned_free(long p) { free(p); }
    }

    public boolean getBoolean(long p) { return A(p).getByte() != 0; }
    public void putBoolean(long p, boolean v) { A(p).putByte((byte) (v ? 1 : 0)); }
    public long getLong(byte[] o, long off) { long r = 0; int i = (int) off; for (int k = 7; k >= 0; k--) r = (r << 8) | (o[i + k] & 0xff); return r; }
    public long getCLong(long p) { return A(p).getLong(); }
    public long getCLongUnaligned(long p) { return A(p).getLong(); }
    public void putCLong(long p, long v) { A(p).putLong(v); }
    public void putCLongUnaligned(long p, long v) { A(p).putLong(v); }
    public long getAddress(long p) { return A(p).getLong(); }
    public long getAddressUnaligned(long p) { return A(p).getLong(); }
    public void putAddress(long p, long v) { A(p).putLong(v); }
    public void putAddressUnaligned(long p, long v) { A(p).putLong(v); }
    public void memset(long p, int v, long n) { Address.fill(A(p), (byte) v, (int) n); }
    public void memcpy(long s, long d, long n) { Address.moveMemoryBlock(A(s), A(d), (int) n); }

    public byte getByte(long p) { return A(p).getByte(); }
    public void putByte(long p, byte v) { A(p).putByte(v); }
    public void memcpy(byte[] s, long d, int off, int n) { var a = A(d); for (int i = 0; i < n; i++) { a.putByte(s[off + i]); a = a.add(1); } }
    public void memcpy(long s, byte[] d, int off, int n) { var a = A(s); for (int i = 0; i < n; i++) { d[off + i] = a.getByte(); a = a.add(1); } }
    public long getAddress0(ByteBuffer b) { return BcBuf.addr(b); }
    public long getAddress(ByteBuffer b) { return BcBuf.addr(b) + b.position(); }
    public long getAddress(ByteBuffer b, int pos) { return BcBuf.addr(b) + pos; }
    public ByteBuffer wrapBufferByte(long a, int c) { return BcBuf.wrap(a, c); }
    public ByteBuffer duplicate(ByteBuffer b) { return b.duplicate().order(b.order()); }
    public ByteBuffer slice(ByteBuffer b) { return b.slice().order(b.order()); }
    public ByteBuffer slice(ByteBuffer b, int off, int len) { return BcBuf.wrap(BcBuf.addr(b) + off, len).order(b.order()); }
    public char getChar(long p) { return A(p).getChar(); }
    public void putChar(long p, char v) { A(p).putChar(v); }
    public char getCharUnaligned(long p) { return A(p).getChar(); }
    public void putCharUnaligned(long p, char v) { A(p).putChar(v); }
    public void memcpy(char[] s, long d, int off, int n) { var a = A(d); for (int i = 0; i < n; i++) { a.putChar(s[off + i]); a = a.add(2); } }
    public void memcpy(long s, char[] d, int off, int n) { var a = A(s); for (int i = 0; i < n; i++) { d[off + i] = a.getChar(); a = a.add(2); } }
    public long getAddress0(CharBuffer b) { return BcBuf.addr(b); }
    public long getAddress(CharBuffer b) { return BcBuf.addr(b) + ((long) b.position() << 1); }
    public long getAddress(CharBuffer b, int pos) { return BcBuf.addr(b) + ((long) pos << 1); }
    public CharBuffer wrapBufferChar(long a, int c) { return BcBuf.wrap(a, c << 1).asCharBuffer(); }
    public CharBuffer duplicate(CharBuffer b) { return b.duplicate(); }
    public CharBuffer slice(CharBuffer b) { return b.slice(); }
    public CharBuffer slice(CharBuffer b, int off, int len) { return wrapBufferChar(BcBuf.addr(b) + ((long) off << 1), len); }
    public short getShort(long p) { return A(p).getShort(); }
    public void putShort(long p, short v) { A(p).putShort(v); }
    public short getShortUnaligned(long p) { return A(p).getShort(); }
    public void putShortUnaligned(long p, short v) { A(p).putShort(v); }
    public void memcpy(short[] s, long d, int off, int n) { var a = A(d); for (int i = 0; i < n; i++) { a.putShort(s[off + i]); a = a.add(2); } }
    public void memcpy(long s, short[] d, int off, int n) { var a = A(s); for (int i = 0; i < n; i++) { d[off + i] = a.getShort(); a = a.add(2); } }
    public long getAddress0(ShortBuffer b) { return BcBuf.addr(b); }
    public long getAddress(ShortBuffer b) { return BcBuf.addr(b) + ((long) b.position() << 1); }
    public long getAddress(ShortBuffer b, int pos) { return BcBuf.addr(b) + ((long) pos << 1); }
    public ShortBuffer wrapBufferShort(long a, int c) { return BcBuf.wrap(a, c << 1).asShortBuffer(); }
    public ShortBuffer duplicate(ShortBuffer b) { return b.duplicate(); }
    public ShortBuffer slice(ShortBuffer b) { return b.slice(); }
    public ShortBuffer slice(ShortBuffer b, int off, int len) { return wrapBufferShort(BcBuf.addr(b) + ((long) off << 1), len); }
    public int getInt(long p) { return A(p).getInt(); }
    public void putInt(long p, int v) { A(p).putInt(v); }
    public int getIntUnaligned(long p) { return A(p).getInt(); }
    public void putIntUnaligned(long p, int v) { A(p).putInt(v); }
    public void memcpy(int[] s, long d, int off, int n) { var a = A(d); for (int i = 0; i < n; i++) { a.putInt(s[off + i]); a = a.add(4); } }
    public void memcpy(long s, int[] d, int off, int n) { var a = A(s); for (int i = 0; i < n; i++) { d[off + i] = a.getInt(); a = a.add(4); } }
    public long getAddress0(IntBuffer b) { return BcBuf.addr(b); }
    public long getAddress(IntBuffer b) { return BcBuf.addr(b) + ((long) b.position() << 2); }
    public long getAddress(IntBuffer b, int pos) { return BcBuf.addr(b) + ((long) pos << 2); }
    public IntBuffer wrapBufferInt(long a, int c) { return BcBuf.wrap(a, c << 2).asIntBuffer(); }
    public IntBuffer duplicate(IntBuffer b) { return b.duplicate(); }
    public IntBuffer slice(IntBuffer b) { return b.slice(); }
    public IntBuffer slice(IntBuffer b, int off, int len) { return wrapBufferInt(BcBuf.addr(b) + ((long) off << 2), len); }
    public long getLong(long p) { return A(p).getLong(); }
    public void putLong(long p, long v) { A(p).putLong(v); }
    public long getLongUnaligned(long p) { return A(p).getLong(); }
    public void putLongUnaligned(long p, long v) { A(p).putLong(v); }
    public void memcpy(long[] s, long d, int off, int n) { var a = A(d); for (int i = 0; i < n; i++) { a.putLong(s[off + i]); a = a.add(8); } }
    public void memcpy(long s, long[] d, int off, int n) { var a = A(s); for (int i = 0; i < n; i++) { d[off + i] = a.getLong(); a = a.add(8); } }
    public long getAddress0(LongBuffer b) { return BcBuf.addr(b); }
    public long getAddress(LongBuffer b) { return BcBuf.addr(b) + ((long) b.position() << 3); }
    public long getAddress(LongBuffer b, int pos) { return BcBuf.addr(b) + ((long) pos << 3); }
    public LongBuffer wrapBufferLong(long a, int c) { return BcBuf.wrap(a, c << 3).asLongBuffer(); }
    public LongBuffer duplicate(LongBuffer b) { return b.duplicate(); }
    public LongBuffer slice(LongBuffer b) { return b.slice(); }
    public LongBuffer slice(LongBuffer b, int off, int len) { return wrapBufferLong(BcBuf.addr(b) + ((long) off << 3), len); }
    public float getFloat(long p) { return A(p).getFloat(); }
    public void putFloat(long p, float v) { A(p).putFloat(v); }
    public float getFloatUnaligned(long p) { return A(p).getFloat(); }
    public void putFloatUnaligned(long p, float v) { A(p).putFloat(v); }
    public void memcpy(float[] s, long d, int off, int n) { var a = A(d); for (int i = 0; i < n; i++) { a.putFloat(s[off + i]); a = a.add(4); } }
    public void memcpy(long s, float[] d, int off, int n) { var a = A(s); for (int i = 0; i < n; i++) { d[off + i] = a.getFloat(); a = a.add(4); } }
    public long getAddress0(FloatBuffer b) { return BcBuf.addr(b); }
    public long getAddress(FloatBuffer b) { return BcBuf.addr(b) + ((long) b.position() << 2); }
    public long getAddress(FloatBuffer b, int pos) { return BcBuf.addr(b) + ((long) pos << 2); }
    public FloatBuffer wrapBufferFloat(long a, int c) { return BcBuf.wrap(a, c << 2).asFloatBuffer(); }
    public FloatBuffer duplicate(FloatBuffer b) { return b.duplicate(); }
    public FloatBuffer slice(FloatBuffer b) { return b.slice(); }
    public FloatBuffer slice(FloatBuffer b, int off, int len) { return wrapBufferFloat(BcBuf.addr(b) + ((long) off << 2), len); }
    public double getDouble(long p) { return A(p).getDouble(); }
    public void putDouble(long p, double v) { A(p).putDouble(v); }
    public double getDoubleUnaligned(long p) { return A(p).getDouble(); }
    public void putDoubleUnaligned(long p, double v) { A(p).putDouble(v); }
    public void memcpy(double[] s, long d, int off, int n) { var a = A(d); for (int i = 0; i < n; i++) { a.putDouble(s[off + i]); a = a.add(8); } }
    public void memcpy(long s, double[] d, int off, int n) { var a = A(s); for (int i = 0; i < n; i++) { d[off + i] = a.getDouble(); a = a.add(8); } }
    public long getAddress0(DoubleBuffer b) { return BcBuf.addr(b); }
    public long getAddress(DoubleBuffer b) { return BcBuf.addr(b) + ((long) b.position() << 3); }
    public long getAddress(DoubleBuffer b, int pos) { return BcBuf.addr(b) + ((long) pos << 3); }
    public DoubleBuffer wrapBufferDouble(long a, int c) { return BcBuf.wrap(a, c << 3).asDoubleBuffer(); }
    public DoubleBuffer duplicate(DoubleBuffer b) { return b.duplicate(); }
    public DoubleBuffer slice(DoubleBuffer b) { return b.slice(); }
    public DoubleBuffer slice(DoubleBuffer b, int off, int len) { return wrapBufferDouble(BcBuf.addr(b) + ((long) off << 3), len); }
    public long getAddress0(Buffer b) { return BcBuf.addr(b); }
    public long getAddress(Buffer b) {
        if (b instanceof ByteBuffer x) return getAddress(x);
        if (b instanceof IntBuffer x) return getAddress(x);
        if (b instanceof FloatBuffer x) return getAddress(x);
        if (b instanceof ShortBuffer x) return getAddress(x);
        if (b instanceof LongBuffer x) return getAddress(x);
        if (b instanceof CharBuffer x) return getAddress(x);
        if (b instanceof DoubleBuffer x) return getAddress(x);
        return BcBuf.addr(b);
    }
    public long getAddress(Buffer b, int pos) {
        if (b instanceof ByteBuffer x) return getAddress(x, pos);
        if (b instanceof IntBuffer x) return getAddress(x, pos);
        if (b instanceof FloatBuffer x) return getAddress(x, pos);
        if (b instanceof ShortBuffer x) return getAddress(x, pos);
        if (b instanceof LongBuffer x) return getAddress(x, pos);
        if (b instanceof CharBuffer x) return getAddress(x, pos);
        if (b instanceof DoubleBuffer x) return getAddress(x, pos);
        return BcBuf.addr(b);
    }
    public void putStringASCII(String t, boolean nt, long d) { putStringASCII((CharSequence) t, nt, d); }
    public void putStringASCII(CharSequence t, boolean nt, long d) {
        var a = A(d);
        for (int i = 0; i < t.length(); i++) { a.putByte((byte) t.charAt(i)); a = a.add(1); }
        if (nt) a.putByte((byte) 0);
    }
    public void putStringUTF16(String t, boolean nt, long d) { putStringUTF16((CharSequence) t, nt, d); }
    public void putStringUTF16(CharSequence t, boolean nt, long d) {
        var a = A(d);
        for (int i = 0; i < t.length(); i++) { a.putChar(t.charAt(i)); a = a.add(2); }
        if (nt) a.putChar((char) 0);
    }
    public void putStringUTF8(CharSequence t, boolean nt, long d) {
        var b = t.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8);
        memcpy(b, d, 0, b.length);
        if (nt) A(d + b.length).putByte((byte) 0);
    }
    public int putStringUTF8(String t, boolean nt, long d, int max) {
        var b = t.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        int n = b.length + (nt ? 1 : 0);
        if (max < n) throw new BufferOverflowException();
        memcpy(b, d, 0, b.length);
        if (nt) A(d + b.length).putByte((byte) 0);
        return n;
    }
    public ByteBuffer allocateUTF8(String t, boolean nt, MemoryUtil.MemoryAllocator al) {
        var b = t.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        int n = b.length + (nt ? 1 : 0);
        long p = al.malloc(n);
        memcpy(b, p, 0, b.length);
        if (nt) A(p + b.length).putByte((byte) 0);
        return wrapBufferByte(p, n);
    }
}
