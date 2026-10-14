package java.nio.channels.spi;

public abstract class SelectorProvider {
    protected SelectorProvider() {}
    public static SelectorProvider provider() { throw new UnsupportedOperationException("no selector provider in browser"); }
    public abstract AbstractSelector openSelector() throws java.io.IOException;
}
