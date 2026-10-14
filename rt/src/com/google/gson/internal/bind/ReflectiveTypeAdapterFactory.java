package com.google.gson.internal.bind;

import bc.gen.Gs;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.ReflectionAccessFilter;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ReflectiveTypeAdapterFactory implements TypeAdapterFactory {
    public ReflectiveTypeAdapterFactory(ConstructorConstructor cc, FieldNamingStrategy fn, Excluder ex, JsonAdapterAnnotationTypeAdapterFactory ja, List<ReflectionAccessFilter> rf) {}

    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<? super T> raw = type.getRawType();
        if (!Object.class.isAssignableFrom(raw) || raw.isPrimitive()) return null;
        int id = Gs.id(raw);
        if (id < 0 || Gs.kind(id) == 3) return new Adapter<T>() {
            public T read(JsonReader in) { throw new JsonIOException("no gson info for " + raw.getName()); }
            public void write(JsonWriter out, T v) { throw new JsonIOException("no gson info for " + raw.getName()); }
        };
        var cls = new ArrayList<Integer>();
        for (Class<?> c = raw; c != null && c != Object.class; c = c.getSuperclass()) {
            int k = Gs.id(c);
            if (k >= 0) cls.add(k);
        }
        var fc = new ArrayList<Integer>();
        var fi = new ArrayList<Integer>();
        var nm = new ArrayList<String>();
        var ad = new ArrayList<TypeAdapter<?>>();
        Map<String, Integer> by = new HashMap<>();
        for (int k : cls) {
            String[] ns = Gs.names(k);
            Type[] ts = Gs.types(k);
            for (int f = 0; f < ns.length; f++) {
                String[] al = ns[f].split("\0");
                if (by.containsKey(al[0])) continue;
                int x = nm.size();
                fc.add(k);
                fi.add(f);
                nm.add(al[0]);
                Type t = ts[f] instanceof TypeVariable<?> ? Object.class : ts[f];
                TypeAdapter<?> a;
                try { a = gson.getAdapter(TypeToken.get(t)); } catch (RuntimeException e) { a = gson.getAdapter(Object.class); }
                ad.add(new TypeAdapterRuntimeTypeWrapper<>(gson, (TypeAdapter<Object>) a, t));
                for (String s : al) by.putIfAbsent(s, x);
            }
        }
        boolean rec = Gs.kind(id) == 1;
        int own = cls.get(0), n = nm.size();
        return new Adapter<T>() {
            public T read(JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) { in.nextNull(); return null; }
                Object[] v = new Object[n];
                boolean[] p = new boolean[n];
                in.beginObject();
                while (in.hasNext()) {
                    Integer x = by.get(in.nextName());
                    if (x == null) { in.skipValue(); continue; }
                    v[x] = ad.get(x).read(in);
                    p[x] = true;
                }
                in.endObject();
                if (rec) {
                    Object[] a = new Object[Gs.names(own).length];
                    for (int i = 0; i < n; i++) if (fc.get(i) == own) a[fi.get(i)] = v[i];
                    return (T) Gs.make(own, a);
                }
                Object o = Gs.make(own, null);
                for (int i = 0; i < n; i++) if (p[i]) Gs.set(fc.get(i), fi.get(i), o, v[i]);
                return (T) o;
            }

            public void write(JsonWriter out, T o) throws IOException {
                if (o == null) { out.nullValue(); return; }
                out.beginObject();
                for (int i = 0; i < n; i++) {
                    Object v = Gs.get(fc.get(i), fi.get(i), o);
                    if (v == null && !out.getSerializeNulls()) continue;
                    out.name(nm.get(i));
                    ((TypeAdapter<Object>) ad.get(i)).write(out, v);
                }
                out.endObject();
            }
        };
    }

    public abstract static class Adapter<T> extends TypeAdapter<T> {}
}
