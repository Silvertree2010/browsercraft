package java.util.concurrent;

final class DelayScheduler {
    static void start(ScheduledForkJoinTask<?> t) {
        var th = new Thread(() -> {
            try {
                long d = t.delay / 1000000L;
                if (d > 0) Thread.sleep(d);
                while (!t.isCancelled()) {
                    t.pool.execute((ForkJoinTask<?>) t);
                    if (t.period == 0) break;
                    Thread.sleep(Math.max(1, Math.abs(t.period) / 1000000L));
                    t.reinitialize();
                }
            } catch (InterruptedException e) {}
        }, "Delay");
        th.setDaemon(true);
        th.start();
    }

    static final class ScheduledForkJoinTask<T> extends ForkJoinTask<T> implements ScheduledFuture<T>, RunnableFuture<T> {
        final long delay, period;
        final Runnable r;
        final Callable<T> c;
        final ForkJoinPool pool;
        final long at;
        T v;

        ScheduledForkJoinTask(long delay, long period, boolean fixed, Runnable r, Callable<T> c, ForkJoinPool pool) {
            this.delay = delay; this.period = period; this.r = r; this.c = c; this.pool = pool;
            this.at = System.nanoTime() + delay;
        }
        public T getRawResult() { return v; }
        protected void setRawResult(T x) { v = x; }
        protected boolean exec() {
            try { if (c != null) v = c.call(); else r.run(); }
            catch (RuntimeException | Error e) { throw e; }
            catch (Exception e) { throw new RuntimeException(e); }
            return true;
        }
        public void run() { invoke(); }
        public long getDelay(TimeUnit u) { return u.convert(at - System.nanoTime(), TimeUnit.NANOSECONDS); }
        public int compareTo(Delayed o) { return Long.compare(getDelay(TimeUnit.NANOSECONDS), o.getDelay(TimeUnit.NANOSECONDS)); }
        public boolean isPeriodic() { return period != 0; }
    }
}
