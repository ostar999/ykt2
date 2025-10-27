package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/* loaded from: classes8.dex */
public final class ObservableFlatMapStream<T, R> extends Observable<R> {
    final Function<? super T, ? extends Stream<? extends R>> mapper;
    final Observable<T> source;

    public static final class FlatMapStreamObserver<T, R> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = -5127032662980523968L;
        volatile boolean disposed;
        boolean done;
        final Observer<? super R> downstream;
        final Function<? super T, ? extends Stream<? extends R>> mapper;
        Disposable upstream;

        public FlatMapStreamObserver(Observer<? super R> downstream, Function<? super T, ? extends Stream<? extends R>> mapper) {
            this.downstream = downstream;
            this.mapper = mapper;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.disposed = true;
            this.upstream.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.disposed;
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
        public void onError(@NonNull Throwable e2) {
            if (this.done) {
                RxJavaPlugins.onError(e2);
            } else {
                this.done = true;
                this.downstream.onError(e2);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(@NonNull T t2) {
            if (this.done) {
                return;
            }
            try {
                Stream<? extends R> streamApply = this.mapper.apply(t2);
                Objects.requireNonNull(streamApply, "The mapper returned a null Stream");
                Stream<? extends R> stream = streamApply;
                try {
                    Iterator it = stream.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        if (this.disposed) {
                            this.done = true;
                            break;
                        }
                        Object next = it.next();
                        Objects.requireNonNull(next, "The Stream's Iterator.next returned a null value");
                        if (this.disposed) {
                            this.done = true;
                            break;
                        }
                        this.downstream.onNext(next);
                        if (this.disposed) {
                            this.done = true;
                            break;
                        }
                    }
                    stream.close();
                } finally {
                }
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

    public ObservableFlatMapStream(Observable<T> source, Function<? super T, ? extends Stream<? extends R>> mapper) {
        this.source = source;
        this.mapper = mapper;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super R> observer) {
        Stream<? extends R> stream;
        Observable<T> observable = this.source;
        if (!(observable instanceof Supplier)) {
            observable.subscribe(new FlatMapStreamObserver(observer, this.mapper));
            return;
        }
        try {
            Object obj = ((Supplier) observable).get();
            if (obj != null) {
                Stream<? extends R> streamApply = this.mapper.apply(obj);
                Objects.requireNonNull(streamApply, "The mapper returned a null Stream");
                stream = streamApply;
            } else {
                stream = null;
            }
            if (stream != null) {
                ObservableFromStream.subscribeStream(observer, stream);
            } else {
                EmptyDisposable.complete(observer);
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, observer);
        }
    }
}
