package org.wrtca.util;

import android.text.TextPaint;
import android.text.TextUtils;
import com.catchpig.mvvm.utils.DateUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

/* loaded from: classes9.dex */
public class StringUtils {
    public static final SimpleDateFormat DATE_FORMAT_PART = new SimpleDateFormat(DateUtil.TIME_FORMAT_WITH_HM);
    private static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd hh:mm:ss";
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    private static final String DEFAULT_FILE_PATTERN = "yyyy-MM-dd-HH-mm-ss";
    public static final String EMPTY = "";
    private static final double GB = 1.073741824E9d;
    private static final double KB = 1024.0d;
    private static final double MB = 1048576.0d;

    public static float GetTextWidth(String str, float f2) {
        if (isEmpty(str)) {
            return 0.0f;
        }
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(f2);
        return textPaint.measureText(str.trim()) + ((int) (f2 * 0.1d));
    }

    public static char chatAt(String str, int i2) {
        if (str == null || str.length() <= 0) {
            return ' ';
        }
        return str.charAt(i2);
    }

    public static String concat(String... strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        if (strArr != null) {
            for (String str : strArr) {
                if (str != null) {
                    stringBuffer.append(str);
                }
            }
        }
        return stringBuffer.toString();
    }

    public static String createFileName() {
        return new SimpleDateFormat(DEFAULT_FILE_PATTERN).format(new Date(System.currentTimeMillis()));
    }

    public static String currentTimeString() {
        return DATE_FORMAT_PART.format(Calendar.getInstance().getTime());
    }

    public static String findString(String str, String str2, String str3) {
        int length = str2.length();
        int iIndexOf = isEmpty(str2) ? 0 : str.indexOf(str2);
        if (iIndexOf <= -1) {
            return "";
        }
        int iIndexOf2 = isEmpty(str3) ? -1 : str.indexOf(str3, length + iIndexOf);
        return iIndexOf2 > -1 ? str.substring(iIndexOf + str2.length(), iIndexOf2) : "";
    }

    public static String formatDate(Date date, String str) {
        return new SimpleDateFormat(str).format(date);
    }

    public static String formatDateTime(Date date) {
        return formatDate(date, DEFAULT_DATETIME_PATTERN);
    }

    public static String formatGMTDate(String str) {
        return formatDate(Calendar.getInstance(TimeZone.getTimeZone(str)).getTimeInMillis());
    }

    public static String generateFileSize(long j2) {
        double d3 = j2;
        if (d3 < 1024.0d) {
            return j2 + "B";
        }
        if (d3 < 1048576.0d) {
            return String.format("%.1f", Double.valueOf(d3 / 1024.0d)) + "KB";
        }
        if (d3 < 1.073741824E9d) {
            return String.format("%.1f", Double.valueOf(d3 / 1048576.0d)) + "MB";
        }
        return String.format("%.1f", Double.valueOf(d3 / 1.073741824E9d)) + "GB";
    }

    public static String generateTime(long j2) {
        int i2 = (int) (j2 / 1000);
        int i3 = i2 % 60;
        int i4 = (i2 / 60) % 60;
        int i5 = i2 / 3600;
        return i5 > 0 ? String.format("%02d:%02d:%02d", Integer.valueOf(i5), Integer.valueOf(i4), Integer.valueOf(i3)) : String.format("%02d:%02d", Integer.valueOf(i4), Integer.valueOf(i3));
    }

    public static String gennerTime(int i2) {
        return String.format("%02d:%02d", Integer.valueOf((i2 / 60) % 60), Integer.valueOf(i2 % 60));
    }

    public static String getDate() {
        return formatDate(new Date(), "yyyy-MM-dd");
    }

    public static String getDateTime() {
        return formatDate(new Date(), DEFAULT_DATETIME_PATTERN);
    }

    public static boolean isBlank(String str) {
        return TextUtils.isEmpty(str);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0 || str.equalsIgnoreCase("null");
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String join(ArrayList<String> arrayList, String str) {
        StringBuffer stringBuffer = new StringBuffer();
        if (arrayList != null && arrayList.size() > 0) {
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                stringBuffer.append(it.next());
                stringBuffer.append(str);
            }
            stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
        }
        return stringBuffer.toString();
    }

    public static String makeSafe(String str) {
        return str == null ? "" : str;
    }

    public static String substring(String str, String str2, String str3, String str4) {
        int length = str2.length();
        int iIndexOf = isEmpty(str2) ? 0 : str.indexOf(str2);
        if (iIndexOf <= -1) {
            return str4;
        }
        int iIndexOf2 = isEmpty(str3) ? -1 : str.indexOf(str3, length + iIndexOf);
        return iIndexOf2 > -1 ? str.substring(iIndexOf + str2.length(), iIndexOf2) : str.substring(iIndexOf + str2.length());
    }

    public static String trim(String str) {
        return str == null ? "" : str.trim();
    }

    public static String formatDateTime(long j2) {
        return formatDate(new Date(j2), DEFAULT_DATETIME_PATTERN);
    }

    public static String formatDate(long j2, String str) {
        return new SimpleDateFormat(str).format(new Date(j2));
    }

    public static String formatDate(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    public static String formatDate(long j2) {
        return formatDate(new Date(j2), "yyyy-MM-dd");
    }

    public static String substring(String str, String str2, String str3) {
        return substring(str, str2, str3, "");
    }

    public static String join(Iterator<String> it, String str) {
        StringBuffer stringBuffer = new StringBuffer();
        if (it != null) {
            while (it.hasNext()) {
                stringBuffer.append(it.next());
                stringBuffer.append(str);
            }
            if (stringBuffer.length() > 0) {
                stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
            }
        }
        return stringBuffer.toString();
    }
}
