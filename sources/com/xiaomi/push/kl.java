package com.xiaomi.push;

/* loaded from: classes6.dex */
public abstract class kl {
    public int a() {
        return 0;
    }

    public abstract int a(byte[] bArr, int i2, int i3);

    public void a(int i2) {
    }

    /* renamed from: a */
    public abstract void mo673a(byte[] bArr, int i2, int i3);

    /* renamed from: a */
    public byte[] mo674a() {
        return null;
    }

    public int b() {
        return -1;
    }

    public int b(byte[] bArr, int i2, int i3) throws km {
        int i4 = 0;
        while (i4 < i3) {
            int iA = a(bArr, i2 + i4, i3 - i4);
            if (iA <= 0) {
                throw new km("Cannot read. Remote side has closed. Tried to read " + i3 + " bytes, but only got " + i4 + " bytes.");
            }
            i4 += iA;
        }
        return i4;
    }
}
