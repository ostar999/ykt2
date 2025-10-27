package com.luck.picture.lib.utils;

import android.os.Build;

/* loaded from: classes4.dex */
public class SdkVersionUtils {
    public static final int R = 30;

    public static boolean isMaxN() {
        return Build.VERSION.SDK_INT >= 24;
    }

    public static boolean isMinM() {
        return false;
    }

    public static boolean isN() {
        return Build.VERSION.SDK_INT == 24;
    }

    public static boolean isO() {
        return Build.VERSION.SDK_INT >= 26;
    }

    public static boolean isQ() {
        return Build.VERSION.SDK_INT >= 29;
    }

    public static boolean isR() {
        return Build.VERSION.SDK_INT >= 30;
    }
}
