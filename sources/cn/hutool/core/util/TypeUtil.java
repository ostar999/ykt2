package cn.hutool.core.util;

import cn.hutool.core.lang.ParameterizedTypeImpl;
import cn.hutool.core.lang.reflect.ActualTypeMapperPool;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes.dex */
public class TypeUtil {
    public static Type getActualType(Type type, Field field) {
        if (field == null) {
            return null;
        }
        return getActualType((Type) ObjectUtil.defaultIfNull((Class<?>) type, field.getDeclaringClass()), field.getGenericType());
    }

    public static Type[] getActualTypes(Type type, Type... typeArr) {
        return ActualTypeMapperPool.getActualTypes(type, typeArr);
    }

    public static Class<?> getClass(Type type) {
        if (type == null) {
            return null;
        }
        if (type instanceof Class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            return (Class) ((ParameterizedType) type).getRawType();
        }
        if (type instanceof TypeVariable) {
            return (Class) ((TypeVariable) type).getBounds()[0];
        }
        if (!(type instanceof WildcardType)) {
            return null;
        }
        Type[] upperBounds = ((WildcardType) type).getUpperBounds();
        if (upperBounds.length == 1) {
            return getClass(upperBounds[0]);
        }
        return null;
    }

    public static Type getFieldType(Class<?> cls, String str) {
        return getType(ReflectUtil.getField(cls, str));
    }

    public static Class<?> getFirstParamClass(Method method) {
        return getParamClass(method, 0);
    }

    public static Type getFirstParamType(Method method) {
        return getParamType(method, 0);
    }

    public static ParameterizedType[] getGenerics(Class<?> cls) {
        ParameterizedType parameterizedType;
        ArrayList arrayList = new ArrayList();
        Type genericSuperclass = cls.getGenericSuperclass();
        if (genericSuperclass != null && !Object.class.equals(genericSuperclass) && (parameterizedType = toParameterizedType(genericSuperclass)) != null) {
            arrayList.add(parameterizedType);
        }
        Type[] genericInterfaces = cls.getGenericInterfaces();
        if (ArrayUtil.isNotEmpty((Object[]) genericInterfaces)) {
            for (Type type : genericInterfaces) {
                if (type instanceof ParameterizedType) {
                    arrayList.add((ParameterizedType) type);
                }
            }
        }
        return (ParameterizedType[]) arrayList.toArray(new ParameterizedType[0]);
    }

    public static Class<?> getParamClass(Method method, int i2) {
        Class<?>[] paramClasses = getParamClasses(method);
        if (paramClasses == null || paramClasses.length <= i2) {
            return null;
        }
        return paramClasses[i2];
    }

    public static Class<?>[] getParamClasses(Method method) {
        if (method == null) {
            return null;
        }
        return method.getParameterTypes();
    }

    public static Type getParamType(Method method, int i2) {
        Type[] paramTypes = getParamTypes(method);
        if (paramTypes == null || paramTypes.length <= i2) {
            return null;
        }
        return paramTypes[i2];
    }

    public static Type[] getParamTypes(Method method) {
        if (method == null) {
            return null;
        }
        return method.getGenericParameterTypes();
    }

    public static Class<?> getReturnClass(Method method) {
        if (method == null) {
            return null;
        }
        return method.getReturnType();
    }

    public static Type getReturnType(Method method) {
        if (method == null) {
            return null;
        }
        return method.getGenericReturnType();
    }

    public static Type getType(Field field) {
        if (field == null) {
            return null;
        }
        return field.getGenericType();
    }

    public static Type getTypeArgument(Type type) {
        return getTypeArgument(type, 0);
    }

    public static Type[] getTypeArguments(Type type) {
        ParameterizedType parameterizedType;
        if (type == null || (parameterizedType = toParameterizedType(type)) == null) {
            return null;
        }
        return parameterizedType.getActualTypeArguments();
    }

    public static Map<Type, Type> getTypeMap(Class<?> cls) {
        return ActualTypeMapperPool.get(cls);
    }

    public static boolean hasTypeVariable(Type... typeArr) {
        for (Type type : typeArr) {
            if (type instanceof TypeVariable) {
                return true;
            }
        }
        return false;
    }

    public static boolean isUnknown(Type type) {
        return type == null || (type instanceof TypeVariable);
    }

    public static ParameterizedType toParameterizedType(Type type) {
        return toParameterizedType(type, 0);
    }

    public static Type getActualType(Type type, Type type2) {
        return type2 instanceof ParameterizedType ? getActualType(type, (ParameterizedType) type2) : type2 instanceof TypeVariable ? ActualTypeMapperPool.getActualType(type, (TypeVariable) type2) : type2;
    }

    public static Type getTypeArgument(Type type, int i2) {
        Type[] typeArguments = getTypeArguments(type);
        if (typeArguments == null || typeArguments.length <= i2) {
            return null;
        }
        return typeArguments[i2];
    }

    public static ParameterizedType toParameterizedType(Type type, int i2) {
        if (type instanceof ParameterizedType) {
            return (ParameterizedType) type;
        }
        if (!(type instanceof Class)) {
            return null;
        }
        ParameterizedType[] generics = getGenerics((Class) type);
        if (generics.length > i2) {
            return generics[i2];
        }
        return null;
    }

    public static Type getActualType(Type type, ParameterizedType parameterizedType) {
        if (!hasTypeVariable(parameterizedType.getActualTypeArguments())) {
            return parameterizedType;
        }
        Type[] actualTypes = getActualTypes(type, parameterizedType.getActualTypeArguments());
        return ArrayUtil.isNotEmpty((Object[]) actualTypes) ? new ParameterizedTypeImpl(actualTypes, parameterizedType.getOwnerType(), parameterizedType.getRawType()) : parameterizedType;
    }

    public static Class<?> getClass(Field field) {
        if (field == null) {
            return null;
        }
        return field.getType();
    }
}
