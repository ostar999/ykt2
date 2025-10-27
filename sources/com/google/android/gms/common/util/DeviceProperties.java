package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.plv.livescenes.linkmic.manager.PLVLinkMicManager;

@KeepForSdk
/* loaded from: classes3.dex */
public final class DeviceProperties {
    private static Boolean zzgn;
    private static Boolean zzgo;
    private static Boolean zzgp;
    private static Boolean zzgq;
    private static Boolean zzgr;
    private static Boolean zzgs;
    private static Boolean zzgt;
    private static Boolean zzgu;

    private DeviceProperties() {
    }

    @KeepForSdk
    public static boolean isAuto(Context context) {
        if (zzgt == null) {
            zzgt = Boolean.valueOf(PlatformVersion.isAtLeastO() && context.getPackageManager().hasSystemFeature("android.hardware.type.automotive"));
        }
        return zzgt.booleanValue();
    }

    @KeepForSdk
    public static boolean isLatchsky(Context context) {
        if (zzgr == null) {
            PackageManager packageManager = context.getPackageManager();
            zzgr = Boolean.valueOf(packageManager.hasSystemFeature("com.google.android.feature.services_updater") && packageManager.hasSystemFeature("cn.google.services"));
        }
        return zzgr.booleanValue();
    }

    @KeepForSdk
    @TargetApi(21)
    public static boolean isSidewinder(Context context) {
        if (zzgq == null) {
            zzgq = Boolean.valueOf(PlatformVersion.isAtLeastLollipop() && context.getPackageManager().hasSystemFeature("cn.google"));
        }
        return zzgq.booleanValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x003e  */
    @com.google.android.gms.common.annotation.KeepForSdk
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isTablet(android.content.res.Resources r4) {
        /*
            r0 = 0
            if (r4 != 0) goto L4
            return r0
        L4:
            java.lang.Boolean r1 = com.google.android.gms.common.util.DeviceProperties.zzgn
            if (r1 != 0) goto L45
            android.content.res.Configuration r1 = r4.getConfiguration()
            int r1 = r1.screenLayout
            r1 = r1 & 15
            r2 = 3
            r3 = 1
            if (r1 <= r2) goto L16
            r1 = r3
            goto L17
        L16:
            r1 = r0
        L17:
            if (r1 != 0) goto L3e
            java.lang.Boolean r1 = com.google.android.gms.common.util.DeviceProperties.zzgo
            if (r1 != 0) goto L36
            android.content.res.Configuration r4 = r4.getConfiguration()
            int r1 = r4.screenLayout
            r1 = r1 & 15
            if (r1 > r2) goto L2f
            int r4 = r4.smallestScreenWidthDp
            r1 = 600(0x258, float:8.41E-43)
            if (r4 < r1) goto L2f
            r4 = r3
            goto L30
        L2f:
            r4 = r0
        L30:
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)
            com.google.android.gms.common.util.DeviceProperties.zzgo = r4
        L36:
            java.lang.Boolean r4 = com.google.android.gms.common.util.DeviceProperties.zzgo
            boolean r4 = r4.booleanValue()
            if (r4 == 0) goto L3f
        L3e:
            r0 = r3
        L3f:
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r0)
            com.google.android.gms.common.util.DeviceProperties.zzgn = r4
        L45:
            java.lang.Boolean r4 = com.google.android.gms.common.util.DeviceProperties.zzgn
            boolean r4 = r4.booleanValue()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.util.DeviceProperties.isTablet(android.content.res.Resources):boolean");
    }

    @KeepForSdk
    public static boolean isTv(Context context) {
        if (zzgu == null) {
            PackageManager packageManager = context.getPackageManager();
            zzgu = Boolean.valueOf(packageManager.hasSystemFeature("com.google.android.tv") || packageManager.hasSystemFeature("android.hardware.type.television") || packageManager.hasSystemFeature("android.software.leanback"));
        }
        return zzgu.booleanValue();
    }

    @KeepForSdk
    public static boolean isUserBuild() {
        return PLVLinkMicManager.USER.equals(Build.TYPE);
    }

    @KeepForSdk
    @TargetApi(20)
    public static boolean isWearable(Context context) {
        if (zzgp == null) {
            zzgp = Boolean.valueOf(PlatformVersion.isAtLeastKitKatWatch() && context.getPackageManager().hasSystemFeature("android.hardware.type.watch"));
        }
        return zzgp.booleanValue();
    }

    @KeepForSdk
    @TargetApi(26)
    public static boolean isWearableWithoutPlayStore(Context context) {
        if (!isWearable(context)) {
            return false;
        }
        if (PlatformVersion.isAtLeastN()) {
            return isSidewinder(context) && !PlatformVersion.isAtLeastO();
        }
        return true;
    }

    public static boolean zzf(Context context) {
        if (zzgs == null) {
            zzgs = Boolean.valueOf(context.getPackageManager().hasSystemFeature("android.hardware.type.iot") || context.getPackageManager().hasSystemFeature("android.hardware.type.embedded"));
        }
        return zzgs.booleanValue();
    }
}
