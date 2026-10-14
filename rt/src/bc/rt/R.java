package bc.rt;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.UUID;

public final class R {
    static int sd = 0x9e3779b9;
    static int pr = 1;

    public static MethodHandles.Lookup lookup() { return null; }
    public static MethodHandles.Lookup lookup(Class<?> c, MethodHandles.Lookup l) { return null; }
    public static VarHandle vh(MethodHandles.Lookup l, Class<?> c, String n, Class<?> t) { return null; }
    public static VarHandle vh(Class<?> c) { return null; }
    public static VarHandle vh(Class<?> c, java.nio.ByteOrder o) { return null; }
    public static VarHandle vh(MethodHandles.Lookup l, String n, Class<?> t) { return null; }
    public static void nop() {}
    public static void nop(Object o) {}
    public static void nopJ(long v) {}
    public static void nop(Thread t, Object o) {}
    public static void spin() { Thread.yield(); }
    public static String id(String s) { return s; }
    public static void exit(int c) { throw new Exit(c); }
    public static void exit(Runtime r, int c) { throw new Exit(c); }
    public static void hook(Runtime r, Thread t) {}
        public static long tid(Thread t) { return t.getId(); }
    public static ClassLoader ccl(Thread t) { return R.class.getClassLoader(); }
    public static int seed() { sd ^= sd << 13; sd ^= sd >>> 17; sd ^= sd << 5; return sd; }
    public static int probe() { return pr; }
    public static int probe(int p) { pr = p ^ (p << 13); pr ^= pr >>> 17; pr ^= pr << 5; if (pr == 0) pr = 1; return pr; }
    public static String cp(int c) { return new String(Character.toChars(c)); }

    public static java.util.List<java.nio.file.spi.FileSystemProvider> fsp() { return java.util.List.of(java.nio.file.FileSystems.getDefault().provider(), JarFs.I); }

    public static java.util.concurrent.CompletableFuture<?> dfo(java.util.Set<?> t) { return java.util.concurrent.CompletableFuture.completedFuture(null); }

    public static java.nio.file.Path upath(java.net.URI u) {
        if (u.getScheme() == null || u.getScheme().equalsIgnoreCase("file")) return java.nio.file.Paths.get(u.getPath());
        throw new java.nio.file.FileSystemNotFoundException(u.getScheme());
    }

    public static net.minecraft.CrashReport crf(Throwable t, String d) {
        var sb = new StringBuilder("CRASHREPORT ").append(d).append(" | ");
        for (Throwable c = t; c != null; c = c.getCause()) sb.append(c.getClass().getName()).append(": ").append(c.getMessage()).append(" | ");
        Js.log(sb.toString());
        t.printStackTrace();
        return net.minecraft.CrashReport.forThrowable(t, d);
    }

    public static int tzOff() { return 0; }

