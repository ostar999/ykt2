package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.provider.Settings;
import androidx.annotation.NonNull;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes2.dex */
public final class TimeUtils {
    private static final ThreadLocal<Map<String, SimpleDateFormat>> SDF_THREAD_LOCAL = new ThreadLocal<Map<String, SimpleDateFormat>>() { // from class: com.blankj.utilcode.util.TimeUtils.1
        @Override // java.lang.ThreadLocal
        public Map<String, SimpleDateFormat> initialValue() {
            return new HashMap();
        }
    };
    private static final String[] CHINESE_ZODIAC = {"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};
    private static final int[] ZODIAC_FLAGS = {20, 19, 21, 21, 21, 22, 23, 23, 23, 24, 23, 22};
    private static final String[] ZODIAC = {"水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"};

    private TimeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static long date2Millis(Date date) {
        return date.getTime();
    }

    public static String date2String(Date date) {
        return date2String(date, getDefaultFormat());
    }

    public static String getChineseWeek(String str) {
        return getChineseWeek(string2Date(str, getDefaultFormat()));
    }

    public static String getChineseZodiac(String str) {
        return getChineseZodiac(string2Date(str, getDefaultFormat()));
    }

    public static Date getDate(long j2, long j3, int i2) {
        return millis2Date(j2 + timeSpan2Millis(j3, i2));
    }

    public static Date getDateByNow(long j2, int i2) {
        return getDate(getNowMills(), j2, i2);
    }

    private static SimpleDateFormat getDefaultFormat() {
        return getSafeDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public static String getFitTimeSpan(String str, String str2, int i2) {
        return millis2FitTimeSpan(string2Millis(str, getDefaultFormat()) - string2Millis(str2, getDefaultFormat()), i2);
    }

    public static String getFitTimeSpanByNow(String str, int i2) {
        return getFitTimeSpan(str, getNowString(), getDefaultFormat(), i2);
    }

    public static String getFriendlyTimeSpanByNow(String str) {
        return getFriendlyTimeSpanByNow(str, getDefaultFormat());
    }

    public static long getMillis(long j2, long j3, int i2) {
        return j2 + timeSpan2Millis(j3, i2);
    }

    public static long getMillisByNow(long j2, int i2) {
        return getMillis(getNowMills(), j2, i2);
    }

    public static Date getNowDate() {
        return new Date();
    }

    public static long getNowMills() {
        return System.currentTimeMillis();
    }

    public static String getNowString() {
        return millis2String(System.currentTimeMillis(), getDefaultFormat());
    }

    @SuppressLint({"SimpleDateFormat"})
    public static SimpleDateFormat getSafeDateFormat(String str) {
        Map<String, SimpleDateFormat> map = SDF_THREAD_LOCAL.get();
        SimpleDateFormat simpleDateFormat = map.get(str);
        if (simpleDateFormat != null) {
            return simpleDateFormat;
        }
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(str);
        map.put(str, simpleDateFormat2);
        return simpleDateFormat2;
    }

    public static String getString(long j2, long j3, int i2) {
        return getString(j2, getDefaultFormat(), j3, i2);
    }

    public static String getStringByNow(long j2, int i2) {
        return getStringByNow(j2, getDefaultFormat(), i2);
    }

    public static long getTimeSpan(String str, String str2, int i2) {
        return getTimeSpan(str, str2, getDefaultFormat(), i2);
    }

    public static long getTimeSpanByNow(String str, int i2) {
        return getTimeSpan(str, getNowString(), getDefaultFormat(), i2);
    }

    public static String getUSWeek(String str) {
        return getUSWeek(string2Date(str, getDefaultFormat()));
    }

    public static int getValueByCalendarField(int i2) {
        return Calendar.getInstance().get(i2);
    }

    private static long getWeeOfToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 0);
        calendar.set(13, 0);
        calendar.set(12, 0);
        calendar.set(14, 0);
        return calendar.getTimeInMillis();
    }

    public static String getZodiac(String str) {
        return getZodiac(string2Date(str, getDefaultFormat()));
    }

    public static boolean isAm() {
        return Calendar.getInstance().get(9) == 0;
    }

