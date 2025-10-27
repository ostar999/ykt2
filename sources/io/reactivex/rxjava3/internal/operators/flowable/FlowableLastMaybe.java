package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableLastMaybe<T> extends Maybe<T> {
    final Publisher<T> source;

    public static final class LastSubscriber<T> implements FlowableSubscriber<T>, Disposable {
        final MaybeObserver<? super T> downstream;
        T item;
        Subscription upstream;

        public LastSubscriber(MaybeObserver<? super T> downstream) {
            this.downstream = downstream;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.upstream.cancel();
            this.upstream = SubscriptionHelper.CANCELLED;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream == SubscriptionHelper.CANCELLED;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.upstream = SubscriptionHelper.CANCELLED;
            T t2 = this.item;
            if (t2 == null) {
                this.downstream.onComplete();
            } else {
                this.item = null;
                this.downstream.onSuccess(t2);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            this.upstream = SubscriptionHelper.CANCELLED;
            this.item = null;
            this.downstream.onError(t2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            this.item = t2;
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                this.downstream.onSubscribe(this);
                s2.request(Long.MAX_VALUE);
            }
        }
    }

    public FlowableLastMaybe(Publisher<T> source) {
        this.source = source;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    public void subscribeActual(MaybeObserver<? super T> observer) {
        this.source.subscribe(new LastSubscriber(observer));
    }
}
