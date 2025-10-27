package io.reactivex.rxjava3.observers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes8.dex */
public final class SafeObserver<T> implements Observer<T>, Disposable {
    boolean done;
    final Observer<? super T> downstream;
    Disposable upstream;

    public SafeObserver(@NonNull Observer<? super T> downstream) {
        this.downstream = downstream;
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public void dispose() {
        this.upstream.dispose();
    }

    @Override // io.reactivex.rxjava3.disposables.Disposable
    public boolean isDisposed() {
        return this.upstream.isDisposed();
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onComplete() {
        if (this.done) {
            return;
        }
        this.done = true;
        if (this.upstream == null) {
            onCompleteNoSubscription();
            return;
        }
        try {
            this.downstream.onComplete();
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            RxJavaPlugins.onError(th);
        }
    }

    public void onCompleteNoSubscription() {
        NullPointerException nullPointerException = new NullPointerException("Subscription not set!");
        try {
            this.downstream.onSubscribe(EmptyDisposable.INSTANCE);
            try {
                this.downstream.onError(nullPointerException);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(new CompositeException(nullPointerException, th));
            }
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            RxJavaPlugins.onError(new CompositeException(nullPointerException, th2));
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onError(@NonNull Throwable t2) {
        if (this.done) {
            RxJavaPlugins.onError(t2);
            return;
        }
        this.done = true;
        if (this.upstream != null) {
            if (t2 == null) {
                t2 = ExceptionHelper.createNullPointerException("onError called with a null Throwable.");
            }
            try {
                this.downstream.onError(t2);
                return;
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(new CompositeException(t2, th));
                return;
            }
        }
        NullPointerException nullPointerException = new NullPointerException("Subscription not set!");
        try {
            this.downstream.onSubscribe(EmptyDisposable.INSTANCE);
            try {
                this.downstream.onError(new CompositeException(t2, nullPointerException));
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                RxJavaPlugins.onError(new CompositeException(t2, nullPointerException, th2));
            }
        } catch (Throwable th3) {
            Exceptions.throwIfFatal(th3);
            RxJavaPlugins.onError(new CompositeException(t2, nullPointerException, th3));
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(@NonNull T t2) {
        if (this.done) {
            return;
        }
        if (this.upstream == null) {
            onNextNoSubscription();
            return;
        }
        if (t2 == null) {
            NullPointerException nullPointerExceptionCreateNullPointerException = ExceptionHelper.createNullPointerException("onNext called with a null value.");
            try {
                this.upstream.dispose();
                onError(nullPointerExceptionCreateNullPointerException);
                return;
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                onError(new CompositeException(nullPointerExceptionCreateNullPointerException, th));
                return;
            }
        }
        try {
            this.downstream.onNext(t2);
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            try {
                this.upstream.dispose();
                onError(th2);
            } catch (Throwable th3) {
                Exceptions.throwIfFatal(th3);
                onError(new CompositeException(th2, th3));
            }
        }
    }

    public void onNextNoSubscription() {
        this.done = true;
        NullPointerException nullPointerException = new NullPointerException("Subscription not set!");
        try {
            this.downstream.onSubscribe(EmptyDisposable.INSTANCE);
            try {
                this.downstream.onError(nullPointerException);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(new CompositeException(nullPointerException, th));
            }
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            RxJavaPlugins.onError(new CompositeException(nullPointerException, th2));
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onSubscribe(@NonNull Disposable d3) {
        if (DisposableHelper.validate(this.upstream, d3)) {
            this.upstream = d3;
            try {
                this.downstream.onSubscribe(this);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.done = true;
                try {
                    d3.dispose();
                    RxJavaPlugins.onError(th);
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    RxJavaPlugins.onError(new CompositeException(th, th2));
                }
            }
        }
    }
}
