package top.defaults.view;

import com.catchpig.mvvm.utils.DateUtil;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/* loaded from: classes9.dex */
class TimeUtils {
    private static SimpleDateFormat HHmm;

    public static int calculateStep(Calendar calendar, int i2) {
        return ((calendar.get(11) * 60) + calendar.get(12)) / i2;
    }

    public static int calculateStepOffset(Calendar calendar, Calendar calendar2, int i2) {
        if (!isAtStartDay(calendar, calendar2)) {
            return 0;
        }
        int i3 = calendar.get(11);
        int i4 = calendar.get(12);
        return (i3 * (60 / i2)) + 0 + ((((i4 / i2) + (i4 % i2 > 0 ? 1 : 0)) * i2) / i2);
    }

    public static int compare(Calendar calendar, Calendar calendar2) {
        long timeInMillis = calendar.getTimeInMillis();
        long timeInMillis2 = calendar2.getTimeInMillis();
        if (timeInMillis == timeInMillis2) {
            return 0;
        }
        return timeInMillis > timeInMillis2 ? 1 : -1;
    }

    public static String date(Calendar calendar) {
        StringBuilder sb = new StringBuilder(calendar.getDisplayName(2, 1, Locale.getDefault()));
        if (Locale.getDefault().equals(Locale.CHINA)) {
            sb.append(calendar.get(5));
            sb.append("日");
        } else {
            sb.append(" ");
            sb.append(calendar.get(5));
        }
        sb.append(" ");
        sb.append(calendar.getDisplayName(7, 1, Locale.getDefault()));
        return sb.toString();
    }

    public static int daySwitchesBetween(Calendar calendar, Calendar calendar2) {
        long timeInMillis = calendar.getTimeInMillis();
        long timeInMillis2 = calendar2.getTimeInMillis();
        return ((int) ((timeInMillis2 - timeInMillis) / 86400000)) + (tomorrowOClock(calendar) - timeInMillis < tomorrowOClock(calendar2) - timeInMillis2 ? 1 : 0);
    }

    public static GregorianCalendar get0Day() {
        return new GregorianCalendar(1, 0, 1);
    }

    public static Calendar getCurrentTime() {
        return Calendar.getInstance();
    }

    private static boolean isAtStartDay(Calendar calendar, Calendar calendar2) {
        return calendar2.get(1) == calendar.get(1) && calendar2.get(6) == calendar.get(6);
    }

    public static boolean isToday(Calendar calendar) {
        Calendar currentTime = getCurrentTime();
        return currentTime.get(1) == calendar.get(1) && currentTime.get(6) == calendar.get(6);
    }

    public static String time(Calendar calendar) {
        if (HHmm == null) {
            HHmm = new SimpleDateFormat(DateUtil.TIME_FORMAT_WITH_HM, Locale.getDefault());
        }
        return HHmm.format(calendar.getTime());
    }

    private static long todayOClock(Calendar calendar) {
        Calendar calendar2 = (Calendar) calendar.clone();
        calendar2.set(11, 0);
        calendar2.set(12, 0);
        calendar2.set(13, 0);
        calendar2.set(14, 0);
        return calendar2.getTimeInMillis();
    }

    private static long tomorrowOClock(Calendar calendar) {
        Calendar calendar2 = (Calendar) calendar.clone();
        calendar2.set(11, 0);
        calendar2.set(12, 0);
        calendar2.set(13, 0);
        calendar2.set(14, 0);
        if (calendar2.getTimeInMillis() < calendar.getTimeInMillis()) {
            calendar2.add(6, 1);
        }
        return calendar2.getTimeInMillis();
    }

    public static int calculateStep(Calendar calendar, Calendar calendar2, int i2) {
        int i3;
        int i4;
        if (isAtStartDay(calendar, calendar2)) {
            i3 = calendar2.get(11) - calendar.get(11);
            if (i3 == 0) {
                i4 = calendar2.get(12) - calendar.get(12);
            } else {
                i4 = calendar2.get(12);
            }
        } else {
            i3 = calendar2.get(11);
            i4 = calendar2.get(12);
        }
        return ((i3 * 60) + i4) / i2;
    }
}
