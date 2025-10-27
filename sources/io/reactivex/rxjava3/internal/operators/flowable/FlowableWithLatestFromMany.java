package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.HalfSerializer;
import io.reactivex.rxjava3.operators.ConditionalSubscriber;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableWithLatestFromMany<T, R> extends AbstractFlowableWithUpstream<T, R> {
    final Function<? super Object[], R> combiner;

    @Nullable
    final Publisher<?>[] otherArray;

    @Nullable
    final Iterable<? extends Publisher<?>> otherIterable;

    public final class SingletonArrayFunc implements Function<T, R> {
        public SingletonArrayFunc() {
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object, java.lang.Object[]] */
        @Override // io.reactivex.rxjava3.functions.Function
        public R apply(T t2) throws Throwable {
            R rApply = FlowableWithLatestFromMany.this.combiner.apply(new Object[]{t2});
            Objects.requireNonNull(rApply, "The combiner returned a null value");
            return rApply;
        }
    }

    public static final class WithLatestFromSubscriber<T, R> extends AtomicInteger implements ConditionalSubscriber<T>, Subscription {
        private static final long serialVersionUID = 1577321883966341961L;
        final Function<? super Object[], R> combiner;
        volatile boolean done;
        final Subscriber<? super R> downstream;
        final AtomicThrowable error;
        final AtomicLong requested;
        final WithLatestInnerSubscriber[] subscribers;
        final AtomicReference<Subscription> upstream;
        final AtomicReferenceArray<Object> values;

        public WithLatestFromSubscriber(Subscriber<? super R> actual, Function<? super Object[], R> combiner, int n2) {
            this.downstream = actual;
            this.combiner = combiner;
            WithLatestInnerSubscriber[] withLatestInnerSubscriberArr = new WithLatestInnerSubscriber[n2];
            for (int i2 = 0; i2 < n2; i2++) {
                withLatestInnerSubscriberArr[i2] = new WithLatestInnerSubscriber(this, i2);
            }
            this.subscribers = withLatestInnerSubscriberArr;
            this.values = new AtomicReferenceArray<>(n2);
            this.upstream = new AtomicReference<>();
            this.requested = new AtomicLong();
            this.error = new AtomicThrowable();
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SubscriptionHelper.cancel(this.upstream);
            for (WithLatestInnerSubscriber withLatestInnerSubscriber : this.subscribers) {
                withLatestInnerSubscriber.dispose();
            }
        }

        public void cancelAllBut(int index) {
            WithLatestInnerSubscriber[] withLatestInnerSubscriberArr = this.subscribers;
            for (int i2 = 0; i2 < withLatestInnerSubscriberArr.length; i2++) {
                if (i2 != index) {
                    withLatestInnerSubscriberArr[i2].dispose();
                }
            }
        }

        public void innerComplete(int index, boolean nonEmpty) {
            if (nonEmpty) {
                return;
            }
            this.done = true;
            SubscriptionHelper.cancel(this.upstream);
            cancelAllBut(index);
            HalfSerializer.onComplete(this.downstream, this, this.error);
        }

        public void innerError(int index, Throwable t2) {
            this.done = true;
            SubscriptionHelper.cancel(this.upstream);
            cancelAllBut(index);
            HalfSerializer.onError(this.downstream, t2, this, this.error);
        }

        public void innerNext(int index, Object o2) {
            this.values.set(index, o2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            cancelAllBut(-1);
            HalfSerializer.onComplete(this.downstream, this, this.error);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            if (this.done) {
                RxJavaPlugins.onError(t2);
                return;
            }
            this.done = true;
            cancelAllBut(-1);
            HalfSerializer.onError(this.downstream, t2, this, this.error);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (tryOnNext(t2) || this.done) {
                return;
            }
            this.upstream.get().request(1L);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            SubscriptionHelper.deferredSetOnce(this.upstream, this.requested, s2);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            SubscriptionHelper.deferredRequest(this.upstream, this.requested, n2);
        }

        public void subscribe(Publisher<?>[] others, int n2) {
            WithLatestInnerSubscriber[] withLatestInnerSubscriberArr = this.subscribers;
            AtomicReference<Subscription> atomicReference = this.upstream;
            for (int i2 = 0; i2 < n2 && atomicReference.get() != SubscriptionHelper.CANCELLED; i2++) {
                others[i2].subscribe(withLatestInnerSubscriberArr[i2]);
            }
        }

        @Override // io.reactivex.rxjava3.operators.ConditionalSubscriber
        public boolean tryOnNext(T t2) {
            if (this.done) {
                return false;
            }
            AtomicReferenceArray<Object> atomicReferenceArray = this.values;
            int length = atomicReferenceArray.length();
            Object[] objArr = new Object[length + 1];
            objArr[0] = t2;
            int i2 = 0;
            while (i2 < length) {
                Object obj = atomicReferenceArray.get(i2);
                if (obj == null) {
                    return false;
                }
                i2++;
                objArr[i2] = obj;
            }
            try {
                R rApply = this.combiner.apply(objArr);
                Objects.requireNonNull(rApply, "The combiner returned a null value");
                HalfSerializer.onNext(this.downstream, rApply, this, this.error);
                return true;
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                cancel();
                onError(th);
                return false;
            }
        }
    }

    public static final class WithLatestInnerSubscriber extends AtomicReference<Subscription> implements FlowableSubscriber<Object> {
        private static final long serialVersionUID = 3256684027868224024L;
        boolean hasValue;
        final int index;
        final WithLatestFromSubscriber<?, ?> parent;

        public WithLatestInnerSubscriber(WithLatestFromSubscriber<?, ?> parent, int index) {
            this.parent = parent;
            this.index = index;
        }

        public void dispose() {
            SubscriptionHelper.cancel(this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.parent.innerComplete(this.index, this.hasValue);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            this.parent.innerError(this.index, t2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object t2) {
            if (!this.hasValue) {
                this.hasValue = true;
            }
            this.parent.innerNext(this.index, t2);
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            SubscriptionHelper.setOnce(this, s2, Long.MAX_VALUE);
        }
    }

    public FlowableWithLatestFromMany(@NonNull Flowable<T> source, @NonNull Publisher<?>[] otherArray, Function<? super Object[], R> combiner) {
        super(source);
        this.otherArray = otherArray;
        this.otherIterable = null;
        this.combiner = combiner;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super R> s2) {
        int length;
        Publisher<?>[] publisherArr = this.otherArray;
        if (publisherArr == null) {
            publisherArr = new Publisher[8];
            try {
                length = 0;
                for (Publisher<?> publisher : this.otherIterable) {
                    if (length == publisherArr.length) {
                        publisherArr = (Publisher[]) Arrays.copyOf(publisherArr, (length >> 1) + length);
                    }
                    int i2 = length + 1;
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
        if (length == 0) {
            new FlowableMap(this.source, new SingletonArrayFunc()).subscribeActual(s2);
            return;
        }
        WithLatestFromSubscriber withLatestFromSubscriber = new WithLatestFromSubscriber(s2, this.combiner, length);
        s2.onSubscribe(withLatestFromSubscriber);
        withLatestFromSubscriber.subscribe(publisherArr, length);
        this.source.subscribe((FlowableSubscriber) withLatestFromSubscriber);
    }

    public FlowableWithLatestFromMany(@NonNull Flowable<T> source, @NonNull Iterable<? extends Publisher<?>> otherIterable, @NonNull Function<? super Object[], R> combiner) {
        super(source);
        this.otherArray = null;
        this.otherIterable = otherIterable;
        this.combiner = combiner;
    }
}
