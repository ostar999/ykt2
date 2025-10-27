package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes8.dex */
public final class CompletableMergeArray extends Completable {
    final CompletableSource[] sources;

    public static final class InnerCompletableObserver extends AtomicInteger implements CompletableObserver, Disposable {
        private static final long serialVersionUID = -8360547806504310570L;
        final CompletableObserver downstream;
        final AtomicBoolean once;
        final CompositeDisposable set;

        public InnerCompletableObserver(CompletableObserver actual, AtomicBoolean once, CompositeDisposable set, int n2) {
            this.downstream = actual;
            this.once = once;
            this.set = set;
            lazySet(n2);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.set.dispose();
            this.once.set(true);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.set.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            if (decrementAndGet() == 0) {
                this.downstream.onComplete();
            }
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            this.set.dispose();
            if (this.once.compareAndSet(false, true)) {
                this.downstream.onError(e2);
            } else {
                RxJavaPlugins.onError(e2);
            }
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            this.set.add(d3);
        }
    }

    public CompletableMergeArray(CompletableSource[] sources) {
        this.sources = sources;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    public void subscribeActual(final CompletableObserver observer) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        InnerCompletableObserver innerCompletableObserver = new InnerCompletableObserver(observer, new AtomicBoolean(), compositeDisposable, this.sources.length + 1);
        observer.onSubscribe(innerCompletableObserver);
        for (CompletableSource completableSource : this.sources) {
            if (compositeDisposable.isDisposed()) {
                return;
            }
            if (completableSource == null) {
                compositeDisposable.dispose();
                innerCompletableObserver.onError(new NullPointerException("A completable source is null"));
                return;
            }
            completableSource.subscribe(innerCompletableObserver);
        }
        innerCompletableObserver.onComplete();
    }
}