    public static java.util.Date date(java.time.Instant i) { return new java.util.Date(i.toEpochMilli()); }
    public static java.nio.file.Path toPath(java.io.File f) { return java.nio.file.Paths.get(f.getPath()); }
    public static long pul(String s, int r) {
        if (s.isEmpty()) throw new NumberFormatException("empty");
        if (s.charAt(0) == '-') throw new NumberFormatException("Illegal leading minus sign on unsigned string " + s);
        if (s.length() <= 12) return Long.parseLong(s, r);
        long hi = pul(s.substring(0, s.length() - 1), r);
        int d = Character.digit(s.charAt(s.length() - 1), r);
        if (d < 0) throw new NumberFormatException(s);
        return hi * r + d;
    }
    public static long pul(String s) { return pul(s, 10); }
    public static int pui(String s, int r) { long v = Long.parseLong(s, r); if (v < 0 || v > 0xffffffffL) throw new NumberFormatException(s); return (int) v; }
    public static int pui(String s) { return pui(s, 10); }
    public static void nop(Class<?> c, String[] a) {}
    public static io.netty.util.internal.TypeParameterMatcher tpm(Object o, Class<?> sup, String n) {
        Class<?> c = o.getClass();
        while (c != null && c.getSuperclass() != sup) c = c.getSuperclass();
        Class<?> r = Object.class;
        if (c != null && bc.gen.Sig.sup(c) instanceof java.lang.reflect.ParameterizedType pt) {
            var as = pt.getActualTypeArguments();
            int i = n.startsWith("OUTBOUND") && as.length > 1 ? 1 : 0;
            var t = as[i];
            if (t instanceof java.lang.reflect.ParameterizedType q) t = q.getRawType();
            if (t instanceof Class<?> k) r = k;
        }
        return io.netty.util.internal.TypeParameterMatcher.get(r);
    }
    public static java.lang.reflect.Type gsc(Class<?> c) { var t = bc.gen.Sig.sup(c); return t != null ? t : c.getSuperclass(); }
    public static java.lang.reflect.Type pt(Class<?> r, java.lang.reflect.Type o, java.lang.reflect.Type[] a) { return new Ty.P(r, o, a); }
    public static java.lang.reflect.Type wc(java.lang.reflect.Type[] u, java.lang.reflect.Type[] l) { return new Ty.W(u, l); }
    public static java.lang.reflect.Type ga(java.lang.reflect.Type c) { return new Ty.G(c); }
    public static java.lang.reflect.Type tv(String n) { return new Ty.V(n); }
    public static java.lang.reflect.Type[] gif(Class<?> c) { return c.getInterfaces(); }
    public static boolean no(Object o) { return false; }
    public static Object[] sig(Class<?> c) { return null; }
    public static float fma(float a, float b, float c) { return a * b + c; }
    public static double fma(double a, double b, double c) { return a * b + c; }
    public static int powExact(int b, int e) { long r = 1; for (int i = 0; i < e; i++) { r *= b; if (r != (int) r) throw new ArithmeticException("integer overflow"); } return (int) r; }
    public static long powExact(long b, int e) { long r = 1; for (int i = 0; i < e; i++) r = Math.multiplyExact(r, b); return r; }
    public static int cpOf(String n) { throw new IllegalArgumentException("Unrecognized character name :" + n); }
    public static java.util.function.Predicate<String> asPred(java.util.regex.Pattern p) { return s -> p.matcher(s).find(); }
    public static java.util.function.Predicate<String> asMPred(java.util.regex.Pattern p) { return s -> p.matcher(s).matches(); }
    public static java.util.stream.IntStream is(java.util.Spliterator.OfInt s, boolean p) { var b = java.util.stream.IntStream.builder(); s.forEachRemaining((java.util.function.IntConsumer) b::add); return b.build(); }
    public static java.util.stream.LongStream ls(java.util.Spliterator.OfLong s, boolean p) { var b = java.util.stream.LongStream.builder(); s.forEachRemaining((java.util.function.LongConsumer) b::add); return b.build(); }
    public static java.util.stream.DoubleStream ds(java.util.Spliterator.OfDouble s, boolean p) { var b = java.util.stream.DoubleStream.builder(); s.forEachRemaining((java.util.function.DoubleConsumer) b::add); return b.build(); }
    public static long maxMem(Runtime r) { return 2048L << 20; }
    public static java.nio.file.FileStore store(java.nio.file.Path p) throws java.io.IOException { throw new java.io.IOException("no file store"); }

