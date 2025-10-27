package io.reactivex.rxjava3.internal.subscribers;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.disposables.DisposableContainer;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.functions.Functions;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.observers.LambdaConsumerIntrospection;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class DisposableAutoReleaseSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Disposable, LambdaConsumerIntrospection {
    private static final long serialVersionUID = 8924480688481408726L;
    final AtomicReference<DisposableContainer> composite;
    final Action onComplete;
    final Consumer<? super Throwable> onError;
    final Consumer<? super T> onNext;

    public DisposableAutoReleaseSubscriber(DisposableContainer composite, Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete) {
        this.onNext = onNext;
        this.onError = onError;
        this.onComplete = onComplete;
        this.composite = new AtomicReference<>(composite);
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        SubscriptionHelper.cancel(this);
        removeSelf();
    }

    @Override // io.reactivex.rxjava3.observers.LambdaConsumerIntrospection
    public boolean hasCustomOnError() {
        return this.onError != Functions.ON_ERROR_MISSING;
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return SubscriptionHelper.CANCELLED == get();
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        Subscription subscription = get();
        SubscriptionHelper subscriptionHelper = SubscriptionHelper.CANCELLED;
        if (subscription != subscriptionHelper) {
            lazySet(subscriptionHelper);
            try {
                this.onComplete.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        }
        removeSelf();
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable t2) {
        Subscription subscription = get();
        SubscriptionHelper subscriptionHelper = SubscriptionHelper.CANCELLED;
        if (subscription != subscriptionHelper) {
            lazySet(subscriptionHelper);
            try {
                this.onError.accept(t2);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(new CompositeException(t2, th));
            }
        } else {
            RxJavaPlugins.onError(t2);
        }
        removeSelf();
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t2) {
        if (get() != SubscriptionHelper.CANCELLED) {
            try {
                this.onNext.accept(t2);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                get().cancel();
                onError(th);
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(Subscription s2) {
        if (SubscriptionHelper.setOnce(this, s2)) {
            s2.request(Long.MAX_VALUE);
        }
    }

    public void removeSelf() {
        DisposableContainer andSet = this.composite.getAndSet(null);
        if (andSet != null) {
            andSet.delete(this);
        }
    }
}
