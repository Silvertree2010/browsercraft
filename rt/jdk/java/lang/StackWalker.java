package java.lang;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public final class StackWalker {
    public enum Option { RETAIN_CLASS_REFERENCE, SHOW_REFLECT_FRAMES, SHOW_HIDDEN_FRAMES, DROP_METHOD_INFO }
    public interface StackFrame {
        String getClassName();
        String getMethodName();
        Class<?> getDeclaringClass();
        int getLineNumber();
        default String getFileName() { return null; }
        StackTraceElement toStackTraceElement();
    }
    static final StackWalker W = new StackWalker();
    StackWalker() {}
    public static StackWalker getInstance() { return W; }
    public static StackWalker getInstance(Option o) { return W; }
    public static StackWalker getInstance(Set<Option> o) { return W; }
    public static StackWalker getInstance(Set<Option> o, int d) { return W; }
    public <T> T walk(Function<? super Stream<StackFrame>, ? extends T> f) { return f.apply(Stream.empty()); }
    public void forEach(Consumer<? super StackFrame> a) {}
    public Class<?> getCallerClass() { return Object.class; }
}
