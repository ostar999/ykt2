package com.hjq.http.model;

import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    HEAD("HEAD"),
    DELETE("DELETE"),
    PUT("PUT"),
    PATCH("PATCH"),
    OPTIONS("OPTIONS"),
    TRACE("TRACE");

    private final String mMethod;

    HttpMethod(String str) {
        this.mMethod = str;
    }

    @Override // java.lang.Enum
    @NonNull
    public String toString() {
        return this.mMethod;
    }
}
