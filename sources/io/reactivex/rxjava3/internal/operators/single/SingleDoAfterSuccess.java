package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes8.dex */
public final class SingleDoAfterSuccess<T> extends Single<T> {
    final Consumer<? super T> onAfterSuccess;
    final SingleSource<T> source;

    public static final class DoAfterObserver<T> implements SingleObserver<T>, Disposable {
        final SingleObserver<? super T> downstream;
        final Consumer<? super T> onAfterSuccess;
        Disposable upstream;

        public DoAfterObserver(SingleObserver<? super T> actual, Consumer<? super T> onAfterSuccess) {
            this.downstream = actual;
            this.onAfterSuccess = onAfterSuccess;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.upstream.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            this.downstream.onError(e2);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T t2) {
            this.downstream.onSuccess(t2);
            try {
                this.onAfterSuccess.accept(t2);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        }
    }

    public SingleDoAfterSuccess(SingleSource<T> source, Consumer<? super T> onAfterSuccess) {
        this.source = source;
        this.onAfterSuccess = onAfterSuccess;
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(SingleObserver<? super T> observer) {
        this.source.subscribe(new DoAfterObserver(observer, this.onAfterSuccess));
    }
}
