package com.aliyun.vod.qupaiokhttp;

import okhttp3.Headers;
import okhttp3.Response;

/* loaded from: classes2.dex */
class ResponseData {
    private int code;
    private Headers headers;
    private Response httpResponse;
    private String message;
    private String response;
    private boolean responseNull;
    private boolean success;
    private boolean timeout;

    public int getCode() {
        return this.code;
    }

    public Headers getHeaders() {
        return this.headers;
    }

    public Response getHttpResponse() {
        return this.httpResponse;
    }

    public String getMessage() {
        return this.message;
    }

    public String getResponse() {
        return this.response;
    }

    public boolean isResponseNull() {
        return this.responseNull;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public boolean isTimeout() {
        return this.timeout;
    }

    public void setCode(int i2) {
        this.code = i2;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public void setHttpResponse(Response response) {
        this.httpResponse = response;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setResponse(String str) {
        this.response = str;
    }

    public void setResponseNull(boolean z2) {
        this.responseNull = z2;
    }

    public void setSuccess(boolean z2) {
        this.success = z2;
    }

    public void setTimeout(boolean z2) {
        this.timeout = z2;
    }
}
