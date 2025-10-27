package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Cancellable;
import io.reactivex.rxjava3.internal.disposables.CancellableDisposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.operators.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class ObservableCreate<T> extends Observable<T> {
    final ObservableOnSubscribe<T> source;

    public static final class CreateEmitter<T> extends AtomicReference<Disposable> implements ObservableEmitter<T>, Disposable {
        private static final long serialVersionUID = -3434801548987643227L;
        final Observer<? super T> observer;

        public CreateEmitter(Observer<? super T> observer) {
            this.observer = observer;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.rxjava3.core.ObservableEmitter, io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public void onComplete() {
            if (isDisposed()) {
                return;
            }
            try {
                this.observer.onComplete();
            } finally {
                dispose();
            }
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public void onError(Throwable t2) {
            if (tryOnError(t2)) {
                return;
            }
            RxJavaPlugins.onError(t2);
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public void onNext(T t2) {
            if (t2 == null) {
                onError(ExceptionHelper.createNullPointerException("onNext called with a null value."));
            } else {
                if (isDisposed()) {
                    return;
                }
                this.observer.onNext(t2);
            }
        }

        @Override // io.reactivex.rxjava3.core.ObservableEmitter
        public ObservableEmitter<T> serialize() {
            return new SerializedEmitter(this);
        }

        @Override // io.reactivex.rxjava3.core.ObservableEmitter
        public void setCancellable(Cancellable c3) {
            setDisposable(new CancellableDisposable(c3));
        }

        @Override // io.reactivex.rxjava3.core.ObservableEmitter
        public void setDisposable(Disposable d3) {
            DisposableHelper.set(this, d3);
        }

        @Override // java.util.concurrent.atomic.AtomicReference
        public String toString() {
            return String.format("%s{%s}", CreateEmitter.class.getSimpleName(), super.toString());
        }

        @Override // io.reactivex.rxjava3.core.ObservableEmitter
        public boolean tryOnError(Throwable t2) {
            if (t2 == null) {
                t2 = ExceptionHelper.createNullPointerException("onError called with a null Throwable.");
            }
            if (isDisposed()) {
                return false;
            }
            try {
                this.observer.onError(t2);
                dispose();
                return true;
            } catch (Throwable th) {
                dispose();
                throw th;
            }
        }
    }

    public static final class SerializedEmitter<T> extends AtomicInteger implements ObservableEmitter<T> {
        private static final long serialVersionUID = 4883307006032401862L;
        volatile boolean done;
        final ObservableEmitter<T> emitter;
        final AtomicThrowable errors = new AtomicThrowable();
        final SpscLinkedArrayQueue<T> queue = new SpscLinkedArrayQueue<>(16);

        public SerializedEmitter(ObservableEmitter<T> emitter) {
            this.emitter = emitter;
        }

        public void drain() {
            if (getAndIncrement() == 0) {
                drainLoop();
            }
        }

        public void drainLoop() {
            ObservableEmitter<T> observableEmitter = this.emitter;
            SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.queue;
            AtomicThrowable atomicThrowable = this.errors;
            int iAddAndGet = 1;
            while (!observableEmitter.isDisposed()) {
                if (atomicThrowable.get() != null) {
                    spscLinkedArrayQueue.clear();
                    atomicThrowable.tryTerminateConsumer(observableEmitter);
                    return;
                }
                boolean z2 = this.done;
                T tPoll = spscLinkedArrayQueue.poll();
                boolean z3 = tPoll == null;
                if (z2 && z3) {
                    observableEmitter.onComplete();
                    return;
                } else if (z3) {
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    observableEmitter.onNext(tPoll);
                }
            }
            spscLinkedArrayQueue.clear();
        }

        @Override // io.reactivex.rxjava3.core.ObservableEmitter, io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.emitter.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public void onComplete() {
            if (this.done || this.emitter.isDisposed()) {
                return;
            }
            this.done = true;
            drain();
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public void onError(Throwable t2) {
            if (tryOnError(t2)) {
                return;
            }
            RxJavaPlugins.onError(t2);
        }

        @Override // io.reactivex.rxjava3.core.Emitter
        public void onNext(T t2) {
            if (this.done || this.emitter.isDisposed()) {
                return;
            }
            if (t2 == null) {
                onError(ExceptionHelper.createNullPointerException("onNext called with a null value."));
                return;
            }
            if (get() == 0 && compareAndSet(0, 1)) {
                this.emitter.onNext(t2);
                if (decrementAndGet() == 0) {
                    return;
                }
            } else {
                SpscLinkedArrayQueue<T> spscLinkedArrayQueue = this.queue;
                synchronized (spscLinkedArrayQueue) {
                    spscLinkedArrayQueue.offer(t2);
                }
                if (getAndIncrement() != 0) {
                    return;
                }
            }
            drainLoop();
        }

        @Override // io.reactivex.rxjava3.core.ObservableEmitter
        public ObservableEmitter<T> serialize() {
            return this;
        }

        @Override // io.reactivex.rxjava3.core.ObservableEmitter
        public void setCancellable(Cancellable c3) {
            this.emitter.setCancellable(c3);
        }

        @Override // io.reactivex.rxjava3.core.ObservableEmitter
        public void setDisposable(Disposable d3) {
            this.emitter.setDisposable(d3);
        }

        @Override // java.util.concurrent.atomic.AtomicInteger
        public String toString() {
            return this.emitter.toString();
        }

        @Override // io.reactivex.rxjava3.core.ObservableEmitter
        public boolean tryOnError(Throwable t2) {
            if (!this.done && !this.emitter.isDisposed()) {
                if (t2 == null) {
                    t2 = ExceptionHelper.createNullPointerException("onError called with a null Throwable.");
                }
                if (this.errors.tryAddThrowable(t2)) {
                    this.done = true;
                    drain();
                    return true;
                }
            }
            return false;
        }
    }

    public ObservableCreate(ObservableOnSubscribe<T> source) {
        this.source = source;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        CreateEmitter createEmitter = new CreateEmitter(observer);
        observer.onSubscribe(createEmitter);
        try {
            this.source.subscribe(createEmitter);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            createEmitter.onError(th);
        }
    }
}
