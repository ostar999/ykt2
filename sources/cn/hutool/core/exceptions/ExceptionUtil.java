package cn.hutool.core.exceptions;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.lang.Matcher;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class ExceptionUtil {
    public static <T extends Throwable> T convertFromOrSuppressedThrowable(Throwable th, Class<T> cls) {
        return (T) convertFromOrSuppressedThrowable(th, cls, true);
    }

    public static Throwable getCausedBy(Throwable th, Class<? extends Exception>... clsArr) {
        while (th != null) {
            for (Class<? extends Exception> cls : clsArr) {
                if (cls.isInstance(th)) {
                    return th;
                }
            }
            th = th.getCause();
        }
        return null;
    }

    public static String getMessage(Throwable th) {
        return th == null ? "null" : CharSequenceUtil.format("{}: {}", th.getClass().getSimpleName(), th.getMessage());
    }

    public static Throwable getRootCause(Throwable th) {
        List<Throwable> throwableList = getThrowableList(th);
        if (throwableList.size() < 1) {
            return null;
        }
        return throwableList.get(throwableList.size() - 1);
    }

    public static String getRootCauseMessage(Throwable th) {
        return getMessage(getRootCause(th));
    }

    public static StackTraceElement getRootStackElement() {
        return Thread.currentThread().getStackTrace()[Thread.currentThread().getStackTrace().length - 1];
    }

    public static String getSimpleMessage(Throwable th) {
        return th == null ? "null" : th.getMessage();
    }

    public static StackTraceElement getStackElement(int i2) {
        return Thread.currentThread().getStackTrace()[i2];
    }

    public static StackTraceElement[] getStackElements() {
        return Thread.currentThread().getStackTrace();
    }

    public static List<Throwable> getThrowableList(Throwable th) {
        ArrayList arrayList = new ArrayList();
        while (th != null && !arrayList.contains(th)) {
            arrayList.add(th);
            th = th.getCause();
        }
        return arrayList;
    }

    public static boolean isCausedBy(Throwable th, Class<? extends Exception>... clsArr) {
        return getCausedBy(th, clsArr) != null;
    }

    public static boolean isFromOrSuppressedThrowable(Throwable th, Class<? extends Throwable> cls) {
        return convertFromOrSuppressedThrowable(th, cls, true) != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getStackElement$0(String str, StackTraceElement stackTraceElement) {
        return CharSequenceUtil.equals(str, stackTraceElement.getClassName());
    }

    public static String stacktraceToOneLineString(Throwable th) {
        return stacktraceToOneLineString(th, 3000);
    }

    public static String stacktraceToString(Throwable th) {
        return stacktraceToString(th, 3000);
    }

    public static Throwable unwrap(Throwable th) {
        while (true) {
            if (th instanceof InvocationTargetException) {
                th = ((InvocationTargetException) th).getTargetException();
            } else {
                if (!(th instanceof UndeclaredThrowableException)) {
                    return th;
                }
                th = ((UndeclaredThrowableException) th).getUndeclaredThrowable();
            }
        }
    }

    public static <T extends Throwable> T wrap(Throwable th, Class<T> cls) {
        return cls.isInstance(th) ? th : (T) ReflectUtil.newInstance(cls, th);
    }

    public static void wrapAndThrow(Throwable th) {
        if (th instanceof RuntimeException) {
            throw ((RuntimeException) th);
        }
        if (!(th instanceof Error)) {
            throw new UndeclaredThrowableException(th);
        }
        throw ((Error) th);
    }

    public static RuntimeException wrapRuntime(Throwable th) {
        return th instanceof RuntimeException ? (RuntimeException) th : new RuntimeException(th);
    }

    public static void wrapRuntimeAndThrow(String str) {
        throw new RuntimeException(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T extends Throwable> T convertFromOrSuppressedThrowable(Throwable th, Class<T> cls, boolean z2) {
        T t2;
        if (th != 0 && cls != null) {
            if (cls.isAssignableFrom(th.getClass())) {
                return th;
            }
            if (z2 && (t2 = (T) th.getCause()) != null && cls.isAssignableFrom(t2.getClass())) {
                return t2;
            }
            Throwable[] suppressed = th.getSuppressed();
            if (ArrayUtil.isNotEmpty((Object[]) suppressed)) {
                for (Throwable th2 : suppressed) {
                    T t3 = (T) th2;
                    if (cls.isAssignableFrom(t3.getClass())) {
                        return t3;
                    }
                }
            }
        }
        return null;
    }

    public static StackTraceElement getStackElement(final String str, int i2) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int iMatchIndex = ArrayUtil.matchIndex(new Matcher() { // from class: w.i
            @Override // cn.hutool.core.lang.Matcher
            public final boolean match(Object obj) {
                return ExceptionUtil.lambda$getStackElement$0(str, (StackTraceElement) obj);
            }
        }, stackTrace);
        if (iMatchIndex > 0) {
            return stackTrace[iMatchIndex + i2];
        }
        return null;
    }

    public static boolean isFromOrSuppressedThrowable(Throwable th, Class<? extends Throwable> cls, boolean z2) {
        return convertFromOrSuppressedThrowable(th, cls, z2) != null;
    }

    public static String stacktraceToOneLineString(Throwable th, int i2) {
        HashMap map = new HashMap();
        map.put('\r', " ");
        map.put('\n', " ");
        map.put('\t', " ");
        return stacktraceToString(th, i2, map);
    }

    public static String stacktraceToString(Throwable th, int i2) {
        return stacktraceToString(th, i2, null);
    }

    public static String stacktraceToString(Throwable th, int i2, Map<Character, String> map) {
        FastByteArrayOutputStream fastByteArrayOutputStream = new FastByteArrayOutputStream();
        th.printStackTrace(new PrintStream(fastByteArrayOutputStream));
        String string = fastByteArrayOutputStream.toString();
        int length = string.length();
        if (i2 < 0 || i2 > length) {
            i2 = length;
        }
        if (!MapUtil.isNotEmpty(map)) {
            return i2 == length ? string : CharSequenceUtil.subPre(string, i2);
        }
        StringBuilder sbBuilder = StrUtil.builder();
        for (int i3 = 0; i3 < i2; i3++) {
            char cCharAt = string.charAt(i3);
            String str = map.get(Character.valueOf(cCharAt));
            if (str != null) {
                sbBuilder.append(str);
            } else {
                sbBuilder.append(cCharAt);
            }
        }
        return sbBuilder.toString();
    }

    public static RuntimeException wrapRuntime(String str) {
        return new RuntimeException(str);
    }
}
