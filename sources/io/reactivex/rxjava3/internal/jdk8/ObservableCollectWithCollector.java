package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.observers.DeferredScalarDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collector;

/* loaded from: classes8.dex */
public final class ObservableCollectWithCollector<T, A, R> extends Observable<R> {
    final Collector<? super T, A, R> collector;
    final Observable<T> source;

    public static final class CollectorObserver<T, A, R> extends DeferredScalarDisposable<R> implements Observer<T> {
        private static final long serialVersionUID = -229544830565448758L;
        final BiConsumer<A, T> accumulator;
        A container;
        boolean done;
        final Function<A, R> finisher;
        Disposable upstream;

        public CollectorObserver(Observer<? super R> downstream, A container, BiConsumer<A, T> accumulator, Function<A, R> finisher) {
            super(downstream);
            this.container = container;
            this.accumulator = accumulator;
            this.finisher = finisher;
        }

        @Override // io.reactivex.rxjava3.internal.observers.DeferredScalarDisposable, io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            super.dispose();
            this.upstream.dispose();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.upstream = DisposableHelper.DISPOSED;
            A a3 = this.container;
            this.container = null;
            try {
                Object objApply = this.finisher.apply(a3);
                Objects.requireNonNull(objApply, "The finisher returned a null value");
                complete(objApply);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            if (this.done) {
                RxJavaPlugins.onError(t2);
                return;
            }
            this.done = true;
            this.upstream = DisposableHelper.DISPOSED;
            this.container = null;
            this.downstream.onError(t2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            try {
                this.accumulator.accept(this.container, t2);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.dispose();
                onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(@NonNull Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                this.downstream.onSubscribe(this);
            }
        }
    }

    public ObservableCollectWithCollector(Observable<T> source, Collector<? super T, A, R> collector) {
        this.source = source;
        this.collector = collector;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(@NonNull Observer<? super R> observer) {
        try {
            this.source.subscribe(new CollectorObserver(observer, this.collector.supplier().get(), this.collector.accumulator(), this.collector.finisher()));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, observer);
        }
    }
}
