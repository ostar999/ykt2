package com.google.android.exoplayer2.upstream;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

/* loaded from: classes3.dex */
public final class Loader implements LoaderErrorThrower {
    private static final int ACTION_TYPE_DONT_RETRY = 2;
    private static final int ACTION_TYPE_DONT_RETRY_FATAL = 3;
    private static final int ACTION_TYPE_RETRY = 0;
    private static final int ACTION_TYPE_RETRY_AND_RESET_ERROR_COUNT = 1;
    public static final LoadErrorAction DONT_RETRY;
    public static final LoadErrorAction DONT_RETRY_FATAL;
    public static final LoadErrorAction RETRY;
    public static final LoadErrorAction RETRY_RESET_ERROR_COUNT;
    private static final String THREAD_NAME_PREFIX = "ExoPlayer:Loader:";

    @Nullable
    private LoadTask<? extends Loadable> currentTask;
    private final ExecutorService downloadExecutorService;

    @Nullable
    private IOException fatalError;

    public interface Callback<T extends Loadable> {
        void onLoadCanceled(T t2, long j2, long j3, boolean z2);

        void onLoadCompleted(T t2, long j2, long j3);

        LoadErrorAction onLoadError(T t2, long j2, long j3, IOException iOException, int i2);
    }

    public static final class LoadErrorAction {
        private final long retryDelayMillis;
        private final int type;

        public boolean isRetry() {
            int i2 = this.type;
            return i2 == 0 || i2 == 1;
        }

        private LoadErrorAction(int i2, long j2) {
            this.type = i2;
            this.retryDelayMillis = j2;
        }
    }

    @SuppressLint({"HandlerLeak"})
    public final class LoadTask<T extends Loadable> extends Handler implements Runnable {
        private static final int MSG_FATAL_ERROR = 3;
        private static final int MSG_FINISH = 1;
        private static final int MSG_IO_EXCEPTION = 2;
        private static final int MSG_START = 0;
        private static final String TAG = "LoadTask";

        @Nullable
        private Callback<T> callback;
        private boolean canceled;

        @Nullable
        private IOException currentError;
        public final int defaultMinRetryCount;
        private int errorCount;

        @Nullable
        private Thread executorThread;
        private final T loadable;
        private volatile boolean released;
        private final long startTimeMs;

        public LoadTask(Looper looper, T t2, Callback<T> callback, int i2, long j2) {
            super(looper);
            this.loadable = t2;
            this.callback = callback;
            this.defaultMinRetryCount = i2;
            this.startTimeMs = j2;
        }

        private void execute() {
            this.currentError = null;
            Loader.this.downloadExecutorService.execute((Runnable) Assertions.checkNotNull(Loader.this.currentTask));
        }

        private void finish() {
            Loader.this.currentTask = null;
        }

        private long getRetryDelayMillis() {
            return Math.min((this.errorCount - 1) * 1000, 5000);
        }

        public void cancel(boolean z2) {
            this.released = z2;
            this.currentError = null;
            if (hasMessages(0)) {
                this.canceled = true;
                removeMessages(0);
                if (!z2) {
                    sendEmptyMessage(1);
                }
            } else {
                synchronized (this) {
                    this.canceled = true;
                    this.loadable.cancelLoad();
                    Thread thread = this.executorThread;
                    if (thread != null) {
                        thread.interrupt();
                    }
                }
            }
            if (z2) {
                finish();
                long jElapsedRealtime = SystemClock.elapsedRealtime();
                ((Callback) Assertions.checkNotNull(this.callback)).onLoadCanceled(this.loadable, jElapsedRealtime, jElapsedRealtime - this.startTimeMs, true);
                this.callback = null;
            }
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (this.released) {
                return;
            }
            int i2 = message.what;
            if (i2 == 0) {
                execute();
                return;
            }
            if (i2 == 3) {
                throw ((Error) message.obj);
            }
            finish();
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            long j2 = jElapsedRealtime - this.startTimeMs;
            Callback callback = (Callback) Assertions.checkNotNull(this.callback);
            if (this.canceled) {
                callback.onLoadCanceled(this.loadable, jElapsedRealtime, j2, false);
                return;
            }
            int i3 = message.what;
            if (i3 == 1) {
                try {
                    callback.onLoadCompleted(this.loadable, jElapsedRealtime, j2);
                    return;
                } catch (RuntimeException e2) {
                    Log.e(TAG, "Unexpected exception handling load completed", e2);
                    Loader.this.fatalError = new UnexpectedLoaderException(e2);
                    return;
                }
            }
            if (i3 != 2) {
                return;
            }
            IOException iOException = (IOException) message.obj;
            this.currentError = iOException;
            int i4 = this.errorCount + 1;
            this.errorCount = i4;
            LoadErrorAction loadErrorActionOnLoadError = callback.onLoadError(this.loadable, jElapsedRealtime, j2, iOException, i4);
            if (loadErrorActionOnLoadError.type == 3) {
                Loader.this.fatalError = this.currentError;
            } else if (loadErrorActionOnLoadError.type != 2) {
                if (loadErrorActionOnLoadError.type == 1) {
                    this.errorCount = 1;
                }
                start(loadErrorActionOnLoadError.retryDelayMillis != C.TIME_UNSET ? loadErrorActionOnLoadError.retryDelayMillis : getRetryDelayMillis());
            }
        }

