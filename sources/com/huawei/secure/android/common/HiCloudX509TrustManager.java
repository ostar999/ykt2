package com.huawei.secure.android.common;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Deprecated
/* loaded from: classes4.dex */
public class HiCloudX509TrustManager extends com.huawei.secure.android.common.ssl.SecureX509TrustManager {
    @Deprecated
    public HiCloudX509TrustManager(InputStream inputStream, String str) throws NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException {
        super(inputStream, str);
    }
}
