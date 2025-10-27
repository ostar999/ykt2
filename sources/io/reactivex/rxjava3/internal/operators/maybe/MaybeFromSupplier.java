package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.disposables.b;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes8.dex */
public final class MaybeFromSupplier<T> extends Maybe<T> implements Supplier<T> {
    final Supplier<? extends T> supplier;

    public MaybeFromSupplier(Supplier<? extends T> supplier) {
        this.supplier = supplier;
    }

    @Override // io.reactivex.rxjava3.functions.Supplier
    public T get() throws Throwable {
        return this.supplier.get();
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        Disposable disposableB = b.b();
        maybeObserver.onSubscribe(disposableB);
        if (disposableB.isDisposed()) {
            return;
        }
        try {
            T t2 = this.supplier.get();
            if (disposableB.isDisposed()) {
                return;
            }
            if (t2 == null) {
                maybeObserver.onComplete();
            } else {
                maybeObserver.onSuccess(t2);
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
