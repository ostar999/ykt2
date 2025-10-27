package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Predicate;

/* loaded from: classes8.dex */
public final class CompletableOnErrorComplete extends Completable {
    final Predicate<? super Throwable> predicate;
    final CompletableSource source;

    public final class OnError implements CompletableObserver {
        private final CompletableObserver downstream;

        public OnError(CompletableObserver observer) {
            this.downstream = observer;
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            try {
                if (CompletableOnErrorComplete.this.predicate.test(e2)) {
                    this.downstream.onComplete();
                } else {
                    this.downstream.onError(e2);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(new CompositeException(e2, th));
            }
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            this.downstream.onSubscribe(d3);
        }
    }

    public CompletableOnErrorComplete(CompletableSource source, Predicate<? super Throwable> predicate) {
        this.source = source;
        this.predicate = predicate;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    public void subscribeActual(final CompletableObserver observer) {
        this.source.subscribe(new OnError(observer));
    }
}
