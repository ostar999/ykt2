package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableSkip<T> extends AbstractFlowableWithUpstream<T, T> {

    /* renamed from: n, reason: collision with root package name */
    final long f27348n;

    public static final class SkipSubscriber<T> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super T> downstream;
        long remaining;
        Subscription upstream;

        public SkipSubscriber(Subscriber<? super T> actual, long n2) {
            this.downstream = actual;
            this.remaining = n2;
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
        public void onError(Throwable t2) {
            this.downstream.onError(t2);
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

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                long j2 = this.remaining;
                this.upstream = s2;
                this.downstream.onSubscribe(this);
                s2.request(j2);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            this.upstream.request(n2);
        }
    }

    public FlowableSkip(Flowable<T> source, long n2) {
        super(source);
        this.f27348n = n2;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        this.source.subscribe((FlowableSubscriber) new SkipSubscriber(s2, this.f27348n));
    }
}
