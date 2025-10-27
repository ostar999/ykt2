package io.reactivex.internal.operators.flowable;

import androidx.camera.view.j;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
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

        public InnerSubscriber(MergeSubscriber<T, U> mergeSubscriber, long j2) {
            this.id = j2;
            this.parent = mergeSubscriber;
            int i2 = mergeSubscriber.bufferSize;
            this.bufferSize = i2;
            this.limit = i2 >> 2;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            SubscriptionHelper.cancel(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() == SubscriptionHelper.CANCELLED;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            this.parent.drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            lazySet(SubscriptionHelper.CANCELLED);
            this.parent.innerError(this, th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(U u2) {
            if (this.fusionMode != 2) {
                this.parent.tryEmit(u2, this);
            } else {
                this.parent.drain();
            }
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
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
                subscription.request(this.bufferSize);
            }
        }

        public void requestMore(long j2) {
            if (this.fusionMode != 1) {
                long j3 = this.produced + j2;
                if (j3 < this.limit) {
                    this.produced = j3;
                } else {
                    this.produced = 0L;
                    get().request(j3);
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
        final AtomicThrowable errs = new AtomicThrowable();
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

        public MergeSubscriber(Subscriber<? super U> subscriber, Function<? super T, ? extends Publisher<? extends U>> function, boolean z2, int i2, int i3) {
            AtomicReference<InnerSubscriber<?, ?>[]> atomicReference = new AtomicReference<>();
            this.subscribers = atomicReference;
            this.requested = new AtomicLong();
            this.downstream = subscriber;
            this.mapper = function;
            this.delayErrors = z2;
            this.maxConcurrency = i2;
            this.bufferSize = i3;
            this.scalarLimit = Math.max(1, i2 >> 1);
            atomicReference.lazySet(EMPTY);
        }

        public boolean addInner(InnerSubscriber<T, U> innerSubscriber) {
            InnerSubscriber<?, ?>[] innerSubscriberArr;
            InnerSubscriber[] innerSubscriberArr2;
            do {
                innerSubscriberArr = this.subscribers.get();
                if (innerSubscriberArr == CANCELLED) {
                    innerSubscriber.dispose();
                    return false;
                }
                int length = innerSubscriberArr.length;
                innerSubscriberArr2 = new InnerSubscriber[length + 1];
                System.arraycopy(innerSubscriberArr, 0, innerSubscriberArr2, 0, length);
                innerSubscriberArr2[length] = innerSubscriber;
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
            if (this.delayErrors || this.errs.get() == null) {
                return false;
            }
            clearScalarQueue();
            Throwable thTerminate = this.errs.terminate();
            if (thTerminate != ExceptionHelper.TERMINATED) {
                this.downstream.onError(thTerminate);
            }
            return true;
        }

        public void clearScalarQueue() {
            SimplePlainQueue<U> simplePlainQueue = this.queue;
            if (simplePlainQueue != null) {
                simplePlainQueue.clear();
            }
        }

        public void disposeAll() {
            InnerSubscriber<?, ?>[] andSet;
            InnerSubscriber<?, ?>[] innerSubscriberArr = this.subscribers.get();
            InnerSubscriber<?, ?>[] innerSubscriberArr2 = CANCELLED;
            if (innerSubscriberArr == innerSubscriberArr2 || (andSet = this.subscribers.getAndSet(innerSubscriberArr2)) == innerSubscriberArr2) {
                return;
            }
            for (InnerSubscriber<?, ?> innerSubscriber : andSet) {
                innerSubscriber.dispose();
            }
            Throwable thTerminate = this.errs.terminate();
            if (thTerminate == null || thTerminate == ExceptionHelper.TERMINATED) {
                return;
            }
            RxJavaPlugins.onError(thTerminate);
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
            boolean z2;
            int i2;
            int i3;
            long j4;
            Object obj;
            Subscriber<? super U> subscriber = this.downstream;
            int iAddAndGet = 1;
            while (!checkTerminate()) {
                SimplePlainQueue<U> simplePlainQueue = this.queue;
                long jAddAndGet = this.requested.get();
                boolean z3 = jAddAndGet == Long.MAX_VALUE;
                long j5 = 0;
                long j6 = 0;
                if (simplePlainQueue != null) {
                    do {
                        long j7 = 0;
                        obj = null;
                        while (true) {
                            if (jAddAndGet == 0) {
                                break;
                            }
                            U uPoll = simplePlainQueue.poll();
                            if (checkTerminate()) {
                                return;
                            }
                            if (uPoll == null) {
                                obj = uPoll;
                                break;
                            }
                            subscriber.onNext(uPoll);
                            j6++;
                            j7++;
                            jAddAndGet--;
                            obj = uPoll;
                        }
                        if (j7 != 0) {
                            jAddAndGet = z3 ? Long.MAX_VALUE : this.requested.addAndGet(-j7);
                        }
                        if (jAddAndGet == 0) {
                            break;
                        }
                    } while (obj != null);
                }
                boolean z4 = this.done;
                SimplePlainQueue<U> simplePlainQueue2 = this.queue;
                InnerSubscriber<?, ?>[] innerSubscriberArr = this.subscribers.get();
                int length = innerSubscriberArr.length;
                if (z4 && ((simplePlainQueue2 == null || simplePlainQueue2.isEmpty()) && length == 0)) {
                    Throwable thTerminate = this.errs.terminate();
                    if (thTerminate != ExceptionHelper.TERMINATED) {
                        if (thTerminate == null) {
                            subscriber.onComplete();
                            return;
                        } else {
                            subscriber.onError(thTerminate);
                            return;
                        }
                    }
                    return;
                }
                int i4 = iAddAndGet;
                if (length != 0) {
                    long j8 = this.lastId;
                    int i5 = this.lastIndex;
                    if (length <= i5 || innerSubscriberArr[i5].id != j8) {
                        if (length <= i5) {
                            i5 = 0;
                        }
                        for (int i6 = 0; i6 < length && innerSubscriberArr[i5].id != j8; i6++) {
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
                        Object obj2 = null;
                        while (!checkTerminate()) {
                            SimpleQueue<U> simpleQueue = innerSubscriber.queue;
                            if (simpleQueue == null) {
                                i2 = length;
                            } else {
                                i2 = length;
                                Object obj3 = obj2;
                                long j9 = j5;
                                while (true) {
                                    if (jAddAndGet == j5) {
                                        break;
                                    }
                                    try {
                                        U uPoll2 = simpleQueue.poll();
                                        if (uPoll2 == null) {
                                            obj3 = uPoll2;
                                            j5 = 0;
                                            break;
                                        }
                                        subscriber.onNext(uPoll2);
                                        if (checkTerminate()) {
                                            return;
                                        }
                                        jAddAndGet--;
                                        j9++;
                                        obj3 = uPoll2;
                                        j5 = 0;
                                    } catch (Throwable th) {
                                        Exceptions.throwIfFatal(th);
                                        innerSubscriber.dispose();
                                        this.errs.addThrowable(th);
                                        if (!this.delayErrors) {
                                            this.upstream.cancel();
                                        }
                                        if (checkTerminate()) {
                                            return;
                                        }
                                        removeInner(innerSubscriber);
                                        i8++;
                                        z5 = true;
                                        i3 = 1;
                                    }
                                }
                                if (j9 != j5) {
                                    jAddAndGet = !z3 ? this.requested.addAndGet(-j9) : Long.MAX_VALUE;
                                    innerSubscriber.requestMore(j9);
                                    j4 = 0;
                                } else {
                                    j4 = j5;
                                }
                                if (jAddAndGet != j4 && obj3 != null) {
                                    length = i2;
                                    obj2 = obj3;
                                    j5 = 0;
                                }
                            }
                            boolean z6 = innerSubscriber.done;
                            SimpleQueue<U> simpleQueue2 = innerSubscriber.queue;
                            if (z6 && (simpleQueue2 == null || simpleQueue2.isEmpty())) {
                                removeInner(innerSubscriber);
                                if (checkTerminate()) {
                                    return;
                                }
                                j6++;
                                z5 = true;
                            }
                            if (jAddAndGet == 0) {
                                z2 = z5;
                                break;
                            }
                            i7++;
                            if (i7 == i2) {
                                i7 = 0;
                            }
                            i3 = 1;
                            i8 += i3;
                            length = i2;
                            j5 = 0;
                        }
                        return;
                    }
                    this.lastIndex = i7;
                    this.lastId = innerSubscriberArr[i7].id;
                    j3 = j6;
                    j2 = 0;
                } else {
                    j2 = 0;
                    j3 = j6;
                    z2 = false;
                }
                if (j3 != j2 && !this.cancelled) {
                    this.upstream.request(j3);
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

        public SimpleQueue<U> getInnerQueue(InnerSubscriber<T, U> innerSubscriber) {
            SimpleQueue<U> simpleQueue = innerSubscriber.queue;
            if (simpleQueue != null) {
                return simpleQueue;
            }
            SpscArrayQueue spscArrayQueue = new SpscArrayQueue(this.bufferSize);
            innerSubscriber.queue = spscArrayQueue;
            return spscArrayQueue;
        }

        public SimpleQueue<U> getMainQueue() {
            SimplePlainQueue<U> spscLinkedArrayQueue = this.queue;
            if (spscLinkedArrayQueue == null) {
                spscLinkedArrayQueue = this.maxConcurrency == Integer.MAX_VALUE ? new SpscLinkedArrayQueue<>(this.bufferSize) : new SpscArrayQueue<>(this.maxConcurrency);
                this.queue = spscLinkedArrayQueue;
            }
            return spscLinkedArrayQueue;
        }

        public void innerError(InnerSubscriber<T, U> innerSubscriber, Throwable th) {
            if (!this.errs.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            innerSubscriber.done = true;
            if (!this.delayErrors) {
                this.upstream.cancel();
                for (InnerSubscriber<?, ?> innerSubscriber2 : this.subscribers.getAndSet(CANCELLED)) {
                    innerSubscriber2.dispose();
                }
            }
            drain();
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
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
            } else if (!this.errs.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else {
                this.done = true;
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
                Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.mapper.apply(t2), "The mapper returned a null Publisher");
                if (!(publisher instanceof Callable)) {
                    long j2 = this.uniqueId;
                    this.uniqueId = 1 + j2;
                    InnerSubscriber innerSubscriber = new InnerSubscriber(this, j2);
                    if (addInner(innerSubscriber)) {
                        publisher.subscribe(innerSubscriber);
                        return;
                    }
                    return;
                }
                try {
                    Object objCall = ((Callable) publisher).call();
                    if (objCall != null) {
                        tryEmitScalar(objCall);
                        return;
                    }
                    if (this.maxConcurrency == Integer.MAX_VALUE || this.cancelled) {
                        return;
                    }
                    int i2 = this.scalarEmitted + 1;
                    this.scalarEmitted = i2;
                    int i3 = this.scalarLimit;
                    if (i2 == i3) {
                        this.scalarEmitted = 0;
                        this.upstream.request(i3);
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.errs.addThrowable(th);
                    drain();
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.upstream.cancel();
                onError(th2);
            }
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                if (this.cancelled) {
                    return;
                }
                int i2 = this.maxConcurrency;
                if (i2 == Integer.MAX_VALUE) {
                    subscription.request(Long.MAX_VALUE);
                } else {
                    subscription.request(i2);
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void removeInner(InnerSubscriber<T, U> innerSubscriber) {
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
                    } else if (innerSubscriberArr[i2] == innerSubscriber) {
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
        public void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                BackpressureHelper.add(this.requested, j2);
                drain();
            }
        }

        public void tryEmit(U u2, InnerSubscriber<T, U> innerSubscriber) {
            if (get() == 0 && compareAndSet(0, 1)) {
                long j2 = this.requested.get();
                SimpleQueue<U> innerQueue = innerSubscriber.queue;
                if (j2 == 0 || !(innerQueue == null || innerQueue.isEmpty())) {
                    if (innerQueue == null) {
                        innerQueue = getInnerQueue(innerSubscriber);
                    }
                    if (!innerQueue.offer(u2)) {
                        onError(new MissingBackpressureException("Inner queue full?!"));
                        return;
                    }
                } else {
                    this.downstream.onNext(u2);
                    if (j2 != Long.MAX_VALUE) {
                        this.requested.decrementAndGet();
                    }
                    innerSubscriber.requestMore(1L);
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            } else {
                SimpleQueue spscArrayQueue = innerSubscriber.queue;
                if (spscArrayQueue == null) {
                    spscArrayQueue = new SpscArrayQueue(this.bufferSize);
                    innerSubscriber.queue = spscArrayQueue;
                }
                if (!spscArrayQueue.offer(u2)) {
                    onError(new MissingBackpressureException("Inner queue full?!"));
                    return;
                } else if (getAndIncrement() != 0) {
                    return;
                }
            }
            drainLoop();
        }

        public void tryEmitScalar(U u2) {
            if (get() == 0 && compareAndSet(0, 1)) {
                long j2 = this.requested.get();
                SimpleQueue<U> mainQueue = this.queue;
                if (j2 == 0 || !(mainQueue == null || mainQueue.isEmpty())) {
                    if (mainQueue == null) {
                        mainQueue = getMainQueue();
                    }
                    if (!mainQueue.offer(u2)) {
                        onError(new IllegalStateException("Scalar queue full?!"));
                        return;
                    }
                } else {
                    this.downstream.onNext(u2);
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
            } else if (!getMainQueue().offer(u2)) {
                onError(new IllegalStateException("Scalar queue full?!"));
                return;
            } else if (getAndIncrement() != 0) {
                return;
            }
            drainLoop();
        }
    }

    public FlowableFlatMap(Flowable<T> flowable, Function<? super T, ? extends Publisher<? extends U>> function, boolean z2, int i2, int i3) {
        super(flowable);
        this.mapper = function;
        this.delayErrors = z2;
        this.maxConcurrency = i2;
        this.bufferSize = i3;
    }

    public static <T, U> FlowableSubscriber<T> subscribe(Subscriber<? super U> subscriber, Function<? super T, ? extends Publisher<? extends U>> function, boolean z2, int i2, int i3) {
        return new MergeSubscriber(subscriber, function, z2, i2, i3);
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super U> subscriber) {
        if (FlowableScalarXMap.tryScalarXMapSubscribe(this.source, subscriber, this.mapper)) {
            return;
        }
        this.source.subscribe((FlowableSubscriber) subscribe(subscriber, this.mapper, this.delayErrors, this.maxConcurrency, this.bufferSize));
    }
}
