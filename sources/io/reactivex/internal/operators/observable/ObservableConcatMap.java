package io.reactivex.internal.operators.observable;

import MTT.ThirdAppInfoNew;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
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
        final AtomicThrowable error = new AtomicThrowable();
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

            public DelayErrorInnerObserver(Observer<? super R> observer, ConcatMapDelayErrorObserver<?, R> concatMapDelayErrorObserver) {
                this.downstream = observer;
                this.parent = concatMapDelayErrorObserver;
            }

            public void dispose() {
                DisposableHelper.dispose(this);
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                ConcatMapDelayErrorObserver<?, R> concatMapDelayErrorObserver = this.parent;
                concatMapDelayErrorObserver.active = false;
                concatMapDelayErrorObserver.drain();
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                ConcatMapDelayErrorObserver<?, R> concatMapDelayErrorObserver = this.parent;
                if (!concatMapDelayErrorObserver.error.addThrowable(th)) {
                    RxJavaPlugins.onError(th);
                    return;
                }
                if (!concatMapDelayErrorObserver.tillTheEnd) {
                    concatMapDelayErrorObserver.upstream.dispose();
                }
                concatMapDelayErrorObserver.active = false;
                concatMapDelayErrorObserver.drain();
            }

            @Override // io.reactivex.Observer
            public void onNext(R r2) {
                this.downstream.onNext(r2);
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.replace(this, disposable);
            }
        }

        public ConcatMapDelayErrorObserver(Observer<? super R> observer, Function<? super T, ? extends ObservableSource<? extends R>> function, int i2, boolean z2) {
            this.downstream = observer;
            this.mapper = function;
            this.bufferSize = i2;
            this.tillTheEnd = z2;
            this.observer = new DelayErrorInnerObserver<>(observer, this);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.cancelled = true;
            this.upstream.dispose();
            this.observer.dispose();
        }

        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            Observer<? super R> observer = this.downstream;
            SimpleQueue<T> simpleQueue = this.queue;
            AtomicThrowable atomicThrowable = this.error;
            while (true) {
                if (!this.active) {
                    if (this.cancelled) {
                        simpleQueue.clear();
                        return;
                    }
                    if (!this.tillTheEnd && atomicThrowable.get() != null) {
                        simpleQueue.clear();
                        this.cancelled = true;
                        observer.onError(atomicThrowable.terminate());
                        return;
                    }
                    boolean z2 = this.done;
                    try {
                        T tPoll = simpleQueue.poll();
                        boolean z3 = tPoll == null;
                        if (z2 && z3) {
                            this.cancelled = true;
                            Throwable thTerminate = atomicThrowable.terminate();
                            if (thTerminate != null) {
                                observer.onError(thTerminate);
                                return;
                            } else {
                                observer.onComplete();
                                return;
                            }
                        }
                        if (!z3) {
                            try {
                                ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.mapper.apply(tPoll), "The mapper returned a null ObservableSource");
                                if (observableSource instanceof Callable) {
                                    try {
                                        ThirdAppInfoNew thirdAppInfoNew = (Object) ((Callable) observableSource).call();
                                        if (thirdAppInfoNew != null && !this.cancelled) {
                                            observer.onNext(thirdAppInfoNew);
                                        }
                                    } catch (Throwable th) {
                                        Exceptions.throwIfFatal(th);
                                        atomicThrowable.addThrowable(th);
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
                                atomicThrowable.addThrowable(th2);
                                observer.onError(atomicThrowable.terminate());
                                return;
                            }
                        }
                    } catch (Throwable th3) {
                        Exceptions.throwIfFatal(th3);
                        this.cancelled = true;
                        this.upstream.dispose();
                        atomicThrowable.addThrowable(th3);
                        observer.onError(atomicThrowable.terminate());
                        return;
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.done = true;
            drain();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (!this.error.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else {
                this.done = true;
                drain();
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t2) {
            if (this.sourceMode == 0) {
                this.queue.offer(t2);
            }
            drain();
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                if (disposable instanceof QueueDisposable) {
                    QueueDisposable queueDisposable = (QueueDisposable) disposable;
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

            public InnerObserver(Observer<? super U> observer, SourceObserver<?, ?> sourceObserver) {
                this.downstream = observer;
                this.parent = sourceObserver;
            }

            public void dispose() {
                DisposableHelper.dispose(this);
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                this.parent.innerComplete();
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                this.parent.dispose();
                this.downstream.onError(th);
            }

            @Override // io.reactivex.Observer
            public void onNext(U u2) {
                this.downstream.onNext(u2);
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.replace(this, disposable);
            }
        }

        public SourceObserver(Observer<? super U> observer, Function<? super T, ? extends ObservableSource<? extends U>> function, int i2) {
            this.downstream = observer;
            this.mapper = function;
            this.bufferSize = i2;
            this.inner = new InnerObserver<>(observer, this);
        }

        @Override // io.reactivex.disposables.Disposable
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
                        } else if (!z3) {
                            try {
                                ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.mapper.apply(tPoll), "The mapper returned a null ObservableSource");
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

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.disposed;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            drain();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            dispose();
            this.downstream.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            if (this.fusionMode == 0) {
                this.queue.offer(t2);
            }
            drain();
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                if (disposable instanceof QueueDisposable) {
                    QueueDisposable queueDisposable = (QueueDisposable) disposable;
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

    public ObservableConcatMap(ObservableSource<T> observableSource, Function<? super T, ? extends ObservableSource<? extends U>> function, int i2, ErrorMode errorMode) {
        super(observableSource);
        this.mapper = function;
        this.delayErrors = errorMode;
        this.bufferSize = Math.max(8, i2);
    }

    @Override // io.reactivex.Observable
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
