package io.reactivex.rxjava3.subscribers;

import androidx.camera.view.j;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.observers.BaseTestConsumer;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public class TestSubscriber<T> extends BaseTestConsumer<T, TestSubscriber<T>> implements FlowableSubscriber<T>, Subscription {
    private volatile boolean cancelled;
    private final Subscriber<? super T> downstream;
    private final AtomicLong missedRequested;
    private final AtomicReference<Subscription> upstream;

    public enum EmptySubscriber implements FlowableSubscriber<Object> {
        INSTANCE;

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object t2) {
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
        }
    }

    public TestSubscriber() {
        this(EmptySubscriber.INSTANCE, Long.MAX_VALUE);
    }

    @NonNull
    public static <T> TestSubscriber<T> create() {
        return new TestSubscriber<>();
    }

    @Override // org.reactivestreams.Subscription
    public final void cancel() {
        if (this.cancelled) {
            return;
        }
        this.cancelled = true;
        SubscriptionHelper.cancel(this.upstream);
    }

    @Override // io.reactivex.rxjava3.observers.BaseTestConsumer, io.reactivex.rxjava3.disposables.Disposable
    public final void dispose() {
        cancel();
    }

    public final boolean hasSubscription() {
        return this.upstream.get() != null;
    }

    public final boolean isCancelled() {
        return this.cancelled;
    }

    @Override // io.reactivex.rxjava3.observers.BaseTestConsumer, io.reactivex.rxjava3.disposables.Disposable
    public final boolean isDisposed() {
        return this.cancelled;
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (!this.checkSubscriptionOnce) {
            this.checkSubscriptionOnce = true;
            if (this.upstream.get() == null) {
                this.errors.add(new IllegalStateException("onSubscribe not called in proper order"));
            }
        }
        try {
            this.lastThread = Thread.currentThread();
            this.completions++;
            this.downstream.onComplete();
        } finally {
            this.done.countDown();
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(@NonNull Throwable t2) {
        if (!this.checkSubscriptionOnce) {
            this.checkSubscriptionOnce = true;
            if (this.upstream.get() == null) {
                this.errors.add(new IllegalStateException("onSubscribe not called in proper order"));
            }
        }
        try {
            this.lastThread = Thread.currentThread();
            if (t2 == null) {
                this.errors.add(new NullPointerException("onError received a null Throwable"));
            } else {
                this.errors.add(t2);
            }
            this.downstream.onError(t2);
        } finally {
            this.done.countDown();
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(@NonNull T t2) {
        if (!this.checkSubscriptionOnce) {
            this.checkSubscriptionOnce = true;
            if (this.upstream.get() == null) {
                this.errors.add(new IllegalStateException("onSubscribe not called in proper order"));
            }
        }
        this.lastThread = Thread.currentThread();
        this.values.add(t2);
        if (t2 == null) {
            this.errors.add(new NullPointerException("onNext received a null value"));
        }
        this.downstream.onNext(t2);
    }

    public void onStart() {
    }

    @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(@NonNull Subscription s2) {
        this.lastThread = Thread.currentThread();
        if (s2 == null) {
            this.errors.add(new NullPointerException("onSubscribe received a null Subscription"));
            return;
        }
        if (j.a(this.upstream, null, s2)) {
            this.downstream.onSubscribe(s2);
            long andSet = this.missedRequested.getAndSet(0L);
            if (andSet != 0) {
                s2.request(andSet);
            }
            onStart();
            return;
        }
        s2.cancel();
        if (this.upstream.get() != SubscriptionHelper.CANCELLED) {
            this.errors.add(new IllegalStateException("onSubscribe received multiple subscriptions: " + s2));
        }
    }

    @Override // org.reactivestreams.Subscription
    public final void request(long n2) {
        SubscriptionHelper.deferredRequest(this.upstream, this.missedRequested, n2);
    }

    public final TestSubscriber<T> requestMore(long n2) {
        request(n2);
        return this;
    }

    public TestSubscriber(long initialRequest) {
        this(EmptySubscriber.INSTANCE, initialRequest);
    }

    @NonNull
    public static <T> TestSubscriber<T> create(long initialRequested) {
        return new TestSubscriber<>(initialRequested);
    }

    @Override // io.reactivex.rxjava3.observers.BaseTestConsumer
    public final TestSubscriber<T> assertSubscribed() {
        if (this.upstream.get() != null) {
            return this;
        }
        throw fail("Not subscribed!");
    }

    public TestSubscriber(@NonNull Subscriber<? super T> downstream) {
        this(downstream, Long.MAX_VALUE);
    }

    public static <T> TestSubscriber<T> create(@NonNull Subscriber<? super T> delegate) {
        return new TestSubscriber<>(delegate);
    }

    public TestSubscriber(@NonNull Subscriber<? super T> actual, long initialRequest) {
        if (initialRequest >= 0) {
            this.downstream = actual;
            this.upstream = new AtomicReference<>();
            this.missedRequested = new AtomicLong(initialRequest);
            return;
        }
        throw new IllegalArgumentException("Negative initial request not allowed");
    }
}
