package com.huawei.hmf.tasks.a;

import android.app.Activity;
import com.huawei.hmf.tasks.Continuation;
import com.huawei.hmf.tasks.ExecuteResult;
import com.huawei.hmf.tasks.OnCanceledListener;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.SuccessContinuation;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.TaskExecutors;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public final class i<TResult> extends Task<TResult> {

    /* renamed from: b, reason: collision with root package name */
    private boolean f7387b;

    /* renamed from: c, reason: collision with root package name */
    private volatile boolean f7388c;

    /* renamed from: d, reason: collision with root package name */
    private TResult f7389d;

    /* renamed from: e, reason: collision with root package name */
    private Exception f7390e;

    /* renamed from: a, reason: collision with root package name */
    private final Object f7386a = new Object();

    /* renamed from: f, reason: collision with root package name */
    private List<ExecuteResult<TResult>> f7391f = new ArrayList();

    private Task<TResult> a(ExecuteResult<TResult> executeResult) {
        boolean zIsComplete;
        synchronized (this.f7386a) {
            zIsComplete = isComplete();
            if (!zIsComplete) {
                this.f7391f.add(executeResult);
            }
        }
        if (zIsComplete) {
            executeResult.onComplete(this);
        }
        return this;
    }

    private void b() {
        synchronized (this.f7386a) {
            Iterator<ExecuteResult<TResult>> it = this.f7391f.iterator();
            while (it.hasNext()) {
                try {
                    it.next().onComplete(this);
                } catch (RuntimeException e2) {
                    throw e2;
                } catch (Exception e3) {
                    throw new RuntimeException(e3);
                }
            }
            this.f7391f = null;
        }
    }

    public final void a(Exception exc) {
        synchronized (this.f7386a) {
            if (this.f7387b) {
                return;
            }
            this.f7387b = true;
            this.f7390e = exc;
            this.f7386a.notifyAll();
            b();
        }
    }

    public final void a(TResult tresult) {
        synchronized (this.f7386a) {
            if (this.f7387b) {
                return;
            }
            this.f7387b = true;
            this.f7389d = tresult;
            this.f7386a.notifyAll();
            b();
        }
    }

    public final boolean a() {
        synchronized (this.f7386a) {
            if (this.f7387b) {
                return false;
            }
            this.f7387b = true;
            this.f7388c = true;
            this.f7386a.notifyAll();
            b();
            return true;
        }
    }

    @Override // com.huawei.hmf.tasks.Task
    public final Task<TResult> addOnCanceledListener(Activity activity, OnCanceledListener onCanceledListener) {
        b bVar = new b(TaskExecutors.uiThread(), onCanceledListener);
        g.a(activity, bVar);
        return a((ExecuteResult) bVar);
    }

    @Override // com.huawei.hmf.tasks.Task
    public final Task<TResult> addOnCanceledListener(OnCanceledListener onCanceledListener) {
        return addOnCanceledListener(TaskExecutors.uiThread(), onCanceledListener);
    }

    @Override // com.huawei.hmf.tasks.Task
    public final Task<TResult> addOnCanceledListener(Executor executor, OnCanceledListener onCanceledListener) {
        return a((ExecuteResult) new b(executor, onCanceledListener));
    }

    @Override // com.huawei.hmf.tasks.Task
    public final Task<TResult> addOnCompleteListener(Activity activity, OnCompleteListener<TResult> onCompleteListener) {
        d dVar = new d(TaskExecutors.uiThread(), onCompleteListener);
        g.a(activity, dVar);
        return a((ExecuteResult) dVar);
    }

    @Override // com.huawei.hmf.tasks.Task
    public final Task<TResult> addOnCompleteListener(OnCompleteListener<TResult> onCompleteListener) {
        return addOnCompleteListener(TaskExecutors.uiThread(), onCompleteListener);
    }

    @Override // com.huawei.hmf.tasks.Task
    public final Task<TResult> addOnCompleteListener(Executor executor, OnCompleteListener<TResult> onCompleteListener) {
        return a((ExecuteResult) new d(executor, onCompleteListener));
    }

    @Override // com.huawei.hmf.tasks.Task
    public final Task<TResult> addOnFailureListener(Activity activity, OnFailureListener onFailureListener) {
        f fVar = new f(TaskExecutors.uiThread(), onFailureListener);
        g.a(activity, fVar);
        return a((ExecuteResult) fVar);
    }

    @Override // com.huawei.hmf.tasks.Task
    public final Task<TResult> addOnFailureListener(OnFailureListener onFailureListener) {
        return addOnFailureListener(TaskExecutors.uiThread(), onFailureListener);
    }

    @Override // com.huawei.hmf.tasks.Task
    public final Task<TResult> addOnFailureListener(Executor executor, OnFailureListener onFailureListener) {
        return a((ExecuteResult) new f(executor, onFailureListener));
    }

    @Override // com.huawei.hmf.tasks.Task
    public final Task<TResult> addOnSuccessListener(Activity activity, OnSuccessListener<TResult> onSuccessListener) {
        h hVar = new h(TaskExecutors.uiThread(), onSuccessListener);
        g.a(activity, hVar);
        return a((ExecuteResult) hVar);
    }

    @Override // com.huawei.hmf.tasks.Task
    public final Task<TResult> addOnSuccessListener(OnSuccessListener<TResult> onSuccessListener) {
        return addOnSuccessListener(TaskExecutors.uiThread(), onSuccessListener);
    }

    @Override // com.huawei.hmf.tasks.Task
    public final Task<TResult> addOnSuccessListener(Executor executor, OnSuccessListener<TResult> onSuccessListener) {
        return a((ExecuteResult) new h(executor, onSuccessListener));
    }

    @Override // com.huawei.hmf.tasks.Task
    public final <TContinuationResult> Task<TContinuationResult> continueWith(Continuation<TResult, TContinuationResult> continuation) {
        return continueWith(TaskExecutors.uiThread(), continuation);
    }

    @Override // com.huawei.hmf.tasks.Task
    public final <TContinuationResult> Task<TContinuationResult> continueWith(Executor executor, final Continuation<TResult, TContinuationResult> continuation) {
        final i iVar = new i();
        addOnCompleteListener(executor, new OnCompleteListener<TResult>() { // from class: com.huawei.hmf.tasks.a.i.5
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.huawei.hmf.tasks.OnCompleteListener
            public final void onComplete(Task<TResult> task) {
                if (task.isCanceled()) {
                    iVar.a();
                    return;
                }
                try {
                    iVar.a((i) continuation.then(task));
                } catch (Exception e2) {
                    iVar.a(e2);
                }
            }
        });
        return iVar;
    }

    @Override // com.huawei.hmf.tasks.Task
    public final <TContinuationResult> Task<TContinuationResult> continueWithTask(Continuation<TResult, Task<TContinuationResult>> continuation) {
        return continueWithTask(TaskExecutors.uiThread(), continuation);
    }

    @Override // com.huawei.hmf.tasks.Task
    public final <TContinuationResult> Task<TContinuationResult> continueWithTask(Executor executor, final Continuation<TResult, Task<TContinuationResult>> continuation) {
        final i iVar = new i();
        addOnCompleteListener(executor, new OnCompleteListener<TResult>() { // from class: com.huawei.hmf.tasks.a.i.4
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.huawei.hmf.tasks.OnCompleteListener
            public final void onComplete(Task<TResult> task) {
                try {
                    Task task2 = (Task) continuation.then(task);
                    if (task2 == 0) {
                        iVar.a((Exception) new NullPointerException("Continuation returned null"));
                    } else {
                        task2.addOnCompleteListener(new OnCompleteListener<TContinuationResult>() { // from class: com.huawei.hmf.tasks.a.i.4.1
                            @Override // com.huawei.hmf.tasks.OnCompleteListener
                            public final void onComplete(Task<TContinuationResult> task3) {
                                if (task3.isSuccessful()) {
                                    iVar.a((i) task3.getResult());
                                } else if (task3.isCanceled()) {
                                    iVar.a();
                                } else {
                                    iVar.a(task3.getException());
                                }
                            }
                        });
                    }
                } catch (Exception e2) {
                    iVar.a(e2);
                }
            }
        });
        return iVar;
    }

    @Override // com.huawei.hmf.tasks.Task
    public final Exception getException() {
        Exception exc;
        synchronized (this.f7386a) {
            exc = this.f7390e;
        }
        return exc;
    }

    @Override // com.huawei.hmf.tasks.Task
    public final TResult getResult() {
        TResult tresult;
        synchronized (this.f7386a) {
            if (this.f7390e != null) {
                throw new RuntimeException(this.f7390e);
            }
            tresult = this.f7389d;
        }
        return tresult;
    }

    @Override // com.huawei.hmf.tasks.Task
    public final <E extends Throwable> TResult getResultThrowException(Class<E> cls) throws Throwable {
        TResult tresult;
        synchronized (this.f7386a) {
            if (cls != null) {
                if (cls.isInstance(this.f7390e)) {
                    throw cls.cast(this.f7390e);
                }
            }
            if (this.f7390e != null) {
                throw new RuntimeException(this.f7390e);
            }
            tresult = this.f7389d;
        }
        return tresult;
    }

    @Override // com.huawei.hmf.tasks.Task
    public final boolean isCanceled() {
        return this.f7388c;
    }

    @Override // com.huawei.hmf.tasks.Task
    public final boolean isComplete() {
        boolean z2;
        synchronized (this.f7386a) {
            z2 = this.f7387b;
        }
        return z2;
    }

    @Override // com.huawei.hmf.tasks.Task
    public final boolean isSuccessful() {
        boolean z2;
        synchronized (this.f7386a) {
            z2 = this.f7387b && !isCanceled() && this.f7390e == null;
        }
        return z2;
    }

    @Override // com.huawei.hmf.tasks.Task
    public final <TContinuationResult> Task<TContinuationResult> onSuccessTask(SuccessContinuation<TResult, TContinuationResult> successContinuation) {
        return onSuccessTask(TaskExecutors.uiThread(), successContinuation);
    }

    @Override // com.huawei.hmf.tasks.Task
    public final <TContinuationResult> Task<TContinuationResult> onSuccessTask(Executor executor, final SuccessContinuation<TResult, TContinuationResult> successContinuation) {
        final i iVar = new i();
        addOnSuccessListener(executor, new OnSuccessListener<TResult>() { // from class: com.huawei.hmf.tasks.a.i.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.huawei.hmf.tasks.OnSuccessListener
            public final void onSuccess(TResult tresult) {
                try {
                    Task taskThen = successContinuation.then(tresult);
                    if (taskThen == 0) {
                        iVar.a((Exception) new NullPointerException("SuccessContinuation returned null"));
                    } else {
                        taskThen.addOnCompleteListener(new OnCompleteListener<TContinuationResult>() { // from class: com.huawei.hmf.tasks.a.i.1.1
                            @Override // com.huawei.hmf.tasks.OnCompleteListener
                            public final void onComplete(Task<TContinuationResult> task) {
                                if (task.isSuccessful()) {
                                    iVar.a((i) task.getResult());
                                } else if (task.isCanceled()) {
                                    iVar.a();
                                } else {
                                    iVar.a(task.getException());
                                }
                            }
                        });
                    }
                } catch (Exception e2) {
                    iVar.a(e2);
                }
            }
        });
        addOnFailureListener(new OnFailureListener() { // from class: com.huawei.hmf.tasks.a.i.2
            @Override // com.huawei.hmf.tasks.OnFailureListener
            public final void onFailure(Exception exc) {
                iVar.a(exc);
            }
        });
        addOnCanceledListener(new OnCanceledListener() { // from class: com.huawei.hmf.tasks.a.i.3
            @Override // com.huawei.hmf.tasks.OnCanceledListener
            public final void onCanceled() {
                iVar.a();
            }
        });
        return iVar;
    }
}
