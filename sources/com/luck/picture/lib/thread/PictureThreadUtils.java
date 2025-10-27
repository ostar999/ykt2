package com.luck.picture.lib.thread;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.CallSuper;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import com.umeng.analytics.pro.am;
import java.lang.Thread;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes4.dex */
public final class PictureThreadUtils {
    private static final byte TYPE_CACHED = -2;
    private static final byte TYPE_CPU = -8;
    private static final byte TYPE_IO = -4;
    private static final byte TYPE_SINGLE = -1;
    private static Executor sDeliver;
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());
    private static final Map<Integer, Map<Integer, ExecutorService>> TYPE_PRIORITY_POOLS = new HashMap();
    private static final Map<Task, ExecutorService> TASK_POOL_MAP = new ConcurrentHashMap();
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final Timer TIMER = new Timer();

    public static final class LinkedBlockingQueue4Util extends LinkedBlockingQueue<Runnable> {
        private int mCapacity;
        private volatile ThreadPoolExecutor4Util mPool;

        public LinkedBlockingQueue4Util() {
            this.mCapacity = Integer.MAX_VALUE;
        }

        @Override // java.util.concurrent.LinkedBlockingQueue, java.util.Queue, java.util.concurrent.BlockingQueue
        public boolean offer(@NonNull Runnable runnable) {
            if (this.mCapacity > size() || this.mPool == null || this.mPool.getPoolSize() >= this.mPool.getMaximumPoolSize()) {
                return super.offer((LinkedBlockingQueue4Util) runnable);
            }
            return false;
        }

        public LinkedBlockingQueue4Util(boolean z2) {
            this.mCapacity = Integer.MAX_VALUE;
            if (z2) {
                this.mCapacity = 0;
            }
        }

        public LinkedBlockingQueue4Util(int i2) {
            this.mCapacity = i2;
        }
    }

    public static abstract class SimpleTask<T> extends Task<T> {
        @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
        public void onCancel() {
            Log.e("ThreadUtils", "onCancel: " + Thread.currentThread());
        }

        @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
        public void onFail(Throwable th) {
            Log.e("ThreadUtils", "onFail: ", th);
        }
    }

    public static abstract class Task<T> implements Runnable {
        private static final int CANCELLED = 4;
        private static final int COMPLETING = 3;
        private static final int EXCEPTIONAL = 2;
        private static final int INTERRUPTED = 5;
        private static final int NEW = 0;
        private static final int RUNNING = 1;
        private static final int TIMEOUT = 6;
        private Executor deliver;
        private volatile boolean isSchedule;
        private OnTimeoutListener mTimeoutListener;
        private long mTimeoutMillis;
        private Timer mTimer;
        private volatile Thread runner;
        private final AtomicInteger state = new AtomicInteger(0);

        public interface OnTimeoutListener {
            void onTimeout();
        }

        private Executor getDeliver() {
            Executor executor = this.deliver;
            return executor == null ? PictureThreadUtils.getGlobalDeliver() : executor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSchedule(boolean z2) {
            this.isSchedule = z2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void timeout() {
            synchronized (this.state) {
                if (this.state.get() > 1) {
                    return;
                }
                this.state.set(6);
                if (this.runner != null) {
                    this.runner.interrupt();
                }
            }
        }

        public void cancel() {
            cancel(true);
        }

        public abstract T doInBackground() throws Throwable;

        public boolean isCanceled() {
            return this.state.get() >= 4;
        }

        public boolean isDone() {
            return this.state.get() > 1;
        }

        public abstract void onCancel();

        @CallSuper
        public void onDone() {
            PictureThreadUtils.TASK_POOL_MAP.remove(this);
            Timer timer = this.mTimer;
            if (timer != null) {
                timer.cancel();
                this.mTimer = null;
                this.mTimeoutListener = null;
            }
        }

        public abstract void onFail(Throwable th);

        public abstract void onSuccess(T t2);

        @Override // java.lang.Runnable
        public void run() {
            if (this.isSchedule) {
                if (this.runner == null) {
                    if (!this.state.compareAndSet(0, 1)) {
                        return;
                    }
                    this.runner = Thread.currentThread();
                    if (this.mTimeoutListener != null) {
                        Log.w("ThreadUtils", "Scheduled task doesn't support timeout.");
                    }
                } else if (this.state.get() != 1) {
                    return;
                }
            } else {
                if (!this.state.compareAndSet(0, 1)) {
                    return;
                }
                this.runner = Thread.currentThread();
                if (this.mTimeoutListener != null) {
                    Timer timer = new Timer();
                    this.mTimer = timer;
                    timer.schedule(new TimerTask() { // from class: com.luck.picture.lib.thread.PictureThreadUtils.Task.1
                        @Override // java.util.TimerTask, java.lang.Runnable
                        public void run() {
                            if (Task.this.isDone() || Task.this.mTimeoutListener == null) {
                                return;
                            }
                            Task.this.timeout();
                            Task.this.mTimeoutListener.onTimeout();
                            Task.this.onDone();
                        }
                    }, this.mTimeoutMillis);
                }
            }
            try {
                final T tDoInBackground = doInBackground();
                if (this.isSchedule) {
                    if (this.state.get() != 1) {
                        return;
                    }
                    getDeliver().execute(new Runnable() { // from class: com.luck.picture.lib.thread.PictureThreadUtils.Task.2
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // java.lang.Runnable
                        public void run() {
                            Task.this.onSuccess(tDoInBackground);
                        }
                    });
                } else if (this.state.compareAndSet(1, 3)) {
                    getDeliver().execute(new Runnable() { // from class: com.luck.picture.lib.thread.PictureThreadUtils.Task.3
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // java.lang.Runnable
                        public void run() {
                            Task.this.onSuccess(tDoInBackground);
                            Task.this.onDone();
                        }
                    });
                }
            } catch (InterruptedException unused) {
                this.state.compareAndSet(4, 5);
            } catch (Throwable th) {
                if (this.state.compareAndSet(1, 2)) {
                    getDeliver().execute(new Runnable() { // from class: com.luck.picture.lib.thread.PictureThreadUtils.Task.4
                        @Override // java.lang.Runnable
                        public void run() {
                            Task.this.onFail(th);
                            Task.this.onDone();
                        }
                    });
                }
            }
        }

        public Task<T> setDeliver(Executor executor) {
            this.deliver = executor;
            return this;
        }

        public Task<T> setTimeout(long j2, OnTimeoutListener onTimeoutListener) {
            this.mTimeoutMillis = j2;
            this.mTimeoutListener = onTimeoutListener;
            return this;
        }

        public void cancel(boolean z2) {
            synchronized (this.state) {
                if (this.state.get() > 1) {
                    return;
                }
                this.state.set(4);
                if (z2 && this.runner != null) {
                    this.runner.interrupt();
                }
                getDeliver().execute(new Runnable() { // from class: com.luck.picture.lib.thread.PictureThreadUtils.Task.5
                    @Override // java.lang.Runnable
                    public void run() {
                        Task.this.onCancel();
                        Task.this.onDone();
                    }
                });
            }
        }
    }

    public static final class ThreadPoolExecutor4Util extends ThreadPoolExecutor {
        private final AtomicInteger mSubmittedCount;
        private final LinkedBlockingQueue4Util mWorkQueue;

        public ThreadPoolExecutor4Util(int i2, int i3, long j2, TimeUnit timeUnit, LinkedBlockingQueue4Util linkedBlockingQueue4Util, ThreadFactory threadFactory) {
            super(i2, i3, j2, timeUnit, linkedBlockingQueue4Util, threadFactory);
            this.mSubmittedCount = new AtomicInteger();
            linkedBlockingQueue4Util.mPool = this;
            this.mWorkQueue = linkedBlockingQueue4Util;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static ExecutorService createPool(int i2, int i3) {
            if (i2 == -8) {
                return new ThreadPoolExecutor4Util(PictureThreadUtils.CPU_COUNT + 1, (PictureThreadUtils.CPU_COUNT * 2) + 1, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue4Util(true), new UtilsThreadFactory(am.f22460w, i3));
            }
            if (i2 == -4) {
                return new ThreadPoolExecutor4Util(5, 10, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue4Util(100), new UtilsThreadFactory("io", i3));
            }
            if (i2 == -2) {
                return new ThreadPoolExecutor4Util(0, 128, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue4Util(true), new UtilsThreadFactory("cached", i3));
            }
            if (i2 == -1) {
                return new ThreadPoolExecutor4Util(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue4Util(), new UtilsThreadFactory("single", i3));
            }
            return new ThreadPoolExecutor4Util(i2, i2, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue4Util(), new UtilsThreadFactory("fixed(" + i2 + ")", i3));
        }

        private int getSubmittedCount() {
            return this.mSubmittedCount.get();
        }

        @Override // java.util.concurrent.ThreadPoolExecutor
        public void afterExecute(Runnable runnable, Throwable th) {
            this.mSubmittedCount.decrementAndGet();
            super.afterExecute(runnable, th);
        }

        @Override // java.util.concurrent.ThreadPoolExecutor, java.util.concurrent.Executor
        public void execute(@NonNull Runnable runnable) {
            if (isShutdown()) {
                return;
            }
            this.mSubmittedCount.incrementAndGet();
            try {
                super.execute(runnable);
            } catch (RejectedExecutionException unused) {
                Log.e("ThreadUtils", "This will not happen!");
                this.mWorkQueue.offer(runnable);
            } catch (Throwable unused2) {
                this.mSubmittedCount.decrementAndGet();
            }
        }
    }

    public static final class UtilsThreadFactory extends AtomicLong implements ThreadFactory {
        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
        private static final long serialVersionUID = -9209200509960368598L;
        private final boolean isDaemon;
        private final String namePrefix;
        private final int priority;

        public UtilsThreadFactory(String str, int i2) {
            this(str, i2, false);
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(@NonNull Runnable runnable) {
            Thread thread = new Thread(runnable, this.namePrefix + getAndIncrement()) { // from class: com.luck.picture.lib.thread.PictureThreadUtils.UtilsThreadFactory.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    try {
                        super.run();
                    } catch (Throwable th) {
                        Log.e("ThreadUtils", "Request threw uncaught throwable", th);
                    }
                }
            };
            thread.setDaemon(this.isDaemon);
            thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { // from class: com.luck.picture.lib.thread.PictureThreadUtils.UtilsThreadFactory.2
                @Override // java.lang.Thread.UncaughtExceptionHandler
                public void uncaughtException(Thread thread2, Throwable th) {
                    System.out.println(th);
                }
            });
            thread.setPriority(this.priority);
            return thread;
        }

        public UtilsThreadFactory(String str, int i2, boolean z2) {
            this.namePrefix = str + "-pool-" + POOL_NUMBER.getAndIncrement() + "-thread-";
            this.priority = i2;
            this.isDaemon = z2;
        }
    }

    public static void cancel(Task task) {
        if (task == null) {
            return;
        }
        task.cancel();
    }

    private static <T> void execute(ExecutorService executorService, Task<T> task) {
        execute(executorService, task, 0L, 0L, null);
    }

    public static <T> void executeByIo(Task<T> task) {
        execute(getPoolByTypeAndPriority(-4), task);
    }

    public static <T> void executeBySingle(Task<T> task) {
        execute(getPoolByTypeAndPriority(-1), task);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Executor getGlobalDeliver() {
        if (sDeliver == null) {
            sDeliver = new Executor() { // from class: com.luck.picture.lib.thread.PictureThreadUtils.3
                @Override // java.util.concurrent.Executor
                public void execute(@NonNull Runnable runnable) {
                    PictureThreadUtils.runOnUiThread(runnable);
                }
            };
        }
        return sDeliver;
    }

    public static ExecutorService getIoPool() {
        return getPoolByTypeAndPriority(-4);
    }

    private static ExecutorService getPoolByTypeAndPriority(int i2) {
        return getPoolByTypeAndPriority(i2, 5);
    }

    public static ExecutorService getSinglePool() {
        return getPoolByTypeAndPriority(-1);
    }

    public static boolean isInUiThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    public static void runOnUiThread(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            HANDLER.post(runnable);
        }
    }

    public static void cancel(Task... taskArr) {
        if (taskArr == null || taskArr.length == 0) {
            return;
        }
        for (Task task : taskArr) {
            if (task != null) {
                task.cancel();
            }
        }
    }

    private static <T> void execute(final ExecutorService executorService, final Task<T> task, long j2, long j3, TimeUnit timeUnit) {
        Map<Task, ExecutorService> map = TASK_POOL_MAP;
        synchronized (map) {
            if (map.get(task) != null) {
                Log.e("ThreadUtils", "Task can only be executed once.");
                return;
            }
            map.put(task, executorService);
            if (j3 != 0) {
                task.setSchedule(true);
                TIMER.scheduleAtFixedRate(new TimerTask() { // from class: com.luck.picture.lib.thread.PictureThreadUtils.2
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        executorService.execute(task);
                    }
                }, timeUnit.toMillis(j2), timeUnit.toMillis(j3));
            } else if (j2 == 0) {
                executorService.execute(task);
            } else {
                TIMER.schedule(new TimerTask() { // from class: com.luck.picture.lib.thread.PictureThreadUtils.1
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        executorService.execute(task);
                    }
                }, timeUnit.toMillis(j2));
            }
        }
    }

    public static <T> void executeByIo(Task<T> task, @IntRange(from = 1, to = 10) int i2) {
        execute(getPoolByTypeAndPriority(-4, i2), task);
    }

    public static <T> void executeBySingle(Task<T> task, @IntRange(from = 1, to = 10) int i2) {
        execute(getPoolByTypeAndPriority(-1, i2), task);
    }

    private static ExecutorService getPoolByTypeAndPriority(int i2, int i3) {
        ExecutorService executorServiceCreatePool;
        Map<Integer, Map<Integer, ExecutorService>> map = TYPE_PRIORITY_POOLS;
        synchronized (map) {
            Map<Integer, ExecutorService> map2 = map.get(Integer.valueOf(i2));
            if (map2 == null) {
                ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
                executorServiceCreatePool = ThreadPoolExecutor4Util.createPool(i2, i3);
                concurrentHashMap.put(Integer.valueOf(i3), executorServiceCreatePool);
                map.put(Integer.valueOf(i2), concurrentHashMap);
            } else {
                executorServiceCreatePool = map2.get(Integer.valueOf(i3));
                if (executorServiceCreatePool == null) {
                    executorServiceCreatePool = ThreadPoolExecutor4Util.createPool(i2, i3);
                    map2.put(Integer.valueOf(i3), executorServiceCreatePool);
                }
            }
        }
        return executorServiceCreatePool;
    }

    public static ExecutorService getSinglePool(@IntRange(from = 1, to = 10) int i2) {
        return getPoolByTypeAndPriority(-1, i2);
    }

    public static class SyncValue<T> {
        private T mValue;
        private CountDownLatch mLatch = new CountDownLatch(1);
        private AtomicBoolean mFlag = new AtomicBoolean();

        public T getValue() throws InterruptedException {
            if (!this.mFlag.get()) {
                try {
                    this.mLatch.await();
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
            return this.mValue;
        }

        public void setValue(T t2) {
            if (this.mFlag.compareAndSet(false, true)) {
                this.mValue = t2;
                this.mLatch.countDown();
            }
        }

        public T getValue(long j2, TimeUnit timeUnit, T t2) throws InterruptedException {
            if (!this.mFlag.get()) {
                try {
                    this.mLatch.await(j2, timeUnit);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                    return t2;
                }
            }
            return this.mValue;
        }
    }

    public static void cancel(List<Task> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        for (Task task : list) {
            if (task != null) {
                task.cancel();
            }
        }
    }

    public static void cancel(ExecutorService executorService) {
        if (executorService instanceof ThreadPoolExecutor4Util) {
            for (Map.Entry<Task, ExecutorService> entry : TASK_POOL_MAP.entrySet()) {
                if (entry.getValue() == executorService) {
                    cancel(entry.getKey());
                }
            }
            return;
        }
        Log.e("ThreadUtils", "The executorService is not ThreadUtils's pool.");
    }
}
