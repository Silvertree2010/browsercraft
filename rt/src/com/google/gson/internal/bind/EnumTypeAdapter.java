package com.google.gson.internal.bind;

import bc.gen.Gs;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class EnumTypeAdapter<T extends Enum<T>> extends TypeAdapter<T> {
    static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> t) {
            Class<? super T> r = t.getRawType();
            if (!Enum.class.isAssignableFrom(r) || r == Enum.class) return null;
            if (!r.isEnum()) r = r.getSuperclass();
            return new EnumTypeAdapter(r);
        }
    };

    private final Map<String, T> byName = new HashMap<>(), byStr = new HashMap<>();
    private final Map<T, String> toName = new HashMap<>();

    private EnumTypeAdapter(Class<T> c) {
        int id = Gs.id(c);
        if (id >= 0) {
            String[] ns = Gs.names(id);
            for (int f = 0; f < ns.length; f++) {
                T k = (T) Gs.get(id, f, null);
                String[] al = ns[f].split("\0");
                for (String s : al) byName.put(s, k);
                toName.put(k, al[0]);
                byStr.put(k.toString(), k);
            }
            return;
        }
        for (T k : c.getEnumConstants()) {
            byName.put(k.name(), k);
            byStr.put(k.toString(), k);
            toName.put(k, k.name());
        }
    }

    public T read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) { in.nextNull(); return null; }
        String s = in.nextString();
        T k = byName.get(s);
        return k != null ? k : byStr.get(s);
    }

    public void write(JsonWriter out, T v) throws IOException {
        out.value(v == null ? null : toName.get(v));
    }
}
