package com.alipay.sdk.auth;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

/* loaded from: classes2.dex */
public final class h {

    /* renamed from: a, reason: collision with root package name */
    private static final String f3161a = "com.eg.android.AlipayGphone";

    /* renamed from: b, reason: collision with root package name */
    private static final int f3162b = 65;

    /* renamed from: c, reason: collision with root package name */
    private static com.alipay.sdk.widget.a f3163c;

    /* renamed from: d, reason: collision with root package name */
    private static String f3164d;

    public static /* synthetic */ com.alipay.sdk.widget.a b() {
        f3163c = null;
        return null;
    }

    private static void b(Activity activity, APAuthInfo aPAuthInfo) {
        a(activity, "alipayauth://platformapi/startapp?appId=20000122&approveType=005&scope=kuaijie&productId=" + aPAuthInfo.getProductId() + "&thirdpartyId=" + aPAuthInfo.getAppId() + "&redirectUri=" + aPAuthInfo.getRedirectUri());
    }

    private static void c(Activity activity, APAuthInfo aPAuthInfo) {
        if (activity != null) {
            try {
                if (!activity.isFinishing()) {
                    com.alipay.sdk.widget.a aVar = new com.alipay.sdk.widget.a(activity, com.alipay.sdk.widget.a.f3397a);
                    f3163c = aVar;
                    aVar.a();
                }
            } catch (Exception unused) {
                f3163c = null;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("app_id=");
        sb.append(aPAuthInfo.getAppId());
        sb.append("&partner=");
        sb.append(aPAuthInfo.getPid());
        sb.append("&scope=kuaijie");
        sb.append("&login_goal=auth");
        sb.append("&redirect_url=");
        sb.append(aPAuthInfo.getRedirectUri());
        sb.append("&view=wap");
        sb.append("&prod_code=");
        sb.append(aPAuthInfo.getProductId());
        new Thread(new i(activity, sb, aPAuthInfo)).start();
    }

    private static boolean a(Context context) throws PackageManager.NameNotFoundException {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(f3161a, 128);
            if (packageInfo == null) {
                return false;
            }
            return packageInfo.versionCode >= 65;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static void a(Activity activity, APAuthInfo aPAuthInfo) {
        com.alipay.sdk.sys.b bVarA = com.alipay.sdk.sys.b.a();
        com.alipay.sdk.data.c.a();
        bVarA.a(activity);
        if (a(activity)) {
            a(activity, "alipayauth://platformapi/startapp?appId=20000122&approveType=005&scope=kuaijie&productId=" + aPAuthInfo.getProductId() + "&thirdpartyId=" + aPAuthInfo.getAppId() + "&redirectUri=" + aPAuthInfo.getRedirectUri());
            return;
        }
        if (activity != null) {
            try {
                if (!activity.isFinishing()) {
                    com.alipay.sdk.widget.a aVar = new com.alipay.sdk.widget.a(activity, com.alipay.sdk.widget.a.f3397a);
                    f3163c = aVar;
                    aVar.a();
                }
            } catch (Exception unused) {
                f3163c = null;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("app_id=");
        sb.append(aPAuthInfo.getAppId());
        sb.append("&partner=");
        sb.append(aPAuthInfo.getPid());
        sb.append("&scope=kuaijie");
        sb.append("&login_goal=auth");
        sb.append("&redirect_url=");
        sb.append(aPAuthInfo.getRedirectUri());
        sb.append("&view=wap");
        sb.append("&prod_code=");
        sb.append(aPAuthInfo.getProductId());
        new Thread(new i(activity, sb, aPAuthInfo)).start();
    }

    public static void a(Activity activity, String str) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(str));
            activity.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
        }
    }
}
