package com.xiaomi.push;

/* loaded from: classes6.dex */
public final class kk extends kl {

    /* renamed from: a, reason: collision with root package name */
    private int f25521a;

    /* renamed from: a, reason: collision with other field name */
    private byte[] f936a;

    /* renamed from: b, reason: collision with root package name */
    private int f25522b;

    @Override // com.xiaomi.push.kl
    public int a() {
        return this.f25521a;
    }

    @Override // com.xiaomi.push.kl
    public int a(byte[] bArr, int i2, int i3) {
        int iB = b();
        if (i3 > iB) {
            i3 = iB;
        }
        if (i3 > 0) {
            System.arraycopy(this.f936a, this.f25521a, bArr, i2, i3);
            a(i3);
        }
        return i3;
    }

    @Override // com.xiaomi.push.kl
    public void a(int i2) {
        this.f25521a += i2;
    }

    public void a(byte[] bArr) {
        b(bArr, 0, bArr.length);
    }

    @Override // com.xiaomi.push.kl
    /* renamed from: a */
    public void mo673a(byte[] bArr, int i2, int i3) {
        throw new UnsupportedOperationException("No writing allowed!");
    }

    @Override // com.xiaomi.push.kl
    /* renamed from: a, reason: collision with other method in class */
    public byte[] mo674a() {
        return this.f936a;
    }

    @Override // com.xiaomi.push.kl
    public int b() {
        return this.f25522b - this.f25521a;
    }

    public void b(byte[] bArr, int i2, int i3) {
        this.f936a = bArr;
        this.f25521a = i2;
        this.f25522b = i2 + i3;
    }
}
