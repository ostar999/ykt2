package com.huawei.secure.android.common.ssl;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/* loaded from: classes4.dex */
public class HiCloudX509TrustManager extends SecureX509TrustManager {
    public HiCloudX509TrustManager(InputStream inputStream, String str) throws NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException, IllegalArgumentException {
        super(inputStream, str);
    }
}
