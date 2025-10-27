package com.mobile.auth.gatewayauth.utils;

import android.app.Activity;
import android.app.Application;
import com.ali.security.MinosSecurityLoad_bbc1bffae6ebd3190382c8ec0f7941ad;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;

@SafeProtector
/* loaded from: classes4.dex */
public class ReflectionUtils {
    private static volatile Application sApplication;

    static {
        MinosSecurityLoad_bbc1bffae6ebd3190382c8ec0f7941ad.SLoad("pns-2.14.3-LogOnlineStandardCuumRelease_alijtca_plus");
        sApplication = null;
    }

    public static native Activity getActivity();

    public static native Application getApplication();
}
