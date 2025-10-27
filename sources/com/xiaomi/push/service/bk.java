package com.xiaomi.push.service;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes6.dex */
public final class bk {

    /* renamed from: a, reason: collision with root package name */
    private static volatile bk f25656a;

    /* renamed from: a, reason: collision with other field name */
    Context f1041a;

    /* renamed from: a, reason: collision with other field name */
    private SharedPreferences f1042a;

    public static abstract class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        long f25657a;

        /* renamed from: a, reason: collision with other field name */
        String f1043a;

        public a(String str, long j2) {
            this.f1043a = str;
            this.f25657a = j2;
        }

        public abstract void a(bk bkVar);

        @Override // java.lang.Runnable
        public void run() {
            if (bk.f25656a != null) {
                Context context = bk.f25656a.f1041a;
                if (com.xiaomi.push.as.c(context)) {
                    if (System.currentTimeMillis() - bk.f25656a.f1042a.getLong(":ts-" + this.f1043a, 0L) > this.f25657a || com.xiaomi.push.af.a(context)) {
                        com.xiaomi.push.t.a(bk.f25656a.f1042a.edit().putLong(":ts-" + this.f1043a, System.currentTimeMillis()));
                        a(bk.f25656a);
                    }
                }
            }
        }
    }

    private bk(Context context) {
        this.f1041a = context.getApplicationContext();
        this.f1042a = context.getSharedPreferences("sync", 0);
    }

    public static bk a(Context context) {
        if (f25656a == null) {
            synchronized (bk.class) {
                if (f25656a == null) {
                    f25656a = new bk(context);
                }
            }
        }
        return f25656a;
    }

    public String a(String str, String str2) {
        return this.f1042a.getString(str + ":" + str2, "");
    }

    public void a(a aVar) {
        com.xiaomi.push.ai.a(this.f1041a).a(aVar, ((int) (Math.random() * 30.0d)) + 10);
    }

    public void a(String str, String str2, String str3) {
        com.xiaomi.push.t.a(f25656a.f1042a.edit().putString(str + ":" + str2, str3));
    }
}
