package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

/* loaded from: classes8.dex */
public final class ObservableOnErrorNext<T> extends AbstractObservableWithUpstream<T, T> {
    final Function<? super Throwable, ? extends ObservableSource<? extends T>> nextSupplier;

    public static final class OnErrorNextObserver<T> implements Observer<T> {
        final SequentialDisposable arbiter = new SequentialDisposable();
        boolean done;
        final Observer<? super T> downstream;
        final Function<? super Throwable, ? extends ObservableSource<? extends T>> nextSupplier;
        boolean once;

        public OnErrorNextObserver(Observer<? super T> actual, Function<? super Throwable, ? extends ObservableSource<? extends T>> nextSupplier) {
            this.downstream = actual;
            this.nextSupplier = nextSupplier;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.once = true;
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            if (this.once) {
                if (this.done) {
                    RxJavaPlugins.onError(t2);
                    return;
                } else {
                    this.downstream.onError(t2);
                    return;
                }
            }
            this.once = true;
            try {
                ObservableSource<? extends T> observableSourceApply = this.nextSupplier.apply(t2);
                if (observableSourceApply != null) {
                    observableSourceApply.subscribe(this);
                    return;
                }
                NullPointerException nullPointerException = new NullPointerException("Observable is null");
                nullPointerException.initCause(t2);
                this.downstream.onError(nullPointerException);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(new CompositeException(t2, th));
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            this.downstream.onNext(t2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            this.arbiter.replace(d3);
        }
    }

    public ObservableOnErrorNext(ObservableSource<T> source, Function<? super Throwable, ? extends ObservableSource<? extends T>> nextSupplier) {
        super(source);
        this.nextSupplier = nextSupplier;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> t2) {
        OnErrorNextObserver onErrorNextObserver = new OnErrorNextObserver(t2, this.nextSupplier);
        t2.onSubscribe(onErrorNextObserver.arbiter);
        this.source.subscribe(onErrorNextObserver);
    }
}
