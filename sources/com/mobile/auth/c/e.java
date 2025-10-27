package com.mobile.auth.c;

import android.content.Context;
import android.text.TextUtils;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import org.eclipse.jetty.servlet.ServletHandler;

/* loaded from: classes4.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    private static final String f9640a = "com.mobile.auth.c.e";

    /* renamed from: b, reason: collision with root package name */
    private static String f9641b = "";

    public static String a() {
        try {
            String string = UUID.randomUUID().toString();
            try {
                string = UUID.nameUUIDFromBytes((string + System.currentTimeMillis() + Math.random()).getBytes(StandardCharsets.UTF_8)).toString();
            } catch (Throwable th) {
                th.printStackTrace();
            }
            return !TextUtils.isEmpty(string) ? string.replace("-", "") : string;
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

    public static String a(Context context) {
        try {
            if (TextUtils.isEmpty(f9641b)) {
                String strB = b(context);
                f9641b = strB;
                if (TextUtils.isEmpty(strB)) {
                    String strC = c(context);
                    f9641b = strC;
                    a(context, strC);
                }
            }
            return f9641b;
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

    private static String a(String str) {
        try {
            char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            try {
                byte[] bArrA = p.a(str);
                int length = bArrA.length;
                char[] cArr2 = new char[length * 2];
                int i2 = 0;
                for (int i3 = 0; i3 < length / 2; i3++) {
                    byte b3 = bArrA[i3];
                    int i4 = i2 + 1;
                    cArr2[i2] = cArr[(b3 >>> 4) & 15];
                    i2 = i4 + 1;
                    cArr2[i4] = cArr[b3 & 15];
                }
                return new String(new String(cArr2).trim().getBytes(Charset.forName("ISO-8859-1")), Charset.forName("UTF-8"));
            } catch (Exception unused) {
                return null;
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

    private static void a(Context context, String str) {
        try {
            if (!TextUtils.isEmpty(str) && context != null) {
                d.a(context, "key_d_i_u", str);
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private static String b(Context context) {
        try {
            return d.b(context, "key_d_i_u", "");
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

    private static String c(Context context) {
        try {
            String string = UUID.randomUUID().toString();
            return TextUtils.isEmpty(string) ? ServletHandler.__DEFAULT_SERVLET : a(string + ServletHandler.__DEFAULT_SERVLET);
        } catch (Throwable th) {
            try {
                th.printStackTrace();
                return ServletHandler.__DEFAULT_SERVLET;
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
