package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableTake<T> extends AbstractFlowableWithUpstream<T, T> {
    final long limit;

    public static final class TakeSubscriber<T> extends AtomicBoolean implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -5636543848937116287L;
        boolean done;
        final Subscriber<? super T> downstream;
        final long limit;
        long remaining;
        Subscription upstream;

        public TakeSubscriber(Subscriber<? super T> subscriber, long j2) {
            this.downstream = subscriber;
            this.limit = j2;
            this.remaining = j2;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.upstream.cancel();
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            long j2 = this.remaining;
            long j3 = j2 - 1;
            this.remaining = j3;
            if (j2 > 0) {
                boolean z2 = j3 == 0;
                this.downstream.onNext(t2);
                if (z2) {
                    this.upstream.cancel();
                    onComplete();
                }
            }
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                if (this.limit != 0) {
                    this.downstream.onSubscribe(this);
                    return;
                }
                subscription.cancel();
                this.done = true;
                EmptySubscription.complete(this.downstream);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                if (get() || !compareAndSet(false, true) || j2 < this.limit) {
                    this.upstream.request(j2);
                } else {
                    this.upstream.request(Long.MAX_VALUE);
                }
            }
        }
    }

    public FlowableTake(Flowable<T> flowable, long j2) {
        super(flowable);
        this.limit = j2;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber) new TakeSubscriber(subscriber, this.limit));
    }
}
