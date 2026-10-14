package javax.crypto;

import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.math.BigInteger;
import javax.crypto.spec.IvParameterSpec;

public class Cipher {
    public static final int ENCRYPT_MODE = 1, DECRYPT_MODE = 2, WRAP_MODE = 3, UNWRAP_MODE = 4;
    final String t;
    final boolean rsa;
    int mode;
    Key key;
    Aes aes;
    byte[] iv, ks = new byte[16];

    protected Cipher(String t) { this.t = t; rsa = t.toUpperCase().startsWith("RSA"); }

    public static Cipher getInstance(String t) throws NoSuchAlgorithmException, NoSuchPaddingException {
        var u = t.toUpperCase();
        if (!(u.startsWith("RSA") || u.equals("AES/CFB8/NOPADDING"))) throw new NoSuchAlgorithmException(t);
        return new Cipher(t);
    }

    public final String getAlgorithm() { return t; }
    public final int getBlockSize() { return rsa ? 0 : 16; }
    public final byte[] getIV() { return iv == null ? null : iv.clone(); }
    public final int getOutputSize(int n) { return rsa ? 512 : n; }

    public final void init(int m, Key k) throws InvalidKeyException { try { init(m, k, (AlgorithmParameterSpec) null); } catch (InvalidAlgorithmParameterException e) { throw new InvalidKeyException(e.getMessage()); } }
    public final void init(int m, Key k, SecureRandom r) throws InvalidKeyException { init(m, k); }
    public final void init(int m, Key k, AlgorithmParameterSpec p) throws InvalidKeyException, InvalidAlgorithmParameterException {
        mode = m;
        key = k;
        if (!rsa) {
            aes = new Aes(k.getEncoded());
            iv = p instanceof IvParameterSpec v ? v.getIV() : new byte[16];
        }
    }

    byte cfb(byte b) {
        aes.enc(iv, ks);
        byte o = (byte) (b ^ ks[0]);
        System.arraycopy(iv, 1, iv, 0, 15);
        iv[15] = mode == ENCRYPT_MODE ? o : b;
        return o;
    }

    public final byte[] update(byte[] in) { return update(in, 0, in.length); }
    public final byte[] update(byte[] in, int o, int n) { var r = new byte[n]; for (int i = 0; i < n; i++) r[i] = cfb(in[o + i]); return r; }
    public final int update(byte[] in, int o, int n, byte[] out) throws ShortBufferException { return update(in, o, n, out, 0); }
    public final int update(byte[] in, int o, int n, byte[] out, int oo) throws ShortBufferException {
        if (out.length - oo < n) throw new ShortBufferException();
        for (int i = 0; i < n; i++) out[oo + i] = cfb(in[o + i]);
        return n;
    }
    public final int update(java.nio.ByteBuffer in, java.nio.ByteBuffer out) throws ShortBufferException {
        int n = in.remaining();
        if (out.remaining() < n) throw new ShortBufferException();
        for (int i = 0; i < n; i++) out.put(cfb(in.get()));
        return n;
    }

    final java.io.ByteArrayOutputStream buf = new java.io.ByteArrayOutputStream();

    public final byte[] doFinal() throws IllegalBlockSizeException, BadPaddingException { return doFinal(new byte[0]); }
    public final byte[] doFinal(byte[] in) throws IllegalBlockSizeException, BadPaddingException { return doFinal(in, 0, in.length); }
    public final byte[] doFinal(byte[] in, int o, int n) throws IllegalBlockSizeException, BadPaddingException {
        if (!rsa) return update(in, o, n);
        buf.write(in, o, n);
        var m = buf.toByteArray();
        buf.reset();
        BigInteger mod, ex;
        if (key instanceof RSAPublicKey p) { mod = p.getModulus(); ex = p.getPublicExponent(); }
        else if (key instanceof RSAPrivateKey p) { mod = p.getModulus(); ex = p.getPrivateExponent(); }
        else throw new IllegalBlockSizeException("bad key");
        int k = (mod.bitLength() + 7) / 8;
        if (mode == ENCRYPT_MODE || mode == WRAP_MODE) {
            if (m.length > k - 11) throw new IllegalBlockSizeException("data too long");
            var em = new byte[k];
            em[1] = (byte) (key instanceof RSAPublicKey ? 2 : 1);
            var r = new SecureRandom();
            for (int i = 2; i < k - m.length - 1; i++) {
                if (em[1] == 2) { byte b; do { b = (byte) r.nextInt(256); } while (b == 0); em[i] = b; } else em[i] = (byte) 0xff;
            }
            System.arraycopy(m, 0, em, k - m.length, m.length);
            return pad(new BigInteger(1, em).modPow(ex, mod).toByteArray(), k);
        }
        var em = pad(new BigInteger(1, m).modPow(ex, mod).toByteArray(), k);
        if (em[0] != 0) throw new BadPaddingException("bad padding");
        int i = 2;
        while (i < k && em[i] != 0) i++;
        if (i >= k) throw new BadPaddingException("bad padding");
        return java.util.Arrays.copyOfRange(em, i + 1, k);
    }

    static byte[] pad(byte[] b, int k) {
        if (b.length == k) return b;
        var r = new byte[k];
        if (b.length > k) System.arraycopy(b, b.length - k, r, 0, k);
        else System.arraycopy(b, 0, r, k - b.length, b.length);
        return r;
    }
}
