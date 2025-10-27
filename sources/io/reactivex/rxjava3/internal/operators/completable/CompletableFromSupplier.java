package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.disposables.b;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes8.dex */
public final class CompletableFromSupplier extends Completable {
    final Supplier<?> supplier;

    public CompletableFromSupplier(Supplier<?> supplier) {
        this.supplier = supplier;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    public void subscribeActual(CompletableObserver observer) {
        Disposable disposableB = b.b();
        observer.onSubscribe(disposableB);
        try {
            this.supplier.get();
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
