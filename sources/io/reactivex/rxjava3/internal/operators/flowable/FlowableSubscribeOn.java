package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableSubscribeOn<T> extends AbstractFlowableWithUpstream<T, T> {
    final boolean nonScheduledRequests;
    final Scheduler scheduler;

    public static final class SubscribeOnSubscriber<T> extends AtomicReference<Thread> implements FlowableSubscriber<T>, Subscription, Runnable {
        private static final long serialVersionUID = 8094547886072529208L;
        final Subscriber<? super T> downstream;
        final boolean nonScheduledRequests;
        Publisher<T> source;
        final Scheduler.Worker worker;
        final AtomicReference<Subscription> upstream = new AtomicReference<>();
        final AtomicLong requested = new AtomicLong();

        public static final class Request implements Runnable {

            /* renamed from: n, reason: collision with root package name */
            final long f27349n;
            final Subscription upstream;

            public Request(Subscription s2, long n2) {
                this.upstream = s2;
                this.f27349n = n2;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.upstream.request(this.f27349n);
            }
        }

        public SubscribeOnSubscriber(Subscriber<? super T> actual, Scheduler.Worker worker, Publisher<T> source, boolean requestOn) {
            this.downstream = actual;
            this.worker = worker;
            this.source = source;
            this.nonScheduledRequests = !requestOn;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SubscriptionHelper.cancel(this.upstream);
            this.worker.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.downstream.onComplete();
            this.worker.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            this.downstream.onError(t2);
            this.worker.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            this.downstream.onNext(t2);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.setOnce(this.upstream, s2)) {
                long andSet = this.requested.getAndSet(0L);
                if (andSet != 0) {
                    requestUpstream(andSet, s2);
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(final long n2) {
            if (SubscriptionHelper.validate(n2)) {
                Subscription subscription = this.upstream.get();
                if (subscription != null) {
                    requestUpstream(n2, subscription);
                    return;
                }
                BackpressureHelper.add(this.requested, n2);
                Subscription subscription2 = this.upstream.get();
                if (subscription2 != null) {
                    long andSet = this.requested.getAndSet(0L);
                    if (andSet != 0) {
                        requestUpstream(andSet, subscription2);
                    }
                }
            }
        }

        public void requestUpstream(final long n2, final Subscription s2) {
            if (this.nonScheduledRequests || Thread.currentThread() == get()) {
                s2.request(n2);
            } else {
                this.worker.schedule(new Request(s2, n2));
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            lazySet(Thread.currentThread());
            Publisher<T> publisher = this.source;
            this.source = null;
            publisher.subscribe(this);
        }
    }

    public FlowableSubscribeOn(Flowable<T> source, Scheduler scheduler, boolean nonScheduledRequests) {
        super(source);
        this.scheduler = scheduler;
        this.nonScheduledRequests = nonScheduledRequests;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(final Subscriber<? super T> s2) {
        Scheduler.Worker workerCreateWorker = this.scheduler.createWorker();
        SubscribeOnSubscriber subscribeOnSubscriber = new SubscribeOnSubscriber(s2, workerCreateWorker, this.source, this.nonScheduledRequests);
        s2.onSubscribe(subscribeOnSubscriber);
        workerCreateWorker.schedule(subscribeOnSubscriber);
    }
}
