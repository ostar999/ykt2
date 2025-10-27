package io.reactivex.rxjava3.internal.operators.parallel;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.operators.QueueSubscription;
import io.reactivex.rxjava3.operators.SimpleQueue;
import io.reactivex.rxjava3.operators.SpscArrayQueue;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLongArray;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class ParallelFromPublisher<T> extends ParallelFlowable<T> {
    final int parallelism;
    final int prefetch;
    final Publisher<? extends T> source;

    public static final class ParallelDispatcher<T> extends AtomicInteger implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -4470634016609963609L;
        volatile boolean cancelled;
        volatile boolean done;
        final long[] emissions;
        Throwable error;
        int index;
        final int limit;
        final int prefetch;
        int produced;
        SimpleQueue<T> queue;
        final AtomicLongArray requests;
        int sourceMode;
        final AtomicInteger subscriberCount = new AtomicInteger();
        final Subscriber<? super T>[] subscribers;
        Subscription upstream;

        public final class RailSubscription implements Subscription {

            /* renamed from: j, reason: collision with root package name */
            final int f27363j;

            /* renamed from: m, reason: collision with root package name */
            final int f27364m;

            public RailSubscription(int j2, int m2) {
                this.f27363j = j2;
                this.f27364m = m2;
            }

            @Override // org.reactivestreams.Subscription
            public void cancel() {
                if (ParallelDispatcher.this.requests.compareAndSet(this.f27363j + this.f27364m, 0L, 1L)) {
                    ParallelDispatcher parallelDispatcher = ParallelDispatcher.this;
                    int i2 = this.f27364m;
                    parallelDispatcher.cancel(i2 + i2);
                }
            }

            @Override // org.reactivestreams.Subscription
            public void request(long n2) {
                long j2;
                if (SubscriptionHelper.validate(n2)) {
                    AtomicLongArray atomicLongArray = ParallelDispatcher.this.requests;
                    do {
                        j2 = atomicLongArray.get(this.f27363j);
                        if (j2 == Long.MAX_VALUE) {
                            return;
                        }
                    } while (!atomicLongArray.compareAndSet(this.f27363j, j2, BackpressureHelper.addCap(j2, n2)));
                    if (ParallelDispatcher.this.subscriberCount.get() == this.f27364m) {
                        ParallelDispatcher.this.drain();
                    }
                }
            }
        }

        public ParallelDispatcher(Subscriber<? super T>[] subscribers, int prefetch) {
            this.subscribers = subscribers;
            this.prefetch = prefetch;
            this.limit = prefetch - (prefetch >> 2);
            int length = subscribers.length;
            int i2 = length + length;
            AtomicLongArray atomicLongArray = new AtomicLongArray(i2 + 1);
            this.requests = atomicLongArray;
            atomicLongArray.lazySet(i2, length);
            this.emissions = new long[length];
        }

        public void cancel(int m2) {
            if (this.requests.decrementAndGet(m2) == 0) {
                this.cancelled = true;
                this.upstream.cancel();
                if (getAndIncrement() == 0) {
                    this.queue.clear();
                }
            }
        }

        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            if (this.sourceMode == 1) {
                drainSync();
            } else {
                drainAsync();
            }
        }

        public void drainAsync() {
            Throwable th;
            SimpleQueue<T> simpleQueue = this.queue;
            Subscriber<? super T>[] subscriberArr = this.subscribers;
            AtomicLongArray atomicLongArray = this.requests;
            long[] jArr = this.emissions;
            int length = jArr.length;
            int i2 = this.index;
            int i3 = this.produced;
            int iAddAndGet = 1;
            while (true) {
                int i4 = 0;
                int i5 = 0;
                while (!this.cancelled) {
                    boolean z2 = this.done;
                    if (z2 && (th = this.error) != null) {
                        simpleQueue.clear();
                        int length2 = subscriberArr.length;
                        while (i4 < length2) {
                            subscriberArr[i4].onError(th);
                            i4++;
                        }
                        return;
                    }
                    boolean zIsEmpty = simpleQueue.isEmpty();
                    if (z2 && zIsEmpty) {
                        int length3 = subscriberArr.length;
                        while (i4 < length3) {
                            subscriberArr[i4].onComplete();
                            i4++;
                        }
                        return;
                    }
                    if (!zIsEmpty) {
                        long j2 = atomicLongArray.get(i2);
                        long j3 = jArr[i2];
                        if (j2 == j3 || atomicLongArray.get(length + i2) != 0) {
                            i5++;
                        } else {
                            try {
                                T tPoll = simpleQueue.poll();
                                if (tPoll != null) {
                                    subscriberArr[i2].onNext(tPoll);
                                    jArr[i2] = j3 + 1;
                                    i3++;
                                    if (i3 == this.limit) {
                                        this.upstream.request(i3);
                                        i3 = 0;
                                    }
                                    i5 = 0;
                                }
                            } catch (Throwable th2) {
                                Exceptions.throwIfFatal(th2);
                                this.upstream.cancel();
                                int length4 = subscriberArr.length;
                                while (i4 < length4) {
                                    subscriberArr[i4].onError(th2);
                                    i4++;
                                }
                                return;
                            }
                        }
                        i2++;
                        if (i2 == length) {
                            i2 = 0;
                        }
                        if (i5 == length) {
                        }
                    }
                    int i6 = get();
                    if (i6 == iAddAndGet) {
                        this.index = i2;
                        this.produced = i3;
                        iAddAndGet = addAndGet(-iAddAndGet);
                        if (iAddAndGet == 0) {
                            return;
                        }
                    } else {
                        iAddAndGet = i6;
                    }
                }
                simpleQueue.clear();
                return;
            }
        }

        public void drainSync() {
            SimpleQueue<T> simpleQueue = this.queue;
            Subscriber<? super T>[] subscriberArr = this.subscribers;
            AtomicLongArray atomicLongArray = this.requests;
            long[] jArr = this.emissions;
            int length = jArr.length;
            int i2 = this.index;
            int iAddAndGet = 1;
            while (true) {
                int i3 = 0;
                int i4 = 0;
                while (!this.cancelled) {
                    if (simpleQueue.isEmpty()) {
                        int length2 = subscriberArr.length;
                        while (i3 < length2) {
                            subscriberArr[i3].onComplete();
                            i3++;
                        }
                        return;
                    }
                    long j2 = atomicLongArray.get(i2);
                    long j3 = jArr[i2];
                    if (j2 == j3 || atomicLongArray.get(length + i2) != 0) {
                        i4++;
                    } else {
                        try {
                            T tPoll = simpleQueue.poll();
                            if (tPoll == null) {
                                int length3 = subscriberArr.length;
                                while (i3 < length3) {
                                    subscriberArr[i3].onComplete();
                                    i3++;
                                }
                                return;
                            }
                            subscriberArr[i2].onNext(tPoll);
                            jArr[i2] = j3 + 1;
                            i4 = 0;
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            this.upstream.cancel();
                            int length4 = subscriberArr.length;
                            while (i3 < length4) {
                                subscriberArr[i3].onError(th);
                                i3++;
                            }
                            return;
                        }
                    }
                    i2++;
                    if (i2 == length) {
                        i2 = 0;
                    }
                    if (i4 == length) {
                        int i5 = get();
                        if (i5 == iAddAndGet) {
                            this.index = i2;
                            iAddAndGet = addAndGet(-iAddAndGet);
                            if (iAddAndGet == 0) {
                                return;
                            }
                        } else {
                            iAddAndGet = i5;
                        }
                    }
                }
                simpleQueue.clear();
                return;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            this.error = t2;
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.sourceMode != 0 || this.queue.offer(t2)) {
                drain();
            } else {
                this.upstream.cancel();
                onError(new MissingBackpressureException("Queue is full?"));
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                if (s2 instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) s2;
                    int iRequestFusion = queueSubscription.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.sourceMode = iRequestFusion;
                        this.queue = queueSubscription;
                        this.done = true;
                        setupSubscribers();
                        drain();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.sourceMode = iRequestFusion;
                        this.queue = queueSubscription;
                        setupSubscribers();
                        s2.request(this.prefetch);
                        return;
                    }
                }
                this.queue = new SpscArrayQueue(this.prefetch);
                setupSubscribers();
                s2.request(this.prefetch);
            }
        }

        public void setupSubscribers() {
            Subscriber<? super T>[] subscriberArr = this.subscribers;
            int length = subscriberArr.length;
            int i2 = 0;
            while (i2 < length) {
                int i3 = i2 + 1;
                this.subscriberCount.lazySet(i3);
                subscriberArr[i2].onSubscribe(new RailSubscription(i2, length));
                i2 = i3;
            }
        }
    }

    public ParallelFromPublisher(Publisher<? extends T> source, int parallelism, int prefetch) {
        this.source = source;
        this.parallelism = parallelism;
        this.prefetch = prefetch;
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable
    public int parallelism() {
        return this.parallelism;
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable
    public void subscribe(Subscriber<? super T>[] subscribers) {
        Subscriber<?>[] subscriberArrOnSubscribe = RxJavaPlugins.onSubscribe(this, subscribers);
        if (validate(subscriberArrOnSubscribe)) {
            this.source.subscribe(new ParallelDispatcher(subscriberArrOnSubscribe, this.prefetch));
        }
    }
}
