package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.internal.observers.BasicQueueDisposable;
import java.util.Objects;

/* loaded from: classes8.dex */
public final class ObservableFromArray<T> extends Observable<T> {
    final T[] array;

    public static final class FromArrayDisposable<T> extends BasicQueueDisposable<T> {
        final T[] array;
        volatile boolean disposed;
        final Observer<? super T> downstream;
        boolean fusionMode;
        int index;

        public FromArrayDisposable(Observer<? super T> actual, T[] array) {
            this.downstream = actual;
            this.array = array;
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public void clear() {
            this.index = this.array.length;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.disposed = true;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.disposed;
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean isEmpty() {
            return this.index == this.array.length;
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public T poll() {
            int i2 = this.index;
            T[] tArr = this.array;
            if (i2 == tArr.length) {
                return null;
            }
            this.index = i2 + 1;
            T t2 = tArr[i2];
            Objects.requireNonNull(t2, "The array element is null");
            return t2;
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int mode) {
            if ((mode & 1) == 0) {
                return 0;
            }
            this.fusionMode = true;
            return 1;
        }

        public void run() {
            T[] tArr = this.array;
            int length = tArr.length;
            for (int i2 = 0; i2 < length && !isDisposed(); i2++) {
                T t2 = tArr[i2];
                if (t2 == null) {
                    this.downstream.onError(new NullPointerException("The element at index " + i2 + " is null"));
                    return;
                }
                this.downstream.onNext(t2);
            }
            if (isDisposed()) {
                return;
            }
            this.downstream.onComplete();
        }
    }

    public ObservableFromArray(T[] array) {
        this.array = array;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        FromArrayDisposable fromArrayDisposable = new FromArrayDisposable(observer, this.array);
        observer.onSubscribe(fromArrayDisposable);
        if (fromArrayDisposable.fusionMode) {
            return;
        }
        fromArrayDisposable.run();
    }
}
