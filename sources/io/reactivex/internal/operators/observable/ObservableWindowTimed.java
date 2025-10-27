package io.reactivex.internal.operators.observable;

import androidx.camera.view.j;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.observers.QueueDrainObserver;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.subjects.UnicastSubject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class ObservableWindowTimed<T> extends AbstractObservableWithUpstream<T, Observable<T>> {
    final int bufferSize;
    final long maxSize;
    final boolean restartTimerOnMaxSize;
    final Scheduler scheduler;
    final long timeskip;
    final long timespan;
    final TimeUnit unit;

    public static final class WindowExactBoundedObserver<T> extends QueueDrainObserver<T, Object, Observable<T>> implements Disposable {
        final int bufferSize;
        long count;
        final long maxSize;
        long producerIndex;
        final boolean restartTimerOnMaxSize;
        final Scheduler scheduler;
        volatile boolean terminated;
        final AtomicReference<Disposable> timer;
        final long timespan;
        final TimeUnit unit;
        Disposable upstream;
        UnicastSubject<T> window;
        final Scheduler.Worker worker;

        public static final class ConsumerIndexHolder implements Runnable {
            final long index;
            final WindowExactBoundedObserver<?> parent;

            public ConsumerIndexHolder(long j2, WindowExactBoundedObserver<?> windowExactBoundedObserver) {
                this.index = j2;
                this.parent = windowExactBoundedObserver;
            }

            @Override // java.lang.Runnable
            public void run() {
                WindowExactBoundedObserver<?> windowExactBoundedObserver = this.parent;
                if (((QueueDrainObserver) windowExactBoundedObserver).cancelled) {
                    windowExactBoundedObserver.terminated = true;
                    windowExactBoundedObserver.disposeTimer();
                } else {
                    ((QueueDrainObserver) windowExactBoundedObserver).queue.offer(this);
                }
                if (windowExactBoundedObserver.enter()) {
                    windowExactBoundedObserver.drainLoop();
                }
            }
        }

        public WindowExactBoundedObserver(Observer<? super Observable<T>> observer, long j2, TimeUnit timeUnit, Scheduler scheduler, int i2, long j3, boolean z2) {
            super(observer, new MpscLinkedQueue());
            this.timer = new AtomicReference<>();
            this.timespan = j2;
            this.unit = timeUnit;
            this.scheduler = scheduler;
            this.bufferSize = i2;
            this.maxSize = j3;
            this.restartTimerOnMaxSize = z2;
            if (z2) {
                this.worker = scheduler.createWorker();
            } else {
                this.worker = null;
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.cancelled = true;
        }

        public void disposeTimer() {
            DisposableHelper.dispose(this.timer);
            Scheduler.Worker worker = this.worker;
            if (worker != null) {
                worker.dispose();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v0, types: [io.reactivex.subjects.UnicastSubject<T>] */
        public void drainLoop() {
            MpscLinkedQueue mpscLinkedQueue = (MpscLinkedQueue) this.queue;
            Observer<? super V> observer = this.downstream;
            UnicastSubject<T> unicastSubject = this.window;
            int iLeave = 1;
            while (!this.terminated) {
                boolean z2 = this.done;
                Object objPoll = mpscLinkedQueue.poll();
                boolean z3 = objPoll == null;
                boolean z4 = objPoll instanceof ConsumerIndexHolder;
                if (z2 && (z3 || z4)) {
                    this.window = null;
                    mpscLinkedQueue.clear();
                    disposeTimer();
                    Throwable th = this.error;
                    if (th != null) {
                        unicastSubject.onError(th);
                        return;
                    } else {
                        unicastSubject.onComplete();
                        return;
                    }
                }
                if (z3) {
                    iLeave = leave(-iLeave);
                    if (iLeave == 0) {
                        return;
                    }
                } else if (z4) {
                    ConsumerIndexHolder consumerIndexHolder = (ConsumerIndexHolder) objPoll;
                    if (this.restartTimerOnMaxSize || this.producerIndex == consumerIndexHolder.index) {
                        unicastSubject.onComplete();
                        this.count = 0L;
                        unicastSubject = (UnicastSubject<T>) UnicastSubject.create(this.bufferSize);
                        this.window = unicastSubject;
                        observer.onNext(unicastSubject);
                    }
                } else {
                    unicastSubject.onNext(NotificationLite.getValue(objPoll));
                    long j2 = this.count + 1;
                    if (j2 >= this.maxSize) {
                        this.producerIndex++;
                        this.count = 0L;
                        unicastSubject.onComplete();
                        unicastSubject = (UnicastSubject<T>) UnicastSubject.create(this.bufferSize);
                        this.window = unicastSubject;
                        this.downstream.onNext(unicastSubject);
                        if (this.restartTimerOnMaxSize) {
                            Disposable disposable = this.timer.get();
                            disposable.dispose();
                            Scheduler.Worker worker = this.worker;
                            ConsumerIndexHolder consumerIndexHolder2 = new ConsumerIndexHolder(this.producerIndex, this);
                            long j3 = this.timespan;
                            Disposable disposableSchedulePeriodically = worker.schedulePeriodically(consumerIndexHolder2, j3, j3, this.unit);
                            if (!j.a(this.timer, disposable, disposableSchedulePeriodically)) {
                                disposableSchedulePeriodically.dispose();
                            }
                        }
                    } else {
                        this.count = j2;
                    }
                }
            }
            this.upstream.dispose();
            mpscLinkedQueue.clear();
            disposeTimer();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.done = true;
            if (enter()) {
                drainLoop();
            }
            this.downstream.onComplete();
            disposeTimer();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            if (enter()) {
                drainLoop();
            }
            this.downstream.onError(th);
            disposeTimer();
        }

        @Override // io.reactivex.Observer
        public void onNext(T t2) {
            if (this.terminated) {
                return;
            }
            if (fastEnter()) {
                UnicastSubject<T> unicastSubject = this.window;
                unicastSubject.onNext(t2);
                long j2 = this.count + 1;
                if (j2 >= this.maxSize) {
                    this.producerIndex++;
                    this.count = 0L;
                    unicastSubject.onComplete();
                    UnicastSubject<T> unicastSubjectCreate = UnicastSubject.create(this.bufferSize);
                    this.window = unicastSubjectCreate;
                    this.downstream.onNext(unicastSubjectCreate);
                    if (this.restartTimerOnMaxSize) {
                        this.timer.get().dispose();
                        Scheduler.Worker worker = this.worker;
                        ConsumerIndexHolder consumerIndexHolder = new ConsumerIndexHolder(this.producerIndex, this);
                        long j3 = this.timespan;
                        DisposableHelper.replace(this.timer, worker.schedulePeriodically(consumerIndexHolder, j3, j3, this.unit));
                    }
                } else {
                    this.count = j2;
                }
                if (leave(-1) == 0) {
                    return;
                }
            } else {
                this.queue.offer(NotificationLite.next(t2));
                if (!enter()) {
                    return;
                }
            }
            drainLoop();
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            Disposable disposableSchedulePeriodicallyDirect;
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                Observer<? super V> observer = this.downstream;
                observer.onSubscribe(this);
                if (this.cancelled) {
                    return;
                }
                UnicastSubject<T> unicastSubjectCreate = UnicastSubject.create(this.bufferSize);
                this.window = unicastSubjectCreate;
                observer.onNext(unicastSubjectCreate);
                ConsumerIndexHolder consumerIndexHolder = new ConsumerIndexHolder(this.producerIndex, this);
                if (this.restartTimerOnMaxSize) {
                    Scheduler.Worker worker = this.worker;
                    long j2 = this.timespan;
                    disposableSchedulePeriodicallyDirect = worker.schedulePeriodically(consumerIndexHolder, j2, j2, this.unit);
                } else {
                    Scheduler scheduler = this.scheduler;
                    long j3 = this.timespan;
                    disposableSchedulePeriodicallyDirect = scheduler.schedulePeriodicallyDirect(consumerIndexHolder, j3, j3, this.unit);
                }
                DisposableHelper.replace(this.timer, disposableSchedulePeriodicallyDirect);
            }
        }
    }

    public static final class WindowExactUnboundedObserver<T> extends QueueDrainObserver<T, Object, Observable<T>> implements Observer<T>, Disposable, Runnable {
        static final Object NEXT = new Object();
        final int bufferSize;
        final Scheduler scheduler;
        volatile boolean terminated;
        final AtomicReference<Disposable> timer;
        final long timespan;
        final TimeUnit unit;
        Disposable upstream;
        UnicastSubject<T> window;

        public WindowExactUnboundedObserver(Observer<? super Observable<T>> observer, long j2, TimeUnit timeUnit, Scheduler scheduler, int i2) {
            super(observer, new MpscLinkedQueue());
            this.timer = new AtomicReference<>();
            this.timespan = j2;
            this.unit = timeUnit;
            this.scheduler = scheduler;
            this.bufferSize = i2;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.cancelled = true;
        }

        public void disposeTimer() {
            DisposableHelper.dispose(this.timer);
        }

        /* JADX WARN: Code restructure failed: missing block: B:10:0x0026, code lost:
        
            r2.onError(r0);
         */
        /* JADX WARN: Code restructure failed: missing block: B:11:0x002a, code lost:
        
            r2.onComplete();
         */
        /* JADX WARN: Code restructure failed: missing block: B:12:0x002d, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:?, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:8:0x0019, code lost:
        
            r7.window = null;
            r0.clear();
            disposeTimer();
            r0 = r7.error;
         */
        /* JADX WARN: Code restructure failed: missing block: B:9:0x0024, code lost:
        
            if (r0 == null) goto L11;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v0, types: [io.reactivex.subjects.UnicastSubject<T>] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void drainLoop() {
            /*
                r7 = this;
                io.reactivex.internal.fuseable.SimplePlainQueue<U> r0 = r7.queue
                io.reactivex.internal.queue.MpscLinkedQueue r0 = (io.reactivex.internal.queue.MpscLinkedQueue) r0
                io.reactivex.Observer<? super V> r1 = r7.downstream
                io.reactivex.subjects.UnicastSubject<T> r2 = r7.window
                r3 = 1
            L9:
                boolean r4 = r7.terminated
                boolean r5 = r7.done
                java.lang.Object r6 = r0.poll()
                if (r5 == 0) goto L2e
                if (r6 == 0) goto L19
                java.lang.Object r5 = io.reactivex.internal.operators.observable.ObservableWindowTimed.WindowExactUnboundedObserver.NEXT
                if (r6 != r5) goto L2e
            L19:
                r1 = 0
                r7.window = r1
                r0.clear()
                r7.disposeTimer()
                java.lang.Throwable r0 = r7.error
                if (r0 == 0) goto L2a
                r2.onError(r0)
                goto L2d
            L2a:
                r2.onComplete()
            L2d:
                return
            L2e:
                if (r6 != 0) goto L38
                int r3 = -r3
                int r3 = r7.leave(r3)
                if (r3 != 0) goto L9
                return
            L38:
                java.lang.Object r5 = io.reactivex.internal.operators.observable.ObservableWindowTimed.WindowExactUnboundedObserver.NEXT
                if (r6 != r5) goto L53
                r2.onComplete()
                if (r4 != 0) goto L4d
                int r2 = r7.bufferSize
                io.reactivex.subjects.UnicastSubject r2 = io.reactivex.subjects.UnicastSubject.create(r2)
                r7.window = r2
                r1.onNext(r2)
                goto L9
            L4d:
                io.reactivex.disposables.Disposable r4 = r7.upstream
                r4.dispose()
                goto L9
            L53:
                java.lang.Object r4 = io.reactivex.internal.util.NotificationLite.getValue(r6)
                r2.onNext(r4)
                goto L9
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.ObservableWindowTimed.WindowExactUnboundedObserver.drainLoop():void");
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.done = true;
            if (enter()) {
                drainLoop();
            }
            disposeTimer();
            this.downstream.onComplete();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            if (enter()) {
                drainLoop();
            }
            disposeTimer();
            this.downstream.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t2) {
            if (this.terminated) {
                return;
            }
            if (fastEnter()) {
                this.window.onNext(t2);
                if (leave(-1) == 0) {
                    return;
                }
            } else {
                this.queue.offer(NotificationLite.next(t2));
                if (!enter()) {
                    return;
                }
            }
            drainLoop();
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.window = UnicastSubject.create(this.bufferSize);
                Observer<? super V> observer = this.downstream;
                observer.onSubscribe(this);
                observer.onNext(this.window);
                if (this.cancelled) {
                    return;
                }
                Scheduler scheduler = this.scheduler;
                long j2 = this.timespan;
                DisposableHelper.replace(this.timer, scheduler.schedulePeriodicallyDirect(this, j2, j2, this.unit));
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.cancelled) {
                this.terminated = true;
                disposeTimer();
            }
            this.queue.offer(NEXT);
            if (enter()) {
                drainLoop();
            }
        }
    }

    public static final class WindowSkipObserver<T> extends QueueDrainObserver<T, Object, Observable<T>> implements Disposable, Runnable {
        final int bufferSize;
        volatile boolean terminated;
        final long timeskip;
        final long timespan;
        final TimeUnit unit;
        Disposable upstream;
        final List<UnicastSubject<T>> windows;
        final Scheduler.Worker worker;

        public final class CompletionTask implements Runnable {

            /* renamed from: w, reason: collision with root package name */
            private final UnicastSubject<T> f27315w;

            public CompletionTask(UnicastSubject<T> unicastSubject) {
                this.f27315w = unicastSubject;
            }

            @Override // java.lang.Runnable
            public void run() {
                WindowSkipObserver.this.complete(this.f27315w);
            }
        }

        public static final class SubjectWork<T> {
            final boolean open;

            /* renamed from: w, reason: collision with root package name */
            final UnicastSubject<T> f27316w;

            public SubjectWork(UnicastSubject<T> unicastSubject, boolean z2) {
                this.f27316w = unicastSubject;
                this.open = z2;
            }
        }

        public WindowSkipObserver(Observer<? super Observable<T>> observer, long j2, long j3, TimeUnit timeUnit, Scheduler.Worker worker, int i2) {
            super(observer, new MpscLinkedQueue());
            this.timespan = j2;
            this.timeskip = j3;
            this.unit = timeUnit;
            this.worker = worker;
            this.bufferSize = i2;
            this.windows = new LinkedList();
        }

        public void complete(UnicastSubject<T> unicastSubject) {
            this.queue.offer(new SubjectWork(unicastSubject, false));
            if (enter()) {
                drainLoop();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.cancelled = true;
        }

        public void disposeWorker() {
            this.worker.dispose();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void drainLoop() {
            MpscLinkedQueue mpscLinkedQueue = (MpscLinkedQueue) this.queue;
            Observer<? super V> observer = this.downstream;
            List<UnicastSubject<T>> list = this.windows;
            int iLeave = 1;
            while (!this.terminated) {
                boolean z2 = this.done;
                Object objPoll = mpscLinkedQueue.poll();
                boolean z3 = objPoll == null;
                boolean z4 = objPoll instanceof SubjectWork;
                if (z2 && (z3 || z4)) {
                    mpscLinkedQueue.clear();
                    Throwable th = this.error;
                    if (th != null) {
                        Iterator<UnicastSubject<T>> it = list.iterator();
                        while (it.hasNext()) {
                            it.next().onError(th);
                        }
                    } else {
                        Iterator<UnicastSubject<T>> it2 = list.iterator();
                        while (it2.hasNext()) {
                            it2.next().onComplete();
                        }
                    }
                    disposeWorker();
                    list.clear();
                    return;
                }
                if (z3) {
                    iLeave = leave(-iLeave);
                    if (iLeave == 0) {
                        return;
                    }
                } else if (z4) {
                    SubjectWork subjectWork = (SubjectWork) objPoll;
                    if (!subjectWork.open) {
                        list.remove(subjectWork.f27316w);
                        subjectWork.f27316w.onComplete();
                        if (list.isEmpty() && this.cancelled) {
                            this.terminated = true;
                        }
                    } else if (!this.cancelled) {
                        UnicastSubject<T> unicastSubjectCreate = UnicastSubject.create(this.bufferSize);
                        list.add(unicastSubjectCreate);
                        observer.onNext(unicastSubjectCreate);
                        this.worker.schedule(new CompletionTask(unicastSubjectCreate), this.timespan, this.unit);
                    }
                } else {
                    Iterator<UnicastSubject<T>> it3 = list.iterator();
                    while (it3.hasNext()) {
                        it3.next().onNext(objPoll);
                    }
                }
            }
            this.upstream.dispose();
            disposeWorker();
            mpscLinkedQueue.clear();
            list.clear();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.done = true;
            if (enter()) {
                drainLoop();
            }
            this.downstream.onComplete();
            disposeWorker();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            if (enter()) {
                drainLoop();
            }
            this.downstream.onError(th);
            disposeWorker();
        }

        @Override // io.reactivex.Observer
        public void onNext(T t2) {
            if (fastEnter()) {
                Iterator<UnicastSubject<T>> it = this.windows.iterator();
                while (it.hasNext()) {
                    it.next().onNext(t2);
                }
                if (leave(-1) == 0) {
                    return;
                }
            } else {
                this.queue.offer(t2);
                if (!enter()) {
                    return;
                }
            }
            drainLoop();
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
                if (this.cancelled) {
                    return;
                }
                UnicastSubject<T> unicastSubjectCreate = UnicastSubject.create(this.bufferSize);
                this.windows.add(unicastSubjectCreate);
                this.downstream.onNext(unicastSubjectCreate);
                this.worker.schedule(new CompletionTask(unicastSubjectCreate), this.timespan, this.unit);
                Scheduler.Worker worker = this.worker;
                long j2 = this.timeskip;
                worker.schedulePeriodically(this, j2, j2, this.unit);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            SubjectWork subjectWork = new SubjectWork(UnicastSubject.create(this.bufferSize), true);
            if (!this.cancelled) {
                this.queue.offer(subjectWork);
            }
            if (enter()) {
                drainLoop();
            }
        }
    }

    public ObservableWindowTimed(ObservableSource<T> observableSource, long j2, long j3, TimeUnit timeUnit, Scheduler scheduler, long j4, int i2, boolean z2) {
        super(observableSource);
        this.timespan = j2;
        this.timeskip = j3;
        this.unit = timeUnit;
        this.scheduler = scheduler;
        this.maxSize = j4;
        this.bufferSize = i2;
        this.restartTimerOnMaxSize = z2;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super Observable<T>> observer) {
        SerializedObserver serializedObserver = new SerializedObserver(observer);
        long j2 = this.timespan;
        long j3 = this.timeskip;
        if (j2 != j3) {
            this.source.subscribe(new WindowSkipObserver(serializedObserver, j2, j3, this.unit, this.scheduler.createWorker(), this.bufferSize));
            return;
        }
        long j4 = this.maxSize;
        if (j4 == Long.MAX_VALUE) {
            this.source.subscribe(new WindowExactUnboundedObserver(serializedObserver, this.timespan, this.unit, this.scheduler, this.bufferSize));
        } else {
            this.source.subscribe(new WindowExactBoundedObserver(serializedObserver, j2, this.unit, this.scheduler, this.bufferSize, j4, this.restartTimerOnMaxSize));
        }
    }
}
