package cn.hutool.core.convert.impl;

import cn.hutool.core.convert.AbstractConverter;
import cn.hutool.core.convert.ConvertException;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.format.GlobalCustomFormat;
import cn.hutool.core.text.CharSequenceUtil;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;

/* loaded from: classes.dex */
public class DateConverter extends AbstractConverter<Date> {
    private static final long serialVersionUID = 1;
    private String format;
    private final Class<? extends Date> targetType;

    public DateConverter(Class<? extends Date> cls) {
        this.targetType = cls;
    }

    private Date wrap(DateTime dateTime) {
        Class<? extends Date> cls = this.targetType;
        if (Date.class == cls) {
            return dateTime.toJdkDate();
        }
        if (DateTime.class == cls) {
            return dateTime;
        }
        if (java.sql.Date.class == cls) {
            return dateTime.toSqlDate();
        }
        if (Time.class == cls) {
            return new Time(dateTime.getTime());
        }
        if (Timestamp.class == cls) {
            return dateTime.toTimestamp();
        }
        throw new UnsupportedOperationException(CharSequenceUtil.format("Unsupported target Date type: {}", this.targetType.getName()));
    }

    public String getFormat() {
        return this.format;
    }

    @Override // cn.hutool.core.convert.AbstractConverter
    public Class<Date> getTargetType() {
        return this.targetType;
    }

    public void setFormat(String str) {
        this.format = str;
    }

    @Override // cn.hutool.core.convert.AbstractConverter
    public Date convertInternal(Object obj) {
        if (obj == null) {
            return null;
        }
        if ((obj instanceof CharSequence) && CharSequenceUtil.isBlank(obj.toString())) {
            return null;
        }
        if (obj instanceof TemporalAccessor) {
            return wrap(DateUtil.date((TemporalAccessor) obj));
        }
        if (obj instanceof Calendar) {
            return wrap(DateUtil.date((Calendar) obj));
        }
        if (obj instanceof Number) {
            return wrap(((Number) obj).longValue());
        }
        String strConvertToStr = convertToStr(obj);
        DateTime dateTime = CharSequenceUtil.isBlank(this.format) ? DateUtil.parse(strConvertToStr) : DateUtil.parse(strConvertToStr, this.format);
        if (dateTime != null) {
            return wrap(dateTime);
        }
        throw new ConvertException("Can not convert {}:[{}] to {}", obj.getClass().getName(), obj, this.targetType.getName());
    }

    public DateConverter(Class<? extends Date> cls, String str) {
        this.targetType = cls;
        this.format = str;
    }

    private Date wrap(long j2) {
        if (GlobalCustomFormat.FORMAT_SECONDS.equals(this.format)) {
            return DateUtil.date(j2 * 1000);
        }
        Class<? extends Date> cls = this.targetType;
        if (Date.class == cls) {
            return new Date(j2);
        }
        if (DateTime.class == cls) {
            return DateUtil.date(j2);
        }
        if (java.sql.Date.class == cls) {
            return new java.sql.Date(j2);
        }
        if (Time.class == cls) {
            return new Time(j2);
        }
        if (Timestamp.class == cls) {
            return new Timestamp(j2);
        }
        throw new UnsupportedOperationException(CharSequenceUtil.format("Unsupported target Date type: {}", this.targetType.getName()));
    }
}
