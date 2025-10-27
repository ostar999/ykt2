package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;

/* loaded from: classes6.dex */
public final class bl extends m implements Cloneable {

    /* renamed from: a, reason: collision with root package name */
    public String f17703a = "";

    /* renamed from: b, reason: collision with root package name */
    public String f17704b = "";

    /* renamed from: c, reason: collision with root package name */
    public String f17705c = "";

    /* renamed from: d, reason: collision with root package name */
    public String f17706d = "";

    /* renamed from: e, reason: collision with root package name */
    public String f17707e = "";

    @Override // com.tencent.bugly.proguard.m
    public final void a(l lVar) throws UnsupportedEncodingException {
        lVar.a(this.f17703a, 0);
        String str = this.f17704b;
        if (str != null) {
            lVar.a(str, 1);
        }
        String str2 = this.f17705c;
        if (str2 != null) {
            lVar.a(str2, 2);
        }
        String str3 = this.f17706d;
        if (str3 != null) {
            lVar.a(str3, 3);
        }
        String str4 = this.f17707e;
        if (str4 != null) {
            lVar.a(str4, 4);
        }
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(StringBuilder sb, int i2) {
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(k kVar) {
        this.f17703a = kVar.b(0, true);
        this.f17704b = kVar.b(1, false);
        this.f17705c = kVar.b(2, false);
        this.f17706d = kVar.b(3, false);
        this.f17707e = kVar.b(4, false);
    }
}
