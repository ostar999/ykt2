package cn.hutool.core.thread;

import cn.hutool.core.exceptions.UtilException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/* loaded from: classes.dex */
public class GlobalThreadPool {
    private static ExecutorService executor;

    static {
        init();
    }

    private GlobalThreadPool() {
    }

    public static void execute(Runnable runnable) {
        try {
            executor.execute(runnable);
        } catch (Exception e2) {
            throw new UtilException(e2, "Exception when running task!", new Object[0]);
        }
    }

    public static ExecutorService getExecutor() {
        return executor;
    }

    public static synchronized void init() {
        ExecutorService executorService = executor;
        if (executorService != null) {
            executorService.shutdownNow();
        }
        executor = ExecutorBuilder.create().useSynchronousQueue().build();
    }

    public static synchronized void shutdown(boolean z2) {
        ExecutorService executorService = executor;
        if (executorService != null) {
            if (z2) {
                executorService.shutdownNow();
            } else {
                executorService.shutdown();
            }
        }
    }

    public static <T> Future<T> submit(Callable<T> callable) {
        return executor.submit(callable);
    }

    public static Future<?> submit(Runnable runnable) {
        return executor.submit(runnable);
    }
}
