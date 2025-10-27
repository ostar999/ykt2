package com.mobile.auth.gatewayauth.utils;

import android.content.Context;
import com.ali.security.MinosSecurityLoad_bbc1bffae6ebd3190382c8ec0f7941ad;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;

@SafeProtector
/* loaded from: classes4.dex */
public class EncryptUtils {
    public static final String IV_PARAMETER_SPEC = "0000000000000000";

    static {
        MinosSecurityLoad_bbc1bffae6ebd3190382c8ec0f7941ad.SLoad("pns-2.14.3-LogOnlineStandardCuumRelease_alijtca_plus");
    }

    private static native boolean checkMonitorUse();

    public static native String encrpytAESKey(String str);

    public static native String encrpytAESKey(String str, String str2);

    private static native String encrypt(String str, String str2);

    public static native String encryptInfoForCertifyId(Context context, String str, String str2, String str3, String str4, String str5);

    public static native String encryptToken(Context context, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, boolean z2, String str9, boolean z3, String str10);

    public static native String generateAesKey();

    private static native String getSecret1();

    private static native String getSecret2();

    private static native String getSecret3();

    private static native String getSecret4();

    public static native String getSecret5();

    public static native String getSecret6();

    private static native byte[] hash(String str);

    public static native String hashWithSalt(String str, String str2);

    public static native String noEncryptTinfo(Context context, String str, String str2);
}
