package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes8.dex */
public final class ObservableZipIterable<T, U, V> extends Observable<V> {
    final Iterable<U> other;
    final Observable<? extends T> source;
    final BiFunction<? super T, ? super U, ? extends V> zipper;

    public static final class ZipIterableObserver<T, U, V> implements Observer<T>, Disposable {
        boolean done;
        final Observer<? super V> downstream;
        final Iterator<U> iterator;
        Disposable upstream;
        final BiFunction<? super T, ? super U, ? extends V> zipper;

        public ZipIterableObserver(Observer<? super V> actual, Iterator<U> iterator, BiFunction<? super T, ? super U, ? extends V> zipper) {
            this.downstream = actual;
            this.iterator = iterator;
            this.zipper = zipper;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.upstream.dispose();
        }

        public void fail(Throwable e2) {
            this.done = true;
            this.upstream.dispose();
            this.downstream.onError(e2);
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
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            if (this.done) {
                RxJavaPlugins.onError(t2);
            } else {
                this.done = true;
                this.downstream.onError(t2);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            try {
                U next = this.iterator.next();
                Objects.requireNonNull(next, "The iterator returned a null value");
                try {
                    V vApply = this.zipper.apply(t2, next);
                    Objects.requireNonNull(vApply, "The zipper function returned a null value");
                    this.downstream.onNext(vApply);
                    try {
                        if (this.iterator.hasNext()) {
                            return;
                        }
                        this.done = true;
                        this.upstream.dispose();
                        this.downstream.onComplete();
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        fail(th);
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    fail(th2);
                }
            } catch (Throwable th3) {
                Exceptions.throwIfFatal(th3);
                fail(th3);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                this.downstream.onSubscribe(this);
            }
        }
    }

    public ObservableZipIterable(Observable<? extends T> source, Iterable<U> other, BiFunction<? super T, ? super U, ? extends V> zipper) {
        this.source = source;
        this.other = other;
        this.zipper = zipper;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super V> t2) {
        try {
            Iterator<U> it = this.other.iterator();
            Objects.requireNonNull(it, "The iterator returned by other is null");
            Iterator<U> it2 = it;
            try {
                if (it2.hasNext()) {
                    this.source.subscribe(new ZipIterableObserver(t2, it2, this.zipper));
                } else {
                    EmptyDisposable.complete(t2);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptyDisposable.error(th, t2);
            }
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            EmptyDisposable.error(th2, t2);
        }
    }
}
