package com.huawei.hmf.tasks.a;

import com.huawei.hmf.tasks.ExecuteResult;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public final class h<TResult> implements ExecuteResult<TResult> {

    /* renamed from: a, reason: collision with root package name */
    private OnSuccessListener<TResult> f7381a;

    /* renamed from: b, reason: collision with root package name */
    private Executor f7382b;

    /* renamed from: c, reason: collision with root package name */
    private final Object f7383c = new Object();

    public h(Executor executor, OnSuccessListener<TResult> onSuccessListener) {
        this.f7381a = onSuccessListener;
        this.f7382b = executor;
    }

    @Override // com.huawei.hmf.tasks.ExecuteResult
    public final void cancel() {
        synchronized (this.f7383c) {
            this.f7381a = null;
        }
    }

    @Override // com.huawei.hmf.tasks.ExecuteResult
    public final void onComplete(final Task<TResult> task) {
        if (!task.isSuccessful() || task.isCanceled()) {
            return;
        }
        this.f7382b.execute(new Runnable() { // from class: com.huawei.hmf.tasks.a.h.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public final void run() {
                synchronized (h.this.f7383c) {
                    if (h.this.f7381a != null) {
                        h.this.f7381a.onSuccess(task.getResult());
                    }
                }
            }
        });
    }
}
