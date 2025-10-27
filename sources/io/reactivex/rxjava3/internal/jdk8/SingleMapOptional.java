package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.Objects;
import java.util.Optional;

/* loaded from: classes8.dex */
public final class SingleMapOptional<T, R> extends Maybe<R> {
    final Function<? super T, Optional<? extends R>> mapper;
    final Single<T> source;

    public static final class MapOptionalSingleObserver<T, R> implements SingleObserver<T>, Disposable {
        final MaybeObserver<? super R> downstream;
        final Function<? super T, Optional<? extends R>> mapper;
        Disposable upstream;

        public MapOptionalSingleObserver(MaybeObserver<? super R> downstream, Function<? super T, Optional<? extends R>> mapper) {
            this.downstream = downstream;
            this.mapper = mapper;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            Disposable disposable = this.upstream;
            this.upstream = DisposableHelper.DISPOSED;
            disposable.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            this.downstream.onError(e2);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T t2) {
            try {
                Optional<? extends R> optionalApply = this.mapper.apply(t2);
                Objects.requireNonNull(optionalApply, "The mapper returned a null item");
                Optional<? extends R> optional = optionalApply;
                if (optional.isPresent()) {
                    this.downstream.onSuccess((Object) optional.get());
                } else {
                    this.downstream.onComplete();
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
            }
        }
    }

    public SingleMapOptional(Single<T> source, Function<? super T, Optional<? extends R>> mapper) {
        this.source = source;
        this.mapper = mapper;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    public void subscribeActual(MaybeObserver<? super R> observer) {
        this.source.subscribe(new MapOptionalSingleObserver(observer, this.mapper));
    }
}
