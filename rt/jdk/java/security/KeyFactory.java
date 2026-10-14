package java.security;

import java.security.spec.*;

public class KeyFactory {
    final String a;
    KeyFactory(String a) { this.a = a; }
    public static KeyFactory getInstance(String a) throws NoSuchAlgorithmException {
        if (!a.equalsIgnoreCase("RSA")) throw new NoSuchAlgorithmException(a);
        return new KeyFactory("RSA");
    }
    public final String getAlgorithm() { return a; }
    public final PublicKey generatePublic(KeySpec s) throws InvalidKeySpecException {
        if (s instanceof X509EncodedKeySpec x) { try { return Rsa.pub(x.getEncoded()); } catch (RuntimeException e) { throw new InvalidKeySpecException(e.toString()); } }
        throw new InvalidKeySpecException("unsupported spec");
    }
    public final PrivateKey generatePrivate(KeySpec s) throws InvalidKeySpecException {
        if (s instanceof PKCS8EncodedKeySpec x) { try { return Rsa.priv(x.getEncoded()); } catch (RuntimeException e) { throw new InvalidKeySpecException(e.toString()); } }
        throw new InvalidKeySpecException("unsupported spec");
    }
}
