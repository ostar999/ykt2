package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.disposables.b;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes8.dex */
public final class MaybeFromAction<T> extends Maybe<T> implements Supplier<T> {
    final Action action;

    public MaybeFromAction(Action action) {
        this.action = action;
    }

    @Override // io.reactivex.rxjava3.functions.Supplier
    public T get() throws Throwable {
        this.action.run();
        return null;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    public void subscribeActual(MaybeObserver<? super T> observer) {
        Disposable disposableB = b.b();
        observer.onSubscribe(disposableB);
        if (disposableB.isDisposed()) {
            return;
        }
        try {
            this.action.run();
            if (disposableB.isDisposed()) {
                return;
            }
            observer.onComplete();
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            if (disposableB.isDisposed()) {
                RxJavaPlugins.onError(th);
            } else {
                observer.onError(th);
            }
        }
    }
}
