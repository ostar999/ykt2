package com.mobile.auth.gatewayauth.utils.security;

import com.ali.security.MinosSecurityLoad_bbc1bffae6ebd3190382c8ec0f7941ad;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;

/* loaded from: classes4.dex */
public class CheckHook {

    /* renamed from: a, reason: collision with root package name */
    private static int f10313a;

    /* renamed from: b, reason: collision with root package name */
    private static int f10314b;

    static {
        MinosSecurityLoad_bbc1bffae6ebd3190382c8ec0f7941ad.SLoad("pns-2.14.3-LogOnlineStandardCuumRelease_alijtca_plus");
        f10313a = -1;
        f10314b = -1;
    }

    @SafeProtector
    public static native synchronized boolean isHookByJar();

    @SafeProtector
    public static native synchronized boolean isHookByStack();
}
