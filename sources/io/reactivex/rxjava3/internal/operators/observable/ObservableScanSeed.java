package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;

/* loaded from: classes8.dex */
public final class ObservableScanSeed<T, R> extends AbstractObservableWithUpstream<T, R> {
    final BiFunction<R, ? super T, R> accumulator;
    final Supplier<R> seedSupplier;

    public static final class ScanSeedObserver<T, R> implements Observer<T>, Disposable {
        final BiFunction<R, ? super T, R> accumulator;
        boolean done;
        final Observer<? super R> downstream;
        Disposable upstream;
        R value;

        public ScanSeedObserver(Observer<? super R> actual, BiFunction<R, ? super T, R> accumulator, R value) {
            this.downstream = actual;
            this.accumulator = accumulator;
            this.value = value;
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
            if (this.done) {
                return;
            }
            this.done = true;
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            if (this.done) {
                RxJavaPlugins.onError(t2);
            } else {
                this.done = true;
                this.downstream.onError(t2);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            try {
                R rApply = this.accumulator.apply(this.value, t2);
                Objects.requireNonNull(rApply, "The accumulator returned a null value");
                this.value = rApply;
                this.downstream.onNext(rApply);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.dispose();
                onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
                this.downstream.onNext(this.value);
            }
        }
    }

    public ObservableScanSeed(ObservableSource<T> source, Supplier<R> seedSupplier, BiFunction<R, ? super T, R> accumulator) {
        super(source);
        this.accumulator = accumulator;
        this.seedSupplier = seedSupplier;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super R> t2) {
        try {
            R r2 = this.seedSupplier.get();
            Objects.requireNonNull(r2, "The seed supplied is null");
            this.source.subscribe(new ScanSeedObserver(t2, this.accumulator, r2));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, t2);
        }
    }
}
