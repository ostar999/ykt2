package com.hjq.http.config;

import com.hjq.http.model.BodyType;
import com.hjq.http.model.CacheMode;

/* loaded from: classes4.dex */
public interface IRequestServer extends IRequestHost, IRequestPath, IRequestClient, IRequestType, IRequestCache {
    @Override // com.hjq.http.config.IRequestCache
    CacheMode getMode();

    @Override // com.hjq.http.config.IRequestPath
    String getPath();

    @Override // com.hjq.http.config.IRequestType
    BodyType getType();
}
