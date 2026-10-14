package java.security.spec;

public abstract class EncodedKeySpec implements KeySpec {
    final byte[] e;
    public EncodedKeySpec(byte[] e) { this.e = e.clone(); }
    public byte[] getEncoded() { return e.clone(); }
    public abstract String getFormat();
}
