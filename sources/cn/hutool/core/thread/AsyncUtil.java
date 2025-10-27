package cn.hutool.core.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/* loaded from: classes.dex */
public class AsyncUtil {
    public static <T> T get(CompletableFuture<T> completableFuture) {
        try {
            return (T) completableFuture.get();
        } catch (InterruptedException | ExecutionException e2) {
            throw new ThreadException(e2);
        }
    }

    public static void waitAll(CompletableFuture<?>... completableFutureArr) throws ExecutionException, InterruptedException {
        try {
            CompletableFuture.allOf(completableFutureArr).get();
        } catch (InterruptedException | ExecutionException e2) {
            throw new ThreadException(e2);
        }
    }

    public static <T> T waitAny(CompletableFuture<?>... completableFutureArr) {
        try {
            return (T) CompletableFuture.anyOf(completableFutureArr).get();
        } catch (InterruptedException | ExecutionException e2) {
            throw new ThreadException(e2);
        }
    }
}
