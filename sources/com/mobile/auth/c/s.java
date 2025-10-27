package com.mobile.auth.c;

import com.google.common.primitives.SignedBytes;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.nio.charset.Charset;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes4.dex */
public class s {

    /* renamed from: a, reason: collision with root package name */
    private static final String f9677a = "s";

    /* renamed from: b, reason: collision with root package name */
    private static byte[] f9678b = {68, SignedBytes.MAX_POWER_OF_TWO, 94, TarConstants.LF_LINK, 69, 35, TarConstants.LF_SYMLINK, TarConstants.LF_GNUTYPE_SPARSE};

    /* renamed from: c, reason: collision with root package name */
    private static final Charset f9679c = Charset.forName("UTF-8");

    public static String a(byte[] bArr) {
        try {
            int length = bArr.length;
            byte[] bArr2 = new byte[length];
            for (int i2 = 0; i2 < length; i2++) {
                bArr2[i2] = bArr[i2];
                for (byte b3 : f9678b) {
                    bArr2[i2] = (byte) (b3 ^ bArr2[i2]);
                }
            }
            return new String(bArr2);
        } catch (Throwable th) {
            try {
                th.printStackTrace();
                return "";
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
    }
}
