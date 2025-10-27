package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BooleanSupplier;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionArbiter;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableRepeatUntil<T> extends AbstractFlowableWithUpstream<T, T> {
    final BooleanSupplier until;

    public static final class RepeatSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -7098360935104053232L;
        final Subscriber<? super T> downstream;
        long produced;
        final SubscriptionArbiter sa;
        final Publisher<? extends T> source;
        final BooleanSupplier stop;

        public RepeatSubscriber(Subscriber<? super T> actual, BooleanSupplier until, SubscriptionArbiter sa, Publisher<? extends T> source) {
            this.downstream = actual;
            this.sa = sa;
            this.source = source;
            this.stop = until;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            try {
                if (this.stop.getAsBoolean()) {
                    this.downstream.onComplete();
                } else {
                    subscribeNext();
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
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

    public FlowableRepeatUntil(Flowable<T> source, BooleanSupplier until) {
        super(source);
        this.until = until;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        SubscriptionArbiter subscriptionArbiter = new SubscriptionArbiter(false);
        s2.onSubscribe(subscriptionArbiter);
        new RepeatSubscriber(s2, this.until, subscriptionArbiter, this.source).subscribeNext();
    }
}
