package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.SerializedObserver;
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
        final Scheduler.Worker f27306w;

        public final class OnComplete implements Runnable {
            public OnComplete() {
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    DelayObserver.this.downstream.onComplete();
                } finally {
                    DelayObserver.this.f27306w.dispose();
                }
            }
        }

        public final class OnError implements Runnable {
            private final Throwable throwable;

            public OnError(Throwable th) {
                this.throwable = th;
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    DelayObserver.this.downstream.onError(this.throwable);
                } finally {
                    DelayObserver.this.f27306w.dispose();
                }
            }
        }

        public final class OnNext implements Runnable {

            /* renamed from: t, reason: collision with root package name */
            private final T f27307t;

            public OnNext(T t2) {
                this.f27307t = t2;
            }

            @Override // java.lang.Runnable
            public void run() {
                DelayObserver.this.downstream.onNext(this.f27307t);
            }
        }

        public DelayObserver(Observer<? super T> observer, long j2, TimeUnit timeUnit, Scheduler.Worker worker, boolean z2) {
            this.downstream = observer;
            this.delay = j2;
            this.unit = timeUnit;
            this.f27306w = worker;
            this.delayError = z2;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.upstream.dispose();
            this.f27306w.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.f27306w.isDisposed();
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.f27306w.schedule(new OnComplete(), this.delay, this.unit);
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.f27306w.schedule(new OnError(th), this.delayError ? this.delay : 0L, this.unit);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t2) {
            this.f27306w.schedule(new OnNext(t2), this.delay, this.unit);
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }
    }

    public ObservableDelay(ObservableSource<T> observableSource, long j2, TimeUnit timeUnit, Scheduler scheduler, boolean z2) {
        super(observableSource);
        this.delay = j2;
        this.unit = timeUnit;
        this.scheduler = scheduler;
        this.delayError = z2;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new DelayObserver(this.delayError ? observer : new SerializedObserver(observer), this.delay, this.unit, this.scheduler.createWorker(), this.delayError));
    }
}
