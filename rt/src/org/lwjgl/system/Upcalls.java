package org.lwjgl.system;

final class Upcalls {
    static long upcallCreate(Callback.Descriptor d, Object i) { return 0; }
    static void shutdown() {}
    static Object getCallbackHandler() { return null; }
}
