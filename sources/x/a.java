package x;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.getter.OptNullBasicTypeFromObjectGetter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/* loaded from: classes.dex */
public final /* synthetic */ class a<K> {
    public static BigDecimal a(OptNullBasicTypeFromObjectGetter optNullBasicTypeFromObjectGetter, Object obj, BigDecimal bigDecimal) {
        Object obj2 = optNullBasicTypeFromObjectGetter.getObj(obj);
        return obj2 == null ? bigDecimal : Convert.toBigDecimal(obj2, bigDecimal);
    }

    public static BigInteger b(OptNullBasicTypeFromObjectGetter optNullBasicTypeFromObjectGetter, Object obj, BigInteger bigInteger) {
        Object obj2 = optNullBasicTypeFromObjectGetter.getObj(obj);
        return obj2 == null ? bigInteger : Convert.toBigInteger(obj2, bigInteger);
    }

    public static Boolean c(OptNullBasicTypeFromObjectGetter optNullBasicTypeFromObjectGetter, Object obj, Boolean bool) {
        Object obj2 = optNullBasicTypeFromObjectGetter.getObj(obj);
        return obj2 == null ? bool : Convert.toBool(obj2, bool);
    }

    public static Byte d(OptNullBasicTypeFromObjectGetter optNullBasicTypeFromObjectGetter, Object obj, Byte b3) {
        Object obj2 = optNullBasicTypeFromObjectGetter.getObj(obj);
        return obj2 == null ? b3 : Convert.toByte(obj2, b3);
    }

    public static Character e(OptNullBasicTypeFromObjectGetter optNullBasicTypeFromObjectGetter, Object obj, Character ch) {
        Object obj2 = optNullBasicTypeFromObjectGetter.getObj(obj);
        return obj2 == null ? ch : Convert.toChar(obj2, ch);
    }

    public static Date f(OptNullBasicTypeFromObjectGetter optNullBasicTypeFromObjectGetter, Object obj, Date date) {
        Object obj2 = optNullBasicTypeFromObjectGetter.getObj(obj);
        return obj2 == null ? date : Convert.toDate(obj2, date);
    }

    public static Double g(OptNullBasicTypeFromObjectGetter optNullBasicTypeFromObjectGetter, Object obj, Double d3) {
        Object obj2 = optNullBasicTypeFromObjectGetter.getObj(obj);
        return obj2 == null ? d3 : Convert.toDouble(obj2, d3);
    }

    public static Enum h(OptNullBasicTypeFromObjectGetter optNullBasicTypeFromObjectGetter, Class cls, Object obj, Enum r3) {
        Object obj2 = optNullBasicTypeFromObjectGetter.getObj(obj);
        return obj2 == null ? r3 : Convert.toEnum(cls, obj2, r3);
    }

    public static Float i(OptNullBasicTypeFromObjectGetter optNullBasicTypeFromObjectGetter, Object obj, Float f2) {
        Object obj2 = optNullBasicTypeFromObjectGetter.getObj(obj);
        return obj2 == null ? f2 : Convert.toFloat(obj2, f2);
    }

    public static Integer j(OptNullBasicTypeFromObjectGetter optNullBasicTypeFromObjectGetter, Object obj, Integer num) {
        Object obj2 = optNullBasicTypeFromObjectGetter.getObj(obj);
        return obj2 == null ? num : Convert.toInt(obj2, num);
    }

    public static Long k(OptNullBasicTypeFromObjectGetter optNullBasicTypeFromObjectGetter, Object obj, Long l2) {
        Object obj2 = optNullBasicTypeFromObjectGetter.getObj(obj);
        return obj2 == null ? l2 : Convert.toLong(obj2, l2);
    }

    public static Short l(OptNullBasicTypeFromObjectGetter optNullBasicTypeFromObjectGetter, Object obj, Short sh) {
        Object obj2 = optNullBasicTypeFromObjectGetter.getObj(obj);
        return obj2 == null ? sh : Convert.toShort(obj2, sh);
    }

    public static String m(OptNullBasicTypeFromObjectGetter optNullBasicTypeFromObjectGetter, Object obj, String str) {
        Object obj2 = optNullBasicTypeFromObjectGetter.getObj(obj);
        return obj2 == null ? str : Convert.toStr(obj2, str);
    }
}
