package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableScan<T> extends AbstractFlowableWithUpstream<T, T> {
    final BiFunction<T, T, T> accumulator;

    public static final class ScanSubscriber<T> implements FlowableSubscriber<T>, Subscription {
        final BiFunction<T, T, T> accumulator;
        boolean done;
        final Subscriber<? super T> downstream;
        Subscription upstream;
        T value;

        public ScanSubscriber(Subscriber<? super T> actual, BiFunction<T, T, T> accumulator) {
            this.downstream = actual;
            this.accumulator = accumulator;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            if (this.done) {
                RxJavaPlugins.onError(t2);
            } else {
                this.done = true;
                this.downstream.onError(t2);
            }
        }

        /* JADX WARN: Type inference failed for: r4v2, types: [T, java.lang.Object] */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            Subscriber<? super T> subscriber = this.downstream;
            T t3 = this.value;
            if (t3 == null) {
                this.value = t2;
                subscriber.onNext(t2);
                return;
            }
            try {
                T tApply = this.accumulator.apply(t3, t2);
                Objects.requireNonNull(tApply, "The value returned by the accumulator is null");
                this.value = tApply;
                subscriber.onNext(tApply);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.cancel();
                onError(th);
            }
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

    public FlowableScan(Flowable<T> source, BiFunction<T, T, T> accumulator) {
        super(source);
        this.accumulator = accumulator;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        this.source.subscribe((FlowableSubscriber) new ScanSubscriber(s2, this.accumulator));
    }
}
