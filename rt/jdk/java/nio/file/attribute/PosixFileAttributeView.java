package java.nio.file.attribute;

public interface PosixFileAttributeView extends BasicFileAttributeView {
    PosixFileAttributes readAttributes() throws java.io.IOException;
    void setPermissions(java.util.Set<PosixFilePermission> p) throws java.io.IOException;
}
