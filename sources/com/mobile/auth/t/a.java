package com.mobile.auth.t;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.nirvana.tools.requestqueue.Callback;
import com.nirvana.tools.requestqueue.Request;
import com.nirvana.tools.requestqueue.TimeoutCallable;
import com.nirvana.tools.requestqueue.strategy.CallbackStrategy;
import com.nirvana.tools.requestqueue.strategy.ExecuteStrategy;
import com.nirvana.tools.requestqueue.strategy.ThreadStrategy;

/* loaded from: classes4.dex */
public class a extends Request<com.mobile.auth.u.a> {

    /* renamed from: a, reason: collision with root package name */
    private static final String f10554a = "com.mobile.auth.t.a";

    public a(Callback<com.mobile.auth.u.a> callback, TimeoutCallable<com.mobile.auth.u.a> timeoutCallable, long j2, Class<com.mobile.auth.u.a> cls) {
        super(callback, timeoutCallable, ThreadStrategy.THREAD, ExecuteStrategy.USE_PREV, CallbackStrategy.COVER, j2, cls);
    }

    @Override // com.nirvana.tools.requestqueue.Request
    public String getKey() {
        try {
            return f10554a;
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
