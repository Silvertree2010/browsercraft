package java.net;

public final class NetworkInterface {
    NetworkInterface() {}
    public static NetworkInterface getByName(String n) { return null; }
    public static NetworkInterface getByIndex(int i) { return null; }
    public static NetworkInterface getByInetAddress(InetAddress a) { return null; }
    public static java.util.Enumeration<NetworkInterface> getNetworkInterfaces() { return java.util.Collections.emptyEnumeration(); }
    public String getName() { return "lo"; }
    public int getIndex() { return 0; }
    public java.util.Enumeration<InetAddress> getInetAddresses() { return java.util.Collections.emptyEnumeration(); }
    public boolean isLoopback() { return true; }
    public boolean isUp() { return true; }
    public boolean isVirtual() { return false; }
    public String getDisplayName() { return "lo"; }
    public byte[] getHardwareAddress() { return null; }
    public boolean isPointToPoint() { return false; }
}
