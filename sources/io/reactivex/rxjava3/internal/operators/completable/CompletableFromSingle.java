package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;

/* loaded from: classes8.dex */
public final class CompletableFromSingle<T> extends Completable {
    final SingleSource<T> single;

    public static final class CompletableFromSingleObserver<T> implements SingleObserver<T> {
        final CompletableObserver co;

        public CompletableFromSingleObserver(CompletableObserver co) {
            this.co = co;
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            this.co.onError(e2);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            this.co.onSubscribe(d3);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T value) {
            this.co.onComplete();
        }
    }

    public CompletableFromSingle(SingleSource<T> single) {
        this.single = single;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    public void subscribeActual(final CompletableObserver observer) {
        this.single.subscribe(new CompletableFromSingleObserver(observer));
    }
}
