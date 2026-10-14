package javax.sound.sampled;

import java.util.Map;

public class AudioFormat {
    public static class Encoding {
        public static final Encoding PCM_SIGNED = new Encoding("PCM_SIGNED");
        public static final Encoding PCM_UNSIGNED = new Encoding("PCM_UNSIGNED");
        public static final Encoding PCM_FLOAT = new Encoding("PCM_FLOAT");
        final String n;
        public Encoding(String n) { this.n = n; }
        public final String toString() { return n; }
        public final boolean equals(Object o) { return o instanceof Encoding e && e.n.equals(n); }
        public final int hashCode() { return n.hashCode(); }
    }
    protected Encoding encoding;
    protected float sampleRate, frameRate;
    protected int sampleSizeInBits, channels, frameSize;
    protected boolean bigEndian;
    public AudioFormat(Encoding e, float sr, int ss, int ch, int fs, float fr, boolean be) { encoding = e; sampleRate = sr; sampleSizeInBits = ss; channels = ch; frameSize = fs; frameRate = fr; bigEndian = be; }
    public AudioFormat(Encoding e, float sr, int ss, int ch, int fs, float fr, boolean be, Map<String, Object> p) { this(e, sr, ss, ch, fs, fr, be); }
    public AudioFormat(float sr, int ss, int ch, boolean signed, boolean be) { this(signed ? Encoding.PCM_SIGNED : Encoding.PCM_UNSIGNED, sr, ss, ch, ch * ((ss + 7) / 8), sr, be); }
    public Encoding getEncoding() { return encoding; }
    public float getSampleRate() { return sampleRate; }
    public int getSampleSizeInBits() { return sampleSizeInBits; }
    public int getChannels() { return channels; }
    public int getFrameSize() { return frameSize; }
    public float getFrameRate() { return frameRate; }
    public boolean isBigEndian() { return bigEndian; }
    public String toString() { return encoding + " " + sampleRate + " Hz, " + sampleSizeInBits + " bit, " + channels + " ch"; }
}
