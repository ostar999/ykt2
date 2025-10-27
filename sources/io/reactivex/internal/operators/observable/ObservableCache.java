package io.reactivex.internal.operators.observable;

import androidx.camera.view.j;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class ObservableCache<T> extends AbstractObservableWithUpstream<T, T> implements Observer<T> {
    static final CacheDisposable[] EMPTY = new CacheDisposable[0];
    static final CacheDisposable[] TERMINATED = new CacheDisposable[0];
    final int capacityHint;
    volatile boolean done;
    Throwable error;
    final Node<T> head;
    final AtomicReference<CacheDisposable<T>[]> observers;
    final AtomicBoolean once;
    volatile long size;
    Node<T> tail;
    int tailOffset;

    public static final class CacheDisposable<T> extends AtomicInteger implements Disposable {
        private static final long serialVersionUID = 6770240836423125754L;
        volatile boolean disposed;
        final Observer<? super T> downstream;
        long index;
        Node<T> node;
        int offset;
        final ObservableCache<T> parent;

        public CacheDisposable(Observer<? super T> observer, ObservableCache<T> observableCache) {
            this.downstream = observer;
            this.parent = observableCache;
            this.node = observableCache.head;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.disposed) {
                return;
            }
            this.disposed = true;
            this.parent.remove(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.disposed;
        }
    }

    public static final class Node<T> {
        volatile Node<T> next;
        final T[] values;

        public Node(int i2) {
            this.values = (T[]) new Object[i2];
        }
    }

    public ObservableCache(Observable<T> observable, int i2) {
        super(observable);
        this.capacityHint = i2;
        this.once = new AtomicBoolean();
        Node<T> node = new Node<>(i2);
        this.head = node;
        this.tail = node;
        this.observers = new AtomicReference<>(EMPTY);
    }

    public void add(CacheDisposable<T> cacheDisposable) {
        CacheDisposable<T>[] cacheDisposableArr;
        CacheDisposable[] cacheDisposableArr2;
        do {
            cacheDisposableArr = this.observers.get();
            if (cacheDisposableArr == TERMINATED) {
                return;
            }
            int length = cacheDisposableArr.length;
            cacheDisposableArr2 = new CacheDisposable[length + 1];
            System.arraycopy(cacheDisposableArr, 0, cacheDisposableArr2, 0, length);
            cacheDisposableArr2[length] = cacheDisposable;
        } while (!j.a(this.observers, cacheDisposableArr, cacheDisposableArr2));
    }

    public long cachedEventCount() {
        return this.size;
    }

    public boolean hasObservers() {
        return this.observers.get().length != 0;
    }

    public boolean isConnected() {
        return this.once.get();
    }

    @Override // io.reactivex.Observer
    public void onComplete() {
        this.done = true;
        for (CacheDisposable<T> cacheDisposable : this.observers.getAndSet(TERMINATED)) {
            replay(cacheDisposable);
        }
    }

    @Override // io.reactivex.Observer
    public void onError(Throwable th) {
        this.error = th;
        this.done = true;
        for (CacheDisposable<T> cacheDisposable : this.observers.getAndSet(TERMINATED)) {
            replay(cacheDisposable);
        }
    }

    @Override // io.reactivex.Observer
    public void onNext(T t2) {
        int i2 = this.tailOffset;
        if (i2 == this.capacityHint) {
            Node<T> node = new Node<>(i2);
            node.values[0] = t2;
            this.tailOffset = 1;
            this.tail.next = node;
            this.tail = node;
        } else {
            this.tail.values[i2] = t2;
            this.tailOffset = i2 + 1;
        }
        this.size++;
        for (CacheDisposable<T> cacheDisposable : this.observers.get()) {
            replay(cacheDisposable);
        }
    }

    @Override // io.reactivex.Observer
    public void onSubscribe(Disposable disposable) {
    }

    public void remove(CacheDisposable<T> cacheDisposable) {
        CacheDisposable<T>[] cacheDisposableArr;
        CacheDisposable[] cacheDisposableArr2;
        do {
            cacheDisposableArr = this.observers.get();
            int length = cacheDisposableArr.length;
            if (length == 0) {
                return;
            }
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    i2 = -1;
                    break;
                } else if (cacheDisposableArr[i2] == cacheDisposable) {
                    break;
                } else {
                    i2++;
                }
            }
            if (i2 < 0) {
                return;
            }
            if (length == 1) {
                cacheDisposableArr2 = EMPTY;
            } else {
                CacheDisposable[] cacheDisposableArr3 = new CacheDisposable[length - 1];
                System.arraycopy(cacheDisposableArr, 0, cacheDisposableArr3, 0, i2);
                System.arraycopy(cacheDisposableArr, i2 + 1, cacheDisposableArr3, i2, (length - i2) - 1);
                cacheDisposableArr2 = cacheDisposableArr3;
            }
        } while (!j.a(this.observers, cacheDisposableArr, cacheDisposableArr2));
    }

    public void replay(CacheDisposable<T> cacheDisposable) {
        if (cacheDisposable.getAndIncrement() != 0) {
            return;
        }
        long j2 = cacheDisposable.index;
        int i2 = cacheDisposable.offset;
        Node<T> node = cacheDisposable.node;
        Observer<? super T> observer = cacheDisposable.downstream;
        int i3 = this.capacityHint;
        int iAddAndGet = 1;
        while (!cacheDisposable.disposed) {
            boolean z2 = this.done;
            boolean z3 = this.size == j2;
            if (z2 && z3) {
                cacheDisposable.node = null;
                Throwable th = this.error;
                if (th != null) {
                    observer.onError(th);
                    return;
                } else {
                    observer.onComplete();
                    return;
                }
            }
            if (z3) {
                cacheDisposable.index = j2;
                cacheDisposable.offset = i2;
                cacheDisposable.node = node;
                iAddAndGet = cacheDisposable.addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            } else {
                if (i2 == i3) {
                    node = node.next;
                    i2 = 0;
                }
                observer.onNext(node.values[i2]);
                i2++;
                j2++;
            }
        }
        cacheDisposable.node = null;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        CacheDisposable<T> cacheDisposable = new CacheDisposable<>(observer, this);
        observer.onSubscribe(cacheDisposable);
        add(cacheDisposable);
        if (this.once.get() || !this.once.compareAndSet(false, true)) {
            replay(cacheDisposable);
        } else {
            this.source.subscribe(this);
        }
    }
}
