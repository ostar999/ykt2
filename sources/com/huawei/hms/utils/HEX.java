package com.huawei.hms.utils;

/* loaded from: classes4.dex */
public final class HEX {

    /* renamed from: a, reason: collision with root package name */
    public static final char[] f8122a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /* renamed from: b, reason: collision with root package name */
    public static final char[] f8123b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static char[] a(byte[] bArr, char[] cArr) {
        char[] cArr2 = new char[bArr.length << 1];
        int i2 = 0;
        for (byte b3 : bArr) {
            int i3 = i2 + 1;
            cArr2[i2] = cArr[(b3 & 240) >>> 4];
            i2 = i3 + 1;
            cArr2[i3] = cArr[b3 & 15];
        }
        return cArr2;
    }

    public static char[] encodeHex(byte[] bArr) {
        return encodeHex(bArr, false);
    }

    public static String encodeHexString(byte[] bArr, boolean z2) {
        return new String(encodeHex(bArr, z2));
    }

    public static char[] encodeHex(byte[] bArr, boolean z2) {
        return a(bArr, z2 ? f8123b : f8122a);
    }
}
