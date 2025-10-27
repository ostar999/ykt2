package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.flowable.FlowableMap;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableCombineLatest<T, R> extends Flowable<R> {

    @Nullable
    final Publisher<? extends T>[] array;
    final int bufferSize;
    final Function<? super Object[], ? extends R> combiner;
    final boolean delayErrors;

    @Nullable
    final Iterable<? extends Publisher<? extends T>> iterable;

    public static final class CombineLatestCoordinator<T, R> extends BasicIntQueueSubscription<R> {
        private static final long serialVersionUID = -5082275438355852221L;
        volatile boolean cancelled;
        final Function<? super Object[], ? extends R> combiner;
        int completedSources;
        final boolean delayErrors;
        volatile boolean done;
        final Subscriber<? super R> downstream;
        final AtomicReference<Throwable> error;
        final Object[] latest;
        int nonEmptySources;
        boolean outputFused;
        final SpscLinkedArrayQueue<Object> queue;
        final AtomicLong requested;
        final CombineLatestInnerSubscriber<T>[] subscribers;

        public CombineLatestCoordinator(Subscriber<? super R> subscriber, Function<? super Object[], ? extends R> function, int i2, int i3, boolean z2) {
            this.downstream = subscriber;
            this.combiner = function;
            CombineLatestInnerSubscriber<T>[] combineLatestInnerSubscriberArr = new CombineLatestInnerSubscriber[i2];
            for (int i4 = 0; i4 < i2; i4++) {
                combineLatestInnerSubscriberArr[i4] = new CombineLatestInnerSubscriber<>(this, i4, i3);
            }
            this.subscribers = combineLatestInnerSubscriberArr;
            this.latest = new Object[i2];
            this.queue = new SpscLinkedArrayQueue<>(i3);
            this.requested = new AtomicLong();
            this.error = new AtomicReference<>();
            this.delayErrors = z2;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            cancelAll();
        }

        public void cancelAll() {
            for (CombineLatestInnerSubscriber<T> combineLatestInnerSubscriber : this.subscribers) {
                combineLatestInnerSubscriber.cancel();
            }
        }

        public boolean checkTerminated(boolean z2, boolean z3, Subscriber<?> subscriber, SpscLinkedArrayQueue<?> spscLinkedArrayQueue) {
            if (this.cancelled) {
                cancelAll();
                spscLinkedArrayQueue.clear();
                return true;
            }
            if (!z2) {
                return false;
            }
            if (this.delayErrors) {
                if (!z3) {
                    return false;
                }
                cancelAll();
                Throwable thTerminate = ExceptionHelper.terminate(this.error);
                if (thTerminate == null || thTerminate == ExceptionHelper.TERMINATED) {
                    subscriber.onComplete();
                } else {
                    subscriber.onError(thTerminate);
                }
                return true;
            }
            Throwable thTerminate2 = ExceptionHelper.terminate(this.error);
            if (thTerminate2 != null && thTerminate2 != ExceptionHelper.TERMINATED) {
                cancelAll();
                spscLinkedArrayQueue.clear();
                subscriber.onError(thTerminate2);
                return true;
            }
            if (!z3) {
                return false;
            }
            cancelAll();
            subscriber.onComplete();
            return true;
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
                drainOutput();
            } else {
                drainAsync();
            }
        }

        public void drainAsync() {
            Subscriber<? super R> subscriber = this.downstream;
            SpscLinkedArrayQueue<?> spscLinkedArrayQueue = this.queue;
            int iAddAndGet = 1;
            do {
                long j2 = this.requested.get();
                long j3 = 0;
                while (j3 != j2) {
                    boolean z2 = this.done;
                    Object objPoll = spscLinkedArrayQueue.poll();
                    boolean z3 = objPoll == null;
                    if (checkTerminated(z2, z3, subscriber, spscLinkedArrayQueue)) {
                        return;
                    }
                    if (z3) {
                        break;
                    }
                    try {
                        subscriber.onNext((Object) ObjectHelper.requireNonNull(this.combiner.apply((Object[]) spscLinkedArrayQueue.poll()), "The combiner returned a null value"));
                        ((CombineLatestInnerSubscriber) objPoll).requestOne();
                        j3++;
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        cancelAll();
                        ExceptionHelper.addThrowable(this.error, th);
                        subscriber.onError(ExceptionHelper.terminate(this.error));
                        return;
                    }
                }
                if (j3 == j2 && checkTerminated(this.done, spscLinkedArrayQueue.isEmpty(), subscriber, spscLinkedArrayQueue)) {
                    return;
                }
                if (j3 != 0 && j2 != Long.MAX_VALUE) {
                    this.requested.addAndGet(-j3);
                }
                iAddAndGet = addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }

        public void drainOutput() {
            Subscriber<? super R> subscriber = this.downstream;
            SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.queue;
            int iAddAndGet = 1;
            while (!this.cancelled) {
                Throwable th = this.error.get();
                if (th != null) {
                    spscLinkedArrayQueue.clear();
                    subscriber.onError(th);
                    return;
                }
                boolean z2 = this.done;
                boolean zIsEmpty = spscLinkedArrayQueue.isEmpty();
                if (!zIsEmpty) {
                    subscriber.onNext(null);
                }
                if (z2 && zIsEmpty) {
                    subscriber.onComplete();
                    return;
                } else {
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                }
            }
            spscLinkedArrayQueue.clear();
        }

        public void innerComplete(int i2) {
            int i3;
            synchronized (this) {
                Object[] objArr = this.latest;
                if (objArr[i2] != null && (i3 = this.completedSources + 1) != objArr.length) {
                    this.completedSources = i3;
                } else {
                    this.done = true;
                    drain();
                }
            }
        }

        public void innerError(int i2, Throwable th) {
            if (!ExceptionHelper.addThrowable(this.error, th)) {
                RxJavaPlugins.onError(th);
            } else {
                if (this.delayErrors) {
                    innerComplete(i2);
                    return;
                }
                cancelAll();
                this.done = true;
                drain();
            }
        }

        public void innerValue(int i2, T t2) {
            boolean z2;
            synchronized (this) {
                Object[] objArr = this.latest;
                int i3 = this.nonEmptySources;
                if (objArr[i2] == null) {
                    i3++;
                    this.nonEmptySources = i3;
                }
                objArr[i2] = t2;
                if (objArr.length == i3) {
                    this.queue.offer(this.subscribers[i2], objArr.clone());
                    z2 = false;
                } else {
                    z2 = true;
                }
            }
            if (z2) {
                this.subscribers[i2].requestOne();
            } else {
                drain();
            }
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public boolean isEmpty() {
            return this.queue.isEmpty();
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public R poll() throws Exception {
            Object objPoll = this.queue.poll();
            if (objPoll == null) {
                return null;
            }
            R r2 = (R) ObjectHelper.requireNonNull(this.combiner.apply((Object[]) this.queue.poll()), "The combiner returned a null value");
            ((CombineLatestInnerSubscriber) objPoll).requestOne();
            return r2;
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
            if ((i2 & 4) != 0) {
                return 0;
            }
            int i3 = i2 & 2;
            this.outputFused = i3 != 0;
            return i3;
        }

        public void subscribe(Publisher<? extends T>[] publisherArr, int i2) {
            CombineLatestInnerSubscriber<T>[] combineLatestInnerSubscriberArr = this.subscribers;
            for (int i3 = 0; i3 < i2 && !this.done && !this.cancelled; i3++) {
                publisherArr[i3].subscribe(combineLatestInnerSubscriberArr[i3]);
            }
        }
    }

    public static final class CombineLatestInnerSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -8730235182291002949L;
        final int index;
        final int limit;
        final CombineLatestCoordinator<T, ?> parent;
        final int prefetch;
        int produced;

        public CombineLatestInnerSubscriber(CombineLatestCoordinator<T, ?> combineLatestCoordinator, int i2, int i3) {
            this.parent = combineLatestCoordinator;
            this.index = i2;
            this.prefetch = i3;
            this.limit = i3 - (i3 >> 2);
        }

        public void cancel() {
            SubscriptionHelper.cancel(this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.parent.innerComplete(this.index);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.parent.innerError(this.index, th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            this.parent.innerValue(this.index, t2);
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.setOnce(this, subscription, this.prefetch);
        }

        public void requestOne() {
            int i2 = this.produced + 1;
            if (i2 != this.limit) {
                this.produced = i2;
            } else {
                this.produced = 0;
                get().request(i2);
            }
        }
    }

    public final class SingletonArrayFunc implements Function<T, R> {
        public SingletonArrayFunc() {
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object, java.lang.Object[]] */
        @Override // io.reactivex.functions.Function
        public R apply(T t2) throws Exception {
            return FlowableCombineLatest.this.combiner.apply(new Object[]{t2});
        }
    }

    public FlowableCombineLatest(@NonNull Publisher<? extends T>[] publisherArr, @NonNull Function<? super Object[], ? extends R> function, int i2, boolean z2) {
        this.array = publisherArr;
        this.iterable = null;
        this.combiner = function;
        this.bufferSize = i2;
        this.delayErrors = z2;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super R> subscriber) {
        int length;
        Publisher<? extends T>[] publisherArr = this.array;
        if (publisherArr == null) {
            publisherArr = new Publisher[8];
            try {
                Iterator it = (Iterator) ObjectHelper.requireNonNull(this.iterable.iterator(), "The iterator returned is null");
                length = 0;
                while (it.hasNext()) {
                    try {
                        try {
                            Publisher<? extends T> publisher = (Publisher) ObjectHelper.requireNonNull(it.next(), "The publisher returned by the iterator is null");
                            if (length == publisherArr.length) {
                                Publisher<? extends T>[] publisherArr2 = new Publisher[(length >> 2) + length];
                                System.arraycopy(publisherArr, 0, publisherArr2, 0, length);
                                publisherArr = publisherArr2;
                            }
                            publisherArr[length] = publisher;
                            length++;
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            EmptySubscription.error(th, subscriber);
                            return;
                        }
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        EmptySubscription.error(th2, subscriber);
                        return;
                    }
                }
            } catch (Throwable th3) {
                Exceptions.throwIfFatal(th3);
                EmptySubscription.error(th3, subscriber);
                return;
            }
        } else {
            length = publisherArr.length;
        }
        int i2 = length;
        if (i2 == 0) {
            EmptySubscription.complete(subscriber);
        } else {
            if (i2 == 1) {
                publisherArr[0].subscribe(new FlowableMap.MapSubscriber(subscriber, new SingletonArrayFunc()));
                return;
            }
            CombineLatestCoordinator combineLatestCoordinator = new CombineLatestCoordinator(subscriber, this.combiner, i2, this.bufferSize, this.delayErrors);
            subscriber.onSubscribe(combineLatestCoordinator);
            combineLatestCoordinator.subscribe(publisherArr, i2);
        }
    }

    public FlowableCombineLatest(@NonNull Iterable<? extends Publisher<? extends T>> iterable, @NonNull Function<? super Object[], ? extends R> function, int i2, boolean z2) {
        this.array = null;
        this.iterable = iterable;
        this.combiner = function;
        this.bufferSize = i2;
        this.delayErrors = z2;
    }
}
