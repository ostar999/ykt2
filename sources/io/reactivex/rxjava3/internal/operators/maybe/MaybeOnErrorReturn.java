package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.Objects;

/* loaded from: classes8.dex */
public final class MaybeOnErrorReturn<T> extends AbstractMaybeWithUpstream<T, T> {
    final Function<? super Throwable, ? extends T> itemSupplier;

    public static final class OnErrorReturnMaybeObserver<T> implements MaybeObserver<T>, Disposable {
        final MaybeObserver<? super T> downstream;
        final Function<? super Throwable, ? extends T> itemSupplier;
        Disposable upstream;

        public OnErrorReturnMaybeObserver(MaybeObserver<? super T> actual, Function<? super Throwable, ? extends T> valueSupplier) {
            this.downstream = actual;
            this.itemSupplier = valueSupplier;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.upstream.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            try {
                T tApply = this.itemSupplier.apply(e2);
                Objects.requireNonNull(tApply, "The itemSupplier returned a null value");
                this.downstream.onSuccess(tApply);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(new CompositeException(e2, th));
            }
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
            this.downstream.onSuccess(value);
        }
    }

    public MaybeOnErrorReturn(MaybeSource<T> source, Function<? super Throwable, ? extends T> itemSupplier) {
        super(source);
        this.itemSupplier = itemSupplier;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    public void subscribeActual(MaybeObserver<? super T> observer) {
        this.source.subscribe(new OnErrorReturnMaybeObserver(observer, this.itemSupplier));
    }
}
