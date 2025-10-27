package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class MaybeOnErrorNext<T> extends AbstractMaybeWithUpstream<T, T> {
    final Function<? super Throwable, ? extends MaybeSource<? extends T>> resumeFunction;

    public static final class OnErrorNextMaybeObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable {
        private static final long serialVersionUID = 2026620218879969836L;
        final MaybeObserver<? super T> downstream;
        final Function<? super Throwable, ? extends MaybeSource<? extends T>> resumeFunction;

        public static final class NextMaybeObserver<T> implements MaybeObserver<T> {
            final MaybeObserver<? super T> downstream;
            final AtomicReference<Disposable> upstream;

            public NextMaybeObserver(MaybeObserver<? super T> actual, AtomicReference<Disposable> d3) {
                this.downstream = actual;
                this.upstream = d3;
            }

            @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.CompletableObserver
            public void onComplete() {
                this.downstream.onComplete();
            }

            @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
            public void onError(Throwable e2) {
                this.downstream.onError(e2);
            }

            @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
            public void onSubscribe(Disposable d3) {
                DisposableHelper.setOnce(this.upstream, d3);
            }

            @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver
            public void onSuccess(T value) {
                this.downstream.onSuccess(value);
            }
        }

        public OnErrorNextMaybeObserver(MaybeObserver<? super T> actual, Function<? super Throwable, ? extends MaybeSource<? extends T>> resumeFunction) {
            this.downstream = actual;
            this.resumeFunction = resumeFunction;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            try {
                MaybeSource<? extends T> maybeSourceApply = this.resumeFunction.apply(e2);
                Objects.requireNonNull(maybeSourceApply, "The resumeFunction returned a null MaybeSource");
                MaybeSource<? extends T> maybeSource = maybeSourceApply;
                DisposableHelper.replace(this, null);
                maybeSource.subscribe(new NextMaybeObserver(this.downstream, this));
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(new CompositeException(e2, th));
            }
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.setOnce(this, d3)) {
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T value) {
            this.downstream.onSuccess(value);
        }
    }

    public MaybeOnErrorNext(MaybeSource<T> source, Function<? super Throwable, ? extends MaybeSource<? extends T>> resumeFunction) {
        super(source);
        this.resumeFunction = resumeFunction;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    public void subscribeActual(MaybeObserver<? super T> observer) {
        this.source.subscribe(new OnErrorNextMaybeObserver(observer, this.resumeFunction));
    }
}
