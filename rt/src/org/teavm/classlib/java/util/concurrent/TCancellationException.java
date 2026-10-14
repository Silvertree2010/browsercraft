package org.teavm.classlib.java.util.concurrent;

public class TCancellationException extends IllegalStateException {
    public TCancellationException() {}
    public TCancellationException(String m) { super(m); }
    public TCancellationException(String m, TCancellationException c) { super(m); initCause(c); }
}
