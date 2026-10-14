package java.net;

public class SocketTimeoutException extends java.io.InterruptedIOException {
    public SocketTimeoutException() { super(); }
    public SocketTimeoutException(String s) { super(s); }
}
