package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeOperator;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import java.util.Objects;

/* loaded from: classes8.dex */
public final class MaybeLift<T, R> extends AbstractMaybeWithUpstream<T, R> {
    final MaybeOperator<? extends R, ? super T> operator;

    public MaybeLift(MaybeSource<T> source, MaybeOperator<? extends R, ? super T> operator) {
        super(source);
        this.operator = operator;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    public void subscribeActual(MaybeObserver<? super R> observer) {
        try {
            MaybeObserver<? super Object> maybeObserverApply = this.operator.apply(observer);
            Objects.requireNonNull(maybeObserverApply, "The operator returned a null MaybeObserver");
            this.source.subscribe(maybeObserverApply);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, observer);
        }
    }
}
