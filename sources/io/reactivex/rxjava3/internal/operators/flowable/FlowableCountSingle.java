package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.fuseable.FuseToFlowable;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableCountSingle<T> extends Single<Long> implements FuseToFlowable<Long> {
    final Flowable<T> source;

    public static final class CountSubscriber implements FlowableSubscriber<Object>, Disposable {
        long count;
        final SingleObserver<? super Long> downstream;
        Subscription upstream;

        public CountSubscriber(SingleObserver<? super Long> downstream) {
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
            this.downstream.onSuccess(Long.valueOf(this.count));
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            this.upstream = SubscriptionHelper.CANCELLED;
            this.downstream.onError(t2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object t2) {
            this.count++;
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

    public FlowableCountSingle(Flowable<T> source) {
        this.source = source;
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.FuseToFlowable
    public Flowable<Long> fuseToFlowable() {
        return RxJavaPlugins.onAssembly(new FlowableCount(this.source));
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(SingleObserver<? super Long> observer) {
        this.source.subscribe((FlowableSubscriber) new CountSubscriber(observer));
    }
}
