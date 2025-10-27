package com.arialyy.aria.core.loader;

import android.os.Looper;
import android.util.Log;
import com.arialyy.aria.core.TaskRecord;
import com.arialyy.aria.core.inf.IThreadStateManager;
import com.arialyy.aria.core.listener.IEventListener;
import com.arialyy.aria.core.manager.ThreadTaskManager;
import com.arialyy.aria.core.task.IThreadTask;
import com.arialyy.aria.core.wrapper.AbsTaskWrapper;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public abstract class AbsNormalLoader<T extends AbsTaskWrapper> implements ILoaderVisitor, ILoader {
    protected IInfoTask mInfoTask;
    private IEventListener mListener;
    protected TaskRecord mRecord;
    protected IRecordHandler mRecordHandler;
    protected IThreadStateManager mStateManager;
    protected IThreadTaskBuilder mTTBuilder;
    protected T mTaskWrapper;
    protected File mTempFile;
    private ScheduledThreadPoolExecutor mTimer;
    protected final String TAG = CommonUtil.getClassName((Class) getClass());
    private List<IThreadTask> mTask = new ArrayList();
    private long mUpdateInterval = 1000;
    protected boolean isCancel = false;
    protected boolean isStop = false;
    private boolean isRuning = false;

    public AbsNormalLoader(T t2, IEventListener iEventListener) {
        this.mListener = iEventListener;
        this.mTaskWrapper = t2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void closeTimer() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = this.mTimer;
        if (scheduledThreadPoolExecutor != null && !scheduledThreadPoolExecutor.isShutdown()) {
            this.mTimer.shutdown();
        }
    }

    private void resetState() {
        closeTimer();
        List<IThreadTask> list = this.mTask;
        if (list == null || list.size() == 0) {
            return;
        }
        for (int i2 = 0; i2 < this.mTask.size(); i2++) {
            this.mTask.get(i2).breakTask();
        }
        this.mTask.clear();
    }

    private void startFlow() {
        if (isBreak()) {
            return;
        }
        Looper.prepare();
        Looper looperMyLooper = Looper.myLooper();
        if (looperMyLooper == Looper.getMainLooper()) {
            throw new IllegalThreadStateException("不能在主线程程序中调用Loader");
        }
        this.isRuning = true;
        resetState();
        onPostPre();
        handleTask(looperMyLooper);
        Looper.loop();
    }

    @Override // com.arialyy.aria.core.loader.ILoader
    public final synchronized void cancel() {
        if (this.isCancel) {
            ALog.d(this.TAG, String.format("任务【%s】正在删除，删除任务失败", this.mTaskWrapper.getKey()));
            return;
        }
        closeTimer();
        this.isCancel = true;
        onCancel();
        for (int i2 = 0; i2 < this.mTask.size(); i2++) {
            IThreadTask iThreadTask = this.mTask.get(i2);
            if (iThreadTask != null && !iThreadTask.isThreadComplete()) {
                iThreadTask.cancel();
            }
        }
        ThreadTaskManager.getInstance().removeTaskThread(this.mTaskWrapper.getKey());
        onPostCancel();
        onDestroy();
        this.mListener.onCancel();
    }

    public void checkComponent() {
        if (this.mRecordHandler == null) {
            throw new NullPointerException("任务记录组件为空");
        }
        if (this.mInfoTask == null) {
            throw new NullPointerException("文件信息组件为空");
        }
        if (this.mStateManager == null) {
            throw new NullPointerException("任务状态管理组件为空");
        }
        if (this.mTTBuilder == null) {
            throw new NullPointerException("线程任务组件为空");
        }
    }

    public long delayTimer() {
        return 1000L;
    }

    public abstract long getFileSize();

    @Override // com.arialyy.aria.core.loader.ILoader
    public String getKey() {
        return this.mTaskWrapper.getKey();
    }

    public IEventListener getListener() {
        return this.mListener;
    }

    public IThreadStateManager getStateManager() {
        return this.mStateManager;
    }

    public List<IThreadTask> getTaskList() {
        return this.mTask;
    }

    public abstract void handleTask(Looper looper);

    @Override // com.arialyy.aria.core.loader.ILoader
    public boolean isBreak() {
        if (!this.isCancel && !this.isStop) {
            return false;
        }
        ALog.d(this.TAG, "isCancel = " + this.isCancel + ", isStop = " + this.isStop);
        ALog.d(this.TAG, String.format("任务【%s】已停止或取消了", this.mTaskWrapper.getKey()));
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0017  */
    @Override // com.arialyy.aria.core.loader.ILoader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized boolean isRunning() {
        /*
            r2 = this;
            monitor-enter(r2)
            com.arialyy.aria.core.manager.ThreadTaskManager r0 = com.arialyy.aria.core.manager.ThreadTaskManager.getInstance()     // Catch: java.lang.Throwable -> L1a
            T extends com.arialyy.aria.core.wrapper.AbsTaskWrapper r1 = r2.mTaskWrapper     // Catch: java.lang.Throwable -> L1a
            java.lang.String r1 = r1.getKey()     // Catch: java.lang.Throwable -> L1a
            boolean r0 = r0.taskIsRunning(r1)     // Catch: java.lang.Throwable -> L1a
            if (r0 == 0) goto L17
            boolean r0 = r2.isRuning     // Catch: java.lang.Throwable -> L1a
            if (r0 == 0) goto L17
            r0 = 1
            goto L18
        L17:
            r0 = 0
        L18:
            monitor-exit(r2)
            return r0
        L1a:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.arialyy.aria.core.loader.AbsNormalLoader.isRunning():boolean");
    }

    public void onCancel() {
    }

    public void onDestroy() {
        this.isRuning = false;
    }

    public void onPostCancel() {
    }

    public void onPostPre() {
    }

    public void onPostStop() {
    }

    public void onStop() {
    }

    public void retryTask() {
        ALog.w(this.TAG, String.format("任务【%s】开始重试", this.mTaskWrapper.getKey()));
        startFlow();
    }

    @Override // java.lang.Runnable
    public void run() {
        checkComponent();
        if (isRunning()) {
            ALog.d(this.TAG, String.format("任务【%s】正在执行，启动任务失败", this.mTaskWrapper.getKey()));
        } else {
            startFlow();
        }
    }

    public void setUpdateInterval(long j2) {
        if (j2 < 0) {
            ALog.w(this.TAG, "更新间隔不能小于0，默认为1000毫秒");
        } else {
            this.mUpdateInterval = j2;
        }
    }

    public synchronized void startTimer() {
        if (isBreak()) {
            return;
        }
        ALog.d(this.TAG, String.format("启动定时器，delayTimer = %s, updateInterval = %s", Long.valueOf(delayTimer()), Long.valueOf(this.mUpdateInterval)));
        closeTimer();
        try {
            ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
            this.mTimer = scheduledThreadPoolExecutor;
            scheduledThreadPoolExecutor.scheduleWithFixedDelay(new Runnable() { // from class: com.arialyy.aria.core.loader.AbsNormalLoader.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        AbsNormalLoader absNormalLoader = AbsNormalLoader.this;
                        IThreadStateManager iThreadStateManager = absNormalLoader.mStateManager;
                        if (iThreadStateManager == null) {
                            ALog.e(absNormalLoader.TAG, "stateManager is null");
                        } else if (iThreadStateManager.isComplete() || AbsNormalLoader.this.mStateManager.isFail() || !AbsNormalLoader.this.isRunning() || AbsNormalLoader.this.isBreak()) {
                            ThreadTaskManager.getInstance().removeTaskThread(AbsNormalLoader.this.mTaskWrapper.getKey());
                            AbsNormalLoader.this.closeTimer();
                            AbsNormalLoader.this.onDestroy();
                        } else if (AbsNormalLoader.this.mStateManager.getCurrentProgress() >= 0) {
                            Log.d(AbsNormalLoader.this.TAG, "running...");
                            AbsNormalLoader.this.mListener.onProgress(AbsNormalLoader.this.mStateManager.getCurrentProgress());
                        } else {
                            Log.d(AbsNormalLoader.this.TAG, "未知状态");
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }, delayTimer(), this.mUpdateInterval, TimeUnit.MILLISECONDS);
        } catch (Exception e2) {
            ALog.e(this.TAG, "启动定时器失败");
            e2.printStackTrace();
        }
    }

    @Override // com.arialyy.aria.core.loader.ILoader
    public final synchronized void stop() {
        if (this.isStop) {
            return;
        }
        closeTimer();
        this.isStop = true;
        onStop();
        for (int i2 = 0; i2 < this.mTask.size(); i2++) {
            IThreadTask iThreadTask = this.mTask.get(i2);
            if (iThreadTask != null && !iThreadTask.isThreadComplete()) {
                iThreadTask.stop();
            }
        }
        ThreadTaskManager.getInstance().removeTaskThread(this.mTaskWrapper.getKey());
        onPostStop();
        onDestroy();
        this.mListener.onStop(getCurrentProgress());
    }
}
