package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.schedulers.TrampolineScheduler;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class ObservableIntervalRange extends Observable<Long> {
    final long end;
    final long initialDelay;
    final long period;
    final Scheduler scheduler;
    final long start;
    final TimeUnit unit;

    public static final class IntervalRangeObserver extends AtomicReference<Disposable> implements Disposable, Runnable {
        private static final long serialVersionUID = 1891866368734007884L;
        long count;
        final Observer<? super Long> downstream;
        final long end;

        public IntervalRangeObserver(Observer<? super Long> observer, long j2, long j3) {
            this.downstream = observer;
            this.count = j2;
            this.end = j3;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() == DisposableHelper.DISPOSED;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (isDisposed()) {
                return;
            }
            long j2 = this.count;
            this.downstream.onNext(Long.valueOf(j2));
            if (j2 != this.end) {
                this.count = j2 + 1;
            } else {
                DisposableHelper.dispose(this);
                this.downstream.onComplete();
            }
        }

        public void setResource(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }
    }

    public ObservableIntervalRange(long j2, long j3, long j4, long j5, TimeUnit timeUnit, Scheduler scheduler) {
        this.initialDelay = j4;
        this.period = j5;
        this.unit = timeUnit;
        this.scheduler = scheduler;
        this.start = j2;
        this.end = j3;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super Long> observer) {
        IntervalRangeObserver intervalRangeObserver = new IntervalRangeObserver(observer, this.start, this.end);
        observer.onSubscribe(intervalRangeObserver);
        Scheduler scheduler = this.scheduler;
        if (!(scheduler instanceof TrampolineScheduler)) {
            intervalRangeObserver.setResource(scheduler.schedulePeriodicallyDirect(intervalRangeObserver, this.initialDelay, this.period, this.unit));
            return;
        }
        Scheduler.Worker workerCreateWorker = scheduler.createWorker();
        intervalRangeObserver.setResource(workerCreateWorker);
        workerCreateWorker.schedulePeriodically(intervalRangeObserver, this.initialDelay, this.period, this.unit);
    }
}
