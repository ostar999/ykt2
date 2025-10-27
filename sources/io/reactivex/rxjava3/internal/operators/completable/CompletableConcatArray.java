package io.reactivex.rxjava3.internal.operators.completable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes8.dex */
public final class CompletableConcatArray extends Completable {
    final CompletableSource[] sources;

    public static final class ConcatInnerObserver extends AtomicInteger implements CompletableObserver {
        private static final long serialVersionUID = -7965400327305809232L;
        final CompletableObserver downstream;
        int index;
        final SequentialDisposable sd = new SequentialDisposable();
        final CompletableSource[] sources;

        public ConcatInnerObserver(CompletableObserver actual, CompletableSource[] sources) {
            this.downstream = actual;
            this.sources = sources;
        }

        public void next() {
            if (!this.sd.isDisposed() && getAndIncrement() == 0) {
                CompletableSource[] completableSourceArr = this.sources;
                while (!this.sd.isDisposed()) {
                    int i2 = this.index;
                    this.index = i2 + 1;
                    if (i2 == completableSourceArr.length) {
                        this.downstream.onComplete();
                        return;
                    } else {
                        completableSourceArr[i2].subscribe(this);
                        if (decrementAndGet() == 0) {
                            return;
                        }
                    }
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            next();
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            this.downstream.onError(e2);
        }

        @Override // io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            this.sd.replace(d3);
        }
    }

    public CompletableConcatArray(CompletableSource[] sources) {
        this.sources = sources;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    public void subscribeActual(CompletableObserver observer) {
        ConcatInnerObserver concatInnerObserver = new ConcatInnerObserver(observer, this.sources);
        observer.onSubscribe(concatInnerObserver.sd);
        concatInnerObserver.next();
    }
}
