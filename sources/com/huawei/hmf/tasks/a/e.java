package com.huawei.hmf.tasks.a;

import com.huawei.hmf.tasks.OnCanceledListener;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import java.util.concurrent.ExecutionException;

/* loaded from: classes4.dex */
final class e<TResult> implements OnCanceledListener, OnFailureListener, OnSuccessListener<TResult> {

    /* renamed from: a, reason: collision with root package name */
    private final Object f7368a = new Object();

    /* renamed from: b, reason: collision with root package name */
    private final int f7369b;

    /* renamed from: c, reason: collision with root package name */
    private final i<Void> f7370c;

    /* renamed from: d, reason: collision with root package name */
    private int f7371d;

    /* renamed from: e, reason: collision with root package name */
    private Exception f7372e;

    /* renamed from: f, reason: collision with root package name */
    private boolean f7373f;

    public e(int i2, i<Void> iVar) {
        this.f7369b = i2;
        this.f7370c = iVar;
    }

    private void a() {
        if (this.f7371d >= this.f7369b) {
            if (this.f7372e != null) {
                this.f7370c.a(new ExecutionException("a task failed", this.f7372e));
            } else if (this.f7373f) {
                this.f7370c.a();
            } else {
                this.f7370c.a((i<Void>) null);
            }
        }
    }

    @Override // com.huawei.hmf.tasks.OnCanceledListener
    public final void onCanceled() {
        synchronized (this.f7368a) {
            this.f7371d++;
            this.f7373f = true;
            a();
        }
    }

    @Override // com.huawei.hmf.tasks.OnFailureListener
    public final void onFailure(Exception exc) {
        synchronized (this.f7368a) {
            this.f7371d++;
            this.f7372e = exc;
            a();
        }
    }

    @Override // com.huawei.hmf.tasks.OnSuccessListener
    public final void onSuccess(TResult tresult) {
        synchronized (this.f7368a) {
            this.f7371d++;
            a();
        }
    }
}
