package io.reactivex.rxjava3.subscribers;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.EndConsumerHelper;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public abstract class DisposableSubscriber<T> implements FlowableSubscriber<T>, Disposable {
    final AtomicReference<Subscription> upstream = new AtomicReference<>();

    public final void cancel() {
        dispose();
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public final void dispose() {
        SubscriptionHelper.cancel(this.upstream);
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public final boolean isDisposed() {
        return this.upstream.get() == SubscriptionHelper.CANCELLED;
    }

    public void onStart() {
        this.upstream.get().request(Long.MAX_VALUE);
    }

    @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
    public final void onSubscribe(Subscription s2) {
        if (EndConsumerHelper.setOnce(this.upstream, s2, getClass())) {
            onStart();
        }
    }

    public final void request(long n2) {
        this.upstream.get().request(n2);
    }
}
