package jdk.internal.ref;

import java.lang.ref.Cleaner;

public final class CleanerFactory {
    static final Cleaner C = Cleaner.create();
    public static Cleaner cleaner() { return C; }
}
