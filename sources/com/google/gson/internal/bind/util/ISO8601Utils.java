package com.google.gson.internal.bind.util;

import cn.hutool.core.text.CharPool;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes4.dex */
public class ISO8601Utils {
    private static final String UTC_ID = "UTC";
    private static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone(UTC_ID);

    private static boolean checkOffset(String str, int i2, char c3) {
        return i2 < str.length() && str.charAt(i2) == c3;
    }

    public static String format(Date date) {
        return format(date, false, TIMEZONE_UTC);
    }

    private static int indexOfNonDigit(String str, int i2) {
        while (i2 < str.length()) {
            char cCharAt = str.charAt(i2);
            if (cCharAt < '0' || cCharAt > '9') {
                return i2;
            }
            i2++;
        }
        return str.length();
    }

    private static void padInt(StringBuilder sb, int i2, int i3) {
        String string = Integer.toString(i2);
        for (int length = i3 - string.length(); length > 0; length--) {
            sb.append('0');
        }
        sb.append(string);
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x00d3 A[Catch: IllegalArgumentException -> 0x01c0, NumberFormatException -> 0x01c2, IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x01c4, TryCatch #2 {IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x01c4, blocks: (B:3:0x0004, B:5:0x0016, B:6:0x0018, B:8:0x0024, B:9:0x0026, B:11:0x0036, B:13:0x003c, B:17:0x0054, B:19:0x0064, B:20:0x0066, B:22:0x0072, B:23:0x0074, B:25:0x007a, B:29:0x0084, B:34:0x0094, B:36:0x009c, B:47:0x00cd, B:49:0x00d3, B:51:0x00da, B:75:0x0187, B:55:0x00e4, B:56:0x00ff, B:57:0x0100, B:61:0x011c, B:63:0x0129, B:66:0x0132, B:68:0x0151, B:71:0x0160, B:72:0x0182, B:74:0x0185, B:60:0x010b, B:77:0x01b8, B:78:0x01bf, B:40:0x00b4, B:41:0x00b7), top: B:94:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01b8 A[Catch: IllegalArgumentException -> 0x01c0, NumberFormatException -> 0x01c2, IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x01c4, TryCatch #2 {IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x01c4, blocks: (B:3:0x0004, B:5:0x0016, B:6:0x0018, B:8:0x0024, B:9:0x0026, B:11:0x0036, B:13:0x003c, B:17:0x0054, B:19:0x0064, B:20:0x0066, B:22:0x0072, B:23:0x0074, B:25:0x007a, B:29:0x0084, B:34:0x0094, B:36:0x009c, B:47:0x00cd, B:49:0x00d3, B:51:0x00da, B:75:0x0187, B:55:0x00e4, B:56:0x00ff, B:57:0x0100, B:61:0x011c, B:63:0x0129, B:66:0x0132, B:68:0x0151, B:71:0x0160, B:72:0x0182, B:74:0x0185, B:60:0x010b, B:77:0x01b8, B:78:0x01bf, B:40:0x00b4, B:41:0x00b7), top: B:94:0x0004 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.Date parse(java.lang.String r17, java.text.ParsePosition r18) throws java.text.ParseException {
        /*
            Method dump skipped, instructions count: 557
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.bind.util.ISO8601Utils.parse(java.lang.String, java.text.ParsePosition):java.util.Date");
    }

    private static int parseInt(String str, int i2, int i3) throws NumberFormatException {
        int i4;
        int i5;
        if (i2 < 0 || i3 > str.length() || i2 > i3) {
            throw new NumberFormatException(str);
        }
        if (i2 < i3) {
            i5 = i2 + 1;
            int iDigit = Character.digit(str.charAt(i2), 10);
            if (iDigit < 0) {
                throw new NumberFormatException("Invalid number: " + str.substring(i2, i3));
            }
            i4 = -iDigit;
        } else {
            i4 = 0;
            i5 = i2;
        }
        while (i5 < i3) {
            int i6 = i5 + 1;
            int iDigit2 = Character.digit(str.charAt(i5), 10);
            if (iDigit2 < 0) {
                throw new NumberFormatException("Invalid number: " + str.substring(i2, i3));
            }
            i4 = (i4 * 10) - iDigit2;
            i5 = i6;
        }
        return -i4;
    }

    public static String format(Date date, boolean z2) {
        return format(date, z2, TIMEZONE_UTC);
    }

    public static String format(Date date, boolean z2, TimeZone timeZone) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(timeZone, Locale.US);
        gregorianCalendar.setTime(date);
        StringBuilder sb = new StringBuilder(19 + (z2 ? 4 : 0) + (timeZone.getRawOffset() == 0 ? 1 : 6));
        padInt(sb, gregorianCalendar.get(1), 4);
        char c3 = CharPool.DASHED;
        sb.append(CharPool.DASHED);
        padInt(sb, gregorianCalendar.get(2) + 1, 2);
        sb.append(CharPool.DASHED);
        padInt(sb, gregorianCalendar.get(5), 2);
        sb.append('T');
        padInt(sb, gregorianCalendar.get(11), 2);
        sb.append(':');
        padInt(sb, gregorianCalendar.get(12), 2);
        sb.append(':');
        padInt(sb, gregorianCalendar.get(13), 2);
        if (z2) {
            sb.append('.');
            padInt(sb, gregorianCalendar.get(14), 3);
        }
        int offset = timeZone.getOffset(gregorianCalendar.getTimeInMillis());
        if (offset != 0) {
            int i2 = offset / 60000;
            int iAbs = Math.abs(i2 / 60);
            int iAbs2 = Math.abs(i2 % 60);
            if (offset >= 0) {
                c3 = '+';
            }
            sb.append(c3);
            padInt(sb, iAbs, 2);
            sb.append(':');
            padInt(sb, iAbs2, 2);
        } else {
            sb.append('Z');
        }
        return sb.toString();
    }
}
