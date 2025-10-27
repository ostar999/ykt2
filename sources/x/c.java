package x;

import cn.hutool.core.getter.OptNullBasicTypeGetter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/* loaded from: classes.dex */
public final /* synthetic */ class c<K> {
    public static BigDecimal a(OptNullBasicTypeGetter optNullBasicTypeGetter, Object obj) {
        return optNullBasicTypeGetter.getBigDecimal(obj, null);
    }

    public static BigInteger b(OptNullBasicTypeGetter optNullBasicTypeGetter, Object obj) {
        return optNullBasicTypeGetter.getBigInteger(obj, null);
    }

    public static Boolean c(OptNullBasicTypeGetter optNullBasicTypeGetter, Object obj) {
        return optNullBasicTypeGetter.getBool(obj, null);
    }

    public static Byte d(OptNullBasicTypeGetter optNullBasicTypeGetter, Object obj) {
        return optNullBasicTypeGetter.getByte(obj, null);
    }

    public static Character e(OptNullBasicTypeGetter optNullBasicTypeGetter, Object obj) {
        return optNullBasicTypeGetter.getChar(obj, null);
    }

    public static Date f(OptNullBasicTypeGetter optNullBasicTypeGetter, Object obj) {
        return optNullBasicTypeGetter.getDate(obj, null);
    }

    public static Double g(OptNullBasicTypeGetter optNullBasicTypeGetter, Object obj) {
        return optNullBasicTypeGetter.getDouble(obj, null);
    }

    public static Enum h(OptNullBasicTypeGetter optNullBasicTypeGetter, Class cls, Object obj) {
        return optNullBasicTypeGetter.getEnum(cls, obj, null);
    }

    public static Float i(OptNullBasicTypeGetter optNullBasicTypeGetter, Object obj) {
        return optNullBasicTypeGetter.getFloat(obj, null);
    }

    public static Integer j(OptNullBasicTypeGetter optNullBasicTypeGetter, Object obj) {
        return optNullBasicTypeGetter.getInt(obj, null);
    }

    public static Long k(OptNullBasicTypeGetter optNullBasicTypeGetter, Object obj) {
        return optNullBasicTypeGetter.getLong(obj, null);
    }

    public static Object l(OptNullBasicTypeGetter optNullBasicTypeGetter, Object obj) {
        return optNullBasicTypeGetter.getObj(obj, null);
    }

    public static Short m(OptNullBasicTypeGetter optNullBasicTypeGetter, Object obj) {
        return optNullBasicTypeGetter.getShort(obj, null);
    }

    public static String n(OptNullBasicTypeGetter optNullBasicTypeGetter, Object obj) {
        return optNullBasicTypeGetter.getStr(obj, null);
    }
}
