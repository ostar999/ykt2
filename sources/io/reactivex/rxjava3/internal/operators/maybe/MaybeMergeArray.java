package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.NotificationLite;
import io.reactivex.rxjava3.operators.SimpleQueue;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.reactivestreams.Subscriber;

/* loaded from: classes8.dex */
public final class MaybeMergeArray<T> extends Flowable<T> {
    final MaybeSource<? extends T>[] sources;

    public static final class ClqSimpleQueue<T> extends ConcurrentLinkedQueue<T> implements SimpleQueueWithConsumerIndex<T> {
        private static final long serialVersionUID = -4025173261791142821L;
        int consumerIndex;
        final AtomicInteger producerIndex = new AtomicInteger();

        @Override // io.reactivex.rxjava3.internal.operators.maybe.MaybeMergeArray.SimpleQueueWithConsumerIndex
        public int consumerIndex() {
            return this.consumerIndex;
        }

        @Override // io.reactivex.rxjava3.internal.operators.maybe.MaybeMergeArray.SimpleQueueWithConsumerIndex
        public void drop() {
            poll();
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean offer(T v12, T v2) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.concurrent.ConcurrentLinkedQueue, java.util.Queue, io.reactivex.rxjava3.internal.operators.maybe.MaybeMergeArray.SimpleQueueWithConsumerIndex, io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public T poll() {
            T t2 = (T) super.poll();
            if (t2 != null) {
                this.consumerIndex++;
            }
            return t2;
        }

        @Override // io.reactivex.rxjava3.internal.operators.maybe.MaybeMergeArray.SimpleQueueWithConsumerIndex
        public int producerIndex() {
            return this.producerIndex.get();
        }

        @Override // java.util.concurrent.ConcurrentLinkedQueue, java.util.Queue, io.reactivex.rxjava3.operators.SimpleQueue
        public boolean offer(T e2) {
            this.producerIndex.getAndIncrement();
            return super.offer(e2);
        }
    }

    public static final class MergeMaybeObserver<T> extends BasicIntQueueSubscription<T> implements MaybeObserver<T> {
        private static final long serialVersionUID = -660395290758764731L;
        volatile boolean cancelled;
        long consumed;
        final Subscriber<? super T> downstream;
        boolean outputFused;
        final SimpleQueueWithConsumerIndex<Object> queue;
        final int sourceCount;
        final CompositeDisposable set = new CompositeDisposable();
        final AtomicLong requested = new AtomicLong();
        final AtomicThrowable errors = new AtomicThrowable();

