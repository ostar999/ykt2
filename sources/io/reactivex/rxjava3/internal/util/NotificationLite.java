package io.reactivex.rxjava3.internal.util;

import cn.hutool.core.text.StrPool;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import java.io.Serializable;
import java.util.Objects;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public enum NotificationLite {
    COMPLETE;

    public static final class DisposableNotification implements Serializable {
        private static final long serialVersionUID = -7482590109178395495L;
        final Disposable upstream;

        public DisposableNotification(Disposable d3) {
            this.upstream = d3;
        }

        public String toString() {
            return "NotificationLite.Disposable[" + this.upstream + StrPool.BRACKET_END;
        }
    }

    public static final class ErrorNotification implements Serializable {
        private static final long serialVersionUID = -8759979445933046293L;

        /* renamed from: e, reason: collision with root package name */
        final Throwable f27370e;

        public ErrorNotification(Throwable e2) {
            this.f27370e = e2;
        }

        public boolean equals(Object obj) {
            if (obj instanceof ErrorNotification) {
                return Objects.equals(this.f27370e, ((ErrorNotification) obj).f27370e);
            }
            return false;
        }

        public int hashCode() {
            return this.f27370e.hashCode();
        }

        public String toString() {
            return "NotificationLite.Error[" + this.f27370e + StrPool.BRACKET_END;
        }
    }

    public static final class SubscriptionNotification implements Serializable {
        private static final long serialVersionUID = -1322257508628817540L;
        final Subscription upstream;

        public SubscriptionNotification(Subscription s2) {
            this.upstream = s2;
        }

        public String toString() {
            return "NotificationLite.Subscription[" + this.upstream + StrPool.BRACKET_END;
        }
    }

    public static <T> boolean accept(Object o2, Subscriber<? super T> s2) {
        if (o2 == COMPLETE) {
            s2.onComplete();
            return true;
        }
        if (o2 instanceof ErrorNotification) {
            s2.onError(((ErrorNotification) o2).f27370e);
            return true;
        }
        s2.onNext(o2);
        return false;
    }

    public static <T> boolean acceptFull(Object o2, Subscriber<? super T> s2) {
        if (o2 == COMPLETE) {
            s2.onComplete();
            return true;
        }
        if (o2 instanceof ErrorNotification) {
            s2.onError(((ErrorNotification) o2).f27370e);
            return true;
        }
        if (o2 instanceof SubscriptionNotification) {
            s2.onSubscribe(((SubscriptionNotification) o2).upstream);
            return false;
        }
        s2.onNext(o2);
        return false;
    }

    public static Object complete() {
        return COMPLETE;
    }

    public static Object disposable(Disposable d3) {
        return new DisposableNotification(d3);
    }

    public static Object error(Throwable e2) {
        return new ErrorNotification(e2);
    }

    public static Disposable getDisposable(Object o2) {
        return ((DisposableNotification) o2).upstream;
    }

    public static Throwable getError(Object o2) {
        return ((ErrorNotification) o2).f27370e;
    }

    public static Subscription getSubscription(Object o2) {
        return ((SubscriptionNotification) o2).upstream;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T getValue(Object o2) {
        return o2;
    }

    public static boolean isComplete(Object o2) {
        return o2 == COMPLETE;
    }

    public static boolean isDisposable(Object o2) {
        return o2 instanceof DisposableNotification;
    }

    public static boolean isError(Object o2) {
        return o2 instanceof ErrorNotification;
    }

    public static boolean isSubscription(Object o2) {
        return o2 instanceof SubscriptionNotification;
    }

    public static <T> Object next(T value) {
        return value;
    }

    public static Object subscription(Subscription s2) {
        return new SubscriptionNotification(s2);
    }

    @Override // java.lang.Enum
    public String toString() {
        return "NotificationLite.Complete";
    }

    public static <T> boolean accept(Object o2, Observer<? super T> observer) {
        if (o2 == COMPLETE) {
            observer.onComplete();
            return true;
        }
        if (o2 instanceof ErrorNotification) {
            observer.onError(((ErrorNotification) o2).f27370e);
            return true;
        }
        observer.onNext(o2);
        return false;
    }

    public static <T> boolean acceptFull(Object o2, Observer<? super T> observer) {
        if (o2 == COMPLETE) {
            observer.onComplete();
            return true;
        }
        if (o2 instanceof ErrorNotification) {
            observer.onError(((ErrorNotification) o2).f27370e);
            return true;
        }
        if (o2 instanceof DisposableNotification) {
            observer.onSubscribe(((DisposableNotification) o2).upstream);
            return false;
        }
        observer.onNext(o2);
        return false;
    }
}
