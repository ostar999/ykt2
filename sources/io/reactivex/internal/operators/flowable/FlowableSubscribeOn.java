package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
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
            final long f27297n;
            final Subscription upstream;

            public Request(Subscription subscription, long j2) {
                this.upstream = subscription;
                this.f27297n = j2;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.upstream.request(this.f27297n);
            }
        }

        public SubscribeOnSubscriber(Subscriber<? super T> subscriber, Scheduler.Worker worker, Publisher<T> publisher, boolean z2) {
            this.downstream = subscriber;
            this.worker = worker;
            this.source = publisher;
            this.nonScheduledRequests = !z2;
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
        public void onError(Throwable th) {
            this.downstream.onError(th);
            this.worker.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            this.downstream.onNext(t2);
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this.upstream, subscription)) {
                long andSet = this.requested.getAndSet(0L);
                if (andSet != 0) {
                    requestUpstream(andSet, subscription);
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                Subscription subscription = this.upstream.get();
                if (subscription != null) {
                    requestUpstream(j2, subscription);
                    return;
                }
                BackpressureHelper.add(this.requested, j2);
                Subscription subscription2 = this.upstream.get();
                if (subscription2 != null) {
                    long andSet = this.requested.getAndSet(0L);
                    if (andSet != 0) {
                        requestUpstream(andSet, subscription2);
                    }
                }
            }
        }

        public void requestUpstream(long j2, Subscription subscription) {
            if (this.nonScheduledRequests || Thread.currentThread() == get()) {
                subscription.request(j2);
            } else {
                this.worker.schedule(new Request(subscription, j2));
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

    public FlowableSubscribeOn(Flowable<T> flowable, Scheduler scheduler, boolean z2) {
        super(flowable);
        this.scheduler = scheduler;
        this.nonScheduledRequests = z2;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        Scheduler.Worker workerCreateWorker = this.scheduler.createWorker();
        SubscribeOnSubscriber subscribeOnSubscriber = new SubscribeOnSubscriber(subscriber, workerCreateWorker, this.source, this.nonScheduledRequests);
        subscriber.onSubscribe(subscribeOnSubscriber);
        workerCreateWorker.schedule(subscribeOnSubscriber);
    }
}
