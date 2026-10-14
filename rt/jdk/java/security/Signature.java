package java.security;

public abstract class Signature {
    final String a;
    final MessageDigest md;
    final byte[] pre;
    Rsa.Pub pu;
    Rsa.Priv pr;

    protected Signature(String a) {
        this.a = a;
        try { md = MessageDigest.getInstance(a.startsWith("SHA256") ? "SHA-256" : "SHA-1"); } catch (NoSuchAlgorithmException e) { throw new IllegalStateException(e); }
        pre = a.startsWith("SHA256") ? Rsa.SHA256 : Rsa.SHA1;
    }

    public static Signature getInstance(String a) throws NoSuchAlgorithmException {
        if (!a.equalsIgnoreCase("SHA256withRSA") && !a.equalsIgnoreCase("SHA1withRSA")) throw new NoSuchAlgorithmException(a);
        return new Signature(a.toUpperCase().startsWith("SHA256") ? "SHA256withRSA" : "SHA1withRSA") {};
    }
    public final String getAlgorithm() { return a; }
    public final void initSign(PrivateKey k) throws InvalidKeyException { if (!(k instanceof Rsa.Priv p)) throw new InvalidKeyException("not RSA"); pr = p; pu = null; md.reset(); }
    public final void initVerify(PublicKey k) throws InvalidKeyException { if (!(k instanceof Rsa.Pub p)) throw new InvalidKeyException("not RSA"); pu = p; pr = null; md.reset(); }
    public final void update(byte b) throws SignatureException { md.update(b); }
    public final void update(byte[] b) throws SignatureException { md.update(b); }
    public final void update(byte[] b, int o, int l) throws SignatureException { md.update(b, o, l); }
    public final void update(java.nio.ByteBuffer b) throws SignatureException { md.update(b); }
    public final byte[] sign() throws SignatureException {
        if (pr == null) throw new SignatureException("not initialized for signing");
        return Rsa.priv(pr, Rsa.emsa(md.digest(), pre, Rsa.kl(pr.n)));
    }
    public final boolean verify(byte[] s) throws SignatureException {
        if (pu == null) throw new SignatureException("not initialized for verification");
        var h = md.digest();
        var em = Rsa.pub(pu, s);
        return java.util.Arrays.equals(em, Rsa.emsa(h, pre, Rsa.kl(pu.n)));
    }
}
