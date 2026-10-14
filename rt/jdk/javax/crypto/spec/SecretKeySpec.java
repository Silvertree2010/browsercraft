package javax.crypto.spec;

public class SecretKeySpec implements java.security.spec.KeySpec, javax.crypto.SecretKey {
    final byte[] k;
    final String a;
    public SecretKeySpec(byte[] k, String a) { this.k = k.clone(); this.a = a; }
    public SecretKeySpec(byte[] k, int o, int l, String a) { this.k = java.util.Arrays.copyOfRange(k, o, o + l); this.a = a; }
    public String getAlgorithm() { return a; }
    public String getFormat() { return "RAW"; }
    public byte[] getEncoded() { return k.clone(); }
    public boolean equals(Object o) { return o instanceof SecretKeySpec s && java.util.Arrays.equals(s.k, k); }
    public int hashCode() { return java.util.Arrays.hashCode(k); }
}
