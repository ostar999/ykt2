package com.google.common.util.concurrent;

import cn.hutool.core.text.StrPool;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.Service;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
@GwtIncompatible
/* loaded from: classes4.dex */
public abstract class AbstractScheduledService implements Service {
    private static final Logger logger = Logger.getLogger(AbstractScheduledService.class.getName());
    private final AbstractService delegate = new ServiceDelegate();

    @Beta
    public static abstract class CustomScheduler extends Scheduler {

        public class ReschedulableCallable extends ForwardingFuture<Void> implements Callable<Void> {

            @NullableDecl
            @GuardedBy("lock")
            private Future<Void> currentFuture;
            private final ScheduledExecutorService executor;
            private final ReentrantLock lock = new ReentrantLock();
            private final AbstractService service;
            private final Runnable wrappedRunnable;

            public ReschedulableCallable(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable) {
                this.wrappedRunnable = runnable;
                this.executor = scheduledExecutorService;
                this.service = abstractService;
            }

            @Override // com.google.common.util.concurrent.ForwardingFuture, java.util.concurrent.Future
            public boolean cancel(boolean z2) {
                this.lock.lock();
                try {
                    return this.currentFuture.cancel(z2);
                } finally {
                    this.lock.unlock();
                }
            }

            @Override // com.google.common.util.concurrent.ForwardingFuture, java.util.concurrent.Future
            public boolean isCancelled() {
                this.lock.lock();
                try {
                    return this.currentFuture.isCancelled();
                } finally {
                    this.lock.unlock();
                }
            }

            public void reschedule() {
                try {
                    Schedule nextSchedule = CustomScheduler.this.getNextSchedule();
                    this.lock.lock();
                    try {
                        Future<Void> future = this.currentFuture;
                        if (future == null || !future.isCancelled()) {
                            this.currentFuture = this.executor.schedule(this, nextSchedule.delay, nextSchedule.unit);
                        }
                        this.lock.unlock();
                        th = null;
                    } catch (Throwable th) {
                        th = th;
                        this.lock.unlock();
                    }
                    if (th != null) {
                        this.service.notifyFailed(th);
                    }
                } catch (Throwable th2) {
                    this.service.notifyFailed(th2);
                }
            }

            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                this.wrappedRunnable.run();
                reschedule();
                return null;
            }

            @Override // com.google.common.util.concurrent.ForwardingFuture, com.google.common.collect.ForwardingObject
            public Future<Void> delegate() {
                throw new UnsupportedOperationException("Only cancel and isCancelled is supported by this future");
            }
        }

        @Beta
        public static final class Schedule {
            private final long delay;
            private final TimeUnit unit;

            public Schedule(long j2, TimeUnit timeUnit) {
                this.delay = j2;
                this.unit = (TimeUnit) Preconditions.checkNotNull(timeUnit);
            }
        }

        public CustomScheduler() {
            super();
        }

        public abstract Schedule getNextSchedule() throws Exception;

