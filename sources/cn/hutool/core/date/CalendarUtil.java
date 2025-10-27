package cn.hutool.core.date;

import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.core.convert.NumberChineseFormatter;
import cn.hutool.core.date.DateModifier;
import cn.hutool.core.date.format.DateParser;
import cn.hutool.core.date.format.FastDateParser;
import cn.hutool.core.date.format.GlobalCustomFormat;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import java.text.ParsePosition;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes.dex */
public class CalendarUtil {
    public static int age(Calendar calendar, Calendar calendar2) {
        return age(calendar.getTimeInMillis(), calendar2.getTimeInMillis());
    }

    public static Calendar beginOfDay(Calendar calendar) {
        return truncate(calendar, DateField.DAY_OF_MONTH);
    }

    public static Calendar beginOfHour(Calendar calendar) {
        return truncate(calendar, DateField.HOUR_OF_DAY);
    }

    public static Calendar beginOfMinute(Calendar calendar) {
        return truncate(calendar, DateField.MINUTE);
    }

    public static Calendar beginOfMonth(Calendar calendar) {
        return truncate(calendar, DateField.MONTH);
    }

    public static Calendar beginOfQuarter(Calendar calendar) {
        calendar.set(2, (calendar.get(DateField.MONTH.getValue()) / 3) * 3);
        calendar.set(5, 1);
        return beginOfDay(calendar);
    }

    public static Calendar beginOfSecond(Calendar calendar) {
        return truncate(calendar, DateField.SECOND);
    }

    public static Calendar beginOfWeek(Calendar calendar) {
        return beginOfWeek(calendar, true);
    }

    public static Calendar beginOfYear(Calendar calendar) {
        return truncate(calendar, DateField.YEAR);
    }

    public static Calendar calendar() {
        return Calendar.getInstance();
    }

    public static Calendar ceiling(Calendar calendar, DateField dateField) {
        return DateModifier.modify(calendar, dateField.getValue(), DateModifier.ModifyType.CEILING);
    }

    public static int compare(Calendar calendar, Calendar calendar2) {
        return CompareUtil.compare(calendar, calendar2);
    }

    public static Calendar endOfDay(Calendar calendar) {
        return ceiling(calendar, DateField.DAY_OF_MONTH);
    }

    public static Calendar endOfHour(Calendar calendar) {
        return ceiling(calendar, DateField.HOUR_OF_DAY);
    }

    public static Calendar endOfMinute(Calendar calendar) {
        return ceiling(calendar, DateField.MINUTE);
    }

    public static Calendar endOfMonth(Calendar calendar) {
        return ceiling(calendar, DateField.MONTH);
    }

    public static Calendar endOfQuarter(Calendar calendar) {
        int i2 = calendar.get(1);
        int i3 = ((calendar.get(DateField.MONTH.getValue()) / 3) * 3) + 2;
        Calendar calendar2 = Calendar.getInstance(calendar.getTimeZone());
        calendar2.set(i2, i3, Month.of(i3).getLastDay(DateUtil.isLeapYear(i2)));
        return endOfDay(calendar2);
    }

    public static Calendar endOfSecond(Calendar calendar) {
        return ceiling(calendar, DateField.SECOND);
    }

    public static Calendar endOfWeek(Calendar calendar) {
        return endOfWeek(calendar, true);
    }

    public static Calendar endOfYear(Calendar calendar) {
        return ceiling(calendar, DateField.YEAR);
    }

