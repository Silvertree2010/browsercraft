package java.util.concurrent;

public class ForkJoinWorkerThread extends Thread {
    ForkJoinPool pool;
    final ForkJoinPool.WorkQueue workQueue = new ForkJoinPool.WorkQueue();

    protected ForkJoinWorkerThread(ForkJoinPool p) { super(); pool = p; setDaemon(true); }
    protected ForkJoinWorkerThread(ThreadGroup g, ForkJoinPool p, boolean c) { this(p); }

    public ForkJoinPool getPool() { return pool; }
    public int getPoolIndex() { return 0; }
    public int getQueuedTaskCount() { return 0; }
    boolean hasKnownQueuedWork() { return false; }
    protected void onStart() {}
    protected void onTermination(Throwable t) {}
    public void run() { pool.work(this); }
}
