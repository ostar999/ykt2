package com.google.common.util.concurrent;

import cn.hutool.core.text.StrPool;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.FluentFuture;
import com.google.errorprone.annotations.ForOverride;
import java.lang.Throwable;
import java.util.concurrent.Executor;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes4.dex */
abstract class AbstractCatchingFuture<V, X extends Throwable, F, T> extends FluentFuture.TrustedFuture<V> implements Runnable {

    @NullableDecl
    Class<X> exceptionType;

    @NullableDecl
    F fallback;

    @NullableDecl
    ListenableFuture<? extends V> inputFuture;

    public static final class AsyncCatchingFuture<V, X extends Throwable> extends AbstractCatchingFuture<V, X, AsyncFunction<? super X, ? extends V>, ListenableFuture<? extends V>> {
        public AsyncCatchingFuture(ListenableFuture<? extends V> listenableFuture, Class<X> cls, AsyncFunction<? super X, ? extends V> asyncFunction) {
            super(listenableFuture, cls, asyncFunction);
        }

        @Override // com.google.common.util.concurrent.AbstractCatchingFuture
        public /* bridge */ /* synthetic */ Object doFallback(Object obj, Throwable th) throws Exception {
            return doFallback((AsyncFunction<? super AsyncFunction<? super X, ? extends V>, ? extends V>) obj, (AsyncFunction<? super X, ? extends V>) th);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public ListenableFuture<? extends V> doFallback(AsyncFunction<? super X, ? extends V> asyncFunction, X x2) throws Exception {
            ListenableFuture<? extends V> listenableFutureApply = asyncFunction.apply(x2);
            Preconditions.checkNotNull(listenableFutureApply, "AsyncFunction.apply returned null instead of a Future. Did you mean to return immediateFuture(null)? %s", asyncFunction);
            return listenableFutureApply;
        }

        @Override // com.google.common.util.concurrent.AbstractCatchingFuture
        public void setResult(ListenableFuture<? extends V> listenableFuture) {
            setFuture(listenableFuture);
        }
    }

    public static final class CatchingFuture<V, X extends Throwable> extends AbstractCatchingFuture<V, X, Function<? super X, ? extends V>, V> {
        public CatchingFuture(ListenableFuture<? extends V> listenableFuture, Class<X> cls, Function<? super X, ? extends V> function) {
            super(listenableFuture, cls, function);
        }

        @Override // com.google.common.util.concurrent.AbstractCatchingFuture
        @NullableDecl
        public /* bridge */ /* synthetic */ Object doFallback(Object obj, Throwable th) throws Exception {
            return doFallback((Function<? super Function<? super X, ? extends V>, ? extends V>) obj, (Function<? super X, ? extends V>) th);
        }

