package javax.crypto;

import javax.crypto.spec.SecretKeySpec;

public class KeyGenerator {
    final String a;
    int bits = 128;
    protected KeyGenerator(String a) { this.a = a; }
    public static KeyGenerator getInstance(String a) throws java.security.NoSuchAlgorithmException {
        if (!a.equalsIgnoreCase("AES")) throw new java.security.NoSuchAlgorithmException(a);
        return new KeyGenerator("AES");
    }
    public final String getAlgorithm() { return a; }
    public final void init(int b) { bits = b; }
    public final void init(int b, java.security.SecureRandom r) { bits = b; }
    public final SecretKey generateKey() {
        var k = new byte[bits / 8];
        new java.security.SecureRandom().nextBytes(k);
        return new SecretKeySpec(k, a);
    }
}
