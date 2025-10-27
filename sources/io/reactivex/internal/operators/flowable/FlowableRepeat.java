package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableRepeat<T> extends AbstractFlowableWithUpstream<T, T> {
    final long count;

    public static final class RepeatSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -7098360935104053232L;
        final Subscriber<? super T> downstream;
        long produced;
        long remaining;
        final SubscriptionArbiter sa;
        final Publisher<? extends T> source;

        public RepeatSubscriber(Subscriber<? super T> subscriber, long j2, SubscriptionArbiter subscriptionArbiter, Publisher<? extends T> publisher) {
            this.downstream = subscriber;
            this.sa = subscriptionArbiter;
            this.source = publisher;
            this.remaining = j2;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            long j2 = this.remaining;
            if (j2 != Long.MAX_VALUE) {
                this.remaining = j2 - 1;
            }
            if (j2 != 0) {
                subscribeNext();
            } else {
                this.downstream.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            this.produced++;
            this.downstream.onNext(t2);
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            this.sa.setSubscription(subscription);
        }

        public void subscribeNext() {
            if (getAndIncrement() == 0) {
                int iAddAndGet = 1;
                while (!this.sa.isCancelled()) {
                    long j2 = this.produced;
                    if (j2 != 0) {
                        this.produced = 0L;
                        this.sa.produced(j2);
                    }
                    this.source.subscribe(this);
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                }
            }
        }
    }

    public FlowableRepeat(Flowable<T> flowable, long j2) {
        super(flowable);
        this.count = j2;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        SubscriptionArbiter subscriptionArbiter = new SubscriptionArbiter(false);
        subscriber.onSubscribe(subscriptionArbiter);
        long j2 = this.count;
        new RepeatSubscriber(subscriber, j2 != Long.MAX_VALUE ? j2 - 1 : Long.MAX_VALUE, subscriptionArbiter, this.source).subscribeNext();
    }
}
