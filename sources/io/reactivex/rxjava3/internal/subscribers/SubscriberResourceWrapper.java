package io.reactivex.rxjava3.internal.subscribers;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class SubscriberResourceWrapper<T> extends AtomicReference<Disposable> implements FlowableSubscriber<T>, Disposable, Subscription {
    private static final long serialVersionUID = -8612022020200669122L;
    final Subscriber<? super T> downstream;
    final AtomicReference<Subscription> upstream = new AtomicReference<>();

    public SubscriberResourceWrapper(Subscriber<? super T> downstream) {
        this.downstream = downstream;
    }

    @Override // org.reactivestreams.Subscription
    public void cancel() {
        dispose();
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        SubscriptionHelper.cancel(this.upstream);
        DisposableHelper.dispose(this);
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return this.upstream.get() == SubscriptionHelper.CANCELLED;
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        DisposableHelper.dispose(this);
        this.downstream.onComplete();
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable t2) {
        DisposableHelper.dispose(this);
        this.downstream.onError(t2);
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t2) {
        this.downstream.onNext(t2);
    }

    @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(Subscription s2) {
        if (SubscriptionHelper.setOnce(this.upstream, s2)) {
            this.downstream.onSubscribe(this);
        }
    }

    @Override // org.reactivestreams.Subscription
    public void request(long n2) {
        if (SubscriptionHelper.validate(n2)) {
            this.upstream.get().request(n2);
        }
    }

    public void setResource(Disposable resource) {
        DisposableHelper.set(this, resource);
    }
}
