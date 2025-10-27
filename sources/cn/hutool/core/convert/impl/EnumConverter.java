package cn.hutool.core.convert.impl;

import cn.hutool.core.convert.AbstractConverter;
import cn.hutool.core.convert.ConvertException;
import cn.hutool.core.convert.impl.EnumConverter;
import cn.hutool.core.lang.EnumItem;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.map.WeakConcurrentMap;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ModifierUtil;
import cn.hutool.core.util.ReflectUtil;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public class EnumConverter extends AbstractConverter<Object> {
    private static final WeakConcurrentMap<Class<?>, Map<Class<?>, Method>> VALUE_OF_METHOD_CACHE = new WeakConcurrentMap<>();
    private static final long serialVersionUID = 1;
    private final Class enumClass;

    public EnumConverter(Class cls) {
        this.enumClass = cls;
    }

    private static Map<Class<?>, Method> getMethodMap(final Class<?> cls) {
        return VALUE_OF_METHOD_CACHE.computeIfAbsent((WeakConcurrentMap<Class<?>, Map<Class<?>, Method>>) cls, (Function<? super WeakConcurrentMap<Class<?>, Map<Class<?>, Method>>, ? extends Map<Class<?>, Method>>) new Function() { // from class: v.f
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return EnumConverter.lambda$getMethodMap$6(cls, (Class) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map lambda$getMethodMap$6(final Class cls, Class cls2) {
        return (Map) Arrays.stream(cls.getMethods()).filter(new Predicate() { // from class: v.i
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ModifierUtil.isStatic((Method) obj);
            }
        }).filter(new Predicate() { // from class: v.j
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return EnumConverter.lambda$null$0(cls, (Method) obj);
            }
        }).filter(new Predicate() { // from class: v.k
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return EnumConverter.lambda$null$1((Method) obj);
            }
        }).filter(new Predicate() { // from class: v.l
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return EnumConverter.lambda$null$2((Method) obj);
            }
        }).collect(Collectors.toMap(new Function() { // from class: v.m
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return EnumConverter.lambda$null$3((Method) obj);
            }
        }, new Function() { // from class: v.g
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return EnumConverter.lambda$null$4((Method) obj);
            }
        }, new BinaryOperator() { // from class: v.h
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return EnumConverter.lambda$null$5((Method) obj, (Method) obj2);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$null$0(Class cls, Method method) {
        return method.getReturnType() == cls;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$null$1(Method method) {
        return method.getParameterCount() == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$null$2(Method method) {
        return !"valueOf".equals(method.getName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Class lambda$null$3(Method method) {
        return method.getParameterTypes()[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Method lambda$null$4(Method method) {
        return method;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Method lambda$null$5(Method method, Method method2) {
        return method;
    }

    public static Enum tryConvertEnum(Object obj, Class cls) {
        EnumItem enumItem;
        if (obj == null) {
            return null;
        }
        if (EnumItem.class.isAssignableFrom(cls) && (enumItem = (EnumItem) EnumUtil.getEnumAt(cls, 0)) != null) {
            if (obj instanceof Integer) {
                return (Enum) enumItem.fromInt((Integer) obj);
            }
            if (obj instanceof String) {
                return (Enum) enumItem.fromStr(obj.toString());
            }
        }
        try {
            Map<Class<?>, Method> methodMap = getMethodMap(cls);
            if (MapUtil.isNotEmpty(methodMap)) {
                Class<?> cls2 = obj.getClass();
                for (Map.Entry<Class<?>, Method> entry : methodMap.entrySet()) {
                    if (ClassUtil.isAssignable(entry.getKey(), cls2)) {
                        return (Enum) ReflectUtil.invokeStatic(entry.getValue(), obj);
                    }
                }
            }
        } catch (Exception unused) {
        }
        if (obj instanceof Integer) {
            return EnumUtil.getEnumAt(cls, ((Integer) obj).intValue());
        }
        if (!(obj instanceof String)) {
            return null;
        }
        try {
            return Enum.valueOf(cls, (String) obj);
        } catch (IllegalArgumentException unused2) {
            return null;
        }
    }

    @Override // cn.hutool.core.convert.AbstractConverter
    public Object convertInternal(Object obj) {
        Enum enumTryConvertEnum = tryConvertEnum(obj, this.enumClass);
        if (enumTryConvertEnum == null && !(obj instanceof String)) {
            enumTryConvertEnum = Enum.valueOf(this.enumClass, convertToStr(obj));
        }
        if (enumTryConvertEnum != null) {
            return enumTryConvertEnum;
        }
        throw new ConvertException("Can not convert {} to {}", obj, this.enumClass);
    }

    @Override // cn.hutool.core.convert.AbstractConverter
    public Class<Object> getTargetType() {
        return this.enumClass;
    }
}
