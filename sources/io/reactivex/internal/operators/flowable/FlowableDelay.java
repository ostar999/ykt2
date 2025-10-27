package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.subscribers.SerializedSubscriber;
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
        final Scheduler.Worker f27288w;

        public final class OnComplete implements Runnable {
            public OnComplete() {
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    DelaySubscriber.this.downstream.onComplete();
                } finally {
                    DelaySubscriber.this.f27288w.dispose();
                }
            }
        }

        public final class OnError implements Runnable {

            /* renamed from: t, reason: collision with root package name */
            private final Throwable f27289t;

            public OnError(Throwable th) {
                this.f27289t = th;
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    DelaySubscriber.this.downstream.onError(this.f27289t);
                } finally {
                    DelaySubscriber.this.f27288w.dispose();
                }
            }
        }

        public final class OnNext implements Runnable {

            /* renamed from: t, reason: collision with root package name */
            private final T f27290t;

            public OnNext(T t2) {
                this.f27290t = t2;
            }

            @Override // java.lang.Runnable
            public void run() {
                DelaySubscriber.this.downstream.onNext(this.f27290t);
            }
        }

        public DelaySubscriber(Subscriber<? super T> subscriber, long j2, TimeUnit timeUnit, Scheduler.Worker worker, boolean z2) {
            this.downstream = subscriber;
            this.delay = j2;
            this.unit = timeUnit;
            this.f27288w = worker;
            this.delayError = z2;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
            this.f27288w.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.f27288w.schedule(new OnComplete(), this.delay, this.unit);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.f27288w.schedule(new OnError(th), this.delayError ? this.delay : 0L, this.unit);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            this.f27288w.schedule(new OnNext(t2), this.delay, this.unit);
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j2) {
            this.upstream.request(j2);
        }
    }

    public FlowableDelay(Flowable<T> flowable, long j2, TimeUnit timeUnit, Scheduler scheduler, boolean z2) {
        super(flowable);
        this.delay = j2;
        this.unit = timeUnit;
        this.scheduler = scheduler;
        this.delayError = z2;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber) new DelaySubscriber(this.delayError ? subscriber : new SerializedSubscriber(subscriber), this.delay, this.unit, this.scheduler.createWorker(), this.delayError));
    }
}
