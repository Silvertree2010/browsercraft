package java.security;

public class KeyException extends GeneralSecurityException {
    public KeyException() { super(); }
    public KeyException(String s) { super(s); }
    public KeyException(String s, Throwable t) { super(s); initCause(t); }
}
