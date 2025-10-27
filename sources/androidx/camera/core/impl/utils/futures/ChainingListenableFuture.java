package androidx.camera.core.impl.utils.futures;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RequiresApi(21)
/* loaded from: classes.dex */
class ChainingListenableFuture<I, O> extends FutureChain<O> implements Runnable {

    @Nullable
    private AsyncFunction<? super I, ? extends O> mFunction;

    @Nullable
    private ListenableFuture<? extends I> mInputFuture;
    private final BlockingQueue<Boolean> mMayInterruptIfRunningChannel = new LinkedBlockingQueue(1);
    private final CountDownLatch mOutputCreated = new CountDownLatch(1);

    @Nullable
    volatile ListenableFuture<? extends O> mOutputFuture;

    public ChainingListenableFuture(@NonNull AsyncFunction<? super I, ? extends O> asyncFunction, @NonNull ListenableFuture<? extends I> listenableFuture) {
        this.mFunction = (AsyncFunction) Preconditions.checkNotNull(asyncFunction);
        this.mInputFuture = (ListenableFuture) Preconditions.checkNotNull(listenableFuture);
    }

    private <E> void putUninterruptibly(@NonNull BlockingQueue<E> blockingQueue, @NonNull E e2) {
        boolean z2 = false;
        while (true) {
            try {
                blockingQueue.put(e2);
                break;
            } catch (InterruptedException unused) {
                z2 = true;
            } catch (Throwable th) {
                if (z2) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
        if (z2) {
            Thread.currentThread().interrupt();
        }
    }

    private <E> E takeUninterruptibly(@NonNull BlockingQueue<E> blockingQueue) {
        E eTake;
        boolean z2 = false;
        while (true) {
            try {
                eTake = blockingQueue.take();
                break;
            } catch (InterruptedException unused) {
                z2 = true;
            } catch (Throwable th) {
                if (z2) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
        if (z2) {
            Thread.currentThread().interrupt();
        }
        return eTake;
    }

    @Override // androidx.camera.core.impl.utils.futures.FutureChain, java.util.concurrent.Future
    public boolean cancel(boolean z2) {
        if (!super.cancel(z2)) {
            return false;
        }
        putUninterruptibly(this.mMayInterruptIfRunningChannel, Boolean.valueOf(z2));
        cancel(this.mInputFuture, z2);
        cancel(this.mOutputFuture, z2);
        return true;
    }

    @Override // androidx.camera.core.impl.utils.futures.FutureChain, java.util.concurrent.Future
    @Nullable
    public O get() throws ExecutionException, InterruptedException {
        if (!isDone()) {
            ListenableFuture<? extends I> listenableFuture = this.mInputFuture;
            if (listenableFuture != null) {
                listenableFuture.get();
            }
            this.mOutputCreated.await();
            ListenableFuture<? extends O> listenableFuture2 = this.mOutputFuture;
            if (listenableFuture2 != null) {
                listenableFuture2.get();
            }
        }
        return (O) super.get();
    }

    @Override // java.lang.Runnable
    public void run() {
        final ListenableFuture<? extends O> listenableFutureApply;
        try {
        } catch (Exception e2) {
            setException(e2);
        }
        try {
            try {
                try {
                    listenableFutureApply = this.mFunction.apply(Futures.getUninterruptibly(this.mInputFuture));
                    this.mOutputFuture = listenableFutureApply;
                } catch (Error e3) {
                    setException(e3);
                } catch (UndeclaredThrowableException e4) {
                    setException(e4.getCause());
                }
            } catch (CancellationException unused) {
                cancel(false);
            } catch (ExecutionException e5) {
                setException(e5.getCause());
            }
            if (!isCancelled()) {
                listenableFutureApply.addListener(new Runnable() { // from class: androidx.camera.core.impl.utils.futures.ChainingListenableFuture.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            try {
                                ChainingListenableFuture.this.set(Futures.getUninterruptibly(listenableFutureApply));
                            } catch (CancellationException unused2) {
                                ChainingListenableFuture.this.cancel(false);
                                ChainingListenableFuture.this.mOutputFuture = null;
                                return;
                            } catch (ExecutionException e6) {
                                ChainingListenableFuture.this.setException(e6.getCause());
                            }
                            ChainingListenableFuture.this.mOutputFuture = null;
                        } catch (Throwable th) {
                            ChainingListenableFuture.this.mOutputFuture = null;
                            throw th;
                        }
                    }
                }, CameraXExecutors.directExecutor());
                this.mFunction = null;
                this.mInputFuture = null;
                this.mOutputCreated.countDown();
                return;
            }
            listenableFutureApply.cancel(((Boolean) takeUninterruptibly(this.mMayInterruptIfRunningChannel)).booleanValue());
            this.mOutputFuture = null;
            this.mFunction = null;
            this.mInputFuture = null;
            this.mOutputCreated.countDown();
        } catch (Throwable th) {
            this.mFunction = null;
            this.mInputFuture = null;
            this.mOutputCreated.countDown();
            throw th;
        }
    }

    private void cancel(@Nullable Future<?> future, boolean z2) {
        if (future != null) {
            future.cancel(z2);
        }
    }

    @Override // androidx.camera.core.impl.utils.futures.FutureChain, java.util.concurrent.Future
    @Nullable
    public O get(long j2, @NonNull TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        if (!isDone()) {
            TimeUnit timeUnit2 = TimeUnit.NANOSECONDS;
            if (timeUnit != timeUnit2) {
                j2 = timeUnit2.convert(j2, timeUnit);
                timeUnit = timeUnit2;
            }
            ListenableFuture<? extends I> listenableFuture = this.mInputFuture;
            if (listenableFuture != null) {
                long jNanoTime = System.nanoTime();
                listenableFuture.get(j2, timeUnit);
                j2 -= Math.max(0L, System.nanoTime() - jNanoTime);
            }
            long jNanoTime2 = System.nanoTime();
            if (this.mOutputCreated.await(j2, timeUnit)) {
                j2 -= Math.max(0L, System.nanoTime() - jNanoTime2);
                ListenableFuture<? extends O> listenableFuture2 = this.mOutputFuture;
                if (listenableFuture2 != null) {
                    listenableFuture2.get(j2, timeUnit);
                }
            } else {
                throw new TimeoutException();
            }
        }
        return (O) super.get(j2, timeUnit);
    }
}
