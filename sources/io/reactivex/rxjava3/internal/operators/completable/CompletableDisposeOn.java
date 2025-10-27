package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes8.dex */
public final class CompletableDisposeOn extends Completable {
    final Scheduler scheduler;
    final CompletableSource source;

    public static final class DisposeOnObserver implements CompletableObserver, Disposable, Runnable {
        volatile boolean disposed;
        final CompletableObserver downstream;
        final Scheduler scheduler;
        Disposable upstream;

        public DisposeOnObserver(CompletableObserver observer, Scheduler scheduler) {
            this.downstream = observer;
            this.scheduler = scheduler;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.disposed = true;
            this.scheduler.scheduleDirect(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.disposed;
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            if (this.disposed) {
                return;
            }
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            if (this.disposed) {
                RxJavaPlugins.onError(e2);
            } else {
                this.downstream.onError(e2);
            }
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(final Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            this.upstream.dispose();
            this.upstream = DisposableHelper.DISPOSED;
        }
    }

    public CompletableDisposeOn(CompletableSource source, Scheduler scheduler) {
        this.source = source;
        this.scheduler = scheduler;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    public void subscribeActual(final CompletableObserver observer) {
        this.source.subscribe(new DisposeOnObserver(observer, this.scheduler));
    }
}
