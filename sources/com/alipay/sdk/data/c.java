package com.alipay.sdk.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.TextView;
import com.alipay.mobilesecuritysdk.face.SecurityClientMobile;
import com.alipay.sdk.util.h;
import com.alipay.sdk.util.l;
import com.google.android.exoplayer2.C;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes2.dex */
public final class c {

    /* renamed from: d, reason: collision with root package name */
    private static final String f3253d = "virtualImeiAndImsi";

    /* renamed from: e, reason: collision with root package name */
    private static final String f3254e = "virtual_imei";

    /* renamed from: f, reason: collision with root package name */
    private static final String f3255f = "virtual_imsi";

    /* renamed from: g, reason: collision with root package name */
    private static c f3256g;

    /* renamed from: a, reason: collision with root package name */
    public String f3257a;

    /* renamed from: b, reason: collision with root package name */
    public String f3258b = "sdk-and-lite";

    /* renamed from: c, reason: collision with root package name */
    public String f3259c;

    private c() {
    }

    public static synchronized c a() {
        if (f3256g == null) {
            f3256g = new c();
        }
        return f3256g;
    }

    private static String b(Context context) {
        return Float.toString(new TextView(context).getTextSize());
    }

    private static String d() {
        return "1";
    }

    private static String d(Context context) {
        WifiInfo connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
        return connectionInfo != null ? connectionInfo.getBSSID() : TarConstants.VERSION_POSIX;
    }

    private static String e() {
        return "-1;-1";
    }

    private static String f() {
        Context context = com.alipay.sdk.sys.b.a().f3333a;
        SharedPreferences sharedPreferences = context.getSharedPreferences(f3253d, 0);
        String string = sharedPreferences.getString(f3254e, null);
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        String strB = TextUtils.isEmpty(com.alipay.sdk.tid.b.a().f3338a) ? b() : com.alipay.sdk.util.a.a(context).b();
        sharedPreferences.edit().putString(f3254e, strB).commit();
        return strB;
    }

    private static String g() {
        String strA;
        Context context = com.alipay.sdk.sys.b.a().f3333a;
        SharedPreferences sharedPreferences = context.getSharedPreferences(f3253d, 0);
        String string = sharedPreferences.getString(f3255f, null);
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        if (TextUtils.isEmpty(com.alipay.sdk.tid.b.a().f3338a)) {
            String strC = com.alipay.sdk.sys.b.a().c();
            strA = TextUtils.isEmpty(strC) ? b() : strC.substring(3, 18);
        } else {
            strA = com.alipay.sdk.util.a.a(context).a();
        }
        String str = strA;
        sharedPreferences.edit().putString(f3255f, str).commit();
        return str;
    }

    private String c() {
        return this.f3259c;
    }

    public static String b() {
        return Long.toHexString(System.currentTimeMillis()) + (new Random().nextInt(9000) + 1000);
    }

    private static String c(Context context) {
        WifiInfo connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
        return connectionInfo != null ? connectionInfo.getSSID() : "-1";
    }

    public final synchronized void a(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        PreferenceManager.getDefaultSharedPreferences(com.alipay.sdk.sys.b.a().f3333a).edit().putString(com.alipay.sdk.cons.b.f3223i, str).commit();
        com.alipay.sdk.cons.a.f3197c = str;
    }