    public static boolean isLeapYear(String str) {
        return isLeapYear(string2Date(str, getDefaultFormat()));
    }

    public static boolean isPm() {
        return !isAm();
    }

    public static boolean isToday(String str) {
        return isToday(string2Millis(str, getDefaultFormat()));
    }

    public static boolean isUsingNetworkProvidedTime() {
        return Settings.Global.getInt(Utils.getApp().getContentResolver(), "auto_time", 0) == 1;
    }

    public static Date millis2Date(long j2) {
        return new Date(j2);
    }

    public static String millis2FitTimeSpan(long j2, int i2) {
        if (i2 <= 0) {
            return null;
        }
        int iMin = Math.min(i2, 5);
        String[] strArr = {"天", "小时", "分钟", "秒", "毫秒"};
        if (j2 == 0) {
            return 0 + strArr[iMin - 1];
        }
        StringBuilder sb = new StringBuilder();
        if (j2 < 0) {
            sb.append("-");
            j2 = -j2;
        }
        int[] iArr = {86400000, 3600000, 60000, 1000, 1};
        for (int i3 = 0; i3 < iMin; i3++) {
            int i4 = iArr[i3];
            if (j2 >= i4) {
                long j3 = j2 / i4;
                j2 -= i4 * j3;
                sb.append(j3);
                sb.append(strArr[i3]);
            }
        }
        return sb.toString();
    }

    public static String millis2String(long j2) {
        return millis2String(j2, getDefaultFormat());
    }

    private static long millis2TimeSpan(long j2, int i2) {
        return j2 / i2;
    }

    public static Date string2Date(String str) {
        return string2Date(str, getDefaultFormat());
    }

    public static long string2Millis(String str) {
        return string2Millis(str, getDefaultFormat());
    }

    private static long timeSpan2Millis(long j2, int i2) {
        return j2 * i2;
    }

    public static String date2String(Date date, @NonNull String str) {
        return getSafeDateFormat(str).format(date);
    }

    public static String getChineseWeek(String str, @NonNull DateFormat dateFormat) {
        return getChineseWeek(string2Date(str, dateFormat));
    }

    public static String getChineseZodiac(String str, @NonNull DateFormat dateFormat) {
        return getChineseZodiac(string2Date(str, dateFormat));
    }

    public static Date getDate(String str, long j2, int i2) {
        return getDate(str, getDefaultFormat(), j2, i2);
    }

    public static String getFitTimeSpanByNow(String str, @NonNull DateFormat dateFormat, int i2) {
        return getFitTimeSpan(str, getNowString(dateFormat), dateFormat, i2);
    }

    public static String getFriendlyTimeSpanByNow(String str, @NonNull DateFormat dateFormat) {
        return getFriendlyTimeSpanByNow(string2Millis(str, dateFormat));
    }

    public static long getMillis(String str, long j2, int i2) {
        return getMillis(str, getDefaultFormat(), j2, i2);
    }

    public static String getNowString(@NonNull DateFormat dateFormat) {
        return millis2String(System.currentTimeMillis(), dateFormat);
    }

    public static String getString(long j2, @NonNull DateFormat dateFormat, long j3, int i2) {
        return millis2String(j2 + timeSpan2Millis(j3, i2), dateFormat);
    }

    public static String getStringByNow(long j2, @NonNull DateFormat dateFormat, int i2) {
        return getString(getNowMills(), dateFormat, j2, i2);
    }

    public static long getTimeSpan(String str, String str2, @NonNull DateFormat dateFormat, int i2) {
        return millis2TimeSpan(string2Millis(str, dateFormat) - string2Millis(str2, dateFormat), i2);
    }

    public static long getTimeSpanByNow(String str, @NonNull DateFormat dateFormat, int i2) {
        return getTimeSpan(str, getNowString(dateFormat), dateFormat, i2);
    }

    public static String getUSWeek(String str, @NonNull DateFormat dateFormat) {
        return getUSWeek(string2Date(str, dateFormat));
    }

    public static String getZodiac(String str, @NonNull DateFormat dateFormat) {
        return getZodiac(string2Date(str, dateFormat));
    }

