package cn.hutool.core.util;

import cn.hutool.core.annotation.Alias;
import cn.hutool.core.bean.NullWrapperBean;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.UniqueKeySet;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.exceptions.InvocationTargetRuntimeException;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Filter;
import cn.hutool.core.lang.Matcher;
import cn.hutool.core.lang.func.Func0;
import cn.hutool.core.lang.reflect.MethodHandleUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.map.WeakConcurrentMap;
import cn.hutool.core.text.CharSequenceUtil;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/* loaded from: classes.dex */
public class ReflectUtil {
    private static final WeakConcurrentMap<Class<?>, Constructor<?>[]> CONSTRUCTORS_CACHE = new WeakConcurrentMap<>();
    private static final WeakConcurrentMap<Class<?>, Field[]> FIELDS_CACHE = new WeakConcurrentMap<>();
    private static final WeakConcurrentMap<Class<?>, Method[]> METHODS_CACHE = new WeakConcurrentMap<>();

    public static <T> Constructor<T> getConstructor(Class<T> cls, Class<?>... clsArr) throws SecurityException, IllegalArgumentException {
        if (cls == null) {
            return null;
        }
        for (Constructor<T> constructor : getConstructors(cls)) {
            if (ClassUtil.isAllAssignableFrom(constructor.getParameterTypes(), clsArr)) {
                setAccessible(constructor);
                return constructor;
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> Constructor<T>[] getConstructors(Class<T> cls) throws SecurityException, IllegalArgumentException {
        Assert.notNull(cls);
        return CONSTRUCTORS_CACHE.computeIfAbsent((WeakConcurrentMap<Class<?>, Constructor<?>[]>) cls, (Func0<? extends Constructor<?>[]>) new y(cls));
    }

    public static Constructor<?>[] getConstructorsDirectly(Class<?> cls) throws SecurityException {
        return cls.getDeclaredConstructors();
    }

    private static List<Method> getDefaultMethodsFromInterface(Class<?> cls) throws SecurityException {
        ArrayList arrayList = new ArrayList();
        for (Class<?> cls2 : cls.getInterfaces()) {
            for (Method method : cls2.getMethods()) {
                if (!ModifierUtil.isAbstract(method)) {
                    arrayList.add(method);
                }
            }
        }
        return arrayList;
    }

    public static Field getField(Class<?> cls, final String str) throws SecurityException {
        return (Field) ArrayUtil.firstMatch(new Matcher() { // from class: cn.hutool.core.util.c0
            @Override // cn.hutool.core.lang.Matcher
            public final boolean match(Object obj) {
                return ReflectUtil.lambda$getField$0(str, (Field) obj);
            }
        }, getFields(cls));
    }

    public static Map<String, Field> getFieldMap(Class<?> cls) throws SecurityException {
        Field[] fields = getFields(cls);
        HashMap mapNewHashMap = MapUtil.newHashMap(fields.length, true);
        for (Field field : fields) {
            mapNewHashMap.put(field.getName(), field);
        }
        return mapNewHashMap;
    }

    public static String getFieldName(Field field) {
        if (field == null) {
            return null;
        }
        Alias alias = (Alias) field.getAnnotation(Alias.class);
        return alias != null ? alias.value() : field.getName();
    }

    public static Object getFieldValue(Object obj, String str) throws UtilException {
        if (obj == null || CharSequenceUtil.isBlank(str)) {
            return null;
        }
        return getFieldValue(obj, getField(obj instanceof Class ? (Class) obj : obj.getClass(), str));
    }

    public static Field[] getFields(Class<?> cls) throws SecurityException {
        Assert.notNull(cls);
        return FIELDS_CACHE.computeIfAbsent((WeakConcurrentMap<Class<?>, Field[]>) cls, (Func0<? extends Field[]>) new d0(cls));
    }

    public static Field[] getFieldsDirectly(Class<?> cls, boolean z2) throws SecurityException, IllegalArgumentException {
        Assert.notNull(cls);
        Field[] fieldArr = null;
        while (cls != null) {
            Field[] declaredFields = cls.getDeclaredFields();
            fieldArr = fieldArr == null ? declaredFields : (Field[]) ArrayUtil.append((Object[]) fieldArr, (Object[]) declaredFields);
            cls = z2 ? cls.getSuperclass() : null;
        }
        return fieldArr;
    }

    public static Object[] getFieldsValue(Object obj) throws SecurityException {
        if (obj == null) {
            return null;
        }
        Field[] fields = getFields(obj instanceof Class ? (Class) obj : obj.getClass());
        if (fields == null) {
            return null;
        }
        Object[] objArr = new Object[fields.length];
        for (int i2 = 0; i2 < fields.length; i2++) {
            objArr[i2] = getFieldValue(obj, fields[i2]);
        }
        return objArr;
    }

    public static Method getMethod(Class<?> cls, String str, Class<?>... clsArr) throws SecurityException {
        return getMethod(cls, false, str, clsArr);
    }

    public static Method getMethodByName(Class<?> cls, String str) throws SecurityException {
        return getMethodByName(cls, false, str);
    }

    public static Method getMethodByNameIgnoreCase(Class<?> cls, String str) throws SecurityException {
        return getMethodByName(cls, true, str);
    }

    public static Method getMethodIgnoreCase(Class<?> cls, String str, Class<?>... clsArr) throws SecurityException {
        return getMethod(cls, true, str, clsArr);
    }

    public static Set<String> getMethodNames(Class<?> cls) throws SecurityException, IllegalArgumentException {
        HashSet hashSet = new HashSet();
        for (Method method : getMethods(cls)) {
            hashSet.add(method.getName());
        }
        return hashSet;
    }

    public static Method getMethodOfObj(Object obj, String str, Object... objArr) throws SecurityException {
        if (obj == null || CharSequenceUtil.isBlank(str)) {
            return null;
        }
        return getMethod(obj.getClass(), str, ClassUtil.getClasses(objArr));
    }

    public static Method[] getMethods(Class<?> cls, Filter<Method> filter) throws SecurityException {
        if (cls == null) {
            return null;
        }
        return (Method[]) ArrayUtil.filter(getMethods(cls), filter);
    }

    public static Method[] getMethodsDirectly(Class<?> cls, boolean z2, boolean z3) throws SecurityException, IllegalArgumentException {
        Assert.notNull(cls);
        if (cls.isInterface()) {
            return z2 ? cls.getMethods() : cls.getDeclaredMethods();
        }
        UniqueKeySet uniqueKeySet = new UniqueKeySet(true, new Function() { // from class: cn.hutool.core.util.z
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ReflectUtil.getUniqueKey((Method) obj);
            }
        });
        while (cls != null && (z3 || Object.class != cls)) {
            uniqueKeySet.addAllIfAbsent(Arrays.asList(cls.getDeclaredMethods()));
            uniqueKeySet.addAllIfAbsent(getDefaultMethodsFromInterface(cls));
            cls = (!z2 || cls.isInterface()) ? null : cls.getSuperclass();
        }
        return (Method[]) uniqueKeySet.toArray(new Method[0]);
    }

    public static Method getPublicMethod(Class<?> cls, String str, Class<?>... clsArr) throws SecurityException {
        try {
            return cls.getMethod(str, clsArr);
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }

    public static Set<String> getPublicMethodNames(Class<?> cls) {
        HashSet hashSet = new HashSet();
        Method[] publicMethods = getPublicMethods(cls);
        if (ArrayUtil.isNotEmpty((Object[]) publicMethods)) {
            for (Method method : publicMethods) {
                hashSet.add(method.getName());
            }
        }
        return hashSet;
    }

    public static Method[] getPublicMethods(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        return cls.getMethods();
    }

    public static Object getStaticFieldValue(Field field) throws UtilException {
        return getFieldValue((Object) null, field);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getUniqueKey(Method method) {
        StringBuilder sb = new StringBuilder();
        sb.append(method.getReturnType().getName());
        sb.append('#');
        sb.append(method.getName());
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (int i2 = 0; i2 < parameterTypes.length; i2++) {
            if (i2 == 0) {
                sb.append(':');
            } else {
                sb.append(',');
            }
            sb.append(parameterTypes[i2].getName());
        }
        return sb.toString();
    }

    public static boolean hasField(Class<?> cls, String str) throws SecurityException {
        return getField(cls, str) != null;
    }

    public static <T> T invoke(Object obj, Method method, Object... objArr) throws UtilException {
        try {
            return (T) invokeRaw(obj, method, objArr);
        } catch (IllegalAccessException e2) {
            throw new UtilException(e2);
        } catch (InvocationTargetException e3) {
            throw new InvocationTargetRuntimeException(e3);
        }
    }

    public static <T> T invokeRaw(Object obj, Method method, Object... objArr) throws Exception {
        Object obj2;
        setAccessible(method);
        Class<?>[] parameterTypes = method.getParameterTypes();
        int length = parameterTypes.length;
        Object[] objArr2 = new Object[length];
        if (objArr != null) {
            for (int i2 = 0; i2 < length; i2++) {
                if (i2 >= objArr.length || (obj2 = objArr[i2]) == null) {
                    objArr2[i2] = ClassUtil.getDefaultValue(parameterTypes[i2]);
                } else if (obj2 instanceof NullWrapperBean) {
                    objArr2[i2] = null;
                } else if (parameterTypes[i2].isAssignableFrom(obj2.getClass())) {
                    objArr2[i2] = objArr[i2];
                } else {
                    Object objConvertWithCheck = Convert.convertWithCheck(parameterTypes[i2], objArr[i2], null, true);
                    if (objConvertWithCheck != null) {
                        objArr2[i2] = objConvertWithCheck;
                    } else {
                        objArr2[i2] = objArr[i2];
                    }
                }
            }
        }
        if (method.isDefault()) {
            return (T) MethodHandleUtil.invokeSpecial(obj, method, objArr);
        }
        if (ClassUtil.isStatic(method)) {
            obj = null;
        }
        return (T) method.invoke(obj, objArr2);
    }

    public static <T> T invokeStatic(Method method, Object... objArr) throws UtilException {
        return (T) invoke((Object) null, method, objArr);
    }

    public static <T> T invokeWithCheck(Object obj, Method method, Object... objArr) throws Throwable {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (objArr != null) {
            Assert.isTrue(objArr.length == parameterTypes.length, "Params length [{}] is not fit for param length [{}] of method !", Integer.valueOf(objArr.length), Integer.valueOf(parameterTypes.length));
            for (int i2 = 0; i2 < objArr.length; i2++) {
                Class<?> cls = parameterTypes[i2];
                if (cls.isPrimitive() && objArr[i2] == null) {
                    objArr[i2] = ClassUtil.getDefaultValue(cls);
                }
            }
        }
        return (T) invoke(obj, method, objArr);
    }

    public static boolean isEmptyParam(Method method) {
        return method.getParameterCount() == 0;
    }

    public static boolean isEqualsMethod(Method method) {
        return method != null && 1 == method.getParameterCount() && "equals".equals(method.getName()) && method.getParameterTypes()[0] == Object.class;
    }

    public static boolean isGetterOrSetter(Method method, boolean z2) {
        int parameterCount;
        if (method == null || (parameterCount = method.getParameterCount()) > 1) {
            return false;
        }
        String name = method.getName();
        if ("getClass".equals(name)) {
            return false;
        }
        if (z2) {
            name = name.toLowerCase();
        }
        if (parameterCount == 0) {
            return name.startsWith("get") || name.startsWith("is");
        }
        if (parameterCount != 1) {
            return false;
        }
        return name.startsWith("set");
    }

    public static boolean isGetterOrSetterIgnoreCase(Method method) {
        return isGetterOrSetter(method, true);
    }

    public static boolean isHashCodeMethod(Method method) {
        return method != null && "hashCode".equals(method.getName()) && isEmptyParam(method);
    }

    public static boolean isOuterClassField(Field field) {
        return "this$0".equals(field.getName());
    }

    public static boolean isToStringMethod(Method method) {
        return method != null && "toString".equals(method.getName()) && isEmptyParam(method);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getField$0(String str, Field field) {
        return str.equals(getFieldName(field));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Field[] lambda$getFields$54eedd5e$1(Class cls) throws Exception {
        return getFieldsDirectly(cls, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Method[] lambda$getMethods$ea73458f$1(Class cls) throws Exception {
        return getMethodsDirectly(cls, true, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getPublicMethods$1(HashSet hashSet, Method method) {
        return !hashSet.contains(method);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getPublicMethods$2(HashSet hashSet, Method method) {
        return !hashSet.contains(method.getName());
    }

    public static <T> T newInstance(String str) throws UtilException {
        try {
            return (T) Class.forName(str).newInstance();
        } catch (Exception e2) {
            throw new UtilException(e2, "Instance class [{}] error!", str);
        }
    }

    public static <T> T newInstanceIfPossible(Class<T> cls) {
        Assert.notNull(cls);
        if (cls.isPrimitive()) {
            return (T) ClassUtil.getPrimitiveDefaultValue(cls);
        }
        if (cls.isAssignableFrom(AbstractMap.class)) {
            cls = (Class<T>) HashMap.class;
        } else if (cls.isAssignableFrom(List.class)) {
            cls = (Class<T>) ArrayList.class;
        } else if (cls.isAssignableFrom(Set.class)) {
            cls = (Class<T>) HashSet.class;
        }
        try {
            return (T) newInstance(cls, new Object[0]);
        } catch (Exception unused) {
            if (cls.isEnum()) {
                return cls.getEnumConstants()[0];
            }
            if (cls.isArray()) {
                return (T) Array.newInstance(cls.getComponentType(), 0);
            }
            Constructor[] constructors = getConstructors(cls);
            int length = constructors.length;
            for (int i2 = 0; i2 < length; i2++) {
                Constructor constructor = constructors[i2];
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                if (parameterTypes.length != 0) {
                    setAccessible(constructor);
                    try {
                        return (T) constructor.newInstance(ClassUtil.getDefaultValues(parameterTypes));
                    } catch (Exception unused2) {
                        continue;
                    }
                }
            }
            return null;
        }
    }

    public static void removeFinalModify(Field field) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        ModifierUtil.removeFinalModify(field);
    }

    public static <T extends AccessibleObject> T setAccessible(T t2) throws SecurityException {
        if (t2 != null && !t2.isAccessible()) {
            t2.setAccessible(true);
        }
        return t2;
    }

    public static void setFieldValue(Object obj, String str, Object obj2) throws UtilException {
        Assert.notNull(obj);
        Assert.notBlank(str);
        Field field = getField(obj instanceof Class ? (Class) obj : obj.getClass(), str);
        Assert.notNull(field, "Field [{}] is not exist in [{}]", str, obj.getClass().getName());
        setFieldValue(obj, field, obj2);
    }

    public static Method getMethod(Class<?> cls, boolean z2, String str, Class<?>... clsArr) throws SecurityException, IllegalArgumentException {
        Method method = null;
        if (cls != null && !CharSequenceUtil.isBlank(str)) {
            Method[] methods = getMethods(cls);
            if (ArrayUtil.isNotEmpty((Object[]) methods)) {
                for (Method method2 : methods) {
                    if (CharSequenceUtil.equals(str, method2.getName(), z2) && ClassUtil.isAllAssignableFrom(method2.getParameterTypes(), clsArr) && (method == null || method.getReturnType().isAssignableFrom(method2.getReturnType()))) {
                        method = method2;
                    }
                }
            }
        }
        return method;
    }

    public static Method getMethodByName(Class<?> cls, boolean z2, String str) throws SecurityException, IllegalArgumentException {
        Method method = null;
        if (cls != null && !CharSequenceUtil.isBlank(str)) {
            Method[] methods = getMethods(cls);
            if (ArrayUtil.isNotEmpty((Object[]) methods)) {
                for (Method method2 : methods) {
                    if (CharSequenceUtil.equals(str, method2.getName(), z2) && (method == null || method.getReturnType().isAssignableFrom(method2.getReturnType()))) {
                        method = method2;
                    }
                }
            }
        }
        return method;
    }

    public static Method[] getMethods(Class<?> cls) throws SecurityException, IllegalArgumentException {
        Assert.notNull(cls);
        return METHODS_CACHE.computeIfAbsent((WeakConcurrentMap<Class<?>, Method[]>) cls, (Func0<? extends Method[]>) new b0(cls));
    }

    public static List<Method> getPublicMethods(Class<?> cls, Filter<Method> filter) {
        if (cls == null) {
            return null;
        }
        Method[] publicMethods = getPublicMethods(cls);
        if (filter == null) {
            return CollUtil.newArrayList(publicMethods);
        }
        ArrayList arrayList = new ArrayList();
        for (Method method : publicMethods) {
            if (filter.accept(method)) {
                arrayList.add(method);
            }
        }
        return arrayList;
    }

    public static Object getFieldValue(Object obj, Field field) throws UtilException, SecurityException {
        if (field == null) {
            return null;
        }
        if (obj instanceof Class) {
            obj = null;
        }
        setAccessible(field);
        try {
            return field.get(obj);
        } catch (IllegalAccessException e2) {
            throw new UtilException(e2, "IllegalAccess for {}.{}", field.getDeclaringClass(), field.getName());
        }
    }

    public static Field[] getFields(Class<?> cls, Filter<Field> filter) throws SecurityException {
        return (Field[]) ArrayUtil.filter(getFields(cls), filter);
    }

    public static <T> T newInstance(Class<T> cls, Object... objArr) throws UtilException, SecurityException, IllegalArgumentException {
        if (ArrayUtil.isEmpty(objArr)) {
            Constructor constructor = getConstructor(cls, new Class[0]);
            if (constructor != null) {
                try {
                    return (T) constructor.newInstance(new Object[0]);
                } catch (Exception e2) {
                    throw new UtilException(e2, "Instance class [{}] error!", cls);
                }
            }
            throw new UtilException("No constructor for [{}]", cls);
        }
        Class<?>[] classes = ClassUtil.getClasses(objArr);
        Constructor constructor2 = getConstructor(cls, classes);
        if (constructor2 != null) {
            try {
                return (T) constructor2.newInstance(objArr);
            } catch (Exception e3) {
                throw new UtilException(e3, "Instance class [{}] error!", cls);
            }
        }
        throw new UtilException("No Constructor matched for parameter types: [{}]", classes);
    }

    public static <T> T invoke(Object obj, String str, Object... objArr) throws UtilException {
        Assert.notNull(obj, "Object to get method must be not null!", new Object[0]);
        Assert.notBlank(str, "Method name must be not blank!", new Object[0]);
        Method methodOfObj = getMethodOfObj(obj, str, objArr);
        if (methodOfObj != null) {
            return (T) invoke(obj, methodOfObj, objArr);
        }
        throw new UtilException("No such method: [{}] from [{}]", str, obj.getClass());
    }

    public static void setFieldValue(Object obj, Field field, Object obj2) throws UtilException {
        Object objConvert;
        Assert.notNull(field, "Field in [{}] not exist !", obj);
        Class<?> type = field.getType();
        if (obj2 != null) {
            if (!type.isAssignableFrom(obj2.getClass()) && (objConvert = Convert.convert((Class<Object>) type, obj2)) != null) {
                obj2 = objConvert;
            }
        } else {
            obj2 = ClassUtil.getDefaultValue(type);
        }
        setAccessible(field);
        try {
            field.set(obj instanceof Class ? null : obj, obj2);
        } catch (IllegalAccessException e2) {
            throw new UtilException(e2, "IllegalAccess for {}.{}", obj, field.getName());
        }
    }

    public static List<Method> getPublicMethods(Class<?> cls, Method... methodArr) {
        final HashSet hashSetNewHashSet = CollUtil.newHashSet(methodArr);
        return getPublicMethods(cls, (Filter<Method>) new Filter() { // from class: cn.hutool.core.util.a0
            @Override // cn.hutool.core.lang.Filter
            public final boolean accept(Object obj) {
                return ReflectUtil.lambda$getPublicMethods$1(hashSetNewHashSet, (Method) obj);
            }
        });
    }

    public static List<Method> getPublicMethods(Class<?> cls, String... strArr) {
        final HashSet hashSetNewHashSet = CollUtil.newHashSet(strArr);
        return getPublicMethods(cls, (Filter<Method>) new Filter() { // from class: cn.hutool.core.util.x
            @Override // cn.hutool.core.lang.Filter
            public final boolean accept(Object obj) {
                return ReflectUtil.lambda$getPublicMethods$2(hashSetNewHashSet, (Method) obj);
            }
        });
    }
}
