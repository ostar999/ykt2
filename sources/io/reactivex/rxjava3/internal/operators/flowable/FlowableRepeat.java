package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionArbiter;
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

        public RepeatSubscriber(Subscriber<? super T> actual, long count, SubscriptionArbiter sa, Publisher<? extends T> source) {
            this.downstream = actual;
            this.sa = sa;
            this.source = source;
            this.remaining = count;
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
        public void onError(Throwable t2) {
            this.downstream.onError(t2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            this.produced++;
            this.downstream.onNext(t2);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            this.sa.setSubscription(s2);
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

    public FlowableRepeat(Flowable<T> source, long count) {
        super(source);
        this.count = count;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        SubscriptionArbiter subscriptionArbiter = new SubscriptionArbiter(false);
        s2.onSubscribe(subscriptionArbiter);
        long j2 = this.count;
        new RepeatSubscriber(s2, j2 != Long.MAX_VALUE ? j2 - 1 : Long.MAX_VALUE, subscriptionArbiter, this.source).subscribeNext();
    }
}
