package com.luck.picture.lib.utils;

/* loaded from: classes4.dex */
public class DoubleUtils {
    private static final long TIME = 600;
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - lastClickTime < TIME) {
            return true;
        }
        lastClickTime = jCurrentTimeMillis;
        return false;
    }
}
