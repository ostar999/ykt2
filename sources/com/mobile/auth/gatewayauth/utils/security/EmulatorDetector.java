package com.mobile.auth.gatewayauth.utils.security;

import android.content.Context;
import com.ali.security.MinosSecurityLoad_bbc1bffae6ebd3190382c8ec0f7941ad;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;

@SafeProtector
/* loaded from: classes4.dex */
public class EmulatorDetector {
    private static final String TAG = "EmulatorDetector";
    private static int rating;

    static {
        MinosSecurityLoad_bbc1bffae6ebd3190382c8ec0f7941ad.SLoad("pns-2.14.3-LogOnlineStandardCuumRelease_alijtca_plus");
        rating = -1;
    }

    private static final native String getProp(Context context, String str);

    public static native boolean isEmulator(Context context);

    private static native boolean isEmulatorAbsoluly(Context context);

    private static final native boolean mayOnEmulatorViaQEMU(Context context);
}