    public final String b(Context context, HashMap<String, String> map) {
        try {
            return (String) Executors.newFixedThreadPool(2).submit(new d(this, context, map)).get(C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS, TimeUnit.MILLISECONDS);
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.f3115e, com.alipay.sdk.app.statistic.c.f3119i, th);
            return "";
        }
    }

    private String a(com.alipay.sdk.tid.b bVar) throws IOException {
        String strB;
        String strA;
        Context context = com.alipay.sdk.sys.b.a().f3333a;
        com.alipay.sdk.util.a aVarA = com.alipay.sdk.util.a.a(context);
        if (TextUtils.isEmpty(this.f3257a)) {
            this.f3257a = "Msp/15.5.5 (" + l.b() + h.f3376b + l.c() + h.f3376b + l.g(context) + h.f3376b + l.i(context) + h.f3376b + l.h(context) + h.f3376b + Float.toString(new TextView(context).getTextSize());
        }
        String str = com.alipay.sdk.util.a.b(context).f3363p;
        String strD = l.d();
        String strA2 = aVarA.a();
        String strB2 = aVarA.b();
        Context context2 = com.alipay.sdk.sys.b.a().f3333a;
        SharedPreferences sharedPreferences = context2.getSharedPreferences(f3253d, 0);
        String string = sharedPreferences.getString(f3255f, null);
        if (TextUtils.isEmpty(string)) {
            if (TextUtils.isEmpty(com.alipay.sdk.tid.b.a().f3338a)) {
                String strC = com.alipay.sdk.sys.b.a().c();
                if (TextUtils.isEmpty(strC)) {
                    strA = b();
                } else {
                    strA = strC.substring(3, 18);
                }
            } else {
                strA = com.alipay.sdk.util.a.a(context2).a();
            }
            string = strA;
            sharedPreferences.edit().putString(f3255f, string).commit();
        }
        Context context3 = com.alipay.sdk.sys.b.a().f3333a;
        SharedPreferences sharedPreferences2 = context3.getSharedPreferences(f3253d, 0);
        String string2 = sharedPreferences2.getString(f3254e, null);
        if (TextUtils.isEmpty(string2)) {
            if (TextUtils.isEmpty(com.alipay.sdk.tid.b.a().f3338a)) {
                strB = b();
            } else {
                strB = com.alipay.sdk.util.a.a(context3).b();
            }
            string2 = strB;
            sharedPreferences2.edit().putString(f3254e, string2).commit();
        }
        if (bVar != null) {
            this.f3259c = bVar.f3339b;
        }
        String strReplace = Build.MANUFACTURER.replace(h.f3376b, " ");
        String strReplace2 = Build.MODEL.replace(h.f3376b, " ");
        boolean zB = com.alipay.sdk.sys.b.b();
        String str2 = aVarA.f3342a;
        WifiInfo connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
        String ssid = connectionInfo != null ? connectionInfo.getSSID() : "-1";
        WifiInfo connectionInfo2 = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
        String bssid = connectionInfo2 != null ? connectionInfo2.getBSSID() : TarConstants.VERSION_POSIX;
        StringBuilder sb = new StringBuilder();
        sb.append(this.f3257a);
        sb.append(h.f3376b);
        sb.append(str);
        sb.append(h.f3376b);
        sb.append(strD);
        sb.append(h.f3376b);
        sb.append("1");
        sb.append(h.f3376b);
        sb.append(strA2);
        sb.append(h.f3376b);
        sb.append(strB2);
        sb.append(h.f3376b);
        sb.append(this.f3259c);
        sb.append(h.f3376b);
        sb.append(strReplace);
        sb.append(h.f3376b);
        sb.append(strReplace2);
        sb.append(h.f3376b);
        sb.append(zB);
        sb.append(h.f3376b);
        sb.append(str2);
        sb.append(";-1;-1;");
        sb.append(this.f3258b);
        sb.append(h.f3376b);
        sb.append(string);
        sb.append(h.f3376b);
        sb.append(string2);
        sb.append(h.f3376b);
        sb.append(ssid);
        sb.append(h.f3376b);
        sb.append(bssid);
        if (bVar != null) {
            HashMap<String, String> map = new HashMap<>();
            map.put(com.alipay.sdk.cons.b.f3217c, bVar.f3338a);
            map.put("utdid", com.alipay.sdk.sys.b.a().c());
            String strB3 = b(context, map);
            if (!TextUtils.isEmpty(strB3)) {
                sb.append(h.f3376b);
                sb.append(strB3);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public static String a(Context context, HashMap<String, String> map) {
        String strGetApdid;
        try {
            strGetApdid = SecurityClientMobile.GetApdid(context, map);
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.f3115e, com.alipay.sdk.app.statistic.c.f3117g, th);
            strGetApdid = "";
        }
        if (TextUtils.isEmpty(strGetApdid)) {
            com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.f3115e, com.alipay.sdk.app.statistic.c.f3118h, "apdid == null");
        }
        return strGetApdid;
    }

    public static String a(Context context) throws PackageManager.NameNotFoundException {
        if (context == null) {
            return "";
        }
        try {
            StringBuilder sb = new StringBuilder();
            String packageName = context.getPackageName();
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            sb.append("(");
            sb.append(packageName);
            sb.append(h.f3376b);
            sb.append(packageInfo.versionCode);
            sb.append(")");
            return sb.toString();
        } catch (Exception unused) {
            return "";
        }
    }
}
