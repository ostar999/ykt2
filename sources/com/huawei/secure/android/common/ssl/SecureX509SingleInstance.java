package com.huawei.secure.android.common.ssl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import com.huawei.secure.android.common.ssl.util.BksUtil;
import com.huawei.secure.android.common.ssl.util.d;
import com.huawei.secure.android.common.ssl.util.g;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/* loaded from: classes4.dex */
public class SecureX509SingleInstance {

    /* renamed from: a, reason: collision with root package name */
    private static final String f8346a = "SecureX509SingleInstance";

    /* renamed from: b, reason: collision with root package name */
    private static volatile SecureX509TrustManager f8347b;

    private SecureX509SingleInstance() {
    }

    @SuppressLint({"NewApi"})
    public static SecureX509TrustManager getInstance(Context context) throws NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (context == null) {
            throw new NullPointerException("context is null");
        }
        com.huawei.secure.android.common.ssl.util.c.a(context);
        if (f8347b == null) {
            synchronized (SecureX509SingleInstance.class) {
                if (f8347b == null) {
                    InputStream filesBksIS = BksUtil.getFilesBksIS(context);
                    if (filesBksIS == null) {
                        g.c(f8346a, "get assets bks");
                        filesBksIS = context.getAssets().open("hmsrootcas.bks");
                    } else {
                        g.c(f8346a, "get files bks");
                    }
                    f8347b = new SecureX509TrustManager(filesBksIS, "");
                    new d().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, context);
                }
            }
        }
        g.a(f8346a, "SecureX509TrustManager getInstance: cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
        return f8347b;
    }

    public static void updateBks(InputStream inputStream) {
        String str = f8346a;
        g.c(str, "update bks");
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (inputStream != null && f8347b != null) {
            f8347b = new SecureX509TrustManager(inputStream, "");
            SecureSSLSocketFactory.a(f8347b);
            SecureApacheSSLSocketFactory.a(f8347b);
        }
        g.c(str, "SecureX509TrustManager update bks cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
    }
}