    public static long xfer(java.io.Reader r, java.io.Writer w) throws java.io.IOException { char[] b = new char[8192]; long t = 0; int n; while ((n = r.read(b)) > 0) { w.write(b, 0, n); t += n; } return t; }
    public static String readAll(java.io.Reader r) throws java.io.IOException { var w = new java.io.StringWriter(); xfer(r, w); return w.toString(); }
    public static java.nio.channels.FileChannel chan(Object s) { throw new UnsupportedOperationException("stream channel"); }
    public static java.net.URL res(Class<?> c, String n) { return Res.url(c, n); }
    public static Class<?> load(ClassLoader l, String n) throws ClassNotFoundException { return Class.forName(n); }
    public static java.lang.invoke.MethodHandle mh(Object l, Object m) throws IllegalAccessException { throw new IllegalAccessException("no method handles"); }
    public static java.lang.invoke.MethodType mt(Class<?> r) { return null; }
    public static java.lang.invoke.MethodType mt(Class<?> r, Class<?> a) { return null; }
    public static java.lang.invoke.MethodType mt(Class<?> r, Class<?> a, Class<?>[] b) { return null; }
    public static java.lang.invoke.MethodHandle bind(java.lang.invoke.MethodHandle h, Object o) { return h; }
    public static Object arr(Class<?> c, int[] d) {
        if (d.length == 1) return java.lang.reflect.Array.newInstance(c, d[0]);
        if (c == java.util.function.Predicate.class && d.length == 3) return new java.util.function.Predicate[d[0]][d[1]][d[2]];
        if (c == int.class && d.length == 2) return new int[d[0]][d[1]];
        if (c == float.class && d.length == 2) return new float[d[0]][d[1]];
        if (c == double.class && d.length == 2) return new double[d[0]][d[1]];
        if (c == long.class && d.length == 2) return new long[d[0]][d[1]];
        if (c == byte.class && d.length == 2) return new byte[d[0]][d[1]];
        if (c == boolean.class && d.length == 2) return new boolean[d[0]][d[1]];
        if (c == Object.class && d.length == 2) return new Object[d[0]][d[1]];
        throw new UnsupportedOperationException("multi array " + c + " " + d.length);
    }
    public static java.util.stream.Stream<String> lines(String s) {
        java.util.ArrayList<String> o = new java.util.ArrayList<>();
        int n = s.length(), b = 0, i = 0;
        while (i < n) {
            char c = s.charAt(i);
            if (c == '\n' || c == '\r') {
                o.add(s.substring(b, i));
                i += c == '\r' && i + 1 < n && s.charAt(i + 1) == '\n' ? 2 : 1;
                b = i;
            } else {
                i++;
            }
        }
        if (b < n) o.add(s.substring(b));
        return o.stream();
    }
    public static java.net.URLConnection open(java.net.URL u, java.net.Proxy p) throws java.io.IOException { return open(u); }
    public static java.net.URLConnection open(java.net.URL u) throws java.io.IOException {
        var h = u.getHost();
        if (u.getProtocol().startsWith("http") && h != null && !h.isEmpty() && !h.equals(Js.host())) {
            u = new java.net.URL(Js.origin() + "/proxy/" + h + u.getFile());
        }
        return u.openConnection();
    }
    public static long clen(java.net.URLConnection c) { return c.getContentLength(); }
    public static java.nio.ByteBuffer slice(java.nio.ByteBuffer b, int i, int l) { var d = b.duplicate(); d.position(i); d.limit(i + l); return d.slice().order(b.order()); }
    public static java.nio.file.Path link(java.nio.file.Path a, java.nio.file.Path b) throws java.io.IOException { java.nio.file.Files.copy(b, a); return a; }
    public static Object attr(java.nio.file.Path p, String a, java.nio.file.LinkOption[] o) throws java.io.IOException {
        var at = java.nio.file.Files.readAttributes(p, java.nio.file.attribute.BasicFileAttributes.class);
        return switch (a.contains(":") ? a.substring(a.indexOf(':') + 1) : a) {
            case "size" -> at.size();
            case "lastModifiedTime" -> at.lastModifiedTime();
            case "isDirectory" -> at.isDirectory();
            case "isRegularFile" -> at.isRegularFile();
            default -> null;
        };
    }
    public static java.nio.file.attribute.UserPrincipal owner(java.nio.file.Path p, java.nio.file.LinkOption[] o) { return () -> "player"; }
    public static java.util.Set<java.nio.file.attribute.PosixFilePermission> perms(java.nio.file.Path p, java.nio.file.LinkOption[] o) { return java.util.EnumSet.allOf(java.nio.file.attribute.PosixFilePermission.class); }
    public static java.nio.file.Path perms(java.nio.file.Path p, java.util.Set<?> s) { return p; }
    public static java.nio.file.Path mtime(java.nio.file.Path p, java.nio.file.attribute.FileTime t) { new java.io.File(p.toString()).setLastModified(t.toMillis()); return p; }
    public static java.nio.channels.SeekableByteChannel nbc(java.nio.file.Path p, java.util.Set<? extends java.nio.file.OpenOption> o, java.nio.file.attribute.FileAttribute<?>[] a) throws java.io.IOException { return java.nio.channels.FileChannel.open(p, o.isEmpty() ? java.util.Set.of(java.nio.file.StandardOpenOption.READ) : o); }
    public static java.nio.channels.SeekableByteChannel nbc(java.nio.file.Path p, java.nio.file.OpenOption[] o) throws java.io.IOException { return java.nio.channels.FileChannel.open(p, o.length == 0 ? new java.nio.file.OpenOption[]{java.nio.file.StandardOpenOption.READ} : o); }
    public static java.nio.file.Path resolve(java.nio.file.Path p, String a, String[] r) { var x = p.resolve(a); for (var s : r) x = x.resolve(s); return x; }
    public static java.nio.file.WatchService watch(java.nio.file.FileSystem f) throws java.io.IOException { throw new java.io.IOException("no watch service"); }
    public static boolean pos(java.time.Duration d) { return !d.isNegative() && !d.isZero(); }
    public static long conv(java.util.concurrent.TimeUnit u, java.time.Duration d) { return u.convert(d.toNanos(), java.util.concurrent.TimeUnit.NANOSECONDS); }
    public static <K, V> java.util.SortedMap<K, V> usm(java.util.SortedMap<K, V> m) { return m; }
    public static String iso3(java.util.Locale l) { return ""; }
    public static <T> java.util.Set<T> keySet() { return java.util.Collections.newSetFromMap(new java.util.concurrent.ConcurrentHashMap<>()); }

