package cn.hutool.core.thread;

import cn.hutool.core.util.RuntimeUtil;
import java.lang.Thread;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class ThreadUtil {
    public static ConcurrencyTester concurrencyTest(int i2, Runnable runnable) {
        return new ConcurrencyTester(i2).test(runnable);
    }

    public static ScheduledThreadPoolExecutor createScheduledExecutor(int i2) {
        return new ScheduledThreadPoolExecutor(i2);
    }

    public static ThreadFactory createThreadFactory(String str) {
        return ThreadFactoryBuilder.create().setNamePrefix(str).build();
    }

    public static ThreadFactoryBuilder createThreadFactoryBuilder() {
        return ThreadFactoryBuilder.create();
    }

    public static <T> ThreadLocal<T> createThreadLocal(boolean z2) {
        return z2 ? new InheritableThreadLocal() : new ThreadLocal<>();
    }

    public static ThreadGroup currentThreadGroup() {
        SecurityManager securityManager = System.getSecurityManager();
        return securityManager != null ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
    }

    public static Runnable execAsync(Runnable runnable, boolean z2) {
        Thread thread = new Thread(runnable);
        thread.setDaemon(z2);
        thread.start();
        return runnable;
    }

    public static void execute(Runnable runnable) {
        GlobalThreadPool.execute(runnable);
    }

    public static Thread getMainThread() {
        for (Thread thread : getThreads()) {
            if (thread.getId() == 1) {
                return thread;
            }
        }
        return null;
    }

    public static StackTraceElement[] getStackTrace() {
        return Thread.currentThread().getStackTrace();
    }

    public static StackTraceElement getStackTraceElement(int i2) {
        StackTraceElement[] stackTrace = getStackTrace();
        if (i2 < 0) {
            i2 += stackTrace.length;
        }
        return stackTrace[i2];
    }

    public static Thread[] getThreads() {
        return getThreads(Thread.currentThread().getThreadGroup().getParent());
    }

    public static void interrupt(Thread thread, boolean z2) throws InterruptedException {
        if (thread == null || thread.isInterrupted()) {
            return;
        }
        thread.interrupt();
        if (z2) {
            waitForDie(thread);
        }
    }

    public static <T> CompletionService<T> newCompletionService() {
        return new ExecutorCompletionService(GlobalThreadPool.getExecutor());
    }

    public static CountDownLatch newCountDownLatch(int i2) {
        return new CountDownLatch(i2);
    }

    public static ExecutorService newExecutor(int i2) {
        ExecutorBuilder executorBuilderCreate = ExecutorBuilder.create();
        if (i2 > 0) {
            executorBuilderCreate.setCorePoolSize(i2);
        }
        return executorBuilderCreate.build();
    }

    public static ThreadPoolExecutor newExecutorByBlockingCoefficient(float f2) {
        if (f2 >= 1.0f || f2 < 0.0f) {
            throw new IllegalArgumentException("[blockingCoefficient] must between 0 and 1, or equals 0.");
        }
        int processorCount = (int) (RuntimeUtil.getProcessorCount() / (1.0f - f2));
        return ExecutorBuilder.create().setCorePoolSize(processorCount).setMaxPoolSize(processorCount).setKeepAliveTime(0L).build();
    }

    public static ExecutorService newFixedExecutor(int i2, String str, boolean z2) {
        return newFixedExecutor(i2, 1024, str, z2);
    }

    public static ThreadFactory newNamedThreadFactory(String str, boolean z2) {
        return new NamedThreadFactory(str, z2);
    }

    public static ExecutorService newSingleExecutor() {
        return ExecutorBuilder.create().setCorePoolSize(1).setMaxPoolSize(1).setKeepAliveTime(0L).buildFinalizable();
    }

    public static Thread newThread(Runnable runnable, String str) {
        Thread threadNewThread = newThread(runnable, str, false);
        if (threadNewThread.getPriority() != 5) {
            threadNewThread.setPriority(5);
        }
        return threadNewThread;
    }

    public static boolean safeSleep(Number number) {
        if (number == null) {
            return true;
        }
        return safeSleep(number.longValue());
    }

    public static ScheduledThreadPoolExecutor schedule(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor, Runnable runnable, long j2, long j3, boolean z2) {
        return schedule(scheduledThreadPoolExecutor, runnable, j2, j3, TimeUnit.MILLISECONDS, z2);
    }

    public static boolean sleep(Number number, TimeUnit timeUnit) throws InterruptedException {
        try {
            timeUnit.sleep(number.longValue());
            return true;
        } catch (InterruptedException unused) {
            return false;
        }
    }

    public static void sync(Object obj) {
        synchronized (obj) {
            try {
                obj.wait();
            } catch (InterruptedException unused) {
            }
        }
    }

    public static void waitForDie() throws InterruptedException {
        waitForDie(Thread.currentThread());
    }

    public static Thread[] getThreads(ThreadGroup threadGroup) {
        Thread[] threadArr = new Thread[threadGroup.activeCount() * 2];
        int iEnumerate = threadGroup.enumerate(threadArr);
        Thread[] threadArr2 = new Thread[iEnumerate];
        System.arraycopy(threadArr, 0, threadArr2, 0, iEnumerate);
        return threadArr2;
    }

    public static <T> CompletionService<T> newCompletionService(ExecutorService executorService) {
        return new ExecutorCompletionService(executorService);
    }

    public static ExecutorService newFixedExecutor(int i2, int i3, String str, boolean z2) {
        return newFixedExecutor(i2, i3, str, (z2 ? RejectPolicy.BLOCK : RejectPolicy.ABORT).getValue());
    }

    public static ThreadFactory newNamedThreadFactory(String str, ThreadGroup threadGroup, boolean z2) {
        return new NamedThreadFactory(str, threadGroup, z2);
    }

    public static boolean safeSleep(long j2) {
        long j3 = 0;
        while (j3 >= 0 && j3 < j2) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (!sleep(j2 - j3)) {
                return false;
            }
            long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
            if (jCurrentTimeMillis2 <= 0) {
                return true;
            }
            j3 += jCurrentTimeMillis2;
        }
        return true;
    }

    public static ScheduledThreadPoolExecutor schedule(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor, Runnable runnable, long j2, long j3, TimeUnit timeUnit, boolean z2) {
        if (scheduledThreadPoolExecutor == null) {
            scheduledThreadPoolExecutor = createScheduledExecutor(2);
        }
        if (z2) {
            scheduledThreadPoolExecutor.scheduleAtFixedRate(runnable, j2, j3, timeUnit);
        } else {
            scheduledThreadPoolExecutor.scheduleWithFixedDelay(runnable, j2, j3, timeUnit);
        }
        return scheduledThreadPoolExecutor;
    }

    public static boolean sleep(Number number) {
        if (number == null) {
            return true;
        }
        return sleep(number.longValue());
    }

    public static void waitForDie(Thread thread) throws InterruptedException {
        if (thread == null) {
            return;
        }
        boolean z2 = false;
        do {
            try {
                thread.join();
                z2 = true;
            } catch (InterruptedException unused) {
            }
        } while (!z2);
    }

    public static <T> ThreadLocal<T> createThreadLocal(Supplier<? extends T> supplier) {
        return ThreadLocal.withInitial(supplier);
    }

    public static ThreadFactory newNamedThreadFactory(String str, ThreadGroup threadGroup, boolean z2, Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        return new NamedThreadFactory(str, threadGroup, z2, uncaughtExceptionHandler);
    }

    public static boolean sleep(long j2) {
        if (j2 <= 0) {
            return true;
        }
        try {
            Thread.sleep(j2);
            return true;
        } catch (InterruptedException unused) {
            return false;
        }
    }

    public static <T> Future<T> execAsync(Callable<T> callable) {
        return GlobalThreadPool.submit(callable);
    }

    public static ExecutorService newExecutor() {
        return ExecutorBuilder.create().useSynchronousQueue().build();
    }

    public static Thread newThread(Runnable runnable, String str, boolean z2) {
        Thread thread = new Thread(null, runnable, str);
        thread.setDaemon(z2);
        return thread;
    }

    public static Future<?> execAsync(Runnable runnable) {
        return GlobalThreadPool.submit(runnable);
    }

    public static ThreadPoolExecutor newExecutor(int i2, int i3) {
        return ExecutorBuilder.create().setCorePoolSize(i2).setMaxPoolSize(i3).build();
    }

    public static ExecutorService newFixedExecutor(int i2, int i3, String str, RejectedExecutionHandler rejectedExecutionHandler) {
        return ExecutorBuilder.create().setCorePoolSize(i2).setMaxPoolSize(i2).setWorkQueue(new LinkedBlockingQueue(i3)).setThreadFactory(createThreadFactory(str)).setHandler(rejectedExecutionHandler).build();
    }

    public static ExecutorService newExecutor(int i2, int i3, int i4) {
        return ExecutorBuilder.create().setCorePoolSize(i2).setMaxPoolSize(i3).setWorkQueue(new LinkedBlockingQueue(i4)).build();
    }
}