    public static boolean isLeapYear(String str, @NonNull DateFormat dateFormat) {
        return isLeapYear(string2Date(str, dateFormat));
    }

    public static boolean isPm(String str) {
        return !isAm(str);
    }

    public static boolean isToday(String str, @NonNull DateFormat dateFormat) {
        return isToday(string2Millis(str, dateFormat));
    }

    public static String millis2String(long j2, @NonNull String str) {
        return millis2String(j2, getSafeDateFormat(str));
    }

    public static Date string2Date(String str, @NonNull String str2) {
        return string2Date(str, getSafeDateFormat(str2));
    }

    public static long string2Millis(String str, @NonNull String str2) {
        return string2Millis(str, getSafeDateFormat(str2));
    }

    public static String date2String(Date date, @NonNull DateFormat dateFormat) {
        return dateFormat.format(date);
    }

    public static String getChineseWeek(Date date) {
        return new SimpleDateFormat("E", Locale.CHINA).format(date);
    }

    public static String getChineseZodiac(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return CHINESE_ZODIAC[calendar.get(1) % 12];
    }

    public static Date getDate(String str, @NonNull DateFormat dateFormat, long j2, int i2) {
        return millis2Date(string2Millis(str, dateFormat) + timeSpan2Millis(j2, i2));
    }

    public static String getFitTimeSpan(String str, String str2, @NonNull DateFormat dateFormat, int i2) {
        return millis2FitTimeSpan(string2Millis(str, dateFormat) - string2Millis(str2, dateFormat), i2);
    }

    public static String getFitTimeSpanByNow(Date date, int i2) {
        return getFitTimeSpan(date, getNowDate(), i2);
    }

    public static String getFriendlyTimeSpanByNow(Date date) {
        return getFriendlyTimeSpanByNow(date.getTime());
    }

    public static long getMillis(String str, @NonNull DateFormat dateFormat, long j2, int i2) {
        return string2Millis(str, dateFormat) + timeSpan2Millis(j2, i2);
    }

    public static String getString(String str, long j2, int i2) {
        return getString(str, getDefaultFormat(), j2, i2);
    }

    public static long getTimeSpan(Date date, Date date2, int i2) {
        return millis2TimeSpan(date2Millis(date) - date2Millis(date2), i2);
    }

    public static long getTimeSpanByNow(Date date, int i2) {
        return getTimeSpan(date, new Date(), i2);
    }

    public static String getUSWeek(Date date) {
        return new SimpleDateFormat("EEEE", Locale.US).format(date);
    }

    public static int getValueByCalendarField(String str, int i2) {
        return getValueByCalendarField(string2Date(str, getDefaultFormat()), i2);
    }

    public static String getZodiac(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getZodiac(calendar.get(2) + 1, calendar.get(5));
    }

    public static boolean isAm(String str) {
        return getValueByCalendarField(str, getDefaultFormat(), 9) == 0;
    }

