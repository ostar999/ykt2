package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Supplier;

/* loaded from: classes8.dex */
public final class CompletableToSingle<T> extends Single<T> {
    final T completionValue;
    final Supplier<? extends T> completionValueSupplier;
    final CompletableSource source;

    public final class ToSingle implements CompletableObserver {
        private final SingleObserver<? super T> observer;

        public ToSingle(SingleObserver<? super T> observer) {
            this.observer = observer;
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            T t2;
            CompletableToSingle completableToSingle = CompletableToSingle.this;
            Supplier<? extends T> supplier = completableToSingle.completionValueSupplier;
            if (supplier != null) {
                try {
                    t2 = supplier.get();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.observer.onError(th);
                    return;
                }
            } else {
                t2 = completableToSingle.completionValue;
            }
            if (t2 == null) {
                this.observer.onError(new NullPointerException("The value supplied is null"));
            } else {
                this.observer.onSuccess(t2);
            }
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            this.observer.onError(e2);
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            this.observer.onSubscribe(d3);
        }
    }

    public CompletableToSingle(CompletableSource source, Supplier<? extends T> completionValueSupplier, T completionValue) {
        this.source = source;
        this.completionValue = completionValue;
        this.completionValueSupplier = completionValueSupplier;
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(final SingleObserver<? super T> observer) {
        this.source.subscribe(new ToSingle(observer));
    }
}
