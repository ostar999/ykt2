package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class ObservableZip<T, R> extends Observable<R> {
    final int bufferSize;
    final boolean delayError;
    final ObservableSource<? extends T>[] sources;
    final Iterable<? extends ObservableSource<? extends T>> sourcesIterable;
    final Function<? super Object[], ? extends R> zipper;

    public static final class ZipCoordinator<T, R> extends AtomicInteger implements Disposable {
        private static final long serialVersionUID = 2983708048395377667L;
        volatile boolean cancelled;
        final boolean delayError;
        final Observer<? super R> downstream;
        final ZipObserver<T, R>[] observers;
        final T[] row;
        final Function<? super Object[], ? extends R> zipper;

        public ZipCoordinator(Observer<? super R> observer, Function<? super Object[], ? extends R> function, int i2, boolean z2) {
            this.downstream = observer;
            this.zipper = function;
            this.observers = new ZipObserver[i2];
            this.row = (T[]) new Object[i2];
            this.delayError = z2;
        }

        public void cancel() {
            clear();
            cancelSources();
        }

        public void cancelSources() {
            for (ZipObserver<T, R> zipObserver : this.observers) {
                zipObserver.dispose();
            }
        }

        public boolean checkTerminated(boolean z2, boolean z3, Observer<? super R> observer, boolean z4, ZipObserver<?, ?> zipObserver) {
            if (this.cancelled) {
                cancel();
                return true;
            }
            if (!z2) {
                return false;
            }
            if (z4) {
                if (!z3) {
                    return false;
                }
                Throwable th = zipObserver.error;
                cancel();
                if (th != null) {
                    observer.onError(th);
                } else {
                    observer.onComplete();
                }
                return true;
            }
            Throwable th2 = zipObserver.error;
            if (th2 != null) {
                cancel();
                observer.onError(th2);
                return true;
            }
            if (!z3) {
                return false;
            }
            cancel();
            observer.onComplete();
            return true;
        }

        public void clear() {
            for (ZipObserver<T, R> zipObserver : this.observers) {
                zipObserver.queue.clear();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            cancelSources();
            if (getAndIncrement() == 0) {
                clear();
            }
        }

        public void drain() {
            Throwable th;
            if (getAndIncrement() != 0) {
                return;
            }
            ZipObserver<T, R>[] zipObserverArr = this.observers;
            Observer<? super R> observer = this.downstream;
            T[] tArr = this.row;
            boolean z2 = this.delayError;
            int iAddAndGet = 1;
            while (true) {
                int i2 = 0;
                int i3 = 0;
                for (ZipObserver<T, R> zipObserver : zipObserverArr) {
                    if (tArr[i3] == null) {
                        boolean z3 = zipObserver.done;
                        T tPoll = zipObserver.queue.poll();
                        boolean z4 = tPoll == null;
                        if (checkTerminated(z3, z4, observer, z2, zipObserver)) {
                            return;
                        }
                        if (z4) {
                            i2++;
                        } else {
                            tArr[i3] = tPoll;
                        }
                    } else if (zipObserver.done && !z2 && (th = zipObserver.error) != null) {
                        cancel();
                        observer.onError(th);
                        return;
                    }
                    i3++;
                }
                if (i2 != 0) {
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    try {
                        observer.onNext((Object) ObjectHelper.requireNonNull(this.zipper.apply(tArr.clone()), "The zipper returned a null value"));
                        Arrays.fill(tArr, (Object) null);
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        cancel();
                        observer.onError(th2);
                        return;
                    }
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        public void subscribe(ObservableSource<? extends T>[] observableSourceArr, int i2) {
            ZipObserver<T, R>[] zipObserverArr = this.observers;
            int length = zipObserverArr.length;
            for (int i3 = 0; i3 < length; i3++) {
                zipObserverArr[i3] = new ZipObserver<>(this, i2);
            }
            lazySet(0);
            this.downstream.onSubscribe(this);
            for (int i4 = 0; i4 < length && !this.cancelled; i4++) {
                observableSourceArr[i4].subscribe(zipObserverArr[i4]);
            }
        }
    }

    public static final class ZipObserver<T, R> implements Observer<T> {
        volatile boolean done;
        Throwable error;
        final ZipCoordinator<T, R> parent;
        final SpscLinkedArrayQueue<T> queue;
        final AtomicReference<Disposable> upstream = new AtomicReference<>();

        public ZipObserver(ZipCoordinator<T, R> zipCoordinator, int i2) {
            this.parent = zipCoordinator;
            this.queue = new SpscLinkedArrayQueue<>(i2);
        }

        public void dispose() {
            DisposableHelper.dispose(this.upstream);
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
            DisposableHelper.setOnce(this.upstream, disposable);
        }
    }

    public ObservableZip(ObservableSource<? extends T>[] observableSourceArr, Iterable<? extends ObservableSource<? extends T>> iterable, Function<? super Object[], ? extends R> function, int i2, boolean z2) {
        this.sources = observableSourceArr;
        this.sourcesIterable = iterable;
        this.zipper = function;
        this.bufferSize = i2;
        this.delayError = z2;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super R> observer) {
        int length;
        ObservableSource<? extends T>[] observableSourceArr = this.sources;
        if (observableSourceArr == null) {
            observableSourceArr = new Observable[8];
            length = 0;
            for (ObservableSource<? extends T> observableSource : this.sourcesIterable) {
                if (length == observableSourceArr.length) {
                    ObservableSource<? extends T>[] observableSourceArr2 = new ObservableSource[(length >> 2) + length];
                    System.arraycopy(observableSourceArr, 0, observableSourceArr2, 0, length);
                    observableSourceArr = observableSourceArr2;
                }
                observableSourceArr[length] = observableSource;
                length++;
            }
        } else {
            length = observableSourceArr.length;
        }
        if (length == 0) {
            EmptyDisposable.complete(observer);
        } else {
            new ZipCoordinator(observer, this.zipper, length, this.delayError).subscribe(observableSourceArr, this.bufferSize);
        }
    }
}
