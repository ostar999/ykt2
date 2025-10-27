package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableElementAt<T> extends AbstractFlowableWithUpstream<T, T> {
    final T defaultValue;
    final boolean errorOnFewer;
    final long index;

    public static final class ElementAtSubscriber<T> extends DeferredScalarSubscription<T> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = 4066607327284737757L;
        long count;
        final T defaultValue;
        boolean done;
        final boolean errorOnFewer;
        final long index;
        Subscription upstream;

        public ElementAtSubscriber(Subscriber<? super T> actual, long index, T defaultValue, boolean errorOnFewer) {
            super(actual);
            this.index = index;
            this.defaultValue = defaultValue;
            this.errorOnFewer = errorOnFewer;
        }

        @Override // io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription, org.reactivestreams.Subscription
        public void cancel() {
            super.cancel();
            this.upstream.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            T t2 = this.defaultValue;
            if (t2 != null) {
                complete(t2);
            } else if (this.errorOnFewer) {
                this.downstream.onError(new NoSuchElementException());
            } else {
                this.downstream.onComplete();
            }
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
            complete(t2);
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

    public FlowableElementAt(Flowable<T> source, long index, T defaultValue, boolean errorOnFewer) {
        super(source);
        this.index = index;
        this.defaultValue = defaultValue;
        this.errorOnFewer = errorOnFewer;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        this.source.subscribe((FlowableSubscriber) new ElementAtSubscriber(s2, this.index, this.defaultValue, this.errorOnFewer));
    }
}
