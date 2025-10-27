package com.hjq.http.config;

import com.hjq.http.annotation.HttpIgnore;
import com.hjq.http.model.BodyType;
import com.hjq.http.model.CacheMode;
import okhttp3.OkHttpClient;
import x0.b;
import x0.d;

/* loaded from: classes4.dex */
public final class RequestServer implements IRequestServer {

    @HttpIgnore
    private final String mHost;

    @HttpIgnore
    private final String mPath;

    public RequestServer(String str) {
        this(str, "");
    }

    @Override // com.hjq.http.config.IRequestClient
    public /* synthetic */ OkHttpClient getClient() {
        return b.a(this);
    }

    @Override // com.hjq.http.config.IRequestHost
    public String getHost() {
        return this.mHost;
    }

    @Override // com.hjq.http.config.IRequestServer, com.hjq.http.config.IRequestCache
    public /* synthetic */ CacheMode getMode() {
        return d.a(this);
    }

    @Override // com.hjq.http.config.IRequestServer, com.hjq.http.config.IRequestPath
    public String getPath() {
        return this.mPath;
    }

    @Override // com.hjq.http.config.IRequestServer, com.hjq.http.config.IRequestType
    public /* synthetic */ BodyType getType() {
        return d.c(this);
    }

    public String toString() {
        return this.mHost + this.mPath;
    }

    public RequestServer(String str, String str2) {
        this.mHost = str;
        this.mPath = str2;
    }
}
