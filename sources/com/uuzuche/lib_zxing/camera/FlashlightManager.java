package com.uuzuche.lib_zxing.camera;

import android.os.IBinder;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes6.dex */
final class FlashlightManager {
    private static final String TAG = "FlashlightManager";
    private static final Object iHardwareService;
    private static final Method setFlashEnabledMethod;

    static {
        String simpleName = FlashlightManager.class.getSimpleName();
        Object hardwareService = getHardwareService();
        iHardwareService = hardwareService;
        setFlashEnabledMethod = getSetFlashEnabledMethod(hardwareService);
        if (hardwareService == null) {
            Log.v(simpleName, "This device does supports control of a flashlight");
        } else {
            Log.v(simpleName, "This device does not support control of a flashlight");
        }
    }

    private FlashlightManager() {
    }

    public static void disableFlashlight() {
        setFlashlight(false);
    }

    public static void enableFlashlight() {
        setFlashlight(false);
    }

    private static Object getHardwareService() {
        Method methodMaybeGetMethod;
        Object objInvoke;
        Class<?> clsMaybeForName;
        Method methodMaybeGetMethod2;
        Class<?> clsMaybeForName2 = maybeForName("android.os.ServiceManager");
        if (clsMaybeForName2 == null || (methodMaybeGetMethod = maybeGetMethod(clsMaybeForName2, "getService", String.class)) == null || (objInvoke = invoke(methodMaybeGetMethod, null, "hardware")) == null || (clsMaybeForName = maybeForName("android.os.IHardwareService$Stub")) == null || (methodMaybeGetMethod2 = maybeGetMethod(clsMaybeForName, "asInterface", IBinder.class)) == null) {
            return null;
        }
        return invoke(methodMaybeGetMethod2, null, objInvoke);
    }

    private static Method getSetFlashEnabledMethod(Object obj) {
        if (obj == null) {
            return null;
        }
        return maybeGetMethod(obj.getClass(), "setFlashlightEnabled", Boolean.TYPE);
    }

    private static Object invoke(Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e2) {
            Log.w(TAG, "Unexpected error while invoking " + method, e2);
            return null;
        } catch (RuntimeException e3) {
            Log.w(TAG, "Unexpected error while invoking " + method, e3);
            return null;
        } catch (InvocationTargetException e4) {
            Log.w(TAG, "Unexpected error while invoking " + method, e4.getCause());
            return null;
        }
    }

    private static Class<?> maybeForName(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException unused) {
            return null;
        } catch (RuntimeException e2) {
            Log.w(TAG, "Unexpected error while finding class " + str, e2);
            return null;
        }
    }

    private static Method maybeGetMethod(Class<?> cls, String str, Class<?>... clsArr) {
        try {
            return cls.getMethod(str, clsArr);
        } catch (NoSuchMethodException unused) {
            return null;
        } catch (RuntimeException e2) {
            Log.w(TAG, "Unexpected error while finding method " + str, e2);
            return null;
        }
    }

    private static void setFlashlight(boolean z2) {
        Object obj = iHardwareService;
        if (obj != null) {
            invoke(setFlashEnabledMethod, obj, Boolean.valueOf(z2));
        }
    }
}
