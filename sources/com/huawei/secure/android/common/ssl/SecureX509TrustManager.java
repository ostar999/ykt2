package com.huawei.secure.android.common.ssl;

import android.content.Context;
import com.huawei.secure.android.common.ssl.util.BksUtil;
import com.huawei.secure.android.common.ssl.util.f;
import com.huawei.secure.android.common.ssl.util.g;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/* loaded from: classes4.dex */
public class SecureX509TrustManager implements X509TrustManager {

    /* renamed from: c, reason: collision with root package name */
    private static final String f8348c = "SecureX509TrustManager";

    /* renamed from: d, reason: collision with root package name */
    public static final String f8349d = "hmsrootcas.bks";

    /* renamed from: e, reason: collision with root package name */
    private static final String f8350e = "";

    /* renamed from: f, reason: collision with root package name */
    private static final String f8351f = "X509";

    /* renamed from: g, reason: collision with root package name */
    private static final String f8352g = "bks";

    /* renamed from: h, reason: collision with root package name */
    private static final String f8353h = "AndroidCAStore";

    /* renamed from: a, reason: collision with root package name */
    protected List<X509TrustManager> f8354a;

    /* renamed from: b, reason: collision with root package name */
    private X509Certificate[] f8355b;

    public SecureX509TrustManager(Context context) throws NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException, IllegalArgumentException {
        this(context, false);
    }

