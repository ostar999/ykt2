package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;

@GwtCompatible(emulated = true)
/* loaded from: classes4.dex */
public final class Uninterruptibles {
    private Uninterruptibles() {
    }

    @GwtIncompatible
    public static void awaitUninterruptibly(CountDownLatch countDownLatch) {
        boolean z2 = false;
        while (true) {
            try {
                countDownLatch.await();
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

    @CanIgnoreReturnValue
    public static <V> V getUninterruptibly(Future<V> future) throws ExecutionException {
        V v2;
        boolean z2 = false;
        while (true) {
            try {
                v2 = future.get();
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
        return v2;
    }

    @GwtIncompatible
    public static void joinUninterruptibly(Thread thread) {
        boolean z2 = false;
        while (true) {
            try {
                thread.join();
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

    @GwtIncompatible
    public static <E> void putUninterruptibly(BlockingQueue<E> blockingQueue, E e2) {
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

    @GwtIncompatible
    public static void sleepUninterruptibly(long j2, TimeUnit timeUnit) {
        boolean z2 = false;
        try {
            long nanos = timeUnit.toNanos(j2);
            long jNanoTime = System.nanoTime() + nanos;
            while (true) {
                try {
                    TimeUnit.NANOSECONDS.sleep(nanos);
                    break;
                } catch (InterruptedException unused) {
                    z2 = true;
                    nanos = jNanoTime - System.nanoTime();
                }
            }
        } finally {
            if (z2) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @GwtIncompatible
    public static <E> E takeUninterruptibly(BlockingQueue<E> blockingQueue) {
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

    @GwtIncompatible
    public static boolean tryAcquireUninterruptibly(Semaphore semaphore, long j2, TimeUnit timeUnit) {
        return tryAcquireUninterruptibly(semaphore, 1, j2, timeUnit);
    }

    @GwtIncompatible
    public static boolean tryAcquireUninterruptibly(Semaphore semaphore, int i2, long j2, TimeUnit timeUnit) {
        boolean z2 = false;
        try {
            long nanos = timeUnit.toNanos(j2);
            while (true) {
                try {
                    break;
                } catch (InterruptedException unused) {
                    z2 = true;
                    nanos = (System.nanoTime() + nanos) - System.nanoTime();
                }
            }
            return semaphore.tryAcquire(i2, nanos, TimeUnit.NANOSECONDS);
        } finally {
            if (z2) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @CanIgnoreReturnValue
    @GwtIncompatible
    public static boolean awaitUninterruptibly(CountDownLatch countDownLatch, long j2, TimeUnit timeUnit) {
        boolean z2 = false;
        try {
            long nanos = timeUnit.toNanos(j2);
            while (true) {
                try {
                    break;
                } catch (InterruptedException unused) {
                    z2 = true;
                    nanos = (System.nanoTime() + nanos) - System.nanoTime();
                }
            }
            return countDownLatch.await(nanos, TimeUnit.NANOSECONDS);
        } finally {
            if (z2) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @CanIgnoreReturnValue
    @GwtIncompatible
    public static <V> V getUninterruptibly(Future<V> future, long j2, TimeUnit timeUnit) throws ExecutionException, TimeoutException {
        boolean z2 = false;
        try {
            long nanos = timeUnit.toNanos(j2);
            while (true) {
                try {
                    break;
                } catch (InterruptedException unused) {
                    z2 = true;
                    nanos = (System.nanoTime() + nanos) - System.nanoTime();
                }
            }
            return future.get(nanos, TimeUnit.NANOSECONDS);
        } finally {
            if (z2) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @GwtIncompatible
    public static void joinUninterruptibly(Thread thread, long j2, TimeUnit timeUnit) {
        Preconditions.checkNotNull(thread);
        boolean z2 = false;
        try {
            long nanos = timeUnit.toNanos(j2);
            long jNanoTime = System.nanoTime() + nanos;
            while (true) {
                try {
                    TimeUnit.NANOSECONDS.timedJoin(thread, nanos);
                    break;
                } catch (InterruptedException unused) {
                    z2 = true;
                    nanos = jNanoTime - System.nanoTime();
                }
            }
        } finally {
            if (z2) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @GwtIncompatible
    public static boolean awaitUninterruptibly(Condition condition, long j2, TimeUnit timeUnit) {
        boolean z2 = false;
        try {
            long nanos = timeUnit.toNanos(j2);
            while (true) {
                try {
                    break;
                } catch (InterruptedException unused) {
                    z2 = true;
                    nanos = (System.nanoTime() + nanos) - System.nanoTime();
                }
            }
            return condition.await(nanos, TimeUnit.NANOSECONDS);
        } finally {
            if (z2) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
