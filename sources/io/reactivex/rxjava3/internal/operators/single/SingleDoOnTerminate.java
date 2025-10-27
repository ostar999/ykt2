package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Action;

/* loaded from: classes8.dex */
public final class SingleDoOnTerminate<T> extends Single<T> {
    final Action onTerminate;
    final SingleSource<T> source;

    public final class DoOnTerminate implements SingleObserver<T> {
        final SingleObserver<? super T> downstream;

        public DoOnTerminate(SingleObserver<? super T> observer) {
            this.downstream = observer;
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            try {
                SingleDoOnTerminate.this.onTerminate.run();
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
            try {
                SingleDoOnTerminate.this.onTerminate.run();
                this.downstream.onSuccess(value);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
            }
        }
    }

    public SingleDoOnTerminate(SingleSource<T> source, Action onTerminate) {
        this.source = source;
        this.onTerminate = onTerminate;
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(final SingleObserver<? super T> observer) {
        this.source.subscribe(new DoOnTerminate(observer));
    }
}
