package io.reactivex.rxjava3.schedulers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class TestScheduler extends Scheduler {
    long counter;
    final Queue<TimedRunnable> queue;
    volatile long time;
    final boolean useOnScheduleHook;

    public static final class TimedRunnable implements Comparable<TimedRunnable> {
        final long count;
        final Runnable run;
        final TestWorker scheduler;
        final long time;

        public TimedRunnable(TestWorker scheduler, long time, Runnable run, long count) {
            this.time = time;
            this.run = run;
            this.scheduler = scheduler;
            this.count = count;
        }

        public String toString() {
            return String.format("TimedRunnable(time = %d, run = %s)", Long.valueOf(this.time), this.run.toString());
        }

        @Override // java.lang.Comparable
        public int compareTo(TimedRunnable o2) {
            long j2 = this.time;
            long j3 = o2.time;
            return j2 == j3 ? Long.compare(this.count, o2.count) : Long.compare(j2, j3);
        }
    }

    public TestScheduler() {
        this(false);
    }

    public void advanceTimeBy(long delayTime, TimeUnit unit) {
        advanceTimeTo(this.time + unit.toNanos(delayTime), TimeUnit.NANOSECONDS);
    }

    public void advanceTimeTo(long delayTime, TimeUnit unit) {
        triggerActions(unit.toNanos(delayTime));
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @NonNull
    public Scheduler.Worker createWorker() {
        return new TestWorker();
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public long now(@NonNull TimeUnit unit) {
        return unit.convert(this.time, TimeUnit.NANOSECONDS);
    }

    public void triggerActions() {
        triggerActions(this.time);
    }

    public TestScheduler(boolean useOnScheduleHook) {
        this.queue = new PriorityBlockingQueue(11);
        this.useOnScheduleHook = useOnScheduleHook;
    }

    private void triggerActions(long targetTimeInNanoseconds) {
        while (true) {
            TimedRunnable timedRunnablePeek = this.queue.peek();
            if (timedRunnablePeek == null) {
                break;
            }
            long j2 = timedRunnablePeek.time;
            if (j2 > targetTimeInNanoseconds) {
                break;
            }
            if (j2 == 0) {
                j2 = this.time;
            }
            this.time = j2;
            this.queue.remove(timedRunnablePeek);
            if (!timedRunnablePeek.scheduler.disposed) {
                timedRunnablePeek.run.run();
            }
        }
        this.time = targetTimeInNanoseconds;
    }

    public TestScheduler(long delayTime, TimeUnit unit) {
        this(delayTime, unit, false);
    }

    public TestScheduler(long delayTime, TimeUnit unit, boolean useOnScheduleHook) {
        this.queue = new PriorityBlockingQueue(11);
        this.time = unit.toNanos(delayTime);
        this.useOnScheduleHook = useOnScheduleHook;
    }

    public final class TestWorker extends Scheduler.Worker {
        volatile boolean disposed;

        public final class QueueRemove extends AtomicReference<TimedRunnable> implements Disposable {
            private static final long serialVersionUID = -7874968252110604360L;

            public QueueRemove(TimedRunnable timedAction) {
                lazySet(timedAction);
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public void dispose() {
                TimedRunnable andSet = getAndSet(null);
                if (andSet != null) {
                    TestScheduler.this.queue.remove(andSet);
                }
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public boolean isDisposed() {
                return get() == null;
            }
        }

        public TestWorker() {
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.disposed = true;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.disposed;
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        public long now(@NonNull TimeUnit unit) {
            return TestScheduler.this.now(unit);
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable run, long delayTime, @NonNull TimeUnit unit) {
            if (this.disposed) {
                return EmptyDisposable.INSTANCE;
            }
            if (TestScheduler.this.useOnScheduleHook) {
                run = RxJavaPlugins.onSchedule(run);
            }
            long nanos = TestScheduler.this.time + unit.toNanos(delayTime);
            TestScheduler testScheduler = TestScheduler.this;
            long j2 = testScheduler.counter;
            testScheduler.counter = 1 + j2;
            TimedRunnable timedRunnable = new TimedRunnable(this, nanos, run, j2);
            TestScheduler.this.queue.add(timedRunnable);
            return new QueueRemove(timedRunnable);
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable run) {
            if (this.disposed) {
                return EmptyDisposable.INSTANCE;
            }
            if (TestScheduler.this.useOnScheduleHook) {
                run = RxJavaPlugins.onSchedule(run);
            }
            TestScheduler testScheduler = TestScheduler.this;
            long j2 = testScheduler.counter;
            testScheduler.counter = 1 + j2;
            TimedRunnable timedRunnable = new TimedRunnable(this, 0L, run, j2);
            TestScheduler.this.queue.add(timedRunnable);
            return new QueueRemove(timedRunnable);
        }
    }
}
