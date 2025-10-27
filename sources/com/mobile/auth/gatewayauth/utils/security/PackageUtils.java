package com.mobile.auth.gatewayauth.utils.security;

import android.content.Context;
import com.ali.security.MinosSecurityLoad_bbc1bffae6ebd3190382c8ec0f7941ad;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;

/* loaded from: classes4.dex */
public class PackageUtils {

    /* renamed from: a, reason: collision with root package name */
    private static String f10315a;

    /* renamed from: b, reason: collision with root package name */
    private static String f10316b;

    /* renamed from: c, reason: collision with root package name */
    private static String f10317c;

    /* renamed from: d, reason: collision with root package name */
    private static String f10318d;

    /* renamed from: e, reason: collision with root package name */
    private static final char[] f10319e;

    static {
        MinosSecurityLoad_bbc1bffae6ebd3190382c8ec0f7941ad.SLoad("pns-2.14.3-LogOnlineStandardCuumRelease_alijtca_plus");
        f10315a = null;
        f10316b = null;
        f10317c = null;
        f10318d = null;
        f10319e = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    }

    @SafeProtector
    public static native synchronized String getPackageName(Context context);

    @SafeProtector
    public static native synchronized String getSign(Context context);

    @SafeProtector
    public static native synchronized String getVersionName(Context context);

    @SafeProtector
    public static native String hexdigest(byte[] bArr);

    @SafeProtector
    private static native void setupAppInfo(Context context);
}
