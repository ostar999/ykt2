package com.xiaomi.push;

import java.io.IOException;

/* loaded from: classes6.dex */
public class kj extends kl {

    /* renamed from: a, reason: collision with root package name */
    private int f25520a;

    /* renamed from: a, reason: collision with other field name */
    private jt f935a;

    public kj(int i2) {
        this.f935a = new jt(i2);
    }

    @Override // com.xiaomi.push.kl
    public int a(byte[] bArr, int i2, int i3) {
        byte[] bArrM658a = this.f935a.m658a();
        if (i3 > this.f935a.a() - this.f25520a) {
            i3 = this.f935a.a() - this.f25520a;
        }
        if (i3 > 0) {
            System.arraycopy(bArrM658a, this.f25520a, bArr, i2, i3);
            this.f25520a += i3;
        }
        return i3;
    }

    @Override // com.xiaomi.push.kl
    /* renamed from: a */
    public void mo673a(byte[] bArr, int i2, int i3) throws IOException {
        this.f935a.write(bArr, i2, i3);
    }

    public int a_() {
        return this.f935a.size();
    }
}
