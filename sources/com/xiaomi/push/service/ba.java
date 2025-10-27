package com.xiaomi.push.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.provider.Settings;

/* loaded from: classes6.dex */
public class ba {

    /* renamed from: a, reason: collision with root package name */
    private static ba f25639a;

    /* renamed from: a, reason: collision with other field name */
    private int f1024a = 0;

    /* renamed from: a, reason: collision with other field name */
    private Context f1025a;

    private ba(Context context) {
        this.f1025a = context.getApplicationContext();
    }

    public static ba a(Context context) {
        if (f25639a == null) {
            f25639a = new ba(context);
        }
        return f25639a;
    }

    @SuppressLint({"NewApi"})
    public int a() {
        int i2 = this.f1024a;
        if (i2 != 0) {
            return i2;
        }
        int i3 = Settings.Global.getInt(this.f1025a.getContentResolver(), "device_provisioned", 0);
        this.f1024a = i3;
        return i3;
    }

    @SuppressLint({"NewApi"})
    /* renamed from: a, reason: collision with other method in class */
    public Uri m724a() {
        return Settings.Global.getUriFor("device_provisioned");
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m725a() {
        String str = com.xiaomi.push.ab.f178a;
        return str.contains("xmsf") || str.contains("xiaomi") || str.contains("miui");
    }
}
