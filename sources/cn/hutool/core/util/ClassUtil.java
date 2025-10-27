package cn.hutool.core.util;

import cn.hutool.core.bean.NullWrapperBean;
import cn.hutool.core.convert.BasicType;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.ClassScanner;
import cn.hutool.core.lang.Filter;
import cn.hutool.core.lang.Singleton;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URL;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/* loaded from: classes.dex */
public class ClassUtil {
    public static boolean equals(Class<?> cls, String str, boolean z2) {
        if (cls == null || CharSequenceUtil.isBlank(str)) {
            return false;
        }
        return z2 ? str.equalsIgnoreCase(cls.getName()) || str.equalsIgnoreCase(cls.getSimpleName()) : str.equals(cls.getName()) || str.equals(cls.getSimpleName());
    }

    public static <T> Class<T> getClass(T t2) {
        if (t2 == null) {
            return null;
        }
        return (Class<T>) t2.getClass();
    }

    public static ClassLoader getClassLoader() {
        return ClassLoaderUtil.getClassLoader();
    }

    public static String getClassName(Object obj, boolean z2) {
        if (obj == null) {
            return null;
        }
        return getClassName(obj.getClass(), z2);
    }

    public static String getClassPath() {
        return getClassPath(false);
    }

    public static Set<String> getClassPathResources() {
        return getClassPathResources(false);
    }

    public static URL getClassPathURL() {
        return getResourceURL("");
    }

    public static Set<String> getClassPaths(String str) {
        return getClassPaths(str, false);
    }

    public static Class<?>[] getClasses(Object... objArr) {
        Class<?>[] clsArr = new Class[objArr.length];
        for (int i2 = 0; i2 < objArr.length; i2++) {
            Object obj = objArr[i2];
            if (obj instanceof NullWrapperBean) {
                clsArr[i2] = ((NullWrapperBean) obj).getWrappedClass();
            } else if (obj == null) {
                clsArr[i2] = Object.class;
            } else {
                clsArr[i2] = obj.getClass();
            }
        }
        return clsArr;
    }

    public static ClassLoader getContextClassLoader() {
        return ClassLoaderUtil.getContextClassLoader();
    }

    public static Field getDeclaredField(Class<?> cls, String str) throws SecurityException {
        if (cls != null && !CharSequenceUtil.isBlank(str)) {
            try {
                return cls.getDeclaredField(str);
            } catch (NoSuchFieldException unused) {
            }
        }
        return null;
    }

    public static Field[] getDeclaredFields(Class<?> cls) throws SecurityException {
        if (cls == null) {
            return null;
        }
        return cls.getDeclaredFields();
    }

    public static Method getDeclaredMethod(Class<?> cls, String str, Class<?>... clsArr) throws SecurityException {
        return ReflectUtil.getMethod(cls, str, clsArr);
    }

    public static Set<String> getDeclaredMethodNames(Class<?> cls) {
        return ReflectUtil.getMethodNames(cls);
    }

    public static Method getDeclaredMethodOfObj(Object obj, String str, Object... objArr) throws SecurityException {
        return getDeclaredMethod(obj.getClass(), str, getClasses(objArr));
    }

    public static Method[] getDeclaredMethods(Class<?> cls) {
        return ReflectUtil.getMethods(cls);
    }

    public static Object getDefaultValue(Class<?> cls) {
        if (cls.isPrimitive()) {
            return getPrimitiveDefaultValue(cls);
        }
        return null;
    }

    public static Object[] getDefaultValues(Class<?>... clsArr) {
        Object[] objArr = new Object[clsArr.length];
        for (int i2 = 0; i2 < clsArr.length; i2++) {
            objArr[i2] = getDefaultValue(clsArr[i2]);
        }
        return objArr;
    }

    public static Class<?> getEnclosingClass(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        return cls.getEnclosingClass();
    }

    public static String[] getJavaClassPaths() {
        return System.getProperty("java.class.path").split(System.getProperty("path.separator"));
    }

