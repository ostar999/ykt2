package com.huawei.hms.base.log;

import android.content.Context;
import android.util.Log;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: b, reason: collision with root package name */
    public String f7534b;

    /* renamed from: a, reason: collision with root package name */
    public int f7533a = 4;

    /* renamed from: c, reason: collision with root package name */
    public b f7535c = new e();

    public void a(Context context, int i2, String str) {
        this.f7533a = i2;
        this.f7534b = str;
        this.f7535c.a(context, "HMSCore");
    }

    public void b(int i2, String str, String str2, Throwable th) {
        if (a(i2)) {
            c cVarA = a(i2, str, str2, th);
            String str3 = cVarA.c() + cVarA.a();
            this.f7535c.a(str3, i2, str, str2 + '\n' + Log.getStackTraceString(th));
        }
    }

    public b a() {
        return this.f7535c;
    }

    public void a(b bVar) {
        this.f7535c = bVar;
    }

    public boolean a(int i2) {
        return i2 >= this.f7533a;
    }

    public void a(int i2, String str, String str2) {
        if (a(i2)) {
            c cVarA = a(i2, str, str2, null);
            this.f7535c.a(cVarA.c() + cVarA.a(), i2, str, str2);
        }
    }

    public void a(String str, String str2) {
        c cVarA = a(4, str, str2, null);
        this.f7535c.a(cVarA.c() + '\n' + cVarA.a(), 4, str, str2);
    }

    public final c a(int i2, String str, String str2, Throwable th) {
        c cVar = new c(8, this.f7534b, i2, str);
        cVar.a((c) str2);
        cVar.a(th);
        return cVar;
    }
}
