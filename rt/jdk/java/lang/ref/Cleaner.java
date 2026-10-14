package java.lang.ref;

import java.util.concurrent.ThreadFactory;

public final class Cleaner {
    Cleaner() {}
    public static Cleaner create() { return new Cleaner(); }
    public static Cleaner create(ThreadFactory f) { return new Cleaner(); }
    public Cleanable register(Object o, Runnable a) {
        return new Cleanable() {
            Runnable r = a;
            public void clean() { var x = r; r = null; if (x != null) x.run(); }
        };
    }
    public interface Cleanable { void clean(); }
}
