package java.util.concurrent;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ForkJoinPool extends AbstractExecutorService {
    public interface ForkJoinWorkerThreadFactory { ForkJoinWorkerThread newThread(ForkJoinPool p); }
    public interface ManagedBlocker { boolean block() throws InterruptedException; boolean isReleasable(); }

    public static final ForkJoinWorkerThreadFactory defaultForkJoinWorkerThreadFactory = ForkJoinWorkerThread::new;
    static ForkJoinPool common;
    static int seq;

    static final class WorkQueue {
        int helpComplete(ForkJoinTask<?> t, boolean o, int m) { return 0; }
    }

    final int par;
    final ForkJoinWorkerThreadFactory fac;
    final Thread.UncaughtExceptionHandler ueh;
    final ArrayDeque<Runnable> q = new ArrayDeque<>();
    final ArrayList<ForkJoinWorkerThread> ws = new ArrayList<>();
    final String pfx;
    int idle;
    boolean down;

    public ForkJoinPool() { this(2); }
    public ForkJoinPool(int p) { this(p, defaultForkJoinWorkerThreadFactory, null, false); }
    public ForkJoinPool(int p, ForkJoinWorkerThreadFactory f, Thread.UncaughtExceptionHandler h, boolean a) {
        par = Math.max(1, Math.min(p, 4));
        fac = f == null ? defaultForkJoinWorkerThreadFactory : f;
        ueh = h;
        pfx = "ForkJoinPool-" + (++seq) + "-worker-";
    }
    public ForkJoinPool(int p, ForkJoinWorkerThreadFactory f, Thread.UncaughtExceptionHandler h, boolean a, int c, int m, int mr, java.util.function.Predicate<? super ForkJoinPool> s, long k, TimeUnit u) { this(p, f, h, a); }

    public static ForkJoinPool commonPool() { if (common == null) common = new ForkJoinPool(2); return common; }
    static ForkJoinPool asyncCommonPool() { return commonPool(); }
    public static int getCommonPoolParallelism() { return commonPool().par; }

    public static void managedBlock(ManagedBlocker b) throws InterruptedException {
        while (!b.isReleasable()) if (b.block()) break;
    }
    static void helpAsyncBlocker(Executor e, ManagedBlocker b) {}

    void push(Runnable r) {
        if (down) throw new RejectedExecutionException("shutdown");
        synchronized (this) {
            q.add(r);
            if (idle > 0) notify();
            else if (ws.size() < par) spawn();
        }
    }

    void spawn() {
        var w = fac.newThread(this);
        if (w == null) return;
        w.pool = this;
        if (w.getName() == null || w.getName().startsWith("Thread-")) w.setName(pfx + ws.size());
        ws.add(w);
        w.start();
    }

    void work(ForkJoinWorkerThread w) {
        Throwable ex = null;
        try {
            w.onStart();
            for (;;) {
                Runnable r;
                synchronized (this) {
                    while ((r = q.poll()) == null) {
                        if (down) return;
                        idle++;
                        try { wait(); } catch (InterruptedException e) { if (down) return; } finally { idle--; }
                    }
                }
                try { r.run(); } catch (Throwable t) {
                    if (ueh != null) ueh.uncaughtException(w, t); else t.printStackTrace();
                }
                Thread.yield();
            }
        } catch (Throwable t) {
            ex = t;
        } finally {
            synchronized (this) { ws.remove(w); notifyAll(); }
            w.onTermination(ex);
        }
    }

    static Runnable wrap(ForkJoinTask<?> t) { return t::doExec; }

    public void execute(ForkJoinTask<?> t) { push(wrap(t)); }
    public void execute(Runnable r) { push(r instanceof ForkJoinTask<?> t ? wrap(t) : r); }
    public <T> ForkJoinTask<T> submit(ForkJoinTask<T> t) { execute(t); return t; }
    public <T> ForkJoinTask<T> submit(Callable<T> c) { var t = ForkJoinTask.adapt(c); execute(t); return t; }
    public <T> ForkJoinTask<T> submit(Runnable r, T v) { var t = ForkJoinTask.adapt(r, v); execute(t); return t; }
    public ForkJoinTask<?> submit(Runnable r) { var t = ForkJoinTask.adapt(r); execute(t); return t; }
    public <T> T invoke(ForkJoinTask<T> t) { t.doExec(); return t.join(); }

    public int getParallelism() { return par; }
    public int getPoolSize() { return ws.size(); }
    public int getActiveThreadCount() { return ws.size() - idle; }
    public int getRunningThreadCount() { return getActiveThreadCount(); }
    public boolean isQuiescent() { return q.isEmpty() && idle == ws.size(); }
    public long getQueuedTaskCount() { return q.size(); }
    public int getQueuedSubmissionCount() { return q.size(); }
    public boolean hasQueuedSubmissions() { return !q.isEmpty(); }
    public long getStealCount() { return 0; }
    public boolean getAsyncMode() { return true; }
    public Thread.UncaughtExceptionHandler getUncaughtExceptionHandler() { return ueh; }
    public ForkJoinWorkerThreadFactory getFactory() { return fac; }

    public void shutdown() { synchronized (this) { down = true; notifyAll(); } }
    public List<Runnable> shutdownNow() { List<Runnable> l; synchronized (this) { l = new ArrayList<>(q); q.clear(); down = true; notifyAll(); } return l; }
    public boolean isShutdown() { return down; }
    public boolean isTerminated() { return down && ws.isEmpty(); }
    public boolean isTerminating() { return down && !ws.isEmpty(); }
    public boolean awaitTermination(long t, TimeUnit u) throws InterruptedException {
        long end = System.currentTimeMillis() + u.toMillis(t);
        synchronized (this) {
            while (!ws.isEmpty()) {
                long w = end - System.currentTimeMillis();
                if (w <= 0) return false;
                wait(w);
            }
        }
        return true;
    }
    public boolean awaitQuiescence(long t, TimeUnit u) { return isQuiescent(); }
    public void close() { shutdown(); }

    <T> DelayScheduler.ScheduledForkJoinTask<T> scheduleDelayedTask(DelayScheduler.ScheduledForkJoinTask<T> t) { DelayScheduler.start(t); return t; }
    void executeEnabledScheduledTask(DelayScheduler.ScheduledForkJoinTask<?> t) { execute((ForkJoinTask<?>) t); }

    public String toString() { return super.toString() + "[parallelism = " + par + ", size = " + ws.size() + ", queued = " + q.size() + "]"; }
}
