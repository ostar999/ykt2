package cn.hutool.core.convert.impl;

import cn.hutool.core.convert.AbstractConverter;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ByteUtil;
import cn.hutool.core.util.NumberUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;

/* loaded from: classes.dex */
public class NumberConverter extends AbstractConverter<Number> {
    private static final long serialVersionUID = 1;
    private final Class<? extends Number> targetType;

    public NumberConverter() {
        this.targetType = Number.class;
    }

    public static Number convert(Object obj, Class<? extends Number> cls, Function<Object, String> function) {
        if (obj instanceof Enum) {
            return convert(Integer.valueOf(((Enum) obj).ordinal()), cls, function);
        }
        if (obj instanceof byte[]) {
            return ByteUtil.bytesToNumber((byte[]) obj, cls, ByteUtil.DEFAULT_ORDER);
        }
        if (Byte.class == cls) {
            if (obj instanceof Number) {
                return Byte.valueOf(((Number) obj).byteValue());
            }
            if (obj instanceof Boolean) {
                return BooleanUtil.toByteObj(((Boolean) obj).booleanValue());
            }
            String str = (String) function.apply(obj);
            try {
                if (CharSequenceUtil.isBlank(str)) {
                    return null;
                }
                return Byte.valueOf(str);
            } catch (NumberFormatException unused) {
                return Byte.valueOf(NumberUtil.parseNumber(str).byteValue());
            }
        }
        if (Short.class == cls) {
            if (obj instanceof Number) {
                return Short.valueOf(((Number) obj).shortValue());
            }
            if (obj instanceof Boolean) {
                return BooleanUtil.toShortObj(((Boolean) obj).booleanValue());
            }
            String str2 = (String) function.apply(obj);
            try {
                if (CharSequenceUtil.isBlank(str2)) {
                    return null;
                }
                return Short.valueOf(str2);
            } catch (NumberFormatException unused2) {
                return Short.valueOf(NumberUtil.parseNumber(str2).shortValue());
            }
        }
        if (Integer.class == cls) {
            if (obj instanceof Number) {
                return Integer.valueOf(((Number) obj).intValue());
            }
            if (obj instanceof Boolean) {
                return BooleanUtil.toInteger(((Boolean) obj).booleanValue());
            }
            if (obj instanceof Date) {
                return Integer.valueOf((int) ((Date) obj).getTime());
            }
            if (obj instanceof Calendar) {
                return Integer.valueOf((int) ((Calendar) obj).getTimeInMillis());
            }
            if (obj instanceof TemporalAccessor) {
                return Integer.valueOf((int) DateUtil.toInstant((TemporalAccessor) obj).toEpochMilli());
            }
            String str3 = (String) function.apply(obj);
            if (CharSequenceUtil.isBlank(str3)) {
                return null;
            }
            return Integer.valueOf(NumberUtil.parseInt(str3));
        }
        if (AtomicInteger.class == cls) {
            Number numberConvert = convert(obj, Integer.class, function);
            if (numberConvert != null) {
                return new AtomicInteger(numberConvert.intValue());
            }
        } else {
            if (Long.class == cls) {
                if (obj instanceof Number) {
                    return Long.valueOf(((Number) obj).longValue());
                }
                if (obj instanceof Boolean) {
                    return BooleanUtil.toLongObj(((Boolean) obj).booleanValue());
                }
                if (obj instanceof Date) {
                    return Long.valueOf(((Date) obj).getTime());
                }
                if (obj instanceof Calendar) {
                    return Long.valueOf(((Calendar) obj).getTimeInMillis());
                }
                if (obj instanceof TemporalAccessor) {
                    return Long.valueOf(DateUtil.toInstant((TemporalAccessor) obj).toEpochMilli());
                }
                String str4 = (String) function.apply(obj);
                if (CharSequenceUtil.isBlank(str4)) {
                    return null;
                }
                return Long.valueOf(NumberUtil.parseLong(str4));
            }
            if (AtomicLong.class == cls) {
                Number numberConvert2 = convert(obj, Long.class, function);
                if (numberConvert2 != null) {
                    return new AtomicLong(numberConvert2.longValue());
                }
            } else if (LongAdder.class == cls) {
                Number numberConvert3 = convert(obj, Long.class, function);
                if (numberConvert3 != null) {
                    LongAdder longAdder = new LongAdder();
                    longAdder.add(numberConvert3.longValue());
                    return longAdder;
                }
            } else {
                if (Float.class == cls) {
                    if (obj instanceof Number) {
                        return Float.valueOf(((Number) obj).floatValue());
                    }
                    if (obj instanceof Boolean) {
                        return BooleanUtil.toFloatObj(((Boolean) obj).booleanValue());
                    }
                    String str5 = (String) function.apply(obj);
                    if (CharSequenceUtil.isBlank(str5)) {
                        return null;
                    }
                    return Float.valueOf(NumberUtil.parseFloat(str5));
                }
                if (Double.class == cls) {
                    if (obj instanceof Number) {
                        return Double.valueOf(NumberUtil.toDouble((Number) obj));
                    }
                    if (obj instanceof Boolean) {
                        return BooleanUtil.toDoubleObj(((Boolean) obj).booleanValue());
                    }
                    String str6 = (String) function.apply(obj);
                    if (CharSequenceUtil.isBlank(str6)) {
                        return null;
                    }
                    return Double.valueOf(NumberUtil.parseDouble(str6));
                }
                if (DoubleAdder.class == cls) {
                    Number numberConvert4 = convert(obj, Double.class, function);
                    if (numberConvert4 != null) {
                        DoubleAdder doubleAdder = new DoubleAdder();
                        doubleAdder.add(numberConvert4.doubleValue());
                        return doubleAdder;
                    }
                } else {
                    if (BigDecimal.class == cls) {
                        return toBigDecimal(obj, function);
                    }
                    if (BigInteger.class == cls) {
                        return toBigInteger(obj, function);
                    }
                    if (Number.class == cls) {
                        if (obj instanceof Number) {
                            return (Number) obj;
                        }
                        if (obj instanceof Boolean) {
                            return BooleanUtil.toInteger(((Boolean) obj).booleanValue());
                        }
                        String str7 = (String) function.apply(obj);
                        if (CharSequenceUtil.isBlank(str7)) {
                            return null;
                        }
                        return NumberUtil.parseNumber(str7);
                    }
                }
            }
        }
        throw new UnsupportedOperationException(CharSequenceUtil.format("Unsupport Number type: {}", cls.getName()));
    }

