package io.reactivex.rxjava3.internal.operators.observable;

import MTT.ThirdAppInfoNew;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.operators.QueueDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes8.dex */
public final class ObservableScalarXMap {

    public static final class ScalarDisposable<T> extends AtomicInteger implements QueueDisposable<T>, Runnable {
        static final int FUSED = 1;
        static final int ON_COMPLETE = 3;
        static final int ON_NEXT = 2;
        static final int START = 0;
        private static final long serialVersionUID = 3880992722410194083L;
        final Observer<? super T> observer;
        final T value;

        public ScalarDisposable(Observer<? super T> observer, T value) {
            this.observer = observer;
            this.value = value;
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public void clear() {
            lazySet(3);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            set(3);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get() == 3;
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean isEmpty() {
            return get() != 1;
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean offer(T value) {
            throw new UnsupportedOperationException("Should not be called!");
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public T poll() {
            if (get() != 1) {
                return null;
            }
            lazySet(3);
            return this.value;
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int mode) {
            if ((mode & 1) == 0) {
                return 0;
            }
            lazySet(1);
            return 1;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (get() == 0 && compareAndSet(0, 2)) {
                this.observer.onNext(this.value);
                if (get() == 2) {
                    lazySet(3);
                    this.observer.onComplete();
                }
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean offer(T v12, T v2) {
            throw new UnsupportedOperationException("Should not be called!");
        }
    }

    public static final class ScalarXMapObservable<T, R> extends Observable<R> {
        final Function<? super T, ? extends ObservableSource<? extends R>> mapper;
        final T value;

        public ScalarXMapObservable(T value, Function<? super T, ? extends ObservableSource<? extends R>> mapper) {
            this.value = value;
            this.mapper = mapper;
        }

        @Override // io.reactivex.rxjava3.core.Observable
        public void subscribeActual(Observer<? super R> observer) {
            try {
                ObservableSource<? extends R> observableSourceApply = this.mapper.apply(this.value);
                Objects.requireNonNull(observableSourceApply, "The mapper returned a null ObservableSource");
                ObservableSource<? extends R> observableSource = observableSourceApply;
                if (!(observableSource instanceof Supplier)) {
                    observableSource.subscribe(observer);
                    return;
                }
                try {
                    Object obj = ((Supplier) observableSource).get();
                    if (obj == null) {
                        EmptyDisposable.complete(observer);
                        return;
                    }
                    ScalarDisposable scalarDisposable = new ScalarDisposable(observer, obj);
                    observer.onSubscribe(scalarDisposable);
                    scalarDisposable.run();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    EmptyDisposable.error(th, observer);
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                EmptyDisposable.error(th2, observer);
            }
        }
    }

    private ObservableScalarXMap() {
        throw new IllegalStateException("No instances!");
    }

    public static <T, U> Observable<U> scalarXMap(T value, Function<? super T, ? extends ObservableSource<? extends U>> mapper) {
        return RxJavaPlugins.onAssembly(new ScalarXMapObservable(value, mapper));
    }

    public static <T, R> boolean tryScalarXMapSubscribe(ObservableSource<T> observableSource, Observer<? super R> observer, Function<? super T, ? extends ObservableSource<? extends R>> function) {
        if (!(observableSource instanceof Supplier)) {
            return false;
        }
        try {
            ThirdAppInfoNew thirdAppInfoNew = (Object) ((Supplier) observableSource).get();
            if (thirdAppInfoNew == null) {
                EmptyDisposable.complete(observer);
                return true;
            }
            try {
                ObservableSource<? extends R> observableSourceApply = function.apply(thirdAppInfoNew);
                Objects.requireNonNull(observableSourceApply, "The mapper returned a null ObservableSource");
                ObservableSource<? extends R> observableSource2 = observableSourceApply;
                if (observableSource2 instanceof Supplier) {
                    try {
                        Object obj = ((Supplier) observableSource2).get();
                        if (obj == null) {
                            EmptyDisposable.complete(observer);
                            return true;
                        }
                        ScalarDisposable scalarDisposable = new ScalarDisposable(observer, obj);
                        observer.onSubscribe(scalarDisposable);
                        scalarDisposable.run();
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        EmptyDisposable.error(th, observer);
                        return true;
                    }
                } else {
                    observableSource2.subscribe(observer);
                }
                return true;
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                EmptyDisposable.error(th2, observer);
                return true;
            }
        } catch (Throwable th3) {
            Exceptions.throwIfFatal(th3);
            EmptyDisposable.error(th3, observer);
            return true;
        }
    }
}
