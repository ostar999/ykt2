package io.reactivex.rxjava3.internal.operators.flowable;

import MTT.ThirdAppInfoNew;
import androidx.camera.view.j;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.operators.SpscLinkedArrayQueue;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableFlatMapMaybe<T, R> extends AbstractFlowableWithUpstream<T, R> {
    final boolean delayErrors;
    final Function<? super T, ? extends MaybeSource<? extends R>> mapper;
    final int maxConcurrency;

    public static final class FlatMapMaybeSubscriber<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = 8600231336733376951L;
        volatile boolean cancelled;
        final boolean delayErrors;
        final Subscriber<? super R> downstream;
        final Function<? super T, ? extends MaybeSource<? extends R>> mapper;
        final int maxConcurrency;
        Subscription upstream;
        final AtomicLong requested = new AtomicLong();
        final CompositeDisposable set = new CompositeDisposable();
        final AtomicThrowable errors = new AtomicThrowable();
        final AtomicInteger active = new AtomicInteger(1);
        final AtomicReference<SpscLinkedArrayQueue<R>> queue = new AtomicReference<>();

        public final class InnerObserver extends AtomicReference<Disposable> implements MaybeObserver<R>, Disposable {
            private static final long serialVersionUID = -502562646270949838L;

            public InnerObserver() {
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public void dispose() {
                DisposableHelper.dispose(this);
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public boolean isDisposed() {
                return DisposableHelper.isDisposed(get());
            }

            @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.CompletableObserver
            public void onComplete() {
                FlatMapMaybeSubscriber.this.innerComplete(this);
            }

            @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
            public void onError(Throwable e2) {
                FlatMapMaybeSubscriber.this.innerError(this, e2);
            }

            @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
            public void onSubscribe(Disposable d3) {
                DisposableHelper.setOnce(this, d3);
            }

            @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver
            public void onSuccess(R value) {
                FlatMapMaybeSubscriber.this.innerSuccess(this, value);
            }
        }

        public FlatMapMaybeSubscriber(Subscriber<? super R> actual, Function<? super T, ? extends MaybeSource<? extends R>> mapper, boolean delayErrors, int maxConcurrency) {
            this.downstream = actual;
            this.mapper = mapper;
            this.delayErrors = delayErrors;
            this.maxConcurrency = maxConcurrency;
        }

        public static boolean checkTerminate(boolean d3, SpscLinkedArrayQueue<?> q2) {
            return d3 && (q2 == null || q2.isEmpty());
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            this.upstream.cancel();
            this.set.dispose();
            this.errors.tryTerminateAndReport();
        }

        public void clear() {
            SpscLinkedArrayQueue<R> spscLinkedArrayQueue = this.queue.get();
            if (spscLinkedArrayQueue != null) {
                spscLinkedArrayQueue.clear();
            }
        }

        public void drain() {
            if (getAndIncrement() == 0) {
                drainLoop();
            }
        }

        public void drainLoop() {
            Subscriber<? super R> subscriber = this.downstream;
            AtomicInteger atomicInteger = this.active;
            AtomicReference<SpscLinkedArrayQueue<R>> atomicReference = this.queue;
            int iAddAndGet = 1;
            do {
                long j2 = this.requested.get();
                long j3 = 0;
                while (true) {
                    if (j3 == j2) {
                        break;
                    }
                    if (this.cancelled) {
                        clear();
                        return;
                    }
                    if (!this.delayErrors && this.errors.get() != null) {
                        clear();
                        this.errors.tryTerminateConsumer(subscriber);
                        return;
                    }
                    boolean z2 = atomicInteger.get() == 0;
                    SpscLinkedArrayQueue<R> spscLinkedArrayQueue = atomicReference.get();
                    ThirdAppInfoNew thirdAppInfoNewPoll = spscLinkedArrayQueue != null ? spscLinkedArrayQueue.poll() : null;
                    boolean z3 = thirdAppInfoNewPoll == null;
                    if (z2 && z3) {
                        this.errors.tryTerminateConsumer(subscriber);
                        return;
                    } else {
                        if (z3) {
                            break;
                        }
                        subscriber.onNext(thirdAppInfoNewPoll);
                        j3++;
                    }
                }
                if (j3 == j2) {
                    if (this.cancelled) {
                        clear();
                        return;
                    }
                    if (!this.delayErrors && this.errors.get() != null) {
                        clear();
                        this.errors.tryTerminateConsumer(subscriber);
                        return;
                    }
                    boolean z4 = atomicInteger.get() == 0;
                    SpscLinkedArrayQueue<R> spscLinkedArrayQueue2 = atomicReference.get();
                    boolean z5 = spscLinkedArrayQueue2 == null || spscLinkedArrayQueue2.isEmpty();
                    if (z4 && z5) {
                        this.errors.tryTerminateConsumer(subscriber);
                        return;
                    }
                }
                if (j3 != 0) {
                    BackpressureHelper.produced(this.requested, j3);
                    if (this.maxConcurrency != Integer.MAX_VALUE) {
                        this.upstream.request(j3);
                    }
                }
                iAddAndGet = addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }

        public SpscLinkedArrayQueue<R> getOrCreateQueue() {
            SpscLinkedArrayQueue<R> spscLinkedArrayQueue = this.queue.get();
            if (spscLinkedArrayQueue != null) {
                return spscLinkedArrayQueue;
            }
            SpscLinkedArrayQueue<R> spscLinkedArrayQueue2 = new SpscLinkedArrayQueue<>(Flowable.bufferSize());
            return j.a(this.queue, null, spscLinkedArrayQueue2) ? spscLinkedArrayQueue2 : this.queue.get();
        }

        public void innerComplete(FlatMapMaybeSubscriber<T, R>.InnerObserver inner) {
            this.set.delete(inner);
            if (get() == 0) {
                if (compareAndSet(0, 1)) {
                    if (checkTerminate(this.active.decrementAndGet() == 0, this.queue.get())) {
                        this.errors.tryTerminateConsumer(this.downstream);
                        return;
                    }
                    if (this.maxConcurrency != Integer.MAX_VALUE) {
                        this.upstream.request(1L);
                    }
                    if (decrementAndGet() == 0) {
                        return;
                    }
                    drainLoop();
                    return;
                }
            }
            this.active.decrementAndGet();
            if (this.maxConcurrency != Integer.MAX_VALUE) {
                this.upstream.request(1L);
            }
            drain();
        }

        public void innerError(FlatMapMaybeSubscriber<T, R>.InnerObserver inner, Throwable e2) {
            this.set.delete(inner);
            if (this.errors.tryAddThrowableOrReport(e2)) {
                if (!this.delayErrors) {
                    this.upstream.cancel();
                    this.set.dispose();
                } else if (this.maxConcurrency != Integer.MAX_VALUE) {
                    this.upstream.request(1L);
                }
                this.active.decrementAndGet();
                drain();
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:28:0x006a  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void innerSuccess(io.reactivex.rxjava3.internal.operators.flowable.FlowableFlatMapMaybe.FlatMapMaybeSubscriber<T, R>.InnerObserver r5, R r6) {
            /*
                r4 = this;
                io.reactivex.rxjava3.disposables.CompositeDisposable r0 = r4.set
                r0.delete(r5)
                int r5 = r4.get()
                if (r5 != 0) goto L6a
                r5 = 0
                r0 = 1
                boolean r1 = r4.compareAndSet(r5, r0)
                if (r1 == 0) goto L6a
                java.util.concurrent.atomic.AtomicInteger r1 = r4.active
                int r1 = r1.decrementAndGet()
                if (r1 != 0) goto L1c
                r5 = r0
            L1c:
                java.util.concurrent.atomic.AtomicLong r0 = r4.requested
                long r0 = r0.get()
                r2 = 0
                int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                if (r0 == 0) goto L57
                org.reactivestreams.Subscriber<? super R> r0 = r4.downstream
                r0.onNext(r6)
                java.util.concurrent.atomic.AtomicReference<io.reactivex.rxjava3.operators.SpscLinkedArrayQueue<R>> r6 = r4.queue
                java.lang.Object r6 = r6.get()
                io.reactivex.rxjava3.operators.SpscLinkedArrayQueue r6 = (io.reactivex.rxjava3.operators.SpscLinkedArrayQueue) r6
                boolean r5 = checkTerminate(r5, r6)
                if (r5 == 0) goto L43
                io.reactivex.rxjava3.internal.util.AtomicThrowable r5 = r4.errors
                org.reactivestreams.Subscriber<? super R> r6 = r4.downstream
                r5.tryTerminateConsumer(r6)
                return
            L43:
                java.util.concurrent.atomic.AtomicLong r5 = r4.requested
                r0 = 1
                io.reactivex.rxjava3.internal.util.BackpressureHelper.produced(r5, r0)
                int r5 = r4.maxConcurrency
                r6 = 2147483647(0x7fffffff, float:NaN)
                if (r5 == r6) goto L60
                org.reactivestreams.Subscription r5 = r4.upstream
                r5.request(r0)
                goto L60
            L57:
                io.reactivex.rxjava3.operators.SpscLinkedArrayQueue r5 = r4.getOrCreateQueue()
                monitor-enter(r5)
                r5.offer(r6)     // Catch: java.lang.Throwable -> L67
                monitor-exit(r5)     // Catch: java.lang.Throwable -> L67
            L60:
                int r5 = r4.decrementAndGet()
                if (r5 != 0) goto L7f
                return
            L67:
                r6 = move-exception
                monitor-exit(r5)     // Catch: java.lang.Throwable -> L67
                throw r6
            L6a:
                io.reactivex.rxjava3.operators.SpscLinkedArrayQueue r5 = r4.getOrCreateQueue()
                monitor-enter(r5)
                r5.offer(r6)     // Catch: java.lang.Throwable -> L83
                monitor-exit(r5)     // Catch: java.lang.Throwable -> L83
                java.util.concurrent.atomic.AtomicInteger r5 = r4.active
                r5.decrementAndGet()
                int r5 = r4.getAndIncrement()
                if (r5 == 0) goto L7f
                return
            L7f:
                r4.drainLoop()
                return
            L83:
                r6 = move-exception
                monitor-exit(r5)     // Catch: java.lang.Throwable -> L83
                throw r6
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.operators.flowable.FlowableFlatMapMaybe.FlatMapMaybeSubscriber.innerSuccess(io.reactivex.rxjava3.internal.operators.flowable.FlowableFlatMapMaybe$FlatMapMaybeSubscriber$InnerObserver, java.lang.Object):void");
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.active.decrementAndGet();
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            this.active.decrementAndGet();
            if (this.errors.tryAddThrowableOrReport(t2)) {
                if (!this.delayErrors) {
                    this.set.dispose();
                }
                drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            try {
                MaybeSource<? extends R> maybeSourceApply = this.mapper.apply(t2);
                Objects.requireNonNull(maybeSourceApply, "The mapper returned a null MaybeSource");
                MaybeSource<? extends R> maybeSource = maybeSourceApply;
                this.active.getAndIncrement();
                InnerObserver innerObserver = new InnerObserver();
                if (this.cancelled || !this.set.add(innerObserver)) {
                    return;
                }
                maybeSource.subscribe(innerObserver);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.cancel();
                onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                this.downstream.onSubscribe(this);
                int i2 = this.maxConcurrency;
                if (i2 == Integer.MAX_VALUE) {
                    s2.request(Long.MAX_VALUE);
                } else {
                    s2.request(i2);
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            if (SubscriptionHelper.validate(n2)) {
                BackpressureHelper.add(this.requested, n2);
                drain();
            }
        }
    }

    public FlowableFlatMapMaybe(Flowable<T> source, Function<? super T, ? extends MaybeSource<? extends R>> mapper, boolean delayError, int maxConcurrency) {
        super(source);
        this.mapper = mapper;
        this.delayErrors = delayError;
        this.maxConcurrency = maxConcurrency;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super R> s2) {
        this.source.subscribe((FlowableSubscriber) new FlatMapMaybeSubscriber(s2, this.mapper, this.delayErrors, this.maxConcurrency));
    }
}
