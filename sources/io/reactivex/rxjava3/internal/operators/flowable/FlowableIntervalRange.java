package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.schedulers.TrampolineScheduler;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableIntervalRange extends Flowable<Long> {
    final long end;
    final long initialDelay;
    final long period;
    final Scheduler scheduler;
    final long start;
    final TimeUnit unit;

    public static final class IntervalRangeSubscriber extends AtomicLong implements Subscription, Runnable {
        private static final long serialVersionUID = -2809475196591179431L;
        long count;
        final Subscriber<? super Long> downstream;
        final long end;
        final AtomicReference<Disposable> resource = new AtomicReference<>();

        public IntervalRangeSubscriber(Subscriber<? super Long> actual, long start, long end) {
            this.downstream = actual;
            this.count = start;
            this.end = end;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            DisposableHelper.dispose(this.resource);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            if (SubscriptionHelper.validate(n2)) {
                BackpressureHelper.add(this, n2);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            Disposable disposable = this.resource.get();
            DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
            if (disposable != disposableHelper) {
                long j2 = get();
                if (j2 == 0) {
                    this.downstream.onError(new MissingBackpressureException("Can't deliver value " + this.count + " due to lack of requests"));
                    DisposableHelper.dispose(this.resource);
                    return;
                }
                long j3 = this.count;
                this.downstream.onNext(Long.valueOf(j3));
                if (j3 == this.end) {
                    if (this.resource.get() != disposableHelper) {
                        this.downstream.onComplete();
                    }
                    DisposableHelper.dispose(this.resource);
                } else {
                    this.count = j3 + 1;
                    if (j2 != Long.MAX_VALUE) {
                        decrementAndGet();
                    }
                }
            }
        }

        public void setResource(Disposable d3) {
            DisposableHelper.setOnce(this.resource, d3);
        }
    }

    public FlowableIntervalRange(long start, long end, long initialDelay, long period, TimeUnit unit, Scheduler scheduler) {
        this.initialDelay = initialDelay;
        this.period = period;
        this.unit = unit;
        this.scheduler = scheduler;
        this.start = start;
        this.end = end;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super Long> s2) {
        IntervalRangeSubscriber intervalRangeSubscriber = new IntervalRangeSubscriber(s2, this.start, this.end);
        s2.onSubscribe(intervalRangeSubscriber);
        Scheduler scheduler = this.scheduler;
        if (!(scheduler instanceof TrampolineScheduler)) {
            intervalRangeSubscriber.setResource(scheduler.schedulePeriodicallyDirect(intervalRangeSubscriber, this.initialDelay, this.period, this.unit));
            return;
        }
        Scheduler.Worker workerCreateWorker = scheduler.createWorker();
        intervalRangeSubscriber.setResource(workerCreateWorker);
        workerCreateWorker.schedulePeriodically(intervalRangeSubscriber, this.initialDelay, this.period, this.unit);
    }
}
