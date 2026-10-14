package java.net;

public class NoRouteToHostException extends SocketException {
    public NoRouteToHostException() { super(); }
    public NoRouteToHostException(String s) { super(s); }
}
