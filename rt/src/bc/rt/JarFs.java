package bc.rt;

import java.io.IOException;
import java.net.URI;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.nio.file.spi.FileSystemProvider;
import java.util.Map;
import java.util.Set;

public final class JarFs extends FileSystemProvider {
    public static final JarFs I = new JarFs();

    static UnsupportedOperationException no() { return new UnsupportedOperationException("jar fs"); }

    public String getScheme() { return "jar"; }
    public FileSystem newFileSystem(URI u, Map<String, ?> e) { throw no(); }
    public FileSystem newFileSystem(Path p, Map<String, ?> e) { throw no(); }
    public FileSystem getFileSystem(URI u) { throw no(); }
    public Path getPath(URI u) { throw no(); }
    public SeekableByteChannel newByteChannel(Path p, Set<? extends OpenOption> o, FileAttribute<?>... a) { throw no(); }
    public DirectoryStream<Path> newDirectoryStream(Path d, DirectoryStream.Filter<? super Path> f) { throw no(); }
    public void createDirectory(Path d, FileAttribute<?>... a) { throw no(); }
    public void delete(Path p) { throw no(); }
    public void copy(Path s, Path t, CopyOption... o) { throw no(); }
    public void move(Path s, Path t, CopyOption... o) { throw no(); }
    public boolean isSameFile(Path a, Path b) { return a.equals(b); }
    public boolean isHidden(Path p) { return false; }
    public FileStore getFileStore(Path p) { throw no(); }
    public void checkAccess(Path p, AccessMode... m) throws IOException { throw new NoSuchFileException(String.valueOf(p)); }
    public <V extends FileAttributeView> V getFileAttributeView(Path p, Class<V> t, LinkOption... o) { return null; }
    public <A extends BasicFileAttributes> A readAttributes(Path p, Class<A> t, LinkOption... o) { throw no(); }
    public Map<String, Object> readAttributes(Path p, String a, LinkOption... o) { throw no(); }
    public void setAttribute(Path p, String a, Object v, LinkOption... o) { throw no(); }
}
