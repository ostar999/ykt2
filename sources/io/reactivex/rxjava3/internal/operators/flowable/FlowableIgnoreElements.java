package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.operators.QueueSubscription;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableIgnoreElements<T> extends AbstractFlowableWithUpstream<T, T> {

    public static final class IgnoreElementsSubscriber<T> implements FlowableSubscriber<T>, QueueSubscription<T> {
        final Subscriber<? super T> downstream;
        Subscription upstream;

        public IgnoreElementsSubscriber(Subscriber<? super T> downstream) {
            this.downstream = downstream;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public void clear() {
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean isEmpty() {
            return true;
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean offer(T e2) {
            throw new UnsupportedOperationException("Should not be called!");
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
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                this.downstream.onSubscribe(this);
                s2.request(Long.MAX_VALUE);
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public T poll() {
            return null;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int mode) {
            return mode & 2;
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean offer(T v12, T v2) {
            throw new UnsupportedOperationException("Should not be called!");
        }
    }

    public FlowableIgnoreElements(Flowable<T> source) {
        super(source);
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(final Subscriber<? super T> t2) {
        this.source.subscribe((FlowableSubscriber) new IgnoreElementsSubscriber(t2));
    }
}
