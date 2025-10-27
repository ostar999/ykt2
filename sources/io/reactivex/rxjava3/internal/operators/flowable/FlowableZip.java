package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.operators.QueueSubscription;
import io.reactivex.rxjava3.operators.SimpleQueue;
import io.reactivex.rxjava3.operators.SpscArrayQueue;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableZip<T, R> extends Flowable<R> {
    final int bufferSize;
    final boolean delayError;
    final Publisher<? extends T>[] sources;
    final Iterable<? extends Publisher<? extends T>> sourcesIterable;
    final Function<? super Object[], ? extends R> zipper;

    public static final class ZipCoordinator<T, R> extends AtomicInteger implements Subscription {
        private static final long serialVersionUID = -2434867452883857743L;
        volatile boolean cancelled;
        final Object[] current;
        final boolean delayErrors;
        final Subscriber<? super R> downstream;
        final AtomicThrowable errors;
        final AtomicLong requested;
        final ZipSubscriber<T, R>[] subscribers;
        final Function<? super Object[], ? extends R> zipper;

        public ZipCoordinator(Subscriber<? super R> actual, Function<? super Object[], ? extends R> zipper, int n2, int prefetch, boolean delayErrors) {
            this.downstream = actual;
            this.zipper = zipper;
            this.delayErrors = delayErrors;
            ZipSubscriber<T, R>[] zipSubscriberArr = new ZipSubscriber[n2];
            for (int i2 = 0; i2 < n2; i2++) {
                zipSubscriberArr[i2] = new ZipSubscriber<>(this, prefetch);
            }
            this.current = new Object[n2];
            this.subscribers = zipSubscriberArr;
            this.requested = new AtomicLong();
            this.errors = new AtomicThrowable();
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            cancelAll();
        }

        public void cancelAll() {
            for (ZipSubscriber<T, R> zipSubscriber : this.subscribers) {
                zipSubscriber.cancel();
            }
        }

        public void drain() {
            T tPoll;
            T tPoll2;
            if (getAndIncrement() != 0) {
                return;
            }
            Subscriber<? super R> subscriber = this.downstream;
            ZipSubscriber<T, R>[] zipSubscriberArr = this.subscribers;
            int length = zipSubscriberArr.length;
            Object[] objArr = this.current;
            int iAddAndGet = 1;
            do {
                long j2 = this.requested.get();
                long j3 = 0;
                while (j2 != j3) {
                    if (this.cancelled) {
                        return;
                    }
                    if (!this.delayErrors && this.errors.get() != null) {
                        cancelAll();
                        this.errors.tryTerminateConsumer(subscriber);
                        return;
                    }
                    boolean z2 = false;
                    for (int i2 = 0; i2 < length; i2++) {
                        ZipSubscriber<T, R> zipSubscriber = zipSubscriberArr[i2];
                        if (objArr[i2] == null) {
                            boolean z3 = zipSubscriber.done;
                            SimpleQueue<T> simpleQueue = zipSubscriber.queue;
                            if (simpleQueue != null) {
                                try {
                                    tPoll2 = simpleQueue.poll();
                                } catch (Throwable th) {
                                    Exceptions.throwIfFatal(th);
                                    this.errors.tryAddThrowableOrReport(th);
                                    if (!this.delayErrors) {
                                        cancelAll();
                                        this.errors.tryTerminateConsumer(subscriber);
                                        return;
                                    } else {
                                        tPoll2 = null;
                                        z3 = true;
                                    }
                                }
                            } else {
                                tPoll2 = null;
                            }
                            boolean z4 = tPoll2 == null;
                            if (z3 && z4) {
                                cancelAll();
                                this.errors.tryTerminateConsumer(subscriber);
                                return;
                            } else if (z4) {
                                z2 = true;
                            } else {
                                objArr[i2] = tPoll2;
                            }
                        }
                    }
                    if (z2) {
                        break;
                    }
                    try {
                        R rApply = this.zipper.apply(objArr.clone());
                        Objects.requireNonNull(rApply, "The zipper returned a null value");
                        subscriber.onNext(rApply);
                        j3++;
                        Arrays.fill(objArr, (Object) null);
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        cancelAll();
                        this.errors.tryAddThrowableOrReport(th2);
                        this.errors.tryTerminateConsumer(subscriber);
                        return;
                    }
                }
                if (j2 == j3) {
                    if (this.cancelled) {
                        return;
                    }
                    if (!this.delayErrors && this.errors.get() != null) {
                        cancelAll();
                        this.errors.tryTerminateConsumer(subscriber);
                        return;
                    }
                    for (int i3 = 0; i3 < length; i3++) {
                        ZipSubscriber<T, R> zipSubscriber2 = zipSubscriberArr[i3];
                        if (objArr[i3] == null) {
                            boolean z5 = zipSubscriber2.done;
                            SimpleQueue<T> simpleQueue2 = zipSubscriber2.queue;
                            if (simpleQueue2 != null) {
                                try {
                                    tPoll = simpleQueue2.poll();
                                } catch (Throwable th3) {
                                    Exceptions.throwIfFatal(th3);
                                    this.errors.tryAddThrowableOrReport(th3);
                                    if (!this.delayErrors) {
                                        cancelAll();
                                        this.errors.tryTerminateConsumer(subscriber);
                                        return;
                                    } else {
                                        tPoll = null;
                                        z5 = true;
                                    }
                                }
                            } else {
                                tPoll = null;
                            }
                            boolean z6 = tPoll == null;
                            if (z5 && z6) {
                                cancelAll();
                                this.errors.tryTerminateConsumer(subscriber);
                                return;
                            } else if (!z6) {
                                objArr[i3] = tPoll;
                            }
                        }
                    }
                }
                if (j3 != 0) {
                    for (ZipSubscriber<T, R> zipSubscriber3 : zipSubscriberArr) {
                        zipSubscriber3.request(j3);
                    }
                    if (j2 != Long.MAX_VALUE) {
                        this.requested.addAndGet(-j3);
                    }
                }
                iAddAndGet = addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }

        public void error(ZipSubscriber<T, R> inner, Throwable e2) {
            if (this.errors.tryAddThrowableOrReport(e2)) {
                inner.done = true;
                drain();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            if (SubscriptionHelper.validate(n2)) {
                BackpressureHelper.add(this.requested, n2);
                drain();
            }
        }

        public void subscribe(Publisher<? extends T>[] sources, int n2) {
            ZipSubscriber<T, R>[] zipSubscriberArr = this.subscribers;
            for (int i2 = 0; i2 < n2 && !this.cancelled; i2++) {
                if (!this.delayErrors && this.errors.get() != null) {
                    return;
                }
                sources[i2].subscribe(zipSubscriberArr[i2]);
            }
        }
    }

    public static final class ZipSubscriber<T, R> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -4627193790118206028L;
        volatile boolean done;
        final int limit;
        final ZipCoordinator<T, R> parent;
        final int prefetch;
        long produced;
        SimpleQueue<T> queue;
        int sourceMode;

        public ZipSubscriber(ZipCoordinator<T, R> parent, int prefetch) {
            this.parent = parent;
            this.prefetch = prefetch;
            this.limit = prefetch - (prefetch >> 2);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SubscriptionHelper.cancel(this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            this.parent.drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            this.parent.error(this, t2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.sourceMode != 2) {
                this.queue.offer(t2);
            }
            this.parent.drain();
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.setOnce(this, s2)) {
                if (s2 instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) s2;
                    int iRequestFusion = queueSubscription.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.sourceMode = iRequestFusion;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.parent.drain();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.sourceMode = iRequestFusion;
                        this.queue = queueSubscription;
                        s2.request(this.prefetch);
                        return;
                    }
                }
                this.queue = new SpscArrayQueue(this.prefetch);
                s2.request(this.prefetch);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            if (this.sourceMode != 1) {
                long j2 = this.produced + n2;
                if (j2 < this.limit) {
                    this.produced = j2;
                } else {
                    this.produced = 0L;
                    get().request(j2);
                }
            }
        }
    }

    public FlowableZip(Publisher<? extends T>[] sources, Iterable<? extends Publisher<? extends T>> sourcesIterable, Function<? super Object[], ? extends R> zipper, int bufferSize, boolean delayError) {
        this.sources = sources;
        this.sourcesIterable = sourcesIterable;
        this.zipper = zipper;
        this.bufferSize = bufferSize;
        this.delayError = delayError;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super R> s2) {
        int length;
        Publisher<? extends T>[] publisherArr = this.sources;
        if (publisherArr == null) {
            publisherArr = new Publisher[8];
            length = 0;
            for (Publisher<? extends T> publisher : this.sourcesIterable) {
                if (length == publisherArr.length) {
                    Publisher<? extends T>[] publisherArr2 = new Publisher[(length >> 2) + length];
                    System.arraycopy(publisherArr, 0, publisherArr2, 0, length);
                    publisherArr = publisherArr2;
                }
                publisherArr[length] = publisher;
                length++;
            }
        } else {
            length = publisherArr.length;
        }
        int i2 = length;
        if (i2 == 0) {
            EmptySubscription.complete(s2);
            return;
        }
        ZipCoordinator zipCoordinator = new ZipCoordinator(s2, this.zipper, i2, this.bufferSize, this.delayError);
        s2.onSubscribe(zipCoordinator);
        zipCoordinator.subscribe(publisherArr, i2);
    }
}
