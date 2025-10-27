package com.beizi.ad;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.RequiresPermission;
import com.beizi.ad.internal.g;
import com.beizi.ad.internal.utilities.UserEnvInfo;

/* loaded from: classes2.dex */
public class BeiZi {

    /* renamed from: a, reason: collision with root package name */
    private static BeiZiAdSdkController f3623a = null;

    /* renamed from: b, reason: collision with root package name */
    private static boolean f3624b = false;

    public static BeiZiAdSdkController getCustomController() {
        return f3623a;
    }

    public static int getLocationDecimalDigits() {
        return UserEnvInfo.getInstance().getLocationDecimalDigits();
    }

    public static boolean getLocationEnabled() {
        return UserEnvInfo.getInstance().locationEnabled;
    }

    public static RewardedVideoAd getRewardedVideoAdInstance(Context context) {
        return g.a().a(context);
    }

    @RequiresPermission("android.permission.INTERNET")
    public static void init(Context context, String str) {
        g.a().a(context, str);
    }

    @RequiresPermission("android.permission.INTERNET")
    public static void initWithDomain(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            g.a().a(context, str);
        } else {
            g.a().a(str2).a(context, str);
        }
    }

    public static boolean isHttpsEnabled() {
        return g.a().f4179a;
    }

    public static boolean isLimitPersonalAds() {
        return f3624b;
    }

    public static void logTagInfo(String str, boolean z2) {
        g.a().a(str, z2);
    }

    public static void setAdRequestUrl(String str) {
        g.a().b(str);
    }

    public static void setAppMuted(boolean z2) {
        g.a().a(z2);
    }

    public static void setAppVolume(float f2) {
        g.a().a(f2);
    }

    public static void setLimitPersonalAds(boolean z2) {
        f3624b = z2;
    }

    public static void setLocationDecimalDigits(int i2) {
        UserEnvInfo.getInstance().setLocationDecimalDigits(i2);
    }

    public static void setLocationEnabled(boolean z2) {
        UserEnvInfo.getInstance().locationEnabled = z2;
    }

    public static void useHttps(boolean z2) {
        g.a().f4179a = z2;
    }

    @RequiresPermission("android.permission.INTERNET")
    public static void init(Context context, String str, BeiZiAdSdkController beiZiAdSdkController) {
        f3623a = beiZiAdSdkController;
        g.a().a(context, str);
    }
}
