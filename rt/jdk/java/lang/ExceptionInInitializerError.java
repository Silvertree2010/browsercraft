package java.lang;

public class ExceptionInInitializerError extends LinkageError {
    public ExceptionInInitializerError() { super(); }
    public ExceptionInInitializerError(String s) { super(s); }
    public ExceptionInInitializerError(Throwable t) { super(String.valueOf(t)); initCause(t); }
    public Throwable getException() { return getCause(); }
}
