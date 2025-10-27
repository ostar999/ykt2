package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscribers.InnerQueuedSubscriber;
import io.reactivex.internal.subscribers.InnerQueuedSubscriberSupport;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableConcatMapEager<T, R> extends AbstractFlowableWithUpstream<T, R> {
    final ErrorMode errorMode;
    final Function<? super T, ? extends Publisher<? extends R>> mapper;
    final int maxConcurrency;
    final int prefetch;

    public static final class ConcatMapEagerDelayErrorSubscriber<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, InnerQueuedSubscriberSupport<R> {
        private static final long serialVersionUID = -4255299542215038287L;
        volatile boolean cancelled;
        volatile InnerQueuedSubscriber<R> current;
        volatile boolean done;
        final Subscriber<? super R> downstream;
        final ErrorMode errorMode;
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        final int maxConcurrency;
        final int prefetch;
        final SpscLinkedArrayQueue<InnerQueuedSubscriber<R>> subscribers;
        Subscription upstream;
        final AtomicThrowable errors = new AtomicThrowable();
        final AtomicLong requested = new AtomicLong();

        public ConcatMapEagerDelayErrorSubscriber(Subscriber<? super R> subscriber, Function<? super T, ? extends Publisher<? extends R>> function, int i2, int i3, ErrorMode errorMode) {
            this.downstream = subscriber;
            this.mapper = function;
            this.maxConcurrency = i2;
            this.prefetch = i3;
            this.errorMode = errorMode;
            this.subscribers = new SpscLinkedArrayQueue<>(Math.min(i3, i2));
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.upstream.cancel();
            drainAndCancel();
        }

        public void cancelAll() {
            while (true) {
                InnerQueuedSubscriber<R> innerQueuedSubscriberPoll = this.subscribers.poll();
                if (innerQueuedSubscriberPoll == null) {
                    return;
                } else {
                    innerQueuedSubscriberPoll.cancel();
                }
            }
        }

        @Override // io.reactivex.internal.subscribers.InnerQueuedSubscriberSupport
        public void drain() {
            InnerQueuedSubscriber<R> innerQueuedSubscriberPoll;
            int i2;
            boolean z2;
            long j2;
            long j3;
            SimpleQueue<R> simpleQueueQueue;
            if (getAndIncrement() != 0) {
                return;
            }
            InnerQueuedSubscriber<R> innerQueuedSubscriber = this.current;
            Subscriber<? super R> subscriber = this.downstream;
            ErrorMode errorMode = this.errorMode;
            int iAddAndGet = 1;
            while (true) {
                long j4 = this.requested.get();
                if (innerQueuedSubscriber != null) {
                    innerQueuedSubscriberPoll = innerQueuedSubscriber;
                } else {
                    if (errorMode != ErrorMode.END && this.errors.get() != null) {
                        cancelAll();
                        subscriber.onError(this.errors.terminate());
                        return;
                    }
                    boolean z3 = this.done;
                    innerQueuedSubscriberPoll = this.subscribers.poll();
                    if (z3 && innerQueuedSubscriberPoll == null) {
                        Throwable thTerminate = this.errors.terminate();
                        if (thTerminate != null) {
                            subscriber.onError(thTerminate);
                            return;
                        } else {
                            subscriber.onComplete();
                            return;
                        }
                    }
                    if (innerQueuedSubscriberPoll != null) {
                        this.current = innerQueuedSubscriberPoll;
                    }
                }
                if (innerQueuedSubscriberPoll == null || (simpleQueueQueue = innerQueuedSubscriberPoll.queue()) == null) {
                    i2 = iAddAndGet;
                    z2 = false;
                    j2 = 0;
                    j3 = 0;
                } else {
                    j3 = 0;
                    while (true) {
                        i2 = iAddAndGet;
                        if (j3 == j4) {
                            break;
                        }
                        if (this.cancelled) {
                            cancelAll();
                            return;
                        }
                        if (errorMode == ErrorMode.IMMEDIATE && this.errors.get() != null) {
                            this.current = null;
                            innerQueuedSubscriberPoll.cancel();
                            cancelAll();
                            subscriber.onError(this.errors.terminate());
                            return;
                        }
                        boolean zIsDone = innerQueuedSubscriberPoll.isDone();
                        try {
                            R rPoll = simpleQueueQueue.poll();
                            boolean z4 = rPoll == null;
                            if (zIsDone && z4) {
                                this.current = null;
                                this.upstream.request(1L);
                                innerQueuedSubscriberPoll = null;
                                z2 = true;
                                break;
                            }
                            if (z4) {
                                break;
                            }
                            subscriber.onNext(rPoll);
                            j3++;
                            innerQueuedSubscriberPoll.requestOne();
                            iAddAndGet = i2;
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            this.current = null;
                            innerQueuedSubscriberPoll.cancel();
                            cancelAll();
                            subscriber.onError(th);
                            return;
                        }
                    }
                    z2 = false;
                    if (j3 == j4) {
                        if (this.cancelled) {
                            cancelAll();
                            return;
                        }
                        if (errorMode == ErrorMode.IMMEDIATE && this.errors.get() != null) {
                            this.current = null;
                            innerQueuedSubscriberPoll.cancel();
                            cancelAll();
                            subscriber.onError(this.errors.terminate());
                            return;
                        }
                        boolean zIsDone2 = innerQueuedSubscriberPoll.isDone();
                        boolean zIsEmpty = simpleQueueQueue.isEmpty();
                        if (zIsDone2 && zIsEmpty) {
                            this.current = null;
                            this.upstream.request(1L);
                            innerQueuedSubscriberPoll = null;
                            z2 = true;
                        }
                    }
                    j2 = 0;
                }
                if (j3 != j2 && j4 != Long.MAX_VALUE) {
                    this.requested.addAndGet(-j3);
                }
                if (z2) {
                    innerQueuedSubscriber = innerQueuedSubscriberPoll;
                    iAddAndGet = i2;
                } else {
                    iAddAndGet = addAndGet(-i2);
                    if (iAddAndGet == 0) {
                        return;
                    } else {
                        innerQueuedSubscriber = innerQueuedSubscriberPoll;
                    }
                }
            }
        }

        public void drainAndCancel() {
            if (getAndIncrement() == 0) {
                do {
                    cancelAll();
                } while (decrementAndGet() != 0);
            }
        }

        @Override // io.reactivex.internal.subscribers.InnerQueuedSubscriberSupport
        public void innerComplete(InnerQueuedSubscriber<R> innerQueuedSubscriber) {
            innerQueuedSubscriber.setDone();
            drain();
        }

        @Override // io.reactivex.internal.subscribers.InnerQueuedSubscriberSupport
        public void innerError(InnerQueuedSubscriber<R> innerQueuedSubscriber, Throwable th) {
            if (!this.errors.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            innerQueuedSubscriber.setDone();
            if (this.errorMode != ErrorMode.END) {
                this.upstream.cancel();
            }
            drain();
        }

        @Override // io.reactivex.internal.subscribers.InnerQueuedSubscriberSupport
        public void innerNext(InnerQueuedSubscriber<R> innerQueuedSubscriber, R r2) {
            if (innerQueuedSubscriber.queue().offer(r2)) {
                drain();
            } else {
                innerQueuedSubscriber.cancel();
                innerError(innerQueuedSubscriber, new MissingBackpressureException());
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (!this.errors.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else {
                this.done = true;
                drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            try {
                Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.mapper.apply(t2), "The mapper returned a null Publisher");
                InnerQueuedSubscriber<R> innerQueuedSubscriber = new InnerQueuedSubscriber<>(this, this.prefetch);
                if (this.cancelled) {
                    return;
                }
                this.subscribers.offer(innerQueuedSubscriber);
                publisher.subscribe(innerQueuedSubscriber);
                if (this.cancelled) {
                    innerQueuedSubscriber.cancel();
                    drainAndCancel();
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.cancel();
                onError(th);
            }
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                int i2 = this.maxConcurrency;
                subscription.request(i2 == Integer.MAX_VALUE ? Long.MAX_VALUE : i2);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                BackpressureHelper.add(this.requested, j2);
                drain();
            }
        }
    }

    public FlowableConcatMapEager(Flowable<T> flowable, Function<? super T, ? extends Publisher<? extends R>> function, int i2, int i3, ErrorMode errorMode) {
        super(flowable);
        this.mapper = function;
        this.maxConcurrency = i2;
        this.prefetch = i3;
        this.errorMode = errorMode;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super R> subscriber) {
        this.source.subscribe((FlowableSubscriber) new ConcatMapEagerDelayErrorSubscriber(subscriber, this.mapper, this.maxConcurrency, this.prefetch, this.errorMode));
    }
}
