package bc.rt;

import static org.lwjgl.system.MemoryUtil.*;

import java.nio.charset.StandardCharsets;
import org.lwjgl.sdl.*;
import org.teavm.jso.JSBody;

public final class Sdl {
    static final long WIN = 0x5000;
    static long evBuf, keys, mode, modes, displays, fmt, str, cur;
    static int mods;
    static String err = "";
    static boolean relative;

    @JSBody(params = {"a"}, script = "return bc.ev(a);")
    static native int ev(int a);

    @JSBody(params = {}, script = "return bc.win.w;")
    static native int w();

    @JSBody(params = {}, script = "return bc.win.h;")
    static native int h();

    @JSBody(params = {}, script = "return bc.win.dpr;")
    static native float dpr();

    @JSBody(params = {}, script = "return bc.win.hz;")
    static native float hz();

    @JSBody(params = {}, script = "return bc.win.sw;")
    static native int sw();

    @JSBody(params = {}, script = "return bc.win.sh;")
    static native int sh();

    @JSBody(params = {"k", "a"}, script = "bc.post({k: k, a: a});")
    static native void post(String k, String a);

    @JSBody(params = {"k", "a"}, script = "bc.post({k: k, n: a});")
    static native void postN(String k, double a);

    @JSBody(params = {}, script = "return bc.clip || '';")
    static native String clip();

    @JSBody(params = {"s"}, script = "bc.clip = s; bc.post({k: 'clip', a: s});")
    static native void setClip(String s);

    static String s(long p) { return p == 0 ? null : memUTF8(p); }

    static long cstr(String s) {
        var b = s.getBytes(StandardCharsets.UTF_8);
        long p = nmemAlloc(b.length + 1);
        for (int i = 0; i < b.length; i++) memPutByte(p + i, b[i]);
        memPutByte(p + b.length, (byte) 0);
        return p;
    }

    static long kept(String s) {
        if (str != 0) nmemFree(str);
        str = cstr(s);
        return str;
    }

    static long f(float v) { return Float.floatToRawIntBits(v) & 0xffffffffL; }
    static float fa(long v) { return Float.intBitsToFloat((int) v); }

    static void reg(String n, Nat.F f) { Nat.reg(n, f); }

