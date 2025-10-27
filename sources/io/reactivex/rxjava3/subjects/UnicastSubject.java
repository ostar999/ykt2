package io.reactivex.rxjava3.subjects;

import androidx.camera.view.j;
import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.functions.ObjectHelper;
import io.reactivex.rxjava3.internal.observers.BasicIntQueueDisposable;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.operators.SimpleQueue;
import io.reactivex.rxjava3.operators.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class UnicastSubject<T> extends Subject<T> {
    final boolean delayError;
    volatile boolean disposed;
    volatile boolean done;
    boolean enableOperatorFusion;
    Throwable error;
    final AtomicReference<Runnable> onTerminate;
    final SpscLinkedArrayQueue<T> queue;
    final AtomicReference<Observer<? super T>> downstream = new AtomicReference<>();
    final AtomicBoolean once = new AtomicBoolean();
    final BasicIntQueueDisposable<T> wip = new UnicastQueueDisposable();

    public final class UnicastQueueDisposable extends BasicIntQueueDisposable<T> {
        private static final long serialVersionUID = 7926949470189395511L;

        public UnicastQueueDisposable() {
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public void clear() {
            UnicastSubject.this.queue.clear();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (UnicastSubject.this.disposed) {
                return;
            }
            UnicastSubject.this.disposed = true;
            UnicastSubject.this.doTerminate();
            UnicastSubject.this.downstream.lazySet(null);
            if (UnicastSubject.this.wip.getAndIncrement() == 0) {
                UnicastSubject.this.downstream.lazySet(null);
                UnicastSubject unicastSubject = UnicastSubject.this;
                if (unicastSubject.enableOperatorFusion) {
                    return;
                }
                unicastSubject.queue.clear();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return UnicastSubject.this.disposed;
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean isEmpty() {
            return UnicastSubject.this.queue.isEmpty();
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public T poll() {
            return UnicastSubject.this.queue.poll();
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int mode) {
            if ((mode & 2) == 0) {
                return 0;
            }
            UnicastSubject.this.enableOperatorFusion = true;
            return 2;
        }
    }

    public UnicastSubject(int capacityHint, Runnable onTerminate, boolean delayError) {
        this.queue = new SpscLinkedArrayQueue<>(capacityHint);
        this.onTerminate = new AtomicReference<>(onTerminate);
        this.delayError = delayError;
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastSubject<T> create() {
        return new UnicastSubject<>(Observable.bufferSize(), null, true);
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

    public void drainFused(Observer<? super T> a3) {
        SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.queue;
        int iAddAndGet = 1;
        boolean z2 = !this.delayError;
        while (!this.disposed) {
            boolean z3 = this.done;
            if (z2 && z3 && failedFast(spscLinkedArrayQueue, a3)) {
                return;
            }
            a3.onNext(null);
            if (z3) {
                errorOrComplete(a3);
                return;
            } else {
                iAddAndGet = this.wip.addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
        }
        this.downstream.lazySet(null);
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

    public void errorOrComplete(Observer<? super T> a3) {
        this.downstream.lazySet(null);
        Throwable th = this.error;
        if (th != null) {
            a3.onError(th);
        } else {
            a3.onComplete();
        }
    }

    public boolean failedFast(final SimpleQueue<T> q2, Observer<? super T> a3) {
        Throwable th = this.error;
        if (th == null) {
            return false;
        }
        this.downstream.lazySet(null);
        q2.clear();
        a3.onError(th);
        return true;
    }

    @Override // io.reactivex.rxjava3.subjects.Subject
    @CheckReturnValue
    @Nullable
    public Throwable getThrowable() {
        if (this.done) {
            return this.error;
        }
        return null;
    }

    @Override // io.reactivex.rxjava3.subjects.Subject
    @CheckReturnValue
    public boolean hasComplete() {
        return this.done && this.error == null;
    }

    @Override // io.reactivex.rxjava3.subjects.Subject
    @CheckReturnValue
    public boolean hasObservers() {
        return this.downstream.get() != null;
    }

    @Override // io.reactivex.rxjava3.subjects.Subject
    @CheckReturnValue
    public boolean hasThrowable() {
        return this.done && this.error != null;
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onComplete() {
        if (this.done || this.disposed) {
            return;
        }
        this.done = true;
        doTerminate();
        drain();
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onError(Throwable t2) {
        ExceptionHelper.nullCheck(t2, "onError called with a null Throwable.");
        if (this.done || this.disposed) {
            RxJavaPlugins.onError(t2);
            return;
        }
        this.error = t2;
        this.done = true;
        doTerminate();
        drain();
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(T t2) {
        ExceptionHelper.nullCheck(t2, "onNext called with a null value.");
        if (this.done || this.disposed) {
            return;
        }
        this.queue.offer(t2);
        drain();
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onSubscribe(Disposable d3) {
        if (this.done || this.disposed) {
            d3.dispose();
        }
    }

    @Override // io.reactivex.rxjava3.core.Observable
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
    public static <T> UnicastSubject<T> create(int capacityHint) {
        ObjectHelper.verifyPositive(capacityHint, "capacityHint");
        return new UnicastSubject<>(capacityHint, null, true);
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastSubject<T> create(int capacityHint, @NonNull Runnable onTerminate) {
        ObjectHelper.verifyPositive(capacityHint, "capacityHint");
        Objects.requireNonNull(onTerminate, "onTerminate");
        return new UnicastSubject<>(capacityHint, onTerminate, true);
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastSubject<T> create(int capacityHint, @NonNull Runnable onTerminate, boolean delayError) {
        ObjectHelper.verifyPositive(capacityHint, "capacityHint");
        Objects.requireNonNull(onTerminate, "onTerminate");
        return new UnicastSubject<>(capacityHint, onTerminate, delayError);
    }

    @CheckReturnValue
    @NonNull
    public static <T> UnicastSubject<T> create(boolean delayError) {
        return new UnicastSubject<>(Observable.bufferSize(), null, delayError);
    }
}
