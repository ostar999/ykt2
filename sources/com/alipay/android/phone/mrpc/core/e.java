package com.alipay.android.phone.mrpc.core;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.protocol.HttpContext;

/* loaded from: classes2.dex */
final class e extends DefaultRedirectHandler {

    /* renamed from: a, reason: collision with root package name */
    int f2949a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ d f2950b;

    public e(d dVar) {
        this.f2950b = dVar;
    }

    @Override // org.apache.http.impl.client.DefaultRedirectHandler, org.apache.http.client.RedirectHandler
    public final boolean isRedirectRequested(HttpResponse httpResponse, HttpContext httpContext) {
        int statusCode;
        this.f2949a++;
        boolean zIsRedirectRequested = super.isRedirectRequested(httpResponse, httpContext);
        if (zIsRedirectRequested || this.f2949a >= 5 || !((statusCode = httpResponse.getStatusLine().getStatusCode()) == 301 || statusCode == 302)) {
            return zIsRedirectRequested;
        }
        return true;
    }
}
