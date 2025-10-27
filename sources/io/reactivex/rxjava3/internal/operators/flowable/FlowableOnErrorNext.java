package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionArbiter;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableOnErrorNext<T> extends AbstractFlowableWithUpstream<T, T> {
    final Function<? super Throwable, ? extends Publisher<? extends T>> nextSupplier;

    public static final class OnErrorNextSubscriber<T> extends SubscriptionArbiter implements FlowableSubscriber<T> {
        private static final long serialVersionUID = 4063763155303814625L;
        boolean done;
        final Subscriber<? super T> downstream;
        final Function<? super Throwable, ? extends Publisher<? extends T>> nextSupplier;
        boolean once;
        long produced;

        public OnErrorNextSubscriber(Subscriber<? super T> actual, Function<? super Throwable, ? extends Publisher<? extends T>> nextSupplier) {
            super(false);
            this.downstream = actual;
            this.nextSupplier = nextSupplier;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.once = true;
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            if (this.once) {
                if (this.done) {
                    RxJavaPlugins.onError(t2);
                    return;
                } else {
                    this.downstream.onError(t2);
                    return;
                }
            }
            this.once = true;
            try {
                Publisher<? extends T> publisherApply = this.nextSupplier.apply(t2);
                Objects.requireNonNull(publisherApply, "The nextSupplier returned a null Publisher");
                Publisher<? extends T> publisher = publisherApply;
                long j2 = this.produced;
                if (j2 != 0) {
                    produced(j2);
                }
                publisher.subscribe(this);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(new CompositeException(t2, th));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            if (!this.once) {
                this.produced++;
            }
            this.downstream.onNext(t2);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            setSubscription(s2);
        }
    }

    public FlowableOnErrorNext(Flowable<T> source, Function<? super Throwable, ? extends Publisher<? extends T>> nextSupplier) {
        super(source);
        this.nextSupplier = nextSupplier;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        OnErrorNextSubscriber onErrorNextSubscriber = new OnErrorNextSubscriber(s2, this.nextSupplier);
        s2.onSubscribe(onErrorNextSubscriber);
        this.source.subscribe((FlowableSubscriber) onErrorNextSubscriber);
    }
}
