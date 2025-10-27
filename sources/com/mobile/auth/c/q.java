package com.mobile.auth.c;

import android.util.Log;
import androidx.core.view.InputDeviceCompat;
import com.mobile.auth.gatewayauth.ExceptionProcessor;

/* loaded from: classes4.dex */
public class q {

    /* renamed from: a, reason: collision with root package name */
    private static final String f9670a = "q";

    /* renamed from: b, reason: collision with root package name */
    private static final char[] f9671b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /* renamed from: c, reason: collision with root package name */
    private static final char[] f9672c = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /* renamed from: d, reason: collision with root package name */
    private static final char[] f9673d = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String a(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        try {
            if (bArr.length == 0) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; i2 < bArr.length; i2++) {
                char[] cArr = f9673d;
                sb.append(cArr[(bArr[i2] >> 4) & 15]);
                sb.append(cArr[bArr[i2] & 15]);
            }
            return sb.toString();
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

    public static byte[] a(String str) {
        if (str == null) {
            return null;
        }
        try {
            char[] charArray = str.toCharArray();
            int length = charArray.length / 2;
            byte[] bArr = new byte[length];
            for (int i2 = 0; i2 < length; i2++) {
                int i3 = i2 * 2;
                int iDigit = Character.digit(charArray[i3 + 1], 16) | (Character.digit(charArray[i3], 16) << 4);
                if (iDigit > 127) {
                    iDigit += InputDeviceCompat.SOURCE_ANY;
                }
                bArr[i2] = (byte) iDigit;
            }
            return bArr;
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

    public static byte[] b(String str) {
        try {
            byte[] bArr = new byte[0];
            try {
                return str.getBytes("UTF-8");
            } catch (Throwable th) {
                Log.w(f9670a, "getBytes error", th);
                return bArr;
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
}
