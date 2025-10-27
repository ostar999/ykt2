package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.fuseable.CancellableQueueFuseable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes8.dex */
public final class ObservableFromAction<T> extends Observable<T> implements Supplier<T> {
    final Action action;

    public ObservableFromAction(Action action) {
        this.action = action;
    }

    @Override // io.reactivex.rxjava3.functions.Supplier
    public T get() throws Throwable {
        this.action.run();
        return null;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        CancellableQueueFuseable cancellableQueueFuseable = new CancellableQueueFuseable();
        observer.onSubscribe(cancellableQueueFuseable);
        if (cancellableQueueFuseable.isDisposed()) {
            return;
        }
        try {
            this.action.run();
            if (cancellableQueueFuseable.isDisposed()) {
                return;
            }
            observer.onComplete();
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            if (cancellableQueueFuseable.isDisposed()) {
                RxJavaPlugins.onError(th);
            } else {
                observer.onError(th);
            }
        }
    }
}
