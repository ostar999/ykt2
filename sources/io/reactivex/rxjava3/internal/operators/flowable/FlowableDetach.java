package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.EmptyComponent;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableDetach<T> extends AbstractFlowableWithUpstream<T, T> {

    public static final class DetachSubscriber<T> implements FlowableSubscriber<T>, Subscription {
        Subscriber<? super T> downstream;
        Subscription upstream;

        public DetachSubscriber(Subscriber<? super T> downstream) {
            this.downstream = downstream;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            Subscription subscription = this.upstream;
            this.upstream = EmptyComponent.INSTANCE;
            this.downstream = EmptyComponent.asSubscriber();
            subscription.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            Subscriber<? super T> subscriber = this.downstream;
            this.upstream = EmptyComponent.INSTANCE;
            this.downstream = EmptyComponent.asSubscriber();
            subscriber.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            Subscriber<? super T> subscriber = this.downstream;
            this.upstream = EmptyComponent.INSTANCE;
            this.downstream = EmptyComponent.asSubscriber();
            subscriber.onError(t2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            this.downstream.onNext(t2);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            this.upstream.request(n2);
        }
    }

    public FlowableDetach(Flowable<T> source) {
        super(source);
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        this.source.subscribe((FlowableSubscriber) new DetachSubscriber(s2));
    }
}
