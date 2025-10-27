package io.reactivex.rxjava3.internal.operators.mixed;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import io.reactivex.rxjava3.operators.SimpleQueue;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class ObservableConcatMapCompletable<T> extends Completable {
    final ErrorMode errorMode;
    final Function<? super T, ? extends CompletableSource> mapper;
    final int prefetch;
    final Observable<T> source;

    public static final class ConcatMapCompletableObserver<T> extends ConcatMapXMainObserver<T> {
        private static final long serialVersionUID = 3610901111000061034L;
        volatile boolean active;
        final CompletableObserver downstream;
        final ConcatMapInnerObserver inner;
        final Function<? super T, ? extends CompletableSource> mapper;

        public static final class ConcatMapInnerObserver extends AtomicReference<Disposable> implements CompletableObserver {
            private static final long serialVersionUID = 5638352172918776687L;
            final ConcatMapCompletableObserver<?> parent;

            public ConcatMapInnerObserver(ConcatMapCompletableObserver<?> parent) {
                this.parent = parent;
            }

            public void dispose() {
                DisposableHelper.dispose(this);
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onComplete() {
                this.parent.innerComplete();
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onError(Throwable e2) {
                this.parent.innerError(e2);
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onSubscribe(Disposable d3) {
                DisposableHelper.replace(this, d3);
            }
        }

        public ConcatMapCompletableObserver(CompletableObserver downstream, Function<? super T, ? extends CompletableSource> mapper, ErrorMode errorMode, int prefetch) {
            super(prefetch, errorMode);
            this.downstream = downstream;
            this.mapper = mapper;
            this.inner = new ConcatMapInnerObserver(this);
        }

        @Override // io.reactivex.rxjava3.internal.operators.mixed.ConcatMapXMainObserver
        public void disposeInner() {
            this.inner.dispose();
        }

        @Override // io.reactivex.rxjava3.internal.operators.mixed.ConcatMapXMainObserver
        public void drain() {
            CompletableSource completableSource;
            boolean z2;
            if (getAndIncrement() != 0) {
                return;
            }
            AtomicThrowable atomicThrowable = this.errors;
            ErrorMode errorMode = this.errorMode;
            SimpleQueue<T> simpleQueue = this.queue;
            while (!this.disposed) {
                if (atomicThrowable.get() != null && (errorMode == ErrorMode.IMMEDIATE || (errorMode == ErrorMode.BOUNDARY && !this.active))) {
                    this.disposed = true;
                    simpleQueue.clear();
                    atomicThrowable.tryTerminateConsumer(this.downstream);
                    return;
                }
                if (!this.active) {
                    boolean z3 = this.done;
                    try {
                        T tPoll = simpleQueue.poll();
                        if (tPoll != null) {
                            CompletableSource completableSourceApply = this.mapper.apply(tPoll);
                            Objects.requireNonNull(completableSourceApply, "The mapper returned a null CompletableSource");
                            completableSource = completableSourceApply;
                            z2 = false;
                        } else {
                            completableSource = null;
                            z2 = true;
                        }
                        if (z3 && z2) {
                            this.disposed = true;
                            atomicThrowable.tryTerminateConsumer(this.downstream);
                            return;
                        } else if (!z2) {
                            this.active = true;
                            completableSource.subscribe(this.inner);
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.disposed = true;
                        simpleQueue.clear();
                        this.upstream.dispose();
                        atomicThrowable.tryAddThrowableOrReport(th);
                        atomicThrowable.tryTerminateConsumer(this.downstream);
                        return;
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            }
            simpleQueue.clear();
        }

        public void innerComplete() {
            this.active = false;
            drain();
        }

        public void innerError(Throwable ex) {
            if (this.errors.tryAddThrowableOrReport(ex)) {
                if (this.errorMode != ErrorMode.END) {
                    this.upstream.dispose();
                }
                this.active = false;
                drain();
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.mixed.ConcatMapXMainObserver
        public void onSubscribeDownstream() {
            this.downstream.onSubscribe(this);
        }
    }

    public ObservableConcatMapCompletable(Observable<T> source, Function<? super T, ? extends CompletableSource> mapper, ErrorMode errorMode, int prefetch) {
        this.source = source;
        this.mapper = mapper;
        this.errorMode = errorMode;
        this.prefetch = prefetch;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    public void subscribeActual(CompletableObserver observer) {
        if (ScalarXMapZHelper.tryAsCompletable(this.source, this.mapper, observer)) {
            return;
        }
        this.source.subscribe(new ConcatMapCompletableObserver(observer, this.mapper, this.errorMode, this.prefetch));
    }
}
