package io.reactivex.rxjava3.internal.operators.flowable;

import androidx.camera.view.j;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.operators.QueueSubscription;
import io.reactivex.rxjava3.operators.SimpleQueue;
import io.reactivex.rxjava3.operators.SpscArrayQueue;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableSwitchMap<T, R> extends AbstractFlowableWithUpstream<T, R> {
    final int bufferSize;
    final boolean delayErrors;
    final Function<? super T, ? extends Publisher<? extends R>> mapper;

    public static final class SwitchMapInnerSubscriber<T, R> extends AtomicReference<Subscription> implements FlowableSubscriber<R> {
        private static final long serialVersionUID = 3837284832786408377L;
        final int bufferSize;
        volatile boolean done;
        int fusionMode;
        final long index;
        final SwitchMapSubscriber<T, R> parent;
        volatile SimpleQueue<R> queue;

        public SwitchMapInnerSubscriber(SwitchMapSubscriber<T, R> parent, long index, int bufferSize) {
            this.parent = parent;
            this.index = index;
            this.bufferSize = bufferSize;
        }

        public void cancel() {
            SubscriptionHelper.cancel(this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() throws Throwable {
            SwitchMapSubscriber<T, R> switchMapSubscriber = this.parent;
            if (this.index == switchMapSubscriber.unique) {
                this.done = true;
                switchMapSubscriber.drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) throws Throwable {
            SwitchMapSubscriber<T, R> switchMapSubscriber = this.parent;
            if (this.index != switchMapSubscriber.unique || !switchMapSubscriber.errors.tryAddThrowable(t2)) {
                RxJavaPlugins.onError(t2);
                return;
            }
            if (!switchMapSubscriber.delayErrors) {
                switchMapSubscriber.upstream.cancel();
                switchMapSubscriber.done = true;
            }
            this.done = true;
            switchMapSubscriber.drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(R t2) throws Throwable {
            SwitchMapSubscriber<T, R> switchMapSubscriber = this.parent;
            if (this.index == switchMapSubscriber.unique) {
                if (this.fusionMode != 0 || this.queue.offer(t2)) {
                    switchMapSubscriber.drain();
                } else {
                    onError(new MissingBackpressureException("Queue full?!"));
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) throws Throwable {
            if (SubscriptionHelper.setOnce(this, s2)) {
                if (s2 instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) s2;
                    int iRequestFusion = queueSubscription.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.fusionMode = iRequestFusion;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.parent.drain();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.fusionMode = iRequestFusion;
                        this.queue = queueSubscription;
                        s2.request(this.bufferSize);
                        return;
                    }
                }
                this.queue = new SpscArrayQueue(this.bufferSize);
                s2.request(this.bufferSize);
            }
        }

        public void request(long n2) {
            if (this.fusionMode != 1) {
                get().request(n2);
            }
        }
    }

    public static final class SwitchMapSubscriber<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        static final SwitchMapInnerSubscriber<Object, Object> CANCELLED;
        private static final long serialVersionUID = -3491074160481096299L;
        final int bufferSize;
        volatile boolean cancelled;
        final boolean delayErrors;
        volatile boolean done;
        final Subscriber<? super R> downstream;
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        volatile long unique;
        Subscription upstream;
        final AtomicReference<SwitchMapInnerSubscriber<T, R>> active = new AtomicReference<>();
        final AtomicLong requested = new AtomicLong();
        final AtomicThrowable errors = new AtomicThrowable();

        static {
            SwitchMapInnerSubscriber<Object, Object> switchMapInnerSubscriber = new SwitchMapInnerSubscriber<>(null, -1L, 1);
            CANCELLED = switchMapInnerSubscriber;
            switchMapInnerSubscriber.cancel();
        }

        public SwitchMapSubscriber(Subscriber<? super R> actual, Function<? super T, ? extends Publisher<? extends R>> mapper, int bufferSize, boolean delayErrors) {
            this.downstream = actual;
            this.mapper = mapper;
            this.bufferSize = bufferSize;
            this.delayErrors = delayErrors;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.upstream.cancel();
            disposeInner();
            this.errors.tryTerminateAndReport();
        }

        public void disposeInner() {
            AtomicReference<SwitchMapInnerSubscriber<T, R>> atomicReference = this.active;
            SwitchMapInnerSubscriber<Object, Object> switchMapInnerSubscriber = CANCELLED;
            SwitchMapInnerSubscriber<Object, Object> switchMapInnerSubscriber2 = (SwitchMapInnerSubscriber) atomicReference.getAndSet(switchMapInnerSubscriber);
            if (switchMapInnerSubscriber2 == switchMapInnerSubscriber || switchMapInnerSubscriber2 == null) {
                return;
            }
            switchMapInnerSubscriber2.cancel();
        }

        public void drain() throws Throwable {
            boolean z2;
            Object objPoll;
            if (getAndIncrement() != 0) {
                return;
            }
            Subscriber<? super R> subscriber = this.downstream;
            int iAddAndGet = 1;
            while (!this.cancelled) {
                if (this.done) {
                    if (this.delayErrors) {
                        if (this.active.get() == null) {
                            this.errors.tryTerminateConsumer(subscriber);
                            return;
                        }
                    } else if (this.errors.get() != null) {
                        disposeInner();
                        this.errors.tryTerminateConsumer(subscriber);
                        return;
                    } else if (this.active.get() == null) {
                        subscriber.onComplete();
                        return;
                    }
                }
                SwitchMapInnerSubscriber<T, R> switchMapInnerSubscriber = this.active.get();
                SimpleQueue<R> simpleQueue = switchMapInnerSubscriber != null ? switchMapInnerSubscriber.queue : null;
                if (simpleQueue != null) {
                    long j2 = this.requested.get();
                    long j3 = 0;
                    while (j3 != j2) {
                        if (!this.cancelled) {
                            boolean z3 = switchMapInnerSubscriber.done;
                            try {
                                objPoll = simpleQueue.poll();
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                switchMapInnerSubscriber.cancel();
                                this.errors.tryAddThrowableOrReport(th);
                                objPoll = null;
                                z3 = true;
                            }
                            boolean z4 = objPoll == null;
                            if (switchMapInnerSubscriber == this.active.get()) {
                                if (z3) {
                                    if (this.delayErrors) {
                                        if (z4) {
                                            j.a(this.active, switchMapInnerSubscriber, null);
                                        }
                                    } else if (this.errors.get() != null) {
                                        this.errors.tryTerminateConsumer(subscriber);
                                        return;
                                    } else if (z4) {
                                        j.a(this.active, switchMapInnerSubscriber, null);
                                    }
                                }
                                if (z4) {
                                    break;
                                }
                                subscriber.onNext(objPoll);
                                j3++;
                            }
                            z2 = true;
                            break;
                        }
                        return;
                    }
                    z2 = false;
                    if (j3 == j2 && switchMapInnerSubscriber.done) {
                        if (this.delayErrors) {
                            if (simpleQueue.isEmpty()) {
                                j.a(this.active, switchMapInnerSubscriber, null);
                            }
                        } else if (this.errors.get() != null) {
                            disposeInner();
                            this.errors.tryTerminateConsumer(subscriber);
                            return;
                        } else if (simpleQueue.isEmpty()) {
                            j.a(this.active, switchMapInnerSubscriber, null);
                        }
                    }
                    if (j3 != 0 && !this.cancelled) {
                        if (j2 != Long.MAX_VALUE) {
                            this.requested.addAndGet(-j3);
                        }
                        switchMapInnerSubscriber.request(j3);
                    }
                    if (z2) {
                        continue;
                    }
                }
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() throws Throwable {
            if (this.done) {
                return;
            }
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) throws Throwable {
            if (this.done || !this.errors.tryAddThrowable(t2)) {
                RxJavaPlugins.onError(t2);
                return;
            }
            if (!this.delayErrors) {
                disposeInner();
            }
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) throws Throwable {
            SwitchMapInnerSubscriber<T, R> switchMapInnerSubscriber;
            if (this.done) {
                return;
            }
            long j2 = this.unique + 1;
            this.unique = j2;
            SwitchMapInnerSubscriber<T, R> switchMapInnerSubscriber2 = this.active.get();
            if (switchMapInnerSubscriber2 != null) {
                switchMapInnerSubscriber2.cancel();
            }
            try {
                Publisher<? extends R> publisherApply = this.mapper.apply(t2);
                Objects.requireNonNull(publisherApply, "The publisher returned is null");
                Publisher<? extends R> publisher = publisherApply;
                SwitchMapInnerSubscriber switchMapInnerSubscriber3 = new SwitchMapInnerSubscriber(this, j2, this.bufferSize);
                do {
                    switchMapInnerSubscriber = this.active.get();
                    if (switchMapInnerSubscriber == CANCELLED) {
                        return;
                    }
                } while (!j.a(this.active, switchMapInnerSubscriber, switchMapInnerSubscriber3));
                publisher.subscribe(switchMapInnerSubscriber3);
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
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) throws Throwable {
            if (SubscriptionHelper.validate(n2)) {
                BackpressureHelper.add(this.requested, n2);
                if (this.unique == 0) {
                    this.upstream.request(Long.MAX_VALUE);
                } else {
                    drain();
                }
            }
        }
    }

    public FlowableSwitchMap(Flowable<T> source, Function<? super T, ? extends Publisher<? extends R>> mapper, int bufferSize, boolean delayErrors) {
        super(source);
        this.mapper = mapper;
        this.bufferSize = bufferSize;
        this.delayErrors = delayErrors;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super R> s2) {
        if (FlowableScalarXMap.tryScalarXMapSubscribe(this.source, s2, this.mapper)) {
            return;
        }
        this.source.subscribe((FlowableSubscriber) new SwitchMapSubscriber(s2, this.mapper, this.bufferSize, this.delayErrors));
    }
}