    private static BigDecimal toBigDecimal(Object obj, Function<Object, String> function) {
        return obj instanceof Number ? NumberUtil.toBigDecimal((Number) obj) : obj instanceof Boolean ? ((Boolean) obj).booleanValue() ? BigDecimal.ONE : BigDecimal.ZERO : NumberUtil.toBigDecimal((String) function.apply(obj));
    }

    private static BigInteger toBigInteger(Object obj, Function<Object, String> function) {
        return obj instanceof Long ? BigInteger.valueOf(((Long) obj).longValue()) : obj instanceof Boolean ? ((Boolean) obj).booleanValue() ? BigInteger.ONE : BigInteger.ZERO : NumberUtil.toBigInteger((String) function.apply(obj));
    }

    @Override // cn.hutool.core.convert.AbstractConverter
    public String convertToStr(Object obj) {
        String strTrim = CharSequenceUtil.trim(super.convertToStr(obj));
        if (strTrim == null || strTrim.length() <= 1) {
            return strTrim;
        }
        char upperCase = Character.toUpperCase(strTrim.charAt(strTrim.length() - 1));
        return (upperCase == 'D' || upperCase == 'L' || upperCase == 'F') ? CharSequenceUtil.subPre(strTrim, -1) : strTrim;
    }

    @Override // cn.hutool.core.convert.AbstractConverter
    public Class<Number> getTargetType() {
        return this.targetType;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // cn.hutool.core.convert.AbstractConverter
    public Number convertInternal(Object obj) {
        return convert(obj, this.targetType, new Function() { // from class: v.r
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                return this.f28249c.convertToStr(obj2);
            }
        });
    }

    /* JADX WARN: Incorrect type for immutable var: ssa=java.lang.Class<? extends java.lang.Number>, code=java.lang.Class, for r1v0, types: [java.lang.Class<? extends java.lang.Number>] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public NumberConverter(java.lang.Class r1) {
        /*
            r0 = this;
            r0.<init>()
            if (r1 != 0) goto L7
            java.lang.Class<java.lang.Number> r1 = java.lang.Number.class
        L7:
            r0.targetType = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.hutool.core.convert.impl.NumberConverter.<init>(java.lang.Class):void");
    }
}
