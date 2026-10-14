package bc.rt;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Objects;

public final class Ty {
    private Ty() {}

    static String nm(Type t) { return t instanceof Class<?> c ? c.getName() : String.valueOf(t); }

    public static final class P implements ParameterizedType {
        final Class<?> r;
        final Type o;
        final Type[] a;

        P(Class<?> r, Type o, Type[] a) { this.r = r; this.o = o; this.a = a; }

        public Type[] getActualTypeArguments() { return a.clone(); }
        public Type getRawType() { return r; }
        public Type getOwnerType() { return o; }

        public boolean equals(Object x) {
            return x instanceof ParameterizedType p && r.equals(p.getRawType()) && Objects.equals(o, p.getOwnerType()) && Arrays.equals(a, p.getActualTypeArguments());
        }

        public int hashCode() { return Arrays.hashCode(a) ^ Objects.hashCode(o) ^ r.hashCode(); }

        public String toString() {
            var sb = new StringBuilder(r.getName());
            if (a.length > 0) {
                sb.append('<');
                for (int i = 0; i < a.length; i++) { if (i > 0) sb.append(", "); sb.append(nm(a[i])); }
                sb.append('>');
            }
            return sb.toString();
        }
    }

    public static final class W implements WildcardType {
        final Type[] u;
        final Type[] l;

        W(Type[] u, Type[] l) { this.u = u; this.l = l; }

        public Type[] getUpperBounds() { return u.clone(); }
        public Type[] getLowerBounds() { return l.clone(); }

        public boolean equals(Object x) {
            return x instanceof WildcardType w && Arrays.equals(u, w.getUpperBounds()) && Arrays.equals(l, w.getLowerBounds());
        }

        public int hashCode() { return Arrays.hashCode(u) ^ Arrays.hashCode(l); }

        public String toString() {
            if (l.length > 0) return "? super " + nm(l[0]);
            if (u.length == 0 || u[0] == Object.class) return "?";
            return "? extends " + nm(u[0]);
        }
    }

    public static final class G implements GenericArrayType {
        final Type c;

        G(Type c) { this.c = c; }

        public Type getGenericComponentType() { return c; }
        public boolean equals(Object x) { return x instanceof GenericArrayType g && c.equals(g.getGenericComponentType()); }
        public int hashCode() { return c.hashCode(); }
        public String toString() { return nm(c) + "[]"; }
    }

    public static final class V implements TypeVariable<GenericDeclaration> {
        final String n;

        V(String n) { this.n = n; }

        public Type[] getBounds() { return new Type[]{Object.class}; }
        public GenericDeclaration getGenericDeclaration() { return null; }
        public String getName() { return n; }
        public AnnotatedType[] getAnnotatedBounds() { return new AnnotatedType[0]; }
        public <T extends Annotation> T getAnnotation(Class<T> c) { return null; }
        public Annotation[] getAnnotations() { return new Annotation[0]; }
        public Annotation[] getDeclaredAnnotations() { return new Annotation[0]; }
        public boolean equals(Object x) { return x instanceof TypeVariable<?> v && n.equals(v.getName()); }
        public int hashCode() { return n.hashCode(); }
        public String toString() { return n; }
    }
}
