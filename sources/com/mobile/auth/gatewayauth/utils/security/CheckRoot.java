package com.mobile.auth.gatewayauth.utils.security;

import com.ali.security.MinosSecurityLoad_bbc1bffae6ebd3190382c8ec0f7941ad;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;

@SafeProtector
/* loaded from: classes4.dex */
public class CheckRoot {
    private static String LOG_TAG;

    static {
        MinosSecurityLoad_bbc1bffae6ebd3190382c8ec0f7941ad.SLoad("pns-2.14.3-LogOnlineStandardCuumRelease_alijtca_plus");
        LOG_TAG = "CheckRoot";
    }

    private static native boolean checkDeviceDebuggable();

    private static native boolean checkRootPathSU();

    private static native boolean checkSuperuserApk();

    public static native String isDeviceRooted();
}
