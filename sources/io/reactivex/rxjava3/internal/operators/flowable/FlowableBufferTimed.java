package io.reactivex.rxjava3.internal.operators.flowable;

import androidx.camera.view.j;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.queue.MpscLinkedQueue;
import io.reactivex.rxjava3.internal.subscribers.QueueDrainSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.QueueDrainHelper;
import io.reactivex.rxjava3.subscribers.SerializedSubscriber;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableBufferTimed<T, U extends Collection<? super T>> extends AbstractFlowableWithUpstream<T, U> {
    final Supplier<U> bufferSupplier;
    final int maxSize;
    final boolean restartTimerOnMaxSize;
    final Scheduler scheduler;
    final long timeskip;
    final long timespan;
    final TimeUnit unit;

    public static final class BufferExactBoundedSubscriber<T, U extends Collection<? super T>> extends QueueDrainSubscriber<T, U, U> implements Subscription, Runnable, Disposable {
        U buffer;
        final Supplier<U> bufferSupplier;
        long consumerIndex;
        final int maxSize;
        long producerIndex;
        final boolean restartTimerOnMaxSize;
        Disposable timer;
        final long timespan;
        final TimeUnit unit;
        Subscription upstream;

        /* renamed from: w, reason: collision with root package name */
        final Scheduler.Worker f27337w;

        public BufferExactBoundedSubscriber(Subscriber<? super U> actual, Supplier<U> bufferSupplier, long timespan, TimeUnit unit, int maxSize, boolean restartOnMaxSize, Scheduler.Worker w2) {
            super(actual, new MpscLinkedQueue());
            this.bufferSupplier = bufferSupplier;
            this.timespan = timespan;
            this.unit = unit;
            this.maxSize = maxSize;
            this.restartTimerOnMaxSize = restartOnMaxSize;
            this.f27337w = w2;
        }

        @Override // io.reactivex.rxjava3.internal.subscribers.QueueDrainSubscriber, io.reactivex.rxjava3.internal.util.QueueDrain
        public /* bridge */ /* synthetic */ boolean accept(Subscriber a3, Object v2) {
            return accept((Subscriber<? super Subscriber>) a3, (Subscriber) v2);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            synchronized (this) {
                this.buffer = null;
            }
            this.upstream.cancel();
            this.f27337w.dispose();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.f27337w.isDisposed();
        }

        @Override // org.reactivestreams.Subscriber
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
                    QueueDrainHelper.drainMaxLoop(this.queue, this.downstream, false, this, this);
                }
                this.f27337w.dispose();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            synchronized (this) {
                this.buffer = null;
            }
            this.downstream.onError(t2);
            this.f27337w.dispose();
        }

        @Override // org.reactivestreams.Subscriber
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
                fastPathOrderedEmitMax(u2, false, this);
                try {
                    U u3 = this.bufferSupplier.get();
                    Objects.requireNonNull(u3, "The supplied buffer is null");
                    U u4 = u3;
                    synchronized (this) {
                        this.buffer = u4;
                        this.consumerIndex++;
                    }
                    if (this.restartTimerOnMaxSize) {
                        Scheduler.Worker worker = this.f27337w;
                        long j2 = this.timespan;
                        this.timer = worker.schedulePeriodically(this, j2, j2, this.unit);
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    cancel();
                    this.downstream.onError(th);
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                try {
                    U u2 = this.bufferSupplier.get();
                    Objects.requireNonNull(u2, "The supplied buffer is null");
                    this.buffer = u2;
                    this.downstream.onSubscribe(this);
                    Scheduler.Worker worker = this.f27337w;
                    long j2 = this.timespan;
                    this.timer = worker.schedulePeriodically(this, j2, j2, this.unit);
                    s2.request(Long.MAX_VALUE);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.f27337w.dispose();
                    s2.cancel();
                    EmptySubscription.error(th, this.downstream);
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            requested(n2);
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                U u2 = this.bufferSupplier.get();
                Objects.requireNonNull(u2, "The supplied buffer is null");
                U u3 = u2;
                synchronized (this) {
                    U u4 = this.buffer;
                    if (u4 != null && this.producerIndex == this.consumerIndex) {
                        this.buffer = u3;
                        fastPathOrderedEmitMax(u4, false, this);
                    }
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                cancel();
                this.downstream.onError(th);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public boolean accept(Subscriber<? super U> a3, U v2) {
            a3.onNext(v2);
            return true;
        }
    }

    public static final class BufferExactUnboundedSubscriber<T, U extends Collection<? super T>> extends QueueDrainSubscriber<T, U, U> implements Subscription, Runnable, Disposable {
        U buffer;
        final Supplier<U> bufferSupplier;
        final Scheduler scheduler;
        final AtomicReference<Disposable> timer;
        final long timespan;
        final TimeUnit unit;
        Subscription upstream;

        public BufferExactUnboundedSubscriber(Subscriber<? super U> actual, Supplier<U> bufferSupplier, long timespan, TimeUnit unit, Scheduler scheduler) {
            super(actual, new MpscLinkedQueue());
            this.timer = new AtomicReference<>();
            this.bufferSupplier = bufferSupplier;
            this.timespan = timespan;
            this.unit = unit;
            this.scheduler = scheduler;
        }

        @Override // io.reactivex.rxjava3.internal.subscribers.QueueDrainSubscriber, io.reactivex.rxjava3.internal.util.QueueDrain
        public /* bridge */ /* synthetic */ boolean accept(Subscriber a3, Object v2) {
            return accept((Subscriber<? super Subscriber>) a3, (Subscriber) v2);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            this.upstream.cancel();
            DisposableHelper.dispose(this.timer);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            cancel();
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.timer.get() == DisposableHelper.DISPOSED;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            DisposableHelper.dispose(this.timer);
            synchronized (this) {
                U u2 = this.buffer;
                if (u2 == null) {
                    return;
                }
                this.buffer = null;
                this.queue.offer(u2);
                this.done = true;
                if (enter()) {
                    QueueDrainHelper.drainMaxLoop(this.queue, this.downstream, false, null, this);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            DisposableHelper.dispose(this.timer);
            synchronized (this) {
                this.buffer = null;
            }
            this.downstream.onError(t2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            synchronized (this) {
                U u2 = this.buffer;
                if (u2 != null) {
                    u2.add(t2);
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                try {
                    U u2 = this.bufferSupplier.get();
                    Objects.requireNonNull(u2, "The supplied buffer is null");
                    this.buffer = u2;
                    this.downstream.onSubscribe(this);
                    if (this.cancelled) {
                        return;
                    }
                    s2.request(Long.MAX_VALUE);
                    Scheduler scheduler = this.scheduler;
                    long j2 = this.timespan;
                    Disposable disposableSchedulePeriodicallyDirect = scheduler.schedulePeriodicallyDirect(this, j2, j2, this.unit);
                    if (j.a(this.timer, null, disposableSchedulePeriodicallyDirect)) {
                        return;
                    }
                    disposableSchedulePeriodicallyDirect.dispose();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    cancel();
                    EmptySubscription.error(th, this.downstream);
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            requested(n2);
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                U u2 = this.bufferSupplier.get();
                Objects.requireNonNull(u2, "The supplied buffer is null");
                U u3 = u2;
                synchronized (this) {
                    U u4 = this.buffer;
                    if (u4 == null) {
                        return;
                    }
                    this.buffer = u3;
                    fastPathEmitMax(u4, false, this);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                cancel();
                this.downstream.onError(th);
            }
        }

        public boolean accept(Subscriber<? super U> a3, U v2) {
            this.downstream.onNext(v2);
            return true;
        }
    }

    public static final class BufferSkipBoundedSubscriber<T, U extends Collection<? super T>> extends QueueDrainSubscriber<T, U, U> implements Subscription, Runnable {
        final Supplier<U> bufferSupplier;
        final List<U> buffers;
        final long timeskip;
        final long timespan;
        final TimeUnit unit;
        Subscription upstream;

        /* renamed from: w, reason: collision with root package name */
        final Scheduler.Worker f27338w;

        public final class RemoveFromBuffer implements Runnable {
            private final U buffer;

            public RemoveFromBuffer(U buffer) {
                this.buffer = buffer;
            }

            @Override // java.lang.Runnable
            public void run() {
                synchronized (BufferSkipBoundedSubscriber.this) {
                    BufferSkipBoundedSubscriber.this.buffers.remove(this.buffer);
                }
                BufferSkipBoundedSubscriber bufferSkipBoundedSubscriber = BufferSkipBoundedSubscriber.this;
                bufferSkipBoundedSubscriber.fastPathOrderedEmitMax(this.buffer, false, bufferSkipBoundedSubscriber.f27338w);
            }
        }

        public BufferSkipBoundedSubscriber(Subscriber<? super U> actual, Supplier<U> bufferSupplier, long timespan, long timeskip, TimeUnit unit, Scheduler.Worker w2) {
            super(actual, new MpscLinkedQueue());
            this.bufferSupplier = bufferSupplier;
            this.timespan = timespan;
            this.timeskip = timeskip;
            this.unit = unit;
            this.f27338w = w2;
            this.buffers = new LinkedList();
        }

        @Override // io.reactivex.rxjava3.internal.subscribers.QueueDrainSubscriber, io.reactivex.rxjava3.internal.util.QueueDrain
        public /* bridge */ /* synthetic */ boolean accept(Subscriber a3, Object v2) {
            return accept((Subscriber<? super Subscriber>) a3, (Subscriber) v2);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            this.upstream.cancel();
            this.f27338w.dispose();
            clear();
        }

        public void clear() {
            synchronized (this) {
                this.buffers.clear();
            }
        }

        @Override // org.reactivestreams.Subscriber
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
                QueueDrainHelper.drainMaxLoop(this.queue, this.downstream, false, this.f27338w, this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            this.done = true;
            this.f27338w.dispose();
            clear();
            this.downstream.onError(t2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            synchronized (this) {
                Iterator<U> it = this.buffers.iterator();
                while (it.hasNext()) {
                    it.next().add(t2);
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                try {
                    U u2 = this.bufferSupplier.get();
                    Objects.requireNonNull(u2, "The supplied buffer is null");
                    U u3 = u2;
                    this.buffers.add(u3);
                    this.downstream.onSubscribe(this);
                    s2.request(Long.MAX_VALUE);
                    Scheduler.Worker worker = this.f27338w;
                    long j2 = this.timeskip;
                    worker.schedulePeriodically(this, j2, j2, this.unit);
                    this.f27338w.schedule(new RemoveFromBuffer(u3), this.timespan, this.unit);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.f27338w.dispose();
                    s2.cancel();
                    EmptySubscription.error(th, this.downstream);
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            requested(n2);
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.cancelled) {
                return;
            }
            try {
                U u2 = this.bufferSupplier.get();
                Objects.requireNonNull(u2, "The supplied buffer is null");
                U u3 = u2;
                synchronized (this) {
                    if (this.cancelled) {
                        return;
                    }
                    this.buffers.add(u3);
                    this.f27338w.schedule(new RemoveFromBuffer(u3), this.timespan, this.unit);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                cancel();
                this.downstream.onError(th);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public boolean accept(Subscriber<? super U> a3, U v2) {
            a3.onNext(v2);
            return true;
        }
    }

    public FlowableBufferTimed(Flowable<T> source, long timespan, long timeskip, TimeUnit unit, Scheduler scheduler, Supplier<U> bufferSupplier, int maxSize, boolean restartTimerOnMaxSize) {
        super(source);
        this.timespan = timespan;
        this.timeskip = timeskip;
        this.unit = unit;
        this.scheduler = scheduler;
        this.bufferSupplier = bufferSupplier;
        this.maxSize = maxSize;
        this.restartTimerOnMaxSize = restartTimerOnMaxSize;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super U> s2) {
        if (this.timespan == this.timeskip && this.maxSize == Integer.MAX_VALUE) {
            this.source.subscribe((FlowableSubscriber) new BufferExactUnboundedSubscriber(new SerializedSubscriber(s2), this.bufferSupplier, this.timespan, this.unit, this.scheduler));
            return;
        }
        Scheduler.Worker workerCreateWorker = this.scheduler.createWorker();
        if (this.timespan == this.timeskip) {
            this.source.subscribe((FlowableSubscriber) new BufferExactBoundedSubscriber(new SerializedSubscriber(s2), this.bufferSupplier, this.timespan, this.unit, this.maxSize, this.restartTimerOnMaxSize, workerCreateWorker));
        } else {
            this.source.subscribe((FlowableSubscriber) new BufferSkipBoundedSubscriber(new SerializedSubscriber(s2), this.bufferSupplier, this.timespan, this.timeskip, this.unit, workerCreateWorker));
        }
    }
}
