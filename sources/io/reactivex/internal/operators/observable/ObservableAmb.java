package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class ObservableAmb<T> extends Observable<T> {
    final ObservableSource<? extends T>[] sources;
    final Iterable<? extends ObservableSource<? extends T>> sourcesIterable;

    public static final class AmbCoordinator<T> implements Disposable {
        final Observer<? super T> downstream;
        final AmbInnerObserver<T>[] observers;
        final AtomicInteger winner = new AtomicInteger();

        public AmbCoordinator(Observer<? super T> observer, int i2) {
            this.downstream = observer;
            this.observers = new AmbInnerObserver[i2];
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (this.winner.get() != -1) {
                this.winner.lazySet(-1);
                for (AmbInnerObserver<T> ambInnerObserver : this.observers) {
                    ambInnerObserver.dispose();
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.winner.get() == -1;
        }

        public void subscribe(ObservableSource<? extends T>[] observableSourceArr) {
            AmbInnerObserver<T>[] ambInnerObserverArr = this.observers;
            int length = ambInnerObserverArr.length;
            int i2 = 0;
            while (i2 < length) {
                int i3 = i2 + 1;
                ambInnerObserverArr[i2] = new AmbInnerObserver<>(this, i3, this.downstream);
                i2 = i3;
            }
            this.winner.lazySet(0);
            this.downstream.onSubscribe(this);
            for (int i4 = 0; i4 < length && this.winner.get() == 0; i4++) {
                observableSourceArr[i4].subscribe(ambInnerObserverArr[i4]);
            }
        }

        public boolean win(int i2) {
            int i3 = this.winner.get();
            int i4 = 0;
            if (i3 != 0) {
                return i3 == i2;
            }
            if (!this.winner.compareAndSet(0, i2)) {
                return false;
            }
            AmbInnerObserver<T>[] ambInnerObserverArr = this.observers;
            int length = ambInnerObserverArr.length;
            while (i4 < length) {
                int i5 = i4 + 1;
                if (i5 != i2) {
                    ambInnerObserverArr[i4].dispose();
                }
                i4 = i5;
            }
            return true;
        }
    }

    public static final class AmbInnerObserver<T> extends AtomicReference<Disposable> implements Observer<T> {
        private static final long serialVersionUID = -1185974347409665484L;
        final Observer<? super T> downstream;
        final int index;
        final AmbCoordinator<T> parent;
        boolean won;

        public AmbInnerObserver(AmbCoordinator<T> ambCoordinator, int i2, Observer<? super T> observer) {
            this.parent = ambCoordinator;
            this.index = i2;
            this.downstream = observer;
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (this.won) {
                this.downstream.onComplete();
            } else if (this.parent.win(this.index)) {
                this.won = true;
                this.downstream.onComplete();
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.won) {
                this.downstream.onError(th);
            } else if (!this.parent.win(this.index)) {
                RxJavaPlugins.onError(th);
            } else {
                this.won = true;
                this.downstream.onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t2) {
            if (this.won) {
                this.downstream.onNext(t2);
            } else if (!this.parent.win(this.index)) {
                get().dispose();
            } else {
                this.won = true;
                this.downstream.onNext(t2);
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }
    }

    public ObservableAmb(ObservableSource<? extends T>[] observableSourceArr, Iterable<? extends ObservableSource<? extends T>> iterable) {
        this.sources = observableSourceArr;
        this.sourcesIterable = iterable;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        int length;
        ObservableSource<? extends T>[] observableSourceArr = this.sources;
        if (observableSourceArr == null) {
            observableSourceArr = new Observable[8];
            try {
                length = 0;
                for (ObservableSource<? extends T> observableSource : this.sourcesIterable) {
                    if (observableSource == null) {
                        EmptyDisposable.error(new NullPointerException("One of the sources is null"), observer);
                        return;
                    }
                    if (length == observableSourceArr.length) {
                        ObservableSource<? extends T>[] observableSourceArr2 = new ObservableSource[(length >> 2) + length];
                        System.arraycopy(observableSourceArr, 0, observableSourceArr2, 0, length);
                        observableSourceArr = observableSourceArr2;
                    }
                    int i2 = length + 1;
                    observableSourceArr[length] = observableSource;
                    length = i2;
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptyDisposable.error(th, observer);
                return;
            }
        } else {
            length = observableSourceArr.length;
        }
        if (length == 0) {
            EmptyDisposable.complete(observer);
        } else if (length == 1) {
            observableSourceArr[0].subscribe(observer);
        } else {
            new AmbCoordinator(observer, length).subscribe(observableSourceArr);
        }
    }
}
