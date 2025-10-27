package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeEmitter;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Cancellable;
import io.reactivex.rxjava3.internal.disposables.CancellableDisposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class MaybeCreate<T> extends Maybe<T> {
    final MaybeOnSubscribe<T> source;

    public static final class Emitter<T> extends AtomicReference<Disposable> implements MaybeEmitter<T>, Disposable {
        private static final long serialVersionUID = -2467358622224974244L;
        final MaybeObserver<? super T> downstream;

        public Emitter(MaybeObserver<? super T> downstream) {
            this.downstream = downstream;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.rxjava3.core.MaybeEmitter, io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override // io.reactivex.rxjava3.core.MaybeEmitter
        public void onComplete() {
            Disposable andSet;
            Disposable disposable = get();
            DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
            if (disposable == disposableHelper || (andSet = getAndSet(disposableHelper)) == disposableHelper) {
                return;
            }
            try {
                this.downstream.onComplete();
            } finally {
                if (andSet != null) {
                    andSet.dispose();
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.MaybeEmitter
        public void onError(Throwable t2) {
            if (tryOnError(t2)) {
                return;
            }
            RxJavaPlugins.onError(t2);
        }

        @Override // io.reactivex.rxjava3.core.MaybeEmitter
        public void onSuccess(T value) {
            Disposable andSet;
            Disposable disposable = get();
            DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
            if (disposable == disposableHelper || (andSet = getAndSet(disposableHelper)) == disposableHelper) {
                return;
            }
            try {
                if (value == null) {
                    this.downstream.onError(ExceptionHelper.createNullPointerException("onSuccess called with a null value."));
                } else {
                    this.downstream.onSuccess(value);
                }
                if (andSet != null) {
                    andSet.dispose();
                }
            } catch (Throwable th) {
                if (andSet != null) {
                    andSet.dispose();
                }
                throw th;
            }
        }

        @Override // io.reactivex.rxjava3.core.MaybeEmitter
        public void setCancellable(Cancellable c3) {
            setDisposable(new CancellableDisposable(c3));
        }

        @Override // io.reactivex.rxjava3.core.MaybeEmitter
        public void setDisposable(Disposable d3) {
            DisposableHelper.set(this, d3);
        }

        @Override // java.util.concurrent.atomic.AtomicReference
        public String toString() {
            return String.format("%s{%s}", Emitter.class.getSimpleName(), super.toString());
        }

        @Override // io.reactivex.rxjava3.core.MaybeEmitter
        public boolean tryOnError(Throwable t2) {
            Disposable andSet;
            if (t2 == null) {
                t2 = ExceptionHelper.createNullPointerException("onError called with a null Throwable.");
            }
            Disposable disposable = get();
            DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
            if (disposable == disposableHelper || (andSet = getAndSet(disposableHelper)) == disposableHelper) {
                return false;
            }
            try {
                this.downstream.onError(t2);
            } finally {
                if (andSet != null) {
                    andSet.dispose();
                }
            }
        }
    }

    public MaybeCreate(MaybeOnSubscribe<T> source) {
        this.source = source;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    public void subscribeActual(MaybeObserver<? super T> observer) {
        Emitter emitter = new Emitter(observer);
        observer.onSubscribe(emitter);
        try {
            this.source.subscribe(emitter);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            emitter.onError(th);
        }
    }
}
