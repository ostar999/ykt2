package com.vivo.push.cache;

import android.content.Context;
import android.text.TextUtils;
import com.vivo.push.util.ContextDelegate;
import com.vivo.push.util.g;
import com.vivo.push.util.p;
import com.vivo.push.util.w;
import com.vivo.push.util.y;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public abstract class c<T> {

    /* renamed from: a, reason: collision with root package name */
    protected static final Object f24299a = new Object();

    /* renamed from: b, reason: collision with root package name */
    protected List<T> f24300b = new ArrayList();

    /* renamed from: c, reason: collision with root package name */
    protected Context f24301c;

    /* renamed from: d, reason: collision with root package name */
    private byte[] f24302d;

    /* renamed from: e, reason: collision with root package name */
    private byte[] f24303e;

    public c(Context context) {
        this.f24301c = ContextDelegate.getContext(context);
        w wVarB = w.b();
        wVarB.a(this.f24301c);
        this.f24302d = wVarB.c();
        this.f24303e = wVarB.d();
        c();
    }

    private String b() {
        return y.b(this.f24301c).a(a(), null);
    }

    private void d(String str) {
        y.b(this.f24301c).b(a(), str);
    }

    public abstract String a();

    public abstract List<T> a(String str);

    public abstract String b(String str) throws Exception;

    public final void c() {
        synchronized (f24299a) {
            g.a(a());
            this.f24300b.clear();
            c(b());
        }
    }

    public final byte[] e() {
        byte[] bArr = this.f24302d;
        return (bArr == null || bArr.length <= 0) ? w.b().c() : bArr;
    }

    public final byte[] f() {
        byte[] bArr = this.f24303e;
        return (bArr == null || bArr.length <= 0) ? w.b().d() : bArr;
    }

    public final void d() {
        synchronized (f24299a) {
            this.f24300b.clear();
            d("");
            p.d("CacheSettings", "clear " + a() + " strApps");
        }
    }

    private void c(String str) {
        if (TextUtils.isEmpty(str)) {
            p.d("CacheSettings", "ClientManager init " + a() + " strApps empty.");
            return;
        }
        if (str.length() > 10000) {
            p.d("CacheSettings", "sync " + a() + " strApps lenght too large");
            d();
            return;
        }
        try {
            p.d("CacheSettings", "ClientManager init " + a() + " strApps : " + str);
            List<T> listA = a(b(str));
            if (listA != null) {
                this.f24300b.addAll(listA);
            }
        } catch (Exception e2) {
            d();
            p.d("CacheSettings", p.a(e2));
        }
    }
}