    public static String formatChineseDate(Calendar calendar, boolean z2) {
        StringBuilder sbBuilder = StrUtil.builder();
        String strValueOf = String.valueOf(calendar.get(1));
        int length = strValueOf.length();
        for (int i2 = 0; i2 < length; i2++) {
            sbBuilder.append(NumberChineseFormatter.numberCharToChinese(strValueOf.charAt(i2), false));
        }
        sbBuilder.append((char) 24180);
        sbBuilder.append(NumberChineseFormatter.formatThousand(calendar.get(2) + 1, false));
        sbBuilder.append((char) 26376);
        sbBuilder.append(NumberChineseFormatter.formatThousand(calendar.get(5), false));
        sbBuilder.append((char) 26085);
        String strReplace = sbBuilder.toString().replace((char) 38646, (char) 12295);
        sbBuilder.delete(0, sbBuilder.length());
        sbBuilder.append(strReplace);
        if (z2) {
            sbBuilder.append(NumberChineseFormatter.formatThousand(calendar.get(11), false));
            sbBuilder.append((char) 26102);
            sbBuilder.append(NumberChineseFormatter.formatThousand(calendar.get(12), false));
            sbBuilder.append((char) 20998);
            sbBuilder.append(NumberChineseFormatter.formatThousand(calendar.get(13), false));
            sbBuilder.append((char) 31186);
        }
        return sbBuilder.toString();
    }

    public static int getBeginValue(Calendar calendar, DateField dateField) {
        return getBeginValue(calendar, dateField.getValue());
    }

    public static int getEndValue(Calendar calendar, DateField dateField) {
        return getEndValue(calendar, dateField.getValue());
    }

    public static boolean isAM(Calendar calendar) {
        return calendar.get(9) == 0;
    }

    public static boolean isPM(Calendar calendar) {
        return 1 == calendar.get(9);
    }

