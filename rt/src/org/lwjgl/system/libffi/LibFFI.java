package org.lwjgl.system.libffi;

import org.lwjgl.PointerBuffer;

public class LibFFI {
    public static final int FFI_DEFAULT_ABI = 2, FFI_STDCALL = 5, FFI_OK = 0;
    public static final FFIType ffi_type_void = null, ffi_type_uint8 = null, ffi_type_sint8 = null, ffi_type_uint16 = null, ffi_type_sint16 = null,
            ffi_type_uint32 = null, ffi_type_sint32 = null, ffi_type_uint64 = null, ffi_type_sint64 = null, ffi_type_uchar = null, ffi_type_schar = null,
            ffi_type_ushort = null, ffi_type_sshort = null, ffi_type_uint = null, ffi_type_sint = null, ffi_type_ulong = null, ffi_type_slong = null,
            ffi_type_float = null, ffi_type_double = null, ffi_type_longdouble = null, ffi_type_pointer = null;

    public static FFIClosure ffi_closure_alloc(long s, PointerBuffer c) { return null; }
    public static void ffi_closure_free(FFIClosure c) {}
    public static long ffi_get_closure_size() { return 64; }
    public static int ffi_prep_cif_var(FFICIF c, int a, int n, FFIType r, PointerBuffer t) { return FFI_OK; }
    public static int ffi_prep_cif(FFICIF c, int a, FFIType r, PointerBuffer t) { return FFI_OK; }
    public static int ffi_prep_closure_loc(FFIClosure c, FFICIF f, long fn, long u, long code) { return FFI_OK; }
    public static void nffi_call(long a, long b, long c, long d) { throw new UnsupportedOperationException("ffi_call"); }
}
