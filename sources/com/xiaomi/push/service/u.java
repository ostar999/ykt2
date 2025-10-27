package com.xiaomi.push.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class u {

    /* renamed from: a, reason: collision with root package name */
    private static u f25719a;

    /* renamed from: a, reason: collision with other field name */
    private Context f1092a;

    /* renamed from: a, reason: collision with other field name */
    private List<String> f1093a = new ArrayList();

    /* renamed from: b, reason: collision with root package name */
    private final List<String> f25720b = new ArrayList();

    /* renamed from: c, reason: collision with root package name */
    private final List<String> f25721c = new ArrayList();

    private u(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.f1092a = applicationContext;
        if (applicationContext == null) {
            this.f1092a = context;
        }
        SharedPreferences sharedPreferences = this.f1092a.getSharedPreferences("mipush_app_info", 0);
        for (String str : sharedPreferences.getString("unregistered_pkg_names", "").split(",")) {
            if (TextUtils.isEmpty(str)) {
                this.f1093a.add(str);
            }
        }
        for (String str2 : sharedPreferences.getString("disable_push_pkg_names", "").split(",")) {
            if (!TextUtils.isEmpty(str2)) {
                this.f25720b.add(str2);
            }
        }
        for (String str3 : sharedPreferences.getString("disable_push_pkg_names_cache", "").split(",")) {
            if (!TextUtils.isEmpty(str3)) {
                this.f25721c.add(str3);
            }
        }
    }

    public static u a(Context context) {
        if (f25719a == null) {
            f25719a = new u(context);
        }
        return f25719a;
    }

    public void a(String str) {
        synchronized (this.f1093a) {
            if (!this.f1093a.contains(str)) {
                this.f1093a.add(str);
                this.f1092a.getSharedPreferences("mipush_app_info", 0).edit().putString("unregistered_pkg_names", com.xiaomi.push.ay.a(this.f1093a, ",")).commit();
            }
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m766a(String str) {
        boolean zContains;
        synchronized (this.f1093a) {
            zContains = this.f1093a.contains(str);
        }
        return zContains;
    }

    public void b(String str) {
        synchronized (this.f25720b) {
            if (!this.f25720b.contains(str)) {
                this.f25720b.add(str);
                this.f1092a.getSharedPreferences("mipush_app_info", 0).edit().putString("disable_push_pkg_names", com.xiaomi.push.ay.a(this.f25720b, ",")).commit();
            }
        }
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m767b(String str) {
        boolean zContains;
        synchronized (this.f25720b) {
            zContains = this.f25720b.contains(str);
        }
        return zContains;
    }

    public void c(String str) {
        synchronized (this.f25721c) {
            if (!this.f25721c.contains(str)) {
                this.f25721c.add(str);
                this.f1092a.getSharedPreferences("mipush_app_info", 0).edit().putString("disable_push_pkg_names_cache", com.xiaomi.push.ay.a(this.f25721c, ",")).commit();
            }
        }
    }

    /* renamed from: c, reason: collision with other method in class */
    public boolean m768c(String str) {
        boolean zContains;
        synchronized (this.f25721c) {
            zContains = this.f25721c.contains(str);
        }
        return zContains;
    }

    public void d(String str) {
        synchronized (this.f1093a) {
            if (this.f1093a.contains(str)) {
                this.f1093a.remove(str);
                this.f1092a.getSharedPreferences("mipush_app_info", 0).edit().putString("unregistered_pkg_names", com.xiaomi.push.ay.a(this.f1093a, ",")).commit();
            }
        }
    }

    public void e(String str) {
        synchronized (this.f25720b) {
            if (this.f25720b.contains(str)) {
                this.f25720b.remove(str);
                this.f1092a.getSharedPreferences("mipush_app_info", 0).edit().putString("disable_push_pkg_names", com.xiaomi.push.ay.a(this.f25720b, ",")).commit();
            }
        }
    }

    public void f(String str) {
        synchronized (this.f25721c) {
            if (this.f25721c.contains(str)) {
                this.f25721c.remove(str);
                this.f1092a.getSharedPreferences("mipush_app_info", 0).edit().putString("disable_push_pkg_names_cache", com.xiaomi.push.ay.a(this.f25721c, ",")).commit();
            }
        }
    }
}
