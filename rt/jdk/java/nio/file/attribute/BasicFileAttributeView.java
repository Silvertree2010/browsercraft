package java.nio.file.attribute;

public interface BasicFileAttributeView extends FileAttributeView {
    String name();
    BasicFileAttributes readAttributes() throws java.io.IOException;
    void setTimes(FileTime m, FileTime a, FileTime c) throws java.io.IOException;
}
