package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes8.dex */
public final class CompletablePeek extends Completable {
    final Action onAfterTerminate;
    final Action onComplete;
    final Action onDispose;
    final Consumer<? super Throwable> onError;
    final Consumer<? super Disposable> onSubscribe;
    final Action onTerminate;
    final CompletableSource source;

    public final class CompletableObserverImplementation implements CompletableObserver, Disposable {
        final CompletableObserver downstream;
        Disposable upstream;

        public CompletableObserverImplementation(CompletableObserver downstream) {
            this.downstream = downstream;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            try {
                CompletablePeek.this.onDispose.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
            this.upstream.dispose();
        }

        public void doAfter() {
            try {
                CompletablePeek.this.onAfterTerminate.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            if (this.upstream == DisposableHelper.DISPOSED) {
                return;
            }
            try {
                CompletablePeek.this.onComplete.run();
                CompletablePeek.this.onTerminate.run();
                this.downstream.onComplete();
                doAfter();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            if (this.upstream == DisposableHelper.DISPOSED) {
                RxJavaPlugins.onError(e2);
                return;
            }
            try {
                CompletablePeek.this.onError.accept(e2);
                CompletablePeek.this.onTerminate.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                e2 = new CompositeException(e2, th);
            }
            this.downstream.onError(e2);
            doAfter();
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(final Disposable d3) {
            try {
                CompletablePeek.this.onSubscribe.accept(d3);
                if (DisposableHelper.validate(this.upstream, d3)) {
                    this.upstream = d3;
                    this.downstream.onSubscribe(this);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                d3.dispose();
                this.upstream = DisposableHelper.DISPOSED;
                EmptyDisposable.error(th, this.downstream);
            }
        }
    }

    public CompletablePeek(CompletableSource source, Consumer<? super Disposable> onSubscribe, Consumer<? super Throwable> onError, Action onComplete, Action onTerminate, Action onAfterTerminate, Action onDispose) {
        this.source = source;
        this.onSubscribe = onSubscribe;
        this.onError = onError;
        this.onComplete = onComplete;
        this.onTerminate = onTerminate;
        this.onAfterTerminate = onAfterTerminate;
        this.onDispose = onDispose;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    public void subscribeActual(final CompletableObserver observer) {
        this.source.subscribe(new CompletableObserverImplementation(observer));
    }
}
