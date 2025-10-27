package io.reactivex.rxjava3.internal.operators.observable;

import MTT.ThirdAppInfoNew;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import io.reactivex.rxjava3.observers.SerializedObserver;
import io.reactivex.rxjava3.operators.QueueDisposable;
import io.reactivex.rxjava3.operators.SimpleQueue;
import io.reactivex.rxjava3.operators.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class ObservableConcatMap<T, U> extends AbstractObservableWithUpstream<T, U> {
    final int bufferSize;
    final ErrorMode delayErrors;
    final Function<? super T, ? extends ObservableSource<? extends U>> mapper;

    public static final class ConcatMapDelayErrorObserver<T, R> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = -6951100001833242599L;
        volatile boolean active;
        final int bufferSize;
        volatile boolean cancelled;
        volatile boolean done;
        final Observer<? super R> downstream;
        final AtomicThrowable errors = new AtomicThrowable();
        final Function<? super T, ? extends ObservableSource<? extends R>> mapper;
        final DelayErrorInnerObserver<R> observer;
        SimpleQueue<T> queue;
        int sourceMode;
        final boolean tillTheEnd;
        Disposable upstream;

        public static final class DelayErrorInnerObserver<R> extends AtomicReference<Disposable> implements Observer<R> {
            private static final long serialVersionUID = 2620149119579502636L;
            final Observer<? super R> downstream;
            final ConcatMapDelayErrorObserver<?, R> parent;

            public DelayErrorInnerObserver(Observer<? super R> actual, ConcatMapDelayErrorObserver<?, R> parent) {
                this.downstream = actual;
                this.parent = parent;
            }

            public void dispose() {
                DisposableHelper.dispose(this);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onComplete() {
                ConcatMapDelayErrorObserver<?, R> concatMapDelayErrorObserver = this.parent;
                concatMapDelayErrorObserver.active = false;
                concatMapDelayErrorObserver.drain();
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onError(Throwable e2) {
                ConcatMapDelayErrorObserver<?, R> concatMapDelayErrorObserver = this.parent;
                if (concatMapDelayErrorObserver.errors.tryAddThrowableOrReport(e2)) {
                    if (!concatMapDelayErrorObserver.tillTheEnd) {
                        concatMapDelayErrorObserver.upstream.dispose();
                    }
                    concatMapDelayErrorObserver.active = false;
                    concatMapDelayErrorObserver.drain();
                }
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onNext(R value) {
                this.downstream.onNext(value);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onSubscribe(Disposable d3) {
                DisposableHelper.replace(this, d3);
            }
        }

        public ConcatMapDelayErrorObserver(Observer<? super R> actual, Function<? super T, ? extends ObservableSource<? extends R>> mapper, int bufferSize, boolean tillTheEnd) {
            this.downstream = actual;
            this.mapper = mapper;
            this.bufferSize = bufferSize;
            this.tillTheEnd = tillTheEnd;
            this.observer = new DelayErrorInnerObserver<>(actual, this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.cancelled = true;
            this.upstream.dispose();
            this.observer.dispose();
            this.errors.tryTerminateAndReport();
        }

        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            Observer<? super R> observer = this.downstream;
            SimpleQueue<T> simpleQueue = this.queue;
            AtomicThrowable atomicThrowable = this.errors;
            while (true) {
                if (!this.active) {
                    if (this.cancelled) {
                        simpleQueue.clear();
                        return;
                    }
                    if (!this.tillTheEnd && atomicThrowable.get() != null) {
                        simpleQueue.clear();
                        this.cancelled = true;
                        atomicThrowable.tryTerminateConsumer(observer);
                        return;
                    }
                    boolean z2 = this.done;
                    try {
                        T tPoll = simpleQueue.poll();
                        boolean z3 = tPoll == null;
                        if (z2 && z3) {
                            this.cancelled = true;
                            atomicThrowable.tryTerminateConsumer(observer);
                            return;
                        }
                        if (!z3) {
                            try {
                                ObservableSource<? extends R> observableSourceApply = this.mapper.apply(tPoll);
                                Objects.requireNonNull(observableSourceApply, "The mapper returned a null ObservableSource");
                                ObservableSource<? extends R> observableSource = observableSourceApply;
                                if (observableSource instanceof Supplier) {
                                    try {
                                        ThirdAppInfoNew thirdAppInfoNew = (Object) ((Supplier) observableSource).get();
                                        if (thirdAppInfoNew != null && !this.cancelled) {
                                            observer.onNext(thirdAppInfoNew);
                                        }
                                    } catch (Throwable th) {
                                        Exceptions.throwIfFatal(th);
                                        atomicThrowable.tryAddThrowableOrReport(th);
                                    }
                                } else {
                                    this.active = true;
                                    observableSource.subscribe(this.observer);
                                }
                            } catch (Throwable th2) {
                                Exceptions.throwIfFatal(th2);
                                this.cancelled = true;
                                this.upstream.dispose();
                                simpleQueue.clear();
                                atomicThrowable.tryAddThrowableOrReport(th2);
                                atomicThrowable.tryTerminateConsumer(observer);
                                return;
                            }
                        }
                    } catch (Throwable th3) {
                        Exceptions.throwIfFatal(th3);
                        this.cancelled = true;
                        this.upstream.dispose();
                        atomicThrowable.tryAddThrowableOrReport(th3);
                        atomicThrowable.tryTerminateConsumer(observer);
                        return;
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.done = true;
            drain();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable e2) {
            if (this.errors.tryAddThrowableOrReport(e2)) {
                this.done = true;
                drain();
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T value) {
            if (this.sourceMode == 0) {
                this.queue.offer(value);
            }
            drain();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                if (d3 instanceof QueueDisposable) {
                    QueueDisposable queueDisposable = (QueueDisposable) d3;
                    int iRequestFusion = queueDisposable.requestFusion(3);
                    if (iRequestFusion == 1) {
                        this.sourceMode = iRequestFusion;
                        this.queue = queueDisposable;
                        this.done = true;
                        this.downstream.onSubscribe(this);
                        drain();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.sourceMode = iRequestFusion;
                        this.queue = queueDisposable;
                        this.downstream.onSubscribe(this);
                        return;
                    }
                }
                this.queue = new SpscLinkedArrayQueue(this.bufferSize);
                this.downstream.onSubscribe(this);
            }
        }
    }

    public static final class SourceObserver<T, U> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = 8828587559905699186L;
        volatile boolean active;
        final int bufferSize;
        volatile boolean disposed;
        volatile boolean done;
        final Observer<? super U> downstream;
        int fusionMode;
        final InnerObserver<U> inner;
        final Function<? super T, ? extends ObservableSource<? extends U>> mapper;
        SimpleQueue<T> queue;
        Disposable upstream;

        public static final class InnerObserver<U> extends AtomicReference<Disposable> implements Observer<U> {
            private static final long serialVersionUID = -7449079488798789337L;
            final Observer<? super U> downstream;
            final SourceObserver<?, ?> parent;

            public InnerObserver(Observer<? super U> actual, SourceObserver<?, ?> parent) {
                this.downstream = actual;
                this.parent = parent;
            }

            public void dispose() {
                DisposableHelper.dispose(this);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onComplete() {
                this.parent.innerComplete();
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onError(Throwable t2) {
                this.parent.dispose();
                this.downstream.onError(t2);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onNext(U t2) {
                this.downstream.onNext(t2);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onSubscribe(Disposable d3) {
                DisposableHelper.replace(this, d3);
            }
        }

        public SourceObserver(Observer<? super U> actual, Function<? super T, ? extends ObservableSource<? extends U>> mapper, int bufferSize) {
            this.downstream = actual;
            this.mapper = mapper;
            this.bufferSize = bufferSize;
            this.inner = new InnerObserver<>(actual, this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.disposed = true;
            this.inner.dispose();
            this.upstream.dispose();
            if (getAndIncrement() == 0) {
                this.queue.clear();
            }
        }

        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            while (!this.disposed) {
                if (!this.active) {
                    boolean z2 = this.done;
                    try {
                        T tPoll = this.queue.poll();
                        boolean z3 = tPoll == null;
                        if (z2 && z3) {
                            this.disposed = true;
                            this.downstream.onComplete();
                            return;
                        }
                        if (!z3) {
                            try {
                                ObservableSource<? extends U> observableSourceApply = this.mapper.apply(tPoll);
                                Objects.requireNonNull(observableSourceApply, "The mapper returned a null ObservableSource");
                                ObservableSource<? extends U> observableSource = observableSourceApply;
                                this.active = true;
                                observableSource.subscribe(this.inner);
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                dispose();
                                this.queue.clear();
                                this.downstream.onError(th);
                                return;
                            }
                        }
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        dispose();
                        this.queue.clear();
                        this.downstream.onError(th2);
                        return;
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            }
            this.queue.clear();
        }

        public void innerComplete() {
            this.active = false;
            drain();
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
            drain();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            if (this.done) {
                RxJavaPlugins.onError(t2);
                return;
            }
            this.done = true;
            dispose();
            this.downstream.onError(t2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            if (this.fusionMode == 0) {
                this.queue.offer(t2);
            }
            drain();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                if (d3 instanceof QueueDisposable) {
                    QueueDisposable queueDisposable = (QueueDisposable) d3;
                    int iRequestFusion = queueDisposable.requestFusion(3);
                    if (iRequestFusion == 1) {
                        this.fusionMode = iRequestFusion;
                        this.queue = queueDisposable;
                        this.done = true;
                        this.downstream.onSubscribe(this);
                        drain();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.fusionMode = iRequestFusion;
                        this.queue = queueDisposable;
                        this.downstream.onSubscribe(this);
                        return;
                    }
                }
                this.queue = new SpscLinkedArrayQueue(this.bufferSize);
                this.downstream.onSubscribe(this);
            }
        }
    }

    public ObservableConcatMap(ObservableSource<T> source, Function<? super T, ? extends ObservableSource<? extends U>> mapper, int bufferSize, ErrorMode delayErrors) {
        super(source);
        this.mapper = mapper;
        this.delayErrors = delayErrors;
        this.bufferSize = Math.max(8, bufferSize);
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super U> observer) {
        if (ObservableScalarXMap.tryScalarXMapSubscribe(this.source, observer, this.mapper)) {
            return;
        }
        if (this.delayErrors == ErrorMode.IMMEDIATE) {
            this.source.subscribe(new SourceObserver(new SerializedObserver(observer), this.mapper, this.bufferSize));
        } else {
            this.source.subscribe(new ConcatMapDelayErrorObserver(observer, this.mapper, this.bufferSize, this.delayErrors == ErrorMode.END));
        }
    }
}
