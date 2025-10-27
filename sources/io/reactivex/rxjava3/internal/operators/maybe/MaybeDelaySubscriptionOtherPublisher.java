package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class MaybeDelaySubscriptionOtherPublisher<T, U> extends AbstractMaybeWithUpstream<T, T> {
    final Publisher<U> other;

    public static final class DelayMaybeObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T> {
        private static final long serialVersionUID = 706635022205076709L;
        final MaybeObserver<? super T> downstream;

        public DelayMaybeObserver(MaybeObserver<? super T> downstream) {
            this.downstream = downstream;
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            this.downstream.onError(e2);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            DisposableHelper.setOnce(this, d3);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T value) {
            this.downstream.onSuccess(value);
        }
    }

    public static final class OtherSubscriber<T> implements FlowableSubscriber<Object>, Disposable {
        final DelayMaybeObserver<T> main;
        MaybeSource<T> source;
        Subscription upstream;

        public OtherSubscriber(MaybeObserver<? super T> actual, MaybeSource<T> source) {
            this.main = new DelayMaybeObserver<>(actual);
            this.source = source;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.upstream.cancel();
            this.upstream = SubscriptionHelper.CANCELLED;
            DisposableHelper.dispose(this.main);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.main.get());
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            Subscription subscription = this.upstream;
            SubscriptionHelper subscriptionHelper = SubscriptionHelper.CANCELLED;
            if (subscription != subscriptionHelper) {
                this.upstream = subscriptionHelper;
                subscribeNext();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            Subscription subscription = this.upstream;
            SubscriptionHelper subscriptionHelper = SubscriptionHelper.CANCELLED;
            if (subscription == subscriptionHelper) {
                RxJavaPlugins.onError(t2);
            } else {
                this.upstream = subscriptionHelper;
                this.main.downstream.onError(t2);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object t2) {
            Subscription subscription = this.upstream;
            SubscriptionHelper subscriptionHelper = SubscriptionHelper.CANCELLED;
            if (subscription != subscriptionHelper) {
                subscription.cancel();
                this.upstream = subscriptionHelper;
                subscribeNext();
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                this.main.downstream.onSubscribe(this);
                s2.request(Long.MAX_VALUE);
            }
        }

        public void subscribeNext() {
            MaybeSource<T> maybeSource = this.source;
            this.source = null;
            maybeSource.subscribe(this.main);
        }
    }

    public MaybeDelaySubscriptionOtherPublisher(MaybeSource<T> source, Publisher<U> other) {
        super(source);
        this.other = other;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    public void subscribeActual(MaybeObserver<? super T> observer) {
        this.other.subscribe(new OtherSubscriber(observer, this.source));
    }
}
