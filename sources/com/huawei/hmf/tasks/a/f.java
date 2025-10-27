package com.huawei.hmf.tasks.a;

import com.huawei.hmf.tasks.ExecuteResult;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.Task;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public final class f<TResult> implements ExecuteResult<TResult> {

    /* renamed from: a, reason: collision with root package name */
    private OnFailureListener f7374a;

    /* renamed from: b, reason: collision with root package name */
    private Executor f7375b;

    /* renamed from: c, reason: collision with root package name */
    private final Object f7376c = new Object();

    public f(Executor executor, OnFailureListener onFailureListener) {
        this.f7374a = onFailureListener;
        this.f7375b = executor;
    }

    @Override // com.huawei.hmf.tasks.ExecuteResult
    public final void cancel() {
        synchronized (this.f7376c) {
            this.f7374a = null;
        }
    }

    @Override // com.huawei.hmf.tasks.ExecuteResult
    public final void onComplete(final Task<TResult> task) {
        if (task.isSuccessful() || task.isCanceled()) {
            return;
        }
        this.f7375b.execute(new Runnable() { // from class: com.huawei.hmf.tasks.a.f.1
            @Override // java.lang.Runnable
            public final void run() {
                synchronized (f.this.f7376c) {
                    if (f.this.f7374a != null) {
                        f.this.f7374a.onFailure(task.getException());
                    }
                }
            }
        });
    }
}
