package java.nio.file.attribute;

public interface DosFileAttributeView extends BasicFileAttributeView {
    DosFileAttributes readAttributes() throws java.io.IOException;
    void setReadOnly(boolean v) throws java.io.IOException;
    void setHidden(boolean v) throws java.io.IOException;
    void setSystem(boolean v) throws java.io.IOException;
    void setArchive(boolean v) throws java.io.IOException;
}
