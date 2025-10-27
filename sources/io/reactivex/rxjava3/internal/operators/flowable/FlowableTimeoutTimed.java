package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionArbiter;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableTimeoutTimed<T> extends AbstractFlowableWithUpstream<T, T> {
    final Publisher<? extends T> other;
    final Scheduler scheduler;
    final long timeout;
    final TimeUnit unit;

    public static final class FallbackSubscriber<T> implements FlowableSubscriber<T> {
        final SubscriptionArbiter arbiter;
        final Subscriber<? super T> downstream;

        public FallbackSubscriber(Subscriber<? super T> actual, SubscriptionArbiter arbiter) {
            this.downstream = actual;
            this.arbiter = arbiter;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            this.downstream.onError(t2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            this.downstream.onNext(t2);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            this.arbiter.setSubscription(s2);
        }
    }

    public static final class TimeoutFallbackSubscriber<T> extends SubscriptionArbiter implements FlowableSubscriber<T>, TimeoutSupport {
        private static final long serialVersionUID = 3764492702657003550L;
        long consumed;
        final Subscriber<? super T> downstream;
        Publisher<? extends T> fallback;
        final AtomicLong index;
        final SequentialDisposable task;
        final long timeout;
        final TimeUnit unit;
        final AtomicReference<Subscription> upstream;
        final Scheduler.Worker worker;

        public TimeoutFallbackSubscriber(Subscriber<? super T> actual, long timeout, TimeUnit unit, Scheduler.Worker worker, Publisher<? extends T> fallback) {
            super(true);
            this.downstream = actual;
            this.timeout = timeout;
            this.unit = unit;
            this.worker = worker;
            this.fallback = fallback;
            this.task = new SequentialDisposable();
            this.upstream = new AtomicReference<>();
            this.index = new AtomicLong();
        }

        @Override // io.reactivex.rxjava3.internal.subscriptions.SubscriptionArbiter, org.reactivestreams.Subscription
        public void cancel() {
            super.cancel();
            this.worker.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.index.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
                this.task.dispose();
                this.downstream.onComplete();
                this.worker.dispose();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            if (this.index.getAndSet(Long.MAX_VALUE) == Long.MAX_VALUE) {
                RxJavaPlugins.onError(t2);
                return;
            }
            this.task.dispose();
            this.downstream.onError(t2);
            this.worker.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            long j2 = this.index.get();
            if (j2 != Long.MAX_VALUE) {
                long j3 = j2 + 1;
                if (this.index.compareAndSet(j2, j3)) {
                    this.task.get().dispose();
                    this.consumed++;
                    this.downstream.onNext(t2);
                    startTimeout(j3);
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.setOnce(this.upstream, s2)) {
                setSubscription(s2);
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableTimeoutTimed.TimeoutSupport
        public void onTimeout(long idx) {
            if (this.index.compareAndSet(idx, Long.MAX_VALUE)) {
                SubscriptionHelper.cancel(this.upstream);
                long j2 = this.consumed;
                if (j2 != 0) {
                    produced(j2);
                }
                Publisher<? extends T> publisher = this.fallback;
                this.fallback = null;
                publisher.subscribe(new FallbackSubscriber(this.downstream, this));
                this.worker.dispose();
            }
        }

        public void startTimeout(long nextIndex) {
            this.task.replace(this.worker.schedule(new TimeoutTask(nextIndex, this), this.timeout, this.unit));
        }
    }

    public static final class TimeoutSubscriber<T> extends AtomicLong implements FlowableSubscriber<T>, Subscription, TimeoutSupport {
        private static final long serialVersionUID = 3764492702657003550L;
        final Subscriber<? super T> downstream;
        final long timeout;
        final TimeUnit unit;
        final Scheduler.Worker worker;
        final SequentialDisposable task = new SequentialDisposable();
        final AtomicReference<Subscription> upstream = new AtomicReference<>();
        final AtomicLong requested = new AtomicLong();

        public TimeoutSubscriber(Subscriber<? super T> actual, long timeout, TimeUnit unit, Scheduler.Worker worker) {
            this.downstream = actual;
            this.timeout = timeout;
            this.unit = unit;
            this.worker = worker;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SubscriptionHelper.cancel(this.upstream);
            this.worker.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
                this.task.dispose();
                this.downstream.onComplete();
                this.worker.dispose();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            if (getAndSet(Long.MAX_VALUE) == Long.MAX_VALUE) {
                RxJavaPlugins.onError(t2);
                return;
            }
            this.task.dispose();
            this.downstream.onError(t2);
            this.worker.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            long j2 = get();
            if (j2 != Long.MAX_VALUE) {
                long j3 = 1 + j2;
                if (compareAndSet(j2, j3)) {
                    this.task.get().dispose();
                    this.downstream.onNext(t2);
                    startTimeout(j3);
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            SubscriptionHelper.deferredSetOnce(this.upstream, this.requested, s2);
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableTimeoutTimed.TimeoutSupport
        public void onTimeout(long idx) {
            if (compareAndSet(idx, Long.MAX_VALUE)) {
                SubscriptionHelper.cancel(this.upstream);
                this.downstream.onError(new TimeoutException(ExceptionHelper.timeoutMessage(this.timeout, this.unit)));
                this.worker.dispose();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            SubscriptionHelper.deferredRequest(this.upstream, this.requested, n2);
        }

        public void startTimeout(long nextIndex) {
            this.task.replace(this.worker.schedule(new TimeoutTask(nextIndex, this), this.timeout, this.unit));
        }
    }

    public interface TimeoutSupport {
        void onTimeout(long idx);
    }

    public static final class TimeoutTask implements Runnable {
        final long idx;
        final TimeoutSupport parent;

        public TimeoutTask(long idx, TimeoutSupport parent) {
            this.idx = idx;
            this.parent = parent;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.parent.onTimeout(this.idx);
        }
    }

    public FlowableTimeoutTimed(Flowable<T> source, long timeout, TimeUnit unit, Scheduler scheduler, Publisher<? extends T> other) {
        super(source);
        this.timeout = timeout;
        this.unit = unit;
        this.scheduler = scheduler;
        this.other = other;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        if (this.other == null) {
            TimeoutSubscriber timeoutSubscriber = new TimeoutSubscriber(s2, this.timeout, this.unit, this.scheduler.createWorker());
            s2.onSubscribe(timeoutSubscriber);
            timeoutSubscriber.startTimeout(0L);
            this.source.subscribe((FlowableSubscriber) timeoutSubscriber);
            return;
        }
        TimeoutFallbackSubscriber timeoutFallbackSubscriber = new TimeoutFallbackSubscriber(s2, this.timeout, this.unit, this.scheduler.createWorker(), this.other);
        s2.onSubscribe(timeoutFallbackSubscriber);
        timeoutFallbackSubscriber.startTimeout(0L);
        this.source.subscribe((FlowableSubscriber) timeoutFallbackSubscriber);
    }
}
