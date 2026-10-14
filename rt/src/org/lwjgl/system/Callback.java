package org.lwjgl.system;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import org.lwjgl.system.libffi.FFICIF;

public abstract class Callback implements Pointer, NativeResource {
    static final HashMap<Long, Callback> M = new HashMap<>();
    static long next = 0x70000000L;
    private long address;

    protected Callback(Descriptor d) {
        address = next;
        next += 16;
        M.put(address, this);
    }

    protected Callback(long a) { address = a; }

    public long address() { return address; }
    public void free() { free(address); }

    @SuppressWarnings("unchecked")
    public static <T extends CallbackI> T get(long fp) { var c = M.get(fp); return (T) c; }
    public static <T extends CallbackI> T getSafe(long fp) { return fp == 0 ? null : get(fp); }
    public static void free(long fp) { M.remove(fp); }

    public boolean equals(Object o) { return o instanceof Callback c && c.address == address; }
    public int hashCode() { return (int) (address ^ (address >>> 32)); }
    public String toString() { return getClass().getSimpleName() + " pointer [0x" + Long.toHexString(address) + "]"; }

    public static final class Descriptor {
        final Class<? extends CallbackI> type;
        final MethodHandles.Lookup lookup;
        final FFICIF cif;
        public Descriptor(Class<? extends CallbackI> type, MethodHandles.Lookup lookup, FFICIF cif) { this.type = type; this.lookup = lookup; this.cif = cif; }
    }
}