        @Override // com.google.common.util.concurrent.AbstractScheduledService.Scheduler
        public final Future<?> schedule(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable) {
            ReschedulableCallable reschedulableCallable = new ReschedulableCallable(abstractService, scheduledExecutorService, runnable);
            reschedulableCallable.reschedule();
            return reschedulableCallable;
        }
    }

    public static abstract class Scheduler {
        public static Scheduler newFixedDelaySchedule(final long j2, final long j3, final TimeUnit timeUnit) {
            Preconditions.checkNotNull(timeUnit);
            Preconditions.checkArgument(j3 > 0, "delay must be > 0, found %s", j3);
            return new Scheduler() { // from class: com.google.common.util.concurrent.AbstractScheduledService.Scheduler.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                @Override // com.google.common.util.concurrent.AbstractScheduledService.Scheduler
                public Future<?> schedule(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable) {
                    return scheduledExecutorService.scheduleWithFixedDelay(runnable, j2, j3, timeUnit);
                }
            };
        }

        public static Scheduler newFixedRateSchedule(final long j2, final long j3, final TimeUnit timeUnit) {
            Preconditions.checkNotNull(timeUnit);
            Preconditions.checkArgument(j3 > 0, "period must be > 0, found %s", j3);
            return new Scheduler() { // from class: com.google.common.util.concurrent.AbstractScheduledService.Scheduler.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                @Override // com.google.common.util.concurrent.AbstractScheduledService.Scheduler
                public Future<?> schedule(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable) {
                    return scheduledExecutorService.scheduleAtFixedRate(runnable, j2, j3, timeUnit);
                }
            };
        }

        public abstract Future<?> schedule(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable);

        private Scheduler() {
        }
    }

    public final class ServiceDelegate extends AbstractService {

        @MonotonicNonNullDecl
        private volatile ScheduledExecutorService executorService;
        private final ReentrantLock lock;

        @MonotonicNonNullDecl
        private volatile Future<?> runningTask;
        private final Runnable task;

        public class Task implements Runnable {
            public Task() {
            }

            @Override // java.lang.Runnable
            public void run() {
                ServiceDelegate.this.lock.lock();
                try {
                } finally {
                    try {
                    } finally {
                    }
                }
                if (ServiceDelegate.this.runningTask.isCancelled()) {
                    return;
                }
                AbstractScheduledService.this.runOneIteration();
            }
        }

        private ServiceDelegate() {
            this.lock = new ReentrantLock();
            this.task = new Task();
        }

        @Override // com.google.common.util.concurrent.AbstractService
        public final void doStart() {
            this.executorService = MoreExecutors.renamingDecorator(AbstractScheduledService.this.executor(), new Supplier<String>() { // from class: com.google.common.util.concurrent.AbstractScheduledService.ServiceDelegate.1
                @Override // com.google.common.base.Supplier
                public String get() {
                    return AbstractScheduledService.this.serviceName() + " " + ServiceDelegate.this.state();
                }
            });
            this.executorService.execute(new Runnable() { // from class: com.google.common.util.concurrent.AbstractScheduledService.ServiceDelegate.2
                @Override // java.lang.Runnable
                public void run() {
                    ServiceDelegate.this.lock.lock();
                    try {
                        AbstractScheduledService.this.startUp();
                        ServiceDelegate serviceDelegate = ServiceDelegate.this;
                        serviceDelegate.runningTask = AbstractScheduledService.this.scheduler().schedule(AbstractScheduledService.this.delegate, ServiceDelegate.this.executorService, ServiceDelegate.this.task);
                        ServiceDelegate.this.notifyStarted();
                    } finally {
                        try {
                        } finally {
                        }
                    }
                }
            });
        }

        @Override // com.google.common.util.concurrent.AbstractService
        public final void doStop() {
            this.runningTask.cancel(false);
            this.executorService.execute(new Runnable() { // from class: com.google.common.util.concurrent.AbstractScheduledService.ServiceDelegate.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        ServiceDelegate.this.lock.lock();
                        try {
                            if (ServiceDelegate.this.state() != Service.State.STOPPING) {
                                return;
                            }
                            AbstractScheduledService.this.shutDown();
                            ServiceDelegate.this.lock.unlock();
                            ServiceDelegate.this.notifyStopped();
                        } finally {
                            ServiceDelegate.this.lock.unlock();
                        }
                    } catch (Throwable th) {
                        ServiceDelegate.this.notifyFailed(th);
                    }
                }
            });
        }

        @Override // com.google.common.util.concurrent.AbstractService
        public String toString() {
            return AbstractScheduledService.this.toString();
        }
    }

    @Override // com.google.common.util.concurrent.Service
    public final void addListener(Service.Listener listener, Executor executor) {
        this.delegate.addListener(listener, executor);
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitRunning() {
        this.delegate.awaitRunning();
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitTerminated() {
        this.delegate.awaitTerminated();
    }

    public ScheduledExecutorService executor() {
        final ScheduledExecutorService scheduledExecutorServiceNewSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() { // from class: com.google.common.util.concurrent.AbstractScheduledService.1ThreadFactoryImpl
            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                return MoreExecutors.newThread(AbstractScheduledService.this.serviceName(), runnable);
            }
        });
        addListener(new Service.Listener() { // from class: com.google.common.util.concurrent.AbstractScheduledService.1
            @Override // com.google.common.util.concurrent.Service.Listener
            public void failed(Service.State state, Throwable th) {
                scheduledExecutorServiceNewSingleThreadScheduledExecutor.shutdown();
            }

            @Override // com.google.common.util.concurrent.Service.Listener
            public void terminated(Service.State state) {
                scheduledExecutorServiceNewSingleThreadScheduledExecutor.shutdown();
            }
        }, MoreExecutors.directExecutor());
        return scheduledExecutorServiceNewSingleThreadScheduledExecutor;
    }

    @Override // com.google.common.util.concurrent.Service
    public final Throwable failureCause() {
        return this.delegate.failureCause();
    }

    @Override // com.google.common.util.concurrent.Service
    public final boolean isRunning() {
        return this.delegate.isRunning();
    }

    public abstract void runOneIteration() throws Exception;

    public abstract Scheduler scheduler();

    public String serviceName() {
        return getClass().getSimpleName();
    }

    public void shutDown() throws Exception {
    }

    @Override // com.google.common.util.concurrent.Service
    @CanIgnoreReturnValue
    public final Service startAsync() {
        this.delegate.startAsync();
        return this;
    }

    public void startUp() throws Exception {
    }

    @Override // com.google.common.util.concurrent.Service
    public final Service.State state() {
        return this.delegate.state();
    }

    @Override // com.google.common.util.concurrent.Service
    @CanIgnoreReturnValue
    public final Service stopAsync() {
        this.delegate.stopAsync();
        return this;
    }

    public String toString() {
        return serviceName() + " [" + state() + StrPool.BRACKET_END;
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitRunning(long j2, TimeUnit timeUnit) throws TimeoutException {
        this.delegate.awaitRunning(j2, timeUnit);
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitTerminated(long j2, TimeUnit timeUnit) throws TimeoutException {
        this.delegate.awaitTerminated(j2, timeUnit);
    }
}
