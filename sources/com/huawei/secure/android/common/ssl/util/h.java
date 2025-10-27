package com.huawei.secure.android.common.ssl.util;

import android.content.Context;
import android.content.pm.PackageManager;

/* loaded from: classes4.dex */
public class h {

    /* renamed from: a, reason: collision with root package name */
    private static final String f8417a = "h";

    public static String a(String str) {
        Context contextA = c.a();
        if (contextA == null) {
            return "";
        }
        try {
            return contextA.getPackageManager().getPackageInfo(str, 0).versionName;
        } catch (PackageManager.NameNotFoundException e2) {
            g.b(f8417a, "getVersion NameNotFoundException : " + e2.getMessage());
            return "";
        } catch (Exception e3) {
            g.b(f8417a, "getVersion: " + e3.getMessage());
            return "";
        } catch (Throwable unused) {
            g.b(f8417a, "throwable");
            return "";
        }
    }

    public static int b(String str) {
        Context contextA = c.a();
        if (contextA == null) {
            return 0;
        }
        try {
            return contextA.getPackageManager().getPackageInfo(str, 0).versionCode;
        } catch (PackageManager.NameNotFoundException unused) {
            g.b(f8417a, "getVersion NameNotFoundException");
            return 0;
        } catch (Exception e2) {
            g.b(f8417a, "getVersion: " + e2.getMessage());
            return 0;
        }
    }
}
