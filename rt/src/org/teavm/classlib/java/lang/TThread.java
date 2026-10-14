/*
 *  Copyright 2014 Alexey Andreev.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.teavm.classlib.java.lang;

import org.teavm.classlib.PlatformDetector;
import org.teavm.interop.Async;
import org.teavm.interop.AsyncCallback;
import org.teavm.platform.Platform;
import org.teavm.platform.PlatformRunnable;
import org.teavm.runtime.EventQueue;
import org.teavm.runtime.Fiber;

public class TThread extends TObject implements TRunnable {
    private static TThread mainThread = new TThread("main");
    private static TThread currentThread = mainThread;
    private static int nextId = 1;
    private static int activeCount = 1;
    private static UncaughtExceptionHandler defaultUncaughtExceptionHandler = new TDefaultUncaughtExceptionHandler();
    private UncaughtExceptionHandler uncaughtExceptionHandler;
    private long id;
    private int priority;
    private boolean daemon;
    private long timeSliceStart;
    private int yieldCount;
    private final Object finishedLock = new Object();
    private boolean interruptedFlag;
    public TThreadInterruptHandler interruptHandler;
    public Object key;

    private String name;
    private boolean alive = true;
    TRunnable target;

    public TThread() {
        this((TRunnable) null, (String) null);
    }

    public TThread(String name) {
        this((TRunnable) null, name);
    }

    public TThread(TRunnable target) {
        this(target, null);
    }

    public TThread(TRunnable target, String name) {
        this.name = name != null ? name : "Thread-" + nextId;
        this.target = target;
        id = nextId++;
    }

    public TThread(TThreadGroup g, TRunnable target) { this(target, null); group = g; }
    public TThread(TThreadGroup g, String name) { this((TRunnable) null, name); group = g; }
    public TThread(TThreadGroup g, TRunnable target, String name) { this(target, name); group = g; }
    public TThread(TThreadGroup g, TRunnable target, String name, long stack) { this(target, name); group = g; }
    public TThread(TThreadGroup g, TRunnable target, String name, long stack, boolean inherit) { this(target, name); group = g; }

    private TThreadGroup group;
    private boolean started;
    private ClassLoader ccl;

    public enum State { NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED }

    public State getState() { return !started ? State.NEW : alive ? State.RUNNABLE : State.TERMINATED; }
    public final TThreadGroup getThreadGroup() { if (group == null) group = TThreadGroup.MAIN; return group; }
    public final long threadId() { return id; }
    public final boolean isVirtual() { return false; }
    public static void onSpinWait() { TThread.yield(); }
    public static void sleep(java.time.Duration d) throws TInterruptedException { sleep(Math.max(0, d.toMillis())); }
    public static void sleep(long ms, int ns) throws TInterruptedException { sleep(ms); }
    public void setContextClassLoader(ClassLoader c) { ccl = c; }
    public final void checkAccess() {}
    public static boolean interrupted0() { return interrupted(); }
    public static java.util.Map<TThread, TStackTraceElement[]> getAllStackTraces() { return new java.util.HashMap<>(); }
    public static int enumerate(TThread[] a) { return 0; }

    public void start() {
        if (started) throw new IllegalThreadStateException();
        started = true;
        if (PlatformDetector.isLowLevel() || PlatformDetector.isWebAssemblyGC()) {
            boolean daemon = this.daemon;
            if (!daemon) {
                Fiber.userThreadCount++;
            }
            EventQueue.offer(() -> Fiber.start(this::runThread, daemon));
        } else {
            Platform.startThread(this::runThread);
        }
    }

    private void runThread() {
        try {
            activeCount++;
            setCurrentThread(TThread.this);
            key = new Object();
            TThread.this.run();
        } catch (Throwable t) {
            getUncaughtExceptionHandler().uncaughtException(this, t);
        } finally {
            synchronized (finishedLock) {
                finishedLock.notifyAll();
            }
            alive = false;
            activeCount--;
            key = null;
            setCurrentThread(mainThread);
        }
    }

    static void setCurrentThread(TThread thread) {
        if (currentThread != thread) {
            currentThread = thread;
        }
        currentThread.timeSliceStart = System.currentTimeMillis();
    }

    static TThread getMainThread() {
        return mainThread;
    }

    @Override
    public void run() {
        if (target != null) {
            target.run();
        }
    }

    public static TThread currentThread() {
        return currentThread;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public final boolean isDaemon() {
        return daemon;
    }

    public final void setDaemon(boolean daemon) {
        this.daemon = daemon;
    }

    public final void join(long millis, int nanos) throws InterruptedException {
        if (currentThread() == this || !alive) {
            return;
        }
        synchronized (finishedLock) {
            finishedLock.wait(millis, nanos);
        }
    }

    public final void join(long millis) throws InterruptedException {
        join(millis, 0);
    }

    public final void join() throws InterruptedException {
        join(0);
    }

    public static void yield() {
        TThread currentThread = currentThread();
        if (currentThread.timeSliceStart + 4 < System.currentTimeMillis()) {
            bc.rt.Js.lo();
            switchContext(currentThread);
        }
    }

    @Async
    static native void switchContext(TThread thread);

    private static void switchContext(final TThread thread, final AsyncCallback<Void> callback) {
        if (PlatformDetector.isLowLevel() || PlatformDetector.isWebAssemblyGC()) {
            EventQueue.offer(() -> {
                setCurrentThread(thread);
                callback.complete(null);
            });
        } else {
            Platform.postpone(() -> {
                setCurrentThread(thread);
                callback.complete(null);
            });
        }
    }

    public void interrupt() {
        interruptedFlag = true;
        if (interruptHandler != null) {
            interruptHandler.interrupted();
            interruptHandler = null;
        }
    }

    public static boolean interrupted() {
        TThread thread = currentThread();
        boolean result = thread.interruptedFlag;
        thread.interruptedFlag = false;
        return result;
    }

    public boolean isInterrupted() {
        return interruptedFlag;
    }

    public boolean isAlive() {
        return alive;
    }

    public static int activeCount() {
        return activeCount;
    }

    public long getId() {
        return id;
    }

    public static boolean holdsLock(TObject obj) {
        return TObject.holdsLock(obj);
    }

    @Async
    public static native void sleep(long millis) throws TInterruptedException;

    private static void sleep(long millis, AsyncCallback<Void> callback) {
        TThread current = currentThread();
        SleepHandler handler = new SleepHandler(current, callback);
        if (PlatformDetector.isLowLevel() || PlatformDetector.isWebAssemblyGC()) {
            if (current.interruptedFlag) {
                handler.interrupted();
            } else {
                handler.scheduleId = EventQueue.offer(handler, System.currentTimeMillis() + millis);
                current.interruptHandler = handler;
            }
        } else {
            int intMillis = millis < Integer.MAX_VALUE ? (int) millis : Integer.MAX_VALUE;
            handler.scheduleId = Platform.schedule(handler, intMillis);
            current.interruptHandler = handler;
        }
    }

    static class SleepHandler implements PlatformRunnable, EventQueue.Event, TThreadInterruptHandler {
        private TThread thread;
        private AsyncCallback<Void> callback;
        private boolean isInterrupted;
        int scheduleId;

        SleepHandler(TThread thread, AsyncCallback<Void> callback) {
            this.thread = thread;
            this.callback = callback;
        }

        @Override
        public void interrupted() {
            thread.interruptedFlag = false;
            isInterrupted = true;
            if (PlatformDetector.isLowLevel() || PlatformDetector.isWebAssemblyGC()) {
                EventQueue.kill(scheduleId);
                EventQueue.offer(() -> callback.error(new TInterruptedException()));
            } else {
                Platform.killSchedule(scheduleId);
                Platform.postpone(() -> callback.error(new TInterruptedException()));
            }
        }

        @Override
        public void run() {
            if (!isInterrupted) {
                thread.interruptHandler = null;
                setCurrentThread(thread);
                callback.complete(null);
            }
        }
    }

    public final void setPriority(int newPriority) {
        this.priority = newPriority;
    }

    public final int getPriority() {
        return this.priority;
    }

    public TStackTraceElement[] getStackTrace() {
        return new TStackTraceElement[0];
    }

    public TClassLoader getContextClassLoader() {
        return TClassLoader.getSystemClassLoader();
    }
    
    public UncaughtExceptionHandler getUncaughtExceptionHandler() {
        if (this.uncaughtExceptionHandler != null) {
            return this.uncaughtExceptionHandler;
        }
        return defaultUncaughtExceptionHandler;
    }
    
    public void setUncaughtExceptionHandler(UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
    }
    
    public static UncaughtExceptionHandler getDefaultUncaughtExceptionHandler() {
        return defaultUncaughtExceptionHandler;
    }
    
    public static void setDefaultUncaughtExceptionHandler(UncaughtExceptionHandler handler) {
        defaultUncaughtExceptionHandler = handler;
    }
    
    public interface UncaughtExceptionHandler {
        void uncaughtException(TThread t, Throwable e);
    }
}