    public static boolean isLeapYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return isLeapYear(calendar.get(1));
    }

    public static boolean isPm(String str, @NonNull DateFormat dateFormat) {
        return !isAm(str, dateFormat);
    }

    public static boolean isToday(Date date) {
        return isToday(date.getTime());
    }

    public static String millis2String(long j2, @NonNull DateFormat dateFormat) {
        return dateFormat.format(new Date(j2));
    }

    public static Date string2Date(String str, @NonNull DateFormat dateFormat) {
        try {
            return dateFormat.parse(str);
        } catch (ParseException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static long string2Millis(String str, @NonNull DateFormat dateFormat) {
        try {
            return dateFormat.parse(str).getTime();
        } catch (ParseException e2) {
            e2.printStackTrace();
            return -1L;
        }
    }

    public static String getChineseWeek(long j2) {
        return getChineseWeek(new Date(j2));
    }

    public static Date getDate(Date date, long j2, int i2) {
        return millis2Date(date2Millis(date) + timeSpan2Millis(j2, i2));
    }

    public static String getFitTimeSpanByNow(long j2, int i2) {
        return getFitTimeSpan(j2, System.currentTimeMillis(), i2);
    }

    public static String getFriendlyTimeSpanByNow(long j2) {
        long jCurrentTimeMillis = System.currentTimeMillis() - j2;
        if (jCurrentTimeMillis < 0) {
            return String.format("%tc", Long.valueOf(j2));
        }
        if (jCurrentTimeMillis < 1000) {
            return "刚刚";
        }
        if (jCurrentTimeMillis < 60000) {
            return String.format(Locale.getDefault(), "%d秒前", Long.valueOf(jCurrentTimeMillis / 1000));
        }
        if (jCurrentTimeMillis < com.heytap.mcssdk.constant.a.f7141e) {
            return String.format(Locale.getDefault(), "%d分钟前", Long.valueOf(jCurrentTimeMillis / 60000));
        }
        long weeOfToday = getWeeOfToday();
        return j2 >= weeOfToday ? String.format("今天%tR", Long.valueOf(j2)) : j2 >= weeOfToday - 86400000 ? String.format("昨天%tR", Long.valueOf(j2)) : String.format("%tF", Long.valueOf(j2));
    }

    public static long getMillis(Date date, long j2, int i2) {
        return date2Millis(date) + timeSpan2Millis(j2, i2);
    }

    public static String getString(String str, @NonNull DateFormat dateFormat, long j2, int i2) {
        return millis2String(string2Millis(str, dateFormat) + timeSpan2Millis(j2, i2), dateFormat);
    }

    public static long getTimeSpan(long j2, long j3, int i2) {
        return millis2TimeSpan(j2 - j3, i2);
    }

    public static long getTimeSpanByNow(long j2, int i2) {
        return getTimeSpan(j2, System.currentTimeMillis(), i2);
    }

    public static String getUSWeek(long j2) {
        return getUSWeek(new Date(j2));
    }

    public static int getValueByCalendarField(String str, @NonNull DateFormat dateFormat, int i2) {
        return getValueByCalendarField(string2Date(str, dateFormat), i2);
    }

    public static boolean isAm(String str, @NonNull DateFormat dateFormat) {
        return getValueByCalendarField(str, dateFormat, 9) == 0;
    }

    public static boolean isPm(Date date) {
        return !isAm(date);
    }

    public static boolean isToday(long j2) {
        long weeOfToday = getWeeOfToday();
        return j2 >= weeOfToday && j2 < weeOfToday + 86400000;
    }

    public static String getFitTimeSpan(Date date, Date date2, int i2) {
        return millis2FitTimeSpan(date2Millis(date) - date2Millis(date2), i2);
    }

    public static String getString(Date date, long j2, int i2) {
        return getString(date, getDefaultFormat(), j2, i2);
    }

    public static int getValueByCalendarField(Date date, int i2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(i2);
    }

    public static boolean isAm(Date date) {
        return getValueByCalendarField(date, 9) == 0;
    }

    public static boolean isPm(long j2) {
        return !isAm(j2);
    }

    public static String getChineseZodiac(long j2) {
        return getChineseZodiac(millis2Date(j2));
    }

    public static String getFitTimeSpan(long j2, long j3, int i2) {
        return millis2FitTimeSpan(j2 - j3, i2);
    }

    public static String getString(Date date, @NonNull DateFormat dateFormat, long j2, int i2) {
        return millis2String(date2Millis(date) + timeSpan2Millis(j2, i2), dateFormat);
    }

    public static boolean isAm(long j2) {
        return getValueByCalendarField(j2, 9) == 0;
    }

    public static String getChineseZodiac(int i2) {
        return CHINESE_ZODIAC[i2 % 12];
    }

    public static boolean isLeapYear(long j2) {
        return isLeapYear(millis2Date(j2));
    }

    public static int getValueByCalendarField(long j2, int i2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j2);
        return calendar.get(i2);
    }

    public static String getZodiac(long j2) {
        return getZodiac(millis2Date(j2));
    }

    public static boolean isLeapYear(int i2) {
        return (i2 % 4 == 0 && i2 % 100 != 0) || i2 % 400 == 0;
    }

    public static String getZodiac(int i2, int i3) {
        String[] strArr = ZODIAC;
        int i4 = i2 - 1;
        if (i3 < ZODIAC_FLAGS[i4]) {
            i4 = (i2 + 10) % 12;
        }
        return strArr[i4];
    }
}
