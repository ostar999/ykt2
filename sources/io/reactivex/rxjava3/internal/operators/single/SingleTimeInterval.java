package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.schedulers.Timed;
import java.util.concurrent.TimeUnit;

/* loaded from: classes8.dex */
public final class SingleTimeInterval<T> extends Single<Timed<T>> {
    final Scheduler scheduler;
    final SingleSource<T> source;
    final boolean start;
    final TimeUnit unit;

    public static final class TimeIntervalSingleObserver<T> implements SingleObserver<T>, Disposable {
        final SingleObserver<? super Timed<T>> downstream;
        final Scheduler scheduler;
        final long startTime;
        final TimeUnit unit;
        Disposable upstream;

        public TimeIntervalSingleObserver(SingleObserver<? super Timed<T>> downstream, TimeUnit unit, Scheduler scheduler, boolean start) {
            this.downstream = downstream;
            this.unit = unit;
            this.scheduler = scheduler;
            this.startTime = start ? scheduler.now(unit) : 0L;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.upstream.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(@NonNull Throwable e2) {
            this.downstream.onError(e2);
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(@NonNull Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(@NonNull T t2) {
            this.downstream.onSuccess(new Timed(t2, this.scheduler.now(this.unit) - this.startTime, this.unit));
        }
    }

    public SingleTimeInterval(SingleSource<T> source, TimeUnit unit, Scheduler scheduler, boolean start) {
        this.source = source;
        this.unit = unit;
        this.scheduler = scheduler;
        this.start = start;
    }

    @Override // io.reactivex.rxjava3.core.Single
    public void subscribeActual(@NonNull SingleObserver<? super Timed<T>> observer) {
        this.source.subscribe(new TimeIntervalSingleObserver(observer, this.unit, this.scheduler, this.start));
    }
}
