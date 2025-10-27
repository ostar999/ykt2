package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes8.dex */
public final class SingleAmb<T> extends Single<T> {
    private final SingleSource<? extends T>[] sources;
    private final Iterable<? extends SingleSource<? extends T>> sourcesIterable;

    public static final class AmbSingleObserver<T> implements SingleObserver<T> {
        final SingleObserver<? super T> downstream;
        final CompositeDisposable set;
        Disposable upstream;
        final AtomicBoolean winner;

        public AmbSingleObserver(SingleObserver<? super T> observer, CompositeDisposable set, AtomicBoolean winner) {
            this.downstream = observer;
            this.set = set;
            this.winner = winner;
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            if (!this.winner.compareAndSet(false, true)) {
                RxJavaPlugins.onError(e2);
                return;
            }
            this.set.delete(this.upstream);
            this.set.dispose();
            this.downstream.onError(e2);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            this.upstream = d3;
            this.set.add(d3);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T value) {
            if (this.winner.compareAndSet(false, true)) {
                this.set.delete(this.upstream);
                this.set.dispose();
                this.downstream.onSuccess(value);
            }
        }
    }

    public SingleAmb(SingleSource<? extends T>[] sources, Iterable<? extends SingleSource<? extends T>> sourcesIterable) {
        this.sources = sources;
        this.sourcesIterable = sourcesIterable;
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(final SingleObserver<? super T> observer) {
        int length;
        SingleSource<? extends T>[] singleSourceArr = this.sources;
        if (singleSourceArr == null) {
            singleSourceArr = new SingleSource[8];
            try {
                length = 0;
                for (SingleSource<? extends T> singleSource : this.sourcesIterable) {
                    if (singleSource == null) {
                        EmptyDisposable.error(new NullPointerException("One of the sources is null"), observer);
                        return;
                    }
                    if (length == singleSourceArr.length) {
                        SingleSource<? extends T>[] singleSourceArr2 = new SingleSource[(length >> 2) + length];
                        System.arraycopy(singleSourceArr, 0, singleSourceArr2, 0, length);
                        singleSourceArr = singleSourceArr2;
                    }
                    int i2 = length + 1;
                    singleSourceArr[length] = singleSource;
                    length = i2;
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptyDisposable.error(th, observer);
                return;
            }
        } else {
            length = singleSourceArr.length;
        }
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        observer.onSubscribe(compositeDisposable);
        for (int i3 = 0; i3 < length; i3++) {
            SingleSource<? extends T> singleSource2 = singleSourceArr[i3];
            if (compositeDisposable.isDisposed()) {
                return;
            }
            if (singleSource2 == null) {
                compositeDisposable.dispose();
                NullPointerException nullPointerException = new NullPointerException("One of the sources is null");
                if (atomicBoolean.compareAndSet(false, true)) {
                    observer.onError(nullPointerException);
                    return;
                } else {
                    RxJavaPlugins.onError(nullPointerException);
                    return;
                }
            }
            singleSource2.subscribe(new AmbSingleObserver(observer, compositeDisposable, atomicBoolean));
        }
    }
}
