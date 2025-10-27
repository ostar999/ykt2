package com.xiaomi.push;

import java.io.IOException;

/* loaded from: classes6.dex */
public abstract class e {
    public abstract int a();

    public abstract e a(b bVar);

    public e a(byte[] bArr) {
        return a(bArr, 0, bArr.length);
    }

    public e a(byte[] bArr, int i2, int i3) throws d {
        try {
            b bVarA = b.a(bArr, i2, i3);
            a(bVarA);
            bVarA.m218a(0);
            return this;
        } catch (d e2) {
            throw e2;
        } catch (IOException unused) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).");
        }
    }

    public abstract void a(c cVar);

    /* renamed from: a, reason: collision with other method in class */
    public void m333a(byte[] bArr, int i2, int i3) {
        try {
            c cVarA = c.a(bArr, i2, i3);
            a(cVarA);
            cVarA.b();
        } catch (IOException unused) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).");
        }
    }

    public boolean a(b bVar, int i2) {
        return bVar.m220a(i2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public byte[] m334a() {
        int iB = b();
        byte[] bArr = new byte[iB];
        m333a(bArr, 0, iB);
        return bArr;
    }

    public abstract int b();
}
