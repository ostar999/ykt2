package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableUnsubscribeOn<T> extends AbstractFlowableWithUpstream<T, T> {
    final Scheduler scheduler;

    public static final class UnsubscribeSubscriber<T> extends AtomicBoolean implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = 1015244841293359600L;
        final Subscriber<? super T> downstream;
        final Scheduler scheduler;
        Subscription upstream;

        public final class Cancellation implements Runnable {
            public Cancellation() {
            }

            @Override // java.lang.Runnable
            public void run() {
                UnsubscribeSubscriber.this.upstream.cancel();
            }
        }

        public UnsubscribeSubscriber(Subscriber<? super T> actual, Scheduler scheduler) {
            this.downstream = actual;
            this.scheduler = scheduler;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (compareAndSet(false, true)) {
                this.scheduler.scheduleDirect(new Cancellation());
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (get()) {
                return;
            }
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            if (get()) {
                RxJavaPlugins.onError(t2);
            } else {
                this.downstream.onError(t2);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (get()) {
                return;
            }
            this.downstream.onNext(t2);
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

    public FlowableUnsubscribeOn(Flowable<T> source, Scheduler scheduler) {
        super(source);
        this.scheduler = scheduler;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        this.source.subscribe((FlowableSubscriber) new UnsubscribeSubscriber(s2, this.scheduler));
    }
}
