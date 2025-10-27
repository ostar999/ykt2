package com.alibaba.fastjson.util;

import androidx.exifinterface.media.ExifInterface;
import cn.hutool.core.text.StrPool;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.asm.ClassReader;
import com.alibaba.fastjson.asm.TypeCollector;
import com.alipay.sdk.util.h;
import com.tencent.rtmp.sharp.jni.QLog;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/* loaded from: classes2.dex */
public class ASMUtils {
    public static final boolean IS_ANDROID;
    public static final String JAVA_VM_NAME;

    static {
        String property = System.getProperty("java.vm.name");
        JAVA_VM_NAME = property;
        IS_ANDROID = isAndroid(property);
    }

    public static boolean checkName(String str) {
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt < 1 || cCharAt > 127 || cCharAt == '.') {
                return false;
            }
        }
        return true;
    }

    public static String desc(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        StringBuilder sb = new StringBuilder((parameterTypes.length + 1) << 4);
        sb.append('(');
        for (Class<?> cls : parameterTypes) {
            sb.append(desc(cls));
        }
        sb.append(')');
        sb.append(desc(method.getReturnType()));
        return sb.toString();
    }

    public static Type getMethodType(Class<?> cls, String str) {
        try {
            return cls.getMethod(str, new Class[0]).getGenericReturnType();
        } catch (Exception unused) {
            return null;
        }
    }

    public static String getPrimitiveLetter(Class<?> cls) {
        if (Integer.TYPE == cls) {
            return "I";
        }
        if (Void.TYPE == cls) {
            return ExifInterface.GPS_MEASUREMENT_INTERRUPTED;
        }
        if (Boolean.TYPE == cls) {
            return "Z";
        }
        if (Character.TYPE == cls) {
            return "C";
        }
        if (Byte.TYPE == cls) {
            return "B";
        }
        if (Short.TYPE == cls) {
            return ExifInterface.LATITUDE_SOUTH;
        }
        if (Float.TYPE == cls) {
            return "F";
        }
        if (Long.TYPE == cls) {
            return "J";
        }
        if (Double.TYPE == cls) {
            return QLog.TAG_REPORTLEVEL_DEVELOPER;
        }
        throw new IllegalStateException("Type: " + cls.getCanonicalName() + " is not a primitive type");
    }

    public static boolean isAndroid(String str) {
        if (str == null) {
            return false;
        }
        String lowerCase = str.toLowerCase();
        return lowerCase.contains("dalvik") || lowerCase.contains("lemur");
    }

    public static String[] lookupParameterNames(AccessibleObject accessibleObject) throws IOException {
        Class<?>[] parameterTypes;
        Class<?> declaringClass;
        Annotation[][] parameterAnnotations;
        String name;
        String strName;
        if (IS_ANDROID) {
            return new String[0];
        }
        if (accessibleObject instanceof Method) {
            Method method = (Method) accessibleObject;
            parameterTypes = method.getParameterTypes();
            name = method.getName();
            declaringClass = method.getDeclaringClass();
            parameterAnnotations = TypeUtils.getParameterAnnotations(method);
        } else {
            Constructor constructor = (Constructor) accessibleObject;
            parameterTypes = constructor.getParameterTypes();
            declaringClass = constructor.getDeclaringClass();
            parameterAnnotations = TypeUtils.getParameterAnnotations(constructor);
            name = "<init>";
        }
        if (parameterTypes.length == 0) {
            return new String[0];
        }
        ClassLoader classLoader = declaringClass.getClassLoader();
        if (classLoader == null) {
            classLoader = ClassLoader.getSystemClassLoader();
        }
        InputStream resourceAsStream = classLoader.getResourceAsStream(declaringClass.getName().replace('.', '/') + ".class");
        try {
            if (resourceAsStream == null) {
                return new String[0];
            }
            ClassReader classReader = new ClassReader(resourceAsStream, false);
            TypeCollector typeCollector = new TypeCollector(name, parameterTypes);
            classReader.accept(typeCollector);
            String[] parameterNamesForMethod = typeCollector.getParameterNamesForMethod();
            for (int i2 = 0; i2 < parameterNamesForMethod.length; i2++) {
                Annotation[] annotationArr = parameterAnnotations[i2];
                if (annotationArr != null) {
                    for (Annotation annotation : annotationArr) {
                        if ((annotation instanceof JSONField) && (strName = ((JSONField) annotation).name()) != null && strName.length() > 0) {
                            parameterNamesForMethod[i2] = strName;
                        }
                    }
                }
            }
            return parameterNamesForMethod;
        } catch (IOException unused) {
            return new String[0];
        } finally {
            IOUtils.close(resourceAsStream);
        }
    }

    public static String type(Class<?> cls) {
        if (!cls.isArray()) {
            return !cls.isPrimitive() ? cls.getName().replace('.', '/') : getPrimitiveLetter(cls);
        }
        return StrPool.BRACKET_START + desc(cls.getComponentType());
    }

    public static String desc(Class<?> cls) {
        if (cls.isPrimitive()) {
            return getPrimitiveLetter(cls);
        }
        if (cls.isArray()) {
            return StrPool.BRACKET_START + desc(cls.getComponentType());
        }
        return "L" + type(cls) + h.f3376b;
    }
}
