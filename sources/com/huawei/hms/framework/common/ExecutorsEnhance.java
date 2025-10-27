package com.huawei.hms.framework.common;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes4.dex */
public class ExecutorsEnhance {

    public static class DelegatedExecutorService extends AbstractExecutorService {
        private final ExecutorService executorService;

        public DelegatedExecutorService(ExecutorService executorService) {
            this.executorService = executorService;
        }

        @Override // java.util.concurrent.ExecutorService
        public boolean awaitTermination(long j2, TimeUnit timeUnit) throws InterruptedException {
            return this.executorService.awaitTermination(j2, timeUnit);
        }

        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            this.executorService.execute(runnable);
        }

        @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection, long j2, TimeUnit timeUnit) throws InterruptedException {
            return this.executorService.invokeAll(collection, j2, timeUnit);
        }

        @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
        public <T> T invokeAny(Collection<? extends Callable<T>> collection, long j2, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
            return (T) this.executorService.invokeAny(collection, j2, timeUnit);
        }

        @Override // java.util.concurrent.ExecutorService
        public boolean isShutdown() {
            return this.executorService.isShutdown();
        }

        @Override // java.util.concurrent.ExecutorService
        public boolean isTerminated() {
            return this.executorService.isTerminated();
        }

        @Override // java.util.concurrent.ExecutorService
        public void shutdown() {
            this.executorService.shutdown();
        }

        @Override // java.util.concurrent.ExecutorService
        public List<Runnable> shutdownNow() {
            return this.executorService.shutdownNow();
        }

        @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
        public <T> Future<T> submit(Runnable runnable, T t2) {
            return this.executorService.submit(runnable, t2);
        }

        @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection) throws InterruptedException {
            return this.executorService.invokeAll(collection);
        }

        @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
        public <T> T invokeAny(Collection<? extends Callable<T>> collection) throws ExecutionException, InterruptedException {
            return (T) this.executorService.invokeAny(collection);
        }

        @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
        public <T> Future<T> submit(Callable<T> callable) {
            return this.executorService.submit(callable);
        }

        @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
        public Future<?> submit(Runnable runnable) {
            return this.executorService.submit(runnable);
        }
    }

    public static class FinalizableDelegatedExecutorService extends DelegatedExecutorService {
        public FinalizableDelegatedExecutorService(ExecutorService executorService) {
            super(executorService);
        }

        public void finalize() {
            super.shutdown();
        }
    }

    public static ExecutorService newSingleThreadExecutor(ThreadFactory threadFactory) {
        return new FinalizableDelegatedExecutorService(new ThreadPoolExcutorEnhance(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), threadFactory));
    }
}
