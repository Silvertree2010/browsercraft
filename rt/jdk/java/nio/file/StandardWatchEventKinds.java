package java.nio.file;

public final class StandardWatchEventKinds {
    static final class K<T> implements WatchEvent.Kind<T> {
        final String n; final Class<T> t;
        K(String n, Class<T> t) { this.n = n; this.t = t; }
        public String name() { return n; }
        public Class<T> type() { return t; }
        public String toString() { return n; }
    }
    public static final WatchEvent.Kind<Object> OVERFLOW = new K<>("OVERFLOW", Object.class);
    public static final WatchEvent.Kind<Path> ENTRY_CREATE = new K<>("ENTRY_CREATE", Path.class);
    public static final WatchEvent.Kind<Path> ENTRY_DELETE = new K<>("ENTRY_DELETE", Path.class);
    public static final WatchEvent.Kind<Path> ENTRY_MODIFY = new K<>("ENTRY_MODIFY", Path.class);
}
