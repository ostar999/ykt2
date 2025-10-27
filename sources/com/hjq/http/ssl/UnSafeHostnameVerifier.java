package com.hjq.http.ssl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/* loaded from: classes4.dex */
public final class UnSafeHostnameVerifier implements HostnameVerifier {
    @Override // javax.net.ssl.HostnameVerifier
    public boolean verify(String str, SSLSession sSLSession) {
        return true;
    }
}
