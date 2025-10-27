package com.huawei.hms.common.api;

import com.huawei.hms.support.api.client.Result;

/* loaded from: classes4.dex */
public class Response<T extends Result> {
    protected T result;

    public Response() {
    }

    public T getResult() {
        return this.result;
    }

    public void setResult(T t2) {
        this.result = t2;
    }

    public Response(T t2) {
        this.result = t2;
    }
}
