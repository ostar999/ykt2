package com.arialyy.aria.core.task;

import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import com.arialyy.aria.core.AriaConfig;
import com.arialyy.aria.core.ThreadRecord;
import com.arialyy.aria.core.common.AbsEntity;
import com.arialyy.aria.core.common.SubThreadConfig;
import com.arialyy.aria.core.inf.IThreadStateManager;
import com.arialyy.aria.core.listener.ISchedulers;
import com.arialyy.aria.core.manager.ThreadTaskManager;
import com.arialyy.aria.core.wrapper.AbsTaskWrapper;
import com.arialyy.aria.core.wrapper.ITaskWrapper;
import com.arialyy.aria.exception.AriaException;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;
import com.arialyy.aria.util.ErrorHelp;
import com.arialyy.aria.util.FileUtil;
import com.arialyy.aria.util.NetUtils;
import java.io.File;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes2.dex */
public class ThreadTask implements IThreadTask, IThreadTaskObserver {
    private boolean isNotNetRetry;
    private IThreadTaskAdapter mAdapter;
    private SubThreadConfig mConfig;
    private ExecutorService mConfigThreadPool;
    private AbsEntity mEntity;
    private long mLastRangeProgress;
    private long mLastSaveTime;
    private long mLastSendProgressTime;
    private long mRangeProgress;
    private ThreadRecord mRecord;
    private Handler mStateHandler;
    protected AbsTaskWrapper mTaskWrapper;
    private String mThreadName;
    private long updateInterval;
    private final int RETRY_NUM = 2;
    private final String TAG = CommonUtil.getClassName((Class) getClass());
    private int mFailTimes = 0;
    private boolean taskBreak = false;
    private boolean isDestroy = false;
    protected boolean isCancel = false;
    protected boolean isStop = false;
    private Thread mConfigThread = new Thread(new Runnable() { // from class: com.arialyy.aria.core.task.ThreadTask.1
        @Override // java.lang.Runnable
        public void run() throws SecurityException, IllegalArgumentException {
            Process.setThreadPriority(10);
            ThreadTask.this.writeConfig(false, ThreadTask.this.mRangeProgress);
        }
    });

    public ThreadTask(SubThreadConfig subThreadConfig) {
        this.mConfig = subThreadConfig;
        AbsTaskWrapper absTaskWrapper = subThreadConfig.taskWrapper;
        this.mTaskWrapper = absTaskWrapper;
        this.mRecord = subThreadConfig.record;
        this.mStateHandler = subThreadConfig.stateHandler;
        this.mEntity = absTaskWrapper.getEntity();
        this.mLastSaveTime = System.currentTimeMillis();
        this.mConfigThreadPool = Executors.newCachedThreadPool();
        this.isNotNetRetry = AriaConfig.getInstance().getAConfig().isNotNetRetry();
        long j2 = this.mRecord.startLocation;
        this.mRangeProgress = j2;
        this.mLastRangeProgress = j2;
        this.updateInterval = subThreadConfig.updateInterval;
        checkFileExist();
    }

    private void checkFileExist() {
        if (getConfig().tempFile.exists()) {
            return;
        }
        FileUtil.createFile(getConfig().tempFile);
    }

    private void handleBlockRecord() {
        SubThreadConfig subThreadConfig = this.mConfig;
        if (subThreadConfig.isBlock) {
            File file = subThreadConfig.tempFile;
            long length = file.length();
            long j2 = this.mRecord.blockLen;
            if (!file.exists()) {
                ALog.i(this.TAG, String.format("分块文件【%s】不存在，该分块将重新开始", file.getName()));
                ThreadRecord threadRecord = this.mRecord;
                threadRecord.isComplete = false;
                threadRecord.startLocation = threadRecord.endLocation - j2;
            } else if (length > j2) {
                ALog.i(this.TAG, String.format("分块【%s】错误，将重新下载该分块", file.getName()));
                FileUtil.deleteFile(file);
                ThreadRecord threadRecord2 = this.mRecord;
                threadRecord2.startLocation = threadRecord2.endLocation - threadRecord2.blockLen;
                threadRecord2.isComplete = false;
            } else {
                ThreadRecord threadRecord3 = this.mRecord;
                long j3 = threadRecord3.blockLen;
                if (length < j3) {
                    threadRecord3.startLocation = (threadRecord3.endLocation - j3) + length;
                    threadRecord3.isComplete = false;
                    updateState(6, null);
                    ALog.i(this.TAG, String.format("修正分块【%s】记录，开始位置：%s，结束位置：%s", file.getName(), Long.valueOf(this.mRecord.startLocation), Long.valueOf(this.mRecord.endLocation)));
                } else {
                    ALog.i(this.TAG, String.format("分块【%s】已完成，更新记录", file.getName()));
                    this.mRecord.isComplete = true;
                }
            }
            this.mRecord.update();
        }
    }

