package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeMap;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class MaybeZipArray<T, R> extends Maybe<R> {
    final MaybeSource<? extends T>[] sources;
    final Function<? super Object[], ? extends R> zipper;

    public final class SingletonArrayFunc implements Function<T, R> {
        public SingletonArrayFunc() {
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object, java.lang.Object[]] */
        @Override // io.reactivex.rxjava3.functions.Function
        public R apply(T t2) throws Throwable {
            R rApply = MaybeZipArray.this.zipper.apply(new Object[]{t2});
            Objects.requireNonNull(rApply, "The zipper returned a null value");
            return rApply;
        }
    }

    public static final class ZipCoordinator<T, R> extends AtomicInteger implements Disposable {
        private static final long serialVersionUID = -5556924161382950569L;
        final MaybeObserver<? super R> downstream;
        final ZipMaybeObserver<T>[] observers;
        Object[] values;
        final Function<? super Object[], ? extends R> zipper;

        public ZipCoordinator(MaybeObserver<? super R> observer, int n2, Function<? super Object[], ? extends R> zipper) {
            super(n2);
            this.downstream = observer;
            this.zipper = zipper;
            ZipMaybeObserver<T>[] zipMaybeObserverArr = new ZipMaybeObserver[n2];
            for (int i2 = 0; i2 < n2; i2++) {
                zipMaybeObserverArr[i2] = new ZipMaybeObserver<>(this, i2);
            }
            this.observers = zipMaybeObserverArr;
            this.values = new Object[n2];
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (getAndSet(0) > 0) {
                for (ZipMaybeObserver<T> zipMaybeObserver : this.observers) {
                    zipMaybeObserver.dispose();
                }
                this.values = null;
            }
        }

        public void disposeExcept(int index) {
            ZipMaybeObserver<T>[] zipMaybeObserverArr = this.observers;
            int length = zipMaybeObserverArr.length;
            for (int i2 = 0; i2 < index; i2++) {
                zipMaybeObserverArr[i2].dispose();
            }
            while (true) {
                index++;
                if (index >= length) {
                    return;
                } else {
                    zipMaybeObserverArr[index].dispose();
                }
            }
        }

        public void innerComplete(int index) {
            if (getAndSet(0) > 0) {
                disposeExcept(index);
                this.values = null;
                this.downstream.onComplete();
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

    public static final class ZipMaybeObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T> {
        private static final long serialVersionUID = 3323743579927613702L;
        final int index;
        final ZipCoordinator<T, ?> parent;

        public ZipMaybeObserver(ZipCoordinator<T, ?> parent, int index) {
            this.parent = parent;
            this.index = index;
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            this.parent.innerComplete(this.index);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            this.parent.innerError(e2, this.index);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            DisposableHelper.setOnce(this, d3);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T value) {
            this.parent.innerSuccess(value, this.index);
        }
    }

    public MaybeZipArray(MaybeSource<? extends T>[] sources, Function<? super Object[], ? extends R> zipper) {
        this.sources = sources;
        this.zipper = zipper;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    public void subscribeActual(MaybeObserver<? super R> observer) {
        MaybeSource<? extends T>[] maybeSourceArr = this.sources;
        int length = maybeSourceArr.length;
        if (length == 1) {
            maybeSourceArr[0].subscribe(new MaybeMap.MapMaybeObserver(observer, new SingletonArrayFunc()));
            return;
        }
        ZipCoordinator zipCoordinator = new ZipCoordinator(observer, length, this.zipper);
        observer.onSubscribe(zipCoordinator);
        for (int i2 = 0; i2 < length && !zipCoordinator.isDisposed(); i2++) {
            MaybeSource<? extends T> maybeSource = maybeSourceArr[i2];
            if (maybeSource == null) {
                zipCoordinator.innerError(new NullPointerException("One of the sources is null"), i2);
                return;
            }
            maybeSource.subscribe(zipCoordinator.observers[i2]);
        }
    }
}
