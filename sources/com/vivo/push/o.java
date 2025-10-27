package com.vivo.push;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

/* loaded from: classes6.dex */
public abstract class o {

    /* renamed from: a, reason: collision with root package name */
    private int f24402a;

    /* renamed from: b, reason: collision with root package name */
    private String f24403b;

    public o(int i2) {
        this.f24402a = -1;
        if (i2 < 0) {
            throw new IllegalArgumentException("PushCommand: the value of command must > 0.");
        }
        this.f24402a = i2;
    }

    private void e(a aVar) {
        aVar.a(com.heytap.mcssdk.constant.b.f7200y, this.f24402a);
        aVar.a("client_pkgname", this.f24403b);
        c(aVar);
    }

    public final String a() {
        return this.f24403b;
    }

    public final int b() {
        return this.f24402a;
    }

    public abstract void c(a aVar);

    public boolean c() {
        return false;
    }

    public abstract void d(a aVar);

    public String toString() {
        return getClass().getSimpleName();
    }

    public final void a(String str) {
        this.f24403b = str;
    }

    public final void b(Intent intent) {
        a aVarA = a.a(intent);
        if (aVarA == null) {
            com.vivo.push.util.p.b("PushCommand", "bundleWapper is null");
            return;
        }
        aVarA.a("method", this.f24402a);
        e(aVarA);
        Bundle bundleB = aVarA.b();
        if (bundleB != null) {
            intent.putExtras(bundleB);
        }
    }

    public final void a(Intent intent) {
        a aVarA = a.a(intent);
        if (aVarA == null) {
            com.vivo.push.util.p.b("PushCommand", "bundleWapper is null");
            return;
        }
        a(aVarA);
        Bundle bundleB = aVarA.b();
        if (bundleB != null) {
            intent.putExtras(bundleB);
        }
    }

    public final void a(a aVar) {
        String strA = p.a(this.f24402a);
        if (strA == null) {
            strA = "";
        }
        aVar.a("method", strA);
        e(aVar);
    }

    public final void b(a aVar) {
        String strA = aVar.a();
        if (!TextUtils.isEmpty(strA)) {
            this.f24403b = strA;
        } else {
            this.f24403b = aVar.a("client_pkgname");
        }
        d(aVar);
    }
}
