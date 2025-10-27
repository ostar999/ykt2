package com.huawei.secure.android.common.ssl.util;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/* loaded from: classes4.dex */
public class j {

    /* renamed from: b, reason: collision with root package name */
    private static final String f8420b = "X509CertificateUtil";

    /* renamed from: c, reason: collision with root package name */
    public static final String f8421c = "hmsrootcas.bks";

    /* renamed from: d, reason: collision with root package name */
    public static final String f8422d = "";

    /* renamed from: e, reason: collision with root package name */
    public static final String f8423e = "bks";

    /* renamed from: f, reason: collision with root package name */
    public static final String f8424f = "052root";

    /* renamed from: g, reason: collision with root package name */
    private static final String f8425g = "hmsincas.bks";

    /* renamed from: h, reason: collision with root package name */
    private static final String f8426h = "huawei cbg application integration ca";

    /* renamed from: a, reason: collision with root package name */
    private Context f8427a;

    public j(Context context) {
        this.f8427a = context;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v4, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v7 */
    public X509Certificate a(String str, String str2) throws Throwable {
        InputStream inputStreamOpen;
        KeyStore keyStore;
        InputStream inputStream = null;
        X509Certificate x509Certificate = null;
        try {
            try {
                keyStore = KeyStore.getInstance(f8423e);
                inputStreamOpen = this.f8427a.getAssets().open(str);
            } catch (IOException e2) {
                e = e2;
                inputStreamOpen = null;
                g.b(f8420b, "loadBksCA: exception : " + e.getMessage());
                str = inputStreamOpen;
                f.a((InputStream) str);
                return x509Certificate;
            } catch (KeyStoreException e3) {
                e = e3;
                inputStreamOpen = null;
                g.b(f8420b, "loadBksCA: exception : " + e.getMessage());
                str = inputStreamOpen;
                f.a((InputStream) str);
                return x509Certificate;
            } catch (NoSuchAlgorithmException e4) {
                e = e4;
                inputStreamOpen = null;
                g.b(f8420b, "loadBksCA: exception : " + e.getMessage());
                str = inputStreamOpen;
                f.a((InputStream) str);
                return x509Certificate;
            } catch (CertificateException e5) {
                e = e5;
                inputStreamOpen = null;
                g.b(f8420b, "loadBksCA: exception : " + e.getMessage());
                str = inputStreamOpen;
                f.a((InputStream) str);
                return x509Certificate;
            } catch (Throwable th) {
                th = th;
                f.a(inputStream);
                throw th;
            }
            try {
                inputStreamOpen.reset();
                keyStore.load(inputStreamOpen, "".toCharArray());
                x509Certificate = (X509Certificate) keyStore.getCertificate(str2);
                str = inputStreamOpen;
            } catch (IOException e6) {
                e = e6;
                g.b(f8420b, "loadBksCA: exception : " + e.getMessage());
                str = inputStreamOpen;
                f.a((InputStream) str);
                return x509Certificate;
            } catch (KeyStoreException e7) {
                e = e7;
                g.b(f8420b, "loadBksCA: exception : " + e.getMessage());
                str = inputStreamOpen;
                f.a((InputStream) str);
                return x509Certificate;
            } catch (NoSuchAlgorithmException e8) {
                e = e8;
                g.b(f8420b, "loadBksCA: exception : " + e.getMessage());
                str = inputStreamOpen;
                f.a((InputStream) str);
                return x509Certificate;
            } catch (CertificateException e9) {
                e = e9;
                g.b(f8420b, "loadBksCA: exception : " + e.getMessage());
                str = inputStreamOpen;
                f.a((InputStream) str);
                return x509Certificate;
            }
            f.a((InputStream) str);
            return x509Certificate;
        } catch (Throwable th2) {
            th = th2;
            inputStream = str;
            f.a(inputStream);
            throw th;
        }
    }

    public X509Certificate b() {
        return a("hmsrootcas.bks", f8424f);
    }

    public X509Certificate a() {
        return a(f8425g, f8426h);
    }
}
