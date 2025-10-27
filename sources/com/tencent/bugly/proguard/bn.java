package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;

/* loaded from: classes6.dex */
public final class bn extends m implements Cloneable {

    /* renamed from: d, reason: collision with root package name */
    static byte[] f17711d;

    /* renamed from: a, reason: collision with root package name */
    public byte f17712a;

    /* renamed from: b, reason: collision with root package name */
    public String f17713b;

    /* renamed from: c, reason: collision with root package name */
    public byte[] f17714c;

    public bn() {
        this.f17712a = (byte) 0;
        this.f17713b = "";
        this.f17714c = null;
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(l lVar) throws UnsupportedEncodingException {
        lVar.a(this.f17712a, 0);
        lVar.a(this.f17713b, 1);
        byte[] bArr = this.f17714c;
        if (bArr != null) {
            lVar.a(bArr, 2);
        }
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(StringBuilder sb, int i2) {
    }

    public bn(byte b3, String str, byte[] bArr) {
        this.f17712a = b3;
        this.f17713b = str;
        this.f17714c = bArr;
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(k kVar) {
        this.f17712a = kVar.a(this.f17712a, 0, true);
        this.f17713b = kVar.b(1, true);
        if (f17711d == null) {
            f17711d = new byte[]{0};
        }
        this.f17714c = kVar.c(2, false);
    }
}
