package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;

/* loaded from: classes8.dex */
public final class MaybeDoOnEvent<T> extends AbstractMaybeWithUpstream<T, T> {
    final BiConsumer<? super T, ? super Throwable> onEvent;

    public static final class DoOnEventMaybeObserver<T> implements MaybeObserver<T>, Disposable {
        final MaybeObserver<? super T> downstream;
        final BiConsumer<? super T, ? super Throwable> onEvent;
        Disposable upstream;

        public DoOnEventMaybeObserver(MaybeObserver<? super T> actual, BiConsumer<? super T, ? super Throwable> onEvent) {
            this.downstream = actual;
            this.onEvent = onEvent;
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
            try {
                this.onEvent.accept(null, null);
                this.downstream.onComplete();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            this.upstream = DisposableHelper.DISPOSED;
            try {
                this.onEvent.accept(null, e2);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                e2 = new CompositeException(e2, th);
            }
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
            try {
                this.onEvent.accept(value, null);
                this.downstream.onSuccess(value);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
            }
        }
    }

    public MaybeDoOnEvent(MaybeSource<T> source, BiConsumer<? super T, ? super Throwable> onEvent) {
        super(source);
        this.onEvent = onEvent;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    public void subscribeActual(MaybeObserver<? super T> observer) {
        this.source.subscribe(new DoOnEventMaybeObserver(observer, this.onEvent));
    }
}
