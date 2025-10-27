package com.alibaba.sdk.android.tbrest.utils;

/* loaded from: classes2.dex */
public class RC4 {
    private static final String RC4_PK = "QrMgt8GGYI6T52ZY5AnhtxkLzb8egpFn3j5JELI8H6wtACbUnZ5cc3aYTsTRbmkAkRJeYbtx92LPBWm7nBO9UIl7y5i5MQNmUZNf5QENurR5tGyo7yJ2G0MBjWvy6iAtlAbacKP0SwOUeUWx5dsBdyhxa7Id1APtybSdDgicBDuNjI0mlZFUzZSS9dmN8lBD0WTVOMz0pRZbR3cysomRXOO1ghqjJdTcyDIxzpNAEszN8RMGjrzyU7Hjbmwi6YNK";

    public static class RC4Key {
        int[] state;

        /* renamed from: x, reason: collision with root package name */
        int f2917x;

        /* renamed from: y, reason: collision with root package name */
        int f2918y;

        private RC4Key() {
            this.state = new int[256];
        }
    }

    private static byte[] doRc4(byte[] bArr, RC4Key rC4Key) {
        if (bArr == null || rC4Key == null) {
            return null;
        }
        int i2 = rC4Key.f2917x;
        int i3 = rC4Key.f2918y;
        for (int i4 = 0; i4 < bArr.length; i4++) {
            i2 = (i2 + 1) % 256;
            int[] iArr = rC4Key.state;
            int i5 = iArr[i2];
            i3 = (i3 + i5) % 256;
            iArr[i2] = iArr[i3];
            iArr[i3] = i5;
            int i6 = (iArr[i2] + i5) % 256;
            bArr[i4] = (byte) (iArr[i6] ^ bArr[i4]);
        }
        rC4Key.f2917x = i2;
        rC4Key.f2918y = i3;
        return bArr;
    }

    private static RC4Key prepareKey(String str) {
        if (str == null) {
            return null;
        }
        RC4Key rC4Key = new RC4Key();
        for (int i2 = 0; i2 < 256; i2++) {
            rC4Key.state[i2] = i2;
        }
        rC4Key.f2917x = 0;
        rC4Key.f2918y = 0;
        int length = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < 256; i4++) {
            try {
                char cCharAt = str.charAt(length);
                int[] iArr = rC4Key.state;
                int i5 = iArr[i4];
                i3 = ((cCharAt + i5) + i3) % 256;
                iArr[i4] = iArr[i3];
                iArr[i3] = i5;
                length = (length + 1) % str.length();
            } catch (Exception unused) {
                return null;
            }
        }
        return rC4Key;
    }

    public static byte[] rc4(byte[] bArr) {
        return rc4(bArr, RC4_PK);
    }

    private static byte[] rc4(byte[] bArr, String str) {
        RC4Key rC4KeyPrepareKey;
        if (bArr == null || str == null || (rC4KeyPrepareKey = prepareKey(str)) == null) {
            return null;
        }
        return doRc4(bArr, rC4KeyPrepareKey);
    }
}
