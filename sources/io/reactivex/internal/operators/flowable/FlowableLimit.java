package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableLimit<T> extends AbstractFlowableWithUpstream<T, T> {

    /* renamed from: n, reason: collision with root package name */
    final long f27292n;

    public static final class LimitSubscriber<T> extends AtomicLong implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = 2288246011222124525L;
        final Subscriber<? super T> downstream;
        long remaining;
        Subscription upstream;

        public LimitSubscriber(Subscriber<? super T> subscriber, long j2) {
            this.downstream = subscriber;
            this.remaining = j2;
            lazySet(j2);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.remaining > 0) {
                this.remaining = 0L;
                this.downstream.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.remaining <= 0) {
                RxJavaPlugins.onError(th);
            } else {
                this.remaining = 0L;
                this.downstream.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            long j2 = this.remaining;
            if (j2 > 0) {
                long j3 = j2 - 1;
                this.remaining = j3;
                this.downstream.onNext(t2);
                if (j3 == 0) {
                    this.upstream.cancel();
                    this.downstream.onComplete();
                }
            }
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                if (this.remaining == 0) {
                    subscription.cancel();
                    EmptySubscription.complete(this.downstream);
                } else {
                    this.upstream = subscription;
                    this.downstream.onSubscribe(this);
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j2) {
            long j3;
            long j4;
            if (SubscriptionHelper.validate(j2)) {
                do {
                    j3 = get();
                    if (j3 == 0) {
                        return;
                    } else {
                        j4 = j3 <= j2 ? j3 : j2;
                    }
                } while (!compareAndSet(j3, j3 - j4));
                this.upstream.request(j4);
            }
        }
    }

    public FlowableLimit(Flowable<T> flowable, long j2) {
        super(flowable);
        this.f27292n = j2;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber) new LimitSubscriber(subscriber, this.f27292n));
    }
}
