package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.disposables.b;
import io.reactivex.rxjava3.exceptions.Exceptions;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/* loaded from: classes8.dex */
public final class MaybeFromFuture<T> extends Maybe<T> {
    final Future<? extends T> future;
    final long timeout;
    final TimeUnit unit;

    public MaybeFromFuture(Future<? extends T> future, long timeout, TimeUnit unit) {
        this.future = future;
        this.timeout = timeout;
        this.unit = unit;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        Disposable disposableB = b.b();
        maybeObserver.onSubscribe(disposableB);
        if (disposableB.isDisposed()) {
            return;
        }
        try {
            long j2 = this.timeout;
            T t2 = j2 <= 0 ? this.future.get() : this.future.get(j2, this.unit);
            if (disposableB.isDisposed()) {
                return;
            }
            if (t2 == null) {
                maybeObserver.onComplete();
            } else {
                maybeObserver.onSuccess(t2);
            }
        } catch (Throwable th) {
            th = th;
            Exceptions.throwIfFatal(th);
            if (th instanceof ExecutionException) {
                th = th.getCause();
            }
            Exceptions.throwIfFatal(th);
            if (disposableB.isDisposed()) {
                return;
            }
            maybeObserver.onError(th);
        }
    }
}
