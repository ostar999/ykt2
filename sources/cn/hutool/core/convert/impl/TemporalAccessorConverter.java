package cn.hutool.core.convert.impl;

import cn.hutool.core.convert.AbstractConverter;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.convert.ConvertException;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.format.GlobalCustomFormat;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.IsoEra;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import v.h1;

/* loaded from: classes.dex */
public class TemporalAccessorConverter extends AbstractConverter<TemporalAccessor> {
    private static final long serialVersionUID = 1;
    private String format;
    private final Class<?> targetType;

    public TemporalAccessorConverter(Class<?> cls) {
        this(cls, null);
    }

    private TemporalAccessor parseFromCharSequence(CharSequence charSequence) {
        ZoneId zoneId;
        Instant instant;
        if (CharSequenceUtil.isBlank(charSequence)) {
            return null;
        }
        if (DayOfWeek.class.equals(this.targetType)) {
            return DayOfWeek.valueOf(StrUtil.toString(charSequence));
        }
        if (Month.class.equals(this.targetType)) {
            return Month.valueOf(StrUtil.toString(charSequence));
        }
        if (Era.class.equals(this.targetType)) {
            return IsoEra.valueOf(StrUtil.toString(charSequence));
        }
        if (MonthDay.class.equals(this.targetType)) {
            return MonthDay.parse(charSequence);
        }
        String str = this.format;
        if (str != null) {
            DateTimeFormatter dateTimeFormatterOfPattern = DateTimeFormatter.ofPattern(str);
            instant = (Instant) dateTimeFormatterOfPattern.parse(charSequence, new TemporalQuery() { // from class: v.j1
                @Override // java.time.temporal.TemporalQuery
                public final Object queryFrom(TemporalAccessor temporalAccessor) {
                    return Instant.from(temporalAccessor);
                }
            });
            zoneId = dateTimeFormatterOfPattern.getZone();
        } else {
            DateTime dateTime = DateUtil.parse(charSequence);
            Objects.requireNonNull(dateTime);
            Instant instant2 = dateTime.toInstant();
            zoneId = dateTime.getZoneId();
            instant = instant2;
        }
        return parseFromInstant(instant, zoneId);
    }

    private TemporalAccessor parseFromInstant(Instant instant, ZoneId zoneId) {
        if (Instant.class.equals(this.targetType)) {
            return instant;
        }
        ZoneId zoneId2 = (ZoneId) ObjectUtil.defaultIfNull(zoneId, (Supplier<? extends ZoneId>) new Supplier() { // from class: v.i1
            @Override // java.util.function.Supplier
            public final Object get() {
                return ZoneId.systemDefault();
            }
        });
        if (LocalDateTime.class.equals(this.targetType)) {
            return LocalDateTime.ofInstant(instant, zoneId2);
        }
        if (LocalDate.class.equals(this.targetType)) {
            return instant.atZone(zoneId2).toLocalDate();
        }
        if (LocalTime.class.equals(this.targetType)) {
            return instant.atZone(zoneId2).toLocalTime();
        }
        if (ZonedDateTime.class.equals(this.targetType)) {
            return instant.atZone(zoneId2);
        }
        if (OffsetDateTime.class.equals(this.targetType)) {
            return OffsetDateTime.ofInstant(instant, zoneId2);
        }
        if (OffsetTime.class.equals(this.targetType)) {
            return OffsetTime.ofInstant(instant, zoneId2);
        }
        return null;
    }

