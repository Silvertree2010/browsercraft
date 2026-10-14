package java.nio.file;

import java.util.concurrent.TimeUnit;

public interface WatchService extends java.io.Closeable {
    void close() throws java.io.IOException;
    WatchKey poll();
    WatchKey poll(long t, TimeUnit u) throws InterruptedException;
    WatchKey take() throws InterruptedException;
}
