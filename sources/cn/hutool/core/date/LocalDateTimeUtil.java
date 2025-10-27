package cn.hutool.core.date;

import cn.hutool.core.date.format.GlobalCustomFormat;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalUnit;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.TimeZone;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class LocalDateTimeUtil {
    public static LocalDateTime beginOfDay(LocalDateTime localDateTime) {
        return localDateTime.with((TemporalAdjuster) LocalTime.MIN);
    }

    public static Duration between(LocalDateTime localDateTime, LocalDateTime localDateTime2) {
        return TemporalUtil.between(localDateTime, localDateTime2);
    }

    public static Period betweenPeriod(LocalDate localDate, LocalDate localDate2) {
        return Period.between(localDate, localDate2);
    }

    public static Week dayOfWeek(LocalDate localDate) {
        return Week.of(localDate.getDayOfWeek());
    }

    public static LocalDateTime endOfDay(LocalDateTime localDateTime) {
        return endOfDay(localDateTime, false);
    }

    public static String format(LocalDateTime localDateTime, DateTimeFormatter dateTimeFormatter) {
        return TemporalAccessorUtil.format(localDateTime, dateTimeFormatter);
    }

    public static String formatNormal(LocalDateTime localDateTime) {
        return format(localDateTime, DatePattern.NORM_DATETIME_FORMATTER);
    }

    public static boolean isIn(ChronoLocalDateTime<?> chronoLocalDateTime, ChronoLocalDateTime<?> chronoLocalDateTime2, ChronoLocalDateTime<?> chronoLocalDateTime3) {
        return TemporalAccessorUtil.isIn(chronoLocalDateTime, chronoLocalDateTime2, chronoLocalDateTime3);
    }

    public static boolean isOverlap(ChronoLocalDateTime<?> chronoLocalDateTime, ChronoLocalDateTime<?> chronoLocalDateTime2, ChronoLocalDateTime<?> chronoLocalDateTime3, ChronoLocalDateTime<?> chronoLocalDateTime4) {
        return chronoLocalDateTime.compareTo((ChronoLocalDateTime<?>) chronoLocalDateTime4) <= 0 && chronoLocalDateTime3.compareTo((ChronoLocalDateTime<?>) chronoLocalDateTime2) <= 0;
    }

    public static boolean isSameDay(LocalDateTime localDateTime, LocalDateTime localDateTime2) {
        return (localDateTime == null || localDateTime2 == null || !isSameDay(localDateTime.toLocalDate(), localDateTime2.toLocalDate())) ? false : true;
    }

    public static boolean isWeekend(LocalDateTime localDateTime) {
        return isWeekend(localDateTime.toLocalDate());
    }

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static LocalDateTime of(Instant instant) {
        return of(instant, ZoneId.systemDefault());
    }

    public static LocalDate ofDate(TemporalAccessor temporalAccessor) {
        if (temporalAccessor == null) {
            return null;
        }
        return temporalAccessor instanceof LocalDateTime ? ((LocalDateTime) temporalAccessor).toLocalDate() : temporalAccessor instanceof Instant ? of(temporalAccessor).toLocalDate() : LocalDate.of(TemporalAccessorUtil.get(temporalAccessor, ChronoField.YEAR), TemporalAccessorUtil.get(temporalAccessor, ChronoField.MONTH_OF_YEAR), TemporalAccessorUtil.get(temporalAccessor, ChronoField.DAY_OF_MONTH));
    }

    public static LocalDateTime ofUTC(Instant instant) {
        return of(instant, ZoneId.of("UTC"));
    }

    public static LocalDateTime offset(LocalDateTime localDateTime, long j2, TemporalUnit temporalUnit) {
        return (LocalDateTime) TemporalUtil.offset(localDateTime, j2, temporalUnit);
    }

    public static LocalDateTime parse(CharSequence charSequence) {
        return parse(charSequence, (DateTimeFormatter) null);
    }

    public static LocalDate parseDate(CharSequence charSequence) {
        return parseDate(charSequence, (DateTimeFormatter) null);
    }

    public static long toEpochMilli(TemporalAccessor temporalAccessor) {
        return TemporalAccessorUtil.toEpochMilli(temporalAccessor);
    }

    public static int weekOfYear(TemporalAccessor temporalAccessor) {
        return TemporalAccessorUtil.get(temporalAccessor, WeekFields.ISO.weekOfYear());
    }

    public static long between(LocalDateTime localDateTime, LocalDateTime localDateTime2, ChronoUnit chronoUnit) {
        return TemporalUtil.between(localDateTime, localDateTime2, chronoUnit);
    }

    public static LocalDateTime endOfDay(LocalDateTime localDateTime, boolean z2) {
        return z2 ? localDateTime.with((TemporalAdjuster) LocalTime.of(23, 59, 59)) : localDateTime.with((TemporalAdjuster) LocalTime.MAX);
    }

    public static String format(LocalDateTime localDateTime, String str) {
        return TemporalAccessorUtil.format(localDateTime, str);
    }

    public static String formatNormal(LocalDate localDate) {
        return format(localDate, DatePattern.NORM_DATE_FORMATTER);
    }

    public static boolean isIn(ChronoLocalDateTime<?> chronoLocalDateTime, ChronoLocalDateTime<?> chronoLocalDateTime2, ChronoLocalDateTime<?> chronoLocalDateTime3, boolean z2, boolean z3) {
        return TemporalAccessorUtil.isIn(chronoLocalDateTime, chronoLocalDateTime2, chronoLocalDateTime3, z2, z3);
    }

    public static boolean isSameDay(LocalDate localDate, LocalDate localDate2) {
        return (localDate == null || localDate2 == null || !localDate.isEqual(localDate2)) ? false : true;
    }

    public static boolean isWeekend(LocalDate localDate) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return DayOfWeek.SATURDAY == dayOfWeek || DayOfWeek.SUNDAY == dayOfWeek;
    }

    public static LocalDateTime of(ZonedDateTime zonedDateTime) {
        if (zonedDateTime == null) {
            return null;
        }
        return zonedDateTime.toLocalDateTime();
    }

    public static LocalDateTime ofUTC(long j2) {
        return ofUTC(Instant.ofEpochMilli(j2));
    }

    public static LocalDateTime parse(CharSequence charSequence, DateTimeFormatter dateTimeFormatter) {
        if (CharSequenceUtil.isBlank(charSequence)) {
            return null;
        }
        return dateTimeFormatter == null ? LocalDateTime.parse(charSequence) : of(dateTimeFormatter.parse(charSequence));
    }

    public static LocalDate parseDate(CharSequence charSequence, DateTimeFormatter dateTimeFormatter) {
        if (charSequence == null) {
            return null;
        }
        return dateTimeFormatter == null ? LocalDate.parse(charSequence) : ofDate(dateTimeFormatter.parse(charSequence));
    }

    public static String format(LocalDate localDate, DateTimeFormatter dateTimeFormatter) {
        return TemporalAccessorUtil.format(localDate, dateTimeFormatter);
    }

    public static LocalDateTime of(Instant instant, ZoneId zoneId) {
        if (instant == null) {
            return null;
        }
        return LocalDateTime.ofInstant(instant, (ZoneId) ObjectUtil.defaultIfNull(zoneId, (Supplier<? extends ZoneId>) new Supplier() { // from class: cn.hutool.core.date.f1
            @Override // java.util.function.Supplier
            public final Object get() {
                return ZoneId.systemDefault();
            }
        }));
    }

    public static String format(LocalDate localDate, String str) {
        if (localDate == null) {
            return null;
        }
        return format(localDate, DateTimeFormatter.ofPattern(str));
    }

    public static LocalDateTime of(Instant instant, TimeZone timeZone) {
        if (instant == null) {
            return null;
        }
        return of(instant, ((TimeZone) ObjectUtil.defaultIfNull(timeZone, new m())).toZoneId());
    }

    public static LocalDate parseDate(CharSequence charSequence, String str) {
        if (charSequence == null) {
            return null;
        }
        return parseDate(charSequence, DateTimeFormatter.ofPattern(str));
    }

    public static LocalDateTime of(long j2) {
        return of(Instant.ofEpochMilli(j2));
    }

    public static LocalDateTime parse(CharSequence charSequence, String str) {
        DateTimeFormatter dateTimeFormatterOfPattern = null;
        if (CharSequenceUtil.isBlank(charSequence)) {
            return null;
        }
        if (GlobalCustomFormat.isCustomFormat(str)) {
            return of(GlobalCustomFormat.parse(charSequence, str));
        }
        if (CharSequenceUtil.isNotBlank(str)) {
            if (CharSequenceUtil.startWithIgnoreEquals(str, "yyyyMMddHHmmss")) {
                String strRemovePrefix = CharSequenceUtil.removePrefix(str, "yyyyMMddHHmmss");
                if (ReUtil.isMatch("[S]{1,2}", strRemovePrefix)) {
                    charSequence = ((Object) charSequence) + CharSequenceUtil.repeat('0', 3 - strRemovePrefix.length());
                }
                dateTimeFormatterOfPattern = new DateTimeFormatterBuilder().appendPattern("yyyyMMddHHmmss").appendValue(ChronoField.MILLI_OF_SECOND, 3).toFormatter();
            } else {
                dateTimeFormatterOfPattern = DateTimeFormatter.ofPattern(str);
            }
        }
        return parse(charSequence, dateTimeFormatterOfPattern);
    }

    public static LocalDateTime of(long j2, ZoneId zoneId) {
        return of(Instant.ofEpochMilli(j2), zoneId);
    }

    public static LocalDateTime of(long j2, TimeZone timeZone) {
        return of(Instant.ofEpochMilli(j2), timeZone);
    }

    public static LocalDateTime of(Date date) {
        if (date == null) {
            return null;
        }
        if (date instanceof DateTime) {
            return of(date.toInstant(), ((DateTime) date).getZoneId());
        }
        return of(date.toInstant());
    }

    public static LocalDateTime of(TemporalAccessor temporalAccessor) {
        if (temporalAccessor == null) {
            return null;
        }
        if (temporalAccessor instanceof LocalDate) {
            return ((LocalDate) temporalAccessor).atStartOfDay();
        }
        if (temporalAccessor instanceof Instant) {
            return LocalDateTime.ofInstant((Instant) temporalAccessor, ZoneId.systemDefault());
        }
        try {
            try {
                try {
                    return LocalDateTime.from(temporalAccessor);
                } catch (Exception unused) {
                    return ZonedDateTime.from(temporalAccessor).toLocalDateTime();
                }
            } catch (Exception unused2) {
                return LocalDateTime.of(TemporalAccessorUtil.get(temporalAccessor, ChronoField.YEAR), TemporalAccessorUtil.get(temporalAccessor, ChronoField.MONTH_OF_YEAR), TemporalAccessorUtil.get(temporalAccessor, ChronoField.DAY_OF_MONTH), TemporalAccessorUtil.get(temporalAccessor, ChronoField.HOUR_OF_DAY), TemporalAccessorUtil.get(temporalAccessor, ChronoField.MINUTE_OF_HOUR), TemporalAccessorUtil.get(temporalAccessor, ChronoField.SECOND_OF_MINUTE), TemporalAccessorUtil.get(temporalAccessor, ChronoField.NANO_OF_SECOND));
            }
        } catch (Exception unused3) {
            return LocalDateTime.ofInstant(Instant.from(temporalAccessor), ZoneId.systemDefault());
        }
    }
}
