package bc.rt;

import java.io.IOException;
import java.util.ArrayList;
import org.teavm.jso.JSBody;
import org.teavm.runtime.fs.VirtualFile;
import org.teavm.runtime.fs.VirtualFileAccessor;
import org.teavm.runtime.fs.VirtualFileSystem;

public final class Vfs implements VirtualFileSystem {
    public static void install() { org.teavm.runtime.fs.VirtualFileSystemProvider.setInstance(new Vfs()); }

    static final java.nio.ByteBuffer BUF = java.nio.ByteBuffer.allocateDirect(1 << 20);
    static final int ADDR = (int) org.lwjgl.system.MemoryUtil.memAddress(BUF);

    @JSBody(params = {"o", "p", "a", "b", "m", "n"}, script = "return bc.io(o, p, a, b, m, n);")
    static native double io(int o, String p, double a, double b, int m, int n);

    @JSBody(params = {"p"}, script = "return bc.ioList(p);")
    static native String list(String p);

    static final int STAT = 1, READ = 2, WRITE = 3, TRUNC = 4, MKDIR = 5, DEL = 6, MOVE = 7, OPEN = 8, CLOSE = 9, TOUCH = 10, MTIME = 11;

    public String getUserDir() { return "/mc"; }
    public VirtualFile getFile(String p) { return new F(norm(p)); }
    public boolean isWindows() { return false; }
    public String canonicalize(String p) { return norm(p); }
    public String[] getRoots() { return new String[]{"/"}; }

    static String norm(String p) {
        if (!p.startsWith("/")) p = "/mc/" + p;
        var parts = new ArrayList<String>();
        for (var s : p.split("/")) {
            if (s.isEmpty() || s.equals(".")) continue;
            if (s.equals("..")) { if (!parts.isEmpty()) parts.remove(parts.size() - 1); continue; }
            parts.add(s);
        }
        return "/" + String.join("/", parts);
    }

    static final class F implements VirtualFile {
        final String p;
        F(String p) { this.p = p; }

        int stat() { return (int) io(STAT, p, 0, 0, 0, 0); }

        public String getName() { int i = p.lastIndexOf('/'); return p.substring(i + 1); }
        public boolean isDirectory() { return stat() == 2; }
        public boolean isFile() { return stat() == 1; }
        public String[] listFiles() {
            var s = list(p);
            if (s == null) return null;
            if (s.isEmpty()) return new String[0];
            return s.split("/");
        }
        public VirtualFileAccessor createAccessor(boolean r, boolean w, boolean a) {
            if (!w && stat() != 1) return null;
            if (io(OPEN, p, w ? 1 : 0, 0, 0, 0) < 0) return null;
            var acc = new A(p);
            if (a) acc.pos = acc.size0();
            return acc;
        }
        public boolean createFile(String n) throws IOException { return io(TOUCH, p + "/" + n, 0, 0, 0, 0) > 0; }
        public boolean createDirectory(String n) { return io(MKDIR, p + "/" + n, 0, 0, 0, 0) > 0; }
        public boolean delete() { return io(DEL, p, 0, 0, 0, 0) > 0; }
        public boolean adopt(VirtualFile f, String n) {
            if (!(f instanceof F src)) return false;
            return mv(src.p, p + "/" + n);
        }
        boolean mv(String a, String b) {
            var bytes = b.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            BUF.clear();
            BUF.put(bytes);
            return io(MOVE, a, bytes.length, 0, ADDR, bytes.length) > 0;
        }
        public boolean canRead() { return stat() != 0; }
        public boolean canWrite() { return true; }
        public long lastModified() { return (long) io(MTIME, p, 0, 0, 0, 0); }
        public boolean setLastModified(long t) { return true; }
        public boolean setReadOnly(boolean r) { return true; }
        public int length() { return (int) Math.max(0, io(STAT, p, 1, 0, 0, 0)); }
    }

    static final class A implements VirtualFileAccessor {
        final String p;
        long pos;
        boolean open = true;
        A(String p) { this.p = p; }

        long size0() { return (long) io(STAT, p, 1, 0, 0, 0); }

        public int read(byte[] b, int o, int l) throws IOException {
            int total = 0;
            while (l > 0) {
                int c = Math.min(l, BUF.capacity());
                int n = (int) io(READ, p, pos, 0, ADDR, c);
                if (n < 0) throw new IOException("read failed " + p);
                if (n == 0) break;
                BUF.clear();
                BUF.get(b, o, n);
                pos += n; o += n; l -= n; total += n;
                if (n < c) break;
            }
            return total == 0 && l > 0 ? -1 : total;
        }
        public void write(byte[] b, int o, int l) throws IOException {
            while (l > 0) {
                int c = Math.min(l, BUF.capacity());
                BUF.clear();
                BUF.put(b, o, c);
                int n = (int) io(WRITE, p, pos, 0, ADDR, c);
                if (n < 0) throw new IOException("write failed " + p);
                pos += c; o += c; l -= c;
            }
        }
        public int tell() { return (int) pos; }
        public void seek(int t) { pos = t; }
        public void skip(int n) { pos += n; }
        public int size() { return (int) size0(); }
        public void resize(int s) throws IOException { if (io(TRUNC, p, s, 0, 0, 0) < 0) throw new IOException("truncate failed " + p); }
        public void close() { if (open) { open = false; io(CLOSE, p, 0, 0, 0, 0); } }
        public void flush() {}
    }
}
