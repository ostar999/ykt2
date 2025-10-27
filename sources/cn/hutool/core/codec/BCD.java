package cn.hutool.core.codec;

import cn.hutool.core.lang.Assert;

@Deprecated
/* loaded from: classes.dex */
public class BCD {
    /* JADX WARN: Removed duplicated region for block: B:6:0x0008  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static byte ascToBcd(byte r3) {
        /*
            r0 = 48
            if (r3 < r0) goto Lb
            r1 = 57
            if (r3 > r1) goto Lb
        L8:
            int r3 = r3 - r0
        L9:
            byte r3 = (byte) r3
            goto L20
        Lb:
            r1 = 65
            if (r3 < r1) goto L17
            r2 = 70
            if (r3 > r2) goto L17
        L13:
            int r3 = r3 - r1
            int r3 = r3 + 10
            goto L9
        L17:
            r1 = 97
            if (r3 < r1) goto L8
            r2 = 102(0x66, float:1.43E-43)
            if (r3 > r2) goto L8
            goto L13
        L20:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.hutool.core.codec.BCD.ascToBcd(byte):byte");
    }

    public static byte[] ascToBcd(byte[] bArr) throws IllegalArgumentException {
        Assert.notNull(bArr, "Ascii must be not null!", new Object[0]);
        return ascToBcd(bArr, bArr.length);
    }

    public static String bcdToStr(byte[] bArr) throws IllegalArgumentException {
        Assert.notNull(bArr, "Bcd bytes must be not null!", new Object[0]);
        char[] cArr = new char[bArr.length * 2];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            byte b3 = bArr[i2];
            char c3 = (char) (((b3 & 240) >> 4) & 15);
            int i3 = i2 * 2;
            cArr[i3] = (char) (c3 > '\t' ? (c3 + 'A') - 10 : c3 + '0');
            char c4 = (char) (b3 & 15);
            cArr[i3 + 1] = (char) (c4 > '\t' ? (c4 + 'A') - 10 : c4 + '0');
        }
        return new String(cArr);
    }

    public static byte[] strToBcd(String str) throws IllegalArgumentException {
        Assert.notNull(str, "ASCII must not be null!", new Object[0]);
        int length = str.length();
        if (length % 2 != 0) {
            str = "0" + str;
            length = str.length();
        }
        if (length >= 2) {
            length >>= 1;
        }
        byte[] bArr = new byte[length];
        byte[] bytes = str.getBytes();
        for (int i2 = 0; i2 < str.length() / 2; i2++) {
            int i3 = i2 * 2;
            byte b3 = bytes[i3];
            int i4 = (b3 < 48 || b3 > 57) ? ((b3 < 97 || b3 > 122) ? b3 - 65 : b3 - 97) + 10 : b3 - 48;
            byte b4 = bytes[i3 + 1];
            bArr[i2] = (byte) ((i4 << 4) + ((b4 < 48 || b4 > 57) ? ((b4 < 97 || b4 > 122) ? b4 - 65 : b4 - 97) + 10 : b4 - 48));
        }
        return bArr;
    }

    public static byte[] ascToBcd(byte[] bArr, int i2) throws IllegalArgumentException {
        byte bAscToBcd;
        Assert.notNull(bArr, "Ascii must be not null!", new Object[0]);
        byte[] bArr2 = new byte[i2 / 2];
        int i3 = 0;
        for (int i4 = 0; i4 < (i2 + 1) / 2; i4++) {
            int i5 = i3 + 1;
            bArr2[i4] = ascToBcd(bArr[i3]);
            if (i5 >= i2) {
                i3 = i5;
                bAscToBcd = 0;
            } else {
                i3 = i5 + 1;
                bAscToBcd = ascToBcd(bArr[i5]);
            }
            bArr2[i4] = (byte) (bAscToBcd + (bArr2[i4] << 4));
        }
        return bArr2;
    }
}
