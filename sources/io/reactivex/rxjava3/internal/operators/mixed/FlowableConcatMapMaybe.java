package io.reactivex.rxjava3.internal.operators.mixed;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.util.BackpressureHelper;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes8.dex */
public final class FlowableConcatMapMaybe<T, R> extends Flowable<R> {
    final ErrorMode errorMode;
    final Function<? super T, ? extends MaybeSource<? extends R>> mapper;
    final int prefetch;
    final Flowable<T> source;

    public static final class ConcatMapMaybeSubscriber<T, R> extends ConcatMapXMainSubscriber<T> implements Subscription {
        static final int STATE_ACTIVE = 1;
        static final int STATE_INACTIVE = 0;
        static final int STATE_RESULT_VALUE = 2;
        private static final long serialVersionUID = -9140123220065488293L;
        int consumed;
        final Subscriber<? super R> downstream;
        long emitted;
        final ConcatMapMaybeObserver<R> inner;
        R item;
        final Function<? super T, ? extends MaybeSource<? extends R>> mapper;
        final AtomicLong requested;
        volatile int state;

        public static final class ConcatMapMaybeObserver<R> extends AtomicReference<Disposable> implements MaybeObserver<R> {
            private static final long serialVersionUID = -3051469169682093892L;
            final ConcatMapMaybeSubscriber<?, R> parent;

            public ConcatMapMaybeObserver(ConcatMapMaybeSubscriber<?, R> parent) {
                this.parent = parent;
            }

            public void dispose() {
                DisposableHelper.dispose(this);
            }

            @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.CompletableObserver
            public void onComplete() {
                this.parent.innerComplete();
            }

            @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
            public void onError(Throwable e2) {
                this.parent.innerError(e2);
            }

            @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver, io.reactivex.rxjava3.core.CompletableObserver
            public void onSubscribe(Disposable d3) {
                DisposableHelper.replace(this, d3);
            }

            @Override // io.reactivex.rxjava3.core.MaybeObserver, io.reactivex.rxjava3.core.SingleObserver
            public void onSuccess(R t2) {
                this.parent.innerSuccess(t2);
            }
        }

        public ConcatMapMaybeSubscriber(Subscriber<? super R> downstream, Function<? super T, ? extends MaybeSource<? extends R>> mapper, int prefetch, ErrorMode errorMode) {
            super(prefetch, errorMode);
            this.downstream = downstream;
            this.mapper = mapper;
            this.requested = new AtomicLong();
            this.inner = new ConcatMapMaybeObserver<>(this);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            stop();
        }

        @Override // io.reactivex.rxjava3.internal.operators.mixed.ConcatMapXMainSubscriber
        public void clearValue() {
            this.item = null;
        }

        @Override // io.reactivex.rxjava3.internal.operators.mixed.ConcatMapXMainSubscriber
        public void disposeInner() {
            this.inner.dispose();
        }

