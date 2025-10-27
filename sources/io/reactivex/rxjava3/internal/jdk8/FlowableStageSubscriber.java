package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
abstract class FlowableStageSubscriber<T> extends CompletableFuture<T> implements FlowableSubscriber<T> {
    final AtomicReference<Subscription> upstream = new AtomicReference<>();
    T value;

    public abstract void afterSubscribe(Subscription s2);

    @Override // java.util.concurrent.CompletableFuture, java.util.concurrent.Future
    public final boolean cancel(boolean mayInterruptIfRunning) {
        cancelUpstream();
        return super.cancel(mayInterruptIfRunning);
    }

    public final void cancelUpstream() {
        SubscriptionHelper.cancel(this.upstream);
    }

    public final void clear() {
        this.value = null;
        this.upstream.lazySet(SubscriptionHelper.CANCELLED);
    }

    @Override // java.util.concurrent.CompletableFuture
    public final boolean complete(T value) {
        cancelUpstream();
        return super.complete(value);
    }

    @Override // java.util.concurrent.CompletableFuture
    public final boolean completeExceptionally(Throwable ex) {
        cancelUpstream();
        return super.completeExceptionally(ex);
    }

    @Override // org.reactivestreams.Subscriber
    public final void onError(Throwable t2) {
        clear();
        if (completeExceptionally(t2)) {
            return;
        }
        RxJavaPlugins.onError(t2);
    }

    @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
    public final void onSubscribe(@NonNull Subscription s2) {
        if (SubscriptionHelper.setOnce(this.upstream, s2)) {
            afterSubscribe(s2);
        }
    }
}
