package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes8.dex */
public final class SingleDoOnSubscribe<T> extends Single<T> {
    final Consumer<? super Disposable> onSubscribe;
    final SingleSource<T> source;

    public static final class DoOnSubscribeSingleObserver<T> implements SingleObserver<T> {
        boolean done;
        final SingleObserver<? super T> downstream;
        final Consumer<? super Disposable> onSubscribe;

        public DoOnSubscribeSingleObserver(SingleObserver<? super T> actual, Consumer<? super Disposable> onSubscribe) {
            this.downstream = actual;
            this.onSubscribe = onSubscribe;
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            if (this.done) {
                RxJavaPlugins.onError(e2);
            } else {
                this.downstream.onError(e2);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            try {
                this.onSubscribe.accept(d3);
                this.downstream.onSubscribe(d3);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.done = true;
                d3.dispose();
                EmptyDisposable.error(th, this.downstream);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T value) {
            if (this.done) {
                return;
            }
            this.downstream.onSuccess(value);
        }
    }

    public SingleDoOnSubscribe(SingleSource<T> source, Consumer<? super Disposable> onSubscribe) {
        this.source = source;
        this.onSubscribe = onSubscribe;
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(final SingleObserver<? super T> observer) {
        this.source.subscribe(new DoOnSubscribeSingleObserver(observer, this.onSubscribe));
    }
}
