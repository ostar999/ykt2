package io.reactivex.rxjava3.processors;

import androidx.camera.view.j;
import io.reactivex.rxjava3.annotations.BackpressureKind;
import io.reactivex.rxjava3.annotations.BackpressureSupport;
import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.SchedulerSupport;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.internal.functions.ObjectHelper;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.operators.QueueSubscription;
import io.reactivex.rxjava3.operators.SimpleQueue;
import io.reactivex.rxjava3.operators.SpscArrayQueue;
import io.reactivex.rxjava3.operators.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@BackpressureSupport(BackpressureKind.FULL)
@SchedulerSupport("none")
/* loaded from: classes8.dex */
public final class MulticastProcessor<T> extends FlowableProcessor<T> {
    static final MulticastSubscription[] EMPTY = new MulticastSubscription[0];
    static final MulticastSubscription[] TERMINATED = new MulticastSubscription[0];
    final int bufferSize;
    int consumed;
    volatile boolean done;
    volatile Throwable error;
    int fusionMode;
    final int limit;
    volatile SimpleQueue<T> queue;
    final boolean refcount;
    final AtomicInteger wip = new AtomicInteger();
    final AtomicReference<MulticastSubscription<T>[]> subscribers = new AtomicReference<>(EMPTY);
    final AtomicReference<Subscription> upstream = new AtomicReference<>();

    public static final class MulticastSubscription<T> extends AtomicLong implements Subscription {
        private static final long serialVersionUID = -363282618957264509L;
        final Subscriber<? super T> downstream;
        long emitted;
        final MulticastProcessor<T> parent;

        public MulticastSubscription(Subscriber<? super T> actual, MulticastProcessor<T> parent) {
            this.downstream = actual;
            this.parent = parent;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.parent.remove(this);
            }
        }

        public void onComplete() {
            if (get() != Long.MIN_VALUE) {
                this.downstream.onComplete();
            }
        }

        public void onError(Throwable t2) {
            if (get() != Long.MIN_VALUE) {
                this.downstream.onError(t2);
            }
        }

        public void onNext(T t2) {
            if (get() != Long.MIN_VALUE) {
                this.emitted++;
                this.downstream.onNext(t2);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            if (SubscriptionHelper.validate(n2)) {
                long jAddCancel = BackpressureHelper.addCancel(this, n2);
                if (jAddCancel == Long.MIN_VALUE || jAddCancel == Long.MAX_VALUE) {
                    return;
                }
                this.parent.drain();
            }
        }
    }

    public MulticastProcessor(int bufferSize, boolean refCount) {
        this.bufferSize = bufferSize;
        this.limit = bufferSize - (bufferSize >> 2);
        this.refcount = refCount;
    }

    @CheckReturnValue
    @NonNull
    public static <T> MulticastProcessor<T> create() {
        return new MulticastProcessor<>(Flowable.bufferSize(), false);
    }

    public boolean add(MulticastSubscription<T> inner) {
        MulticastSubscription<T>[] multicastSubscriptionArr;
        MulticastSubscription[] multicastSubscriptionArr2;
        do {
            multicastSubscriptionArr = this.subscribers.get();
            if (multicastSubscriptionArr == TERMINATED) {
                return false;
            }
            int length = multicastSubscriptionArr.length;
            multicastSubscriptionArr2 = new MulticastSubscription[length + 1];
            System.arraycopy(multicastSubscriptionArr, 0, multicastSubscriptionArr2, 0, length);
            multicastSubscriptionArr2[length] = inner;
        } while (!j.a(this.subscribers, multicastSubscriptionArr, multicastSubscriptionArr2));
        return true;
    }

