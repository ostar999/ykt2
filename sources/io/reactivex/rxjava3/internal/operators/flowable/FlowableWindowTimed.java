package io.reactivex.rxjava3.internal.operators.flowable;

import com.catchpig.download.subscriber.DownloadSubscriber;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable;
import io.reactivex.rxjava3.internal.queue.MpscLinkedQueue;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.operators.SimplePlainQueue;
import io.reactivex.rxjava3.processors.UnicastProcessor;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableWindowTimed<T> extends AbstractFlowableWithUpstream<T, Flowable<T>> {
    final int bufferSize;
    final long maxSize;
    final boolean restartTimerOnMaxSize;
    final Scheduler scheduler;
    final long timeskip;
    final long timespan;
    final TimeUnit unit;

    public static abstract class AbstractWindowSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = 5724293814035355511L;
        final int bufferSize;
        volatile boolean done;
        final Subscriber<? super Flowable<T>> downstream;
        long emitted;
        Throwable error;
        final long timespan;
        final TimeUnit unit;
        Subscription upstream;
        volatile boolean upstreamCancelled;
        final SimplePlainQueue<Object> queue = new MpscLinkedQueue();
        final AtomicLong requested = new AtomicLong();
        final AtomicBoolean downstreamCancelled = new AtomicBoolean();
        final AtomicInteger windowCount = new AtomicInteger(1);

        public AbstractWindowSubscriber(Subscriber<? super Flowable<T>> downstream, long timespan, TimeUnit unit, int bufferSize) {
            this.downstream = downstream;
            this.timespan = timespan;
            this.unit = unit;
            this.bufferSize = bufferSize;
        }

        @Override // org.reactivestreams.Subscription
        public final void cancel() {
            if (this.downstreamCancelled.compareAndSet(false, true)) {
                windowDone();
            }
        }

        abstract void cleanupResources();

        abstract void createFirstWindow();

        abstract void drain();

        @Override // org.reactivestreams.Subscriber
        public final void onComplete() {
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public final void onError(Throwable t2) {
            this.error = t2;
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public final void onNext(T t2) {
            this.queue.offer(t2);
            drain();
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public final void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                this.downstream.onSubscribe(this);
                createFirstWindow();
            }
        }

        @Override // org.reactivestreams.Subscription
        public final void request(long n2) {
            if (SubscriptionHelper.validate(n2)) {
                BackpressureHelper.add(this.requested, n2);
            }
        }

        final void windowDone() {
            if (this.windowCount.decrementAndGet() == 0) {
                cleanupResources();
                this.upstream.cancel();
                this.upstreamCancelled = true;
                drain();
            }
        }
    }

    public static final class WindowExactBoundedSubscriber<T> extends AbstractWindowSubscriber<T> implements Runnable {
        private static final long serialVersionUID = -6130475889925953722L;
        long count;
        final long maxSize;
        final boolean restartTimerOnMaxSize;
        final Scheduler scheduler;
        final SequentialDisposable timer;
        UnicastProcessor<T> window;
        final Scheduler.Worker worker;

        public static final class WindowBoundaryRunnable implements Runnable {
            final long index;
            final WindowExactBoundedSubscriber<?> parent;

            public WindowBoundaryRunnable(WindowExactBoundedSubscriber<?> parent, long index) {
                this.parent = parent;
                this.index = index;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.parent.boundary(this);
            }
        }

        public WindowExactBoundedSubscriber(Subscriber<? super Flowable<T>> actual, long timespan, TimeUnit unit, Scheduler scheduler, int bufferSize, long maxSize, boolean restartTimerOnMaxSize) {
            super(actual, timespan, unit, bufferSize);
            this.scheduler = scheduler;
            this.maxSize = maxSize;
            this.restartTimerOnMaxSize = restartTimerOnMaxSize;
            if (restartTimerOnMaxSize) {
                this.worker = scheduler.createWorker();
            } else {
                this.worker = null;
            }
            this.timer = new SequentialDisposable();
        }

        public void boundary(WindowBoundaryRunnable sender) {
            this.queue.offer(sender);
            drain();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowTimed.AbstractWindowSubscriber
        public void cleanupResources() {
            this.timer.dispose();
            Scheduler.Worker worker = this.worker;
            if (worker != null) {
                worker.dispose();
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowTimed.AbstractWindowSubscriber
        public void createFirstWindow() {
            if (this.downstreamCancelled.get()) {
                return;
            }
            if (this.requested.get() == 0) {
                this.upstream.cancel();
                this.downstream.onError(new MissingBackpressureException(FlowableWindowTimed.missingBackpressureMessage(this.emitted)));
                cleanupResources();
                this.upstreamCancelled = true;
                return;
            }
            this.emitted = 1L;
            this.windowCount.getAndIncrement();
            this.window = UnicastProcessor.create(this.bufferSize, this);
            FlowableWindowSubscribeIntercept flowableWindowSubscribeIntercept = new FlowableWindowSubscribeIntercept(this.window);
            this.downstream.onNext(flowableWindowSubscribeIntercept);
            WindowBoundaryRunnable windowBoundaryRunnable = new WindowBoundaryRunnable(this, 1L);
            if (this.restartTimerOnMaxSize) {
                SequentialDisposable sequentialDisposable = this.timer;
                Scheduler.Worker worker = this.worker;
                long j2 = this.timespan;
                sequentialDisposable.replace(worker.schedulePeriodically(windowBoundaryRunnable, j2, j2, this.unit));
            } else {
                SequentialDisposable sequentialDisposable2 = this.timer;
                Scheduler scheduler = this.scheduler;
                long j3 = this.timespan;
                sequentialDisposable2.replace(scheduler.schedulePeriodicallyDirect(windowBoundaryRunnable, j3, j3, this.unit));
            }
            if (flowableWindowSubscribeIntercept.tryAbandon()) {
                this.window.onComplete();
            }
            this.upstream.request(Long.MAX_VALUE);
        }

        public UnicastProcessor<T> createNewWindow(UnicastProcessor<T> window) {
            if (window != null) {
                window.onComplete();
                window = null;
            }
            if (this.downstreamCancelled.get()) {
                cleanupResources();
            } else {
                long j2 = this.emitted;
                if (this.requested.get() == j2) {
                    this.upstream.cancel();
                    cleanupResources();
                    this.upstreamCancelled = true;
                    this.downstream.onError(new MissingBackpressureException(FlowableWindowTimed.missingBackpressureMessage(j2)));
                } else {
                    long j3 = j2 + 1;
                    this.emitted = j3;
                    this.windowCount.getAndIncrement();
                    window = UnicastProcessor.create(this.bufferSize, this);
                    this.window = window;
                    FlowableWindowSubscribeIntercept flowableWindowSubscribeIntercept = new FlowableWindowSubscribeIntercept(window);
                    this.downstream.onNext(flowableWindowSubscribeIntercept);
                    if (this.restartTimerOnMaxSize) {
                        SequentialDisposable sequentialDisposable = this.timer;
                        Scheduler.Worker worker = this.worker;
                        WindowBoundaryRunnable windowBoundaryRunnable = new WindowBoundaryRunnable(this, j3);
                        long j4 = this.timespan;
                        sequentialDisposable.update(worker.schedulePeriodically(windowBoundaryRunnable, j4, j4, this.unit));
                    }
                    if (flowableWindowSubscribeIntercept.tryAbandon()) {
                        window.onComplete();
                    }
                }
            }
            return window;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowTimed.AbstractWindowSubscriber
        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            SimplePlainQueue<Object> simplePlainQueue = this.queue;
            Subscriber<? super Flowable<T>> subscriber = this.downstream;
            UnicastProcessor<T> unicastProcessorCreateNewWindow = this.window;
            int iAddAndGet = 1;
            while (true) {
                if (this.upstreamCancelled) {
                    simplePlainQueue.clear();
                    unicastProcessorCreateNewWindow = 0;
                    this.window = null;
                } else {
                    boolean z2 = this.done;
                    Object objPoll = simplePlainQueue.poll();
                    boolean z3 = objPoll == null;
                    if (z2 && z3) {
                        Throwable th = this.error;
                        if (th != null) {
                            if (unicastProcessorCreateNewWindow != 0) {
                                unicastProcessorCreateNewWindow.onError(th);
                            }
                            subscriber.onError(th);
                        } else {
                            if (unicastProcessorCreateNewWindow != 0) {
                                unicastProcessorCreateNewWindow.onComplete();
                            }
                            subscriber.onComplete();
                        }
                        cleanupResources();
                        this.upstreamCancelled = true;
                    } else if (!z3) {
                        if (objPoll instanceof WindowBoundaryRunnable) {
                            if (((WindowBoundaryRunnable) objPoll).index == this.emitted || !this.restartTimerOnMaxSize) {
                                this.count = 0L;
                                unicastProcessorCreateNewWindow = createNewWindow(unicastProcessorCreateNewWindow);
                            }
                        } else if (unicastProcessorCreateNewWindow != 0) {
                            unicastProcessorCreateNewWindow.onNext((DownloadSubscriber) objPoll);
                            long j2 = this.count + 1;
                            if (j2 == this.maxSize) {
                                this.count = 0L;
                                unicastProcessorCreateNewWindow = createNewWindow(unicastProcessorCreateNewWindow);
                            } else {
                                this.count = j2;
                            }
                        }
                    }
                }
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            windowDone();
        }
    }

    public static final class WindowExactUnboundedSubscriber<T> extends AbstractWindowSubscriber<T> implements Runnable {
        static final Object NEXT_WINDOW = new Object();
        private static final long serialVersionUID = 1155822639622580836L;
        final Scheduler scheduler;
        final SequentialDisposable timer;
        UnicastProcessor<T> window;
        final Runnable windowRunnable;

        public final class WindowRunnable implements Runnable {
            public WindowRunnable() {
            }

            @Override // java.lang.Runnable
            public void run() {
                WindowExactUnboundedSubscriber.this.windowDone();
            }
        }

        public WindowExactUnboundedSubscriber(Subscriber<? super Flowable<T>> actual, long timespan, TimeUnit unit, Scheduler scheduler, int bufferSize) {
            super(actual, timespan, unit, bufferSize);
            this.scheduler = scheduler;
            this.timer = new SequentialDisposable();
            this.windowRunnable = new WindowRunnable();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowTimed.AbstractWindowSubscriber
        public void cleanupResources() {
            this.timer.dispose();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowTimed.AbstractWindowSubscriber
        public void createFirstWindow() {
            if (this.downstreamCancelled.get()) {
                return;
            }
            if (this.requested.get() == 0) {
                this.upstream.cancel();
                this.downstream.onError(new MissingBackpressureException(FlowableWindowTimed.missingBackpressureMessage(this.emitted)));
                cleanupResources();
                this.upstreamCancelled = true;
                return;
            }
            this.windowCount.getAndIncrement();
            this.window = UnicastProcessor.create(this.bufferSize, this.windowRunnable);
            this.emitted = 1L;
            FlowableWindowSubscribeIntercept flowableWindowSubscribeIntercept = new FlowableWindowSubscribeIntercept(this.window);
            this.downstream.onNext(flowableWindowSubscribeIntercept);
            SequentialDisposable sequentialDisposable = this.timer;
            Scheduler scheduler = this.scheduler;
            long j2 = this.timespan;
            sequentialDisposable.replace(scheduler.schedulePeriodicallyDirect(this, j2, j2, this.unit));
            if (flowableWindowSubscribeIntercept.tryAbandon()) {
                this.window.onComplete();
            }
            this.upstream.request(Long.MAX_VALUE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v12, types: [io.reactivex.rxjava3.processors.UnicastProcessor] */
        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowTimed.AbstractWindowSubscriber
        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            SimplePlainQueue<Object> simplePlainQueue = this.queue;
            Subscriber<? super Flowable<T>> subscriber = this.downstream;
            UnicastProcessor unicastProcessor = (UnicastProcessor<T>) this.window;
            int iAddAndGet = 1;
            while (true) {
                if (this.upstreamCancelled) {
                    simplePlainQueue.clear();
                    this.window = null;
                    unicastProcessor = (UnicastProcessor<T>) null;
                } else {
                    boolean z2 = this.done;
                    Object objPoll = simplePlainQueue.poll();
                    boolean z3 = objPoll == null;
                    if (z2 && z3) {
                        Throwable th = this.error;
                        if (th != null) {
                            if (unicastProcessor != null) {
                                unicastProcessor.onError(th);
                            }
                            subscriber.onError(th);
                        } else {
                            if (unicastProcessor != null) {
                                unicastProcessor.onComplete();
                            }
                            subscriber.onComplete();
                        }
                        cleanupResources();
                        this.upstreamCancelled = true;
                    } else if (!z3) {
                        if (objPoll == NEXT_WINDOW) {
                            if (unicastProcessor != null) {
                                unicastProcessor.onComplete();
                                this.window = null;
                                unicastProcessor = (UnicastProcessor<T>) null;
                            }
                            if (this.downstreamCancelled.get()) {
                                this.timer.dispose();
                            } else {
                                long j2 = this.requested.get();
                                long j3 = this.emitted;
                                if (j2 == j3) {
                                    this.upstream.cancel();
                                    cleanupResources();
                                    this.upstreamCancelled = true;
                                    subscriber.onError(new MissingBackpressureException(FlowableWindowTimed.missingBackpressureMessage(this.emitted)));
                                } else {
                                    this.emitted = j3 + 1;
                                    this.windowCount.getAndIncrement();
                                    unicastProcessor = (UnicastProcessor<T>) UnicastProcessor.create(this.bufferSize, this.windowRunnable);
                                    this.window = unicastProcessor;
                                    FlowableWindowSubscribeIntercept flowableWindowSubscribeIntercept = new FlowableWindowSubscribeIntercept(unicastProcessor);
                                    subscriber.onNext(flowableWindowSubscribeIntercept);
                                    if (flowableWindowSubscribeIntercept.tryAbandon()) {
                                        unicastProcessor.onComplete();
                                    }
                                }
                            }
                        } else if (unicastProcessor != null) {
                            unicastProcessor.onNext(objPoll);
                        }
                    }
                }
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            this.queue.offer(NEXT_WINDOW);
            drain();
        }
    }

    public static final class WindowSkipSubscriber<T> extends AbstractWindowSubscriber<T> implements Runnable {
        private static final long serialVersionUID = -7852870764194095894L;
        final long timeskip;
        final List<UnicastProcessor<T>> windows;
        final Scheduler.Worker worker;
        static final Object WINDOW_OPEN = new Object();
        static final Object WINDOW_CLOSE = new Object();

        public static final class WindowBoundaryRunnable implements Runnable {
            final boolean isOpen;
            final WindowSkipSubscriber<?> parent;

            public WindowBoundaryRunnable(WindowSkipSubscriber<?> parent, boolean isOpen) {
                this.parent = parent;
                this.isOpen = isOpen;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.parent.boundary(this.isOpen);
            }
        }

        public WindowSkipSubscriber(Subscriber<? super Flowable<T>> actual, long timespan, long timeskip, TimeUnit unit, Scheduler.Worker worker, int bufferSize) {
            super(actual, timespan, unit, bufferSize);
            this.timeskip = timeskip;
            this.worker = worker;
            this.windows = new LinkedList();
        }

        public void boundary(boolean isOpen) {
            this.queue.offer(isOpen ? WINDOW_OPEN : WINDOW_CLOSE);
            drain();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowTimed.AbstractWindowSubscriber
        public void cleanupResources() {
            this.worker.dispose();
        }

        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowTimed.AbstractWindowSubscriber
        public void createFirstWindow() {
            if (this.downstreamCancelled.get()) {
                return;
            }
            if (this.requested.get() == 0) {
                this.upstream.cancel();
                this.downstream.onError(new MissingBackpressureException(FlowableWindowTimed.missingBackpressureMessage(this.emitted)));
                cleanupResources();
                this.upstreamCancelled = true;
                return;
            }
            this.emitted = 1L;
            this.windowCount.getAndIncrement();
            UnicastProcessor<T> unicastProcessorCreate = UnicastProcessor.create(this.bufferSize, this);
            this.windows.add(unicastProcessorCreate);
            FlowableWindowSubscribeIntercept flowableWindowSubscribeIntercept = new FlowableWindowSubscribeIntercept(unicastProcessorCreate);
            this.downstream.onNext(flowableWindowSubscribeIntercept);
            this.worker.schedule(new WindowBoundaryRunnable(this, false), this.timespan, this.unit);
            Scheduler.Worker worker = this.worker;
            WindowBoundaryRunnable windowBoundaryRunnable = new WindowBoundaryRunnable(this, true);
            long j2 = this.timeskip;
            worker.schedulePeriodically(windowBoundaryRunnable, j2, j2, this.unit);
            if (flowableWindowSubscribeIntercept.tryAbandon()) {
                unicastProcessorCreate.onComplete();
                this.windows.remove(unicastProcessorCreate);
            }
            this.upstream.request(Long.MAX_VALUE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowTimed.AbstractWindowSubscriber
        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            SimplePlainQueue<Object> simplePlainQueue = this.queue;
            Subscriber<? super Flowable<T>> subscriber = this.downstream;
            List<UnicastProcessor<T>> list = this.windows;
            int iAddAndGet = 1;
            while (true) {
                if (this.upstreamCancelled) {
                    simplePlainQueue.clear();
                    list.clear();
                } else {
                    boolean z2 = this.done;
                    Object objPoll = simplePlainQueue.poll();
                    boolean z3 = objPoll == null;
                    if (z2 && z3) {
                        Throwable th = this.error;
                        if (th != null) {
                            Iterator<UnicastProcessor<T>> it = list.iterator();
                            while (it.hasNext()) {
                                it.next().onError(th);
                            }
                            subscriber.onError(th);
                        } else {
                            Iterator<UnicastProcessor<T>> it2 = list.iterator();
                            while (it2.hasNext()) {
                                it2.next().onComplete();
                            }
                            subscriber.onComplete();
                        }
                        cleanupResources();
                        this.upstreamCancelled = true;
                    } else if (!z3) {
                        if (objPoll == WINDOW_OPEN) {
                            if (!this.downstreamCancelled.get()) {
                                long j2 = this.emitted;
                                if (this.requested.get() != j2) {
                                    this.emitted = j2 + 1;
                                    this.windowCount.getAndIncrement();
                                    UnicastProcessor<T> unicastProcessorCreate = UnicastProcessor.create(this.bufferSize, this);
                                    list.add(unicastProcessorCreate);
                                    FlowableWindowSubscribeIntercept flowableWindowSubscribeIntercept = new FlowableWindowSubscribeIntercept(unicastProcessorCreate);
                                    subscriber.onNext(flowableWindowSubscribeIntercept);
                                    this.worker.schedule(new WindowBoundaryRunnable(this, false), this.timespan, this.unit);
                                    if (flowableWindowSubscribeIntercept.tryAbandon()) {
                                        unicastProcessorCreate.onComplete();
                                    }
                                } else {
                                    this.upstream.cancel();
                                    MissingBackpressureException missingBackpressureException = new MissingBackpressureException(FlowableWindowTimed.missingBackpressureMessage(j2));
                                    Iterator<UnicastProcessor<T>> it3 = list.iterator();
                                    while (it3.hasNext()) {
                                        it3.next().onError(missingBackpressureException);
                                    }
                                    subscriber.onError(missingBackpressureException);
                                    cleanupResources();
                                    this.upstreamCancelled = true;
                                }
                            }
                        } else if (objPoll != WINDOW_CLOSE) {
                            Iterator<UnicastProcessor<T>> it4 = list.iterator();
                            while (it4.hasNext()) {
                                it4.next().onNext(objPoll);
                            }
                        } else if (!list.isEmpty()) {
                            list.remove(0).onComplete();
                        }
                    }
                }
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            windowDone();
        }
    }

    public FlowableWindowTimed(Flowable<T> source, long timespan, long timeskip, TimeUnit unit, Scheduler scheduler, long maxSize, int bufferSize, boolean restartTimerOnMaxSize) {
        super(source);
        this.timespan = timespan;
        this.timeskip = timeskip;
        this.unit = unit;
        this.scheduler = scheduler;
        this.maxSize = maxSize;
        this.bufferSize = bufferSize;
        this.restartTimerOnMaxSize = restartTimerOnMaxSize;
    }

    public static String missingBackpressureMessage(long index) {
        return "Unable to emit the next window (#" + index + ") due to lack of requests. Please make sure the downstream is ready to consume windows.";
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super Flowable<T>> downstream) {
        if (this.timespan != this.timeskip) {
            this.source.subscribe((FlowableSubscriber) new WindowSkipSubscriber(downstream, this.timespan, this.timeskip, this.unit, this.scheduler.createWorker(), this.bufferSize));
        } else if (this.maxSize == Long.MAX_VALUE) {
            this.source.subscribe((FlowableSubscriber) new WindowExactUnboundedSubscriber(downstream, this.timespan, this.unit, this.scheduler, this.bufferSize));
        } else {
            this.source.subscribe((FlowableSubscriber) new WindowExactBoundedSubscriber(downstream, this.timespan, this.unit, this.scheduler, this.bufferSize, this.maxSize, this.restartTimerOnMaxSize));
        }
    }
}
