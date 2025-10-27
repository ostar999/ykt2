package com.hyphenate.util;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes4.dex */
public class DateUtils {
    private static final long INTERVAL_IN_MILLISECONDS = 30000;

    @SuppressLint({"SimpleDateFormat"})
    public static Date StringToDate(String str, String str2) {
        try {
            return new SimpleDateFormat(str2).parse(str);
        } catch (ParseException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static TimeInfo getBeforeYesterdayStartAndEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(5, -2);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        long time = calendar.getTime().getTime();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(5, -2);
        calendar2.set(11, 23);
        calendar2.set(12, 59);
        calendar2.set(13, 59);
        calendar2.set(14, 999);
        long time2 = calendar2.getTime().getTime();
        TimeInfo timeInfo = new TimeInfo();
        timeInfo.setStartTime(time);
        timeInfo.setEndTime(time2);
        return timeInfo;
    }

    public static TimeInfo getCurrentMonthStartAndEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(5, 1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        long time = calendar.getTime().getTime();
        long time2 = Calendar.getInstance().getTime().getTime();
        TimeInfo timeInfo = new TimeInfo();
        timeInfo.setStartTime(time);
        timeInfo.setEndTime(time2);
        return timeInfo;
    }

    public static TimeInfo getLastMonthStartAndEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(2, -1);
        calendar.set(5, 1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        long time = calendar.getTime().getTime();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(2, -1);
        calendar2.set(5, 1);
        calendar2.set(11, 23);
        calendar2.set(12, 59);
        calendar2.set(13, 59);
        calendar2.set(14, 999);
        calendar2.roll(5, -1);
        long time2 = calendar2.getTime().getTime();
        TimeInfo timeInfo = new TimeInfo();
        timeInfo.setStartTime(time);
        timeInfo.setEndTime(time2);
        return timeInfo;
    }

    public static String getTimestampStr() {
        return Long.toString(System.currentTimeMillis());
    }

    public static String getTimestampString(Date date) {
        boolean zStartsWith = Locale.getDefault().getLanguage().startsWith("zh");
        long time = date.getTime();
        String str = "hh:mm aa";
        if (isSameDay(time)) {
            if (zStartsWith) {
                str = "aa hh:mm";
            }
        } else if (!isYesterday(time)) {
            str = zStartsWith ? "M月d日aa hh:mm" : "MMM dd hh:mm aa";
        } else {
            if (!zStartsWith) {
                return "Yesterday " + new SimpleDateFormat("hh:mm aa", Locale.ENGLISH).format(date);
            }
            str = "昨天aa hh:mm";
        }
        return zStartsWith ? new SimpleDateFormat(str, Locale.CHINESE).format(date) : new SimpleDateFormat(str, Locale.ENGLISH).format(date);
    }

    public static TimeInfo getTodayStartAndEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        long time = calendar.getTime().getTime();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(11, 23);
        calendar2.set(12, 59);
        calendar2.set(13, 59);
        calendar2.set(14, 999);
        long time2 = calendar2.getTime().getTime();
        TimeInfo timeInfo = new TimeInfo();
        timeInfo.setStartTime(time);
        timeInfo.setEndTime(time2);
        return timeInfo;
    }

    public static TimeInfo getYesterdayStartAndEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(5, -1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        long time = calendar.getTime().getTime();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(5, -1);
        calendar2.set(11, 23);
        calendar2.set(12, 59);
        calendar2.set(13, 59);
        calendar2.set(14, 999);
        long time2 = calendar2.getTime().getTime();
        TimeInfo timeInfo = new TimeInfo();
        timeInfo.setStartTime(time);
        timeInfo.setEndTime(time2);
        return timeInfo;
    }

    public static boolean isCloseEnough(long j2, long j3) {
        long j4 = j2 - j3;
        if (j4 < 0) {
            j4 = -j4;
        }
        return j4 < 30000;
    }

    private static boolean isSameDay(long j2) {
        TimeInfo todayStartAndEndTime = getTodayStartAndEndTime();
        return j2 > todayStartAndEndTime.getStartTime() && j2 < todayStartAndEndTime.getEndTime();
    }

    private static boolean isYesterday(long j2) {
        TimeInfo yesterdayStartAndEndTime = getYesterdayStartAndEndTime();
        return j2 > yesterdayStartAndEndTime.getStartTime() && j2 < yesterdayStartAndEndTime.getEndTime();
    }

    @SuppressLint({"DefaultLocale"})
    public static String toTime(int i2) {
        int i3 = i2 / 1000;
        int i4 = i3 / 60;
        if (i4 >= 60) {
            int i5 = i4 / 60;
            i4 %= 60;
        }
        return String.format("%02d:%02d", Integer.valueOf(i4), Integer.valueOf(i3 % 60));
    }

    @SuppressLint({"DefaultLocale"})
    public static String toTimeBySecond(int i2) {
        int i3 = i2 / 60;
        if (i3 >= 60) {
            int i4 = i3 / 60;
            i3 %= 60;
        }
        return String.format("%02d:%02d", Integer.valueOf(i3), Integer.valueOf(i2 % 60));
    }
}
