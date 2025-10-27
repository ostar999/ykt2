package cn.hutool.core.thread;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class BlockPolicy implements RejectedExecutionHandler {
    private final Consumer<Runnable> handlerwhenshutdown;

    public BlockPolicy(Consumer<Runnable> consumer) {
        this.handlerwhenshutdown = consumer;
    }

    @Override // java.util.concurrent.RejectedExecutionHandler
    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) throws InterruptedException {
        if (threadPoolExecutor.isShutdown()) {
            Consumer<Runnable> consumer = this.handlerwhenshutdown;
            if (consumer != null) {
                consumer.accept(runnable);
                return;
            }
            return;
        }
        try {
            threadPoolExecutor.getQueue().put(runnable);
        } catch (InterruptedException unused) {
            throw new RejectedExecutionException("Task " + runnable + " rejected from " + threadPoolExecutor);
        }
    }

    public BlockPolicy() {
        this(null);
    }
}
