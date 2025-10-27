package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.disposables.b;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;

/* loaded from: classes8.dex */
public final class MaybeFromCallable<T> extends Maybe<T> implements Supplier<T> {
    final Callable<? extends T> callable;

    public MaybeFromCallable(Callable<? extends T> callable) {
        this.callable = callable;
    }

    @Override // io.reactivex.rxjava3.functions.Supplier
    public T get() throws Exception {
        return this.callable.call();
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        Disposable disposableB = b.b();
        maybeObserver.onSubscribe(disposableB);
        if (disposableB.isDisposed()) {
            return;
        }
        try {
            T tCall = this.callable.call();
            if (disposableB.isDisposed()) {
                return;
            }
            if (tCall == null) {
                maybeObserver.onComplete();
            } else {
                maybeObserver.onSuccess(tCall);
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            if (disposableB.isDisposed()) {
                RxJavaPlugins.onError(th);
            } else {
                maybeObserver.onError(th);
            }
        }
    }
}
