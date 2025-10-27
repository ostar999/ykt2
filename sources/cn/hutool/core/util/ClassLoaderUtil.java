package cn.hutool.core.util;

import cn.hutool.core.convert.BasicType;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.JarClassLoader;
import cn.hutool.core.map.SafeConcurrentHashMap;
import cn.hutool.core.text.CharSequenceUtil;
import java.io.File;
import java.lang.reflect.Array;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes.dex */
public class ClassLoaderUtil {
    private static final String ARRAY_SUFFIX = "[]";
    private static final char INNER_CLASS_SEPARATOR = '$';
    private static final String INTERNAL_ARRAY_PREFIX = "[";
    private static final String NON_PRIMITIVE_ARRAY_PREFIX = "[L";
    private static final char PACKAGE_SEPARATOR = '.';
    private static final Map<String, Class<?>> PRIMITIVE_TYPE_NAME_MAP = new SafeConcurrentHashMap(32);

    static {
        ArrayList<Class<?>> arrayList = new ArrayList(32);
        arrayList.addAll(BasicType.PRIMITIVE_WRAPPER_MAP.keySet());
        arrayList.add(boolean[].class);
        arrayList.add(byte[].class);
        arrayList.add(char[].class);
        arrayList.add(double[].class);
        arrayList.add(float[].class);
        arrayList.add(int[].class);
        arrayList.add(long[].class);
        arrayList.add(short[].class);
        arrayList.add(Void.TYPE);
        for (Class<?> cls : arrayList) {
            PRIMITIVE_TYPE_NAME_MAP.put(cls.getName(), cls);
        }
    }

    private static Class<?> doLoadClass(String str, ClassLoader classLoader, boolean z2) {
        if (str.endsWith("[]")) {
            return Array.newInstance(loadClass(str.substring(0, str.length() - 2), classLoader, z2), 0).getClass();
        }
        if (str.startsWith(NON_PRIMITIVE_ARRAY_PREFIX) && str.endsWith(com.alipay.sdk.util.h.f3376b)) {
            return Array.newInstance(loadClass(str.substring(2, str.length() - 1), classLoader, z2), 0).getClass();
        }
        if (str.startsWith("[")) {
            return Array.newInstance(loadClass(str.substring(1), classLoader, z2), 0).getClass();
        }
        if (classLoader == null) {
            classLoader = getClassLoader();
        }
        try {
            return Class.forName(str, z2, classLoader);
        } catch (ClassNotFoundException e2) {
            Class<?> clsTryLoadInnerClass = tryLoadInnerClass(str, classLoader, z2);
            if (clsTryLoadInnerClass != null) {
                return clsTryLoadInnerClass;
            }
            throw new UtilException(e2);
        }
    }

    public static ClassLoader getClassLoader() {
        ClassLoader contextClassLoader = getContextClassLoader();
        if (contextClassLoader != null) {
            return contextClassLoader;
        }
        ClassLoader classLoader = ClassLoaderUtil.class.getClassLoader();
        return classLoader == null ? getSystemClassLoader() : classLoader;
    }

    public static ClassLoader getContextClassLoader() {
        return System.getSecurityManager() == null ? Thread.currentThread().getContextClassLoader() : (ClassLoader) AccessController.doPrivileged(new PrivilegedAction() { // from class: cn.hutool.core.util.i
            @Override // java.security.PrivilegedAction
            public final Object run() {
                return ClassLoaderUtil.lambda$getContextClassLoader$0();
            }
        });
    }

    public static JarClassLoader getJarClassLoader(File file) {
        return JarClassLoader.load(file);
    }

    public static ClassLoader getSystemClassLoader() {
        return System.getSecurityManager() == null ? ClassLoader.getSystemClassLoader() : (ClassLoader) AccessController.doPrivileged(new PrivilegedAction() { // from class: cn.hutool.core.util.h
            @Override // java.security.PrivilegedAction
            public final Object run() {
                return ClassLoader.getSystemClassLoader();
            }
        });
    }

    public static boolean isPresent(String str) {
        return isPresent(str, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ClassLoader lambda$getContextClassLoader$0() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static Class<?> loadClass(String str) throws UtilException {
        return loadClass(str, true);
    }

    public static Class<?> loadPrimitiveClass(String str) {
        if (CharSequenceUtil.isNotBlank(str)) {
            String strTrim = str.trim();
            if (strTrim.length() <= 8) {
                return PRIMITIVE_TYPE_NAME_MAP.get(strTrim);
            }
        }
        return null;
    }

    private static Class<?> tryLoadInnerClass(String str, ClassLoader classLoader, boolean z2) {
        int iLastIndexOf = str.lastIndexOf(46);
        if (iLastIndexOf <= 0) {
            return null;
        }
        try {
            return Class.forName(str.substring(0, iLastIndexOf) + '$' + str.substring(iLastIndexOf + 1), z2, classLoader);
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public static boolean isPresent(String str, ClassLoader classLoader) {
        try {
            loadClass(str, classLoader, false);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static Class<?> loadClass(String str, boolean z2) throws UtilException {
        return loadClass(str, null, z2);
    }

    public static Class<?> loadClass(String str, ClassLoader classLoader, boolean z2) throws UtilException, IllegalArgumentException {
        Assert.notNull(str, "Name must not be null", new Object[0]);
        String strReplace = str.replace('/', '.');
        if (classLoader == null) {
            classLoader = getClassLoader();
        }
        Class<?> clsLoadPrimitiveClass = loadPrimitiveClass(strReplace);
        return clsLoadPrimitiveClass == null ? doLoadClass(strReplace, classLoader, z2) : clsLoadPrimitiveClass;
    }

    public static Class<?> loadClass(File file, String str) {
        try {
            return getJarClassLoader(file).loadClass(str);
        } catch (ClassNotFoundException e2) {
            throw new UtilException(e2);
        }
    }
}
