package io.reactivex.rxjava3.internal.operators.parallel;

import androidx.camera.view.j;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class ParallelSortedJoin<T> extends Flowable<T> {
    final Comparator<? super T> comparator;
    final ParallelFlowable<List<T>> source;

    public static final class SortedJoinInnerSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<List<T>> {
        private static final long serialVersionUID = 6751017204873808094L;
        final int index;
        final SortedJoinSubscription<T> parent;

        public SortedJoinInnerSubscriber(SortedJoinSubscription<T> parent, int index) {
            this.parent = parent;
            this.index = index;
        }

        public void cancel() {
            SubscriptionHelper.cancel(this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            this.parent.innerError(t2);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            SubscriptionHelper.setOnce(this, s2, Long.MAX_VALUE);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(List<T> t2) {
            this.parent.innerNext(t2, this.index);
        }
    }

    public static final class SortedJoinSubscription<T> extends AtomicInteger implements Subscription {
        private static final long serialVersionUID = 3481980673745556697L;
        volatile boolean cancelled;
        final Comparator<? super T> comparator;
        final Subscriber<? super T> downstream;
        final int[] indexes;
        final List<T>[] lists;
        final SortedJoinInnerSubscriber<T>[] subscribers;
        final AtomicLong requested = new AtomicLong();
        final AtomicInteger remaining = new AtomicInteger();
        final AtomicReference<Throwable> error = new AtomicReference<>();

        public SortedJoinSubscription(Subscriber<? super T> actual, int n2, Comparator<? super T> comparator) {
            this.downstream = actual;
            this.comparator = comparator;
            SortedJoinInnerSubscriber<T>[] sortedJoinInnerSubscriberArr = new SortedJoinInnerSubscriber[n2];
            for (int i2 = 0; i2 < n2; i2++) {
                sortedJoinInnerSubscriberArr[i2] = new SortedJoinInnerSubscriber<>(this, i2);
            }
            this.subscribers = sortedJoinInnerSubscriberArr;
            this.lists = new List[n2];
            this.indexes = new int[n2];
            this.remaining.lazySet(n2);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            cancelAll();
            if (getAndIncrement() == 0) {
                Arrays.fill(this.lists, (Object) null);
            }
        }

        public void cancelAll() {
            for (SortedJoinInnerSubscriber<T> sortedJoinInnerSubscriber : this.subscribers) {
                sortedJoinInnerSubscriber.cancel();
            }
        }

        public void drain() {
            boolean z2;
            if (getAndIncrement() != 0) {
                return;
            }
            Subscriber<? super T> subscriber = this.downstream;
            List<T>[] listArr = this.lists;
            int[] iArr = this.indexes;
            int length = iArr.length;
            int iAddAndGet = 1;
            do {
                long j2 = this.requested.get();
                long j3 = 0;
                while (j3 != j2) {
                    if (this.cancelled) {
                        Arrays.fill(listArr, (Object) null);
                        return;
                    }
                    Throwable th = this.error.get();
                    if (th != null) {
                        cancelAll();
                        Arrays.fill(listArr, (Object) null);
                        subscriber.onError(th);
                        return;
                    }
                    int i2 = -1;
                    T t2 = null;
                    for (int i3 = 0; i3 < length; i3++) {
                        List<T> list = listArr[i3];
                        int i4 = iArr[i3];
                        if (list.size() != i4) {
                            if (t2 == null) {
                                t2 = list.get(i4);
                            } else {
                                T t3 = list.get(i4);
                                try {
                                    if (this.comparator.compare(t2, t3) > 0) {
                                        t2 = t3;
                                    }
                                } catch (Throwable th2) {
                                    Exceptions.throwIfFatal(th2);
                                    cancelAll();
                                    Arrays.fill(listArr, (Object) null);
                                    if (!j.a(this.error, null, th2)) {
                                        RxJavaPlugins.onError(th2);
                                    }
                                    subscriber.onError(this.error.get());
                                    return;
                                }
                            }
                            i2 = i3;
                        }
                    }
                    if (t2 == null) {
                        Arrays.fill(listArr, (Object) null);
                        subscriber.onComplete();
                        return;
                    } else {
                        subscriber.onNext(t2);
                        iArr[i2] = iArr[i2] + 1;
                        j3++;
                    }
                }
                if (this.cancelled) {
                    Arrays.fill(listArr, (Object) null);
                    return;
                }
                Throwable th3 = this.error.get();
                if (th3 != null) {
                    cancelAll();
                    Arrays.fill(listArr, (Object) null);
                    subscriber.onError(th3);
                    return;
                }
                int i5 = 0;
                while (true) {
                    if (i5 >= length) {
                        z2 = true;
                        break;
                    } else {
                        if (iArr[i5] != listArr[i5].size()) {
                            z2 = false;
                            break;
                        }
                        i5++;
                    }
                }
                if (z2) {
                    Arrays.fill(listArr, (Object) null);
                    subscriber.onComplete();
                    return;
                } else {
                    if (j3 != 0) {
                        BackpressureHelper.produced(this.requested, j3);
                    }
                    iAddAndGet = addAndGet(-iAddAndGet);
                }
            } while (iAddAndGet != 0);
        }

        public void innerError(Throwable e2) {
            if (j.a(this.error, null, e2)) {
                drain();
            } else if (e2 != this.error.get()) {
                RxJavaPlugins.onError(e2);
            }
        }

        public void innerNext(List<T> value, int index) {
            this.lists[index] = value;
            if (this.remaining.decrementAndGet() == 0) {
                drain();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            if (SubscriptionHelper.validate(n2)) {
                BackpressureHelper.add(this.requested, n2);
                if (this.remaining.get() == 0) {
                    drain();
                }
            }
        }
    }

    public ParallelSortedJoin(ParallelFlowable<List<T>> source, Comparator<? super T> comparator) {
        this.source = source;
        this.comparator = comparator;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super T> s2) {
        SortedJoinSubscription sortedJoinSubscription = new SortedJoinSubscription(s2, this.source.parallelism(), this.comparator);
        s2.onSubscribe(sortedJoinSubscription);
        this.source.subscribe(sortedJoinSubscription.subscribers);
    }
}
