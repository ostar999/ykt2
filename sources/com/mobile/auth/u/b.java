package com.mobile.auth.u;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.manager.a;
import com.nirvana.tools.requestqueue.TimeoutResponse;

/* loaded from: classes4.dex */
public class b extends TimeoutResponse {

    /* renamed from: a, reason: collision with root package name */
    private a.C0199a f10558a;

    /* renamed from: b, reason: collision with root package name */
    private boolean f10559b;

    /* renamed from: c, reason: collision with root package name */
    private com.mobile.auth.gatewayauth.manager.base.b f10560c;

    public b(boolean z2) {
        super(z2);
    }

    public void a(a.C0199a c0199a) {
        try {
            this.f10558a = c0199a;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void a(com.mobile.auth.gatewayauth.manager.base.b bVar) {
        try {
            this.f10560c = bVar;
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
            this.f10559b = z2;
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
            return this.f10559b;
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
            return this.f10560c;
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
