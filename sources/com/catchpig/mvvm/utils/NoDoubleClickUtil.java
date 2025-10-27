package com.catchpig.mvvm.utils;

/* loaded from: classes2.dex */
public class NoDoubleClickUtil {
    private static final int SPACE_TIME = 500;
    private static long lastClickTime;

    public static synchronized boolean isDoubleClick() {
        boolean z2;
        long jCurrentTimeMillis = System.currentTimeMillis();
        z2 = jCurrentTimeMillis - lastClickTime <= 500;
        lastClickTime = jCurrentTimeMillis;
        return z2;
    }

    public static synchronized boolean isNoDoubleClick() {
        boolean z2;
        long jCurrentTimeMillis = System.currentTimeMillis();
        z2 = jCurrentTimeMillis - lastClickTime > 500;
        lastClickTime = jCurrentTimeMillis;
        return z2;
    }

    public static synchronized boolean isNoDoubleClick(int i2) {
        boolean z2;
        long jCurrentTimeMillis = System.currentTimeMillis();
        z2 = jCurrentTimeMillis - lastClickTime > ((long) i2);
        lastClickTime = jCurrentTimeMillis;
        return z2;
    }
}
