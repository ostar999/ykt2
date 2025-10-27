package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.QueueDrainHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableBuffer<T, C extends Collection<? super T>> extends AbstractFlowableWithUpstream<T, C> {
    final Callable<C> bufferSupplier;
    final int size;
    final int skip;

    public static final class PublisherBufferExactSubscriber<T, C extends Collection<? super T>> implements FlowableSubscriber<T>, Subscription {
        C buffer;
        final Callable<C> bufferSupplier;
        boolean done;
        final Subscriber<? super C> downstream;
        int index;
        final int size;
        Subscription upstream;

        public PublisherBufferExactSubscriber(Subscriber<? super C> subscriber, int i2, Callable<C> callable) {
            this.downstream = subscriber;
            this.size = i2;
            this.bufferSupplier = callable;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            C c3 = this.buffer;
            if (c3 != null && !c3.isEmpty()) {
                this.downstream.onNext(c3);
            }
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
            } else {
                this.done = true;
                this.downstream.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            C c3 = this.buffer;
            if (c3 == null) {
                try {
                    c3 = (C) ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The bufferSupplier returned a null buffer");
                    this.buffer = c3;
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    cancel();
                    onError(th);
                    return;
                }
            }
            c3.add(t2);
            int i2 = this.index + 1;
            if (i2 != this.size) {
                this.index = i2;
                return;
            }
            this.index = 0;
            this.buffer = null;
            this.downstream.onNext(c3);
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                this.upstream.request(BackpressureHelper.multiplyCap(j2, this.size));
            }
        }
    }

    public static final class PublisherBufferOverlappingSubscriber<T, C extends Collection<? super T>> extends AtomicLong implements FlowableSubscriber<T>, Subscription, BooleanSupplier {
        private static final long serialVersionUID = -7370244972039324525L;
        final Callable<C> bufferSupplier;
        volatile boolean cancelled;
        boolean done;
        final Subscriber<? super C> downstream;
        int index;
        long produced;
        final int size;
        final int skip;
        Subscription upstream;
        final AtomicBoolean once = new AtomicBoolean();
        final ArrayDeque<C> buffers = new ArrayDeque<>();

        public PublisherBufferOverlappingSubscriber(Subscriber<? super C> subscriber, int i2, int i3, Callable<C> callable) {
            this.downstream = subscriber;
            this.size = i2;
            this.skip = i3;
            this.bufferSupplier = callable;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            this.upstream.cancel();
        }

        @Override // io.reactivex.functions.BooleanSupplier
        public boolean getAsBoolean() {
            return this.cancelled;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            long j2 = this.produced;
            if (j2 != 0) {
                BackpressureHelper.produced(this, j2);
            }
            QueueDrainHelper.postComplete(this.downstream, this.buffers, this, this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.buffers.clear();
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            ArrayDeque<C> arrayDeque = this.buffers;
            int i2 = this.index;
            int i3 = i2 + 1;
            if (i2 == 0) {
                try {
                    arrayDeque.offer((Collection) ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The bufferSupplier returned a null buffer"));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    cancel();
                    onError(th);
                    return;
                }
            }
            Collection collection = (Collection) arrayDeque.peek();
            if (collection != null && collection.size() + 1 == this.size) {
                arrayDeque.poll();
                collection.add(t2);
                this.produced++;
                this.downstream.onNext(collection);
            }
            Iterator it = arrayDeque.iterator();
            while (it.hasNext()) {
                ((Collection) it.next()).add(t2);
            }
            if (i3 == this.skip) {
                i3 = 0;
            }
            this.index = i3;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j2) {
            if (!SubscriptionHelper.validate(j2) || QueueDrainHelper.postCompleteRequest(j2, this.downstream, this.buffers, this, this)) {
                return;
            }
            if (this.once.get() || !this.once.compareAndSet(false, true)) {
                this.upstream.request(BackpressureHelper.multiplyCap(this.skip, j2));
            } else {
                this.upstream.request(BackpressureHelper.addCap(this.size, BackpressureHelper.multiplyCap(this.skip, j2 - 1)));
            }
        }
    }

    public static final class PublisherBufferSkipSubscriber<T, C extends Collection<? super T>> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -5616169793639412593L;
        C buffer;
        final Callable<C> bufferSupplier;
        boolean done;
        final Subscriber<? super C> downstream;
        int index;
        final int size;
        final int skip;
        Subscription upstream;

        public PublisherBufferSkipSubscriber(Subscriber<? super C> subscriber, int i2, int i3, Callable<C> callable) {
            this.downstream = subscriber;
            this.size = i2;
            this.skip = i3;
            this.bufferSupplier = callable;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.upstream.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            C c3 = this.buffer;
            this.buffer = null;
            if (c3 != null) {
                this.downstream.onNext(c3);
            }
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.buffer = null;
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            C c3 = this.buffer;
            int i2 = this.index;
            int i3 = i2 + 1;
            if (i2 == 0) {
                try {
                    c3 = (C) ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The bufferSupplier returned a null buffer");
                    this.buffer = c3;
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    cancel();
                    onError(th);
                    return;
                }
            }
            if (c3 != null) {
                c3.add(t2);
                if (c3.size() == this.size) {
                    this.buffer = null;
                    this.downstream.onNext(c3);
                }
            }
            if (i3 == this.skip) {
                i3 = 0;
            }
            this.index = i3;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j2) {
            if (SubscriptionHelper.validate(j2)) {
                if (get() != 0 || !compareAndSet(0, 1)) {
                    this.upstream.request(BackpressureHelper.multiplyCap(this.skip, j2));
                    return;
                }
                this.upstream.request(BackpressureHelper.addCap(BackpressureHelper.multiplyCap(j2, this.size), BackpressureHelper.multiplyCap(this.skip - this.size, j2 - 1)));
            }
        }
    }

    public FlowableBuffer(Flowable<T> flowable, int i2, int i3, Callable<C> callable) {
        super(flowable);
        this.size = i2;
        this.skip = i3;
        this.bufferSupplier = callable;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super C> subscriber) {
        int i2 = this.size;
        int i3 = this.skip;
        if (i2 == i3) {
            this.source.subscribe((FlowableSubscriber) new PublisherBufferExactSubscriber(subscriber, i2, this.bufferSupplier));
        } else if (i3 > i2) {
            this.source.subscribe((FlowableSubscriber) new PublisherBufferSkipSubscriber(subscriber, this.size, this.skip, this.bufferSupplier));
        } else {
            this.source.subscribe((FlowableSubscriber) new PublisherBufferOverlappingSubscriber(subscriber, this.size, this.skip, this.bufferSupplier));
        }
    }
}
