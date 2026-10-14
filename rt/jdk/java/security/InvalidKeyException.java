package java.security;

public class InvalidKeyException extends KeyException {
    public InvalidKeyException() { super(); }
    public InvalidKeyException(String s) { super(s); }
    public InvalidKeyException(String s, Throwable t) { super(s); initCause(t); }
}
