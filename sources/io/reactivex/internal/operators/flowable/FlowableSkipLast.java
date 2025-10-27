package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.ArrayDeque;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableSkipLast<T> extends AbstractFlowableWithUpstream<T, T> {
    final int skip;

    public static final class SkipLastSubscriber<T> extends ArrayDeque<T> implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -3807491841935125653L;
        final Subscriber<? super T> downstream;
        final int skip;
        Subscription upstream;

        public SkipLastSubscriber(Subscriber<? super T> subscriber, int i2) {
            super(i2);
            this.downstream = subscriber;
            this.skip = i2;
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
            if (this.skip == size()) {
                this.downstream.onNext(poll());
            } else {
                this.upstream.request(1L);
            }
            offer(t2);
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j2) {
            this.upstream.request(j2);
        }
    }

    public FlowableSkipLast(Flowable<T> flowable, int i2) {
        super(flowable);
        this.skip = i2;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber) new SkipLastSubscriber(subscriber, this.skip));
    }
}
