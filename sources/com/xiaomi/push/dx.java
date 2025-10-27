package com.xiaomi.push;

/* loaded from: classes6.dex */
public class dx {
    private static void a(byte[] bArr) {
        if (bArr.length >= 2) {
            bArr[0] = 99;
            bArr[1] = 100;
        }
    }

    public static byte[] a(String str, byte[] bArr) {
        byte[] bArrA = av.a(str);
        try {
            a(bArrA);
            return i.a(bArrA, bArr);
        } catch (Exception unused) {
            return null;
        }
    }

    public static byte[] b(String str, byte[] bArr) {
        byte[] bArrA = av.a(str);
        try {
            a(bArrA);
            return i.b(bArrA, bArr);
        } catch (Exception unused) {
            return null;
        }
    }
}
