package cn.hutool.core.lang.reflect;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.reflect.ActualTypeMapperPool;
import cn.hutool.core.map.WeakConcurrentMap;
import cn.hutool.core.util.TypeUtil;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/* loaded from: classes.dex */
public class ActualTypeMapperPool {
    private static final WeakConcurrentMap<Type, Map<Type, Type>> CACHE = new WeakConcurrentMap<>();

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.reflect.Type] */
    /* JADX WARN: Type inference failed for: r6v1, types: [java.lang.reflect.Type] */
    /* JADX WARN: Type inference failed for: r6v4, types: [java.lang.Class] */
    private static Map<Type, Type> createTypeMap(Type type) {
        HashMap map = new HashMap();
        while (type != 0) {
            ParameterizedType parameterizedType = TypeUtil.toParameterizedType(type);
            if (parameterizedType == null) {
                break;
            }
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            type = (Class) parameterizedType.getRawType();
            TypeVariable[] typeParameters = type.getTypeParameters();
            for (int i2 = 0; i2 < typeParameters.length; i2++) {
                Type type2 = actualTypeArguments[i2];
                if (!(type2 instanceof TypeVariable)) {
                    map.put(typeParameters[i2], type2);
                }
            }
        }
        return map;
    }

    public static Map<Type, Type> get(final Type type) {
        return CACHE.computeIfAbsent((WeakConcurrentMap<Type, Map<Type, Type>>) type, (Function<? super WeakConcurrentMap<Type, Map<Type, Type>>, ? extends Map<Type, Type>>) new Function() { // from class: f0.a
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ActualTypeMapperPool.lambda$get$0(type, (Type) obj);
            }
        });
    }

    public static Type getActualType(Type type, TypeVariable<?> typeVariable) {
        Map<Type, Type> map = get(type);
        Type type2 = map.get(typeVariable);
        while (true) {
            Type type3 = type2;
            if (!(type3 instanceof TypeVariable)) {
                return type3;
            }
            type2 = map.get(type3);
        }
    }

    public static Type[] getActualTypes(Type type, Type... typeArr) {
        Type[] typeArr2 = new Type[typeArr.length];
        for (int i2 = 0; i2 < typeArr.length; i2++) {
            Type actualType = typeArr[i2];
            if (actualType instanceof TypeVariable) {
                actualType = getActualType(type, (TypeVariable) actualType);
            }
            typeArr2[i2] = actualType;
        }
        return typeArr2;
    }

    public static Map<String, Type> getStrKeyMap(Type type) {
        return Convert.toMap(String.class, Type.class, get(type));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map lambda$get$0(Type type, Type type2) {
        return createTypeMap(type);
    }
}
