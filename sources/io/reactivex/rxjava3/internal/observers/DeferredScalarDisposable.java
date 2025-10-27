package io.reactivex.rxjava3.internal.observers;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes8.dex */
public class DeferredScalarDisposable<T> extends BasicIntQueueDisposable<T> {
    static final int DISPOSED = 4;
    static final int FUSED_CONSUMED = 32;
    static final int FUSED_EMPTY = 8;
    static final int FUSED_READY = 16;
    static final int TERMINATED = 2;
    private static final long serialVersionUID = -5502432239815349361L;
    protected final Observer<? super T> downstream;
    protected T value;

    public DeferredScalarDisposable(Observer<? super T> downstream) {
        this.downstream = downstream;
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public final void clear() {
        lazySet(32);
        this.value = null;
    }

    public final void complete(T value) {
        int i2 = get();
        if ((i2 & 54) != 0) {
            return;
        }
        Observer<? super T> observer = this.downstream;
        if (i2 == 8) {
            this.value = value;
            lazySet(16);
            observer.onNext(null);
        } else {
            lazySet(2);
            observer.onNext(value);
        }
        if (get() != 4) {
            observer.onComplete();
        }
    }

    public void dispose() {
        set(4);
        this.value = null;
    }

    public final void error(Throwable t2) {
        if ((get() & 54) != 0) {
            RxJavaPlugins.onError(t2);
        } else {
            lazySet(2);
            this.downstream.onError(t2);
        }
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public final boolean isDisposed() {
        return get() == 4;
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    public final boolean isEmpty() {
        return get() != 16;
    }

    @Override // io.reactivex.rxjava3.operators.SimpleQueue
    @Nullable
    public final T poll() {
        if (get() != 16) {
            return null;
        }
        T t2 = this.value;
        this.value = null;
        lazySet(32);
        return t2;
    }

    @Override // io.reactivex.rxjava3.operators.QueueFuseable
    public final int requestFusion(int mode) {
        if ((mode & 2) == 0) {
            return 0;
        }
        lazySet(8);
        return 2;
    }

    public final boolean tryDispose() {
        return getAndSet(4) != 4;
    }

    public final void complete() {
        if ((get() & 54) != 0) {
            return;
        }
        lazySet(2);
        this.downstream.onComplete();
    }
}
