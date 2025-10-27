package io.reactivex.rxjava3.internal.schedulers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.disposables.b;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes8.dex */
public final class TrampolineScheduler extends Scheduler {
    private static final TrampolineScheduler INSTANCE = new TrampolineScheduler();

    public static final class SleepingRunnable implements Runnable {
        private final long execTime;
        private final Runnable run;
        private final TrampolineWorker worker;

        public SleepingRunnable(Runnable run, TrampolineWorker worker, long execTime) {
            this.run = run;
            this.worker = worker;
            this.execTime = execTime;
        }

        @Override // java.lang.Runnable
        public void run() throws InterruptedException {
            if (this.worker.disposed) {
                return;
            }
            long jNow = this.worker.now(TimeUnit.MILLISECONDS);
            long j2 = this.execTime;
            if (j2 > jNow) {
                try {
                    Thread.sleep(j2 - jNow);
                } catch (InterruptedException e2) {
                    Thread.currentThread().interrupt();
                    RxJavaPlugins.onError(e2);
                    return;
                }
            }
            if (this.worker.disposed) {
                return;
            }
            this.run.run();
        }
    }

    public static final class TimedRunnable implements Comparable<TimedRunnable> {
        final int count;
        volatile boolean disposed;
        final long execTime;
        final Runnable run;

        public TimedRunnable(Runnable run, Long execTime, int count) {
            this.run = run;
            this.execTime = execTime.longValue();
            this.count = count;
        }

        @Override // java.lang.Comparable
        public int compareTo(TimedRunnable that) {
            int iCompare = Long.compare(this.execTime, that.execTime);
            return iCompare == 0 ? Integer.compare(this.count, that.count) : iCompare;
        }
    }

    public static final class TrampolineWorker extends Scheduler.Worker implements Disposable {
        volatile boolean disposed;
        final PriorityBlockingQueue<TimedRunnable> queue = new PriorityBlockingQueue<>();
        private final AtomicInteger wip = new AtomicInteger();
        final AtomicInteger counter = new AtomicInteger();

        public final class AppendToQueueTask implements Runnable {
            final TimedRunnable timedRunnable;

            public AppendToQueueTask(TimedRunnable timedRunnable) {
                this.timedRunnable = timedRunnable;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.timedRunnable.disposed = true;
                TrampolineWorker.this.queue.remove(this.timedRunnable);
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.disposed = true;
        }

        public Disposable enqueue(Runnable action, long execTime) {
            if (this.disposed) {
                return EmptyDisposable.INSTANCE;
            }
            TimedRunnable timedRunnable = new TimedRunnable(action, Long.valueOf(execTime), this.counter.incrementAndGet());
            this.queue.add(timedRunnable);
            if (this.wip.getAndIncrement() != 0) {
                return b.g(new AppendToQueueTask(timedRunnable));
            }
            int iAddAndGet = 1;
            while (!this.disposed) {
                TimedRunnable timedRunnablePoll = this.queue.poll();
                if (timedRunnablePoll == null) {
                    iAddAndGet = this.wip.addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return EmptyDisposable.INSTANCE;
                    }
                } else if (!timedRunnablePoll.disposed) {
                    timedRunnablePoll.run.run();
                }
            }
            this.queue.clear();
            return EmptyDisposable.INSTANCE;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.disposed;
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable action) {
            return enqueue(action, now(TimeUnit.MILLISECONDS));
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable action, long delayTime, @NonNull TimeUnit unit) {
            long jNow = now(TimeUnit.MILLISECONDS) + unit.toMillis(delayTime);
            return enqueue(new SleepingRunnable(action, this, jNow), jNow);
        }
    }

    public static TrampolineScheduler instance() {
        return INSTANCE;
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @NonNull
    public Scheduler.Worker createWorker() {
        return new TrampolineWorker();
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable run) {
        RxJavaPlugins.onSchedule(run).run();
        return EmptyDisposable.INSTANCE;
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable run, long delay, TimeUnit unit) throws InterruptedException {
        try {
            unit.sleep(delay);
            RxJavaPlugins.onSchedule(run).run();
        } catch (InterruptedException e2) {
            Thread.currentThread().interrupt();
            RxJavaPlugins.onError(e2);
        }
        return EmptyDisposable.INSTANCE;
    }
}
