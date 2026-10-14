package bc.rt;

import org.teavm.jso.JSBody;

public final class Audio {
    @JSBody(params = {"id", "p", "n", "ch", "bps", "hz"}, script = "bc.abuf(id, p, n, ch, bps, hz);")
    static native void buf(int id, int p, int n, int ch, int bps, int hz);

    @JSBody(params = {"m"}, script = "bc.post({au: m});")
    static native void msg(String m);

    @JSBody(params = {"o", "s", "p", "a", "b", "c", "d", "e", "f"}, script = "bc.post({au: o, s: s, p: p, v: [a, b, c, d, e, f]});")
    static native void cmd(String o, int s, int p, double a, double b, double c, double d, double e, double f);

    static void buffer(int id, long p, int n, int ch, int bps, int hz) { buf(id, (int) p, n, ch, bps, hz); }
    static void srcI(int s, int p, int v) { cmd("si", s, p, v, 0, 0, 0, 0, 0); }
    static void srcF(int s, int p, float v) { cmd("sf", s, p, v, 0, 0, 0, 0, 0); }
    static void src3(int s, int p, float x, float y, float z) { cmd("s3", s, p, x, y, z, 0, 0, 0); }
    static void lis(int p, float a, float b, float c, float d, float e, float f) { cmd("l", 0, p, a, b, c, d, e, f); }
    static void lisF(int p, float v) { cmd("lf", 0, p, v, 0, 0, 0, 0, 0); }
    static void play(int s) { cmd("play", s, 0, 0, 0, 0, 0, 0, 0); }
    static void pause(int s) { cmd("pause", s, 0, 0, 0, 0, 0, 0, 0); }
    static void stop(int s) { cmd("stop", s, 0, 0, 0, 0, 0, 0, 0); }
    static void queue(int s, int b) { cmd("queue", s, b, 0, 0, 0, 0, 0, 0); }
}
