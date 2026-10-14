package org.lwjgl.system.jni;

import java.nio.ByteBuffer;
import java.util.HashMap;
import org.lwjgl.system.MemoryUtil;

public class JNINativeInterface {
    public static final int JNI_VERSION_1_1 = 0x10001, JNI_VERSION_1_2 = 0x10002, JNI_VERSION_1_4 = 0x10004, JNI_VERSION_1_6 = 0x10006, JNI_VERSION_1_8 = 0x10008, JNI_VERSION_9 = 0x90000, JNI_VERSION_10 = 0xa0000, JNI_VERSION_19 = 0x130000, JNI_VERSION_20 = 0x140000, JNI_VERSION_21 = 0x150000, JNI_VERSION_24 = 0x180000;
    static final HashMap<Long, Object> R = new HashMap<>();
    static long next = 0x60000000L;
    public static int GetVersion() { return 0x00150000; }
    public static long NewGlobalRef(Object o) { long a = next; next += 8; R.put(a, o); return a; }
    public static void DeleteGlobalRef(long a) { R.remove(a); }
    public static void nDeleteGlobalRef(long a) { R.remove(a); }
    public static Object get(long a) { return R.get(a); }
    public static ByteBuffer NewDirectByteBuffer(long a, long c) { return MemoryUtil.memByteBuffer(a, (int) c); }
}
