package com.umeng.analytics.pro;

/* loaded from: classes6.dex */
public final class ch extends ci {

    /* renamed from: a, reason: collision with root package name */
    private byte[] f22669a;

    /* renamed from: b, reason: collision with root package name */
    private int f22670b;

    /* renamed from: c, reason: collision with root package name */
    private int f22671c;

    public ch() {
    }

    public void a(byte[] bArr) {
        c(bArr, 0, bArr.length);
    }

    @Override // com.umeng.analytics.pro.ci
    public boolean a() {
        return true;
    }

    @Override // com.umeng.analytics.pro.ci
    public void b() throws cj {
    }

    @Override // com.umeng.analytics.pro.ci
    public void b(byte[] bArr, int i2, int i3) throws cj {
        throw new UnsupportedOperationException("No writing allowed!");
    }

    @Override // com.umeng.analytics.pro.ci
    public void c() {
    }

    public void c(byte[] bArr, int i2, int i3) {
        this.f22669a = bArr;
        this.f22670b = i2;
        this.f22671c = i2 + i3;
    }

    public void e() {
        this.f22669a = null;
    }

    @Override // com.umeng.analytics.pro.ci
    public byte[] f() {
        return this.f22669a;
    }

    @Override // com.umeng.analytics.pro.ci
    public int g() {
        return this.f22670b;
    }

    @Override // com.umeng.analytics.pro.ci
    public int h() {
        return this.f22671c - this.f22670b;
    }

    public ch(byte[] bArr) {
        a(bArr);
    }

    @Override // com.umeng.analytics.pro.ci
    public int a(byte[] bArr, int i2, int i3) throws cj {
        int iH = h();
        if (i3 > iH) {
            i3 = iH;
        }
        if (i3 > 0) {
            System.arraycopy(this.f22669a, this.f22670b, bArr, i2, i3);
            a(i3);
        }
        return i3;
    }

    public ch(byte[] bArr, int i2, int i3) {
        c(bArr, i2, i3);
    }

    @Override // com.umeng.analytics.pro.ci
    public void a(int i2) {
        this.f22670b += i2;
    }
}
