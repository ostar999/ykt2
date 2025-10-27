package com.sina.weibo.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.b.a;
import com.sina.weibo.sdk.openapi.SdkListener;
import com.weibo.ssosdk.VisitorLoginListener;
import com.weibo.ssosdk.WeiboSsoSdk;
import com.weibo.ssosdk.WeiboSsoSdkConfig;
import java.util.List;

/* loaded from: classes6.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f17168a = false;

    /* renamed from: b, reason: collision with root package name */
    private static AuthInfo f17169b;
    private static String mAid;

    public static AuthInfo b() {
        a();
        return f17169b;
    }

    public static /* synthetic */ boolean c() {
        f17168a = true;
        return true;
    }

    public static String getAid() {
        a();
        return mAid;
    }

    public static void a(Context context, AuthInfo authInfo, final SdkListener sdkListener) {
        if (f17168a) {
            return;
        }
        if (authInfo == null) {
            throw new RuntimeException("authInfo must not be null.");
        }
        f17169b = authInfo;
        String appKey = authInfo.getAppKey();
        WeiboSsoSdkConfig weiboSsoSdkConfig = new WeiboSsoSdkConfig();
        weiboSsoSdkConfig.setContext(context.getApplicationContext());
        weiboSsoSdkConfig.setAppKey(appKey);
        weiboSsoSdkConfig.setFrom("1478195010");
        weiboSsoSdkConfig.setWm("1000_0001");
        WeiboSsoSdk.initConfig(weiboSsoSdkConfig);
        try {
            WeiboSsoSdk.getInstance().visitorLogin(new VisitorLoginListener() { // from class: com.sina.weibo.sdk.a.1
                @Override // com.weibo.ssosdk.VisitorLoginListener
                public final void handler(WeiboSsoSdk.VisitorLoginInfo visitorLoginInfo) {
                    if (visitorLoginInfo != null) {
                        try {
                            String unused = a.mAid = visitorLoginInfo.getAid();
                            a.c();
                            SdkListener sdkListener2 = sdkListener;
                            if (sdkListener2 != null) {
                                sdkListener2.onInitSuccess();
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            SdkListener sdkListener3 = sdkListener;
                            if (sdkListener3 != null) {
                                sdkListener3.onInitFailure(e2);
                            }
                        }
                    }
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
            if (sdkListener != null) {
                sdkListener.onInitFailure(e2);
            }
        }
    }

    public static boolean b(Context context) {
        a.C0313a c0313aE;
        return a(context) && (c0313aE = com.sina.weibo.sdk.b.a.e(context)) != null && c0313aE.ah >= 10772;
    }

    private static void a() {
        if (!f17168a) {
            throw new RuntimeException("please init sdk before use it. Wb.install()");
        }
    }

    public static boolean a(Context context) {
        List<ResolveInfo> listQueryIntentServices;
        Intent intent = new Intent("com.sina.weibo.action.sdkidentity");
        intent.addCategory("android.intent.category.DEFAULT");
        return (context == null || (listQueryIntentServices = context.getPackageManager().queryIntentServices(intent, 0)) == null || listQueryIntentServices.isEmpty()) ? false : true;
    }
}
