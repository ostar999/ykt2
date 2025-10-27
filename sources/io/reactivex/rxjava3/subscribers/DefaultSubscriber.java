package io.reactivex.rxjava3.subscribers;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.EndConsumerHelper;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public abstract class DefaultSubscriber<T> implements FlowableSubscriber<T> {
    Subscription upstream;

    public final void cancel() {
        Subscription subscription = this.upstream;
        this.upstream = SubscriptionHelper.CANCELLED;
        subscription.cancel();
    }

    public void onStart() {
        request(Long.MAX_VALUE);
    }

    @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
    public final void onSubscribe(Subscription s2) {
        if (EndConsumerHelper.validate(this.upstream, s2, getClass())) {
            this.upstream = s2;
            onStart();
        }
    }

    public final void request(long n2) {
        Subscription subscription = this.upstream;
        if (subscription != null) {
            subscription.request(n2);
        }
    }
}
