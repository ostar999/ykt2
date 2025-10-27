package io.reactivex.rxjava3.observers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.util.EndConsumerHelper;

/* loaded from: classes8.dex */
public abstract class DefaultObserver<T> implements Observer<T> {
    private Disposable upstream;

    public final void cancel() {
        Disposable disposable = this.upstream;
        this.upstream = DisposableHelper.DISPOSED;
        disposable.dispose();
    }

    public void onStart() {
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public final void onSubscribe(@NonNull Disposable d3) {
        if (EndConsumerHelper.validate(this.upstream, d3, getClass())) {
            this.upstream = d3;
            onStart();
        }
    }
}
