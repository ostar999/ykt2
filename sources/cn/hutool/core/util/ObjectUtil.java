package cn.hutool.core.util;

import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class ObjectUtil {
    public static <T> T clone(T t2) {
        T t3 = (T) ArrayUtil.clone(t2);
        return t3 == null ? t2 instanceof Cloneable ? (T) ReflectUtil.invoke(t2, "clone", new Object[0]) : (T) cloneByStream(t2) : t3;
    }

    public static <T> T cloneByStream(T t2) {
        return (T) SerializeUtil.clone(t2);
    }

    public static <T> T cloneIfPossible(T t2) {
        Object objClone;
        try {
            objClone = clone(t2);
        } catch (Exception unused) {
            objClone = null;
        }
        return objClone == null ? t2 : (T) objClone;
    }

    public static <T extends Comparable<? super T>> int compare(T t2, T t3) {
        return CompareUtil.compare(t2, t3);
    }

    public static boolean contains(Object obj, Object obj2) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof String) {
            if (obj2 == null) {
                return false;
            }
            return ((String) obj).contains(obj2.toString());
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).contains(obj2);
        }
        if (obj instanceof Map) {
            return ((Map) obj).containsValue(obj2);
        }
        if (obj instanceof Iterator) {
            Iterator it = (Iterator) obj;
            while (it.hasNext()) {
                if (equal(it.next(), obj2)) {
                    return true;
                }
            }
            return false;
        }
        if (obj instanceof Enumeration) {
            Enumeration enumeration = (Enumeration) obj;
            while (enumeration.hasMoreElements()) {
                if (equal(enumeration.nextElement(), obj2)) {
                    return true;
                }
            }
            return false;
        }
        if (obj.getClass().isArray()) {
            int length = Array.getLength(obj);
            for (int i2 = 0; i2 < length; i2++) {
                if (equal(Array.get(obj, i2), obj2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static <T extends CharSequence> T defaultIfBlank(T t2, T t3) {
        return CharSequenceUtil.isBlank(t2) ? t3 : t2;
    }

    @Deprecated
    public static <T> T defaultIfEmpty(String str, Supplier<? extends T> supplier, T t2) {
        return CharSequenceUtil.isNotEmpty(str) ? (T) supplier.get() : t2;
    }

    public static <T> T defaultIfNull(T t2, T t3) {
        return isNull(t2) ? t3 : t2;
    }

    public static <T> T deserialize(byte[] bArr, Class<?>... clsArr) {
        return (T) SerializeUtil.deserialize(bArr, clsArr);
    }

    public static int emptyCount(Object... objArr) {
        return ArrayUtil.emptyCount(objArr);
    }

    public static boolean equal(Object obj, Object obj2) {
        return ((obj instanceof Number) && (obj2 instanceof Number)) ? NumberUtil.equals((Number) obj, (Number) obj2) : Objects.equals(obj, obj2);
    }

    public static boolean equals(Object obj, Object obj2) {
        return equal(obj, obj2);
    }

    public static Class<?> getTypeArgument(Object obj) {
        return getTypeArgument(obj, 0);
    }

    public static boolean hasEmpty(Object... objArr) {
        return ArrayUtil.hasEmpty(objArr);
    }

    public static boolean hasNull(Object... objArr) {
        return ArrayUtil.hasNull(objArr);
    }

    public static boolean isAllEmpty(Object... objArr) {
        return ArrayUtil.isAllEmpty(objArr);
    }

    public static boolean isAllNotEmpty(Object... objArr) {
        return ArrayUtil.isAllNotEmpty(objArr);
    }

    public static boolean isBasicType(Object obj) {
        if (obj == null) {
            return false;
        }
        return ClassUtil.isBasicType(obj.getClass());
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof CharSequence) {
            return CharSequenceUtil.isEmpty((CharSequence) obj);
        }
        if (obj instanceof Map) {
            return MapUtil.isEmpty((Map) obj);
        }
        if (obj instanceof Iterable) {
            return IterUtil.isEmpty((Iterable<?>) obj);
        }
        if (obj instanceof Iterator) {
            return IterUtil.isEmpty((Iterator<?>) obj);
        }
        if (ArrayUtil.isArray(obj)) {
            return ArrayUtil.isEmpty(obj);
        }
        return false;
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotNull(Object obj) {
        return (obj == null || obj.equals(null)) ? false : true;
    }

    public static boolean isNull(Object obj) {
        return obj == null || obj.equals(null);
    }

    public static boolean isValidIfNumber(Object obj) {
        if (obj instanceof Number) {
            return NumberUtil.isValidNumber((Number) obj);
        }
        return true;
    }

    public static int length(Object obj) {
        int i2 = 0;
        if (obj == null) {
            return 0;
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length();
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).size();
        }
        if (obj instanceof Map) {
            return ((Map) obj).size();
        }
        if (obj instanceof Iterator) {
            Iterator it = (Iterator) obj;
            while (it.hasNext()) {
                i2++;
                it.next();
            }
            return i2;
        }
        if (!(obj instanceof Enumeration)) {
            if (obj.getClass().isArray()) {
                return Array.getLength(obj);
            }
            return -1;
        }
        Enumeration enumeration = (Enumeration) obj;
        while (enumeration.hasMoreElements()) {
            i2++;
            enumeration.nextElement();
        }
        return i2;
    }

    public static boolean notEqual(Object obj, Object obj2) {
        return !equal(obj, obj2);
    }

    public static <T> byte[] serialize(T t2) {
        return SerializeUtil.serialize(t2);
    }

    public static String toString(Object obj) {
        return obj == null ? "null" : obj instanceof Map ? obj.toString() : Convert.toStr(obj);
    }

    public static <T extends Comparable<? super T>> int compare(T t2, T t3, boolean z2) {
        return CompareUtil.compare((Comparable) t2, (Comparable) t3, z2);
    }

    public static <T extends CharSequence> T defaultIfBlank(T t2, Supplier<? extends T> supplier) {
        return CharSequenceUtil.isBlank(t2) ? (T) supplier.get() : t2;
    }

    public static <T> T defaultIfNull(T t2, Supplier<? extends T> supplier) {
        return isNull(t2) ? (T) supplier.get() : t2;
    }

    public static Class<?> getTypeArgument(Object obj, int i2) {
        return ClassUtil.getTypeArgument(obj.getClass(), i2);
    }

    public static <T> T defaultIfEmpty(String str, Function<CharSequence, ? extends T> function, T t2) {
        return CharSequenceUtil.isNotEmpty(str) ? (T) function.apply(str) : t2;
    }

    public static <T extends CharSequence> T defaultIfBlank(T t2, Function<T, ? extends T> function) {
        return CharSequenceUtil.isBlank(t2) ? (T) function.apply(null) : t2;
    }

    public static <T> T defaultIfNull(T t2, Function<T, ? extends T> function) {
        return isNull(t2) ? (T) function.apply(null) : t2;
    }

    public static <T extends CharSequence> T defaultIfEmpty(T t2, T t3) {
        return CharSequenceUtil.isEmpty(t2) ? t3 : t2;
    }

    public static <T extends CharSequence> T defaultIfEmpty(T t2, Supplier<? extends T> supplier) {
        return CharSequenceUtil.isEmpty(t2) ? (T) supplier.get() : t2;
    }

    @Deprecated
    public static <T> T defaultIfNull(Object obj, Supplier<? extends T> supplier, T t2) {
        return isNotNull(obj) ? (T) supplier.get() : t2;
    }

    public static <T extends CharSequence> T defaultIfEmpty(T t2, Function<T, ? extends T> function) {
        return CharSequenceUtil.isEmpty(t2) ? (T) function.apply(null) : t2;
    }

    public static <T, R> T defaultIfNull(R r2, Function<R, ? extends T> function, T t2) {
        return isNotNull(r2) ? (T) function.apply(r2) : t2;
    }
}
