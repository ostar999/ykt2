package cn.hutool.core.date;

import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.format.DateParser;
import cn.hutool.core.date.format.DatePrinter;
import cn.hutool.core.date.format.FastDateFormat;
import cn.hutool.core.date.format.GlobalCustomFormat;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.SystemPropsUtil;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes.dex */
public class DateTime extends Date {
    private static final long serialVersionUID = -5395712593979185936L;
    private static boolean useJdkToStringStyle = false;
    private Week firstDayOfWeek;
    private int minimalDaysInFirstWeek;
    private boolean mutable;
    private TimeZone timeZone;

    public DateTime() {
        this(TimeZone.getDefault());
    }

    public static DateTime now() {
        return new DateTime();
    }

    public static DateTime of(long j2) {
        return new DateTime(j2);
    }

    private static Date parse(CharSequence charSequence, DateFormat dateFormat) throws IllegalArgumentException {
        Assert.notBlank(charSequence, "Date String must be not blank !", new Object[0]);
        try {
            return dateFormat.parse(charSequence.toString());
        } catch (Exception e2) {
            throw new DateException(CharSequenceUtil.format("Parse [{}] with format [{}] error!", charSequence, dateFormat instanceof SimpleDateFormat ? ((SimpleDateFormat) dateFormat).toPattern() : dateFormat.toString()), e2);
        }
    }

    private DateTime setTimeInternal(long j2) {
        super.setTime(j2);
        return this;
    }

    public static void setUseJdkToStringStyle(boolean z2) {
        useJdkToStringStyle = z2;
    }

    public DateBetween between(Date date) {
        return new DateBetween(this, date);
    }

    public int dayOfMonth() {
        return getField(DateField.DAY_OF_MONTH);
    }

    public int dayOfWeek() {
        return getField(DateField.DAY_OF_WEEK);
    }

    public Week dayOfWeekEnum() {
        return Week.of(dayOfWeek());
    }

    public int dayOfWeekInMonth() {
        return getField(DateField.DAY_OF_WEEK_IN_MONTH);
    }

    public int dayOfYear() {
        return getField(DateField.DAY_OF_YEAR);
    }

    public int getField(DateField dateField) {
        return getField(dateField.getValue());
    }

    public Week getFirstDayOfWeek() {
        return this.firstDayOfWeek;
    }

    public int getLastDayOfMonth() {
        return monthEnum().getLastDay(isLeapYear());
    }

    public TimeZone getTimeZone() {
        return this.timeZone;
    }

    public ZoneId getZoneId() {
        return this.timeZone.toZoneId();
    }

    public int hour(boolean z2) {
        return getField(z2 ? DateField.HOUR_OF_DAY : DateField.HOUR);
    }

    public boolean isAM() {
        return getField(DateField.AM_PM) == 0;
    }

    public boolean isAfter(Date date) {
        if (date != null) {
            return compareTo(date) > 0;
        }
        throw new NullPointerException("Date to compare is null !");
    }

    public boolean isAfterOrEquals(Date date) {
        if (date != null) {
            return compareTo(date) >= 0;
        }
        throw new NullPointerException("Date to compare is null !");
    }

    public boolean isBefore(Date date) {
        if (date != null) {
            return compareTo(date) < 0;
        }
        throw new NullPointerException("Date to compare is null !");
    }

    public boolean isBeforeOrEquals(Date date) {
        if (date != null) {
            return compareTo(date) <= 0;
        }
        throw new NullPointerException("Date to compare is null !");
    }

    public boolean isIn(Date date, Date date2) {
        long time = date.getTime();
        long time2 = date2.getTime();
        long time3 = getTime();
        return time3 >= Math.min(time, time2) && time3 <= Math.max(time, time2);
    }

    public boolean isLastDayOfMonth() {
        return dayOfMonth() == getLastDayOfMonth();
    }

    public boolean isLeapYear() {
        return DateUtil.isLeapYear(year());
    }

    public boolean isMutable() {
        return this.mutable;
    }

    public boolean isPM() {
        return 1 == getField(DateField.AM_PM);
    }

    public boolean isWeekend() {
        int iDayOfWeek = dayOfWeek();
        return 7 == iDayOfWeek || 1 == iDayOfWeek;
    }

    public int millisecond() {
        return getField(DateField.MILLISECOND);
    }

    public int minute() {
        return getField(DateField.MINUTE);
    }

    public int month() {
        return getField(DateField.MONTH);
    }

    public int monthBaseOne() {
        return month() + 1;
    }

    public Month monthEnum() {
        return Month.of(month());
    }

