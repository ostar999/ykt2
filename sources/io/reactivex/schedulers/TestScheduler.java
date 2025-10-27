package io.reactivex.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes8.dex */
public final class TestScheduler extends Scheduler {
    long counter;
    final Queue<TimedRunnable> queue = new PriorityBlockingQueue(11);
    volatile long time;

    public static final class TimedRunnable implements Comparable<TimedRunnable> {
        final long count;
        final Runnable run;
        final TestWorker scheduler;
        final long time;

        public TimedRunnable(TestWorker testWorker, long j2, Runnable runnable, long j3) {
            this.time = j2;
            this.run = runnable;
            this.scheduler = testWorker;
            this.count = j3;
        }

        public String toString() {
            return String.format("TimedRunnable(time = %d, run = %s)", Long.valueOf(this.time), this.run.toString());
        }

        @Override // java.lang.Comparable
        public int compareTo(TimedRunnable timedRunnable) {
            long j2 = this.time;
            long j3 = timedRunnable.time;
            return j2 == j3 ? ObjectHelper.compare(this.count, timedRunnable.count) : ObjectHelper.compare(j2, j3);
        }
    }

    public TestScheduler() {
    }

    public void advanceTimeBy(long j2, TimeUnit timeUnit) {
        advanceTimeTo(this.time + timeUnit.toNanos(j2), TimeUnit.NANOSECONDS);
    }

    public void advanceTimeTo(long j2, TimeUnit timeUnit) {
        triggerActions(timeUnit.toNanos(j2));
    }

    @Override // io.reactivex.Scheduler
    @NonNull
    public Scheduler.Worker createWorker() {
        return new TestWorker();
    }

    @Override // io.reactivex.Scheduler
    public long now(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.time, TimeUnit.NANOSECONDS);
    }

    public void triggerActions() {
        triggerActions(this.time);
    }

    private void triggerActions(long j2) {
        while (true) {
            TimedRunnable timedRunnablePeek = this.queue.peek();
            if (timedRunnablePeek == null) {
                break;
            }
            long j3 = timedRunnablePeek.time;
            if (j3 > j2) {
                break;
            }
            if (j3 == 0) {
                j3 = this.time;
            }
            this.time = j3;
            this.queue.remove(timedRunnablePeek);
            if (!timedRunnablePeek.scheduler.disposed) {
                timedRunnablePeek.run.run();
            }
        }
        this.time = j2;
    }

    public TestScheduler(long j2, TimeUnit timeUnit) {
        this.time = timeUnit.toNanos(j2);
    }

    public final class TestWorker extends Scheduler.Worker {
        volatile boolean disposed;

        public final class QueueRemove implements Runnable {
            final TimedRunnable timedAction;

            public QueueRemove(TimedRunnable timedRunnable) {
                this.timedAction = timedRunnable;
            }

            @Override // java.lang.Runnable
            public void run() {
                TestScheduler.this.queue.remove(this.timedAction);
            }
        }

        public TestWorker() {
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.disposed = true;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.disposed;
        }

        @Override // io.reactivex.Scheduler.Worker
        public long now(@NonNull TimeUnit timeUnit) {
            return TestScheduler.this.now(timeUnit);
        }

        @Override // io.reactivex.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable, long j2, @NonNull TimeUnit timeUnit) {
            if (this.disposed) {
                return EmptyDisposable.INSTANCE;
            }
            long nanos = TestScheduler.this.time + timeUnit.toNanos(j2);
            TestScheduler testScheduler = TestScheduler.this;
            long j3 = testScheduler.counter;
            testScheduler.counter = 1 + j3;
            TimedRunnable timedRunnable = new TimedRunnable(this, nanos, runnable, j3);
            TestScheduler.this.queue.add(timedRunnable);
            return Disposables.fromRunnable(new QueueRemove(timedRunnable));
        }

        @Override // io.reactivex.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable) {
            if (this.disposed) {
                return EmptyDisposable.INSTANCE;
            }
            TestScheduler testScheduler = TestScheduler.this;
            long j2 = testScheduler.counter;
            testScheduler.counter = 1 + j2;
            TimedRunnable timedRunnable = new TimedRunnable(this, 0L, runnable, j2);
            TestScheduler.this.queue.add(timedRunnable);
            return Disposables.fromRunnable(new QueueRemove(timedRunnable));
        }
    }
}
