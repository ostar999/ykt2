package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.BasicQueueDisposable;

/* loaded from: classes8.dex */
public final class ObservableFromArray<T> extends Observable<T> {
    final T[] array;

    public static final class FromArrayDisposable<T> extends BasicQueueDisposable<T> {
        final T[] array;
        volatile boolean disposed;
        final Observer<? super T> downstream;
        boolean fusionMode;
        int index;

        public FromArrayDisposable(Observer<? super T> observer, T[] tArr) {
            this.downstream = observer;
            this.array = tArr;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public void clear() {
            this.index = this.array.length;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.disposed = true;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.disposed;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return this.index == this.array.length;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() {
            int i2 = this.index;
            T[] tArr = this.array;
            if (i2 == tArr.length) {
                return null;
            }
            this.index = i2 + 1;
            return (T) ObjectHelper.requireNonNull(tArr[i2], "The array element is null");
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i2) {
            if ((i2 & 1) == 0) {
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
                    this.downstream.onError(new NullPointerException("The " + i2 + "th element is null"));
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

    public ObservableFromArray(T[] tArr) {
        this.array = tArr;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        FromArrayDisposable fromArrayDisposable = new FromArrayDisposable(observer, this.array);
        observer.onSubscribe(fromArrayDisposable);
        if (fromArrayDisposable.fusionMode) {
            return;
        }
        fromArrayDisposable.run();
    }
}
