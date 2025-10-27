package com.huawei.hms.hatool;

/* loaded from: classes4.dex */
public class p0 {

    /* renamed from: a, reason: collision with root package name */
    public byte[] f7850a;

    /* renamed from: b, reason: collision with root package name */
    public int f7851b = 0;

    public p0(int i2) {
        this.f7850a = null;
        this.f7850a = new byte[i2];
    }

    public void a(byte[] bArr, int i2) {
        if (i2 <= 0) {
            return;
        }
        byte[] bArr2 = this.f7850a;
        int length = bArr2.length;
        int i3 = this.f7851b;
        if (length - i3 >= i2) {
            System.arraycopy(bArr, 0, bArr2, i3, i2);
        } else {
            byte[] bArr3 = new byte[(bArr2.length + i2) << 1];
            System.arraycopy(bArr2, 0, bArr3, 0, i3);
            System.arraycopy(bArr, 0, bArr3, this.f7851b, i2);
            this.f7850a = bArr3;
        }
        this.f7851b += i2;
    }

    public byte[] a() {
        int i2 = this.f7851b;
        if (i2 <= 0) {
            return new byte[0];
        }
        byte[] bArr = new byte[i2];
        System.arraycopy(this.f7850a, 0, bArr, 0, i2);
        return bArr;
    }

    public int b() {
        return this.f7851b;
    }
}
