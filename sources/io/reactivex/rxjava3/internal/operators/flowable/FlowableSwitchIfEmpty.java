package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionArbiter;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableSwitchIfEmpty<T> extends AbstractFlowableWithUpstream<T, T> {
    final Publisher<? extends T> other;

    public static final class SwitchIfEmptySubscriber<T> implements FlowableSubscriber<T> {
        final Subscriber<? super T> downstream;
        final Publisher<? extends T> other;
        boolean empty = true;
        final SubscriptionArbiter arbiter = new SubscriptionArbiter(false);

        public SwitchIfEmptySubscriber(Subscriber<? super T> actual, Publisher<? extends T> other) {
            this.downstream = actual;
            this.other = other;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.empty) {
                this.downstream.onComplete();
            } else {
                this.empty = false;
                this.other.subscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            this.downstream.onError(t2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.empty) {
                this.empty = false;
            }
            this.downstream.onNext(t2);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            this.arbiter.setSubscription(s2);
        }
    }

    public FlowableSwitchIfEmpty(Flowable<T> source, Publisher<? extends T> other) {
        super(source);
        this.other = other;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        SwitchIfEmptySubscriber switchIfEmptySubscriber = new SwitchIfEmptySubscriber(s2, this.other);
        s2.onSubscribe(switchIfEmptySubscriber.arbiter);
        this.source.subscribe((FlowableSubscriber) switchIfEmptySubscriber);
    }
}
