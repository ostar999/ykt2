package io.reactivex.rxjava3.internal.operators.mixed;

import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.disposables.DisposableHelper;
import io.reactivex.rxjava3.internal.util.ErrorMode;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes8.dex */
public final class ObservableConcatMapMaybe<T, R> extends Observable<R> {
    final ErrorMode errorMode;
    final Function<? super T, ? extends MaybeSource<? extends R>> mapper;
    final int prefetch;
    final Observable<T> source;

    public static final class ConcatMapMaybeMainObserver<T, R> extends ConcatMapXMainObserver<T> {
        static final int STATE_ACTIVE = 1;
        static final int STATE_INACTIVE = 0;
        static final int STATE_RESULT_VALUE = 2;
        private static final long serialVersionUID = -9140123220065488293L;
        final Observer<? super R> downstream;
        final ConcatMapMaybeObserver<R> inner;
        R item;
        final Function<? super T, ? extends MaybeSource<? extends R>> mapper;
        volatile int state;

        public static final class ConcatMapMaybeObserver<R> extends AtomicReference<Disposable> implements MaybeObserver<R> {
            private static final long serialVersionUID = -3051469169682093892L;
            final ConcatMapMaybeMainObserver<?, R> parent;

            public ConcatMapMaybeObserver(ConcatMapMaybeMainObserver<?, R> parent) {
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

        public ConcatMapMaybeMainObserver(Observer<? super R> downstream, Function<? super T, ? extends MaybeSource<? extends R>> mapper, int prefetch, ErrorMode errorMode) {
            super(prefetch, errorMode);
            this.downstream = downstream;
            this.mapper = mapper;
            this.inner = new ConcatMapMaybeObserver<>(this);
        }

        @Override // io.reactivex.rxjava3.internal.operators.mixed.ConcatMapXMainObserver
        public void clearValue() {
            this.item = null;
        }

        @Override // io.reactivex.rxjava3.internal.operators.mixed.ConcatMapXMainObserver
        public void disposeInner() {
            this.inner.dispose();
        }

        /* JADX WARN: Code restructure failed: missing block: B:16:0x002f, code lost:
        
            r2.clear();
            r10.item = null;
            r3.tryTerminateConsumer(r0);
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x0037, code lost:
        
            return;
         */
        @Override // io.reactivex.rxjava3.internal.operators.mixed.ConcatMapXMainObserver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void drain() {
            /*
                r10 = this;
                int r0 = r10.getAndIncrement()
                if (r0 == 0) goto L7
                return
            L7:
                io.reactivex.rxjava3.core.Observer<? super R> r0 = r10.downstream
                io.reactivex.rxjava3.internal.util.ErrorMode r1 = r10.errorMode
                io.reactivex.rxjava3.operators.SimpleQueue<T> r2 = r10.queue
                io.reactivex.rxjava3.internal.util.AtomicThrowable r3 = r10.errors
                r4 = 1
                r5 = r4
            L11:
                boolean r6 = r10.disposed
                r7 = 0
                if (r6 == 0) goto L1d
                r2.clear()
                r10.item = r7
                goto L97
            L1d:
                int r6 = r10.state
                java.lang.Object r8 = r3.get()
                if (r8 == 0) goto L38
                io.reactivex.rxjava3.internal.util.ErrorMode r8 = io.reactivex.rxjava3.internal.util.ErrorMode.IMMEDIATE
                if (r1 == r8) goto L2f
                io.reactivex.rxjava3.internal.util.ErrorMode r8 = io.reactivex.rxjava3.internal.util.ErrorMode.BOUNDARY
                if (r1 != r8) goto L38
                if (r6 != 0) goto L38
            L2f:
                r2.clear()
                r10.item = r7
                r3.tryTerminateConsumer(r0)
                return
            L38:
                r8 = 0
                if (r6 != 0) goto L89
                boolean r6 = r10.done
                java.lang.Object r7 = r2.poll()     // Catch: java.lang.Throwable -> L77
                if (r7 != 0) goto L44
                r8 = r4
            L44:
                if (r6 == 0) goto L4c
                if (r8 == 0) goto L4c
                r3.tryTerminateConsumer(r0)
                return
            L4c:
                if (r8 == 0) goto L4f
                goto L97
            L4f:
                io.reactivex.rxjava3.functions.Function<? super T, ? extends io.reactivex.rxjava3.core.MaybeSource<? extends R>> r6 = r10.mapper     // Catch: java.lang.Throwable -> L64
                java.lang.Object r6 = r6.apply(r7)     // Catch: java.lang.Throwable -> L64
                java.lang.String r7 = "The mapper returned a null MaybeSource"
                java.util.Objects.requireNonNull(r6, r7)     // Catch: java.lang.Throwable -> L64
                io.reactivex.rxjava3.core.MaybeSource r6 = (io.reactivex.rxjava3.core.MaybeSource) r6     // Catch: java.lang.Throwable -> L64
                r10.state = r4
                io.reactivex.rxjava3.internal.operators.mixed.ObservableConcatMapMaybe$ConcatMapMaybeMainObserver$ConcatMapMaybeObserver<R> r7 = r10.inner
                r6.subscribe(r7)
                goto L97
            L64:
                r1 = move-exception
                io.reactivex.rxjava3.exceptions.Exceptions.throwIfFatal(r1)
                io.reactivex.rxjava3.disposables.Disposable r4 = r10.upstream
                r4.dispose()
                r2.clear()
                r3.tryAddThrowableOrReport(r1)
                r3.tryTerminateConsumer(r0)
                return
            L77:
                r1 = move-exception
                io.reactivex.rxjava3.exceptions.Exceptions.throwIfFatal(r1)
                r10.disposed = r4
                io.reactivex.rxjava3.disposables.Disposable r2 = r10.upstream
                r2.dispose()
                r3.tryAddThrowableOrReport(r1)
                r3.tryTerminateConsumer(r0)
                return
            L89:
                r9 = 2
                if (r6 != r9) goto L97
                R r6 = r10.item
                r10.item = r7
                r0.onNext(r6)
                r10.state = r8
                goto L11
            L97:
                int r5 = -r5
                int r5 = r10.addAndGet(r5)
                if (r5 != 0) goto L11
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.rxjava3.internal.operators.mixed.ObservableConcatMapMaybe.ConcatMapMaybeMainObserver.drain():void");
        }

        public void innerComplete() {
            this.state = 0;
            drain();
        }

        public void innerError(Throwable ex) {
            if (this.errors.tryAddThrowableOrReport(ex)) {
                if (this.errorMode != ErrorMode.END) {
                    this.upstream.dispose();
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

        @Override // io.reactivex.rxjava3.internal.operators.mixed.ConcatMapXMainObserver
        public void onSubscribeDownstream() {
            this.downstream.onSubscribe(this);
        }
    }

    public ObservableConcatMapMaybe(Observable<T> source, Function<? super T, ? extends MaybeSource<? extends R>> mapper, ErrorMode errorMode, int prefetch) {
        this.source = source;
        this.mapper = mapper;
        this.errorMode = errorMode;
        this.prefetch = prefetch;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer<? super R> observer) {
        if (ScalarXMapZHelper.tryAsMaybe(this.source, this.mapper, observer)) {
            return;
        }
        this.source.subscribe(new ConcatMapMaybeMainObserver(observer, this.mapper, this.prefetch, this.errorMode));
    }
}
