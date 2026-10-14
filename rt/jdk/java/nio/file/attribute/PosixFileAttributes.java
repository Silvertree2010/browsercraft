package java.nio.file.attribute;

public interface PosixFileAttributes extends BasicFileAttributes {
    UserPrincipal owner();
    GroupPrincipal group();
    java.util.Set<PosixFilePermission> permissions();
}
