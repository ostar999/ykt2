package io.reactivex.internal.operators.observable;

import androidx.camera.view.j;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class ObservableDebounce<T, U> extends AbstractObservableWithUpstream<T, T> {
    final Function<? super T, ? extends ObservableSource<U>> debounceSelector;

    public static final class DebounceObserver<T, U> implements Observer<T>, Disposable {
        final Function<? super T, ? extends ObservableSource<U>> debounceSelector;
        final AtomicReference<Disposable> debouncer = new AtomicReference<>();
        boolean done;
        final Observer<? super T> downstream;
        volatile long index;
        Disposable upstream;

        public static final class DebounceInnerObserver<T, U> extends DisposableObserver<U> {
            boolean done;
            final long index;
            final AtomicBoolean once = new AtomicBoolean();
            final DebounceObserver<T, U> parent;
            final T value;

            public DebounceInnerObserver(DebounceObserver<T, U> debounceObserver, long j2, T t2) {
                this.parent = debounceObserver;
                this.index = j2;
                this.value = t2;
            }

            public void emit() {
                if (this.once.compareAndSet(false, true)) {
                    this.parent.emit(this.index, this.value);
                }
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                if (this.done) {
                    return;
                }
                this.done = true;
                emit();
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                if (this.done) {
                    RxJavaPlugins.onError(th);
                } else {
                    this.done = true;
                    this.parent.onError(th);
                }
            }

            @Override // io.reactivex.Observer
            public void onNext(U u2) {
                if (this.done) {
                    return;
                }
                this.done = true;
                dispose();
                emit();
            }
        }

        public DebounceObserver(Observer<? super T> observer, Function<? super T, ? extends ObservableSource<U>> function) {
            this.downstream = observer;
            this.debounceSelector = function;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.upstream.dispose();
            DisposableHelper.dispose(this.debouncer);
        }

        public void emit(long j2, T t2) {
            if (j2 == this.index) {
                this.downstream.onNext(t2);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            Disposable disposable = this.debouncer.get();
            if (disposable != DisposableHelper.DISPOSED) {
                ((DebounceInnerObserver) disposable).emit();
                DisposableHelper.dispose(this.debouncer);
                this.downstream.onComplete();
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            DisposableHelper.dispose(this.debouncer);
            this.downstream.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            long j2 = this.index + 1;
            this.index = j2;
            Disposable disposable = this.debouncer.get();
            if (disposable != null) {
                disposable.dispose();
            }
            try {
                ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.debounceSelector.apply(t2), "The ObservableSource supplied is null");
                DebounceInnerObserver debounceInnerObserver = new DebounceInnerObserver(this, j2, t2);
                if (j.a(this.debouncer, disposable, debounceInnerObserver)) {
                    observableSource.subscribe(debounceInnerObserver);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                dispose();
                this.downstream.onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }
    }

    public ObservableDebounce(ObservableSource<T> observableSource, Function<? super T, ? extends ObservableSource<U>> function) {
        super(observableSource);
        this.debounceSelector = function;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new DebounceObserver(new SerializedObserver(observer), this.debounceSelector));
    }
}
