package com.meizu.cloud.pushsdk.b.g;

/* loaded from: classes4.dex */
final class i {

    /* renamed from: a, reason: collision with root package name */
    final byte[] f9199a;

    /* renamed from: b, reason: collision with root package name */
    int f9200b;

    /* renamed from: c, reason: collision with root package name */
    int f9201c;

    /* renamed from: d, reason: collision with root package name */
    boolean f9202d;

    /* renamed from: e, reason: collision with root package name */
    boolean f9203e;

    /* renamed from: f, reason: collision with root package name */
    i f9204f;

    /* renamed from: g, reason: collision with root package name */
    i f9205g;

    public i() {
        this.f9199a = new byte[2048];
        this.f9203e = true;
        this.f9202d = false;
    }

    public i(i iVar) {
        this(iVar.f9199a, iVar.f9200b, iVar.f9201c);
        iVar.f9202d = true;
    }

    public i(byte[] bArr, int i2, int i3) {
        this.f9199a = bArr;
        this.f9200b = i2;
        this.f9201c = i3;
        this.f9203e = false;
        this.f9202d = true;
    }

    public i a() {
        i iVar = this.f9204f;
        i iVar2 = iVar != this ? iVar : null;
        i iVar3 = this.f9205g;
        iVar3.f9204f = iVar;
        this.f9204f.f9205g = iVar3;
        this.f9204f = null;
        this.f9205g = null;
        return iVar2;
    }

    public i a(int i2) {
        if (i2 <= 0 || i2 > this.f9201c - this.f9200b) {
            throw new IllegalArgumentException();
        }
        i iVar = new i(this);
        iVar.f9201c = iVar.f9200b + i2;
        this.f9200b += i2;
        this.f9205g.a(iVar);
        return iVar;
    }

    public i a(i iVar) {
        iVar.f9205g = this;
        iVar.f9204f = this.f9204f;
        this.f9204f.f9205g = iVar;
        this.f9204f = iVar;
        return iVar;
    }

    public void a(i iVar, int i2) {
        if (!iVar.f9203e) {
            throw new IllegalArgumentException();
        }
        int i3 = iVar.f9201c;
        if (i3 + i2 > 2048) {
            if (iVar.f9202d) {
                throw new IllegalArgumentException();
            }
            int i4 = iVar.f9200b;
            if ((i3 + i2) - i4 > 2048) {
                throw new IllegalArgumentException();
            }
            byte[] bArr = iVar.f9199a;
            System.arraycopy(bArr, i4, bArr, 0, i3 - i4);
            iVar.f9201c -= iVar.f9200b;
            iVar.f9200b = 0;
        }
        System.arraycopy(this.f9199a, this.f9200b, iVar.f9199a, iVar.f9201c, i2);
        iVar.f9201c += i2;
        this.f9200b += i2;
    }

    public void b() {
        i iVar = this.f9205g;
        if (iVar == this) {
            throw new IllegalStateException();
        }
        if (iVar.f9203e) {
            int i2 = this.f9201c - this.f9200b;
            if (i2 > (2048 - iVar.f9201c) + (iVar.f9202d ? 0 : iVar.f9200b)) {
                return;
            }
            a(iVar, i2);
            a();
            j.a(this);
        }
    }
}
