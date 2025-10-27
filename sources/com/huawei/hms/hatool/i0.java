package com.huawei.hms.hatool;

import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class i0 implements n0 {

    /* renamed from: a, reason: collision with root package name */
    public byte[] f7746a;

    /* renamed from: b, reason: collision with root package name */
    public String f7747b;

    /* renamed from: c, reason: collision with root package name */
    public String f7748c;

    /* renamed from: d, reason: collision with root package name */
    public String f7749d;

    /* renamed from: e, reason: collision with root package name */
    public String f7750e;

    /* renamed from: f, reason: collision with root package name */
    public List<q> f7751f;

    public i0(byte[] bArr, String str, String str2, String str3, String str4, List<q> list) {
        this.f7746a = (byte[]) bArr.clone();
        this.f7747b = str;
        this.f7748c = str2;
        this.f7750e = str3;
        this.f7749d = str4;
        this.f7751f = list;
    }

    public final b0 a(Map<String, String> map) {
        return a0.a(this.f7747b, this.f7746a, map);
    }

    public final Map<String, String> a() {
        return d1.b(this.f7748c, this.f7750e, this.f7749d);
    }

    public final void b() {
        o0.c().a(new l0(this.f7751f, this.f7748c, this.f7749d, this.f7750e));
    }

    @Override // java.lang.Runnable
    public void run() {
        y.c("hmsSdk", "send data running");
        int iB = a(a()).b();
        if (iB != 200) {
            b();
            return;
        }
        y.b("hmsSdk", "events PostRequest sendevent TYPE : %s, TAG : %s, resultCode: %d ,reqID:" + this.f7749d, this.f7750e, this.f7748c, Integer.valueOf(iB));
    }
}
