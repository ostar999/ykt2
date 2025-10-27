package com.mobile.auth.t;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.nirvana.tools.requestqueue.Callback;
import com.nirvana.tools.requestqueue.Request;
import com.nirvana.tools.requestqueue.strategy.CallbackStrategy;
import com.nirvana.tools.requestqueue.strategy.ExecuteStrategy;
import com.nirvana.tools.requestqueue.strategy.ThreadStrategy;

/* loaded from: classes4.dex */
public class b extends Request<com.mobile.auth.u.b> {

    /* renamed from: a, reason: collision with root package name */
    private String f10555a;

    public b(com.mobile.auth.p.b bVar, Callback<com.mobile.auth.u.b> callback, long j2, String str) {
        super(callback, bVar, ThreadStrategy.THREAD, ExecuteStrategy.USE_PREV, CallbackStrategy.LIST, j2, com.mobile.auth.u.b.class);
        this.f10555a = str;
    }

    @Override // com.nirvana.tools.requestqueue.Request
    public String getKey() {
        try {
            return this.f10555a;
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
}
