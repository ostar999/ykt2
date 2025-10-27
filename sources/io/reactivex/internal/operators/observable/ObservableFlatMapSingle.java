package io.reactivex.internal.operators.observable;

import MTT.ThirdAppInfoNew;
import androidx.camera.view.j;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class ObservableFlatMapSingle<T, R> extends AbstractObservableWithUpstream<T, R> {
    final boolean delayErrors;
    final Function<? super T, ? extends SingleSource<? extends R>> mapper;

    public static final class FlatMapSingleObserver<T, R> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = 8600231336733376951L;
        volatile boolean cancelled;
        final boolean delayErrors;
        final Observer<? super R> downstream;
        final Function<? super T, ? extends SingleSource<? extends R>> mapper;
        Disposable upstream;
        final CompositeDisposable set = new CompositeDisposable();
        final AtomicThrowable errors = new AtomicThrowable();
        final AtomicInteger active = new AtomicInteger(1);
        final AtomicReference<SpscLinkedArrayQueue<R>> queue = new AtomicReference<>();

        public final class InnerObserver extends AtomicReference<Disposable> implements SingleObserver<R>, Disposable {
            private static final long serialVersionUID = -502562646270949838L;

            public InnerObserver() {
            }

            @Override // io.reactivex.disposables.Disposable
            public void dispose() {
                DisposableHelper.dispose(this);
            }

            @Override // io.reactivex.disposables.Disposable
            public boolean isDisposed() {
                return DisposableHelper.isDisposed(get());
            }

            @Override // io.reactivex.SingleObserver
            public void onError(Throwable th) {
                FlatMapSingleObserver.this.innerError(this, th);
            }

            @Override // io.reactivex.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.SingleObserver
            public void onSuccess(R r2) {
                FlatMapSingleObserver.this.innerSuccess(this, r2);
            }
        }

        public FlatMapSingleObserver(Observer<? super R> observer, Function<? super T, ? extends SingleSource<? extends R>> function, boolean z2) {
            this.downstream = observer;
            this.mapper = function;
            this.delayErrors = z2;
        }

        public void clear() {
            SpscLinkedArrayQueue<R> spscLinkedArrayQueue = this.queue.get();
            if (spscLinkedArrayQueue != null) {
                spscLinkedArrayQueue.clear();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.cancelled = true;
            this.upstream.dispose();
            this.set.dispose();
        }

        public void drain() {
            if (getAndIncrement() == 0) {
                drainLoop();
            }
        }

        public void drainLoop() {
            Observer<? super R> observer = this.downstream;
            AtomicInteger atomicInteger = this.active;
            AtomicReference<SpscLinkedArrayQueue<R>> atomicReference = this.queue;
            int iAddAndGet = 1;
            while (!this.cancelled) {
                if (!this.delayErrors && this.errors.get() != null) {
                    Throwable thTerminate = this.errors.terminate();
                    clear();
                    observer.onError(thTerminate);
                    return;
                }
                boolean z2 = atomicInteger.get() == 0;
                SpscLinkedArrayQueue<R> spscLinkedArrayQueue = atomicReference.get();
                ThirdAppInfoNew thirdAppInfoNewPoll = spscLinkedArrayQueue != null ? spscLinkedArrayQueue.poll() : null;
                boolean z3 = thirdAppInfoNewPoll == null;
                if (z2 && z3) {
                    Throwable thTerminate2 = this.errors.terminate();
                    if (thTerminate2 != null) {
                        observer.onError(thTerminate2);
                        return;
                    } else {
                        observer.onComplete();
                        return;
                    }
                }
                if (z3) {
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    observer.onNext(thirdAppInfoNewPoll);
                }
            }
            clear();
        }

        public SpscLinkedArrayQueue<R> getOrCreateQueue() {
            SpscLinkedArrayQueue<R> spscLinkedArrayQueue;
            do {
                SpscLinkedArrayQueue<R> spscLinkedArrayQueue2 = this.queue.get();
                if (spscLinkedArrayQueue2 != null) {
                    return spscLinkedArrayQueue2;
                }
                spscLinkedArrayQueue = new SpscLinkedArrayQueue<>(Observable.bufferSize());
            } while (!j.a(this.queue, null, spscLinkedArrayQueue));
            return spscLinkedArrayQueue;
        }

        public void innerError(FlatMapSingleObserver<T, R>.InnerObserver innerObserver, Throwable th) {
            this.set.delete(innerObserver);
            if (!this.errors.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (!this.delayErrors) {
                this.upstream.dispose();
                this.set.dispose();
            }
            this.active.decrementAndGet();
            drain();
        }

        /* JADX WARN: Removed duplicated region for block: B:22:0x004e  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void innerSuccess(io.reactivex.internal.operators.observable.ObservableFlatMapSingle.FlatMapSingleObserver<T, R>.InnerObserver r3, R r4) {
            /*
                r2 = this;
                io.reactivex.disposables.CompositeDisposable r0 = r2.set
                r0.delete(r3)
                int r3 = r2.get()
                if (r3 != 0) goto L4e
                r3 = 0
                r0 = 1
                boolean r1 = r2.compareAndSet(r3, r0)
                if (r1 == 0) goto L4e
                io.reactivex.Observer<? super R> r1 = r2.downstream
                r1.onNext(r4)
                java.util.concurrent.atomic.AtomicInteger r4 = r2.active
                int r4 = r4.decrementAndGet()
                if (r4 != 0) goto L21
                r3 = r0
            L21:
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.queue.SpscLinkedArrayQueue<R>> r4 = r2.queue
                java.lang.Object r4 = r4.get()
                io.reactivex.internal.queue.SpscLinkedArrayQueue r4 = (io.reactivex.internal.queue.SpscLinkedArrayQueue) r4
                if (r3 == 0) goto L47
                if (r4 == 0) goto L33
                boolean r3 = r4.isEmpty()
                if (r3 == 0) goto L47
            L33:
                io.reactivex.internal.util.AtomicThrowable r3 = r2.errors
                java.lang.Throwable r3 = r3.terminate()
                if (r3 == 0) goto L41
                io.reactivex.Observer<? super R> r4 = r2.downstream
                r4.onError(r3)
                goto L46
            L41:
                io.reactivex.Observer<? super R> r3 = r2.downstream
                r3.onComplete()
            L46:
                return
            L47:
                int r3 = r2.decrementAndGet()
                if (r3 != 0) goto L63
                return
            L4e:
                io.reactivex.internal.queue.SpscLinkedArrayQueue r3 = r2.getOrCreateQueue()
                monitor-enter(r3)
                r3.offer(r4)     // Catch: java.lang.Throwable -> L67
                monitor-exit(r3)     // Catch: java.lang.Throwable -> L67
                java.util.concurrent.atomic.AtomicInteger r3 = r2.active
                r3.decrementAndGet()
                int r3 = r2.getAndIncrement()
                if (r3 == 0) goto L63
                return
            L63:
                r2.drainLoop()
                return
            L67:
                r4 = move-exception
                monitor-exit(r3)     // Catch: java.lang.Throwable -> L67
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.ObservableFlatMapSingle.FlatMapSingleObserver.innerSuccess(io.reactivex.internal.operators.observable.ObservableFlatMapSingle$FlatMapSingleObserver$InnerObserver, java.lang.Object):void");
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.active.decrementAndGet();
            drain();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.active.decrementAndGet();
            if (!this.errors.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (!this.delayErrors) {
                this.set.dispose();
            }
            drain();
        }

        @Override // io.reactivex.Observer
        public void onNext(T t2) {
            try {
                SingleSource singleSource = (SingleSource) ObjectHelper.requireNonNull(this.mapper.apply(t2), "The mapper returned a null SingleSource");
                this.active.getAndIncrement();
                InnerObserver innerObserver = new InnerObserver();
                if (this.cancelled || !this.set.add(innerObserver)) {
                    return;
                }
                singleSource.subscribe(innerObserver);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.dispose();
                onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }
    }

    public ObservableFlatMapSingle(ObservableSource<T> observableSource, Function<? super T, ? extends SingleSource<? extends R>> function, boolean z2) {
        super(observableSource);
        this.mapper = function;
        this.delayErrors = z2;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super R> observer) {
        this.source.subscribe(new FlatMapSingleObserver(observer, this.mapper, this.delayErrors));
    }
}
