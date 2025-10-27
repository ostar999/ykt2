package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.observers.SerializedObserver;
import java.util.concurrent.TimeUnit;

/* loaded from: classes8.dex */
public final class ObservableDelay<T> extends AbstractObservableWithUpstream<T, T> {
    final long delay;
    final boolean delayError;
    final Scheduler scheduler;
    final TimeUnit unit;

    public static final class DelayObserver<T> implements Observer<T>, Disposable {
        final long delay;
        final boolean delayError;
        final Observer<? super T> downstream;
        final TimeUnit unit;
        Disposable upstream;

        /* renamed from: w, reason: collision with root package name */
        final Scheduler.Worker f27356w;

        public final class OnComplete implements Runnable {
            public OnComplete() {
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    DelayObserver.this.downstream.onComplete();
                } finally {
                    DelayObserver.this.f27356w.dispose();
                }
            }
        }

        public final class OnError implements Runnable {
            private final Throwable throwable;

            public OnError(Throwable throwable) {
                this.throwable = throwable;
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    DelayObserver.this.downstream.onError(this.throwable);
                } finally {
                    DelayObserver.this.f27356w.dispose();
                }
            }
        }

        public final class OnNext implements Runnable {

            /* renamed from: t, reason: collision with root package name */
            private final T f27357t;

            public OnNext(T t2) {
                this.f27357t = t2;
            }

            @Override // java.lang.Runnable
            public void run() {
                DelayObserver.this.downstream.onNext(this.f27357t);
            }
        }

        public DelayObserver(Observer<? super T> actual, long delay, TimeUnit unit, Scheduler.Worker w2, boolean delayError) {
            this.downstream = actual;
            this.delay = delay;
            this.unit = unit;
            this.f27356w = w2;
            this.delayError = delayError;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.upstream.dispose();
            this.f27356w.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.f27356w.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            this.f27356w.schedule(new OnComplete(), this.delay, this.unit);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(final Throwable t2) {
            this.f27356w.schedule(new OnError(t2), this.delayError ? this.delay : 0L, this.unit);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(final T t2) {
            this.f27356w.schedule(new OnNext(t2), this.delay, this.unit);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                this.downstream.onSubscribe(this);
            }
        }
    }

    public ObservableDelay(ObservableSource<T> source, long delay, TimeUnit unit, Scheduler scheduler, boolean delayError) {
        super(source);
        this.delay = delay;
        this.unit = unit;
        this.scheduler = scheduler;
        this.delayError = delayError;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super T> t2) {
        this.source.subscribe(new DelayObserver(this.delayError ? t2 : new SerializedObserver(t2), this.delay, this.unit, this.scheduler.createWorker(), this.delayError));
    }
}