    public int monthStartFromOne() {
        return month() + 1;
    }

    public DateTime offset(DateField dateField, int i2) {
        if (DateField.ERA == dateField) {
            throw new IllegalArgumentException("ERA is not support offset!");
        }
        Calendar calendar = toCalendar();
        calendar.add(dateField.getValue(), i2);
        return (this.mutable ? this : (DateTime) ObjectUtil.clone(this)).setTimeInternal(calendar.getTimeInMillis());
    }

    public DateTime offsetNew(DateField dateField, int i2) {
        Calendar calendar = toCalendar();
        calendar.add(dateField.getValue(), i2);
        return ((DateTime) ObjectUtil.clone(this)).setTimeInternal(calendar.getTimeInMillis());
    }

    public int quarter() {
        return (month() / 3) + 1;
    }

    public Quarter quarterEnum() {
        return Quarter.of(quarter());
    }

    public int second() {
        return getField(DateField.SECOND);
    }

    public DateTime setField(DateField dateField, int i2) {
        return setField(dateField.getValue(), i2);
    }

    public DateTime setFirstDayOfWeek(Week week) {
        this.firstDayOfWeek = week;
        return this;
    }

    public DateTime setMinimalDaysInFirstWeek(int i2) {
        this.minimalDaysInFirstWeek = i2;
        return this;
    }

    public DateTime setMutable(boolean z2) {
        this.mutable = z2;
        return this;
    }

    @Override // java.util.Date
    public void setTime(long j2) {
        if (!this.mutable) {
            throw new DateException("This is not a mutable object !");
        }
        super.setTime(j2);
    }

    public DateTime setTimeZone(TimeZone timeZone) {
        this.timeZone = (TimeZone) ObjectUtil.defaultIfNull(timeZone, new m());
        return this;
    }

    public Calendar toCalendar() {
        return toCalendar(Locale.getDefault(Locale.Category.FORMAT));
    }

    public String toDateStr() {
        TimeZone timeZone = this.timeZone;
        return timeZone != null ? toString(DateUtil.newSimpleFormat("yyyy-MM-dd", null, timeZone)) : toString(DatePattern.NORM_DATE_FORMAT);
    }

    public Date toJdkDate() {
        return new Date(getTime());
    }

    public LocalDateTime toLocalDateTime() {
        return LocalDateTimeUtil.of(this);
    }

    public String toMsStr() {
        return toString(DatePattern.NORM_DATETIME_MS_FORMAT);
    }

    public java.sql.Date toSqlDate() {
        return new java.sql.Date(getTime());
    }

    @Override // java.util.Date
    public String toString() {
        return useJdkToStringStyle ? super.toString() : toString(this.timeZone);
    }

    public String toStringDefaultTimeZone() {
        return toString(TimeZone.getDefault());
    }

    public String toTimeStr() {
        TimeZone timeZone = this.timeZone;
        return timeZone != null ? toString(DateUtil.newSimpleFormat("HH:mm:ss", null, timeZone)) : toString(DatePattern.NORM_TIME_FORMAT);
    }

    public Timestamp toTimestamp() {
        return new Timestamp(getTime());
    }

    public int weekOfMonth() {
        return getField(DateField.WEEK_OF_MONTH);
    }

    public int weekOfYear() {
        return getField(DateField.WEEK_OF_YEAR);
    }

    public int year() {
        return getField(DateField.YEAR);
    }

    public DateTime(TimeZone timeZone) {
        this(System.currentTimeMillis(), timeZone);
    }

    public static DateTime of(Date date) {
        return date instanceof DateTime ? (DateTime) date : new DateTime(date);
    }

    public long between(Date date, DateUnit dateUnit) {
        return new DateBetween(this, date).between(dateUnit);
    }

    public int getField(int i2) {
        return toCalendar().get(i2);
    }

    public DateTime setField(int i2, int i3) {
        Calendar calendar = toCalendar();
        calendar.set(i2, i3);
        return (!this.mutable ? (DateTime) ObjectUtil.clone(this) : this).setTimeInternal(calendar.getTimeInMillis());
    }

    public Calendar toCalendar(Locale locale) {
        return toCalendar(this.timeZone, locale);
    }

    public DateTime(Date date) {
        this(date, date instanceof DateTime ? ((DateTime) date).timeZone : TimeZone.getDefault());
    }

    public String between(Date date, DateUnit dateUnit, BetweenFormatter.Level level) {
        return new DateBetween(this, date).toString(dateUnit, level);
    }

    public Calendar toCalendar(TimeZone timeZone) {
        return toCalendar(timeZone, Locale.getDefault(Locale.Category.FORMAT));
    }