        public MergeMaybeObserver(Subscriber<? super T> actual, int sourceCount, SimpleQueueWithConsumerIndex<Object> queue) {
            this.downstream = actual;
            this.sourceCount = sourceCount;
            this.queue = queue;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.set.dispose();
            if (getAndIncrement() == 0) {
                this.queue.clear();
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public void clear() {
            this.queue.clear();
        }

        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            if (this.outputFused) {
                drainFused();
            } else {
                drainNormal();
            }
        }

        public void drainFused() {
            Subscriber<? super T> subscriber = this.downstream;
            SimpleQueueWithConsumerIndex<Object> simpleQueueWithConsumerIndex = this.queue;
            int iAddAndGet = 1;
            while (!this.cancelled) {
                Throwable th = this.errors.get();
                if (th != null) {
                    simpleQueueWithConsumerIndex.clear();
                    subscriber.onError(th);
                    return;
                }
                boolean z2 = simpleQueueWithConsumerIndex.producerIndex() == this.sourceCount;
                if (!simpleQueueWithConsumerIndex.isEmpty()) {
                    subscriber.onNext(null);
                }
                if (z2) {
                    subscriber.onComplete();
                    return;
                } else {
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                }
            }
            simpleQueueWithConsumerIndex.clear();
        }

        public void drainNormal() {
            Subscriber<? super T> subscriber = this.downstream;
            SimpleQueueWithConsumerIndex<Object> simpleQueueWithConsumerIndex = this.queue;
            long j2 = this.consumed;
            int iAddAndGet = 1;
            do {
                long j3 = this.requested.get();
                while (j2 != j3) {
                    if (this.cancelled) {
                        simpleQueueWithConsumerIndex.clear();
                        return;
                    }
                    if (this.errors.get() != null) {
                        simpleQueueWithConsumerIndex.clear();
                        this.errors.tryTerminateConsumer(this.downstream);
                        return;
                    } else {
                        if (simpleQueueWithConsumerIndex.consumerIndex() == this.sourceCount) {
                            subscriber.onComplete();
                            return;
                        }
                        Object objPoll = simpleQueueWithConsumerIndex.poll();
                        if (objPoll == null) {
                            break;
                        } else if (objPoll != NotificationLite.COMPLETE) {
                            subscriber.onNext(objPoll);
                            j2++;
                        }
                    }
                }
                if (j2 == j3) {
                    if (this.errors.get() != null) {
                        simpleQueueWithConsumerIndex.clear();
                        this.errors.tryTerminateConsumer(this.downstream);
                        return;
                    } else {
                        while (simpleQueueWithConsumerIndex.peek() == NotificationLite.COMPLETE) {
                            simpleQueueWithConsumerIndex.drop();
                        }
                        if (simpleQueueWithConsumerIndex.consumerIndex() == this.sourceCount) {
                            subscriber.onComplete();
                            return;
                        }
                    }
                }
                this.consumed = j2;
                iAddAndGet = addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }

        public boolean isCancelled() {
            return this.cancelled;
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean isEmpty() {
            return this.queue.isEmpty();
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onComplete() {
            this.queue.offer(NotificationLite.COMPLETE);
            drain();
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onError(Throwable e2) {
            if (this.errors.tryAddThrowableOrReport(e2)) {
                this.set.dispose();
                this.queue.offer(NotificationLite.COMPLETE);
                drain();
            }
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
        public void onSubscribe(Disposable d3) {
            this.set.add(d3);
        }

        @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver
        public void onSuccess(T value) {
            this.queue.offer(value);
            drain();
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public T poll() {
            T t2;
            do {
                t2 = (T) this.queue.poll();
            } while (t2 == NotificationLite.COMPLETE);
            return t2;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            if (SubscriptionHelper.validate(n2)) {
                BackpressureHelper.add(this.requested, n2);
                drain();
            }
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int mode) {
            if ((mode & 2) == 0) {
                return 0;
            }
            this.outputFused = true;
            return 2;
        }
    }

    public interface SimpleQueueWithConsumerIndex<T> extends SimpleQueue<T> {
        int consumerIndex();

        void drop();

        T peek();

        @Override // java.util.Queue, io.reactivex.rxjava3.internal.operators.maybe.MaybeMergeArray.SimpleQueueWithConsumerIndex, io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        T poll();

        int producerIndex();
    }

    public MaybeMergeArray(MaybeSource<? extends T>[] sources) {
        this.sources = sources;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        MaybeSource[] maybeSourceArr = this.sources;
        int length = maybeSourceArr.length;
        MergeMaybeObserver mergeMaybeObserver = new MergeMaybeObserver(s2, length, length <= Flowable.bufferSize() ? new MpscFillOnceSimpleQueue(length) : new ClqSimpleQueue());
        s2.onSubscribe(mergeMaybeObserver);
        AtomicThrowable atomicThrowable = mergeMaybeObserver.errors;
        for (MaybeSource maybeSource : maybeSourceArr) {
            if (mergeMaybeObserver.isCancelled() || atomicThrowable.get() != null) {
                return;
            }
            maybeSource.subscribe(mergeMaybeObserver);
        }
    }

    public static final class MpscFillOnceSimpleQueue<T> extends AtomicReferenceArray<T> implements SimpleQueueWithConsumerIndex<T> {
        private static final long serialVersionUID = -7969063454040569579L;
        int consumerIndex;
        final AtomicInteger producerIndex;

        public MpscFillOnceSimpleQueue(int length) {
            super(length);
            this.producerIndex = new AtomicInteger();
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public void clear() {
            while (poll() != null && !isEmpty()) {
            }
        }

        @Override // io.reactivex.rxjava3.internal.operators.maybe.MaybeMergeArray.SimpleQueueWithConsumerIndex
        public int consumerIndex() {
            return this.consumerIndex;
        }

        @Override // io.reactivex.rxjava3.internal.operators.maybe.MaybeMergeArray.SimpleQueueWithConsumerIndex
        public void drop() {
            int i2 = this.consumerIndex;
            lazySet(i2, null);
            this.consumerIndex = i2 + 1;
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean isEmpty() {
            return this.consumerIndex == producerIndex();
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean offer(T value) {
            Objects.requireNonNull(value, "value is null");
            int andIncrement = this.producerIndex.getAndIncrement();
            if (andIncrement >= length()) {
                return false;
            }
            lazySet(andIncrement, value);
            return true;
        }

        @Override // io.reactivex.rxjava3.internal.operators.maybe.MaybeMergeArray.SimpleQueueWithConsumerIndex
        public T peek() {
            int i2 = this.consumerIndex;
            if (i2 == length()) {
                return null;
            }
            return get(i2);
        }

        @Override // io.reactivex.rxjava3.internal.operators.maybe.MaybeMergeArray.SimpleQueueWithConsumerIndex, java.util.Queue, io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public T poll() {
            int i2 = this.consumerIndex;
            if (i2 == length()) {
                return null;
            }
            AtomicInteger atomicInteger = this.producerIndex;
            do {
                T t2 = get(i2);
                if (t2 != null) {
                    this.consumerIndex = i2 + 1;
                    lazySet(i2, null);
                    return t2;
                }
            } while (atomicInteger.get() != i2);
            return null;
        }

        @Override // io.reactivex.rxjava3.internal.operators.maybe.MaybeMergeArray.SimpleQueueWithConsumerIndex
        public int producerIndex() {
            return this.producerIndex.get();
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean offer(T v12, T v2) {
            throw new UnsupportedOperationException();
        }
    }
}
