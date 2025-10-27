package cn.hutool.core.convert;

import cn.hutool.core.convert.impl.CollectionConverter;
import cn.hutool.core.convert.impl.EnumConverter;
import cn.hutool.core.convert.impl.MapConverter;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.core.util.ByteUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.HexUtil;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class Convert {
    public static int byteToUnsignedInt(byte b3) {
        return b3 & 255;
    }

    public static int bytesToInt(byte[] bArr) {
        return ByteUtil.bytesToInt(bArr);
    }

    public static long bytesToLong(byte[] bArr) {
        return ByteUtil.bytesToLong(bArr);
    }

    public static short bytesToShort(byte[] bArr) {
        return ByteUtil.bytesToShort(bArr);
    }

    public static BigDecimal chineseMoneyToNumber(String str) {
        return NumberChineseFormatter.chineseMoneyToNumber(str);
    }

    public static int chineseToNumber(String str) {
        return NumberChineseFormatter.chineseToNumber(str);
    }

    public static <T> T convert(Class<T> cls, Object obj) throws ConvertException {
        return (T) convert((Type) cls, obj);
    }

    public static <T> T convertByClassName(String str, Object obj) throws ConvertException {
        return (T) convert(ClassUtil.loadClass(str), obj);
    }

    public static String convertCharset(String str, String str2, String str3) {
        return CharSequenceUtil.hasBlank(str, str2, str3) ? str : CharsetUtil.convert(str, str2, str3);
    }

    public static <T> T convertQuietly(Type type, Object obj) {
        return (T) convertQuietly(type, obj, null);
    }

    public static long convertTime(long j2, TimeUnit timeUnit, TimeUnit timeUnit2) throws IllegalArgumentException {
        Assert.notNull(timeUnit, "sourceUnit is null !", new Object[0]);
        Assert.notNull(timeUnit2, "destUnit is null !", new Object[0]);
        return timeUnit2.convert(j2, timeUnit);
    }

    public static <T> T convertWithCheck(Type type, Object obj, T t2, boolean z2) throws Exception {
        try {
            return (T) ConverterRegistry.getInstance().convert(type, obj, t2);
        } catch (Exception e2) {
            if (z2) {
                return t2;
            }
            throw e2;
        }
    }

    public static String digitToChinese(Number number) {
        return number == null ? "零" : NumberChineseFormatter.format(number.doubleValue(), true, true);
    }

    public static byte[] hexToBytes(String str) {
        return HexUtil.decodeHex(str.toCharArray());
    }

    public static String hexToStr(String str, Charset charset) {
        return HexUtil.decodeHexStr(str, charset);
    }

    public static byte intToByte(int i2) {
        return (byte) i2;
    }

    public static byte[] intToBytes(int i2) {
        return ByteUtil.intToBytes(i2);
    }

    public static byte[] longToBytes(long j2) {
        return ByteUtil.longToBytes(j2);
    }

    public static String numberToChinese(double d3, boolean z2) {
        return NumberChineseFormatter.format(d3, z2);
    }

    public static String numberToSimple(Number number) {
        return NumberWordFormatter.formatSimple(number.longValue());
    }

    public static String numberToWord(Number number) {
        return NumberWordFormatter.format(number);
    }

    public static byte[] shortToBytes(short s2) {
        return ByteUtil.shortToBytes(s2);
    }

    public static String strToUnicode(String str) {
        return UnicodeUtil.toUnicode(str);
    }

    public static BigDecimal toBigDecimal(Object obj, BigDecimal bigDecimal) {
        return (BigDecimal) convertQuietly(BigDecimal.class, obj, bigDecimal);
    }

    public static BigInteger toBigInteger(Object obj, BigInteger bigInteger) {
        return (BigInteger) convertQuietly(BigInteger.class, obj, bigInteger);
    }

    public static Boolean toBool(Object obj, Boolean bool) {
        return (Boolean) convertQuietly(Boolean.class, obj, bool);
    }

    public static Boolean[] toBooleanArray(Object obj) {
        return (Boolean[]) convert(Boolean[].class, obj);
    }

    public static Byte toByte(Object obj, Byte b3) {
        return (Byte) convertQuietly(Byte.class, obj, b3);
    }

    public static Byte[] toByteArray(Object obj) {
        return (Byte[]) convert(Byte[].class, obj);
    }

    public static Character toChar(Object obj, Character ch) {
        return (Character) convertQuietly(Character.class, obj, ch);
    }

    public static Character[] toCharArray(Object obj) {
        return (Character[]) convert(Character[].class, obj);
    }

    public static Collection<?> toCollection(Class<?> cls, Class<?> cls2, Object obj) {
        return new CollectionConverter(cls, cls2).convert(obj, (Collection<?>) null);
    }

    public static String toDBC(String str) {
        return toDBC(str, null);
    }

    public static Date toDate(Object obj, Date date) {
        return (Date) convertQuietly(Date.class, obj, date);
    }

    public static Double toDouble(Object obj, Double d3) {
        return (Double) convertQuietly(Double.class, obj, d3);
    }

    public static Double[] toDoubleArray(Object obj) {
        return (Double[]) convert(Double[].class, obj);
    }

    public static <E extends Enum<E>> E toEnum(Class<E> cls, Object obj, E e2) {
        return (E) new EnumConverter(cls).convertQuietly(obj, e2);
    }

    public static Float toFloat(Object obj, Float f2) {
        return (Float) convertQuietly(Float.class, obj, f2);
    }

    public static Float[] toFloatArray(Object obj) {
        return (Float[]) convert(Float[].class, obj);
    }

    public static String toHex(String str, Charset charset) {
        return HexUtil.encodeHexStr(str, charset);
    }

    public static Date toInstant(Object obj, Date date) {
        return (Date) convertQuietly(Instant.class, obj, date);
    }

    public static Integer toInt(Object obj, Integer num) {
        return (Integer) convertQuietly(Integer.class, obj, num);
    }

    public static Integer[] toIntArray(Object obj) {
        return (Integer[]) convert(Integer[].class, obj);
    }

    public static List<?> toList(Object obj) {
        return (List) convert(List.class, obj);
    }

    public static LocalDateTime toLocalDateTime(Object obj, LocalDateTime localDateTime) {
        return (LocalDateTime) convertQuietly(LocalDateTime.class, obj, localDateTime);
    }

    public static Long toLong(Object obj, Long l2) {
        return (Long) convertQuietly(Long.class, obj, l2);
    }

    public static Long[] toLongArray(Object obj) {
        return (Long[]) convert(Long[].class, obj);
    }

    public static <K, V> Map<K, V> toMap(Class<K> cls, Class<V> cls2, Object obj) {
        return obj instanceof Map ? toMap(obj.getClass(), cls, cls2, obj) : toMap(HashMap.class, cls, cls2, obj);
    }

    public static Number toNumber(Object obj, Number number) {
        return (Number) convertQuietly(Number.class, obj, number);
    }

    public static Number[] toNumberArray(Object obj) {
        return (Number[]) convert(Number[].class, obj);
    }

    public static byte[] toPrimitiveByteArray(Object obj) {
        return (byte[]) convert(byte[].class, obj);
    }

    public static String toSBC(String str) {
        return toSBC(str, null);
    }

    public static <T> Set<T> toSet(Class<T> cls, Object obj) {
        return (Set) toCollection(HashSet.class, cls, obj);
    }

    public static Short toShort(Object obj, Short sh) {
        return (Short) convertQuietly(Short.class, obj, sh);
    }

    public static Short[] toShortArray(Object obj) {
        return (Short[]) convert(Short[].class, obj);
    }

    public static String toStr(Object obj, String str) {
        return (String) convertQuietly(String.class, obj, str);
    }

    public static String[] toStrArray(Object obj) {
        return (String[]) convert(String[].class, obj);
    }

    public static Class<?> unWrap(Class<?> cls) {
        return BasicType.unWrap(cls);
    }

    public static String unicodeToStr(String str) {
        return UnicodeUtil.toString(str);
    }

    public static Class<?> wrap(Class<?> cls) {
        return BasicType.wrap(cls);
    }

    public static <T> T convert(TypeReference<T> typeReference, Object obj) throws ConvertException {
        return (T) convert(typeReference.getType(), obj, (Object) null);
    }

    public static <T> T convertQuietly(Type type, Object obj, T t2) {
        return (T) convertWithCheck(type, obj, t2, true);
    }

    public static BigDecimal toBigDecimal(Object obj) {
        return toBigDecimal(obj, null);
    }

    public static BigInteger toBigInteger(Object obj) {
        return toBigInteger(obj, null);
    }

    public static Boolean toBool(Object obj) {
        return toBool(obj, null);
    }

    public static Byte toByte(Object obj) {
        return toByte(obj, null);
    }

    public static Character toChar(Object obj) {
        return toChar(obj, null);
    }

    public static String toDBC(String str, Set<Character> set) {
        if (CharSequenceUtil.isBlank(str)) {
            return str;
        }
        char[] charArray = str.toCharArray();
        for (int i2 = 0; i2 < charArray.length; i2++) {
            if (set == null || !set.contains(Character.valueOf(charArray[i2]))) {
                char c3 = charArray[i2];
                if (c3 == 12288 || c3 == 160 || c3 == 8199 || c3 == 8239) {
                    charArray[i2] = ' ';
                } else if (c3 > 65280 && c3 < 65375) {
                    charArray[i2] = (char) (c3 - 65248);
                }
            }
        }
        return new String(charArray);
    }

    public static Date toDate(Object obj) {
        return toDate(obj, null);
    }

    public static Double toDouble(Object obj) {
        return toDouble(obj, null);
    }

    public static <E extends Enum<E>> E toEnum(Class<E> cls, Object obj) {
        return (E) toEnum(cls, obj, null);
    }

    public static Float toFloat(Object obj) {
        return toFloat(obj, null);
    }

    public static String toHex(byte[] bArr) {
        return HexUtil.encodeHexStr(bArr);
    }

    public static Integer toInt(Object obj) {
        return toInt(obj, null);
    }

    public static <T> List<T> toList(Class<T> cls, Object obj) {
        return (List) toCollection(ArrayList.class, cls, obj);
    }

    public static LocalDateTime toLocalDateTime(Object obj) {
        return toLocalDateTime(obj, null);
    }

    public static Long toLong(Object obj) {
        return toLong(obj, null);
    }

    public static Number toNumber(Object obj) {
        return toNumber(obj, null);
    }

    public static String toSBC(String str, Set<Character> set) {
        if (CharSequenceUtil.isEmpty(str)) {
            return str;
        }
        char[] charArray = str.toCharArray();
        for (int i2 = 0; i2 < charArray.length; i2++) {
            if (set == null || !set.contains(Character.valueOf(charArray[i2]))) {
                char c3 = charArray[i2];
                if (c3 == ' ') {
                    charArray[i2] = 12288;
                } else if (c3 < 127) {
                    charArray[i2] = (char) (c3 + 65248);
                }
            }
        }
        return new String(charArray);
    }

    public static Short toShort(Object obj) {
        return toShort(obj, null);
    }

    public static String toStr(Object obj) {
        return toStr(obj, null);
    }

    public static <T> T convert(Type type, Object obj) throws ConvertException {
        return (T) convert(type, obj, (Object) null);
    }

    public static <T> T convert(Class<T> cls, Object obj, T t2) throws ConvertException {
        return (T) convert((Type) cls, obj, (Object) t2);
    }

    public static <K, V> Map<K, V> toMap(Class<? extends Map> cls, Class<K> cls2, Class<V> cls3, Object obj) {
        return (Map) new MapConverter(cls, cls2, cls3).convert(obj, null);
    }

    public static <T> T convert(Type type, Object obj, T t2) throws ConvertException {
        return (T) convertWithCheck(type, obj, t2, false);
    }
}
