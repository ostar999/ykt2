package com.psychiatrygarden.utils;

import android.content.Context;
import android.text.format.DateFormat;
import com.hyphenate.util.TimeInfo;
import java.util.Calendar;

/* loaded from: classes6.dex */
public class YKBDateUtils {
    /* JADX WARN: Removed duplicated region for block: B:6:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getTimestampString(android.content.Context r7, java.util.Date r8) {
        /*
            java.util.Locale r0 = java.util.Locale.getDefault()
            java.lang.String r0 = r0.getLanguage()
            java.lang.String r1 = "zh"
            boolean r0 = r0.startsWith(r1)
            long r1 = r8.getTime()
            boolean r3 = isSameDay(r1)
            java.lang.String r4 = "aa hh:mm"
            java.lang.String r5 = "hh:mm aa"
            java.lang.String r6 = "HH:mm"
            if (r3 == 0) goto L2c
            boolean r7 = is24HourFormat(r7)
            if (r7 == 0) goto L27
        L25:
            r4 = r6
            goto L86
        L27:
            if (r0 == 0) goto L2a
            goto L86
        L2a:
            r4 = r5
            goto L86
        L2c:
            boolean r1 = isYesterday(r1)
            if (r1 == 0) goto L79
            if (r0 == 0) goto L3b
            boolean r7 = is24HourFormat(r7)
            if (r7 == 0) goto L86
            goto L25
        L3b:
            boolean r7 = is24HourFormat(r7)
            java.lang.String r0 = ""
            if (r7 == 0) goto L5e
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r0)
            java.text.SimpleDateFormat r0 = new java.text.SimpleDateFormat
            java.util.Locale r1 = java.util.Locale.ENGLISH
            r0.<init>(r6, r1)
            java.lang.String r8 = r0.format(r8)
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            return r7
        L5e:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r0)
            java.text.SimpleDateFormat r0 = new java.text.SimpleDateFormat
            java.util.Locale r1 = java.util.Locale.ENGLISH
            r0.<init>(r5, r1)
            java.lang.String r8 = r0.format(r8)
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            return r7
        L79:
            if (r0 == 0) goto L81
            is24HourFormat(r7)
            java.lang.String r4 = "M月d日"
            goto L86
        L81:
            is24HourFormat(r7)
            java.lang.String r4 = "MMM dd"
        L86:
            if (r0 == 0) goto L94
            java.text.SimpleDateFormat r7 = new java.text.SimpleDateFormat
            java.util.Locale r0 = java.util.Locale.CHINESE
            r7.<init>(r4, r0)
            java.lang.String r7 = r7.format(r8)
            return r7
        L94:
            java.text.SimpleDateFormat r7 = new java.text.SimpleDateFormat
            java.util.Locale r0 = java.util.Locale.ENGLISH
            r7.<init>(r4, r0)
            java.lang.String r7 = r7.format(r8)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.utils.YKBDateUtils.getTimestampString(android.content.Context, java.util.Date):java.lang.String");
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

    public static boolean is24HourFormat(Context context) {
        return DateFormat.is24HourFormat(context);
    }

    private static boolean isSameDay(long inputTime) {
        TimeInfo todayStartAndEndTime = getTodayStartAndEndTime();
        return inputTime > todayStartAndEndTime.getStartTime() && inputTime < todayStartAndEndTime.getEndTime();
    }

    private static boolean isYesterday(long inputTime) {
        TimeInfo yesterdayStartAndEndTime = getYesterdayStartAndEndTime();
        return inputTime > yesterdayStartAndEndTime.getStartTime() && inputTime < yesterdayStartAndEndTime.getEndTime();
    }
}
