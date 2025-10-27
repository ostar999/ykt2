package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.schedulers.Timed;
import java.util.concurrent.TimeUnit;

/* loaded from: classes8.dex */
public final class ObservableTimeInterval<T> extends AbstractObservableWithUpstream<T, Timed<T>> {
    final Scheduler scheduler;
    final TimeUnit unit;

    public static final class TimeIntervalObserver<T> implements Observer<T>, Disposable {
        final Observer<? super Timed<T>> downstream;
        long lastTime;
        final Scheduler scheduler;
        final TimeUnit unit;
        Disposable upstream;

        public TimeIntervalObserver(Observer<? super Timed<T>> actual, TimeUnit unit, Scheduler scheduler) {
            this.downstream = actual;
            this.scheduler = scheduler;
            this.unit = unit;
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
            this.downstream.onComplete();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            this.downstream.onError(t2);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            long jNow = this.scheduler.now(this.unit);
            long j2 = this.lastTime;
            this.lastTime = jNow;
            this.downstream.onNext(new Timed(t2, jNow - j2, this.unit));
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                this.lastTime = this.scheduler.now(this.unit);
                this.downstream.onSubscribe(this);
            }
        }
    }

    public ObservableTimeInterval(ObservableSource<T> source, TimeUnit unit, Scheduler scheduler) {
        super(source);
        this.scheduler = scheduler;
        this.unit = unit;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super Timed<T>> t2) {
        this.source.subscribe(new TimeIntervalObserver(t2, this.unit, this.scheduler));
    }
}
