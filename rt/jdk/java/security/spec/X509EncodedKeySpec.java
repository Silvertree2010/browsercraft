package java.security.spec;

public class X509EncodedKeySpec extends EncodedKeySpec {
    public X509EncodedKeySpec(byte[] e) { super(e); }
    public String getFormat() { return "X.509"; }
}
