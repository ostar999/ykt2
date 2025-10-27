package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes8.dex */
public final class CompletableMergeArrayDelayError extends Completable {
    final CompletableSource[] sources;

    public static final class MergeInnerCompletableObserver implements CompletableObserver {
        final CompletableObserver downstream;
        final AtomicThrowable errors;
        final CompositeDisposable set;
        final AtomicInteger wip;

        public MergeInnerCompletableObserver(CompletableObserver observer, CompositeDisposable set, AtomicThrowable error, AtomicInteger wip) {
            this.downstream = observer;
            this.set = set;
            this.errors = error;
            this.wip = wip;
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            tryTerminate();
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            if (this.errors.tryAddThrowableOrReport(e2)) {
                tryTerminate();
            }
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            this.set.add(d3);
        }

        public void tryTerminate() {
            if (this.wip.decrementAndGet() == 0) {
                this.errors.tryTerminateConsumer(this.downstream);
            }
        }
    }

    public static final class TryTerminateAndReportDisposable implements Disposable {
        final AtomicThrowable errors;

        public TryTerminateAndReportDisposable(AtomicThrowable errors) {
            this.errors = errors;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.errors.tryTerminateAndReport();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.errors.isTerminated();
        }
    }

    public CompletableMergeArrayDelayError(CompletableSource[] sources) {
        this.sources = sources;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    public void subscribeActual(final CompletableObserver observer) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AtomicInteger atomicInteger = new AtomicInteger(this.sources.length + 1);
        AtomicThrowable atomicThrowable = new AtomicThrowable();
        compositeDisposable.add(new TryTerminateAndReportDisposable(atomicThrowable));
        observer.onSubscribe(compositeDisposable);
        for (CompletableSource completableSource : this.sources) {
            if (compositeDisposable.isDisposed()) {
                return;
            }
            if (completableSource == null) {
                atomicThrowable.tryAddThrowableOrReport(new NullPointerException("A completable source is null"));
                atomicInteger.decrementAndGet();
            } else {
                completableSource.subscribe(new MergeInnerCompletableObserver(observer, compositeDisposable, atomicThrowable, atomicInteger));
            }
        }
        if (atomicInteger.decrementAndGet() == 0) {
            atomicThrowable.tryTerminateConsumer(observer);
        }
    }
}
