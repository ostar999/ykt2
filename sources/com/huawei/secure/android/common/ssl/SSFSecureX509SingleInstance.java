package com.huawei.secure.android.common.ssl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import com.huawei.secure.android.common.ssl.util.BksUtil;
import com.huawei.secure.android.common.ssl.util.e;
import com.huawei.secure.android.common.ssl.util.g;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/* loaded from: classes4.dex */
public class SSFSecureX509SingleInstance {

    /* renamed from: a, reason: collision with root package name */
    private static final String f8306a = "SSFSecureX509SingleInstance";

    /* renamed from: b, reason: collision with root package name */
    private static volatile SecureX509TrustManager f8307b;

    private SSFSecureX509SingleInstance() {
    }

    @SuppressLint({"NewApi"})
    public static SecureX509TrustManager getInstance(Context context) throws NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException {
        if (context == null) {
            throw new NullPointerException("context is null");
        }
        com.huawei.secure.android.common.ssl.util.c.a(context);
        if (f8307b == null) {
            synchronized (SSFSecureX509SingleInstance.class) {
                if (f8307b == null) {
                    InputStream filesBksIS = BksUtil.getFilesBksIS(context);
                    if (filesBksIS == null) {
                        g.c(f8306a, "get assets bks");
                        filesBksIS = context.getAssets().open("hmsrootcas.bks");
                    } else {
                        g.c(f8306a, "get files bks");
                    }
                    f8307b = new SecureX509TrustManager(filesBksIS, "", true);
                    new e().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, context);
                }
            }
        }
        return f8307b;
    }

    public static void updateBks(InputStream inputStream) {
        String str = f8306a;
        g.c(str, "update bks");
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (inputStream != null && f8307b != null) {
            f8307b = new SecureX509TrustManager(inputStream, "", true);
            g.a(str, "updateBks: new SecureX509TrustManager cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
            SSFCompatiableSystemCA.a(f8307b);
            SASFCompatiableSystemCA.a(f8307b);
        }
        g.a(str, "update bks cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
    }
}
