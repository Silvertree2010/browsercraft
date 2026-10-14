package com.google.gson.internal;

public abstract class UnsafeAllocator {
    public static final UnsafeAllocator INSTANCE = new UnsafeAllocator() {
        public <T> T newInstance(Class<T> c) { throw new UnsupportedOperationException("Unable to create instance of " + c); }
    };
    public abstract <T> T newInstance(Class<T> c) throws Exception;
}