        /* JADX WARN: Code restructure failed: missing block: B:16:0x003a, code lost:
        
            r3.clear();
            r17.item = null;
            r4.tryTerminateConsumer(r2);
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x0042, code lost:
        
            return;
         */
        @Override // io.reactivex.rxjava3.internal.operators.mixed.ConcatMapXMainSubscriber
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void drain() {
            /*
                r17 = this;
                r1 = r17
                int r0 = r17.getAndIncrement()
                if (r0 == 0) goto L9
                return
            L9:
                org.reactivestreams.Subscriber<? super R> r2 = r1.downstream
                io.reactivex.rxjava3.internal.util.ErrorMode r0 = r1.errorMode
                io.reactivex.rxjava3.operators.SimpleQueue<T> r3 = r1.queue
                io.reactivex.rxjava3.internal.util.AtomicThrowable r4 = r1.errors
                java.util.concurrent.atomic.AtomicLong r5 = r1.requested
                int r6 = r1.prefetch
                int r7 = r6 >> 1
                int r6 = r6 - r7
                boolean r7 = r1.syncFused
                r8 = 1
                r9 = r8
            L1c:
                boolean r10 = r1.cancelled
                r11 = 0
                if (r10 == 0) goto L28
                r3.clear()
                r1.item = r11
                goto Lc4
            L28:
                int r10 = r1.state
                java.lang.Object r12 = r4.get()
                if (r12 == 0) goto L43
                io.reactivex.rxjava3.internal.util.ErrorMode r12 = io.reactivex.rxjava3.internal.util.ErrorMode.IMMEDIATE
                if (r0 == r12) goto L3a
                io.reactivex.rxjava3.internal.util.ErrorMode r12 = io.reactivex.rxjava3.internal.util.ErrorMode.BOUNDARY
                if (r0 != r12) goto L43
                if (r10 != 0) goto L43
            L3a:
                r3.clear()
                r1.item = r11
                r4.tryTerminateConsumer(r2)
                return
            L43:
                r12 = 0
                if (r10 != 0) goto La7
                boolean r10 = r1.done
                java.lang.Object r11 = r3.poll()     // Catch: java.lang.Throwable -> L96
                if (r11 != 0) goto L50
                r13 = r8
                goto L51
            L50:
                r13 = r12
            L51:
                if (r10 == 0) goto L59
                if (r13 == 0) goto L59
                r4.tryTerminateConsumer(r2)
                return
            L59:
                if (r13 == 0) goto L5c
                goto Lc4
            L5c:
                if (r7 != 0) goto L6e
                int r10 = r1.consumed
                int r10 = r10 + r8
                if (r10 != r6) goto L6c
                r1.consumed = r12
                org.reactivestreams.Subscription r10 = r1.upstream
                long r12 = (long) r6
                r10.request(r12)
                goto L6e
            L6c:
                r1.consumed = r10
            L6e:
                io.reactivex.rxjava3.functions.Function<? super T, ? extends io.reactivex.rxjava3.core.MaybeSource<? extends R>> r10 = r1.mapper     // Catch: java.lang.Throwable -> L83
                java.lang.Object r10 = r10.apply(r11)     // Catch: java.lang.Throwable -> L83
                java.lang.String r11 = "The mapper returned a null MaybeSource"
                java.util.Objects.requireNonNull(r10, r11)     // Catch: java.lang.Throwable -> L83
                io.reactivex.rxjava3.core.MaybeSource r10 = (io.reactivex.rxjava3.core.MaybeSource) r10     // Catch: java.lang.Throwable -> L83
                r1.state = r8
                io.reactivex.rxjava3.internal.operators.mixed.FlowableConcatMapMaybe$ConcatMapMaybeSubscriber$ConcatMapMaybeObserver<R> r11 = r1.inner
                r10.subscribe(r11)
                goto Lc4
            L83:
                r0 = move-exception
                io.reactivex.rxjava3.exceptions.Exceptions.throwIfFatal(r0)
                org.reactivestreams.Subscription r5 = r1.upstream
                r5.cancel()
                r3.clear()
                r4.tryAddThrowableOrReport(r0)
                r4.tryTerminateConsumer(r2)
                return
            L96:
                r0 = move-exception
                r3 = r0
                io.reactivex.rxjava3.exceptions.Exceptions.throwIfFatal(r3)
                org.reactivestreams.Subscription r0 = r1.upstream
                r0.cancel()
                r4.tryAddThrowableOrReport(r3)
                r4.tryTerminateConsumer(r2)
                return
            La7:
                r13 = 2
                if (r10 != r13) goto Lc4
                long r13 = r1.emitted
                long r15 = r5.get()
                int r10 = (r13 > r15 ? 1 : (r13 == r15 ? 0 : -1))
                if (r10 == 0) goto Lc4
                R r10 = r1.item
                r1.item = r11
                r2.onNext(r10)
                r10 = 1
                long r13 = r13 + r10
                r1.emitted = r13
                r1.state = r12
                goto L1c
            Lc4:
                int r9 = -r9
                int r9 = r1.addAndGet(r9)
                if (r9 != 0) goto L1c
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.operators.mixed.FlowableConcatMapMaybe.ConcatMapMaybeSubscriber.drain():void");
        }

        public void innerComplete() {
            this.state = 0;
            drain();
        }

        public void innerError(Throwable ex) {
            if (this.errors.tryAddThrowableOrReport(ex)) {
                if (this.errorMode != ErrorMode.END) {
                    this.upstream.cancel();
                }
                this.state = 0;
                drain();
            }
        }

        public void innerSuccess(R item) {
            this.item = item;
            this.state = 2;
            drain();
        }

        @Override // io.reactivex.rxjava3.internal.operators.mixed.ConcatMapXMainSubscriber
        public void onSubscribeDownstream() {
            this.downstream.onSubscribe(this);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long n2) {
            BackpressureHelper.add(this.requested, n2);
            drain();
        }
    }

    public FlowableConcatMapMaybe(Flowable<T> source, Function<? super T, ? extends MaybeSource<? extends R>> mapper, ErrorMode errorMode, int prefetch) {
        this.source = source;
        this.mapper = mapper;
        this.errorMode = errorMode;
        this.prefetch = prefetch;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber<? super R> s2) {
        this.source.subscribe((FlowableSubscriber) new ConcatMapMaybeSubscriber(s2, this.mapper, this.prefetch, this.errorMode));
    }
}
