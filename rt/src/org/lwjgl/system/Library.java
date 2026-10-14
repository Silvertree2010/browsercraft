package org.lwjgl.system;

import java.nio.ByteBuffer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import bc.rt.Nat;

public final class Library {
    public static final String JNI_LIBRARY_NAME = "lwjgl";
    static final String JAVA_LIBRARY_PATH = "java.library.path";

    private Library() {}

    public static void initialize() {}
    public static void loadSystem(String m, String n) {}
    public static void loadSystem(Consumer<String> a, Consumer<String> b, Class<?> c, String m, String n) {}
    public static SharedLibrary loadNative(String m, String n) { return new Lib(n); }
    public static SharedLibrary loadNative(Class<?> c, String m, String n) { return new Lib(n); }
    public static SharedLibrary loadNative(Class<?> c, String m, String n, boolean b) { return new Lib(n); }
    public static SharedLibrary loadNative(Class<?> c, String m, Configuration<String> n, String... d) { return new Lib(d.length > 0 ? d[0] : m); }
    public static SharedLibrary loadNative(Class<?> c, String m, Configuration<String> n, Supplier<SharedLibrary> f, String... d) { return new Lib(d.length > 0 ? d[0] : m); }
    static void printError(String s) { System.err.println(s); }

    static final class Lib extends SharedLibrary.Default implements FunctionProviderLocal {
        Lib(String n) { super(n, 1); }
        public String getPath() { return getName(); }
        public long getFunctionAddress(ByteBuffer b) { return Nat.id(MemoryUtil.memASCII(b)); }
        public long getFunctionAddress(CharSequence n) { return Nat.id(n.toString()); }
        public long getFunctionAddress(long h, ByteBuffer b) { return getFunctionAddress(b); }
        public void free() {}
    }
}
