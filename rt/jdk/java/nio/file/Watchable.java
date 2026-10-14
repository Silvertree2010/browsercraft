package java.nio.file;

public interface Watchable {
    WatchKey register(WatchService w, WatchEvent.Kind<?>[] k, WatchEvent.Modifier... m) throws java.io.IOException;
    WatchKey register(WatchService w, WatchEvent.Kind<?>... k) throws java.io.IOException;
}
