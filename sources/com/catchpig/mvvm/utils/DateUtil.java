package com.catchpig.mvvm.utils;

import android.util.Log;
import com.catchpig.mvvm.R;
import com.catchpig.utils.LogUtils;
import com.catchpig.utils.manager.ContextManager;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes2.dex */
public class DateUtil {
    public static final String TAG = "DateUtil";
    public static final String TIME_FORMAT_MONTH_DAY = "MM-dd";
    public static final String TIME_FORMAT_MONTH_DAY_HM = "MM-dd HH:mm";
    public static final String TIME_FORMAT_WITH_HM = "HH:mm";
    public static final String TIME_FORMAT_WITH_HMS = "HH:mm:ss";
    public static final String TIME_FORMAT_WITH_NORMAL = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT_WITH_TIME_ZONE = "yyyy-MM-dd HH:mm:ss Z";
    public static final String TIME_FORMAT_WITH_YMDHM = "yyyy-MM-dd HH:mm";
    public static final String TIME_FORMAT_YMDHMS = "yyyy/MM/dd HH:mm:ss";
    public static final String TIME_FORMAT_YMDHMSNO = "yyyyMMddHHmmss";
    public static final String TIME_FORMAT_YMDHMSSS = "yyyyMMddHHmmssSSS";
    public static final String TIME_FORMAT_YMDHM_SSS = "yyyyMMddHHmmss-SSS";
    public static final String TIME_FROMAT_CHINESE = "yyyy年MM月";
    public static final String TIME_FROMAT_DAY = "yyyy-MM-dd";
    public static final String TIME_FROMAT_DAY_WITH_SLASH = "yyyy/MM/dd";

