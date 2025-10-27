package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.BooleanSupplier;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.QueueDrainHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableBuffer<T, C extends Collection<? super T>> extends AbstractFlowableWithUpstream<T, C> {
    final Supplier<C> bufferSupplier;
    final int size;
    final int skip;

    public static final class PublisherBufferExactSubscriber<T, C extends Collection<? super T>> implements FlowableSubscriber<T>, Subscription {
        C buffer;
        final Supplier<C> bufferSupplier;
        boolean done;
        final Subscriber<? super C> downstream;
        int index;
        final int size;
        Subscription upstream;

        public PublisherBufferExactSubscriber(Subscriber<? super C> actual, int size, Supplier<C> bufferSupplier) {
            this.downstream = actual;
            this.size = size;
            this.bufferSupplier = bufferSupplier;
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
        public void onError(Throwable t2) {
            if (this.done) {
                RxJavaPlugins.onError(t2);
                return;
            }
            this.buffer = null;
            this.done = true;
            this.downstream.onError(t2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            C c3 = this.buffer;
            if (c3 == null) {
                try {
                    C c4 = this.bufferSupplier.get();
                    Objects.requireNonNull(c4, "The bufferSupplier returned a null buffer");
                    c3 = c4;
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

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            if (SubscriptionHelper.validate(n2)) {
                this.upstream.request(BackpressureHelper.multiplyCap(n2, this.size));
            }
        }
    }

    public static final class PublisherBufferOverlappingSubscriber<T, C extends Collection<? super T>> extends AtomicLong implements FlowableSubscriber<T>, Subscription, BooleanSupplier {
        private static final long serialVersionUID = -7370244972039324525L;
        final Supplier<C> bufferSupplier;
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

        public PublisherBufferOverlappingSubscriber(Subscriber<? super C> actual, int size, int skip, Supplier<C> bufferSupplier) {
            this.downstream = actual;
            this.size = size;
            this.skip = skip;
            this.bufferSupplier = bufferSupplier;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            this.upstream.cancel();
        }

        @Override // io.reactivex.rxjava3.functions.BooleanSupplier
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
        public void onError(Throwable t2) {
            if (this.done) {
                RxJavaPlugins.onError(t2);
                return;
            }
            this.done = true;
            this.buffers.clear();
            this.downstream.onError(t2);
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
                    C c3 = this.bufferSupplier.get();
                    Objects.requireNonNull(c3, "The bufferSupplier returned a null buffer");
                    arrayDeque.offer(c3);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    cancel();
                    onError(th);
                    return;
                }
            }
            Collection collection = (Collection) arrayDeque.peek();
            if (collection.size() + 1 == this.size) {
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

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            if (!SubscriptionHelper.validate(n2) || QueueDrainHelper.postCompleteRequest(n2, this.downstream, this.buffers, this, this)) {
                return;
            }
            if (this.once.get() || !this.once.compareAndSet(false, true)) {
                this.upstream.request(BackpressureHelper.multiplyCap(this.skip, n2));
            } else {
                this.upstream.request(BackpressureHelper.addCap(this.size, BackpressureHelper.multiplyCap(this.skip, n2 - 1)));
            }
        }
    }

    public static final class PublisherBufferSkipSubscriber<T, C extends Collection<? super T>> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -5616169793639412593L;
        C buffer;
        final Supplier<C> bufferSupplier;
        boolean done;
        final Subscriber<? super C> downstream;
        int index;
        final int size;
        final int skip;
        Subscription upstream;

        public PublisherBufferSkipSubscriber(Subscriber<? super C> actual, int size, int skip, Supplier<C> bufferSupplier) {
            this.downstream = actual;
            this.size = size;
            this.skip = skip;
            this.bufferSupplier = bufferSupplier;
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
        public void onError(Throwable t2) {
            if (this.done) {
                RxJavaPlugins.onError(t2);
                return;
            }
            this.done = true;
            this.buffer = null;
            this.downstream.onError(t2);
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
                    C c4 = this.bufferSupplier.get();
                    Objects.requireNonNull(c4, "The bufferSupplier returned a null buffer");
                    c3 = c4;
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

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                this.downstream.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            if (SubscriptionHelper.validate(n2)) {
                if (get() != 0 || !compareAndSet(0, 1)) {
                    this.upstream.request(BackpressureHelper.multiplyCap(this.skip, n2));
                    return;
                }
                this.upstream.request(BackpressureHelper.addCap(BackpressureHelper.multiplyCap(n2, this.size), BackpressureHelper.multiplyCap(this.skip - this.size, n2 - 1)));
            }
        }
    }

    public FlowableBuffer(Flowable<T> source, int size, int skip, Supplier<C> bufferSupplier) {
        super(source);
        this.size = size;
        this.skip = skip;
        this.bufferSupplier = bufferSupplier;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super C> s2) {
        int i2 = this.size;
        int i3 = this.skip;
        if (i2 == i3) {
            this.source.subscribe((FlowableSubscriber) new PublisherBufferExactSubscriber(s2, i2, this.bufferSupplier));
        } else if (i3 > i2) {
            this.source.subscribe((FlowableSubscriber) new PublisherBufferSkipSubscriber(s2, this.size, this.skip, this.bufferSupplier));
        } else {
            this.source.subscribe((FlowableSubscriber) new PublisherBufferOverlappingSubscriber(s2, this.size, this.skip, this.bufferSupplier));
        }
    }
}
