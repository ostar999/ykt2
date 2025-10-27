package com.tencent.tbs.one.impl.common.a;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.tencent.tbs.one.impl.a.e;
import com.tencent.tbs.one.impl.a.g;

/* loaded from: classes6.dex */
public final class c {

    /* renamed from: a, reason: collision with root package name */
    public static String f21975a = "";

    /* renamed from: b, reason: collision with root package name */
    public static String f21976b = "";

    /* renamed from: c, reason: collision with root package name */
    public static String f21977c = "";

    public static int a(Context context) {
        ConnectivityManager connectivityManager;
        try {
            connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
        if (!(context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", context.getPackageName()) == 0)) {
            g.b("Require \"ACCESS_NETWORK_STATE\" permission for reading apn type!", new Object[0]);
            return 0;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnectedOrConnecting()) {
            return 0;
        }
        int type = activeNetworkInfo.getType();
        if (type != 0) {
            return type != 1 ? 0 : 3;
        }
        switch (activeNetworkInfo.getSubtype()) {
        }
        return 0;
    }

    @Deprecated
    public static String a() {
        return "";
    }

    public static String b(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e2) {
            e2.printStackTrace();
            g.c("Get app version name exception!", e2);
            return null;
        }
    }

    public static int c(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public static String d(Context context) {
        return e.b(context);
    }
}
