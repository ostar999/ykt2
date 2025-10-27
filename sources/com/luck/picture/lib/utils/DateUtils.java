package com.luck.picture.lib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import cn.hutool.core.date.DatePattern;
import com.luck.picture.lib.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class DateUtils {

    @SuppressLint({"SimpleDateFormat"})
    private static final SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    @SuppressLint({"SimpleDateFormat"})
    private static final SimpleDateFormat sdf = new SimpleDateFormat(DatePattern.NORM_MONTH_PATTERN);

    public static String cdTime(long j2, long j3) {
        long j4 = j3 - j2;
        if (j4 > 1000) {
            return (j4 / 1000) + "秒";
        }
        return j4 + "毫秒";
    }

    public static int dateDiffer(long j2) {
        try {
            return (int) Math.abs(getCurrentTimeMillis() - j2);
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    public static String formatDurationTime(long j2) {
        Locale locale = Locale.getDefault();
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        return String.format(locale, "%02d:%02d", Long.valueOf(timeUnit.toMinutes(j2)), Long.valueOf(timeUnit.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(timeUnit.toMinutes(j2))));
    }

    public static String getCreateFileName(String str) {
        return str + sf.format(Long.valueOf(System.currentTimeMillis()));
    }

    public static long getCurrentTimeMillis() {
        String string = ValueOf.toString(Long.valueOf(System.currentTimeMillis()));
        if (string.length() > 10) {
            string = string.substring(0, 10);
        }
        return ValueOf.toLong(string);
    }

    public static String getDataFormat(Context context, long j2) {
        if (String.valueOf(j2).length() <= 10) {
            j2 *= 1000;
        }
        return isThisWeek(j2) ? context.getString(R.string.ps_current_week) : isThisMonth(j2) ? context.getString(R.string.ps_current_month) : sdf.format(Long.valueOf(j2));
    }

    public static boolean isThisMonth(long j2) {
        Date date = new Date(j2);
        SimpleDateFormat simpleDateFormat = sdf;
        return simpleDateFormat.format(date).equals(simpleDateFormat.format(new Date()));
    }

    private static boolean isThisWeek(long j2) {
        Calendar calendar = Calendar.getInstance();
        int i2 = calendar.get(3);
        calendar.setTime(new Date(j2));
        return calendar.get(3) == i2;
    }

    public static String getCreateFileName() {
        return sf.format(Long.valueOf(System.currentTimeMillis()));
    }
}
