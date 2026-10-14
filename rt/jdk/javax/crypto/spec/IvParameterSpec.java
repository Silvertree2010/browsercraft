package javax.crypto.spec;

public class IvParameterSpec implements java.security.spec.AlgorithmParameterSpec {
    final byte[] iv;
    public IvParameterSpec(byte[] iv) { this.iv = iv.clone(); }
    public IvParameterSpec(byte[] iv, int o, int l) { this.iv = java.util.Arrays.copyOfRange(iv, o, o + l); }
    public byte[] getIV() { return iv.clone(); }
}
