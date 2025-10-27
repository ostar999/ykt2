package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.disposables.b;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;

/* loaded from: classes8.dex */
public final class SingleFromSupplier<T> extends Single<T> {
    final Supplier<? extends T> supplier;

    public SingleFromSupplier(Supplier<? extends T> supplier) {
        this.supplier = supplier;
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        Disposable disposableB = b.b();
        singleObserver.onSubscribe(disposableB);
        if (disposableB.isDisposed()) {
            return;
        }
        try {
            T t2 = this.supplier.get();
            Objects.requireNonNull(t2, "The supplier returned a null value");
            if (disposableB.isDisposed()) {
                return;
            }
            singleObserver.onSuccess(t2);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            if (disposableB.isDisposed()) {
                RxJavaPlugins.onError(th);
            } else {
                singleObserver.onError(th);
            }
        }
    }
}
