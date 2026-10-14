package java.util;

import java.io.*;

public final class Scanner implements Closeable, Iterator<String> {
    final BufferedReader r;
    String line;
    int pos;
    public Scanner(InputStream in) { r = new BufferedReader(new InputStreamReader(in)); }
    public Scanner(Readable in) { r = new BufferedReader((Reader) in); }
    public Scanner(String s) { r = new BufferedReader(new StringReader(s)); }
    boolean fill() {
        while (line == null || pos >= line.length() || line.substring(pos).isBlank()) {
            try { line = r.readLine(); } catch (IOException e) { line = null; }
            pos = 0;
            if (line == null) return false;
        }
        return true;
    }
    public boolean hasNext() { return fill(); }
    public String next() {
        if (!fill()) throw new NoSuchElementException();
        while (pos < line.length() && Character.isWhitespace(line.charAt(pos))) pos++;
        int s = pos;
        while (pos < line.length() && !Character.isWhitespace(line.charAt(pos))) pos++;
        return line.substring(s, pos);
    }
    public boolean hasNextLine() { if (line != null && pos < line.length()) return true; try { line = r.readLine(); pos = 0; } catch (IOException e) { line = null; } return line != null; }
    public String nextLine() { if (!hasNextLine()) throw new NoSuchElementException(); var s = line.substring(pos); line = null; pos = 0; return s; }
    public int nextInt() { return Integer.parseInt(next()); }
    public long nextLong() { return Long.parseLong(next()); }
    public double nextDouble() { return Double.parseDouble(next()); }
    public float nextFloat() { return Float.parseFloat(next()); }
    public boolean nextBoolean() { return Boolean.parseBoolean(next()); }
    public boolean hasNextInt() { if (!fill()) return false; int p = pos; try { Integer.parseInt(next()); return true; } catch (NumberFormatException e) { return false; } finally { pos = p; } }
    public Scanner useDelimiter(String d) { return this; }
    public Scanner useLocale(Locale l) { return this; }
    public void close() { try { r.close(); } catch (IOException e) {} }
}
