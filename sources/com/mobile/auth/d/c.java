package com.mobile.auth.d;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import com.mobile.auth.BuildConfig;
import com.mobile.auth.d.b;
import com.mobile.auth.l.k;

/* loaded from: classes4.dex */
public class c implements b.a {

    /* renamed from: a, reason: collision with root package name */
    @SuppressLint({"StaticFieldLeak"})
    private static c f9701a;

    /* renamed from: b, reason: collision with root package name */
    private a f9702b;

    /* renamed from: c, reason: collision with root package name */
    private a f9703c;

    /* renamed from: d, reason: collision with root package name */
    private b f9704d;

    /* renamed from: e, reason: collision with root package name */
    private Context f9705e;

    private c(Context context) {
        this.f9705e = context;
        b();
    }

    public static c a(Context context) {
        if (f9701a == null) {
            synchronized (c.class) {
                if (f9701a == null) {
                    f9701a = new c(context);
                }
            }
        }
        return f9701a;
    }

    private void b() {
        String strB = k.b("sdk_config_version", "");
        if (TextUtils.isEmpty(strB) || !BuildConfig.CMCC_SDK_VERSION.equals(strB)) {
            b bVarA = b.a(true);
            this.f9704d = bVarA;
            this.f9702b = bVarA.a();
            if (!TextUtils.isEmpty(strB)) {
                c();
            }
        } else {
            b bVarA2 = b.a(false);
            this.f9704d = bVarA2;
            this.f9702b = bVarA2.b();
        }
        this.f9704d.a(this);
        this.f9703c = this.f9704d.a();
    }

    private void c() {
        com.mobile.auth.l.c.b("UmcConfigManager", "delete localConfig");
        this.f9704d.c();
    }

    public a a() {
        try {
            return this.f9702b.clone();
        } catch (CloneNotSupportedException unused) {
            return this.f9703c;
        }
    }

    public void a(com.cmic.sso.sdk.a aVar) {
        this.f9704d.a(aVar);
    }

    @Override // com.mobile.auth.d.b.a
    public void a(a aVar) {
        this.f9702b = aVar;
    }
}