    public static URL getLocation(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        return cls.getProtectionDomain().getCodeSource().getLocation();
    }

    public static String getLocationPath(Class<?> cls) {
        URL location = getLocation(cls);
        if (location == null) {
            return null;
        }
        return location.getPath();
    }

    public static String getPackage(Class<?> cls) {
        String name;
        int iLastIndexOf;
        return (cls == null || (iLastIndexOf = (name = cls.getName()).lastIndexOf(StrPool.DOT)) == -1) ? "" : name.substring(0, iLastIndexOf);
    }

    public static String getPackagePath(Class<?> cls) {
        return getPackage(cls).replace('.', '/');
    }

    public static Object getPrimitiveDefaultValue(Class<?> cls) {
        if (Long.TYPE == cls) {
            return 0L;
        }
        if (Integer.TYPE == cls) {
            return 0;
        }
        if (Short.TYPE == cls) {
            return (short) 0;
        }
        if (Character.TYPE == cls) {
            return (char) 0;
        }
        if (Byte.TYPE == cls) {
            return (byte) 0;
        }
        if (Double.TYPE == cls) {
            return Double.valueOf(0.0d);
        }
        if (Float.TYPE == cls) {
            return Float.valueOf(0.0f);
        }
        if (Boolean.TYPE == cls) {
            return Boolean.FALSE;
        }
        return null;
    }

    public static Method getPublicMethod(Class<?> cls, String str, Class<?>... clsArr) throws SecurityException {
        return ReflectUtil.getPublicMethod(cls, str, clsArr);
    }

    public static Set<String> getPublicMethodNames(Class<?> cls) {
        return ReflectUtil.getPublicMethodNames(cls);
    }

    public static Method[] getPublicMethods(Class<?> cls) {
        return ReflectUtil.getPublicMethods(cls);
    }

    public static URL getResourceURL(String str) throws IORuntimeException {
        return ResourceUtil.getResource(str);
    }

    public static URL getResourceUrl(String str, Class<?> cls) {
        return ResourceUtil.getResource(str, cls);
    }

    public static List<URL> getResources(String str) {
        return ResourceUtil.getResources(str);
    }

    public static String getShortClassName(String str) {
        List<String> listSplit = CharSequenceUtil.split((CharSequence) str, '.');
        if (listSplit == null || listSplit.size() < 2) {
            return str;
        }
        int size = listSplit.size();
        StringBuilder sbBuilder = StrUtil.builder();
        sbBuilder.append(listSplit.get(0).charAt(0));
        int i2 = 1;
        while (true) {
            int i3 = size - 1;
            if (i2 >= i3) {
                sbBuilder.append('.');
                sbBuilder.append(listSplit.get(i3));
                return sbBuilder.toString();
            }
            sbBuilder.append('.');
            sbBuilder.append(listSplit.get(i2).charAt(0));
            i2++;
        }
    }

    public static Class<?> getTypeArgument(Class<?> cls) {
        return getTypeArgument(cls, 0);
    }

    public static <T> T invoke(String str, Object[] objArr) {
        return (T) invoke(str, false, objArr);
    }

    public static boolean isAbstract(Class<?> cls) {
        return Modifier.isAbstract(cls.getModifiers());
    }

    public static boolean isAbstractOrInterface(Class<?> cls) {
        return isAbstract(cls) || isInterface(cls);
    }

