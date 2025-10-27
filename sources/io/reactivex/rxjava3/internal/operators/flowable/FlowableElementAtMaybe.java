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
public final class FlowableElementAtMaybe<T> extends Maybe<T> implements FuseToFlowable<T> {
    final long index;
    final Flowable<T> source;

    public static final class ElementAtSubscriber<T> implements FlowableSubscriber<T>, Disposable {
        long count;
        boolean done;
        final MaybeObserver<? super T> downstream;
        final long index;
        Subscription upstream;

        public ElementAtSubscriber(MaybeObserver<? super T> actual, long index) {
            this.downstream = actual;
            this.index = index;
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
            long j2 = this.count;
            if (j2 != this.index) {
                this.count = j2 + 1;
                return;
            }
            this.done = true;
            this.upstream.cancel();
            this.upstream = SubscriptionHelper.CANCELLED;
            this.downstream.onSuccess(t2);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                this.downstream.onSubscribe(this);
                s2.request(this.index + 1);
            }
        }
    }

    public FlowableElementAtMaybe(Flowable<T> source, long index) {
        this.source = source;
        this.index = index;
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.FuseToFlowable
    public Flowable<T> fuseToFlowable() {
        return RxJavaPlugins.onAssembly(new FlowableElementAt(this.source, this.index, null, false));
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    public void subscribeActual(MaybeObserver<? super T> observer) {
        this.source.subscribe((FlowableSubscriber) new ElementAtSubscriber(observer, this.index));
    }
}
