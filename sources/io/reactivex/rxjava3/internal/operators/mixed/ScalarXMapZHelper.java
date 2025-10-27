package io.reactivex.rxjava3.internal.operators.mixed;

import MTT.ThirdAppInfoNew;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.operators.maybe.MaybeToObservable;
import io.reactivex.rxjava3.internal.operators.single.SingleToObservable;
import java.util.Objects;

/* loaded from: classes8.dex */
final class ScalarXMapZHelper {
    private ScalarXMapZHelper() {
        throw new IllegalStateException("No instances!");
    }

    public static <T> boolean tryAsCompletable(Object obj, Function<? super T, ? extends CompletableSource> function, CompletableObserver completableObserver) {
        CompletableSource completableSource;
        if (!(obj instanceof Supplier)) {
            return false;
        }
        try {
            ThirdAppInfoNew thirdAppInfoNew = (Object) ((Supplier) obj).get();
            if (thirdAppInfoNew != null) {
                CompletableSource completableSourceApply = function.apply(thirdAppInfoNew);
                Objects.requireNonNull(completableSourceApply, "The mapper returned a null CompletableSource");
                completableSource = completableSourceApply;
            } else {
                completableSource = null;
            }
            if (completableSource == null) {
                EmptyDisposable.complete(completableObserver);
            } else {
                completableSource.subscribe(completableObserver);
            }
            return true;
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, completableObserver);
            return true;
        }
    }

    public static <T, R> boolean tryAsMaybe(Object obj, Function<? super T, ? extends MaybeSource<? extends R>> function, Observer<? super R> observer) {
        MaybeSource<? extends R> maybeSource;
        if (!(obj instanceof Supplier)) {
            return false;
        }
        try {
            ThirdAppInfoNew thirdAppInfoNew = (Object) ((Supplier) obj).get();
            if (thirdAppInfoNew != null) {
                MaybeSource<? extends R> maybeSourceApply = function.apply(thirdAppInfoNew);
                Objects.requireNonNull(maybeSourceApply, "The mapper returned a null MaybeSource");
                maybeSource = maybeSourceApply;
            } else {
                maybeSource = null;
            }
            if (maybeSource == null) {
                EmptyDisposable.complete(observer);
            } else {
                maybeSource.subscribe(MaybeToObservable.create(observer));
            }
            return true;
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, observer);
            return true;
        }
    }

    public static <T, R> boolean tryAsSingle(Object obj, Function<? super T, ? extends SingleSource<? extends R>> function, Observer<? super R> observer) {
        SingleSource<? extends R> singleSource;
        if (!(obj instanceof Supplier)) {
            return false;
        }
        try {
            ThirdAppInfoNew thirdAppInfoNew = (Object) ((Supplier) obj).get();
            if (thirdAppInfoNew != null) {
                SingleSource<? extends R> singleSourceApply = function.apply(thirdAppInfoNew);
                Objects.requireNonNull(singleSourceApply, "The mapper returned a null SingleSource");
                singleSource = singleSourceApply;
            } else {
                singleSource = null;
            }
            if (singleSource == null) {
                EmptyDisposable.complete(observer);
            } else {
                singleSource.subscribe(SingleToObservable.create(observer));
            }
            return true;
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, observer);
            return true;
        }
    }
}
