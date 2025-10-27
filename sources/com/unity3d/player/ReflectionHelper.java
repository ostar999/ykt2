package com.unity3d.player;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import com.tencent.rtmp.sharp.jni.QLog;
import com.yikaobang.yixue.R2;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
final class ReflectionHelper {
    protected static boolean LOG = false;
    protected static final boolean LOGV = false;

    /* renamed from: a, reason: collision with root package name */
    private static a[] f23907a = new a[4096];

    /* renamed from: b, reason: collision with root package name */
    private static long f23908b = 0;

    /* renamed from: c, reason: collision with root package name */
    private static long f23909c = 0;

    /* renamed from: d, reason: collision with root package name */
    private static boolean f23910d = false;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public volatile Member f23919a;

        /* renamed from: b, reason: collision with root package name */
        private final Class f23920b;

        /* renamed from: c, reason: collision with root package name */
        private final String f23921c;

        /* renamed from: d, reason: collision with root package name */
        private final String f23922d;

        /* renamed from: e, reason: collision with root package name */
        private final int f23923e;

        public a(Class cls, String str, String str2) {
            this.f23920b = cls;
            this.f23921c = str;
            this.f23922d = str2;
            this.f23923e = ((((cls.hashCode() + R2.attr.bl_checkable_gradient_endColor) * 31) + str.hashCode()) * 31) + str2.hashCode();
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof a) {
                a aVar = (a) obj;
                if (this.f23923e == aVar.f23923e && this.f23922d.equals(aVar.f23922d) && this.f23921c.equals(aVar.f23921c) && this.f23920b.equals(aVar.f23920b)) {
                    return true;
                }
            }
            return false;
        }

        public final int hashCode() {
            return this.f23923e;
        }
    }

    public static class b implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final long f23924a;

        /* renamed from: b, reason: collision with root package name */
        final long f23925b;

        public b(long j2, long j3) {
            this.f23924a = j2;
            this.f23925b = j3;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (ReflectionHelper.beginProxyCall(this.f23924a)) {
                try {
                    ReflectionHelper.nativeProxyFinalize(this.f23925b);
                } finally {
                    ReflectionHelper.endProxyCall();
                }
            }
        }
    }

    public interface c extends InvocationHandler {
        void a(long j2, boolean z2);
    }

    private static float a(Class cls, Class cls2) {
        if (cls.equals(cls2)) {
            return 1.0f;
        }
        if (cls.isPrimitive() || cls2.isPrimitive()) {
            return 0.0f;
        }
        try {
            if (cls.asSubclass(cls2) != null) {
                return 0.5f;
            }
        } catch (ClassCastException unused) {
        }
        try {
            return cls2.asSubclass(cls) != null ? 0.1f : 0.0f;
        } catch (ClassCastException unused2) {
            return 0.0f;
        }
    }

    private static float a(Class cls, Class[] clsArr, Class[] clsArr2) {
        if (clsArr2.length == 0) {
            return 0.1f;
        }
        int i2 = 0;
        if ((clsArr == null ? 0 : clsArr.length) + 1 != clsArr2.length) {
            return 0.0f;
        }
        float f2 = 1.0f;
        if (clsArr != null) {
            int length = clsArr.length;
            float fA = 1.0f;
            int i3 = 0;
            while (i2 < length) {
                fA *= a(clsArr[i2], clsArr2[i3]);
                i2++;
                i3++;
            }
            f2 = fA;
        }
        return f2 * a(cls, clsArr2[clsArr2.length - 1]);
    }

    private static Class a(String str, int[] iArr) {
        while (iArr[0] < str.length()) {
            int i2 = iArr[0];
            iArr[0] = i2 + 1;
            char cCharAt = str.charAt(i2);
            if (cCharAt != '(' && cCharAt != ')') {
                if (cCharAt == 'L') {
                    int iIndexOf = str.indexOf(59, iArr[0]);
                    if (iIndexOf == -1) {
                        return null;
                    }
                    String strSubstring = str.substring(iArr[0], iIndexOf);
                    iArr[0] = iIndexOf + 1;
                    try {
                        return Class.forName(strSubstring.replace('/', '.'));
                    } catch (ClassNotFoundException unused) {
                        return null;
                    }
                }
                if (cCharAt == 'Z') {
                    return Boolean.TYPE;
                }
                if (cCharAt == 'I') {
                    return Integer.TYPE;
                }
                if (cCharAt == 'F') {
                    return Float.TYPE;
                }
                if (cCharAt == 'V') {
                    return Void.TYPE;
                }
                if (cCharAt == 'B') {
                    return Byte.TYPE;
                }
                if (cCharAt == 'C') {
                    return Character.TYPE;
                }
                if (cCharAt == 'S') {
                    return Short.TYPE;
                }
                if (cCharAt == 'J') {
                    return Long.TYPE;
                }
                if (cCharAt == 'D') {
                    return Double.TYPE;
                }
                if (cCharAt == '[') {
                    return Array.newInstance((Class<?>) a(str, iArr), 0).getClass();
                }
                f.Log(5, "! parseType; " + cCharAt + " is not known!");
                return null;
            }
        }
        return null;
    }

    private static synchronized void a(a aVar, Member member) {
        aVar.f23919a = member;
        f23907a[aVar.hashCode() & (f23907a.length - 1)] = aVar;
    }

    private static synchronized boolean a(a aVar) {
        a aVar2 = f23907a[aVar.hashCode() & (f23907a.length - 1)];
        if (!aVar.equals(aVar2)) {
            return false;
        }
        aVar.f23919a = aVar2.f23919a;
        return true;
    }

    private static Class[] a(String str) {
        Class clsA;
        int i2 = 0;
        int[] iArr = {0};
        ArrayList arrayList = new ArrayList();
        while (iArr[0] < str.length() && (clsA = a(str, iArr)) != null) {
            arrayList.add(clsA);
        }
        Class[] clsArr = new Class[arrayList.size()];
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            clsArr[i2] = (Class) it.next();
            i2++;
        }
        return clsArr;
    }

    public static synchronized boolean beginProxyCall(long j2) {
        if (j2 != f23908b) {
            return false;
        }
        f23909c++;
        return true;
    }

    public static synchronized void endProxyCall() {
        long j2 = f23909c - 1;
        f23909c = j2;
        if (0 == j2 && f23910d) {
            ReflectionHelper.class.notifyAll();
        }
    }

    public static synchronized void endUnityLaunch() {
        try {
            f23908b++;
            f23910d = true;
            while (f23909c > 0) {
                ReflectionHelper.class.wait();
            }
        } catch (InterruptedException unused) {
            f.Log(6, "Interrupted while waiting for all proxies to exit.");
        }
        f23910d = false;
    }

    public static Constructor getConstructorID(Class cls, String str) throws SecurityException {
        Constructor<?> constructor;
        a aVar = new a(cls, "", str);
        if (a(aVar)) {
            constructor = (Constructor) aVar.f23919a;
        } else {
            Class[] clsArrA = a(str);
            Constructor<?>[] constructors = cls.getConstructors();
            int length = constructors.length;
            Constructor<?> constructor2 = null;
            float f2 = 0.0f;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                Constructor<?> constructor3 = constructors[i2];
                float fA = a(Void.TYPE, constructor3.getParameterTypes(), clsArrA);
                if (fA > f2) {
                    if (fA == 1.0f) {
                        constructor2 = constructor3;
                        break;
                    }
                    constructor2 = constructor3;
                    f2 = fA;
                }
                i2++;
            }
            a(aVar, constructor2);
            constructor = constructor2;
        }
        if (constructor != null) {
            return constructor;
        }
        throw new NoSuchMethodError("<init>" + str + " in class " + cls.getName());
    }

    public static Field getFieldID(Class cls, String str, String str2, boolean z2) {
        Field field;
        Class superclass = cls;
        a aVar = new a(superclass, str, str2);
        if (a(aVar)) {
            field = (Field) aVar.f23919a;
        } else {
            Class[] clsArrA = a(str2);
            float f2 = 0.0f;
            Field field2 = null;
            while (superclass != null) {
                Field[] declaredFields = superclass.getDeclaredFields();
                int length = declaredFields.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    Field field3 = declaredFields[i2];
                    if (z2 == Modifier.isStatic(field3.getModifiers()) && field3.getName().compareTo(str) == 0) {
                        float fA = a(field3.getType(), (Class[]) null, clsArrA);
                        if (fA > f2) {
                            field2 = field3;
                            if (fA == 1.0f) {
                                f2 = fA;
                                break;
                            }
                            f2 = fA;
                        } else {
                            continue;
                        }
                    }
                    i2++;
                }
                if (f2 == 1.0f || superclass.isPrimitive() || superclass.isInterface() || superclass.equals(Object.class) || superclass.equals(Void.TYPE)) {
                    break;
                }
                superclass = superclass.getSuperclass();
            }
            a(aVar, field2);
            field = field2;
        }
        if (field != null) {
            return field;
        }
        Object[] objArr = new Object[4];
        objArr[0] = z2 ? "static" : "non-static";
        objArr[1] = str;
        objArr[2] = str2;
        objArr[3] = superclass.getName();
        throw new NoSuchFieldError(String.format("no %s field with name='%s' signature='%s' in class L%s;", objArr));
    }

    public static String getFieldSignature(Field field) {
        Class<?> type = field.getType();
        if (type.isPrimitive()) {
            String name = type.getName();
            return TypedValues.Custom.S_BOOLEAN.equals(name) ? "Z" : "byte".equals(name) ? "B" : "char".equals(name) ? "C" : "double".equals(name) ? QLog.TAG_REPORTLEVEL_DEVELOPER : TypedValues.Custom.S_FLOAT.equals(name) ? "F" : "int".equals(name) ? "I" : "long".equals(name) ? "J" : "short".equals(name) ? ExifInterface.LATITUDE_SOUTH : name;
        }
        if (type.isArray()) {
            return type.getName().replace('.', '/');
        }
        return "L" + type.getName().replace('.', '/') + com.alipay.sdk.util.h.f3376b;
    }

    public static Method getMethodID(Class cls, String str, String str2, boolean z2) throws SecurityException {
        Method method;
        a aVar = new a(cls, str, str2);
        if (a(aVar)) {
            method = (Method) aVar.f23919a;
        } else {
            Class[] clsArrA = a(str2);
            Method method2 = null;
            float f2 = 0.0f;
            while (cls != null) {
                Method[] declaredMethods = cls.getDeclaredMethods();
                int length = declaredMethods.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    Method method3 = declaredMethods[i2];
                    if (z2 == Modifier.isStatic(method3.getModifiers()) && method3.getName().compareTo(str) == 0) {
                        float fA = a(method3.getReturnType(), method3.getParameterTypes(), clsArrA);
                        if (fA > f2) {
                            if (fA == 1.0f) {
                                method2 = method3;
                                f2 = fA;
                                break;
                            }
                            method2 = method3;
                            f2 = fA;
                        } else {
                            continue;
                        }
                    }
                    i2++;
                }
                if (f2 == 1.0f || cls.isPrimitive() || cls.isInterface() || cls.equals(Object.class) || cls.equals(Void.TYPE)) {
                    break;
                }
                cls = cls.getSuperclass();
            }
            a(aVar, method2);
            method = method2;
        }
        if (method != null) {
            return method;
        }
        Object[] objArr = new Object[4];
        objArr[0] = z2 ? "static" : "non-static";
        objArr[1] = str;
        objArr[2] = str2;
        objArr[3] = cls.getName();
        throw new NoSuchMethodError(String.format("no %s method with name='%s' signature='%s' in class L%s;", objArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeProxyFinalize(long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native Object nativeProxyInvoke(long j2, String str, Object[] objArr);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeProxyLogJNIInvokeException(long j2);

    public static Object newProxyInstance(UnityPlayer unityPlayer, long j2, Class cls) {
        return newProxyInstance(unityPlayer, j2, new Class[]{cls});
    }

    public static Object newProxyInstance(UnityPlayer unityPlayer, long j2, Class[] clsArr) {
        return Proxy.newProxyInstance(ReflectionHelper.class.getClassLoader(), clsArr, new c(j2, unityPlayer, clsArr) { // from class: com.unity3d.player.ReflectionHelper.1

            /* renamed from: a, reason: collision with root package name */
            final /* synthetic */ long f23911a;

            /* renamed from: b, reason: collision with root package name */
            final /* synthetic */ UnityPlayer f23912b;

            /* renamed from: c, reason: collision with root package name */
            final /* synthetic */ Class[] f23913c;

            /* renamed from: d, reason: collision with root package name */
            private Runnable f23914d;

            /* renamed from: e, reason: collision with root package name */
            private UnityPlayer f23915e;

            /* renamed from: f, reason: collision with root package name */
            private long f23916f = ReflectionHelper.f23908b;

            /* renamed from: g, reason: collision with root package name */
            private long f23917g;

            /* renamed from: h, reason: collision with root package name */
            private boolean f23918h;

            {
                this.f23911a = j2;
                this.f23912b = unityPlayer;
                this.f23913c = clsArr;
                this.f23914d = new b(ReflectionHelper.f23908b, j2);
                this.f23915e = unityPlayer;
            }

            private Object a(Object obj, Method method, Object[] objArr) throws NoSuchMethodException, SecurityException {
                if (objArr == null) {
                    try {
                        objArr = new Object[0];
                    } catch (NoClassDefFoundError unused) {
                        f.Log(6, String.format("Java interface default methods are only supported since Android Oreo", new Object[0]));
                        ReflectionHelper.nativeProxyLogJNIInvokeException(this.f23917g);
                        return null;
                    }
                }
                Class<?> declaringClass = method.getDeclaringClass();
                Constructor declaredConstructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, Integer.TYPE);
                declaredConstructor.setAccessible(true);
                return ((MethodHandles.Lookup) declaredConstructor.newInstance(declaringClass, 2)).in(declaringClass).unreflectSpecial(method, declaringClass).bindTo(obj).invokeWithArguments(objArr);
            }

            @Override // com.unity3d.player.ReflectionHelper.c
            public final void a(long j3, boolean z2) {
                this.f23917g = j3;
                this.f23918h = z2;
            }

            public final void finalize() throws Throwable {
                this.f23915e.queueGLThreadEvent(this.f23914d);
                super.finalize();
            }

            @Override // java.lang.reflect.InvocationHandler
            public final Object invoke(Object obj, Method method, Object[] objArr) {
                long j3;
                if (!ReflectionHelper.beginProxyCall(this.f23916f)) {
                    f.Log(6, "Scripting proxy object was destroyed, because Unity player was unloaded.");
                    return null;
                }
                try {
                    this.f23917g = 0L;
                    this.f23918h = false;
                    Object objNativeProxyInvoke = ReflectionHelper.nativeProxyInvoke(this.f23911a, method.getName(), objArr);
                    if (!this.f23918h) {
                        j3 = this.f23917g;
                        if (j3 != 0) {
                        }
                        return objNativeProxyInvoke;
                    }
                    if ((method.getModifiers() & 1024) == 0) {
                        return a(obj, method, objArr);
                    }
                    j3 = this.f23917g;
                    ReflectionHelper.nativeProxyLogJNIInvokeException(j3);
                    return objNativeProxyInvoke;
                } finally {
                    ReflectionHelper.endProxyCall();
                }
            }
        });
    }

    public static void setNativeExceptionOnProxy(Object obj, long j2, boolean z2) {
        ((c) Proxy.getInvocationHandler(obj)).a(j2, z2);
    }
}
