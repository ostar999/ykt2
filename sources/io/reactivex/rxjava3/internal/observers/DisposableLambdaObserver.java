package io.reactivex.rxjava3.internal.observers;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes8.dex */
public final class DisposableLambdaObserver<T> implements Observer<T>, Disposable {
    final Observer<? super T> downstream;
    final Action onDispose;
    final Consumer<? super Disposable> onSubscribe;
    Disposable upstream;

    public DisposableLambdaObserver(Observer<? super T> actual, Consumer<? super Disposable> onSubscribe, Action onDispose) {
        this.downstream = actual;
        this.onSubscribe = onSubscribe;
        this.onDispose = onDispose;
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        Disposable disposable = this.upstream;
        DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
        if (disposable != disposableHelper) {
            this.upstream = disposableHelper;
            try {
                this.onDispose.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
            disposable.dispose();
        }
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return this.upstream.isDisposed();
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onComplete() {
        Disposable disposable = this.upstream;
        DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
        if (disposable != disposableHelper) {
            this.upstream = disposableHelper;
            this.downstream.onComplete();
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onError(Throwable t2) {
        Disposable disposable = this.upstream;
        DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
        if (disposable == disposableHelper) {
            RxJavaPlugins.onError(t2);
        } else {
            this.upstream = disposableHelper;
            this.downstream.onError(t2);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(T t2) {
        this.downstream.onNext(t2);
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onSubscribe(Disposable d3) {
        try {
            this.onSubscribe.accept(d3);
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                this.downstream.onSubscribe(this);
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            d3.dispose();
            this.upstream = DisposableHelper.DISPOSED;
            EmptyDisposable.error(th, this.downstream);
        }
    }
}
