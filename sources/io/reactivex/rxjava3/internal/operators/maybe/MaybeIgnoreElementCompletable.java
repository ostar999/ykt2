package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.fuseable.FuseToMaybe;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes8.dex */
public final class MaybeIgnoreElementCompletable<T> extends Completable implements FuseToMaybe<T> {
    final MaybeSource<T> source;

    public static final class IgnoreMaybeObserver<T> implements MaybeObserver<T>, Disposable {
        final CompletableObserver downstream;
        Disposable upstream;

        public IgnoreMaybeObserver(CompletableObserver downstream) {
            this.downstream = downstream;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.upstream.dispose();
            this.upstream = DisposableHelper.DISPOSED;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            this.upstream = DisposableHelper.DISPOSED;
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            this.upstream = DisposableHelper.DISPOSED;
            this.downstream.onError(e2);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T value) {
            this.upstream = DisposableHelper.DISPOSED;
            this.downstream.onComplete();
        }
    }

    public MaybeIgnoreElementCompletable(MaybeSource<T> source) {
        this.source = source;
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.FuseToMaybe
    public Maybe<T> fuseToMaybe() {
        return RxJavaPlugins.onAssembly(new MaybeIgnoreElement(this.source));
    }

    @Override // io.reactivex.rxjava3.core.Completable
    public void subscribeActual(CompletableObserver observer) {
        this.source.subscribe(new IgnoreMaybeObserver(observer));
    }
}
