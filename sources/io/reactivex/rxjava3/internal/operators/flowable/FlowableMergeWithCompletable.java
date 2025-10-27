package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.HalfSerializer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableMergeWithCompletable<T> extends AbstractFlowableWithUpstream<T, T> {
    final CompletableSource other;

    public static final class MergeWithSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -4592979584110982903L;
        final Subscriber<? super T> downstream;
        volatile boolean mainDone;
        volatile boolean otherDone;
        final AtomicReference<Subscription> mainSubscription = new AtomicReference<>();
        final OtherObserver otherObserver = new OtherObserver(this);
        final AtomicThrowable errors = new AtomicThrowable();
        final AtomicLong requested = new AtomicLong();

        public static final class OtherObserver extends AtomicReference<Disposable> implements CompletableObserver {
            private static final long serialVersionUID = -2935427570954647017L;
            final MergeWithSubscriber<?> parent;

            public OtherObserver(MergeWithSubscriber<?> parent) {
                this.parent = parent;
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onComplete() {
                this.parent.otherComplete();
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onError(Throwable e2) {
                this.parent.otherError(e2);
            }

            @Override // io.reactivex.rxjava3.core.CompletableObserver
            public void onSubscribe(Disposable d3) {
                DisposableHelper.setOnce(this, d3);
            }
        }

        public MergeWithSubscriber(Subscriber<? super T> downstream) {
            this.downstream = downstream;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SubscriptionHelper.cancel(this.mainSubscription);
            DisposableHelper.dispose(this.otherObserver);
            this.errors.tryTerminateAndReport();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.mainDone = true;
            if (this.otherDone) {
                HalfSerializer.onComplete(this.downstream, this, this.errors);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable ex) {
            DisposableHelper.dispose(this.otherObserver);
            HalfSerializer.onError(this.downstream, ex, this, this.errors);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            HalfSerializer.onNext(this.downstream, t2, this, this.errors);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            SubscriptionHelper.deferredSetOnce(this.mainSubscription, this.requested, s2);
        }

        public void otherComplete() {
            this.otherDone = true;
            if (this.mainDone) {
                HalfSerializer.onComplete(this.downstream, this, this.errors);
            }
        }

        public void otherError(Throwable ex) {
            SubscriptionHelper.cancel(this.mainSubscription);
            HalfSerializer.onError(this.downstream, ex, this, this.errors);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            SubscriptionHelper.deferredRequest(this.mainSubscription, this.requested, n2);
        }
    }

    public FlowableMergeWithCompletable(Flowable<T> source, CompletableSource other) {
        super(source);
        this.other = other;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        MergeWithSubscriber mergeWithSubscriber = new MergeWithSubscriber(subscriber);
        subscriber.onSubscribe(mergeWithSubscriber);
        this.source.subscribe((FlowableSubscriber) mergeWithSubscriber);
        this.other.subscribe(mergeWithSubscriber.otherObserver);
    }
}