    public void drain() {
        T tPoll;
        if (this.wip.getAndIncrement() != 0) {
            return;
        }
        AtomicReference<MulticastSubscription<T>[]> atomicReference = this.subscribers;
        int i2 = this.consumed;
        int i3 = this.limit;
        int i4 = this.fusionMode;
        int iAddAndGet = 1;
        while (true) {
            SimpleQueue<T> simpleQueue = this.queue;
            if (simpleQueue != null) {
                MulticastSubscription<T>[] multicastSubscriptionArr = atomicReference.get();
                if (multicastSubscriptionArr.length != 0) {
                    int length = multicastSubscriptionArr.length;
                    long j2 = -1;
                    long jMin = -1;
                    int i5 = 0;
                    while (i5 < length) {
                        MulticastSubscription<T> multicastSubscription = multicastSubscriptionArr[i5];
                        long j3 = multicastSubscription.get();
                        if (j3 >= 0) {
                            jMin = jMin == j2 ? j3 - multicastSubscription.emitted : Math.min(jMin, j3 - multicastSubscription.emitted);
                        }
                        i5++;
                        j2 = -1;
                    }
                    int i6 = i2;
                    while (jMin > 0) {
                        MulticastSubscription<T>[] multicastSubscriptionArr2 = atomicReference.get();
                        if (multicastSubscriptionArr2 == TERMINATED) {
                            simpleQueue.clear();
                            return;
                        }
                        if (multicastSubscriptionArr != multicastSubscriptionArr2) {
                            break;
                        }
                        boolean z2 = this.done;
                        try {
                            tPoll = simpleQueue.poll();
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            SubscriptionHelper.cancel(this.upstream);
                            this.error = th;
                            this.done = true;
                            tPoll = null;
                            z2 = true;
                        }
                        boolean z3 = tPoll == null;
                        if (z2 && z3) {
                            Throwable th2 = this.error;
                            if (th2 != null) {
                                for (MulticastSubscription<T> multicastSubscription2 : atomicReference.getAndSet(TERMINATED)) {
                                    multicastSubscription2.onError(th2);
                                }
                                return;
                            }
                            for (MulticastSubscription<T> multicastSubscription3 : atomicReference.getAndSet(TERMINATED)) {
                                multicastSubscription3.onComplete();
                            }
                            return;
                        }
                        if (z3) {
                            break;
                        }
                        for (MulticastSubscription<T> multicastSubscription4 : multicastSubscriptionArr) {
                            multicastSubscription4.onNext(tPoll);
                        }
                        jMin--;
                        if (i4 != 1 && (i6 = i6 + 1) == i3) {
                            this.upstream.get().request(i3);
                            i6 = 0;
                        }
                    }
                    if (jMin == 0) {
                        MulticastSubscription<T>[] multicastSubscriptionArr3 = atomicReference.get();
                        MulticastSubscription<T>[] multicastSubscriptionArr4 = TERMINATED;
                        if (multicastSubscriptionArr3 == multicastSubscriptionArr4) {
                            simpleQueue.clear();
                            return;
                        }
                        if (multicastSubscriptionArr != multicastSubscriptionArr3) {
                            i2 = i6;
                        } else if (this.done && simpleQueue.isEmpty()) {
                            Throwable th3 = this.error;
                            if (th3 != null) {
                                for (MulticastSubscription<T> multicastSubscription5 : atomicReference.getAndSet(multicastSubscriptionArr4)) {
                                    multicastSubscription5.onError(th3);
                                }
                                return;
                            }
                            for (MulticastSubscription<T> multicastSubscription6 : atomicReference.getAndSet(multicastSubscriptionArr4)) {
                                multicastSubscription6.onComplete();
                            }
                            return;
                        }
                    }
                    i2 = i6;
                }
            }
            this.consumed = i2;
            iAddAndGet = this.wip.addAndGet(-iAddAndGet);
            if (iAddAndGet == 0) {
                return;
            }
        }
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    public Throwable getThrowable() {
        if (this.done) {
            return this.error;
        }
        return null;
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    public boolean hasComplete() {
        return this.done && this.error == null;
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    public boolean hasSubscribers() {
        return this.subscribers.get().length != 0;
    }

    @Override // io.reactivex.rxjava3.processors.FlowableProcessor
    @CheckReturnValue
    public boolean hasThrowable() {
        return this.done && this.error != null;
    }

    @CheckReturnValue
    public boolean offer(@NonNull T t2) {
        ExceptionHelper.nullCheck(t2, "offer called with a null value.");
        if (this.done) {
            return false;
        }
        if (this.fusionMode != 0) {
            throw new IllegalStateException("offer() should not be called in fusion mode!");
        }
        if (!this.queue.offer(t2)) {
            return false;
        }
        drain();
        return true;
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        this.done = true;
        drain();
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(@NonNull Throwable t2) {
        ExceptionHelper.nullCheck(t2, "onError called with a null Throwable.");
        if (this.done) {
            RxJavaPlugins.onError(t2);
            return;
        }
        this.error = t2;
        this.done = true;
        drain();
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(@NonNull T t2) {
        if (this.done) {
            return;
        }
        if (this.fusionMode == 0) {
            ExceptionHelper.nullCheck(t2, "onNext called with a null value.");
            if (!this.queue.offer(t2)) {
                SubscriptionHelper.cancel(this.upstream);
                onError(new MissingBackpressureException());
                return;
            }
        }
        drain();
    }

    @Override // org.reactivestreams.Subscriber
    public void onSubscribe(@NonNull Subscription s2) {
        if (SubscriptionHelper.setOnce(this.upstream, s2)) {
            if (s2 instanceof QueueSubscription) {
                QueueSubscription queueSubscription = (QueueSubscription) s2;
                int iRequestFusion = queueSubscription.requestFusion(3);
                if (iRequestFusion == 1) {
                    this.fusionMode = iRequestFusion;
                    this.queue = queueSubscription;
                    this.done = true;
                    drain();
                    return;
                }
                if (iRequestFusion == 2) {
                    this.fusionMode = iRequestFusion;
                    this.queue = queueSubscription;
                    s2.request(this.bufferSize);
                    return;
                }
            }
            this.queue = new SpscArrayQueue(this.bufferSize);
            s2.request(this.bufferSize);
        }
    }

    public void remove(MulticastSubscription<T> inner) {
        while (true) {
            MulticastSubscription<T>[] multicastSubscriptionArr = this.subscribers.get();
            int length = multicastSubscriptionArr.length;
            if (length == 0) {
                return;
            }
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    i2 = -1;
                    break;
                } else if (multicastSubscriptionArr[i2] == inner) {
                    break;
                } else {
                    i2++;
                }
            }
            if (i2 < 0) {
                return;
            }
            if (length != 1) {
                MulticastSubscription[] multicastSubscriptionArr2 = new MulticastSubscription[length - 1];
                System.arraycopy(multicastSubscriptionArr, 0, multicastSubscriptionArr2, 0, i2);
                System.arraycopy(multicastSubscriptionArr, i2 + 1, multicastSubscriptionArr2, i2, (length - i2) - 1);
                if (j.a(this.subscribers, multicastSubscriptionArr, multicastSubscriptionArr2)) {
                    return;
                }
            } else if (this.refcount) {
                if (j.a(this.subscribers, multicastSubscriptionArr, TERMINATED)) {
                    SubscriptionHelper.cancel(this.upstream);
                    this.done = true;
                    return;
                }
            } else if (j.a(this.subscribers, multicastSubscriptionArr, EMPTY)) {
                return;
            }
        }
    }

    public void start() {
        if (SubscriptionHelper.setOnce(this.upstream, EmptySubscription.INSTANCE)) {
            this.queue = new SpscArrayQueue(this.bufferSize);
        }
    }

    public void startUnbounded() {
        if (SubscriptionHelper.setOnce(this.upstream, EmptySubscription.INSTANCE)) {
            this.queue = new SpscLinkedArrayQueue(this.bufferSize);
        }
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(@NonNull Subscriber<? super T> s2) {
        Throwable th;
        MulticastSubscription<T> multicastSubscription = new MulticastSubscription<>(s2, this);
        s2.onSubscribe(multicastSubscription);
        if (add(multicastSubscription)) {
            if (multicastSubscription.get() == Long.MIN_VALUE) {
                remove(multicastSubscription);
                return;
            } else {
                drain();
                return;
            }
        }
        if (!this.done || (th = this.error) == null) {
            s2.onComplete();
        } else {
            s2.onError(th);
        }
    }

    @CheckReturnValue
    @NonNull
    public static <T> MulticastProcessor<T> create(boolean refCount) {
        return new MulticastProcessor<>(Flowable.bufferSize(), refCount);
    }

    @CheckReturnValue
    @NonNull
    public static <T> MulticastProcessor<T> create(int bufferSize) {
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return new MulticastProcessor<>(bufferSize, false);
    }

    @CheckReturnValue
    @NonNull
    public static <T> MulticastProcessor<T> create(int bufferSize, boolean refCount) {
        ObjectHelper.verifyPositive(bufferSize, "bufferSize");
        return new MulticastProcessor<>(bufferSize, refCount);
    }
}