    public Calendar toCalendar(TimeZone timeZone, Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault(Locale.Category.FORMAT);
        }
        Calendar calendar = timeZone != null ? Calendar.getInstance(timeZone, locale) : Calendar.getInstance(locale);
        calendar.setFirstDayOfWeek(this.firstDayOfWeek.getValue());
        int i2 = this.minimalDaysInFirstWeek;
        if (i2 > 0) {
            calendar.setMinimalDaysInFirstWeek(i2);
        }
        calendar.setTime(this);
        return calendar;
    }

    public String toString(TimeZone timeZone) {
        if (timeZone != null) {
            return toString(DateUtil.newSimpleFormat("yyyy-MM-dd HH:mm:ss", null, timeZone));
        }
        return toString(DatePattern.NORM_DATETIME_FORMAT);
    }

    public static DateTime of(Calendar calendar) {
        return new DateTime(calendar);
    }

    public DateTime(Date date, TimeZone timeZone) {
        this(((Date) ObjectUtil.defaultIfNull(date, new Date())).getTime(), timeZone);
    }

    public static DateTime of(String str, String str2) {
        return new DateTime(str, str2);
    }

    public String toString(String str) {
        TimeZone timeZone = this.timeZone;
        if (timeZone != null) {
            return toString(DateUtil.newSimpleFormat(str, null, timeZone));
        }
        return toString(FastDateFormat.getInstance(str));
    }

    public DateTime(Calendar calendar) {
        this(calendar.getTime(), calendar.getTimeZone());
        setFirstDayOfWeek(Week.of(calendar.getFirstDayOfWeek()));
    }

    private static Calendar parse(CharSequence charSequence, DateParser dateParser, boolean z2) throws IllegalArgumentException {
        Assert.notNull(dateParser, "Parser or DateFromat must be not null !", new Object[0]);
        Assert.notBlank(charSequence, "Date String must be not blank !", new Object[0]);
        Calendar calendar = CalendarUtil.parse(charSequence, z2, dateParser);
        if (calendar != null) {
            calendar.setFirstDayOfWeek(Week.MONDAY.getValue());
            return calendar;
        }
        throw new DateException("Parse [{}] with format [{}] error!", charSequence, dateParser.getPattern());
    }

    public DateTime(Instant instant) {
        this(instant.toEpochMilli());
    }

    public String toString(DatePrinter datePrinter) {
        return datePrinter.format(this);
    }

    public DateTime(Instant instant, ZoneId zoneId) {
        this(instant.toEpochMilli(), ZoneUtil.toTimeZone(zoneId));
    }

    public String toString(DateFormat dateFormat) {
        return dateFormat.format((Date) this);
    }

    public DateTime(TemporalAccessor temporalAccessor) {
        this(TemporalAccessorUtil.toInstant(temporalAccessor));
    }

    public DateTime(ZonedDateTime zonedDateTime) {
        this(zonedDateTime.toInstant(), zonedDateTime.getZone());
    }

    public DateTime(long j2) {
        this(j2, TimeZone.getDefault());
    }

    public DateTime(long j2, TimeZone timeZone) {
        super(j2);
        this.mutable = true;
        this.firstDayOfWeek = Week.MONDAY;
        this.timeZone = (TimeZone) ObjectUtil.defaultIfNull(timeZone, new m());
    }

    public DateTime(CharSequence charSequence) {
        this(DateUtil.parse(charSequence));
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public DateTime(CharSequence charSequence, String str) throws IllegalArgumentException {
        Date date;
        if (GlobalCustomFormat.isCustomFormat(str)) {
            date = GlobalCustomFormat.parse(charSequence, str);
        } else {
            date = parse(charSequence, DateUtil.newSimpleFormat(str));
        }
        this(date);
    }

    public DateTime(CharSequence charSequence, DateFormat dateFormat) {
        this(parse(charSequence, dateFormat), dateFormat.getTimeZone());
    }

    public DateTime(CharSequence charSequence, DateTimeFormatter dateTimeFormatter) {
        this(TemporalAccessorUtil.toInstant(dateTimeFormatter.parse(charSequence)), dateTimeFormatter.getZone());
    }

    public DateTime(CharSequence charSequence, DateParser dateParser) {
        this(charSequence, dateParser, SystemPropsUtil.getBoolean(SystemPropsUtil.HUTOOL_DATE_LENIENT, true));
    }

    public DateTime(CharSequence charSequence, DateParser dateParser, boolean z2) {
        this(parse(charSequence, dateParser, z2));
    }
}