    public static java.lang.invoke.MethodHandle find(Object l, Class<?> c, String n, Object t) throws NoSuchMethodException { if (n.startsWith("acquireFence")) return null; throw new NoSuchMethodException(n); }
    public static java.nio.file.attribute.FileAttributeView view(java.nio.file.Path p, Class<?> c, java.nio.file.LinkOption[] o) { return null; }
    public static java.nio.file.WatchKey reg(java.nio.file.Path p, java.nio.file.WatchService w, java.nio.file.WatchEvent.Kind<?>[] k) throws java.io.IOException { throw new java.io.IOException("no watch service"); }
    public static java.nio.file.PathMatcher matcher(java.nio.file.FileSystem f, String s) {
        int i = s.indexOf(':');
        if (i < 0) throw new IllegalArgumentException(s);
        var kind = s.substring(0, i);
        var pat = s.substring(i + 1);
        String rx;
        if (kind.equals("regex")) rx = pat;
        else if (kind.equals("glob")) rx = glob(pat);
        else throw new UnsupportedOperationException(kind);
        var p = java.util.regex.Pattern.compile(rx);
        return x -> p.matcher(x.toString()).matches();
    }
    static String glob(String g) {
        var sb = new StringBuilder("^");
        boolean grp = false;
        for (int i = 0; i < g.length(); i++) {
            char c = g.charAt(i);
            switch (c) {
                case '*' -> { if (i + 1 < g.length() && g.charAt(i + 1) == '*') { sb.append(".*"); i++; } else sb.append("[^/]*"); }
                case '?' -> sb.append("[^/]");
                case '{' -> { sb.append("(?:"); grp = true; }
                case '}' -> { sb.append(")"); grp = false; }
                case ',' -> sb.append(grp ? "|" : ",");
                case '[' -> { int j = g.indexOf(']', i); sb.append(g, i, j + 1); i = j; }
                case '\\' -> { i++; sb.append(java.util.regex.Pattern.quote(String.valueOf(g.charAt(i)))); }
                default -> sb.append(".^$|()+".indexOf(c) >= 0 ? "\\" + c : String.valueOf(c));
            }
        }
        return sb.append('$').toString();
    }