    private void a() throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        g.c(f8348c, "loadSystemCA");
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            KeyStore keyStore = KeyStore.getInstance(f8353h);
            keyStore.load(null, null);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(f8351f);
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            for (TrustManager trustManager : trustManagers) {
                if (trustManager instanceof X509TrustManager) {
                    this.f8354a.add((X509TrustManager) trustManager);
                }
            }
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e2) {
            g.b(f8348c, "loadSystemCA: exception : " + e2.getMessage());
        }
        g.a(f8348c, "loadSystemCA: cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        g.c(f8348c, "checkClientTrusted: ");
        Iterator<X509TrustManager> it = this.f8354a.iterator();
        while (it.hasNext()) {
            try {
                it.next().checkServerTrusted(x509CertificateArr, str);
                return;
            } catch (CertificateException e2) {
                g.b(f8348c, "checkServerTrusted CertificateException" + e2.getMessage());
            }
        }
        throw new CertificateException("checkServerTrusted CertificateException");
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        setChain(x509CertificateArr);
        g.c(f8348c, "checkServerTrusted begin ,server ca chain size is : " + x509CertificateArr.length + " ,auth type is : " + str);
        long jCurrentTimeMillis = System.currentTimeMillis();
        int length = x509CertificateArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            X509Certificate x509Certificate = x509CertificateArr[i2];
            String str2 = f8348c;
            g.a(str2, "server ca chain: getSubjectDN is :" + x509Certificate.getSubjectDN());
            g.a(str2, "IssuerDN :" + x509Certificate.getIssuerDN());
            g.a(str2, "SerialNumber : " + x509Certificate.getSerialNumber());
        }
        int size = this.f8354a.size();
        for (int i3 = 0; i3 < size; i3++) {
            try {
                String str3 = f8348c;
                g.c(str3, "check server i : " + i3);
                X509TrustManager x509TrustManager = this.f8354a.get(i3);
                X509Certificate[] acceptedIssuers = x509TrustManager.getAcceptedIssuers();
                if (acceptedIssuers != null) {
                    g.c(str3, "client root ca size is : " + acceptedIssuers.length);
                    for (int i4 = 0; i4 < acceptedIssuers.length; i4++) {
                        g.a(f8348c, "client root ca getIssuerDN :" + acceptedIssuers[i4].getIssuerDN());
                    }
                }
                x509TrustManager.checkServerTrusted(x509CertificateArr, str);
                g.c(f8348c, "checkServerTrusted succeed ,root ca issuer is : " + x509CertificateArr[x509CertificateArr.length - 1].getIssuerDN());
                return;
            } catch (CertificateException e2) {
                String str4 = f8348c;
                g.b(str4, "checkServerTrusted error :" + e2.getMessage() + " , time : " + i3);
                if (i3 == size - 1) {
                    if (x509CertificateArr.length > 0) {
                        g.b(str4, "root ca issuer : " + x509CertificateArr[x509CertificateArr.length - 1].getIssuerDN());
                    }
                    throw e2;
                }
            }
        }
        g.a(f8348c, "checkServerTrusted: cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
    }

    @Override // javax.net.ssl.X509TrustManager
    public X509Certificate[] getAcceptedIssuers() {
        try {
            ArrayList arrayList = new ArrayList();
            Iterator<X509TrustManager> it = this.f8354a.iterator();
            while (it.hasNext()) {
                arrayList.addAll(Arrays.asList(it.next().getAcceptedIssuers()));
            }
            return (X509Certificate[]) arrayList.toArray(new X509Certificate[arrayList.size()]);
        } catch (Exception e2) {
            g.b(f8348c, "getAcceptedIssuers exception : " + e2.getMessage());
            return new X509Certificate[0];
        }
    }

    public X509Certificate[] getChain() {
        return this.f8355b;
    }

    public List<X509TrustManager> getX509TrustManagers() {
        return this.f8354a;
    }

    public void setChain(X509Certificate[] x509CertificateArr) {
        this.f8355b = x509CertificateArr;
    }

    public void setX509TrustManagers(List<X509TrustManager> list) {
        this.f8354a = list;
    }

    public SecureX509TrustManager(Context context, boolean z2) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException, IllegalArgumentException {
        this.f8354a = new ArrayList();
        if (context == null) {
            throw new IllegalArgumentException("context is null");
        }
        com.huawei.secure.android.common.ssl.util.c.a(context);
        if (z2) {
            a();
        }
        a(context);
        if (this.f8354a.isEmpty()) {
            throw new CertificateException("X509TrustManager is empty");
        }
    }

    public SecureX509TrustManager(InputStream inputStream, String str) throws IOException, IllegalArgumentException {
        this.f8354a = new ArrayList();
        a(inputStream, str);
    }

    public SecureX509TrustManager(String str) throws IllegalArgumentException, FileNotFoundException {
        this(str, false);
    }

    private void a(Context context) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        boolean z2;
        String str = f8348c;
        g.c(str, "loadBksCA");
        long jCurrentTimeMillis = System.currentTimeMillis();
        InputStream filesBksIS = BksUtil.getFilesBksIS(context);
        if (filesBksIS != null) {
            try {
                g.c(str, "get bks not from assets");
                a(filesBksIS);
                z2 = true;
            } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e2) {
                g.b(f8348c, "loadBksCA: exception : " + e2.getMessage());
                z2 = false;
            }
        } else {
            z2 = true;
        }
        if (!z2 || filesBksIS == null) {
            g.c(f8348c, " get bks from assets ");
            a(context.getAssets().open("hmsrootcas.bks"));
        }
        g.a(f8348c, "loadBksCA: cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
    }

    public SecureX509TrustManager(String str, boolean z2) throws Throwable {
        FileInputStream fileInputStream;
        this.f8354a = new ArrayList();
        try {
            fileInputStream = new FileInputStream(str);
        } catch (Throwable th) {
            th = th;
            fileInputStream = null;
        }
        try {
            a(fileInputStream, "");
            f.a((InputStream) fileInputStream);
            if (z2) {
                a();
            }
        } catch (Throwable th2) {
            th = th2;
            f.a((InputStream) fileInputStream);
            throw th;
        }
    }

    public SecureX509TrustManager(InputStream inputStream, String str, boolean z2) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException, IllegalArgumentException {
        this.f8354a = new ArrayList();
        if (z2) {
            a();
        }
        a(inputStream, str);
    }

    private void a(InputStream inputStream) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(f8351f);
            KeyStore keyStore = KeyStore.getInstance("bks");
            keyStore.load(inputStream, "".toCharArray());
            trustManagerFactory.init(keyStore);
            for (TrustManager trustManager : trustManagerFactory.getTrustManagers()) {
                if (trustManager instanceof X509TrustManager) {
                    this.f8354a.add((X509TrustManager) trustManager);
                }
            }
        } finally {
            f.a(inputStream);
        }
    }

    private void a(InputStream inputStream, String str) throws IOException {
        if (inputStream != null && str != null) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            try {
                try {
                    TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(f8351f);
                    KeyStore keyStore = KeyStore.getInstance("bks");
                    keyStore.load(inputStream, str.toCharArray());
                    trustManagerFactory.init(keyStore);
                    TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
                    for (TrustManager trustManager : trustManagers) {
                        if (trustManager instanceof X509TrustManager) {
                            this.f8354a.add((X509TrustManager) trustManager);
                        }
                    }
                    f.a(inputStream);
                } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e2) {
                    g.b(f8348c, "loadInputStream: exception : " + e2.getMessage());
                }
                g.a(f8348c, "loadInputStream: cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
                return;
            } finally {
                f.a(inputStream);
            }
        }
        throw new IllegalArgumentException("inputstream or trustPwd is null");
    }
}