    public static boolean isSameDay(Calendar calendar, Calendar calendar2) {
        if (calendar == null || calendar2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return calendar.get(6) == calendar2.get(6) && calendar.get(1) == calendar2.get(1) && calendar.get(0) == calendar2.get(0);
    }

    public static boolean isSameInstant(Calendar calendar, Calendar calendar2) {
        return calendar == null ? calendar2 == null : calendar2 != null && calendar.getTimeInMillis() == calendar2.getTimeInMillis();
    }

    public static boolean isSameMonth(Calendar calendar, Calendar calendar2) {
        if (calendar == null || calendar2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return calendar.get(1) == calendar2.get(1) && calendar.get(2) == calendar2.get(2) && calendar.get(0) == calendar2.get(0);
    }

    public static boolean isSameWeek(Calendar calendar, Calendar calendar2, boolean z2) {
        if (calendar == null || calendar2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar calendar3 = (Calendar) calendar.clone();
        Calendar calendar4 = (Calendar) calendar2.clone();
        if (z2) {
            calendar3.setFirstDayOfWeek(2);
            calendar3.set(7, 2);
            calendar4.setFirstDayOfWeek(2);
            calendar4.set(7, 2);
        } else {
            calendar3.setFirstDayOfWeek(1);
            calendar3.set(7, 1);
            calendar4.setFirstDayOfWeek(1);
            calendar4.set(7, 1);
        }
        return isSameDay(calendar3, calendar4);
    }

    public static Calendar parse(CharSequence charSequence, boolean z2, DateParser dateParser) {
        Calendar calendar = Calendar.getInstance(dateParser.getTimeZone(), dateParser.getLocale());
        calendar.clear();
        calendar.setLenient(z2);
        if (dateParser.parse(CharSequenceUtil.str(charSequence), new ParsePosition(0), calendar)) {
            return calendar;
        }
        return null;
    }

    public static Calendar parseByPatterns(String str, String... strArr) throws DateException {
        return parseByPatterns(str, null, strArr);
    }

    public static Calendar round(Calendar calendar, DateField dateField) {
        return DateModifier.modify(calendar, dateField.getValue(), DateModifier.ModifyType.ROUND);
    }

    public static Instant toInstant(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        return calendar.toInstant();
    }

    public static LocalDateTime toLocalDateTime(Calendar calendar) {
        return LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId());
    }

    public static Calendar truncate(Calendar calendar, DateField dateField) {
        return DateModifier.modify(calendar, dateField.getValue(), DateModifier.ModifyType.TRUNCATE);
    }

    public static LinkedHashSet<String> yearAndQuarter(long j2, long j3) {
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
        Calendar calendar = calendar(j2);
        while (j2 <= j3) {
            linkedHashSet.add(yearAndQuarter(calendar));
            calendar.add(2, 3);
            j2 = calendar.getTimeInMillis();
        }
        return linkedHashSet;
    }

    public static int age(long j2, long j3) {
        if (j2 > j3) {
            throw new IllegalArgumentException("Birthday is after dateToCompare!");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j3);
        int i2 = calendar.get(1);
        int i3 = calendar.get(2);
        int i4 = calendar.get(5);
        calendar.setTimeInMillis(j2);
        int i5 = i2 - calendar.get(1);
        if (i5 == 0) {
            return 0;
        }
        int i6 = calendar.get(2);
        if (i3 == i6) {
            if (i4 > calendar.get(5)) {
                return i5;
            }
        } else if (i3 >= i6) {
            return i5;
        }
        return i5 - 1;
    }

    public static Calendar beginOfWeek(Calendar calendar, boolean z2) {
        calendar.setFirstDayOfWeek(z2 ? 2 : 1);
        return truncate(calendar, DateField.WEEK_OF_MONTH);
    }

    public static Calendar calendar(Date date) {
        return date instanceof DateTime ? ((DateTime) date).toCalendar() : calendar(date.getTime());
    }

    public static Calendar ceiling(Calendar calendar, DateField dateField, boolean z2) {
        return DateModifier.modify(calendar, dateField.getValue(), DateModifier.ModifyType.CEILING, z2);
    }

    public static Calendar endOfWeek(Calendar calendar, boolean z2) {
        calendar.setFirstDayOfWeek(z2 ? 2 : 1);
        return ceiling(calendar, DateField.WEEK_OF_MONTH);
    }

    public static int getBeginValue(Calendar calendar, int i2) {
        return 7 == i2 ? calendar.getFirstDayOfWeek() : calendar.getActualMinimum(i2);
    }

    public static int getEndValue(Calendar calendar, int i2) {
        return 7 == i2 ? (calendar.getFirstDayOfWeek() + 6) % 7 : calendar.getActualMaximum(i2);
    }

    public static Calendar parseByPatterns(String str, Locale locale, String... strArr) throws DateException {
        return parseByPatterns(str, locale, true, strArr);
    }

    public static Calendar parseByPatterns(String str, Locale locale, boolean z2, String... strArr) throws DateException {
        if (str != null && strArr != null) {
            TimeZone timeZone = TimeZone.getDefault();
            Locale locale2 = (Locale) ObjectUtil.defaultIfNull(locale, Locale.getDefault());
            ParsePosition parsePosition = new ParsePosition(0);
            Calendar calendar = Calendar.getInstance(timeZone, locale2);
            calendar.setLenient(z2);
            for (String str2 : strArr) {
                if (GlobalCustomFormat.isCustomFormat(str2)) {
                    Date date = GlobalCustomFormat.parse(str, str2);
                    if (date != null) {
                        calendar.setTime(date);
                        return calendar;
                    }
                } else {
                    FastDateParser fastDateParser = new FastDateParser(str2, timeZone, locale2);
                    calendar.clear();
                    try {
                        if (fastDateParser.parse(str, parsePosition, calendar) && parsePosition.getIndex() == str.length()) {
                            return calendar;
                        }
                    } catch (IllegalArgumentException unused) {
                    }
                    parsePosition.setIndex(0);
                }
            }
            throw new DateException("Unable to parse the date: {}", str);
        }
        throw new IllegalArgumentException("Date and Patterns must not be null");
    }

    public static Calendar calendar(long j2) {
        return calendar(j2, TimeZone.getDefault());
    }

    public static Calendar calendar(long j2, TimeZone timeZone) {
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setTimeInMillis(j2);
        return calendar;
    }

    public static String yearAndQuarter(Calendar calendar) {
        StringBuilder sbBuilder = StrUtil.builder();
        sbBuilder.append(calendar.get(1));
        sbBuilder.append((calendar.get(2) / 3) + 1);
        return sbBuilder.toString();
    }
}
