package com.mobile.auth.u;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.model.ConfigRule;
import com.nirvana.tools.requestqueue.TimeoutResponse;

/* loaded from: classes4.dex */
public class a extends TimeoutResponse {

    /* renamed from: a, reason: collision with root package name */
    private ConfigRule f10557a;

    public a(boolean z2) {
        super(z2);
    }

    public a(boolean z2, ConfigRule configRule) {
        super(z2);
        this.f10557a = configRule;
    }

    public ConfigRule a() {
        try {
            return this.f10557a;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    @Override // com.nirvana.tools.requestqueue.TimeoutResponse
    public boolean isResultTimeout() {
        return false;
    }
}
