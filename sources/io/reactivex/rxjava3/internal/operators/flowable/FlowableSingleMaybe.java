package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.fuseable.FuseToFlowable;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableSingleMaybe<T> extends Maybe<T> implements FuseToFlowable<T> {
    final Flowable<T> source;

    public static final class SingleElementSubscriber<T> implements FlowableSubscriber<T>, Disposable {
        boolean done;
        final MaybeObserver<? super T> downstream;
        Subscription upstream;
        T value;

        public SingleElementSubscriber(MaybeObserver<? super T> downstream) {
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
            if (this.done) {
                return;
            }
            this.done = true;
            this.upstream = SubscriptionHelper.CANCELLED;
            T t2 = this.value;
            this.value = null;
            if (t2 == null) {
                this.downstream.onComplete();
            } else {
                this.downstream.onSuccess(t2);
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
            this.downstream.onError(t2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            if (this.value == null) {
                this.value = t2;
                return;
            }
            this.done = true;
            this.upstream.cancel();
            this.upstream = SubscriptionHelper.CANCELLED;
            this.downstream.onError(new IllegalArgumentException("Sequence contains more than one element!"));
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

    public FlowableSingleMaybe(Flowable<T> source) {
        this.source = source;
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.FuseToFlowable
    public Flowable<T> fuseToFlowable() {
        return RxJavaPlugins.onAssembly(new FlowableSingle(this.source, null, false));
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    public void subscribeActual(MaybeObserver<? super T> observer) {
        this.source.subscribe((FlowableSubscriber) new SingleElementSubscriber(observer));
    }
}
