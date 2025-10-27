package io.agora.rtc.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes8.dex */
public class ThreadUtils {

    /* renamed from: io.agora.rtc.utils.ThreadUtils$1CaughtException, reason: invalid class name */
    public class C1CaughtException {

        /* renamed from: e, reason: collision with root package name */
        Exception f27144e;
    }

    /* renamed from: io.agora.rtc.utils.ThreadUtils$1Result, reason: invalid class name */
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

    public static void awaitUninterruptibly(final CountDownLatch latch) {
        executeUninterruptibly(new BlockingOperation() { // from class: io.agora.rtc.utils.ThreadUtils.2
            @Override // io.agora.rtc.utils.ThreadUtils.BlockingOperation
            public void run() throws InterruptedException {
                latch.await();
            }
        });
    }

    public static void checkIsOnMainThread() {
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            throw new IllegalStateException("Not on main thread!");
        }
    }

    public static StackTraceElement[] concatStackTraces(StackTraceElement[] inner, StackTraceElement[] outer) {
        StackTraceElement[] stackTraceElementArr = new StackTraceElement[inner.length + outer.length];
        System.arraycopy(inner, 0, stackTraceElementArr, 0, inner.length);
        System.arraycopy(outer, 0, stackTraceElementArr, inner.length, outer.length);
        return stackTraceElementArr;
    }

    public static void executeUninterruptibly(BlockingOperation operation) {
        boolean z2 = false;
        while (true) {
            try {
                operation.run();
                break;
            } catch (InterruptedException unused) {
                z2 = true;
            }
        }
        if (z2) {
            Thread.currentThread().interrupt();
        }
    }

    public static <V> V invokeAtFrontUninterruptibly(final Handler handler, final Callable<V> callable) {
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
        handler.post(new Runnable() { // from class: io.agora.rtc.utils.ThreadUtils.4
            /* JADX WARN: Type inference failed for: r1v2, types: [V, java.lang.Object] */
            @Override // java.lang.Runnable
            public void run() {
                try {
                    c1Result.value = callable.call();
                } catch (Exception e3) {
                    c1CaughtException.f27144e = e3;
                }
                countDownLatch.countDown();
            }
        });
        awaitUninterruptibly(countDownLatch);
        if (c1CaughtException.f27144e == null) {
            return c1Result.value;
        }
        RuntimeException runtimeException = new RuntimeException(c1CaughtException.f27144e);
        runtimeException.setStackTrace(concatStackTraces(c1CaughtException.f27144e.getStackTrace(), runtimeException.getStackTrace()));
        throw runtimeException;
    }

    public static boolean joinUninterruptibly(final Thread thread, long timeoutMs) throws InterruptedException {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        boolean z2 = false;
        long jElapsedRealtime2 = timeoutMs;
        while (jElapsedRealtime2 > 0) {
            try {
                thread.join(jElapsedRealtime2);
                break;
            } catch (InterruptedException unused) {
                jElapsedRealtime2 = timeoutMs - (SystemClock.elapsedRealtime() - jElapsedRealtime);
                z2 = true;
            }
        }
        if (z2) {
            Thread.currentThread().interrupt();
        }
        return !thread.isAlive();
    }

    public static void waitUninterruptibly(final Object object) {
        executeUninterruptibly(new BlockingOperation() { // from class: io.agora.rtc.utils.ThreadUtils.3
            @Override // io.agora.rtc.utils.ThreadUtils.BlockingOperation
            public void run() throws InterruptedException {
                object.wait();
            }
        });
    }

    public static boolean awaitUninterruptibly(CountDownLatch barrier, long timeoutMs) throws InterruptedException {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        boolean zAwait = false;
        long jElapsedRealtime2 = timeoutMs;
        boolean z2 = false;
        while (true) {
            try {
                zAwait = barrier.await(jElapsedRealtime2, TimeUnit.MILLISECONDS);
                break;
            } catch (InterruptedException unused) {
                jElapsedRealtime2 = timeoutMs - (SystemClock.elapsedRealtime() - jElapsedRealtime);
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
        executeUninterruptibly(new BlockingOperation() { // from class: io.agora.rtc.utils.ThreadUtils.1
            @Override // io.agora.rtc.utils.ThreadUtils.BlockingOperation
            public void run() throws InterruptedException {
                thread.join();
            }
        });
    }

    public static void invokeAtFrontUninterruptibly(final Handler handler, final Runnable runner) {
        invokeAtFrontUninterruptibly(handler, new Callable<Void>() { // from class: io.agora.rtc.utils.ThreadUtils.5
            @Override // java.util.concurrent.Callable
            public Void call() {
                runner.run();
                return null;
            }
        });
    }
}
