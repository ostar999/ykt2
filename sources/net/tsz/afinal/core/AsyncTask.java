package net.tsz.afinal.core;

import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes9.dex */
public abstract class AsyncTask<Params, Progress, Result> {
    private static final int CORE_POOL_SIZE = 5;
    public static final Executor DUAL_THREAD_EXECUTOR;
    private static final int KEEP_ALIVE = 1;
    private static final String LOG_TAG = "AsyncTask";
    private static final int MAXIMUM_POOL_SIZE = 128;
    private static final int MESSAGE_POST_PROGRESS = 2;
    private static final int MESSAGE_POST_RESULT = 1;
    public static final Executor SERIAL_EXECUTOR;
    public static final Executor THREAD_POOL_EXECUTOR;
    private static volatile Executor sDefaultExecutor;
    private static final InternalHandler sHandler;
    private static final BlockingQueue<Runnable> sPoolWorkQueue;
    private static final ThreadFactory sThreadFactory;
    private final FutureTask<Result> mFuture;
    private final WorkerRunnable<Params, Result> mWorker;
    private volatile Status mStatus = Status.PENDING;
    private final AtomicBoolean mCancelled = new AtomicBoolean();
    private final AtomicBoolean mTaskInvoked = new AtomicBoolean();

    /* renamed from: net.tsz.afinal.core.AsyncTask$4, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$net$tsz$afinal$core$AsyncTask$Status;

        static {
            int[] iArr = new int[Status.values().length];
            $SwitchMap$net$tsz$afinal$core$AsyncTask$Status = iArr;
            try {
                iArr[Status.RUNNING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$tsz$afinal$core$AsyncTask$Status[Status.FINISHED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static class AsyncTaskResult<Data> {
        final Data[] mData;
        final AsyncTask mTask;

        public AsyncTaskResult(AsyncTask asyncTask, Data... dataArr) {
            this.mTask = asyncTask;
            this.mData = dataArr;
        }
    }

    public static class InternalHandler extends Handler {
        private InternalHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            AsyncTaskResult asyncTaskResult = (AsyncTaskResult) message.obj;
            int i2 = message.what;
            if (i2 == 1) {
                asyncTaskResult.mTask.finish(asyncTaskResult.mData[0]);
            } else {
                if (i2 != 2) {
                    return;
                }
                asyncTaskResult.mTask.onProgressUpdate(asyncTaskResult.mData);
            }
        }
    }

    public static class SerialExecutor implements Executor {
        Runnable mActive;
        final ArrayDeque<Runnable> mTasks;

        private SerialExecutor() {
            this.mTasks = new ArrayDeque<>();
        }

        @Override // java.util.concurrent.Executor
        public synchronized void execute(final Runnable runnable) {
            this.mTasks.offer(new Runnable() { // from class: net.tsz.afinal.core.AsyncTask.SerialExecutor.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        runnable.run();
                    } finally {
                        SerialExecutor.this.scheduleNext();
                    }
                }
            });
            if (this.mActive == null) {
                scheduleNext();
            }
        }

        public synchronized void scheduleNext() {
            Runnable runnablePoll = this.mTasks.poll();
            this.mActive = runnablePoll;
            if (runnablePoll != null) {
                AsyncTask.THREAD_POOL_EXECUTOR.execute(runnablePoll);
            }
        }
    }

    public enum Status {
        PENDING,
        RUNNING,
        FINISHED
    }

    public static abstract class WorkerRunnable<Params, Result> implements Callable<Result> {
        Params[] mParams;

        private WorkerRunnable() {
        }
    }

    static {
        ThreadFactory threadFactory = new ThreadFactory() { // from class: net.tsz.afinal.core.AsyncTask.1
            private final AtomicInteger mCount = new AtomicInteger(1);

            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, "AsyncTask #" + this.mCount.getAndIncrement());
                thread.setPriority(4);
                return thread;
            }
        };
        sThreadFactory = threadFactory;
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue(10);
        sPoolWorkQueue = linkedBlockingQueue;
        THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(5, 128, 1L, TimeUnit.SECONDS, linkedBlockingQueue, threadFactory, new ThreadPoolExecutor.DiscardOldestPolicy());
        SerialExecutor serialExecutor = new SerialExecutor();
        SERIAL_EXECUTOR = serialExecutor;
        DUAL_THREAD_EXECUTOR = Executors.newFixedThreadPool(3, threadFactory);
        sHandler = new InternalHandler();
        sDefaultExecutor = serialExecutor;
    }

    public AsyncTask() {
        WorkerRunnable<Params, Result> workerRunnable = new WorkerRunnable<Params, Result>() { // from class: net.tsz.afinal.core.AsyncTask.2
            @Override // java.util.concurrent.Callable
            public Result call() throws Exception {
                AsyncTask.this.mTaskInvoked.set(true);
                Process.setThreadPriority(10);
                AsyncTask asyncTask = AsyncTask.this;
                return (Result) asyncTask.postResult(asyncTask.doInBackground(this.mParams));
            }
        };
        this.mWorker = workerRunnable;
        this.mFuture = new FutureTask<Result>(workerRunnable) { // from class: net.tsz.afinal.core.AsyncTask.3
            @Override // java.util.concurrent.FutureTask
            public void done() {
                try {
                    AsyncTask.this.postResultIfNotInvoked(get());
                } catch (InterruptedException e2) {
                    Log.w(AsyncTask.LOG_TAG, e2);
                } catch (CancellationException unused) {
                    AsyncTask.this.postResultIfNotInvoked(null);
                } catch (ExecutionException e3) {
                    throw new RuntimeException("An error occured while executing doInBackground()", e3.getCause());
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finish(Result result) {
        if (isCancelled()) {
            onCancelled(result);
        } else {
            onPostExecute(result);
        }
        this.mStatus = Status.FINISHED;
    }

    public static void init() {
        sHandler.getLooper();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Result postResult(Result result) {
        sHandler.obtainMessage(1, new AsyncTaskResult(this, result)).sendToTarget();
        return result;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postResultIfNotInvoked(Result result) {
        if (this.mTaskInvoked.get()) {
            return;
        }
        postResult(result);
    }

    public static void setDefaultExecutor(Executor executor) {
        sDefaultExecutor = executor;
    }

    public final boolean cancel(boolean z2) {
        this.mCancelled.set(true);
        return this.mFuture.cancel(z2);
    }

    public abstract Result doInBackground(Params... paramsArr);

    public final AsyncTask<Params, Progress, Result> execute(Params... paramsArr) {
        return executeOnExecutor(sDefaultExecutor, paramsArr);
    }

    public final AsyncTask<Params, Progress, Result> executeOnExecutor(Executor executor, Params... paramsArr) {
        if (this.mStatus != Status.PENDING) {
            int i2 = AnonymousClass4.$SwitchMap$net$tsz$afinal$core$AsyncTask$Status[this.mStatus.ordinal()];
            if (i2 == 1) {
                throw new IllegalStateException("Cannot execute task: the task is already running.");
            }
            if (i2 == 2) {
                throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
            }
        }
        this.mStatus = Status.RUNNING;
        onPreExecute();
        this.mWorker.mParams = paramsArr;
        executor.execute(this.mFuture);
        return this;
    }

    public final Result get() throws ExecutionException, InterruptedException {
        return this.mFuture.get();
    }

    public final Status getStatus() {
        return this.mStatus;
    }

    public final boolean isCancelled() {
        return this.mCancelled.get();
    }

    public void onCancelled() {
    }

    public void onCancelled(Result result) {
        onCancelled();
    }

    public void onPostExecute(Result result) {
    }

    public void onPreExecute() {
    }

    public void onProgressUpdate(Progress... progressArr) {
    }

    public final void publishProgress(Progress... progressArr) {
        if (isCancelled()) {
            return;
        }
        sHandler.obtainMessage(2, new AsyncTaskResult(this, progressArr)).sendToTarget();
    }

    public static void execute(Runnable runnable) {
        sDefaultExecutor.execute(runnable);
    }

    public final Result get(long j2, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        return this.mFuture.get(j2, timeUnit);
    }
}
