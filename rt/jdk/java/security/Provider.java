package java.security;

public abstract class Provider {
    protected Provider(String n, String v, String i) {}
    public String getName() { return "browser"; }
}
