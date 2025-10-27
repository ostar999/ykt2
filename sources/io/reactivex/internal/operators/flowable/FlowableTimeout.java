package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.flowable.FlowableTimeoutTimed;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableTimeout<T, U, V> extends AbstractFlowableWithUpstream<T, T> {
    final Publisher<U> firstTimeoutIndicator;
    final Function<? super T, ? extends Publisher<V>> itemTimeoutIndicator;
    final Publisher<? extends T> other;

    public static final class TimeoutConsumer extends AtomicReference<Subscription> implements FlowableSubscriber<Object>, Disposable {
        private static final long serialVersionUID = 8708641127342403073L;
        final long idx;
        final TimeoutSelectorSupport parent;

        public TimeoutConsumer(long j2, TimeoutSelectorSupport timeoutSelectorSupport) {
            this.idx = j2;
            this.parent = timeoutSelectorSupport;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            SubscriptionHelper.cancel(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() == SubscriptionHelper.CANCELLED;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            Object obj = get();
            SubscriptionHelper subscriptionHelper = SubscriptionHelper.CANCELLED;
            if (obj != subscriptionHelper) {
                lazySet(subscriptionHelper);
                this.parent.onTimeout(this.idx);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            Object obj = get();
            SubscriptionHelper subscriptionHelper = SubscriptionHelper.CANCELLED;
            if (obj == subscriptionHelper) {
                RxJavaPlugins.onError(th);
            } else {
                lazySet(subscriptionHelper);
                this.parent.onTimeoutError(this.idx, th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            Subscription subscription = (Subscription) get();
            SubscriptionHelper subscriptionHelper = SubscriptionHelper.CANCELLED;
            if (subscription != subscriptionHelper) {
                subscription.cancel();
                lazySet(subscriptionHelper);
                this.parent.onTimeout(this.idx);
            }
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this, subscription, Long.MAX_VALUE);
        }
    }

    public static final class TimeoutFallbackSubscriber<T> extends SubscriptionArbiter implements FlowableSubscriber<T>, TimeoutSelectorSupport {
        private static final long serialVersionUID = 3764492702657003550L;
        long consumed;
        final Subscriber<? super T> downstream;
        Publisher<? extends T> fallback;
        final AtomicLong index;
        final Function<? super T, ? extends Publisher<?>> itemTimeoutIndicator;
        final SequentialDisposable task;
        final AtomicReference<Subscription> upstream;

        public TimeoutFallbackSubscriber(Subscriber<? super T> subscriber, Function<? super T, ? extends Publisher<?>> function, Publisher<? extends T> publisher) {
            super(true);
            this.downstream = subscriber;
            this.itemTimeoutIndicator = function;
            this.task = new SequentialDisposable();
            this.upstream = new AtomicReference<>();
            this.fallback = publisher;
            this.index = new AtomicLong();
        }

        @Override // io.reactivex.internal.subscriptions.SubscriptionArbiter, org.reactivestreams.Subscription
        public void cancel() {
            super.cancel();
            this.task.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.index.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
                this.task.dispose();
                this.downstream.onComplete();
                this.task.dispose();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.index.getAndSet(Long.MAX_VALUE) == Long.MAX_VALUE) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.task.dispose();
            this.downstream.onError(th);
            this.task.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            long j2 = this.index.get();
            if (j2 != Long.MAX_VALUE) {
                long j3 = j2 + 1;
                if (this.index.compareAndSet(j2, j3)) {
                    Disposable disposable = this.task.get();
                    if (disposable != null) {
                        disposable.dispose();
                    }
                    this.consumed++;
                    this.downstream.onNext(t2);
                    try {
                        Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.itemTimeoutIndicator.apply(t2), "The itemTimeoutIndicator returned a null Publisher.");
                        TimeoutConsumer timeoutConsumer = new TimeoutConsumer(j3, this);
                        if (this.task.replace(timeoutConsumer)) {
                            publisher.subscribe(timeoutConsumer);
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.upstream.get().cancel();
                        this.index.getAndSet(Long.MAX_VALUE);
                        this.downstream.onError(th);
                    }
                }
            }
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this.upstream, subscription)) {
                setSubscription(subscription);
            }
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableTimeoutTimed.TimeoutSupport
        public void onTimeout(long j2) {
            if (this.index.compareAndSet(j2, Long.MAX_VALUE)) {
                SubscriptionHelper.cancel(this.upstream);
                Publisher<? extends T> publisher = this.fallback;
                this.fallback = null;
                long j3 = this.consumed;
                if (j3 != 0) {
                    produced(j3);
                }
                publisher.subscribe(new FlowableTimeoutTimed.FallbackSubscriber(this.downstream, this));
            }
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableTimeout.TimeoutSelectorSupport
        public void onTimeoutError(long j2, Throwable th) {
            if (!this.index.compareAndSet(j2, Long.MAX_VALUE)) {
                RxJavaPlugins.onError(th);
            } else {
                SubscriptionHelper.cancel(this.upstream);
                this.downstream.onError(th);
            }
        }

        public void startFirstTimeout(Publisher<?> publisher) {
            if (publisher != null) {
                TimeoutConsumer timeoutConsumer = new TimeoutConsumer(0L, this);
                if (this.task.replace(timeoutConsumer)) {
                    publisher.subscribe(timeoutConsumer);
                }
            }
        }
    }

    public interface TimeoutSelectorSupport extends FlowableTimeoutTimed.TimeoutSupport {
        void onTimeoutError(long j2, Throwable th);
    }

    public static final class TimeoutSubscriber<T> extends AtomicLong implements FlowableSubscriber<T>, Subscription, TimeoutSelectorSupport {
        private static final long serialVersionUID = 3764492702657003550L;
        final Subscriber<? super T> downstream;
        final Function<? super T, ? extends Publisher<?>> itemTimeoutIndicator;
        final SequentialDisposable task = new SequentialDisposable();
        final AtomicReference<Subscription> upstream = new AtomicReference<>();
        final AtomicLong requested = new AtomicLong();

        public TimeoutSubscriber(Subscriber<? super T> subscriber, Function<? super T, ? extends Publisher<?>> function) {
            this.downstream = subscriber;
            this.itemTimeoutIndicator = function;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SubscriptionHelper.cancel(this.upstream);
            this.task.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
                this.task.dispose();
                this.downstream.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (getAndSet(Long.MAX_VALUE) == Long.MAX_VALUE) {
                RxJavaPlugins.onError(th);
            } else {
                this.task.dispose();
                this.downstream.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            long j2 = get();
            if (j2 != Long.MAX_VALUE) {
                long j3 = 1 + j2;
                if (compareAndSet(j2, j3)) {
                    Disposable disposable = this.task.get();
                    if (disposable != null) {
                        disposable.dispose();
                    }
                    this.downstream.onNext(t2);
                    try {
                        Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.itemTimeoutIndicator.apply(t2), "The itemTimeoutIndicator returned a null Publisher.");
                        TimeoutConsumer timeoutConsumer = new TimeoutConsumer(j3, this);
                        if (this.task.replace(timeoutConsumer)) {
                            publisher.subscribe(timeoutConsumer);
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.upstream.get().cancel();
                        getAndSet(Long.MAX_VALUE);
                        this.downstream.onError(th);
                    }
                }
            }
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.deferredSetOnce(this.upstream, this.requested, subscription);
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableTimeoutTimed.TimeoutSupport
        public void onTimeout(long j2) {
            if (compareAndSet(j2, Long.MAX_VALUE)) {
                SubscriptionHelper.cancel(this.upstream);
                this.downstream.onError(new TimeoutException());
            }
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableTimeout.TimeoutSelectorSupport
        public void onTimeoutError(long j2, Throwable th) {
            if (!compareAndSet(j2, Long.MAX_VALUE)) {
                RxJavaPlugins.onError(th);
            } else {
                SubscriptionHelper.cancel(this.upstream);
                this.downstream.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j2) {
            SubscriptionHelper.deferredRequest(this.upstream, this.requested, j2);
        }

        public void startFirstTimeout(Publisher<?> publisher) {
            if (publisher != null) {
                TimeoutConsumer timeoutConsumer = new TimeoutConsumer(0L, this);
                if (this.task.replace(timeoutConsumer)) {
                    publisher.subscribe(timeoutConsumer);
                }
            }
        }
    }

    public FlowableTimeout(Flowable<T> flowable, Publisher<U> publisher, Function<? super T, ? extends Publisher<V>> function, Publisher<? extends T> publisher2) {
        super(flowable);
        this.firstTimeoutIndicator = publisher;
        this.itemTimeoutIndicator = function;
        this.other = publisher2;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        if (this.other == null) {
            TimeoutSubscriber timeoutSubscriber = new TimeoutSubscriber(subscriber, this.itemTimeoutIndicator);
            subscriber.onSubscribe(timeoutSubscriber);
            timeoutSubscriber.startFirstTimeout(this.firstTimeoutIndicator);
            this.source.subscribe((FlowableSubscriber) timeoutSubscriber);
            return;
        }
        TimeoutFallbackSubscriber timeoutFallbackSubscriber = new TimeoutFallbackSubscriber(subscriber, this.itemTimeoutIndicator, this.other);
        subscriber.onSubscribe(timeoutFallbackSubscriber);
        timeoutFallbackSubscriber.startFirstTimeout(this.firstTimeoutIndicator);
        this.source.subscribe((FlowableSubscriber) timeoutFallbackSubscriber);
    }
}
