package io.reactivex.rxjava3.internal.operators.flowable;

import androidx.camera.view.j;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.operators.QueueSubscription;
import io.reactivex.rxjava3.operators.SimplePlainQueue;
import io.reactivex.rxjava3.operators.SimpleQueue;
import io.reactivex.rxjava3.operators.SpscArrayQueue;
import io.reactivex.rxjava3.operators.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableFlatMap<T, U> extends AbstractFlowableWithUpstream<T, U> {
    final int bufferSize;
    final boolean delayErrors;
    final Function<? super T, ? extends Publisher<? extends U>> mapper;
    final int maxConcurrency;

    public static final class InnerSubscriber<T, U> extends AtomicReference<Subscription> implements FlowableSubscriber<U>, Disposable {
        private static final long serialVersionUID = -4606175640614850599L;
        final int bufferSize;
        volatile boolean done;
        int fusionMode;
        final long id;
        final int limit;
        final MergeSubscriber<T, U> parent;
        long produced;
        volatile SimpleQueue<U> queue;

        public InnerSubscriber(MergeSubscriber<T, U> parent, int bufferSize, long id) {
            this.id = id;
            this.parent = parent;
            this.bufferSize = bufferSize;
            this.limit = bufferSize >> 2;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            SubscriptionHelper.cancel(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get() == SubscriptionHelper.CANCELLED;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            this.parent.drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            lazySet(SubscriptionHelper.CANCELLED);
            this.parent.innerError(this, t2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(U t2) {
            if (this.fusionMode != 2) {
                this.parent.tryEmit(t2, this);
            } else {
                this.parent.drain();
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.setOnce(this, s2)) {
                if (s2 instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) s2;
                    int iRequestFusion = queueSubscription.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.fusionMode = iRequestFusion;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.parent.drain();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.fusionMode = iRequestFusion;
                        this.queue = queueSubscription;
                    }
                }
                s2.request(this.bufferSize);
            }
        }

        public void requestMore(long n2) {
            if (this.fusionMode != 1) {
                long j2 = this.produced + n2;
                if (j2 < this.limit) {
                    this.produced = j2;
                } else {
                    this.produced = 0L;
                    get().request(j2);
                }
            }
        }
    }

    public static final class MergeSubscriber<T, U> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -2117620485640801370L;
        final int bufferSize;
        volatile boolean cancelled;
        final boolean delayErrors;
        volatile boolean done;
        final Subscriber<? super U> downstream;
        final AtomicThrowable errors = new AtomicThrowable();
        long lastId;
        int lastIndex;
        final Function<? super T, ? extends Publisher<? extends U>> mapper;
        final int maxConcurrency;
        volatile SimplePlainQueue<U> queue;
        final AtomicLong requested;
        int scalarEmitted;
        final int scalarLimit;
        final AtomicReference<InnerSubscriber<?, ?>[]> subscribers;
        long uniqueId;
        Subscription upstream;
        static final InnerSubscriber<?, ?>[] EMPTY = new InnerSubscriber[0];
        static final InnerSubscriber<?, ?>[] CANCELLED = new InnerSubscriber[0];

        public MergeSubscriber(Subscriber<? super U> actual, Function<? super T, ? extends Publisher<? extends U>> mapper, boolean delayErrors, int maxConcurrency, int bufferSize) {
            AtomicReference<InnerSubscriber<?, ?>[]> atomicReference = new AtomicReference<>();
            this.subscribers = atomicReference;
            this.requested = new AtomicLong();
            this.downstream = actual;
            this.mapper = mapper;
            this.delayErrors = delayErrors;
            this.maxConcurrency = maxConcurrency;
            this.bufferSize = bufferSize;
            this.scalarLimit = Math.max(1, maxConcurrency >> 1);
            atomicReference.lazySet(EMPTY);
        }

        public boolean addInner(InnerSubscriber<T, U> inner) {
            InnerSubscriber<?, ?>[] innerSubscriberArr;
            InnerSubscriber[] innerSubscriberArr2;
            do {
                innerSubscriberArr = this.subscribers.get();
                if (innerSubscriberArr == CANCELLED) {
                    inner.dispose();
                    return false;
                }
                int length = innerSubscriberArr.length;
                innerSubscriberArr2 = new InnerSubscriber[length + 1];
                System.arraycopy(innerSubscriberArr, 0, innerSubscriberArr2, 0, length);
                innerSubscriberArr2[length] = inner;
            } while (!j.a(this.subscribers, innerSubscriberArr, innerSubscriberArr2));
            return true;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SimplePlainQueue<U> simplePlainQueue;
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.upstream.cancel();
            disposeAll();
            if (getAndIncrement() != 0 || (simplePlainQueue = this.queue) == null) {
                return;
            }
            simplePlainQueue.clear();
        }

        public boolean checkTerminate() {
            if (this.cancelled) {
                clearScalarQueue();
                return true;
            }
            if (this.delayErrors || this.errors.get() == null) {
                return false;
            }
            clearScalarQueue();
            this.errors.tryTerminateConsumer(this.downstream);
            return true;
        }

        public void clearScalarQueue() {
            SimplePlainQueue<U> simplePlainQueue = this.queue;
            if (simplePlainQueue != null) {
                simplePlainQueue.clear();
            }
        }

        public void disposeAll() {
            AtomicReference<InnerSubscriber<?, ?>[]> atomicReference = this.subscribers;
            InnerSubscriber<?, ?>[] innerSubscriberArr = CANCELLED;
            InnerSubscriber<?, ?>[] andSet = atomicReference.getAndSet(innerSubscriberArr);
            if (andSet != innerSubscriberArr) {
                for (InnerSubscriber<?, ?> innerSubscriber : andSet) {
                    innerSubscriber.dispose();
                }
                this.errors.tryTerminateAndReport();
            }
        }

        public void drain() {
            if (getAndIncrement() == 0) {
                drainLoop();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void drainLoop() {
            long j2;
            long j3;
            long j4;
            boolean z2;
            int i2;
            int i3;
            long j5;
            long j6;
            Subscriber<? super U> subscriber = this.downstream;
            int iAddAndGet = 1;
            while (!checkTerminate()) {
                SimplePlainQueue<U> simplePlainQueue = this.queue;
                long jAddAndGet = this.requested.get();
                boolean z3 = jAddAndGet == Long.MAX_VALUE;
                long j7 = 0;
                if (simplePlainQueue != null) {
                    long j8 = 0;
                    j2 = 0;
                    while (jAddAndGet != 0) {
                        U uPoll = simplePlainQueue.poll();
                        if (checkTerminate()) {
                            return;
                        }
                        if (uPoll == null) {
                            break;
                        }
                        subscriber.onNext(uPoll);
                        j2++;
                        j8++;
                        jAddAndGet--;
                    }
                    if (j8 != 0) {
                        jAddAndGet = z3 ? Long.MAX_VALUE : this.requested.addAndGet(-j8);
                    }
                } else {
                    j2 = 0;
                }
                boolean z4 = this.done;
                SimplePlainQueue<U> simplePlainQueue2 = this.queue;
                InnerSubscriber<?, ?>[] innerSubscriberArr = this.subscribers.get();
                int length = innerSubscriberArr.length;
                if (z4 && ((simplePlainQueue2 == null || simplePlainQueue2.isEmpty()) && length == 0)) {
                    this.errors.tryTerminateConsumer(this.downstream);
                    return;
                }
                int i4 = iAddAndGet;
                if (length != 0) {
                    long j9 = this.lastId;
                    int i5 = this.lastIndex;
                    if (length <= i5 || innerSubscriberArr[i5].id != j9) {
                        if (length <= i5) {
                            i5 = 0;
                        }
                        for (int i6 = 0; i6 < length && innerSubscriberArr[i5].id != j9; i6++) {
                            i5++;
                            if (i5 == length) {
                                i5 = 0;
                            }
                        }
                        this.lastIndex = i5;
                        this.lastId = innerSubscriberArr[i5].id;
                    }
                    int i7 = i5;
                    boolean z5 = false;
                    int i8 = 0;
                    while (true) {
                        if (i8 >= length) {
                            z2 = z5;
                            break;
                        }
                        if (checkTerminate()) {
                            return;
                        }
                        InnerSubscriber<T, U> innerSubscriber = innerSubscriberArr[i7];
                        Object obj = null;
                        while (true) {
                            SimpleQueue<U> simpleQueue = innerSubscriber.queue;
                            if (simpleQueue == null) {
                                i2 = length;
                                break;
                            }
                            i2 = length;
                            Object obj2 = obj;
                            long j10 = j7;
                            while (true) {
                                if (jAddAndGet == j7) {
                                    j5 = j7;
                                    break;
                                }
                                if (checkTerminate()) {
                                    return;
                                }
                                try {
                                    U uPoll2 = simpleQueue.poll();
                                    if (uPoll2 == null) {
                                        obj2 = uPoll2;
                                        j5 = 0;
                                        break;
                                    } else {
                                        subscriber.onNext(uPoll2);
                                        jAddAndGet--;
                                        j10++;
                                        obj2 = uPoll2;
                                        j7 = 0;
                                    }
                                } catch (Throwable th) {
                                    Exceptions.throwIfFatal(th);
                                    innerSubscriber.dispose();
                                    this.errors.tryAddThrowableOrReport(th);
                                    if (!this.delayErrors) {
                                        this.upstream.cancel();
                                    }
                                    if (checkTerminate()) {
                                        return;
                                    }
                                    removeInner(innerSubscriber);
                                    i8++;
                                    i3 = i2;
                                    z5 = true;
                                }
                            }
                            if (j10 != j5) {
                                jAddAndGet = !z3 ? this.requested.addAndGet(-j10) : Long.MAX_VALUE;
                                innerSubscriber.requestMore(j10);
                                j6 = 0;
                            } else {
                                j6 = j5;
                            }
                            if (jAddAndGet == j6 || obj2 == null) {
                                break;
                            }
                            length = i2;
                            obj = obj2;
                            j7 = 0;
                        }
                        boolean z6 = innerSubscriber.done;
                        SimpleQueue<U> simpleQueue2 = innerSubscriber.queue;
                        if (z6 && (simpleQueue2 == null || simpleQueue2.isEmpty())) {
                            removeInner(innerSubscriber);
                            if (checkTerminate()) {
                                return;
                            }
                            j2++;
                            z5 = true;
                        }
                        if (jAddAndGet == 0) {
                            z2 = z5;
                            break;
                        }
                        i7++;
                        i3 = i2;
                        if (i7 == i3) {
                            i7 = 0;
                        }
                        i8++;
                        length = i3;
                        j7 = 0;
                    }
                    this.lastIndex = i7;
                    this.lastId = innerSubscriberArr[i7].id;
                    j4 = j2;
                    j3 = 0;
                } else {
                    j3 = 0;
                    j4 = j2;
                    z2 = false;
                }
                if (j4 != j3 && !this.cancelled) {
                    this.upstream.request(j4);
                }
                if (z2) {
                    iAddAndGet = i4;
                } else {
                    iAddAndGet = addAndGet(-i4);
                    if (iAddAndGet == 0) {
                        return;
                    }
                }
            }
        }

        public SimpleQueue<U> getMainQueue() {
            SimplePlainQueue<U> spscLinkedArrayQueue = this.queue;
            if (spscLinkedArrayQueue == null) {
                spscLinkedArrayQueue = this.maxConcurrency == Integer.MAX_VALUE ? new SpscLinkedArrayQueue<>(this.bufferSize) : new SpscArrayQueue<>(this.maxConcurrency);
                this.queue = spscLinkedArrayQueue;
            }
            return spscLinkedArrayQueue;
        }

        public void innerError(InnerSubscriber<T, U> inner, Throwable t2) {
            if (this.errors.tryAddThrowableOrReport(t2)) {
                inner.done = true;
                if (!this.delayErrors) {
                    this.upstream.cancel();
                    for (InnerSubscriber<?, ?> innerSubscriber : this.subscribers.getAndSet(CANCELLED)) {
                        innerSubscriber.dispose();
                    }
                }
                drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            if (this.done) {
                RxJavaPlugins.onError(t2);
                return;
            }
            if (this.errors.tryAddThrowableOrReport(t2)) {
                this.done = true;
                if (!this.delayErrors) {
                    for (InnerSubscriber<?, ?> innerSubscriber : this.subscribers.getAndSet(CANCELLED)) {
                        innerSubscriber.dispose();
                    }
                }
                drain();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            try {
                Publisher<? extends U> publisherApply = this.mapper.apply(t2);
                Objects.requireNonNull(publisherApply, "The mapper returned a null Publisher");
                Publisher<? extends U> publisher = publisherApply;
                if (!(publisher instanceof Supplier)) {
                    int i2 = this.bufferSize;
                    long j2 = this.uniqueId;
                    this.uniqueId = 1 + j2;
                    InnerSubscriber innerSubscriber = new InnerSubscriber(this, i2, j2);
                    if (addInner(innerSubscriber)) {
                        publisher.subscribe(innerSubscriber);
                        return;
                    }
                    return;
                }
                try {
                    Object obj = ((Supplier) publisher).get();
                    if (obj != null) {
                        tryEmitScalar(obj);
                        return;
                    }
                    if (this.maxConcurrency == Integer.MAX_VALUE || this.cancelled) {
                        return;
                    }
                    int i3 = this.scalarEmitted + 1;
                    this.scalarEmitted = i3;
                    int i4 = this.scalarLimit;
                    if (i3 == i4) {
                        this.scalarEmitted = 0;
                        this.upstream.request(i4);
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.errors.tryAddThrowableOrReport(th);
                    drain();
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.upstream.cancel();
                onError(th2);
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                this.downstream.onSubscribe(this);
                if (this.cancelled) {
                    return;
                }
                int i2 = this.maxConcurrency;
                if (i2 == Integer.MAX_VALUE) {
                    s2.request(Long.MAX_VALUE);
                } else {
                    s2.request(i2);
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void removeInner(InnerSubscriber<T, U> inner) {
            InnerSubscriber<?, ?>[] innerSubscriberArr;
            InnerSubscriber<?, ?>[] innerSubscriberArr2;
            do {
                innerSubscriberArr = this.subscribers.get();
                int length = innerSubscriberArr.length;
                if (length == 0) {
                    return;
                }
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        i2 = -1;
                        break;
                    } else if (innerSubscriberArr[i2] == inner) {
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i2 < 0) {
                    return;
                }
                if (length == 1) {
                    innerSubscriberArr2 = EMPTY;
                } else {
                    InnerSubscriber<?, ?>[] innerSubscriberArr3 = new InnerSubscriber[length - 1];
                    System.arraycopy(innerSubscriberArr, 0, innerSubscriberArr3, 0, i2);
                    System.arraycopy(innerSubscriberArr, i2 + 1, innerSubscriberArr3, i2, (length - i2) - 1);
                    innerSubscriberArr2 = innerSubscriberArr3;
                }
            } while (!j.a(this.subscribers, innerSubscriberArr, innerSubscriberArr2));
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            if (SubscriptionHelper.validate(n2)) {
                BackpressureHelper.add(this.requested, n2);
                drain();
            }
        }

        public void tryEmit(U value, InnerSubscriber<T, U> inner) {
            if (get() == 0 && compareAndSet(0, 1)) {
                long j2 = this.requested.get();
                SimpleQueue spscArrayQueue = inner.queue;
                if (j2 == 0 || !(spscArrayQueue == null || spscArrayQueue.isEmpty())) {
                    if (spscArrayQueue == null) {
                        spscArrayQueue = new SpscArrayQueue(this.bufferSize);
                        inner.queue = spscArrayQueue;
                    }
                    if (!spscArrayQueue.offer(value)) {
                        onError(new MissingBackpressureException("Inner queue full?!"));
                    }
                } else {
                    this.downstream.onNext(value);
                    if (j2 != Long.MAX_VALUE) {
                        this.requested.decrementAndGet();
                    }
                    inner.requestMore(1L);
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            } else {
                SimpleQueue spscArrayQueue2 = inner.queue;
                if (spscArrayQueue2 == null) {
                    spscArrayQueue2 = new SpscArrayQueue(this.bufferSize);
                    inner.queue = spscArrayQueue2;
                }
                if (!spscArrayQueue2.offer(value)) {
                    onError(new MissingBackpressureException("Inner queue full?!"));
                    return;
                } else if (getAndIncrement() != 0) {
                    return;
                }
            }
            drainLoop();
        }

        public void tryEmitScalar(U value) {
            if (get() == 0 && compareAndSet(0, 1)) {
                long j2 = this.requested.get();
                SimpleQueue<U> mainQueue = this.queue;
                if (j2 == 0 || !(mainQueue == null || mainQueue.isEmpty())) {
                    if (mainQueue == null) {
                        mainQueue = getMainQueue();
                    }
                    if (!mainQueue.offer(value)) {
                        onError(new MissingBackpressureException("Scalar queue full?!"));
                    }
                } else {
                    this.downstream.onNext(value);
                    if (j2 != Long.MAX_VALUE) {
                        this.requested.decrementAndGet();
                    }
                    if (this.maxConcurrency != Integer.MAX_VALUE && !this.cancelled) {
                        int i2 = this.scalarEmitted + 1;
                        this.scalarEmitted = i2;
                        int i3 = this.scalarLimit;
                        if (i2 == i3) {
                            this.scalarEmitted = 0;
                            this.upstream.request(i3);
                        }
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            } else if (!getMainQueue().offer(value)) {
                onError(new MissingBackpressureException("Scalar queue full?!"));
                return;
            } else if (getAndIncrement() != 0) {
                return;
            }
            drainLoop();
        }
    }

    public FlowableFlatMap(Flowable<T> source, Function<? super T, ? extends Publisher<? extends U>> mapper, boolean delayErrors, int maxConcurrency, int bufferSize) {
        super(source);
        this.mapper = mapper;
        this.delayErrors = delayErrors;
        this.maxConcurrency = maxConcurrency;
        this.bufferSize = bufferSize;
    }

    public static <T, U> FlowableSubscriber<T> subscribe(Subscriber<? super U> s2, Function<? super T, ? extends Publisher<? extends U>> mapper, boolean delayErrors, int maxConcurrency, int bufferSize) {
        return new MergeSubscriber(s2, mapper, delayErrors, maxConcurrency, bufferSize);
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super U> s2) {
        if (FlowableScalarXMap.tryScalarXMapSubscribe(this.source, s2, this.mapper)) {
            return;
        }
        this.source.subscribe((FlowableSubscriber) subscribe(s2, this.mapper, this.delayErrors, this.maxConcurrency, this.bufferSize));
    }
}
