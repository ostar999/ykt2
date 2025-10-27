package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.exceptions.Exceptions;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/* loaded from: classes8.dex */
public final class MaybeFromFuture<T> extends Maybe<T> {
    final Future<? extends T> future;
    final long timeout;
    final TimeUnit unit;

    public MaybeFromFuture(Future<? extends T> future, long j2, TimeUnit timeUnit) {
        this.future = future;
        this.timeout = j2;
        this.unit = timeUnit;
    }

    @Override // io.reactivex.Maybe
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        Disposable disposableEmpty = Disposables.empty();
        maybeObserver.onSubscribe(disposableEmpty);
        if (disposableEmpty.isDisposed()) {
            return;
        }
        try {
            long j2 = this.timeout;
            T t2 = j2 <= 0 ? this.future.get() : this.future.get(j2, this.unit);
            if (disposableEmpty.isDisposed()) {
                return;
            }
            if (t2 == null) {
                maybeObserver.onComplete();
            } else {
                maybeObserver.onSuccess(t2);
            }
        } catch (Throwable th) {
            th = th;
            if (th instanceof ExecutionException) {
                th = th.getCause();
            }
            Exceptions.throwIfFatal(th);
            if (disposableEmpty.isDisposed()) {
                return;
            }
            maybeObserver.onError(th);
        }
    }
}
