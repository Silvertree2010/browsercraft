package java.security;

public class SignatureException extends GeneralSecurityException {
    public SignatureException() { super(); }
    public SignatureException(String s) { super(s); }
    public SignatureException(String s, Throwable t) { super(s); initCause(t); }
}
