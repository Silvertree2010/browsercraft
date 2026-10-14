package bc.rt;

import org.teavm.jso.JSBody;

public final class Js {
    @JSBody(params = {}, script = "bc.lo = 1;")
    public static native void lo();
    @JSBody(params = {}, script = "return performance.now();")
    public static native double now();

    @JSBody(params = {}, script = "return bc.text || '';")
    public static native String lastText();

    @JSBody(params = {}, script = "return self.location.origin;")
    public static native String origin();

    @JSBody(params = {}, script = "return self.location.host;")
    public static native String host();

    @JSBody(params = {"s"}, script = "console.log(s);")
    public static native void log(String s);
}
