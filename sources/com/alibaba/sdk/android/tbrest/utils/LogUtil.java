package com.alibaba.sdk.android.tbrest.utils;

import android.util.Log;

/* loaded from: classes2.dex */
public class LogUtil {
    public static final String TAG = "RestApi";

    public static void d(String str) {
        Log.d(TAG, str);
    }

    public static void e(String str) {
        Log.e(TAG, str);
    }

    public static void i(String str) {
        Log.i(TAG, str);
    }

    public static void w(String str, Throwable th) {
        Log.w(TAG, str, th);
    }

    public static void e(String str, Throwable th) {
        Log.e(TAG, str, th);
    }
}
