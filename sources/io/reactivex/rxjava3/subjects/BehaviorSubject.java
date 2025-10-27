package io.reactivex.rxjava3.subjects;

import androidx.camera.view.j;
import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.internal.util.NotificationLite;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes8.dex */
public final class BehaviorSubject<T> extends Subject<T> {
    static final BehaviorDisposable[] EMPTY = new BehaviorDisposable[0];
    static final BehaviorDisposable[] TERMINATED = new BehaviorDisposable[0];
    long index;
    final ReadWriteLock lock;
    final AtomicReference<BehaviorDisposable<T>[]> observers;
    final Lock readLock;
    final AtomicReference<Throwable> terminalEvent;
    final AtomicReference<Object> value;
    final Lock writeLock;

    public static final class BehaviorDisposable<T> implements Disposable, AppendOnlyLinkedArrayList.NonThrowingPredicate<Object> {
        volatile boolean cancelled;
        final Observer<? super T> downstream;
        boolean emitting;
        boolean fastPath;
        long index;
        boolean next;
        AppendOnlyLinkedArrayList<Object> queue;
        final BehaviorSubject<T> state;

        public BehaviorDisposable(Observer<? super T> actual, BehaviorSubject<T> state) {
            this.downstream = actual;
            this.state = state;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.state.remove(this);
        }

        public void emitFirst() {
            if (this.cancelled) {
                return;
            }
            synchronized (this) {
                if (this.cancelled) {
                    return;
                }
                if (this.next) {
                    return;
                }
                BehaviorSubject<T> behaviorSubject = this.state;
                Lock lock = behaviorSubject.readLock;
                lock.lock();
                this.index = behaviorSubject.index;
                Object obj = behaviorSubject.value.get();
                lock.unlock();
                this.emitting = obj != null;
                this.next = true;
                if (obj == null || test(obj)) {
                    return;
                }
                emitLoop();
            }
        }

        public void emitLoop() {
            AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList;
            while (!this.cancelled) {
                synchronized (this) {
                    appendOnlyLinkedArrayList = this.queue;
                    if (appendOnlyLinkedArrayList == null) {
                        this.emitting = false;
                        return;
                    }
                    this.queue = null;
                }
                appendOnlyLinkedArrayList.forEachWhile(this);
            }
        }

        public void emitNext(Object value, long stateIndex) {
            if (this.cancelled) {
                return;
            }
            if (!this.fastPath) {
                synchronized (this) {
                    if (this.cancelled) {
                        return;
                    }
                    if (this.index == stateIndex) {
                        return;
                    }
                    if (this.emitting) {
                        AppendOnlyLinkedArrayList<Object> appendOnlyLinkedArrayList = this.queue;
                        if (appendOnlyLinkedArrayList == null) {
                            appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList<>(4);
                            this.queue = appendOnlyLinkedArrayList;
                        }
                        appendOnlyLinkedArrayList.add(value);
                        return;
                    }
                    this.next = true;
                    this.fastPath = true;
                }
            }
            test(value);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override // io.reactivex.rxjava3.internal.util.AppendOnlyLinkedArrayList.NonThrowingPredicate, io.reactivex.rxjava3.functions.Predicate
        public boolean test(Object o2) {
            return this.cancelled || NotificationLite.accept(o2, this.downstream);
        }
    }

    public BehaviorSubject(T defaultValue) {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        this.lock = reentrantReadWriteLock;
        this.readLock = reentrantReadWriteLock.readLock();
        this.writeLock = reentrantReadWriteLock.writeLock();
        this.observers = new AtomicReference<>(EMPTY);
        this.value = new AtomicReference<>(defaultValue);
        this.terminalEvent = new AtomicReference<>();
    }

    @CheckReturnValue
    @NonNull
    public static <T> BehaviorSubject<T> create() {
        return new BehaviorSubject<>(null);
    }

    @CheckReturnValue
    @NonNull
    public static <T> BehaviorSubject<T> createDefault(T defaultValue) {
        Objects.requireNonNull(defaultValue, "defaultValue is null");
        return new BehaviorSubject<>(defaultValue);
    }

    public boolean add(BehaviorDisposable<T> rs) {
        BehaviorDisposable<T>[] behaviorDisposableArr;
        BehaviorDisposable[] behaviorDisposableArr2;
        do {
            behaviorDisposableArr = this.observers.get();
            if (behaviorDisposableArr == TERMINATED) {
                return false;
            }
            int length = behaviorDisposableArr.length;
            behaviorDisposableArr2 = new BehaviorDisposable[length + 1];
            System.arraycopy(behaviorDisposableArr, 0, behaviorDisposableArr2, 0, length);
            behaviorDisposableArr2[length] = rs;
        } while (!j.a(this.observers, behaviorDisposableArr, behaviorDisposableArr2));
        return true;
    }

