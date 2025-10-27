package com.meizu.cloud.pushsdk.util;

import android.os.Build;

/* loaded from: classes4.dex */
public class MinSdkChecker {
    public static boolean isSupportBigTextStyleAndAction() {
        return true;
    }

    public static boolean isSupportDeviceDefaultLight() {
        return true;
    }

    public static boolean isSupportKeyguardState() {
        return true;
    }

    public static boolean isSupportNotificationBuild() {
        return true;
    }

    public static boolean isSupportNotificationChannel() {
        return Build.VERSION.SDK_INT >= 26;
    }

    public static boolean isSupportSendNotification() {
        return true;
    }

    public static boolean isSupportSetDrawableSmallIcon() {
        return true;
    }

    public static boolean isSupportVideoNotification() {
        return true;
    }
}
