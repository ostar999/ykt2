package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes8.dex */
public final class MaybeDoOnLifecycle<T> extends AbstractMaybeWithUpstream<T, T> {
    final Action onDispose;
    final Consumer<? super Disposable> onSubscribe;

    public static final class MaybeLifecycleObserver<T> implements MaybeObserver<T>, Disposable {
        final MaybeObserver<? super T> downstream;
        final Action onDispose;
        final Consumer<? super Disposable> onSubscribe;
        Disposable upstream;

        public MaybeLifecycleObserver(MaybeObserver<? super T> downstream, Consumer<? super Disposable> onSubscribe, Action onDispose) {
            this.downstream = downstream;
            this.onSubscribe = onSubscribe;
            this.onDispose = onDispose;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            try {
                this.onDispose.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
            this.upstream.dispose();
            this.upstream = DisposableHelper.DISPOSED;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            Disposable disposable = this.upstream;
            DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
            if (disposable != disposableHelper) {
                this.upstream = disposableHelper;
                this.downstream.onComplete();
            }
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(@NonNull Throwable e2) {
            Disposable disposable = this.upstream;
            DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
            if (disposable == disposableHelper) {
                RxJavaPlugins.onError(e2);
            } else {
                this.upstream = disposableHelper;
                this.downstream.onError(e2);
            }
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(@NonNull Disposable d3) {
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

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(@NonNull T t2) {
            Disposable disposable = this.upstream;
            DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
            if (disposable != disposableHelper) {
                this.upstream = disposableHelper;
                this.downstream.onSuccess(t2);
            }
        }
    }

    public MaybeDoOnLifecycle(Maybe<T> upstream, Consumer<? super Disposable> onSubscribe, Action onDispose) {
        super(upstream);
        this.onSubscribe = onSubscribe;
        this.onDispose = onDispose;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    public void subscribeActual(MaybeObserver<? super T> observer) {
        this.source.subscribe(new MaybeLifecycleObserver(observer, this.onSubscribe, this.onDispose));
    }
}
