package io.reactivex.rxjava3.internal.observers;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.DisposableContainer;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;

/* loaded from: classes8.dex */
public final class DisposableAutoReleaseObserver<T> extends AbstractDisposableAutoRelease implements Observer<T> {
    private static final long serialVersionUID = 8924480688481408726L;
    final Consumer<? super T> onNext;

    public DisposableAutoReleaseObserver(DisposableContainer composite, Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete) {
        super(composite, onError, onComplete);
        this.onNext = onNext;
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(T t2) {
        if (get() != DisposableHelper.DISPOSED) {
            try {
                this.onNext.accept(t2);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                get().dispose();
                onError(th);
            }
        }
    }
}
