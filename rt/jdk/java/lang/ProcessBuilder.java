package java.lang;

import java.util.List;

public final class ProcessBuilder {
    final List<String> c;
    public ProcessBuilder(List<String> c) { this.c = c; }
    public ProcessBuilder(String... c) { this.c = List.of(c); }
    public List<String> command() { return c; }
    public ProcessBuilder redirectErrorStream(boolean b) { return this; }
    public ProcessBuilder directory(java.io.File d) { return this; }
    public java.util.Map<String, String> environment() { return new java.util.HashMap<>(); }
    public Process start() throws java.io.IOException { throw new java.io.IOException("no processes in browser"); }
}
