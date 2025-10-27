package com.mobile.auth.u;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.nirvana.tools.requestqueue.TimeoutResponse;

/* loaded from: classes4.dex */
public class c extends TimeoutResponse {

    /* renamed from: a, reason: collision with root package name */
    private boolean f10561a;

    /* renamed from: b, reason: collision with root package name */
    private com.mobile.auth.gatewayauth.manager.base.b f10562b;

    public c(boolean z2) {
        super(z2);
    }

    public void a(com.mobile.auth.gatewayauth.manager.base.b bVar) {
        try {
            this.f10562b = bVar;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void a(boolean z2) {
        try {
            this.f10561a = z2;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public boolean a() {
        try {
            return this.f10561a;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public com.mobile.auth.gatewayauth.manager.base.b b() {
        try {
            return this.f10562b;
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
