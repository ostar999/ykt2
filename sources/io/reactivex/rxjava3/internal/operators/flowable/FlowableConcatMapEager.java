package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.exceptions.MissingBackpressureException;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.subscribers.InnerQueuedSubscriber;
import io.reactivex.rxjava3.internal.subscribers.InnerQueuedSubscriberSupport;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.AtomicThrowable;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import io.reactivex.rxjava3.operators.SpscLinkedArrayQueue;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableConcatMapEager<T, R> extends AbstractFlowableWithUpstream<T, R> {
    final ErrorMode errorMode;
    final Function<? super T, ? extends Publisher<? extends R>> mapper;
    final int maxConcurrency;
    final int prefetch;

    public static final class ConcatMapEagerDelayErrorSubscriber<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, InnerQueuedSubscriberSupport<R> {
        private static final long serialVersionUID = -4255299542215038287L;
        volatile boolean cancelled;
        volatile InnerQueuedSubscriber<R> current;
        volatile boolean done;
        final Subscriber<? super R> downstream;
        final ErrorMode errorMode;
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        final int maxConcurrency;
        final int prefetch;
        final SpscLinkedArrayQueue<InnerQueuedSubscriber<R>> subscribers;
        Subscription upstream;
        final AtomicThrowable errors = new AtomicThrowable();
        final AtomicLong requested = new AtomicLong();

        public ConcatMapEagerDelayErrorSubscriber(Subscriber<? super R> actual, Function<? super T, ? extends Publisher<? extends R>> mapper, int maxConcurrency, int prefetch, ErrorMode errorMode) {
            this.downstream = actual;
            this.mapper = mapper;
            this.maxConcurrency = maxConcurrency;
            this.prefetch = prefetch;
            this.errorMode = errorMode;
            this.subscribers = new SpscLinkedArrayQueue<>(Math.min(prefetch, maxConcurrency));
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.upstream.cancel();
            this.errors.tryTerminateAndReport();
            drainAndCancel();
        }

        public void cancelAll() {
            InnerQueuedSubscriber<R> innerQueuedSubscriber = this.current;
            this.current = null;
            if (innerQueuedSubscriber != null) {
                innerQueuedSubscriber.cancel();
            }
            while (true) {
                InnerQueuedSubscriber<R> innerQueuedSubscriberPoll = this.subscribers.poll();
                if (innerQueuedSubscriberPoll == null) {
                    return;
                } else {
                    innerQueuedSubscriberPoll.cancel();
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:52:0x00bf, code lost:
        
            r0 = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:53:0x00c0, code lost:
        
            if (r12 != r6) goto L68;
         */
        /* JADX WARN: Code restructure failed: missing block: B:55:0x00c4, code lost:
        
            if (r17.cancelled == false) goto L58;
         */
        /* JADX WARN: Code restructure failed: missing block: B:56:0x00c6, code lost:
        
            cancelAll();
         */
        /* JADX WARN: Code restructure failed: missing block: B:57:0x00c9, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:59:0x00cc, code lost:
        
            if (r3 != io.reactivex.rxjava3.internal.util.ErrorMode.IMMEDIATE) goto L64;
         */
        /* JADX WARN: Code restructure failed: missing block: B:61:0x00d6, code lost:
        
            if (r17.errors.get() == null) goto L64;
         */
        /* JADX WARN: Code restructure failed: missing block: B:62:0x00d8, code lost:
        
            r17.current = null;
            r8.cancel();
            cancelAll();
            r17.errors.tryTerminateConsumer(r17.downstream);
         */
        /* JADX WARN: Code restructure failed: missing block: B:63:0x00e7, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:64:0x00e8, code lost:
        
            r9 = r8.isDone();
            r10 = r11.isEmpty();
         */
        /* JADX WARN: Code restructure failed: missing block: B:65:0x00f0, code lost:
        
            if (r9 == false) goto L68;
         */
        /* JADX WARN: Code restructure failed: missing block: B:66:0x00f2, code lost:
        
            if (r10 == false) goto L68;
         */
        /* JADX WARN: Code restructure failed: missing block: B:67:0x00f4, code lost:
        
            r17.current = null;
            r17.upstream.request(1);
            r8 = null;
            r0 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:68:0x00fd, code lost:
        
            r4 = 0;
         */
        @Override // io.reactivex.rxjava3.internal.subscribers.InnerQueuedSubscriberSupport
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void drain() {
            /*
                Method dump skipped, instructions count: 302
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.operators.flowable.FlowableConcatMapEager.ConcatMapEagerDelayErrorSubscriber.drain():void");
        }

        public void drainAndCancel() {
            if (getAndIncrement() == 0) {
                do {
                    cancelAll();
                } while (decrementAndGet() != 0);
            }
        }

        @Override // io.reactivex.rxjava3.internal.subscribers.InnerQueuedSubscriberSupport
        public void innerComplete(InnerQueuedSubscriber<R> inner) {
            inner.setDone();
            drain();
        }

        @Override // io.reactivex.rxjava3.internal.subscribers.InnerQueuedSubscriberSupport
        public void innerError(InnerQueuedSubscriber<R> inner, Throwable e2) {
            if (this.errors.tryAddThrowableOrReport(e2)) {
                inner.setDone();
                if (this.errorMode != ErrorMode.END) {
                    this.upstream.cancel();
                }
                drain();
            }
        }

        @Override // io.reactivex.rxjava3.internal.subscribers.InnerQueuedSubscriberSupport
        public void innerNext(InnerQueuedSubscriber<R> inner, R value) {
            if (inner.queue().offer(value)) {
                drain();
            } else {
                inner.cancel();
                innerError(inner, new MissingBackpressureException());
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            if (this.errors.tryAddThrowableOrReport(t2)) {
                this.done = true;
                drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            try {
                Publisher<? extends R> publisherApply = this.mapper.apply(t2);
                Objects.requireNonNull(publisherApply, "The mapper returned a null Publisher");
                Publisher<? extends R> publisher = publisherApply;
                InnerQueuedSubscriber<R> innerQueuedSubscriber = new InnerQueuedSubscriber<>(this, this.prefetch);
                if (this.cancelled) {
                    return;
                }
                this.subscribers.offer(innerQueuedSubscriber);
                publisher.subscribe(innerQueuedSubscriber);
                if (this.cancelled) {
                    innerQueuedSubscriber.cancel();
                    drainAndCancel();
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.upstream.cancel();
                onError(th);
            }
        }

        @Override // io.reactivex.rxjava3.core.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription s2) {
            if (SubscriptionHelper.validate(this.upstream, s2)) {
                this.upstream = s2;
                this.downstream.onSubscribe(this);
                int i2 = this.maxConcurrency;
                s2.request(i2 == Integer.MAX_VALUE ? Long.MAX_VALUE : i2);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            if (SubscriptionHelper.validate(n2)) {
                BackpressureHelper.add(this.requested, n2);
                drain();
            }
        }
    }

    public FlowableConcatMapEager(Flowable<T> source, Function<? super T, ? extends Publisher<? extends R>> mapper, int maxConcurrency, int prefetch, ErrorMode errorMode) {
        super(source);
        this.mapper = mapper;
        this.maxConcurrency = maxConcurrency;
        this.prefetch = prefetch;
        this.errorMode = errorMode;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super R> s2) {
        this.source.subscribe((FlowableSubscriber) new ConcatMapEagerDelayErrorSubscriber(s2, this.mapper, this.maxConcurrency, this.prefetch, this.errorMode));
    }
}
