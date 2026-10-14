package java.security;

import java.math.BigInteger;

public abstract class KeyPairGenerator {
    final String a;
    int bits = 1024;
    protected KeyPairGenerator(String a) { this.a = a; }
    public static KeyPairGenerator getInstance(String a) throws NoSuchAlgorithmException {
        if (!a.equalsIgnoreCase("RSA")) throw new NoSuchAlgorithmException(a);
        return new KeyPairGenerator("RSA") {};
    }
    public String getAlgorithm() { return a; }
    public void initialize(int b) { bits = b; }
    public void initialize(int b, SecureRandom r) { bits = b; }
    public final KeyPair genKeyPair() { return generateKeyPair(); }
    public KeyPair generateKeyPair() {
        var r = new java.util.Random();
        var e = BigInteger.valueOf(65537);
        for (;;) {
            var p = BigInteger.probablePrime(bits / 2, r);
            var q = BigInteger.probablePrime(bits - bits / 2, r);
            var phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
            if (!phi.gcd(e).equals(BigInteger.ONE) || p.equals(q)) continue;
            var n = p.multiply(q);
            var d = e.modInverse(phi);
            return new KeyPair(new Rsa.Pub(n, e), new Rsa.Priv(n, e, d, p, q));
        }
    }
}
