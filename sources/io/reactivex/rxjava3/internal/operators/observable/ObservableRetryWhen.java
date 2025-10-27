package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.HalfSerializer;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class ObservableRetryWhen<T> extends AbstractObservableWithUpstream<T, T> {
    final Function<? super Observable<Throwable>, ? extends ObservableSource<?>> handler;

    public static final class RepeatWhenObserver<T> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = 802743776666017014L;
        volatile boolean active;
        final Observer<? super T> downstream;
        final Subject<Throwable> signaller;
        final ObservableSource<T> source;
        final AtomicInteger wip = new AtomicInteger();
        final AtomicThrowable error = new AtomicThrowable();
        final RepeatWhenObserver<T>.InnerRepeatObserver inner = new InnerRepeatObserver();
        final AtomicReference<Disposable> upstream = new AtomicReference<>();

        public final class InnerRepeatObserver extends AtomicReference<Disposable> implements Observer<Object> {
            private static final long serialVersionUID = 3254781284376480842L;

            public InnerRepeatObserver() {
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onComplete() {
                RepeatWhenObserver.this.innerComplete();
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onError(Throwable e2) {
                RepeatWhenObserver.this.innerError(e2);
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onNext(Object t2) {
                RepeatWhenObserver.this.innerNext();
            }

            @Override // io.reactivex.rxjava3.core.Observer
            public void onSubscribe(Disposable d3) {
                DisposableHelper.setOnce(this, d3);
            }
        }

        public RepeatWhenObserver(Observer<? super T> actual, Subject<Throwable> signaller, ObservableSource<T> source) {
            this.downstream = actual;
            this.signaller = signaller;
            this.source = source;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this.upstream);
            DisposableHelper.dispose(this.inner);
        }

        public void innerComplete() {
            DisposableHelper.dispose(this.upstream);
            HalfSerializer.onComplete(this.downstream, this, this.error);
        }

        public void innerError(Throwable ex) {
            DisposableHelper.dispose(this.upstream);
            HalfSerializer.onError(this.downstream, ex, this, this.error);
        }

        public void innerNext() {
            subscribeNext();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(this.upstream.get());
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            DisposableHelper.dispose(this.inner);
            HalfSerializer.onComplete(this.downstream, this, this.error);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable e2) {
            DisposableHelper.replace(this.upstream, null);
            this.active = false;
            this.signaller.onNext(e2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            HalfSerializer.onNext(this.downstream, t2, this, this.error);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            DisposableHelper.replace(this.upstream, d3);
        }

        public void subscribeNext() {
            if (this.wip.getAndIncrement() == 0) {
                while (!isDisposed()) {
                    if (!this.active) {
                        this.active = true;
                        this.source.subscribe(this);
                    }
                    if (this.wip.decrementAndGet() == 0) {
                        return;
                    }
                }
            }
        }
    }

    public ObservableRetryWhen(ObservableSource<T> source, Function<? super Observable<Throwable>, ? extends ObservableSource<?>> handler) {
        super(source);
        this.handler = handler;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> observer) {
        Subject<T> serialized = PublishSubject.create().toSerialized();
        try {
            ObservableSource<?> observableSourceApply = this.handler.apply(serialized);
            Objects.requireNonNull(observableSourceApply, "The handler returned a null ObservableSource");
            ObservableSource<?> observableSource = observableSourceApply;
            RepeatWhenObserver repeatWhenObserver = new RepeatWhenObserver(observer, serialized, this.source);
            observer.onSubscribe(repeatWhenObserver);
            observableSource.subscribe(repeatWhenObserver.inner);
            repeatWhenObserver.subscribeNext();
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, observer);
        }
    }
}
