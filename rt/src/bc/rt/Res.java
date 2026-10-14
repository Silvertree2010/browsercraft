package bc.rt;

public final class Res {
    public static String root = "/mc/jar";

    public static java.net.URL url(Class<?> c, String n) {
        if (!n.startsWith("/")) {
            var p = c.getName();
            int i = p.lastIndexOf('.');
            n = "/" + (i < 0 ? "" : p.substring(0, i).replace('.', '/') + "/") + n;
        }
        var f = new java.io.File(root + n);
        if (!f.exists()) return null;
        try { return new java.net.URL("file", "", root + n); } catch (java.net.MalformedURLException e) { return null; }
    }

    public static java.net.URL url(String n) {
        return url(Res.class, n.startsWith("/") ? n : "/" + n);
    }

    public static java.io.InputStream in(Class<?> c, String n) {
        var u = url(c, n);
        if (u == null) return null;
        try { return new java.io.FileInputStream(u.getPath()); } catch (java.io.IOException e) { return null; }
    }
}
