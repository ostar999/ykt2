package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Consumer;

/* loaded from: classes8.dex */
public final class SingleDoOnSuccess<T> extends Single<T> {
    final Consumer<? super T> onSuccess;
    final SingleSource<T> source;

    public final class DoOnSuccess implements SingleObserver<T> {
        final SingleObserver<? super T> downstream;

        public DoOnSuccess(SingleObserver<? super T> observer) {
            this.downstream = observer;
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            this.downstream.onError(e2);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            this.downstream.onSubscribe(d3);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T value) {
            try {
                SingleDoOnSuccess.this.onSuccess.accept(value);
                this.downstream.onSuccess(value);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
            }
        }
    }

    public SingleDoOnSuccess(SingleSource<T> source, Consumer<? super T> onSuccess) {
        this.source = source;
        this.onSuccess = onSuccess;
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(final SingleObserver<? super T> observer) {
        this.source.subscribe(new DoOnSuccess(observer));
    }
}
