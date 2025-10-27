package kotlinx.coroutines;

import cn.hutool.core.text.CharPool;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0007\u001a\u0010\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0005H\u0007¨\u0006\u0007"}, d2 = {"newFixedThreadPoolContext", "Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", "nThreads", "", "name", "", "newSingleThreadContext", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class ThreadPoolDispatcherKt {
    @DelicateCoroutinesApi
    @NotNull
    public static final ExecutorCoroutineDispatcher newFixedThreadPoolContext(final int i2, @NotNull final String str) {
        if (i2 >= 1) {
            final AtomicInteger atomicInteger = new AtomicInteger();
            return ExecutorsKt.from((ExecutorService) Executors.newScheduledThreadPool(i2, new ThreadFactory() { // from class: kotlinx.coroutines.a
                @Override // java.util.concurrent.ThreadFactory
                public final Thread newThread(Runnable runnable) {
                    return ThreadPoolDispatcherKt.m2307newFixedThreadPoolContext$lambda1(i2, str, atomicInteger, runnable);
                }
            }));
        }
        throw new IllegalArgumentException(("Expected at least one thread, but " + i2 + " specified").toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: newFixedThreadPoolContext$lambda-1, reason: not valid java name */
    public static final Thread m2307newFixedThreadPoolContext$lambda1(int i2, String str, AtomicInteger atomicInteger, Runnable runnable) {
        if (i2 != 1) {
            str = str + CharPool.DASHED + atomicInteger.incrementAndGet();
        }
        Thread thread = new Thread(runnable, str);
        thread.setDaemon(true);
        return thread;
    }

    @DelicateCoroutinesApi
    @NotNull
    public static final ExecutorCoroutineDispatcher newSingleThreadContext(@NotNull String str) {
        return newFixedThreadPoolContext(1, str);
    }
}
