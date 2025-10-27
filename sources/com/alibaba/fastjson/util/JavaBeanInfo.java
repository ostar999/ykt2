package com.alibaba.fastjson.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class JavaBeanInfo {
    public final Method buildMethod;
    public final Class<?> builderClass;
    public final Class<?> clazz;
    public final Constructor<?> creatorConstructor;
    public Type[] creatorConstructorParameterTypes;
    public String[] creatorConstructorParameters;
    public final Constructor<?> defaultConstructor;
    public final int defaultConstructorParameterSize;
    public final Method factoryMethod;
    public final FieldInfo[] fields;
    public final JSONType jsonType;

    /* renamed from: kotlin, reason: collision with root package name */
    public boolean f2628kotlin;
    public Constructor<?> kotlinDefaultConstructor;
    public String[] orders;
    public final int parserFeatures;
    public final FieldInfo[] sortedFields;
    public final String typeKey;
    public final String typeName;

    public JavaBeanInfo(Class<?> cls, Class<?> cls2, Constructor<?> constructor, Constructor<?> constructor2, Method method, Method method2, JSONType jSONType, List<FieldInfo> list) {
        JSONField jSONField;
        this.clazz = cls;
        this.builderClass = cls2;
        this.defaultConstructor = constructor;
        this.creatorConstructor = constructor2;
        this.factoryMethod = method;
        this.parserFeatures = TypeUtils.getParserFeatures(cls);
        this.buildMethod = method2;
        this.jsonType = jSONType;
        if (jSONType != null) {
            String strTypeName = jSONType.typeName();
            String strTypeKey = jSONType.typeKey();
            this.typeKey = strTypeKey.length() <= 0 ? null : strTypeKey;
            if (strTypeName.length() != 0) {
                this.typeName = strTypeName;
            } else {
                this.typeName = cls.getName();
            }
            String[] strArrOrders = jSONType.orders();
            this.orders = strArrOrders.length == 0 ? null : strArrOrders;
        } else {
            this.typeName = cls.getName();
            this.typeKey = null;
            this.orders = null;
        }
        FieldInfo[] fieldInfoArr = new FieldInfo[list.size()];
        this.fields = fieldInfoArr;
        list.toArray(fieldInfoArr);
        FieldInfo[] fieldInfoArr2 = new FieldInfo[fieldInfoArr.length];
        boolean z2 = false;
        if (this.orders != null) {
            LinkedHashMap linkedHashMap = new LinkedHashMap(list.size());
            for (FieldInfo fieldInfo : fieldInfoArr) {
                linkedHashMap.put(fieldInfo.name, fieldInfo);
            }
            int i2 = 0;
            for (String str : this.orders) {
                FieldInfo fieldInfo2 = (FieldInfo) linkedHashMap.get(str);
                if (fieldInfo2 != null) {
                    fieldInfoArr2[i2] = fieldInfo2;
                    linkedHashMap.remove(str);
                    i2++;
                }
            }
            Iterator it = linkedHashMap.values().iterator();
            while (it.hasNext()) {
                fieldInfoArr2[i2] = (FieldInfo) it.next();
                i2++;
            }
        } else {
            System.arraycopy(fieldInfoArr, 0, fieldInfoArr2, 0, fieldInfoArr.length);
            Arrays.sort(fieldInfoArr2);
        }
        this.sortedFields = Arrays.equals(this.fields, fieldInfoArr2) ? this.fields : fieldInfoArr2;
        if (constructor != null) {
            this.defaultConstructorParameterSize = constructor.getParameterTypes().length;
        } else if (method != null) {
            this.defaultConstructorParameterSize = method.getParameterTypes().length;
        } else {
            this.defaultConstructorParameterSize = 0;
        }
        if (constructor2 != null) {
            this.creatorConstructorParameterTypes = constructor2.getParameterTypes();
            boolean zIsKotlin = TypeUtils.isKotlin(cls);
            this.f2628kotlin = zIsKotlin;
            if (!zIsKotlin) {
                if (this.creatorConstructorParameterTypes.length == this.fields.length) {
                    int i3 = 0;
                    while (true) {
                        Type[] typeArr = this.creatorConstructorParameterTypes;
                        if (i3 >= typeArr.length) {
                            z2 = true;
                            break;
                        } else if (typeArr[i3] != this.fields[i3].fieldClass) {
                            break;
                        } else {
                            i3++;
                        }
                    }
                }
                if (z2) {
                    return;
                }
                this.creatorConstructorParameters = ASMUtils.lookupParameterNames(constructor2);
                return;
            }
            this.creatorConstructorParameters = TypeUtils.getKoltinConstructorParameters(cls);
            try {
                this.kotlinDefaultConstructor = cls.getConstructor(new Class[0]);
            } catch (Throwable unused) {
            }
            Annotation[][] parameterAnnotations = TypeUtils.getParameterAnnotations(constructor2);
            for (int i4 = 0; i4 < this.creatorConstructorParameters.length && i4 < parameterAnnotations.length; i4++) {
                Annotation[] annotationArr = parameterAnnotations[i4];
                int length = annotationArr.length;
                int i5 = 0;
                while (true) {
                    if (i5 >= length) {
                        jSONField = null;
                        break;
                    }
                    Annotation annotation = annotationArr[i5];
                    if (annotation instanceof JSONField) {
                        jSONField = (JSONField) annotation;
                        break;
                    }
                    i5++;
                }
                if (jSONField != null) {
                    String strName = jSONField.name();
                    if (strName.length() > 0) {
                        this.creatorConstructorParameters[i4] = strName;
                    }
                }
            }
        }
    }

    public static boolean add(List<FieldInfo> list, FieldInfo fieldInfo) {
        for (int size = list.size() - 1; size >= 0; size--) {
            FieldInfo fieldInfo2 = list.get(size);
            if (fieldInfo2.name.equals(fieldInfo.name) && (!fieldInfo2.getOnly || fieldInfo.getOnly)) {
                if (fieldInfo2.fieldClass.isAssignableFrom(fieldInfo.fieldClass)) {
                    list.set(size, fieldInfo);
                    return true;
                }
                if (fieldInfo2.compareTo(fieldInfo) >= 0) {
                    return false;
                }
                list.set(size, fieldInfo);
                return true;
            }
        }
        list.add(fieldInfo);
        return true;
    }

    public static JavaBeanInfo build(Class<?> cls, Type type, PropertyNamingStrategy propertyNamingStrategy) {
        return build(cls, type, propertyNamingStrategy, false, TypeUtils.compatibleWithJavaBean, false);
    }

    private static Map<TypeVariable, Type> buildGenericInfo(Class<?> cls) {
        Class<? super Object> superclass = cls.getSuperclass();
        HashMap map = null;
        if (superclass == null) {
            return null;
        }
        while (true) {
            Class<? super Object> cls2 = superclass;
            Class<?> cls3 = cls;
            cls = cls2;
            if (cls == null || cls == Object.class) {
                break;
            }
            if (cls3.getGenericSuperclass() instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) cls3.getGenericSuperclass()).getActualTypeArguments();
                TypeVariable<Class<?>>[] typeParameters = cls.getTypeParameters();
                for (int i2 = 0; i2 < actualTypeArguments.length; i2++) {
                    if (map == null) {
                        map = new HashMap();
                    }
                    if (map.containsKey(actualTypeArguments[i2])) {
                        map.put(typeParameters[i2], map.get(actualTypeArguments[i2]));
                    } else {
                        map.put(typeParameters[i2], actualTypeArguments[i2]);
                    }
                }
            }
            superclass = cls.getSuperclass();
        }
        return map;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0019  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void computeFields(java.lang.Class<?> r20, java.lang.reflect.Type r21, com.alibaba.fastjson.PropertyNamingStrategy r22, java.util.List<com.alibaba.fastjson.util.FieldInfo> r23, java.lang.reflect.Field[] r24) {
        /*
            Method dump skipped, instructions count: 235
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.JavaBeanInfo.computeFields(java.lang.Class, java.lang.reflect.Type, com.alibaba.fastjson.PropertyNamingStrategy, java.util.List, java.lang.reflect.Field[]):void");
    }

    public static Class<?> getBuilderClass(JSONType jSONType) {
        return getBuilderClass(null, jSONType);
    }

    public static Constructor<?> getCreatorConstructor(Constructor[] constructorArr) {
        boolean z2;
        Constructor constructor = null;
        for (Constructor constructor2 : constructorArr) {
            if (((JSONCreator) constructor2.getAnnotation(JSONCreator.class)) != null) {
                if (constructor != null) {
                    throw new JSONException("multi-JSONCreator");
                }
                constructor = constructor2;
            }
        }
        if (constructor != null) {
            return constructor;
        }
        for (Constructor constructor3 : constructorArr) {
            Annotation[][] parameterAnnotations = TypeUtils.getParameterAnnotations(constructor3);
            if (parameterAnnotations.length != 0) {
                int length = parameterAnnotations.length;
                int i2 = 0;
                while (true) {
                    z2 = true;
                    if (i2 >= length) {
                        break;
                    }
                    Annotation[] annotationArr = parameterAnnotations[i2];
                    int length2 = annotationArr.length;
                    int i3 = 0;
                    while (true) {
                        if (i3 >= length2) {
                            z2 = false;
                            break;
                        }
                        if (annotationArr[i3] instanceof JSONField) {
                            break;
                        }
                        i3++;
                    }
                    if (!z2) {
                        z2 = false;
                        break;
                    }
                    i2++;
                }
                if (!z2) {
                    continue;
                } else {
                    if (constructor != null) {
                        throw new JSONException("multi-JSONCreator");
                    }
                    constructor = constructor3;
                }
            }
        }
        return constructor;
    }

    public static Constructor<?> getDefaultConstructor(Class<?> cls, Constructor<?>[] constructorArr) {
        Constructor<?> constructor = null;
        if (Modifier.isAbstract(cls.getModifiers())) {
            return null;
        }
        int length = constructorArr.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            Constructor<?> constructor2 = constructorArr[i2];
            if (constructor2.getParameterTypes().length == 0) {
                constructor = constructor2;
                break;
            }
            i2++;
        }
        if (constructor != null || !cls.isMemberClass() || Modifier.isStatic(cls.getModifiers())) {
            return constructor;
        }
        for (Constructor<?> constructor3 : constructorArr) {
            Class<?>[] parameterTypes = constructor3.getParameterTypes();
            if (parameterTypes.length == 1 && parameterTypes[0].equals(cls.getDeclaringClass())) {
                return constructor3;
            }
        }
        return constructor;
    }

    private static Method getFactoryMethod(Class<?> cls, Method[] methodArr, boolean z2) {
        Method method = null;
        for (Method method2 : methodArr) {
            if (Modifier.isStatic(method2.getModifiers()) && cls.isAssignableFrom(method2.getReturnType()) && ((JSONCreator) TypeUtils.getAnnotation(method2, JSONCreator.class)) != null) {
                if (method != null) {
                    throw new JSONException("multi-JSONCreator");
                }
                method = method2;
            }
        }
        if (method != null || !z2) {
            return method;
        }
        for (Method method3 : methodArr) {
            if (TypeUtils.isJacksonCreator(method3)) {
                return method3;
            }
        }
        return method;
    }

    private static FieldInfo getField(List<FieldInfo> list, String str) {
        for (FieldInfo fieldInfo : list) {
            if (fieldInfo.name.equals(str)) {
                return fieldInfo;
            }
            Field field = fieldInfo.field;
            if (field != null && fieldInfo.getAnnotation() != null && field.getName().equals(str)) {
                return fieldInfo;
            }
        }
        return null;
    }

    public static JavaBeanInfo build(Class<?> cls, Type type, PropertyNamingStrategy propertyNamingStrategy, boolean z2, boolean z3) {
        return build(cls, type, propertyNamingStrategy, z2, z3, false);
    }

    public static Class<?> getBuilderClass(Class<?> cls, JSONType jSONType) {
        Class<?> clsBuilder;
        if (cls != null && cls.getName().equals("org.springframework.security.web.savedrequest.DefaultSavedRequest")) {
            return TypeUtils.loadClass("org.springframework.security.web.savedrequest.DefaultSavedRequest$Builder");
        }
        if (jSONType == null || (clsBuilder = jSONType.builder()) == Void.class) {
            return null;
        }
        return clsBuilder;
    }

    /* JADX WARN: Removed duplicated region for block: B:178:0x037b  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x0381  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x04e0  */
    /* JADX WARN: Removed duplicated region for block: B:297:0x06c0  */
    /* JADX WARN: Removed duplicated region for block: B:352:0x083a  */
    /* JADX WARN: Removed duplicated region for block: B:375:0x08a5  */
    /* JADX WARN: Removed duplicated region for block: B:377:0x08ad  */
    /* JADX WARN: Removed duplicated region for block: B:378:0x08b6  */
    /* JADX WARN: Removed duplicated region for block: B:381:0x08d6  */
    /* JADX WARN: Removed duplicated region for block: B:383:0x08dc  */
    /* JADX WARN: Removed duplicated region for block: B:387:0x090c  */
    /* JADX WARN: Removed duplicated region for block: B:390:0x0911  */
    /* JADX WARN: Removed duplicated region for block: B:399:0x0992  */
    /* JADX WARN: Removed duplicated region for block: B:402:0x09a5  */
    /* JADX WARN: Removed duplicated region for block: B:403:0x09ab  */
    /* JADX WARN: Removed duplicated region for block: B:458:0x0b15 A[PHI: r4 r5 r6
      0x0b15: PHI (r4v3 java.lang.reflect.Field[]) = (r4v2 java.lang.reflect.Field[]), (r4v4 java.lang.reflect.Field[]) binds: [B:452:0x0b05, B:457:0x0b13] A[DONT_GENERATE, DONT_INLINE]
      0x0b15: PHI (r5v2 char) = (r5v1 char), (r5v3 char) binds: [B:452:0x0b05, B:457:0x0b13] A[DONT_GENERATE, DONT_INLINE]
      0x0b15: PHI (r6v3 char) = (r6v2 char), (r6v4 char) binds: [B:452:0x0b05, B:457:0x0b13] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.alibaba.fastjson.util.JavaBeanInfo build(java.lang.Class<?> r56, java.lang.reflect.Type r57, com.alibaba.fastjson.PropertyNamingStrategy r58, boolean r59, boolean r60, boolean r61) {
        /*
            Method dump skipped, instructions count: 3014
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.JavaBeanInfo.build(java.lang.Class, java.lang.reflect.Type, com.alibaba.fastjson.PropertyNamingStrategy, boolean, boolean, boolean):com.alibaba.fastjson.util.JavaBeanInfo");
    }
}
