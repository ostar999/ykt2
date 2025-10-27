package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.operators.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableBufferBoundary<T, U extends Collection<? super T>, Open, Close> extends AbstractFlowableWithUpstream<T, U> {
    final Function<? super Open, ? extends Publisher<? extends Close>> bufferClose;
    final Publisher<? extends Open> bufferOpen;
    final Supplier<U> bufferSupplier;

    public static final class BufferBoundarySubscriber<T, C extends Collection<? super T>, Open, Close> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -8466418554264089604L;
        final Function<? super Open, ? extends Publisher<? extends Close>> bufferClose;
        final Publisher<? extends Open> bufferOpen;
        final Supplier<C> bufferSupplier;
        volatile boolean cancelled;
        volatile boolean done;
        final Subscriber<? super C> downstream;
        long emitted;
        long index;
        final SpscLinkedArrayQueue<C> queue = new SpscLinkedArrayQueue<>(Flowable.bufferSize());
        final CompositeDisposable subscribers = new CompositeDisposable();
        final AtomicLong requested = new AtomicLong();
        final AtomicReference<Subscription> upstream = new AtomicReference<>();
        Map<Long, C> buffers = new LinkedHashMap();
        final AtomicThrowable errors = new AtomicThrowable();

        public static final class BufferOpenSubscriber<Open> extends AtomicReference<Subscription> implements FlowableSubscriber<Open>, Disposable {
            private static final long serialVersionUID = -8498650778633225126L;
            final BufferBoundarySubscriber<?, ?, Open, ?> parent;

            public BufferOpenSubscriber(BufferBoundarySubscriber<?, ?, Open, ?> parent) {
                this.parent = parent;
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public void dispose() {
                SubscriptionHelper.cancel(this);
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public boolean isDisposed() {
                return get() == SubscriptionHelper.CANCELLED;
            }

            @Override // org.reactivestreams.Subscriber
            public void onComplete() {
                lazySet(SubscriptionHelper.CANCELLED);
                this.parent.openComplete(this);
            }

            @Override // org.reactivestreams.Subscriber
            public void onError(Throwable t2) {
                lazySet(SubscriptionHelper.CANCELLED);
                this.parent.boundaryError(this, t2);
            }

            @Override // org.reactivestreams.Subscriber
            public void onNext(Open t2) {
                this.parent.open(t2);
            }

            @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
            public void onSubscribe(Subscription s2) {
                SubscriptionHelper.setOnce(this, s2, Long.MAX_VALUE);
            }
        }

        public BufferBoundarySubscriber(Subscriber<? super C> actual, Publisher<? extends Open> bufferOpen, Function<? super Open, ? extends Publisher<? extends Close>> bufferClose, Supplier<C> bufferSupplier) {
            this.downstream = actual;
            this.bufferSupplier = bufferSupplier;
            this.bufferOpen = bufferOpen;
            this.bufferClose = bufferClose;
        }

        public void boundaryError(Disposable subscriber, Throwable ex) {
            SubscriptionHelper.cancel(this.upstream);
            this.subscribers.delete(subscriber);
            onError(ex);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (SubscriptionHelper.cancel(this.upstream)) {
                this.cancelled = true;
                this.subscribers.dispose();
                synchronized (this) {
                    this.buffers = null;
                }
                if (getAndIncrement() != 0) {
                    this.queue.clear();
                }
            }
        }

        public void close(BufferCloseSubscriber<T, C> closer, long idx) {
            boolean z2;
            this.subscribers.delete(closer);
            if (this.subscribers.size() == 0) {
                SubscriptionHelper.cancel(this.upstream);
                z2 = true;
            } else {
                z2 = false;
            }
            synchronized (this) {
                Map<Long, C> map = this.buffers;
                if (map == null) {
                    return;
                }
                this.queue.offer(map.remove(Long.valueOf(idx)));
                if (z2) {
                    this.done = true;
                }
                drain();
            }
        }

        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            long j2 = this.emitted;
            Subscriber<? super C> subscriber = this.downstream;
            SpscLinkedArrayQueue<C> spscLinkedArrayQueue = this.queue;
            int iAddAndGet = 1;
            do {
                long j3 = this.requested.get();
                while (j2 != j3) {
                    if (this.cancelled) {
                        spscLinkedArrayQueue.clear();
                        return;
                    }
                    boolean z2 = this.done;
                    if (z2 && this.errors.get() != null) {
                        spscLinkedArrayQueue.clear();
                        this.errors.tryTerminateConsumer(subscriber);
                        return;
                    }
                    C cPoll = spscLinkedArrayQueue.poll();
                    boolean z3 = cPoll == null;
                    if (z2 && z3) {
                        subscriber.onComplete();
                        return;
                    } else {
                        if (z3) {
                            break;
                        }
                        subscriber.onNext(cPoll);
                        j2++;
                    }
                }
                if (j2 == j3) {
                    if (this.cancelled) {
                        spscLinkedArrayQueue.clear();
                        return;
                    }
                    if (this.done) {
                        if (this.errors.get() != null) {
                            spscLinkedArrayQueue.clear();
                            this.errors.tryTerminateConsumer(subscriber);
                            return;
                        } else if (spscLinkedArrayQueue.isEmpty()) {
                            subscriber.onComplete();
                            return;
                        }
                    }
                }
                this.emitted = j2;
                iAddAndGet = addAndGet(-iAddAndGet);
            } while (iAddAndGet != 0);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.subscribers.dispose();
            synchronized (this) {
                Map<Long, C> map = this.buffers;
                if (map == null) {
                    return;
                }
                Iterator<C> it = map.values().iterator();
                while (it.hasNext()) {
                    this.queue.offer(it.next());
                }
                this.buffers = null;
                this.done = true;
                drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            if (this.errors.tryAddThrowableOrReport(t2)) {
                this.subscribers.dispose();
                synchronized (this) {
                    this.buffers = null;
                }
                this.done = true;
                drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            synchronized (this) {
                Map<Long, C> map = this.buffers;
                if (map == null) {
                    return;
                }
                Iterator<C> it = map.values().iterator();
                while (it.hasNext()) {
                    it.next().add(t2);
                }
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.setOnce(this.upstream, s2)) {
                BufferOpenSubscriber bufferOpenSubscriber = new BufferOpenSubscriber(this);
                this.subscribers.add(bufferOpenSubscriber);
                this.bufferOpen.subscribe(bufferOpenSubscriber);
                s2.request(Long.MAX_VALUE);
            }
        }

        public void open(Open open) {
            try {
                C c3 = this.bufferSupplier.get();
                Objects.requireNonNull(c3, "The bufferSupplier returned a null Collection");
                C c4 = c3;
                Publisher<? extends Close> publisherApply = this.bufferClose.apply(open);
                Objects.requireNonNull(publisherApply, "The bufferClose returned a null Publisher");
                Publisher<? extends Close> publisher = publisherApply;
                long j2 = this.index;
                this.index = 1 + j2;
                synchronized (this) {
                    Map<Long, C> map = this.buffers;
                    if (map == null) {
                        return;
                    }
                    map.put(Long.valueOf(j2), c4);
                    BufferCloseSubscriber bufferCloseSubscriber = new BufferCloseSubscriber(this, j2);
                    this.subscribers.add(bufferCloseSubscriber);
                    publisher.subscribe(bufferCloseSubscriber);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                SubscriptionHelper.cancel(this.upstream);
                onError(th);
            }
        }

        public void openComplete(BufferOpenSubscriber<Open> os) {
            this.subscribers.delete(os);
            if (this.subscribers.size() == 0) {
                SubscriptionHelper.cancel(this.upstream);
                this.done = true;
                drain();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            BackpressureHelper.add(this.requested, n2);
            drain();
        }
    }

    public static final class BufferCloseSubscriber<T, C extends Collection<? super T>> extends AtomicReference<Subscription> implements FlowableSubscriber<Object>, Disposable {
        private static final long serialVersionUID = -8498650778633225126L;
        final long index;
        final BufferBoundarySubscriber<T, C, ?, ?> parent;

        public BufferCloseSubscriber(BufferBoundarySubscriber<T, C, ?, ?> parent, long index) {
            this.parent = parent;
            this.index = index;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            SubscriptionHelper.cancel(this);
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return get() == SubscriptionHelper.CANCELLED;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            Subscription subscription = get();
            SubscriptionHelper subscriptionHelper = SubscriptionHelper.CANCELLED;
            if (subscription != subscriptionHelper) {
                lazySet(subscriptionHelper);
                this.parent.close(this, this.index);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            Subscription subscription = get();
            SubscriptionHelper subscriptionHelper = SubscriptionHelper.CANCELLED;
            if (subscription == subscriptionHelper) {
                RxJavaPlugins.onError(t2);
            } else {
                lazySet(subscriptionHelper);
                this.parent.boundaryError(this, t2);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object t2) {
            Subscription subscription = get();
            SubscriptionHelper subscriptionHelper = SubscriptionHelper.CANCELLED;
            if (subscription != subscriptionHelper) {
                lazySet(subscriptionHelper);
                subscription.cancel();
                this.parent.close(this, this.index);
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            SubscriptionHelper.setOnce(this, s2, Long.MAX_VALUE);
        }
    }

    public FlowableBufferBoundary(Flowable<T> source, Publisher<? extends Open> bufferOpen, Function<? super Open, ? extends Publisher<? extends Close>> bufferClose, Supplier<U> bufferSupplier) {
        super(source);
        this.bufferOpen = bufferOpen;
        this.bufferClose = bufferClose;
        this.bufferSupplier = bufferSupplier;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super U> s2) {
        BufferBoundarySubscriber bufferBoundarySubscriber = new BufferBoundarySubscriber(s2, this.bufferOpen, this.bufferClose, this.bufferSupplier);
        s2.onSubscribe(bufferBoundarySubscriber);
        this.source.subscribe((FlowableSubscriber) bufferBoundarySubscriber);
    }
}
