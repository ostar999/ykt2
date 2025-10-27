package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Arrays;
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

        public ZipCoordinator(Subscriber<? super R> subscriber, Function<? super Object[], ? extends R> function, int i2, int i3, boolean z2) {
            this.downstream = subscriber;
            this.zipper = function;
            this.delayErrors = z2;
            ZipSubscriber<T, R>[] zipSubscriberArr = new ZipSubscriber[i2];
            for (int i4 = 0; i4 < i2; i4++) {
                zipSubscriberArr[i4] = new ZipSubscriber<>(this, i3);
            }
            this.current = new Object[i2];
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
            boolean z2;
            T tPoll;
            boolean z3;
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
                        subscriber.onError(this.errors.terminate());
                        return;
                    }
                    boolean z4 = false;
                    for (int i2 = 0; i2 < length; i2++) {
                        ZipSubscriber<T, R> zipSubscriber = zipSubscriberArr[i2];
                        if (objArr[i2] == null) {
                            try {
                                z2 = zipSubscriber.done;
                                SimpleQueue<T> simpleQueue = zipSubscriber.queue;
                                tPoll = simpleQueue != null ? simpleQueue.poll() : null;
                                z3 = tPoll == null;
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                this.errors.addThrowable(th);
                                if (!this.delayErrors) {
                                    cancelAll();
                                    subscriber.onError(this.errors.terminate());
                                    return;
                                }
                            }
                            if (z2 && z3) {
                                cancelAll();
                                if (this.errors.get() != null) {
                                    subscriber.onError(this.errors.terminate());
                                    return;
                                } else {
                                    subscriber.onComplete();
                                    return;
                                }
                            }
                            if (z3) {
                                z4 = true;
                            } else {
                                objArr[i2] = tPoll;
                            }
                        }
                    }
                    if (z4) {
                        break;
                    }
                    try {
                        subscriber.onNext((Object) ObjectHelper.requireNonNull(this.zipper.apply(objArr.clone()), "The zipper returned a null value"));
                        j3++;
                        Arrays.fill(objArr, (Object) null);
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        cancelAll();
                        this.errors.addThrowable(th2);
                        subscriber.onError(this.errors.terminate());
                        return;
                    }
                }
                if (j2 == j3) {
                    if (this.cancelled) {
                        return;
                    }
                    if (!this.delayErrors && this.errors.get() != null) {
                        cancelAll();
                        subscriber.onError(this.errors.terminate());
                        return;
                    }
                    for (int i3 = 0; i3 < length; i3++) {
                        ZipSubscriber<T, R> zipSubscriber2 = zipSubscriberArr[i3];
                        if (objArr[i3] == null) {
                            try {
                                boolean z5 = zipSubscriber2.done;
                                SimpleQueue<T> simpleQueue2 = zipSubscriber2.queue;
                                T tPoll2 = simpleQueue2 != null ? simpleQueue2.poll() : null;
                                boolean z6 = tPoll2 == null;
                                if (z5 && z6) {
                                    cancelAll();
                                    if (this.errors.get() != null) {
                                        subscriber.onError(this.errors.terminate());
                                        return;
                                    } else {
                                        subscriber.onComplete();
                                        return;
                                    }
                                }
                                if (!z6) {
                                    objArr[i3] = tPoll2;
                                }
                            } catch (Throwable th3) {
                                Exceptions.throwIfFatal(th3);
                                this.errors.addThrowable(th3);
                                if (!this.delayErrors) {
                                    cancelAll();
                                    subscriber.onError(this.errors.terminate());
                                    return;
                                }
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

        public void error(ZipSubscriber<T, R> zipSubscriber, Throwable th) {
            if (!this.errors.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else {
                zipSubscriber.done = true;
                drain();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                BackpressureHelper.add(this.requested, j2);
                drain();
            }
        }

        public void subscribe(Publisher<? extends T>[] publisherArr, int i2) {
            ZipSubscriber<T, R>[] zipSubscriberArr = this.subscribers;
            for (int i3 = 0; i3 < i2 && !this.cancelled; i3++) {
                if (!this.delayErrors && this.errors.get() != null) {
                    return;
                }
                publisherArr[i3].subscribe(zipSubscriberArr[i3]);
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

        public ZipSubscriber(ZipCoordinator<T, R> zipCoordinator, int i2) {
            this.parent = zipCoordinator;
            this.prefetch = i2;
            this.limit = i2 - (i2 >> 2);
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
        public void onError(Throwable th) {
            this.parent.error(this, th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.sourceMode != 2) {
                this.queue.offer(t2);
            }
            this.parent.drain();
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
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
                        subscription.request(this.prefetch);
                        return;
                    }
                }
                this.queue = new SpscArrayQueue(this.prefetch);
                subscription.request(this.prefetch);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j2) {
            if (this.sourceMode != 1) {
                long j3 = this.produced + j2;
                if (j3 < this.limit) {
                    this.produced = j3;
                } else {
                    this.produced = 0L;
                    get().request(j3);
                }
            }
        }
    }

    public FlowableZip(Publisher<? extends T>[] publisherArr, Iterable<? extends Publisher<? extends T>> iterable, Function<? super Object[], ? extends R> function, int i2, boolean z2) {
        this.sources = publisherArr;
        this.sourcesIterable = iterable;
        this.zipper = function;
        this.bufferSize = i2;
        this.delayError = z2;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super R> subscriber) {
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
            EmptySubscription.complete(subscriber);
            return;
        }
        ZipCoordinator zipCoordinator = new ZipCoordinator(subscriber, this.zipper, i2, this.bufferSize, this.delayError);
        subscriber.onSubscribe(zipCoordinator);
        zipCoordinator.subscribe(publisherArr, i2);
    }
}
