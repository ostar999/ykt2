package io.reactivex.rxjava3.internal.observers;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.disposables.DisposableContainer;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.functions.Functions;
import io.reactivex.rxjava3.observers.LambdaConsumerIntrospection;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
abstract class AbstractDisposableAutoRelease extends AtomicReference<Disposable> implements Disposable, LambdaConsumerIntrospection {
    private static final long serialVersionUID = 8924480688481408726L;
    final AtomicReference<DisposableContainer> composite;
    final Action onComplete;
    final Consumer<? super Throwable> onError;

    public AbstractDisposableAutoRelease(DisposableContainer composite, Consumer<? super Throwable> onError, Action onComplete) {
        this.onError = onError;
        this.onComplete = onComplete;
        this.composite = new AtomicReference<>(composite);
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public final void dispose() {
        DisposableHelper.dispose(this);
        removeSelf();
    }

    @Override // io.reactivex.rxjava3.observers.LambdaConsumerIntrospection
    public final boolean hasCustomOnError() {
        return this.onError != Functions.ON_ERROR_MISSING;
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public final boolean isDisposed() {
        return DisposableHelper.isDisposed(get());
    }

    public final void onComplete() {
        Disposable disposable = get();
        DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
        if (disposable != disposableHelper) {
            lazySet(disposableHelper);
            try {
                this.onComplete.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        }
        removeSelf();
    }

    public final void onError(Throwable t2) {
        Disposable disposable = get();
        DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
        if (disposable != disposableHelper) {
            lazySet(disposableHelper);
            try {
                this.onError.accept(t2);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(new CompositeException(t2, th));
            }
        } else {
            RxJavaPlugins.onError(t2);
        }
        removeSelf();
    }

    public final void onSubscribe(Disposable d3) {
        DisposableHelper.setOnce(this, d3);
    }

    final void removeSelf() {
        DisposableContainer andSet = this.composite.getAndSet(null);
        if (andSet != null) {
            andSet.delete(this);
        }
    }
}
