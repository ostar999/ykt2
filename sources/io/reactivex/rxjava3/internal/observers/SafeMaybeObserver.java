package io.reactivex.rxjava3.internal.observers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes8.dex */
public final class SafeMaybeObserver<T> implements MaybeObserver<T> {
    final MaybeObserver<? super T> downstream;
    boolean onSubscribeFailed;

    public SafeMaybeObserver(MaybeObserver<? super T> downstream) {
        this.downstream = downstream;
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.CompletableObserver
    public void onComplete() {
        if (this.onSubscribeFailed) {
            return;
        }
        try {
            this.downstream.onComplete();
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            RxJavaPlugins.onError(th);
        }
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
    public void onError(@NonNull Throwable e2) {
        if (this.onSubscribeFailed) {
            RxJavaPlugins.onError(e2);
            return;
        }
        try {
            this.downstream.onError(e2);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            RxJavaPlugins.onError(new CompositeException(e2, th));
        }
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
    public void onSubscribe(@NonNull Disposable d3) {
        try {
            this.downstream.onSubscribe(d3);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            this.onSubscribeFailed = true;
            d3.dispose();
            RxJavaPlugins.onError(th);
        }
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver
    public void onSuccess(@NonNull T t2) {
        if (this.onSubscribeFailed) {
            return;
        }
        try {
            this.downstream.onSuccess(t2);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            RxJavaPlugins.onError(th);
        }
    }
}
