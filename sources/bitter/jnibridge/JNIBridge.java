package bitter.jnibridge;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/* loaded from: classes.dex */
public class JNIBridge {

    public static class a implements InvocationHandler {

        /* renamed from: a, reason: collision with root package name */
        private Object f2207a = new Object[0];

        /* renamed from: b, reason: collision with root package name */
        private long f2208b;

        /* renamed from: c, reason: collision with root package name */
        private Constructor f2209c;

        public a(long j2) throws NoSuchMethodException, SecurityException {
            this.f2208b = j2;
            try {
                Constructor declaredConstructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, Integer.TYPE);
                this.f2209c = declaredConstructor;
                declaredConstructor.setAccessible(true);
            } catch (NoClassDefFoundError unused) {
                this.f2209c = null;
            } catch (NoSuchMethodException unused2) {
                this.f2209c = null;
            }
        }

        private Object a(Object obj, Method method, Object[] objArr) {
            if (objArr == null) {
                objArr = new Object[0];
            }
            Class<?> declaringClass = method.getDeclaringClass();
            return ((MethodHandles.Lookup) this.f2209c.newInstance(declaringClass, 2)).in(declaringClass).unreflectSpecial(method, declaringClass).bindTo(obj).invokeWithArguments(objArr);
        }

        public final void a() {
            synchronized (this.f2207a) {
                this.f2208b = 0L;
            }
        }

        public final void finalize() {
            synchronized (this.f2207a) {
                long j2 = this.f2208b;
                if (j2 == 0) {
                    return;
                }
                JNIBridge.delete(j2);
            }
        }

        @Override // java.lang.reflect.InvocationHandler
        public final Object invoke(Object obj, Method method, Object[] objArr) {
            synchronized (this.f2207a) {
                long j2 = this.f2208b;
                if (j2 == 0) {
                    return null;
                }
                try {
                    return JNIBridge.invoke(j2, method.getDeclaringClass(), method, objArr);
                } catch (NoSuchMethodError e2) {
                    if (this.f2209c == null) {
                        System.err.println("JNIBridge error: Java interface default methods are only supported since Android Oreo");
                        throw e2;
                    }
                    if ((method.getModifiers() & 1024) == 0) {
                        return a(obj, method, objArr);
                    }
                    throw e2;
                }
            }
        }
    }

    public static native void delete(long j2);

    public static void disableInterfaceProxy(Object obj) {
        if (obj != null) {
            ((a) Proxy.getInvocationHandler(obj)).a();
        }
    }

    public static native Object invoke(long j2, Class cls, Method method, Object[] objArr);

    public static Object newInterfaceProxy(long j2, Class[] clsArr) {
        return Proxy.newProxyInstance(JNIBridge.class.getClassLoader(), clsArr, new a(j2));
    }
}
