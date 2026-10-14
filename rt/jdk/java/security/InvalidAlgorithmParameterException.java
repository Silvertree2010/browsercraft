package java.security;

public class InvalidAlgorithmParameterException extends GeneralSecurityException {
    public InvalidAlgorithmParameterException() { super(); }
    public InvalidAlgorithmParameterException(String s) { super(s); }
    public InvalidAlgorithmParameterException(String s, Throwable t) { super(s); initCause(t); }
}