    private void retryBlockTask(boolean z2) {
        if (!NetUtils.isConnected(AriaConfig.getInstance().getAPP()) && !this.isNotNetRetry) {
            ALog.w(this.TAG, String.format("分块【%s】重试失败，网络未连接", getFileName()));
            sendFailMsg(null, false);
            return;
        }
        if (this.mFailTimes >= 2 || !z2 || (!(NetUtils.isConnected(AriaConfig.getInstance().getAPP()) || this.isNotNetRetry) || isBreak())) {
            ALog.e(this.TAG, String.format("任务【%s】执行失败", getFileName()));
            sendFailMsg(null, z2);
        } else {
            ALog.w(this.TAG, String.format("分块【%s】第%s次重试", getFileName(), String.valueOf(this.mFailTimes)));
            this.mFailTimes++;
            handleBlockRecord();
            ThreadTaskManager.getInstance().retryThread(this);
        }
    }

    private void retryM3U8Peer(boolean z2) {
        if (this.mConfig.ignoreFailure) {
            ALog.d(this.TAG, "忽略失败的切片");
            sendFailMsg(null, false);
            return;
        }
        if (!NetUtils.isConnected(AriaConfig.getInstance().getAPP()) && !this.isNotNetRetry) {
            ALog.w(this.TAG, String.format("ts切片【%s】重试失败，网络未连接", getFileName()));
            sendFailMsg(null, false);
            return;
        }
        if (this.mFailTimes >= 2 || !z2 || (!(NetUtils.isConnected(AriaConfig.getInstance().getAPP()) || this.isNotNetRetry) || isBreak())) {
            sendFailMsg(null, false);
            return;
        }
        ALog.w(this.TAG, String.format("ts切片【%s】第%s重试", getFileName(), String.valueOf(this.mFailTimes)));
        this.mFailTimes++;
        FileUtil.deleteFile(this.mConfig.tempFile);
        FileUtil.createFile(this.mConfig.tempFile);
        ThreadTaskManager.getInstance().retryThread(this);
    }

