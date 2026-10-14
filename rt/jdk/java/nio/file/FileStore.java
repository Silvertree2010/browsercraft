package java.nio.file;

import java.io.IOException;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileStoreAttributeView;

public abstract class FileStore {
    protected FileStore() {}
    public abstract String name();
    public abstract String type();
    public abstract boolean isReadOnly();
    public abstract long getTotalSpace() throws IOException;
    public abstract long getUsableSpace() throws IOException;
    public abstract long getUnallocatedSpace() throws IOException;
    public long getBlockSize() throws IOException { return 4096; }
    public abstract boolean supportsFileAttributeView(Class<? extends FileAttributeView> t);
    public abstract boolean supportsFileAttributeView(String n);
    public abstract <V extends FileStoreAttributeView> V getFileStoreAttributeView(Class<V> t);
    public abstract Object getAttribute(String a) throws IOException;
}
