package com.aliyun.vod.qupaiokhttp;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/* loaded from: classes2.dex */
class ClassTypeReflect {
    private static Type getGenericType(int i2, Class<?> cls) {
        Type genericSuperclass = cls.getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
        if (i2 >= actualTypeArguments.length || i2 < 0) {
            throw new RuntimeException("Index outof bounds");
        }
        Type type = actualTypeArguments[i2];
        return !(type instanceof Class) ? Object.class : type;
    }

    public static Type getModelClazz(Class<?> cls) {
        return getGenericType(0, cls);
    }
}
