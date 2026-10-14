package java.net;

public abstract class Authenticator {
    static Authenticator def;
    public static synchronized void setDefault(Authenticator a) { def = a; }
    public static Authenticator getDefault() { return def; }
    protected PasswordAuthentication getPasswordAuthentication() { return null; }
    protected final String getRequestingHost() { return null; }
    protected final int getRequestingPort() { return -1; }
}
