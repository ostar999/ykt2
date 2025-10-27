package io.reactivex.rxjava3.internal.schedulers;

import cn.hutool.core.text.StrPool;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.disposables.DisposableContainer;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReferenceArray;

/* loaded from: classes8.dex */
public final class ScheduledRunnable extends AtomicReferenceArray<Object> implements Runnable, Callable<Object>, Disposable {
    static final int FUTURE_INDEX = 1;
    static final int PARENT_INDEX = 0;
    static final int THREAD_INDEX = 2;
    private static final long serialVersionUID = -6120223772001106981L;
    final Runnable actual;
    static final Object PARENT_DISPOSED = new Object();
    static final Object SYNC_DISPOSED = new Object();
    static final Object ASYNC_DISPOSED = new Object();
    static final Object DONE = new Object();

    public ScheduledRunnable(Runnable actual, DisposableContainer parent) {
        super(3);
        this.actual = actual;
        lazySet(0, parent);
    }

    @Override // java.util.concurrent.Callable
    public Object call() {
        run();
        return null;
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        Object obj;
        Object obj2;
        Object obj3;
        Object obj4;
        while (true) {
            Object obj5 = get(1);
            if (obj5 == DONE || obj5 == (obj3 = SYNC_DISPOSED) || obj5 == (obj4 = ASYNC_DISPOSED)) {
                break;
            }
            boolean z2 = get(2) != Thread.currentThread();
            if (z2) {
                obj3 = obj4;
            }
            if (compareAndSet(1, obj5, obj3)) {
                if (obj5 != null) {
                    ((Future) obj5).cancel(z2);
                }
            }
        }
        do {
            obj = get(0);
            if (obj == DONE || obj == (obj2 = PARENT_DISPOSED) || obj == null) {
                return;
            }
        } while (!compareAndSet(0, obj, obj2));
        ((DisposableContainer) obj).delete(this);
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        Object obj = get(0);
        return obj == PARENT_DISPOSED || obj == DONE;
    }

    @Override // java.lang.Runnable
    public void run() {
        Object obj;
        Object obj2;
        lazySet(2, Thread.currentThread());
        try {
            this.actual.run();
            Object obj3 = get(0);
            if (obj3 != PARENT_DISPOSED && compareAndSet(0, obj3, DONE) && obj3 != null) {
                ((DisposableContainer) obj3).delete(this);
            }
            do {
                obj2 = get(1);
                if (obj2 == SYNC_DISPOSED || obj2 == ASYNC_DISPOSED) {
                    break;
                }
            } while (!compareAndSet(1, obj2, DONE));
            lazySet(2, null);
        } catch (Throwable th) {
            try {
                RxJavaPlugins.onError(th);
                throw th;
            } catch (Throwable th2) {
                Object obj4 = get(0);
                if (obj4 != PARENT_DISPOSED && compareAndSet(0, obj4, DONE) && obj4 != null) {
                    ((DisposableContainer) obj4).delete(this);
                }
                do {
                    obj = get(1);
                    if (obj == SYNC_DISPOSED || obj == ASYNC_DISPOSED) {
                        break;
                    }
                } while (!compareAndSet(1, obj, DONE));
                lazySet(2, null);
                throw th2;
            }
        }
    }

    public void setFuture(Future<?> f2) {
        Object obj;
        do {
            obj = get(1);
            if (obj == DONE) {
                return;
            }
            if (obj == SYNC_DISPOSED) {
                f2.cancel(false);
                return;
            } else if (obj == ASYNC_DISPOSED) {
                f2.cancel(true);
                return;
            }
        } while (!compareAndSet(1, obj, f2));
    }

    @Override // java.util.concurrent.atomic.AtomicReferenceArray
    public String toString() {
        String str;
        Object obj = get(1);
        if (obj == DONE) {
            str = "Finished";
        } else if (obj == SYNC_DISPOSED) {
            str = "Disposed(Sync)";
        } else if (obj == ASYNC_DISPOSED) {
            str = "Disposed(Async)";
        } else {
            Object obj2 = get(2);
            if (obj2 == null) {
                str = "Waiting";
            } else {
                str = "Running on " + obj2;
            }
        }
        return ScheduledRunnable.class.getSimpleName() + StrPool.BRACKET_START + str + StrPool.BRACKET_END;
    }
}
