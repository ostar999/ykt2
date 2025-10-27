package com.tencent.open.utils;

import androidx.core.view.MotionEventCompat;

/* loaded from: classes6.dex */
public final class m implements Cloneable {

    /* renamed from: a, reason: collision with root package name */
    private int f20695a;

    public m(byte[] bArr) {
        this(bArr, 0);
    }

    public byte[] a() {
        int i2 = this.f20695a;
        return new byte[]{(byte) (i2 & 255), (byte) ((i2 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8)};
    }

    public int b() {
        return this.f20695a;
    }

    public boolean equals(Object obj) {
        return obj != null && (obj instanceof m) && this.f20695a == ((m) obj).b();
    }

    public int hashCode() {
        return this.f20695a;
    }

    public m(byte[] bArr, int i2) {
        this.f20695a = ((bArr[i2 + 1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) + (bArr[i2] & 255);
    }

    public m(int i2) {
        this.f20695a = i2;
    }
}
