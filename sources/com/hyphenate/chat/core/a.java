package com.hyphenate.chat.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import com.hyphenate.chat.EMClient;
import com.hyphenate.push.EMPushType;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f8556a = "hyphenate.sdk.pref";

    /* renamed from: b, reason: collision with root package name */
    private static SharedPreferences f8557b = null;

    /* renamed from: c, reason: collision with root package name */
    private static SharedPreferences.Editor f8558c = null;

    /* renamed from: d, reason: collision with root package name */
    private static a f8559d = null;

    /* renamed from: e, reason: collision with root package name */
    private static String f8560e = "shared_key_ddversion";

    /* renamed from: f, reason: collision with root package name */
    private static String f8561f = "shared_key_ddxml";

    /* renamed from: g, reason: collision with root package name */
    private static String f8562g = "shared_key_ddtime";

    /* renamed from: h, reason: collision with root package name */
    private static String f8563h = "valid_before";

    /* renamed from: i, reason: collision with root package name */
    private static String f8564i = "scheduled_logout_time";

    /* renamed from: j, reason: collision with root package name */
    private static String f8565j = "shared_key_fcm_id";

    /* renamed from: k, reason: collision with root package name */
    private static String f8566k = "push_flag";

    /* renamed from: l, reason: collision with root package name */
    private static String f8567l = "huawei_app_id";

    /* renamed from: m, reason: collision with root package name */
    private static String f8568m = "huawei_hms_token";

    /* renamed from: n, reason: collision with root package name */
    private static String f8569n = "push_token_";

    /* renamed from: o, reason: collision with root package name */
    private static final String f8570o = "sdk_push_token";

    /* renamed from: p, reason: collision with root package name */
    private static final String f8571p = "sdk_notifier_name";

    /* renamed from: q, reason: collision with root package name */
    private long f8572q = 0;

    /* renamed from: com.hyphenate.chat.core.a$a, reason: collision with other inner class name */
    public static class C0183a {

        /* renamed from: a, reason: collision with root package name */
        String f8573a;

        /* renamed from: b, reason: collision with root package name */
        long f8574b;

        public C0183a() {
        }

        public C0183a(String str, long j2) {
            this.f8573a = str;
            this.f8574b = j2;
        }

        public C0183a a(long j2) {
            this.f8574b = j2;
            return this;
        }

        public C0183a a(String str) {
            this.f8573a = str;
            return this;
        }

        public String a() {
            if (this.f8574b <= 0) {
                this.f8573a = null;
            }
            return this.f8573a;
        }

        public long b() {
            return this.f8574b;
        }
    }

    @SuppressLint({"CommitPrefEdits"})
    private a(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(f8556a, 0);
        f8557b = sharedPreferences;
        f8558c = sharedPreferences.edit();
    }

    public static synchronized a a() {
        if (f8559d == null) {
            f8559d = new a(EMClient.getInstance().getContext());
        }
        return f8559d;
    }

    public String a(EMPushType eMPushType) {
        return f8557b.getString(f8569n + eMPushType.toString(), null);
    }

    public void a(long j2) {
        f8558c.putLong(f8562g, j2);
        f8558c.commit();
    }

    public void a(EMPushType eMPushType, String str) {
        f8558c.putString(f8569n + eMPushType.toString(), str);
        f8558c.commit();
    }

    public void a(String str) {
        f8558c.putString(f8560e, str);
        f8558c.commit();
    }

    public void a(String str, String str2) {
        if (str == null && str2 == null) {
            f8558c.remove("debugIM");
            f8558c.remove("debugRest");
        } else {
            f8558c.putString("debugIM", str);
            f8558c.putString("debugRest", str2);
        }
        f8558c.commit();
    }

    public void a(boolean z2) {
        f8558c.putString("debugMode", String.valueOf(z2));
        f8558c.commit();
    }

    public long b() {
        return f8557b.getLong(f8563h, -1L);
    }

    public void b(long j2) {
        f8558c.putLong(f8563h, j2);
        f8558c.commit();
    }

    public void b(String str) {
        f8558c.putString(f8561f, str);
        f8558c.commit();
    }

    public String c() {
        return f8557b.getString(f8560e, "");
    }

    public void c(long j2) {
        this.f8572q = j2;
        f8558c.putLong(f8564i, j2);
        f8558c.commit();
    }

    public void c(String str) {
        f8558c.putString("debugAppkey", str);
        f8558c.commit();
    }

    public String d() {
        return f8557b.getString(f8561f, "");
    }

    public void d(String str) {
        f8558c.putString(f8565j, str);
        f8558c.commit();
    }

    public long e() {
        return f8557b.getLong(f8562g, -1L);
    }

    public void e(String str) {
        f8558c.putString(f8566k, str);
        f8558c.commit();
    }

    public void f(String str) {
        f8558c.putString(f8567l, str);
        f8558c.commit();
    }

    public boolean f() {
        if (this.f8572q != 0) {
            return true;
        }
        return f8557b.contains(f8564i);
    }

    public long g() {
        long j2 = this.f8572q;
        if (j2 != 0) {
            return j2;
        }
        long j3 = f8557b.getLong(f8564i, -1L);
        this.f8572q = j3;
        return j3;
    }

    public void g(String str) {
        f8558c.putString(f8568m, str);
        f8558c.commit();
    }

    public void h() {
        if (f()) {
            this.f8572q = 0L;
            f8558c.remove(f8564i);
            f8558c.commit();
        }
    }

    public void h(String str) {
        f8558c.putString(f8570o, str);
        f8558c.commit();
    }

    public String i() {
        return f8557b.getString("debugIM", null);
    }

    public void i(String str) {
        f8558c.putString(f8571p, str);
        f8558c.commit();
    }

    public String j() {
        return f8557b.getString("debugRest", null);
    }

    public String k() {
        return f8557b.getString("debugAppkey", null);
    }

    public String l() {
        return f8557b.getString("debugMode", null);
    }

    public String m() {
        return f8557b.getString(f8565j, null);
    }

    public String n() {
        return f8557b.getString(f8566k, null);
    }

    public String o() {
        return f8557b.getString(f8567l, null);
    }

    public String p() {
        return f8557b.getString(f8568m, null);
    }

    public String q() {
        return f8557b.getString(f8570o, null);
    }

    public String r() {
        return f8557b.getString(f8571p, null);
    }
}
