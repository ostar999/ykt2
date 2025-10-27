package io.reactivex.rxjava3.exceptions;

import io.reactivex.rxjava3.annotations.NonNull;

/* loaded from: classes8.dex */
public final class OnErrorNotImplementedException extends RuntimeException {
    private static final long serialVersionUID = -6298857009889503852L;

    public OnErrorNotImplementedException(String message, @NonNull Throwable e2) {
        super(message, e2 == null ? new NullPointerException() : e2);
    }

    public OnErrorNotImplementedException(@NonNull Throwable e2) {
        this("The exception was not handled due to missing onError handler in the subscribe() method call. Further reading: https://github.com/ReactiveX/RxJava/wiki/Error-Handling | " + e2, e2);
    }
}
