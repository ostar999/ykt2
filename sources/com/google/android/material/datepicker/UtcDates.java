package com.google.android.material.datepicker;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.icu.text.DateFormat;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.hutool.core.date.DatePattern;
import com.google.android.material.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes3.dex */
class UtcDates {
    static final String UTC = "UTC";
    static AtomicReference<TimeSource> timeSourceRef = new AtomicReference<>();

    private UtcDates() {
    }

    public static long canonicalYearMonthDay(long j2) {
        Calendar utcCalendar = getUtcCalendar();
        utcCalendar.setTimeInMillis(j2);
        return getDayCopy(utcCalendar).getTimeInMillis();
    }

    private static int findCharactersInDateFormatPattern(@NonNull String str, @NonNull String str2, int i2, int i3) {
        while (i3 >= 0 && i3 < str.length() && str2.indexOf(str.charAt(i3)) == -1) {
            if (str.charAt(i3) == '\'') {
                do {
                    i3 += i2;
                    if (i3 < 0 || i3 >= str.length()) {
                        break;
                    }
                } while (str.charAt(i3) != '\'');
            }
            i3 += i2;
        }
        return i3;
    }

    @TargetApi(24)
    public static DateFormat getAbbrMonthDayFormat(Locale locale) {
        return getAndroidFormat("MMMd", locale);
    }

    @TargetApi(24)
    public static DateFormat getAbbrMonthWeekdayDayFormat(Locale locale) {
        return getAndroidFormat("MMMEd", locale);
    }

    @TargetApi(24)
    private static DateFormat getAndroidFormat(String str, Locale locale) {
        DateFormat instanceForSkeleton = DateFormat.getInstanceForSkeleton(str, locale);
        instanceForSkeleton.setTimeZone(getUtcAndroidTimeZone());
        return instanceForSkeleton;
    }

    public static Calendar getDayCopy(Calendar calendar) {
        Calendar utcCalendarOf = getUtcCalendarOf(calendar);
        Calendar utcCalendar = getUtcCalendar();
        utcCalendar.set(utcCalendarOf.get(1), utcCalendarOf.get(2), utcCalendarOf.get(5));
        return utcCalendar;
    }

    private static java.text.DateFormat getFormat(int i2, Locale locale) {
        java.text.DateFormat dateInstance = java.text.DateFormat.getDateInstance(i2, locale);
        dateInstance.setTimeZone(getTimeZone());
        return dateInstance;
    }

    public static java.text.DateFormat getFullFormat() {
        return getFullFormat(Locale.getDefault());
    }

    public static java.text.DateFormat getMediumFormat() {
        return getMediumFormat(Locale.getDefault());
    }

    public static java.text.DateFormat getMediumNoYear() {
        return getMediumNoYear(Locale.getDefault());
    }

    public static SimpleDateFormat getSimpleFormat(String str) {
        return getSimpleFormat(str, Locale.getDefault());
    }

    public static SimpleDateFormat getTextInputFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(((SimpleDateFormat) java.text.DateFormat.getDateInstance(3, Locale.getDefault())).toPattern().replaceAll("\\s+", ""), Locale.getDefault());
        simpleDateFormat.setTimeZone(getTimeZone());
        simpleDateFormat.setLenient(false);
        return simpleDateFormat;
    }

    public static String getTextInputHint(Resources resources, SimpleDateFormat simpleDateFormat) throws Resources.NotFoundException {
        String pattern = simpleDateFormat.toPattern();
        String string = resources.getString(R.string.mtrl_picker_text_input_year_abbr);
        String string2 = resources.getString(R.string.mtrl_picker_text_input_month_abbr);
        String string3 = resources.getString(R.string.mtrl_picker_text_input_day_abbr);
        if (pattern.replaceAll("[^y]", "").length() == 1) {
            pattern = pattern.replace("y", DatePattern.NORM_YEAR_PATTERN);
        }
        return pattern.replace("d", string3).replace("M", string2).replace("y", string);
    }

    public static TimeSource getTimeSource() {
        TimeSource timeSource = timeSourceRef.get();
        return timeSource == null ? TimeSource.system() : timeSource;
    }

    private static TimeZone getTimeZone() {
        return TimeZone.getTimeZone(UTC);
    }

    public static Calendar getTodayCalendar() {
        Calendar calendarNow = getTimeSource().now();
        calendarNow.set(11, 0);
        calendarNow.set(12, 0);
        calendarNow.set(13, 0);
        calendarNow.set(14, 0);
        calendarNow.setTimeZone(getTimeZone());
        return calendarNow;
    }

    @TargetApi(24)
    private static android.icu.util.TimeZone getUtcAndroidTimeZone() {
        return android.icu.util.TimeZone.getTimeZone(UTC);
    }

    public static Calendar getUtcCalendar() {
        return getUtcCalendarOf(null);
    }

    public static Calendar getUtcCalendarOf(@Nullable Calendar calendar) {
        Calendar calendar2 = Calendar.getInstance(getTimeZone());
        if (calendar == null) {
            calendar2.clear();
        } else {
            calendar2.setTimeInMillis(calendar.getTimeInMillis());
        }
        return calendar2;
    }

    @TargetApi(24)
    public static DateFormat getYearAbbrMonthDayFormat(Locale locale) {
        return getAndroidFormat("yMMMd", locale);
    }

    @TargetApi(24)
    public static DateFormat getYearAbbrMonthWeekdayDayFormat(Locale locale) {
        return getAndroidFormat("yMMMEd", locale);
    }

    @NonNull
    private static String removeYearFromDateFormatPattern(@NonNull String str) {
        int iFindCharactersInDateFormatPattern = findCharactersInDateFormatPattern(str, "yY", 1, 0);
        if (iFindCharactersInDateFormatPattern >= str.length()) {
            return str;
        }
        String str2 = "EMd";
        int iFindCharactersInDateFormatPattern2 = findCharactersInDateFormatPattern(str, "EMd", 1, iFindCharactersInDateFormatPattern);
        if (iFindCharactersInDateFormatPattern2 < str.length()) {
            str2 = "EMd,";
        }
        return str.replace(str.substring(findCharactersInDateFormatPattern(str, str2, -1, iFindCharactersInDateFormatPattern) + 1, iFindCharactersInDateFormatPattern2), " ").trim();
    }

    public static void setTimeSource(@Nullable TimeSource timeSource) {
        timeSourceRef.set(timeSource);
    }

    public static java.text.DateFormat getFullFormat(Locale locale) {
        return getFormat(0, locale);
    }

    public static java.text.DateFormat getMediumFormat(Locale locale) {
        return getFormat(2, locale);
    }

    public static java.text.DateFormat getMediumNoYear(Locale locale) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) getMediumFormat(locale);
        simpleDateFormat.applyPattern(removeYearFromDateFormatPattern(simpleDateFormat.toPattern()));
        return simpleDateFormat;
    }

    private static SimpleDateFormat getSimpleFormat(String str, Locale locale) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str, locale);
        simpleDateFormat.setTimeZone(getTimeZone());
        return simpleDateFormat;
    }
}
