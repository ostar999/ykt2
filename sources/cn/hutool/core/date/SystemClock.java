package cn.hutool.core.date;

import java.sql.Timestamp;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class SystemClock {
    private volatile long now = System.currentTimeMillis();
    private final long period;

    public static class InstanceHolder {
        public static final SystemClock INSTANCE = new SystemClock(1);

        private InstanceHolder() {
        }
    }

    public SystemClock(long j2) {
        this.period = j2;
        scheduleClockUpdating();
    }

    private long currentTimeMillis() {
        return this.now;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Thread lambda$scheduleClockUpdating$0(Runnable runnable) {
        Thread thread = new Thread(runnable, "System Clock");
        thread.setDaemon(true);
        return thread;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleClockUpdating$1() {
        this.now = System.currentTimeMillis();
    }

    public static long now() {
        return InstanceHolder.INSTANCE.currentTimeMillis();
    }

    public static String nowDate() {
        return new Timestamp(InstanceHolder.INSTANCE.currentTimeMillis()).toString();
    }

    private void scheduleClockUpdating() {
        ScheduledExecutorService scheduledExecutorServiceNewSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() { // from class: cn.hutool.core.date.i1
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                return SystemClock.lambda$scheduleClockUpdating$0(runnable);
            }
        });
        Runnable runnable = new Runnable() { // from class: cn.hutool.core.date.j1
            @Override // java.lang.Runnable
            public final void run() {
                this.f2457c.lambda$scheduleClockUpdating$1();
            }
        };
        long j2 = this.period;
        scheduledExecutorServiceNewSingleThreadScheduledExecutor.scheduleAtFixedRate(runnable, j2, j2, TimeUnit.MILLISECONDS);
    }
}
