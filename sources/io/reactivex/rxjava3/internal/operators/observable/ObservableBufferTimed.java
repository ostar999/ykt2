package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import io.reactivex.rxjava3.internal.observers.QueueDrainObserver;
import io.reactivex.rxjava3.internal.queue.MpscLinkedQueue;
import io.reactivex.rxjava3.internal.util.QueueDrainHelper;
import io.reactivex.rxjava3.observers.SerializedObserver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class ObservableBufferTimed<T, U extends Collection<? super T>> extends AbstractObservableWithUpstream<T, U> {
    final Supplier<U> bufferSupplier;
    final int maxSize;
    final boolean restartTimerOnMaxSize;
    final Scheduler scheduler;
    final long timeskip;
    final long timespan;
    final TimeUnit unit;

    public static final class BufferExactBoundedObserver<T, U extends Collection<? super T>> extends QueueDrainObserver<T, U, U> implements Runnable, Disposable {
        U buffer;
        final Supplier<U> bufferSupplier;
        long consumerIndex;
        final int maxSize;
        long producerIndex;
        final boolean restartTimerOnMaxSize;
        Disposable timer;
        final long timespan;
        final TimeUnit unit;
        Disposable upstream;

        /* renamed from: w, reason: collision with root package name */
        final Scheduler.Worker f27351w;

        public BufferExactBoundedObserver(Observer<? super U> actual, Supplier<U> bufferSupplier, long timespan, TimeUnit unit, int maxSize, boolean restartOnMaxSize, Scheduler.Worker w2) {
            super(actual, new MpscLinkedQueue());
            this.bufferSupplier = bufferSupplier;
            this.timespan = timespan;
            this.unit = unit;
            this.maxSize = maxSize;
            this.restartTimerOnMaxSize = restartOnMaxSize;
            this.f27351w = w2;
        }

        @Override // io.reactivex.rxjava3.internal.observers.QueueDrainObserver, io.reactivex.rxjava3.internal.util.ObservableQueueDrain
        public /* bridge */ /* synthetic */ void accept(Observer a3, Object v2) {
            accept((Observer<? super Observer>) a3, (Observer) v2);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.upstream.dispose();
            this.f27351w.dispose();
            synchronized (this) {
                this.buffer = null;
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            U u2;
            this.f27351w.dispose();
            synchronized (this) {
                u2 = this.buffer;
                this.buffer = null;
            }
            if (u2 != null) {
                this.queue.offer(u2);
                this.done = true;
                if (enter()) {
                    QueueDrainHelper.drainLoop(this.queue, this.downstream, false, this, this);
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            synchronized (this) {
                this.buffer = null;
            }
            this.downstream.onError(t2);
            this.f27351w.dispose();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            synchronized (this) {
                U u2 = this.buffer;
                if (u2 == null) {
                    return;
                }
                u2.add(t2);
                if (u2.size() < this.maxSize) {
                    return;
                }
                this.buffer = null;
                this.producerIndex++;
                if (this.restartTimerOnMaxSize) {
                    this.timer.dispose();
                }
                fastPathOrderedEmit(u2, false, this);
                try {
                    U u3 = this.bufferSupplier.get();
                    Objects.requireNonNull(u3, "The buffer supplied is null");
                    U u4 = u3;
                    synchronized (this) {
                        this.buffer = u4;
                        this.consumerIndex++;
                    }
                    if (this.restartTimerOnMaxSize) {
                        Scheduler.Worker worker = this.f27351w;
                        long j2 = this.timespan;
                        this.timer = worker.schedulePeriodically(this, j2, j2, this.unit);
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.downstream.onError(th);
                    dispose();
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                try {
                    U u2 = this.bufferSupplier.get();
                    Objects.requireNonNull(u2, "The buffer supplied is null");
                    this.buffer = u2;
                    this.downstream.onSubscribe(this);
                    Scheduler.Worker worker = this.f27351w;
                    long j2 = this.timespan;
                    this.timer = worker.schedulePeriodically(this, j2, j2, this.unit);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    d3.dispose();
                    EmptyDisposable.error(th, this.downstream);
                    this.f27351w.dispose();
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                U u2 = this.bufferSupplier.get();
                Objects.requireNonNull(u2, "The bufferSupplier returned a null buffer");
                U u3 = u2;
                synchronized (this) {
                    U u4 = this.buffer;
                    if (u4 != null && this.producerIndex == this.consumerIndex) {
                        this.buffer = u3;
                        fastPathOrderedEmit(u4, false, this);
                    }
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                dispose();
                this.downstream.onError(th);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void accept(Observer<? super U> a3, U v2) {
            a3.onNext(v2);
        }
    }

    public static final class BufferExactUnboundedObserver<T, U extends Collection<? super T>> extends QueueDrainObserver<T, U, U> implements Runnable, Disposable {
        U buffer;
        final Supplier<U> bufferSupplier;
        final Scheduler scheduler;
        final AtomicReference<Disposable> timer;
        final long timespan;
        final TimeUnit unit;
        Disposable upstream;

        public BufferExactUnboundedObserver(Observer<? super U> actual, Supplier<U> bufferSupplier, long timespan, TimeUnit unit, Scheduler scheduler) {
            super(actual, new MpscLinkedQueue());
            this.timer = new AtomicReference<>();
            this.bufferSupplier = bufferSupplier;
            this.timespan = timespan;
            this.unit = unit;
            this.scheduler = scheduler;
        }

        @Override // io.reactivex.rxjava3.internal.observers.QueueDrainObserver, io.reactivex.rxjava3.internal.util.ObservableQueueDrain
        public /* bridge */ /* synthetic */ void accept(Observer a3, Object v2) {
            accept((Observer<? super Observer>) a3, (Observer) v2);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this.timer);
            this.upstream.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.timer.get() == DisposableHelper.DISPOSED;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            U u2;
            synchronized (this) {
                u2 = this.buffer;
                this.buffer = null;
            }
            if (u2 != null) {
                this.queue.offer(u2);
                this.done = true;
                if (enter()) {
                    QueueDrainHelper.drainLoop(this.queue, this.downstream, false, null, this);
                }
            }
            DisposableHelper.dispose(this.timer);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            synchronized (this) {
                this.buffer = null;
            }
            this.downstream.onError(t2);
            DisposableHelper.dispose(this.timer);
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            synchronized (this) {
                U u2 = this.buffer;
                if (u2 == null) {
                    return;
                }
                u2.add(t2);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                try {
                    U u2 = this.bufferSupplier.get();
                    Objects.requireNonNull(u2, "The buffer supplied is null");
                    this.buffer = u2;
                    this.downstream.onSubscribe(this);
                    if (DisposableHelper.isDisposed(this.timer.get())) {
                        return;
                    }
                    Scheduler scheduler = this.scheduler;
                    long j2 = this.timespan;
                    DisposableHelper.set(this.timer, scheduler.schedulePeriodicallyDirect(this, j2, j2, this.unit));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    dispose();
                    EmptyDisposable.error(th, this.downstream);
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            U u2;
            try {
                U u3 = this.bufferSupplier.get();
                Objects.requireNonNull(u3, "The bufferSupplier returned a null buffer");
                U u4 = u3;
                synchronized (this) {
                    u2 = this.buffer;
                    if (u2 != null) {
                        this.buffer = u4;
                    }
                }
                if (u2 == null) {
                    DisposableHelper.dispose(this.timer);
                } else {
                    fastPathEmit(u2, false, this);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
                dispose();
            }
        }

        public void accept(Observer<? super U> a3, U v2) {
            this.downstream.onNext(v2);
        }
    }

    public static final class BufferSkipBoundedObserver<T, U extends Collection<? super T>> extends QueueDrainObserver<T, U, U> implements Runnable, Disposable {
        final Supplier<U> bufferSupplier;
        final List<U> buffers;
        final long timeskip;
        final long timespan;
        final TimeUnit unit;
        Disposable upstream;

        /* renamed from: w, reason: collision with root package name */
        final Scheduler.Worker f27352w;

        public final class RemoveFromBuffer implements Runnable {

            /* renamed from: b, reason: collision with root package name */
            private final U f27353b;

            public RemoveFromBuffer(U b3) {
                this.f27353b = b3;
            }

            @Override // java.lang.Runnable
            public void run() {
                synchronized (BufferSkipBoundedObserver.this) {
                    BufferSkipBoundedObserver.this.buffers.remove(this.f27353b);
                }
                BufferSkipBoundedObserver bufferSkipBoundedObserver = BufferSkipBoundedObserver.this;
                bufferSkipBoundedObserver.fastPathOrderedEmit(this.f27353b, false, bufferSkipBoundedObserver.f27352w);
            }
        }

        public final class RemoveFromBufferEmit implements Runnable {
            private final U buffer;

            public RemoveFromBufferEmit(U buffer) {
                this.buffer = buffer;
            }

            @Override // java.lang.Runnable
            public void run() {
                synchronized (BufferSkipBoundedObserver.this) {
                    BufferSkipBoundedObserver.this.buffers.remove(this.buffer);
                }
                BufferSkipBoundedObserver bufferSkipBoundedObserver = BufferSkipBoundedObserver.this;
                bufferSkipBoundedObserver.fastPathOrderedEmit(this.buffer, false, bufferSkipBoundedObserver.f27352w);
            }
        }

        public BufferSkipBoundedObserver(Observer<? super U> actual, Supplier<U> bufferSupplier, long timespan, long timeskip, TimeUnit unit, Scheduler.Worker w2) {
            super(actual, new MpscLinkedQueue());
            this.bufferSupplier = bufferSupplier;
            this.timespan = timespan;
            this.timeskip = timeskip;
            this.unit = unit;
            this.f27352w = w2;
            this.buffers = new LinkedList();
        }

        @Override // io.reactivex.rxjava3.internal.observers.QueueDrainObserver, io.reactivex.rxjava3.internal.util.ObservableQueueDrain
        public /* bridge */ /* synthetic */ void accept(Observer a3, Object v2) {
            accept((Observer<? super Observer>) a3, (Observer) v2);
        }

        public void clear() {
            synchronized (this) {
                this.buffers.clear();
            }
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            clear();
            this.upstream.dispose();
            this.f27352w.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onComplete() {
            ArrayList arrayList;
            synchronized (this) {
                arrayList = new ArrayList(this.buffers);
                this.buffers.clear();
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this.queue.offer((Collection) it.next());
            }
            this.done = true;
            if (enter()) {
                QueueDrainHelper.drainLoop(this.queue, this.downstream, false, this.f27352w, this);
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onError(Throwable t2) {
            this.done = true;
            clear();
            this.downstream.onError(t2);
            this.f27352w.dispose();
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(T t2) {
            synchronized (this) {
                Iterator<U> it = this.buffers.iterator();
                while (it.hasNext()) {
                    it.next().add(t2);
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onSubscribe(Disposable d3) {
            if (DisposableHelper.validate(this.upstream, d3)) {
                this.upstream = d3;
                try {
                    U u2 = this.bufferSupplier.get();
                    Objects.requireNonNull(u2, "The buffer supplied is null");
                    U u3 = u2;
                    this.buffers.add(u3);
                    this.downstream.onSubscribe(this);
                    Scheduler.Worker worker = this.f27352w;
                    long j2 = this.timeskip;
                    worker.schedulePeriodically(this, j2, j2, this.unit);
                    this.f27352w.schedule(new RemoveFromBufferEmit(u3), this.timespan, this.unit);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    d3.dispose();
                    EmptyDisposable.error(th, this.downstream);
                    this.f27352w.dispose();
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.cancelled) {
                return;
            }
            try {
                U u2 = this.bufferSupplier.get();
                Objects.requireNonNull(u2, "The bufferSupplier returned a null buffer");
                U u3 = u2;
                synchronized (this) {
                    if (this.cancelled) {
                        return;
                    }
                    this.buffers.add(u3);
                    this.f27352w.schedule(new RemoveFromBuffer(u3), this.timespan, this.unit);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.downstream.onError(th);
                dispose();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void accept(Observer<? super U> a3, U v2) {
            a3.onNext(v2);
        }
    }

    public ObservableBufferTimed(ObservableSource<T> source, long timespan, long timeskip, TimeUnit unit, Scheduler scheduler, Supplier<U> bufferSupplier, int maxSize, boolean restartTimerOnMaxSize) {
        super(source);
        this.timespan = timespan;
        this.timeskip = timeskip;
        this.unit = unit;
        this.scheduler = scheduler;
        this.bufferSupplier = bufferSupplier;
        this.maxSize = maxSize;
        this.restartTimerOnMaxSize = restartTimerOnMaxSize;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super U> t2) {
        if (this.timespan == this.timeskip && this.maxSize == Integer.MAX_VALUE) {
            this.source.subscribe(new BufferExactUnboundedObserver(new SerializedObserver(t2), this.bufferSupplier, this.timespan, this.unit, this.scheduler));
            return;
        }
        Scheduler.Worker workerCreateWorker = this.scheduler.createWorker();
        if (this.timespan == this.timeskip) {
            this.source.subscribe(new BufferExactBoundedObserver(new SerializedObserver(t2), this.bufferSupplier, this.timespan, this.unit, this.maxSize, this.restartTimerOnMaxSize, workerCreateWorker));
        } else {
            this.source.subscribe(new BufferSkipBoundedObserver(new SerializedObserver(t2), this.bufferSupplier, this.timespan, this.timeskip, this.unit, workerCreateWorker));
        }
    }
}
