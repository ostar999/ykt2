package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiConsumer;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableCollect<T, U> extends AbstractFlowableWithUpstream<T, U> {
    final BiConsumer<? super U, ? super T> collector;
    final Callable<? extends U> initialSupplier;

    public static final class CollectSubscriber<T, U> extends DeferredScalarSubscription<U> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -3589550218733891694L;
        final BiConsumer<? super U, ? super T> collector;
        boolean done;

        /* renamed from: u, reason: collision with root package name */
        final U f27286u;
        Subscription upstream;

        public CollectSubscriber(Subscriber<? super U> subscriber, U u2, BiConsumer<? super U, ? super T> biConsumer) {
            super(subscriber);
            this.collector = biConsumer;
            this.f27286u = u2;
        }

        @Override // io.reactivex.internal.subscriptions.DeferredScalarSubscription, org.reactivestreams.Subscription
        public void cancel() {
            super.cancel();
            this.upstream.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            complete(this.f27286u);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
            } else {
                this.done = true;
                this.downstream.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            try {
                this.collector.accept(this.f27286u, t2);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.cancel();
                onError(th);
            }
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }
    }

    public FlowableCollect(Flowable<T> flowable, Callable<? extends U> callable, BiConsumer<? super U, ? super T> biConsumer) {
        super(flowable);
        this.initialSupplier = callable;
        this.collector = biConsumer;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super U> subscriber) {
        try {
            this.source.subscribe((FlowableSubscriber) new CollectSubscriber(subscriber, ObjectHelper.requireNonNull(this.initialSupplier.call(), "The initial value supplied is null"), this.collector));
        } catch (Throwable th) {
            EmptySubscription.error(th, subscriber);
        }
    }
}
