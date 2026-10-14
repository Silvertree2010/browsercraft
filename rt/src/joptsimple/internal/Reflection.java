package joptsimple.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.function.Function;
import joptsimple.ValueConversionException;
import joptsimple.ValueConverter;

public final class Reflection {
    private Reflection() {}

    @SuppressWarnings("unchecked")
    public static <V> ValueConverter<V> findConverter(Class<V> c) {
        Function<String, ?> f;
        if (c == String.class) f = s -> s;
        else if (c == Integer.class || c == int.class) f = Integer::valueOf;
        else if (c == Long.class || c == long.class) f = Long::valueOf;
        else if (c == Double.class || c == double.class) f = Double::valueOf;
        else if (c == Float.class || c == float.class) f = Float::valueOf;
        else if (c == Short.class || c == short.class) f = Short::valueOf;
        else if (c == Byte.class || c == byte.class) f = Byte::valueOf;
        else if (c == Boolean.class || c == boolean.class) f = Boolean::valueOf;
        else if (c == java.io.File.class) f = java.io.File::new;
        else if (c == java.nio.file.Path.class) f = java.nio.file.Path::of;
        else throw new IllegalArgumentException(c + " is not a value type");
        var g = (Function<String, V>) f;
        return new ValueConverter<V>() {
            public V convert(String s) {
                try {
                    return g.apply(s);
                } catch (RuntimeException e) {
                    throw new ValueConversionException("Cannot convert '" + s + "' to " + c.getName(), e);
                }
            }

            public Class<? extends V> valueType() { return c; }

            public String valuePattern() { return null; }
        };
    }

    public static <T> T instantiate(Constructor<T> c, Object... a) {
        try {
            return c.newInstance(a);
        } catch (Exception e) {
            throw new ReflectionException(e);
        }
    }

    public static Object invoke(Method m, Object... a) {
        try {
            return m.invoke(null, a);
        } catch (Exception e) {
            throw new ReflectionException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <V> V convertWith(ValueConverter<V> c, String s) {
        return c == null ? (V) s : c.convert(s);
    }
}