    public static void install() {
        evBuf = nmemCalloc(1, 256);
        keys = nmemCalloc(1, 512);
        mode = nmemCalloc(1, SDL_DisplayMode.SIZEOF);
        modes = nmemCalloc(1, 16);
        displays = nmemCalloc(1, 8);
        fmt = nmemCalloc(1, 64);
        memPutInt(displays, 1);
        memPutInt(displays + 4, 0);

        reg("SDL_Init", a -> 1);
        reg("SDL_InitSubSystem", a -> 1);
        reg("SDL_Quit", a -> 0);
        reg("SDL_QuitSubSystem", a -> 0);
        reg("SDL_WasInit", a -> a[0]);
        reg("SDL_SetHint", a -> 1);
        reg("SDL_SetHintWithPriority", a -> 1);
        reg("SDL_GetHint", a -> 0);
        reg("SDL_SetAppMetadata", a -> 1);
        reg("SDL_SetAppMetadataProperty", a -> 1);
        reg("SDL_SetLogOutputFunction", a -> 0);
        reg("SDL_SetLogPriorities", a -> 0);
        reg("SDL_SetLogPriority", a -> 0);
        reg("SDL_GetError", a -> kept(err));
        reg("SDL_ClearError", a -> { err = ""; return 1; });
        reg("SDL_free", a -> { if (a[0] != 0) nmemFree(a[0]); return 0; });
        reg("SDL_malloc", a -> nmemAlloc(a[0]));
        reg("SDL_GetPlatform", a -> kept("Linux"));
        reg("SDL_GetCurrentVideoDriver", a -> kept("browser"));
        reg("SDL_GetTicksNS", a -> (long) (Js.now() * 1e6));
        reg("SDL_GetTicks", a -> (long) Js.now());
        reg("SDL_GetPerformanceCounter", a -> (long) (Js.now() * 1e6));
        reg("SDL_GetPerformanceFrequency", a -> 1000000000L);
        reg("SDL_Delay", a -> { try { Thread.sleep(a[0]); } catch (InterruptedException e) {} return 0; });
        reg("SDL_OpenURL", a -> { post("url", s(a[0])); return 1; });
        reg("SDL_ShowSimpleMessageBox", a -> { System.err.println("[messagebox] " + s(a[1]) + ": " + s(a[2])); return 1; });
        reg("SDL_ShowMessageBox", a -> { if (a[1] != 0) memPutInt(a[1], 0); return 1; });

        reg("SDL_CreateWindow", a -> { post("title", s(a[0])); return WIN; });
        reg("SDL_DestroyWindow", a -> 0);
        reg("SDL_GetWindowID", a -> 1);
        reg("SDL_GetWindowFromID", a -> WIN);
        reg("SDL_GetWindowFromEvent", a -> WIN);
        reg("SDL_SetWindowTitle", a -> { post("title", s(a[1])); return 1; });
        reg("SDL_SetWindowIcon", a -> 1);
        reg("SDL_SetWindowSize", a -> 1);
        reg("SDL_SetWindowPosition", a -> 1);
        reg("SDL_SetWindowMinimumSize", a -> 1);
        reg("SDL_SetWindowMaximumSize", a -> 1);
        reg("SDL_SetWindowBordered", a -> 1);
        reg("SDL_SetWindowResizable", a -> 1);
        reg("SDL_SetWindowFullscreen", a -> { postN("fullscreen", a[1]); return 1; });
        reg("SDL_SetWindowFullscreenMode", a -> 1);
        reg("SDL_GetWindowFullscreenMode", a -> 0);
        reg("SDL_RestoreWindow", a -> 1);
        reg("SDL_MaximizeWindow", a -> 1);
        reg("SDL_ShowWindow", a -> 1);
        reg("SDL_RaiseWindow", a -> 1);
        reg("SDL_SyncWindow", a -> 1);
        reg("SDL_GetWindowFlags", a -> 0x200L | 0x400L);
        reg("SDL_GetWindowPixelDensity", a -> f(1f));
        reg("SDL_GetWindowDisplayScale", a -> f(dpr()));
        reg("SDL_GetWindowSizeInPixels", a -> { if (a[1] != 0) memPutInt(a[1], w()); if (a[2] != 0) memPutInt(a[2], h()); return 1; });
        reg("SDL_GetWindowSize", a -> { if (a[1] != 0) memPutInt(a[1], w()); if (a[2] != 0) memPutInt(a[2], h()); return 1; });
        reg("SDL_GetWindowPosition", a -> { if (a[1] != 0) memPutInt(a[1], 0); if (a[2] != 0) memPutInt(a[2], 0); return 1; });
        reg("SDL_SetWindowMouseGrab", a -> 1);
        reg("SDL_SetWindowRelativeMouseMode", a -> { relative = a[1] != 0; postN("grab", a[1]); return 1; });
        reg("SDL_GetWindowRelativeMouseMode", a -> relative ? 1 : 0);
        reg("SDL_WarpMouseInWindow", a -> 0);
        reg("SDL_StartTextInput", a -> { postN("text", 1); return 1; });
        reg("SDL_StopTextInput", a -> { postN("text", 0); return 1; });
        reg("SDL_SetTextInputArea", a -> 1);
        reg("SDL_ClearComposition", a -> 1);

        reg("SDL_GetDisplays", a -> { if (a[0] != 0) memPutInt(a[0], 1); long p = nmemAlloc(8); memPutInt(p, 1); memPutInt(p + 4, 0); return p; });
        reg("SDL_GetPrimaryDisplay", a -> 1);
        reg("SDL_GetDisplayForWindow", a -> 1);
        reg("SDL_GetDisplayName", a -> kept("Browser"));
        reg("SDL_GetDisplayBounds", a -> { memPutInt(a[1], 0); memPutInt(a[1] + 4, 0); memPutInt(a[1] + 8, sw()); memPutInt(a[1] + 12, sh()); return 1; });
        reg("SDL_GetDisplayUsableBounds", a -> { memPutInt(a[1], 0); memPutInt(a[1] + 4, 0); memPutInt(a[1] + 8, sw()); memPutInt(a[1] + 12, sh()); return 1; });
        reg("SDL_GetCurrentDisplayMode", a -> fillMode());
        reg("SDL_GetDesktopDisplayMode", a -> fillMode());
        reg("SDL_GetFullscreenDisplayModes", a -> { fillMode(); memPutLong(modes, mode); memPutLong(modes + 8, 0); if (a[1] != 0) memPutInt(a[1], 1); long p = nmemAlloc(16); memPutLong(p, mode); memPutLong(p + 8, 0); return p; });
        reg("SDL_GetClosestFullscreenDisplayMode", a -> { fillMode(); memCopy(mode, a[5], SDL_DisplayMode.SIZEOF); return 1; });
        reg("SDL_GetPixelFormatDetails", a -> fmt);

        reg("SDL_PumpEvents", a -> 0);
        reg("SDL_FlushEvents", a -> 0);
        reg("SDL_FlushEvent", a -> 0);
        reg("SDL_PollEvent", a -> poll(a[0]) ? 1 : 0);
        reg("SDL_GetKeyboardState", a -> { if (a[0] != 0) memPutInt(a[0], 512); return keys; });
        reg("SDL_GetModState", a -> mods);
        reg("SDL_GetKeyFromScancode", a -> Keys.key((int) a[0]));
        reg("SDL_GetScancodeFromKey", a -> Keys.scan((int) a[0]));
        reg("SDL_GetKeyName", a -> kept(Keys.name((int) a[0])));
        reg("SDL_GetScancodeName", a -> kept(Keys.name(Keys.key((int) a[0]))));
        reg("SDL_GetMouseState", a -> mouse(a));
        reg("SDL_GetGlobalMouseState", a -> mouse(a));
        reg("SDL_GetRelativeMouseState", a -> { if (a[0] != 0) memPutInt(a[0], 0); if (a[1] != 0) memPutInt(a[1], 0); return 0; });
        reg("SDL_CreateSystemCursor", a -> 0x6000 + a[0]);
        reg("SDL_GetDefaultCursor", a -> 0x6000);
        reg("SDL_CreateColorCursor", a -> 0x6000);
        reg("SDL_SetCursor", a -> { cur = a[0]; postN("cursor", a[0] - 0x6000); return 1; });
        reg("SDL_DestroyCursor", a -> 0);
        reg("SDL_ShowCursor", a -> 1);
        reg("SDL_HideCursor", a -> 1);
        reg("SDL_GetClipboardText", a -> cstr(clip()));
        reg("SDL_SetClipboardText", a -> { setClip(s(a[0])); return 1; });
        reg("SDL_HasClipboardText", a -> clip().isEmpty() ? 0 : 1);
        reg("SDL_CreateSurfaceFrom", a -> nmemCalloc(1, 128));
        reg("SDL_DestroySurface", a -> { if (a[0] != 0) nmemFree(a[0]); return 0; });
        reg("SDL_AddSurfaceAlternateImage", a -> 1);
    }

