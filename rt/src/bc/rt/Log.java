package bc.rt;

import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.helpers.LegacyAbstractLogger;
import org.slf4j.helpers.MessageFormatter;

public final class Log extends LegacyAbstractLogger {
    public static int min = 20;

    public Log(String n) { this.name = n; }

    public boolean isTraceEnabled() { return min <= 0; }
    public boolean isDebugEnabled() { return min <= 10; }
    public boolean isInfoEnabled() { return min <= 20; }
    public boolean isWarnEnabled() { return min <= 30; }
    public boolean isErrorEnabled() { return min <= 40; }

    protected String getFullyQualifiedCallerName() { return null; }

    protected void handleNormalizedLoggingCall(Level l, Marker m, String f, Object[] a, Throwable t) {
        var s = "[" + l + "] " + (name == null ? "" : name + ": ") + MessageFormatter.basicArrayFormat(f, a);
        Js.log(s);
        if (t != null) t.printStackTrace();
        com.mojang.logging.LogListeners.emit(s, l);
    }
}
