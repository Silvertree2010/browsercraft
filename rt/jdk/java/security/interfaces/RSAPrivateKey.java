package java.security.interfaces;

public interface RSAPrivateKey extends java.security.PrivateKey, RSAKey {
    java.math.BigInteger getPrivateExponent();
}