    public static boolean isAllAssignableFrom(Class<?>[] clsArr, Class<?>[] clsArr2) {
        if (ArrayUtil.isEmpty((Object[]) clsArr) && ArrayUtil.isEmpty((Object[]) clsArr2)) {
            return true;
        }
        if (clsArr == null || clsArr2 == null || clsArr.length != clsArr2.length) {
            return false;
        }
        for (int i2 = 0; i2 < clsArr.length; i2++) {
            Class<?> cls = clsArr[i2];
            Class<?> cls2 = clsArr2[i2];
            if (isBasicType(cls) && isBasicType(cls2)) {
                if (BasicType.unWrap(cls) != BasicType.unWrap(cls2)) {
                    return false;
                }
            } else if (!cls.isAssignableFrom(cls2)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAssignable(Class<?> cls, Class<?> cls2) {
        if (cls == null || cls2 == null) {
            return false;
        }
        if (cls.isAssignableFrom(cls2)) {
            return true;
        }
        if (cls.isPrimitive()) {
            return cls.equals(BasicType.WRAPPER_PRIMITIVE_MAP.get(cls2));
        }
        Class<?> cls3 = BasicType.PRIMITIVE_WRAPPER_MAP.get(cls2);
        return cls3 != null && cls.isAssignableFrom(cls3);
    }

    public static boolean isBasicType(Class<?> cls) {
        if (cls == null) {
            return false;
        }
        return cls.isPrimitive() || isPrimitiveWrapper(cls);
    }

    public static boolean isEnum(Class<?> cls) {
        return cls != null && cls.isEnum();
    }

    public static boolean isInterface(Class<?> cls) {
        return cls.isInterface();
    }

    public static boolean isJdkClass(Class<?> cls) {
        Package r02 = cls.getPackage();
        if (r02 == null) {
            return false;
        }
        String name = r02.getName();
        return name.startsWith("java.") || name.startsWith("javax.") || cls.getClassLoader() == null;
    }

    public static boolean isNormalClass(Class<?> cls) {
        return (cls == null || cls.isInterface() || isAbstract(cls) || cls.isEnum() || cls.isArray() || cls.isAnnotation() || cls.isSynthetic() || cls.isPrimitive()) ? false : true;
    }

    public static boolean isNotPublic(Class<?> cls) {
        return !isPublic(cls);
    }

    public static boolean isPrimitiveWrapper(Class<?> cls) {
        if (cls == null) {
            return false;
        }
        return BasicType.WRAPPER_PRIMITIVE_MAP.containsKey(cls);
    }

    public static boolean isPublic(Class<?> cls) {
        if (cls != null) {
            return Modifier.isPublic(cls.getModifiers());
        }
        throw new NullPointerException("Class to provided is null.");
    }

    public static boolean isSimpleTypeOrArray(Class<?> cls) {
        if (cls == null) {
            return false;
        }
        return isSimpleValueType(cls) || (cls.isArray() && isSimpleValueType(cls.getComponentType()));
    }

    public static boolean isSimpleValueType(Class<?> cls) {
        return isBasicType(cls) || cls.isEnum() || CharSequence.class.isAssignableFrom(cls) || Number.class.isAssignableFrom(cls) || Date.class.isAssignableFrom(cls) || cls.equals(URI.class) || cls.equals(URL.class) || cls.equals(Locale.class) || cls.equals(Class.class) || TemporalAccessor.class.isAssignableFrom(cls);
    }

    public static boolean isStatic(Method method) throws IllegalArgumentException {
        Assert.notNull(method, "Method to provided is null.", new Object[0]);
        return Modifier.isStatic(method.getModifiers());
    }

    public static boolean isTopLevelClass(Class<?> cls) {
        return cls != null && getEnclosingClass(cls) == null;
    }

    public static <T> Class<T> loadClass(String str, boolean z2) {
        return (Class<T>) ClassLoaderUtil.loadClass(str, z2);
    }

    public static Set<Class<?>> scanPackage() {
        return ClassScanner.scanPackage();
    }

    public static Set<Class<?>> scanPackageByAnnotation(String str, Class<? extends Annotation> cls) {
        return ClassScanner.scanPackageByAnnotation(str, cls);
    }

    public static Set<Class<?>> scanPackageBySuper(String str, Class<?> cls) {
        return ClassScanner.scanPackageBySuper(str, cls);
    }

    public static Method setAccessible(Method method) throws SecurityException {
        if (method != null && !method.isAccessible()) {
            method.setAccessible(true);
        }
        return method;
    }

    public static String getClassPath(boolean z2) {
        URL classPathURL = getClassPathURL();
        return FileUtil.normalize(z2 ? classPathURL.getPath() : URLUtil.getDecodedPath(classPathURL));
    }

    public static Set<String> getClassPathResources(boolean z2) {
        return getClassPaths("", z2);
    }

    public static Set<String> getClassPaths(String str, boolean z2) throws UtilException, IOException {
        String strReplace = str.replace(StrPool.DOT, "/");
        try {
            Enumeration<URL> resources = getClassLoader().getResources(strReplace);
            HashSet hashSet = new HashSet();
            while (resources.hasMoreElements()) {
                String path = resources.nextElement().getPath();
                if (z2) {
                    path = URLUtil.decode(path, CharsetUtil.systemCharsetName());
                }
                hashSet.add(path);
            }
            return hashSet;
        } catch (IOException e2) {
            throw new UtilException(e2, "Loading classPath [{}] error!", strReplace);
        }
    }

    public static List<Method> getPublicMethods(Class<?> cls, Filter<Method> filter) {
        return ReflectUtil.getPublicMethods(cls, filter);
    }

    public static Class<?> getTypeArgument(Class<?> cls, int i2) {
        return TypeUtil.getClass(TypeUtil.getTypeArgument(cls, i2));
    }

    public static <T> T invoke(String str, boolean z2, Object... objArr) {
        if (CharSequenceUtil.isBlank(str)) {
            throw new UtilException("Blank classNameDotMethodName!");
        }
        int iLastIndexOf = str.lastIndexOf(35);
        if (iLastIndexOf <= 0) {
            iLastIndexOf = str.lastIndexOf(46);
        }
        if (iLastIndexOf > 0) {
            return (T) invoke(str.substring(0, iLastIndexOf), str.substring(iLastIndexOf + 1), z2, objArr);
        }
        throw new UtilException("Invalid classNameWithMethodName [{}]!", str);
    }

    public static boolean isNotPublic(Method method) {
        return !isPublic(method);
    }

    public static <T> Class<T> loadClass(String str) {
        return loadClass(str, true);
    }

    public static Set<Class<?>> scanPackage(String str) {
        return ClassScanner.scanPackage(str);
    }

    public static String getClassName(Class<?> cls, boolean z2) {
        if (cls == null) {
            return null;
        }
        return z2 ? cls.getSimpleName() : cls.getName();
    }

    public static List<Method> getPublicMethods(Class<?> cls, Method... methodArr) {
        return ReflectUtil.getPublicMethods(cls, methodArr);
    }

    public static boolean isPublic(Method method) throws IllegalArgumentException {
        Assert.notNull(method, "Method to provided is null.", new Object[0]);
        return Modifier.isPublic(method.getModifiers());
    }

    public static Set<Class<?>> scanPackage(String str, Filter<Class<?>> filter) {
        return ClassScanner.scanPackage(str, filter);
    }

    public static List<Method> getPublicMethods(Class<?> cls, String... strArr) {
        return ReflectUtil.getPublicMethods(cls, strArr);
    }

    public static <T> T invoke(String str, String str2, Object[] objArr) {
        return (T) invoke(str, str2, false, objArr);
    }

    public static <T> T invoke(String str, String str2, boolean z2, Object... objArr) throws NoSuchMethodException {
        Class clsLoadClass = loadClass(str);
        try {
            Method declaredMethod = getDeclaredMethod(clsLoadClass, str2, getClasses(objArr));
            if (declaredMethod != null) {
                if (isStatic(declaredMethod)) {
                    return (T) ReflectUtil.invoke((Object) null, declaredMethod, objArr);
                }
                return (T) ReflectUtil.invoke(z2 ? Singleton.get(clsLoadClass, new Object[0]) : clsLoadClass.newInstance(), declaredMethod, objArr);
            }
            throw new NoSuchMethodException(CharSequenceUtil.format("No such method: [{}]", str2));
        } catch (Exception e2) {
            throw new UtilException(e2);
        }
    }
}
