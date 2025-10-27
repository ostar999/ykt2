package com.umeng.analytics.pro;

/* loaded from: classes6.dex */
public class at {

    /* renamed from: a, reason: collision with root package name */
    private short[] f22529a;

    /* renamed from: b, reason: collision with root package name */
    private int f22530b = -1;

    public at(int i2) {
        this.f22529a = new short[i2];
    }

    private void d() {
        short[] sArr = this.f22529a;
        short[] sArr2 = new short[sArr.length * 2];
        System.arraycopy(sArr, 0, sArr2, 0, sArr.length);
        this.f22529a = sArr2;
    }

    public short a() {
        short[] sArr = this.f22529a;
        int i2 = this.f22530b;
        this.f22530b = i2 - 1;
        return sArr[i2];
    }

    public short b() {
        return this.f22529a[this.f22530b];
    }

    public void c() {
        this.f22530b = -1;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<ShortStack vector:[");
        for (int i2 = 0; i2 < this.f22529a.length; i2++) {
            if (i2 != 0) {
                sb.append(" ");
            }
            if (i2 == this.f22530b) {
                sb.append(">>");
            }
            sb.append((int) this.f22529a[i2]);
            if (i2 == this.f22530b) {
                sb.append("<<");
            }
        }
        sb.append("]>");
        return sb.toString();
    }

    public void a(short s2) {
        if (this.f22529a.length == this.f22530b + 1) {
            d();
        }
        short[] sArr = this.f22529a;
        int i2 = this.f22530b + 1;
        this.f22530b = i2;
        sArr[i2] = s2;
    }
}
