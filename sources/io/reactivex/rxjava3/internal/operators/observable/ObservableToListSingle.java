package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.functions.Functions;
import io.reactivex.rxjava3.internal.fuseable.FuseToObservable;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Collection;

/* loaded from: classes8.dex */
public final class ObservableToListSingle<T, U extends Collection<? super T>> extends Single<U> implements FuseToObservable<U> {
    final Supplier<U> collectionSupplier;
    final ObservableSource<T> source;

    public static final class ToListObserver<T, U extends Collection<? super T>> implements Observer<T>, Disposable {
        U collection;
        final SingleObserver<? super U> downstream;
        Disposable upstream;

        public ToListObserver(SingleObserver<? super U> actual, U collection) {
            this.downstream = actual;
            this.collection = collection;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.upstream.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            U u2 = this.collection;
            this.collection = null;
            this.downstream.onSuccess(u2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            this.collection = null;
            this.downstream.onError(t2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            this.collection.add(t2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                this.downstream.onSubscribe(this);
            }
        }
    }

    public ObservableToListSingle(ObservableSource<T> source, final int defaultCapacityHint) {
        this.source = source;
        this.collectionSupplier = Functions.createArrayList(defaultCapacityHint);
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.FuseToObservable
    public Observable<U> fuseToObservable() {
        return RxJavaPlugins.onAssembly(new ObservableToList(this.source, this.collectionSupplier));
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(SingleObserver<? super U> t2) {
        try {
            this.source.subscribe(new ToListObserver(t2, (Collection) ExceptionHelper.nullCheck(this.collectionSupplier.get(), "The collectionSupplier returned a null Collection.")));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, t2);
        }
    }

    public ObservableToListSingle(ObservableSource<T> source, Supplier<U> collectionSupplier) {
        this.source = source;
        this.collectionSupplier = collectionSupplier;
    }
}
