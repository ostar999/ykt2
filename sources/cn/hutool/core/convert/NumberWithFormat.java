package cn.hutool.core.convert;

import cn.hutool.core.convert.impl.DateConverter;
import cn.hutool.core.convert.impl.TemporalAccessorConverter;
import java.lang.reflect.Type;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/* loaded from: classes.dex */
public class NumberWithFormat extends Number implements TypeConverter {
    private static final long serialVersionUID = 1;
    private final String format;
    private final Number number;

    public NumberWithFormat(Number number, String str) {
        this.number = number;
        this.format = str;
    }

    @Override // cn.hutool.core.convert.TypeConverter
    public Object convert(Type type, Object obj) {
        if (this.format != null && (type instanceof Class)) {
            Class cls = (Class) type;
            if (Date.class.isAssignableFrom(cls)) {
                return new DateConverter(cls, this.format).convert(this.number, null);
            }
            if (TemporalAccessor.class.isAssignableFrom(cls)) {
                return new TemporalAccessorConverter(cls, this.format).convert(this.number, null);
            }
            if (String.class == cls) {
                return toString();
            }
        }
        return Convert.convertWithCheck(type, this.number, null, false);
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return this.number.doubleValue();
    }

    @Override // java.lang.Number
    public float floatValue() {
        return this.number.floatValue();
    }

    @Override // java.lang.Number
    public int intValue() {
        return this.number.intValue();
    }

    @Override // java.lang.Number
    public long longValue() {
        return this.number.longValue();
    }

    public String toString() {
        return this.number.toString();
    }
}
