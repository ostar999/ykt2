package com.vivo.push.b;

import android.content.Context;
import android.text.TextUtils;

/* loaded from: classes6.dex */
public class c extends com.vivo.push.o {

    /* renamed from: a, reason: collision with root package name */
    private String f24245a;

    /* renamed from: b, reason: collision with root package name */
    private String f24246b;

    /* renamed from: c, reason: collision with root package name */
    private long f24247c;

    /* renamed from: d, reason: collision with root package name */
    private int f24248d;

    /* renamed from: e, reason: collision with root package name */
    private int f24249e;

    /* renamed from: f, reason: collision with root package name */
    private String f24250f;

    public c(int i2, String str) {
        super(i2);
        this.f24247c = -1L;
        this.f24248d = -1;
        this.f24245a = null;
        this.f24246b = str;
    }

    public final void a(int i2) {
        this.f24249e = i2;
    }

    public final void b(String str) {
        this.f24245a = str;
    }

    @Override // com.vivo.push.o
    public void c(com.vivo.push.a aVar) {
        aVar.a("req_id", this.f24245a);
        aVar.a("package_name", this.f24246b);
        aVar.a("sdk_version", 323L);
        aVar.a("PUSH_APP_STATUS", this.f24248d);
        if (TextUtils.isEmpty(this.f24250f)) {
            return;
        }
        aVar.a("BaseAppCommand.EXTRA__HYBRIDVERSION", this.f24250f);
    }

    @Override // com.vivo.push.o
    public void d(com.vivo.push.a aVar) {
        this.f24245a = aVar.a("req_id");
        this.f24246b = aVar.a("package_name");
        this.f24247c = aVar.b("sdk_version", 0L);
        this.f24248d = aVar.b("PUSH_APP_STATUS", 0);
        this.f24250f = aVar.a("BaseAppCommand.EXTRA__HYBRIDVERSION");
    }

    public final int f() {
        return this.f24249e;
    }

    public final void g() {
        this.f24250f = null;
    }

    public final String h() {
        return this.f24245a;
    }

    @Override // com.vivo.push.o
    public String toString() {
        return "BaseAppCommand";
    }

    public final int a(Context context) {
        if (this.f24248d == -1) {
            String strA = this.f24246b;
            if (TextUtils.isEmpty(strA)) {
                com.vivo.push.util.p.a("BaseAppCommand", "pkg name is null");
                strA = a();
                if (TextUtils.isEmpty(strA)) {
                    com.vivo.push.util.p.a("BaseAppCommand", "src is null");
                    return -1;
                }
            }
            this.f24248d = com.vivo.push.util.t.b(context, strA);
            if (!TextUtils.isEmpty(this.f24250f)) {
                this.f24248d = 2;
            }
        }
        return this.f24248d;
    }
}
