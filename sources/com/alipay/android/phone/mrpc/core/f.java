package com.alipay.android.phone.mrpc.core;

import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.protocol.HttpContext;

/* loaded from: classes2.dex */
final class f implements ConnectionKeepAliveStrategy {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ d f2951a;

    public f(d dVar) {
        this.f2951a = dVar;
    }

    @Override // org.apache.http.conn.ConnectionKeepAliveStrategy
    public final long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
        return 180000L;
    }
}
