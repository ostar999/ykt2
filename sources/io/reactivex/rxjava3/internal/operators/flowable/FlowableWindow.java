package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.operators.SpscLinkedArrayQueue;
import io.reactivex.rxjava3.processors.UnicastProcessor;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableWindow<T> extends AbstractFlowableWithUpstream<T, Flowable<T>> {
    final int bufferSize;
    final long size;
    final long skip;

    public static final class WindowExactSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, Runnable {
        private static final long serialVersionUID = -2365647875069161133L;
        final int bufferSize;
        final Subscriber<? super Flowable<T>> downstream;
        long index;
        final AtomicBoolean once;
        final long size;
        Subscription upstream;
        UnicastProcessor<T> window;

        public WindowExactSubscriber(Subscriber<? super Flowable<T>> actual, long size, int bufferSize) {
            super(1);
            this.downstream = actual;
            this.size = size;
            this.once = new AtomicBoolean();
            this.bufferSize = bufferSize;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.once.compareAndSet(false, true)) {
                run();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            UnicastProcessor<T> unicastProcessor = this.window;
            if (unicastProcessor != null) {
                this.window = null;
                unicastProcessor.onComplete();
            }
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            UnicastProcessor<T> unicastProcessor = this.window;
            if (unicastProcessor != null) {
                this.window = null;
                unicastProcessor.onError(t2);
            }
            this.downstream.onError(t2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            FlowableWindowSubscribeIntercept flowableWindowSubscribeIntercept;
            long j2 = this.index;
            UnicastProcessor<T> unicastProcessorCreate = this.window;
            if (j2 == 0) {
                getAndIncrement();
                unicastProcessorCreate = UnicastProcessor.create(this.bufferSize, this);
                this.window = unicastProcessorCreate;
                flowableWindowSubscribeIntercept = new FlowableWindowSubscribeIntercept(unicastProcessorCreate);
                this.downstream.onNext(flowableWindowSubscribeIntercept);
            } else {
                flowableWindowSubscribeIntercept = null;
            }
            long j3 = j2 + 1;
            unicastProcessorCreate.onNext(t2);
            if (j3 == this.size) {
                this.index = 0L;
                this.window = null;
                unicastProcessorCreate.onComplete();
            } else {
                this.index = j3;
            }
            if (flowableWindowSubscribeIntercept == null || !flowableWindowSubscribeIntercept.tryAbandon()) {
                return;
            }
            flowableWindowSubscribeIntercept.window.onComplete();
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
                this.upstream.request(BackpressureHelper.multiplyCap(this.size, n2));
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (decrementAndGet() == 0) {
                this.upstream.cancel();
            }
        }
    }

    public static final class WindowOverlapSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, Runnable {
        private static final long serialVersionUID = 2428527070996323976L;
        final int bufferSize;
        volatile boolean cancelled;
        volatile boolean done;
        final Subscriber<? super Flowable<T>> downstream;
        Throwable error;
        final AtomicBoolean firstRequest;
        long index;
        final AtomicBoolean once;
        long produced;
        final SpscLinkedArrayQueue<UnicastProcessor<T>> queue;
        final AtomicLong requested;
        final long size;
        final long skip;
        Subscription upstream;
        final ArrayDeque<UnicastProcessor<T>> windows;
        final AtomicInteger wip;

        public WindowOverlapSubscriber(Subscriber<? super Flowable<T>> actual, long size, long skip, int bufferSize) {
            super(1);
            this.downstream = actual;
            this.size = size;
            this.skip = skip;
            this.queue = new SpscLinkedArrayQueue<>(bufferSize);
            this.windows = new ArrayDeque<>();
            this.once = new AtomicBoolean();
            this.firstRequest = new AtomicBoolean();
            this.requested = new AtomicLong();
            this.wip = new AtomicInteger();
            this.bufferSize = bufferSize;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            if (this.once.compareAndSet(false, true)) {
                run();
            }
            drain();
        }

        public boolean checkTerminated(boolean d3, boolean empty, Subscriber<?> a3, SpscLinkedArrayQueue<?> q2) {
            if (!d3) {
                return false;
            }
            Throwable th = this.error;
            if (th != null) {
                q2.clear();
                a3.onError(th);
                return true;
            }
            if (!empty) {
                return false;
            }
            a3.onComplete();
            return true;
        }

        /* JADX WARN: Code restructure failed: missing block: B:54:0x000f, code lost:
        
            continue;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void drain() {
            /*
                r15 = this;
                java.util.concurrent.atomic.AtomicInteger r0 = r15.wip
                int r0 = r0.getAndIncrement()
                if (r0 == 0) goto L9
                return
            L9:
                org.reactivestreams.Subscriber<? super io.reactivex.rxjava3.core.Flowable<T>> r0 = r15.downstream
                io.reactivex.rxjava3.operators.SpscLinkedArrayQueue<io.reactivex.rxjava3.processors.UnicastProcessor<T>> r1 = r15.queue
                r2 = 1
                r3 = r2
            Lf:
                boolean r4 = r15.cancelled
                if (r4 == 0) goto L1f
            L13:
                java.lang.Object r4 = r1.poll()
                io.reactivex.rxjava3.processors.UnicastProcessor r4 = (io.reactivex.rxjava3.processors.UnicastProcessor) r4
                if (r4 == 0) goto L84
                r4.onComplete()
                goto L13
            L1f:
                java.util.concurrent.atomic.AtomicLong r4 = r15.requested
                long r4 = r4.get()
                r6 = 0
                r8 = r6
            L28:
                int r10 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
                if (r10 == 0) goto L5d
                boolean r11 = r15.done
                java.lang.Object r12 = r1.poll()
                io.reactivex.rxjava3.processors.UnicastProcessor r12 = (io.reactivex.rxjava3.processors.UnicastProcessor) r12
                if (r12 != 0) goto L38
                r13 = r2
                goto L39
            L38:
                r13 = 0
            L39:
                boolean r14 = r15.cancelled
                if (r14 == 0) goto L3e
                goto Lf
            L3e:
                boolean r11 = r15.checkTerminated(r11, r13, r0, r1)
                if (r11 == 0) goto L45
                return
            L45:
                if (r13 == 0) goto L48
                goto L5d
            L48:
                io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowSubscribeIntercept r10 = new io.reactivex.rxjava3.internal.operators.flowable.FlowableWindowSubscribeIntercept
                r10.<init>(r12)
                r0.onNext(r10)
                boolean r10 = r10.tryAbandon()
                if (r10 == 0) goto L59
                r12.onComplete()
            L59:
                r10 = 1
                long r8 = r8 + r10
                goto L28
            L5d:
                if (r10 != 0) goto L71
                boolean r10 = r15.cancelled
                if (r10 == 0) goto L64
                goto Lf
            L64:
                boolean r10 = r15.done
                boolean r11 = r1.isEmpty()
                boolean r10 = r15.checkTerminated(r10, r11, r0, r1)
                if (r10 == 0) goto L71
                return
            L71:
                int r6 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
                if (r6 == 0) goto L84
                r6 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                if (r4 == 0) goto L84
                java.util.concurrent.atomic.AtomicLong r4 = r15.requested
                long r5 = -r8
                r4.addAndGet(r5)
            L84:
                java.util.concurrent.atomic.AtomicInteger r4 = r15.wip
                int r3 = -r3
                int r3 = r4.addAndGet(r3)
                if (r3 != 0) goto Lf
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.operators.flowable.FlowableWindow.WindowOverlapSubscriber.drain():void");
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            Iterator<UnicastProcessor<T>> it = this.windows.iterator();
            while (it.hasNext()) {
                it.next().onComplete();
            }
            this.windows.clear();
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            Iterator<UnicastProcessor<T>> it = this.windows.iterator();
            while (it.hasNext()) {
                it.next().onError(t2);
            }
            this.windows.clear();
            this.error = t2;
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            UnicastProcessor<T> unicastProcessorCreate;
            long j2 = this.index;
            if (j2 != 0 || this.cancelled) {
                unicastProcessorCreate = null;
            } else {
                getAndIncrement();
                unicastProcessorCreate = UnicastProcessor.create(this.bufferSize, this);
                this.windows.offer(unicastProcessorCreate);
            }
            long j3 = j2 + 1;
            Iterator<UnicastProcessor<T>> it = this.windows.iterator();
            while (it.hasNext()) {
                it.next().onNext(t2);
            }
            if (unicastProcessorCreate != null) {
                this.queue.offer(unicastProcessorCreate);
                drain();
            }
            long j4 = this.produced + 1;
            if (j4 == this.size) {
                this.produced = j4 - this.skip;
                UnicastProcessor<T> unicastProcessorPoll = this.windows.poll();
                if (unicastProcessorPoll != null) {
                    unicastProcessorPoll.onComplete();
                }
            } else {
                this.produced = j4;
            }
            if (j3 == this.skip) {
                this.index = 0L;
            } else {
                this.index = j3;
            }
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
                BackpressureHelper.add(this.requested, n2);
                if (this.firstRequest.get() || !this.firstRequest.compareAndSet(false, true)) {
                    this.upstream.request(BackpressureHelper.multiplyCap(this.skip, n2));
                } else {
                    this.upstream.request(BackpressureHelper.addCap(this.size, BackpressureHelper.multiplyCap(this.skip, n2 - 1)));
                }
                drain();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (decrementAndGet() == 0) {
                this.upstream.cancel();
            }
        }
    }

    public static final class WindowSkipSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, Runnable {
        private static final long serialVersionUID = -8792836352386833856L;
        final int bufferSize;
        final Subscriber<? super Flowable<T>> downstream;
        final AtomicBoolean firstRequest;
        long index;
        final AtomicBoolean once;
        final long size;
        final long skip;
        Subscription upstream;
        UnicastProcessor<T> window;

        public WindowSkipSubscriber(Subscriber<? super Flowable<T>> actual, long size, long skip, int bufferSize) {
            super(1);
            this.downstream = actual;
            this.size = size;
            this.skip = skip;
            this.once = new AtomicBoolean();
            this.firstRequest = new AtomicBoolean();
            this.bufferSize = bufferSize;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.once.compareAndSet(false, true)) {
                run();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            UnicastProcessor<T> unicastProcessor = this.window;
            if (unicastProcessor != null) {
                this.window = null;
                unicastProcessor.onComplete();
            }
            this.downstream.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable t2) {
            UnicastProcessor<T> unicastProcessor = this.window;
            if (unicastProcessor != null) {
                this.window = null;
                unicastProcessor.onError(t2);
            }
            this.downstream.onError(t2);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t2) {
            FlowableWindowSubscribeIntercept flowableWindowSubscribeIntercept;
            long j2 = this.index;
            UnicastProcessor<T> unicastProcessorCreate = this.window;
            if (j2 == 0) {
                getAndIncrement();
                unicastProcessorCreate = UnicastProcessor.create(this.bufferSize, this);
                this.window = unicastProcessorCreate;
                flowableWindowSubscribeIntercept = new FlowableWindowSubscribeIntercept(unicastProcessorCreate);
                this.downstream.onNext(flowableWindowSubscribeIntercept);
            } else {
                flowableWindowSubscribeIntercept = null;
            }
            long j3 = j2 + 1;
            if (unicastProcessorCreate != null) {
                unicastProcessorCreate.onNext(t2);
            }
            if (j3 == this.size) {
                this.window = null;
                unicastProcessorCreate.onComplete();
            }
            if (j3 == this.skip) {
                this.index = 0L;
            } else {
                this.index = j3;
            }
            if (flowableWindowSubscribeIntercept == null || !flowableWindowSubscribeIntercept.tryAbandon()) {
                return;
            }
            flowableWindowSubscribeIntercept.window.onComplete();
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
                if (this.firstRequest.get() || !this.firstRequest.compareAndSet(false, true)) {
                    this.upstream.request(BackpressureHelper.multiplyCap(this.skip, n2));
                } else {
                    this.upstream.request(BackpressureHelper.addCap(BackpressureHelper.multiplyCap(this.size, n2), BackpressureHelper.multiplyCap(this.skip - this.size, n2 - 1)));
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (decrementAndGet() == 0) {
                this.upstream.cancel();
            }
        }
    }

    public FlowableWindow(Flowable<T> source, long size, long skip, int bufferSize) {
        super(source);
        this.size = size;
        this.skip = skip;
        this.bufferSize = bufferSize;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super Flowable<T>> s2) {
        long j2 = this.skip;
        long j3 = this.size;
        if (j2 == j3) {
            this.source.subscribe((FlowableSubscriber) new WindowExactSubscriber(s2, this.size, this.bufferSize));
        } else if (j2 > j3) {
            this.source.subscribe((FlowableSubscriber) new WindowSkipSubscriber(s2, this.size, this.skip, this.bufferSize));
        } else {
            this.source.subscribe((FlowableSubscriber) new WindowOverlapSubscriber(s2, this.size, this.skip, this.bufferSize));
        }
    }
}
