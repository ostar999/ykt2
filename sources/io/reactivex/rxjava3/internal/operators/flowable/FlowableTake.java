package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableTake<T> extends AbstractFlowableWithUpstream<T, T> {

    /* renamed from: n, reason: collision with root package name */
    final long f27350n;

    public static final class TakeSubscriber<T> extends AtomicLong implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = 2288246011222124525L;
        final Subscriber<? super T> downstream;
        long remaining;
        Subscription upstream;

        public TakeSubscriber(Subscriber<? super T> actual, long remaining) {
            this.downstream = actual;
            this.remaining = remaining;
            lazySet(remaining);
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
        public void onError(Throwable t2) {
            if (this.remaining <= 0) {
                RxJavaPlugins.onError(t2);
            } else {
                this.remaining = 0L;
                this.downstream.onError(t2);
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

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                if (this.remaining == 0) {
                    s2.cancel();
                    EmptySubscription.complete(this.downstream);
                } else {
                    this.upstream = s2;
                    this.downstream.onSubscribe(this);
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            long j2;
            long jMin;
            if (SubscriptionHelper.validate(n2)) {
                do {
                    j2 = get();
                    if (j2 == 0) {
                        return;
                    } else {
                        jMin = Math.min(j2, n2);
                    }
                } while (!compareAndSet(j2, j2 - jMin));
                this.upstream.request(jMin);
            }
        }
    }

    public FlowableTake(Flowable<T> source, long n2) {
        super(source);
        this.f27350n = n2;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        this.source.subscribe((FlowableSubscriber) new TakeSubscriber(s2, this.f27350n));
    }
}
