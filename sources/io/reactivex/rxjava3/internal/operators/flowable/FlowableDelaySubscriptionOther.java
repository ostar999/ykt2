package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableDelaySubscriptionOther<T, U> extends Flowable<T> {
    final Publisher<? extends T> main;
    final Publisher<U> other;

    public static final class MainSubscriber<T> extends AtomicLong implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = 2259811067697317255L;
        final Subscriber<? super T> downstream;
        final Publisher<? extends T> main;
        final MainSubscriber<T>.OtherSubscriber other = new OtherSubscriber();
        final AtomicReference<Subscription> upstream = new AtomicReference<>();

        public final class OtherSubscriber extends AtomicReference<Subscription> implements FlowableSubscriber<Object> {
            private static final long serialVersionUID = -3892798459447644106L;

            public OtherSubscriber() {
            }

            @Override // org.reactivestreams.Subscriber
            public void onComplete() {
                if (get() != SubscriptionHelper.CANCELLED) {
                    MainSubscriber.this.next();
                }
            }

            @Override // org.reactivestreams.Subscriber
            public void onError(Throwable t2) {
                if (get() != SubscriptionHelper.CANCELLED) {
                    MainSubscriber.this.downstream.onError(t2);
                } else {
                    RxJavaPlugins.onError(t2);
                }
            }

            @Override // org.reactivestreams.Subscriber
            public void onNext(Object t2) {
                Subscription subscription = get();
                SubscriptionHelper subscriptionHelper = SubscriptionHelper.CANCELLED;
                if (subscription != subscriptionHelper) {
                    lazySet(subscriptionHelper);
                    subscription.cancel();
                    MainSubscriber.this.next();
                }
            }

            @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
            public void onSubscribe(Subscription s2) {
                if (SubscriptionHelper.setOnce(this, s2)) {
                    s2.request(Long.MAX_VALUE);
                }
            }
        }

        public MainSubscriber(Subscriber<? super T> downstream, Publisher<? extends T> main) {
            this.downstream = downstream;
            this.main = main;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SubscriptionHelper.cancel(this.other);
            SubscriptionHelper.cancel(this.upstream);
        }

        public void next() {
            this.main.subscribe(this);
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
            this.downstream.onNext(t2);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            SubscriptionHelper.deferredSetOnce(this.upstream, this, s2);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            if (SubscriptionHelper.validate(n2)) {
                SubscriptionHelper.deferredRequest(this.upstream, this, n2);
            }
        }
    }

    public FlowableDelaySubscriptionOther(Publisher<? extends T> main, Publisher<U> other) {
        this.main = main;
        this.other = other;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(final Subscriber<? super T> child) {
        MainSubscriber mainSubscriber = new MainSubscriber(child, this.main);
        child.onSubscribe(mainSubscriber);
        this.other.subscribe(mainSubscriber.other);
    }
}
