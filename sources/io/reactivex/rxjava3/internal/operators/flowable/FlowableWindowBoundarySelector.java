package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.queue.MpscLinkedQueue;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.operators.SimplePlainQueue;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.processors.UnicastProcessor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableWindowBoundarySelector<T, B, V> extends AbstractFlowableWithUpstream<T, Flowable<T>> {
    final int bufferSize;
    final Function<? super B, ? extends Publisher<V>> closingIndicator;
    final Publisher<B> open;

    public static final class WindowBoundaryMainSubscriber<T, B, V> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, Runnable {
        private static final long serialVersionUID = 8646217640096099753L;
        final int bufferSize;
        final Function<? super B, ? extends Publisher<V>> closingIndicator;
        final Subscriber<? super Flowable<T>> downstream;
        long emitted;
        final Publisher<B> open;
        volatile boolean openDone;
        Subscription upstream;
        volatile boolean upstreamCanceled;
        volatile boolean upstreamDone;
        final SimplePlainQueue<Object> queue = new MpscLinkedQueue();
        final CompositeDisposable resources = new CompositeDisposable();
        final List<UnicastProcessor<T>> windows = new ArrayList();
        final AtomicLong windowCount = new AtomicLong(1);
        final AtomicBoolean downstreamCancelled = new AtomicBoolean();
        final AtomicThrowable error = new AtomicThrowable();
        final WindowStartSubscriber<B> startSubscriber = new WindowStartSubscriber<>(this);
        final AtomicLong requested = new AtomicLong();

        public static final class WindowEndSubscriberIntercept<T, V> extends Flowable<T> implements FlowableSubscriber<V>, Disposable {
            final WindowBoundaryMainSubscriber<T, ?, V> parent;
            final UnicastProcessor<T> window;
            final AtomicReference<Subscription> upstream = new AtomicReference<>();
            final AtomicBoolean once = new AtomicBoolean();

            public WindowEndSubscriberIntercept(WindowBoundaryMainSubscriber<T, ?, V> parent, UnicastProcessor<T> window) {
                this.parent = parent;
                this.window = window;
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public void dispose() {
                SubscriptionHelper.cancel(this.upstream);
            }

            @Override // io.reactivex.rxjava3.disposables.Disposable
            public boolean isDisposed() {
                return this.upstream.get() == SubscriptionHelper.CANCELLED;
            }

            @Override // org.reactivestreams.Subscriber
            public void onComplete() {
                this.parent.close(this);
            }

            @Override // org.reactivestreams.Subscriber
            public void onError(Throwable t2) {
                if (isDisposed()) {
                    RxJavaPlugins.onError(t2);
                } else {
                    this.parent.closeError(t2);
                }
            }

            @Override // org.reactivestreams.Subscriber
            public void onNext(V t2) {
                if (SubscriptionHelper.cancel(this.upstream)) {
                    this.parent.close(this);
                }
            }

            @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
            public void onSubscribe(Subscription s2) {
                if (SubscriptionHelper.setOnce(this.upstream, s2)) {
                    s2.request(Long.MAX_VALUE);
                }
            }

            @Override // io.reactivex.rxjava3.core.Flowable
            public void subscribeActual(Subscriber<? super T> s2) {
                this.window.subscribe(s2);
                this.once.set(true);
            }

            public boolean tryAbandon() {
                return !this.once.get() && this.once.compareAndSet(false, true);
            }
        }

        public static final class WindowStartItem<B> {
            final B item;

            public WindowStartItem(B item) {
                this.item = item;
            }
        }

        public static final class WindowStartSubscriber<B> extends AtomicReference<Subscription> implements FlowableSubscriber<B> {
            private static final long serialVersionUID = -3326496781427702834L;
            final WindowBoundaryMainSubscriber<?, B, ?> parent;

            public WindowStartSubscriber(WindowBoundaryMainSubscriber<?, B, ?> parent) {
                this.parent = parent;
            }

            public void cancel() {
                SubscriptionHelper.cancel(this);
            }

            @Override // org.reactivestreams.Subscriber
            public void onComplete() {
                this.parent.openComplete();
            }

            @Override // org.reactivestreams.Subscriber
            public void onError(Throwable t2) {
                this.parent.openError(t2);
            }

            @Override // org.reactivestreams.Subscriber
            public void onNext(B t2) {
                this.parent.open(t2);
            }

            @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
            public void onSubscribe(Subscription s2) {
                if (SubscriptionHelper.setOnce(this, s2)) {
                    s2.request(Long.MAX_VALUE);
                }
            }
        }

        public WindowBoundaryMainSubscriber(Subscriber<? super Flowable<T>> actual, Publisher<B> open, Function<? super B, ? extends Publisher<V>> closingIndicator, int bufferSize) {
            this.downstream = actual;
            this.open = open;
            this.closingIndicator = closingIndicator;
            this.bufferSize = bufferSize;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.downstreamCancelled.compareAndSet(false, true)) {
                if (this.windowCount.decrementAndGet() != 0) {
                    this.startSubscriber.cancel();
                    return;
                }
                this.upstream.cancel();
                this.startSubscriber.cancel();
                this.resources.dispose();
                this.error.tryTerminateAndReport();
                this.upstreamCanceled = true;
                drain();
            }
        }

        public void close(WindowEndSubscriberIntercept<T, V> what) {
            this.queue.offer(what);
            drain();
        }

        public void closeError(Throwable t2) {
            this.upstream.cancel();
            this.startSubscriber.cancel();
            this.resources.dispose();
            if (this.error.tryAddThrowableOrReport(t2)) {
                this.upstreamDone = true;
                drain();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            Subscriber<? super Flowable<T>> subscriber = this.downstream;
            SimplePlainQueue<Object> simplePlainQueue = this.queue;
            List<UnicastProcessor<T>> list = this.windows;
            int iAddAndGet = 1;
            while (true) {
                if (this.upstreamCanceled) {
                    simplePlainQueue.clear();
                    list.clear();
                } else {
                    boolean z2 = this.upstreamDone;
                    Object objPoll = simplePlainQueue.poll();
                    boolean z3 = objPoll == null;
                    if (z2 && (z3 || this.error.get() != null)) {
                        terminateDownstream(subscriber);
                        this.upstreamCanceled = true;
                    } else if (z3) {
                        if (this.openDone && list.size() == 0) {
                            this.upstream.cancel();
                            this.startSubscriber.cancel();
                            this.resources.dispose();
                            terminateDownstream(subscriber);
                            this.upstreamCanceled = true;
                        }
                    } else if (objPoll instanceof WindowStartItem) {
                        if (!this.downstreamCancelled.get()) {
                            long j2 = this.emitted;
                            if (this.requested.get() != j2) {
                                this.emitted = j2 + 1;
                                try {
                                    Publisher<V> publisherApply = this.closingIndicator.apply(((WindowStartItem) objPoll).item);
                                    Objects.requireNonNull(publisherApply, "The closingIndicator returned a null Publisher");
                                    Publisher<V> publisher = publisherApply;
                                    this.windowCount.getAndIncrement();
                                    UnicastProcessor<T> unicastProcessorCreate = UnicastProcessor.create(this.bufferSize, this);
                                    WindowEndSubscriberIntercept windowEndSubscriberIntercept = new WindowEndSubscriberIntercept(this, unicastProcessorCreate);
                                    subscriber.onNext(windowEndSubscriberIntercept);
                                    if (windowEndSubscriberIntercept.tryAbandon()) {
                                        unicastProcessorCreate.onComplete();
                                    } else {
                                        list.add(unicastProcessorCreate);
                                        this.resources.add(windowEndSubscriberIntercept);
                                        publisher.subscribe(windowEndSubscriberIntercept);
                                    }
                                } catch (Throwable th) {
                                    Exceptions.throwIfFatal(th);
                                    this.upstream.cancel();
                                    this.startSubscriber.cancel();
                                    this.resources.dispose();
                                    Exceptions.throwIfFatal(th);
                                    this.error.tryAddThrowableOrReport(th);
                                    this.upstreamDone = true;
                                }
                            } else {
                                this.upstream.cancel();
                                this.startSubscriber.cancel();
                                this.resources.dispose();
                                this.error.tryAddThrowableOrReport(new MissingBackpressureException(FlowableWindowTimed.missingBackpressureMessage(j2)));
                                this.upstreamDone = true;
                            }
                        }
                    } else if (objPoll instanceof WindowEndSubscriberIntercept) {
                        UnicastProcessor<T> unicastProcessor = ((WindowEndSubscriberIntercept) objPoll).window;
                        list.remove(unicastProcessor);
                        this.resources.delete((Disposable) objPoll);
                        unicastProcessor.onComplete();
                    } else {
                        Iterator<UnicastProcessor<T>> it = list.iterator();
                        while (it.hasNext()) {
                            it.next().onNext(objPoll);
                        }
                    }
                }
                iAddAndGet = addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.startSubscriber.cancel();
            this.resources.dispose();
            this.upstreamDone = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            this.startSubscriber.cancel();
            this.resources.dispose();
            if (this.error.tryAddThrowableOrReport(t2)) {
                this.upstreamDone = true;
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
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                this.downstream.onSubscribe(this);
                this.open.subscribe(this.startSubscriber);
                s2.request(Long.MAX_VALUE);
            }
        }

        public void open(B startValue) {
            this.queue.offer(new WindowStartItem(startValue));
            drain();
        }

        public void openComplete() {
            this.openDone = true;
            drain();
        }

        public void openError(Throwable t2) {
            this.upstream.cancel();
            this.resources.dispose();
            if (this.error.tryAddThrowableOrReport(t2)) {
                this.upstreamDone = true;
                drain();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            if (SubscriptionHelper.validate(n2)) {
                BackpressureHelper.add(this.requested, n2);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.windowCount.decrementAndGet() == 0) {
                this.upstream.cancel();
                this.startSubscriber.cancel();
                this.resources.dispose();
                this.error.tryTerminateAndReport();
                this.upstreamCanceled = true;
                drain();
            }
        }

        public void terminateDownstream(Subscriber<?> downstream) {
            Throwable thTerminate = this.error.terminate();
            if (thTerminate == null) {
                Iterator<UnicastProcessor<T>> it = this.windows.iterator();
                while (it.hasNext()) {
                    it.next().onComplete();
                }
                downstream.onComplete();
                return;
            }
            if (thTerminate != ExceptionHelper.TERMINATED) {
                Iterator<UnicastProcessor<T>> it2 = this.windows.iterator();
                while (it2.hasNext()) {
                    it2.next().onError(thTerminate);
                }
                downstream.onError(thTerminate);
            }
        }
    }

    public FlowableWindowBoundarySelector(Flowable<T> source, Publisher<B> open, Function<? super B, ? extends Publisher<V>> closingIndicator, int bufferSize) {
        super(source);
        this.open = open;
        this.closingIndicator = closingIndicator;
        this.bufferSize = bufferSize;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super Flowable<T>> s2) {
        this.source.subscribe((FlowableSubscriber) new WindowBoundaryMainSubscriber(s2, this.open, this.closingIndicator, this.bufferSize));
    }
}
