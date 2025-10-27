package io.reactivex.rxjava3.internal.operators.flowable;

import androidx.camera.view.j;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.QueueDrainHelper;
import io.reactivex.rxjava3.operators.QueueSubscription;
import io.reactivex.rxjava3.operators.SimpleQueue;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowablePublishMulticast<T, R> extends AbstractFlowableWithUpstream<T, R> {
    final boolean delayError;
    final int prefetch;
    final Function<? super Flowable<T>, ? extends Publisher<? extends R>> selector;

    public static final class MulticastProcessor<T> extends Flowable<T> implements FlowableSubscriber<T> {
        static final MulticastSubscription[] EMPTY = new MulticastSubscription[0];
        static final MulticastSubscription[] TERMINATED = new MulticastSubscription[0];
        int consumed;
        final boolean delayError;
        volatile boolean done;
        Throwable error;
        final int limit;
        final int prefetch;
        volatile SimpleQueue<T> queue;
        int sourceMode;
        final AtomicInteger wip = new AtomicInteger();
        final AtomicReference<Subscription> upstream = new AtomicReference<>();
        final AtomicReference<MulticastSubscription<T>[]> subscribers = new AtomicReference<>(EMPTY);

        public MulticastProcessor(int prefetch, boolean delayError) {
            this.prefetch = prefetch;
            this.limit = prefetch - (prefetch >> 2);
            this.delayError = delayError;
        }

        public boolean add(MulticastSubscription<T> s2) {
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
                multicastSubscriptionArr2[length] = s2;
            } while (!j.a(this.subscribers, multicastSubscriptionArr, multicastSubscriptionArr2));
            return true;
        }

        public void completeAll() {
            for (MulticastSubscription<T> multicastSubscription : this.subscribers.getAndSet(TERMINATED)) {
                if (multicastSubscription.get() != Long.MIN_VALUE) {
                    multicastSubscription.downstream.onComplete();
                }
            }
        }

        public void dispose() {
            SimpleQueue<T> simpleQueue;
            if (this.done) {
                return;
            }
            SubscriptionHelper.cancel(this.upstream);
            if (this.wip.getAndIncrement() != 0 || (simpleQueue = this.queue) == null) {
                return;
            }
            simpleQueue.clear();
        }

        public void drain() {
            AtomicReference<MulticastSubscription<T>[]> atomicReference;
            Throwable th;
            Throwable th2;
            if (this.wip.getAndIncrement() != 0) {
                return;
            }
            SimpleQueue<T> simpleQueue = this.queue;
            int i2 = this.consumed;
            int i3 = this.limit;
            boolean z2 = this.sourceMode != 1;
            AtomicReference<MulticastSubscription<T>[]> atomicReference2 = this.subscribers;
            MulticastSubscription<T>[] multicastSubscriptionArr = atomicReference2.get();
            int iAddAndGet = 1;
            while (true) {
                int length = multicastSubscriptionArr.length;
                if (simpleQueue == null || length == 0) {
                    atomicReference = atomicReference2;
                } else {
                    int length2 = multicastSubscriptionArr.length;
                    long j2 = Long.MAX_VALUE;
                    long j3 = Long.MAX_VALUE;
                    int i4 = 0;
                    while (i4 < length2) {
                        MulticastSubscription<T> multicastSubscription = multicastSubscriptionArr[i4];
                        AtomicReference<MulticastSubscription<T>[]> atomicReference3 = atomicReference2;
                        long j4 = multicastSubscription.get() - multicastSubscription.emitted;
                        if (j4 == Long.MIN_VALUE) {
                            length--;
                        } else if (j3 > j4) {
                            j3 = j4;
                        }
                        i4++;
                        atomicReference2 = atomicReference3;
                    }
                    atomicReference = atomicReference2;
                    long j5 = 0;
                    if (length == 0) {
                        j3 = 0;
                    }
                    while (j3 != j5) {
                        if (isDisposed()) {
                            simpleQueue.clear();
                            return;
                        }
                        boolean z3 = this.done;
                        if (z3 && !this.delayError && (th2 = this.error) != null) {
                            errorAll(th2);
                            return;
                        }
                        try {
                            T tPoll = simpleQueue.poll();
                            boolean z4 = tPoll == null;
                            if (z3 && z4) {
                                Throwable th3 = this.error;
                                if (th3 != null) {
                                    errorAll(th3);
                                    return;
                                } else {
                                    completeAll();
                                    return;
                                }
                            }
                            if (z4) {
                                break;
                            }
                            int length3 = multicastSubscriptionArr.length;
                            int i5 = 0;
                            boolean z5 = false;
                            while (i5 < length3) {
                                MulticastSubscription<T> multicastSubscription2 = multicastSubscriptionArr[i5];
                                long j6 = multicastSubscription2.get();
                                if (j6 != Long.MIN_VALUE) {
                                    if (j6 != j2) {
                                        multicastSubscription2.emitted++;
                                    }
                                    multicastSubscription2.downstream.onNext(tPoll);
                                } else {
                                    z5 = true;
                                }
                                i5++;
                                j2 = Long.MAX_VALUE;
                            }
                            j3--;
                            if (z2 && (i2 = i2 + 1) == i3) {
                                this.upstream.get().request(i3);
                                i2 = 0;
                            }
                            MulticastSubscription<T>[] multicastSubscriptionArr2 = atomicReference.get();
                            if (z5 || multicastSubscriptionArr2 != multicastSubscriptionArr) {
                                multicastSubscriptionArr = multicastSubscriptionArr2;
                                break;
                            } else {
                                j5 = 0;
                                j2 = Long.MAX_VALUE;
                            }
                        } catch (Throwable th4) {
                            Exceptions.throwIfFatal(th4);
                            SubscriptionHelper.cancel(this.upstream);
                            errorAll(th4);
                            return;
                        }
                    }
                    if (j3 == j5) {
                        if (isDisposed()) {
                            simpleQueue.clear();
                            return;
                        }
                        boolean z6 = this.done;
                        if (z6 && !this.delayError && (th = this.error) != null) {
                            errorAll(th);
                            return;
                        }
                        if (z6 && simpleQueue.isEmpty()) {
                            Throwable th5 = this.error;
                            if (th5 != null) {
                                errorAll(th5);
                                return;
                            } else {
                                completeAll();
                                return;
                            }
                        }
                    }
                }
                this.consumed = i2;
                iAddAndGet = this.wip.addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
                if (simpleQueue == null) {
                    simpleQueue = this.queue;
                }
                multicastSubscriptionArr = atomicReference.get();
                atomicReference2 = atomicReference;
            }
        }

        public void errorAll(Throwable ex) {
            for (MulticastSubscription<T> multicastSubscription : this.subscribers.getAndSet(TERMINATED)) {
                if (multicastSubscription.get() != Long.MIN_VALUE) {
                    multicastSubscription.downstream.onError(ex);
                }
            }
        }

        public boolean isDisposed() {
            return this.upstream.get() == SubscriptionHelper.CANCELLED;
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
            this.error = t2;
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            if (this.sourceMode != 0 || this.queue.offer(t2)) {
                drain();
            } else {
                this.upstream.get().cancel();
                onError(new MissingBackpressureException());
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.setOnce(this.upstream, s2)) {
                if (s2 instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) s2;
                    int iRequestFusion = queueSubscription.requestFusion(3);
                    if (iRequestFusion == 1) {
                        this.sourceMode = iRequestFusion;
                        this.queue = queueSubscription;
                        this.done = true;
                        drain();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.sourceMode = iRequestFusion;
                        this.queue = queueSubscription;
                        QueueDrainHelper.request(s2, this.prefetch);
                        return;
                    }
                }
                this.queue = QueueDrainHelper.createQueue(this.prefetch);
                QueueDrainHelper.request(s2, this.prefetch);
            }
        }

        public void remove(MulticastSubscription<T> s2) {
            MulticastSubscription<T>[] multicastSubscriptionArr;
            MulticastSubscription[] multicastSubscriptionArr2;
            do {
                multicastSubscriptionArr = this.subscribers.get();
                int length = multicastSubscriptionArr.length;
                if (length == 0) {
                    return;
                }
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        i2 = -1;
                        break;
                    } else if (multicastSubscriptionArr[i2] == s2) {
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i2 < 0) {
                    return;
                }
                if (length == 1) {
                    multicastSubscriptionArr2 = EMPTY;
                } else {
                    MulticastSubscription[] multicastSubscriptionArr3 = new MulticastSubscription[length - 1];
                    System.arraycopy(multicastSubscriptionArr, 0, multicastSubscriptionArr3, 0, i2);
                    System.arraycopy(multicastSubscriptionArr, i2 + 1, multicastSubscriptionArr3, i2, (length - i2) - 1);
                    multicastSubscriptionArr2 = multicastSubscriptionArr3;
                }
            } while (!j.a(this.subscribers, multicastSubscriptionArr, multicastSubscriptionArr2));
        }

        @Override // io.reactivex.rxjava3.core.Flowable
        public void subscribeActual(Subscriber<? super T> s2) {
            MulticastSubscription<T> multicastSubscription = new MulticastSubscription<>(s2, this);
            s2.onSubscribe(multicastSubscription);
            if (add(multicastSubscription)) {
                if (multicastSubscription.isCancelled()) {
                    remove(multicastSubscription);
                    return;
                } else {
                    drain();
                    return;
                }
            }
            Throwable th = this.error;
            if (th != null) {
                s2.onError(th);
            } else {
                s2.onComplete();
            }
        }
    }

    public static final class MulticastSubscription<T> extends AtomicLong implements Subscription {
        private static final long serialVersionUID = 8664815189257569791L;
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
                this.parent.drain();
            }
        }

        public boolean isCancelled() {
            return get() == Long.MIN_VALUE;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            if (SubscriptionHelper.validate(n2)) {
                BackpressureHelper.addCancel(this, n2);
                this.parent.drain();
            }
        }
    }

    public static final class OutputCanceller<R> implements FlowableSubscriber<R>, Subscription {
        final Subscriber<? super R> downstream;
        final MulticastProcessor<?> processor;
        Subscription upstream;

        public OutputCanceller(Subscriber<? super R> actual, MulticastProcessor<?> processor) {
            this.downstream = actual;
            this.processor = processor;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
            this.processor.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.downstream.onComplete();
            this.processor.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            this.downstream.onError(t2);
            this.processor.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(R t2) {
            this.downstream.onNext(t2);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            this.upstream.request(n2);
        }
    }

    public FlowablePublishMulticast(Flowable<T> source, Function<? super Flowable<T>, ? extends Publisher<? extends R>> selector, int prefetch, boolean delayError) {
        super(source);
        this.selector = selector;
        this.prefetch = prefetch;
        this.delayError = delayError;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super R> s2) {
        MulticastProcessor multicastProcessor = new MulticastProcessor(this.prefetch, this.delayError);
        try {
            Publisher<? extends R> publisherApply = this.selector.apply(multicastProcessor);
            Objects.requireNonNull(publisherApply, "selector returned a null Publisher");
            publisherApply.subscribe(new OutputCanceller(s2, multicastProcessor));
            this.source.subscribe((FlowableSubscriber) multicastProcessor);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptySubscription.error(th, s2);
        }
    }
}