    private TemporalAccessor parseFromLocalDateTime(LocalDateTime localDateTime) {
        if (Instant.class.equals(this.targetType)) {
            return DateUtil.toInstant(localDateTime);
        }
        if (LocalDate.class.equals(this.targetType)) {
            return localDateTime.toLocalDate();
        }
        if (LocalTime.class.equals(this.targetType)) {
            return localDateTime.toLocalTime();
        }
        if (ZonedDateTime.class.equals(this.targetType)) {
            return localDateTime.atZone(ZoneId.systemDefault());
        }
        if (OffsetDateTime.class.equals(this.targetType)) {
            return localDateTime.atZone(ZoneId.systemDefault()).toOffsetDateTime();
        }
        if (OffsetTime.class.equals(this.targetType)) {
            return localDateTime.atZone(ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
        }
        return null;
    }

    private TemporalAccessor parseFromLong(Long l2) {
        if (DayOfWeek.class.equals(this.targetType)) {
            return DayOfWeek.of(h1.a(l2.longValue()));
        }
        if (Month.class.equals(this.targetType)) {
            return Month.of(h1.a(l2.longValue()));
        }
        if (Era.class.equals(this.targetType)) {
            return IsoEra.of(h1.a(l2.longValue()));
        }
        return parseFromInstant(GlobalCustomFormat.FORMAT_SECONDS.equals(this.format) ? Instant.ofEpochSecond(l2.longValue()) : Instant.ofEpochMilli(l2.longValue()), null);
    }

    private TemporalAccessor parseFromTemporalAccessor(TemporalAccessor temporalAccessor) {
        if (DayOfWeek.class.equals(this.targetType)) {
            return DayOfWeek.from(temporalAccessor);
        }
        if (Month.class.equals(this.targetType)) {
            return Month.from(temporalAccessor);
        }
        if (MonthDay.class.equals(this.targetType)) {
            return MonthDay.from(temporalAccessor);
        }
        TemporalAccessor fromLocalDateTime = temporalAccessor instanceof LocalDateTime ? parseFromLocalDateTime((LocalDateTime) temporalAccessor) : temporalAccessor instanceof ZonedDateTime ? parseFromZonedDateTime((ZonedDateTime) temporalAccessor) : null;
        return fromLocalDateTime == null ? parseFromInstant(DateUtil.toInstant(temporalAccessor), null) : fromLocalDateTime;
    }

    private TemporalAccessor parseFromZonedDateTime(ZonedDateTime zonedDateTime) {
        if (Instant.class.equals(this.targetType)) {
            return DateUtil.toInstant(zonedDateTime);
        }
        if (LocalDateTime.class.equals(this.targetType)) {
            return zonedDateTime.toLocalDateTime();
        }
        if (LocalDate.class.equals(this.targetType)) {
            return zonedDateTime.toLocalDate();
        }
        if (LocalTime.class.equals(this.targetType)) {
            return zonedDateTime.toLocalTime();
        }
        if (OffsetDateTime.class.equals(this.targetType)) {
            return zonedDateTime.toOffsetDateTime();
        }
        if (OffsetTime.class.equals(this.targetType)) {
            return zonedDateTime.toOffsetDateTime().toOffsetTime();
        }
        return null;
    }

    public String getFormat() {
        return this.format;
    }

    @Override // cn.hutool.core.convert.AbstractConverter
    public Class<TemporalAccessor> getTargetType() {
        return this.targetType;
    }

    public void setFormat(String str) {
        this.format = str;
    }

    public TemporalAccessorConverter(Class<?> cls, String str) {
        this.targetType = cls;
        this.format = str;
    }

    @Override // cn.hutool.core.convert.AbstractConverter
    public TemporalAccessor convertInternal(Object obj) {
        if (obj instanceof Number) {
            return parseFromLong(Long.valueOf(((Number) obj).longValue()));
        }
        if (obj instanceof TemporalAccessor) {
            return parseFromTemporalAccessor((TemporalAccessor) obj);
        }
        if (obj instanceof Date) {
            DateTime dateTimeDate = DateUtil.date((Date) obj);
            return parseFromInstant(dateTimeDate.toInstant(), dateTimeDate.getZoneId());
        }
        if (obj instanceof Calendar) {
            Calendar calendar = (Calendar) obj;
            return parseFromInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId());
        }
        if (!(obj instanceof Map)) {
            return parseFromCharSequence(convertToStr(obj));
        }
        Map map = (Map) obj;
        if (LocalDate.class.equals(this.targetType)) {
            return LocalDate.of(Convert.toInt(map.get("year")).intValue(), Convert.toInt(map.get("month")).intValue(), Convert.toInt(map.get("day")).intValue());
        }
        if (LocalDateTime.class.equals(this.targetType)) {
            return LocalDateTime.of(Convert.toInt(map.get("year")).intValue(), Convert.toInt(map.get("month")).intValue(), Convert.toInt(map.get("day")).intValue(), Convert.toInt(map.get("hour")).intValue(), Convert.toInt(map.get("minute")).intValue(), Convert.toInt(map.get("second")).intValue(), Convert.toInt(map.get("second")).intValue());
        }
        if (LocalTime.class.equals(this.targetType)) {
            return LocalTime.of(Convert.toInt(map.get("hour")).intValue(), Convert.toInt(map.get("minute")).intValue(), Convert.toInt(map.get("second")).intValue(), Convert.toInt(map.get("nano")).intValue());
        }
        throw new ConvertException("Unsupported type: [{}] from map: [{}]", this.targetType, map);
    }
}
