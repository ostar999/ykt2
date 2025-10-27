package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiPredicate;
import io.reactivex.internal.disposables.ArrayCompositeDisposable;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes8.dex */
public final class ObservableSequenceEqual<T> extends Observable<Boolean> {
    final int bufferSize;
    final BiPredicate<? super T, ? super T> comparer;
    final ObservableSource<? extends T> first;
    final ObservableSource<? extends T> second;

    public static final class EqualCoordinator<T> extends AtomicInteger implements Disposable {
        private static final long serialVersionUID = -6178010334400373240L;
        volatile boolean cancelled;
        final BiPredicate<? super T, ? super T> comparer;
        final Observer<? super Boolean> downstream;
        final ObservableSource<? extends T> first;
        final EqualObserver<T>[] observers;
        final ArrayCompositeDisposable resources = new ArrayCompositeDisposable(2);
        final ObservableSource<? extends T> second;

        /* renamed from: v1, reason: collision with root package name */
        T f27310v1;
        T v2;

        public EqualCoordinator(Observer<? super Boolean> observer, int i2, ObservableSource<? extends T> observableSource, ObservableSource<? extends T> observableSource2, BiPredicate<? super T, ? super T> biPredicate) {
            this.downstream = observer;
            this.first = observableSource;
            this.second = observableSource2;
            this.comparer = biPredicate;
            this.observers = new EqualObserver[]{new EqualObserver<>(this, 0, i2), new EqualObserver<>(this, 1, i2)};
        }

        public void cancel(SpscLinkedArrayQueue<T> spscLinkedArrayQueue, SpscLinkedArrayQueue<T> spscLinkedArrayQueue2) {
            this.cancelled = true;
            spscLinkedArrayQueue.clear();
            spscLinkedArrayQueue2.clear();
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.resources.dispose();
            if (getAndIncrement() == 0) {
                EqualObserver<T>[] equalObserverArr = this.observers;
                equalObserverArr[0].queue.clear();
                equalObserverArr[1].queue.clear();
            }
        }

        public void drain() {
            Throwable th;
            Throwable th2;
            if (getAndIncrement() != 0) {
                return;
            }
            EqualObserver<T>[] equalObserverArr = this.observers;
            EqualObserver<T> equalObserver = equalObserverArr[0];
            SpscLinkedArrayQueue<T> spscLinkedArrayQueue = equalObserver.queue;
            EqualObserver<T> equalObserver2 = equalObserverArr[1];
            SpscLinkedArrayQueue<T> spscLinkedArrayQueue2 = equalObserver2.queue;
            int iAddAndGet = 1;
            while (!this.cancelled) {
                boolean z2 = equalObserver.done;
                if (z2 && (th2 = equalObserver.error) != null) {
                    cancel(spscLinkedArrayQueue, spscLinkedArrayQueue2);
                    this.downstream.onError(th2);
                    return;
                }
                boolean z3 = equalObserver2.done;
                if (z3 && (th = equalObserver2.error) != null) {
                    cancel(spscLinkedArrayQueue, spscLinkedArrayQueue2);
                    this.downstream.onError(th);
                    return;
                }
                if (this.f27310v1 == null) {
                    this.f27310v1 = spscLinkedArrayQueue.poll();
                }
                boolean z4 = this.f27310v1 == null;
                if (this.v2 == null) {
                    this.v2 = spscLinkedArrayQueue2.poll();
                }
                T t2 = this.v2;
                boolean z5 = t2 == null;
                if (z2 && z3 && z4 && z5) {
                    this.downstream.onNext(Boolean.TRUE);
                    this.downstream.onComplete();
                    return;
                }
                if (z2 && z3 && z4 != z5) {
                    cancel(spscLinkedArrayQueue, spscLinkedArrayQueue2);
                    this.downstream.onNext(Boolean.FALSE);
                    this.downstream.onComplete();
                    return;
                }
                if (!z4 && !z5) {
                    try {
                        if (!this.comparer.test(this.f27310v1, t2)) {
                            cancel(spscLinkedArrayQueue, spscLinkedArrayQueue2);
                            this.downstream.onNext(Boolean.FALSE);
                            this.downstream.onComplete();
                            return;
                        }
                        this.f27310v1 = null;
                        this.v2 = null;
                    } catch (Throwable th3) {
                        Exceptions.throwIfFatal(th3);
                        cancel(spscLinkedArrayQueue, spscLinkedArrayQueue2);
                        this.downstream.onError(th3);
                        return;
                    }
                }
                if (z4 || z5) {
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                }
            }
            spscLinkedArrayQueue.clear();
            spscLinkedArrayQueue2.clear();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        public boolean setDisposable(Disposable disposable, int i2) {
            return this.resources.setResource(i2, disposable);
        }

        public void subscribe() {
            EqualObserver<T>[] equalObserverArr = this.observers;
            this.first.subscribe(equalObserverArr[0]);
            this.second.subscribe(equalObserverArr[1]);
        }
    }

    public static final class EqualObserver<T> implements Observer<T> {
        volatile boolean done;
        Throwable error;
        final int index;
        final EqualCoordinator<T> parent;
        final SpscLinkedArrayQueue<T> queue;

        public EqualObserver(EqualCoordinator<T> equalCoordinator, int i2, int i3) {
            this.parent = equalCoordinator;
            this.index = i2;
            this.queue = new SpscLinkedArrayQueue<>(i3);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.done = true;
            this.parent.drain();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            this.parent.drain();
        }

        @Override // io.reactivex.Observer
        public void onNext(T t2) {
            this.queue.offer(t2);
            this.parent.drain();
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            this.parent.setDisposable(disposable, this.index);
        }
    }

    public ObservableSequenceEqual(ObservableSource<? extends T> observableSource, ObservableSource<? extends T> observableSource2, BiPredicate<? super T, ? super T> biPredicate, int i2) {
        this.first = observableSource;
        this.second = observableSource2;
        this.comparer = biPredicate;
        this.bufferSize = i2;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super Boolean> observer) {
        EqualCoordinator equalCoordinator = new EqualCoordinator(observer, this.bufferSize, this.first, this.second, this.comparer);
        observer.onSubscribe(equalCoordinator);
        equalCoordinator.subscribe();
    }
}
