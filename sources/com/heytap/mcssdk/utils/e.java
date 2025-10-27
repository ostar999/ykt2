package com.heytap.mcssdk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import com.heytap.mcssdk.PushService;

/* loaded from: classes4.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    private static final String f7225a = "shared_msg_sdk";

    /* renamed from: b, reason: collision with root package name */
    private static final String f7226b = "hasDefaultChannelCreated";

    /* renamed from: c, reason: collision with root package name */
    private static final String f7227c = "lastUpLoadInfoSDKVersionName";

    /* renamed from: d, reason: collision with root package name */
    private static final String f7228d = "lastUploadInfoUniqueID";

    /* renamed from: e, reason: collision with root package name */
    private static final String f7229e = "decryptTag";

    /* renamed from: f, reason: collision with root package name */
    private Context f7230f;

    /* renamed from: g, reason: collision with root package name */
    private SharedPreferences f7231g;

    /* renamed from: h, reason: collision with root package name */
    private Object f7232h;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        static e f7233a = new e();

        private a() {
        }
    }

    private e() {
        this.f7232h = new Object();
        Context context = PushService.getInstance().getContext();
        if (context != null) {
            this.f7230f = a(context);
        }
        Context context2 = this.f7230f;
        if (context2 != null) {
            this.f7231g = context2.getSharedPreferences(f7225a, 0);
        }
    }

    private Context a(Context context) {
        boolean zA = com.heytap.mcssdk.utils.a.a();
        d.b("fbeVersion is " + zA);
        return (!zA || Build.VERSION.SDK_INT < 24) ? context.getApplicationContext() : context.createDeviceProtectedStorageContext();
    }

    public static e f() {
        return a.f7233a;
    }

    private SharedPreferences g() {
        Context context;
        SharedPreferences sharedPreferences = this.f7231g;
        if (sharedPreferences != null) {
            return sharedPreferences;
        }
        synchronized (this.f7232h) {
            SharedPreferences sharedPreferences2 = this.f7231g;
            if (sharedPreferences2 != null || (context = this.f7230f) == null) {
                return sharedPreferences2;
            }
            SharedPreferences sharedPreferences3 = context.getSharedPreferences(f7225a, 0);
            this.f7231g = sharedPreferences3;
            return sharedPreferences3;
        }
    }

    public int a(String str, int i2) {
        SharedPreferences sharedPreferencesG = g();
        return sharedPreferencesG != null ? sharedPreferencesG.getInt(str, i2) : i2;
    }

    public long a(String str, long j2) {
        SharedPreferences sharedPreferencesG = g();
        return sharedPreferencesG != null ? sharedPreferencesG.getLong(str, j2) : j2;
    }

    public void a(String str) {
        SharedPreferences sharedPreferencesG = g();
        if (sharedPreferencesG != null) {
            sharedPreferencesG.edit().putString(f7228d, str).commit();
        }
    }

    public void a(boolean z2) {
        SharedPreferences sharedPreferencesG = g();
        if (sharedPreferencesG != null) {
            sharedPreferencesG.edit().putBoolean(f7226b, z2).commit();
        }
    }

    public boolean a() {
        SharedPreferences sharedPreferencesG = g();
        if (sharedPreferencesG != null) {
            return sharedPreferencesG.getBoolean(f7226b, false);
        }
        return false;
    }

    public void b() {
        SharedPreferences sharedPreferencesG = g();
        if (sharedPreferencesG != null) {
            sharedPreferencesG.edit().putString(f7227c, "3.0.0").commit();
        }
    }

    public void b(String str) {
        SharedPreferences sharedPreferencesG = g();
        if (sharedPreferencesG != null) {
            sharedPreferencesG.edit().putString(f7229e, str).commit();
        }
    }

    public void b(String str, int i2) {
        SharedPreferences sharedPreferencesG = g();
        if (sharedPreferencesG != null) {
            SharedPreferences.Editor editorEdit = sharedPreferencesG.edit();
            editorEdit.putInt(str, i2);
            editorEdit.apply();
        }
    }

    public void b(String str, long j2) {
        SharedPreferences sharedPreferencesG = g();
        if (sharedPreferencesG != null) {
            SharedPreferences.Editor editorEdit = sharedPreferencesG.edit();
            editorEdit.putLong(str, j2);
            editorEdit.apply();
        }
    }

    public int c(String str) {
        SharedPreferences sharedPreferencesG = g();
        if (sharedPreferencesG != null) {
            return sharedPreferencesG.getInt(str, 0);
        }
        return 0;
    }

    public String c() {
        SharedPreferences sharedPreferencesG = g();
        return sharedPreferencesG != null ? sharedPreferencesG.getString(f7228d, "") : "";
    }

    public long d(String str) {
        SharedPreferences sharedPreferencesG = g();
        return sharedPreferencesG != null ? sharedPreferencesG.getLong(str, com.heytap.mcssdk.constant.a.f7138b.longValue()) : com.heytap.mcssdk.constant.a.f7138b.longValue();
    }

    public String d() {
        SharedPreferences sharedPreferencesG = g();
        return sharedPreferencesG != null ? sharedPreferencesG.getString(f7227c, "") : "";
    }

    public String e() {
        SharedPreferences sharedPreferencesG = g();
        return sharedPreferencesG != null ? sharedPreferencesG.getString(f7229e, "DES") : "DES";
    }
}
