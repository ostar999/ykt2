package com.mobile.auth.y;

import com.google.common.base.Ascii;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import kotlin.io.encoding.Base64;
import okio.Utf8;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.eclipse.jetty.http.HttpTokens;

/* loaded from: classes4.dex */
public final class j {

    /* renamed from: a, reason: collision with root package name */
    private static char[] f10601a = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

    /* renamed from: b, reason: collision with root package name */
    private static byte[] f10602b = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, Utf8.REPLACEMENT_BYTE, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, HttpTokens.COLON, HttpTokens.SEMI_COLON, 60, Base64.padSymbol, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, Ascii.DC2, 19, Ascii.DC4, 21, 22, 23, Ascii.CAN, Ascii.EM, -1, -1, -1, -1, -1, -1, Ascii.SUB, Ascii.ESC, Ascii.FS, Ascii.GS, Ascii.RS, Ascii.US, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, -1, -1, -1, -1, -1};

    public static String a(String str) {
        try {
            return str.replaceAll("\\+", "%2B");
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static String a(byte[] bArr) {
        String str;
        try {
            StringBuffer stringBuffer = new StringBuffer();
            int length = bArr.length;
            int i2 = 0;
            while (i2 < length) {
                int i3 = i2 + 1;
                int i4 = bArr[i2] & 255;
                if (i3 == length) {
                    stringBuffer.append(f10601a[i4 >>> 2]);
                    stringBuffer.append(f10601a[(i4 & 3) << 4]);
                    str = "==";
                } else {
                    int i5 = i3 + 1;
                    int i6 = bArr[i3] & 255;
                    if (i5 == length) {
                        stringBuffer.append(f10601a[i4 >>> 2]);
                        stringBuffer.append(f10601a[((i4 & 3) << 4) | ((i6 & 240) >>> 4)]);
                        stringBuffer.append(f10601a[(i6 & 15) << 2]);
                        str = "=";
                    } else {
                        int i7 = i5 + 1;
                        int i8 = bArr[i5] & 255;
                        stringBuffer.append(f10601a[i4 >>> 2]);
                        stringBuffer.append(f10601a[((i4 & 3) << 4) | ((i6 & 240) >>> 4)]);
                        stringBuffer.append(f10601a[((i6 & 15) << 2) | ((i8 & 192) >>> 6)]);
                        stringBuffer.append(f10601a[i8 & 63]);
                        i2 = i7;
                    }
                }
                stringBuffer.append(str);
                break;
            }
            return stringBuffer.toString();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x0076, code lost:
    
        if (r2 == (-1)) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0078, code lost:
    
        r1.write(r2 | ((r5 & 3) << 6));
        r2 = r4;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] b(java.lang.String r8) {
        /*
            byte[] r8 = r8.getBytes()     // Catch: java.lang.Throwable -> L87
            int r0 = r8.length     // Catch: java.lang.Throwable -> L87
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> L87
            r1.<init>(r0)     // Catch: java.lang.Throwable -> L87
            r2 = 0
        Lb:
            if (r2 >= r0) goto L82
        Ld:
            byte[] r3 = com.mobile.auth.y.j.f10602b     // Catch: java.lang.Throwable -> L87
            int r4 = r2 + 1
            r2 = r8[r2]     // Catch: java.lang.Throwable -> L87
            r2 = r3[r2]     // Catch: java.lang.Throwable -> L87
            r3 = -1
            if (r4 >= r0) goto L1d
            if (r2 == r3) goto L1b
            goto L1d
        L1b:
            r2 = r4
            goto Ld
        L1d:
            if (r2 == r3) goto L82
        L1f:
            byte[] r5 = com.mobile.auth.y.j.f10602b     // Catch: java.lang.Throwable -> L87
            int r6 = r4 + 1
            r4 = r8[r4]     // Catch: java.lang.Throwable -> L87
            r4 = r5[r4]     // Catch: java.lang.Throwable -> L87
            if (r6 >= r0) goto L2e
            if (r4 == r3) goto L2c
            goto L2e
        L2c:
            r4 = r6
            goto L1f
        L2e:
            if (r4 == r3) goto L82
            int r2 = r2 << 2
            r5 = r4 & 48
            int r5 = r5 >>> 4
            r2 = r2 | r5
            r1.write(r2)     // Catch: java.lang.Throwable -> L87
        L3a:
            int r2 = r6 + 1
            r5 = r8[r6]     // Catch: java.lang.Throwable -> L87
            r6 = 61
            if (r5 != r6) goto L47
            byte[] r8 = r1.toByteArray()     // Catch: java.lang.Throwable -> L87
            return r8
        L47:
            byte[] r7 = com.mobile.auth.y.j.f10602b     // Catch: java.lang.Throwable -> L87
            r5 = r7[r5]     // Catch: java.lang.Throwable -> L87
            if (r2 >= r0) goto L52
            if (r5 == r3) goto L50
            goto L52
        L50:
            r6 = r2
            goto L3a
        L52:
            if (r5 == r3) goto L82
            r4 = r4 & 15
            int r4 = r4 << 4
            r7 = r5 & 60
            int r7 = r7 >>> 2
            r4 = r4 | r7
            r1.write(r4)     // Catch: java.lang.Throwable -> L87
        L60:
            int r4 = r2 + 1
            r2 = r8[r2]     // Catch: java.lang.Throwable -> L87
            if (r2 != r6) goto L6b
            byte[] r8 = r1.toByteArray()     // Catch: java.lang.Throwable -> L87
            return r8
        L6b:
            byte[] r7 = com.mobile.auth.y.j.f10602b     // Catch: java.lang.Throwable -> L87
            r2 = r7[r2]     // Catch: java.lang.Throwable -> L87
            if (r4 >= r0) goto L76
            if (r2 == r3) goto L74
            goto L76
        L74:
            r2 = r4
            goto L60
        L76:
            if (r2 == r3) goto L82
            r3 = r5 & 3
            int r3 = r3 << 6
            r2 = r2 | r3
            r1.write(r2)     // Catch: java.lang.Throwable -> L87
            r2 = r4
            goto Lb
        L82:
            byte[] r8 = r1.toByteArray()     // Catch: java.lang.Throwable -> L87
            return r8
        L87:
            r8 = move-exception
            com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r8)
            r8 = 0
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.y.j.b(java.lang.String):byte[]");
    }
}
