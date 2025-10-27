package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;

/* loaded from: classes8.dex */
public final class SingleOnErrorReturn<T> extends Single<T> {
    final SingleSource<? extends T> source;
    final T value;
    final Function<? super Throwable, ? extends T> valueSupplier;

    public final class OnErrorReturn implements SingleObserver<T> {
        private final SingleObserver<? super T> observer;

        public OnErrorReturn(SingleObserver<? super T> observer) {
            this.observer = observer;
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            T tApply;
            SingleOnErrorReturn singleOnErrorReturn = SingleOnErrorReturn.this;
            Function<? super Throwable, ? extends T> function = singleOnErrorReturn.valueSupplier;
            if (function != null) {
                try {
                    tApply = function.apply(e2);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.observer.onError(new CompositeException(e2, th));
                    return;
                }
            } else {
                tApply = singleOnErrorReturn.value;
            }
            if (tApply != null) {
                this.observer.onSuccess(tApply);
                return;
            }
            NullPointerException nullPointerException = new NullPointerException("Value supplied was null");
            nullPointerException.initCause(e2);
            this.observer.onError(nullPointerException);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            this.observer.onSubscribe(d3);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T value) {
            this.observer.onSuccess(value);
        }
    }

    public SingleOnErrorReturn(SingleSource<? extends T> source, Function<? super Throwable, ? extends T> valueSupplier, T value) {
        this.source = source;
        this.valueSupplier = valueSupplier;
        this.value = value;
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(final SingleObserver<? super T> observer) {
        this.source.subscribe(new OnErrorReturn(observer));
    }
}
