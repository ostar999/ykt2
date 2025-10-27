package com.aliyun.player.aliyunplayerbase.util;

import android.text.TextUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Locale;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes2.dex */
public class Formatter {
    public static String double2Date(double d3) {
        return formatDate(new Double(d3).longValue() - 28800).substring(3);
    }

    public static String formatDate(long j2) {
        Object objValueOf;
        Object objValueOf2;
        Object objValueOf3;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j2 * 1000);
        int i2 = calendar.get(11);
        StringBuilder sb = new StringBuilder();
        sb.append("");
        if (i2 < 10) {
            objValueOf = "0" + i2;
        } else {
            objValueOf = Integer.valueOf(i2);
        }
        sb.append(objValueOf);
        sb.append(":");
        String string = sb.toString();
        int i3 = calendar.get(12);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(string);
        if (i3 < 10) {
            objValueOf2 = "0" + i3;
        } else {
            objValueOf2 = Integer.valueOf(i3);
        }
        sb2.append(objValueOf2);
        sb2.append(":");
        String string2 = sb2.toString();
        int i4 = calendar.get(13);
        StringBuilder sb3 = new StringBuilder();
        sb3.append(string2);
        if (i4 < 10) {
            objValueOf3 = "0" + i4;
        } else {
            objValueOf3 = Integer.valueOf(i4);
        }
        sb3.append(objValueOf3);
        return sb3.toString();
    }

    public static String formatSizeDecimal(long j2) {
        double d3 = (j2 / 1024.0d) * 1.0d;
        BigDecimal bigDecimal = new BigDecimal(d3);
        if (d3 < 1024.0d) {
            return String.format("%.1f", bigDecimal.setScale(2, RoundingMode.HALF_UP)) + "K";
        }
        return String.format("%.1f", new BigDecimal((d3 / 1024.0d) * 1.0d).setScale(2, RoundingMode.HALF_UP)) + "M";
    }

    public static String formatTime(int i2) {
        String str;
        int i3 = i2 / 1000;
        int i4 = i3 % 60;
        int i5 = i3 / 60;
        int i6 = i5 % 60;
        int i7 = i5 / 60;
        String str2 = "";
        if (i7 > 9) {
            str2 = "" + i7 + ":";
        } else if (i7 > 0) {
            str2 = "0" + i7 + ":";
        }
        if (i6 > 9) {
            str = str2 + i6 + ":";
        } else if (i6 > 0) {
            str = str2 + "0" + i6 + ":";
        } else {
            str = str2 + "00:";
        }
        if (i4 > 9) {
            return str + i4;
        }
        if (i4 <= 0) {
            return str + TarConstants.VERSION_POSIX;
        }
        return str + "0" + i4;
    }

    public static String getFileSizeDescription(long j2) {
        StringBuffer stringBuffer = new StringBuffer();
        DecimalFormat decimalFormat = new DecimalFormat("###.0");
        if (j2 >= 1073741824) {
            stringBuffer.append(decimalFormat.format(j2 / 1.073741824E9d));
            stringBuffer.append("G");
        } else if (j2 >= 1048576) {
            stringBuffer.append(decimalFormat.format(j2 / 1048576.0d));
            stringBuffer.append("M");
        } else if (j2 >= 1024) {
            stringBuffer.append(decimalFormat.format(j2 / 1024.0d));
            stringBuffer.append("K");
        } else if (j2 < 1024) {
            if (j2 <= 0) {
                stringBuffer.append("0B");
            } else {
                stringBuffer.append((int) j2);
                stringBuffer.append("B");
            }
        }
        return stringBuffer.toString();
    }

    public static int getIntTime(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        String[] strArrSplit = str.split(":");
        if (strArrSplit.length < 3) {
            return 0;
        }
        return ((Integer.valueOf(strArrSplit[0]).intValue() * 3600) + (Integer.valueOf(strArrSplit[1]).intValue() * 60) + Integer.valueOf(strArrSplit[2]).intValue()) * 1000;
    }

    public static String getStringTime(int i2) {
        StringBuilder sb = new StringBuilder();
        java.util.Formatter formatter = new java.util.Formatter(sb, Locale.getDefault());
        int i3 = i2 / 1000;
        int i4 = (i3 / 60) % 60;
        sb.setLength(0);
        return formatter.format("%02d:%02d:%02d", Integer.valueOf(i3 / 3600), Integer.valueOf(i4), Integer.valueOf(i3 % 60)).toString();
    }
}