    @Override // io.reactivex.rxjava3.subjects.Subject
    @CheckReturnValue
    @Nullable
    public Throwable getThrowable() {
        Object obj = this.value.get();
        if (NotificationLite.isError(obj)) {
            return NotificationLite.getError(obj);
        }
        return null;
    }

    @CheckReturnValue
    @Nullable
    public T getValue() {
        Object obj = this.value.get();
        if (NotificationLite.isComplete(obj) || NotificationLite.isError(obj)) {
            return null;
        }
        return (T) NotificationLite.getValue(obj);
    }

    @Override // io.reactivex.rxjava3.subjects.Subject
    @CheckReturnValue
    public boolean hasComplete() {
        return NotificationLite.isComplete(this.value.get());
    }

    @Override // io.reactivex.rxjava3.subjects.Subject
    @CheckReturnValue
    public boolean hasObservers() {
        return this.observers.get().length != 0;
    }

    @Override // io.reactivex.rxjava3.subjects.Subject
    @CheckReturnValue
    public boolean hasThrowable() {
        return NotificationLite.isError(this.value.get());
    }

    @CheckReturnValue
    public boolean hasValue() {
        Object obj = this.value.get();
        return (obj == null || NotificationLite.isComplete(obj) || NotificationLite.isError(obj)) ? false : true;
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onComplete() {
        if (j.a(this.terminalEvent, null, ExceptionHelper.TERMINATED)) {
            Object objComplete = NotificationLite.complete();
            for (BehaviorDisposable<T> behaviorDisposable : terminate(objComplete)) {
                behaviorDisposable.emitNext(objComplete, this.index);
            }
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onError(Throwable t2) {
        ExceptionHelper.nullCheck(t2, "onError called with a null Throwable.");
        if (!j.a(this.terminalEvent, null, t2)) {
            RxJavaPlugins.onError(t2);
            return;
        }
        Object objError = NotificationLite.error(t2);
        for (BehaviorDisposable<T> behaviorDisposable : terminate(objError)) {
            behaviorDisposable.emitNext(objError, this.index);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(T t2) {
        ExceptionHelper.nullCheck(t2, "onNext called with a null value.");
        if (this.terminalEvent.get() != null) {
            return;
        }
        Object next = NotificationLite.next(t2);
        setCurrent(next);
        for (BehaviorDisposable<T> behaviorDisposable : this.observers.get()) {
            behaviorDisposable.emitNext(next, this.index);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onSubscribe(Disposable d3) {
        if (this.terminalEvent.get() != null) {
            d3.dispose();
        }
    }

    public void remove(BehaviorDisposable<T> rs) {
        BehaviorDisposable<T>[] behaviorDisposableArr;
        BehaviorDisposable[] behaviorDisposableArr2;
        do {
            behaviorDisposableArr = this.observers.get();
            int length = behaviorDisposableArr.length;
            if (length == 0) {
                return;
            }
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    i2 = -1;
                    break;
                } else if (behaviorDisposableArr[i2] == rs) {
                    break;
                } else {
                    i2++;
                }
            }
            if (i2 < 0) {
                return;
            }
            if (length == 1) {
                behaviorDisposableArr2 = EMPTY;
            } else {
                BehaviorDisposable[] behaviorDisposableArr3 = new BehaviorDisposable[length - 1];
                System.arraycopy(behaviorDisposableArr, 0, behaviorDisposableArr3, 0, i2);
                System.arraycopy(behaviorDisposableArr, i2 + 1, behaviorDisposableArr3, i2, (length - i2) - 1);
                behaviorDisposableArr2 = behaviorDisposableArr3;
            }
        } while (!j.a(this.observers, behaviorDisposableArr, behaviorDisposableArr2));
    }

    public void setCurrent(Object o2) {
        this.writeLock.lock();
        this.index++;
        this.value.lazySet(o2);
        this.writeLock.unlock();
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        BehaviorDisposable<T> behaviorDisposable = new BehaviorDisposable<>(observer, this);
        observer.onSubscribe(behaviorDisposable);
        if (add(behaviorDisposable)) {
            if (behaviorDisposable.cancelled) {
                remove(behaviorDisposable);
                return;
            } else {
                behaviorDisposable.emitFirst();
                return;
            }
        }
        Throwable th = this.terminalEvent.get();
        if (th == ExceptionHelper.TERMINATED) {
            observer.onComplete();
        } else {
            observer.onError(th);
        }
    }

    @CheckReturnValue
    public int subscriberCount() {
        return this.observers.get().length;
    }

    public BehaviorDisposable<T>[] terminate(Object terminalValue) {
        setCurrent(terminalValue);
        return this.observers.getAndSet(TERMINATED);
    }
}
