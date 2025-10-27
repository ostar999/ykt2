package cn.hutool.core.date;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.format.DateParser;
import cn.hutool.core.date.format.DatePrinter;
import cn.hutool.core.date.format.GlobalCustomFormat;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.meizu.cloud.pushsdk.notification.model.NotificationStyle;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Marker;

/* loaded from: classes.dex */
public class DateUtil extends CalendarUtil {
    private static final String[] wtb = {"sun", "mon", "tue", "wed", "thu", "fri", "sat", "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec", "gmt", "ut", "utc", "est", "edt", "cst", "cdt", "mst", "mdt", "pst", "pdt"};

    /* renamed from: cn.hutool.core.date.DateUtil$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$java$util$concurrent$TimeUnit;

        static {
            int[] iArr = new int[TimeUnit.values().length];
            $SwitchMap$java$util$concurrent$TimeUnit = iArr;
            try {
                iArr[TimeUnit.NANOSECONDS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.MICROSECONDS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.MILLISECONDS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.SECONDS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.MINUTES.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.HOURS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public static int age(Date date, Date date2) throws IllegalArgumentException {
        Assert.notNull(date, "Birthday can not be null !", new Object[0]);
        if (date2 == null) {
            date2 = date();
        }
        return CalendarUtil.age(date.getTime(), date2.getTime());
    }

    public static int ageOfNow(String str) {
        return ageOfNow(parse(str));
    }

    public static DateTime beginOfDay(Date date) {
        return new DateTime(CalendarUtil.beginOfDay(CalendarUtil.calendar(date)));
    }

    public static DateTime beginOfHour(Date date) {
        return new DateTime(CalendarUtil.beginOfHour(CalendarUtil.calendar(date)));
    }

    public static DateTime beginOfMinute(Date date) {
        return new DateTime(CalendarUtil.beginOfMinute(CalendarUtil.calendar(date)));
    }

    public static DateTime beginOfMonth(Date date) {
        return new DateTime(CalendarUtil.beginOfMonth(CalendarUtil.calendar(date)));
    }

    public static DateTime beginOfQuarter(Date date) {
        return new DateTime(CalendarUtil.beginOfQuarter(CalendarUtil.calendar(date)));
    }

    public static DateTime beginOfSecond(Date date) {
        return new DateTime(CalendarUtil.beginOfSecond(CalendarUtil.calendar(date)));
    }

    public static DateTime beginOfWeek(Date date) {
        return new DateTime(CalendarUtil.beginOfWeek(CalendarUtil.calendar(date)));
    }

    public static DateTime beginOfYear(Date date) {
        return new DateTime(CalendarUtil.beginOfYear(CalendarUtil.calendar(date)));
    }

    public static long between(Date date, Date date2, DateUnit dateUnit) {
        return between(date, date2, dateUnit, true);
    }

    public static long betweenDay(Date date, Date date2, boolean z2) {
        if (z2) {
            date = beginOfDay(date);
            date2 = beginOfDay(date2);
        }
        return between(date, date2, DateUnit.DAY);
    }

    public static long betweenMonth(Date date, Date date2, boolean z2) {
        return new DateBetween(date, date2).betweenMonth(z2);
    }

    public static long betweenMs(Date date, Date date2) {
        return new DateBetween(date, date2).between(DateUnit.MS);
    }

    public static long betweenWeek(Date date, Date date2, boolean z2) {
        if (z2) {
            date = beginOfDay(date);
            date2 = beginOfDay(date2);
        }
        return between(date, date2, DateUnit.WEEK);
    }

    public static long betweenYear(Date date, Date date2, boolean z2) {
        return new DateBetween(date, date2).betweenYear(z2);
    }

    public static DateTime ceiling(Date date, DateField dateField) {
        return new DateTime(CalendarUtil.ceiling(CalendarUtil.calendar(date), dateField));
    }

    public static int compare(Date date, Date date2) {
        return CompareUtil.compare(date, date2);
    }

    public static DateTime convertTimeZone(Date date, ZoneId zoneId) {
        return new DateTime(date, ZoneUtil.toTimeZone(zoneId));
    }

    public static StopWatch createStopWatch() {
        return new StopWatch();
    }

    public static long current() {
        return System.currentTimeMillis();
    }

    public static long currentSeconds() {
        return System.currentTimeMillis() / 1000;
    }

    public static DateTime date() {
        return new DateTime();
    }

    public static DateTime dateNew(Date date) {
        if (date == null) {
            return null;
        }
        return new DateTime(date);
    }

    public static DateTime dateSecond() {
        return beginOfSecond(date());
    }

    public static int dayOfMonth(Date date) {
        return DateTime.of(date).dayOfMonth();
    }

    public static int dayOfWeek(Date date) {
        return DateTime.of(date).dayOfWeek();
    }

    public static Week dayOfWeekEnum(Date date) {
        return DateTime.of(date).dayOfWeekEnum();
    }

    public static int dayOfYear(Date date) {
        return DateTime.of(date).dayOfYear();
    }

    public static DateTime endOfDay(Date date) {
        return new DateTime(CalendarUtil.endOfDay(CalendarUtil.calendar(date)));
    }

    public static DateTime endOfHour(Date date) {
        return new DateTime(CalendarUtil.endOfHour(CalendarUtil.calendar(date)));
    }

    public static DateTime endOfMinute(Date date) {
        return new DateTime(CalendarUtil.endOfMinute(CalendarUtil.calendar(date)));
    }

    public static DateTime endOfMonth(Date date) {
        return new DateTime(CalendarUtil.endOfMonth(CalendarUtil.calendar(date)));
    }

    public static DateTime endOfQuarter(Date date) {
        return new DateTime(CalendarUtil.endOfQuarter(CalendarUtil.calendar(date)));
    }

    public static DateTime endOfSecond(Date date) {
        return new DateTime(CalendarUtil.endOfSecond(CalendarUtil.calendar(date)));
    }

    public static DateTime endOfWeek(Date date) {
        return new DateTime(CalendarUtil.endOfWeek(CalendarUtil.calendar(date)));
    }

    public static DateTime endOfYear(Date date) {
        return new DateTime(CalendarUtil.endOfYear(CalendarUtil.calendar(date)));
    }

    public static String format(LocalDateTime localDateTime, String str) {
        return LocalDateTimeUtil.format(localDateTime, str);
    }

    public static String formatBetween(Date date, Date date2, BetweenFormatter.Level level) {
        return formatBetween(between(date, date2, DateUnit.MS), level);
    }

    public static String formatChineseDate(Date date, boolean z2, boolean z3) {
        if (date == null) {
            return null;
        }
        if (z2) {
            return CalendarUtil.formatChineseDate(CalendarUtil.calendar(date), z3);
        }
        return (z3 ? DatePattern.CHINESE_DATE_TIME_FORMAT : DatePattern.CHINESE_DATE_FORMAT).format(date);
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        return DatePattern.NORM_DATE_FORMAT.format(date);
    }

    public static String formatDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return DatePattern.NORM_DATETIME_FORMAT.format(date);
    }

    public static String formatHttpDate(Date date) {
        if (date == null) {
            return null;
        }
        return DatePattern.HTTP_DATETIME_FORMAT.format(date);
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return LocalDateTimeUtil.formatNormal(localDateTime);
    }

    public static String formatTime(Date date) {
        if (date == null) {
            return null;
        }
        return DatePattern.NORM_TIME_FORMAT.format(date);
    }

    public static String getChineseZodiac(int i2) {
        return Zodiac.getChineseZodiac(i2);
    }

    public static int getLastDayOfMonth(Date date) {
        return date(date).getLastDayOfMonth();
    }

    public static String getShotName(TimeUnit timeUnit) {
        switch (AnonymousClass1.$SwitchMap$java$util$concurrent$TimeUnit[timeUnit.ordinal()]) {
            case 1:
                return NotificationStyle.NOTIFICATION_STYLE;
            case 2:
                return "μs";
            case 3:
                return "ms";
            case 4:
                return "s";
            case 5:
                return "min";
            case 6:
                return "h";
            default:
                return timeUnit.name().toLowerCase();
        }
    }

    public static String getZodiac(int i2, int i3) {
        return Zodiac.getZodiac(i2, i3);
    }

    public static int hour(Date date, boolean z2) {
        return DateTime.of(date).hour(z2);
    }

    public static boolean isAM(Date date) {
        return DateTime.of(date).isAM();
    }

    @Deprecated
    public static boolean isExpired(Date date, DateField dateField, int i2, Date date2) {
        return offset(date, dateField, i2).after(date2);
    }

    public static boolean isIn(Date date, Date date2, Date date3) {
        return date instanceof DateTime ? ((DateTime) date).isIn(date2, date3) : new DateTime(date).isIn(date2, date3);
    }

    public static boolean isLastDayOfMonth(Date date) {
        return date(date).isLastDayOfMonth();
    }

    public static boolean isLeapYear(int i2) {
        return Year.isLeap(i2);
    }

    public static boolean isOverlap(Date date, Date date2, Date date3, Date date4) {
        return date.compareTo(date4) <= 0 && date3.compareTo(date2) <= 0;
    }

    public static boolean isPM(Date date) {
        return DateTime.of(date).isPM();
    }

    public static boolean isSameDay(Date date, Date date2) {
        if (date == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return CalendarUtil.isSameDay(CalendarUtil.calendar(date), CalendarUtil.calendar(date2));
    }

    public static boolean isSameMonth(Date date, Date date2) {
        if (date == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return CalendarUtil.isSameMonth(CalendarUtil.calendar(date), CalendarUtil.calendar(date2));
    }

    public static boolean isSameTime(Date date, Date date2) {
        return date.compareTo(date2) == 0;
    }

    public static boolean isSameWeek(Date date, Date date2, boolean z2) {
        if (date == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return CalendarUtil.isSameWeek(CalendarUtil.calendar(date), CalendarUtil.calendar(date2), z2);
    }

    public static boolean isWeekend(Date date) {
        Week weekDayOfWeekEnum = dayOfWeekEnum(date);
        return Week.SATURDAY == weekDayOfWeekEnum || Week.SUNDAY == weekDayOfWeekEnum;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$rangeNotContains$0(List list, DateTime dateTime) {
        return !list.contains(dateTime);
    }

    public static DateTime lastMonth() {
        return offsetMonth(new DateTime(), -1);
    }

    public static DateTime lastWeek() {
        return offsetWeek(new DateTime(), -1);
    }

    public static int lengthOfMonth(int i2, boolean z2) {
        return java.time.Month.of(i2).length(z2);
    }

    public static int lengthOfYear(int i2) {
        return Year.of(i2).length();
    }

    public static int millisecond(Date date) {
        return DateTime.of(date).millisecond();
    }

    public static int minute(Date date) {
        return DateTime.of(date).minute();
    }

    public static int month(Date date) {
        return DateTime.of(date).month();
    }

    public static Month monthEnum(Date date) {
        return DateTime.of(date).monthEnum();
    }

    public static long nanosToMillis(long j2) {
        return TimeUnit.NANOSECONDS.toMillis(j2);
    }

    public static double nanosToSeconds(long j2) {
        return j2 / 1.0E9d;
    }

    public static SimpleDateFormat newSimpleFormat(String str) {
        return newSimpleFormat(str, null, null);
    }

    public static DateTime nextMonth() {
        return offsetMonth(new DateTime(), 1);
    }

    public static DateTime nextWeek() {
        return offsetWeek(new DateTime(), 1);
    }

    private static String normalize(CharSequence charSequence) {
        if (CharSequenceUtil.isBlank(charSequence)) {
            return CharSequenceUtil.str(charSequence);
        }
        List<String> listSplitTrim = CharSequenceUtil.splitTrim(charSequence, ' ');
        int size = listSplitTrim.size();
        if (size < 1 || size > 2) {
            return CharSequenceUtil.str(charSequence);
        }
        StringBuilder sbBuilder = StrUtil.builder();
        sbBuilder.append(CharSequenceUtil.removeSuffix(listSplitTrim.get(0).replaceAll("[/.年月]", "-"), "日"));
        if (size == 2) {
            sbBuilder.append(' ');
            sbBuilder.append(CharSequenceUtil.removeSuffix(listSplitTrim.get(1).replaceAll("[时分秒]", ":"), ":").replace(',', '.'));
        }
        return sbBuilder.toString();
    }

    private static String normalizeMillSeconds(String str, CharSequence charSequence, CharSequence charSequence2) {
        if (CharSequenceUtil.isBlank(charSequence2)) {
            return CharSequenceUtil.subBefore((CharSequence) str, charSequence, true) + ((Object) charSequence) + CharSequenceUtil.subPre(CharSequenceUtil.subAfter((CharSequence) str, charSequence, true), 3);
        }
        return CharSequenceUtil.subBefore((CharSequence) str, charSequence, true) + ((Object) charSequence) + CharSequenceUtil.subPre(CharSequenceUtil.subBetween(str, charSequence, charSequence2), 3) + ((Object) charSequence2) + CharSequenceUtil.subAfter((CharSequence) str, charSequence2, true);
    }

    public static String now() {
        return formatDateTime(new DateTime());
    }

    public static DateTime offset(Date date, DateField dateField, int i2) {
        return dateNew(date).offset(dateField, i2);
    }

    public static DateTime offsetDay(Date date, int i2) {
        return offset(date, DateField.DAY_OF_YEAR, i2);
    }

    public static DateTime offsetHour(Date date, int i2) {
        return offset(date, DateField.HOUR_OF_DAY, i2);
    }

    public static DateTime offsetMillisecond(Date date, int i2) {
        return offset(date, DateField.MILLISECOND, i2);
    }

    public static DateTime offsetMinute(Date date, int i2) {
        return offset(date, DateField.MINUTE, i2);
    }

    public static DateTime offsetMonth(Date date, int i2) {
        return offset(date, DateField.MONTH, i2);
    }

    public static DateTime offsetSecond(Date date, int i2) {
        return offset(date, DateField.SECOND, i2);
    }

    public static DateTime offsetWeek(Date date, int i2) {
        return offset(date, DateField.WEEK_OF_YEAR, i2);
    }

    public static DateTime parse(CharSequence charSequence, DateFormat dateFormat) {
        return new DateTime(charSequence, dateFormat);
    }

    public static DateTime parseCST(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        return parse(charSequence, DatePattern.JDK_DATETIME_FORMAT);
    }

    public static DateTime parseDate(CharSequence charSequence) {
        return parse(normalize(charSequence), DatePattern.NORM_DATE_FORMAT);
    }

    public static DateTime parseDateTime(CharSequence charSequence) {
        return parse(normalize(charSequence), DatePattern.NORM_DATETIME_FORMAT);
    }

    public static LocalDateTime parseLocalDateTime(CharSequence charSequence) {
        return parseLocalDateTime(charSequence, "yyyy-MM-dd HH:mm:ss");
    }

    public static DateTime parseTime(CharSequence charSequence) {
        return parse(normalize(charSequence), DatePattern.NORM_TIME_FORMAT);
    }

    public static DateTime parseTimeToday(CharSequence charSequence) {
        String str = CharSequenceUtil.format("{} {}", today(), charSequence);
        return 1 == CharSequenceUtil.count((CharSequence) str, ':') ? parse(str, "yyyy-MM-dd HH:mm") : parse(str, DatePattern.NORM_DATETIME_FORMAT);
    }

    public static DateTime parseUTC(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (CharSequenceUtil.contains((CharSequence) str, 'Z')) {
            if (length == 20) {
                return parse(str, DatePattern.UTC_FORMAT);
            }
            if (length <= 28 && length >= 22) {
                return parse(str, DatePattern.UTC_MS_FORMAT);
            }
        } else {
            if (CharSequenceUtil.contains((CharSequence) str, '+')) {
                String strReplace = str.replace(" +", Marker.ANY_NON_NULL_MARKER);
                String strSubAfter = CharSequenceUtil.subAfter((CharSequence) strReplace, '+', true);
                if (CharSequenceUtil.isBlank(strSubAfter)) {
                    throw new DateException("Invalid format: [{}]", strReplace);
                }
                if (!CharSequenceUtil.contains((CharSequence) strSubAfter, ':')) {
                    strReplace = CharSequenceUtil.subBefore((CharSequence) strReplace, '+', true) + Marker.ANY_NON_NULL_MARKER + strSubAfter.substring(0, 2) + ":00";
                }
                return CharSequenceUtil.contains((CharSequence) strReplace, '.') ? parse(normalizeMillSeconds(strReplace, StrPool.DOT, Marker.ANY_NON_NULL_MARKER), DatePattern.UTC_MS_WITH_XXX_OFFSET_FORMAT) : parse(strReplace, DatePattern.UTC_WITH_XXX_OFFSET_FORMAT);
            }
            if (ReUtil.contains("-\\d{2}:?00", str)) {
                String strReplace2 = str.replace(" -", "-");
                if (':' != strReplace2.charAt(strReplace2.length() - 3)) {
                    strReplace2 = strReplace2.substring(0, strReplace2.length() - 2) + ":00";
                }
                return CharSequenceUtil.contains((CharSequence) strReplace2, '.') ? new DateTime(normalizeMillSeconds(strReplace2, StrPool.DOT, "-"), DatePattern.UTC_MS_WITH_XXX_OFFSET_FORMAT) : new DateTime(strReplace2, DatePattern.UTC_WITH_XXX_OFFSET_FORMAT);
            }
            if (length == 19) {
                return parse(str, DatePattern.UTC_SIMPLE_FORMAT);
            }
            if (length == 16) {
                return parse(str + ":00", DatePattern.UTC_SIMPLE_FORMAT);
            }
            if (CharSequenceUtil.contains((CharSequence) str, '.')) {
                return parse(normalizeMillSeconds(str, StrPool.DOT, null), DatePattern.UTC_SIMPLE_MS_FORMAT);
            }
        }
        throw new DateException("No format fit for date String [{}] !", str);
    }

    public static int quarter(Date date) {
        return DateTime.of(date).quarter();
    }

    public static Quarter quarterEnum(Date date) {
        return DateTime.of(date).quarterEnum();
    }

    public static DateRange range(Date date, Date date2, DateField dateField) {
        return new DateRange(date, date2, dateField);
    }

    public static void rangeConsume(Date date, Date date2, DateField dateField, Consumer<Date> consumer) {
        if (date == null || date2 == null || date.after(date2)) {
            return;
        }
        range(date, date2, dateField).forEach(consumer);
    }

    public static List<DateTime> rangeContains(DateRange dateRange, DateRange dateRange2) {
        ArrayList arrayListNewArrayList = CollUtil.newArrayList((Iterable) dateRange);
        final ArrayList arrayListNewArrayList2 = CollUtil.newArrayList((Iterable) dateRange2);
        Stream stream = arrayListNewArrayList.stream();
        arrayListNewArrayList2.getClass();
        return (List) stream.filter(new Predicate() { // from class: cn.hutool.core.date.z
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return arrayListNewArrayList2.contains((DateTime) obj);
            }
        }).collect(Collectors.toList());
    }

    public static <T> List<T> rangeFunc(Date date, Date date2, DateField dateField, Function<Date, T> function) {
        if (date == null || date2 == null || date.after(date2)) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        Iterator<DateTime> it = range(date, date2, dateField).iterator();
        while (it.hasNext()) {
            arrayList.add(function.apply(it.next()));
        }
        return arrayList;
    }

    public static List<DateTime> rangeNotContains(DateRange dateRange, DateRange dateRange2) {
        final ArrayList arrayListNewArrayList = CollUtil.newArrayList((Iterable) dateRange);
        return (List) CollUtil.newArrayList((Iterable) dateRange2).stream().filter(new Predicate() { // from class: cn.hutool.core.date.a0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return DateUtil.lambda$rangeNotContains$0(arrayListNewArrayList, (DateTime) obj);
            }
        }).collect(Collectors.toList());
    }

    public static List<DateTime> rangeToList(Date date, Date date2, DateField dateField) {
        return CollUtil.newArrayList((Iterable) range(date, date2, dateField));
    }

    public static DateTime round(Date date, DateField dateField) {
        return new DateTime(CalendarUtil.round(CalendarUtil.calendar(date), dateField));
    }

    public static int second(Date date) {
        return DateTime.of(date).second();
    }

    public static String secondToTime(int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("Seconds must be a positive number!");
        }
        int i3 = i2 / 3600;
        int i4 = i2 % 3600;
        int i5 = i4 / 60;
        int i6 = i4 % 60;
        StringBuilder sb = new StringBuilder();
        if (i3 < 10) {
            sb.append("0");
        }
        sb.append(i3);
        sb.append(":");
        if (i5 < 10) {
            sb.append("0");
        }
        sb.append(i5);
        sb.append(":");
        if (i6 < 10) {
            sb.append("0");
        }
        sb.append(i6);
        return sb.toString();
    }

    public static long spendMs(long j2) {
        return System.currentTimeMillis() - j2;
    }

    public static long spendNt(long j2) {
        return System.nanoTime() - j2;
    }

    public static int thisDayOfMonth() {
        return dayOfMonth(date());
    }

    public static int thisDayOfWeek() {
        return dayOfWeek(date());
    }

    public static Week thisDayOfWeekEnum() {
        return dayOfWeekEnum(date());
    }

    public static int thisHour(boolean z2) {
        return hour(date(), z2);
    }

    public static int thisMillisecond() {
        return millisecond(date());
    }

    public static int thisMinute() {
        return minute(date());
    }

    public static int thisMonth() {
        return month(date());
    }

    public static Month thisMonthEnum() {
        return monthEnum(date());
    }

    public static int thisSecond() {
        return second(date());
    }

    public static int thisWeekOfMonth() {
        return weekOfMonth(date());
    }

    public static int thisWeekOfYear() {
        return weekOfYear(date());
    }

    public static int thisYear() {
        return year(date());
    }

    public static int timeToSecond(String str) {
        int i2 = 0;
        if (CharSequenceUtil.isEmpty(str)) {
            return 0;
        }
        for (int size = CharSequenceUtil.splitTrim((CharSequence) str, ':', 3).size() - 1; size >= 0; size--) {
            i2 = (int) (i2 + (Integer.parseInt(r11.get(size)) * Math.pow(60.0d, r0 - size)));
        }
        return i2;
    }

    public static TimeInterval timer() {
        return new TimeInterval();
    }

    public static Instant toInstant(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant();
    }

    @Deprecated
    public static int toIntSecond(Date date) {
        return Integer.parseInt(format(date, "yyMMddHHmm"));
    }

    public static LocalDateTime toLocalDateTime(Instant instant) {
        return LocalDateTimeUtil.of(instant);
    }

    public static String today() {
        return formatDate(new DateTime());
    }

    public static DateTime tomorrow() {
        return offsetDay(new DateTime(), 1);
    }

    public static DateTime truncate(Date date, DateField dateField) {
        return new DateTime(CalendarUtil.truncate(CalendarUtil.calendar(date), dateField));
    }

    public static int weekOfMonth(Date date) {
        return DateTime.of(date).weekOfMonth();
    }

    public static int weekOfYear(Date date) {
        return DateTime.of(date).weekOfYear();
    }

    public static int year(Date date) {
        return DateTime.of(date).year();
    }

    public static String yearAndQuarter(Date date) {
        return CalendarUtil.yearAndQuarter(CalendarUtil.calendar(date));
    }

    public static DateTime yesterday() {
        return offsetDay(new DateTime(), -1);
    }

    public static int ageOfNow(Date date) {
        return age(date, date());
    }

    public static DateTime beginOfWeek(Date date, boolean z2) {
        return new DateTime(CalendarUtil.beginOfWeek(CalendarUtil.calendar(date), z2));
    }

    public static long between(Date date, Date date2, DateUnit dateUnit, boolean z2) {
        return new DateBetween(date, date2, z2).between(dateUnit);
    }

    public static DateTime ceiling(Date date, DateField dateField, boolean z2) {
        return new DateTime(CalendarUtil.ceiling(CalendarUtil.calendar(date), dateField, z2));
    }

    public static int compare(Date date, Date date2, String str) {
        if (str != null) {
            if (date != null) {
                date = parse(format(date, str), str);
            }
            if (date2 != null) {
                date2 = parse(format(date2, str), str);
            }
        }
        return CompareUtil.compare(date, date2);
    }

    public static DateTime convertTimeZone(Date date, TimeZone timeZone) {
        return new DateTime(date, timeZone);
    }

    public static StopWatch createStopWatch(String str) {
        return new StopWatch(str);
    }

    public static DateTime date(Date date) {
        if (date == null) {
            return null;
        }
        return date instanceof DateTime ? (DateTime) date : dateNew(date);
    }

    public static DateTime endOfWeek(Date date, boolean z2) {
        return new DateTime(CalendarUtil.endOfWeek(CalendarUtil.calendar(date), z2));
    }

    public static String format(Date date, String str) {
        if (date == null || CharSequenceUtil.isBlank(str)) {
            return null;
        }
        if (GlobalCustomFormat.isCustomFormat(str)) {
            return GlobalCustomFormat.format(date, str);
        }
        return format(date, newSimpleFormat(str, null, date instanceof DateTime ? ((DateTime) date).getTimeZone() : null));
    }

    public static String formatBetween(Date date, Date date2) {
        return formatBetween(between(date, date2, DateUnit.MS));
    }

    public static SimpleDateFormat newSimpleFormat(String str, Locale locale, TimeZone timeZone) {
        if (locale == null) {
            locale = Locale.getDefault(Locale.Category.FORMAT);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str, locale);
        if (timeZone != null) {
            simpleDateFormat.setTimeZone(timeZone);
        }
        simpleDateFormat.setLenient(false);
        return simpleDateFormat;
    }

    public static DateTime parse(CharSequence charSequence, DateParser dateParser) {
        return new DateTime(charSequence, dateParser);
    }

    public static LocalDateTime parseLocalDateTime(CharSequence charSequence, String str) {
        return LocalDateTimeUtil.parse(charSequence, str);
    }

    public static List<DateTime> rangeToList(Date date, Date date2, DateField dateField, int i2) {
        return CollUtil.newArrayList((Iterable) new DateRange(date, date2, dateField, i2));
    }

    public static TimeInterval timer(boolean z2) {
        return new TimeInterval(z2);
    }

    public static Instant toInstant(TemporalAccessor temporalAccessor) {
        return TemporalAccessorUtil.toInstant(temporalAccessor);
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTimeUtil.of(date);
    }

    public static LinkedHashSet<String> yearAndQuarter(Date date, Date date2) {
        return (date == null || date2 == null) ? new LinkedHashSet<>(0) : CalendarUtil.yearAndQuarter(date.getTime(), date2.getTime());
    }

    public static String formatBetween(long j2, BetweenFormatter.Level level) {
        return new BetweenFormatter(j2, level).format();
    }

    @Deprecated
    public static boolean isExpired(Date date, Date date2, Date date3) {
        return betweenMs(date, date3) > betweenMs(date, date2);
    }

    public static DateTime parse(CharSequence charSequence, DateParser dateParser, boolean z2) {
        return new DateTime(charSequence, dateParser, z2);
    }

    public static String formatBetween(long j2) {
        return new BetweenFormatter(j2, BetweenFormatter.Level.MILLISECOND).format();
    }

    public static DateTime parse(CharSequence charSequence, DateTimeFormatter dateTimeFormatter) {
        return new DateTime(charSequence, dateTimeFormatter);
    }

    public static DateTime date(long j2) {
        return new DateTime(j2);
    }

    public static DateTime parse(CharSequence charSequence, String str) {
        return new DateTime(charSequence, str);
    }

    public static DateTime date(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        return new DateTime(calendar);
    }

    public static DateTime parse(CharSequence charSequence, String str, Locale locale) {
        if (GlobalCustomFormat.isCustomFormat(str)) {
            return new DateTime(GlobalCustomFormat.parse(charSequence, str));
        }
        return new DateTime(charSequence, newSimpleFormat(str, locale, null));
    }

    public static DateTime date(TemporalAccessor temporalAccessor) {
        if (temporalAccessor == null) {
            return null;
        }
        return new DateTime(temporalAccessor);
    }

    public static String format(Date date, DatePrinter datePrinter) {
        if (datePrinter == null || date == null) {
            return null;
        }
        return datePrinter.format(date);
    }

    public static String format(Date date, DateFormat dateFormat) {
        if (dateFormat == null || date == null) {
            return null;
        }
        return dateFormat.format(date);
    }

    public static DateTime parse(String str, String... strArr) throws DateException {
        return new DateTime(CalendarUtil.parseByPatterns(str, strArr));
    }

    public static String format(Date date, DateTimeFormatter dateTimeFormatter) {
        if (dateTimeFormatter == null || date == null) {
            return null;
        }
        return TemporalAccessorUtil.format(date.toInstant(), dateTimeFormatter);
    }

    public static DateTime parse(CharSequence charSequence) {
        if (CharSequenceUtil.isBlank(charSequence)) {
            return null;
        }
        String strRemoveAll = CharSequenceUtil.removeAll(charSequence.toString().trim(), 26085, 31186);
        int length = strRemoveAll.length();
        if (NumberUtil.isNumber(strRemoveAll)) {
            if (length == 14) {
                return parse(strRemoveAll, DatePattern.PURE_DATETIME_FORMAT);
            }
            if (length == 17) {
                return parse(strRemoveAll, DatePattern.PURE_DATETIME_MS_FORMAT);
            }
            if (length == 8) {
                return parse(strRemoveAll, DatePattern.PURE_DATE_FORMAT);
            }
            if (length == 6) {
                return parse(strRemoveAll, DatePattern.PURE_TIME_FORMAT);
            }
        } else {
            if (ReUtil.isMatch(PatternPool.TIME, strRemoveAll)) {
                return parseTimeToday(strRemoveAll);
            }
            if (CharSequenceUtil.containsAnyIgnoreCase(strRemoveAll, wtb)) {
                return parseCST(strRemoveAll);
            }
            if (CharSequenceUtil.contains((CharSequence) strRemoveAll, 'T')) {
                return parseUTC(strRemoveAll);
            }
        }
        String strNormalize = normalize(strRemoveAll);
        if (ReUtil.isMatch(DatePattern.REGEX_NORM, strNormalize)) {
            int iCount = CharSequenceUtil.count((CharSequence) strNormalize, ':');
            if (iCount == 0) {
                return parse(strNormalize, DatePattern.NORM_DATE_FORMAT);
            }
            if (iCount == 1) {
                return parse(strNormalize, DatePattern.NORM_DATETIME_MINUTE_FORMAT);
            }
            if (iCount == 2) {
                int iIndexOf = CharSequenceUtil.indexOf(strNormalize, '.');
                if (iIndexOf > 0) {
                    if (strNormalize.length() - iIndexOf > 4) {
                        strNormalize = CharSequenceUtil.subPre(strNormalize, iIndexOf + 4);
                    }
                    return parse(strNormalize, DatePattern.NORM_DATETIME_MS_FORMAT);
                }
                return parse(strNormalize, DatePattern.NORM_DATETIME_FORMAT);
            }
        }
        throw new DateException("No format fit for date String [{}] !", strNormalize);
    }
}
