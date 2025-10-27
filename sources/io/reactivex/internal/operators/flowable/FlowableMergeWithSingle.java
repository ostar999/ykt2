package io.reactivex.internal.operators.flowable;

import MTT.ThirdAppInfoNew;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableMergeWithSingle<T> extends AbstractFlowableWithUpstream<T, T> {
    final SingleSource<? extends T> other;

    public static final class MergeWithObserver<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        static final int OTHER_STATE_CONSUMED_OR_EMPTY = 2;
        static final int OTHER_STATE_HAS_VALUE = 1;
        private static final long serialVersionUID = -4592979584110982903L;
        volatile boolean cancelled;
        int consumed;
        final Subscriber<? super T> downstream;
        long emitted;
        final int limit;
        volatile boolean mainDone;
        volatile int otherState;
        final int prefetch;
        volatile SimplePlainQueue<T> queue;
        T singleItem;
        final AtomicReference<Subscription> mainSubscription = new AtomicReference<>();
        final OtherObserver<T> otherObserver = new OtherObserver<>(this);
        final AtomicThrowable error = new AtomicThrowable();
        final AtomicLong requested = new AtomicLong();

        public static final class OtherObserver<T> extends AtomicReference<Disposable> implements SingleObserver<T> {
            private static final long serialVersionUID = -2935427570954647017L;
            final MergeWithObserver<T> parent;

            public OtherObserver(MergeWithObserver<T> mergeWithObserver) {
                this.parent = mergeWithObserver;
            }

            @Override // io.reactivex.SingleObserver
            public void onError(Throwable th) {
                this.parent.otherError(th);
            }

            @Override // io.reactivex.SingleObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            @Override // io.reactivex.SingleObserver
            public void onSuccess(T t2) {
                this.parent.otherSuccess(t2);
            }
        }

        public MergeWithObserver(Subscriber<? super T> subscriber) {
            this.downstream = subscriber;
            int iBufferSize = Flowable.bufferSize();
            this.prefetch = iBufferSize;
            this.limit = iBufferSize - (iBufferSize >> 2);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            SubscriptionHelper.cancel(this.mainSubscription);
            DisposableHelper.dispose(this.otherObserver);
            if (getAndIncrement() == 0) {
                this.queue = null;
                this.singleItem = null;
            }
        }

        public void drain() {
            if (getAndIncrement() == 0) {
                drainLoop();
            }
        }

        public void drainLoop() {
            Subscriber<? super T> subscriber = this.downstream;
            long j2 = this.emitted;
            int i2 = this.consumed;
            int i3 = this.limit;
            int i4 = 1;
            int iAddAndGet = 1;
            while (true) {
                long j3 = this.requested.get();
                while (j2 != j3) {
                    if (this.cancelled) {
                        this.singleItem = null;
                        this.queue = null;
                        return;
                    }
                    if (this.error.get() != null) {
                        this.singleItem = null;
                        this.queue = null;
                        subscriber.onError(this.error.terminate());
                        return;
                    }
                    int i5 = this.otherState;
                    if (i5 == i4) {
                        T t2 = this.singleItem;
                        this.singleItem = null;
                        this.otherState = 2;
                        subscriber.onNext(t2);
                        j2++;
                    } else {
                        boolean z2 = this.mainDone;
                        SimplePlainQueue<T> simplePlainQueue = this.queue;
                        ThirdAppInfoNew thirdAppInfoNewPoll = simplePlainQueue != null ? simplePlainQueue.poll() : null;
                        boolean z3 = thirdAppInfoNewPoll == null;
                        if (z2 && z3 && i5 == 2) {
                            this.queue = null;
                            subscriber.onComplete();
                            return;
                        } else {
                            if (z3) {
                                break;
                            }
                            subscriber.onNext(thirdAppInfoNewPoll);
                            j2++;
                            i2++;
                            if (i2 == i3) {
                                this.mainSubscription.get().request(i3);
                                i2 = 0;
                            }
                            i4 = 1;
                        }
                    }
                }
                if (j2 == j3) {
                    if (this.cancelled) {
                        this.singleItem = null;
                        this.queue = null;
                        return;
                    }
                    if (this.error.get() != null) {
                        this.singleItem = null;
                        this.queue = null;
                        subscriber.onError(this.error.terminate());
                        return;
                    }
                    boolean z4 = this.mainDone;
                    SimplePlainQueue<T> simplePlainQueue2 = this.queue;
                    boolean z5 = simplePlainQueue2 == null || simplePlainQueue2.isEmpty();
                    if (z4 && z5 && this.otherState == 2) {
                        this.queue = null;
                        subscriber.onComplete();
                        return;
                    }
                }
                this.emitted = j2;
                this.consumed = i2;
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                } else {
                    i4 = 1;
                }
            }
        }

        public SimplePlainQueue<T> getOrCreateQueue() {
            SimplePlainQueue<T> simplePlainQueue = this.queue;
            if (simplePlainQueue != null) {
                return simplePlainQueue;
            }
            SpscArrayQueue spscArrayQueue = new SpscArrayQueue(Flowable.bufferSize());
            this.queue = spscArrayQueue;
            return spscArrayQueue;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.mainDone = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (!this.error.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else {
                SubscriptionHelper.cancel(this.mainSubscription);
                drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (compareAndSet(0, 1)) {
                long j2 = this.emitted;
                if (this.requested.get() != j2) {
                    SimplePlainQueue<T> simplePlainQueue = this.queue;
                    if (simplePlainQueue == null || simplePlainQueue.isEmpty()) {
                        this.emitted = j2 + 1;
                        this.downstream.onNext(t2);
                        int i2 = this.consumed + 1;
                        if (i2 == this.limit) {
                            this.consumed = 0;
                            this.mainSubscription.get().request(i2);
                        } else {
                            this.consumed = i2;
                        }
                    } else {
                        simplePlainQueue.offer(t2);
                    }
                } else {
                    getOrCreateQueue().offer(t2);
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            } else {
                getOrCreateQueue().offer(t2);
                if (getAndIncrement() != 0) {
                    return;
                }
            }
            drainLoop();
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this.mainSubscription, subscription, this.prefetch);
        }

        public void otherError(Throwable th) {
            if (!this.error.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else {
                SubscriptionHelper.cancel(this.mainSubscription);
                drain();
            }
        }

        public void otherSuccess(T t2) {
            if (compareAndSet(0, 1)) {
                long j2 = this.emitted;
                if (this.requested.get() != j2) {
                    this.emitted = j2 + 1;
                    this.downstream.onNext(t2);
                    this.otherState = 2;
                } else {
                    this.singleItem = t2;
                    this.otherState = 1;
                    if (decrementAndGet() == 0) {
                        return;
                    }
                }
            } else {
                this.singleItem = t2;
                this.otherState = 1;
                if (getAndIncrement() != 0) {
                    return;
                }
            }
            drainLoop();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j2) {
            BackpressureHelper.add(this.requested, j2);
            drain();
        }
    }

    public FlowableMergeWithSingle(Flowable<T> flowable, SingleSource<? extends T> singleSource) {
        super(flowable);
        this.other = singleSource;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        MergeWithObserver mergeWithObserver = new MergeWithObserver(subscriber);
        subscriber.onSubscribe(mergeWithObserver);
        this.source.subscribe((FlowableSubscriber) mergeWithObserver);
        this.other.subscribe(mergeWithObserver.otherObserver);
    }
}
