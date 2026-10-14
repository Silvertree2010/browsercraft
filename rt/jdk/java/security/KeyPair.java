package java.security;

public final class KeyPair implements java.io.Serializable {
    final PublicKey pu;
    final PrivateKey pr;
    public KeyPair(PublicKey pu, PrivateKey pr) { this.pu = pu; this.pr = pr; }
    public PublicKey getPublic() { return pu; }
    public PrivateKey getPrivate() { return pr; }
}
