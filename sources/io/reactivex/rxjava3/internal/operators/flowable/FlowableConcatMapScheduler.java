package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatMap;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import io.reactivex.rxjava3.operators.QueueSubscription;
import io.reactivex.rxjava3.operators.SimpleQueue;
import io.reactivex.rxjava3.operators.SpscArrayQueue;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableConcatMapScheduler<T, R> extends AbstractFlowableWithUpstream<T, R> {
    final ErrorMode errorMode;
    final Function<? super T, ? extends Publisher<? extends R>> mapper;
    final int prefetch;
    final Scheduler scheduler;

    /* renamed from: io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatMapScheduler$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$reactivex$rxjava3$internal$util$ErrorMode;

        static {
            int[] iArr = new int[ErrorMode.values().length];
            $SwitchMap$io$reactivex$rxjava3$internal$util$ErrorMode = iArr;
            try {
                iArr[ErrorMode.BOUNDARY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$reactivex$rxjava3$internal$util$ErrorMode[ErrorMode.END.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static abstract class BaseConcatMapSubscriber<T, R> extends AtomicInteger implements FlowableSubscriber<T>, FlowableConcatMap.ConcatMapSupport<R>, Subscription, Runnable {
        private static final long serialVersionUID = -3511336836796789179L;
        volatile boolean active;
        volatile boolean cancelled;
        int consumed;
        volatile boolean done;
        final int limit;
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        final int prefetch;
        SimpleQueue<T> queue;
        int sourceMode;
        Subscription upstream;
        final Scheduler.Worker worker;
        final FlowableConcatMap.ConcatMapInner<R> inner = new FlowableConcatMap.ConcatMapInner<>(this);
        final AtomicThrowable errors = new AtomicThrowable();

        public BaseConcatMapSubscriber(Function<? super T, ? extends Publisher<? extends R>> mapper, int prefetch, Scheduler.Worker worker) {
            this.mapper = mapper;
            this.prefetch = prefetch;
            this.limit = prefetch - (prefetch >> 2);
            this.worker = worker;
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatMap.ConcatMapSupport
        public final void innerComplete() {
            this.active = false;
            schedule();
        }

        @Override // org.reactivestreams.Subscriber
        public final void onComplete() {
            this.done = true;
            schedule();
        }

        @Override // org.reactivestreams.Subscriber
        public final void onNext(T t2) {
            if (this.sourceMode == 2 || this.queue.offer(t2)) {
                schedule();
            } else {
                this.upstream.cancel();
                onError(new IllegalStateException("Queue full?!"));
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public final void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                if (s2 instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) s2;
                    int iRequestFusion = queueSubscription.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.sourceMode = iRequestFusion;
                        this.queue = queueSubscription;
                        this.done = true;
                        subscribeActual();
                        schedule();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.sourceMode = iRequestFusion;
                        this.queue = queueSubscription;
                        subscribeActual();
                        s2.request(this.prefetch);
                        return;
                    }
                }
                this.queue = new SpscArrayQueue(this.prefetch);
                subscribeActual();
                s2.request(this.prefetch);
            }
        }

        public abstract void schedule();

        public abstract void subscribeActual();
    }

    public static final class ConcatMapDelayed<T, R> extends BaseConcatMapSubscriber<T, R> {
        private static final long serialVersionUID = -2945777694260521066L;
        final Subscriber<? super R> downstream;
        final boolean veryEnd;

        public ConcatMapDelayed(Subscriber<? super R> actual, Function<? super T, ? extends Publisher<? extends R>> mapper, int prefetch, boolean veryEnd, Scheduler.Worker worker) {
            super(mapper, prefetch, worker);
            this.downstream = actual;
            this.veryEnd = veryEnd;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.inner.cancel();
            this.upstream.cancel();
            this.worker.dispose();
            this.errors.tryTerminateAndReport();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatMap.ConcatMapSupport
        public void innerError(Throwable e2) {
            if (this.errors.tryAddThrowableOrReport(e2)) {
                if (!this.veryEnd) {
                    this.upstream.cancel();
                    this.done = true;
                }
                this.active = false;
                schedule();
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatMap.ConcatMapSupport
        public void innerNext(R value) {
            this.downstream.onNext(value);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            if (this.errors.tryAddThrowableOrReport(t2)) {
                this.done = true;
                schedule();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            this.inner.request(n2);
        }

        @Override // java.lang.Runnable
        public void run() {
            Object obj;
            while (!this.cancelled) {
                if (!this.active) {
                    boolean z2 = this.done;
                    if (z2 && !this.veryEnd && this.errors.get() != null) {
                        this.errors.tryTerminateConsumer(this.downstream);
                        this.worker.dispose();
                        return;
                    }
                    try {
                        T tPoll = this.queue.poll();
                        boolean z3 = tPoll == null;
                        if (z2 && z3) {
                            this.errors.tryTerminateConsumer(this.downstream);
                            this.worker.dispose();
                            return;
                        }
                        if (!z3) {
                            try {
                                Publisher<? extends R> publisherApply = this.mapper.apply(tPoll);
                                Objects.requireNonNull(publisherApply, "The mapper returned a null Publisher");
                                Publisher<? extends R> publisher = publisherApply;
                                if (this.sourceMode != 1) {
                                    int i2 = this.consumed + 1;
                                    if (i2 == this.limit) {
                                        this.consumed = 0;
                                        this.upstream.request(i2);
                                    } else {
                                        this.consumed = i2;
                                    }
                                }
                                if (publisher instanceof Supplier) {
                                    try {
                                        obj = ((Supplier) publisher).get();
                                    } catch (Throwable th) {
                                        Exceptions.throwIfFatal(th);
                                        this.errors.tryAddThrowableOrReport(th);
                                        if (!this.veryEnd) {
                                            this.upstream.cancel();
                                            this.errors.tryTerminateConsumer(this.downstream);
                                            this.worker.dispose();
                                            return;
                                        }
                                        obj = null;
                                    }
                                    if (obj != null && !this.cancelled) {
                                        if (this.inner.isUnbounded()) {
                                            this.downstream.onNext(obj);
                                        } else {
                                            this.active = true;
                                            this.inner.setSubscription(new FlowableConcatMap.SimpleScalarSubscription(obj, this.inner));
                                        }
                                    }
                                } else {
                                    this.active = true;
                                    publisher.subscribe(this.inner);
                                }
                            } catch (Throwable th2) {
                                Exceptions.throwIfFatal(th2);
                                this.upstream.cancel();
                                this.errors.tryAddThrowableOrReport(th2);
                                this.errors.tryTerminateConsumer(this.downstream);
                                this.worker.dispose();
                                return;
                            }
                        }
                    } catch (Throwable th3) {
                        Exceptions.throwIfFatal(th3);
                        this.upstream.cancel();
                        this.errors.tryAddThrowableOrReport(th3);
                        this.errors.tryTerminateConsumer(this.downstream);
                        this.worker.dispose();
                        return;
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatMapScheduler.BaseConcatMapSubscriber
        public void schedule() {
            if (getAndIncrement() == 0) {
                this.worker.schedule(this);
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatMapScheduler.BaseConcatMapSubscriber
        public void subscribeActual() {
            this.downstream.onSubscribe(this);
        }
    }

    public static final class ConcatMapImmediate<T, R> extends BaseConcatMapSubscriber<T, R> {
        private static final long serialVersionUID = 7898995095634264146L;
        final Subscriber<? super R> downstream;
        final AtomicInteger wip;

        public ConcatMapImmediate(Subscriber<? super R> actual, Function<? super T, ? extends Publisher<? extends R>> mapper, int prefetch, Scheduler.Worker worker) {
            super(mapper, prefetch, worker);
            this.downstream = actual;
            this.wip = new AtomicInteger();
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.inner.cancel();
            this.upstream.cancel();
            this.worker.dispose();
            this.errors.tryTerminateAndReport();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatMap.ConcatMapSupport
        public void innerError(Throwable e2) {
            if (this.errors.tryAddThrowableOrReport(e2)) {
                this.upstream.cancel();
                if (getAndIncrement() == 0) {
                    this.errors.tryTerminateConsumer(this.downstream);
                    this.worker.dispose();
                }
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatMap.ConcatMapSupport
        public void innerNext(R value) {
            if (tryEnter()) {
                this.downstream.onNext(value);
                if (compareAndSet(1, 0)) {
                    return;
                }
                this.errors.tryTerminateConsumer(this.downstream);
                this.worker.dispose();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            if (this.errors.tryAddThrowableOrReport(t2)) {
                this.inner.cancel();
                if (getAndIncrement() == 0) {
                    this.errors.tryTerminateConsumer(this.downstream);
                    this.worker.dispose();
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            this.inner.request(n2);
        }

        @Override // java.lang.Runnable
        public void run() {
            while (!this.cancelled) {
                if (!this.active) {
                    boolean z2 = this.done;
                    try {
                        T tPoll = this.queue.poll();
                        boolean z3 = tPoll == null;
                        if (z2 && z3) {
                            this.downstream.onComplete();
                            this.worker.dispose();
                            return;
                        }
                        if (!z3) {
                            try {
                                Publisher<? extends R> publisherApply = this.mapper.apply(tPoll);
                                Objects.requireNonNull(publisherApply, "The mapper returned a null Publisher");
                                Publisher<? extends R> publisher = publisherApply;
                                if (this.sourceMode != 1) {
                                    int i2 = this.consumed + 1;
                                    if (i2 == this.limit) {
                                        this.consumed = 0;
                                        this.upstream.request(i2);
                                    } else {
                                        this.consumed = i2;
                                    }
                                }
                                if (publisher instanceof Supplier) {
                                    try {
                                        Object obj = ((Supplier) publisher).get();
                                        if (obj != null && !this.cancelled) {
                                            if (!this.inner.isUnbounded()) {
                                                this.active = true;
                                                this.inner.setSubscription(new FlowableConcatMap.SimpleScalarSubscription(obj, this.inner));
                                            } else if (tryEnter()) {
                                                this.downstream.onNext(obj);
                                                if (!compareAndSet(1, 0)) {
                                                    this.errors.tryTerminateConsumer(this.downstream);
                                                    this.worker.dispose();
                                                    return;
                                                }
                                            } else {
                                                continue;
                                            }
                                        }
                                    } catch (Throwable th) {
                                        Exceptions.throwIfFatal(th);
                                        this.upstream.cancel();
                                        this.errors.tryAddThrowableOrReport(th);
                                        this.errors.tryTerminateConsumer(this.downstream);
                                        this.worker.dispose();
                                        return;
                                    }
                                } else {
                                    this.active = true;
                                    publisher.subscribe(this.inner);
                                }
                            } catch (Throwable th2) {
                                Exceptions.throwIfFatal(th2);
                                this.upstream.cancel();
                                this.errors.tryAddThrowableOrReport(th2);
                                this.errors.tryTerminateConsumer(this.downstream);
                                this.worker.dispose();
                                return;
                            }
                        }
                    } catch (Throwable th3) {
                        Exceptions.throwIfFatal(th3);
                        this.upstream.cancel();
                        this.errors.tryAddThrowableOrReport(th3);
                        this.errors.tryTerminateConsumer(this.downstream);
                        this.worker.dispose();
                        return;
                    }
                }
                if (this.wip.decrementAndGet() == 0) {
                    return;
                }
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatMapScheduler.BaseConcatMapSubscriber
        public void schedule() {
            if (this.wip.getAndIncrement() == 0) {
                this.worker.schedule(this);
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatMapScheduler.BaseConcatMapSubscriber
        public void subscribeActual() {
            this.downstream.onSubscribe(this);
        }

        public boolean tryEnter() {
            return get() == 0 && compareAndSet(0, 1);
        }
    }

    public FlowableConcatMapScheduler(Flowable<T> source, Function<? super T, ? extends Publisher<? extends R>> mapper, int prefetch, ErrorMode errorMode, Scheduler scheduler) {
        super(source);
        this.mapper = mapper;
        this.prefetch = prefetch;
        this.errorMode = errorMode;
        this.scheduler = scheduler;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super R> s2) {
        int i2 = AnonymousClass1.$SwitchMap$io$reactivex$rxjava3$internal$util$ErrorMode[this.errorMode.ordinal()];
        if (i2 == 1) {
            this.source.subscribe((FlowableSubscriber) new ConcatMapDelayed(s2, this.mapper, this.prefetch, false, this.scheduler.createWorker()));
        } else if (i2 != 2) {
            this.source.subscribe((FlowableSubscriber) new ConcatMapImmediate(s2, this.mapper, this.prefetch, this.scheduler.createWorker()));
        } else {
            this.source.subscribe((FlowableSubscriber) new ConcatMapDelayed(s2, this.mapper, this.prefetch, true, this.scheduler.createWorker()));
        }
    }
}
