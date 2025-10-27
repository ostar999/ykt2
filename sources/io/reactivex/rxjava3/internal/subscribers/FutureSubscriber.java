package io.reactivex.rxjava3.internal.subscribers;

import androidx.camera.view.j;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BlockingHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FutureSubscriber<T> extends CountDownLatch implements FlowableSubscriber<T>, Future<T>, Subscription {
    Throwable error;
    final AtomicReference<Subscription> upstream;
    T value;

    public FutureSubscriber() {
        super(1);
        this.upstream = new AtomicReference<>();
    }

    @Override // org.reactivestreams.Subscription
    public void cancel() {
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean mayInterruptIfRunning) {
        Subscription subscription;
        SubscriptionHelper subscriptionHelper;
        do {
            subscription = this.upstream.get();
            if (subscription == this || subscription == (subscriptionHelper = SubscriptionHelper.CANCELLED)) {
                return false;
            }
        } while (!j.a(this.upstream, subscription, subscriptionHelper));
        if (subscription != null) {
            subscription.cancel();
        }
        countDown();
        return true;
    }

    @Override // java.util.concurrent.Future
    public T get() throws ExecutionException, InterruptedException {
        if (getCount() != 0) {
            BlockingHelper.verifyNonBlocking();
            await();
        }
        if (isCancelled()) {
            throw new CancellationException();
        }
        Throwable th = this.error;
        if (th == null) {
            return this.value;
        }
        throw new ExecutionException(th);
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return this.upstream.get() == SubscriptionHelper.CANCELLED;
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return getCount() == 0;
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (this.value == null) {
            onError(new NoSuchElementException("The source is empty"));
            return;
        }
        Subscription subscription = this.upstream.get();
        if (subscription == this || subscription == SubscriptionHelper.CANCELLED || !j.a(this.upstream, subscription, this)) {
            return;
        }
        countDown();
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable t2) {
        Subscription subscription;
        if (this.error != null || (subscription = this.upstream.get()) == this || subscription == SubscriptionHelper.CANCELLED || !j.a(this.upstream, subscription, this)) {
            RxJavaPlugins.onError(t2);
        } else {
            this.error = t2;
            countDown();
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t2) {
        if (this.value == null) {
            this.value = t2;
        } else {
            this.upstream.get().cancel();
            onError(new IndexOutOfBoundsException("More than one element received"));
        }
    }

    @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(Subscription s2) {
        SubscriptionHelper.setOnce(this.upstream, s2, Long.MAX_VALUE);
    }

    @Override // org.reactivestreams.Subscription
    public void request(long n2) {
    }

    @Override // java.util.concurrent.Future
    public T get(long timeout, @NonNull TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
        if (getCount() != 0) {
            BlockingHelper.verifyNonBlocking();
            if (!await(timeout, unit)) {
                throw new TimeoutException(ExceptionHelper.timeoutMessage(timeout, unit));
            }
        }
        if (!isCancelled()) {
            Throwable th = this.error;
            if (th == null) {
                return this.value;
            }
            throw new ExecutionException(th);
        }
        throw new CancellationException();
    }
}