    public static void DateUtil() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }

    public static String getAfterDate(int i2, long j2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            simpleDateFormat.setLenient(false);
            Date date = j2 == 0 ? new Date(simpleDateFormat.parse(getDateWithFormat("yyyy-MM-dd")).getTime()) : new Date(j2);
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(date);
            int i3 = gregorianCalendar.get(1);
            int i4 = gregorianCalendar.get(2);
            int i5 = gregorianCalendar.get(5) + i2;
            gregorianCalendar.set(1, i3);
            gregorianCalendar.set(2, i4);
            gregorianCalendar.set(5, i5);
            return new Date(gregorianCalendar.getTimeInMillis()).toString();
        } catch (Exception e2) {
            LogUtils.INSTANCE.getInstance().e(TAG, e2.getMessage());
            throw new RuntimeException("日期转换错误");
        }
    }

    public static String getDateFormat(String str, java.util.Date date) {
        return new SimpleDateFormat(str).format(date);
    }

    public static String getDateFromMs(long j2, String str) {
        if (str == null || str.equals("")) {
            str = "yyyy-MM-dd HH:mm:ss";
        }
        Calendar calendar = Calendar.getInstance();
        java.util.Date date = new java.util.Date(j2);
        calendar.getTimeZone().getRawOffset();
        return new SimpleDateFormat(str).format(date);
    }

    public static String getDateFromSecond(long j2, String str) {
        if (str == null || str.equals("")) {
            str = "yyyy-MM-dd HH:mm:ss";
        }
        return getDateFromMs(j2 * 1000, str);
    }

    public static String getDateWithFormat(String str) {
        Calendar calendar = Calendar.getInstance();
        java.util.Date time = calendar.getTime();
        calendar.getTimeZone().getRawOffset();
        return new SimpleDateFormat(str).format(time);
    }

    public static String getDayHourSecondMsFromMs(long j2) {
        Integer num = 1000;
        Integer numValueOf = Integer.valueOf(Integer.valueOf(Integer.valueOf(num.intValue() * 60).intValue() * 60).intValue() * 24);
        Long lValueOf = Long.valueOf(j2 / numValueOf.intValue());
        Long lValueOf2 = Long.valueOf((j2 - (lValueOf.longValue() * numValueOf.intValue())) / r2.intValue());
        Long lValueOf3 = Long.valueOf(((j2 - (lValueOf.longValue() * numValueOf.intValue())) - (lValueOf2.longValue() * r2.intValue())) / r1.intValue());
        Long lValueOf4 = Long.valueOf((((j2 - (lValueOf.longValue() * numValueOf.intValue())) - (lValueOf2.longValue() * r2.intValue())) - (lValueOf3.longValue() * r1.intValue())) / num.intValue());
        Long lValueOf5 = Long.valueOf((((j2 - (lValueOf.longValue() * numValueOf.intValue())) - (lValueOf2.longValue() * r2.intValue())) - (lValueOf3.longValue() * r1.intValue())) - (lValueOf4.longValue() * num.intValue()));
        StringBuffer stringBuffer = new StringBuffer();
        if (lValueOf.longValue() > 0) {
            stringBuffer.append(lValueOf + ContextManager.INSTANCE.getInstance().getContext().getString(R.string.time_day));
        } else {
            stringBuffer.append("0" + ContextManager.INSTANCE.getInstance().getContext().getString(R.string.time_day));
        }
        if (lValueOf2.longValue() > 0) {
            stringBuffer.append(lValueOf2 + ContextManager.INSTANCE.getInstance().getContext().getString(R.string.time_hours));
        } else {
            stringBuffer.append("0" + ContextManager.INSTANCE.getInstance().getContext().getString(R.string.time_hours));
        }
        if (lValueOf3.longValue() > 0) {
            stringBuffer.append(lValueOf3 + ContextManager.INSTANCE.getInstance().getContext().getString(R.string.time_minute));
        } else {
            stringBuffer.append("0" + ContextManager.INSTANCE.getInstance().getContext().getString(R.string.time_minute));
        }
        if (lValueOf4.longValue() > 0) {
            stringBuffer.append(lValueOf4 + ContextManager.INSTANCE.getInstance().getContext().getString(R.string.time_seconds));
        } else {
            stringBuffer.append("0" + ContextManager.INSTANCE.getInstance().getContext().getString(R.string.time_seconds));
        }
        if (lValueOf5.longValue() > 0) {
            stringBuffer.append(lValueOf5 + ContextManager.INSTANCE.getInstance().getContext().getString(R.string.time_millisecond));
        }
        return stringBuffer.toString();
    }

    public static String getDayHourSecondMsFromS(long j2) {
        Integer num = 1000;
        Integer numValueOf = Integer.valueOf(Integer.valueOf(Integer.valueOf(num.intValue() * 60).intValue() * 60).intValue() * 24);
        Long lValueOf = Long.valueOf(j2 / numValueOf.intValue());
        Long lValueOf2 = Long.valueOf((j2 - (lValueOf.longValue() * numValueOf.intValue())) / r2.intValue());
        Long lValueOf3 = Long.valueOf(((j2 - (lValueOf.longValue() * numValueOf.intValue())) - (lValueOf2.longValue() * r2.intValue())) / r1.intValue());
        Long lValueOf4 = Long.valueOf((((j2 - (lValueOf.longValue() * numValueOf.intValue())) - (lValueOf2.longValue() * r2.intValue())) - (lValueOf3.longValue() * r1.intValue())) / num.intValue());
        StringBuffer stringBuffer = new StringBuffer();
        if (lValueOf2.longValue() > 0) {
            stringBuffer.append(lValueOf2 + ContextManager.INSTANCE.getInstance().getContext().getString(R.string.time_hours1));
        } else {
            stringBuffer.append(TarConstants.VERSION_POSIX + ContextManager.INSTANCE.getInstance().getContext().getString(R.string.time_hours1));
        }
        if (lValueOf3.longValue() > 0) {
            stringBuffer.append(lValueOf3 + ContextManager.INSTANCE.getInstance().getContext().getString(R.string.time_minute1));
        } else {
            stringBuffer.append(TarConstants.VERSION_POSIX + ContextManager.INSTANCE.getInstance().getContext().getString(R.string.time_minute1));
        }
        if (lValueOf4.longValue() > 0) {
            stringBuffer.append(lValueOf4 + ContextManager.INSTANCE.getInstance().getContext().getString(R.string.time_seconds));
        } else {
            stringBuffer.append(TarConstants.VERSION_POSIX + ContextManager.INSTANCE.getInstance().getContext().getString(R.string.time_seconds));
        }
        return stringBuffer.toString();
    }

    public static String getDayHourSecondMsFromSecond(long j2) {
        return getDayHourSecondMsFromMs(j2 * 1000);
    }

    public static long getTimeFormStrFormat(String str, String str2) {
        try {
            return new SimpleDateFormat(str).parse(str2).getTime();
        } catch (ParseException e2) {
            LogUtils.INSTANCE.getInstance().e(TAG, e2.toString());
            return 0L;
        }
    }

    public static String getTimeInMillis() {
        return String.valueOf(getTimeInMillisLong());
    }

    public static long getTimeInMillisLong() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static String getTimeInSecond() {
        return String.valueOf(getTimeInMillisLong() / 1000);
    }

    public static long getTimeInSecondLong() {
        return getTimeInMillisLong() / 1000;
    }

    public static String getTimeTowDay(long j2) {
        return sameDate(j2, getTimeInMillisLong()) ? ContextManager.INSTANCE.getInstance().getContext().getString(R.string.time_today) : sameDate(j2, getTimeInMillisLong() - 86400000) ? ContextManager.INSTANCE.getInstance().getContext().getString(R.string.time_yesterday) : isSameWeek(getDateFromMs(j2, "yyyy-MM-dd"), getDateFromSecond(getTimeInMillisLong(), "yyyy-MM-dd")) ? getWeek(j2 * 1000) : getDateFromMs(j2, "yyyy-MM-dd");
    }

    private static String getWeek(long j2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j2);
        switch (calendar.get(7)) {
            case 1:
                return ContextManager.INSTANCE.getInstance().getContext().getString(R.string.time_sunday);
            case 2:
                return ContextManager.INSTANCE.getInstance().getContext().getString(R.string.time_monday);
            case 3:
                return ContextManager.INSTANCE.getInstance().getContext().getString(R.string.time_tuesday);
            case 4:
                return ContextManager.INSTANCE.getInstance().getContext().getString(R.string.time_wednesday);
            case 5:
                return ContextManager.INSTANCE.getInstance().getContext().getString(R.string.time_thursday);
            case 6:
                return ContextManager.INSTANCE.getInstance().getContext().getString(R.string.time_friday);
            default:
                return ContextManager.INSTANCE.getInstance().getContext().getString(R.string.time_saturday);
        }
    }

    public static boolean isInTime(String str, String str2) {
        if (str == null || !str.contains("-") || !str.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + str);
        }
        if (str2 == null || !str2.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + str2);
        }
        String[] strArrSplit = str.split("-");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT_WITH_HM);
        try {
            long time = simpleDateFormat.parse(str2).getTime();
            long time2 = simpleDateFormat.parse(strArrSplit[0]).getTime();
            long time3 = simpleDateFormat.parse(strArrSplit[1]).getTime();
            if (strArrSplit[1].equals("00:00")) {
                strArrSplit[1] = "24:00";
            }
            return time3 < time2 ? time < time3 || time >= time2 : time >= time2 && time < time3;
        } catch (ParseException e2) {
            e2.printStackTrace();
            throw new IllegalArgumentException("Illegal Argument arg:" + str);
        }
    }

    public static boolean isSameWeek(String str, String str2) throws ParseException {
        Calendar calendar;
        Calendar calendar2;
        int i2;
        boolean z2;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = simpleDateFormat.parse(str);
            java.util.Date date2 = simpleDateFormat.parse(str2);
            calendar = Calendar.getInstance();
            calendar2 = Calendar.getInstance();
            calendar.setFirstDayOfWeek(2);
            calendar2.setFirstDayOfWeek(2);
            calendar.setTime(date);
            calendar2.setTime(date2);
            i2 = calendar.get(1) - calendar2.get(1);
            z2 = calendar.get(3) == calendar2.get(3);
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        if (i2 == 0) {
            return z2;
        }
        if (i2 == 1 && calendar2.get(2) == 11) {
            return z2;
        }
        if (i2 == -1) {
            if (calendar.get(2) == 11) {
                return z2;
            }
        }
        return false;
    }

    public static java.util.Date rollDay(java.util.Date date, int i2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, i2);
        return calendar.getTime();
    }

    public static boolean sameDate(long j2, long j3) {
        String dateFromMs = getDateFromMs(j2, "yyyy-MM-dd");
        String dateFromMs2 = getDateFromMs(j3, "yyyy-MM-dd");
        Log.v("Tag", "是否同天" + dateFromMs + " " + dateFromMs2);
        return dateFromMs.equals(dateFromMs2);
    }

    public static String[] secToTimes(long j2) {
        String[] strArr = new String[3];
        if (j2 <= 0) {
            strArr[0] = TarConstants.VERSION_POSIX;
            strArr[1] = TarConstants.VERSION_POSIX;
            strArr[2] = TarConstants.VERSION_POSIX;
            return strArr;
        }
        int i2 = (int) (j2 / 60);
        if (i2 < 60) {
            strArr[0] = TarConstants.VERSION_POSIX;
            strArr[1] = unitFormat(i2);
            strArr[2] = unitFormat((int) (j2 % 60));
        } else {
            int i3 = i2 / 60;
            if (i3 > 99) {
                strArr[0] = "99";
                strArr[1] = "59";
                strArr[2] = "59";
                return strArr;
            }
            strArr[0] = unitFormat(i3);
            strArr[1] = unitFormat(i2 % 60);
            strArr[2] = unitFormat((int) ((j2 - (i3 * 3600)) - (r1 * 60)));
        }
        return strArr;
    }

    public static String second2Time(long j2) {
        if (j2 < 0) {
            return "0'00''";
        }
        long j3 = j2 / 3600;
        long j4 = (j2 % 3600) / 60;
        long j5 = j2 % 60;
        if (j3 > 0) {
            j4 += j3 * 60;
        }
        return j4 + "'" + j5 + "''";
    }

    public static String second2Time1(int i2) {
        if (i2 <= 0) {
            return "00:00:00";
        }
        int i3 = i2 / 3600;
        int i4 = i2 - (i3 * 3600);
        int i5 = i4 / 60;
        return String.format("%02d:%02d:%02d", Integer.valueOf(i3), Integer.valueOf(i5), Integer.valueOf(i4 - (i5 * 60)));
    }

    public static String second2Time2(int i2) {
        if (i2 <= 0) {
            return "00:00:00";
        }
        int i3 = i2 / 3600;
        int i4 = i2 - (i3 * 3600);
        int i5 = i4 / 60;
        return String.format("%02d:%02d:%02d", Integer.valueOf(i3), Integer.valueOf(i5), Integer.valueOf(i4 - (i5 * 60)));
    }

    public static String second2Time3(int i2) {
        return i2 <= 60 ? "1" : String.valueOf(i2 / 60);
    }

    public static String second2Time5(int i2) {
        if (i2 < 0) {
            return "00:00";
        }
        long j2 = i2 / 3600;
        long j3 = (i2 % 3600) / 60;
        long j4 = i2 % 60;
        if (j2 > 0) {
            j3 += j2 * 60;
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (j3 < 10) {
            stringBuffer.append("0");
        }
        stringBuffer.append(j3);
        stringBuffer.append(":");
        if (j4 < 10) {
            stringBuffer.append("0");
        }
        stringBuffer.append(j4);
        return stringBuffer.toString();
    }

    public static String second2Time6(int i2) {
        if (i2 <= 0) {
            return "00'00''";
        }
        int i3 = i2 / 3600;
        int i4 = i2 - (i3 * 3600);
        int i5 = i4 / 60;
        int i6 = i4 - (i5 * 60);
        StringBuffer stringBuffer = new StringBuffer();
        if (i3 > 0) {
            stringBuffer.append(i3 + ":");
        }
        stringBuffer.append(i5 + "'");
        stringBuffer.append(i6 + "''");
        return stringBuffer.toString();
    }

    private static String unitFormat(int i2) {
        if (i2 < 0 || i2 >= 10) {
            return "" + i2;
        }
        return "0" + Integer.toString(i2);
    }

    public static String getDateWithFormat(String str, long j2) {
        Calendar calendar = Calendar.getInstance();
        calendar.getTimeZone().getRawOffset();
        calendar.setTimeInMillis(j2);
        return new SimpleDateFormat(str).format(calendar.getTime());
    }

    public static String getDateWithFormat(String str, String str2) {
        Calendar calendar = Calendar.getInstance();
        calendar.getTimeZone().getRawOffset();
        calendar.setTimeInMillis(getTimeFormStrFormat("yyyyMMddHHmmss", str2));
        return new SimpleDateFormat(str).format(calendar.getTime());
    }
}
