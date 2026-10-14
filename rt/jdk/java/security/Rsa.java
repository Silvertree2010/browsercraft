package java.security;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

final class Rsa {
    static final byte[] OID = {0x30, 0x0d, 0x06, 0x09, 0x2a, (byte) 0x86, 0x48, (byte) 0x86, (byte) 0xf7, 0x0d, 0x01, 0x01, 0x01, 0x05, 0x00};

    static final class Pub implements RSAPublicKey {
        final BigInteger n, e;
        Pub(BigInteger n, BigInteger e) { this.n = n; this.e = e; }
        public String getAlgorithm() { return "RSA"; }
        public String getFormat() { return "X.509"; }
        public byte[] getEncoded() {
            var k = seq(cat(integer(n), integer(e)));
            var bs = new byte[k.length + 1];
            System.arraycopy(k, 0, bs, 1, k.length);
            return seq(cat(OID, tlv(0x03, bs)));
        }
        public BigInteger getModulus() { return n; }
        public BigInteger getPublicExponent() { return e; }
        public boolean equals(Object o) { return o instanceof Pub p && p.n.equals(n) && p.e.equals(e); }
        public int hashCode() { return n.hashCode() ^ e.hashCode(); }
    }

    static final class Priv implements RSAPrivateKey {
        final BigInteger n, e, d, p, q;
        Priv(BigInteger n, BigInteger e, BigInteger d, BigInteger p, BigInteger q) { this.n = n; this.e = e; this.d = d; this.p = p; this.q = q; }
        public String getAlgorithm() { return "RSA"; }
        public String getFormat() { return "PKCS#8"; }
        public byte[] getEncoded() {
            var z = BigInteger.ZERO;
            var pp = p == null ? z : p;
            var qq = q == null ? z : q;
            var dp = p == null ? z : d.mod(p.subtract(BigInteger.ONE));
            var dq = q == null ? z : d.mod(q.subtract(BigInteger.ONE));
            var qi = p == null ? z : q.modInverse(p);
            var k = seq(cat(integer(z), integer(n), integer(e), integer(d), integer(pp), integer(qq), integer(dp), integer(dq), integer(qi)));
            return seq(cat(integer(z), OID, tlv(0x04, k)));
        }
        public BigInteger getModulus() { return n; }
        public BigInteger getPrivateExponent() { return d; }
    }

    static byte[] cat(byte[]... a) { var o = new ByteArrayOutputStream(); for (var b : a) o.write(b, 0, b.length); return o.toByteArray(); }
    static byte[] len(int l) {
        if (l < 128) return new byte[]{(byte) l};
        if (l < 256) return new byte[]{(byte) 0x81, (byte) l};
        if (l < 65536) return new byte[]{(byte) 0x82, (byte) (l >> 8), (byte) l};
        return new byte[]{(byte) 0x83, (byte) (l >> 16), (byte) (l >> 8), (byte) l};
    }
    static byte[] tlv(int t, byte[] v) { return cat(new byte[]{(byte) t}, len(v.length), v); }
    static byte[] seq(byte[] v) { return tlv(0x30, v); }
    static byte[] integer(BigInteger i) { return tlv(0x02, i.toByteArray()); }

    static final class Der {
        final byte[] b;
        int p;
        Der(byte[] b) { this.b = b; }
        int tag() { return b[p] & 255; }
        int hdr() {
            p++;
            int l = b[p++] & 255;
            if (l >= 128) { int n = l & 127; l = 0; for (int i = 0; i < n; i++) l = (l << 8) | (b[p++] & 255); }
            return l;
        }
        Der inner() { int l = hdr(); var d = new Der(java.util.Arrays.copyOfRange(b, p, p + l)); p += l; return d; }
        BigInteger integer() { int l = hdr(); var v = new BigInteger(java.util.Arrays.copyOfRange(b, p, p + l)); p += l; return v; }
        void skip() { int l = hdr(); p += l; }
        byte[] bytes() { int l = hdr(); var v = java.util.Arrays.copyOfRange(b, p, p + l); p += l; return v; }
    }

    static Pub pub(byte[] x509) {
        var s = new Der(x509).inner();
        s.skip();
        var bs = s.bytes();
        var k = new Der(java.util.Arrays.copyOfRange(bs, 1, bs.length)).inner();
        return new Pub(k.integer(), k.integer());
    }

    static Priv priv(byte[] pkcs8) {
        var s = new Der(pkcs8).inner();
        s.integer();
        s.skip();
        var k = new Der(s.bytes()).inner();
        k.integer();
        var n = k.integer();
        var e = k.integer();
        var d = k.integer();
        var p = k.integer();
        var q = k.integer();
        return new Priv(n, e, d, p.signum() == 0 ? null : p, q.signum() == 0 ? null : q);
    }

    static int kl(BigInteger n) { return (n.bitLength() + 7) / 8; }

    static byte[] i2osp(BigInteger x, int l) {
        var b = x.toByteArray();
        if (b.length == l) return b;
        var r = new byte[l];
        if (b.length > l) System.arraycopy(b, b.length - l, r, 0, l);
        else System.arraycopy(b, 0, r, l - b.length, b.length);
        return r;
    }

    static byte[] priv(Priv k, byte[] m) { return i2osp(new BigInteger(1, m).modPow(k.d, k.n), kl(k.n)); }
    static byte[] pub(Pub k, byte[] m) { return i2osp(new BigInteger(1, m).modPow(k.e, k.n), kl(k.n)); }

    static final byte[] SHA256 = {0x30, 0x31, 0x30, 0x0d, 0x06, 0x09, 0x60, (byte) 0x86, 0x48, 0x01, 0x65, 0x03, 0x04, 0x02, 0x01, 0x05, 0x00, 0x04, 0x20};
    static final byte[] SHA1 = {0x30, 0x21, 0x30, 0x09, 0x06, 0x05, 0x2b, 0x0e, 0x03, 0x02, 0x1a, 0x05, 0x00, 0x04, 0x14};

    static byte[] emsa(byte[] h, byte[] pre, int l) {
        var t = cat(pre, h);
        var r = new byte[l];
        r[0] = 0; r[1] = 1;
        for (int i = 2; i < l - t.length - 1; i++) r[i] = (byte) 0xff;
        r[l - t.length - 1] = 0;
        System.arraycopy(t, 0, r, l - t.length, t.length);
        return r;
    }
}