        @Override // com.google.common.util.concurrent.AbstractCatchingFuture
        public void setResult(@NullableDecl V v2) {
            set(v2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @NullableDecl
        public V doFallback(Function<? super X, ? extends V> function, X x2) throws Exception {
            return function.apply(x2);
        }
    }

    public AbstractCatchingFuture(ListenableFuture<? extends V> listenableFuture, Class<X> cls, F f2) {
        this.inputFuture = (ListenableFuture) Preconditions.checkNotNull(listenableFuture);
        this.exceptionType = (Class) Preconditions.checkNotNull(cls);
        this.fallback = (F) Preconditions.checkNotNull(f2);
    }

    public static <V, X extends Throwable> ListenableFuture<V> create(ListenableFuture<? extends V> listenableFuture, Class<X> cls, Function<? super X, ? extends V> function, Executor executor) {
        CatchingFuture catchingFuture = new CatchingFuture(listenableFuture, cls, function);
        listenableFuture.addListener(catchingFuture, MoreExecutors.rejectionPropagatingExecutor(executor, catchingFuture));
        return catchingFuture;
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    public final void afterDone() {
        maybePropagateCancellationTo(this.inputFuture);
        this.inputFuture = null;
        this.exceptionType = null;
        this.fallback = null;
    }

    @NullableDecl
    @ForOverride
    public abstract T doFallback(F f2, X x2) throws Exception;

    @Override // com.google.common.util.concurrent.AbstractFuture
    public String pendingToString() {
        String str;
        ListenableFuture<? extends V> listenableFuture = this.inputFuture;
        Class<X> cls = this.exceptionType;
        F f2 = this.fallback;
        String strPendingToString = super.pendingToString();
        if (listenableFuture != null) {
            str = "inputFuture=[" + listenableFuture + "], ";
        } else {
            str = "";
        }
        if (cls == null || f2 == null) {
            if (strPendingToString == null) {
                return null;
            }
            return str + strPendingToString;
        }
        return str + "exceptionType=[" + cls + "], fallback=[" + f2 + StrPool.BRACKET_END;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:25:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x003e  */
    /* JADX WARN: Type inference failed for: r3v4, types: [F, java.lang.Class<X extends java.lang.Throwable>] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() throws java.util.concurrent.ExecutionException {
        /*
            r7 = this;
            com.google.common.util.concurrent.ListenableFuture<? extends V> r0 = r7.inputFuture
            java.lang.Class<X extends java.lang.Throwable> r1 = r7.exceptionType
            F r2 = r7.fallback
            r3 = 1
            r4 = 0
            if (r0 != 0) goto Lc
            r5 = r3
            goto Ld
        Lc:
            r5 = r4
        Ld:
            if (r1 != 0) goto L11
            r6 = r3
            goto L12
        L11:
            r6 = r4
        L12:
            r5 = r5 | r6
            if (r2 != 0) goto L16
            goto L17
        L16:
            r3 = r4
        L17:
            r3 = r3 | r5
            boolean r4 = r7.isCancelled()
            r3 = r3 | r4
            if (r3 == 0) goto L20
            return
        L20:
            r3 = 0
            r7.inputFuture = r3
            java.lang.Object r4 = com.google.common.util.concurrent.Futures.getDone(r0)     // Catch: java.lang.Throwable -> L2a java.util.concurrent.ExecutionException -> L2c
            r5 = r4
            r4 = r3
            goto L38
        L2a:
            r4 = move-exception
            goto L37
        L2c:
            r4 = move-exception
            java.lang.Throwable r4 = r4.getCause()
            java.lang.Object r4 = com.google.common.base.Preconditions.checkNotNull(r4)
            java.lang.Throwable r4 = (java.lang.Throwable) r4
        L37:
            r5 = r3
        L38:
            if (r4 != 0) goto L3e
            r7.set(r5)
            return
        L3e:
            boolean r1 = com.google.common.util.concurrent.Platform.isInstanceOfThrowableClass(r4, r1)
            if (r1 != 0) goto L48
            r7.setFuture(r0)
            return
        L48:
            java.lang.Object r0 = r7.doFallback(r2, r4)     // Catch: java.lang.Throwable -> L54
            r7.exceptionType = r3
            r7.fallback = r3
            r7.setResult(r0)
            return
        L54:
            r0 = move-exception
            r7.setException(r0)     // Catch: java.lang.Throwable -> L5d
            r7.exceptionType = r3
            r7.fallback = r3
            return
        L5d:
            r0 = move-exception
            r7.exceptionType = r3
            r7.fallback = r3
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.AbstractCatchingFuture.run():void");
    }

    @ForOverride
    public abstract void setResult(@NullableDecl T t2);

    public static <X extends Throwable, V> ListenableFuture<V> create(ListenableFuture<? extends V> listenableFuture, Class<X> cls, AsyncFunction<? super X, ? extends V> asyncFunction, Executor executor) {
        AsyncCatchingFuture asyncCatchingFuture = new AsyncCatchingFuture(listenableFuture, cls, asyncFunction);
        listenableFuture.addListener(asyncCatchingFuture, MoreExecutors.rejectionPropagatingExecutor(executor, asyncCatchingFuture));
        return asyncCatchingFuture;
    }
}
