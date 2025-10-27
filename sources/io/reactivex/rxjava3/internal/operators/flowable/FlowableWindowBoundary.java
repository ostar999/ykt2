package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.internal.queue.MpscLinkedQueue;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.processors.UnicastProcessor;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableWindowBoundary<T, B> extends AbstractFlowableWithUpstream<T, Flowable<T>> {
    final int capacityHint;
    final Publisher<B> other;

    public static final class WindowBoundaryInnerSubscriber<T, B> extends DisposableSubscriber<B> {
        boolean done;
        final WindowBoundaryMainSubscriber<T, B> parent;

        public WindowBoundaryInnerSubscriber(WindowBoundaryMainSubscriber<T, B> parent) {
            this.parent = parent;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.parent.innerComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            if (this.done) {
                RxJavaPlugins.onError(t2);
            } else {
                this.done = true;
                this.parent.innerError(t2);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(B t2) {
            if (this.done) {
                return;
            }
            this.parent.innerNext();
        }
    }

    public static final class WindowBoundaryMainSubscriber<T, B> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, Runnable {
        static final Object NEXT_WINDOW = new Object();
        private static final long serialVersionUID = 2233020065421370272L;
        final int capacityHint;
        volatile boolean done;
        final Subscriber<? super Flowable<T>> downstream;
        long emitted;
        UnicastProcessor<T> window;
        final WindowBoundaryInnerSubscriber<T, B> boundarySubscriber = new WindowBoundaryInnerSubscriber<>(this);
        final AtomicReference<Subscription> upstream = new AtomicReference<>();
        final AtomicInteger windows = new AtomicInteger(1);
        final MpscLinkedQueue<Object> queue = new MpscLinkedQueue<>();
        final AtomicThrowable errors = new AtomicThrowable();
        final AtomicBoolean stopWindows = new AtomicBoolean();
        final AtomicLong requested = new AtomicLong();

        public WindowBoundaryMainSubscriber(Subscriber<? super Flowable<T>> downstream, int capacityHint) {
            this.downstream = downstream;
            this.capacityHint = capacityHint;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.stopWindows.compareAndSet(false, true)) {
                this.boundarySubscriber.dispose();
                if (this.windows.decrementAndGet() == 0) {
                    SubscriptionHelper.cancel(this.upstream);
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            Subscriber<? super Flowable<T>> subscriber = this.downstream;
            MpscLinkedQueue<Object> mpscLinkedQueue = this.queue;
            AtomicThrowable atomicThrowable = this.errors;
            long j2 = this.emitted;
            int iAddAndGet = 1;
            while (this.windows.get() != 0) {
                UnicastProcessor<T> unicastProcessor = this.window;
                boolean z2 = this.done;
                if (z2 && atomicThrowable.get() != null) {
                    mpscLinkedQueue.clear();
                    Throwable thTerminate = atomicThrowable.terminate();
                    if (unicastProcessor != 0) {
                        this.window = null;
                        unicastProcessor.onError(thTerminate);
                    }
                    subscriber.onError(thTerminate);
                    return;
                }
                Object objPoll = mpscLinkedQueue.poll();
                boolean z3 = objPoll == null;
                if (z2 && z3) {
                    Throwable thTerminate2 = atomicThrowable.terminate();
                    if (thTerminate2 == null) {
                        if (unicastProcessor != 0) {
                            this.window = null;
                            unicastProcessor.onComplete();
                        }
                        subscriber.onComplete();
                        return;
                    }
                    if (unicastProcessor != 0) {
                        this.window = null;
                        unicastProcessor.onError(thTerminate2);
                    }
                    subscriber.onError(thTerminate2);
                    return;
                }
                if (z3) {
                    this.emitted = j2;
                    iAddAndGet = addAndGet(-iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else if (objPoll != NEXT_WINDOW) {
                    unicastProcessor.onNext(objPoll);
                } else {
                    if (unicastProcessor != 0) {
                        this.window = null;
                        unicastProcessor.onComplete();
                    }
                    if (!this.stopWindows.get()) {
                        UnicastProcessor<T> unicastProcessorCreate = UnicastProcessor.create(this.capacityHint, this);
                        this.window = unicastProcessorCreate;
                        this.windows.getAndIncrement();
                        if (j2 != this.requested.get()) {
                            j2++;
                            FlowableWindowSubscribeIntercept flowableWindowSubscribeIntercept = new FlowableWindowSubscribeIntercept(unicastProcessorCreate);
                            subscriber.onNext(flowableWindowSubscribeIntercept);
                            if (flowableWindowSubscribeIntercept.tryAbandon()) {
                                unicastProcessorCreate.onComplete();
                            }
                        } else {
                            SubscriptionHelper.cancel(this.upstream);
                            this.boundarySubscriber.dispose();
                            atomicThrowable.tryAddThrowableOrReport(new MissingBackpressureException("Could not deliver a window due to lack of requests"));
                            this.done = true;
                        }
                    }
                }
            }
            mpscLinkedQueue.clear();
            this.window = null;
        }

        public void innerComplete() {
            SubscriptionHelper.cancel(this.upstream);
            this.done = true;
            drain();
        }

        public void innerError(Throwable e2) {
            SubscriptionHelper.cancel(this.upstream);
            if (this.errors.tryAddThrowableOrReport(e2)) {
                this.done = true;
                drain();
            }
        }

        public void innerNext() {
            this.queue.offer(NEXT_WINDOW);
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.boundarySubscriber.dispose();
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable e2) {
            this.boundarySubscriber.dispose();
            if (this.errors.tryAddThrowableOrReport(e2)) {
                this.done = true;
                drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            this.queue.offer(t2);
            drain();
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            SubscriptionHelper.setOnce(this.upstream, s2, Long.MAX_VALUE);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            BackpressureHelper.add(this.requested, n2);
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.windows.decrementAndGet() == 0) {
                SubscriptionHelper.cancel(this.upstream);
            }
        }
    }

    public FlowableWindowBoundary(Flowable<T> source, Publisher<B> other, int capacityHint) {
        super(source);
        this.other = other;
        this.capacityHint = capacityHint;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super Flowable<T>> subscriber) {
        WindowBoundaryMainSubscriber windowBoundaryMainSubscriber = new WindowBoundaryMainSubscriber(subscriber, this.capacityHint);
        subscriber.onSubscribe(windowBoundaryMainSubscriber);
        windowBoundaryMainSubscriber.innerNext();
        this.other.subscribe(windowBoundaryMainSubscriber.boundarySubscriber);
        this.source.subscribe((FlowableSubscriber) windowBoundaryMainSubscriber);
    }
}
