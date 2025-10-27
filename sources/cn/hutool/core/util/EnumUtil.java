package cn.hutool.core.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.lang.func.LambdaUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class EnumUtil {
    public static <E extends Enum<E>> boolean contains(Class<E> cls, String str) {
        return getEnumMap(cls).containsKey(str);
    }

    public static boolean equals(Enum<?> r02, String str) {
        return CharSequenceUtil.equals(toString(r02), str);
    }

    public static boolean equalsIgnoreCase(Enum<?> r02, String str) {
        return CharSequenceUtil.equalsIgnoreCase(toString(r02), str);
    }

    public static <E extends Enum<E>> E fromString(Class<E> cls, String str) {
        return (E) Enum.valueOf(cls, str);
    }

    public static <E extends Enum<E>> E fromStringQuietly(Class<E> cls, String str) {
        if (cls != null && !CharSequenceUtil.isBlank(str)) {
            try {
                return (E) fromString(cls, str);
            } catch (IllegalArgumentException unused) {
            }
        }
        return null;
    }

    public static <E extends Enum<E>> E getBy(Class<E> cls, Predicate<? super E> predicate) {
        return (E) Arrays.stream(cls.getEnumConstants()).filter(predicate).findFirst().orElse(null);
    }

    public static <E extends Enum<E>> E getEnumAt(Class<E> cls, int i2) {
        E[] enumConstants = cls.getEnumConstants();
        if (i2 < 0 || i2 >= enumConstants.length) {
            return null;
        }
        return enumConstants[i2];
    }

    public static <E extends Enum<E>> LinkedHashMap<String, E> getEnumMap(Class<E> cls) {
        LinkedHashMap<String, E> linkedHashMap = new LinkedHashMap<>();
        for (E e2 : cls.getEnumConstants()) {
            linkedHashMap.put(e2.name(), e2);
        }
        return linkedHashMap;
    }

    public static <E extends Enum<E>, F, C> F getFieldBy(final Func1<E, F> func1, final Function<E, C> function, final C c3) {
        Class realClass = LambdaUtil.getRealClass(func1);
        if (Enum.class.equals(realClass)) {
            realClass = LambdaUtil.getRealClass(func1);
        }
        Optional optionalFindFirst = Arrays.stream(realClass.getEnumConstants()).filter(new Predicate() { // from class: cn.hutool.core.util.k
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return EnumUtil.lambda$getFieldBy$1(function, c3, (Enum) obj);
            }
        }).findFirst();
        func1.getClass();
        return (F) optionalFindFirst.map(new Function() { // from class: cn.hutool.core.util.l
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return func1.callWithRuntimeException((Enum) obj);
            }
        }).orElse(null);
    }

    public static List<String> getFieldNames(Class<? extends Enum<?>> cls) throws SecurityException {
        ArrayList arrayList = new ArrayList();
        for (Field field : ReflectUtil.getFields(cls)) {
            String name = field.getName();
            if (!field.getType().isEnum() && !name.contains("$VALUES") && !"ordinal".equals(name) && !arrayList.contains(name)) {
                arrayList.add(name);
            }
        }
        return arrayList;
    }

    public static List<Object> getFieldValues(Class<? extends Enum<?>> cls, String str) {
        Enum[] enumArr = (Enum[]) cls.getEnumConstants();
        if (enumArr == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(enumArr.length);
        for (Enum r02 : enumArr) {
            arrayList.add(ReflectUtil.getFieldValue(r02, str));
        }
        return arrayList;
    }

    public static Map<String, Object> getNameFieldMap(Class<? extends Enum<?>> cls, String str) {
        Enum[] enumArr = (Enum[]) cls.getEnumConstants();
        if (enumArr == null) {
            return null;
        }
        HashMap mapNewHashMap = MapUtil.newHashMap(enumArr.length, true);
        for (Enum r3 : enumArr) {
            mapNewHashMap.put(r3.name(), ReflectUtil.getFieldValue(r3, str));
        }
        return mapNewHashMap;
    }

    public static List<String> getNames(Class<? extends Enum<?>> cls) {
        Enum[] enumArr = (Enum[]) cls.getEnumConstants();
        if (enumArr == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(enumArr.length);
        for (Enum r02 : enumArr) {
            arrayList.add(r02.name());
        }
        return arrayList;
    }

    public static boolean isEnum(Class<?> cls) throws IllegalArgumentException {
        Assert.notNull(cls);
        return cls.isEnum();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getBy$0(Func1 func1, Object obj, Enum r2) {
        return func1.callWithRuntimeException(r2).equals(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getFieldBy$1(Function function, Object obj, Enum r2) {
        return function.apply(r2).equals(obj);
    }

    public static <E extends Enum<E>> E likeValueOf(Class<E> cls, Object obj) throws SecurityException {
        if (obj instanceof CharSequence) {
            obj = obj.toString().trim();
        }
        Field[] fields = ReflectUtil.getFields(cls);
        E[] enumConstants = cls.getEnumConstants();
        for (Field field : fields) {
            String name = field.getName();
            if (!field.getType().isEnum() && !"ENUM$VALUES".equals(name) && !"ordinal".equals(name)) {
                for (E e2 : enumConstants) {
                    if (ObjectUtil.equal(obj, ReflectUtil.getFieldValue(e2, field))) {
                        return e2;
                    }
                }
            }
        }
        return null;
    }

    public static <E extends Enum<E>> boolean notContains(Class<E> cls, String str) {
        return !contains(cls, str);
    }

    public static String toString(Enum<?> r02) {
        if (r02 != null) {
            return r02.name();
        }
        return null;
    }

    public static <E extends Enum<E>> E fromString(Class<E> cls, String str, E e2) {
        return (E) ObjectUtil.defaultIfNull((E) fromStringQuietly(cls, str), e2);
    }

    public static <E extends Enum<E>, C> E getBy(final Func1<E, C> func1, final C c3) {
        Class realClass = LambdaUtil.getRealClass(func1);
        if (Enum.class.equals(realClass)) {
            realClass = LambdaUtil.getRealClass(func1);
        }
        return (E) Arrays.stream(realClass.getEnumConstants()).filter(new Predicate() { // from class: cn.hutool.core.util.m
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return EnumUtil.lambda$getBy$0(func1, c3, (Enum) obj);
            }
        }).findAny().orElse(null);
    }

    public static boolean isEnum(Object obj) throws IllegalArgumentException {
        Assert.notNull(obj);
        return obj.getClass().isEnum();
    }

    public static <E extends Enum<E>, C> E getBy(Func1<E, C> func1, C c3, E e2) {
        return (E) ObjectUtil.defaultIfNull((E) getBy(func1, c3), e2);
    }
}
