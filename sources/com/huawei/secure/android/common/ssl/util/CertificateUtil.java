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
public final class CertificateUtil {

    /* renamed from: a, reason: collision with root package name */
    private static final String f8408a = "CertificateUtil";

    private CertificateUtil() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v0, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v7, types: [java.io.InputStream] */
    public static X509Certificate getHwCbgRootCA(Context context) throws Throwable {
        InputStream inputStreamOpen;
        KeyStore keyStore;
        InputStream inputStream = null;
        X509Certificate x509Certificate = null;
        try {
            try {
                keyStore = KeyStore.getInstance(j.f8423e);
                inputStreamOpen = context.getAssets().open("hmsrootcas.bks");
            } catch (IOException e2) {
                e = e2;
                inputStreamOpen = null;
                g.b(f8408a, "loadBksCA: exception : " + e.getMessage());
                context = inputStreamOpen;
                f.a((InputStream) context);
                return x509Certificate;
            } catch (KeyStoreException e3) {
                e = e3;
                inputStreamOpen = null;
                g.b(f8408a, "loadBksCA: exception : " + e.getMessage());
                context = inputStreamOpen;
                f.a((InputStream) context);
                return x509Certificate;
            } catch (NoSuchAlgorithmException e4) {
                e = e4;
                inputStreamOpen = null;
                g.b(f8408a, "loadBksCA: exception : " + e.getMessage());
                context = inputStreamOpen;
                f.a((InputStream) context);
                return x509Certificate;
            } catch (CertificateException e5) {
                e = e5;
                inputStreamOpen = null;
                g.b(f8408a, "loadBksCA: exception : " + e.getMessage());
                context = inputStreamOpen;
                f.a((InputStream) context);
                return x509Certificate;
            } catch (Throwable th) {
                th = th;
                f.a(inputStream);
                throw th;
            }
            try {
                inputStreamOpen.reset();
                keyStore.load(inputStreamOpen, "".toCharArray());
                x509Certificate = (X509Certificate) keyStore.getCertificate(j.f8424f);
                context = inputStreamOpen;
            } catch (IOException e6) {
                e = e6;
                g.b(f8408a, "loadBksCA: exception : " + e.getMessage());
                context = inputStreamOpen;
                f.a((InputStream) context);
                return x509Certificate;
            } catch (KeyStoreException e7) {
                e = e7;
                g.b(f8408a, "loadBksCA: exception : " + e.getMessage());
                context = inputStreamOpen;
                f.a((InputStream) context);
                return x509Certificate;
            } catch (NoSuchAlgorithmException e8) {
                e = e8;
                g.b(f8408a, "loadBksCA: exception : " + e.getMessage());
                context = inputStreamOpen;
                f.a((InputStream) context);
                return x509Certificate;
            } catch (CertificateException e9) {
                e = e9;
                g.b(f8408a, "loadBksCA: exception : " + e.getMessage());
                context = inputStreamOpen;
                f.a((InputStream) context);
                return x509Certificate;
            }
            f.a((InputStream) context);
            return x509Certificate;
        } catch (Throwable th2) {
            inputStream = context;
            th = th2;
            f.a(inputStream);
            throw th;
        }
    }
}
