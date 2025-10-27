package cn.hutool.core.lang;

import cn.hutool.core.lang.func.Func0;
import cn.hutool.core.map.SafeConcurrentHashMap;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public final class Singleton {
    private static final SafeConcurrentHashMap<String, Object> POOL = new SafeConcurrentHashMap<>();

    private Singleton() {
    }

    private static String buildKey(String str, Object... objArr) {
        return ArrayUtil.isEmpty(objArr) ? str : CharSequenceUtil.format("{}#{}", str, ArrayUtil.join(objArr, (CharSequence) StrPool.UNDERLINE));
    }

    public static void destroy() {
        POOL.clear();
    }

    public static boolean exists(Class<?> cls, Object... objArr) {
        if (cls == null) {
            return false;
        }
        return POOL.containsKey(buildKey(cls.getName(), objArr));
    }

    public static <T> T get(Class<T> cls, Object... objArr) throws IllegalArgumentException {
        Assert.notNull(cls, "Class must be not null !", new Object[0]);
        return (T) get(buildKey(cls.getName(), objArr), new l0(cls, objArr));
    }

    public static Set<Class<?>> getExistClass() {
        return (Set) POOL.values().stream().map(new Function() { // from class: cn.hutool.core.lang.m0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return obj.getClass();
            }
        }).collect(Collectors.toSet());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$get$0(Func0 func0, String str) {
        return func0.callWithRuntimeException();
    }

    public static void put(Object obj) throws IllegalArgumentException {
        Assert.notNull(obj, "Bean object must be not null !", new Object[0]);
        put(obj.getClass().getName(), obj);
    }

    public static void remove(Class<?> cls) {
        if (cls != null) {
            remove(cls.getName());
        }
    }

    public static void remove(String str) {
        POOL.remove(str);
    }

    public static void put(String str, Object obj) {
        POOL.put(str, obj);
    }

    public static <T> T get(String str, final Func0<T> func0) {
        return (T) POOL.computeIfAbsent(str, new Function() { // from class: cn.hutool.core.lang.k0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Singleton.lambda$get$0(func0, (String) obj);
            }
        });
    }

    public static <T> T get(String str, Object... objArr) throws IllegalArgumentException {
        Assert.notBlank(str, "Class name must be not blank !", new Object[0]);
        return (T) get(ClassUtil.loadClass(str), objArr);
    }
}
