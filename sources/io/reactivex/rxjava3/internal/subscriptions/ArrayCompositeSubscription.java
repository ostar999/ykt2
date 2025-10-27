package io.reactivex.rxjava3.internal.subscriptions;

import io.reactivex.rxjava3.disposables.Disposable;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class ArrayCompositeSubscription extends AtomicReferenceArray<Subscription> implements Disposable {
    private static final long serialVersionUID = 2746389416410565408L;

    public ArrayCompositeSubscription(int capacity) {
        super(capacity);
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        Subscription andSet;
        if (get(0) != SubscriptionHelper.CANCELLED) {
            int length = length();
            for (int i2 = 0; i2 < length; i2++) {
                Subscription subscription = get(i2);
                SubscriptionHelper subscriptionHelper = SubscriptionHelper.CANCELLED;
                if (subscription != subscriptionHelper && (andSet = getAndSet(i2, subscriptionHelper)) != subscriptionHelper && andSet != null) {
                    andSet.cancel();
                }
            }
        }
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return get(0) == SubscriptionHelper.CANCELLED;
    }

    public Subscription replaceResource(int index, Subscription resource) {
        Subscription subscription;
        do {
            subscription = get(index);
            if (subscription == SubscriptionHelper.CANCELLED) {
                if (resource == null) {
                    return null;
                }
                resource.cancel();
                return null;
            }
        } while (!compareAndSet(index, subscription, resource));
        return subscription;
    }

    public boolean setResource(int index, Subscription resource) {
        Subscription subscription;
        do {
            subscription = get(index);
            if (subscription == SubscriptionHelper.CANCELLED) {
                if (resource == null) {
                    return false;
                }
                resource.cancel();
                return false;
            }
        } while (!compareAndSet(index, subscription, resource));
        if (subscription == null) {
            return true;
        }
        subscription.cancel();
        return true;
    }
}
