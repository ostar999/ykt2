package com.huawei.agconnect.core.service.auth;

/* loaded from: classes4.dex */
public interface Token {
    long getExpiration();

    long getIssuedAt();

    long getNotBefore();

    String getTokenString();
}
