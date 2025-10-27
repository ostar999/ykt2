package org.wrtca.util;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes9.dex */
public class ThreadUtils {

    /* renamed from: org.wrtca.util.ThreadUtils$1CaughtException, reason: invalid class name */
    public class C1CaughtException {

        /* renamed from: e, reason: collision with root package name */
        public Exception f28137e;
    }

    /* renamed from: org.wrtca.util.ThreadUtils$1Result, reason: invalid class name */
    public class C1Result {
        public V value;
    }

    public interface BlockingOperation {
        void run() throws InterruptedException;
    }

    public static class ThreadChecker {
        private Thread thread = Thread.currentThread();

        public void checkIsOnValidThread() {
            if (this.thread == null) {
                this.thread = Thread.currentThread();
            }
            if (Thread.currentThread() != this.thread) {
                throw new IllegalStateException("Wrong thread");
            }
        }

        public void detachThread() {
            this.thread = null;
        }
    }

    public static void awaitUninterruptibly(final CountDownLatch countDownLatch) {
        executeUninterruptibly(new BlockingOperation() { // from class: org.wrtca.util.ThreadUtils.2
            @Override // org.wrtca.util.ThreadUtils.BlockingOperation
            public void run() throws InterruptedException {
                countDownLatch.await();
            }
        });
    }

    public static void checkIsOnMainThread() {
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            throw new IllegalStateException("Not on main thread!");
        }
    }

    public static StackTraceElement[] concatStackTraces(StackTraceElement[] stackTraceElementArr, StackTraceElement[] stackTraceElementArr2) {
        StackTraceElement[] stackTraceElementArr3 = new StackTraceElement[stackTraceElementArr.length + stackTraceElementArr2.length];
        System.arraycopy(stackTraceElementArr, 0, stackTraceElementArr3, 0, stackTraceElementArr.length);
        System.arraycopy(stackTraceElementArr2, 0, stackTraceElementArr3, stackTraceElementArr.length, stackTraceElementArr2.length);
        return stackTraceElementArr3;
    }

    public static void executeUninterruptibly(BlockingOperation blockingOperation) {
        boolean z2 = false;
        while (true) {
            try {
                blockingOperation.run();
                break;
            } catch (InterruptedException unused) {
                z2 = true;
            }
        }
        if (z2) {
            Thread.currentThread().interrupt();
        }
    }

    public static <V> V invokeAtFrontUninterruptibly(Handler handler, final Callable<V> callable) {
        if (handler.getLooper().getThread() == Thread.currentThread()) {
            try {
                return callable.call();
            } catch (Exception e2) {
                throw new RuntimeException(e2);
            }
        }
        final C1Result c1Result = new C1Result();
        final C1CaughtException c1CaughtException = new C1CaughtException();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        handler.post(new Runnable() { // from class: org.wrtca.util.ThreadUtils.3
            /* JADX WARN: Type inference failed for: r1v2, types: [V, java.lang.Object] */
            @Override // java.lang.Runnable
            public void run() {
                try {
                    c1Result.value = callable.call();
                } catch (Exception e3) {
                    c1CaughtException.f28137e = e3;
                }
                countDownLatch.countDown();
            }
        });
        awaitUninterruptibly(countDownLatch);
        if (c1CaughtException.f28137e == null) {
            return c1Result.value;
        }
        RuntimeException runtimeException = new RuntimeException(c1CaughtException.f28137e);
        runtimeException.setStackTrace(concatStackTraces(c1CaughtException.f28137e.getStackTrace(), runtimeException.getStackTrace()));
        throw runtimeException;
    }

    public static boolean joinUninterruptibly(Thread thread, long j2) throws InterruptedException {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        boolean z2 = false;
        long jElapsedRealtime2 = j2;
        while (jElapsedRealtime2 > 0) {
            try {
                thread.join(jElapsedRealtime2);
                break;
            } catch (InterruptedException unused) {
                jElapsedRealtime2 = j2 - (SystemClock.elapsedRealtime() - jElapsedRealtime);
                z2 = true;
            }
        }
        if (z2) {
            Thread.currentThread().interrupt();
        }
        return !thread.isAlive();
    }

    public static boolean awaitUninterruptibly(CountDownLatch countDownLatch, long j2) throws InterruptedException {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        boolean zAwait = false;
        long jElapsedRealtime2 = j2;
        boolean z2 = false;
        while (true) {
            try {
                zAwait = countDownLatch.await(jElapsedRealtime2, TimeUnit.MILLISECONDS);
                break;
            } catch (InterruptedException unused) {
                jElapsedRealtime2 = j2 - (SystemClock.elapsedRealtime() - jElapsedRealtime);
                if (jElapsedRealtime2 <= 0) {
                    z2 = true;
                    break;
                }
                z2 = true;
            }
        }
        if (z2) {
            Thread.currentThread().interrupt();
        }
        return zAwait;
    }

    public static void joinUninterruptibly(final Thread thread) {
        executeUninterruptibly(new BlockingOperation() { // from class: org.wrtca.util.ThreadUtils.1
            @Override // org.wrtca.util.ThreadUtils.BlockingOperation
            public void run() throws InterruptedException {
                thread.join();
            }
        });
    }

    public static void invokeAtFrontUninterruptibly(Handler handler, final Runnable runnable) {
        invokeAtFrontUninterruptibly(handler, new Callable<Void>() { // from class: org.wrtca.util.ThreadUtils.4
            @Override // java.util.concurrent.Callable
            public Void call() {
                runnable.run();
                return null;
            }
        });
    }
}
