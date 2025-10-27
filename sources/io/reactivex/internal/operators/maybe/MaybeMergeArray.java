package io.reactivex.internal.operators.maybe;

import io.reactivex.Flowable;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
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

        @Override // io.reactivex.internal.operators.maybe.MaybeMergeArray.SimpleQueueWithConsumerIndex
        public int consumerIndex() {
            return this.consumerIndex;
        }

        @Override // io.reactivex.internal.operators.maybe.MaybeMergeArray.SimpleQueueWithConsumerIndex
        public void drop() {
            poll();
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public boolean offer(T t2, T t3) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.concurrent.ConcurrentLinkedQueue, java.util.Queue, io.reactivex.internal.operators.maybe.MaybeMergeArray.SimpleQueueWithConsumerIndex, io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() {
            T t2 = (T) super.poll();
            if (t2 != null) {
                this.consumerIndex++;
            }
            return t2;
        }

        @Override // io.reactivex.internal.operators.maybe.MaybeMergeArray.SimpleQueueWithConsumerIndex
        public int producerIndex() {
            return this.producerIndex.get();
        }

        @Override // java.util.concurrent.ConcurrentLinkedQueue, java.util.Queue, io.reactivex.internal.fuseable.SimpleQueue
        public boolean offer(T t2) {
            this.producerIndex.getAndIncrement();
            return super.offer(t2);
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
        final AtomicThrowable error = new AtomicThrowable();

        public MergeMaybeObserver(Subscriber<? super T> subscriber, int i2, SimpleQueueWithConsumerIndex<Object> simpleQueueWithConsumerIndex) {
            this.downstream = subscriber;
            this.sourceCount = i2;
            this.queue = simpleQueueWithConsumerIndex;
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

        @Override // io.reactivex.internal.fuseable.SimpleQueue
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
                Throwable th = this.error.get();
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
                    if (this.error.get() != null) {
                        simpleQueueWithConsumerIndex.clear();
                        subscriber.onError(this.error.terminate());
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
                    if (this.error.get() != null) {
                        simpleQueueWithConsumerIndex.clear();
                        subscriber.onError(this.error.terminate());
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

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return this.queue.isEmpty();
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            this.queue.offer(NotificationLite.COMPLETE);
            drain();
        }

        @Override // io.reactivex.MaybeObserver
        public void onError(Throwable th) {
            if (!this.error.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.set.dispose();
            this.queue.offer(NotificationLite.COMPLETE);
            drain();
        }

        @Override // io.reactivex.MaybeObserver
        public void onSubscribe(Disposable disposable) {
            this.set.add(disposable);
        }

        @Override // io.reactivex.MaybeObserver
        public void onSuccess(T t2) {
            this.queue.offer(t2);
            drain();
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Exception {
            T t2;
            do {
                t2 = (T) this.queue.poll();
            } while (t2 == NotificationLite.COMPLETE);
            return t2;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                BackpressureHelper.add(this.requested, j2);
                drain();
            }
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i2) {
            if ((i2 & 2) == 0) {
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

        @Override // java.util.Queue, io.reactivex.internal.operators.maybe.MaybeMergeArray.SimpleQueueWithConsumerIndex, io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        T poll();

        int producerIndex();
    }

    public MaybeMergeArray(MaybeSource<? extends T>[] maybeSourceArr) {
        this.sources = maybeSourceArr;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        MaybeSource[] maybeSourceArr = this.sources;
        int length = maybeSourceArr.length;
        MergeMaybeObserver mergeMaybeObserver = new MergeMaybeObserver(subscriber, length, length <= Flowable.bufferSize() ? new MpscFillOnceSimpleQueue(length) : new ClqSimpleQueue());
        subscriber.onSubscribe(mergeMaybeObserver);
        AtomicThrowable atomicThrowable = mergeMaybeObserver.error;
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

        public MpscFillOnceSimpleQueue(int i2) {
            super(i2);
            this.producerIndex = new AtomicInteger();
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public void clear() {
            while (poll() != null && !isEmpty()) {
            }
        }

        @Override // io.reactivex.internal.operators.maybe.MaybeMergeArray.SimpleQueueWithConsumerIndex
        public int consumerIndex() {
            return this.consumerIndex;
        }

        @Override // io.reactivex.internal.operators.maybe.MaybeMergeArray.SimpleQueueWithConsumerIndex
        public void drop() {
            int i2 = this.consumerIndex;
            lazySet(i2, null);
            this.consumerIndex = i2 + 1;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return this.consumerIndex == producerIndex();
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public boolean offer(T t2) {
            ObjectHelper.requireNonNull(t2, "value is null");
            int andIncrement = this.producerIndex.getAndIncrement();
            if (andIncrement >= length()) {
                return false;
            }
            lazySet(andIncrement, t2);
            return true;
        }

        @Override // io.reactivex.internal.operators.maybe.MaybeMergeArray.SimpleQueueWithConsumerIndex
        public T peek() {
            int i2 = this.consumerIndex;
            if (i2 == length()) {
                return null;
            }
            return get(i2);
        }

        @Override // io.reactivex.internal.operators.maybe.MaybeMergeArray.SimpleQueueWithConsumerIndex, java.util.Queue, io.reactivex.internal.fuseable.SimpleQueue
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

        @Override // io.reactivex.internal.operators.maybe.MaybeMergeArray.SimpleQueueWithConsumerIndex
        public int producerIndex() {
            return this.producerIndex.get();
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public boolean offer(T t2, T t3) {
            throw new UnsupportedOperationException();
        }
    }
}
