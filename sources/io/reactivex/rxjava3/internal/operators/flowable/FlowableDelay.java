package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.subscribers.SerializedSubscriber;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableDelay<T> extends AbstractFlowableWithUpstream<T, T> {
    final long delay;
    final boolean delayError;
    final Scheduler scheduler;
    final TimeUnit unit;

    public static final class DelaySubscriber<T> implements FlowableSubscriber<T>, Subscription {
        final long delay;
        final boolean delayError;
        final Subscriber<? super T> downstream;
        final TimeUnit unit;
        Subscription upstream;

        /* renamed from: w, reason: collision with root package name */
        final Scheduler.Worker f27341w;

        public final class OnComplete implements Runnable {
            public OnComplete() {
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    DelaySubscriber.this.downstream.onComplete();
                } finally {
                    DelaySubscriber.this.f27341w.dispose();
                }
            }
        }

        public final class OnError implements Runnable {

            /* renamed from: t, reason: collision with root package name */
            private final Throwable f27342t;

            public OnError(Throwable t2) {
                this.f27342t = t2;
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    DelaySubscriber.this.downstream.onError(this.f27342t);
                } finally {
                    DelaySubscriber.this.f27341w.dispose();
                }
            }
        }

        public final class OnNext implements Runnable {

            /* renamed from: t, reason: collision with root package name */
            private final T f27343t;

            public OnNext(T t2) {
                this.f27343t = t2;
            }

            @Override // java.lang.Runnable
            public void run() {
                DelaySubscriber.this.downstream.onNext(this.f27343t);
            }
        }

        public DelaySubscriber(Subscriber<? super T> actual, long delay, TimeUnit unit, Scheduler.Worker w2, boolean delayError) {
            this.downstream = actual;
            this.delay = delay;
            this.unit = unit;
            this.f27341w = w2;
            this.delayError = delayError;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
            this.f27341w.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.f27341w.schedule(new OnComplete(), this.delay, this.unit);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(final Throwable t2) {
            this.f27341w.schedule(new OnError(t2), this.delayError ? this.delay : 0L, this.unit);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(final T t2) {
            this.f27341w.schedule(new OnNext(t2), this.delay, this.unit);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            this.upstream.request(n2);
        }
    }

    public FlowableDelay(Flowable<T> source, long delay, TimeUnit unit, Scheduler scheduler, boolean delayError) {
        super(source);
        this.delay = delay;
        this.unit = unit;
        this.scheduler = scheduler;
        this.delayError = delayError;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> t2) {
        this.source.subscribe((FlowableSubscriber) new DelaySubscriber(this.delayError ? t2 : new SerializedSubscriber(t2), this.delay, this.unit, this.scheduler.createWorker(), this.delayError));
    }
}
