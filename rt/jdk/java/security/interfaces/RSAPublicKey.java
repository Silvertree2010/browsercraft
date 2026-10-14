package java.security.interfaces;

public interface RSAPublicKey extends java.security.PublicKey, RSAKey {
    java.math.BigInteger getPublicExponent();
}
