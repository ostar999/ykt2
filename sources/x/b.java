package x;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.getter.OptNullBasicTypeFromStringGetter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/* loaded from: classes.dex */
public final /* synthetic */ class b<K> {
    public static BigDecimal a(OptNullBasicTypeFromStringGetter optNullBasicTypeFromStringGetter, Object obj, BigDecimal bigDecimal) {
        return Convert.toBigDecimal(optNullBasicTypeFromStringGetter.getStr(obj), bigDecimal);
    }

    public static BigInteger b(OptNullBasicTypeFromStringGetter optNullBasicTypeFromStringGetter, Object obj, BigInteger bigInteger) {
        return Convert.toBigInteger(optNullBasicTypeFromStringGetter.getStr(obj), bigInteger);
    }

    public static Boolean c(OptNullBasicTypeFromStringGetter optNullBasicTypeFromStringGetter, Object obj, Boolean bool) {
        return Convert.toBool(optNullBasicTypeFromStringGetter.getStr(obj), bool);
    }

    public static Byte d(OptNullBasicTypeFromStringGetter optNullBasicTypeFromStringGetter, Object obj, Byte b3) {
        return Convert.toByte(optNullBasicTypeFromStringGetter.getStr(obj), b3);
    }

    public static Character e(OptNullBasicTypeFromStringGetter optNullBasicTypeFromStringGetter, Object obj, Character ch) {
        return Convert.toChar(optNullBasicTypeFromStringGetter.getStr(obj), ch);
    }

    public static Date f(OptNullBasicTypeFromStringGetter optNullBasicTypeFromStringGetter, Object obj, Date date) {
        return Convert.toDate(optNullBasicTypeFromStringGetter.getStr(obj), date);
    }

    public static Double g(OptNullBasicTypeFromStringGetter optNullBasicTypeFromStringGetter, Object obj, Double d3) {
        return Convert.toDouble(optNullBasicTypeFromStringGetter.getStr(obj), d3);
    }

    public static Enum h(OptNullBasicTypeFromStringGetter optNullBasicTypeFromStringGetter, Class cls, Object obj, Enum r3) {
        return Convert.toEnum(cls, optNullBasicTypeFromStringGetter.getStr(obj), r3);
    }

    public static Float i(OptNullBasicTypeFromStringGetter optNullBasicTypeFromStringGetter, Object obj, Float f2) {
        return Convert.toFloat(optNullBasicTypeFromStringGetter.getStr(obj), f2);
    }

    public static Integer j(OptNullBasicTypeFromStringGetter optNullBasicTypeFromStringGetter, Object obj, Integer num) {
        return Convert.toInt(optNullBasicTypeFromStringGetter.getStr(obj), num);
    }

    public static Long k(OptNullBasicTypeFromStringGetter optNullBasicTypeFromStringGetter, Object obj, Long l2) {
        return Convert.toLong(optNullBasicTypeFromStringGetter.getStr(obj), l2);
    }

    public static Object l(OptNullBasicTypeFromStringGetter optNullBasicTypeFromStringGetter, Object obj, Object obj2) {
        return optNullBasicTypeFromStringGetter.getStr(obj, obj2 == null ? null : obj2.toString());
    }

    public static Short m(OptNullBasicTypeFromStringGetter optNullBasicTypeFromStringGetter, Object obj, Short sh) {
        return Convert.toShort(optNullBasicTypeFromStringGetter.getStr(obj), sh);
    }
}
