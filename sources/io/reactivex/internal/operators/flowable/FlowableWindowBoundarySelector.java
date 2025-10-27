package io.reactivex.internal.operators.flowable;

import androidx.camera.view.j;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscribers.QueueDrainSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.processors.UnicastProcessor;
import io.reactivex.subscribers.DisposableSubscriber;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableWindowBoundarySelector<T, B, V> extends AbstractFlowableWithUpstream<T, Flowable<T>> {
    final int bufferSize;
    final Function<? super B, ? extends Publisher<V>> close;
    final Publisher<B> open;

    public static final class OperatorWindowBoundaryCloseSubscriber<T, V> extends DisposableSubscriber<V> {
        boolean done;
        final WindowBoundaryMainSubscriber<T, ?, V> parent;

        /* renamed from: w, reason: collision with root package name */
        final UnicastProcessor<T> f27298w;

        public OperatorWindowBoundaryCloseSubscriber(WindowBoundaryMainSubscriber<T, ?, V> windowBoundaryMainSubscriber, UnicastProcessor<T> unicastProcessor) {
            this.parent = windowBoundaryMainSubscriber;
            this.f27298w = unicastProcessor;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.parent.close(this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
            } else {
                this.done = true;
                this.parent.error(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(V v2) {
            cancel();
            onComplete();
        }
    }

    public static final class OperatorWindowBoundaryOpenSubscriber<T, B> extends DisposableSubscriber<B> {
        final WindowBoundaryMainSubscriber<T, B, ?> parent;

        public OperatorWindowBoundaryOpenSubscriber(WindowBoundaryMainSubscriber<T, B, ?> windowBoundaryMainSubscriber) {
            this.parent = windowBoundaryMainSubscriber;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.parent.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.parent.error(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(B b3) {
            this.parent.open(b3);
        }
    }

    public static final class WindowBoundaryMainSubscriber<T, B, V> extends QueueDrainSubscriber<T, Object, Flowable<T>> implements Subscription {
        final AtomicReference<Disposable> boundary;
        final int bufferSize;
        final Function<? super B, ? extends Publisher<V>> close;
        final Publisher<B> open;
        final CompositeDisposable resources;
        Subscription upstream;
        final AtomicLong windows;
        final List<UnicastProcessor<T>> ws;

        public WindowBoundaryMainSubscriber(Subscriber<? super Flowable<T>> subscriber, Publisher<B> publisher, Function<? super B, ? extends Publisher<V>> function, int i2) {
            super(subscriber, new MpscLinkedQueue());
            this.boundary = new AtomicReference<>();
            AtomicLong atomicLong = new AtomicLong();
            this.windows = atomicLong;
            this.open = publisher;
            this.close = function;
            this.bufferSize = i2;
            this.resources = new CompositeDisposable();
            this.ws = new ArrayList();
            atomicLong.lazySet(1L);
        }

        @Override // io.reactivex.internal.subscribers.QueueDrainSubscriber, io.reactivex.internal.util.QueueDrain
        public boolean accept(Subscriber<? super Flowable<T>> subscriber, Object obj) {
            return false;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
        }

        public void close(OperatorWindowBoundaryCloseSubscriber<T, V> operatorWindowBoundaryCloseSubscriber) {
            this.resources.delete(operatorWindowBoundaryCloseSubscriber);
            this.queue.offer(new WindowOperation(operatorWindowBoundaryCloseSubscriber.f27298w, null));
            if (enter()) {
                drainLoop();
            }
        }

        public void dispose() {
            this.resources.dispose();
            DisposableHelper.dispose(this.boundary);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void drainLoop() {
            SimpleQueue simpleQueue = this.queue;
            Subscriber<? super V> subscriber = this.downstream;
            List<UnicastProcessor<T>> list = this.ws;
            int iLeave = 1;
            while (true) {
                boolean z2 = this.done;
                Object objPoll = simpleQueue.poll();
                boolean z3 = objPoll == null;
                if (z2 && z3) {
                    dispose();
                    Throwable th = this.error;
                    if (th != null) {
                        Iterator<UnicastProcessor<T>> it = list.iterator();
                        while (it.hasNext()) {
                            it.next().onError(th);
                        }
                    } else {
                        Iterator<UnicastProcessor<T>> it2 = list.iterator();
                        while (it2.hasNext()) {
                            it2.next().onComplete();
                        }
                    }
                    list.clear();
                    return;
                }
                if (z3) {
                    iLeave = leave(-iLeave);
                    if (iLeave == 0) {
                        return;
                    }
                } else if (objPoll instanceof WindowOperation) {
                    WindowOperation windowOperation = (WindowOperation) objPoll;
                    UnicastProcessor<T> unicastProcessor = windowOperation.f27299w;
                    if (unicastProcessor != null) {
                        if (list.remove(unicastProcessor)) {
                            windowOperation.f27299w.onComplete();
                            if (this.windows.decrementAndGet() == 0) {
                                dispose();
                                return;
                            }
                        } else {
                            continue;
                        }
                    } else if (!this.cancelled) {
                        UnicastProcessor<T> unicastProcessorCreate = UnicastProcessor.create(this.bufferSize);
                        long jRequested = requested();
                        if (jRequested != 0) {
                            list.add(unicastProcessorCreate);
                            subscriber.onNext(unicastProcessorCreate);
                            if (jRequested != Long.MAX_VALUE) {
                                produced(1L);
                            }
                            try {
                                Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.close.apply(windowOperation.open), "The publisher supplied is null");
                                OperatorWindowBoundaryCloseSubscriber operatorWindowBoundaryCloseSubscriber = new OperatorWindowBoundaryCloseSubscriber(this, unicastProcessorCreate);
                                if (this.resources.add(operatorWindowBoundaryCloseSubscriber)) {
                                    this.windows.getAndIncrement();
                                    publisher.subscribe(operatorWindowBoundaryCloseSubscriber);
                                }
                            } catch (Throwable th2) {
                                this.cancelled = true;
                                subscriber.onError(th2);
                            }
                        } else {
                            this.cancelled = true;
                            subscriber.onError(new MissingBackpressureException("Could not deliver new window due to lack of requests"));
                        }
                    }
                } else {
                    Iterator<UnicastProcessor<T>> it3 = list.iterator();
                    while (it3.hasNext()) {
                        it3.next().onNext(NotificationLite.getValue(objPoll));
                    }
                }
            }
        }

        public void error(Throwable th) {
            this.upstream.cancel();
            this.resources.dispose();
            DisposableHelper.dispose(this.boundary);
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            if (enter()) {
                drainLoop();
            }
            if (this.windows.decrementAndGet() == 0) {
                this.resources.dispose();
            }
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.error = th;
            this.done = true;
            if (enter()) {
                drainLoop();
            }
            if (this.windows.decrementAndGet() == 0) {
                this.resources.dispose();
            }
            this.downstream.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            if (this.done) {
                return;
            }
            if (fastEnter()) {
                Iterator<UnicastProcessor<T>> it = this.ws.iterator();
                while (it.hasNext()) {
                    it.next().onNext(t2);
                }
                if (leave(-1) == 0) {
                    return;
                }
            } else {
                this.queue.offer(NotificationLite.next(t2));
                if (!enter()) {
                    return;
                }
            }
            drainLoop();
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                if (this.cancelled) {
                    return;
                }
                OperatorWindowBoundaryOpenSubscriber operatorWindowBoundaryOpenSubscriber = new OperatorWindowBoundaryOpenSubscriber(this);
                if (j.a(this.boundary, null, operatorWindowBoundaryOpenSubscriber)) {
                    this.windows.getAndIncrement();
                    subscription.request(Long.MAX_VALUE);
                    this.open.subscribe(operatorWindowBoundaryOpenSubscriber);
                }
            }
        }

        public void open(B b3) {
            this.queue.offer(new WindowOperation(null, b3));
            if (enter()) {
                drainLoop();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j2) {
            requested(j2);
        }
    }

    public static final class WindowOperation<T, B> {
        final B open;

        /* renamed from: w, reason: collision with root package name */
        final UnicastProcessor<T> f27299w;

        public WindowOperation(UnicastProcessor<T> unicastProcessor, B b3) {
            this.f27299w = unicastProcessor;
            this.open = b3;
        }
    }

    public FlowableWindowBoundarySelector(Flowable<T> flowable, Publisher<B> publisher, Function<? super B, ? extends Publisher<V>> function, int i2) {
        super(flowable);
        this.open = publisher;
        this.close = function;
        this.bufferSize = i2;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super Flowable<T>> subscriber) {
        this.source.subscribe((FlowableSubscriber) new WindowBoundaryMainSubscriber(new SerializedSubscriber(subscriber), this.open, this.close, this.bufferSize));
    }
}
