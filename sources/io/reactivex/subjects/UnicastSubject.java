package io.reactivex.subjects;

import androidx.camera.view.j;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.observers.BasicIntQueueDisposable;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class UnicastSubject<T> extends Subject<T> {
    final boolean delayError;
    volatile boolean disposed;
    volatile boolean done;
    final AtomicReference<Observer<? super T>> downstream;
    boolean enableOperatorFusion;
    Throwable error;
    final AtomicReference<Runnable> onTerminate;
    final AtomicBoolean once;
    final SpscLinkedArrayQueue<T> queue;
    final BasicIntQueueDisposable<T> wip;

    public final class UnicastQueueDisposable extends BasicIntQueueDisposable<T> {
        private static final long serialVersionUID = 7926949470189395511L;

        public UnicastQueueDisposable() {
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public void clear() {
            UnicastSubject.this.queue.clear();
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (UnicastSubject.this.disposed) {
                return;
            }
            UnicastSubject.this.disposed = true;
            UnicastSubject.this.doTerminate();
            UnicastSubject.this.downstream.lazySet(null);
            if (UnicastSubject.this.wip.getAndIncrement() == 0) {
                UnicastSubject.this.downstream.lazySet(null);
                UnicastSubject.this.queue.clear();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return UnicastSubject.this.disposed;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return UnicastSubject.this.queue.isEmpty();
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Exception {
            return UnicastSubject.this.queue.poll();
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i2) {
            if ((i2 & 2) == 0) {
                return 0;
            }
            UnicastSubject.this.enableOperatorFusion = true;
            return 2;
        }
    }

    public UnicastSubject(int i2, boolean z2) {
        this.queue = new SpscLinkedArrayQueue<>(ObjectHelper.verifyPositive(i2, "capacityHint"));
        this.onTerminate = new AtomicReference<>();
        this.delayError = z2;
        this.downstream = new AtomicReference<>();
        this.once = new AtomicBoolean();
        this.wip = new UnicastQueueDisposable();
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastSubject<T> create() {
        return new UnicastSubject<>(Observable.bufferSize(), true);
    }

    public void doTerminate() {
        Runnable runnable = this.onTerminate.get();
        if (runnable == null || !j.a(this.onTerminate, runnable, null)) {
            return;
        }
        runnable.run();
    }

    public void drain() {
        if (this.wip.getAndIncrement() != 0) {
            return;
        }
        Observer<? super T> observer = this.downstream.get();
        int iAddAndGet = 1;
        while (observer == null) {
            iAddAndGet = this.wip.addAndGet(-iAddAndGet);
            if (iAddAndGet == 0) {
                return;
            } else {
                observer = this.downstream.get();
            }
        }
        if (this.enableOperatorFusion) {
            drainFused(observer);
        } else {
            drainNormal(observer);
        }
    }

    public void drainFused(Observer<? super T> observer) {
        SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.queue;
        int iAddAndGet = 1;
        boolean z2 = !this.delayError;
        while (!this.disposed) {
            boolean z3 = this.done;
            if (z2 && z3 && failedFast(spscLinkedArrayQueue, observer)) {
                return;
            }
            observer.onNext(null);
            if (z3) {
                errorOrComplete(observer);
                return;
            } else {
                iAddAndGet = this.wip.addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
        }
        this.downstream.lazySet(null);
        spscLinkedArrayQueue.clear();
    }

    public void drainNormal(Observer<? super T> observer) {
        SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.queue;
        boolean z2 = !this.delayError;
        boolean z3 = true;
        int iAddAndGet = 1;
        while (!this.disposed) {
            boolean z4 = this.done;
            T tPoll = this.queue.poll();
            boolean z5 = tPoll == null;
            if (z4) {
                if (z2 && z3) {
                    if (failedFast(spscLinkedArrayQueue, observer)) {
                        return;
                    } else {
                        z3 = false;
                    }
                }
                if (z5) {
                    errorOrComplete(observer);
                    return;
                }
            }
            if (z5) {
                iAddAndGet = this.wip.addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            } else {
                observer.onNext(tPoll);
            }
        }
        this.downstream.lazySet(null);
        spscLinkedArrayQueue.clear();
    }

    public void errorOrComplete(Observer<? super T> observer) {
        this.downstream.lazySet(null);
        Throwable th = this.error;
        if (th != null) {
            observer.onError(th);
        } else {
            observer.onComplete();
        }
    }

    public boolean failedFast(SimpleQueue<T> simpleQueue, Observer<? super T> observer) {
        Throwable th = this.error;
        if (th == null) {
            return false;
        }
        this.downstream.lazySet(null);
        simpleQueue.clear();
        observer.onError(th);
        return true;
    }

    @Override // io.reactivex.subjects.Subject
    @Nullable
    public Throwable getThrowable() {
        if (this.done) {
            return this.error;
        }
        return null;
    }

    @Override // io.reactivex.subjects.Subject
    public boolean hasComplete() {
        return this.done && this.error == null;
    }

    @Override // io.reactivex.subjects.Subject
    public boolean hasObservers() {
        return this.downstream.get() != null;
    }

    @Override // io.reactivex.subjects.Subject
    public boolean hasThrowable() {
        return this.done && this.error != null;
    }

    @Override // io.reactivex.Observer
    public void onComplete() {
        if (this.done || this.disposed) {
            return;
        }
        this.done = true;
        doTerminate();
        drain();
    }

    @Override // io.reactivex.Observer
    public void onError(Throwable th) {
        ObjectHelper.requireNonNull(th, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.done || this.disposed) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.error = th;
        this.done = true;
        doTerminate();
        drain();
    }

    @Override // io.reactivex.Observer
    public void onNext(T t2) {
        ObjectHelper.requireNonNull(t2, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.done || this.disposed) {
            return;
        }
        this.queue.offer(t2);
        drain();
    }

    @Override // io.reactivex.Observer
    public void onSubscribe(Disposable disposable) {
        if (this.done || this.disposed) {
            disposable.dispose();
        }
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        if (this.once.get() || !this.once.compareAndSet(false, true)) {
            EmptyDisposable.error(new IllegalStateException("Only a single observer allowed."), observer);
            return;
        }
        observer.onSubscribe(this.wip);
        this.downstream.lazySet(observer);
        if (this.disposed) {
            this.downstream.lazySet(null);
        } else {
            drain();
        }
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastSubject<T> create(int i2) {
        return new UnicastSubject<>(i2, true);
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastSubject<T> create(int i2, Runnable runnable) {
        return new UnicastSubject<>(i2, runnable, true);
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastSubject<T> create(int i2, Runnable runnable, boolean z2) {
        return new UnicastSubject<>(i2, runnable, z2);
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastSubject<T> create(boolean z2) {
        return new UnicastSubject<>(Observable.bufferSize(), z2);
    }

    public UnicastSubject(int i2, Runnable runnable) {
        this(i2, runnable, true);
    }

    public UnicastSubject(int i2, Runnable runnable, boolean z2) {
        this.queue = new SpscLinkedArrayQueue<>(ObjectHelper.verifyPositive(i2, "capacityHint"));
        this.onTerminate = new AtomicReference<>(ObjectHelper.requireNonNull(runnable, "onTerminate"));
        this.delayError = z2;
        this.downstream = new AtomicReference<>();
        this.once = new AtomicBoolean();
        this.wip = new UnicastQueueDisposable();
    }
}
