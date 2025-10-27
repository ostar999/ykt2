package com.ta.a.e;

import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import cn.hutool.core.text.StrPool;

/* loaded from: classes6.dex */
public class h {

    /* renamed from: c, reason: collision with root package name */
    private static boolean f17223c = false;

    /* renamed from: d, reason: collision with root package name */
    private static boolean f17224d = false;

    /* renamed from: a, reason: collision with other method in class */
    public static void m109a(String str, Object... objArr) {
        if (f17223c) {
            Log.d(k(), a(str, objArr));
        }
    }

    public static boolean b() {
        return f17223c;
    }

    public static void f() {
        if (f17223c) {
            Log.d(k(), a((String) null, new Object[0]));
        }
    }

    private static String k() {
        String methodName;
        String strSubstring;
        StackTraceElement stackTraceElementA = a();
        if (stackTraceElementA != null) {
            String className = stackTraceElementA.getClassName();
            strSubstring = !TextUtils.isEmpty(className) ? className.substring(className.lastIndexOf(46) + 1) : "";
            methodName = stackTraceElementA.getMethodName();
        } else {
            methodName = "";
            strSubstring = methodName;
        }
        return "Utdid." + strSubstring + StrPool.DOT + methodName + StrPool.DOT + String.valueOf(Process.myPid()) + StrPool.DOT + (Thread.currentThread().getId() + "");
    }

    public static void b(String str, Object... objArr) {
        if (f17224d) {
            Log.d(k(), a(str, objArr));
        }
    }

    public static void a(String str, Throwable th, Object... objArr) {
        if (f17223c) {
            Log.e(k(), a(str, objArr), th);
        }
    }

    public static void b(String str, Throwable th, Object... objArr) {
        if (f17224d) {
            Log.e(k(), a(str, objArr), th);
        }
    }

    private static String a(Object obj, Object obj2) {
        Object[] objArr = new Object[2];
        if (obj == null) {
            obj = "";
        }
        objArr[0] = obj;
        if (obj2 == null) {
            obj2 = "";
        }
        objArr[1] = obj2;
        return String.format("%s:%s", objArr);
    }

    private static String a(String str, Object... objArr) {
        if (str == null && objArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Object[] objArr2 = new Object[1];
        if (str == null) {
            str = "-";
        }
        int i2 = 0;
        objArr2[0] = str;
        sb.append(String.format("[%s] ", objArr2));
        if (objArr != null) {
            int length = objArr.length;
            while (true) {
                int i3 = i2 + 1;
                if (i3 >= objArr.length) {
                    break;
                }
                sb.append(a(objArr[i2], objArr[i3]));
                if (i3 < length - 1) {
                    sb.append(",");
                }
                i2 = i3 + 1;
            }
            if (i2 == objArr.length - 1) {
                sb.append(objArr[i2]);
            }
        }
        return sb.toString();
    }

    private static StackTraceElement a() {
        try {
            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                if (!stackTraceElement.isNativeMethod() && !stackTraceElement.getClassName().equals(Thread.class.getName()) && !stackTraceElement.getClassName().equals(h.class.getName())) {
                    return stackTraceElement;
                }
            }
        } catch (Throwable unused) {
        }
        return null;
    }
}
