package com.alipay.android.phone.mrpc.core;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/* loaded from: classes2.dex */
final class m extends FutureTask<u> {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ q f2970a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ l f2971b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public m(l lVar, Callable callable, q qVar) {
        super(callable);
        this.f2971b = lVar;
        this.f2970a = qVar;
    }

    @Override // java.util.concurrent.FutureTask
    public final void done() {
        o oVarA = this.f2970a.a();
        if (oVarA.f() == null) {
            super.done();
            return;
        }
        try {
            get();
            if (isCancelled() || oVarA.h()) {
                oVarA.g();
                if (isCancelled() && isDone()) {
                    return;
                }
                cancel(false);
            }
        } catch (InterruptedException e2) {
            e2.toString();
        } catch (CancellationException unused) {
            oVarA.g();
        } catch (ExecutionException e3) {
            if (e3.getCause() == null || !(e3.getCause() instanceof HttpException)) {
                e3.toString();
                return;
            }
            HttpException httpException = (HttpException) e3.getCause();
            httpException.getCode();
            httpException.getMsg();
        } catch (Throwable th) {
            throw new RuntimeException("An error occured while executing http request", th);
        }
    }
}
