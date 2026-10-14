package java.security.spec;

public class PKCS8EncodedKeySpec extends EncodedKeySpec {
    public PKCS8EncodedKeySpec(byte[] e) { super(e); }
    public String getFormat() { return "PKCS#8"; }
}
