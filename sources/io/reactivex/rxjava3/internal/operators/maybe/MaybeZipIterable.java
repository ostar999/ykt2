package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeMap;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeZipArray;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes8.dex */
public final class MaybeZipIterable<T, R> extends Maybe<R> {
    final Iterable<? extends MaybeSource<? extends T>> sources;
    final Function<? super Object[], ? extends R> zipper;

    public final class SingletonArrayFunc implements Function<T, R> {
        public SingletonArrayFunc() {
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object, java.lang.Object[]] */
        @Override // io.reactivex.rxjava3.functions.Function
        public R apply(T t2) throws Throwable {
            R rApply = MaybeZipIterable.this.zipper.apply(new Object[]{t2});
            Objects.requireNonNull(rApply, "The zipper returned a null value");
            return rApply;
        }
    }

    public MaybeZipIterable(Iterable<? extends MaybeSource<? extends T>> sources, Function<? super Object[], ? extends R> zipper) {
        this.sources = sources;
        this.zipper = zipper;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    public void subscribeActual(MaybeObserver<? super R> observer) {
        MaybeSource[] maybeSourceArr = new MaybeSource[8];
        try {
            int i2 = 0;
            for (MaybeSource<? extends T> maybeSource : this.sources) {
                if (maybeSource == null) {
                    EmptyDisposable.error(new NullPointerException("One of the sources is null"), observer);
                    return;
                }
                if (i2 == maybeSourceArr.length) {
                    maybeSourceArr = (MaybeSource[]) Arrays.copyOf(maybeSourceArr, (i2 >> 2) + i2);
                }
                int i3 = i2 + 1;
                maybeSourceArr[i2] = maybeSource;
                i2 = i3;
            }
            if (i2 == 0) {
                EmptyDisposable.complete(observer);
                return;
            }
            if (i2 == 1) {
                maybeSourceArr[0].subscribe(new MaybeMap.MapMaybeObserver(observer, new SingletonArrayFunc()));
                return;
            }
            MaybeZipArray.ZipCoordinator zipCoordinator = new MaybeZipArray.ZipCoordinator(observer, i2, this.zipper);
            observer.onSubscribe(zipCoordinator);
            for (int i4 = 0; i4 < i2 && !zipCoordinator.isDisposed(); i4++) {
                maybeSourceArr[i4].subscribe(zipCoordinator.observers[i4]);
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, observer);
        }
    }
}
