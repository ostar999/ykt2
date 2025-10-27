package com.google.android.gms.common.wrappers;

import android.content.Context;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.PlatformVersion;

@KeepForSdk
/* loaded from: classes3.dex */
public class InstantApps {
    private static Context zzhv;
    private static Boolean zzhw;

    @KeepForSdk
    public static synchronized boolean isInstantApp(Context context) {
        Boolean bool;
        Context applicationContext = context.getApplicationContext();
        Context context2 = zzhv;
        if (context2 != null && (bool = zzhw) != null && context2 == applicationContext) {
            return bool.booleanValue();
        }
        zzhw = null;
        if (PlatformVersion.isAtLeastO()) {
            zzhw = Boolean.valueOf(applicationContext.getPackageManager().isInstantApp());
        } else {
            try {
                context.getClassLoader().loadClass("com.google.android.instantapps.supervisor.InstantAppsRuntime");
                zzhw = Boolean.TRUE;
            } catch (ClassNotFoundException unused) {
                zzhw = Boolean.FALSE;
            }
        }
        zzhv = applicationContext;
        return zzhw.booleanValue();
    }
}
