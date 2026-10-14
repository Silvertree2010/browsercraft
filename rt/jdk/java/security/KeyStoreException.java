package java.security;

public class KeyStoreException extends GeneralSecurityException {
    public KeyStoreException() { super(); }
    public KeyStoreException(String s) { super(s); }
    public KeyStoreException(String s, Throwable t) { super(s); initCause(t); }
}
