package cn.hutool.core.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.text.CharSequenceUtil;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.caverock.androidsvg.SVGParser;
import java.util.Set;
import kotlinx.coroutines.DebugKt;

/* loaded from: classes.dex */
public class BooleanUtil {
    private static final Set<String> TRUE_SET = CollUtil.newHashSet(k.a.f27523u, "yes", "y", "t", AliyunLogKey.KEY_OBJECT_KEY, "1", DebugKt.DEBUG_PROPERTY_VALUE_ON, "是", "对", "真", "對", "√");
    private static final Set<String> FALSE_SET = CollUtil.newHashSet(k.a.f27524v, SVGParser.XML_STYLESHEET_ATTR_ALTERNATE_NO, "n", "f", "0", DebugKt.DEBUG_PROPERTY_VALUE_OFF, "否", "错", "假", "錯", "×");

    public static boolean and(boolean... zArr) {
        if (PrimitiveArrayUtil.isEmpty(zArr)) {
            throw new IllegalArgumentException("The Array must not be empty !");
        }
        for (boolean z2 : zArr) {
            if (!z2) {
                return false;
            }
        }
        return true;
    }

    public static Boolean andOfWrap(Boolean... boolArr) {
        if (ArrayUtil.isEmpty((Object[]) boolArr)) {
            throw new IllegalArgumentException("The Array must not be empty !");
        }
        for (Boolean bool : boolArr) {
            if (isFalse(bool)) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    public static boolean isBoolean(Class<?> cls) {
        return cls == Boolean.class || cls == Boolean.TYPE;
    }

    public static boolean isFalse(Boolean bool) {
        return Boolean.FALSE.equals(bool);
    }

    public static boolean isTrue(Boolean bool) {
        return Boolean.TRUE.equals(bool);
    }

    public static Boolean negate(Boolean bool) {
        if (bool == null) {
            return null;
        }
        return bool.booleanValue() ? Boolean.FALSE : Boolean.TRUE;
    }

    public static boolean negate(boolean z2) {
        return !z2;
    }

    public static boolean or(boolean... zArr) {
        if (PrimitiveArrayUtil.isEmpty(zArr)) {
            throw new IllegalArgumentException("The Array must not be empty !");
        }
        for (boolean z2 : zArr) {
            if (z2) {
                return true;
            }
        }
        return false;
    }

    public static Boolean orOfWrap(Boolean... boolArr) {
        if (ArrayUtil.isEmpty((Object[]) boolArr)) {
            throw new IllegalArgumentException("The Array must not be empty !");
        }
        for (Boolean bool : boolArr) {
            if (isTrue(bool)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public static boolean toBoolean(String str) {
        if (!CharSequenceUtil.isNotBlank(str)) {
            return false;
        }
        return TRUE_SET.contains(str.trim().toLowerCase());
    }

    public static Boolean toBooleanObject(String str) {
        if (!CharSequenceUtil.isNotBlank(str)) {
            return null;
        }
        String lowerCase = str.trim().toLowerCase();
        if (TRUE_SET.contains(lowerCase)) {
            return Boolean.TRUE;
        }
        if (FALSE_SET.contains(lowerCase)) {
            return Boolean.FALSE;
        }
        return null;
    }

    public static byte toByte(boolean z2) {
        return (byte) toInt(z2);
    }

    public static Byte toByteObj(boolean z2) {
        return Byte.valueOf(toByte(z2));
    }

    public static char toChar(boolean z2) {
        return (char) toInt(z2);
    }

    public static Character toCharacter(boolean z2) {
        return Character.valueOf(toChar(z2));
    }

    public static double toDouble(boolean z2) {
        return toInt(z2);
    }

    public static Double toDoubleObj(boolean z2) {
        return Double.valueOf(toDouble(z2));
    }

    public static float toFloat(boolean z2) {
        return toInt(z2);
    }

    public static Float toFloatObj(boolean z2) {
        return Float.valueOf(toFloat(z2));
    }

    public static int toInt(boolean z2) {
        return z2 ? 1 : 0;
    }

    public static Integer toInteger(boolean z2) {
        return Integer.valueOf(toInt(z2));
    }

    public static long toLong(boolean z2) {
        return toInt(z2);
    }

    public static Long toLongObj(boolean z2) {
        return Long.valueOf(toLong(z2));
    }

    public static short toShort(boolean z2) {
        return (short) toInt(z2);
    }

    public static Short toShortObj(boolean z2) {
        return Short.valueOf(toShort(z2));
    }

    public static String toString(Boolean bool, String str, String str2, String str3) {
        return bool == null ? str3 : bool.booleanValue() ? str : str2;
    }

    public static String toString(boolean z2, String str, String str2) {
        return z2 ? str : str2;
    }

    public static String toStringOnOff(boolean z2) {
        return toString(z2, DebugKt.DEBUG_PROPERTY_VALUE_ON, DebugKt.DEBUG_PROPERTY_VALUE_OFF);
    }

    public static String toStringTrueFalse(boolean z2) {
        return toString(z2, k.a.f27523u, k.a.f27524v);
    }

    public static String toStringYesNo(boolean z2) {
        return toString(z2, "yes", SVGParser.XML_STYLESHEET_ATTR_ALTERNATE_NO);
    }

    public static boolean xor(boolean... zArr) {
        if (PrimitiveArrayUtil.isEmpty(zArr)) {
            throw new IllegalArgumentException("The Array must not be empty");
        }
        boolean z2 = false;
        for (boolean z3 : zArr) {
            z2 ^= z3;
        }
        return z2;
    }

    public static Boolean xorOfWrap(Boolean... boolArr) {
        if (ArrayUtil.isEmpty((Object[]) boolArr)) {
            throw new IllegalArgumentException("The Array must not be empty !");
        }
        return Boolean.valueOf(xor((boolean[]) Convert.convert(boolean[].class, (Object) boolArr)));
    }
}
