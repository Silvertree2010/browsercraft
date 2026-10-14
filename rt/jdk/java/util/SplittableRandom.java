package java.util;

public final class SplittableRandom {
    long s;
    public SplittableRandom() { this(System.nanoTime() ^ 0x9e3779b97f4a7c15L); }
    public SplittableRandom(long s) { this.s = s; }
    long mix(long z) { z = (z ^ (z >>> 33)) * 0xff51afd7ed558ccdL; z = (z ^ (z >>> 33)) * 0xc4ceb9fe1a85ec53L; return z ^ (z >>> 33); }
    public long nextLong() { return mix(s += 0x9e3779b97f4a7c15L); }
    public int nextInt() { return (int) nextLong(); }
    public int nextInt(int b) { return (int) Math.floorMod(nextLong(), (long) b); }
    public int nextInt(int o, int b) { return o + nextInt(b - o); }
    public long nextLong(long b) { return Math.floorMod(nextLong(), b); }
    public double nextDouble() { return (nextLong() >>> 11) * 0x1.0p-53; }
    public boolean nextBoolean() { return nextInt() < 0; }
    public void nextBytes(byte[] b) { for (int i = 0; i < b.length; i++) b[i] = (byte) nextLong(); }
    public SplittableRandom split() { return new SplittableRandom(nextLong()); }
}
