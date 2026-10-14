package java.util;

public final class StringJoiner {
    final String p, d, s;
    StringBuilder v;
    String e;
    public StringJoiner(CharSequence d) { this(d, "", ""); }
    public StringJoiner(CharSequence d, CharSequence p, CharSequence s) { this.d = d.toString(); this.p = p.toString(); this.s = s.toString(); e = this.p + this.s; }
    public StringJoiner setEmptyValue(CharSequence e) { this.e = e.toString(); return this; }
    public StringJoiner add(CharSequence x) { if (v == null) v = new StringBuilder(p); else v.append(d); v.append(x); return this; }
    public StringJoiner merge(StringJoiner o) { if (o.v != null) add(o.v.substring(o.p.length())); return this; }
    public int length() { return v == null ? e.length() : v.length() + s.length(); }
    public String toString() { return v == null ? e : v + s; }
}
