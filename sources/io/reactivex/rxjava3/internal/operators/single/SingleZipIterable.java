package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.operators.single.SingleMap;
import io.reactivex.rxjava3.internal.operators.single.SingleZipArray;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

/* loaded from: classes8.dex */
public final class SingleZipIterable<T, R> extends Single<R> {
    final Iterable<? extends SingleSource<? extends T>> sources;
    final Function<? super Object[], ? extends R> zipper;

    public final class SingletonArrayFunc implements Function<T, R> {
        public SingletonArrayFunc() {
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object, java.lang.Object[]] */
        @Override // io.reactivex.rxjava3.functions.Function
        public R apply(T t2) throws Throwable {
            R rApply = SingleZipIterable.this.zipper.apply(new Object[]{t2});
            Objects.requireNonNull(rApply, "The zipper returned a null value");
            return rApply;
        }
    }

    public SingleZipIterable(Iterable<? extends SingleSource<? extends T>> sources, Function<? super Object[], ? extends R> zipper) {
        this.sources = sources;
        this.zipper = zipper;
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(SingleObserver<? super R> observer) {
        SingleSource[] singleSourceArr = new SingleSource[8];
        try {
            int i2 = 0;
            for (SingleSource<? extends T> singleSource : this.sources) {
                if (singleSource == null) {
                    EmptyDisposable.error(new NullPointerException("One of the sources is null"), observer);
                    return;
                }
                if (i2 == singleSourceArr.length) {
                    singleSourceArr = (SingleSource[]) Arrays.copyOf(singleSourceArr, (i2 >> 2) + i2);
                }
                int i3 = i2 + 1;
                singleSourceArr[i2] = singleSource;
                i2 = i3;
            }
            if (i2 == 0) {
                EmptyDisposable.error(new NoSuchElementException(), observer);
                return;
            }
            if (i2 == 1) {
                singleSourceArr[0].subscribe(new SingleMap.MapSingleObserver(observer, new SingletonArrayFunc()));
                return;
            }
            SingleZipArray.ZipCoordinator zipCoordinator = new SingleZipArray.ZipCoordinator(observer, i2, this.zipper);
            observer.onSubscribe(zipCoordinator);
            for (int i4 = 0; i4 < i2 && !zipCoordinator.isDisposed(); i4++) {
                singleSourceArr[i4].subscribe(zipCoordinator.observers[i4]);
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, observer);
        }
    }
}
