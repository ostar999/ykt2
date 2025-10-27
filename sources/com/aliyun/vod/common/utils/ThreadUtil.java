package com.aliyun.vod.common.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/* loaded from: classes2.dex */
public class ThreadUtil {
    public static <T> T exchange(Exchanger<T> exchanger, T t2) {
        try {
            return exchanger.exchange(t2);
        } catch (InterruptedException e2) {
            return (T) Assert.fail(e2);
        }
    }

    public static <T> T get(Future<T> future) {
        try {
            return future.get();
        } catch (InterruptedException e2) {
            return (T) Assert.fail(e2);
        } catch (ExecutionException e3) {
            return (T) Assert.fail(e3);
        }
    }

    public static void join(Thread thread) throws InterruptedException {
        try {
            thread.join();
        } catch (InterruptedException unused) {
            Assert.fail();
        }
    }

    public static <T> T take(BlockingQueue<T> blockingQueue) {
        try {
            return blockingQueue.take();
        } catch (InterruptedException e2) {
            return (T) Assert.fail(e2);
        }
    }

    public static void wait(Object obj) throws InterruptedException {
        try {
            obj.wait();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }

    public static ExecutionException waitForCompletion(Future<?> future) throws ExecutionException, InterruptedException {
        try {
            future.get();
            return null;
        } catch (InterruptedException unused) {
            Assert.fail();
            return null;
        } catch (ExecutionException e2) {
            return e2;
        }
    }

    public static <T> T exchange(Exchanger<T> exchanger) {
        return (T) exchange(exchanger, null);
    }

    public static boolean join(Thread thread, int i2) throws InterruptedException {
        try {
            thread.join(i2);
        } catch (InterruptedException unused) {
            Assert.fail();
        }
        return !thread.isAlive();
    }

    public static void wait(Object obj, long j2) throws InterruptedException {
        try {
            obj.wait(j2);
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }
}
