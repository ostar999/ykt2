package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;

/* loaded from: classes6.dex */
public final class bs extends m implements Cloneable {

    /* renamed from: a, reason: collision with root package name */
    public String f17779a = "";

    /* renamed from: b, reason: collision with root package name */
    public String f17780b = "";

    @Override // com.tencent.bugly.proguard.m
    public final void a(l lVar) throws UnsupportedEncodingException {
        lVar.a(this.f17779a, 0);
        lVar.a(this.f17780b, 1);
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(StringBuilder sb, int i2) {
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(k kVar) {
        this.f17779a = kVar.b(0, true);
        this.f17780b = kVar.b(1, true);
    }
}
