package com.umeng.analytics.pro;

import android.util.Log;

/* loaded from: classes6.dex */
public class al {

    /* renamed from: a, reason: collision with root package name */
    private static final String f22436a = "OpenId";

    /* renamed from: b, reason: collision with root package name */
    private static boolean f22437b = false;

    public static void a(boolean z2) {
        Log.d(f22436a, "setDebug:" + z2);
        f22437b = z2;
    }

    public static void b(String str, Object... objArr) {
        if (f22437b) {
            Log.i(f22436a, e(str, objArr));
        }
    }

    public static void c(String str, Object... objArr) {
        if (f22437b) {
            Log.w(f22436a, e(str, objArr));
        }
    }

    public static void d(String str, Object... objArr) {
        if (f22437b) {
            Log.e(f22436a, e(str, objArr));
        }
    }

    private static String e(String str, Object... objArr) {
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

    public static void a(String str, Object... objArr) {
        if (f22437b) {
            Log.d(f22436a, e(str, objArr));
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
}