        public void maybeThrowError(int i2) throws IOException {
            IOException iOException = this.currentError;
            if (iOException != null && this.errorCount > i2) {
                throw iOException;
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            boolean z2;
            try {
                synchronized (this) {
                    z2 = !this.canceled;
                    this.executorThread = Thread.currentThread();
                }
                if (z2) {
                    String simpleName = this.loadable.getClass().getSimpleName();
                    TraceUtil.beginSection(simpleName.length() != 0 ? "load:".concat(simpleName) : new String("load:"));
                    try {
                        this.loadable.load();
                        TraceUtil.endSection();
                    } catch (Throwable th) {
                        TraceUtil.endSection();
                        throw th;
                    }
                }
                synchronized (this) {
                    this.executorThread = null;
                    Thread.interrupted();
                }
                if (this.released) {
                    return;
                }
                sendEmptyMessage(1);
            } catch (IOException e2) {
                if (this.released) {
                    return;
                }
                obtainMessage(2, e2).sendToTarget();
            } catch (Exception e3) {
                if (this.released) {
                    return;
                }
                Log.e(TAG, "Unexpected exception loading stream", e3);
                obtainMessage(2, new UnexpectedLoaderException(e3)).sendToTarget();
            } catch (OutOfMemoryError e4) {
                if (this.released) {
                    return;
                }
                Log.e(TAG, "OutOfMemory error loading stream", e4);
                obtainMessage(2, new UnexpectedLoaderException(e4)).sendToTarget();
            } catch (Error e5) {
                if (!this.released) {
                    Log.e(TAG, "Unexpected error loading stream", e5);
                    obtainMessage(3, e5).sendToTarget();
                }
                throw e5;
            }
        }

        public void start(long j2) {
            Assertions.checkState(Loader.this.currentTask == null);
            Loader.this.currentTask = this;
            if (j2 > 0) {
                sendEmptyMessageDelayed(0, j2);
            } else {
                execute();
            }
        }
    }

    public interface Loadable {
        void cancelLoad();

        void load() throws IOException;
    }

    public interface ReleaseCallback {
        void onLoaderReleased();
    }

    public static final class ReleaseTask implements Runnable {
        private final ReleaseCallback callback;

        public ReleaseTask(ReleaseCallback releaseCallback) {
            this.callback = releaseCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.callback.onLoaderReleased();
        }
    }

    public static final class UnexpectedLoaderException extends IOException {
        public UnexpectedLoaderException(Throwable th) {
            String simpleName = th.getClass().getSimpleName();
            String message = th.getMessage();
            StringBuilder sb = new StringBuilder(simpleName.length() + 13 + String.valueOf(message).length());
            sb.append("Unexpected ");
            sb.append(simpleName);
            sb.append(": ");
            sb.append(message);
            super(sb.toString(), th);
        }
    }

    static {
        long j2 = C.TIME_UNSET;
        RETRY = createRetryAction(false, C.TIME_UNSET);
        RETRY_RESET_ERROR_COUNT = createRetryAction(true, C.TIME_UNSET);
        DONT_RETRY = new LoadErrorAction(2, j2);
        DONT_RETRY_FATAL = new LoadErrorAction(3, j2);
    }

    public Loader(String str) {
        String strValueOf = String.valueOf(str);
        this.downloadExecutorService = Util.newSingleThreadExecutor(strValueOf.length() != 0 ? THREAD_NAME_PREFIX.concat(strValueOf) : new String(THREAD_NAME_PREFIX));
    }

    public static LoadErrorAction createRetryAction(boolean z2, long j2) {
        return new LoadErrorAction(z2 ? 1 : 0, j2);
    }

    public void cancelLoading() {
        ((LoadTask) Assertions.checkStateNotNull(this.currentTask)).cancel(false);
    }

    public void clearFatalError() {
        this.fatalError = null;
    }

    public boolean hasFatalError() {
        return this.fatalError != null;
    }

    public boolean isLoading() {
        return this.currentTask != null;
    }

    @Override // com.google.android.exoplayer2.upstream.LoaderErrorThrower
    public void maybeThrowError() throws IOException {
        maybeThrowError(Integer.MIN_VALUE);
    }

    public void release() {
        release(null);
    }

    public <T extends Loadable> long startLoading(T t2, Callback<T> callback, int i2) {
        Looper looper = (Looper) Assertions.checkStateNotNull(Looper.myLooper());
        this.fatalError = null;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        new LoadTask(looper, t2, callback, i2, jElapsedRealtime).start(0L);
        return jElapsedRealtime;
    }

    @Override // com.google.android.exoplayer2.upstream.LoaderErrorThrower
    public void maybeThrowError(int i2) throws IOException {
        IOException iOException = this.fatalError;
        if (iOException != null) {
            throw iOException;
        }
        LoadTask<? extends Loadable> loadTask = this.currentTask;
        if (loadTask != null) {
            if (i2 == Integer.MIN_VALUE) {
                i2 = loadTask.defaultMinRetryCount;
            }
            loadTask.maybeThrowError(i2);
        }
    }

    public void release(@Nullable ReleaseCallback releaseCallback) {
        LoadTask<? extends Loadable> loadTask = this.currentTask;
        if (loadTask != null) {
            loadTask.cancel(true);
        }
        if (releaseCallback != null) {
            this.downloadExecutorService.execute(new ReleaseTask(releaseCallback));
        }
        this.downloadExecutorService.shutdown();
    }
}
