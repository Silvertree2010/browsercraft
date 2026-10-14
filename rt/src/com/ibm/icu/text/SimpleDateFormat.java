package com.ibm.icu.text;

import java.util.Date;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;

public class SimpleDateFormat extends DateFormat {
    final String p;
    Calendar c;
    public SimpleDateFormat(String p, ULocale l) { this.p = p; }
    public void setCalendar(Calendar c) { this.c = c; }
    public String format(Date d) {
        var t = java.time.LocalDateTime.ofEpochSecond(d.getTime() / 1000, 0, java.time.ZoneOffset.ofTotalSeconds(c == null ? 0 : c.off()));
        var sb = new StringBuilder();
        for (int i = 0; i < p.length(); ) {
            char ch = p.charAt(i);
            int j = i;
            while (j < p.length() && p.charAt(j) == ch) j++;
            int n = j - i;
            int v = switch (ch) {
                case 'H' -> t.getHour();
                case 'h' -> (t.getHour() + 11) % 12 + 1;
                case 'm' -> t.getMinute();
                case 's' -> t.getSecond();
                case 'd' -> t.getDayOfMonth();
                case 'M' -> t.getMonthValue();
                case 'y' -> n == 2 ? t.getYear() % 100 : t.getYear();
                default -> -1;
            };
            if (ch == 'a') sb.append(t.getHour() < 12 ? "AM" : "PM");
            else if (v < 0) sb.append(p, i, j);
            else { var s = Integer.toString(v); for (int k = s.length(); k < n; k++) sb.append('0'); sb.append(s); }
            i = j;
        }
        return sb.toString();
    }
}
