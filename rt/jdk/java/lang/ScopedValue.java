package java.lang;

import java.util.NoSuchElementException;

public final class ScopedValue<T> {
    static final Object NONE = new Object();
    Object v = NONE;

    ScopedValue() {}

    public static <T> ScopedValue<T> newInstance() { return new ScopedValue<>(); }
    public static <T> Carrier where(ScopedValue<T> k, T v) { return new Carrier(null, k, v); }
    @SuppressWarnings("unchecked")
    public T get() { if (v == NONE) throw new NoSuchElementException("not bound"); return (T) v; }
    public boolean isBound() { return v != NONE; }
    @SuppressWarnings("unchecked")
    public T orElse(T o) { return v == NONE ? o : (T) v; }

    public interface CallableOp<T, X extends Throwable> { T call() throws X; }

    public static final class Carrier {
        final Carrier p;
        final ScopedValue<?> k;
        final Object v;
        Carrier(Carrier p, ScopedValue<?> k, Object v) { this.p = p; this.k = k; this.v = v; }
        public <T> Carrier where(ScopedValue<T> k, T v) { return new Carrier(this, k, v); }
        Object[] bind() {
            int n = 0;
            for (var c = this; c != null; c = c.p) n++;
            Object[] o = new Object[n];
            int i = 0;
            for (var c = this; c != null; c = c.p) { o[i++] = c.k.v; c.k.v = c.v; }
            return o;
        }
        void unbind(Object[] o) {
            int i = 0;
            for (var c = this; c != null; c = c.p) c.k.v = o[i++];
        }
        public void run(Runnable r) { var o = bind(); try { r.run(); } finally { unbind(o); } }
        public <R, X extends Throwable> R call(CallableOp<? extends R, X> op) throws X { var o = bind(); try { return op.call(); } finally { unbind(o); } }
        @SuppressWarnings("unchecked")
        public <T> T get(ScopedValue<T> k) { for (var c = this; c != null; c = c.p) if (c.k == k) return (T) c.v; throw new NoSuchElementException(); }
    }
}
