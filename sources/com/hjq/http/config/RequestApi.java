package com.hjq.http.config;

import com.hjq.http.annotation.HttpIgnore;

/* loaded from: classes4.dex */
public final class RequestApi implements IRequestApi {

    @HttpIgnore
    private final String mApi;

    public RequestApi(String str) {
        this.mApi = str;
    }

    @Override // com.hjq.http.config.IRequestApi
    public String getApi() {
        return this.mApi;
    }

    public String toString() {
        return this.mApi;
    }
}
