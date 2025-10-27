package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.BiPredicate;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableSequenceEqual<T> extends Flowable<Boolean> {
    final BiPredicate<? super T, ? super T> comparer;
    final Publisher<? extends T> first;
    final int prefetch;
    final Publisher<? extends T> second;

    public static final class EqualCoordinator<T> extends DeferredScalarSubscription<Boolean> implements EqualCoordinatorHelper {
        private static final long serialVersionUID = -6178010334400373240L;
        final BiPredicate<? super T, ? super T> comparer;
        final AtomicThrowable error;
        final EqualSubscriber<T> first;
        final EqualSubscriber<T> second;

        /* renamed from: v1, reason: collision with root package name */
        T f27294v1;
        T v2;
        final AtomicInteger wip;

        public EqualCoordinator(Subscriber<? super Boolean> subscriber, int i2, BiPredicate<? super T, ? super T> biPredicate) {
            super(subscriber);
            this.comparer = biPredicate;
            this.wip = new AtomicInteger();
            this.first = new EqualSubscriber<>(this, i2);
            this.second = new EqualSubscriber<>(this, i2);
            this.error = new AtomicThrowable();
        }

        @Override // io.reactivex.internal.subscriptions.DeferredScalarSubscription, org.reactivestreams.Subscription
        public void cancel() {
            super.cancel();
            this.first.cancel();
            this.second.cancel();
            if (this.wip.getAndIncrement() == 0) {
                this.first.clear();
                this.second.clear();
            }
        }

        public void cancelAndClear() {
            this.first.cancel();
            this.first.clear();
            this.second.cancel();
            this.second.clear();
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableSequenceEqual.EqualCoordinatorHelper
        public void drain() {
            if (this.wip.getAndIncrement() != 0) {
                return;
            }
            int iAddAndGet = 1;
            do {
                SimpleQueue<T> simpleQueue = this.first.queue;
                SimpleQueue<T> simpleQueue2 = this.second.queue;
                if (simpleQueue != null && simpleQueue2 != null) {
                    while (!isCancelled()) {
                        if (this.error.get() != null) {
                            cancelAndClear();
                            this.downstream.onError(this.error.terminate());
                            return;
                        }
                        boolean z2 = this.first.done;
                        T tPoll = this.f27294v1;
                        if (tPoll == null) {
                            try {
                                tPoll = simpleQueue.poll();
                                this.f27294v1 = tPoll;
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                cancelAndClear();
                                this.error.addThrowable(th);
                                this.downstream.onError(this.error.terminate());
                                return;
                            }
                        }
                        boolean z3 = tPoll == null;
                        boolean z4 = this.second.done;
                        T tPoll2 = this.v2;
                        if (tPoll2 == null) {
                            try {
                                tPoll2 = simpleQueue2.poll();
                                this.v2 = tPoll2;
                            } catch (Throwable th2) {
                                Exceptions.throwIfFatal(th2);
                                cancelAndClear();
                                this.error.addThrowable(th2);
                                this.downstream.onError(this.error.terminate());
                                return;
                            }
                        }
                        boolean z5 = tPoll2 == null;
                        if (z2 && z4 && z3 && z5) {
                            complete(Boolean.TRUE);
                            return;
                        }
                        if (z2 && z4 && z3 != z5) {
                            cancelAndClear();
                            complete(Boolean.FALSE);
                            return;
                        }
                        if (!z3 && !z5) {
                            try {
                                if (!this.comparer.test(tPoll, tPoll2)) {
                                    cancelAndClear();
                                    complete(Boolean.FALSE);
                                    return;
                                } else {
                                    this.f27294v1 = null;
                                    this.v2 = null;
                                    this.first.request();
                                    this.second.request();
                                }
                            } catch (Throwable th3) {
                                Exceptions.throwIfFatal(th3);
                                cancelAndClear();
                                this.error.addThrowable(th3);
                                this.downstream.onError(this.error.terminate());
                                return;
                            }
                        }
                    }
                    this.first.clear();
                    this.second.clear();
                    return;
                }
                if (isCancelled()) {
                    this.first.clear();
                    this.second.clear();
                    return;
                } else if (this.error.get() != null) {
                    cancelAndClear();
                    this.downstream.onError(this.error.terminate());
                    return;
                }
                iAddAndGet = this.wip.addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableSequenceEqual.EqualCoordinatorHelper
        public void innerError(Throwable th) {
            if (this.error.addThrowable(th)) {
                drain();
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        public void subscribe(Publisher<? extends T> publisher, Publisher<? extends T> publisher2) {
            publisher.subscribe(this.first);
            publisher2.subscribe(this.second);
        }
    }

    public interface EqualCoordinatorHelper {
        void drain();

        void innerError(Throwable th);
    }

    public static final class EqualSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = 4804128302091633067L;
        volatile boolean done;
        final int limit;
        final EqualCoordinatorHelper parent;
        final int prefetch;
        long produced;
        volatile SimpleQueue<T> queue;
        int sourceMode;

        public EqualSubscriber(EqualCoordinatorHelper equalCoordinatorHelper, int i2) {
            this.parent = equalCoordinatorHelper;
            this.limit = i2 - (i2 >> 2);
            this.prefetch = i2;
        }

        public void cancel() {
            SubscriptionHelper.cancel(this);
        }

        public void clear() {
            SimpleQueue<T> simpleQueue = this.queue;
            if (simpleQueue != null) {
                simpleQueue.clear();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            this.parent.drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.parent.innerError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.sourceMode != 0 || this.queue.offer(t2)) {
                this.parent.drain();
            } else {
                onError(new MissingBackpressureException());
            }
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int iRequestFusion = queueSubscription.requestFusion(3);
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

        public void request() {
            if (this.sourceMode != 1) {
                long j2 = this.produced + 1;
                if (j2 < this.limit) {
                    this.produced = j2;
                } else {
                    this.produced = 0L;
                    get().request(j2);
                }
            }
        }
    }

    public FlowableSequenceEqual(Publisher<? extends T> publisher, Publisher<? extends T> publisher2, BiPredicate<? super T, ? super T> biPredicate, int i2) {
        this.first = publisher;
        this.second = publisher2;
        this.comparer = biPredicate;
        this.prefetch = i2;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super Boolean> subscriber) {
        EqualCoordinator equalCoordinator = new EqualCoordinator(subscriber, this.prefetch, this.comparer);
        subscriber.onSubscribe(equalCoordinator);
        equalCoordinator.subscribe(this.first, this.second);
    }
}
