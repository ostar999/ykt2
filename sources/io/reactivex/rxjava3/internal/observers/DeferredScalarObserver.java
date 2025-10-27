package io.reactivex.rxjava3.internal.observers;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;

/* loaded from: classes8.dex */
public abstract class DeferredScalarObserver<T, R> extends DeferredScalarDisposable<R> implements Observer<T> {
    private static final long serialVersionUID = -266195175408988651L;
    protected Disposable upstream;

    public DeferredScalarObserver(Observer<? super R> downstream) {
        super(downstream);
    }

    @Override // io.reactivex.rxjava3.internal.observers.DeferredScalarDisposable, io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        super.dispose();
        this.upstream.dispose();
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onComplete() {
        T t2 = this.value;
        if (t2 == null) {
            complete();
        } else {
            this.value = null;
            complete(t2);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onError(Throwable t2) {
        this.value = null;
        error(t2);
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onSubscribe(Disposable d3) {
        if (DisposableHelper.validate(this.upstream, d3)) {
            this.upstream = d3;
            this.downstream.onSubscribe(this);
        }
    }
}
