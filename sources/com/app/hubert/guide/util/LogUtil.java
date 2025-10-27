package com.app.hubert.guide.util;

import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import java.util.Locale;

/* loaded from: classes2.dex */
public class LogUtil {
    private static final int NONE = 8;
    public static final int level = 8;
    private static final String tagPrefix = "NewbieGuide";

    public static void d(String str) {
    }

    public static void d(String str, Throwable th) {
    }

    public static void e(String str) {
    }

    public static void e(String str, Throwable th) {
    }

    private static String generateTag() {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[4];
        String className = stackTraceElement.getClassName();
        String str = String.format(Locale.CHINA, "%s.%s(L:%d)", className.substring(className.lastIndexOf(StrPool.DOT) + 1), stackTraceElement.getMethodName(), Integer.valueOf(stackTraceElement.getLineNumber()));
        if (TextUtils.isEmpty("NewbieGuide")) {
            return str;
        }
        return "NewbieGuide:" + str;
    }

    public static void i(String str) {
    }

    public static void i(String str, Throwable th) {
    }

    public static void v(String str) {
    }

    public static void v(String str, Throwable th) {
    }

    public static void w(String str) {
    }

    public static void w(String str, Throwable th) {
    }
}
