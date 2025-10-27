package com.psychiatrygarden.utils;

import cn.hutool.core.date.DatePattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/* loaded from: classes6.dex */
public class RelativeDateFormat {
    private static final long ONE_DAY = 86400000;
    private static final String ONE_DAY_AGO = "天前";
    private static final long ONE_HOUR = 3600000;
    private static final String ONE_HOUR_AGO = "小时前";
    private static final long ONE_MINUTE = 60000;
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_SECOND_AGO = "秒前";
    private static final long ONE_WEEK = 604800000;
    private static final String ONE_YEAR_AGO = "年前";

    public static String format(String dateStr) {
        try {
            long time = new Date().getTime() - (Long.parseLong(dateStr) * 1000);
            long j2 = 1;
            if (time < 60000) {
                long seconds = toSeconds(time);
                StringBuilder sb = new StringBuilder();
                if (seconds > 0) {
                    j2 = seconds;
                }
                sb.append(j2);
                sb.append(ONE_SECOND_AGO);
                return sb.toString();
            }
            if (time < 2700000) {
                long minutes = toMinutes(time);
                StringBuilder sb2 = new StringBuilder();
                if (minutes > 0) {
                    j2 = minutes;
                }
                sb2.append(j2);
                sb2.append(ONE_MINUTE_AGO);
                return sb2.toString();
            }
            if (time >= 86400000) {
                return time < 172800000 ? "昨天" : new SimpleDateFormat(DatePattern.CHINESE_DATE_PATTERN).format(new Date(Long.parseLong(dateStr) * 1000));
            }
            long hours = toHours(time);
            StringBuilder sb3 = new StringBuilder();
            if (hours > 0) {
                j2 = hours;
            }
            sb3.append(j2);
            sb3.append(ONE_HOUR_AGO);
            return sb3.toString();
        } catch (Exception e2) {
            e2.printStackTrace();
            return dateStr.substring(0, 10);
        }
    }

    public static String formatLetter(String dateStr) {
        long j2;
        long timeInMillis;
        try {
            long time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr).getTime();
            Calendar calendar = Calendar.getInstance();
            j2 = ((calendar.get(11) * 3600) + (calendar.get(12) * 60) + calendar.get(13)) * 1000;
            timeInMillis = calendar.getTimeInMillis() - time;
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        if (timeInMillis < j2) {
            return "今天" + dateStr.substring(11, 16);
        }
        if (timeInMillis < j2 + 86400000) {
            return "昨天" + dateStr.substring(11, 16);
        }
        String strSubstring = dateStr.substring(0, 4);
        String strSubstring2 = dateStr.substring(5, 7);
        String strSubstring3 = dateStr.substring(8, 10);
        String strSubstring4 = dateStr.substring(11, 13);
        String strSubstring5 = dateStr.substring(14, 16);
        dateStr.substring(17, 19);
        return strSubstring + "年" + strSubstring2 + "月" + strSubstring3 + "日 " + strSubstring4 + ":" + strSubstring5;
    }

    public static long formatLetterTime(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:m:s").parse(dateStr).getTime();
        } catch (ParseException e2) {
            e2.printStackTrace();
            return 0L;
        }
    }

    private static long toDays(long date) {
        return toHours(date) / 24;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30;
    }

    private static long toSeconds(long date) {
        return date / 1000;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365;
    }
}
