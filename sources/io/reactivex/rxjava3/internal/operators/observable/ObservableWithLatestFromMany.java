package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.HalfSerializer;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

/* loaded from: classes8.dex */
public final class ObservableWithLatestFromMany<T, R> extends AbstractObservableWithUpstream<T, R> {

    @NonNull
    final Function<? super Object[], R> combiner;

    @Nullable
    final ObservableSource<?>[] otherArray;

    @Nullable
    final Iterable<? extends ObservableSource<?>> otherIterable;

    public final class SingletonArrayFunc implements Function<T, R> {
        public SingletonArrayFunc() {
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object, java.lang.Object[]] */
        @Override // io.reactivex.rxjava3.functions.Function
        public R apply(T t2) throws Throwable {
            R rApply = ObservableWithLatestFromMany.this.combiner.apply(new Object[]{t2});
            Objects.requireNonNull(rApply, "The combiner returned a null value");
            return rApply;
        }
    }

    public static final class WithLatestFromObserver<T, R> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = 1577321883966341961L;
        final Function<? super Object[], R> combiner;
        volatile boolean done;
        final Observer<? super R> downstream;
        final AtomicThrowable error;
        final WithLatestInnerObserver[] observers;
        final AtomicReference<Disposable> upstream;
        final AtomicReferenceArray<Object> values;

        public WithLatestFromObserver(Observer<? super R> actual, Function<? super Object[], R> combiner, int n2) {
            this.downstream = actual;
            this.combiner = combiner;
            WithLatestInnerObserver[] withLatestInnerObserverArr = new WithLatestInnerObserver[n2];
            for (int i2 = 0; i2 < n2; i2++) {
                withLatestInnerObserverArr[i2] = new WithLatestInnerObserver(this, i2);
            }
            this.observers = withLatestInnerObserverArr;
            this.values = new AtomicReferenceArray<>(n2);
            this.upstream = new AtomicReference<>();
            this.error = new AtomicThrowable();
        }

        public void cancelAllBut(int index) {
            WithLatestInnerObserver[] withLatestInnerObserverArr = this.observers;
            for (int i2 = 0; i2 < withLatestInnerObserverArr.length; i2++) {
                if (i2 != index) {
                    withLatestInnerObserverArr[i2].dispose();
                }
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this.upstream);
            for (WithLatestInnerObserver withLatestInnerObserver : this.observers) {
                withLatestInnerObserver.dispose();
            }
        }

        public void innerComplete(int index, boolean nonEmpty) {
            if (nonEmpty) {
                return;
            }
            this.done = true;
            cancelAllBut(index);
            HalfSerializer.onComplete(this.downstream, this, this.error);
        }

        public void innerError(int index, Throwable t2) {
            this.done = true;
            DisposableHelper.dispose(this.upstream);
            cancelAllBut(index);
            HalfSerializer.onError(this.downstream, t2, this, this.error);
        }

        public void innerNext(int index, Object o2) {
            this.values.set(index, o2);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.upstream.get());
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            cancelAllBut(-1);
            HalfSerializer.onComplete(this.downstream, this, this.error);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            if (this.done) {
                RxJavaPlugins.onError(t2);
                return;
            }
            this.done = true;
            cancelAllBut(-1);
            HalfSerializer.onError(this.downstream, t2, this, this.error);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            AtomicReferenceArray<Object> atomicReferenceArray = this.values;
            int length = atomicReferenceArray.length();
            Object[] objArr = new Object[length + 1];
            int i2 = 0;
            objArr[0] = t2;
            while (i2 < length) {
                Object obj = atomicReferenceArray.get(i2);
                if (obj == null) {
                    return;
                }
                i2++;
                objArr[i2] = obj;
            }
            try {
                R rApply = this.combiner.apply(objArr);
                Objects.requireNonNull(rApply, "combiner returned a null value");
                HalfSerializer.onNext(this.downstream, rApply, this, this.error);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                dispose();
                onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            DisposableHelper.setOnce(this.upstream, d3);
        }

        public void subscribe(ObservableSource<?>[] others, int n2) {
            WithLatestInnerObserver[] withLatestInnerObserverArr = this.observers;
            AtomicReference<Disposable> atomicReference = this.upstream;
            for (int i2 = 0; i2 < n2 && !DisposableHelper.isDisposed(atomicReference.get()) && !this.done; i2++) {
                others[i2].subscribe(withLatestInnerObserverArr[i2]);
            }
        }
    }

    public static final class WithLatestInnerObserver extends AtomicReference<Disposable> implements Observer<Object> {
        private static final long serialVersionUID = 3256684027868224024L;
        boolean hasValue;
        final int index;
        final WithLatestFromObserver<?, ?> parent;

        public WithLatestInnerObserver(WithLatestFromObserver<?, ?> parent, int index) {
            this.parent = parent;
            this.index = index;
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.parent.innerComplete(this.index, this.hasValue);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            this.parent.innerError(this.index, t2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object t2) {
            if (!this.hasValue) {
                this.hasValue = true;
            }
            this.parent.innerNext(this.index, t2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            DisposableHelper.setOnce(this, d3);
        }
    }

    public ObservableWithLatestFromMany(@NonNull ObservableSource<T> source, @NonNull ObservableSource<?>[] otherArray, @NonNull Function<? super Object[], R> combiner) {
        super(source);
        this.otherArray = otherArray;
        this.otherIterable = null;
        this.combiner = combiner;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super R> observer) {
        int length;
        ObservableSource<?>[] observableSourceArr = this.otherArray;
        if (observableSourceArr == null) {
            observableSourceArr = new ObservableSource[8];
            try {
                length = 0;
                for (ObservableSource<?> observableSource : this.otherIterable) {
                    if (length == observableSourceArr.length) {
                        observableSourceArr = (ObservableSource[]) Arrays.copyOf(observableSourceArr, (length >> 1) + length);
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
            new ObservableMap(this.source, new SingletonArrayFunc()).subscribeActual(observer);
            return;
        }
        WithLatestFromObserver withLatestFromObserver = new WithLatestFromObserver(observer, this.combiner, length);
        observer.onSubscribe(withLatestFromObserver);
        withLatestFromObserver.subscribe(observableSourceArr, length);
        this.source.subscribe(withLatestFromObserver);
    }

    public ObservableWithLatestFromMany(@NonNull ObservableSource<T> source, @NonNull Iterable<? extends ObservableSource<?>> otherIterable, @NonNull Function<? super Object[], R> combiner) {
        super(source);
        this.otherArray = null;
        this.otherIterable = otherIterable;
        this.combiner = combiner;
    }
}
