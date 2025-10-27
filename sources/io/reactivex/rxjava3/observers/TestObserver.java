package io.reactivex.rxjava3.observers;

import androidx.camera.view.j;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public class TestObserver<T> extends BaseTestConsumer<T, TestObserver<T>> implements Observer<T>, Disposable, MaybeObserver<T>, SingleObserver<T>, CompletableObserver {
    private final Observer<? super T> downstream;
    private final AtomicReference<Disposable> upstream;

    public enum EmptyObserver implements Observer<Object> {
        INSTANCE;

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object t2) {
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
        }
    }

    public TestObserver() {
        this(EmptyObserver.INSTANCE);
    }

    @NonNull
    public static <T> TestObserver<T> create() {
        return new TestObserver<>();
    }

    @Override // io.reactivex.rxjava3.observers.BaseTestConsumer, io.reactivex.rxjava3.disposables.Disposable
    public final void dispose() {
        DisposableHelper.dispose(this.upstream);
    }

    public final boolean hasSubscription() {
        return this.upstream.get() != null;
    }

    @Override // io.reactivex.rxjava3.observers.BaseTestConsumer, io.reactivex.rxjava3.disposables.Disposable
    public final boolean isDisposed() {
        return DisposableHelper.isDisposed(this.upstream.get());
    }

    @Override // io.reactivex.rxjava3.core.Observer
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

    @Override // io.reactivex.rxjava3.core.Observer
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

    @Override // io.reactivex.rxjava3.core.Observer
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

    @Override // io.reactivex.rxjava3.core.Observer
    public void onSubscribe(@NonNull Disposable d3) {
        this.lastThread = Thread.currentThread();
        if (d3 == null) {
            this.errors.add(new NullPointerException("onSubscribe received a null Subscription"));
            return;
        }
        if (j.a(this.upstream, null, d3)) {
            this.downstream.onSubscribe(d3);
            return;
        }
        d3.dispose();
        if (this.upstream.get() != DisposableHelper.DISPOSED) {
            this.errors.add(new IllegalStateException("onSubscribe received multiple subscriptions: " + d3));
        }
    }

    @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver
    public void onSuccess(@NonNull T value) {
        onNext(value);
        onComplete();
    }

    public TestObserver(@NonNull Observer<? super T> downstream) {
        this.upstream = new AtomicReference<>();
        this.downstream = downstream;
    }

    @NonNull
    public static <T> TestObserver<T> create(@NonNull Observer<? super T> delegate) {
        return new TestObserver<>(delegate);
    }

    @Override // io.reactivex.rxjava3.observers.BaseTestConsumer
    @NonNull
    public final TestObserver<T> assertSubscribed() {
        if (this.upstream.get() != null) {
            return this;
        }
        throw fail("Not subscribed!");
    }
}
