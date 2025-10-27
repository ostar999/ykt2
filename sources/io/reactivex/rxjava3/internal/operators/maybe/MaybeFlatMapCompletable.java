package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class MaybeFlatMapCompletable<T> extends Completable {
    final Function<? super T, ? extends CompletableSource> mapper;
    final MaybeSource<T> source;

    public static final class FlatMapCompletableObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T>, CompletableObserver, Disposable {
        private static final long serialVersionUID = -2177128922851101253L;
        final CompletableObserver downstream;
        final Function<? super T, ? extends CompletableSource> mapper;

        public FlatMapCompletableObserver(CompletableObserver actual, Function<? super T, ? extends CompletableSource> mapper) {
            this.downstream = actual;
            this.mapper = mapper;
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
            this.downstream.onError(e2);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            DisposableHelper.replace(this, d3);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T value) {
            try {
                CompletableSource completableSourceApply = this.mapper.apply(value);
                Objects.requireNonNull(completableSourceApply, "The mapper returned a null CompletableSource");
                CompletableSource completableSource = completableSourceApply;
                if (isDisposed()) {
                    return;
                }
                completableSource.subscribe(this);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                onError(th);
            }
        }
    }

    public MaybeFlatMapCompletable(MaybeSource<T> source, Function<? super T, ? extends CompletableSource> mapper) {
        this.source = source;
        this.mapper = mapper;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    public void subscribeActual(CompletableObserver observer) {
        FlatMapCompletableObserver flatMapCompletableObserver = new FlatMapCompletableObserver(observer, this.mapper);
        observer.onSubscribe(flatMapCompletableObserver);
        this.source.subscribe(flatMapCompletableObserver);
    }
}
