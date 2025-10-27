package io.reactivex.rxjava3.processors;

import androidx.camera.view.j;
import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class AsyncProcessor<T> extends FlowableProcessor<T> {
    static final AsyncSubscription[] EMPTY = new AsyncSubscription[0];
    static final AsyncSubscription[] TERMINATED = new AsyncSubscription[0];
    Throwable error;
    final AtomicReference<AsyncSubscription<T>[]> subscribers = new AtomicReference<>(EMPTY);
    T value;

    public static final class AsyncSubscription<T> extends DeferredScalarSubscription<T> {
        private static final long serialVersionUID = 5629876084736248016L;
        final AsyncProcessor<T> parent;

        public AsyncSubscription(Subscriber<? super T> actual, AsyncProcessor<T> parent) {
            super(actual);
            this.parent = parent;
        }

        @Override // io.reactivex.rxjava3.internal.subscriptions.DeferredScalarSubscription, org.reactivestreams.Subscription
        public void cancel() {
            if (super.tryCancel()) {
                this.parent.remove(this);
            }
        }

        public void onComplete() {
            if (isCancelled()) {
                return;
            }
            this.downstream.onComplete();
        }

        public void onError(Throwable t2) {
            if (isCancelled()) {
                RxJavaPlugins.onError(t2);
            } else {
                this.downstream.onError(t2);
            }
        }
    }

    @CheckReturnValue
    @NonNull
    public static <T> AsyncProcessor<T> create() {
        return new AsyncProcessor<>();
    }

    public boolean add(AsyncSubscription<T> ps) {
        AsyncSubscription<T>[] asyncSubscriptionArr;
        AsyncSubscription[] asyncSubscriptionArr2;
        do {
            asyncSubscriptionArr = this.subscribers.get();
            if (asyncSubscriptionArr == TERMINATED) {
                return false;
            }
            int length = asyncSubscriptionArr.length;
            asyncSubscriptionArr2 = new AsyncSubscription[length + 1];
            System.arraycopy(asyncSubscriptionArr, 0, asyncSubscriptionArr2, 0, length);
            asyncSubscriptionArr2[length] = ps;
        } while (!j.a(this.subscribers, asyncSubscriptionArr, asyncSubscriptionArr2));
        return true;
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    @Nullable
    public Throwable getThrowable() {
        if (this.subscribers.get() == TERMINATED) {
            return this.error;
        }
        return null;
    }

    @CheckReturnValue
    @Nullable
    public T getValue() {
        if (this.subscribers.get() == TERMINATED) {
            return this.value;
        }
        return null;
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    public boolean hasComplete() {
        return this.subscribers.get() == TERMINATED && this.error == null;
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    public boolean hasSubscribers() {
        return this.subscribers.get().length != 0;
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    public boolean hasThrowable() {
        return this.subscribers.get() == TERMINATED && this.error != null;
    }

    @CheckReturnValue
    public boolean hasValue() {
        return this.subscribers.get() == TERMINATED && this.value != null;
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        AsyncSubscription<T>[] asyncSubscriptionArr = this.subscribers.get();
        AsyncSubscription<T>[] asyncSubscriptionArr2 = TERMINATED;
        if (asyncSubscriptionArr == asyncSubscriptionArr2) {
            return;
        }
        T t2 = this.value;
        AsyncSubscription<T>[] andSet = this.subscribers.getAndSet(asyncSubscriptionArr2);
        int i2 = 0;
        if (t2 == null) {
            int length = andSet.length;
            while (i2 < length) {
                andSet[i2].onComplete();
                i2++;
            }
            return;
        }
        int length2 = andSet.length;
        while (i2 < length2) {
            andSet[i2].complete(t2);
            i2++;
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(@NonNull Throwable t2) {
        ExceptionHelper.nullCheck(t2, "onError called with a null Throwable.");
        AsyncSubscription<T>[] asyncSubscriptionArr = this.subscribers.get();
        AsyncSubscription<T>[] asyncSubscriptionArr2 = TERMINATED;
        if (asyncSubscriptionArr == asyncSubscriptionArr2) {
            RxJavaPlugins.onError(t2);
            return;
        }
        this.value = null;
        this.error = t2;
        for (AsyncSubscription<T> asyncSubscription : this.subscribers.getAndSet(asyncSubscriptionArr2)) {
            asyncSubscription.onError(t2);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(@NonNull T t2) {
        ExceptionHelper.nullCheck(t2, "onNext called with a null value.");
        if (this.subscribers.get() == TERMINATED) {
            return;
        }
        this.value = t2;
    }

    @Override // org.reactivestreams.Subscriber
    public void onSubscribe(@NonNull Subscription s2) {
        if (this.subscribers.get() == TERMINATED) {
            s2.cancel();
        } else {
            s2.request(Long.MAX_VALUE);
        }
    }

    public void remove(AsyncSubscription<T> ps) {
        AsyncSubscription<T>[] asyncSubscriptionArr;
        AsyncSubscription[] asyncSubscriptionArr2;
        do {
            asyncSubscriptionArr = this.subscribers.get();
            int length = asyncSubscriptionArr.length;
            if (length == 0) {
                return;
            }
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    i2 = -1;
                    break;
                } else if (asyncSubscriptionArr[i2] == ps) {
                    break;
                } else {
                    i2++;
                }
            }
            if (i2 < 0) {
                return;
            }
            if (length == 1) {
                asyncSubscriptionArr2 = EMPTY;
            } else {
                AsyncSubscription[] asyncSubscriptionArr3 = new AsyncSubscription[length - 1];
                System.arraycopy(asyncSubscriptionArr, 0, asyncSubscriptionArr3, 0, i2);
                System.arraycopy(asyncSubscriptionArr, i2 + 1, asyncSubscriptionArr3, i2, (length - i2) - 1);
                asyncSubscriptionArr2 = asyncSubscriptionArr3;
            }
        } while (!j.a(this.subscribers, asyncSubscriptionArr, asyncSubscriptionArr2));
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(@NonNull Subscriber<? super T> s2) {
        AsyncSubscription<T> asyncSubscription = new AsyncSubscription<>(s2, this);
        s2.onSubscribe(asyncSubscription);
        if (add(asyncSubscription)) {
            if (asyncSubscription.isCancelled()) {
                remove(asyncSubscription);
                return;
            }
            return;
        }
        Throwable th = this.error;
        if (th != null) {
            s2.onError(th);
            return;
        }
        T t2 = this.value;
        if (t2 != null) {
            asyncSubscription.complete(t2);
        } else {
            asyncSubscription.onComplete();
        }
    }
}
