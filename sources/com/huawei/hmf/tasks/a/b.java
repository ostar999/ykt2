package com.huawei.hmf.tasks.a;

import com.huawei.hmf.tasks.ExecuteResult;
import com.huawei.hmf.tasks.OnCanceledListener;
import com.huawei.hmf.tasks.Task;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public final class b<TResult> implements ExecuteResult<TResult> {

    /* renamed from: a, reason: collision with root package name */
    private OnCanceledListener f7356a;

    /* renamed from: b, reason: collision with root package name */
    private Executor f7357b;

    /* renamed from: c, reason: collision with root package name */
    private final Object f7358c = new Object();

    public b(Executor executor, OnCanceledListener onCanceledListener) {
        this.f7356a = onCanceledListener;
        this.f7357b = executor;
    }

    @Override // com.huawei.hmf.tasks.ExecuteResult
    public final void cancel() {
        synchronized (this.f7358c) {
            this.f7356a = null;
        }
    }

    @Override // com.huawei.hmf.tasks.ExecuteResult
    public final void onComplete(Task<TResult> task) {
        if (task.isCanceled()) {
            this.f7357b.execute(new Runnable() { // from class: com.huawei.hmf.tasks.a.b.1
                @Override // java.lang.Runnable
                public final void run() {
                    synchronized (b.this.f7358c) {
                        if (b.this.f7356a != null) {
                            b.this.f7356a.onCanceled();
                        }
                    }
                }
            });
        }
    }
}
