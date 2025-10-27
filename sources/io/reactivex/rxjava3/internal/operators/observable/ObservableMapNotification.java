package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.Objects;

/* loaded from: classes8.dex */
public final class ObservableMapNotification<T, R> extends AbstractObservableWithUpstream<T, ObservableSource<? extends R>> {
    final Supplier<? extends ObservableSource<? extends R>> onCompleteSupplier;
    final Function<? super Throwable, ? extends ObservableSource<? extends R>> onErrorMapper;
    final Function<? super T, ? extends ObservableSource<? extends R>> onNextMapper;

    public static final class MapNotificationObserver<T, R> implements Observer<T>, Disposable {
        final Observer<? super ObservableSource<? extends R>> downstream;
        final Supplier<? extends ObservableSource<? extends R>> onCompleteSupplier;
        final Function<? super Throwable, ? extends ObservableSource<? extends R>> onErrorMapper;
        final Function<? super T, ? extends ObservableSource<? extends R>> onNextMapper;
        Disposable upstream;

        public MapNotificationObserver(Observer<? super ObservableSource<? extends R>> actual, Function<? super T, ? extends ObservableSource<? extends R>> onNextMapper, Function<? super Throwable, ? extends ObservableSource<? extends R>> onErrorMapper, Supplier<? extends ObservableSource<? extends R>> onCompleteSupplier) {
            this.downstream = actual;
            this.onNextMapper = onNextMapper;
            this.onErrorMapper = onErrorMapper;
            this.onCompleteSupplier = onCompleteSupplier;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.upstream.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            try {
                ObservableSource<? extends R> observableSource = this.onCompleteSupplier.get();
                Objects.requireNonNull(observableSource, "The onComplete ObservableSource returned is null");
                this.downstream.onNext(observableSource);
                this.downstream.onComplete();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            try {
                ObservableSource<? extends R> observableSourceApply = this.onErrorMapper.apply(t2);
                Objects.requireNonNull(observableSourceApply, "The onError ObservableSource returned is null");
                this.downstream.onNext(observableSourceApply);
                this.downstream.onComplete();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(new CompositeException(t2, th));
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            try {
                ObservableSource<? extends R> observableSourceApply = this.onNextMapper.apply(t2);
                Objects.requireNonNull(observableSourceApply, "The onNext ObservableSource returned is null");
                this.downstream.onNext(observableSourceApply);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                this.downstream.onSubscribe(this);
            }
        }
    }

    public ObservableMapNotification(ObservableSource<T> source, Function<? super T, ? extends ObservableSource<? extends R>> onNextMapper, Function<? super Throwable, ? extends ObservableSource<? extends R>> onErrorMapper, Supplier<? extends ObservableSource<? extends R>> onCompleteSupplier) {
        super(source);
        this.onNextMapper = onNextMapper;
        this.onErrorMapper = onErrorMapper;
        this.onCompleteSupplier = onCompleteSupplier;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super ObservableSource<? extends R>> t2) {
        this.source.subscribe(new MapNotificationObserver(t2, this.onNextMapper, this.onErrorMapper, this.onCompleteSupplier));
    }
}