    private void sendFailMsg(AriaException ariaException, boolean z2) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(IThreadStateManager.DATA_RETRY, z2);
        if (ariaException != null) {
            bundle.putSerializable(IThreadStateManager.DATA_ERROR_INFO, ariaException);
        }
        updateState(2, bundle);
    }

    private void sendM3U8Info(int i2, Message message) {
        Bundle data = message.getData();
        if (i2 != 6) {
            message.obj = this;
        }
        if (i2 == 4 || i2 == 2) {
            data.putString(ISchedulers.DATA_M3U8_URL, getConfig().url);
            data.putString(ISchedulers.DATA_M3U8_PEER_PATH, getConfig().tempFile.getPath());
            data.putInt(ISchedulers.DATA_M3U8_PEER_INDEX, getConfig().peerIndex);
        }
    }

    private void sendRunningState() {
        Message messageObtainMessage = this.mStateHandler.obtainMessage();
        Bundle data = messageObtainMessage.getData();
        if (data == null) {
            data = new Bundle();
            messageObtainMessage.setData(data);
        }
        data.putString(IThreadStateManager.DATA_THREAD_NAME, getThreadName());
        data.putLong(IThreadStateManager.DATA_ADD_LEN, this.mRangeProgress - this.mLastRangeProgress);
        messageObtainMessage.what = 5;
        messageObtainMessage.obj = Long.valueOf(this.mRangeProgress);
        Thread thread = this.mStateHandler.getLooper().getThread();
        if (!thread.isAlive() || thread.isInterrupted()) {
            return;
        }
        messageObtainMessage.sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeConfig(boolean z2, long j2) {
        ThreadRecord threadRecord = this.mRecord;
        if (threadRecord != null) {
            threadRecord.isComplete = z2;
            SubThreadConfig subThreadConfig = this.mConfig;
            if (subThreadConfig.isBlock) {
                threadRecord.startLocation = subThreadConfig.tempFile.length();
            } else if (0 < j2 && j2 < threadRecord.endLocation) {
                threadRecord.startLocation = j2;
            }
            this.mRecord.update();
        }
    }

    @Override // com.arialyy.aria.core.task.IThreadTask
    public void breakTask() {
        this.taskBreak = true;
        if (!this.mTaskWrapper.isSupportBP()) {
            ALog.i(this.TAG, String.format("任务【%s】已中断", getFileName()));
            return;
        }
        long j2 = this.mRangeProgress;
        updateState(1, null);
        ALog.d(this.TAG, String.format("任务【%s】thread__%s__中断【停止位置：%s】", getFileName(), Integer.valueOf(this.mRecord.threadId), Long.valueOf(j2)));
        writeConfig(false, j2);
    }

    @Override // com.arialyy.aria.core.task.IThreadTask
    public void cancel() {
        this.isCancel = true;
        updateState(3, null);
        ALog.d(this.TAG, String.format("任务【%s】thread__%s__取消", getFileName(), Integer.valueOf(this.mRecord.threadId)));
    }

    @Override // com.arialyy.aria.core.task.IThreadTask
    public boolean checkBlock() {
        SubThreadConfig subThreadConfig = this.mConfig;
        if (!subThreadConfig.isBlock) {
            return true;
        }
        File file = subThreadConfig.tempFile;
        if (file.exists() && file.length() == this.mRecord.blockLen) {
            return true;
        }
        ALog.i(this.TAG, String.format("分块【%s】错误，blockFileLen: %s, threadRect: %s; 即将重新下载该分块，开始位置：%s，结束位置：%s", file.getName(), Long.valueOf(file.length()), Long.valueOf(this.mRecord.blockLen), Long.valueOf(this.mRecord.startLocation), Long.valueOf(this.mRecord.endLocation)));
        if (file.exists()) {
            FileUtil.deleteFile(file);
            ALog.i(this.TAG, String.format("删除分块【%s】成功", file.getName()));
        }
        retryBlockTask(isBreak());
        return false;
    }

    @Override // com.arialyy.aria.core.task.IThreadTask
    public void destroy() {
        this.isDestroy = true;
    }

    public void fail(long j2, AriaException ariaException, boolean z2) throws Throwable {
        if (ariaException != null) {
            ariaException.printStackTrace();
        }
        if (this.mTaskWrapper.getRequestType() == 7) {
            writeConfig(false, 0L);
            retryM3U8Peer(z2);
        } else if (this.mTaskWrapper.isSupportBP()) {
            writeConfig(false, j2);
            retryBlockTask(z2);
        } else {
            ALog.e(this.TAG, String.format("任务【%s】执行失败", getFileName()));
            ErrorHelp.saveError("", ALog.getExceptionString(ariaException));
            sendFailMsg(null, z2);
        }
    }

    public void finalize() throws Throwable {
        super.finalize();
        ExecutorService executorService = this.mConfigThreadPool;
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    public SubThreadConfig getConfig() {
        return this.mConfig;
    }

    public AbsEntity getEntity() {
        return this.mEntity;
    }

    public String getFileName() {
        return this.mConfig.tempFile.getName();
    }

    public ITaskWrapper getTaskWrapper() {
        return this.mTaskWrapper;
    }

    @Override // com.arialyy.aria.core.task.IThreadTask
    public int getThreadId() {
        return this.mRecord.threadId;
    }

    @Override // com.arialyy.aria.core.task.IThreadTask
    public String getThreadName() {
        String str = this.mThreadName;
        if (str != null) {
            return str;
        }
        String threadName = CommonUtil.getThreadName(getEntity().getKey(), getThreadId());
        this.mThreadName = threadName;
        return threadName;
    }

    @Override // com.arialyy.aria.core.task.IThreadTaskObserver
    public long getThreadProgress() {
        return this.mRangeProgress;
    }

    @Override // com.arialyy.aria.core.task.IThreadTask
    public boolean isBreak() {
        return this.isCancel || this.isStop || this.taskBreak;
    }

    @Override // com.arialyy.aria.core.task.IThreadTask
    public boolean isDestroy() {
        return Thread.currentThread().isInterrupted();
    }

    @Override // com.arialyy.aria.core.task.IThreadTask
    public boolean isLive() {
        return (Thread.currentThread().isInterrupted() || this.isDestroy) ? false : true;
    }

    @Override // com.arialyy.aria.core.task.IThreadTask
    public boolean isThreadComplete() {
        return this.mRecord.isComplete;
    }

    public void setAdapter(IThreadTaskAdapter iThreadTaskAdapter) {
        this.mAdapter = iThreadTaskAdapter;
        iThreadTaskAdapter.attach(this);
    }

    @Override // com.arialyy.aria.core.task.IThreadTask
    public void setMaxSpeed(int i2) {
        IThreadTaskAdapter iThreadTaskAdapter = this.mAdapter;
        if (iThreadTaskAdapter != null) {
            iThreadTaskAdapter.setMaxSpeed(i2);
        }
    }

    @Override // com.arialyy.aria.core.task.IThreadTask
    public void stop() {
        this.isStop = true;
        long j2 = this.mRangeProgress;
        updateState(1, null);
        if (this.mTaskWrapper.getRequestType() == 7) {
            writeConfig(false, getConfig().tempFile.length());
            ALog.i(this.TAG, String.format("任务【%s】已停止", getFileName()));
        } else if (!this.mTaskWrapper.isSupportBP()) {
            ALog.i(this.TAG, String.format("任务【%s】已停止", getFileName()));
        } else {
            ALog.d(this.TAG, String.format("任务【%s】thread__%s__停止【当前线程停止位置：%s】", getFileName(), Integer.valueOf(this.mRecord.threadId), Long.valueOf(j2)));
            writeConfig(false, j2);
        }
    }

    @Override // com.arialyy.aria.core.task.IThreadTaskObserver
    public synchronized void updateCompleteState() {
        ALog.i(this.TAG, String.format("任务【%s】线程__%s__完成", getTaskWrapper().getKey(), Integer.valueOf(this.mRecord.threadId)));
        writeConfig(true, this.mRecord.endLocation);
        sendRunningState();
        updateState(4, null);
    }

    @Override // com.arialyy.aria.core.task.IThreadTaskObserver
    public synchronized void updateFailState(AriaException ariaException, boolean z2) {
        fail(this.mRangeProgress, ariaException, z2);
    }

    @Override // com.arialyy.aria.core.task.IThreadTaskObserver
    public synchronized void updateProgress(long j2) {
        this.mRangeProgress += j2;
        Thread thread = this.mStateHandler.getLooper().getThread();
        if (thread.isAlive() && !thread.isInterrupted()) {
            if (System.currentTimeMillis() - this.mLastSendProgressTime > this.updateInterval) {
                sendRunningState();
                this.mLastRangeProgress = this.mRangeProgress;
                this.mLastSendProgressTime = System.currentTimeMillis();
            }
            if (System.currentTimeMillis() - this.mLastSaveTime > 5000 && this.mRangeProgress < this.mRecord.endLocation) {
                this.mLastSaveTime = System.currentTimeMillis();
                if (!this.mConfigThreadPool.isShutdown()) {
                    this.mConfigThreadPool.execute(this.mConfigThread);
                }
            }
        }
    }

    @Override // com.arialyy.aria.core.task.IThreadTaskObserver
    public synchronized void updateState(int i2, Bundle bundle) {
        Message messageObtainMessage = this.mStateHandler.obtainMessage();
        if (bundle == null) {
            bundle = new Bundle();
        }
        messageObtainMessage.setData(bundle);
        bundle.putString(IThreadStateManager.DATA_THREAD_NAME, getThreadName());
        bundle.putLong(IThreadStateManager.DATA_THREAD_LOCATION, this.mRangeProgress);
        messageObtainMessage.what = i2;
        if (getConfig().threadType == 3) {
            sendM3U8Info(i2, messageObtainMessage);
        }
        Thread thread = this.mStateHandler.getLooper().getThread();
        if (thread.isAlive() && !thread.isInterrupted()) {
            messageObtainMessage.sendToTarget();
        }
    }

    @Override // java.util.concurrent.Callable
    /* renamed from: call, reason: merged with bridge method [inline-methods] */
    public IThreadTask call2() throws Exception {
        this.isDestroy = false;
        Process.setThreadPriority(10);
        TrafficStats.setThreadStatsTag(UUID.randomUUID().toString().hashCode());
        this.mAdapter.call(this);
        return this;
    }
}
