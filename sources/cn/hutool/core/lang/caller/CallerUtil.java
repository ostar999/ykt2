package cn.hutool.core.lang.caller;

import cn.hutool.core.text.StrPool;

/* loaded from: classes.dex */
public class CallerUtil {
    private static final Caller INSTANCE = tryCreateCaller();

    public static Class<?> getCaller() {
        return INSTANCE.getCaller();
    }

    public static Class<?> getCallerCaller() {
        return INSTANCE.getCallerCaller();
    }

    public static String getCallerMethodName(boolean z2) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        String methodName = stackTraceElement.getMethodName();
        if (!z2) {
            return methodName;
        }
        return stackTraceElement.getClassName() + StrPool.DOT + methodName;
    }

    public static boolean isCalledBy(Class<?> cls) {
        return INSTANCE.isCalledBy(cls);
    }

    private static Caller tryCreateCaller() {
        try {
            SecurityManagerCaller securityManagerCaller = new SecurityManagerCaller();
            if (securityManagerCaller.getCaller() != null) {
                if (securityManagerCaller.getCallerCaller() != null) {
                    return securityManagerCaller;
                }
            }
        } catch (Throwable unused) {
        }
        return new StackTraceCaller();
    }

    public static Class<?> getCaller(int i2) {
        return INSTANCE.getCaller(i2);
    }
}
