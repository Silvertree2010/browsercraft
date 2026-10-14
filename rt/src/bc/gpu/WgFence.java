package bc.gpu;

import com.mojang.renderpearl.api.commands.GpuFence;

public class WgFence implements GpuFence {
    final int t;
    boolean done;

    WgFence(int t) { this.t = t; }

    public void close() { done = true; }

    public boolean awaitCompletion(long ns) {
        if (done) return true;
        if (G.done() >= t) return done = true;
        if (ns == 0) return false;
        long end = ns < 0 ? Long.MAX_VALUE : System.nanoTime() + ns;
        while (G.done() < t) {
            if (System.nanoTime() > end) return false;
            try { Thread.sleep(1); } catch (InterruptedException e) { return false; }
        }
        return done = true;
    }
}
