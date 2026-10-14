package bc.rt;

import static org.lwjgl.system.MemoryUtil.*;

import java.util.HashMap;

public final class Al {
    static int next = 1;
    static final HashMap<Integer, Src> src = new HashMap<>();
    static final HashMap<Integer, Double> len = new HashMap<>();
    static long empty, name;

    static final class Src {
        int state = 0x1011;
        boolean loop;
        final java.util.ArrayDeque<double[]> q = new java.util.ArrayDeque<>();
        double t0;
        int buffer;
    }

    static void reg(String n, Nat.F f) { Nat.reg(n, f); }

    static void ints(long p, long n) { for (int i = 0; i < n; i++) memPutInt(p + 4L * i, next++); }

    public static void install() {
        empty = Sdl.cstr("");
        name = Sdl.cstr("Browser Audio");
        reg("alcOpenDevice", a -> 0x7000);
        reg("alcCloseDevice", a -> 1);
        reg("alcCreateContext", a -> 0x7100);
        reg("alcDestroyContext", a -> 0);
        reg("alcMakeContextCurrent", a -> 1);
        reg("alcGetCurrentContext", a -> 0x7100);
        reg("alcGetContextsDevice", a -> 0x7000);
        reg("alcProcessContext", a -> 0);
        reg("alcSuspendContext", a -> 0);
        reg("alcGetError", a -> 0);
        reg("alcIsExtensionPresent", a -> 0);
        reg("alcGetProcAddress", a -> Nat.id(Sdl.s(a[1])));
        reg("alcGetEnumValue", a -> 0);
        reg("alcGetString", a -> {
            int p = (int) a[1];
            if (p == 0x1005 || p == 0x1012 || p == 0x1013 || p == 0x311 || p == 0x310) return name;
            return empty;
        });
        reg("alcGetIntegerv", a -> {
            int p = (int) a[1];
            long n = a[2], d = a[3];
            int[] v = switch (p) {
                case 0x1000 -> new int[]{1};
                case 0x1001 -> new int[]{1};
                case 0x1002 -> new int[]{7};
                case 0x1003 -> new int[]{0x1010, 255, 0x1011, 16, 0x1007, 48000, 0, 0, 0, 0};
                case 0x1007 -> new int[]{48000};
                case 0x1010 -> new int[]{255};
                case 0x1011 -> new int[]{16};
                case 0x313 -> new int[]{1};
                default -> new int[]{0};
            };
            for (int i = 0; i < Math.min(n, v.length); i++) memPutInt(d + 4L * i, v[i]);
            return 0;
        });
        reg("alcEventControlSOFT", a -> 0);
        reg("alcEventCallbackSOFT", a -> 0);
        reg("alcEventIsSupportedSOFT", a -> 0);
        reg("alcResetDeviceSOFT", a -> 1);
        reg("alcReopenDeviceSOFT", a -> 1);

        reg("alGetError", a -> 0);
        reg("alEnable", a -> 0);
        reg("alDisable", a -> 0);
        reg("alIsExtensionPresent", a -> 0);
        reg("alGetProcAddress", a -> Nat.id(Sdl.s(a[0])));
        reg("alGetEnumValue", a -> 0);
        reg("alGetString", a -> empty);
        reg("alDistanceModel", a -> 0);
        reg("alGenSources", a -> { for (int i = 0; i < a[0]; i++) { int id = next++; src.put(id, new Src()); memPutInt(a[1] + 4L * i, id); } return 0; });
        reg("alDeleteSources", a -> { for (int i = 0; i < a[0]; i++) src.remove(memGetInt(a[1] + 4L * i)); return 0; });
        reg("alGenBuffers", a -> { ints(a[1], a[0]); return 0; });
        reg("alDeleteBuffers", a -> 0);
        reg("alIsSource", a -> src.containsKey((int) a[0]) ? 1 : 0);
        reg("alBufferData", a -> {
            int fmt = (int) a[1];
            int ch = fmt == 0x1101 || fmt == 0x1103 ? 2 : 1;
            int bps = fmt == 0x1100 || fmt == 0x1102 ? 1 : 2;
            len.put((int) a[0], a[3] / (double) (ch * bps) / Math.max(1, a[4]));
            Audio.buffer((int) a[0], a[2], (int) a[3], ch, bps, (int) a[4]);
            return 0;
        });
        reg("alSourcei", a -> { var s = src.get((int) a[0]); if (s != null) { if (a[1] == 0x1007) s.loop = a[2] != 0; if (a[1] == 0x1009) { s.buffer = (int) a[2]; s.q.clear(); } } Audio.srcI((int) a[0], (int) a[1], (int) a[2]); return 0; });
        reg("alSourcef", a -> { Audio.srcF((int) a[0], (int) a[1], Sdl.fa(a[2])); return 0; });
        reg("alSource3f", a -> { Audio.src3((int) a[0], (int) a[1], Sdl.fa(a[2]), Sdl.fa(a[3]), Sdl.fa(a[4])); return 0; });
        reg("alSourcefv", a -> { Audio.src3((int) a[0], (int) a[1], memGetFloat(a[2]), memGetFloat(a[2] + 4), memGetFloat(a[2] + 8)); return 0; });
        reg("alListenerf", a -> { Audio.lisF((int) a[0], Sdl.fa(a[1])); return 0; });
        reg("alListener3f", a -> { Audio.lis((int) a[0], Sdl.fa(a[1]), Sdl.fa(a[2]), Sdl.fa(a[3]), 0, 0, 0); return 0; });
        reg("alListenerfv", a -> {
            long p = a[1];
            if (a[0] == 0x100F) Audio.lis((int) a[0], memGetFloat(p), memGetFloat(p + 4), memGetFloat(p + 8), memGetFloat(p + 12), memGetFloat(p + 16), memGetFloat(p + 20));
            else Audio.lis((int) a[0], memGetFloat(p), memGetFloat(p + 4), memGetFloat(p + 8), 0, 0, 0);
            return 0;
        });
        reg("alSourcePlay", a -> {
            var s = src.get((int) a[0]);
            if (s != null) { s.state = 0x1012; s.t0 = Js.now(); }
            Audio.play((int) a[0]);
            return 0;
        });
        reg("alSourcePause", a -> { var s = src.get((int) a[0]); if (s != null) s.state = 0x1013; Audio.pause((int) a[0]); return 0; });
        reg("alSourceStop", a -> { var s = src.get((int) a[0]); if (s != null) { s.state = 0x1014; s.q.clear(); } Audio.stop((int) a[0]); return 0; });
        reg("alSourceRewind", a -> 0);
        reg("alSourceQueueBuffers", a -> {
            var s = src.get((int) a[0]);
            for (int i = 0; i < a[1]; i++) {
                int b = memGetInt(a[2] + 4L * i);
                if (s != null) s.q.add(new double[]{b, len.getOrDefault(b, 0.0)});
                Audio.queue((int) a[0], b);
            }
            return 0;
        });
        reg("alSourceUnqueueBuffers", a -> {
            var s = src.get((int) a[0]);
            for (int i = 0; i < a[1]; i++) {
                var e = s == null ? null : s.q.poll();
                if (s != null && e != null) s.t0 += e[1] * 1000;
                memPutInt(a[2] + 4L * i, e == null ? 0 : (int) e[0]);
            }
            return 0;
        });
        reg("alGetSourcei", a -> {
            var s = src.get((int) a[0]);
            int v = 0;
            if (s != null) {
                if (a[1] == 0x1010) v = state(s);
                else if (a[1] == 0x1016) v = processed(s);
                else if (a[1] == 0x1015) v = s.q.size();
            }
            memPutInt(a[2], v);
            return 0;
        });
        reg("alGetSourcef", a -> { memPutFloat(a[2], 0f); return 0; });
    }

    static int processed(Src s) {
        if (s.state != 0x1012) return s.q.size();
        double el = (Js.now() - s.t0) / 1000.0;
        int n = 0;
        for (var e : s.q) { if (el < e[1]) break; el -= e[1]; n++; }
        return n;
    }

    static int state(Src s) {
        if (s.state != 0x1012) return s.state;
        if (s.loop) return 0x1012;
        double total = s.buffer != 0 ? len.getOrDefault(s.buffer, 0.0) : 0;
        for (var e : s.q) total += e[1];
        if ((Js.now() - s.t0) / 1000.0 > total + 0.05) s.state = 0x1014;
        return s.state;
    }
}
