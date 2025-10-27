package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collector;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableCollectWithCollector<T, A, R> extends Flowable<R> {
    final Collector<? super T, A, R> collector;
    final Flowable<T> source;

    public static final class CollectorSubscriber<T, A, R> extends DeferredScalarSubscription<R> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -229544830565448758L;
        final BiConsumer<A, T> accumulator;
        A container;
        boolean done;
        final Function<A, R> finisher;
        Subscription upstream;

        public CollectorSubscriber(Subscriber<? super R> downstream, A container, BiConsumer<A, T> accumulator, Function<A, R> finisher) {
            super(downstream);
            this.container = container;
            this.accumulator = accumulator;
            this.finisher = finisher;
        }

        @Override // io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription, org.reactivestreams.Subscription
        public void cancel() {
            super.cancel();
            this.upstream.cancel();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.upstream = SubscriptionHelper.CANCELLED;
            A a3 = this.container;
            this.container = null;
            try {
                Object objApply = this.finisher.apply(a3);
                Objects.requireNonNull(objApply, "The finisher returned a null value");
                complete(objApply);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            if (this.done) {
                RxJavaPlugins.onError(t2);
                return;
            }
            this.done = true;
            this.upstream = SubscriptionHelper.CANCELLED;
            this.container = null;
            this.downstream.onError(t2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            try {
                this.accumulator.accept(this.container, t2);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.cancel();
                onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(@NonNull Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                this.downstream.onSubscribe(this);
                s2.request(Long.MAX_VALUE);
            }
        }
    }

    public FlowableCollectWithCollector(Flowable<T> source, Collector<? super T, A, R> collector) {
        this.source = source;
        this.collector = collector;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(@NonNull Subscriber<? super R> s2) {
        try {
            this.source.subscribe((FlowableSubscriber) new CollectorSubscriber(s2, this.collector.supplier().get(), this.collector.accumulator(), this.collector.finisher()));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptySubscription.error(th, s2);
        }
    }
}
