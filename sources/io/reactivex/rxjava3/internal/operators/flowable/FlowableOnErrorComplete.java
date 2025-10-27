package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableOnErrorComplete<T> extends AbstractFlowableWithUpstream<T, T> {
    final Predicate<? super Throwable> predicate;

    public static final class OnErrorCompleteSubscriber<T> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super T> downstream;
        final Predicate<? super Throwable> predicate;
        Subscription upstream;

        public OnErrorCompleteSubscriber(Subscriber<? super T> actual, Predicate<? super Throwable> predicate) {
            this.downstream = actual;
            this.predicate = predicate;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable e2) {
            try {
                if (this.predicate.test(e2)) {
                    this.downstream.onComplete();
                } else {
                    this.downstream.onError(e2);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(new CompositeException(e2, th));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T value) {
            this.downstream.onNext(value);
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

    public FlowableOnErrorComplete(Flowable<T> source, Predicate<? super Throwable> predicate) {
        super(source);
        this.predicate = predicate;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> observer) {
        this.source.subscribe((FlowableSubscriber) new OnErrorCompleteSubscriber(observer, this.predicate));
    }
}
