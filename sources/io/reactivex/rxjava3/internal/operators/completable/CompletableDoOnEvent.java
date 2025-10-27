package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Consumer;

/* loaded from: classes8.dex */
public final class CompletableDoOnEvent extends Completable {
    final Consumer<? super Throwable> onEvent;
    final CompletableSource source;

    public final class DoOnEvent implements CompletableObserver {
        private final CompletableObserver observer;

        public DoOnEvent(CompletableObserver observer) {
            this.observer = observer;
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            try {
                CompletableDoOnEvent.this.onEvent.accept(null);
                this.observer.onComplete();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.observer.onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            try {
                CompletableDoOnEvent.this.onEvent.accept(e2);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                e2 = new CompositeException(e2, th);
            }
            this.observer.onError(e2);
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(final Disposable d3) {
            this.observer.onSubscribe(d3);
        }
    }

    public CompletableDoOnEvent(final CompletableSource source, final Consumer<? super Throwable> onEvent) {
        this.source = source;
        this.onEvent = onEvent;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    public void subscribeActual(final CompletableObserver observer) {
        this.source.subscribe(new DoOnEvent(observer));
    }
}
