package io.reactivex.rxjava3.internal.subscriptions;

import androidx.camera.view.j;
import io.reactivex.rxjava3.exceptions.ProtocolViolationException;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public enum SubscriptionHelper implements Subscription {
    CANCELLED;

    public static boolean cancel(AtomicReference<Subscription> field) {
        Subscription andSet;
        Subscription subscription = field.get();
        SubscriptionHelper subscriptionHelper = CANCELLED;
        if (subscription == subscriptionHelper || (andSet = field.getAndSet(subscriptionHelper)) == subscriptionHelper) {
            return false;
        }
        if (andSet == null) {
            return true;
        }
        andSet.cancel();
        return true;
    }

    public static void deferredRequest(AtomicReference<Subscription> field, AtomicLong requested, long n2) {
        Subscription subscription = field.get();
        if (subscription != null) {
            subscription.request(n2);
            return;
        }
        if (validate(n2)) {
            BackpressureHelper.add(requested, n2);
            Subscription subscription2 = field.get();
            if (subscription2 != null) {
                long andSet = requested.getAndSet(0L);
                if (andSet != 0) {
                    subscription2.request(andSet);
                }
            }
        }
    }

    public static boolean deferredSetOnce(AtomicReference<Subscription> field, AtomicLong requested, Subscription s2) {
        if (!setOnce(field, s2)) {
            return false;
        }
        long andSet = requested.getAndSet(0L);
        if (andSet == 0) {
            return true;
        }
        s2.request(andSet);
        return true;
    }

    public static boolean replace(AtomicReference<Subscription> field, Subscription s2) {
        Subscription subscription;
        do {
            subscription = field.get();
            if (subscription == CANCELLED) {
                if (s2 == null) {
                    return false;
                }
                s2.cancel();
                return false;
            }
        } while (!j.a(field, subscription, s2));
        return true;
    }

    public static void reportMoreProduced(long n2) {
        RxJavaPlugins.onError(new ProtocolViolationException("More produced than requested: " + n2));
    }

    public static void reportSubscriptionSet() {
        RxJavaPlugins.onError(new ProtocolViolationException("Subscription already set!"));
    }

    public static boolean set(AtomicReference<Subscription> field, Subscription s2) {
        Subscription subscription;
        do {
            subscription = field.get();
            if (subscription == CANCELLED) {
                if (s2 == null) {
                    return false;
                }
                s2.cancel();
                return false;
            }
        } while (!j.a(field, subscription, s2));
        if (subscription == null) {
            return true;
        }
        subscription.cancel();
        return true;
    }

    public static boolean setOnce(AtomicReference<Subscription> field, Subscription s2) {
        Objects.requireNonNull(s2, "s is null");
        if (j.a(field, null, s2)) {
            return true;
        }
        s2.cancel();
        if (field.get() == CANCELLED) {
            return false;
        }
        reportSubscriptionSet();
        return false;
    }

    public static boolean validate(Subscription current, Subscription next) {
        if (next == null) {
            RxJavaPlugins.onError(new NullPointerException("next is null"));
            return false;
        }
        if (current == null) {
            return true;
        }
        next.cancel();
        reportSubscriptionSet();
        return false;
    }

    @Override // org.reactivestreams.Subscription
    public void cancel() {
    }

    @Override // org.reactivestreams.Subscription
    public void request(long n2) {
    }

    public static boolean validate(long n2) {
        if (n2 > 0) {
            return true;
        }
        RxJavaPlugins.onError(new IllegalArgumentException("n > 0 required but it was " + n2));
        return false;
    }

    public static boolean setOnce(AtomicReference<Subscription> field, Subscription s2, long request) {
        if (!setOnce(field, s2)) {
            return false;
        }
        s2.request(request);
        return true;
    }
}
