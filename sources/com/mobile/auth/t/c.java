package com.mobile.auth.t;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.manager.a;
import com.mobile.auth.gatewayauth.manager.e;
import com.nirvana.tools.requestqueue.Callback;
import com.nirvana.tools.requestqueue.Request;
import com.nirvana.tools.requestqueue.strategy.CallbackStrategy;
import com.nirvana.tools.requestqueue.strategy.ExecuteStrategy;
import com.nirvana.tools.requestqueue.strategy.ThreadStrategy;

/* loaded from: classes4.dex */
public class c extends Request<com.mobile.auth.u.c> {

    /* renamed from: a, reason: collision with root package name */
    private String f10556a;

    public c(e eVar, Callback<com.mobile.auth.u.c> callback, long j2, String str, a.b bVar) {
        super(callback, new com.mobile.auth.p.c(eVar, str, bVar, j2), ThreadStrategy.THREAD, ExecuteStrategy.USE_PREV, CallbackStrategy.LIST, j2, com.mobile.auth.u.c.class);
        this.f10556a = bVar.b();
    }

    @Override // com.nirvana.tools.requestqueue.Request
    public String getKey() {
        try {
            return this.f10556a;
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
