package io.reactivex.rxjava3.disposables;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;

/* loaded from: classes8.dex */
final class ActionDisposable extends ReferenceDisposable<Action> {
    private static final long serialVersionUID = -8219729196779211169L;

    public ActionDisposable(Action value) {
        super(value);
    }

    @Override // java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "ActionDisposable(disposed=" + isDisposed() + ", " + get() + ")";
    }

    @Override // io.reactivex.rxjava3.disposables.ReferenceDisposable
    public void onDisposed(@NonNull Action value) {
        try {
            value.run();
        } catch (Throwable th) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }
}
