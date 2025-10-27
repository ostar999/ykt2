package io.reactivex.rxjava3.internal.observers;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.disposables.DisposableContainer;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes8.dex */
public final class DisposableAutoReleaseMultiObserver<T> extends AbstractDisposableAutoRelease implements SingleObserver<T>, MaybeObserver<T>, CompletableObserver {
    private static final long serialVersionUID = 8924480688481408726L;
    final Consumer<? super T> onSuccess;

    public DisposableAutoReleaseMultiObserver(DisposableContainer composite, Consumer<? super T> onSuccess, Consumer<? super Throwable> onError, Action onComplete) {
        super(composite, onError, onComplete);
        this.onSuccess = onSuccess;
    }

    @Override // io.reactivex.rxjava3.core.SingleObserver
    public void onSuccess(T t2) {
        Disposable disposable = get();
        DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
        if (disposable != disposableHelper) {
            lazySet(disposableHelper);
            try {
                this.onSuccess.accept(t2);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        }
        removeSelf();
    }
}