    static double mx, my;

    static long mouse(long[] a) {
        if (a[0] != 0) memPutInt(a[0], Float.floatToRawIntBits((float) mx));
        if (a[1] != 0) memPutInt(a[1], Float.floatToRawIntBits((float) my));
        return 0;
    }

    static long fillMode() {
        memPutInt(mode + SDL_DisplayMode.DISPLAYID, 1);
        memPutInt(mode + SDL_DisplayMode.FORMAT, 0x16362004);
        memPutInt(mode + SDL_DisplayMode.W, sw());
        memPutInt(mode + SDL_DisplayMode.H, sh());
        memPutFloat(mode + SDL_DisplayMode.PIXEL_DENSITY, dpr());
        memPutFloat(mode + SDL_DisplayMode.REFRESH_RATE, hz());
        memPutInt(mode + SDL_DisplayMode.REFRESH_RATE_NUMERATOR, Math.round(hz()));
        memPutInt(mode + SDL_DisplayMode.REFRESH_RATE_DENOMINATOR, 1);
        return mode;
    }

    static long textBuf;

    static boolean poll(long e) {
        int t = ev((int) evBuf);
        if (t == 0) return false;
        memSet(e, 0, 128);
        memPutInt(e, t);
        memPutLong(e + 8, (long) (Js.now() * 1e6));
        switch (t) {
            case 0x300, 0x301 -> {
                int sc = (int) memGetDouble(evBuf);
                int key = (int) memGetDouble(evBuf + 8);
                mods = (int) memGetDouble(evBuf + 16);
                boolean rep = memGetDouble(evBuf + 24) != 0;
                memPutInt(e + SDL_KeyboardEvent.WINDOWID, 1);
                memPutInt(e + SDL_KeyboardEvent.SCANCODE, sc);
                memPutInt(e + SDL_KeyboardEvent.KEY, key == 0 ? Keys.key(sc) : key);
                memPutShort(e + SDL_KeyboardEvent.MOD, (short) mods);
                memPutByte(e + SDL_KeyboardEvent.DOWN, (byte) (t == 0x300 ? 1 : 0));
                memPutByte(e + SDL_KeyboardEvent.REPEAT, (byte) (rep ? 1 : 0));
                if (sc >= 0 && sc < 512) memPutByte(keys + sc, (byte) (t == 0x300 ? 1 : 0));
            }
            case 0x303 -> {
                var s = Js.lastText();
                if (textBuf != 0) nmemFree(textBuf);
                textBuf = cstr(s);
                memPutInt(e + SDL_TextInputEvent.WINDOWID, 1);
                memPutLong(e + SDL_TextInputEvent.TEXT, textBuf);
            }
            case 0x400 -> {
                mx = memGetDouble(evBuf);
                my = memGetDouble(evBuf + 8);
                memPutInt(e + SDL_MouseMotionEvent.WINDOWID, 1);
                memPutFloat(e + SDL_MouseMotionEvent.X, (float) mx);
                memPutFloat(e + SDL_MouseMotionEvent.Y, (float) my);
                memPutFloat(e + SDL_MouseMotionEvent.XREL, (float) memGetDouble(evBuf + 16));
                memPutFloat(e + SDL_MouseMotionEvent.YREL, (float) memGetDouble(evBuf + 24));
            }
            case 0x401, 0x402 -> {
                memPutInt(e + SDL_MouseButtonEvent.WINDOWID, 1);
                memPutByte(e + SDL_MouseButtonEvent.BUTTON, (byte) memGetDouble(evBuf));
                memPutByte(e + SDL_MouseButtonEvent.DOWN, (byte) (t == 0x401 ? 1 : 0));
                memPutByte(e + SDL_MouseButtonEvent.CLICKS, (byte) 1);
                memPutFloat(e + SDL_MouseButtonEvent.X, (float) mx);
                memPutFloat(e + SDL_MouseButtonEvent.Y, (float) my);
            }
            case 0x403 -> {
                memPutInt(e + SDL_MouseWheelEvent.WINDOWID, 1);
                memPutFloat(e + SDL_MouseWheelEvent.X, (float) memGetDouble(evBuf));
                memPutFloat(e + SDL_MouseWheelEvent.Y, (float) memGetDouble(evBuf + 8));
                memPutFloat(e + SDL_MouseWheelEvent.MOUSE_X, (float) mx);
                memPutFloat(e + SDL_MouseWheelEvent.MOUSE_Y, (float) my);
            }
            default -> {
                if (t >= 0x202 && t < 0x300) {
                    memPutInt(e + SDL_WindowEvent.WINDOWID, 1);
                    memPutInt(e + SDL_WindowEvent.DATA1, (int) memGetDouble(evBuf));
                    memPutInt(e + SDL_WindowEvent.DATA2, (int) memGetDouble(evBuf + 8));
                }
            }
        }
        return true;
    }
}
