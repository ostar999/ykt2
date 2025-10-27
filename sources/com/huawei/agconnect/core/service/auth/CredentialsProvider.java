package com.huawei.agconnect.core.service.auth;

import com.huawei.hmf.tasks.Task;

/* loaded from: classes4.dex */
public interface CredentialsProvider {
    Task<Token> getTokens();

    Task<Token> getTokens(boolean z2);
}
