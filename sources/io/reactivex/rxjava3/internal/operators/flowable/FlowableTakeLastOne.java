package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableTakeLastOne<T> extends AbstractFlowableWithUpstream<T, T> {

    public static final class TakeLastOneSubscriber<T> extends DeferredScalarSubscription<T> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -5467847744262967226L;
        Subscription upstream;

        public TakeLastOneSubscriber(Subscriber<? super T> downstream) {
            super(downstream);
        }

        @Override // io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription, org.reactivestreams.Subscription
        public void cancel() {
            super.cancel();
            this.upstream.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            T t2 = this.value;
            if (t2 != null) {
                complete(t2);
            } else {
                this.downstream.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            this.value = null;
            this.downstream.onError(t2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            this.value = t2;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                this.downstream.onSubscribe(this);
                s2.request(Long.MAX_VALUE);
            }
        }
    }

    public FlowableTakeLastOne(Flowable<T> source) {
        super(source);
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        this.source.subscribe((FlowableSubscriber) new TakeLastOneSubscriber(s2));
    }
}
