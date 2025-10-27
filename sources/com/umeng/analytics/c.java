package com.umeng.analytics;

import android.content.Context;
import android.text.TextUtils;

/* loaded from: classes6.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static String[] f22390a = new String[2];

    public static void a(Context context, String str, String str2) {
        String[] strArr = f22390a;
        strArr[0] = str;
        strArr[1] = str2;
        if (context != null) {
            com.umeng.common.b.a(context).a(str, str2);
        }
    }

    public static void b(Context context) {
        String[] strArr = f22390a;
        strArr[0] = null;
        strArr[1] = null;
        if (context != null) {
            com.umeng.common.b.a(context).b();
        }
    }

    public static String[] a(Context context) {
        String[] strArrA;
        if (!TextUtils.isEmpty(f22390a[0]) && !TextUtils.isEmpty(f22390a[1])) {
            return f22390a;
        }
        if (context == null || (strArrA = com.umeng.common.b.a(context).a()) == null) {
            return null;
        }
        String[] strArr = f22390a;
        strArr[0] = strArrA[0];
        strArr[1] = strArrA[1];
        return strArr;
    }
}
