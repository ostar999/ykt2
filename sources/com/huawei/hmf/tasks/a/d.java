package com.huawei.hmf.tasks.a;

import com.huawei.hmf.tasks.ExecuteResult;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.Task;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public final class d<TResult> implements ExecuteResult<TResult> {

    /* renamed from: a, reason: collision with root package name */
    Executor f7363a;

    /* renamed from: b, reason: collision with root package name */
    private OnCompleteListener<TResult> f7364b;

    /* renamed from: c, reason: collision with root package name */
    private final Object f7365c = new Object();

    public d(Executor executor, OnCompleteListener<TResult> onCompleteListener) {
        this.f7364b = onCompleteListener;
        this.f7363a = executor;
    }

    @Override // com.huawei.hmf.tasks.ExecuteResult
    public final void cancel() {
        synchronized (this.f7365c) {
            this.f7364b = null;
        }
    }

    @Override // com.huawei.hmf.tasks.ExecuteResult
    public final void onComplete(final Task<TResult> task) {
        this.f7363a.execute(new Runnable() { // from class: com.huawei.hmf.tasks.a.d.1
            @Override // java.lang.Runnable
            public final void run() {
                synchronized (d.this.f7365c) {
                    if (d.this.f7364b != null) {
                        d.this.f7364b.onComplete(task);
                    }
                }
            }
        });
    }
}
