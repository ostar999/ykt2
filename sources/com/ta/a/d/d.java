package com.ta.a.d;

import android.text.TextUtils;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/* loaded from: classes6.dex */
class d implements HostnameVerifier {

    /* renamed from: b, reason: collision with root package name */
    public String f17211b;

    public d(String str) {
        this.f17211b = str;
    }

    public boolean equals(Object obj) {
        if (TextUtils.isEmpty(this.f17211b) || !(obj instanceof d)) {
            return false;
        }
        String str = ((d) obj).f17211b;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return this.f17211b.equals(str);
    }

    @Override // javax.net.ssl.HostnameVerifier
    public boolean verify(String str, SSLSession sSLSession) {
        return HttpsURLConnection.getDefaultHostnameVerifier().verify(this.f17211b, sSLSession);
    }
}
