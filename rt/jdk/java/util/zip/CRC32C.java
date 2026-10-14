package java.util.zip;

public final class CRC32C implements Checksum {
    static final int[] T = new int[256];
    static {
        for (int i = 0; i < 256; i++) {
            int c = i;
            for (int k = 0; k < 8; k++) c = (c & 1) != 0 ? (c >>> 1) ^ 0x82F63B78 : c >>> 1;
            T[i] = c;
        }
    }
    int crc = 0xffffffff;
    public CRC32C() {}
    public void update(int b) { crc = (crc >>> 8) ^ T[(crc ^ b) & 0xff]; }
    public void update(byte[] b, int o, int l) { for (int i = o; i < o + l; i++) crc = (crc >>> 8) ^ T[(crc ^ b[i]) & 0xff]; }
    public void update(java.nio.ByteBuffer b) { while (b.hasRemaining()) update(b.get()); }
    public void update(byte[] b) { update(b, 0, b.length); }
    public long getValue() { return (~crc) & 0xffffffffL; }
    public void reset() { crc = 0xffffffff; }
}
