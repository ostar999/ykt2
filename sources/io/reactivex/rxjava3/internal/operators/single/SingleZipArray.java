package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.operators.single.SingleMap;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class SingleZipArray<T, R> extends Single<R> {
    final SingleSource<? extends T>[] sources;
    final Function<? super Object[], ? extends R> zipper;

    public final class SingletonArrayFunc implements Function<T, R> {
        public SingletonArrayFunc() {
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object, java.lang.Object[]] */
        @Override // io.reactivex.rxjava3.functions.Function
        public R apply(T t2) throws Throwable {
            R rApply = SingleZipArray.this.zipper.apply(new Object[]{t2});
            Objects.requireNonNull(rApply, "The zipper returned a null value");
            return rApply;
        }
    }

    public static final class ZipCoordinator<T, R> extends AtomicInteger implements Disposable {
        private static final long serialVersionUID = -5556924161382950569L;
        final SingleObserver<? super R> downstream;
        final ZipSingleObserver<T>[] observers;
        Object[] values;
        final Function<? super Object[], ? extends R> zipper;

        public ZipCoordinator(SingleObserver<? super R> observer, int n2, Function<? super Object[], ? extends R> zipper) {
            super(n2);
            this.downstream = observer;
            this.zipper = zipper;
            ZipSingleObserver<T>[] zipSingleObserverArr = new ZipSingleObserver[n2];
            for (int i2 = 0; i2 < n2; i2++) {
                zipSingleObserverArr[i2] = new ZipSingleObserver<>(this, i2);
            }
            this.observers = zipSingleObserverArr;
            this.values = new Object[n2];
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (getAndSet(0) > 0) {
                for (ZipSingleObserver<T> zipSingleObserver : this.observers) {
                    zipSingleObserver.dispose();
                }
                this.values = null;
            }
        }

        public void disposeExcept(int index) {
            ZipSingleObserver<T>[] zipSingleObserverArr = this.observers;
            int length = zipSingleObserverArr.length;
            for (int i2 = 0; i2 < index; i2++) {
                zipSingleObserverArr[i2].dispose();
            }
            while (true) {
                index++;
                if (index >= length) {
                    return;
                } else {
                    zipSingleObserverArr[index].dispose();
                }
            }
        }

        public void innerError(Throwable ex, int index) {
            if (getAndSet(0) <= 0) {
                RxJavaPlugins.onError(ex);
                return;
            }
            disposeExcept(index);
            this.values = null;
            this.downstream.onError(ex);
        }

        public void innerSuccess(T value, int index) {
            Object[] objArr = this.values;
            if (objArr != null) {
                objArr[index] = value;
            }
            if (decrementAndGet() == 0) {
                try {
                    R rApply = this.zipper.apply(objArr);
                    Objects.requireNonNull(rApply, "The zipper returned a null value");
                    this.values = null;
                    this.downstream.onSuccess(rApply);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.values = null;
                    this.downstream.onError(th);
                }
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get() <= 0;
        }
    }

    public static final class ZipSingleObserver<T> extends AtomicReference<Disposable> implements SingleObserver<T> {
        private static final long serialVersionUID = 3323743579927613702L;
        final int index;
        final ZipCoordinator<T, ?> parent;

        public ZipSingleObserver(ZipCoordinator<T, ?> parent, int index) {
            this.parent = parent;
            this.index = index;
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            this.parent.innerError(e2, this.index);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            DisposableHelper.setOnce(this, d3);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T value) {
            this.parent.innerSuccess(value, this.index);
        }
    }

    public SingleZipArray(SingleSource<? extends T>[] sources, Function<? super Object[], ? extends R> zipper) {
        this.sources = sources;
        this.zipper = zipper;
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(SingleObserver<? super R> observer) {
        SingleSource<? extends T>[] singleSourceArr = this.sources;
        int length = singleSourceArr.length;
        if (length == 1) {
            singleSourceArr[0].subscribe(new SingleMap.MapSingleObserver(observer, new SingletonArrayFunc()));
            return;
        }
        ZipCoordinator zipCoordinator = new ZipCoordinator(observer, length, this.zipper);
        observer.onSubscribe(zipCoordinator);
        for (int i2 = 0; i2 < length && !zipCoordinator.isDisposed(); i2++) {
            SingleSource<? extends T> singleSource = singleSourceArr[i2];
            if (singleSource == null) {
                zipCoordinator.innerError(new NullPointerException("One of the sources is null"), i2);
                return;
            }
            singleSource.subscribe(zipCoordinator.observers[i2]);
        }
    }
}
