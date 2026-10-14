package com.ibm.icu.text;

import java.util.Comparator;
import java.util.Locale;

public class Collator implements Comparator<Object> {
    public static Collator getInstance(Locale l) { return new Collator(); }
    public int compare(String a, String b) { return a.compareToIgnoreCase(b); }
    public int compare(Object a, Object b) { return compare(String.valueOf(a), String.valueOf(b)); }
}
