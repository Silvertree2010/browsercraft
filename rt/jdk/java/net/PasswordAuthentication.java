package java.net;

public final class PasswordAuthentication {
    final String u;
    final char[] p;
    public PasswordAuthentication(String u, char[] p) { this.u = u; this.p = p.clone(); }
    public String getUserName() { return u; }
    public char[] getPassword() { return p; }
}
