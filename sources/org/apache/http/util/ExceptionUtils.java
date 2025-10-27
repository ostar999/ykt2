package org.apache.http.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes9.dex */
public final class ExceptionUtils {
    private static final Method INIT_CAUSE_METHOD = getInitCauseMethod();
    static /* synthetic */ Class class$java$lang$Throwable;

    private ExceptionUtils() {
    }

    public static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e2) {
            throw new NoClassDefFoundError(e2.getMessage());
        }
    }

    private static Method getInitCauseMethod() {
        try {
            Class<?>[] clsArr = new Class[1];
            Class<?> clsClass$ = class$java$lang$Throwable;
            if (clsClass$ == null) {
                clsClass$ = class$("java.lang.Throwable");
                class$java$lang$Throwable = clsClass$;
            }
            clsArr[0] = clsClass$;
            Class clsClass$2 = class$java$lang$Throwable;
            if (clsClass$2 == null) {
                clsClass$2 = class$("java.lang.Throwable");
                class$java$lang$Throwable = clsClass$2;
            }
            return clsClass$2.getMethod("initCause", clsArr);
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }

    public static void initCause(Throwable th, Throwable th2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method = INIT_CAUSE_METHOD;
        if (method != null) {
            try {
                method.invoke(th, th2);
            } catch (Exception unused) {
            }
        }
    }
}
