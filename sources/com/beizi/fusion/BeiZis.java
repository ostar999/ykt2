package com.beizi.fusion;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.RequiresPermission;
import com.beizi.fusion.g.ag;

/* loaded from: classes2.dex */
public class BeiZis {

    /* renamed from: a, reason: collision with root package name */
    private static BeiZiCustomController f4709a = null;

    /* renamed from: b, reason: collision with root package name */
    private static boolean f4710b = false;

    /* renamed from: c, reason: collision with root package name */
    private static String f4711c = "1.0.25";

    /* renamed from: d, reason: collision with root package name */
    private static boolean f4712d = true;

    /* renamed from: e, reason: collision with root package name */
    private static boolean f4713e = false;

    @RequiresPermission("android.permission.INTERNET")
    public static void asyncInit(Context context, String str) {
        f4712d = false;
        com.beizi.fusion.d.b.a().a(context, str, null, null);
    }

    @RequiresPermission("android.permission.INTERNET")
    public static void asyncInitWithDomain(Context context, String str, String str2) {
        f4712d = false;
        if (TextUtils.isEmpty(str2)) {
            com.beizi.fusion.d.b.a().a(context, str, null, null);
        } else {
            com.beizi.fusion.d.b.a().a(str2).a(context, str, null, null);
        }
    }

    public static BeiZiCustomController getCustomController() {
        return f4709a;
    }

    public static String getOaidVersion() {
        return f4711c;
    }

    public static String getSdkVersion() {
        return "4.90.2.36";
    }

    public static boolean getTransferProtocol() {
        return f4713e;
    }

    @RequiresPermission("android.permission.INTERNET")
    public static void init(Context context, String str) {
        com.beizi.fusion.d.b.a().a(context, str, null, null);
    }

    @RequiresPermission("android.permission.INTERNET")
    public static void initWithDomain(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            com.beizi.fusion.d.b.a().a(context, str, null, null);
        } else {
            com.beizi.fusion.d.b.a().a(str2).a(context, str, null, null);
        }
    }

    public static boolean isIsSyncInit() {
        return f4712d;
    }

    public static boolean isLimitPersonalAds() {
        return f4710b;
    }

    public static void setLimitPersonalAds(boolean z2) {
        f4710b = z2;
    }

    public static void setOaidVersion(String str) {
        f4711c = str;
    }

    public static void setSupportPersonalized(boolean z2) {
        ag.a(z2);
    }

    public static void setTransferProtocol(boolean z2) {
        f4713e = z2;
    }

    @RequiresPermission("android.permission.INTERNET")
    public static void init(Context context, String str, BeiZiCustomController beiZiCustomController) {
        f4709a = beiZiCustomController;
        com.beizi.fusion.d.b.a().a(context, str, null, null);
    }

    @RequiresPermission("android.permission.INTERNET")
    public static void asyncInit(Context context, String str, BeiZiCustomController beiZiCustomController) {
        f4709a = beiZiCustomController;
        f4712d = false;
        com.beizi.fusion.d.b.a().a(context, str, null, null);
    }

    @RequiresPermission("android.permission.INTERNET")
    public static void init(Context context, String str, BeiZiCustomController beiZiCustomController, String str2) {
        f4709a = beiZiCustomController;
        com.beizi.fusion.d.b.a().a(context, str, str2, null);
    }

    @RequiresPermission("android.permission.INTERNET")
    public static void asyncInit(Context context, String str, BeiZiCustomController beiZiCustomController, String str2) {
        f4709a = beiZiCustomController;
        f4712d = false;
        com.beizi.fusion.d.b.a().a(context, str, str2, null);
    }

    @RequiresPermission("android.permission.INTERNET")
    public static void init(Context context, String str, BeiZiCustomController beiZiCustomController, String str2, String str3) {
        f4709a = beiZiCustomController;
        com.beizi.fusion.d.b.a().a(context, str, str2, str3);
    }

    @RequiresPermission("android.permission.INTERNET")
    public static void asyncInit(Context context, String str, BeiZiCustomController beiZiCustomController, String str2, String str3) {
        f4709a = beiZiCustomController;
        f4712d = false;
        com.beizi.fusion.d.b.a().a(context, str, str2, str3);
    }
}
