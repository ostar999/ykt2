package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class ObserverResourceWrapper<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable {
    private static final long serialVersionUID = -8612022020200669122L;
    final Observer<? super T> downstream;
    final AtomicReference<Disposable> upstream = new AtomicReference<>();

    public ObserverResourceWrapper(Observer<? super T> downstream) {
        this.downstream = downstream;
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        DisposableHelper.dispose(this.upstream);
        DisposableHelper.dispose(this);
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return this.upstream.get() == DisposableHelper.DISPOSED;
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onComplete() {
        dispose();
        this.downstream.onComplete();
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onError(Throwable t2) {
        dispose();
        this.downstream.onError(t2);
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(T t2) {
        this.downstream.onNext(t2);
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onSubscribe(Disposable d3) {
        if (DisposableHelper.setOnce(this.upstream, d3)) {
            this.downstream.onSubscribe(this);
        }
    }

    public void setResource(Disposable resource) {
        DisposableHelper.set(this, resource);
    }
}
