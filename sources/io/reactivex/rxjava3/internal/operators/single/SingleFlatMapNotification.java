package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class SingleFlatMapNotification<T, R> extends Single<R> {
    final Function<? super Throwable, ? extends SingleSource<? extends R>> onErrorMapper;
    final Function<? super T, ? extends SingleSource<? extends R>> onSuccessMapper;
    final SingleSource<T> source;

    public static final class FlatMapSingleObserver<T, R> extends AtomicReference<Disposable> implements SingleObserver<T>, Disposable {
        private static final long serialVersionUID = 4375739915521278546L;
        final SingleObserver<? super R> downstream;
        final Function<? super Throwable, ? extends SingleSource<? extends R>> onErrorMapper;
        final Function<? super T, ? extends SingleSource<? extends R>> onSuccessMapper;
        Disposable upstream;

        public final class InnerObserver implements SingleObserver<R> {
            public InnerObserver() {
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
            public void onError(Throwable e2) {
                FlatMapSingleObserver.this.downstream.onError(e2);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
            public void onSubscribe(Disposable d3) {
                DisposableHelper.setOnce(FlatMapSingleObserver.this, d3);
            }

            @Override // io.reactivex.rxjava3.core.SingleObserver
            public void onSuccess(R value) {
                FlatMapSingleObserver.this.downstream.onSuccess(value);
            }
        }

        public FlatMapSingleObserver(SingleObserver<? super R> actual, Function<? super T, ? extends SingleSource<? extends R>> onSuccessMapper, Function<? super Throwable, ? extends SingleSource<? extends R>> onErrorMapper) {
            this.downstream = actual;
            this.onSuccessMapper = onSuccessMapper;
            this.onErrorMapper = onErrorMapper;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
            this.upstream.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            try {
                SingleSource<? extends R> singleSourceApply = this.onErrorMapper.apply(e2);
                Objects.requireNonNull(singleSourceApply, "The onErrorMapper returned a null SingleSource");
                SingleSource<? extends R> singleSource = singleSourceApply;
                if (isDisposed()) {
                    return;
                }
                singleSource.subscribe(new InnerObserver());
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(new CompositeException(e2, th));
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T value) {
            try {
                SingleSource<? extends R> singleSourceApply = this.onSuccessMapper.apply(value);
                Objects.requireNonNull(singleSourceApply, "The onSuccessMapper returned a null SingleSource");
                SingleSource<? extends R> singleSource = singleSourceApply;
                if (isDisposed()) {
                    return;
                }
                singleSource.subscribe(new InnerObserver());
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
            }
        }
    }

    public SingleFlatMapNotification(SingleSource<T> source, Function<? super T, ? extends SingleSource<? extends R>> onSuccessMapper, Function<? super Throwable, ? extends SingleSource<? extends R>> onErrorMapper) {
        this.source = source;
        this.onSuccessMapper = onSuccessMapper;
        this.onErrorMapper = onErrorMapper;
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(SingleObserver<? super R> observer) {
        this.source.subscribe(new FlatMapSingleObserver(observer, this.onSuccessMapper, this.onErrorMapper));
    }
}
