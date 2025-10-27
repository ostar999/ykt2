package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.HalfSerializer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableTakeUntil<T, U> extends AbstractFlowableWithUpstream<T, T> {
    final Publisher<? extends U> other;

    public static final class TakeUntilMainSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -4945480365982832967L;
        final Subscriber<? super T> downstream;
        final AtomicLong requested = new AtomicLong();
        final AtomicReference<Subscription> upstream = new AtomicReference<>();
        final TakeUntilMainSubscriber<T>.OtherSubscriber other = new OtherSubscriber();
        final AtomicThrowable error = new AtomicThrowable();

        public final class OtherSubscriber extends AtomicReference<Subscription> implements FlowableSubscriber<Object> {
            private static final long serialVersionUID = -3592821756711087922L;

            public OtherSubscriber() {
            }

            @Override // org.reactivestreams.Subscriber
            public void onComplete() {
                SubscriptionHelper.cancel(TakeUntilMainSubscriber.this.upstream);
                TakeUntilMainSubscriber takeUntilMainSubscriber = TakeUntilMainSubscriber.this;
                HalfSerializer.onComplete(takeUntilMainSubscriber.downstream, takeUntilMainSubscriber, takeUntilMainSubscriber.error);
            }

            @Override // org.reactivestreams.Subscriber
            public void onError(Throwable t2) {
                SubscriptionHelper.cancel(TakeUntilMainSubscriber.this.upstream);
                TakeUntilMainSubscriber takeUntilMainSubscriber = TakeUntilMainSubscriber.this;
                HalfSerializer.onError(takeUntilMainSubscriber.downstream, t2, takeUntilMainSubscriber, takeUntilMainSubscriber.error);
            }

            @Override // org.reactivestreams.Subscriber
            public void onNext(Object t2) {
                SubscriptionHelper.cancel(this);
                onComplete();
            }

            @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
            public void onSubscribe(Subscription s2) {
                SubscriptionHelper.setOnce(this, s2, Long.MAX_VALUE);
            }
        }

        public TakeUntilMainSubscriber(Subscriber<? super T> downstream) {
            this.downstream = downstream;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SubscriptionHelper.cancel(this.upstream);
            SubscriptionHelper.cancel(this.other);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            SubscriptionHelper.cancel(this.other);
            HalfSerializer.onComplete(this.downstream, this, this.error);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            SubscriptionHelper.cancel(this.other);
            HalfSerializer.onError(this.downstream, t2, this, this.error);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            HalfSerializer.onNext(this.downstream, t2, this, this.error);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            SubscriptionHelper.deferredSetOnce(this.upstream, this.requested, s2);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            SubscriptionHelper.deferredRequest(this.upstream, this.requested, n2);
        }
    }

    public FlowableTakeUntil(Flowable<T> source, Publisher<? extends U> other) {
        super(source);
        this.other = other;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> child) {
        TakeUntilMainSubscriber takeUntilMainSubscriber = new TakeUntilMainSubscriber(child);
        child.onSubscribe(takeUntilMainSubscriber);
        this.other.subscribe(takeUntilMainSubscriber.other);
        this.source.subscribe((FlowableSubscriber) takeUntilMainSubscriber);
    }
}
