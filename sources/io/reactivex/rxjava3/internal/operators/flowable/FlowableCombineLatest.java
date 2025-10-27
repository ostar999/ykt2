package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableMap;
import io.reactivex.rxjava3.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.operators.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Objects;
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
        final AtomicThrowable error;
        final Object[] latest;
        int nonEmptySources;
        boolean outputFused;
        final SpscLinkedArrayQueue<Object> queue;
        final AtomicLong requested;
        final CombineLatestInnerSubscriber<T>[] subscribers;

        public CombineLatestCoordinator(Subscriber<? super R> actual, Function<? super Object[], ? extends R> combiner, int n2, int bufferSize, boolean delayErrors) {
            this.downstream = actual;
            this.combiner = combiner;
            CombineLatestInnerSubscriber<T>[] combineLatestInnerSubscriberArr = new CombineLatestInnerSubscriber[n2];
            for (int i2 = 0; i2 < n2; i2++) {
                combineLatestInnerSubscriberArr[i2] = new CombineLatestInnerSubscriber<>(this, i2, bufferSize);
            }
            this.subscribers = combineLatestInnerSubscriberArr;
            this.latest = new Object[n2];
            this.queue = new SpscLinkedArrayQueue<>(bufferSize);
            this.requested = new AtomicLong();
            this.error = new AtomicThrowable();
            this.delayErrors = delayErrors;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            cancelAll();
            drain();
        }

        public void cancelAll() {
            for (CombineLatestInnerSubscriber<T> combineLatestInnerSubscriber : this.subscribers) {
                combineLatestInnerSubscriber.cancel();
            }
        }

        public boolean checkTerminated(boolean d3, boolean empty, Subscriber<?> a3, SpscLinkedArrayQueue<?> q2) {
            if (this.cancelled) {
                cancelAll();
                q2.clear();
                this.error.tryTerminateAndReport();
                return true;
            }
            if (!d3) {
                return false;
            }
            if (this.delayErrors) {
                if (!empty) {
                    return false;
                }
                cancelAll();
                this.error.tryTerminateConsumer(a3);
                return true;
            }
            Throwable thTerminate = ExceptionHelper.terminate(this.error);
            if (thTerminate != null && thTerminate != ExceptionHelper.TERMINATED) {
                cancelAll();
                q2.clear();
                a3.onError(thTerminate);
                return true;
            }
            if (!empty) {
                return false;
            }
            cancelAll();
            a3.onComplete();
            return true;
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
                        R rApply = this.combiner.apply((Object[]) spscLinkedArrayQueue.poll());
                        Objects.requireNonNull(rApply, "The combiner returned a null value");
                        subscriber.onNext(rApply);
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

        public void innerComplete(int index) {
            int i2;
            synchronized (this) {
                Object[] objArr = this.latest;
                if (objArr[index] != null && (i2 = this.completedSources + 1) != objArr.length) {
                    this.completedSources = i2;
                } else {
                    this.done = true;
                    drain();
                }
            }
        }

        public void innerError(int index, Throwable e2) {
            if (!ExceptionHelper.addThrowable(this.error, e2)) {
                RxJavaPlugins.onError(e2);
            } else {
                if (this.delayErrors) {
                    innerComplete(index);
                    return;
                }
                cancelAll();
                this.done = true;
                drain();
            }
        }

        public void innerValue(int index, T value) {
            boolean z2;
            synchronized (this) {
                Object[] objArr = this.latest;
                int i2 = this.nonEmptySources;
                if (objArr[index] == null) {
                    i2++;
                    this.nonEmptySources = i2;
                }
                objArr[index] = value;
                if (objArr.length == i2) {
                    this.queue.offer(this.subscribers[index], objArr.clone());
                    z2 = false;
                } else {
                    z2 = true;
                }
            }
            if (z2) {
                this.subscribers[index].requestOne();
            } else {
                drain();
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public boolean isEmpty() {
            return this.queue.isEmpty();
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        @Nullable
        public R poll() throws Throwable {
            Object objPoll = this.queue.poll();
            if (objPoll == null) {
                return null;
            }
            R rApply = this.combiner.apply((Object[]) this.queue.poll());
            Objects.requireNonNull(rApply, "The combiner returned a null value");
            ((CombineLatestInnerSubscriber) objPoll).requestOne();
            return rApply;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            if (SubscriptionHelper.validate(n2)) {
                BackpressureHelper.add(this.requested, n2);
                drain();
            }
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int requestedMode) {
            if ((requestedMode & 4) != 0) {
                return 0;
            }
            int i2 = requestedMode & 2;
            this.outputFused = i2 != 0;
            return i2;
        }

        public void subscribe(Publisher<? extends T>[] sources, int n2) {
            CombineLatestInnerSubscriber<T>[] combineLatestInnerSubscriberArr = this.subscribers;
            for (int i2 = 0; i2 < n2 && !this.done && !this.cancelled; i2++) {
                sources[i2].subscribe(combineLatestInnerSubscriberArr[i2]);
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

        public CombineLatestInnerSubscriber(CombineLatestCoordinator<T, ?> parent, int index, int prefetch) {
            this.parent = parent;
            this.index = index;
            this.prefetch = prefetch;
            this.limit = prefetch - (prefetch >> 2);
        }

        public void cancel() {
            SubscriptionHelper.cancel(this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.parent.innerComplete(this.index);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            this.parent.innerError(this.index, t2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            this.parent.innerValue(this.index, t2);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            SubscriptionHelper.setOnce(this, s2, this.prefetch);
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
        @Override // io.reactivex.rxjava3.functions.Function
        public R apply(T t2) throws Throwable {
            return FlowableCombineLatest.this.combiner.apply(new Object[]{t2});
        }
    }

    public FlowableCombineLatest(@NonNull Publisher<? extends T>[] array, @NonNull Function<? super Object[], ? extends R> combiner, int bufferSize, boolean delayErrors) {
        this.array = array;
        this.iterable = null;
        this.combiner = combiner;
        this.bufferSize = bufferSize;
        this.delayErrors = delayErrors;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super R> s2) {
        int length;
        Publisher<? extends T>[] publisherArr = this.array;
        if (publisherArr == null) {
            publisherArr = new Publisher[8];
            try {
                length = 0;
                for (Publisher<? extends T> publisher : this.iterable) {
                    if (length == publisherArr.length) {
                        Publisher<? extends T>[] publisherArr2 = new Publisher[(length >> 2) + length];
                        System.arraycopy(publisherArr, 0, publisherArr2, 0, length);
                        publisherArr = publisherArr2;
                    }
                    int i2 = length + 1;
                    Objects.requireNonNull(publisher, "The Iterator returned a null Publisher");
                    publisherArr[length] = publisher;
                    length = i2;
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptySubscription.error(th, s2);
                return;
            }
        } else {
            length = publisherArr.length;
        }
        int i3 = length;
        if (i3 == 0) {
            EmptySubscription.complete(s2);
        } else {
            if (i3 == 1) {
                publisherArr[0].subscribe(new FlowableMap.MapSubscriber(s2, new SingletonArrayFunc()));
                return;
            }
            CombineLatestCoordinator combineLatestCoordinator = new CombineLatestCoordinator(s2, this.combiner, i3, this.bufferSize, this.delayErrors);
            s2.onSubscribe(combineLatestCoordinator);
            combineLatestCoordinator.subscribe(publisherArr, i3);
        }
    }

    public FlowableCombineLatest(@NonNull Iterable<? extends Publisher<? extends T>> iterable, @NonNull Function<? super Object[], ? extends R> combiner, int bufferSize, boolean delayErrors) {
        this.array = null;
        this.iterable = iterable;
        this.combiner = combiner;
        this.bufferSize = bufferSize;
        this.delayErrors = delayErrors;
    }
}
