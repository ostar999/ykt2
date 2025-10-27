package io.reactivex.rxjava3.internal.subscriptions;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class AsyncSubscription extends AtomicLong implements Subscription, Disposable {
    private static final long serialVersionUID = 7028635084060361255L;
    final AtomicReference<Subscription> actual;
    final AtomicReference<Disposable> resource;

    public AsyncSubscription() {
        this.resource = new AtomicReference<>();
        this.actual = new AtomicReference<>();
    }

    @Override // org.reactivestreams.Subscription
    public void cancel() {
        dispose();
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        SubscriptionHelper.cancel(this.actual);
        DisposableHelper.dispose(this.resource);
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return this.actual.get() == SubscriptionHelper.CANCELLED;
    }

    public boolean replaceResource(Disposable r2) {
        return DisposableHelper.replace(this.resource, r2);
    }

    @Override // org.reactivestreams.Subscription
    public void request(long n2) {
        SubscriptionHelper.deferredRequest(this.actual, this, n2);
    }

    public boolean setResource(Disposable r2) {
        return DisposableHelper.set(this.resource, r2);
    }

    public void setSubscription(Subscription s2) {
        SubscriptionHelper.deferredSetOnce(this.actual, this, s2);
    }

    public AsyncSubscription(Disposable resource) {
        this();
        this.resource.lazySet(resource);
    }
}
