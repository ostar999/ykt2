package com.aliyun.vod.qupaiokhttp.https;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/* loaded from: classes2.dex */
public class SkirtHttpsHostnameVerifier implements HostnameVerifier {
    @Override // javax.net.ssl.HostnameVerifier
    public boolean verify(String str, SSLSession sSLSession) {
        return true;
    }
}
