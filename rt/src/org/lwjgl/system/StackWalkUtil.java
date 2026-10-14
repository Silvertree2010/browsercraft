package org.lwjgl.system;

final class StackWalkUtil {
    static StackTraceElement[] stackWalkArray(Object[] a) { return new StackTraceElement[0]; }
    static Object stackWalkGetMethod(Class<?> c) { return null; }
    static Object stackWalkCheckPop(Class<?> c, Object o) { return null; }
    static Object[] stackWalkGetTrace() { return new Object[0]; }
}
