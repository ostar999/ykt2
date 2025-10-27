package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes4.dex */
final class CombinedFuture<V> extends AggregateFuture<Object, V> {

    public final class AsyncCallableInterruptibleTask extends CombinedFuture<V>.CombinedFutureInterruptibleTask<ListenableFuture<V>> {
        private final AsyncCallable<V> callable;

        public AsyncCallableInterruptibleTask(AsyncCallable<V> asyncCallable, Executor executor) {
            super(executor);
            this.callable = (AsyncCallable) Preconditions.checkNotNull(asyncCallable);
        }

        @Override // com.google.common.util.concurrent.InterruptibleTask
        public String toPendingString() {
            return this.callable.toString();
        }

        @Override // com.google.common.util.concurrent.InterruptibleTask
        public ListenableFuture<V> runInterruptibly() throws Exception {
            this.thrownByExecute = false;
            return (ListenableFuture) Preconditions.checkNotNull(this.callable.call(), "AsyncCallable.call returned null instead of a Future. Did you mean to return immediateFuture(null)? %s", this.callable);
        }

        @Override // com.google.common.util.concurrent.CombinedFuture.CombinedFutureInterruptibleTask
        public void setValue(ListenableFuture<V> listenableFuture) {
            CombinedFuture.this.setFuture(listenableFuture);
        }
    }

    public final class CallableInterruptibleTask extends CombinedFuture<V>.CombinedFutureInterruptibleTask<V> {
        private final Callable<V> callable;

        public CallableInterruptibleTask(Callable<V> callable, Executor executor) {
            super(executor);
            this.callable = (Callable) Preconditions.checkNotNull(callable);
        }

        @Override // com.google.common.util.concurrent.InterruptibleTask
        public V runInterruptibly() throws Exception {
            this.thrownByExecute = false;
            return this.callable.call();
        }

        @Override // com.google.common.util.concurrent.CombinedFuture.CombinedFutureInterruptibleTask
        public void setValue(V v2) {
            CombinedFuture.this.set(v2);
        }

        @Override // com.google.common.util.concurrent.InterruptibleTask
        public String toPendingString() {
            return this.callable.toString();
        }
    }

    public abstract class CombinedFutureInterruptibleTask<T> extends InterruptibleTask<T> {
        private final Executor listenerExecutor;
        boolean thrownByExecute = true;

        public CombinedFutureInterruptibleTask(Executor executor) {
            this.listenerExecutor = (Executor) Preconditions.checkNotNull(executor);
        }

        @Override // com.google.common.util.concurrent.InterruptibleTask
        public final void afterRanInterruptibly(T t2, Throwable th) {
            if (th == null) {
                setValue(t2);
                return;
            }
            if (th instanceof ExecutionException) {
                CombinedFuture.this.setException(th.getCause());
            } else if (th instanceof CancellationException) {
                CombinedFuture.this.cancel(false);
            } else {
                CombinedFuture.this.setException(th);
            }
        }

        public final void execute() {
            try {
                this.listenerExecutor.execute(this);
            } catch (RejectedExecutionException e2) {
                if (this.thrownByExecute) {
                    CombinedFuture.this.setException(e2);
                }
            }
        }

        @Override // com.google.common.util.concurrent.InterruptibleTask
        public final boolean isDone() {
            return CombinedFuture.this.isDone();
        }

        public abstract void setValue(T t2);
    }

    public final class CombinedFutureRunningState extends AggregateFuture<Object, V>.RunningState {
        private CombinedFutureInterruptibleTask task;

        public CombinedFutureRunningState(ImmutableCollection<? extends ListenableFuture<?>> immutableCollection, boolean z2, CombinedFutureInterruptibleTask combinedFutureInterruptibleTask) {
            super(immutableCollection, z2, false);
            this.task = combinedFutureInterruptibleTask;
        }

        @Override // com.google.common.util.concurrent.AggregateFuture.RunningState
        public void collectOneValue(boolean z2, int i2, @NullableDecl Object obj) {
        }

        @Override // com.google.common.util.concurrent.AggregateFuture.RunningState
        public void handleAllCompleted() {
            CombinedFutureInterruptibleTask combinedFutureInterruptibleTask = this.task;
            if (combinedFutureInterruptibleTask != null) {
                combinedFutureInterruptibleTask.execute();
            } else {
                Preconditions.checkState(CombinedFuture.this.isDone());
            }
        }

        @Override // com.google.common.util.concurrent.AggregateFuture.RunningState
        public void interruptTask() {
            CombinedFutureInterruptibleTask combinedFutureInterruptibleTask = this.task;
            if (combinedFutureInterruptibleTask != null) {
                combinedFutureInterruptibleTask.interruptTask();
            }
        }

        @Override // com.google.common.util.concurrent.AggregateFuture.RunningState
        public void releaseResourcesAfterFailure() {
            super.releaseResourcesAfterFailure();
            this.task = null;
        }
    }

    public CombinedFuture(ImmutableCollection<? extends ListenableFuture<?>> immutableCollection, boolean z2, Executor executor, AsyncCallable<V> asyncCallable) {
        init(new CombinedFutureRunningState(immutableCollection, z2, new AsyncCallableInterruptibleTask(asyncCallable, executor)));
    }

    public CombinedFuture(ImmutableCollection<? extends ListenableFuture<?>> immutableCollection, boolean z2, Executor executor, Callable<V> callable) {
        init(new CombinedFutureRunningState(immutableCollection, z2, new CallableInterruptibleTask(callable, executor)));
    }
}
