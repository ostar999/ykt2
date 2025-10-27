package com.jakewharton.rxrelay2;

import androidx.camera.view.j;
import com.jakewharton.rxrelay2.AppendOnlyLinkedArrayList;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.lang.reflect.Array;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes4.dex */
public final class BehaviorRelay<T> extends Relay<T> {
    long index;
    final Lock readLock;
    private final AtomicReference<BehaviorDisposable<T>[]> subscribers;
    final AtomicReference<T> value;
    private final Lock writeLock;
    private static final Object[] EMPTY_ARRAY = new Object[0];
    private static final BehaviorDisposable[] EMPTY = new BehaviorDisposable[0];

    public static final class BehaviorDisposable<T> implements Disposable, AppendOnlyLinkedArrayList.NonThrowingPredicate<T> {
        final Observer<? super T> actual;
        volatile boolean cancelled;
        boolean emitting;
        boolean fastPath;
        long index;
        boolean next;
        AppendOnlyLinkedArrayList<T> queue;
        final BehaviorRelay<T> state;

        public BehaviorDisposable(Observer<? super T> observer, BehaviorRelay<T> behaviorRelay) {
            this.actual = observer;
            this.state = behaviorRelay;
        }

        @Override // io.reactivex.disposables.Disposable
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
                BehaviorRelay<T> behaviorRelay = this.state;
                Lock lock = behaviorRelay.readLock;
                lock.lock();
                this.index = behaviorRelay.index;
                T t2 = behaviorRelay.value.get();
                lock.unlock();
                this.emitting = t2 != null;
                this.next = true;
                if (t2 != null) {
                    test(t2);
                    emitLoop();
                }
            }
        }

        public void emitLoop() {
            AppendOnlyLinkedArrayList<T> appendOnlyLinkedArrayList;
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

        public void emitNext(T t2, long j2) {
            if (this.cancelled) {
                return;
            }
            if (!this.fastPath) {
                synchronized (this) {
                    if (this.cancelled) {
                        return;
                    }
                    if (this.index == j2) {
                        return;
                    }
                    if (this.emitting) {
                        AppendOnlyLinkedArrayList<T> appendOnlyLinkedArrayList = this.queue;
                        if (appendOnlyLinkedArrayList == null) {
                            appendOnlyLinkedArrayList = new AppendOnlyLinkedArrayList<>(4);
                            this.queue = appendOnlyLinkedArrayList;
                        }
                        appendOnlyLinkedArrayList.add(t2);
                        return;
                    }
                    this.next = true;
                    this.fastPath = true;
                }
            }
            test(t2);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override // com.jakewharton.rxrelay2.AppendOnlyLinkedArrayList.NonThrowingPredicate, io.reactivex.functions.Predicate
        public boolean test(T t2) {
            if (this.cancelled) {
                return false;
            }
            this.actual.onNext(t2);
            return false;
        }
    }

    private BehaviorRelay() {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        this.readLock = reentrantReadWriteLock.readLock();
        this.writeLock = reentrantReadWriteLock.writeLock();
        this.subscribers = new AtomicReference<>(EMPTY);
        this.value = new AtomicReference<>();
    }

    private void add(BehaviorDisposable<T> behaviorDisposable) {
        BehaviorDisposable<T>[] behaviorDisposableArr;
        BehaviorDisposable[] behaviorDisposableArr2;
        do {
            behaviorDisposableArr = this.subscribers.get();
            int length = behaviorDisposableArr.length;
            behaviorDisposableArr2 = new BehaviorDisposable[length + 1];
            System.arraycopy(behaviorDisposableArr, 0, behaviorDisposableArr2, 0, length);
            behaviorDisposableArr2[length] = behaviorDisposable;
        } while (!j.a(this.subscribers, behaviorDisposableArr, behaviorDisposableArr2));
    }

    public static <T> BehaviorRelay<T> create() {
        return new BehaviorRelay<>();
    }

    public static <T> BehaviorRelay<T> createDefault(T t2) {
        return new BehaviorRelay<>(t2);
    }

    private void setCurrent(T t2) {
        this.writeLock.lock();
        try {
            this.index++;
            this.value.lazySet(t2);
        } finally {
            this.writeLock.unlock();
        }
    }

    @Override // com.jakewharton.rxrelay2.Relay, io.reactivex.functions.Consumer
    public void accept(T t2) {
        if (t2 == null) {
            throw new NullPointerException("value == null");
        }
        setCurrent(t2);
        for (BehaviorDisposable<T> behaviorDisposable : this.subscribers.get()) {
            behaviorDisposable.emitNext(t2, this.index);
        }
    }

    public T getValue() {
        return this.value.get();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Object[] getValues() {
        Object[] objArr = EMPTY_ARRAY;
        Object[] values = getValues(objArr);
        return values == objArr ? new Object[0] : values;
    }

    @Override // com.jakewharton.rxrelay2.Relay
    public boolean hasObservers() {
        return this.subscribers.get().length != 0;
    }

    public boolean hasValue() {
        return this.value.get() != null;
    }

    public void remove(BehaviorDisposable<T> behaviorDisposable) {
        BehaviorDisposable<T>[] behaviorDisposableArr;
        BehaviorDisposable[] behaviorDisposableArr2;
        do {
            behaviorDisposableArr = this.subscribers.get();
            if (behaviorDisposableArr == EMPTY) {
                return;
            }
            int length = behaviorDisposableArr.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    i2 = -1;
                    break;
                } else if (behaviorDisposableArr[i2] == behaviorDisposable) {
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
        } while (!j.a(this.subscribers, behaviorDisposableArr, behaviorDisposableArr2));
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        BehaviorDisposable<T> behaviorDisposable = new BehaviorDisposable<>(observer, this);
        observer.onSubscribe(behaviorDisposable);
        add(behaviorDisposable);
        if (behaviorDisposable.cancelled) {
            remove(behaviorDisposable);
        } else {
            behaviorDisposable.emitFirst();
        }
    }

    public int subscriberCount() {
        return this.subscribers.get().length;
    }

    public T[] getValues(T[] tArr) {
        T t2 = this.value.get();
        if (t2 == null) {
            if (tArr.length != 0) {
                tArr[0] = null;
            }
            return tArr;
        }
        if (tArr.length != 0) {
            tArr[0] = t2;
            if (tArr.length == 1) {
                return tArr;
            }
            tArr[1] = null;
            return tArr;
        }
        T[] tArr2 = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), 1));
        tArr2[0] = t2;
        return tArr2;
    }

    private BehaviorRelay(T t2) {
        this();
        if (t2 != null) {
            this.value.lazySet(t2);
            return;
        }
        throw new NullPointerException("defaultValue == null");
    }
}
