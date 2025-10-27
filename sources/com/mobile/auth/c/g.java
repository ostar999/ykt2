package com.mobile.auth.c;

import android.text.TextUtils;
import android.util.Log;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.util.Locale;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes4.dex */
public class g {

    /* renamed from: a, reason: collision with root package name */
    private static final String f9643a = "g";

    /* renamed from: b, reason: collision with root package name */
    private final int[] f9644b = {1732584193, -271733879, -1732584194, 271733878, -1009589776};

    /* renamed from: c, reason: collision with root package name */
    private int[] f9645c = new int[5];

    /* renamed from: d, reason: collision with root package name */
    private int[] f9646d = new int[80];

    private int a(int i2, int i3) {
        return (i2 >>> (32 - i3)) | (i2 << i3);
    }

    private int a(int i2, int i3, int i4) {
        return ((~i2) & i4) | (i3 & i2);
    }

    private int a(byte[] bArr, int i2) {
        try {
            return (bArr[i2 + 3] & 255) | ((bArr[i2] & 255) << 24) | ((bArr[i2 + 1] & 255) << 16) | ((bArr[i2 + 2] & 255) << 8);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1;
            }
        }
    }

    private void a() {
        for (int i2 = 16; i2 <= 79; i2++) {
            try {
                int[] iArr = this.f9646d;
                iArr[i2] = a(((iArr[i2 - 3] ^ iArr[i2 - 8]) ^ iArr[i2 - 14]) ^ iArr[i2 - 16], 1);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                    return;
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                    return;
                }
            }
        }
        int[] iArr2 = new int[5];
        for (int i3 = 0; i3 < 5; i3++) {
            iArr2[i3] = this.f9645c[i3];
        }
        for (int i4 = 0; i4 <= 19; i4++) {
            int iA = a(iArr2[0], 5) + a(iArr2[1], iArr2[2], iArr2[3]) + iArr2[4] + this.f9646d[i4] + 1518500249;
            iArr2[4] = iArr2[3];
            iArr2[3] = iArr2[2];
            iArr2[2] = a(iArr2[1], 30);
            iArr2[1] = iArr2[0];
            iArr2[0] = iA;
        }
        for (int i5 = 20; i5 <= 39; i5++) {
            int iA2 = a(iArr2[0], 5) + b(iArr2[1], iArr2[2], iArr2[3]) + iArr2[4] + this.f9646d[i5] + 1859775393;
            iArr2[4] = iArr2[3];
            iArr2[3] = iArr2[2];
            iArr2[2] = a(iArr2[1], 30);
            iArr2[1] = iArr2[0];
            iArr2[0] = iA2;
        }
        for (int i6 = 40; i6 <= 59; i6++) {
            int iA3 = (((a(iArr2[0], 5) + c(iArr2[1], iArr2[2], iArr2[3])) + iArr2[4]) + this.f9646d[i6]) - 1894007588;
            iArr2[4] = iArr2[3];
            iArr2[3] = iArr2[2];
            iArr2[2] = a(iArr2[1], 30);
            iArr2[1] = iArr2[0];
            iArr2[0] = iA3;
        }
        for (int i7 = 60; i7 <= 79; i7++) {
            int iA4 = (((a(iArr2[0], 5) + b(iArr2[1], iArr2[2], iArr2[3])) + iArr2[4]) + this.f9646d[i7]) - 899497514;
            iArr2[4] = iArr2[3];
            iArr2[3] = iArr2[2];
            iArr2[2] = a(iArr2[1], 30);
            iArr2[1] = iArr2[0];
            iArr2[0] = iA4;
        }
        for (int i8 = 0; i8 < 5; i8++) {
            int[] iArr3 = this.f9645c;
            iArr3[i8] = iArr3[i8] + iArr2[i8];
        }
        int i9 = 0;
        while (true) {
            int[] iArr4 = this.f9646d;
            if (i9 >= iArr4.length) {
                return;
            }
            iArr4[i9] = 0;
            i9++;
        }
    }

    private void a(int i2, byte[] bArr, int i3) {
        try {
            bArr[i3] = (byte) (i2 >>> 24);
            bArr[i3 + 1] = (byte) (i2 >>> 16);
            bArr[i3 + 2] = (byte) (i2 >>> 8);
            bArr[i3 + 3] = (byte) i2;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static byte[] a(String str, String str2) {
        try {
            if (TextUtils.isEmpty(str2)) {
                Log.i(f9643a, "when getHmacSHA1,the key is null");
                return null;
            }
            try {
                byte[] bArr = new byte[64];
                byte[] bArr2 = new byte[64];
                byte[] bArr3 = new byte[64];
                int length = str2.length();
                g gVar = new g();
                if (str2.length() > 64) {
                    byte[] bArrA = gVar.a(q.b(str2));
                    length = bArrA.length;
                    for (int i2 = 0; i2 < length; i2++) {
                        bArr3[i2] = bArrA[i2];
                    }
                } else {
                    byte[] bArrB = q.b(str2);
                    for (int i3 = 0; i3 < bArrB.length; i3++) {
                        bArr3[i3] = bArrB[i3];
                    }
                }
                while (length < 64) {
                    bArr3[length] = 0;
                    length++;
                }
                for (int i4 = 0; i4 < 64; i4++) {
                    bArr[i4] = (byte) (bArr3[i4] ^ TarConstants.LF_FIFO);
                    bArr2[i4] = (byte) (bArr3[i4] ^ 92);
                }
                return gVar.a(a(bArr2, gVar.a(a(bArr, q.b(str)))));
            } catch (Throwable th) {
                Log.w(f9643a, "getHmacSHA1 error", th);
                return null;
            }
        } catch (Throwable th2) {
            try {
                ExceptionProcessor.processException(th2);
                return null;
            } catch (Throwable th3) {
                ExceptionProcessor.processException(th3);
                return null;
            }
        }
    }

    private static byte[] a(byte[] bArr, byte[] bArr2) {
        try {
            byte[] bArr3 = new byte[bArr.length + bArr2.length];
            for (int i2 = 0; i2 < bArr.length; i2++) {
                bArr3[i2] = bArr[i2];
            }
            for (int i3 = 0; i3 < bArr2.length; i3++) {
                bArr3[bArr.length + i3] = bArr2[i3];
            }
            return bArr3;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    private int b(int i2, int i3, int i4) {
        return (i2 ^ i3) ^ i4;
    }

    public static String b(byte[] bArr) {
        try {
            StringBuilder sb = new StringBuilder("");
            if (bArr != null && bArr.length > 0) {
                for (byte b3 : bArr) {
                    String upperCase = Integer.toHexString(b3 & 255).toUpperCase(Locale.CHINA);
                    if (upperCase.length() < 2) {
                        sb.append(0);
                    }
                    sb.append(upperCase);
                }
                return sb.toString();
            }
            return null;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    private int c(int i2, int i3, int i4) {
        return (i2 & i4) | (i2 & i3) | (i3 & i4);
    }

    private int c(byte[] bArr) {
        try {
            int[] iArr = this.f9644b;
            System.arraycopy(iArr, 0, this.f9645c, 0, iArr.length);
            byte[] bArrD = d(bArr);
            int length = bArrD.length / 64;
            for (int i2 = 0; i2 < length; i2++) {
                for (int i3 = 0; i3 < 16; i3++) {
                    this.f9646d[i3] = a(bArrD, (i2 * 64) + (i3 * 4));
                }
                a();
            }
            return 20;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1;
            }
        }
    }

    private byte[] d(byte[] bArr) {
        int i2;
        int i3;
        try {
            int length = bArr.length;
            int i4 = length % 64;
            if (i4 < 56) {
                i2 = 55 - i4;
                i3 = (length - i4) + 64;
            } else if (i4 == 56) {
                i3 = length + 8 + 64;
                i2 = 63;
            } else {
                i2 = (63 - i4) + 56;
                i3 = ((length + 64) - i4) + 64;
            }
            byte[] bArr2 = new byte[i3];
            System.arraycopy(bArr, 0, bArr2, 0, length);
            int i5 = length + 1;
            bArr2[length] = -128;
            int i6 = 0;
            while (i6 < i2) {
                bArr2[i5] = 0;
                i6++;
                i5++;
            }
            long j2 = length * 8;
            byte b3 = (byte) (j2 & 255);
            byte b4 = (byte) ((j2 >> 8) & 255);
            byte b5 = (byte) ((j2 >> 16) & 255);
            byte b6 = (byte) ((j2 >> 24) & 255);
            byte b7 = (byte) ((j2 >> 32) & 255);
            byte b8 = (byte) ((j2 >> 40) & 255);
            byte b9 = (byte) (255 & (j2 >> 48));
            byte b10 = (byte) (j2 >> 56);
            int i7 = i5 + 1;
            bArr2[i5] = b10;
            int i8 = i7 + 1;
            bArr2[i7] = b9;
            int i9 = i8 + 1;
            bArr2[i8] = b8;
            int i10 = i9 + 1;
            bArr2[i9] = b7;
            int i11 = i10 + 1;
            bArr2[i10] = b6;
            int i12 = i11 + 1;
            bArr2[i11] = b5;
            bArr2[i12] = b4;
            bArr2[i12 + 1] = b3;
            return bArr2;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public byte[] a(byte[] bArr) {
        try {
            c(bArr);
            byte[] bArr2 = new byte[20];
            int i2 = 0;
            while (true) {
                int[] iArr = this.f9645c;
                if (i2 >= iArr.length) {
                    return bArr2;
                }
                a(iArr[i2], bArr2, i2 * 4);
                i2++;
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }
}
