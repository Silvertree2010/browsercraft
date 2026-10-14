package bc.gpu;

import org.teavm.interop.Async;
import org.teavm.interop.AsyncCallback;
import org.teavm.jso.JSBody;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;

public final class G {
    private G() {}

    @JSBody(params = {"s", "u"}, script = "return bc.g.buf(s, u);")
    static native int buf(double s, int u);
    @JSBody(params = {"i"}, script = "bc.g.bufDel(i);")
    static native void bufDel(int i);
    @JSBody(params = {"i", "o", "p", "n"}, script = "bc.g.write(i, o, p, n);")
    static native void write(int i, double o, int p, int n);
    @JSBody(params = {"s", "so", "d", "dof", "n"}, script = "bc.g.copyBB(s, so, d, dof, n);")
    static native void copyBB(int s, double so, int d, double dof, double n);
    @JSBody(params = {"w", "h", "l", "m", "f", "u", "c"}, script = "return bc.g.tex(w, h, l, m, f, u, c);")
    static native int tex(int w, int h, int l, int m, int f, int u, boolean c);
    @JSBody(params = {"i"}, script = "bc.g.texDel(i);")
    static native void texDel(int i);
    @JSBody(params = {"t", "b", "m", "c"}, script = "return bc.g.view(t, b, m, c);")
    static native int view(int t, int b, int m, boolean c);
    @JSBody(params = {"i"}, script = "bc.g.viewDel(i);")
    static native void viewDel(int i);
    @JSBody(params = {"au", "av", "mi", "ma", "an", "ml"}, script = "return bc.g.sampler(au, av, mi, ma, an, ml);")
    static native int sampler(int au, int av, int mi, int ma, int an, double ml);
    @JSBody(params = {"t", "m", "l", "x", "y", "w", "h", "p", "n", "r"}, script = "bc.g.writeTex(t, m, l, x, y, w, h, p, n, r);")
    static native void writeTex(int t, int m, int l, int x, int y, int w, int h, int p, int n, int r);
    @JSBody(params = {"t", "m", "dx", "dy", "s", "sx", "sy", "w", "h"}, script = "bc.g.copyTT(s, t, m, dx, dy, sx, sy, w, h);")
    static native void copyTT(int t, int m, int dx, int dy, int s, int sx, int sy, int w, int h);
    @JSBody(params = {"j"}, script = "bc.g.passJ(j);")
    static native void pass(String j);
    @JSBody(params = {}, script = "bc.g.endPass();")
    static native void endPass();
    @JSBody(params = {"c"}, script = "return bc.g.module(c);")
    static native int module(String c);
    @JSBody(params = {"j", "v", "f"}, script = "return bc.g.pipe(j, v, f);")
    static native int pipe(String j, int v, int f);
    @JSBody(params = {"i"}, script = "bc.g.setPipe(i);")
    static native void setPipe(int i);
    @JSBody(params = {"s", "b", "o"}, script = "bc.g.setVB(s, b, o);")
    static native void setVB(int s, int b, double o);
    @JSBody(params = {"b", "f", "o"}, script = "bc.g.setIB(b, f, o);")
    static native void setIB(int b, int f, double o);
    @JSBody(params = {"p", "a", "n"}, script = "bc.g.bindM(p, a, n);")
    static native void bind(int p, int a, int n);
    @JSBody(params = {"x", "y", "w", "h"}, script = "bc.g.scissor(x, y, w, h);")
    static native void scissor(int x, int y, int w, int h);
    @JSBody(params = {"a", "b", "c", "d"}, script = "bc.g.draw(a, b, c, d);")
    static native void draw(int a, int b, int c, int d);
    @JSBody(params = {"a", "b", "c", "d", "e"}, script = "bc.g.drawIdx(a, b, c, d, e);")
    static native void drawIdx(int a, int b, int c, int d, int e);
    @JSBody(params = {"b", "o"}, script = "bc.g.drawInd(b, o);")
    static native void drawInd(int b, double o);
    @JSBody(params = {"b", "o"}, script = "bc.g.drawIdxInd(b, o);")
    static native void drawIdxInd(int b, double o);
    @JSBody(params = {}, script = "bc.g.submit();")
    static native void submit();
    @JSBody(params = {"v", "w", "h"}, script = "bc.g.present(v, w, h);")
    static native void present(int v, int w, int h);
    @JSBody(params = {"w", "h"}, script = "bc.g.resize(w, h);")
    static native void resize(int w, int h);
    @JSBody(params = {}, script = "return bc.g.fence();")
    static native int fence();
    @JSBody(params = {}, script = "return bc.g.doneMax;")
    static native int done();
    @JSBody(params = {"t", "m", "x", "y", "w", "h", "bs", "d"}, script = "return bc.g.readTex(t, m, x, y, w, h, bs, d);")
    static native int readTex(int t, int m, int x, int y, int w, int h, int bs, int d);
    @JSBody(params = {"b", "o", "n", "d"}, script = "return bc.g.readBack(b, o, n, d);")
    static native int readBack(int b, double o, int n, int d);
    @JSBody(params = {"k"}, script = "return bc.gi[k];")
    static native String info(String k);
    @JSBody(params = {"k"}, script = "return +bc.gi[k];")
    static native int infoI(String k);

    @JSFunctor
    interface Cb extends JSObject { void run(); }

    @JSBody(params = {"f"}, script = "(self.requestAnimationFrame || function(g) { setTimeout(g, 8); })(function() { f(); });")
    static native void raf(Cb f);

    @Async
    static native void frame();

    static void frame(AsyncCallback<Void> cb) { raf(() -> cb.complete(null)); }
}