    public static java.util.Enumeration<java.net.URL> ress(ClassLoader l, String n) { return ress(n); }
    public static java.util.Enumeration<java.net.URL> ress(String n) { var u = Res.url(n); return u == null ? java.util.Collections.emptyEnumeration() : java.util.Collections.enumeration(java.util.List.of(u)); }
    public static java.net.URL res(ClassLoader l, String n) { return Res.url(n); }
    public static java.net.URL res(String n) { return Res.url(n); }
    public static java.io.InputStream ras(Class<?> c, String n) { return Res.in(c, n); }
    public static java.io.InputStream ras(ClassLoader l, String n) { return Res.in(Res.class, n.startsWith("/") ? n : "/" + n); }
    public static java.io.InputStream ras(String n) { return ras((ClassLoader) null, n); }
    public static Process exec(Runtime r, String c) throws java.io.IOException { throw new java.io.IOException("no processes"); }

    public static String str(byte[] b, int hi, int o, int n) { char[] c = new char[n]; for (int i = 0; i < n; i++) c[i] = (char) (((hi & 0xff) << 8) | (b[o + i] & 0xff)); return new String(c); }
    public static void inflIn(java.util.zip.Inflater f, java.nio.ByteBuffer b) { byte[] a = new byte[b.remaining()]; b.get(a); f.setInput(a); }
    public static int infl(java.util.zip.Inflater f, java.nio.ByteBuffer o) throws java.util.zip.DataFormatException { byte[] a = new byte[o.remaining()]; int n = f.inflate(a); o.put(a, 0, n); return n; }
    public static void deflIn(java.util.zip.Deflater f, java.nio.ByteBuffer b) { byte[] a = new byte[b.remaining()]; b.get(a); f.setInput(a); }
    public static int defl(java.util.zip.Deflater f, java.nio.ByteBuffer o) { byte[] a = new byte[o.remaining()]; int n = f.deflate(a); o.put(a, 0, n); return n; }
    public static long secs(java.time.Duration d) { return d.getSeconds(); }

    public static org.lwjgl.system.libffi.FFICIF cif(Object r, Object[] a) { return null; }
    public static org.lwjgl.system.libffi.FFICIF cif(int abi, Object r, Object[] a) { return null; }
    public static org.lwjgl.system.libffi.FFICIF cif(int abi, int n, Object r, Object[] a) { return null; }
    public static org.lwjgl.system.libffi.FFIType ffit(Object[] a) { return null; }
    public static org.lwjgl.system.libffi.FFIType ffit(Object a, int n) { return null; }
    public static <T> java.util.ServiceLoader<T> svc(Class<T> c) { return null; }
    public static <T> java.util.ServiceLoader<T> svc(Class<T> c, ClassLoader l) { return null; }
    public static <T> java.util.Iterator<T> svcIt(java.util.ServiceLoader<T> s) { return java.util.Collections.emptyIterator(); }
    public static <T> java.util.Optional<T> svcFirst(java.util.ServiceLoader<T> s) { return java.util.Optional.empty(); }
    public static <T> java.util.stream.Stream<T> svcStream(java.util.ServiceLoader<T> s) { return java.util.stream.Stream.empty(); }
    public static com.google.common.collect.MapMaker mmv(com.google.common.collect.MapMaker m) { return m; }
    public static <K, V> com.google.common.cache.CacheBuilder<K, V> cbv(com.google.common.cache.CacheBuilder<K, V> b) { return b; }
    public static <T> java.util.stream.Stream<T> gstream(Iterable<T> i) { return i == null ? java.util.stream.Stream.empty() : com.google.common.collect.Streams.stream(i); }

    public static final class Exit extends RuntimeException {
        public final int c;
        Exit(int c) { super("exit " + c); this.c = c; }
    }
    public static java.lang.reflect.Type gce(java.lang.reflect.Type t, Class<?> r) {
        if (t instanceof java.lang.reflect.ParameterizedType p && p.getActualTypeArguments().length == 1 && java.util.Collection.class.isAssignableFrom(r)) return p.getActualTypeArguments()[0];
        return com.google.gson.internal.GsonTypes.getCollectionElementType(t, r);
    }

    public static java.lang.reflect.Type[] gmk(java.lang.reflect.Type t, Class<?> r) {
        if (t instanceof java.lang.reflect.ParameterizedType p && p.getActualTypeArguments().length == 2 && java.util.Map.class.isAssignableFrom(r)) return p.getActualTypeArguments();
        return com.google.gson.internal.GsonTypes.getMapKeyAndValueTypes(t, r);
    }
}
