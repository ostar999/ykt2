package com.tencent.bugly.proguard;

/* loaded from: classes6.dex */
public final class f {

    /* renamed from: b, reason: collision with root package name */
    private static final char[] f17825b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /* renamed from: a, reason: collision with root package name */
    public static final byte[] f17824a = new byte[0];

    public static String a(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        char[] cArr = new char[bArr.length * 2];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            byte b3 = bArr[i2];
            int i3 = i2 * 2;
            char[] cArr2 = f17825b;
            cArr[i3 + 1] = cArr2[b3 & 15];
            cArr[i3 + 0] = cArr2[((byte) (b3 >>> 4)) & 15];
        }
        return new String(cArr);
    }
}
