package java.util.concurrent;

import java.util.List;

public class Executors {
    private Executors() {}

    public static ExecutorService newFixedThreadPool(int n) { return newFixedThreadPool(n, defaultThreadFactory()); }
    public static ExecutorService newFixedThreadPool(int n, ThreadFactory f) { return new ThreadPoolExecutor(n, n, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), f); }
    public static ExecutorService newWorkStealingPool(int p) { return new ForkJoinPool(p); }
    public static ExecutorService newWorkStealingPool() { return new ForkJoinPool(2); }
    public static ExecutorService newSingleThreadExecutor() { return newSingleThreadExecutor(defaultThreadFactory()); }
    public static ExecutorService newSingleThreadExecutor(ThreadFactory f) { return new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), f); }
    public static ExecutorService newCachedThreadPool() { return newCachedThreadPool(defaultThreadFactory()); }
    public static ExecutorService newCachedThreadPool(ThreadFactory f) { return new ThreadPoolExecutor(0, 4, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), f); }
    public static ExecutorService newThreadPerTaskExecutor(ThreadFactory f) { return newCachedThreadPool(f); }
    public static ExecutorService newVirtualThreadPerTaskExecutor() { return newCachedThreadPool(); }
    public static ScheduledExecutorService newSingleThreadScheduledExecutor() { return new ScheduledThreadPoolExecutor(1); }
    public static ScheduledExecutorService newSingleThreadScheduledExecutor(ThreadFactory f) { return new ScheduledThreadPoolExecutor(1, f); }
    public static ScheduledExecutorService newScheduledThreadPool(int n) { return new ScheduledThreadPoolExecutor(n); }
    public static ScheduledExecutorService newScheduledThreadPool(int n, ThreadFactory f) { return new ScheduledThreadPoolExecutor(n, f); }
    public static ExecutorService unconfigurableExecutorService(ExecutorService e) { return e; }
    public static ScheduledExecutorService unconfigurableScheduledExecutorService(ScheduledExecutorService e) { return e; }

    static int pools;

    public static ThreadFactory defaultThreadFactory() {
        int p = ++pools;
        return new ThreadFactory() {
            int n;
            public Thread newThread(Runnable r) { var t = new Thread(r, "pool-" + p + "-thread-" + (++n)); t.setDaemon(false); return t; }
        };
    }
    public static ThreadFactory privilegedThreadFactory() { return defaultThreadFactory(); }

    public static <T> Callable<T> callable(Runnable r, T v) { return () -> { r.run(); return v; }; }
    public static Callable<Object> callable(Runnable r) { return callable(r, null); }
    public static <T> Callable<T> privilegedCallable(Callable<T> c) { return c; }
    public static <T> Callable<T> privilegedCallableUsingCurrentClassLoader(Callable<T> c) { return c; }
}
