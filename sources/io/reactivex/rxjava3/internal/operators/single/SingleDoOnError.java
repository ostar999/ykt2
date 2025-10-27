package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Consumer;

/* loaded from: classes8.dex */
public final class SingleDoOnError<T> extends Single<T> {
    final Consumer<? super Throwable> onError;
    final SingleSource<T> source;

    public final class DoOnError implements SingleObserver<T> {
        private final SingleObserver<? super T> downstream;

        public DoOnError(SingleObserver<? super T> observer) {
            this.downstream = observer;
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            try {
                SingleDoOnError.this.onError.accept(e2);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                e2 = new CompositeException(e2, th);
            }
            this.downstream.onError(e2);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            this.downstream.onSubscribe(d3);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T value) {
            this.downstream.onSuccess(value);
        }
    }

    public SingleDoOnError(SingleSource<T> source, Consumer<? super Throwable> onError) {
        this.source = source;
        this.onError = onError;
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(final SingleObserver<? super T> observer) {
        this.source.subscribe(new DoOnError(observer));
    }
}
