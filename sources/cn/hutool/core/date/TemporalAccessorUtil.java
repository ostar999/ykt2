package cn.hutool.core.date;

import cn.hutool.core.date.format.GlobalCustomFormat;
import cn.hutool.core.text.CharSequenceUtil;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.Era;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.UnsupportedTemporalTypeException;

/* loaded from: classes.dex */
public class TemporalAccessorUtil extends TemporalUtil {
    public static String format(TemporalAccessor temporalAccessor, DateTimeFormatter dateTimeFormatter) {
        if (temporalAccessor == null) {
            return null;
        }
        if (temporalAccessor instanceof java.time.Month) {
            return temporalAccessor.toString();
        }
        if (dateTimeFormatter == null) {
            dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        }
        try {
            return dateTimeFormatter.format(temporalAccessor);
        } catch (UnsupportedTemporalTypeException e2) {
            if ((temporalAccessor instanceof LocalDate) && e2.getMessage().contains("HourOfDay")) {
                return dateTimeFormatter.format(((LocalDate) temporalAccessor).atStartOfDay());
            }
            if ((temporalAccessor instanceof LocalTime) && e2.getMessage().contains("YearOfEra")) {
                return dateTimeFormatter.format(((LocalTime) temporalAccessor).atDate(LocalDate.now()));
            }
            if (temporalAccessor instanceof Instant) {
                return dateTimeFormatter.format(((Instant) temporalAccessor).atZone(ZoneId.systemDefault()));
            }
            throw e2;
        }
    }

    public static int get(TemporalAccessor temporalAccessor, TemporalField temporalField) {
        return temporalAccessor.isSupported(temporalField) ? temporalAccessor.get(temporalField) : (int) temporalField.range().getMinimum();
    }

    public static boolean isIn(TemporalAccessor temporalAccessor, TemporalAccessor temporalAccessor2, TemporalAccessor temporalAccessor3) {
        return isIn(temporalAccessor, temporalAccessor2, temporalAccessor3, true, true);
    }

    public static long toEpochMilli(TemporalAccessor temporalAccessor) {
        return temporalAccessor instanceof java.time.Month ? ((java.time.Month) temporalAccessor).getValue() : temporalAccessor instanceof DayOfWeek ? ((DayOfWeek) temporalAccessor).getValue() : temporalAccessor instanceof Era ? ((Era) temporalAccessor).getValue() : toInstant(temporalAccessor).toEpochMilli();
    }

    public static Instant toInstant(TemporalAccessor temporalAccessor) {
        if (temporalAccessor == null) {
            return null;
        }
        return temporalAccessor instanceof Instant ? (Instant) temporalAccessor : temporalAccessor instanceof LocalDateTime ? ((LocalDateTime) temporalAccessor).atZone(ZoneId.systemDefault()).toInstant() : temporalAccessor instanceof ZonedDateTime ? ((ZonedDateTime) temporalAccessor).toInstant() : temporalAccessor instanceof OffsetDateTime ? ((OffsetDateTime) temporalAccessor).toInstant() : temporalAccessor instanceof LocalDate ? ((LocalDate) temporalAccessor).atStartOfDay(ZoneId.systemDefault()).toInstant() : temporalAccessor instanceof LocalTime ? ((LocalTime) temporalAccessor).atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant() : temporalAccessor instanceof OffsetTime ? ((OffsetTime) temporalAccessor).atDate(LocalDate.now()).toInstant() : toInstant(LocalDateTimeUtil.of(temporalAccessor));
    }

    public static boolean isIn(TemporalAccessor temporalAccessor, TemporalAccessor temporalAccessor2, TemporalAccessor temporalAccessor3, boolean z2, boolean z3) {
        if (temporalAccessor == null || temporalAccessor2 == null || temporalAccessor3 == null) {
            throw new IllegalArgumentException("参数不可为null");
        }
        long epochMilli = toEpochMilli(temporalAccessor);
        long epochMilli2 = toEpochMilli(temporalAccessor2);
        long epochMilli3 = toEpochMilli(temporalAccessor3);
        long jMin = Math.min(epochMilli2, epochMilli3);
        long jMax = Math.max(epochMilli2, epochMilli3);
        boolean z4 = jMin < epochMilli && epochMilli < jMax;
        if (!z4 && z2) {
            z4 = epochMilli == jMin;
        }
        if (z4 || !z3) {
            return z4;
        }
        return epochMilli == jMax;
    }

    public static String format(TemporalAccessor temporalAccessor, String str) {
        if (temporalAccessor == null) {
            return null;
        }
        if (!(temporalAccessor instanceof DayOfWeek) && !(temporalAccessor instanceof java.time.Month) && !(temporalAccessor instanceof Era) && !(temporalAccessor instanceof MonthDay)) {
            if (GlobalCustomFormat.isCustomFormat(str)) {
                return GlobalCustomFormat.format(temporalAccessor, str);
            }
            return format(temporalAccessor, CharSequenceUtil.isBlank(str) ? null : DateTimeFormatter.ofPattern(str));
        }
        return temporalAccessor.toString();
    }
}
