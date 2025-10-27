package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableSkip<T> extends AbstractFlowableWithUpstream<T, T> {

    /* renamed from: n, reason: collision with root package name */
    final long f27296n;

    public static final class SkipSubscriber<T> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super T> downstream;
        long remaining;
        Subscription upstream;

        public SkipSubscriber(Subscriber<? super T> subscriber, long j2) {
            this.downstream = subscriber;
            this.remaining = j2;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            long j2 = this.remaining;
            if (j2 != 0) {
                this.remaining = j2 - 1;
            } else {
                this.downstream.onNext(t2);
            }
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                long j2 = this.remaining;
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                subscription.request(j2);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j2) {
            this.upstream.request(j2);
        }
    }

    public FlowableSkip(Flowable<T> flowable, long j2) {
        super(flowable);
        this.f27296n = j2;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber) new SkipSubscriber(subscriber, this.f27296n));
    }
}
