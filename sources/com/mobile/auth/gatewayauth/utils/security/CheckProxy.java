package com.mobile.auth.gatewayauth.utils.security;

import android.content.Context;
import com.ali.security.MinosSecurityLoad_bbc1bffae6ebd3190382c8ec0f7941ad;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;

@SafeProtector
/* loaded from: classes4.dex */
public class CheckProxy {
    static {
        MinosSecurityLoad_bbc1bffae6ebd3190382c8ec0f7941ad.SLoad("pns-2.14.3-LogOnlineStandardCuumRelease_alijtca_plus");
    }

    public static native boolean isDevicedProxy(Context context);
}
