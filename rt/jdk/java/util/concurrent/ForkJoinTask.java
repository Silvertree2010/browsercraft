package java.util.concurrent;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public abstract class ForkJoinTask<V> implements Future<V>, Serializable {
    static final int NEW = 0, RUN = 1, DONE = 2, ABN = 3, CAN = 4;
    volatile int status;
    Throwable ex;
    short tag;

    public ForkJoinTask() {}

    public abstract V getRawResult();
    protected abstract void setRawResult(V v);
    protected abstract boolean exec();

    final void doExec() {
        if (status != NEW) return;
        status = RUN;
        boolean c;
        try { c = exec(); } catch (Throwable t) { fail(t); return; }
        if (c) done(DONE);
        else if (status == RUN) status = NEW + 5;
    }

    synchronized void done(int s) { if (status < DONE || status == NEW + 5) { status = s; notifyAll(); } }
    void fail(Throwable t) { ex = t; done(ABN); }

    static ForkJoinPool cur() {
        return Thread.currentThread() instanceof ForkJoinWorkerThread w ? w.pool : ForkJoinPool.commonPool();
    }

    public final ForkJoinTask<V> fork() { cur().execute(this); return this; }

    void await(long ms) throws InterruptedException {
        synchronized (this) {
            long end = ms > 0 ? System.currentTimeMillis() + ms : 0;
            while (status < DONE || status == NEW + 5) {
                if (ms > 0) { long w = end - System.currentTimeMillis(); if (w <= 0) return; wait(w); }
                else wait();
            }
        }
    }

    void awaitQ() { try { await(0); } catch (InterruptedException e) { Thread.currentThread().interrupt(); } }

    V rep() {
        if (status == CAN) throw new CancellationException();
        if (status == ABN) { if (ex instanceof RuntimeException r) throw r; if (ex instanceof Error e) throw e; throw new RuntimeException(ex); }
        return getRawResult();
    }

    public final V join() { if (status == NEW) doExec(); awaitQ(); return rep(); }
    public final V invoke() { doExec(); awaitQ(); return rep(); }
    public final void quietlyJoin() { if (status == NEW) doExec(); awaitQ(); }
    public final void quietlyInvoke() { doExec(); awaitQ(); }

    public static void invokeAll(ForkJoinTask<?> a, ForkJoinTask<?> b) { b.fork(); a.invoke(); b.join(); }
    public static void invokeAll(ForkJoinTask<?>... ts) { for (int i = 1; i < ts.length; i++) ts[i].fork(); if (ts.length > 0) ts[0].invoke(); for (int i = 1; i < ts.length; i++) ts[i].join(); }
    public static <T extends ForkJoinTask<?>> Collection<T> invokeAll(Collection<T> ts) { for (var t : ts) t.fork(); for (var t : ts) t.join(); return ts; }

    public boolean cancel(boolean i) { if (status == NEW) { done(CAN); return true; } return status == CAN; }
    public final boolean isDone() { return status >= DONE && status != NEW + 5; }
    public final boolean isCancelled() { return status == CAN; }
    public final boolean isCompletedAbnormally() { return status == ABN || status == CAN; }
    public final boolean isCompletedNormally() { return status == DONE; }
    public final Throwable getException() { return status == CAN ? new CancellationException() : ex; }
    public void completeExceptionally(Throwable t) { fail(t); }
    public void complete(V v) { setRawResult(v); done(DONE); }
    public final void quietlyComplete() { done(DONE); }
    public final V get() throws InterruptedException, ExecutionException {
        if (status == NEW) doExec();
        await(0);
        if (status == CAN) throw new CancellationException();
        if (status == ABN) throw new ExecutionException(ex);
        return getRawResult();
    }
    public final V get(long t, TimeUnit u) throws InterruptedException, ExecutionException, TimeoutException {
        await(Math.max(1, u.toMillis(t)));
        if (!isDone()) throw new TimeoutException();
        return get();
    }
    public State state() { return switch (status) { case DONE -> State.SUCCESS; case ABN -> State.FAILED; case CAN -> State.CANCELLED; default -> State.RUNNING; }; }
    public V resultNow() { if (status != DONE) throw new IllegalStateException(); return getRawResult(); }
    public Throwable exceptionNow() { if (status != ABN) throw new IllegalStateException(); return ex; }
    public void reinitialize() { status = NEW; ex = null; }
    public boolean tryUnfork() { return false; }
    public static ForkJoinPool getPool() { return Thread.currentThread() instanceof ForkJoinWorkerThread w ? w.pool : null; }
    public static boolean inForkJoinPool() { return Thread.currentThread() instanceof ForkJoinWorkerThread; }
    public static void helpQuiesce() {}
    public static int getQueuedTaskCount() { return 0; }
    public static int getSurplusQueuedTaskCount() { return 0; }
    public final short getForkJoinTaskTag() { return tag; }
    public final short setForkJoinTaskTag(short t) { short o = tag; tag = t; return o; }
    public final boolean compareAndSetForkJoinTaskTag(short e, short t) { if (tag != e) return false; tag = t; return true; }

    public static ForkJoinTask<?> adapt(Runnable r) { return new A<Object>(r, null, null); }
    public static <T> ForkJoinTask<T> adapt(Runnable r, T v) { return new A<T>(r, null, v); }
    public static <T> ForkJoinTask<T> adapt(Callable<? extends T> c) { return new A<T>(null, c, null); }
    public static <T> ForkJoinTask<T> adaptInterruptible(Callable<? extends T> c) { return adapt(c); }
    public static <T> ForkJoinTask<T> adaptInterruptible(Runnable r, T v) { return adapt(r, v); }
    public static ForkJoinTask<?> adaptInterruptible(Runnable r) { return adapt(r); }

    static final class A<T> extends ForkJoinTask<T> implements RunnableFuture<T> {
        final Runnable r;
        final Callable<? extends T> c;
        T v;
        A(Runnable r, Callable<? extends T> c, T v) { this.r = r; this.c = c; this.v = v; }
        public T getRawResult() { return v; }
        protected void setRawResult(T x) { v = x; }
        protected boolean exec() {
            try { if (c != null) v = c.call(); else r.run(); }
            catch (RuntimeException | Error e) { throw e; }
            catch (Exception e) { throw new RuntimeException(e); }
            return true;
        }
        public void run() { invoke(); }
    }
}
